package committee.nova.portablecraft.core;

import committee.nova.portablecraft.common.containers.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
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
public class WorldSaveInventory extends SavedData {

    private static final String NAME = "PortableCraftSavedData";
    public static List<Container> inventoryFurnaces = new ArrayList<>();
    public static List<Container> inventorySmokers = new ArrayList<>();
    public static List<Container> inventoryBlastFurnaces = new ArrayList<>();
    public static List<Container> inventoryBrewingStands = new ArrayList<>();
    public static List<Container> inventoryChests = new ArrayList<>();
    public static List<Container> inventoryEnchants = new ArrayList<>();


    private static int listnrFurnace = 0;
    private static int listnrSmoker = 0;
    private static int listnrBlastFurnace = 0;
    private static int listnrBrewingStand = 0;
    private static int listnrChest = 0;
    private static int listnrEnchant = 0;


    private static WorldSaveInventory INSTANCE;


    public static WorldSaveInventory load(CompoundTag compoundTag) {
        WorldSaveInventory.read(compoundTag);
        return new WorldSaveInventory();
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


    public static WorldSaveInventory getInstance() {
        return INSTANCE;
    }

    @OnlyIn(Dist.CLIENT)
    public static void setDataFurnace(CompoundTag nbt) {
        FurnaceInventory furnace = new FurnaceInventory();
        furnace.loadClient(nbt);
        if (getInstance().getInventoryFurnace(furnace.getInventoryNr()) == null) {
            inventoryFurnaces.add(furnace);
        } else {
            getInstance().getInventoryFurnace(furnace.getInventoryNr()).loadClient(nbt);
        }

    }

    public static void setINSTANCE(ServerLevel world) {
        if (world == null) return;
        DimensionDataStorage manager = world.getDataStorage();
        INSTANCE = manager.computeIfAbsent(WorldSaveInventory::load, WorldSaveInventory::new, NAME);
    }

    public static void read(CompoundTag nbt) {
        ListTag listFurnace = nbt.getList("TAG_FURNACE", 10);

        for (int i = 0; i < listFurnace.size(); ++i) {
            inventoryFurnaces.add(new FurnaceInventory(listFurnace.getCompound(i)));
        }

        int j;
        if (inventoryFurnaces.size() != 0) {
            for (Container inventoryFurnace : inventoryFurnaces) {
                FurnaceInventory furnace = (FurnaceInventory) inventoryFurnace;
                j = furnace.getInventoryNr();
                if (listnrFurnace == 0) {
                    listnrFurnace = j;
                } else if (listnrFurnace < j) {
                    listnrFurnace = j;
                }
            }
        }

        ListTag listSmoker = nbt.getList("TAG_SMOKER", 10);

        for (int i = 0; i < listSmoker.size(); ++i) {
            inventorySmokers.add(new SmokerInventory(listSmoker.getCompound(i)));
        }

        int smoker;
        if (inventorySmokers.size() != 0) {
            for (Container inventorySmoker : inventorySmokers) {
                SmokerInventory furnace = (SmokerInventory) inventorySmoker;
                smoker = furnace.getInventoryNr();
                if (listnrSmoker == 0) {
                    listnrSmoker = smoker;
                } else if (listnrSmoker < smoker) {
                    listnrSmoker = smoker;
                }
            }
        }


        ListTag listBlastFurnace = nbt.getList("TAG_BlAST_FURNACE", 10);

        for (int i = 0; i < listBlastFurnace.size(); ++i) {
            inventoryBlastFurnaces.add(new BlastFurnaceInventory(listBlastFurnace.getCompound(i)));
        }

        int BlastFurnace;
        if (inventoryBlastFurnaces.size() != 0) {
            for (Container inventoryBlastFurnace : inventoryBlastFurnaces) {
                BlastFurnaceInventory furnace = (BlastFurnaceInventory) inventoryBlastFurnace;
                BlastFurnace = furnace.getInventoryNr();
                if (listnrBlastFurnace == 0) {
                    listnrBlastFurnace = BlastFurnace;
                } else if (listnrBlastFurnace < BlastFurnace) {
                    listnrBlastFurnace = BlastFurnace;
                }
            }
        }

        ListTag listBrewingStand = nbt.getList("TAG_BREWING_STAND", 10);

        for (int i = 0; i < listBrewingStand.size(); ++i) {
            inventoryBrewingStands.add(new BrewingStandInventory(listBrewingStand.getCompound(i)));
        }

        int BrewingStand;
        if (inventoryBrewingStands.size() != 0) {
            for (Container inventoryBrewingStand : inventoryBrewingStands) {
                BrewingStandInventory furnace = (BrewingStandInventory) inventoryBrewingStand;
                BrewingStand = furnace.getInventoryNr();
                if (listnrBrewingStand == 0) {
                    listnrBrewingStand = BrewingStand;
                } else if (listnrBrewingStand < BrewingStand) {
                    listnrBrewingStand = BrewingStand;
                }
            }
        }


        ListTag listChest = nbt.getList("TAG_CHEST", 10);

        for (int i = 0; i < listChest.size(); ++i) {
            inventoryChests.add(new ChestInventory(listChest.getCompound(i)));
        }

        int Chest;
        if (inventoryChests.size() != 0) {
            for (Container inventoryChest : inventoryChests) {
                ChestInventory furnace = (ChestInventory) inventoryChest;
                Chest = furnace.getInventoryNr();
                if (listnrChest == 0) {
                    listnrChest = Chest;
                } else if (listnrChest < Chest) {
                    listnrChest = Chest;
                }
            }
        }

        ListTag listEnchant = nbt.getList("TAG_ENCHANT", 10);

        for (int i = 0; i < listEnchant.size(); ++i) {
            inventoryEnchants.add(new EnchantmentInventory(listEnchant.getCompound(i)));
        }

        int Enchant;
        if (inventoryEnchants.size() != 0) {
            for (Container inventoryEnchant : inventoryEnchants) {
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
    public CompoundTag save(CompoundTag nbt) {
        ListTag listFurnace = new ListTag();

        for (Container inventoryFurnace : inventoryFurnaces) {
            FurnaceInventory furnace = (FurnaceInventory) inventoryFurnace;
            CompoundTag nbt2 = new CompoundTag();
            furnace.save(nbt2);
            listFurnace.add(nbt2);
        }

        nbt.put("TAG_FURNACE", listFurnace);


        ListTag listSmoker = new ListTag();
        for (Container inventorySmoker : inventorySmokers) {
            SmokerInventory smoker = (SmokerInventory) inventorySmoker;
            CompoundTag nbt2 = new CompoundTag();
            smoker.save(nbt2);
            listSmoker.add(nbt2);
        }

        nbt.put("TAG_SMOKER", listSmoker);

        ListTag listBlastFurnace = new ListTag();
        for (Container inventoryBlastFurnace : inventoryBlastFurnaces) {
            BlastFurnaceInventory smoker = (BlastFurnaceInventory) inventoryBlastFurnace;
            CompoundTag nbt2 = new CompoundTag();
            smoker.save(nbt2);
            listBlastFurnace.add(nbt2);
        }

        nbt.put("TAG_BlAST_FURNACE", listBlastFurnace);


        ListTag listBrewingStand = new ListTag();
        for (Container inventoryBrewingStand : inventoryBrewingStands) {
            BrewingStandInventory BrewingStand = (BrewingStandInventory) inventoryBrewingStand;
            CompoundTag nbt2 = new CompoundTag();
            BrewingStand.save(nbt2);
            listBrewingStand.add(nbt2);
        }

        nbt.put("TAG_BREWING_STAND", listBrewingStand);

        ListTag listChest = new ListTag();
        for (Container inventoryChest : inventoryChests) {
            ChestInventory Chest = (ChestInventory) inventoryChest;
            CompoundTag nbt2 = new CompoundTag();
            Chest.save(nbt2);
            listChest.add(nbt2);
        }

        nbt.put("TAG_CHEST", listChest);

        ListTag listEnchant = new ListTag();
        for (Container inventoryEnchant : inventoryEnchants) {
            EnchantmentInventory Enchant = (EnchantmentInventory) inventoryEnchant;
            CompoundTag nbt2 = new CompoundTag();
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
        Iterator<Container> var2 = inventoryFurnaces.iterator();

        FurnaceInventory furnace;
        do {
            if (!var2.hasNext()) {
                FurnaceInventory furnace1 = new FurnaceInventory();
                furnace1.setInventoryNr(inv);
                inventoryFurnaces.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (FurnaceInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public SmokerInventory getInventorySmoker(int inv) {
        Iterator<Container> var2 = inventorySmokers.iterator();

        SmokerInventory furnace;
        do {
            if (!var2.hasNext()) {
                SmokerInventory furnace1 = new SmokerInventory();
                furnace1.setInventoryNr(inv);
                inventorySmokers.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (SmokerInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public BlastFurnaceInventory getInventoryBlastFurnace(int inv) {
        Iterator<Container> var2 = inventoryBlastFurnaces.iterator();

        BlastFurnaceInventory furnace;
        do {
            if (!var2.hasNext()) {
                BlastFurnaceInventory furnace1 = new BlastFurnaceInventory();
                furnace1.setInventoryNr(inv);
                inventoryBlastFurnaces.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (BlastFurnaceInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public BrewingStandInventory getInventoryBrewingStand(int inv) {
        Iterator<Container> var2 = inventoryBrewingStands.iterator();

        BrewingStandInventory furnace;
        do {
            if (!var2.hasNext()) {
                BrewingStandInventory furnace1 = new BrewingStandInventory();
                furnace1.setInventoryNr(inv);
                inventoryBrewingStands.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (BrewingStandInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public ChestInventory getInventoryChest(int inv) {
        Iterator<Container> var2 = inventoryChests.iterator();

        ChestInventory furnace;
        do {
            if (!var2.hasNext()) {
                ChestInventory furnace1 = new ChestInventory();
                furnace1.setInventoryNr(inv);
                inventoryChests.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (ChestInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }

    public EnchantmentInventory getInventoryEnchant(int inv) {
        Iterator<Container> var2 = inventoryEnchants.iterator();

        EnchantmentInventory furnace;
        do {
            if (!var2.hasNext()) {
                EnchantmentInventory furnace1 = new EnchantmentInventory();
                furnace1.setInventoryNr(inv);
                inventoryEnchants.add(furnace1);
                this.setDirty();
                return furnace1;
            }

            furnace = (EnchantmentInventory) var2.next();
        } while (furnace.getInventoryNr() != inv);

        return furnace;
    }


}
