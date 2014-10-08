package net.canarymod.api.entity.living.humanoid;

import net.canarymod.api.CanaryVillagerTrade;
import net.canarymod.api.VillagerTrade;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.CanaryEntityLiving;
import net.canarymod.api.entity.living.CanaryLivingBase;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.world.CanaryVillage;
import net.canarymod.api.world.Village;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

/**
 * Villager wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryVillager extends CanaryEntityLiving implements Villager {

    /**
     * Constructs a new wrapper for EntityVillager
     *
     * @param entity the EntityVillager to wrap
     */
    public CanaryVillager(EntityVillager entity) {
        super(entity);
    }

    public EntityType getEntityType() {
        return EntityType.VILLAGER;
    }

    @Override
    public String getFqName() {
        return "Villager";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Profession getProfession() {
        return Profession.fromId(getHandle().cj());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProfession(Profession profession) {
        getHandle().r(profession.ordinal());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMating() {
        return getHandle().ck();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMating(boolean isMating) {
        getHandle().j(isMating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaying() {
        return getHandle().cl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlaying(boolean playing) {
        getHandle().m(playing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRevengeTarget(LivingBase targetEntity) {
        getHandle().b(((CanaryLivingBase) targetEntity).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCustomer() {
        EntityPlayer customerentity = getHandle().u_();
        return customerentity == null ? null : (Player) customerentity.getCanaryEntity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasCustomer() {
        return getHandle().ck();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCustomer(Player player) {
        getHandle().a_(((CanaryPlayer) player).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Village getVillage() {
        return getHandle().bk.getCanaryVillage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVillage(Village village) {
        getHandle().bk = ((CanaryVillage) village).getHandle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGrowingAge() {
        return getHandle().l();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGrowingAge(int age) {
        getHandle().b(age);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VillagerTrade[] getTrades() {
        MerchantRecipeList list = getHandle().b_((EntityPlayer) null);
        VillagerTrade[] rt = new VillagerTrade[list.size()];

        for (int i = 0; i < rt.length; i++) {
            rt[i] = new CanaryVillagerTrade((MerchantRecipe) list.get(i));
        }
        return rt;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addTrade(VillagerTrade trade) {
        getHandle().b_((EntityPlayer) null).add(((CanaryVillagerTrade) trade).getRecipe());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTrade(int index) {
        getHandle().b_((EntityPlayer) null).remove(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityVillager getHandle() {
        return (EntityVillager) entity;
    }
}
