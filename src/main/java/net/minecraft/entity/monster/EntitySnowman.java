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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob {

    public EntitySnowman(World world) {
        super(world);
        this.a(0.4F, 1.8F);
        this.m().a(true);
        this.c.a(1, new EntityAIArrowAttack(this, 1.25D, 20, 10.0F));
        this.c.a(2, new EntityAIWander(this, 1.0D));
        this.c.a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.c.a(4, new EntityAILookIdle(this));
        this.d.a(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.a));
        this.entity = new CanarySnowman(this); // CanaryMod: Wrap Entity
    }

    public boolean bk() {
        return true;
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(4.0D);
        this.a(SharedMonsterAttributes.d).a(0.20000000298023224D);
    }

    public void e() {
        super.e();
        int i0 = MathHelper.c(this.t);
        int i1 = MathHelper.c(this.u);
        int i2 = MathHelper.c(this.v);

        if (this.L()) {
            this.a(DamageSource.e, 1.0F);
        }

        if (this.p.a(i0, i2).a(i0, i1, i2) > 1.0F) {
            this.a(DamageSource.b, 1.0F);
        }

        for (int i3 = 0; i3 < 4; ++i3) {
            i0 = MathHelper.c(this.t + (double) ((float) (i3 % 2 * 2 - 1) * 0.25F));
            i1 = MathHelper.c(this.u);
            i2 = MathHelper.c(this.v + (double) ((float) (i3 / 2 % 2 * 2 - 1) * 0.25F));
            if (this.p.a(i0, i1, i2).o() == Material.a && this.p.a(i0, i2).a(i0, i1, i2) < 0.8F && Blocks.aC.c(this.p, i0, i1, i2)) {
                this.p.b(i0, i1, i2, Blocks.aC);
            }
        }
    }

    protected Item u() {
        return Items.ay;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.aa.nextInt(16);

        for (int i2 = 0; i2 < i1; ++i2) {
            this.a(Items.ay, 1);
        }
    }

    public void a(EntityLivingBase entitylivingbase, float f0) {
        EntitySnowball entitysnowball = new EntitySnowball(this.p, this);
        double d0 = entitylivingbase.t - this.t;
        double d1 = entitylivingbase.u + (double) entitylivingbase.g() - 1.100000023841858D - entitysnowball.u;
        double d2 = entitylivingbase.v - this.v;
        float f1 = MathHelper.a(d0 * d0 + d2 * d2) * 0.2F;

        entitysnowball.c(d0, d1 + (double) f1, d2, 1.6F, 12.0F);
        this.a("random.bow", 1.0F, 1.0F / (this.aI().nextFloat() * 0.4F + 0.8F));
        this.p.d((Entity) entitysnowball);
    }
}
