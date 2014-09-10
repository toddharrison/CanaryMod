package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.ItemUseHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemMonsterPlacer extends Item {

    public ItemMonsterPlacer() {
        this.a(true);
        this.a(CreativeTabs.f);
    }

    public String n(ItemStack itemstack) {
        String s0 = ("" + StatCollector.a(this.a() + ".name")).trim();
        String s1 = EntityList.b(itemstack.k());

        if (s1 != null) {
            s0 = s0 + " " + StatCollector.a("entity." + s1 + ".name");
        }

        return s0;
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        if (world.E) {
            return true;
        }
        else {
            // CanaryMod: ItemUse
            CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);

            clicked.setFaceClicked(BlockFace.fromByte((byte) i3));
            ItemUseHook hook = (ItemUseHook) new ItemUseHook(((EntityPlayerMP) entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call();
            if (hook.isCanceled()) {
                return false;
            }
            //

            Block block = world.a(i0, i1, i2);

            i0 += Facing.b[i3];
            i1 += Facing.c[i3];
            i2 += Facing.d[i3];
            double d0 = 0.0D;

            if (i3 == 1 && block.b() == 11) {
                d0 = 0.5D;
            }

            Entity entity = a(world, itemstack.k(), (double) i0 + 0.5D, (double) i1 + d0, (double) i2 + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && itemstack.u()) {
                    ((EntityLiving) entity).a(itemstack.s());
                }

                if (!entityplayer.bE.d) {
                    --itemstack.b;
                }
            }

            return true;
        }
    }

    public ItemStack a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (world.E) {
            return itemstack;
        }
        else {
            MovingObjectPosition movingobjectposition = this.a(world, entityplayer, true);

            if (movingobjectposition == null) {
                return itemstack;
            }
            else {
                if (movingobjectposition.a == MovingObjectPosition.MovingObjectType.BLOCK) {
                    int i0 = movingobjectposition.b;
                    int i1 = movingobjectposition.c;
                    int i2 = movingobjectposition.d;

                    if (!world.a(entityplayer, i0, i1, i2)) {
                        return itemstack;
                    }

                    if (!entityplayer.a(i0, i1, i2, movingobjectposition.e, itemstack)) {
                        return itemstack;
                    }

                    if (world.a(i0, i1, i2) instanceof BlockLiquid) {
                        Entity entity = a(world, itemstack.k(), (double) i0, (double) i1, (double) i2);

                        if (entity != null) {
                            if (entity instanceof EntityLivingBase && itemstack.u()) {
                                ((EntityLiving) entity).a(itemstack.s());
                            }

                            if (!entityplayer.bE.d) {
                                --itemstack.b;
                            }
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
                if (entity != null && entity instanceof EntityLivingBase) {
                    EntityLiving entityliving = (EntityLiving) entity;

                    entity.b(d0, d1, d2, MathHelper.g(world.s.nextFloat() * 360.0F), 0.0F);
                    entityliving.aO = entityliving.y;
                    entityliving.aM = entityliving.y;
                    entityliving.a((IEntityLivingData) null);
                    if (spawn) { // CanaryMod check if spawn is allowed
                        world.d(entity);
                    }
                    entityliving.r();
                }
            }

            return entity;
        }
    }
}
