package net.minecraft.block;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.TNTActivateHook;
import net.canarymod.hook.world.TNTActivateHook.ActivationCause;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTNT extends Block {

    public static final PropertyBool a = PropertyBool.a("explode");

    public BlockTNT() {
        super(Material.u);
        this.j(this.L.b().a(a, Boolean.valueOf(false)));
        this.a(CreativeTabs.d);
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        super.c(world, blockpos, iblockstate);
        if (world.z(blockpos)) {
            this.d(world, blockpos, iblockstate.a(a, Boolean.valueOf(true)));
            world.g(blockpos);
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (world.z(blockpos)) {
            this.d(world, blockpos, iblockstate.a(a, Boolean.valueOf(true)));
            world.g(blockpos);
        }
    }

    public void a(World world, BlockPos blockpos, Explosion explosion) {
        if (!world.D) {
            // CanaryMod: TNTActivateHook
            Canary.log.info("#1");
            if (!new TNTActivateHook(new CanaryBlock(world.p(blockpos), blockpos, world), null, ActivationCause.EXPLOSION).call().isCanceled()) {
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double)((float)blockpos.n() + 0.5F), (double)((float)blockpos.o() + 0.5F), (double)((float)blockpos.p() + 0.5F), explosion.c());

                entitytntprimed.a = world.s.nextInt(entitytntprimed.a / 4) + entitytntprimed.a / 8;
                world.d((Entity)entitytntprimed);
            }
            //
        }
    }

    public void d(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.a(world, blockpos, iblockstate, (EntityLivingBase)null);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityLivingBase entitylivingbase, ActivationCause cause) {
        if (!world.D) {
            Canary.log.info(String.format("#2 %s", ((Boolean)iblockstate.b(a)).booleanValue()));
            if (((Boolean)iblockstate.b(a)).booleanValue()) {
                // CanaryMod: TNTActivateHook
                if (!new TNTActivateHook(new CanaryBlock(iblockstate, blockpos, world), (LivingBase)(entitylivingbase == null ? null : entitylivingbase.getCanaryEntity()), cause).call().isCanceled()) {
                    EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double)((float)blockpos.n() + 0.5F), (double)((float)blockpos.o() + 0.5F), (double)((float)blockpos.p() + 0.5F), entitylivingbase);

                    world.d((Entity)entitytntprimed);
                    world.a((Entity)entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
                }
                //
            }
        }
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (entityplayer.bY() != null) {
            Item item = entityplayer.bY().b();

            if (item == Items.d || item == Items.bL) {
                this.a(world, blockpos, iblockstate.a(a, Boolean.valueOf(true)), (EntityLivingBase)entityplayer);
                world.g(blockpos);
                if (item == Items.d) {
                    entityplayer.bY().a(1, (EntityLivingBase)entityplayer);
                }
                else if (!entityplayer.by.d) {
                    --entityplayer.bY().b;
                }

                return true;
            }
        }

        return super.a(world, blockpos, iblockstate, entityplayer, enumfacing, f0, f1, f2);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Entity entity) {
        if (!world.D && entity instanceof EntityArrow) {
            EntityArrow entityarrow = (EntityArrow)entity;

            if (entityarrow.au()) {
                // CanaryMod: Activation Cause Passing
                this.a(world, blockpos, world.p(blockpos).a(a, Boolean.valueOf(true)), entityarrow.c instanceof EntityLivingBase ? (EntityLivingBase)entityarrow.c : null, ActivationCause.ENTITY);
                world.g(blockpos);
            }
        }
    }

    public boolean a(Explosion explosion) {
        return false;
    }

    public IBlockState a(int i0) {
        return this.P().a(a, Boolean.valueOf((i0 & 1) > 0));
    }

    public int c(IBlockState iblockstate) {
        return ((Boolean)iblockstate.b(a)).booleanValue() ? 1 : 0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a });
    }
}
