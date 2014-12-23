package net.minecraft.item;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.ItemUseHook;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemMonsterPlacer extends Item {

    public ItemMonsterPlacer() {
        this.a(true);
        this.a(CreativeTabs.f);
    }

    public String a(ItemStack itemstack) {
        String s0 = ("" + StatCollector.a(this.a() + ".name")).trim();
        String s1 = EntityList.b(itemstack.i());

        if (s1 != null) {
            s0 = s0 + " " + StatCollector.a("entity." + s1 + ".name");
        }

        return s0;
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (world.D) {
            return true;
        }
        else if (!entityplayer.a(blockpos.a(enumfacing), enumfacing, itemstack)) {
            return false;
        }
        else {
            IBlockState iblockstate = world.p(blockpos);

            // CanaryMod: ItemUse
            CanaryBlock clicked = CanaryBlock.getPooledBlock(iblockstate, blockpos, world); // Store Clicked
            clicked.setFaceClicked(enumfacing.asBlockFace()); // Set face clicked
            if (new ItemUseHook(((EntityPlayerMP)entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call().isCanceled()) {
                return false;
            }
            //

            if (iblockstate.c() == Blocks.ac) {
                TileEntity tileentity = world.s(blockpos);

                if (tileentity instanceof TileEntityMobSpawner) {
                    MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).b();

                    mobspawnerbaselogic.a(EntityList.b(itemstack.i()));
                    tileentity.o_();
                    world.h(blockpos);
                    if (!entityplayer.by.d) {
                        --itemstack.b;
                    }

                    return true;
                }
            }

            blockpos = blockpos.a(enumfacing);
            double d0 = 0.0D;

            if (enumfacing == EnumFacing.UP && iblockstate instanceof BlockFence) {
                d0 = 0.5D;
            }

            Entity entity = a(world, itemstack.i(), (double)blockpos.n() + 0.5D, (double)blockpos.o() + d0, (double)blockpos.p() + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && itemstack.s()) {
                    entity.a(itemstack.q());
                }

                if (!entityplayer.by.d) {
                    --itemstack.b;
                }
            }

            return true;
        }
    }

    public ItemStack a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (world.D) {
            return itemstack;
        }
        else {
            MovingObjectPosition movingobjectposition = this.a(world, entityplayer, true);

            if (movingobjectposition == null) {
                return itemstack;
            }
            else {
                if (movingobjectposition.a == MovingObjectPosition.MovingObjectType.BLOCK) {
                    BlockPos blockpos = movingobjectposition.a();

                    if (!world.a(entityplayer, blockpos)) {
                        return itemstack;
                    }

                    if (!entityplayer.a(blockpos, movingobjectposition.b, itemstack)) {
                        return itemstack;
                    }

                    if (world.p(blockpos).c() instanceof BlockLiquid) {
                        Entity entity = a(world, itemstack.i(), (double)blockpos.n() + 0.5D, (double)blockpos.o() + 0.5D, (double)blockpos.p() + 0.5D);

                        if (entity != null) {
                            if (entity instanceof EntityLivingBase && itemstack.s()) {
                                ((EntityLiving)entity).a(itemstack.q());
                            }

                            if (!entityplayer.by.d) {
                                --itemstack.b;
                            }

                            entityplayer.b(StatList.J[Item.b((Item)this)]);
                        }
                    }
                }

                return itemstack;
            }
        }
    }

    public static Entity a(World world, int i0, double d0, double d1, double d2) {
        return a(world, i0, d0, d1, d2, true); // CanaryMod: Redirect
    }

    public static Entity a(World world, int i0, double d0, double d1, double d2, boolean spawn) { // CanaryMod: check spawning
        if (!EntityList.a.containsKey(Integer.valueOf(i0))) {
            return null;
        }
        else {
            Entity entity = null;

            for (int i1 = 0; i1 < 1; ++i1) {
                entity = EntityList.a(i0, world);
                if (entity instanceof EntityLivingBase) {
                    EntityLiving entityliving = (EntityLiving)entity;

                    entity.b(d0, d1, d2, MathHelper.g(world.s.nextFloat() * 360.0F), 0.0F);
                    entityliving.aI = entityliving.y;
                    entityliving.aG = entityliving.y;
                    entityliving.a(world.E(new BlockPos(entityliving)), (IEntityLivingData)null);
                    if (spawn) { // CanaryMod check if spawn is allowed
                        world.d(entity);
                    }
                    entityliving.x();
                }
            }

            return entity;
        }
    }
}
