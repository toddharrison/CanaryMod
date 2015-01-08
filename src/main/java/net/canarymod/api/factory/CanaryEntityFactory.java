package net.canarymod.api.factory;

import net.canarymod.Canary;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.EntityLiving;
import net.canarymod.api.entity.living.animal.EntityAnimal;
import net.canarymod.api.entity.living.animal.Horse;
import net.canarymod.api.entity.living.animal.Horse.HorseType;
import net.canarymod.api.entity.living.humanoid.EntityNonPlayableCharacter;
import net.canarymod.api.entity.living.humanoid.NonPlayableCharacter;
import net.canarymod.api.entity.living.humanoid.Villager;
import net.canarymod.api.entity.living.humanoid.Villager.Profession;
import net.canarymod.api.entity.living.monster.EntityMob;
import net.canarymod.api.entity.living.monster.Skeleton;
import net.canarymod.api.entity.throwable.EntityThrowable;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;
import net.canarymod.api.world.position.Location;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.projectile.*;

/**
 * Entity Manufacturing Factory implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryEntityFactory implements EntityFactory {

    @Override
    public Entity newEntity(String name) {
        return newEntity(name, Canary.getServer().getDefaultWorld());
    }

    @Override
    public Entity newEntity(String name, World world) {
        if (name != null && world != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return newEntity(type, world);

        }
        return null;
    }

    @Override
    public Entity newEntity(String name, Location location) {
        if (name != null && location != null) {
            Entity toRet = newEntity(name, location.getWorld());

            if (toRet != null) {
                toRet.setX(location.getX());
                toRet.setY(location.getY());
                toRet.setZ(location.getZ());
                toRet.setRotation(location.getRotation());
                toRet.setPitch(location.getPitch());
            }
            return toRet;
        }
        return null;
    }

    @Override
    public Entity newEntity(EntityType type) {
        return newEntity(type, Canary.getServer().getDefaultWorld());
    }

    @Override
    public Entity newEntity(EntityType type, World world) {
        if (type != null && world != null) {
            net.minecraft.world.World mcworld = ((CanaryWorld) world).getHandle();

            switch (type) {
                case ARMORSTAND:
                    return new EntityArmorStand(mcworld).getCanaryEntity();
                case ARROW:
                    return new EntityArrow(mcworld).getCanaryEntity();
                case BAT:
                    return new EntityBat(mcworld).getCanaryEntity();
                case BLACKSMITH: // Special Case
                    Villager blacksmith = (Villager) new EntityVillager(mcworld).getCanaryEntity();
                    blacksmith.setProfession(Profession.BLACKSMITH);
                    return blacksmith;
                case BLAZE:
                    return new EntityBlaze(mcworld).getCanaryEntity();
                case BOAT:
                    return new EntityBoat(mcworld).getCanaryEntity();
                case BUTCHER: // Special Case
                    Villager butcher = (Villager) new EntityVillager(mcworld).getCanaryEntity();
                    butcher.setProfession(Profession.BUTCHER);
                    return butcher;
                case CAVESPIDER:
                    return new EntityCaveSpider(mcworld).getCanaryEntity();
                case CHESTMINECART:
                    return new EntityMinecartChest(mcworld).getCanaryEntity();
                case CHICKEN:
                    return new EntityChicken(mcworld).getCanaryEntity();
                case CHICKENEGG:
                    return new EntityEgg(mcworld).getCanaryEntity();
                case COW:
                    return new EntityCow(mcworld).getCanaryEntity();
                case CREEPER:
                    return new EntityCreeper(mcworld).getCanaryEntity();
                case DONKEY: // Special Case
                    Horse donkey = (Horse) new EntityHorse(mcworld).getCanaryEntity();
                    donkey.setType(HorseType.DONKEY);
                    return donkey;
                case EMPTYMINECART:
                    return new EntityMinecartEmpty(mcworld).getCanaryEntity();
                case ENDERCRYSTAL:
                    return new EntityEnderCrystal(mcworld).getCanaryEntity();
                case ENDERDRAGON:
                    return new EntityDragon(mcworld).getCanaryEntity();
                case ENDEREYE:
                    return new EntityEnderEye(mcworld).getCanaryEntity();
                case ENDERMAN:
                    return new EntityEnderman(mcworld).getCanaryEntity();
                case ENDERPEARL:
                    return new EntityEnderPearl(mcworld).getCanaryEntity();
                case ENTITYITEM:
                    return new EntityItem(mcworld).getCanaryEntity();
                case ENTITYPOTION:
                    return new EntityPotion(mcworld).getCanaryEntity();
                case FALLINGBLOCK:
                    return new EntityFallingBlock(mcworld).getCanaryEntity();
                case FARMER: // Special Case
                    Villager farmer = (Villager) new EntityVillager(mcworld).getCanaryEntity();
                    farmer.setProfession(Profession.FARMER);
                    return farmer;
                case FIREWORKROCKET:
                    return new EntityFireworkRocket(mcworld).getCanaryEntity();
                case FURNACEMINECART:
                    return new EntityMinecartFurnace(mcworld).getCanaryEntity();
                case GHAST:
                    return new EntityGhast(mcworld).getCanaryEntity();
                case GIANTZOMBIE:
                    return new EntityGiantZombie(mcworld).getCanaryEntity();
                case HOPPERMINECART:
                    return new EntityMinecartHopper(mcworld).getCanaryEntity();
                case HORSE:
                    return new EntityHorse(mcworld).getCanaryEntity();
                case IRONGOLEM:
                    return new EntityIronGolem(mcworld).getCanaryEntity();
                case ITEMFRAME:
                    return new EntityItemFrame(mcworld).getCanaryEntity();
                case LARGEFIREBALL:
                    return new EntityLargeFireball(mcworld).getCanaryEntity();
                case LEASHKNOT:
                    return new EntityLeashKnot(mcworld).getCanaryEntity();
                case LIBRARIAN: // Special Case
                    Villager librarian = (Villager) new EntityVillager(mcworld).getCanaryEntity();
                    librarian.setProfession(Profession.LIBRARIAN);
                    return librarian;
                case LIGHTNINGBOLT: // There is a chance that LightningBolt isnt quite right
                    return new EntityLightningBolt(mcworld, 0, 0, 0).getCanaryEntity();
                case MAGMACUBE:
                    return new EntityMagmaCube(mcworld).getCanaryEntity();
                case MOBSPAWNERMINECART:
                    return new EntityMinecartMobSpawner(mcworld).getCanaryEntity();
                case MOOSHROOM:
                    return new EntityMooshroom(mcworld).getCanaryEntity();
                case MULE: // Special Case
                    Horse mule = (Horse) new EntityHorse(mcworld).getCanaryEntity();
                    mule.setType(HorseType.MULE);
                    return mule;
                case OCELOT:
                    return new EntityOcelot(mcworld).getCanaryEntity();
                case PAINTING:
                    return new EntityPainting(mcworld).getCanaryEntity();
                case PIG:
                    return new EntityPig(mcworld).getCanaryEntity();
                case PIGZOMBIE:
                    return new EntityPigZombie(mcworld).getCanaryEntity();
                case POTION:
                    return new EntityPotion(mcworld).getCanaryEntity();
                case PRIEST: // Special Case
                    Villager priest = (Villager) new EntityVillager(mcworld).getCanaryEntity();
                    priest.setProfession(Profession.PRIEST);
                    return priest;
                case RABBIT:
                    return new EntityRabbit(mcworld).getCanaryEntity();
                case SHEEP:
                    return new EntitySheep(mcworld).getCanaryEntity();
                case SILVERFISH:
                    return new EntitySilverfish(mcworld).getCanaryEntity();
                case SKELETON:
                    return new EntitySkeleton(mcworld).getCanaryEntity();
                case SKELETONHORSE: // Special Case
                    Horse skeletonhorse = (Horse) new EntityHorse(mcworld).getCanaryEntity();
                    skeletonhorse.setType(HorseType.SKELETON);
                    return skeletonhorse;
                case SLIME:
                    return new EntitySlime(mcworld).getCanaryEntity();
                case SMALLFIREBALL:
                    return new EntitySmallFireball(mcworld).getCanaryEntity();
                case SNOWBALL:
                    return new EntitySnowball(mcworld).getCanaryEntity();
                case SNOWMAN:
                    return new EntitySnowman(mcworld).getCanaryEntity();
                case SPIDER:
                    return new EntitySpider(mcworld).getCanaryEntity();
                case SQUID:
                    return new EntitySquid(mcworld).getCanaryEntity();
                case TNTMINECART:
                    return new EntityMinecartTNT(mcworld).getCanaryEntity();
                case TNTPRIMED:
                    return new EntityTNTPrimed(mcworld).getCanaryEntity();
                case VILLAGER:
                    return new EntityVillager(mcworld).getCanaryEntity();
                case WITCH:
                    return new EntityWitch(mcworld).getCanaryEntity();
                case WITHER:
                    return new EntityWither(mcworld).getCanaryEntity();
                case WITHERSKELETON: // Special Case
                    Skeleton witherskeleton = (Skeleton) new EntitySkeleton(mcworld).getCanaryEntity();
                    witherskeleton.setIsWitherSkeleton(true);
                    return witherskeleton;
                case WITHERSKULL:
                    return new EntityWitherSkull(mcworld).getCanaryEntity();
                case WOLF:
                    return new EntityWolf(mcworld).getCanaryEntity();
                case XPBOTTLE:
                    return new EntityExpBottle(mcworld).getCanaryEntity();
                case XPORB:
                    return new EntityXPOrb(mcworld).getCanaryEntity();
                case ZOMBIE:
                    return new EntityZombie(mcworld).getCanaryEntity();
                case ZOMBIEHORSE: // Special Case
                    Horse zombiehorse = (Horse) new EntityHorse(mcworld).getCanaryEntity();
                    zombiehorse.setType(HorseType.ZOMBIE);
                    return zombiehorse;
                default:
                    break;
            }
        }
        return null;
    }

    @Override
    public Entity newEntity(EntityType type, Location location) {
        if (type != null && location != null) {
            Entity toRet = newEntity(type, location.getWorld());

            if (toRet != null) {
                toRet.setX(location.getX());
                toRet.setY(location.getY());
                toRet.setZ(location.getZ());
                toRet.setRotation(location.getRotation());
                toRet.setPitch(location.getPitch());
            }
            return toRet;
        }
        return null;
    }

    @Override
    public EntityThrowable newThrowable(String name) {
        return newThrowable(name, Canary.getServer().getDefaultWorld());
    }

    @Override
    public EntityThrowable newThrowable(String name, World world) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isThrowable() ? (EntityThrowable) newEntity(type, world) : null;
        }
        return null;
    }

    @Override
    public EntityThrowable newThrowable(String name, Location location) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isThrowable() ? (EntityThrowable) newEntity(type, location) : null;
        }
        return null;
    }

    @Override
    public EntityThrowable newThrowable(EntityType type) {
        return newThrowable(type, Canary.getServer().getDefaultWorld());
    }

    @Override
    public EntityThrowable newThrowable(EntityType type, World world) {
        if (type != null && type.isThrowable()) {
            return (EntityThrowable) newEntity(type, world);
        }
        return null;
    }

    @Override
    public EntityThrowable newThrowable(EntityType type, Location location) {
        if (type != null && type.isThrowable()) {
            return (EntityThrowable) newEntity(type, location);
        }
        return null;
    }

    @Override
    public Vehicle newVehicle(String name) {
        return newVehicle(name, Canary.getServer().getDefaultWorld());
    }

    @Override
    public Vehicle newVehicle(String name, World world) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isVehicle() ? (Vehicle) newEntity(type, world) : null;
        }
        return null;
    }

    @Override
    public Vehicle newVehicle(String name, Location location) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isVehicle() ? (Vehicle) newEntity(type, location) : null;
        }
        return null;
    }

    @Override
    public Vehicle newVehicle(EntityType type) {
        return newVehicle(type, Canary.getServer().getDefaultWorld());
    }

    @Override
    public Vehicle newVehicle(EntityType type, World world) {
        if (type != null && type.isVehicle()) {
            return (Vehicle) newEntity(type, world);
        }
        return null;
    }

    @Override
    public Vehicle newVehicle(EntityType type, Location location) {
        if (type != null && type.isVehicle()) {
            return (Vehicle) newEntity(type, location);
        }
        return null;
    }

    @Override
    public EntityLiving newEntityLiving(String name) {
        return newEntityLiving(name, Canary.getServer().getDefaultWorld());
    }

    @Override
    public EntityLiving newEntityLiving(String name, World world) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isLiving() ? (EntityLiving) newEntity(type, world) : null;
        }
        return null;
    }

    @Override
    public EntityLiving newEntityLiving(String name, Location location) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isLiving() ? (EntityLiving) newEntity(type, location) : null;
        }
        return null;
    }

    @Override
    public EntityLiving newEntityLiving(EntityType type) {
        return newEntityLiving(type, Canary.getServer().getDefaultWorld());
    }

    @Override
    public EntityLiving newEntityLiving(EntityType type, World world) {
        if (type != null && type.isLiving()) {
            return (EntityLiving) newEntity(type, world);
        }
        return null;
    }

    @Override
    public EntityLiving newEntityLiving(EntityType type, Location location) {
        if (type != null && type.isLiving()) {
            return (EntityLiving) newEntity(type, location);
        }
        return null;
    }

    @Override
    public EntityAnimal newEntityAnimal(String name) {
        return newEntityAnimal(name, Canary.getServer().getDefaultWorld());
    }

    @Override
    public EntityAnimal newEntityAnimal(String name, World world) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isAnimal() ? (EntityAnimal) newEntity(type, world) : null;
        }
        return null;
    }

    @Override
    public EntityAnimal newEntityAnimal(String name, Location location) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isAnimal() ? (EntityAnimal) newEntity(type, location) : null;
        }
        return null;
    }

    @Override
    public EntityAnimal newEntityAnimal(EntityType type) {
        return newEntityAnimal(type, Canary.getServer().getDefaultWorld());
    }

    @Override
    public EntityAnimal newEntityAnimal(EntityType type, World world) {
        if (type != null && type.isAnimal()) {
            return (EntityAnimal) newEntity(type, world);
        }
        return null;
    }

    @Override
    public EntityAnimal newEntityAnimal(EntityType type, Location location) {
        if (type != null && type.isAnimal()) {
            return (EntityAnimal) newEntity(type, location);
        }
        return null;
    }

    @Override
    public EntityMob newEntityMob(String name) {
        return newEntityMob(name, Canary.getServer().getDefaultWorld());
    }

    @Override
    public EntityMob newEntityMob(String name, World world) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isMob() ? (EntityMob) newEntity(type, world) : null;
        }
        return null;
    }

    @Override
    public EntityMob newEntityMob(String name, Location location) {
        if (name != null) {
            EntityType type;

            try {
                type = EntityType.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException iaex) {
                return null;
            }
            return type.isMob() ? (EntityMob) newEntity(type, location) : null;
        }
        return null;
    }

    @Override
    public EntityMob newEntityMob(EntityType type) {
        return newEntityMob(type, Canary.getServer().getDefaultWorld());
    }

    @Override
    public EntityMob newEntityMob(EntityType type, World world) {
        if (type != null && type.isMob()) {
            return (EntityMob) newEntity(type, world);
        }
        return null;
    }

    @Override
    public EntityMob newEntityMob(EntityType type, Location location) {
        if (type != null && type.isMob()) {
            return (EntityMob) newEntity(type, location);
        }
        return null;
    }

    @Override
    public NonPlayableCharacter newNPC(String name, Location location) {
        if (name != null && location != null) {
            return new EntityNonPlayableCharacter(name, location).getNPC();
        }
        return null;
    }

}
