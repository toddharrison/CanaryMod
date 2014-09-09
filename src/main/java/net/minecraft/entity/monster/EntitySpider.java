package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanarySpider;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Random;

public class EntitySpider extends EntityMob {

    public EntitySpider(World world) {
        super(world);
        this.a(1.4F, 0.9F);
        this.entity = new CanarySpider(this); // CanaryMod: Wrap Entity
    }

    protected void c() {
        super.c();
        this.af.a(16, new Byte((byte) 0));
    }

    public void h() {
        super.h();
        if (!this.o.E) {
            this.a(this.E);
        }
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(16.0D);
        this.a(SharedMonsterAttributes.d).a(0.800000011920929D);
    }

    protected Entity bR() {
        float f0 = this.d(1.0F);

        if (f0 < 0.5F) {
            double d0 = 16.0D;

            return this.o.b(this, d0);
        } else {
            return null;
        }
    }

    protected String t() {
        return "mob.spider.say";
    }

    protected String aT() {
        return "mob.spider.say";
    }

    protected String aU() {
        return "mob.spider.death";
    }

    protected void a(int i0, int i1, int i2, Block block) {
        this.a("mob.spider.step", 0.15F, 1.0F);
    }

    protected void a(Entity entity, float f0) {
        float f1 = this.d(1.0F);

        if (f1 > 0.5F && this.Z.nextInt(100) == 0) {
            this.bm = null;
        } else {
            if (f0 > 2.0F && f0 < 6.0F && this.Z.nextInt(10) == 0) {
                if (this.D) {
                    double d0 = entity.s - this.s;
                    double d1 = entity.u - this.u;
                    float f2 = MathHelper.a(d0 * d0 + d1 * d1);

                    this.v = d0 / (double) f2 * 0.5D * 0.800000011920929D + this.v * 0.20000000298023224D;
                    this.x = d1 / (double) f2 * 0.5D * 0.800000011920929D + this.x * 0.20000000298023224D;
                    this.w = 0.4000000059604645D;
                }
            } else {
                super.a(entity, f0);
            }
        }
    }

    protected Item u() {
        return Items.F;
    }

    protected void b(boolean flag0, int i0) {
        super.b(flag0, i0);
        if (flag0 && (this.Z.nextInt(3) == 0 || this.Z.nextInt(1 + i0) > 0)) {
            this.a(Items.bp, 1);
        }
    }

    public boolean h_() {
        return this.bZ();
    }

    public void as() {
    }

    public EnumCreatureAttribute bd() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    public boolean d(PotionEffect potioneffect) {
        return potioneffect.a() == Potion.u.H ? false : super.d(potioneffect);
    }

    public boolean bZ() {
        return (this.af.a(16) & 1) != 0;
    }

    public void a(boolean flag0) {
        byte b0 = this.af.a(16);

        if (flag0) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 &= -2;
        }

        this.af.b(16, Byte.valueOf(b0));
    }

    public IEntityLivingData a(IEntityLivingData ientitylivingdata) {
        Object object = super.a(ientitylivingdata);

        if (this.o.s.nextInt(100) == 0) {
            EntitySkeleton entityskeleton = new EntitySkeleton(this.o);

            entityskeleton.b(this.s, this.t, this.u, this.y, 0.0F);
            entityskeleton.a((IEntityLivingData) null);
            this.o.d((Entity) entityskeleton);
            entityskeleton.a((Entity) this);
        }

        if (object == null) {
            object = new EntitySpider.GroupData();
            if (this.o.r == EnumDifficulty.HARD && this.o.s.nextFloat() < 0.1F * this.o.b(this.s, this.t, this.u)) {
                ((EntitySpider.GroupData) object).a(this.o.s);
            }
        }

        if (ientitylivingdata1 instanceof EntitySpider.GroupData) {
            int i0 = ((EntitySpider.GroupData) object).a;

            if (i0 > 0 && Potion.a[i0] != null) {
                this.c(new PotionEffect(i0, Integer.MAX_VALUE));
            }
        }

        return (IEntityLivingData) object;
    }

    public static class GroupData implements IEntityLivingData {

        public int a;

        public void a(Random random) {
            int i0 = random.nextInt(5);

            if (i0 <= 1) {
                this.a = Potion.c.H;
            } else if (i0 <= 2) {
                this.a = Potion.g.H;
            } else if (i0 <= 3) {
                this.a = Potion.l.H;
            } else if (i0 <= 4) {
                this.a = Potion.p.H;
            }

        }
    }
}
