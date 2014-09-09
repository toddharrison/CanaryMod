package net.minecraft.util;

import net.canarymod.api.CanaryDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.world.Explosion;

public class DamageSource {

    public static DamageSource a = (new DamageSource("inFire")).n();
    public static DamageSource b = (new DamageSource("onFire")).k().n();
    public static DamageSource c = (new DamageSource("lava")).n();
    public static DamageSource d = (new DamageSource("inWall")).k();
    public static DamageSource e = (new DamageSource("drown")).k();
    public static DamageSource f = (new DamageSource("starve")).k().m();
    public static DamageSource g = new DamageSource("cactus");
    public static DamageSource h = (new DamageSource("fall")).k();
    public static DamageSource i = (new DamageSource("outOfWorld")).k().l();
    public static DamageSource j = (new DamageSource("generic")).k();
    public static DamageSource k = (new DamageSource("magic")).k().t();
    public static DamageSource l = (new DamageSource("wither")).k();
    public static DamageSource m = new DamageSource("anvil");
    public static DamageSource n = new DamageSource("fallingBlock");
    private boolean p;
    private boolean q;
    private boolean r;
    private float s = 0.3F;
    private boolean t;
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    public String o;

    // CanaryMod
    protected CanaryDamageSource damageSource;

    public static DamageSource a(EntityLivingBase entitylivingbase) {
        return new EntityDamageSource("mob", entitylivingbase);
    }

    public static DamageSource a(EntityPlayer entityplayer) {
        return new EntityDamageSource("player", entityplayer);
    }

    public static DamageSource a(EntityArrow entityarrow, Entity entity) {
        return (new EntityDamageSourceIndirect("arrow", entityarrow, entity)).b();
    }

    public static DamageSource a(EntityFireball entityfireball, Entity entity) {
        return entity == null ? (new EntityDamageSourceIndirect("onFire", entityfireball, entityfireball)).n().b() : (new EntityDamageSourceIndirect("fireball", entityfireball, entity)).n().b();
    }

    public static DamageSource a(Entity entity, Entity entity1) {
        return (new EntityDamageSourceIndirect("thrown", entity, entity1)).b();
    }

    public static DamageSource b(Entity entity, Entity entity1) {
        return (new EntityDamageSourceIndirect("indirectMagic", entity, entity1)).k().t();
    }

    public static DamageSource a(Entity entity) {
        return (new EntityDamageSource("thorns", entity)).t();
    }

    public static DamageSource a(Explosion explosion) {
        return explosion != null && explosion.c() != null ? (new EntityDamageSource("explosion.player", explosion.c())).q().d() : (new DamageSource("explosion")).q().d();
    }

    public boolean a() {
        return this.u;
    }

    public DamageSource b() {
        this.u = true;
        return this;
    }

    public boolean c() {
        return this.x;
    }

    public DamageSource d() {
        this.x = true;
        return this;
    }

    public boolean e() {
        return this.p;
    }

    public float f() {
        return this.s;
    }

    public boolean g() {
        return this.q;
    }

    public boolean h() {
        return this.r;
    }

    protected DamageSource(String s0) {
        this.o = s0;
        damageSource = new CanaryDamageSource(this);
    }

    public Entity i() {
        return this.j();
    }

    public Entity j() {
        return null;
    }

    protected DamageSource k() {
        this.p = true;
        this.s = 0.0F;
        return this;
    }

    protected DamageSource l() {
        this.q = true;
        return this;
    }

    protected DamageSource m() {
        this.r = true;
        this.s = 0.0F;
        return this;
    }

    protected DamageSource n() {
        this.t = true;
        return this;
    }

    public IChatComponent b(EntityLivingBase entitylivingbase) {
        EntityLivingBase entitylivingbase1 = entitylivingbase.aX();
        String s0 = "death.attack." + this.o;
        String s1 = s0 + ".player";

        return entitylivingbase1 != null && StatCollector.c(s1) ? new ChatComponentTranslation(s1, new Object[]{entitylivingbase.c_(), entitylivingbase1.c_()}) : new ChatComponentTranslation(s0, new Object[]{entitylivingbase.c_()});
    }

    public boolean o() {
        return this.t;
    }

    public String p() {
        return this.o;
    }

    public DamageSource q() {
        this.v = true;
        return this;
    }

    public boolean r() {
        return this.v;
    }

    public boolean s() {
        return this.w;
    }

    public DamageSource t() {
        this.w = true;
        return this;
    }

    /**
     * Gets the CanaryMod damagesource wrapper
     *
     * @return
     */
    public CanaryDamageSource getCanaryDamageSource() {
        return damageSource;
    }

    /**
     * Set hunger damage
     *
     * @param f
     */
    public void setHungerDamage(float f) {
        this.s = f;
    }

    /**
     * Set unblockable
     *
     * @param b
     */
    public void setUnblockable(boolean b) {
        this.p = b;
        if (b == true) {
            this.s = 0.0f;
        }
    }
}
