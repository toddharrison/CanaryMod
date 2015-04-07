package net.canarymod.api.factory;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import net.canarymod.api.ai.AIArrowAttack;
import net.canarymod.api.ai.AIAttackOnCollide;
import net.canarymod.api.ai.AIAvoidEntity;
import net.canarymod.api.ai.AIBeg;
import net.canarymod.api.ai.AIBreakDoor;
import net.canarymod.api.ai.AIControlledByPlayer;
import net.canarymod.api.ai.AICreeperSwell;
import net.canarymod.api.ai.AIDefendVillage;
import net.canarymod.api.ai.AIEatGrass;
import net.canarymod.api.ai.AIFindEntityNearest;
import net.canarymod.api.ai.AIFindEntityNearestPlayer;
import net.canarymod.api.ai.AIFleeSun;
import net.canarymod.api.ai.AIFollowGolem;
import net.canarymod.api.ai.AIFollowOwner;
import net.canarymod.api.ai.AIFollowParent;
import net.canarymod.api.ai.AIHarvestFarmland;
import net.canarymod.api.ai.AIHurtByTarget;
import net.canarymod.api.ai.AILeapAtTarget;
import net.canarymod.api.ai.AILookAtTradePlayer;
import net.canarymod.api.ai.AILookAtVillager;
import net.canarymod.api.ai.AILookIdle;
import net.canarymod.api.ai.AIMate;
import net.canarymod.api.ai.AIMoveIndoors;
import net.canarymod.api.ai.AIMoveThroughVillage;
import net.canarymod.api.ai.AIMoveTowardsRestriction;
import net.canarymod.api.ai.AIMoveTowardsTarget;
import net.canarymod.api.ai.AINearestAttackableTarget;
import net.canarymod.api.entity.living.CanaryEntityLiving;
import net.canarymod.api.entity.living.CanaryIronGolem;
import net.canarymod.api.entity.living.EntityLiving;
import net.canarymod.api.entity.living.IronGolem;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.living.animal.CanaryWolf;
import net.canarymod.api.entity.living.animal.EntityAnimal;
import net.canarymod.api.entity.living.animal.Tameable;
import net.canarymod.api.entity.living.animal.Wolf;
import net.canarymod.api.entity.living.humanoid.CanaryVillager;
import net.canarymod.api.entity.living.humanoid.Villager;
import net.canarymod.api.entity.living.monster.CanaryCreeper;
import net.canarymod.api.entity.living.monster.CanaryEntityMob;
import net.canarymod.api.entity.living.monster.Creeper;
import net.canarymod.api.entity.living.monster.EntityMob;
import net.canarymod.api.entity.living.monster.RangedAttackMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityTameable;

import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author Aaron
 */
public class CanaryAIFactory implements AIFactory {
    //return (AI) new EntityAI(((Canary)).getHandle()).getCanaryAIBase();
    
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
                if (method != null) break;
            } catch (Exception ex) {
            }
        }
        return method == null ? null : method.getReturnType();
    }
    
    private Class[] getNMSClasses(Class... clazz) {
        List<Class> classes = Lists.newArrayList();
        for (Class c : clazz) {
            classes.add(getNMSClass(c));
        }
        return classes.toArray(new Class[classes.size()]);
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

    @Override
    public AIHarvestFarmland newAIHarvestFarmland(Villager villager, double speed) {
        return (AIHarvestFarmland) new EntityAIHarvestFarmland(((CanaryVillager) villager).getHandle(), speed).getCanaryAIBase();
    }

    @Override
    public AIHurtByTarget newAIHurtByTarget(EntityMob entity, boolean callForHelp, Class<? extends net.canarymod.api.entity.Entity>... targets) {
        return (AIHurtByTarget) new EntityAIHurtByTarget((EntityCreature) ((CanaryEntityMob)entity).getHandle(), callForHelp, getNMSClasses(targets)).getCanaryAIBase();
    }

    @Override
    public AILeapAtTarget newAILeapAtTarget(EntityLiving entity, float leapMotionY) {
        return (AILeapAtTarget) new EntityAILeapAtTarget(((CanaryEntityLiving)entity).getHandle(), leapMotionY).getCanaryAIBase();
    }

    @Override
    public AILookAtTradePlayer newAILookAtTradePlayer(Villager villager) {
        return (AILookAtTradePlayer) new EntityAILookAtTradePlayer(((CanaryVillager)villager).getHandle()).getCanaryAIBase();
    }

    @Override
    public AILookAtVillager newAILookAtVillager(IronGolem golem) {
        return (AILookAtVillager) new EntityAILookAtVillager(((CanaryIronGolem)golem).getHandle()).getCanaryAIBase();
    }

    @Override
    public AILookIdle newAILookIdle(EntityLiving entity) {
        return (AILookIdle) new EntityAILookIdle(((CanaryEntityLiving)entity).getHandle()).getCanaryAIBase();
    }

    @Override
    public AIMate newAIMate(EntityAnimal animal, double speed) {
        return (AIMate) new EntityAIMate((net.minecraft.entity.passive.EntityAnimal) ((CanaryEntityLiving)animal).getHandle(), speed).getCanaryAIBase();
    }

    @Override
    public AIMoveIndoors newAIMoveIndoors(EntityMob entity) {
        return (AIMoveIndoors) new EntityAIMoveIndoors((EntityCreature) ((CanaryEntityMob)entity).getHandle()).getCanaryAIBase();
    }

    @Override
    public AIMoveThroughVillage newAIMoveThroughVillage(EntityMob entity, double speed, boolean isNoctournal) {
        return (AIMoveThroughVillage) new EntityAIMoveThroughVillage((EntityCreature) ((CanaryEntityMob)entity).getHandle(), speed, isNoctournal).getCanaryAIBase();
    }

    @Override
    public AIMoveTowardsRestriction newAIMoveTowardsRestriction(EntityMob entity, double speed) {
        return (AIMoveTowardsRestriction) new EntityAIMoveTowardsRestriction((EntityCreature) ((CanaryEntityMob)entity).getHandle(), speed).getCanaryAIBase();
    }

    @Override
    public AIMoveTowardsTarget newAIMoveTowardsTarget(EntityMob entity, double speed, float maxDistance) {
        return (AIMoveTowardsTarget) new EntityAIMoveTowardsTarget((EntityCreature) ((CanaryEntityMob)entity).getHandle(), speed, maxDistance).getCanaryAIBase();
    }

    @Override
    public AINearestAttackableTarget newAINearestAttackableTarget(EntityMob entity, Class<? extends net.canarymod.api.entity.Entity> target, int targetChanve, boolean shouldCheckSight, boolean nearbyOnly) {
        Predicate p = new Predicate() {

            @Override
            public boolean apply(Object t) {
                return true;
            }
        };
        return (AINearestAttackableTarget) new EntityAINearestAttackableTarget((EntityCreature) ((CanaryEntityMob)entity).getHandle(), this.getNMSClass(target), targetChanve, shouldCheckSight, nearbyOnly, p).getCanaryAIBase();
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