package net.minecraft.block;

import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.hook.world.TNTActivateHook;
import net.canarymod.hook.world.TNTActivateHook.ActivationCause;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Random;

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
            TNTActivateHook tah = (TNTActivateHook) new TNTActivateHook(world.getCanaryWorld().getBlockAt(i0, i1, i2), null, ActivationCause.EXPLOSION).call();
            if (!tah.isCanceled()) {
                EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.5F), (double) ((float) i2 + 0.5F), explosion.c());

                entitytntprimed.a = world.s.nextInt(entitytntprimed.a / 4) + entitytntprimed.a / 8;
                world.d((Entity) entitytntprimed);
        }
            //
        }
    }

    public void b(World world, int i0, int i1, int i2, int i3) {
        this.a(world, i0, i1, i2, i3, (EntityLivingBase) null, ActivationCause.UNKNOWN); // CanaryMod: Can't say for sure what calls this...
    }

    public void a(World world, int i0, int i1, int i2, int i3, EntityLivingBase entitylivingbase) { // Original Method
        this.a(world, i0, i1, i2, i3, entitylivingbase, ActivationCause.UNKNOWN);
    }

    public void a(World world, int i0, int i1, int i2, int i3, EntityLivingBase entitylivingbase, ActivationCause cause) { // CanaryMod: Signature Change to incorporate ActivationCause
        if (!world.E) {
            if ((i3 & 1) == 1) {
                // CanaryMod: TNTActivateHook
                TNTActivateHook tah = (TNTActivateHook) new TNTActivateHook(world.getCanaryWorld().getBlockAt(i0, i1, i2), (LivingBase) (entitylivingbase == null ? null : entitylivingbase.getCanaryEntity()), cause).call();
                if (!tah.isCanceled()) {
                    EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.5F), (double) ((float) i2 + 0.5F), entitylivingbase);

                    world.d((Entity) entitytntprimed);
                    world.a((Entity) entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
                }
                //
            }
        }
    }

    public boolean a(World world, int i0, int i1, int i2, EntityPlayer entityplayer, int i3, float f0, float f1, float f2) {
        if (entityplayer.bF() != null && entityplayer.bF().b() == Items.d) {
            this.a(world, i0, i1, i2, 1, entityplayer, ActivationCause.FIRE); // CanaryMod: Add FIRE cause
            world.f(i0, i1, i2);
            entityplayer.bF().a(1, (EntityLivingBase) entityplayer);
            return true;
        }
        else {
            return super.a(world, i0, i1, i2, entityplayer, i3, f0, f1, f2);
        }
    }

    public void a(World world, int i0, int i1, int i2, Entity entity) {
        if (entity instanceof EntityArrow && !world.E) {
            EntityArrow entityarrow = (EntityArrow) entity;

            if (entityarrow.al()) {
                this.a(world, i0, i1, i2, 1, entityarrow.c instanceof EntityLivingBase ? (EntityLivingBase) entityarrow.c : null);
                world.f(i0, i1, i2);
            }
        }
    }

    public boolean a(Explosion explosion) {
        return false;
    }
}
