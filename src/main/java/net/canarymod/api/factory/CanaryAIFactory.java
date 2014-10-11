package net.canarymod.api.factory;

import com.google.common.base.Predicate;
import java.lang.reflect.Method;
import net.canarymod.api.ai.*;
import net.canarymod.api.entity.living.CanaryEntityLiving;
import net.canarymod.api.entity.living.EntityLiving;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.living.animal.CanaryWolf;
import net.canarymod.api.entity.living.animal.Wolf;
import net.canarymod.api.entity.living.monster.CanaryEntityMob;
import net.canarymod.api.entity.living.monster.EntityMob;
import net.canarymod.api.entity.living.monster.RangedAttackMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityWolf;

/**
 *
 * @author Aaron
 */
public class CanaryAIFactory implements AIFactory {
    
    private Class getNMSClass(Class clazz) {
        Class canaryClazz;
        Method method = null;
        String[] dirs = {"",".effect",".hanging",".living",".living.animal",".living.humanoid",".living.monster",".living.throwable",".living.vehicle"};
        StringBuilder sb;
        for (String s : dirs) {
            sb = new StringBuilder();
            sb.append("net.canarymod.api.entity").append(s).append(".Canary").append(clazz.getSimpleName());
            try {
                canaryClazz = Class.forName(sb.toString() , true, this.getClass().getClassLoader());
                method = canaryClazz.getMethod("getHandle");
                break;
            } catch (Exception ex) {
            }
        }
        return method == null ? null : method.getReturnType();
    }

    @Override
    public AIArrowAttack newAIArrowAttack(RangedAttackMob mob, double moveSpeed, int attackTimeModifier, int maxRangedAttackTime, int maxAttackDistance) {
        return (AIArrowAttack) new EntityAIArrowAttack((IRangedAttackMob)((CanaryEntityMob)mob).getHandle(), moveSpeed, attackTimeModifier, maxRangedAttackTime, maxAttackDistance).getCanaryAIBase();
    }

    @Override
    public AIAttackOnCollide newAIAttackOnCollide(EntityMob creature, Class<? extends LivingBase> targetClass, double moveSpeed, boolean persistant) {
        return (AIAttackOnCollide) new EntityAIAttackOnCollide((EntityCreature)((CanaryEntityMob)creature).getHandle(), this.getNMSClass(targetClass), moveSpeed, persistant).getCanaryAIBase();
    }

    @Override
    public AIAvoidEntity newAIAvoidEntity(EntityMob mob, Predicate predicate, float radius, double farSpeed, double nearSpeed) {
        Predicate wrappedPredicate = new WrappedPredicate(predicate);
        return (AIAvoidEntity) new EntityAIAvoidEntity((EntityCreature) ((CanaryEntityMob)mob).getHandle(), wrappedPredicate, radius, farSpeed, nearSpeed).getCanaryAIBase();
    }

    @Override
    public AIBeg newAIBeg(Wolf wolf, float minBegDistance) {
        return (AIBeg) new EntityAIBeg(((CanaryWolf)wolf).getHandle(), minBegDistance).getCanaryAIBase();
    }

    @Override
    public AIBreakDoor newAIBreakDoor(EntityLiving entity) {
        return (AIBreakDoor) new EntityAIBreakDoor(((CanaryEntityLiving)entity).getHandle()).getCanaryAIBase();
    }

    @Override
    public AIControlledByPlayer newAIControlledByPlayer(EntityLiving entity, float speed) {
        return (AIControlledByPlayer) new EntityAIControlledByPlayer(((CanaryEntityLiving)entity).getHandle(), speed).getCanaryAIBase();
    }
    
    private class WrappedPredicate implements Predicate {
        private Predicate predicate;
        public WrappedPredicate(Predicate predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean apply(Object input) {
            return predicate.apply(((Entity)input).getCanaryEntity());
        }
    }
}
