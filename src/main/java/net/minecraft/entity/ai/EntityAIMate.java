package net.minecraft.entity.ai;


import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.canarymod.api.ai.CanaryAIMate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;


public class EntityAIMate extends EntityAIBase {

    private EntityAnimal d;
    World a;
    private EntityAnimal e;
    int b;
    double c;
   
    public EntityAIMate(EntityAnimal entityanimal, double d0) {
        this.d = entityanimal;
        this.a = entityanimal.o;
        this.c = d0;
        this.a(3);
        this.canaryAI = new CanaryAIMate(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (!this.d.cp()) {
            return false;
        } else {
            this.e = this.f();
            return this.e != null;
        }
    }

    public boolean b() {
        return this.e.ai() && this.e.cp() && this.b < 60;
    }

    public void d() {
        this.e = null;
        this.b = 0;
    }

    public void e() {
        this.d.p().a(this.e, 10.0F, (float) this.d.bP());
        this.d.s().a((Entity) this.e, this.c);
        ++this.b;
        if (this.b >= 60 && this.d.h(this.e) < 9.0D) {
            this.g();
        }

    }

    private EntityAnimal f() {
        float f0 = 8.0F;
        List list = this.a.a(this.d.getClass(), this.d.aQ().b((double) f0, (double) f0, (double) f0));
        double d0 = Double.MAX_VALUE;
        EntityAnimal entityanimal = null;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityAnimal entityanimal1 = (EntityAnimal) iterator.next();

            if (this.d.a(entityanimal1) && this.d.h(entityanimal1) < d0) {
                entityanimal = entityanimal1;
                d0 = this.d.h(entityanimal1);
            }
        }

        return entityanimal;
    }

    private void g() {
        EntityAgeable entityageable = this.d.a((EntityAgeable) this.e);

        if (entityageable != null) {
            EntityPlayer entityplayer = this.d.co();

            if (entityplayer == null && this.e.co() != null) {
                entityplayer = this.e.co();
            }

            if (entityplayer != null) {
                entityplayer.b(StatList.A);
                if (this.d instanceof EntityCow) {
                    entityplayer.b((StatBase) AchievementList.H);
                }
            }

            this.d.b(6000);
            this.e.b(6000);
            this.d.cq();
            this.e.cq();
            entityageable.b(-24000);
            entityageable.b(this.d.s, this.d.t, this.d.u, 0.0F, 0.0F);
            this.a.d((Entity) entityageable);
            Random random = this.d.bb();

            for (int i0 = 0; i0 < 7; ++i0) {
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;

                this.a.a(EnumParticleTypes.HEART, this.d.s + (double) (random.nextFloat() * this.d.J * 2.0F) - (double) this.d.J, this.d.t + 0.5D + (double) (random.nextFloat() * this.d.K), this.d.u + (double) (random.nextFloat() * this.d.J * 2.0F) - (double) this.d.J, d0, d1, d2, new int[0]);
            }

            if (this.a.Q().b("doMobLoot")) {
                this.a.d((Entity) (new EntityXPOrb(this.a, this.d.s, this.d.t, this.d.u, random.nextInt(7) + 1)));
            }

        }
    }
}
