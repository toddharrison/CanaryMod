package net.minecraft.tileentity;

import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.nbt.CompoundTag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.Callable;

public abstract class TileEntity {

    private static final Logger a = LogManager.getLogger();
    private static Map f = Maps.newHashMap();
    private static Map g = Maps.newHashMap();
    protected World b;
    protected BlockPos c;
    protected boolean d;
    private int h;
    protected Block e;

    // CanaryMod: Variable Declaration
    public net.canarymod.api.world.blocks.TileEntity complexBlock;
    private CanaryCompoundTag meta = new CanaryCompoundTag(); // hold it for extra data

    // CanaryMod: End

    public TileEntity() {
        this.c = BlockPos.a;
        this.h = -1;
    }

    private static void a(Class oclass0, String s0) {
        if (f.containsKey(s0)) {
            throw new IllegalArgumentException("Duplicate id: " + s0);
        }
        else {
            f.put(s0, oclass0);
            g.put(oclass0, s0);
        }
    }

    public World z() {
        return this.b;
    }

    public void a(World world) {
        this.b = world;
    }

    public boolean t() {
        return this.b != null;
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.c = new BlockPos(nbttagcompound.f("x"), nbttagcompound.f("y"), nbttagcompound.f("z"));
        if (nbttagcompound.c("Canary")) {
            this.meta = new CanaryCompoundTag(nbttagcompound.m("Canary"));
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        String s0 = (String)g.get(this.getClass());

        if (s0 == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        else {
            nbttagcompound.a("id", s0);
            nbttagcompound.a("x", this.c.n());
            nbttagcompound.a("y", this.c.o());
            nbttagcompound.a("z", this.c.p());
            if (meta != null) {
                nbttagcompound.a("Canary", meta.getHandle());
            }
        }
    }

    public static TileEntity c(NBTTagCompound nbttagcompound) {
        TileEntity tileentity = null;

        try {
            Class oclass0 = (Class)f.get(nbttagcompound.j("id"));

            if (oclass0 != null) {
                tileentity = (TileEntity)oclass0.newInstance();
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

    public int u() {
        if (this.h == -1) {
            IBlockState iblockstate = this.b.p(this.c);

            this.h = iblockstate.c().c(iblockstate);
        }

        return this.h;
    }

    public void o_() {
        if (this.b != null) {
            IBlockState iblockstate = this.b.p(this.c);

            this.h = iblockstate.c().c(iblockstate);
            this.b.b(this.c, this);
            if (this.w() != Blocks.a) {
                this.b.e(this.c, this.w());
            }
        }
    }

    public BlockPos v() {
        return this.c;
    }

    public Block w() {
        if (this.e == null) {
            this.e = this.b.p(this.c).c();
        }

        return this.e;
    }

    public Packet x_() {
        return null;
    }

    public boolean x() {
        return this.d;
    }

    public void y() {
        this.d = true;
    }

    public void D() {
        this.d = false;
    }

    public boolean c(int i0, int i1) {
        return false;
    }

    public void E() {
        this.e = null;
        this.h = -1;
    }

    public void a(CrashReportCategory crashreportcategory) {
        crashreportcategory.a("Name", new Callable() {

                                  public String call() {
                                      return (String)TileEntity.g.get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
                                  }
                              }
                             );
        if (this.b != null) {
            CrashReportCategory.a(crashreportcategory, this.c, this.w(), this.u());
            crashreportcategory.a("Actual block type", new Callable() {

                                      public String call() {
                                          int iblockstate = Block.a(TileEntity.this.b.p(TileEntity.this.c).c());

                                          try {
                                              return String.format("ID #%d (%s // %s)", new Object[]{ Integer.valueOf(i1), Block.e(i1).a(), Block.e(i1).getClass().getCanonicalName() });
                                          }
                                          catch (Throwable s0) {
                                              return "ID #" + i1;
                                          }
                                      }
                                  }
                                 );
            crashreportcategory.a("Actual block data value", new Callable() {

                                      public String call() {
                                          IBlockState iblockstate = TileEntity.this.b.p(TileEntity.this.c);
                                          int i1 = iblockstate.c().c(iblockstate);

                                          if (i1 < 0) {
                                              return "Unknown? (Got " + i1 + ")";
                                          }
                                          else {
                                              String s0 = String.format("%4s", new Object[]{ Integer.toBinaryString(i1) }).replace(" ", "0");

                                              return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[]{ Integer.valueOf(i1), s0 });
                                          }
                                      }
                                  }
                                 );
        }
    }

    public void a(BlockPos blockpos) {
        this.c = blockpos;
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
        a(TileEntityBanner.class, "Banner");
    }
}
