package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryCaveSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityCaveSpider extends EntitySpider {

    public EntityCaveSpider(World world) {
        super(world);
        this.a(0.7F, 0.5F);
        this.entity = new CanaryCaveSpider(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(12.0D);
    }

    public boolean m(Entity entity) {
        if (super.m(entity)) {
            if (entity instanceof EntityLivingBase) {
                byte b0 = 0;

                if (this.p.r == EnumDifficulty.NORMAL) {
                    b0 = 7;
                }
                else if (this.p.r == EnumDifficulty.HARD) {
                    b0 = 15;
                }

                if (b0 > 0) {
                    ((EntityLivingBase) entity).c(new PotionEffect(Potion.u.H, b0 * 20, 0));
                }
            }

            return true;
        }
        else {
            return false;
        }
    }

    public EntityLivingData a(EntityLivingData entitylivingdata) {
        return entitylivingdata;
    }
}
