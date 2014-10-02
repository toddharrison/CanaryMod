package net.minecraft.entity.item;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.CanaryFallingBlock;
import net.canarymod.hook.entity.DamageHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;

public class EntityFallingBlock extends Entity {

    private IBlockState d;
    public int a;
    public boolean b;
    public boolean e; // CanaryMod: private => public; isBreakingAnvil?
    public boolean f; // CanaryMod: private => public; hurtEntities
    public int g = 40; // CanaryMod: private -> public
    public float h = 2.0F; // CanaryMod: private -> public
    public NBTTagCompound c;
   
    public EntityFallingBlock(World world) {
        super(world);
        this.entity = new CanaryFallingBlock(this); // CanaryMod: Wrap Entity
    }

    public EntityFallingBlock(World world, double d0, double d1, double d2, Block block) {
        this(world, d0, d1, d2, block, 0);
    }

    public EntityFallingBlock(World world, double d0, double d1, double d2, Block block, int i0) {
        super(world);
        this.d = iblockstate;
        this.k = true;
        this.a(0.98F, 0.98F);
        this.b(d0, d1, d2);
        this.v = 0.0D;
        this.w = 0.0D;
        this.x = 0.0D;
        this.p = d0;
        this.q = d1;
        this.r = d2;
        this.entity = new CanaryFallingBlock(this); // CanaryMod: Wrap Entity
    }

    protected boolean r_() {
        return false;
    }

    protected void h() {}

    public boolean ad() {
        return !this.I;
    }

    public void s_() {
        Block block = this.d.c();

        if (block.r() == Material.a) {
            this.J();
        } else {
            this.p = this.s;
            this.q = this.t;
            this.r = this.u;
            BlockPos blockpos;

            if (this.a++ == 0) {
                blockpos = new BlockPos(this);
                if (this.o.p(blockpos).c() == block) {
                    this.o.g(blockpos);
                } else if (!this.o.D) {
                    this.J();
                    return;
                }
            }

            this.w -= 0.03999999910593033D;
            this.d(this.v, this.w, this.x);
            this.v *= 0.9800000190734863D;
            this.w *= 0.9800000190734863D;
            this.x *= 0.9800000190734863D;
            if (!this.o.D) {
                blockpos = new BlockPos(this);
                if (this.C) {
                    this.v *= 0.699999988079071D;
                    this.x *= 0.699999988079071D;
                    this.w *= -0.5D;
                    if (this.o.p(blockpos).c() != Blocks.M) {
                        this.J();
                        if (!this.e && this.o.a(block, blockpos, true, EnumFacing.UP, (Entity) null, (ItemStack) null) && !BlockFalling.d(this.o, blockpos.b()) && this.o.a(blockpos, this.d, 3)) {
                            if (block instanceof BlockFalling) {
                                ((BlockFalling) block).a_(this.o, blockpos);
                            }

                            if (this.c != null && block instanceof ITileEntityProvider) {
                                TileEntity tileentity = this.o.s(blockpos);

                                if (tileentity != null) {
                                    NBTTagCompound nbttagcompound = new NBTTagCompound();

                                    tileentity.b(nbttagcompound);
                                    Iterator iterator = this.c.c().iterator();

                                    while (iterator.hasNext()) {
                                        String s0 = (String) iterator.next();
                                        NBTBase nbtbase = this.c.a(s0);

                                        if (!s0.equals("x") && !s0.equals("y") && !s0.equals("z")) {
                                            nbttagcompound.a(s0, nbtbase.b());
                                        }
                                    }

                                    tileentity.a(nbttagcompound);
                                    tileentity.o_();
                                }
                            }
                        } else if (this.b && !this.e && this.o.Q().b("doTileDrops")) {
                            this.a(new ItemStack(block, 1, block.a(this.d)), 0.0F);
                        }
                    }
                } else if (this.a > 100 && !this.o.D && (blockpos.o() < 1 || blockpos.o() > 256) || this.a > 600) {
                    if (this.b && this.o.Q().b("doTileDrops")) {
                        this.a(new ItemStack(block, 1, block.a(this.d)), 0.0F);
                    }

                    this.J();
                }
            }

        }
    }

    public void e(float f0, float f1) {
        Block block = this.d.c();

        if (this.f) {
            int i0 = MathHelper.f(f0 - 1.0F);

            if (i0 > 0) {
                ArrayList arraylist = Lists.newArrayList(this.o.b((Entity) this, this.aQ()));
                boolean flag0 = block == Blocks.cf;
                DamageSource damagesource = flag0 ? DamageSource.n : DamageSource.o;
                Iterator iterator = arraylist.iterator();

                while (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();
                    // CanaryMod: DamageHook (FallingBlock/Anvil)
                    DamageHook hook = (DamageHook) new DamageHook(null, entity.getCanaryEntity(), new CanaryDamageSource(damagesource), Math.min(MathHelper.d((float) i0 * this.i), this.h)).call();
                    if (!hook.isCanceled()) {
                        entity.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), Math.min(hook.getDamageDealt(), this.h));
                    }
                    //
                }

                if (flag0 && (double) this.V.nextFloat() < 0.05000000074505806D + (double) i0 * 0.05D) {
                    int i1 = ((Integer) this.d.b(BlockAnvil.b)).intValue();

                    ++i1;
                    if (i1 > 2) {
                        this.e = true;
                    } else {
                        this.d = this.d.a(BlockAnvil.b, Integer.valueOf(i1));
                    }
                }
            }
        }

    }

    protected void b(NBTTagCompound nbttagcompound) {
        Block block = this.d != null ? this.d.c() : Blocks.a;
        ResourceLocation resourcelocation = (ResourceLocation) Block.c.c(block);

        nbttagcompound.a("Block", resourcelocation == null ? "" : resourcelocation.toString());
        nbttagcompound.a("Data", (byte) block.c(this.d));
        nbttagcompound.a("Time", (byte) this.a);
        nbttagcompound.a("DropItem", this.b);
        nbttagcompound.a("HurtEntities", this.f);
        nbttagcompound.a("FallHurtAmount", this.h);
        nbttagcompound.a("FallHurtMax", this.g);
        if (this.c != null) {
            nbttagcompound.a("TileEntityData", (NBTBase) this.c);
        }

    }

    protected void a(NBTTagCompound nbttagcompound) {
        int i0 = nbttagcompound.d("Data") & 255;

        if (nbttagcompound.b("Block", 8)) {
            this.d = Block.b(nbttagcompound.j("Block")).a(i0);
        } else if (nbttagcompound.b("TileID", 99)) {
            this.d = Block.c(nbttagcompound.f("TileID")).a(i0);
        } else {
            this.d = Block.c(nbttagcompound.d("Tile") & 255).a(i0);
        }

        this.a = nbttagcompound.d("Time") & 255;
        Block block = this.d.c();

        if (nbttagcompound.b("HurtEntities", 99)) {
            this.f = nbttagcompound.n("HurtEntities");
            this.h = nbttagcompound.h("FallHurtAmount");
            this.g = nbttagcompound.f("FallHurtMax");
        } else if (block == Blocks.cf) {
            this.f = true;
        }

        if (nbttagcompound.b("DropItem", 99)) {
            this.b = nbttagcompound.n("DropItem");
        }

        if (nbttagcompound.b("TileEntityData", 10)) {
            this.c = nbttagcompound.m("TileEntityData");
        }

        if (block == null || block.r() == Material.a) {
            this.d = Blocks.m.P();
        }

    }

    public void a(boolean flag0) {
        this.f = flag0;
    }

    public void a(CrashReportCategory crashreportcategory) {
        super.a(crashreportcategory);
        if (this.d != null) {
            Block block = this.d.c();

            crashreportcategory.a("Immitating block ID", (Object) Integer.valueOf(Block.a(block)));
            crashreportcategory.a("Immitating block data", (Object) Integer.valueOf(block.c(this.d)));
        }

    }

    public IBlockState l() {
        return this.d;
    }

    // CanaryMod
    public void setBlock(Block block) {
        this.e = block;
    }
}
