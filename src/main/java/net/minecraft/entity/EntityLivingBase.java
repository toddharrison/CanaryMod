package net.minecraft.entity;

import com.google.common.collect.Maps;
import net.canarymod.Canary;
import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.CanaryLivingBase;
import net.canarymod.api.potion.CanaryPotionEffect;
import net.canarymod.api.scoreboard.CanaryScoreboard;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.entity.EntityDeathHook;
import net.canarymod.hook.entity.PotionEffectAppliedHook;
import net.canarymod.hook.entity.PotionEffectFinishHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public abstract class EntityLivingBase extends Entity {

    private static final UUID a = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
    private static final AttributeModifier b = (new AttributeModifier(a, "Sprinting speed boost", 0.30000001192092896D, 2)).a(false);
    private BaseAttributeMap c;
    private final CombatTracker f = new CombatTracker(this);
    private final Map g = Maps.newHashMap();
    private final ItemStack[] h = new ItemStack[5];
    public boolean ap;
    public int aq;
    public int ar;
    public int as;
    public int at;
    public float au;
    public int av;
    public float aw;
    public float ax;
    public float ay;
    public float az;
    public float aA;
    public int aB = 20;
    public float aC;
    public float aD;
    public float aE;
    public float aF;
    public float aG;
    public float aH;
    public float aI;
    public float aJ;
    public float aK = 0.02F;
    protected EntityPlayer aL;
    protected int aM;
    protected boolean aN;
    protected int aO;
    protected float aP;
    protected float aQ;
    protected float aR;
    protected float aS;
    protected float aT;
    protected int aU;
    protected float aV;
    protected boolean aW;
    public float aX;
    public float aY;
    protected float aZ;
    protected int ba;
    protected double bb;
    protected double bc;
    protected double bd;
    protected double be;
    protected double bf;
    private boolean i = true;
    private EntityLivingBase bg;
    private int bh;
    private EntityLivingBase bi;
    private int bj;
    private float bk;
    private int bl;
    private float bm;
   
    public void G() {
        this.a(DamageSource.j, Float.MAX_VALUE);
    }

    public EntityLivingBase(World world) {
        super(world);
        this.aW();
        this.h(this.bt());
        this.k = true;
        this.aF = (float) ((Math.random() + 1.0D) * 0.009999999776482582D);
        this.b(this.s, this.t, this.u);
        this.aE = (float) Math.random() * 12398.0F;
        this.y = (float) (Math.random() * 3.1415927410125732D * 2.0D);
        this.aI = this.y;
        this.S = 0.6F;
    }

    protected void h() {
        this.ac.a(7, Integer.valueOf(0));
        this.ac.a(8, Byte.valueOf((byte) 0));
        this.ac.a(9, Byte.valueOf((byte) 0));
        this.ac.a(6, Float.valueOf(1.0F));
    }

    protected void aW() {
        this.bx().b(SharedMonsterAttributes.a);
        this.bx().b(SharedMonsterAttributes.c);
        this.bx().b(SharedMonsterAttributes.d);
    }

    protected void a(double d0, boolean flag0, Block block, BlockPos blockpos) {
        if (!this.V()) {
            this.W();
        }

        if (!this.o.D && this.O > 3.0F && flag0) {
            IBlockState iblockstate = this.o.p(blockpos);
            Block block1 = iblockstate.c();
            float f0 = (float) MathHelper.f(this.O - 3.0F);

            if (block1.r() != Material.a) {
                double d1 = (double) Math.min(0.2F + f0 / 15.0F, 10.0F);

                if (d1 > 2.5D) {
                    d1 = 2.5D;
                }

                int i0 = (int) (150.0D * d1);

                ((WorldServer) this.o).a(EnumParticleTypes.BLOCK_DUST, this.s, this.t, this.u, i0, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.f(iblockstate)});
            }
        }

        super.a(d0, flag0, block, blockpos);
    }

    public boolean aX() {
        return false;
    }

    public void K() {
        this.aw = this.ax;
        super.K();
        this.o.B.a("livingEntityBaseTick");
        boolean flag0 = this instanceof EntityPlayer;

        if (this.ai()) {
            if (this.aj()) {
                // CanaryMod: call DamageHook (Suffocation)
                DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.e), 1.0F).call();
                if (!hook.isCanceled()) {
                    this.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), hook.getDamageDealt());
                }
                //
            }
            else if (flag0 && !this.o.af().a(this.aQ())) {
                double d0 = this.o.af().a((Entity) this) + this.o.af().m();

                if (d0 < 0.0D) {
                    // CanaryMod: call DamageHook (Suffocation)
                    DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.e), 1.0F).call();
                    if (!hook.isCanceled()) {
                        this.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), hook.getDamageDealt());
                    }
                    //
                    this.a(DamageSource.e, (float) Math.max(1, MathHelper.c(-d0 * this.o.af().n())));
                }
            }
        }

        if (this.T() || this.o.D) {
            this.N();
        }

        boolean flag1 = flag0 && ((EntityPlayer) this).by.a;

        if (this.ai() && this.a(Material.h)) {
            if (!this.aX() && !this.k(Potion.o.H) && !flag1) {
                this.h(this.j(this.aA()));
                if (this.aA() == -20) {
                    this.h(0);

                    // CanaryMod - drowning damage.
                    DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.f), 2.0F).call();
                    if (!hook.isCanceled()) {
                        for (int i0 = 0; i0 < 8; ++i0) {
                            float f0 = this.V.nextFloat() - this.V.nextFloat();
                            float f1 = this.V.nextFloat() - this.V.nextFloat();
                            float f2 = this.V.nextFloat() - this.V.nextFloat();

                            this.o.a(EnumParticleTypes.WATER_BUBBLE, this.s + (double) f0, this.t + (double) f1, this.u + (double) f2, this.v, this.w, this.x, new int[0]);
                        }

                        this.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), hook.getDamageDealt());
                    }
                    //
                }
            }

            if (!this.o.D && this.av() && this.m instanceof EntityLivingBase) {
                this.a((Entity) null);
            }
        } else {
            this.h(300);
        }

        if (this.ai() && this.U()) {
            this.N();
        }

        this.aC = this.aD;
        if (this.as > 0) {
            --this.as;
        }

        if (this.Z > 0 && !(this instanceof EntityPlayerMP)) {
            --this.Z;
        }

        if (this.bm() <= 0.0F) {
            this.aY();
        }

        if (this.aM > 0) {
            --this.aM;
        } else {
            this.aL = null;
        }

        if (this.bi != null && !this.bi.ai()) {
            this.bi = null;
        }

        if (this.bg != null) {
            if (!this.bg.ai()) {
                this.b((EntityLivingBase) null);
            } else if (this.W - this.bh > 100) {
                this.b((EntityLivingBase) null);
            }
        }

        this.bh();
        this.aS = this.aR;
        this.aH = this.aG;
        this.aJ = this.aI;
        this.A = this.y;
        this.B = this.z;
        this.o.B.b();
    }

    public boolean i_() {
        return false;
    }

    protected void aY() {
        ++this.av;
        if (this.av == 20) {
            int i0;

            if (!this.o.D && (this.aM > 0 || this.ba()) && this.aZ() && this.o.Q().b("doMobLoot")) {
                // CanaryMod: XP Drop checks
                if (getCanaryEntity().doesDropXP()) {
                    i0 = this.b(this.aL);

                    while (i0 > 0) {
                        int i1 = EntityXPOrb.a(i0);

                        i0 -= i1;
                        this.o.d((Entity)(new EntityXPOrb(this.o, this.s, this.t, this.u, i1)));
                    }
                }
            }

            this.J();

            for (i0 = 0; i0 < 20; ++i0) {
                double d0 = this.V.nextGaussian() * 0.02D;
                double d1 = this.V.nextGaussian() * 0.02D;
                double d2 = this.V.nextGaussian() * 0.02D;

                this.o.a(EnumParticleTypes.EXPLOSION_NORMAL, this.s + (double) (this.V.nextFloat() * this.J * 2.0F) - (double) this.J, this.t + (double) (this.V.nextFloat() * this.K), this.u + (double) (this.V.nextFloat() * this.J * 2.0F) - (double) this.J, d0, d1, d2, new int[0]);
            }
        }

    }

    protected boolean aZ() {
        return !this.i_();
    }

    protected int j(int i0) {
        int i1 = EnchantmentHelper.a((Entity) this);

        return i1 > 0 && this.V.nextInt(i1 + 1) > 0 ? i0 : i0 - 1;
    }

    protected int b(EntityPlayer entityplayer) {
        return 0;
    }

    protected boolean ba() {
        return false;
    }

    public Random bb() {
        return this.V;
    }

    public EntityLivingBase bc() {
        return this.bg;
    }

    public int bd() {
        return this.bh;
    }

    public void b(EntityLivingBase entitylivingbase) {
        this.bg = entitylivingbase;
        this.bh = this.W;
    }

    public EntityLivingBase be() {
        return this.bi;
    }

    public int bf() {
        return this.bj;
    }

    public void p(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.bi = (EntityLivingBase) entity;
        } else {
            this.bi = null;
        }

        this.bj = this.W;
    }

    public int bg() {
        return this.aO;
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("HealF", this.bm());
        nbttagcompound.a("Health", (short) ((int) Math.ceil((double) this.bm())));
        nbttagcompound.a("HurtTime", (short) this.as);
        nbttagcompound.a("HurtByTimestamp", this.bh);
        nbttagcompound.a("DeathTime", (short) this.av);
        nbttagcompound.a("AbsorptionAmount", this.bM());
        ItemStack[] aitemstack = this.at();
        int i0 = aitemstack.length;

        int i1;
        ItemStack itemstack;

        for (i1 = 0; i1 < i0; ++i1) {
            itemstack = aitemstack[i1];
            if (itemstack != null) {
                this.c.a(itemstack.B());
            }
        }

        nbttagcompound.a("Attributes", (NBTBase) SharedMonsterAttributes.a(this.bx()));
        aitemstack = this.at();
        i0 = aitemstack.length;

        for (i1 = 0; i1 < i0; ++i1) {
            itemstack = aitemstack[i1];
            if (itemstack != null) {
                this.c.b(itemstack.B());
            }
        }

        if (!this.g.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();
            Iterator iterator = this.g.values().iterator();

            while (iterator.hasNext()) {
                PotionEffect potioneffect = (PotionEffect) iterator.next();

                nbttaglist.a((NBTBase) potioneffect.a(new NBTTagCompound()));
            }

            nbttagcompound.a("ActiveEffects", (NBTBase) nbttaglist);
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        this.l(nbttagcompound.h("AbsorptionAmount"));
        if (nbttagcompound.b("Attributes", 9) && this.o != null && !this.o.D) {
            SharedMonsterAttributes.a(this.bx(), nbttagcompound.c("Attributes", 10));
        }

        if (nbttagcompound.b("ActiveEffects", 9)) {
            NBTTagList nbttaglist = nbttagcompound.c("ActiveEffects", 10);

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
                PotionEffect potioneffect = PotionEffect.b(nbttagcompound1);

                if (potioneffect != null) {
                    this.g.put(Integer.valueOf(potioneffect.a()), potioneffect);
                }
            }
        }

        if (nbttagcompound.b("HealF", 99)) {
            this.h(nbttagcompound.h("HealF"));
        } else {
            NBTBase nbtbase = nbttagcompound.a("Health");

            if (nbtbase == null) {
                this.h(this.bt());
            } else if (nbtbase.a() == 5) {
                this.h(((NBTTagFloat) nbtbase).h());
            } else if (nbtbase.a() == 2) {
                this.h((float) ((NBTTagShort) nbtbase).e());
            }
        }

        this.as = nbttagcompound.e("HurtTime");
        this.av = nbttagcompound.e("DeathTime");
        this.bh = nbttagcompound.f("HurtByTimestamp");
    }

    protected void bh() {
        Iterator iterator = this.g.keySet().iterator();

        while (iterator.hasNext()) {
            Integer integer = (Integer) iterator.next();
            PotionEffect potioneffect = (PotionEffect) this.g.get(integer);

            if (!potioneffect.a(this)) {
                if (!this.o.D) {
                    iterator.remove();
                    this.b(potioneffect);
                }
            } else if (potioneffect.b() % 600 == 0) {
                this.a(potioneffect, false);
            }
        }

        if (this.i) {
            if (!this.o.D) {
                this.B();
            }

            this.i = false;
        }

        int i0 = this.ac.c(7);
        boolean flag0 = this.ac.a(8) > 0;

        if (i0 > 0) {
            boolean flag1 = false;

            if (!this.ay()) {
                flag1 = this.V.nextBoolean();
            } else {
                flag1 = this.V.nextInt(15) == 0;
            }

            if (flag0) {
                flag1 &= this.V.nextInt(5) == 0;
            }

            if (flag1 && i0 > 0) {
                double d0 = (double) (i0 >> 16 & 255) / 255.0D;
                double d1 = (double) (i0 >> 8 & 255) / 255.0D;
                double d2 = (double) (i0 >> 0 & 255) / 255.0D;

                this.o.a(flag0 ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB, this.s + (this.V.nextDouble() - 0.5D) * (double) this.J, this.t + this.V.nextDouble() * (double) this.K, this.u + (this.V.nextDouble() - 0.5D) * (double) this.J, d0, d1, d2, new int[0]);
            }
        }

    }

    protected void B() {
        if (this.g.isEmpty()) {
            this.bi();
            this.e(false);
        } else {
            int i0 = PotionHelper.a(this.g.values());

            this.ac.b(8, Byte.valueOf((byte) (PotionHelper.b(this.g.values()) ? 1 : 0)));
            this.ac.b(7, Integer.valueOf(i0));
            this.e(this.k(Potion.p.H));
        }

    }

    protected void bi() {
        this.ac.b(8, Byte.valueOf((byte) 0));
        this.ac.b(7, Integer.valueOf(0));
    }

    public void bj() {
        Iterator iterator = this.g.keySet().iterator();

        while (iterator.hasNext()) {
            Integer integer = (Integer) iterator.next();
            PotionEffect potioneffect = (PotionEffect) this.g.get(integer);

            if (!this.o.D) {
                iterator.remove();
                this.b(potioneffect);
            }
        }

    }

    public Collection bk() {
        return this.g.values();
    }

    public boolean k(int i0) {
        return this.g.containsKey(Integer.valueOf(i0));
    }

    public boolean a(Potion potion) {
        return this.g.containsKey(Integer.valueOf(potion.H));
    }

    public PotionEffect b(Potion potion) {
        return (PotionEffect) this.g.get(Integer.valueOf(potion.H));
    }

    public void c(PotionEffect potioneffect) {
        if (this.d(potioneffect)) {
            // CanaryMod: PotionEffectApplied
            PotionEffectAppliedHook hook = (PotionEffectAppliedHook) new PotionEffectAppliedHook((net.canarymod.api.entity.living.LivingBase) getCanaryEntity(), new CanaryPotionEffect(potioneffect)).call();
            if (hook.getPotionEffect() == null) {
                return;
            }
            potioneffect = ((CanaryPotionEffect) hook.getPotionEffect()).getHandle();
            //
            if (this.g.containsKey(Integer.valueOf(potioneffect.a()))) {
                ((PotionEffect) this.g.get(Integer.valueOf(potioneffect.a()))).a(potioneffect);
                this.a((PotionEffect) this.g.get(Integer.valueOf(potioneffect.a())), true);
            } else {
                this.g.put(Integer.valueOf(potioneffect.a()), potioneffect);
                this.a(potioneffect);
            }

        }
    }

    public boolean d(PotionEffect potioneffect) {
        if (this.by() == EnumCreatureAttribute.UNDEAD) {
            int i0 = potioneffect.a();

            if (i0 == Potion.l.H || i0 == Potion.u.H) {
                return false;
            }
        }

        return true;
    }

    public boolean bl() {
        return this.by() == EnumCreatureAttribute.UNDEAD;
    }

    public void m(int i0) {
        PotionEffect potioneffect = (PotionEffect) this.g.remove(Integer.valueOf(i0));

        if (potioneffect != null) {
            this.b(potioneffect);
        }

    }

    protected void a(PotionEffect potioneffect) {
        this.i = true;
        if (!this.o.D) {
            Potion.a[potioneffect.a()].b(this, this.bx(), potioneffect.c());
        }

    }

    protected void a(PotionEffect potioneffect, boolean flag0) {
        this.i = true;
        if (flag0 && !this.o.D) {
            Potion.a[potioneffect.a()].a(this, this.bx(), potioneffect.c());
            Potion.a[potioneffect.a()].b(this, this.bx(), potioneffect.c());
        }
    }

    protected void b(PotionEffect potioneffect) {
        this.i = true;
        if (!this.o.D) {
            // CanaryMod: PotionEffectFinish
            new PotionEffectFinishHook((net.canarymod.api.entity.living.LivingBase) getCanaryEntity(), new CanaryPotionEffect(potioneffect)).call();
            //
            Potion.a[potioneffect.a()].a(this, this.bx(), potioneffect.c());
        }

    }

    public void g(float f0) {
        float f1 = this.bm();

        if (f1 > 0.0F) {
            this.h(f1 + f0);
        }

    }

    public final float bm() {
        return this.ac.d(6);
    }

    public void h(float f0) {
        this.ac.b(6, Float.valueOf(MathHelper.a(f0, 0.0F, this.bt())));
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else if (this.o.D) {
            return false;
        } else {
            this.aO = 0;
            if (this.bm() <= 0.0F) {
                return false;
            } else if (damagesource.o() && this.a(Potion.n)) {
                return false;
            } else {
                if ((damagesource == DamageSource.n || damagesource == DamageSource.o) && this.p(4) != null) {
                    this.p(4).a((int) (f0 * 4.0F + this.V.nextFloat() * f0 * 2.0F), this);
                    f0 *= 0.75F;
                }

                this.az = 1.5F;
                boolean flag0 = true;

                // CanaryMod: call DamageHook (Entity)
                CanaryLivingBase attacker = null;

                if (damagesource instanceof EntityDamageSource && (damagesource).i() instanceof EntityLivingBase) {
                    attacker = (CanaryLivingBase) (damagesource).i().getCanaryEntity();
                }
                DamageHook hook = new DamageHook(attacker, entity, new CanaryDamageSource(damagesource), f0);

                if ((float) this.Z > (float) this.aB / 2.0F) {
                    if (f0 <= this.aV) {
                        return false;
                    }

                    hook.setDamageDealt((f0 - this.aV));
                    if (attacker != null) {
                        hook.call();
                    }
                    if (hook.isCanceled()) {
                        return false;
                    }

                    this.d(damagesource, f0 - this.aV);
                    this.aV = f0;
                    flag0 = false;
                } else {
                    if (attacker != null) {
                        hook.call();
                    }
                    if (hook.isCanceled()) {
                        return false;
                    }
                    this.aV = f0;
                    this.Z = this.aB;
                    this.d(damagesource, f0);
                    this.as = this.at = 10;
                }
                //

                this.au = 0.0F;
                Entity entity = damagesource.j();

                if (entity != null) {
                    if (entity instanceof EntityLivingBase) {
                        this.b((EntityLivingBase) entity);
                    }

                    if (entity instanceof EntityPlayer) {
                        this.aM = 100;
                        this.aL = (EntityPlayer) entity;
                    } else if (entity instanceof EntityWolf) {
                        EntityWolf entitywolf = (EntityWolf) entity;

                        if (entitywolf.cj()) {
                            this.aM = 100;
                            this.aL = null;
                        }
                    }
                }

                if (flag0) {
                    this.o.a((Entity) this, (byte) 2);
                    if (damagesource != DamageSource.f) {
                        this.ac();
                    }

                    if (entity != null) {
                        double d0 = entity.s - this.s;

                        double d1;

                        for (d1 = entity.u - this.u; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                            d0 = (Math.random() - Math.random()) * 0.01D;
                        }

                        this.au = (float) (Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D - (double) this.y);
                        this.a(entity, f0, d0, d1);
                    } else {
                        this.au = (float) ((int) (Math.random() * 2.0D) * 180);
                    }
                }

                String s0;

                if (this.bm() <= 0.0F) {
                    s0 = this.bo();
                    if (flag0 && s0 != null) {
                        this.a(s0, this.bA(), this.bB());
                    }

                    this.a(damagesource);
                } else {
                    s0 = this.bn();
                    if (flag0 && s0 != null) {
                        this.a(s0, this.bA(), this.bB());
                    }
                }

                return true;
            }
        }
    }

    public void b(ItemStack itemstack) {
        this.a("random.break", 0.8F, 0.8F + this.o.s.nextFloat() * 0.4F);

        for (int i0 = 0; i0 < 5; ++i0) {
            Vec3 vec3 = new Vec3(((double) this.V.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);

            vec3 = vec3.a(-this.z * 3.1415927F / 180.0F);
            vec3 = vec3.b(-this.y * 3.1415927F / 180.0F);
            double d0 = (double) (-this.V.nextFloat()) * 0.6D - 0.3D;
            Vec3 vec31 = new Vec3(((double) this.V.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);

            vec31 = vec31.a(-this.z * 3.1415927F / 180.0F);
            vec31 = vec31.b(-this.y * 3.1415927F / 180.0F);
            vec31 = vec31.b(this.s, this.t + (double) this.aR(), this.u);
            this.o.a(EnumParticleTypes.ITEM_CRACK, vec31.a, vec31.b, vec31.c, vec3.a, vec3.b + 0.05D, vec3.c, new int[] { Item.b(itemstack.b())});
        }
    }

    public void a(DamageSource damagesource) {
        // CanaryMod: EntityDeath
        new EntityDeathHook(this.getCanaryEntity(), damagesource.getCanaryDamageSource()).call();
        //
        Entity entity = damagesource.j();
        EntityLivingBase entitylivingbase = this.bs();

        if (this.aU >= 0 && entitylivingbase != null) {
            entitylivingbase.b(this, this.aU);
        }

        if (entity != null) {
            entity.a(this);
        }

        this.aN = true;
        this.br().g();
        if (!this.o.D) {
            int i0 = 0;

            if (entity instanceof EntityPlayer) {
                i0 = EnchantmentHelper.i((EntityLivingBase) entity);
            }

            if (this.aZ() && this.o.Q().b("doMobLoot")) {
                // CanaryMod: Per-Entity Loot Drop checks
                if (getCanaryEntity().doesDropLoot()) {
                    this.b(this.aM > 0, i0);
                    this.a(this.aM > 0, i0);
                    if (this.aM > 0 && this.V.nextFloat() < 0.025F + (float)i0 * 0.01F) {
                        this.bp();
                    }
                }
                //
            }
        }

        this.o.a((Entity) this, (byte) 3);
    }

    protected void a(boolean flag0, int i0) {}

    public void a(Entity entity, float f0, double d0, double d1) {
        if (this.V.nextDouble() >= this.a(SharedMonsterAttributes.c).e()) {
            this.ai = true;
            float f1 = MathHelper.a(d0 * d0 + d1 * d1);
            float f2 = 0.4F;

            this.v /= 2.0D;
            this.w /= 2.0D;
            this.x /= 2.0D;
            this.v -= d0 / (double) f1 * (double) f2;
            this.w += (double) f2;
            this.x -= d1 / (double) f1 * (double) f2;
            if (this.w > 0.4000000059604645D) {
                this.w = 0.4000000059604645D;
            }

        }
    }

    protected String bn() {
        return "game.neutral.hurt";
    }

    protected String bo() {
        return "game.neutral.die";
    }

    protected void bp() {}

    protected void b(boolean flag0, int i0) {}

    public boolean j_() {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.aQ().b);
        int i2 = MathHelper.c(this.u);
        Block block = this.o.p(new BlockPos(i0, i1, i2)).c();

        return (block == Blocks.au || block == Blocks.bn) && (!(this instanceof EntityPlayer) || !((EntityPlayer) this).v());
    }

    public boolean ai() {
        return !this.I && this.bm() > 0.0F;
    }

    public void e(float f0, float f1) {
        super.e(f0, f1);
        PotionEffect potioneffect = this.b(Potion.j);
        float f2 = potioneffect != null ? (float) (potioneffect.c() + 1) : 0.0F;
        int i0 = MathHelper.f((f0 - 3.0F - f2) * f1);

        if (i0 > 0) {
            this.a(this.n(i0), 1.0F, 1.0F);
            // CanaryMod: call DamageHook (Fall)
            DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.i), i0).call();

            if (!hook.isCanceled()) {
                this.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), hook.getDamageDealt());
            }
            //
            int i1 = MathHelper.c(this.s);
            int i2 = MathHelper.c(this.t - 0.20000000298023224D);
            int i3 = MathHelper.c(this.u);
            Block block = this.o.p(new BlockPos(i1, i2, i3)).c();

            if (block.r() != Material.a) {
                Block.SoundType block_soundtype = block.H;

                this.a(block_soundtype.c(), block_soundtype.d() * 0.5F, block_soundtype.e() * 0.75F);
            }
        }

    }

    protected String n(int i0) {
        return i0 > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
    }

    public int bq() {
        int i0 = 0;
        ItemStack[] aitemstack = this.at();
        int i1 = aitemstack.length;

        for (int i2 = 0; i2 < i1; ++i2) {
            ItemStack itemstack = aitemstack[i2];

            if (itemstack != null && itemstack.b() instanceof ItemArmor) {
                int i3 = ((ItemArmor) itemstack.b()).c;

                i0 += i3;
            }
        }

        return i0;
    }

    protected void i(float f0) {}

    protected float b(DamageSource damagesource, float f0) {
        if (!damagesource.e()) {
            int i0 = 25 - this.bq();
            float f1 = f0 * (float) i0;

            this.i(f0);
            f0 = f1 / 25.0F;
        }

        return f0;
    }

    protected float c(DamageSource damagesource, float f0) {
        if (damagesource.h()) {
            return f0;
        } else {
            int i0;
            int i1;
            float f1;

            if (this.a(Potion.m) && damagesource != DamageSource.j) {
                i0 = (this.b(Potion.m).c() + 1) * 5;
                i1 = 25 - i0;
                f1 = f0 * (float) i1;
                f0 = f1 / 25.0F;
            }

            if (f0 <= 0.0F) {
                return 0.0F;
            } else {
                i0 = EnchantmentHelper.a(this.at(), damagesource);
                if (i0 > 20) {
                    i0 = 20;
                }

                if (i0 > 0 && i0 <= 20) {
                    i1 = 25 - i0;
                    f1 = f0 * (float) i1;
                    f0 = f1 / 25.0F;
                }

                return f0;
            }
        }
    }

    protected void d(DamageSource damagesource, float f0) {
        if (!this.b(damagesource)) {
            f0 = this.b(damagesource, f0);
            f0 = this.c(damagesource, f0);
            float f1 = f0;

            f0 = Math.max(f0 - this.bM(), 0.0F);
            this.l(this.bM() - (f1 - f0));
            if (f0 != 0.0F) {
                float f2 = this.bm();

                this.h(f2 - f0);
                this.br().a(damagesource, f2, f0);
                this.l(this.bM() - f0);
            }
        }
    }

    public CombatTracker br() {
        return this.f;
    }

    public EntityLivingBase bs() {
        return (EntityLivingBase) (this.f.c() != null ? this.f.c() : (this.aL != null ? this.aL : (this.bg != null ? this.bg : null)));
    }

    public final float bt() {
        return (float) this.a(SharedMonsterAttributes.a).e();
    }

    public final int bu() {
        return this.ac.a(9);
    }

    public final void o(int i0) {
        this.ac.b(9, Byte.valueOf((byte) i0));
    }

    private int n() {
        return this.a(Potion.e) ? 6 - (1 + this.b(Potion.e).c()) * 1 : (this.a(Potion.f) ? 6 + (1 + this.b(Potion.f).c()) * 2 : 6);
    }

    public void bv() {
        if (!this.ap || this.aq >= this.n() / 2 || this.aq < 0) {
            this.aq = -1;
            this.ap = true;
            if (this.o instanceof WorldServer) {
                ((WorldServer) this.o).s().a((Entity) this, (Packet) (new S0BPacketAnimation(this, 0)));
            }
        }

    }

    protected void O() {
        this.a(DamageSource.j, 4.0F);
    }

    protected void bw() {
        int i0 = this.n();

        if (this.ap) {
            ++this.aq;
            if (this.aq >= i0) {
                this.aq = 0;
                this.ap = false;
            }
        } else {
            this.aq = 0;
        }

        this.ax = (float) this.aq / (float) i0;
    }

    public IAttributeInstance a(IAttribute iattribute) {
        return this.bx().a(iattribute);
    }

    public BaseAttributeMap bx() {
        if (this.c == null) {
            this.c = new ServersideAttributeMap();
        }

        return this.c;
    }

    public EnumCreatureAttribute by() {
        return EnumCreatureAttribute.UNDEFINED;
    }

    public abstract ItemStack bz();

    public abstract ItemStack p(int i0);

    public abstract void c(int i0, ItemStack itemstack);

    public void d(boolean flag0) {
        super.d(flag0);
        IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

        if (iattributeinstance.a(a) != null) {
            iattributeinstance.c(b);
        }

        if (flag0) {
            iattributeinstance.b(b);
        }

    }

    public abstract ItemStack[] at();

    protected float bA() {
        return 1.0F;
    }

    protected float bB() {
        return this.i_() ? (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.5F : (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F;
    }

    protected boolean bC() {
        return this.bm() <= 0.0F;
    }

    public void q(Entity entity) {
        double d0 = entity.s;
        double d1 = entity.aQ().b + (double) entity.K;
        double d2 = entity.u;
        byte b0 = 1;

        for (int i0 = -b0; i0 <= b0; ++i0) {
            for (int i1 = -b0; i1 < b0; ++i1) {
                if (i0 != 0 || i1 != 0) {
                    int i2 = (int) (this.s + (double) i0);
                    int i3 = (int) (this.u + (double) i1);
                    AxisAlignedBB axisalignedbb = this.aQ().c((double) i0, 1.0D, (double) i1);

                    if (this.o.a(axisalignedbb).isEmpty()) {
                        if (World.a((IBlockAccess) this.o, new BlockPos(i2, (int) this.t, i3))) {
                            this.a(this.s + (double) i0, this.t + 1.0D, this.u + (double) i1);
                            return;
                        }

                        if (World.a((IBlockAccess) this.o, new BlockPos(i2, (int) this.t - 1, i3)) || this.o.p(new BlockPos(i2, (int) this.t - 1, i3)).c().r() == Material.h) {
                            d0 = this.s + (double) i0;
                            d1 = this.t + 1.0D;
                            d2 = this.u + (double) i1;
                        }
                    }
                }
            }
        }

        this.a(d0, d1, d2);
    }

    protected float bD() {
        return 0.42F;
    }

    protected void bE() {
        this.w = (double) this.bD();
        if (this.a(Potion.j)) {
            this.w += (double) ((float) (this.b(Potion.j).c() + 1) * 0.1F);
        }

        if (this.ax()) {
            float f0 = this.y * 0.017453292F;

            this.v -= (double) (MathHelper.a(f0) * 0.2F);
            this.x += (double) (MathHelper.b(f0) * 0.2F);
        }

        this.ai = true;
    }

    protected void bF() {
        this.w += 0.03999999910593033D;
    }

    protected void bG() {
        this.w += 0.03999999910593033D;
    }

    public void g(float f0, float f1) {
        double d0;
        float f2;

        if (this.bL()) {
            float f3;
            float f4;

            if (this.V() && (!(this instanceof EntityPlayer) || !((EntityPlayer) this).by.b)) {
                d0 = this.t;
                f3 = 0.8F;
                f4 = 0.02F;
                f2 = (float) EnchantmentHelper.b((Entity) this);
                if (f2 > 3.0F) {
                    f2 = 3.0F;
                }

                if (!this.C) {
                    f2 *= 0.5F;
                }

                if (f2 > 0.0F) {
                    f3 += (0.54600006F - f3) * f2 / 3.0F;
                    f4 += (this.bH() * 1.0F - f4) * f2 / 3.0F;
                }

                this.a(f0, f1, f4);
                this.d(this.v, this.w, this.x);
                this.v *= (double) f3;
                this.w *= 0.800000011920929D;
                this.x *= (double) f3;
                this.w -= 0.02D;
                if (this.D && this.c(this.v, this.w + 0.6000000238418579D - this.t + d0, this.x)) {
                    this.w = 0.30000001192092896D;
                }
            } else if (this.ab() && (!(this instanceof EntityPlayer) || !((EntityPlayer) this).by.b)) {
                d0 = this.t;
                this.a(f0, f1, 0.02F);
                this.d(this.v, this.w, this.x);
                this.v *= 0.5D;
                this.w *= 0.5D;
                this.x *= 0.5D;
                this.w -= 0.02D;
                if (this.D && this.c(this.v, this.w + 0.6000000238418579D - this.t + d0, this.x)) {
                    this.w = 0.30000001192092896D;
                }
            } else {
                float f5 = 0.91F;

                if (this.C) {
                    f5 = this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.aQ().b) - 1, MathHelper.c(this.u))).c().K * 0.91F;
                }

                float f6 = 0.16277136F / (f5 * f5 * f5);

                if (this.C) {
                    f3 = this.bH() * f6;
                } else {
                    f3 = this.aK;
                }

                this.a(f0, f1, f3);
                f5 = 0.91F;
                if (this.C) {
                    f5 = this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.aQ().b) - 1, MathHelper.c(this.u))).c().K * 0.91F;
                }

                if (this.j_()) {
                    f4 = 0.15F;
                    this.v = MathHelper.a(this.v, (double) (-f4), (double) f4);
                    this.x = MathHelper.a(this.x, (double) (-f4), (double) f4);
                    this.O = 0.0F;
                    if (this.w < -0.15D) {
                        this.w = -0.15D;
                    }

                    boolean flag0 = this.aw() && this instanceof EntityPlayer;

                    if (flag0 && this.w < 0.0D) {
                        this.w = 0.0D;
                    }
                }

                this.d(this.v, this.w, this.x);
                if (this.D && this.j_()) {
                    this.w = 0.2D;
                }

                if (this.o.D && (!this.o.e(new BlockPos((int) this.s, 0, (int) this.u)) || !this.o.f(new BlockPos((int) this.s, 0, (int) this.u)).o())) {
                    if (this.t > 0.0D) {
                        this.w = -0.1D;
                    } else {
                        this.w = 0.0D;
                    }
                } else {
                    this.w -= 0.08D;
                }

                this.w *= 0.9800000190734863D;
                this.v *= (double) f5;
                this.x *= (double) f5;
            }
        }

        this.ay = this.az;
        d0 = this.s - this.p;
        double d1 = this.u - this.r;

        f2 = MathHelper.a(d0 * d0 + d1 * d1) * 4.0F;
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        this.az += (f2 - this.az) * 0.4F;
        this.aA += this.az;
    }

    public float bH() {
        return this.bk;
    }

    public void j(float f0) {
        this.bk = f0;
    }

    public boolean r(Entity entity) {
        this.p(entity);
        return false;
    }

    public boolean bI() {
        return false;
    }

    public void s_() {
        super.s_();
        if (!this.o.D) {
            int i0 = this.bu();

            if (i0 > 0) {
                if (this.ar <= 0) {
                    this.ar = 20 * (30 - i0);
                }

                --this.ar;
                if (this.ar <= 0) {
                    this.o(i0 - 1);
                }
            }

            for (int i1 = 0; i1 < 5; ++i1) {
                ItemStack itemstack = this.h[i1];
                ItemStack itemstack1 = this.p(i1);

                if (!ItemStack.b(itemstack1, itemstack)) {
                    ((WorldServer) this.o).s().a((Entity) this, (Packet) (new S04PacketEntityEquipment(this.F(), i1, itemstack1)));
                    if (itemstack != null) {
                        this.c.a(itemstack.B());
                    }

                    if (itemstack1 != null) {
                        this.c.b(itemstack1.B());
                    }

                    this.h[i1] = itemstack1 == null ? null : itemstack1.k();
                }
            }

            if (this.W % 20 == 0) {
                this.br().g();
            }
        }

        this.m();
        double d0 = this.s - this.p;
        double d1 = this.u - this.r;
        float f0 = (float) (d0 * d0 + d1 * d1);
        float f1 = this.aG;
        float f2 = 0.0F;

        this.aP = this.aQ;
        float f3 = 0.0F;

        if (f0 > 0.0025000002F) {
            f3 = 1.0F;
            f2 = (float) Math.sqrt((double) f0) * 3.0F;
            f1 = (float) Math.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
        }

        if (this.ax > 0.0F) {
            f1 = this.y;
        }

        if (!this.C) {
            f3 = 0.0F;
        }

        this.aQ += (f3 - this.aQ) * 0.3F;
        this.o.B.a("headTurn");
        f2 = this.h(f1, f2);
        this.o.B.b();
        this.o.B.a("rangeChecks");

        while (this.y - this.A < -180.0F) {
            this.A -= 360.0F;
        }

        while (this.y - this.A >= 180.0F) {
            this.A += 360.0F;
        }

        while (this.aG - this.aH < -180.0F) {
            this.aH -= 360.0F;
        }

        while (this.aG - this.aH >= 180.0F) {
            this.aH += 360.0F;
        }

        while (this.z - this.B < -180.0F) {
            this.B -= 360.0F;
        }

        while (this.z - this.B >= 180.0F) {
            this.B += 360.0F;
        }

        while (this.aI - this.aJ < -180.0F) {
            this.aJ -= 360.0F;
        }

        while (this.aI - this.aJ >= 180.0F) {
            this.aJ += 360.0F;
        }

        this.o.B.b();
        this.aR += f2;
    }

    protected float h(float f0, float f1) {
        float f2 = MathHelper.g(f0 - this.aG);

        this.aG += f2 * 0.3F;
        float f3 = MathHelper.g(this.y - this.aG);
        boolean flag0 = f3 < -90.0F || f3 >= 90.0F;

        if (f3 < -75.0F) {
            f3 = -75.0F;
        }

        if (f3 >= 75.0F) {
            f3 = 75.0F;
        }

        this.aG = this.y - f3;
        if (f3 * f3 > 2500.0F) {
            this.aG += f3 * 0.2F;
        }

        if (flag0) {
            f1 *= -1.0F;
        }

        return f1;
    }

    public void m() {
        if (this.bl > 0) {
            --this.bl;
        }

        if (this.ba > 0) {
            double d0 = this.s + (this.bb - this.s) / (double) this.ba;
            double d1 = this.t + (this.bc - this.t) / (double) this.ba;
            double d2 = this.u + (this.bd - this.u) / (double) this.ba;
            double d3 = MathHelper.g(this.be - (double) this.y);

            this.y = (float) ((double) this.y + d3 / (double) this.ba);
            this.z = (float) ((double) this.z + (this.bf - (double) this.z) / (double) this.ba);
            --this.ba;
            this.b(d0, d1, d2);
            this.b(this.y, this.z);
        } else if (!this.bL()) {
            this.v *= 0.98D;
            this.w *= 0.98D;
            this.x *= 0.98D;
        }

        if (Math.abs(this.v) < 0.005D) {
            this.v = 0.0D;
        }

        if (Math.abs(this.w) < 0.005D) {
            this.w = 0.0D;
        }

        if (Math.abs(this.x) < 0.005D) {
            this.x = 0.0D;
        }

        this.o.B.a("ai");
        if (this.bC()) {
            this.aW = false;
            this.aX = 0.0F;
            this.aY = 0.0F;
            this.aZ = 0.0F;
        } else if (this.bL()) {
            this.o.B.a("newAi");
            this.bJ();
            this.o.B.b();
        }

        this.o.B.b();
        this.o.B.a("jump");
        if (this.aW) {
            if (this.V()) {
                this.bF();
            } else if (this.ab()) {
                this.bG();
            } else if (this.C && this.bl == 0) {
                this.bE();
                this.bl = 10;
            }
        } else {
            this.bl = 0;
        }

        this.o.B.b();
        this.o.B.a("travel");
        this.aX *= 0.98F;
        this.aY *= 0.98F;
        this.aZ *= 0.9F;
        this.g(this.aX, this.aY);
        this.o.B.b();
        this.o.B.a("push");
        if (!this.o.D) {
            this.bK();
        }

        this.o.B.b();
    }

    protected void bJ() {}

    protected void bK() {
        List list = this.o.b((Entity) this, this.aQ().b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (list != null && !list.isEmpty()) {
            for (int i0 = 0; i0 < list.size(); ++i0) {
                Entity entity = (Entity) list.get(i0);

                if (entity.ae()) {
                    this.s(entity);
                }
            }
        }

    }

    protected void s(Entity entity) {
        entity.i(this);
    }

    public void a(Entity entity) {
        if (this.m != null && entity == null) {
            if (!this.o.D) {
                this.q(this.m);
            }

            if (this.m != null) {
                this.m.l = null;
            }

            this.m = null;
        } else {
            super.a(entity);
        }
    }

    public void ak() {
        super.ak();
        this.aP = this.aQ;
        this.aQ = 0.0F;
        this.O = 0.0F;
    }

    public void i(boolean flag0) {
        this.aW = flag0;
    }

    public void a(Entity entity, int i0) {
        if (!entity.I && !this.o.D) {
            EntityTracker entitytracker = ((WorldServer) this.o).s();

            if (entity instanceof EntityItem) {
                entitytracker.a(entity, (Packet) (new S0DPacketCollectItem(entity.F(), this.F())));
            }

            if (entity instanceof EntityArrow) {
                entitytracker.a(entity, (Packet) (new S0DPacketCollectItem(entity.F(), this.F())));
            }

            if (entity instanceof EntityXPOrb) {
                entitytracker.a(entity, (Packet) (new S0DPacketCollectItem(entity.F(), this.F())));
            }
        }

    }

    public boolean t(Entity entity) {
        return this.o.a(new Vec3(this.s, this.t + (double) this.aR(), this.u), new Vec3(entity.s, entity.t + (double) entity.aR(), entity.u)) == null;
    }

    public Vec3 ap() {
        return this.d(1.0F);
    }

    public Vec3 d(float f0) {
        if (f0 == 1.0F) {
            return this.f(this.z, this.aI);
        } else {
            float f1 = this.B + (this.z - this.B) * f0;
            float f2 = this.aJ + (this.aI - this.aJ) * f0;

            return this.f(f1, f2);
        }
    }

    public boolean bL() {
        return !this.o.D;
    }

    public boolean ad() {
        return !this.I;
    }

    public boolean ae() {
        return !this.I;
    }

    protected void ac() {
        this.G = this.V.nextDouble() >= this.a(SharedMonsterAttributes.c).e();
    }

    public float aD() {
        return this.aI;
    }

    public void f(float f0) {
        this.aI = f0;
    }

    public float bM() {
        return this.bm;
    }

    public void l(float f0) {
        if (f0 < 0.0F) {
            f0 = 0.0F;
        }

        this.bm = f0;
    }

    public Team bN() {
        //return this.o.Z().h(this.aJ().toString());
        // return Canary Master Scoreboard
        return ((CanaryScoreboard)Canary.scoreboards().getScoreboard()).getHandle().h(this.aJ().toString());
    }

    public boolean c(EntityLivingBase entitylivingbase) {
        return this.a(entitylivingbase.bN());
    }

    public boolean a(Team team) {
        return this.bN() != null ? this.bN().a(team) : false;
    }

    public void g_() {}

    public void j() {}

    protected void bO() {
        this.i = true;
    }

    // CanaryMod
    public void setAge(int age) {
        this.aV = age;
    }

    //CanaryMod
    public void removeAllPotionEffects() {
        Iterator iterator = this.g.values().iterator();
        while (iterator.hasNext()) {
            PotionEffect potioneffect = (PotionEffect) iterator.next();
            this.b(potioneffect);
        }
        this.g.clear(); // Clear the map of effects now
    }

    //CanaryMod: Used to check if a ArmSwing animation is allowed to play (look above for S0BPacketAnimation)
    public boolean showAnimation() {
        return !this.ap || this.aq >= this.n() / 2 || this.aq < 0;
    }

    @Override
    public CanaryLivingBase getCanaryEntity() {
        if (this.entity == null || !(this.entity instanceof CanaryLivingBase)) {
            // Set the proper wrapper as needed
            this.entity =
                    new CanaryLivingBase(this) {

                        @Override
                        public EntityLivingBase getHandle() {
                            return (EntityLivingBase)entity;
                        }

                        @Override
                        public String getFqName() {
                            return "GenericLivingBase[" + getClass().getSimpleName() + "]";
                        }

                        @Override
                        public EntityType getEntityType() {
                            return EntityType.GENERIC_LIVING;
                        }
                    };
        }
        return (CanaryLivingBase)this.entity;
    }
}
