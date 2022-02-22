package committee.nova.portablecraft.common.items;

import committee.nova.portablecraft.core.PlayerSpawnData;
import committee.nova.portablecraft.core.WorldSaveInventory;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Optional;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/19 17:39
 * Version: 1.0
 */
public class BedItem extends Item {


    public BedItem() {
        super(new Properties()
                .tab(ModTabs.tab)
                .stacksTo(1)
        );
        setRegistryName("bed1");
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (!worldIn.isClientSide) {
            if (WorldSaveInventory.getInstance().isSleepingPlayer()) {
                for(int a = 0; a < WorldSaveInventory.getInstance().returnSleepingList().size(); ++a) {
                    PlayerSpawnData splayer = WorldSaveInventory.getInstance().returnSleepingList().get(a);
                    PlayerEntity player = splayer.getPlayer();
                    if (!player.isSleeping()) {
                        if (splayer.isBed()) {
                            if (splayer.getBedLocate() != 0L) {
                                BlockPos pos = BlockPos.of(splayer.getBedLocate());
                                ((ServerWorld)(player.level)).setDefaultSpawnPos(pos, 0);
                            }

                            WorldSaveInventory.getInstance().removeSleepingPlayer(player);
                        } else {
                            this.sleeping((ServerPlayerEntity) player);
                            splayer.setBed(true);
                        }
                    }
                }
            }

        }
    }


    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        if (!pLevel.isClientSide && this.canSleep((ServerPlayerEntity) pPlayer, pLevel)) {
            long bedLocation = 0L;
            if (((ServerWorld)pLevel).getSharedSpawnPos() != null) {
                bedLocation = ((ServerWorld)pLevel).getSharedSpawnPos().asLong();
            }

            if (WorldSaveInventory.getInstance() != null) {
                WorldSaveInventory.getInstance().setSleepingPlayer(new PlayerSpawnData(pPlayer, bedLocation));
            }
        }
        return super.use(pLevel, pPlayer, pHand);
    }

    private void sleeping(ServerPlayerEntity player) {
        BlockPos pos = player.blockPosition();
        player.startSleeping(pos);
    }

    private boolean canSleep(ServerPlayerEntity player, World worldIn) {
        if (!player.isSleeping() && player.isAlive()) {
            BlockPos pos = player.blockPosition();
            PlayerEntity.SleepResult status = this.canSleep(player, worldIn, pos);
            if (!player.isOnGround()) {
                player.displayClientMessage(new TranslationTextComponent("potioncraft.portablebed.isGround", new Object[0]), true);
            } else if (status == PlayerEntity.SleepResult.NOT_POSSIBLE_NOW) {
                player.displayClientMessage(new TranslationTextComponent("tile.bed.noSleep", new Object[0]), true);
            } else if (status == PlayerEntity.SleepResult.NOT_SAFE) {
                player.displayClientMessage(new TranslationTextComponent("tile.bed.notSafe", new Object[0]), true);
            } else return status == PlayerEntity.SleepResult.OTHER_PROBLEM;
        }
        return false;
    }


    private PlayerEntity.SleepResult canSleep(ServerPlayerEntity player, World worldIn, BlockPos pos) {
        PlayerSleepInBedEvent event = new PlayerSleepInBedEvent(player, Optional.of(pos));
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResultStatus() != null) {
            return event.getResultStatus();
        } else if (!worldIn.dimensionType().natural()) {
            return PlayerEntity.SleepResult.NOT_POSSIBLE_HERE;
        } else if (worldIn.isDay()) {
            return PlayerEntity.SleepResult.NOT_POSSIBLE_NOW;
        } else {
            player.setRespawnPosition(worldIn.dimension(), pos, player.yRot, false, true);
            if (!net.minecraftforge.event.ForgeEventFactory.fireSleepingTimeCheck(player, Optional.of(pos))) {
                return PlayerEntity.SleepResult.NOT_POSSIBLE_NOW;
            } else {
                if (!player.isCreative()) {
                    double d0 = 8.0D;
                    double d1 = 5.0D;
                    Vector3d vector3d = Vector3d.atBottomCenterOf(pos);
                    List<MonsterEntity> list = worldIn.getEntitiesOfClass(MonsterEntity.class, new AxisAlignedBB(vector3d.x() - 8.0D, vector3d.y() - 5.0D, vector3d.z() - 8.0D, vector3d.x() + 8.0D, vector3d.y() + 5.0D, vector3d.z() + 8.0D), (p_241146_1_) -> {
                        return p_241146_1_.isPreventingPlayerRest(player);
                    });
                    if (!list.isEmpty()) {
                        return PlayerEntity.SleepResult.NOT_SAFE;
                    }
                }
                player.startSleeping(pos);
                ObfuscationReflectionHelper.setPrivateValue(PlayerEntity.class, (PlayerEntity) player, 0, "field_71076_b");
                player.awardStat(Stats.SLEEP_IN_BED);
                CriteriaTriggers.SLEPT_IN_BED.trigger(player);
                ((ServerWorld)worldIn).updateSleepingPlayerList();
                return PlayerEntity.SleepResult.OTHER_PROBLEM;
            }
        }


    }
}
