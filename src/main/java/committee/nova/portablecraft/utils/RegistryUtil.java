package committee.nova.portablecraft.utils;

import committee.nova.portablecraft.common.enchants.base.BaseEnchant;
import committee.nova.portablecraft.init.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;
import java.util.function.Supplier;

public class RegistryUtil {

    @SuppressWarnings("unchecked")
    public static <T extends TileEntity> TileEntityType<T> build(Supplier<T> factory, String registryName, Block... block) {
        //noinspection ConstantConditions
        return (TileEntityType<T>) TileEntityType.Builder.of(factory, block).build(null).setRegistryName(registryName);
    }

    @SuppressWarnings("unchecked")
    public static <T extends TileEntity> TileEntityType<T> build(Supplier<T> factory, ResourceLocation registryName, Block... block) {
        //noinspection ConstantConditions
        return (TileEntityType<T>) TileEntityType.Builder.of(factory, block).build(null).setRegistryName(registryName);
    }

    public static Item blockItem(Block block) {
        return blockItem(block, new Item.Properties().tab(ModTabs.tab));
    }

    private static Item blockItem(Block block, Item.Properties properties) {
        return new BlockItem(block, properties).setRegistryName(Objects.requireNonNull(block.getRegistryName()));
    }


    @SuppressWarnings("unchecked")
    public static <T extends Container> ContainerType<T> registerContainer(String name, IContainerFactory<T> containerFactory) {
        return (ContainerType<T>) new ContainerType<>(containerFactory).setRegistryName(name);
    }

    public static void registerEnchant(IForgeRegistry<Enchantment> r, Enchantment e) {
        BaseEnchant ench = (BaseEnchant) e;
        if (ench.isEnabled()) {
            r.register(ench);
        }
    }
}
