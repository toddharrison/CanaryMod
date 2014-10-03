package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryWitch;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EntityWitch extends EntityMob implements IRangedAttackMob {

    private static final UUID b = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier c = (new AttributeModifier(b, "Drinking speed penalty", -0.25D, 0)).a(false);
    private static final Item[] bk = new Item[] { Items.aT, Items.aY, Items.aC, Items.bB, Items.bA, Items.H, Items.y, Items.y};
    private int bl;
   
    public EntityWitch(World world) {
        super(world);
        this.a(0.6F, 1.95F);
        this.i.a(1, new EntityAISwimming(this));
        this.i.a(2, new EntityAIArrowAttack(this, 1.0D, 60, 10.0F));
        this.i.a(2, new EntityAIWander(this, 1.0D));
        this.i.a(2, this.a);
        this.i.a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(3, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.entity = new CanaryWitch(this); // CanaryMod: Wrap Entity
    }

    protected void h() {
        super.h();
        this.H().a(21, Byte.valueOf((byte) 0));
    }

    protected String z() {
        return null;
    }

    protected String bn() {
        return null;
    }

    protected String bo() {
        return null;
    }

    public void a(boolean flag0) {
        this.H().b(21, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

    public boolean n() {
        return this.H().a(21) == 1;
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(26.0D);
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    public void m() {
        if (!this.o.D) {
            if (this.n()) {
                if (this.bl-- <= 0) {
                    this.a(false);
                    ItemStack itemstack = this.bz();

                    this.c(0, (ItemStack) null);
                    if (itemstack != null && itemstack.b() == Items.bz) {
                        List list = Items.bz.h(itemstack);

                        if (list != null) {
                            Iterator iterator = list.iterator();

                            while (iterator.hasNext()) {
                                PotionEffect potioneffect = (PotionEffect) iterator.next();

                                this.c(new PotionEffect(potioneffect));
                            }
                        }
                    }

                    this.a(SharedMonsterAttributes.d).c(c);
                }
            } else {
                short short1 = -1;

                if (this.V.nextFloat() < 0.15F && this.a(Material.h) && !this.a(Potion.o)) {
                    short1 = 8237;
                } else if (this.V.nextFloat() < 0.15F && this.au() && !this.a(Potion.n)) {
                    short1 = 16307;
                } else if (this.V.nextFloat() < 0.05F && this.bm() < this.bt()) {
                    short1 = 16341;
                } else if (this.V.nextFloat() < 0.25F && this.u() != null && !this.a(Potion.c) && this.u().h(this) > 121.0D) {
                    short1 = 16274;
                } else if (this.V.nextFloat() < 0.25F && this.u() != null && !this.a(Potion.c) && this.u().h(this) > 121.0D) {
                    short1 = 16274;
                }

                if (short1 > -1) {
                    this.c(0, new ItemStack(Items.bz, 1, short1));
                    this.bl = this.bz().l();
                    this.a(true);
                    IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

                    iattributeinstance.c(c);
                    iattributeinstance.b(c);
                }
            }

            if (this.V.nextFloat() < 7.5E-4F) {
                this.o.a((Entity) this, (byte) 15);
            }
        }

        super.m();
    }

    protected float c(DamageSource damagesource, float f0) {
        f0 = super.c(damagesource, f0);
        if (damagesource.j() == this) {
            f0 = 0.0F;
        }

        if (damagesource.s()) {
            f0 = (float) ((double) f0 * 0.15D);
        }

        return f0;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(3) + 1;

        for (int i2 = 0; i2 < i1; ++i2) {
            int i3 = this.V.nextInt(3);
            Item item = bk[this.V.nextInt(bk.length)];

            if (i0 > 0) {
                i3 += this.V.nextInt(i0 + 1);
            }

            for (int i4 = 0; i4 < i3; ++i4) {
                this.a(item, 1);
            }
        }

    }

    public void a(EntityLivingBase entitylivingbase, float f0) {
        if (!this.n()) {
            EntityPotion entitypotion = new EntityPotion(this.o, this, 32732);
            double d0 = entitylivingbase.t + (double) entitylivingbase.aR() - 1.100000023841858D;

            entitypotion.z -= -20.0F;
            double d1 = entitylivingbase.s + entitylivingbase.v - this.s;
            double d2 = d0 - this.t;
            double d3 = entitylivingbase.u + entitylivingbase.x - this.u;
            float f1 = MathHelper.a(d1 * d1 + d3 * d3);

            if (f1 >= 8.0F && !entitylivingbase.a(Potion.d)) {
                entitypotion.a(32698);
            } else if (entitylivingbase.bm() >= 8.0F && !entitylivingbase.a(Potion.u)) {
                entitypotion.a(32660);
            } else if (f1 <= 3.0F && !entitylivingbase.a(Potion.t) && this.V.nextFloat() < 0.25F) {
                entitypotion.a(32696);
            }

            entitypotion.c(d1, d2 + (double) (f1 * 0.2F), d3, 0.75F, 8.0F);
            this.o.d((Entity) entitypotion);
        }
    }

    public float aR() {
        return 1.62F;
    }

}
