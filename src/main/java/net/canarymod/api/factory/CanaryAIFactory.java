package net.canarymod.api.factory;

import com.google.common.base.Predicate;
import java.lang.reflect.Method;
import net.canarymod.api.ai.*;
import net.canarymod.api.entity.living.*;
import net.canarymod.api.entity.living.animal.CanaryWolf;
import net.canarymod.api.entity.living.animal.EntityAnimal;
import net.canarymod.api.entity.living.animal.Tameable;
import net.canarymod.api.entity.living.animal.Wolf;
import net.canarymod.api.entity.living.humanoid.CanaryVillager;
import net.canarymod.api.entity.living.humanoid.Villager;
import net.canarymod.api.entity.living.monster.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;

/**
 *
 * @author Aaron
 */
public class CanaryAIFactory implements AIFactory {
    //return () new EntityAI(((Canary)).getHandle()).getCanaryAIBase();
    
    /**
     * Helper method to get the nms class that corellates to our cms class
     * @param clazz the cms class to test for
     * @return an nms class
     */
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

    @Override
    public AICreeperSwell newAICreeperSwell(Creeper creeper) {
        return (AICreeperSwell) new EntityAICreeperSwell(((CanaryCreeper)creeper).getHandle()).getCanaryAIBase();
    }

    @Override
    public AIDefendVillage newAIDefendVillage(IronGolem ironGolem) {
        return (AIDefendVillage) new EntityAIDefendVillage(((CanaryIronGolem)ironGolem).getHandle()).getCanaryAIBase();
    }

    @Override
    public AIEatGrass newAIEatGrass(EntityLiving entity) {
        return (AIEatGrass) new EntityAIEatGrass(((CanaryEntityLiving)entity).getHandle()).getCanaryAIBase();
    }

    @Override
    public AIFindEntityNearest newAIFindEntityNearest(EntityLiving entityLiving, Class<? extends net.canarymod.api.entity.Entity> entityClass) {
        return (AIFindEntityNearest) new EntityAIFindEntityNearest(((CanaryEntityLiving)entityLiving).getHandle(), this.getNMSClass(entityClass)).getCanaryAIBase();
    }

    @Override
    public AIFindEntityNearestPlayer newAIFindEntityNearestPlayer(EntityLiving entityLiving) {
        return (AIFindEntityNearestPlayer) new EntityAIFindEntityNearestPlayer(((CanaryEntityLiving)entityLiving).getHandle()).getCanaryAIBase();
    }

    @Override
    public AIFleeSun newAIFleeSun(EntityMob mob, double speed) {
        return (AIFleeSun) new EntityAIFleeSun((EntityCreature)((CanaryEntityMob)mob).getHandle(), speed).getCanaryAIBase();
    }

    @Override
    public AIFollowGolem newAIFollowGolem(Villager villager) {
        return (AIFollowGolem) new EntityAIFollowGolem(((CanaryVillager)villager).getHandle()).getCanaryAIBase();
    }

    @Override
    public AIFollowOwner newAIFollowOwner(Tameable entity, double speed, float minDistance, float maxDistance) {
        return (AIFollowOwner) new EntityAIFollowOwner((EntityTameable)((CanaryEntityLiving)entity).getHandle(), speed, minDistance, maxDistance).getCanaryAIBase();
    }

    @Override
    public AIFollowParent newAIFollowParent(EntityAnimal animal, double speed) {
        return (AIFollowParent) new EntityAIFollowParent((net.minecraft.entity.passive.EntityAnimal)((CanaryEntityLiving)animal).getHandle(), speed).getCanaryAIBase();
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