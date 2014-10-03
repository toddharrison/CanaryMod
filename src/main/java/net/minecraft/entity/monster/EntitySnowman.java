package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.CanarySnowman;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public class EntitySnowman extends EntityGolem implements IRangedAttackMob {

    public EntitySnowman(World world) {
        super(world);
        this.a(0.7F, 1.9F);
        ((PathNavigateGround) this.s()).a(true);
        this.i.a(1, new EntityAIArrowAttack(this, 1.25D, 20, 10.0F));
        this.i.a(2, new EntityAIWander(this, 1.0D));
        this.i.a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.i.a(4, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, false, IMob.d));
        this.entity = new CanarySnowman(this); // CanaryMod: Wrap Entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(4.0D);
        this.a(SharedMonsterAttributes.d).a(0.20000000298023224D);
    }

    public void m() {
        super.m();
        if (!this.o.D) {
            int i0 = MathHelper.c(this.s);
            int i1 = MathHelper.c(this.t);
            int i2 = MathHelper.c(this.u);

            if (this.U()) {
                this.a(DamageSource.f, 1.0F);
            }

            if (this.o.b(new BlockPos(i0, 0, i2)).a(new BlockPos(i0, i1, i2)) > 1.0F) {
                this.a(DamageSource.c, 1.0F);
            }

            for (int i3 = 0; i3 < 4; ++i3) {
                i0 = MathHelper.c(this.s + (double) ((float) (i3 % 2 * 2 - 1) * 0.25F));
                i1 = MathHelper.c(this.t);
                i2 = MathHelper.c(this.u + (double) ((float) (i3 / 2 % 2 * 2 - 1) * 0.25F));
                if (this.o.p(new BlockPos(i0, i1, i2)).c().r() == Material.a && this.o.b(new BlockPos(i0, 0, i2)).a(new BlockPos(i0, i1, i2)) < 0.8F && Blocks.aH.c(this.o, new BlockPos(i0, i1, i2))) {
                    this.o.a(new BlockPos(i0, i1, i2), Blocks.aH.P());
                }
            }
        }

    }

    protected Item A() {
        return Items.aD;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(16);

        for (int i2 = 0; i2 < i1; ++i2) {
            this.a(Items.aD, 1);
        }

    }

    public void a(EntityLivingBase entitylivingbase, float f0) {
        EntitySnowball entitysnowball = new EntitySnowball(this.o, this);
        double d0 = entitylivingbase.t + (double) entitylivingbase.aR() - 1.100000023841858D;
        double d1 = entitylivingbase.s - this.s;
        double d2 = d0 - entitysnowball.t;
        double d3 = entitylivingbase.u - this.u;
        float f1 = MathHelper.a(d1 * d1 + d3 * d3) * 0.2F;

        entitysnowball.c(d1, d2 + (double) f1, d3, 1.6F, 12.0F);
        this.a("random.bow", 1.0F, 1.0F / (this.bb().nextFloat() * 0.4F + 0.8F));
        this.o.d((Entity) entitysnowball);
    }

    public float aR() {
        return 1.7F;
    }
}
