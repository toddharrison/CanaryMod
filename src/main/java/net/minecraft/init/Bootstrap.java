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

            protected IProjectile a(World world, IPosition iposition) {
                EntityArrow world8 = new EntityArrow(world, iposition.a(), iposition.b(), iposition.c());

                world8.a = 1;
                return world8;
            }
        });
        BlockDispenser.M.a(Items.aP, new BehaviorProjectileDispense() {

            protected IProjectile a(World world, IPosition iposition) {
                return new EntityEgg(world, iposition.a(), iposition.b(), iposition.c());
            }
        });
        BlockDispenser.M.a(Items.aD, new BehaviorProjectileDispense() {

            protected IProjectile a(World world, IPosition iposition) {
                return new EntitySnowball(world, iposition.a(), iposition.b(), iposition.c());
            }
        });
        BlockDispenser.M.a(Items.bK, new BehaviorProjectileDispense() {

            protected IProjectile a(World world, IPosition iposition) {
                return new EntityExpBottle(world, iposition.a(), iposition.b(), iposition.c());
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

            public ItemStack a(IBlockSource iblocksource, final ItemStack itemStack) {
                return ItemPotion.f(itemStack.i()) ? (new BehaviorProjectileDispense() {

                    protected IProjectile a(World world, IPosition iposition) {
                        return new EntityPotion(world, iposition.a(), iposition.b(), iposition.c(), itemStack.k());
                    }

                    protected float a() {
                        return super.a() * 0.5F;
                    }

                    protected float b() {
                        return super.b() * 1.25F;
                    }
                }
                ).a(iblocksource, itemStack) : this.b.a(iblocksource, itemStack);
            }
        });
        BlockDispenser.M.a(Items.bJ, new BehaviorDefaultDispenseItem() {

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.f());
                double d0 = iblocksource.a() + (double) enumfacing.g();
                double d1 = (double) ((float) iblocksource.d().o() + 0.2F);
                double d2 = iblocksource.c() + (double) enumfacing.i();
                // CanaryMod: Dispense
                Entity entity = ItemMonsterPlacer.a(iblocksource.i(), itemstack.i(), d0, d1, d2);
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.h()).getCanaryDispenser(), entity.getCanaryEntity()).call();
                if (hook.isCanceled()) {
                    entity.J(); // Clean up unspawned entity
                    return itemstack;
                }
                entity = ItemMonsterPlacer.a(iblocksource.i(), itemstack.i(), d0, d1, d2); // Ok, now let it spawn
                //

                if (entity instanceof EntityLivingBase && itemstack.s()) {
                    ((EntityLiving) entity).a(itemstack.q());
                }
                itemstack.a(1);
                return itemstack;
            }
        });
        BlockDispenser.M.a(Items.cb, new BehaviorDefaultDispenseItem() {

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing world8 = BlockDispenser.b(iblocksource.f());
                double d0 = iblocksource.a() + (double) world8.g();
                double d1 = (double) ((float) iblocksource.d().o() + 0.2F);
                double d2 = iblocksource.c() + (double) world8.i();
                EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(iblocksource.i(), d0, d1, d2, itemstack);

                // CanaryMod: Dispense
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.h()).getCanaryDispenser(), entityfireworkrocket.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    iblocksource.i().d(entityfireworkrocket);
                    itemstack.a(1);
                }
                //
                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                iblocksource.i().b(1002, iblocksource.d(), 0);
            }
        });
        BlockDispenser.M.a(Items.bL, new BehaviorDefaultDispenseItem() {

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.f());
                IPosition iposition = BlockDispenser.a(iblocksource);
                double d0 = iposition.a() + (double) ((float) enumfacing.g() * 0.3F);
                double d1 = iposition.b() + (double) ((float) enumfacing.g() * 0.3F);
                double d2 = iposition.c() + (double) ((float) enumfacing.i() * 0.3F);
                World world = iblocksource.i();
                Random random = world.s;
                double d3 = random.nextGaussian() * 0.05D + (double) enumfacing.g();
                double d4 = random.nextGaussian() * 0.05D + (double) enumfacing.h();
                double d5 = random.nextGaussian() * 0.05D + (double) enumfacing.i();

                // CanaryMod: Dispense
                EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5);
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.h()).getCanaryDispenser(), entitysmallfireball.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    iblocksource.i().d(entitysmallfireball);
                    itemstack.a(1);
                }
                //
                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                iblocksource.i().b(1009, iblocksource.d(), 0);
            }
        });
        BlockDispenser.M.a(Items.aE, new BehaviorDefaultDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.f());
                World world = iblocksource.i();
                double d0 = iblocksource.a() + (double) ((float) enumfacing.g() * 1.125F);
                double d1 = iblocksource.b() + (double) ((float) enumfacing.h() * 1.125F);
                double d2 = iblocksource.c() + (double) ((float) enumfacing.i() * 1.125F);
                BlockPos blockpos = iblocksource.d().a(enumfacing);
                Material material = world.p(blockpos).c().r();
                double d15;

                if (Material.h.equals(material)) {
                    d15 = 1.0D;
                }
                else {
                    if (!Material.a.equals(material) || !Material.h.equals(world.p(blockpos.b()).c().r())) {
                        return this.b.a(iblocksource, itemstack);
                    }

                    d15 = 0.0D;
                }

                EntityBoat entityboat = new EntityBoat(world, d0, d1 + d15, d2);

                // CanaryMod: Dispense
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.h()).getCanaryDispenser(), entityboat.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    world.d(entityboat);
                    itemstack.a(1);
                }
                //

                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                iblocksource.i().b(1000, iblocksource.d(), 0);
            }
        });
        BehaviorDefaultDispenseItem behaviordefaultdispenseitem = new BehaviorDefaultDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                ItemBucket itembucket = (ItemBucket) itemstack.b();
                BlockPos blockpos = iblocksource.d().a(BlockDispenser.b(iblocksource.f()));

                // CanaryMod: Dispense
                if (itembucket.a(iblocksource.i(), blockpos)) { // Simulate first
                    DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.h()).getCanaryDispenser(), null).call();
                    if (!hook.isCanceled()) {
                        itembucket.a(iblocksource.i(), blockpos); // now do it
                        itemstack.a(Items.ar);
                        itemstack.b = 1;
                    }
                    return itemstack;
                }
                else {
                    return this.b.a(iblocksource, itemstack);
                }
                //
            }
        };

        BlockDispenser.M.a(Items.ay, behaviordefaultdispenseitem);
        BlockDispenser.M.a(Items.ax, behaviordefaultdispenseitem);
        BlockDispenser.M.a(Items.aw, new BehaviorDefaultDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                World world = iblocksource.i();
                BlockPos blockpos = iblocksource.d().a(BlockDispenser.b(iblocksource.f()));
                IBlockState iblockstate = world.p(blockpos);
                Block block = iblockstate.c();
                Material material = block.r();
                Item item;

                if (Material.h.equals(material) && block instanceof BlockLiquid && ((Integer) iblockstate.b(BlockLiquid.b)).intValue() == 0) {
                    item = Items.ax;
                }
                else {
                    if (!Material.i.equals(material) || !(block instanceof BlockLiquid) || ((Integer) iblockstate.b(BlockLiquid.b)).intValue() != 0) {
                        return super.b(iblocksource, itemstack);
                    }

                    item = Items.ay;
                }

                world.g(blockpos);
                if (--itemstack.b == 0) {
                    itemstack.a(item);
                    itemstack.b = 1;
                }
                else if (((TileEntityDispenser) iblocksource.h()).a(new ItemStack(item)) < 0) {
                    this.b.a(iblocksource, new ItemStack(item));
                }

                return itemstack;
            }
        });
        BlockDispenser.M.a(Items.d, new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                World world = iblocksource.i();
                BlockPos blockpos = iblocksource.d().a(BlockDispenser.b(iblocksource.f()));

                if (world.d(blockpos)) {
                    world.a(blockpos, Blocks.ab.P());
                    if (itemstack.a(1, world.s)) {
                        itemstack.b = 0;
                    }
                }
                else if (world.p(blockpos).c() == Blocks.W) {
                    Blocks.W.d(world, blockpos, Blocks.W.P().a(BlockTNT.a, Boolean.valueOf(true)));
                    world.g(blockpos);
                }
                else {
                    this.b = false;
                }

                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                if (this.b) {
                    iblocksource.i().b(1000, iblocksource.d(), 0);
                }
                else {
                    iblocksource.i().b(1001, iblocksource.d(), 0);
                }

            }
        });
        BlockDispenser.M.a(Items.aW, new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                if (EnumDyeColor.WHITE == EnumDyeColor.a(itemstack.i())) {
                    World world = iblocksource.i();
                    BlockPos blockpos = iblocksource.d().a(BlockDispenser.b(iblocksource.f()));

                    if (ItemDye.a(itemstack, world, blockpos)) {
                        if (!world.D) {
                            world.b(2005, blockpos, 0);
                        }
                    }
                    else {
                        this.b = false;
                    }

                    return itemstack;
                }
                else {
                    return super.b(iblocksource, itemstack);
                }
            }

            protected void a(IBlockSource iblocksource) {
                if (this.b) {
                    iblocksource.i().b(1000, iblocksource.d(), 0);
                }
                else {
                    iblocksource.i().b(1001, iblocksource.d(), 0);
                }

            }
        });
        BlockDispenser.M.a(Item.a(Blocks.W), new BehaviorDefaultDispenseItem() {

            protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                World world = iblocksource.i();
                BlockPos blockpos = iblocksource.d().a(BlockDispenser.b(iblocksource.f()));
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double) blockpos.n() + 0.5D, (double) blockpos.o(), (double) blockpos.p() + 0.5D, (EntityLivingBase) null);

                // CanaryMod: Dispense
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.h()).getCanaryDispenser(), entitytntprimed.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    world.d((Entity) entitytntprimed);
                    world.a((Entity) entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
                    --itemstack.b;
                }
                //
                return itemstack;
            }
        });

        BlockDispenser.M.a(Items.bX, new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                World world = iblocksource.i();
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.f());
                BlockPos blockpos = iblocksource.d().a(enumfacing);
                BlockSkull blockskull = Blocks.ce;

                if (world.d(blockpos) && blockskull.b(world, blockpos, itemstack)) {
                    if (!world.D) {
                        world.a(blockpos, blockskull.P().a(BlockSkull.a, EnumFacing.UP), 3);
                        TileEntity tileentity = world.s(blockpos);

                        if (tileentity instanceof TileEntitySkull) {
                            if (itemstack.i() == 3) {
                                GameProfile gameprofile = null;

                                if (itemstack.n()) {
                                    NBTTagCompound nbttagcompound = itemstack.o();

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
                                ((TileEntitySkull) tileentity).a(itemstack.i());
                            }

                            ((TileEntitySkull) tileentity).b(enumfacing.d().b() * 4);
                            Blocks.ce.a(world, blockpos, (TileEntitySkull) tileentity);
                        }

                        --itemstack.b;
                    }
                }
                else {
                    this.b = false;
                }

                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                if (this.b) {
                    iblocksource.i().b(1000, iblocksource.d(), 0);
                }
                else {
                    iblocksource.i().b(1001, iblocksource.d(), 0);
                }

            }
        });
        BlockDispenser.M.a(Item.a(Blocks.aU), new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                World world = iblocksource.i();
                BlockPos blockpos = iblocksource.d().a(BlockDispenser.b(iblocksource.f()));
                BlockPumpkin blockpumpkin = (BlockPumpkin) Blocks.aU;

                if (world.d(blockpos) && blockpumpkin.d(world, blockpos)) {
                    if (!world.D) {
                        world.a(blockpos, blockpumpkin.P(), 3);
                    }

                    --itemstack.b;
                }
                else {
                    this.b = false;
                }

                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                if (this.b) {
                    iblocksource.i().b(1000, iblocksource.d(), 0);
                }
                else {
                    iblocksource.i().b(1001, iblocksource.d(), 0);
                }

            }
        });
        BlockDispenser.M.a(Item.a(Blocks.bX), new BehaviorDefaultDispenseItem() {

            protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                World world = iblocksource.i();
                BlockPos blockpos = iblocksource.d().a(BlockDispenser.b(iblocksource.f()));

                if (world.d(blockpos)) {
                    if (!world.D) {
                        IBlockState iblockstate1 = Blocks.bX.P().a(BlockCommandBlock.a, Boolean.valueOf(false));

                        world.a(blockpos, iblockstate1, 3);
                        ItemBlock.a(world, blockpos, itemstack);
                        world.c(iblocksource.d(), iblocksource.e());
                    }

                    --itemstack.b;
                }

                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
            }

            protected void a(IBlockSource iblocksource, EnumFacing enumfacing) {
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
