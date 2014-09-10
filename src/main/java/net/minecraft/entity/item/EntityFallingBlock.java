package net.minecraft.entity.item;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.CanaryFallingBlock;
import net.canarymod.hook.entity.DamageHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;


public class EntityFallingBlock extends Entity {

    private Block e;
    public int a;
    public int b;
    public boolean c;
    public boolean f; // CanaryMod: private => public; isBreakingAnvil?
    public boolean g; // CanaryMod: private => public; hurtEntities
    public int h; // CanaryMod: private -> public
    public float i; // CanaryMod: private -> public
    public NBTTagCompound d;

    public EntityFallingBlock(World world) {
        super(world);
        this.c = true;
        this.h = 40;
        this.i = 2.0F;
        this.entity = new CanaryFallingBlock(this); // CanaryMod: Wrap Entity
    }

    public EntityFallingBlock(World world, double d0, double d1, double d2, Block block) {
        this(world, d0, d1, d2, block, 0);
    }

    public EntityFallingBlock(World world, double d0, double d1, double d2, Block block, int i0) {
        super(world);
        this.c = true;
        this.h = 40;
        this.i = 2.0F;
        this.e = block;
        this.a = i0;
        this.k = true;
        this.a(0.98F, 0.98F);
        this.L = this.N / 2.0F;
        this.b(d0, d1, d2);
        this.v = 0.0D;
        this.w = 0.0D;
        this.x = 0.0D;
        this.p = d0;
        this.q = d1;
        this.r = d2;
        this.entity = new CanaryFallingBlock(this); // CanaryMod: Wrap Entity
    }

    protected boolean g_() {
        return false;
    }

    protected void c() {
    }

    public boolean R() {
        return !this.K;
    }

    public void h() {
        if (this.e.o() == Material.a) {
            this.B();
        } else {
            this.p = this.s;
            this.q = this.t;
            this.r = this.u;
            ++this.b;
            this.w -= 0.03999999910593033D;
            this.d(this.v, this.w, this.x);
            this.v *= 0.9800000190734863D;
            this.w *= 0.9800000190734863D;
            this.x *= 0.9800000190734863D;
            if (!this.o.E) {
                int i0 = MathHelper.c(this.s);
                int i1 = MathHelper.c(this.t);
                int i2 = MathHelper.c(this.u);

                if (this.b == 1) {
                    if (this.o.a(i0, i1, i2) != this.e) {
                        this.B();
                        return;
                    }

                    this.o.f(i0, i1, i2);
                }

                if (this.D) {
                    this.v *= 0.699999988079071D;
                    this.x *= 0.699999988079071D;
                    this.w *= -0.5D;
                    if (this.o.a(i0, i1, i2) != Blocks.M) {
                        this.B();
                        if (!this.f && this.o.a(this.e, i0, i1, i2, true, 1, (Entity) null, (ItemStack) null) && !BlockFalling.e(this.o, i0, i1 - 1, i2) && this.o.d(i0, i1, i2, this.e, this.a, 3)) {
                            if (this.e instanceof BlockFalling) {
                                ((BlockFalling) this.e).a(this.o, i0, i1, i2, this.a);
                            }

                            if (this.d != null && this.e instanceof ITileEntityProvider) {
                                TileEntity tileentity = this.o.o(i0, i1, i2);

                                if (tileentity != null) {
                                    NBTTagCompound nbttagcompound = new NBTTagCompound();

                                    tileentity.b(nbttagcompound);
                                    Iterator iterator = this.d.c().iterator();

                                    while (iterator.hasNext()) {
                                        String s0 = (String) iterator.next();
                                        NBTBase nbtbase = this.d.a(s0);

                                        if (!s0.equals("x") && !s0.equals("y") && !s0.equals("z")) {
                                            nbttagcompound.a(s0, nbtbase.b());
                                        }
                                    }

                                    tileentity.a(nbttagcompound);
                                    tileentity.e();
                                }
                            }
                        } else if (this.c && !this.f) {
                            this.a(new ItemStack(this.e, 1, this.e.a(this.a)), 0.0F);
                        }
                    }
                } else if (this.b > 100 && !this.o.E && (i1 < 1 || i1 > 256) || this.b > 600) {
                    if (this.c) {
                        this.a(new ItemStack(this.e, 1, this.e.a(this.a)), 0.0F);
                    }
                    this.B();
                }
            }
        }
    }

    protected void b(float f0) {
        if (this.g) {
            int i0 = MathHelper.f(f0 - 1.0F);

            if (i0 > 0) {
                ArrayList arraylist = new ArrayList(this.o.b((Entity) this, this.C));
                boolean flag0 = this.e == Blocks.bQ;
                DamageSource damagesource = flag0 ? DamageSource.m : DamageSource.n;
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

                if (flag0 && (double) this.Z.nextFloat() < 0.05000000074505806D + (double) i0 * 0.05D) {
                    int i1 = this.a >> 2;
                    int i2 = this.a & 3;

                    ++i1;
                    if (i1 > 2) {
                        this.f = true;
                    } else {
                        this.a = i2 | i1 << 2;
                    }
                }
            }
        }
    }

    protected void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Tile", (byte) Block.b(this.e));
        nbttagcompound.a("TileID", Block.b(this.e));
        nbttagcompound.a("Data", (byte) this.a);
        nbttagcompound.a("Time", (byte) this.b);
        nbttagcompound.a("DropItem", this.c);
        nbttagcompound.a("HurtEntities", this.g);
        nbttagcompound.a("FallHurtAmount", this.i);
        nbttagcompound.a("FallHurtMax", this.h);
        if (this.d != null) {
            nbttagcompound.a("TileEntityData", (NBTBase) this.d);
        }
    }

    protected void a(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.b("TileID", 99)) {
            this.e = Block.e(nbttagcompound.f("TileID"));
        } else {
            this.e = Block.e(nbttagcompound.d("Tile") & 255);
        }

        this.a = nbttagcompound.d("Data") & 255;
        this.b = nbttagcompound.d("Time") & 255;
        if (nbttagcompound.b("HurtEntities", 99)) {
            this.g = nbttagcompound.n("HurtEntities");
            this.i = nbttagcompound.h("FallHurtAmount");
            this.h = nbttagcompound.f("FallHurtMax");
        } else if (this.e == Blocks.bQ) {
            this.g = true;
        }

        if (nbttagcompound.b("DropItem", 99)) {
            this.c = nbttagcompound.n("DropItem");
        }

        if (nbttagcompound.b("TileEntityData", 10)) {
            this.d = nbttagcompound.m("TileEntityData");
        }

        if (this.e.o() == Material.a) {
            this.e = Blocks.m;
        }
    }

    public void a(boolean flag0) {
        this.g = flag0;
    }

    public void a(CrashReportCategory crashreportcategory) {
        super.a(crashreportcategory);
        crashreportcategory.a("Immitating block ID", (Object) Integer.valueOf(Block.b(this.e)));
        crashreportcategory.a("Immitating block data", (Object) Integer.valueOf(this.a));
    }

    public Block f() {
        return this.e;
    }

    // CanaryMod
    public void setBlock(Block block) {
        this.e = block;
    }
}
