package net.minecraft.potion;

import com.google.common.collect.Maps;
import net.canarymod.Canary;
import net.canarymod.api.potion.CanaryPotion;
import net.canarymod.hook.entity.PotionEffectAppliedHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class Potion {

    public static final Potion[] a = new Potion[32];
    private static final Map I = Maps.newHashMap();
    public static final Potion b = null;
    public static final Potion c = (new Potion(1, new ResourceLocation("speed"), false, 8171462)).c("potion.moveSpeed").b(0, 0).a(SharedMonsterAttributes.d, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2);
    public static final Potion d = (new Potion(2, new ResourceLocation("slowness"), true, 5926017)).c("potion.moveSlowdown").b(1, 0).a(SharedMonsterAttributes.d, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2);
    public static final Potion e = (new Potion(3, new ResourceLocation("haste"), false, 14270531)).c("potion.digSpeed").b(2, 0).a(1.5D);
    public static final Potion f = (new Potion(4, new ResourceLocation("mining_fatigue"), true, 4866583)).c("potion.digSlowDown").b(3, 0);
    public static final Potion g = (new PotionAttackDamage(5, new ResourceLocation("strength"), false, 9643043)).c("potion.damageBoost").b(4, 0).a(SharedMonsterAttributes.e, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.5D, 2);
    public static final Potion h = (new PotionHealth(6, new ResourceLocation("instant_health"), false, 16262179)).c("potion.heal");
    public static final Potion i = (new PotionHealth(7, new ResourceLocation("instant_damage"), true, 4393481)).c("potion.harm");
    public static final Potion j = (new Potion(8, new ResourceLocation("jump_boost"), false, 2293580)).c("potion.jump").b(2, 1);
    public static final Potion k = (new Potion(9, new ResourceLocation("nausea"), true, 5578058)).c("potion.confusion").b(3, 1).a(0.25D);
    public static final Potion l = (new Potion(10, new ResourceLocation("regeneration"), false, 13458603)).c("potion.regeneration").b(7, 0).a(0.25D);
    public static final Potion m = (new Potion(11, new ResourceLocation("resistance"), false, 10044730)).c("potion.resistance").b(6, 1);
    public static final Potion n = (new Potion(12, new ResourceLocation("fire_resistance"), false, 14981690)).c("potion.fireResistance").b(7, 1);
    public static final Potion o = (new Potion(13, new ResourceLocation("water_breathing"), false, 3035801)).c("potion.waterBreathing").b(0, 2);
    public static final Potion p = (new Potion(14, new ResourceLocation("invisibility"), false, 8356754)).c("potion.invisibility").b(0, 1);
    public static final Potion q = (new Potion(15, new ResourceLocation("blindness"), true, 2039587)).c("potion.blindness").b(5, 1).a(0.25D);
    public static final Potion r = (new Potion(16, new ResourceLocation("night_vision"), false, 2039713)).c("potion.nightVision").b(4, 1);
    public static final Potion s = (new Potion(17, new ResourceLocation("hunger"), true, 5797459)).c("potion.hunger").b(1, 1);
    public static final Potion t = (new PotionAttackDamage(18, new ResourceLocation("weakness"), true, 4738376)).c("potion.weakness").b(5, 0).a(SharedMonsterAttributes.e, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0D, 0);
    public static final Potion u = (new Potion(19, new ResourceLocation("poison"), true, 5149489)).c("potion.poison").b(6, 0).a(0.25D);
    public static final Potion v = (new Potion(20, new ResourceLocation("wither"), true, 3484199)).c("potion.wither").b(1, 2).a(0.25D);
    public static final Potion w = (new PotionHealthBoost(21, new ResourceLocation("health_boost"), false, 16284963)).c("potion.healthBoost").b(2, 2).a(SharedMonsterAttributes.a, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0);
    public static final Potion x = (new PotionAbsoption(22, new ResourceLocation("absorption"), false, 2445989)).c("potion.absorption").b(2, 2);
    public static final Potion y = (new PotionHealth(23, new ResourceLocation("saturation"), false, 16262179)).c("potion.saturation");
    public static final Potion z = null;
    public static final Potion A = null;
    public static final Potion B = null;
    public static final Potion C = null;
    public static final Potion D = null;
    public static final Potion E = null;
    public static final Potion F = null;
    public static final Potion G = null;
    public final int H;
    private final Map J = Maps.newHashMap();
    public final boolean K; // CanaryMod: private => public
    private final int L;
    private String M = "";
    private int N = -1;
    private double O;
    private boolean P;
    private CanaryPotion canaryPotion; // CanaryMod: potion instance

    protected Potion(int i0, ResourceLocation resourcelocation, boolean flag0, int i1) {
        this.H = i0;
        a[i0] = this;
        I.put(resourcelocation, this);
        this.K = flag0;
        if (flag0) {
            this.O = 0.5D;
        }
        else {
            this.O = 1.0D;
        }

        this.L = i1;

        canaryPotion = new CanaryPotion(this); // CanaryMod: wrap potion
    }

    public static Potion b(String s0) {
        return (Potion) I.get(new ResourceLocation(s0));
    }

    public static String[] c() {
        String[] astring = new String[I.size()];
        int i0 = 0;

        ResourceLocation resourcelocation;

        for (Iterator iterator = I.keySet().iterator(); iterator.hasNext(); astring[i0++] = resourcelocation.toString()) {
            resourcelocation = (ResourceLocation) iterator.next();
        }

        return astring;
    }

    protected Potion b(int i0, int i1) {
        this.N = i0 + i1 * 8;
        return this;
    }

    public int d() {
        return this.H;
    }

    public void a(EntityLivingBase entitylivingbase, int i0) {
        if (this.H == l.H) {
            if (entitylivingbase.bm() < entitylivingbase.bt()) {
                entitylivingbase.g(1.0F);
            }
        }
        else if (this.H == u.H) {
            if (entitylivingbase.bm() > 1.0F) {
                entitylivingbase.a(DamageSource.l, 1.0F);
            }
        }
        else if (this.H == v.H) {
            entitylivingbase.a(DamageSource.m, 1.0F);
        }
        else if (this.H == s.H && entitylivingbase instanceof EntityPlayer) {
            ((EntityPlayer) entitylivingbase).a(0.025F * (float) (i0 + 1));
        }
        else if (this.H == y.H && entitylivingbase instanceof EntityPlayer) {
            if (!entitylivingbase.o.D) {
                ((EntityPlayer) entitylivingbase).ck().a(i0 + 1, 1.0F);
            }
        }
        else if ((this.H != h.H || entitylivingbase.bl()) && (this.H != i.H || !entitylivingbase.bl())) {
            if (this.H == i.H && !entitylivingbase.bl() || this.H == h.H && entitylivingbase.bl()) {
                entitylivingbase.a(DamageSource.l, (float) (6 << i0));
            }
        }
        else {
            entitylivingbase.g((float) Math.max(4 << i0, 0));
        }
    }

    public void a(Entity entity, Entity entity1, EntityLivingBase entitylivingbase, int i0, double d0) {
        // CanaryMod: PotionEffectApplied
        PotionEffectAppliedHook hook = (PotionEffectAppliedHook) new PotionEffectAppliedHook((net.canarymod.api.entity.living.CanaryLivingBase) entitylivingbase.getCanaryEntity(), Canary.factory().getPotionFactory().newPotionEffect(this.H, 0, i0)).call();
        if (hook.getPotionEffect() == null) {
            return;
        }
        i0 = hook.getPotionEffect().getAmplifier();
        //
        int i1;

        if ((this.H != h.H || entitylivingbase.bl()) && (this.H != i.H || !entitylivingbase.bl())) {
            if (this.H == i.H && !entitylivingbase.bl() || this.H == h.H && entitylivingbase.bl()) {
                i1 = (int) (d0 * (double) (6 << i0) + 0.5D);
                if (entity == null) {
                    entitylivingbase.a(DamageSource.l, (float) i1);
                }
                else {
                    entitylivingbase.a(DamageSource.b(entity, entity1), (float) i1);
                }
            }
        }
        else {
            i1 = (int) (d0 * (double) (4 << i0) + 0.5D);
            entitylivingbase.g((float) i1);
        }
    }

    public boolean b() {
        return false;
    }

    public boolean a(int i0, int i1) {
        int i2;

        if (this.H == l.H) {
            i2 = 50 >> i1;
            return i2 > 0 ? i0 % i2 == 0 : true;
        }
        else if (this.H == u.H) {
            i2 = 25 >> i1;
            return i2 > 0 ? i0 % i2 == 0 : true;
        }
        else if (this.H == v.H) {
            i2 = 40 >> i1;
            return i2 > 0 ? i0 % i2 == 0 : true;
        }
        else {
            return this.H == s.H;
        }
    }

    public Potion c(String s0) {
        this.M = s0;
        return this;
    }

    public String a() {
        return this.M;
    }

    protected Potion a(double d0) {
        this.O = d0;
        return this;
    }

    public double h() {
        return this.O;
    }

    public boolean j() {
        return this.P;
    }

    public int k() {
        return this.L;
    }

    public Potion a(IAttribute iattribute, String s0, double d0, int i0) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(s0), this.a(), d0, i0);

        this.J.put(iattribute, attributemodifier);
        return this;
    }

    public void a(EntityLivingBase entitylivingbase, BaseAttributeMap baseattributemap, int i0) {
        Iterator iterator = this.J.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            IAttributeInstance iattributeinstance = baseattributemap.a((IAttribute) entry.getKey());

            if (iattributeinstance != null) {
                iattributeinstance.c((AttributeModifier) entry.getValue());
            }
        }
    }

    public void b(EntityLivingBase entitylivingbase, BaseAttributeMap baseattributemap, int i0) {
        Iterator iterator = this.J.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            IAttributeInstance iattributeinstance = baseattributemap.a((IAttribute) entry.getKey());

            if (iattributeinstance != null) {
                AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();

                iattributeinstance.c(attributemodifier);
                iattributeinstance.b(new AttributeModifier(attributemodifier.a(), this.a() + " " + i0, this.a(i0, attributemodifier), attributemodifier.c()));
            }
        }
    }

    public double a(int i0, AttributeModifier attributemodifier) {
        return attributemodifier.d() * (double) (i0 + 1);
    }

    // CanaryMod
    public CanaryPotion getCanaryPotion() {
        return canaryPotion;
    }
    //
}
