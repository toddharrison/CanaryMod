package net.minecraft.init;


import com.mojang.authlib.GameProfile;
import net.canarymod.hook.world.DispenseHook;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LoggingPrintStream;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.util.Random;
import java.util.UUID;


public class Bootstrap {

    private static final PrintStream a = System.out;
    private static boolean b = false;
    private static final Logger c = LogManager.getLogger();

    public static boolean a() {
        return b;
    }

    static void b() {
        BlockDispenser.M.a(Items.g, new BehaviorProjectileDispense() {

            protected IProjectile a(World p_a_1_, IPosition p_a_2_) {
                EntityArrow world8 = new EntityArrow(p_a_1_, p_a_2_.a(), p_a_2_.b(), p_a_2_.c());

                world8.a = 1;
                return world8;
            }
        });
        BlockDispenser.M.a(Items.aP, new BehaviorProjectileDispense() {

            protected IProjectile a(World p_a_1_, IPosition p_a_2_) {
                return new EntityEgg(p_a_1_, p_a_2_.a(), p_a_2_.b(), p_a_2_.c());
            }
        });
        BlockDispenser.M.a(Items.aD, new BehaviorProjectileDispense() {

            protected IProjectile a(World p_a_1_, IPosition p_a_2_) {
                return new EntitySnowball(p_a_1_, p_a_2_.a(), p_a_2_.b(), p_a_2_.c());
            }
        });
        BlockDispenser.M.a(Items.bK, new BehaviorProjectileDispense() {

            protected IProjectile a(World p_a_1_, IPosition p_a_2_) {
                return new EntityExpBottle(p_a_1_, p_a_2_.a(), p_a_2_.b(), p_a_2_.c());
            }

            protected float a() {
                return super.a() * 0.5F;
            }

            protected float b() {
                return super.b() * 1.25F;
            }
        });
        BlockDispenser.M.a(Items.bz, new IBehaviorDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack a(IBlockSource p_a_1_, final ItemStack p_a_2_) {
                return ItemPotion.f(p_a_2_.i()) ? (new BehaviorProjectileDispense() {

                    protected IProjectile a(World p_a_1_, IPosition p_a_2_x) {
                        return new EntityPotion(p_a_1_, p_a_2_x.a(), p_a_2_x.b(), p_a_2_x.c(), p_a_2_.k());
                    }

                    protected float a() {
                        return super.a() * 0.5F;
                    }

                    protected float b() {
                        return super.b() * 1.25F;
                    }
                }
                ).a(p_a_1_, p_a_2_) : this.b.a(p_a_1_, p_a_2_);
            }
        });
        BlockDispenser.M.a(Items.bJ, new BehaviorDefaultDispenseItem() {

            public ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                EnumFacing world8 = BlockDispenser.b(p_b_1_.f());
                double blockpos8 = p_b_1_.a() + (double) world8.g();
                double blockskull = (double) ((float) p_b_1_.d().o() + 0.2F);
                double gameprofile = p_b_1_.c() + (double) world8.i();
                // CanaryMod: Dispense
                Entity entity = ItemMonsterPlacer.a(p_b_1_.i(), p_b_2_.i(), blockpos8, blockskull, gameprofile);
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) p_b_1_.j()).getCanaryDispenser(), entity.getCanaryEntity()).call();
                if (hook.isCanceled()) {
                    entity.J()); // Clean up unspawned entity
                    return p_b_2_;
                }
                entity = ItemMonsterPlacer.a(p_b_1_.i(), p_b_2_.k(), blockpos8); // Ok, now let it spawn
                //

                if (entity instanceof EntityLivingBase && p_b_2_.s()) {
                    ((EntityLiving) entity).a(p_b_2_.q());
                }
                p_b_2_.a(1);
                return p_b_2_;
            }
        });
        BlockDispenser.M.a(Items.cb, new BehaviorDefaultDispenseItem() {

            public ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                EnumFacing world8 = BlockDispenser.b(p_b_1_.f());
                double blockpos8 = p_b_1_.a() + (double) world8.g();
                double blockskull = (double) ((float) p_b_1_.d().o() + 0.2F);
                double gameprofile = p_b_1_.c() + (double) world8.i();
                EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(p_b_1_.i(), blockpos8, blockskull, gameprofile, p_b_2_);

                // CanaryMod: Dispense
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) p_b_1_.i()).getCanaryDispenser(), entityfireworkrocket.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    p_b_1_.i().d(entityfireworkrocket);
                    p_b_2_.a(1);
                }
                //
                return p_b_2_;
            }

            protected void a(IBlockSource p_a_1_) {
                p_a_1_.i().b(1002, p_a_1_.d(), 0);
            }
        });
        BlockDispenser.M.a(Items.bL, new BehaviorDefaultDispenseItem() {

            public ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                EnumFacing world8 = BlockDispenser.b(p_b_1_.f());
                IPosition blockpos8 = BlockDispenser.a(p_b_1_);
                double d0 = blockpos8.a() + (double) ((float) world8.g() * 0.3F);
                double d1 = blockpos8.b() + (double) ((float) world8.g() * 0.3F);
                double d2 = blockpos8.c() + (double) ((float) world8.i() * 0.3F);
                World world = p_b_1_.i();
                Random material = world.s;
                double d3 = material.nextGaussian() * 0.05D + (double) world8.g();
                double d4 = material.nextGaussian() * 0.05D + (double) world8.h();
                double d5 = material.nextGaussian() * 0.05D + (double) world8.i();

                // CanaryMod: Dispense
                EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5);
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) p_b_1_.h()).getCanaryDispenser(), entitysmallfireball.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    p_b_1_.i().d(entitysmallfireball);
                    p_b_2_.a(1);
                }
                //
                return p_b_2_;
            }

            protected void a(IBlockSource p_a_1_) {
                p_a_1_.i().b(1009, p_a_1_.d(), 0);
            }
        });
        BlockDispenser.M.a(Items.aE, new BehaviorDefaultDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                EnumFacing world8 = BlockDispenser.b(p_b_1_.f());
                World world = p_b_1_.i();
                double iblockstate1 = p_b_1_.a() + (double) ((float) world8.g() * 1.125F);
                double tileentity = p_b_1_.b() + (double) ((float) world8.h() * 1.125F);
                double nbttagcompound = p_b_1_.c() + (double) ((float) world8.i() * 1.125F);
                BlockPos blockpos = p_b_1_.d().a(world8);
                Material material = world.p(blockpos).c().r();
                double d15;

                if (Material.h.equals(material)) {
                    d15 = 1.0D;
                }
                else {
                    if (!Material.a.equals(material) || !Material.h.equals(world.p(blockpos.b()).c().r())) {
                        return this.b.a(p_b_1_, p_b_2_);
                    }

                    d15 = 0.0D;
                }

                EntityBoat entityboat = new EntityBoat(world, iblockstate1, tileentity + d15, nbttagcompound);

                // CanaryMod: Dispense
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) p_b_1_.h()).getCanaryDispenser(), entityboat.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    world.d(entityboat);
                    p_b_2_.a(1);
                }
                //

                return p_b_2_;
            }

            protected void a(IBlockSource p_a_1_) {
                p_a_1_.i().b(1000, p_a_1_.d(), 0);
            }
        });
        BehaviorDefaultDispenseItem behaviordefaultdispenseitem = new BehaviorDefaultDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                ItemBucket world8 = (ItemBucket) p_b_2_.b();
                BlockPos blockpos8 = p_b_1_.d().a(BlockDispenser.b(p_b_1_.f()));

                // CanaryMod: Dispense
                if (world8.a(p_b_1_.i(), blockpos8)) { // Simulate first
                    DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) p_b_1_.h()).getCanaryDispenser(), null).call();
                    if (!hook.isCanceled()) {
                        world8.a(p_b_1_.i(), blockpos8); // now do it
                        p_b_2_.a(Items.ar);
                        p_b_2_.b = 1;
                    }
                    return p_b_1_;
                }
                else {
                    return this.b.a(p_b_2_, p_b_1_);
                }
                //
            }
        };

        BlockDispenser.M.a(Items.ay, behaviordefaultdispenseitem);
        BlockDispenser.M.a(Items.ax, behaviordefaultdispenseitem);
        BlockDispenser.M.a(Items.aw, new BehaviorDefaultDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                World world8 = p_b_1_.i();
                BlockPos blockpos8 = p_b_1_.d().a(BlockDispenser.b(p_b_1_.f()));
                IBlockState iblockstate1 = world8.p(blockpos8);
                Block blockskull = iblockstate1.c();
                Material tileentity = blockskull.r();
                Item gameprofile;

                if (Material.h.equals(tileentity) && blockskull instanceof BlockLiquid && ((Integer) iblockstate1.b(BlockLiquid.b)).intValue() == 0) {
                    gameprofile = Items.ax;
                }
                else {
                    if (!Material.i.equals(tileentity) || !(blockskull instanceof BlockLiquid) || ((Integer) iblockstate1.b(BlockLiquid.b)).intValue() != 0) {
                        return super.b(p_b_1_, p_b_2_);
                    }

                    gameprofile = Items.ay;
                }

                world8.g(blockpos8);
                if (--p_b_2_.b == 0) {
                    p_b_2_.a(gameprofile);
                    p_b_2_.b = 1;
                }
                else if (((TileEntityDispenser) p_b_1_.h()).a(new ItemStack(gameprofile)) < 0) {
                    this.b.a(p_b_1_, new ItemStack(gameprofile));
                }

                return p_b_2_;
            }
        });
        BlockDispenser.M.a(Items.d, new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                World world8 = p_b_1_.i();
                BlockPos blockpos8 = p_b_1_.d().a(BlockDispenser.b(p_b_1_.f()));

                if (world8.d(blockpos8)) {
                    world8.a(blockpos8, Blocks.ab.P());
                    if (p_b_2_.a(1, world8.s)) {
                        p_b_2_.b = 0;
                    }
                }
                else if (world8.p(blockpos8).c() == Blocks.W) {
                    Blocks.W.d(world8, blockpos8, Blocks.W.P().a(BlockTNT.a, Boolean.valueOf(true)));
                    world8.g(blockpos8);
                }
                else {
                    this.b = false;
                }

                return p_b_2_;
            }

            protected void a(IBlockSource p_a_1_) {
                if (this.b) {
                    p_a_1_.i().b(1000, p_a_1_.d(), 0);
                }
                else {
                    p_a_1_.i().b(1001, p_a_1_.d(), 0);
                }

            }
        });
        BlockDispenser.M.a(Items.aW, new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                if (EnumDyeColor.WHITE == EnumDyeColor.a(p_b_2_.i())) {
                    World world8 = p_b_1_.i();
                    BlockPos blockpos8 = p_b_1_.d().a(BlockDispenser.b(p_b_1_.f()));

                    if (ItemDye.a(p_b_2_, world8, blockpos8)) {
                        if (!world8.D) {
                            world8.b(2005, blockpos8, 0);
                        }
                    }
                    else {
                        this.b = false;
                    }

                    return p_b_2_;
                }
                else {
                    return super.b(p_b_1_, p_b_2_);
                }
            }

            protected void a(IBlockSource p_a_1_) {
                if (this.b) {
                    p_a_1_.i().b(1000, p_a_1_.d(), 0);
                }
                else {
                    p_a_1_.i().b(1001, p_a_1_.d(), 0);
                }

            }
        });
        BlockDispenser.M.a(Item.a(Blocks.W), new BehaviorDefaultDispenseItem() {

            protected ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                World world8 = p_b_1_.i();
                BlockPos blockpos8 = p_b_1_.d().a(BlockDispenser.b(p_b_1_.f()));
                EntityTNTPrimed iblockstate1 = new EntityTNTPrimed(world8, (double) blockpos8.n() + 0.5D, (double) blockpos8.o(), (double) blockpos8.p() + 0.5D, (EntityLivingBase) null);

                // CanaryMod: Dispense
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) p_b_1_.j()).getCanaryDispenser(), iblockstate1.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    world8.d((Entity) iblockstate1);
                    world8.a((Entity) iblockstate1, "game.tnt.primed", 1.0F, 1.0F);
                    --p_b_2_.b;
                }
                //
                return p_b_2_;
            }
        });

        BlockDispenser.M.a(Items.bX, new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                World world8 = p_b_1_.i();
                EnumFacing blockpos8 = BlockDispenser.b(p_b_1_.f());
                BlockPos iblockstate1 = p_b_1_.d().a(blockpos8);
                BlockSkull blockskull = Blocks.ce;

                if (world8.d(iblockstate1) && blockskull.b(world8, iblockstate1, p_b_2_)) {
                    if (!world8.D) {
                        world8.a(iblockstate1, blockskull.P().a(BlockSkull.a, EnumFacing.UP), 3);
                        TileEntity tileentity = world8.s(iblockstate1);

                        if (tileentity instanceof TileEntitySkull) {
                            if (p_b_2_.i() == 3) {
                                GameProfile gameprofile = null;

                                if (p_b_2_.n()) {
                                    NBTTagCompound nbttagcompound = p_b_2_.o();

                                    if (nbttagcompound.b("SkullOwner", 10)) {
                                        gameprofile = NBTUtil.a(nbttagcompound.m("SkullOwner"));
                                    }
                                    else if (nbttagcompound.b("SkullOwner", 8)) {
                                        gameprofile = new GameProfile((UUID) null, nbttagcompound.j("SkullOwner"));
                                    }
                                }

                                ((TileEntitySkull) tileentity).a(gameprofile);
                            }
                            else {
                                ((TileEntitySkull) tileentity).a(p_b_2_.i());
                            }

                            ((TileEntitySkull) tileentity).b(blockpos8.d().b() * 4);
                            Blocks.ce.a(world8, iblockstate1, (TileEntitySkull) tileentity);
                        }

                        --p_b_2_.b;
                    }
                }
                else {
                    this.b = false;
                }

                return p_b_2_;
            }

            protected void a(IBlockSource p_a_1_) {
                if (this.b) {
                    p_a_1_.i().b(1000, p_a_1_.d(), 0);
                }
                else {
                    p_a_1_.i().b(1001, p_a_1_.d(), 0);
                }

            }
        });
        BlockDispenser.M.a(Item.a(Blocks.aU), new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                World world8 = p_b_1_.i();
                BlockPos blockpos8 = p_b_1_.d().a(BlockDispenser.b(p_b_1_.f()));
                BlockPumpkin iblockstate1 = (BlockPumpkin) Blocks.aU;

                if (world8.d(blockpos8) && iblockstate1.d(world8, blockpos8)) {
                    if (!world8.D) {
                        world8.a(blockpos8, iblockstate1.P(), 3);
                    }

                    --p_b_2_.b;
                }
                else {
                    this.b = false;
                }

                return p_b_2_;
            }

            protected void a(IBlockSource p_a_1_) {
                if (this.b) {
                    p_a_1_.i().b(1000, p_a_1_.d(), 0);
                }
                else {
                    p_a_1_.i().b(1001, p_a_1_.d(), 0);
                }

            }
        });
        BlockDispenser.M.a(Item.a(Blocks.bX), new BehaviorDefaultDispenseItem() {

            protected ItemStack b(IBlockSource p_b_1_, ItemStack p_b_2_) {
                World world8 = p_b_1_.i();
                BlockPos blockpos8 = p_b_1_.d().a(BlockDispenser.b(p_b_1_.f()));

                if (world8.d(blockpos8)) {
                    if (!world8.D) {
                        IBlockState iblockstate1 = Blocks.bX.P().a(BlockCommandBlock.a, Boolean.valueOf(false));

                        world8.a(blockpos8, iblockstate1, 3);
                        ItemBlock.a(world8, blockpos8, p_b_2_);
                        world8.c(p_b_1_.d(), p_b_1_.e());
                    }

                    --p_b_2_.b;
                }

                return p_b_2_;
            }

            protected void a(IBlockSource p_a_1_) {
            }

            protected void a(IBlockSource p_a_1_, EnumFacing p_a_2_) {
            }
        });
    }

    public static void c() {
        if (!b) {
            b = true;
            if (c.isDebugEnabled()) {
                d();
            }

            Block.R();
            BlockFire.j();
            Item.t();
            StatList.a();
            b();
        }
    }

    private static void d() {
        System.setErr(new LoggingPrintStream("STDERR", System.err));
        System.setOut(new LoggingPrintStream("STDOUT", a));
    }

}
