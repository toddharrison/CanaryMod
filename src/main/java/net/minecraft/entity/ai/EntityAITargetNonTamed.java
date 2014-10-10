package net.minecraft.entity.ai;


import com.google.common.base.Predicate;
import net.canarymod.api.ai.CanaryAITargetNonTamed;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityTameable;


public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget {

    private EntityTameable g;
   
    public EntityAITargetNonTamed(EntityTameable entitytameable, Class oclass0, boolean flag0, Predicate predicate) {
        super(entitytameable, oclass0, 10, flag0, false, predicate);
        this.g = entitytameable;
        this.canaryAI = new CanaryAITargetNonTamed(this); //CanaryMod: set our variable
    }

    public boolean a() {
        return !this.g.cj() && super.a();
    }
}
