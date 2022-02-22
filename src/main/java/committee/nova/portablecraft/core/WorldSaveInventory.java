package committee.nova.portablecraft.core;

import committee.nova.portablecraft.common.inventorys.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/20 7:33
 * Version: 1.0
 */
public class WorldSaveInventory extends WorldSavedData {

    private static final String NAME = "PortableCraftSavedData";
    private static final String TAG_FURNACE = "TAG_FURNACE";
    public static List<IInventory> inventoryFurnaces = new ArrayList<>();
    public static List<IInventory> inventorySmokers = new ArrayList<>();
    public static List<IInventory> inventoryBlastFurnaces = new ArrayList<>();
    public static List<IInventory> inventoryBrewingStands = new ArrayList<>();
    public static List<IInventory> inventoryChests = new ArrayList<>();
    public static List<IInventory> inventoryEnchants = new ArrayList<>();

    public static List<PlayerSpawnData> sleepingPlayer = new ArrayList<>();

    private static int listnrFurnace = 0;
    private static int listnrSmoker = 0;
    private static int listnrBlastFurnace = 0;
    private static int listnrBrewingStand = 0;
    private static int listnrChest = 0;
    private static int listnrEnchant = 0;


    private static WorldSaveInventory INSTANCE;


    public WorldSaveInventory() {
            super(NAME);
    }

    public WorldSaveInventory(CompoundNBT nbt) {
        super(NAME);
        this.load(nbt);
    }

    public static void resetInstance() {
        inventoryFurnaces = new ArrayList<>();
        inventoryBrewingStands = new ArrayList<>();
        inventorySmokers = new ArrayList<>();
        inventoryBlastFurnaces = new ArrayList<>();
        inventoryChests = new ArrayList<>();
        inventoryEnchants = new ArrayList<>();

        listnrFurnace = 0;
        listnrSmoker = 0;
        listnrBrewingStand = 0;
        listnrBlastFurnace = 0;
        listnrChest = 0;
        listnrEnchant = 0;

        INSTANCE = null;
    }

    @OnlyIn(Dist.CLIENT)
    public static void setInstance() {
        INSTANCE = new WorldSaveInventory();
    }

    public static WorldSaveInventory getInstance() {
        return INSTANCE;
    }

    @OnlyIn(Dist.CLIENT)
    public static void setDataFurnace(CompoundNBT nbt) {
        FurnaceInventory furnace = new FurnaceInventory();
        furnace.loadClient(nbt);
        if (getInstance().getInventoryFurnace(furnace.getInventoryNr()) == null) {
            inventoryFurnaces.add(furnace);
        } else {
            getInstance().getInventoryFurnace(furnace.getInventoryNr()).loadClient(nbt);
        }

    }

    public static void setINSTANCE(ServerWorld world) {
        DimensionSavedDataManager manager = world.getDataStorage();
        INSTANCE = manager.computeIfAbsent(WorldSaveInventory::new, NAME);
    }

    @Override
    public void load(CompoundNBT nbt) {
        ListNBT listFurnace = nbt.getList("TAG_FURNACE", 10);

        for(int i = 0; i < listFurnace.size(); ++i) {
            inventoryFurnaces.add(new FurnaceInventory(listFurnace.getCompound(i)));
        }

        int j;
        if (inventoryFurnaces.size() != 0) {
            for (IInventory inventoryFurnace : inventoryFurnaces) {
                FurnaceInventory furnace = (FurnaceInventory) inventoryFurnace;
                j = furnace.getInventoryNr();
                if (listnrFurnace == 0) {
                    listnrFurnace = j;
                } else if (listnrFurnace < j) {
                    listnrFurnace = j;
                }
            }
        }

        ListNBT listSmoker = nbt.getList("TAG_SMOKER", 10);

        for(int i = 0; i < listSmoker.size(); ++i) {
            inventorySmokers.add(new SmokerInventory(listSmoker.getCompound(i)));
        }

        int smoker;
        if (inventorySmokers.size() != 0) {
            for (IInventory inventorySmoker : inventorySmokers) {
                SmokerInventory furnace = (SmokerInventory) inventorySmoker;
                smoker = furnace.getInventoryNr();
                if (listnrSmoker == 0) {
                    listnrSmoker = smoker;
                } else if (listnrSmoker < smoker) {
                    listnrSmoker = smoker;
                }
            }
        }


        ListNBT listBlastFurnace = nbt.getList("TAG_BlAST_FURNACE", 10);

        for(int i = 0; i < listBlastFurnace.size(); ++i) {
            inventoryBlastFurnaces.add(new BlastFurnaceInventory(listBlastFurnace.getCompound(i)));
        }

        int BlastFurnace;
        if (inventoryBlastFurnaces.size() != 0) {
            for (IInventory inventoryBlastFurnace : inventoryBlastFurnaces) {
                BlastFurnaceInventory furnace = (BlastFurnaceInventory) inventoryBlastFurnace;
                BlastFurnace = furnace.getInventoryNr();
                if (listnrBlastFurnace == 0) {
                    listnrBlastFurnace = BlastFurnace;
                } else if (listnrBlastFurnace < BlastFurnace) {
                    listnrBlastFurnace = BlastFurnace;
                }
            }
        }

        ListNBT listBrewingStand = nbt.getList("TAG_BREWING_STAND", 10);

        for(int i = 0; i < listBrewingStand.size(); ++i) {
            inventoryBrewingStands.add(new BrewingStandInventory(listBrewingStand.getCompound(i)));
        }

        int BrewingStand;
        if (inventoryBrewingStands.size() != 0) {
            for (IInventory inventoryBrewingStand : inventoryBrewingStands) {
                BrewingStandInventory furnace = (BrewingStandInventory) inventoryBrewingStand;
                BrewingStand = furnace.getInventoryNr();
                if (listnrBrewingStand == 0) {
                    listnrBrewingStand = BrewingStand;
                } else if (listnrBrewingStand < BrewingStand) {
                    listnrBrewingStand = BrewingStand;
                }
            }
        }


        ListNBT listChest = nbt.getList("TAG_CHEST", 10);

        for(int i = 0; i < listChest.size(); ++i) {
            inventoryChests.add(new ChestInventory(listChest.getCompound(i)));
        }

        int Chest;
        if (inventoryChests.size() != 0) {
            for (IInventory inventoryChest : inventoryChests) {
                ChestInventory furnace = (ChestInventory) inventoryChest;
                Chest = furnace.getInventoryNr();
                if (listnrChest == 0) {
                    listnrChest = Chest;
                } else if (listnrChest < Chest) {
                    listnrChest = Chest;
                }
            }
        }

        ListNBT listEnchant = nbt.getList("TAG_ENCHANT", 10);

        for(int i = 0; i < listEnchant.size(); ++i) {
            inventoryEnchants.add(new EnchantmentInventory(listEnchant.getCompound(i)));
        }

        int Enchant;
        if (inventoryEnchants.size() != 0) {
            for (IInventory inventoryEnchant : inventoryEnchants) {
                EnchantmentInventory furnace = (EnchantmentInventory) inventoryEnchant;
                Enchant = furnace.getInventoryNr();
                if (listnrEnchant == 0) {
                    listnrEnchant = Enchant;
                } else if (listnrEnchant < Enchant) {
                    listnrEnchant = Enchant;
                }
            }
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        ListNBT listFurnace = new ListNBT();

        for (IInventory inventoryFurnace : inventoryFurnaces) {
            FurnaceInventory furnace = (FurnaceInventory) inventoryFurnace;
            CompoundNBT nbt2 = new CompoundNBT();
            furnace.save(nbt2);
            listFurnace.add(nbt2);
        }

        nbt.put("TAG_FURNACE", listFurnace);


        ListNBT listSmoker = new ListNBT();
        for (IInventory inventorySmoker : inventorySmokers) {
            SmokerInventory smoker = (SmokerInventory) inventorySmoker;
            CompoundNBT nbt2 = new CompoundNBT();
            smoker.save(nbt2);
            listSmoker.add(nbt2);
        }

        nbt.put("TAG_SMOKER", listSmoker);

        ListNBT listBlastFurnace = new ListNBT();
        for (IInventory inventoryBlastFurnace : inventoryBlastFurnaces) {
            BlastFurnaceInventory smoker = (BlastFurnaceInventory) inventoryBlastFurnace;
            CompoundNBT nbt2 = new CompoundNBT();
            smoker.save(nbt2);
            listBlastFurnace.add(nbt2);
        }

        nbt.put("TAG_BlAST_FURNACE", listBlastFurnace);


        ListNBT listBrewingStand = new ListNBT();
        for (IInventory inventoryBrewingStand : inventoryBrewingStands) {
            BrewingStandInventory BrewingStand = (BrewingStandInventory) inventoryBrewingStand;
            CompoundNBT nbt2 = new CompoundNBT();
            BrewingStand.save(nbt2);
            listBrewingStand.add(nbt2);
        }

        nbt.put("TAG_BREWING_STAND", listBrewingStand);

        ListNBT listChest = new ListNBT();
        for (IInventory inventoryChest : inventoryChests) {
            ChestInventory Chest = (ChestInventory) inventoryChest;
            CompoundNBT nbt2 = new CompoundNBT();
            Chest.save(nbt2);
            listChest.add(nbt2);
        }

        nbt.put("TAG_CHEST", listChest);

        ListNBT listEnchant = new ListNBT();
        for (IInventory inventoryEnchant : inventoryEnchants) {
            EnchantmentInventory Enchant = (EnchantmentInventory) inventoryEnchant;
            CompoundNBT nbt2 = new CompoundNBT();
            Enchant.save(nbt2);
            listEnchant.add(nbt2);
        }

        nbt.put("TAG_ENCHANT", listEnchant);


        return nbt;
    }

    public int addandCreateInvFurnace() {
        ++listnrFurnace;
        FurnaceInventory furnace = new FurnaceInventory();
        furnace.setInventoryNr(listnrFurnace);
        inventoryFurnaces.add(furnace);
        this.setDirty();
        return listnrFurnace;
    }

    public int addandCreateInvSmoker() {
        ++listnrSmoker;
        SmokerInventory furnace = new SmokerInventory();
        furnace.setInventoryNr(listnrSmoker);
        inventorySmokers.add(furnace);
        this.setDirty();
        return listnrSmoker;
    }

    public int addandCreateInvBlastFurnace() {
        ++listnrBlastFurnace;
        BlastFurnaceInventory furnace = new BlastFurnaceInventory();
        furnace.setInventoryNr(listnrBlastFurnace);
        inventoryBlastFurnaces.add(furnace);
        this.setDirty();
        return listnrBlastFurnace;
    }

    public int addandCreateBrewingStand() {
        ++listnrBrewingStand;
        BrewingStandInventory furnace = new BrewingStandInventory();
        furnace.setInventoryNr(listnrBrewingStand);
        inventoryBrewingStands.add(furnace);
        this.setDirty();
        return listnrBrewingStand;
    }

    public int addandCreateInvChest() {
        ++listnrChest;
        ChestInventory furnace = new ChestInventory();
        furnace.setInventoryNr(listnrChest);
        inventoryChests.add(furnace);
        this.setDirty();
        return listnrChest;
    }

    public int addandCreateInvEnchant() {
        ++listnrEnchant;
        EnchantmentInventory furnace = new EnchantmentInventory();
        furnace.setInventoryNr(listnrEnchant);
        inventoryEnchants.add(furnace);
        this.setDirty();
        return listnrEnchant;
    }

    public FurnaceInventory getInventoryFurnace(int inv) {
        Iterator<IInventory> var2 = inventoryFurnaces.iterator();

        FurnaceInventory furnace;
        do {
            if (!var2.hasNext()) {
                FurnaceInventory furnace1 = new FurnaceInventory();
                furnace1.setInventoryNr(inv);
                inventoryFurnaces.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (FurnaceInventory)var2.next();
        } while(furnace.getInventoryNr() != inv);

        return furnace;
    }

    public SmokerInventory getInventorySmoker(int inv) {
        Iterator<IInventory> var2 = inventorySmokers.iterator();

        SmokerInventory furnace;
        do {
            if (!var2.hasNext()) {
                SmokerInventory furnace1 = new SmokerInventory();
                furnace1.setInventoryNr(inv);
                inventorySmokers.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (SmokerInventory)var2.next();
        } while(furnace.getInventoryNr() != inv);

        return furnace;
    }

    public BlastFurnaceInventory getInventoryBlastFurnace(int inv) {
        Iterator<IInventory> var2 = inventoryBlastFurnaces.iterator();

        BlastFurnaceInventory furnace;
        do {
            if (!var2.hasNext()) {
                BlastFurnaceInventory furnace1 = new BlastFurnaceInventory();
                furnace1.setInventoryNr(inv);
                inventoryBlastFurnaces.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (BlastFurnaceInventory)var2.next();
        } while(furnace.getInventoryNr() != inv);

        return furnace;
    }

    public BrewingStandInventory getInventoryBrewingStand(int inv) {
        Iterator<IInventory> var2 = inventoryBrewingStands.iterator();

        BrewingStandInventory furnace;
        do {
            if (!var2.hasNext()) {
                BrewingStandInventory furnace1 = new BrewingStandInventory();
                furnace1.setInventoryNr(inv);
                inventoryBrewingStands.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (BrewingStandInventory)var2.next();
        } while(furnace.getInventoryNr() != inv);

        return furnace;
    }

    public ChestInventory getInventoryChest(int inv) {
        Iterator<IInventory> var2 = inventoryChests.iterator();

        ChestInventory furnace;
        do {
            if (!var2.hasNext()) {
                ChestInventory furnace1 = new ChestInventory();
                furnace1.setInventoryNr(inv);
                inventoryChests.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (ChestInventory)var2.next();
        } while(furnace.getInventoryNr() != inv);

        return furnace;
    }

    public EnchantmentInventory getInventoryEnchant(int inv) {
        Iterator<IInventory> var2 = inventoryEnchants.iterator();

        EnchantmentInventory furnace;
        do {
            if (!var2.hasNext()) {
                EnchantmentInventory furnace1 = new EnchantmentInventory();
                furnace1.setInventoryNr(inv);
                inventoryEnchants.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (EnchantmentInventory)var2.next();
        } while(furnace.getInventoryNr() != inv);

        return furnace;
    }

    public boolean isSleepingPlayer() {
        return !sleepingPlayer.isEmpty();
    }

    public void setSleepingPlayer(PlayerSpawnData splayer) {
        sleepingPlayer.add(splayer);
        this.setDirty();
    }

    public List<PlayerSpawnData> returnSleepingList() {
        return sleepingPlayer;
    }

    public void removeSleepingPlayer(PlayerEntity player) {
        for(int a = 0; a < sleepingPlayer.size(); ++a) {
            if (sleepingPlayer.get(a).getPlayer().getUUID() == player.getUUID()) {
                sleepingPlayer.remove(a);
                this.setDirty();
                return;
            }
        }

    }

}
