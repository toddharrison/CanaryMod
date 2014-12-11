package net.canarymod.api.entity.living.animal;

import net.canarymod.ToolBox;
import net.canarymod.api.entity.living.LivingBase;
import net.minecraft.entity.passive.EntityTameable;

import java.util.UUID;

/**
 * Tameable wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryTameable extends CanaryAgeableAnimal implements Tameable {

    /**
     * Constructs a new wrapper for EntityTameable
     *
     * @param entity the EntityTameable to wrap
     */
    public CanaryTameable(EntityTameable entity) {
        super(entity);
    }

    @Override
    public LivingBase getOwner() {
        return getHandle().cm() == null ? null : (LivingBase)getHandle().cm().getCanaryEntity();
    }

    @Deprecated
    @Override
    public String getOwnerName() {
        return getHandle().b();
    }

    @Override
    public String getOwnerID() {
        return getHandle().b();
    }

    @Override
    public void setOwner(LivingBase entity) {
        setOwner(entity.getUUID().toString());
    }

    @Override
    public void setOwner(String name) {
        if (name == null || name.trim().isEmpty()) {
            getHandle().b("");
            setTamed(false);
        }
        else if (ToolBox.isUUID(name)) {
            getHandle().b(name);
            setTamed(true);
        }
        else {
            UUID uuid = ToolBox.uuidFromUsername(name);
            if (uuid != null) {
                getHandle().b(uuid.toString());
                setTamed(true);
            }
        }
    }

    @Override
    public boolean isTamed() {
        return getHandle().cj();
    }

    @Override
    public void setTamed(boolean tamed) {
        getHandle().m(tamed);
    }

    @Override
    public boolean isSitting() {
        return getHandle().cl();
    }

    @Override
    public void setSitting(boolean sitting) {
        getHandle().n(sitting);
    }

    @Override
    public EntityTameable getHandle() {
        return (EntityTameable) entity;
    }
}
