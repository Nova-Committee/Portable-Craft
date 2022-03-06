package committee.nova.portablecraft.utils;

import committee.nova.portablecraft.common.enchants.base.BaseEnchant;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;

public class RegistryUtil {

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> BlockEntityType<T> build(BlockEntityType.BlockEntitySupplier<T> factory, String registryName, Block... block) {
        //noinspection ConstantConditions
        return (BlockEntityType<T>) BlockEntityType.Builder.of(factory, block).build(null).setRegistryName(registryName);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> BlockEntityType<T> build(BlockEntityType.BlockEntitySupplier<T> factory, ResourceLocation registryName, Block... block) {
        //noinspection ConstantConditions
        return (BlockEntityType<T>) BlockEntityType.Builder.of(factory, block).build(null).setRegistryName(registryName);
    }

    public static Item blockItem(Block block) {
        return blockItem(block, new Item.Properties().tab(ModTabs.tab));
    }

    private static Item blockItem(Block block, Item.Properties properties) {
        return new BlockItem(block, properties).setRegistryName(Objects.requireNonNull(block.getRegistryName()));
    }


    @SuppressWarnings("unchecked")
    public static <T extends AbstractContainerMenu> MenuType<T> registerContainer(String name, IContainerFactory<T> containerFactory) {
        return (MenuType<T>) new MenuType<>(containerFactory).setRegistryName(name);
    }

    public static void registerEnchant(IForgeRegistry<Enchantment> r, Enchantment e) {
        BaseEnchant ench = (BaseEnchant) e;
        if (ench.isEnabled()) {
            r.register(ench);
        }
    }
}
