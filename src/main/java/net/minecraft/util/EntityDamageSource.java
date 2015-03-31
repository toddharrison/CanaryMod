package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityDamageSource extends DamageSource {

    protected Entity q;
    private boolean r = false;
    private boolean critical = false; // CanaryMod: pass critical

    public EntityDamageSource(String s0, Entity entity) {
        super(s0);
        this.q = entity;
    }

    // CanaryMod: Add constructor for tracking critical hits
    public EntityDamageSource(String s0, EntityPlayer entity, boolean critical) {
        super(s0);
        this.q = entity;
        this.critical = critical; // CanaryMod: track critical
    }

    public EntityDamageSource v() {
        this.r = true;
        return this;
    }

    public boolean w() {
        return this.r;
    }

    public Entity j() {
        return this.q;
    }

    public IChatComponent b(EntityLivingBase entitylivingbase) {
        ItemStack itemstack = this.q instanceof EntityLivingBase ? ((EntityLivingBase)this.q).bz() : null;
        String s0 = "death.attack." + this.p;
        String s1 = s0 + ".item";

        return itemstack != null && itemstack.s() && StatCollector.c(s1) ? new ChatComponentTranslation(s1, new Object[]{ entitylivingbase.e_(), this.q.e_(), itemstack.C() }) : new ChatComponentTranslation(s0, new Object[]{ entitylivingbase.e_(), this.q.e_() });
    }

    public boolean r() {
        return this.q != null && this.q instanceof EntityLivingBase && !(this.q instanceof EntityPlayer);
    }

    // CanaryMod
    public boolean isCritical() {
        return critical;
    }
}
