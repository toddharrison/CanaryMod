package net.minecraft.init;


import net.canarymod.hook.world.DispenseHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
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
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;


public class Bootstrap {

    private static boolean a = false;

    static void a() {
        BlockDispenser.a.a(Items.g, new BehaviorProjectileDispense() {

            protected IProjectile a(World world, IPosition iposition) {
                EntityArrow entityarrow = new EntityArrow(world, iposition.a(), iposition.b(), iposition.c());

                entityarrow.a = 1;
                return entityarrow;
            }
        });
        BlockDispenser.a.a(Items.aK, new BehaviorProjectileDispense() {

            protected IProjectile a(World world, IPosition iposition) {
                return new EntityEgg(world, iposition.a(), iposition.b(), iposition.c());
            }
        });
        BlockDispenser.a.a(Items.ay, new BehaviorProjectileDispense() {

            protected IProjectile a(World world, IPosition iposition) {
                return new EntitySnowball(world, iposition.a(), iposition.b(), iposition.c());
            }
        });
        BlockDispenser.a.a(Items.by, new BehaviorProjectileDispense() {

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
        BlockDispenser.a.a(Items.bn, new IBehaviorDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack a(IBlockSource iblocksource, final ItemStack itemstack) {
                return ItemPotion.g(itemstack.k()) ? (new BehaviorProjectileDispense() {

                    protected IProjectile a(World world, IPosition iposition) {
                        return new EntityPotion(world, iposition.a(), iposition.b(), iposition.c(), itemstack.m());
                    }

                    protected float a() {
                        return super.a() * 0.5F;
                    }

                    protected float b() {
                        return super.b() * 1.25F;
                    }
                }
                ).a(iblocksource, itemstack) : this.b.a(iblocksource, itemstack);
            }
        });
        BlockDispenser.a.a(Items.bx, new BehaviorDefaultDispenseItem() {

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
                double d0 = iblocksource.a() + (double) enumfacing.c();
                double d1 = (double) ((float) iblocksource.e() + 0.2F);
                double d2 = iblocksource.c() + (double) enumfacing.e();
                Entity entity = ItemMonsterPlacer.a(iblocksource.k(), itemstack.k(), d0, d1, d2);

                if (entity instanceof EntityLivingBase && itemstack.u()) {
                    ((EntityLiving) entity).a(itemstack.s());
                }

                itemstack.a(1);
                return itemstack;
            }
        });
        BlockDispenser.a.a(Items.bP, new BehaviorDefaultDispenseItem() {

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
                double d0 = iblocksource.a() + (double) enumfacing.c();
                double d1 = (double) ((float) iblocksource.e() + 0.2F);
                double d2 = iblocksource.c() + (double) enumfacing.e();
                EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(iblocksource.k(), d0, d1, d2, itemstack);

                iblocksource.k().d(entityfireworkrocket);
                itemstack.a(1);
                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                iblocksource.k().c(1002, iblocksource.d(), iblocksource.e(), iblocksource.f(), 0);
            }
        });
        BlockDispenser.a.a(Items.bz, new BehaviorDefaultDispenseItem() {

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
                IPosition world5 = BlockDispenser.a(iblocksource);
                double d0 = world5.a() + (double) ((float) enumfacing.c() * 0.3F);
                double d1 = world5.b() + (double) ((float) enumfacing.c() * 0.3F);
                double d2 = world5.c() + (double) ((float) enumfacing.e() * 0.3F);
                World world = iblocksource.k();
                Random random = world.s;
                double d3 = random.nextGaussian() * 0.05D + (double) enumfacing.c();
                double d4 = random.nextGaussian() * 0.05D + (double) enumfacing.d();
                double d5 = random.nextGaussian() * 0.05D + (double) enumfacing.e();

                world.d((Entity) (new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5)));
                itemstack.a(1);
                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                iblocksource.k().c(1009, iblocksource.d(), iblocksource.e(), iblocksource.f(), 0);
            }
        });
        BlockDispenser.a.a(Items.az, new BehaviorDefaultDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
                World world = iblocksource.k();
                double d0 = iblocksource.a() + (double) ((float) enumfacing.c() * 1.125F);
                double d1 = iblocksource.b() + (double) ((float) enumfacing.d() * 1.125F);
                double d2 = iblocksource.c() + (double) ((float) enumfacing.e() * 1.125F);
                int i0 = iblocksource.d() + enumfacing.c();
                int i1 = iblocksource.e() + enumfacing.d();
                int i2 = iblocksource.f() + enumfacing.e();
                Material material = world.a(i0, i1, i2).o();
                double d3;

                if (Material.h.equals(material)) {
                    d3 = 1.0D;
                } else {
                    if (!Material.a.equals(material) || !Material.h.equals(world.a(i0, i1 - 1, i2).o())) {
                        return this.b.a(iblocksource, itemstack);
                    }

                    d3 = 0.0D;
                }

                EntityBoat entityboat = new EntityBoat(world, d0, d1 + d3, d2);

                // CanaryMod: Dispense
                DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.j()).getCanaryDispenser(), entityboat.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    world.d(entityboat);
                    itemstack.a(1);
                }
                //

                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                iblocksource.k().c(1000, iblocksource.d(), iblocksource.e(), iblocksource.f(), 0);
            }
        });
        BehaviorDefaultDispenseItem behaviordefaultdispenseitem = new BehaviorDefaultDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                ItemBucket enumfacing = (ItemBucket) itemstack.b();
                int world = iblocksource.d();
                int i0 = iblocksource.e();
                int i1 = iblocksource.f();
                EnumFacing enumfacing1 = BlockDispenser.b(iblocksource.h());

                if (enumfacing.a(iblocksource.k(), world + enumfacing1.c(), i0 + enumfacing1.d(), i1 + enumfacing1.e())) {
                    itemstack.a(Items.ar);
                    itemstack.b = 1;
                    return itemstack;
                } else {
                    return this.b.a(iblocksource, itemstack);
                }
            }
        };

        BlockDispenser.a.a(Items.at, behaviordefaultdispenseitem);
        BlockDispenser.a.a(Items.as, behaviordefaultdispenseitem);
        BlockDispenser.a.a(Items.ar, new BehaviorDefaultDispenseItem() {

            private final BehaviorDefaultDispenseItem b = new BehaviorDefaultDispenseItem();

            public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
                World world = iblocksource.k();
                int i0 = iblocksource.d() + enumfacing.c();
                int i1 = iblocksource.e() + enumfacing.d();
                int i2 = iblocksource.f() + enumfacing.e();
                Material entitytntprimed = world.a(i0, i1, i2).o();
                int i3 = world.e(i0, i1, i2);
                Item item;

                if (Material.h.equals(entitytntprimed) && i3 == 0) {
                    item = Items.as;
                } else {
                    if (!Material.i.equals(entitytntprimed) || i3 != 0) {
                        return super.b(iblocksource, itemstack);
                    }

                    item = Items.at;
                }

                world.f(i0, i1, i2);
                if (--itemstack.b == 0) {
                    itemstack.a(item);
                    itemstack.b = 1;
                } else if (((TileEntityDispenser) iblocksource.j()).a(new ItemStack(item)) < 0) {
                    this.b.a(iblocksource, new ItemStack(item));
                }

                return itemstack;
            }
        });
        BlockDispenser.a.a(Items.d, new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
                World world = iblocksource.k();
                int i0 = iblocksource.d() + enumfacing.c();
                int i1 = iblocksource.e() + enumfacing.d();
                int i2 = iblocksource.f() + enumfacing.e();

                if (world.c(i0, i1, i2)) {
                    world.b(i0, i1, i2, (Block) Blocks.ab);
                    if (itemstack.a(1, world.s)) {
                        itemstack.b = 0;
                    }
                } else if (world.a(i0, i1, i2) == Blocks.W) {
                    Blocks.W.b(world, i0, i1, i2, 1);
                    world.f(i0, i1, i2);
                } else {
                    this.b = false;
                }

                return itemstack;
            }

            protected void a(IBlockSource iblocksource) {
                if (this.b) {
                    iblocksource.k().c(1000, iblocksource.d(), iblocksource.e(), iblocksource.f(), 0);
                } else {
                    iblocksource.k().c(1001, iblocksource.d(), iblocksource.e(), iblocksource.f(), 0);
                }

            }
        });
        BlockDispenser.a.a(Items.aR, new BehaviorDefaultDispenseItem() {

            private boolean b = true;

            protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                if (itemstack.k() == 15) {
                    EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
                    World world = iblocksource.k();
                    int i16 = iblocksource.d() + enumfacing.c();
                    int i17 = iblocksource.e() + enumfacing.d();
                    int i18 = iblocksource.f() + enumfacing.e();

                    if (ItemDye.a(itemstack, world, i16, i17, i18)) {
                        if (!world.E) {
                            world.c(2005, i16, i17, i18, 0);
                        }
                    } else {
                        this.b = false;
                    }

                    return itemstack;
                } else {
                    return super.b(iblocksource, itemstack);
                }
            }

            protected void a(IBlockSource iblocksource) {
                if (this.b) {
                    iblocksource.k().c(1000, iblocksource.d(), iblocksource.e(), iblocksource.f(), 0);
                } else {
                    iblocksource.k().c(1001, iblocksource.d(), iblocksource.e(), iblocksource.f(), 0);
                }

            }
        });
        BlockDispenser.a.a(Item.a(Blocks.W), new BehaviorDefaultDispenseItem() {

            protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
                EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
                World world = iblocksource.k();
                int i0 = iblocksource.d() + enumfacing.c();
                int i1 = iblocksource.e() + enumfacing.d();
                int i2 = iblocksource.f() + enumfacing.e();
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.5F), (double) ((float) i2 + 0.5F), (EntityLivingBase) null);

                world.d(entitytntprimed);
                --itemstack.b;
                return itemstack;
            }
        });
    }

    public static void b() {
        if (!a) {
            a = true;
            Block.p();
            BlockFire.e();
            Item.l();
            StatList.a();
            a();
        }
    }

}
