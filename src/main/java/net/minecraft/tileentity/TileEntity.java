package net.minecraft.tileentity;

import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.nbt.CompoundTag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class TileEntity {

    private static final Logger a = LogManager.getLogger();
    private static Map i = new HashMap();
    private static Map j = new HashMap();
    protected World b;
    public int c;
    public int d;
    public int e;
    protected boolean f;
    public int g = -1;
    public Block h;

    // CanaryMod: Variable Declaration
    public net.canarymod.api.world.blocks.TileEntity complexBlock;
    private CanaryCompoundTag meta = new CanaryCompoundTag("Canary"); // hold it for extra data

    // CanaryMod: End

    public TileEntity() {
    }

    private static void a(Class oclass0, String s0) {
        if (i.containsKey(s0)) {
            throw new IllegalArgumentException("Duplicate id: " + s0);
        }
        else {
            i.put(s0, oclass0);
            j.put(oclass0, s0);
        }
    }

    public World w() {
        return this.b;
    }

    public void a(World world) {
        this.b = world;
    }

    public boolean o() {
        return this.b != null;
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.c = nbttagcompound.f("x");
        this.d = nbttagcompound.f("y");
        this.e = nbttagcompound.f("z");
        if (nbttagcompound.b("Canary")) {
            this.meta = new CanaryCompoundTag(nbttagcompound.l("Canary"));
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        String s0 = (String) j.get(this.getClass());

        if (s0 == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        else {
            nbttagcompound.a("id", s0);
            nbttagcompound.a("x", this.c);
            nbttagcompound.a("y", this.d);
            nbttagcompound.a("z", this.e);
            if (meta != null) {
                nbttagcompound.a("Canary", meta.getHandle());
            }
        }
    }

    public void h() {
    }

    public static TileEntity c(NBTTagCompound nbttagcompound) {
        TileEntity tileentity = null;

        try {
            Class oclass0 = (Class) i.get(nbttagcompound.j("id"));

            if (oclass0 != null) {
                tileentity = (TileEntity) oclass0.newInstance();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        if (tileentity != null) {
            tileentity.a(nbttagcompound);
        }
        else {
            a.warn("Skipping BlockEntity with id " + nbttagcompound.j("id"));
        }

        return tileentity;
    }

    public int p() {
        if (this.g == -1) {
            this.g = this.b.e(this.c, this.d, this.e);
        }

        return this.g;
    }

    public void e() {
        if (this.b != null) {
            this.g = this.b.e(this.c, this.d, this.e);
            this.b.b(this.c, this.d, this.e, this);
            if (this.q() != Blocks.a) {
                this.b.f(this.c, this.d, this.e, this.q());
            }
        }
    }

    public Block q() {
        if (this.h == null) {
            this.h = this.b.a(this.c, this.d, this.e);
        }

        return this.h;
    }

    public Packet m() {
        return null;
    }

    public boolean r() {
        return this.f;
    }

    public void s() {
        this.f = true;
    }

    public void t() {
        this.f = false;
    }

    public boolean c(int i0, int i1) {
        return false;
    }

    public void u() {
        this.h = null;
        this.g = -1;
    }

    public void a(CrashReportCategory i1) {
        i1.a("Name", new Callable() {

            public String call() {
                return (String) TileEntity.j.get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
            }
        });
        CrashReportCategory.a(i1, this.c, this.d, this.e, this.q(), this.p());
        i1.a("Actual block type", new Callable() {

            public String call() {
                int i1 = Block.b(TileEntity.this.b.a(TileEntity.this.c, TileEntity.this.d, TileEntity.this.e));

                try {
                    return String.format("ID #%d (%s // %s)", new Object[]{ Integer.valueOf(i1), Block.e(i1).a(), Block.e(i1).getClass().getCanonicalName() });
                }
                catch (Throwable throwable) {
                    return "ID #" + i1;
                }
            }
        });
        i1.a("Actual block data value", new Callable() {

            public String call() {
                int i1 = TileEntity.this.b.e(TileEntity.this.c, TileEntity.this.d, TileEntity.this.e);

                if (i1 < 0) {
                    return "Unknown? (Got " + i1 + ")";
                }
                else {
                    String s0 = String.format("%4s", new Object[]{ Integer.toBinaryString(i1) }).replace(" ", "0");

                    return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[]{ Integer.valueOf(i1), s0 });
                }
            }
        });
    }

    // CanaryMod:
    public CompoundTag getMetaTag() {
        return meta;
    }
    //

    static {
        a(TileEntityFurnace.class, "Furnace");
        a(TileEntityChest.class, "Chest");
        a(TileEntityEnderChest.class, "EnderChest");
        a(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
        a(TileEntityDispenser.class, "Trap");
        a(TileEntityDropper.class, "Dropper");
        a(TileEntitySign.class, "Sign");
        a(TileEntityMobSpawner.class, "MobSpawner");
        a(TileEntityNote.class, "Music");
        a(TileEntityPiston.class, "Piston");
        a(TileEntityBrewingStand.class, "Cauldron");
        a(TileEntityEnchantmentTable.class, "EnchantTable");
        a(TileEntityEndPortal.class, "Airportal");
        a(TileEntityCommandBlock.class, "Control");
        a(TileEntityBeacon.class, "Beacon");
        a(TileEntitySkull.class, "Skull");
        a(TileEntityDaylightDetector.class, "DLDetector");
        a(TileEntityHopper.class, "Hopper");
        a(TileEntityComparator.class, "Comparator");
        a(TileEntityFlowerPot.class, "FlowerPot");
    }
}
