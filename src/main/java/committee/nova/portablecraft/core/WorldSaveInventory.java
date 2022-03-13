package committee.nova.portablecraft.core;

import committee.nova.portablecraft.common.containers.*;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/6 19:49
 * Version: 1.0
 */
public class WorldSaveInventory extends PersistentState {

    private static final String NAME = "PortableCraftSavedData";
    public static List<Inventory> inventoryFurnaces = new ArrayList<>();
    public static List<Inventory> inventorySmokers = new ArrayList<>();
    public static List<Inventory> inventoryBlastFurnaces = new ArrayList<>();
    public static List<Inventory> inventoryBrewingStands = new ArrayList<>();
    public static List<Inventory> inventoryChests = new ArrayList<>();
    public static List<Inventory> inventoryEnchants = new ArrayList<>();
    public static List<Inventory> inventoryCrafts = new ArrayList<>();


    private static int listnrFurnace = 0;
    private static int listnrSmoker = 0;
    private static int listnrBlastFurnace = 0;
    private static int listnrBrewingStand = 0;
    private static int listnrChest = 0;
    private static int listnrEnchant = 0;
    private static int listnrCraft = 0;

    private static WorldSaveInventory INSTANCE;


    public static void resetInstance() {
        inventoryFurnaces = new ArrayList<>();
        inventoryBrewingStands = new ArrayList<>();
        inventorySmokers = new ArrayList<>();
        inventoryBlastFurnaces = new ArrayList<>();
        inventoryChests = new ArrayList<>();
        inventoryEnchants = new ArrayList<>();
        inventoryCrafts = new ArrayList<>();


        listnrFurnace = 0;
        listnrSmoker = 0;
        listnrBrewingStand = 0;
        listnrBlastFurnace = 0;
        listnrChest = 0;
        listnrEnchant = 0;
        listnrCraft = 0;

        INSTANCE = null;
    }

    public static WorldSaveInventory getInstance() {
        return INSTANCE;
    }

    public static void setINSTANCE(ServerWorld world) {
        if (world == null) return;
        PersistentStateManager manager = world.getPersistentStateManager();
        INSTANCE = manager.getOrCreate(WorldSaveInventory::load, WorldSaveInventory::new, NAME);
    }

    public static WorldSaveInventory load(NbtCompound NbtCompound) {
        WorldSaveInventory worldSaveInventory = new WorldSaveInventory();
        worldSaveInventory.read(NbtCompound);
        return worldSaveInventory;
    }

    public void read(NbtCompound nbt) {
        NbtList listFurnace = nbt.getList("TAG_FURNACE", 10);

        for (int i = 0; i < listFurnace.size(); ++i) {
            inventoryFurnaces.add(new FurnaceInventory(listFurnace.getCompound(i)));
        }

        int j;
        if (inventoryFurnaces.size() != 0) {
            for (Inventory inventoryFurnace : inventoryFurnaces) {
                FurnaceInventory furnace = (FurnaceInventory) inventoryFurnace;
                j = furnace.getInventoryNr();
                if (listnrFurnace == 0) {
                    listnrFurnace = j;
                } else if (listnrFurnace < j) {
                    listnrFurnace = j;
                }
            }
        }

        NbtList listSmoker = nbt.getList("TAG_SMOKER", 10);

        for (int i = 0; i < listSmoker.size(); ++i) {
            inventorySmokers.add(new SmokerInventory(listSmoker.getCompound(i)));
        }

        int smoker;
        if (inventorySmokers.size() != 0) {
            for (Inventory inventorySmoker : inventorySmokers) {
                SmokerInventory furnace = (SmokerInventory) inventorySmoker;
                smoker = furnace.getInventoryNr();
                if (listnrSmoker == 0) {
                    listnrSmoker = smoker;
                } else if (listnrSmoker < smoker) {
                    listnrSmoker = smoker;
                }
            }
        }


        NbtList listBlastFurnace = nbt.getList("TAG_BlAST_FURNACE", 10);

        for (int i = 0; i < listBlastFurnace.size(); ++i) {
            inventoryBlastFurnaces.add(new BlastFurnaceInventory(listBlastFurnace.getCompound(i)));
        }

        int BlastFurnace;
        if (inventoryBlastFurnaces.size() != 0) {
            for (Inventory inventoryBlastFurnace : inventoryBlastFurnaces) {
                BlastFurnaceInventory furnace = (BlastFurnaceInventory) inventoryBlastFurnace;
                BlastFurnace = furnace.getInventoryNr();
                if (listnrBlastFurnace == 0) {
                    listnrBlastFurnace = BlastFurnace;
                } else if (listnrBlastFurnace < BlastFurnace) {
                    listnrBlastFurnace = BlastFurnace;
                }
            }
        }

        NbtList listBrewingStand = nbt.getList("TAG_BREWING_STAND", 10);

        for (int i = 0; i < listBrewingStand.size(); ++i) {
            inventoryBrewingStands.add(new BrewingStandInventory(listBrewingStand.getCompound(i)));
        }

        int BrewingStand;
        if (inventoryBrewingStands.size() != 0) {
            for (Inventory inventoryBrewingStand : inventoryBrewingStands) {
                BrewingStandInventory furnace = (BrewingStandInventory) inventoryBrewingStand;
                BrewingStand = furnace.getInventoryNr();
                if (listnrBrewingStand == 0) {
                    listnrBrewingStand = BrewingStand;
                } else if (listnrBrewingStand < BrewingStand) {
                    listnrBrewingStand = BrewingStand;
                }
            }
        }


        NbtList listChest = nbt.getList("TAG_CHEST", 10);

        for (int i = 0; i < listChest.size(); ++i) {
            inventoryChests.add(new ChestInventory(listChest.getCompound(i)));
        }

        int Chest;
        if (inventoryChests.size() != 0) {
            for (Inventory inventoryChest : inventoryChests) {
                ChestInventory furnace = (ChestInventory) inventoryChest;
                Chest = furnace.getInventoryNr();
                if (listnrChest == 0) {
                    listnrChest = Chest;
                } else if (listnrChest < Chest) {
                    listnrChest = Chest;
                }
            }
        }

        NbtList listEnchant = nbt.getList("TAG_ENCHANT", 10);

        for (int i = 0; i < listEnchant.size(); ++i) {
            inventoryEnchants.add(new EnchantmentInventory(listEnchant.getCompound(i)));
        }

        int Enchant;
        if (inventoryEnchants.size() != 0) {
            for (Inventory inventoryEnchant : inventoryEnchants) {
                EnchantmentInventory furnace = (EnchantmentInventory) inventoryEnchant;
                Enchant = furnace.getInventoryNr();
                if (listnrEnchant == 0) {
                    listnrEnchant = Enchant;
                } else if (listnrEnchant < Enchant) {
                    listnrEnchant = Enchant;
                }
            }
        }

        NbtList listCraft = nbt.getList("TAG_CRAFT", 10);

        for (int i = 0; i < listCraft.size(); ++i) {
            inventoryCrafts.add(new CraftingInventory(listCraft.getCompound(i)));
        }

        int Craft;
        if (inventoryCrafts.size() != 0) {
            for (Inventory inventoryCraft : inventoryCrafts) {
                CraftingInventory craft = (CraftingInventory) inventoryCraft;
                Craft = craft.getInventoryNr();
                if (listnrCraft == 0) {
                    listnrCraft = Craft;
                } else if (listnrCraft < Craft) {
                    listnrCraft = Craft;
                }
            }
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList listFurnace = new NbtList();

        for (Inventory inventoryFurnace : inventoryFurnaces) {
            FurnaceInventory furnace = (FurnaceInventory) inventoryFurnace;
            NbtCompound nbt2 = new NbtCompound();
            furnace.save(nbt2);
            listFurnace.add(nbt2);
        }

        nbt.put("TAG_FURNACE", listFurnace);


        NbtList listSmoker = new NbtList();
        for (Inventory inventorySmoker : inventorySmokers) {
            SmokerInventory smoker = (SmokerInventory) inventorySmoker;
            NbtCompound nbt2 = new NbtCompound();
            smoker.save(nbt2);
            listSmoker.add(nbt2);
        }

        nbt.put("TAG_SMOKER", listSmoker);

        NbtList listBlastFurnace = new NbtList();
        for (Inventory inventoryBlastFurnace : inventoryBlastFurnaces) {
            BlastFurnaceInventory smoker = (BlastFurnaceInventory) inventoryBlastFurnace;
            NbtCompound nbt2 = new NbtCompound();
            smoker.save(nbt2);
            listBlastFurnace.add(nbt2);
        }

        nbt.put("TAG_BlAST_FURNACE", listBlastFurnace);


        NbtList listBrewingStand = new NbtList();
        for (Inventory inventoryBrewingStand : inventoryBrewingStands) {
            BrewingStandInventory BrewingStand = (BrewingStandInventory) inventoryBrewingStand;
            NbtCompound nbt2 = new NbtCompound();
            BrewingStand.save(nbt2);
            listBrewingStand.add(nbt2);
        }

        nbt.put("TAG_BREWING_STAND", listBrewingStand);

        NbtList listChest = new NbtList();
        for (Inventory inventoryChest : inventoryChests) {
            ChestInventory Chest = (ChestInventory) inventoryChest;
            NbtCompound nbt2 = new NbtCompound();
            Chest.save(nbt2);
            listChest.add(nbt2);
        }

        nbt.put("TAG_CHEST", listChest);

        NbtList listEnchant = new NbtList();
        for (Inventory inventoryEnchant : inventoryEnchants) {
            EnchantmentInventory Enchant = (EnchantmentInventory) inventoryEnchant;
            NbtCompound nbt2 = new NbtCompound();
            Enchant.save(nbt2);
            listEnchant.add(nbt2);
        }

        nbt.put("TAG_ENCHANT", listEnchant);

        NbtList listCraft = new NbtList();
        for (Inventory inventoryCraft : inventoryCrafts) {
            CraftingInventory Craft = (CraftingInventory) inventoryCraft;
            NbtCompound nbt2 = new NbtCompound();
            Craft.save(nbt2);
            listCraft.add(nbt2);
        }

        nbt.put("TAG_CRAFT", listCraft);

        return nbt;
    }

    public int addandCreateInvCraft() {
        ++listnrCraft;
        CraftingInventory craft = new CraftingInventory();
        craft.setInventoryNr(listnrCraft);
        inventoryCrafts.add(craft);
        this.markDirty();
        return listnrCraft;
    }

    public int addandCreateInvFurnace() {
        ++listnrFurnace;
        FurnaceInventory furnace = new FurnaceInventory();
        furnace.setInventoryNr(listnrFurnace);
        inventoryFurnaces.add(furnace);
        this.markDirty();
        return listnrFurnace;
    }

    public int addandCreateInvSmoker() {
        ++listnrSmoker;
        SmokerInventory furnace = new SmokerInventory();
        furnace.setInventoryNr(listnrSmoker);
        inventorySmokers.add(furnace);
        this.markDirty();
        return listnrSmoker;
    }

    public int addandCreateInvBlastFurnace() {
        ++listnrBlastFurnace;
        BlastFurnaceInventory furnace = new BlastFurnaceInventory();
        furnace.setInventoryNr(listnrBlastFurnace);
        inventoryBlastFurnaces.add(furnace);
        this.markDirty();
        return listnrBlastFurnace;
    }

    public int addandCreateBrewingStand() {
        ++listnrBrewingStand;
        BrewingStandInventory furnace = new BrewingStandInventory();
        furnace.setInventoryNr(listnrBrewingStand);
        inventoryBrewingStands.add(furnace);
        this.markDirty();
        return listnrBrewingStand;
    }

    public int addandCreateInvChest() {
        ++listnrChest;
        ChestInventory furnace = new ChestInventory();
        furnace.setInventoryNr(listnrChest);
        inventoryChests.add(furnace);
        this.markDirty();
        return listnrChest;
    }

    public int addandCreateInvEnchant() {
        ++listnrEnchant;
        EnchantmentInventory furnace = new EnchantmentInventory();
        furnace.setInventoryNr(listnrEnchant);
        inventoryEnchants.add(furnace);
        this.markDirty();
        return listnrEnchant;
    }

    public CraftingInventory getInventoryCraft(int inv) {
        Iterator<Inventory> var2 = inventoryCrafts.iterator();

        CraftingInventory craft1;
        do {
            if (!var2.hasNext()) {
                CraftingInventory craft = new CraftingInventory();
                craft.setInventoryNr(inv);
                inventoryCrafts.add(craft);
                this.markDirty();
                return craft;
            }

            craft1 = (CraftingInventory) var2.next();
        } while (craft1.getInventoryNr() != inv);

        return craft1;
    }

    public FurnaceInventory getInventoryFurnace(int inv) {
        Iterator<Inventory> var2 = inventoryFurnaces.iterator();

        FurnaceInventory furnace;
        do {
            if (!var2.hasNext()) {
                FurnaceInventory furnace1 = new FurnaceInventory();
                furnace1.setInventoryNr(inv);
                inventoryFurnaces.add(furnace1);
                this.markDirty();
                return furnace1;
            }

            furnace = (FurnaceInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public SmokerInventory getInventorySmoker(int inv) {
        Iterator<Inventory> var2 = inventorySmokers.iterator();

        SmokerInventory furnace;
        do {
            if (!var2.hasNext()) {
                SmokerInventory furnace1 = new SmokerInventory();
                furnace1.setInventoryNr(inv);
                inventorySmokers.add(furnace1);
                this.markDirty();
                return furnace1;
            }

            furnace = (SmokerInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public BlastFurnaceInventory getInventoryBlastFurnace(int inv) {
        Iterator<Inventory> var2 = inventoryBlastFurnaces.iterator();

        BlastFurnaceInventory furnace;
        do {
            if (!var2.hasNext()) {
                BlastFurnaceInventory furnace1 = new BlastFurnaceInventory();
                furnace1.setInventoryNr(inv);
                inventoryBlastFurnaces.add(furnace1);
                this.markDirty();
                return furnace1;
            }

            furnace = (BlastFurnaceInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public BrewingStandInventory getInventoryBrewingStand(int inv) {
        Iterator<Inventory> var2 = inventoryBrewingStands.iterator();

        BrewingStandInventory furnace;
        do {
            if (!var2.hasNext()) {
                BrewingStandInventory furnace1 = new BrewingStandInventory();
                furnace1.setInventoryNr(inv);
                inventoryBrewingStands.add(furnace1);
                this.markDirty();
                return furnace1;
            }

            furnace = (BrewingStandInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public ChestInventory getInventoryChest(int inv) {
        Iterator<Inventory> var2 = inventoryChests.iterator();

        ChestInventory furnace;
        do {
            if (!var2.hasNext()) {
                ChestInventory furnace1 = new ChestInventory();
                furnace1.setInventoryNr(inv);
                inventoryChests.add(furnace1);
                this.markDirty();
                return furnace1;
            }

            furnace = (ChestInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public EnchantmentInventory getInventoryEnchant(int inv) {
        Iterator<Inventory> var2 = inventoryEnchants.iterator();

        EnchantmentInventory furnace;
        do {
            if (!var2.hasNext()) {
                EnchantmentInventory furnace1 = new EnchantmentInventory();
                furnace1.setInventoryNr(inv);
                inventoryEnchants.add(furnace1);
                this.markDirty();
                return furnace1;
            }

            furnace = (EnchantmentInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }
}
