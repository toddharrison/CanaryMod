package net.minecraft.entity.passive;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.animal.CanaryAnimal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals {

    private int bp;
    private int bq;
    private EntityPlayer br;

    public EntityAnimal(World world) {
        super(world);

        //CanaryMod: Genericly wrapped animal
        this.entity = new CanaryAnimal(this) {

            @Override
            public String getFqName() {
                return "Generic Animal";
            }

            @Override
            public EntityType getEntityType() {
                return EntityType.GENERIC_ANIMAL;
            }

            @Override
            public EntityAnimal getHandle() {
                return (EntityAnimal) entity;
            }
        };
    }

    protected void bp() {
        if (this.d() != 0) {
            this.bp = 0;
        }

        super.bp();
    }

    public void e() {
        super.e();
        if (this.d() != 0) {
            this.bp = 0;
        }

        if (this.bp > 0) {
            --this.bp;
            String s0 = "heart";

            if (this.bp % 10 == 0) {
                double d0 = this.Z.nextGaussian() * 0.02D;
                double d1 = this.Z.nextGaussian() * 0.02D;
                double d2 = this.Z.nextGaussian() * 0.02D;

                this.o.a(s0, this.s + (double) (this.Z.nextFloat() * this.M * 2.0F) - (double) this.M, this.t + 0.5D + (double) (this.Z.nextFloat() * this.N), this.u + (double) (this.Z.nextFloat() * this.M * 2.0F) - (double) this.M, d0, d1, d2);
            }
        } else {
            this.bq = 0;
        }

    }

    protected void a(Entity entity, float f0) {
        if (entity instanceof EntityPlayer) {
            if (f0 < 3.0F) {
                double d0 = entity.s - this.s;
                double d1 = entity.u - this.u;

                this.y = (float) (Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
                this.bn = true;
            }

            EntityPlayer entityplayer = (EntityPlayer) entity;

            if (entityplayer.bF() == null || !this.c(entityplayer.bF())) {
                this.bm = null;
            }
        } else if (entity instanceof EntityAnimal) {
            EntityAnimal entityanimal = (EntityAnimal) entity;

            if (this.d() > 0 && entityanimal.d() < 0) {
                if ((double) f0 < 2.5D) {
                    this.bn = true;
                }
            } else if (this.bp > 0 && entityanimal.bp > 0) {
                if (entityanimal.bm == null) {
                    entityanimal.bm = this;
                }

                if (entityanimal.bm == this && (double) f0 < 3.5D) {
                    ++entityanimal.bp;
                    ++this.bp;
                    ++this.bq;
                    if (this.bq % 4 == 0) {
                        this.o.a("heart", this.s + (double) (this.Z.nextFloat() * this.M * 2.0F) - (double) this.M, this.t + 0.5D + (double) (this.Z.nextFloat() * this.N), this.u + (double) (this.Z.nextFloat() * this.M * 2.0F) - (double) this.M, 0.0D, 0.0D, 0.0D);
                    }

                    if (this.bq == 60) {
                        this.b((EntityAnimal) entity);
                    }
                } else {
                    this.bq = 0;
                }
            } else {
                this.bq = 0;
                this.bm = null;
            }
        }

    }

    private void b(EntityAnimal entityanimal) {
        EntityAgeable entityageable = this.a((EntityAgeable) entityanimal);

        if (entityageable != null) {
            if (this.br == null && entityanimal.cd() != null) {
                this.br = entityanimal.cd();
            }

            if (this.br != null) {
                this.br.a(StatList.x);
                if (this instanceof EntityCow) {
                    this.br.a((StatBase) AchievementList.H);
                }
            }

            this.c(6000);
            entityanimal.c(6000);
            this.bp = 0;
            this.bq = 0;
            this.bm = null;
            entityanimal.bm = null;
            entityanimal.bq = 0;
            entityanimal.bp = 0;
            entityageable.c(-24000);
            entityageable.b(this.s, this.t, this.u, this.y, this.z);

            for (int i0 = 0; i0 < 7; ++i0) {
                double d0 = this.Z.nextGaussian() * 0.02D;
                double d1 = this.Z.nextGaussian() * 0.02D;
                double d2 = this.Z.nextGaussian() * 0.02D;

                this.o.a("heart", this.s + (double) (this.Z.nextFloat() * this.M * 2.0F) - (double) this.M, this.t + 0.5D + (double) (this.Z.nextFloat() * this.N), this.u + (double) (this.Z.nextFloat() * this.M * 2.0F) - (double) this.M, d0, d1, d2);
            }

            this.o.d((Entity) entityageable);
        }

    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        } else {
            this.bo = 60;
            if (!this.bk()) {
                IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

                if (iattributeinstance.a(h) == null) {
                    iattributeinstance.a(i);
                }
            }

            this.bm = null;
            this.bp = 0;
            return super.a(damagesource, f0);
        }
    }

    public float a(int i0, int i1, int i2) {
        return this.o.a(i0, i1 - 1, i2) == Blocks.c ? 10.0F : this.o.n(i0, i1, i2) - 0.5F;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("InLove", this.bp);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.bp = nbttagcompound.f("InLove");
    }

    protected Entity bR() {
        if (this.bo > 0) {
            return null;
        } else {
            float f0 = 8.0F;
            List list;
            int i0;
            EntityAnimal entityanimal;

            if (this.bp > 0) {
                list = this.o.a(this.getClass(), this.C.b((double) f0, (double) f0, (double) f0));

                for (i0 = 0; i0 < list.size(); ++i0) {
                    entityanimal = (EntityAnimal) list.get(i0);
                    if (entityanimal != this && entityanimal.bp > 0) {
                        return entityanimal;
                    }
                }
            } else if (this.d() == 0) {
                list = this.o.a(EntityPlayer.class, this.C.b((double) f0, (double) f0, (double) f0));

                for (i0 = 0; i0 < list.size(); ++i0) {
                    EntityPlayer entityplayer = (EntityPlayer) list.get(i0);

                    if (entityplayer.bF() != null && this.c(entityplayer.bF())) {
                        return entityplayer;
                    }
                }
            } else if (this.d() > 0) {
                list = this.o.a(this.getClass(), this.C.b((double) f0, (double) f0, (double) f0));

                for (i0 = 0; i0 < list.size(); ++i0) {
                    entityanimal = (EntityAnimal) list.get(i0);
                    if (entityanimal != this && entityanimal.d() < 0) {
                        return entityanimal;
                    }
                }
            }

            return null;
        }
    }

    public boolean by() {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.C.b);
        int i2 = MathHelper.c(this.u);

        return this.o.a(i0, i1 - 1, i2) == Blocks.c && this.o.j(i0, i1, i2) > 8 && super.by();
    }

    public int q() {
        return 120;
    }

    protected boolean v() {
        return false;
    }

    protected int e(EntityPlayer entityplayer) {
        return 1 + this.o.s.nextInt(3);
    }

    public boolean c(ItemStack itemstack) {
        return itemstack.b() == Items.O;
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bm.h();

        if (itemstack != null && this.c(itemstack) && this.d() == 0 && this.bp <= 0) {
            if (!entityplayer.bE.d) {
                --itemstack.b;
                if (itemstack.b <= 0) {
                    entityplayer.bm.a(entityplayer.bm.c, (ItemStack) null);
                }
            }

            this.f(entityplayer);
            return true;
        } else {
            return super.a(entityplayer);
        }
    }

    public void f(EntityPlayer entityplayer) {
        this.bp = 600;
        this.br = entityplayer;
        this.bm = null;
        this.o.a(this, (byte) 18);
    }

    public EntityPlayer cd() {
        return this.br;
    }

    public boolean ce() {
        return this.bp > 0;
    }

    public void cf() {
        this.bp = 0;
    }

    public boolean a(EntityAnimal entityanimal) {
        return entityanimal == this ? false : (entityanimal.getClass() != this.getClass() ? false : this.ce() && entityanimal.ce());
    }
}
