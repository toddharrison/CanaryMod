package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryZombie;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EntityZombie extends EntityMob {

    protected static final IAttribute bp = (new RangedAttribute("zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).a("Spawn Reinforcements Chance");
    private static final UUID bq = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier br = new AttributeModifier(bq, "Baby speed boost", 0.5D, 1);
    private final EntityAIBreakDoor bs = new EntityAIBreakDoor(this);
    private int bt;
    private boolean bu = false;
    private float bv = -1.0F;
    private float bw;

    public EntityZombie(World world) {
        super(world);
        this.m().b(true);
        this.c.a(0, new EntityAISwimming(this));
        this.c.a(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.c.a(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
        this.c.a(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.c.a(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.c.a(7, new EntityAIWander(this, 1.0D));
        this.c.a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.c.a(8, new EntityAILookIdle(this));
        this.d.a(1, new EntityAIHurtByTarget(this, true));
        this.d.a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.d.a(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.a(0.6F, 1.8F);
        this.entity = new CanaryZombie(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.b).a(40.0D);
        this.a(SharedMonsterAttributes.d).a(0.23000000417232513D);
        this.a(SharedMonsterAttributes.e).a(3.0D);
        this.bc().b(bp).a(this.Z.nextDouble() * 0.10000000149011612D);
    }

    protected void c() {
        super.c();
        this.z().a(12, Byte.valueOf((byte) 0));
        this.z().a(13, Byte.valueOf((byte) 0));
        this.z().a(14, Byte.valueOf((byte) 0));
    }

    public int aV() {
        int i0 = super.aV() + 2;

        if (i0 > 20) {
            i0 = 20;
        }

        return i0;
    }

    protected boolean bk() {
        return true;
    }

    public boolean bZ() {
        return this.bu;
    }

    public void a(boolean flag0) {
        if (this.bu != flag0) {
            this.bu = flag0;
            if (flag0) {
                this.c.a(1, this.bs);
            } else {
                this.c.a((EntityAIBase) this.bs);
            }
        }

    }

    public boolean f() {
        return this.z().a(12) == 1;
    }

    protected int e(EntityPlayer entityplayer) {
        if (this.f()) {
            this.b = (int) ((float) this.b * 2.5F);
        }

        return super.e(entityplayer);
    }

    public void i(boolean flag0) {
        this.z().b(12, Byte.valueOf((byte) (flag0 ? 1 : 0)));
        if (this.o != null && !this.o.E) {
            IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

            iattributeinstance.b(br);
            if (flag0) {
                iattributeinstance.a(br);
            }
        }
        this.k(flag0);
    }

    public boolean cb() {
        return this.z().a(13) == 1;
    }

    public void j(boolean flag0) {
        this.z().b(13, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

    public void e() {
        if (this.o.w() && !this.o.E && !this.f()) {
            float f0 = this.d(1.0F);

            if (f0 > 0.5F && this.Z.nextFloat() * 30.0F < (f0 - 0.4F) * 2.0F && this.o.i(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u))) {
                boolean flag0 = true;
                ItemStack itemstack = this.q(4);

                if (itemstack != null) {
                    if (itemstack.g()) {
                        itemstack.b(itemstack.j() + this.Z.nextInt(2));
                        if (itemstack.j() >= itemstack.l()) {
                            this.a(itemstack);
                            this.c(4, (ItemStack) null);
                        }
                    }

                    flag0 = false;
                }

                if (flag0) {
                    this.e(8);
                }
            }
        }

        if (this.am() && this.o() != null && this.m instanceof EntityChicken) {
            ((EntityLiving) this.m).m().a(this.m().e(), 1.5D);
        }

        super.e();
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (!super.a(damagesource, f0)) {
            return false;
        } else {
            EntityLivingBase entitylivingbase = this.o();

            if (entitylivingbase == null && this.bT() instanceof EntityLivingBase) {
                entitylivingbase = (EntityLivingBase) this.bT();
            }

            if (entitylivingbase == null && damagesource.j() instanceof EntityLivingBase) {
                entitylivingbase = (EntityLivingBase) damagesource.j();
            }

            if (entitylivingbase != null && this.o.r == EnumDifficulty.HARD && (double) this.Z.nextFloat() < this.a(bp).e()) {
                int i0 = MathHelper.c(this.s);
                int i1 = MathHelper.c(this.t);
                int i2 = MathHelper.c(this.u);
                EntityZombie entityzombie = new EntityZombie(this.o);

                for (int i3 = 0; i3 < 50; ++i3) {
                    int i4 = i0 + MathHelper.a(this.Z, 7, 40) * MathHelper.a(this.Z, -1, 1);
                    int i5 = i1 + MathHelper.a(this.Z, 7, 40) * MathHelper.a(this.Z, -1, 1);
                    int i6 = i2 + MathHelper.a(this.Z, 7, 40) * MathHelper.a(this.Z, -1, 1);

                    if (World.a((IBlockAccess) this.o, i4, i5 - 1, i6) && this.o.k(i4, i5, i6) < 10) {
                        entityzombie.b((double) i4, (double) i5, (double) i6);
                        if (this.o.b(entityzombie.C) && this.o.a((Entity) entityzombie, entityzombie.C).isEmpty() && !this.o.d(entityzombie.C)) {
                            this.o.d((Entity) entityzombie);
                            entityzombie.d(entitylivingbase);
                            entityzombie.a((IEntityLivingData) null);
                            this.a(bp).a(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
                            entityzombie.a(bp).a(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
                            break;
                        }
                    }
                }
            }

            return true;
        }
    }

    public void h() {
        if (!this.o.E && this.cc()) {
            int i0 = this.ce();

            this.bt -= i0;
            if (this.bt <= 0) {
                this.cd();
            }
        }

        super.h();
    }

    public boolean n(Entity entity) {
        boolean flag0 = super.n(entity);

        if (flag0) {
            int i0 = this.o.r.a();

            if (this.be() == null && this.al() && this.Z.nextFloat() < (float) i0 * 0.3F) {
                entity.e(2 * i0);
            }
        }

        return flag0;
    }

    protected String t() {
        return "mob.zombie.say";
    }

    protected String aT() {
        return "mob.zombie.hurt";
    }

    protected String aU() {
        return "mob.zombie.death";
    }

    protected void a(int i0, int i1, int i2, Block block) {
        this.a("mob.zombie.step", 0.15F, 1.0F);
    }

    protected Item u() {
        return Items.bh;
    }

    public EnumCreatureAttribute bd() {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected void n(int i0) {
        switch (this.Z.nextInt(3)) {
            case 0:
                this.a(Items.j, 1);
                break;

            case 1:
                this.a(Items.bF, 1);
                break;

            case 2:
                this.a(Items.bG, 1);
        }
    }

    protected void bC() {
        super.bC();
        if (this.Z.nextFloat() < (this.o.r == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
            int i0 = this.Z.nextInt(3);

            if (i0 == 0) {
                this.c(0, new ItemStack(Items.l));
            } else {
                this.c(0, new ItemStack(Items.a));
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.f()) {
            nbttagcompound.a("IsBaby", true);
        }

        if (this.cb()) {
            nbttagcompound.a("IsVillager", true);
        }

        nbttagcompound.a("ConversionTime", this.cc() ? this.bt : -1);
        nbttagcompound.a("CanBreakDoors", this.bZ());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.n("IsBaby")) {
            this.i(true);
        }

        if (nbttagcompound.n("IsVillager")) {
            this.j(true);
        }

        if (nbttagcompound.b("ConversionTime", 99) && nbttagcompound.f("ConversionTime") > -1) {
            this.a(nbttagcompound.f("ConversionTime"));
        }
        this.a(nbttagcompound.n("CanBreakDoors"));
    }

    public void a(EntityLivingBase entitylivingbase) {
        super.a(entitylivingbase);
        if ((this.o.r == EnumDifficulty.NORMAL || this.o.r == EnumDifficulty.HARD) && entitylivingbase instanceof EntityVillager) {
            if (this.o.r != EnumDifficulty.HARD && this.Z.nextBoolean()) {
                return;
            }

            EntityZombie entityzombie = new EntityZombie(this.o);

            entityzombie.k(entitylivingbase);
            this.o.e((Entity) entitylivingbase);
            entityzombie.a((IEntityLivingData) null);
            entityzombie.j(true);
            if (entitylivingbase.f()) {
                entityzombie.i(true);
            }

            this.o.d((Entity) entityzombie);
            this.o.a((EntityPlayer) null, 1016, (int) this.s, (int) this.t, (int) this.u, 0);
        }
    }

    public IEntityLivingData a(IEntityLivingData ientitylivingdata) {
        Object ientitylivingdata1 = super.a(ientitylivingdata);
        float f0 = this.o.b(this.s, this.t, this.u);

        this.h(this.Z.nextFloat() < 0.55F * f0);
        if (ientitylivingdata1 == null) {
            ientitylivingdata1 = new EntityZombie.GroupData(this.o.s.nextFloat() < 0.05F, this.o.s.nextFloat() < 0.05F, null);
        }

        if (ientitylivingdata1 instanceof EntityZombie.GroupData) {
            EntityZombie.GroupData entityzombie_groupdata = (EntityZombie.GroupData) ientitylivingdata1;

            if (entityzombie_groupdata.b) {
                this.j(true);
            }

            if (entityzombie_groupdata.a) {
                this.i(true);
                if ((double) this.o.s.nextFloat() < 0.05D) {
                    List list = this.o.a(EntityChicken.class, this.C.b(5.0D, 3.0D, 5.0D), IEntitySelector.b);

                    if (!list.isEmpty()) {
                        EntityChicken entitychicken = (EntityChicken) list.get(0);

                        entitychicken.i(true);
                        this.a((Entity) entitychicken);
                    }
                } else if ((double) this.o.s.nextFloat() < 0.05D) {
                    EntityChicken entitychicken1 = new EntityChicken(this.o);

                    entitychicken1.b(this.s, this.t, this.u, this.y, 0.0F);
                    entitychicken1.a((IEntityLivingData) null);
                    entitychicken1.i(true);
                    this.o.d((Entity) entitychicken1);
                    this.a((Entity) entitychicken1);
                }
            }
        }

        this.a(this.Z.nextFloat() < f0 * 0.1F);
        this.bC();
        this.bD();
        if (this.q(4) == null) {
            Calendar calendar = this.o.V();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.Z.nextFloat() < 0.25F) {
                this.c(4, new ItemStack(this.Z.nextFloat() < 0.1F ? Blocks.aP : Blocks.aK));
                this.e[4] = 0.0F;
            }
        }

        this.a(SharedMonsterAttributes.c).a(new AttributeModifier("Random spawn bonus", this.Z.nextDouble() * 0.05000000074505806D, 0));
        double d0 = this.Z.nextDouble() * 1.5D * (double) this.o.b(this.s, this.t, this.u);

        if (d0 > 1.0D) {
            this.a(SharedMonsterAttributes.b).a(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
        }

        if (this.Z.nextFloat() < f0 * 0.05F) {
            this.a(bp).a(new AttributeModifier("Leader zombie bonus", this.Z.nextDouble() * 0.25D + 0.5D, 0));
            this.a(SharedMonsterAttributes.a).a(new AttributeModifier("Leader zombie bonus", this.Z.nextDouble() * 3.0D + 1.0D, 2));
            this.a(true);
        }

        return (IEntityLivingData) ientitylivingdata1;
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bF();

        if (itemstack != null && itemstack.b() == Items.ao && itemstack.k() == 0 && this.cb() && this.a(Potion.t)) {
            if (!entityplayer.bE.d) {
                --itemstack.b;
            }

            if (itemstack.b <= 0) {
                entityplayer.bm.a(entityplayer.bm.c, (ItemStack) null);
            }

            if (!this.o.E) {
                this.a(this.Z.nextInt(2401) + 3600);
            }

            return true;
        } else {
            return false;
        }
    }

    protected void a(int i0) {
        this.bt = i0;
        this.z().b(14, Byte.valueOf((byte) 1));
        this.m(Potion.t.H);
        this.c(new PotionEffect(Potion.g.H, i0, Math.min(this.o.r.a() - 1, 0)));
        this.o.a(this, (byte) 16);
    }

    protected boolean v() {
        return !this.cc();
    }

    public boolean cc() {
        return this.z().a(14) == 1;
    }

    public void cd() { // CanaryMod: protected => public
        EntityVillager entityvillager = new EntityVillager(this.o);

        entityvillager.k(this);
        entityvillager.a((IEntityLivingData) null);
        entityvillager.cd();
        if (this.f()) {
            entityvillager.c(-24000);
        }

        this.o.e((Entity) this);
        this.o.d((Entity) entityvillager);
        entityvillager.c(new PotionEffect(Potion.k.H, 200, 0));
        this.o.a((EntityPlayer) null, 1017, (int) this.s, (int) this.t, (int) this.u, 0);
    }

    protected int ce() {
        int i0 = 1;

        if (this.Z.nextFloat() < 0.01F) {
            int i1 = 0;

            for (int i2 = (int) this.s - 4; i2 < (int) this.s + 4 && i1 < 14; ++i2) {
                for (int i3 = (int) this.t - 4; i3 < (int) this.t + 4 && i1 < 14; ++i3) {
                    for (int i4 = (int) this.u - 4; i4 < (int) this.u + 4 && i1 < 14; ++i4) {
                        Block block = this.o.a(i2, i3, i4);

                        if (block == Blocks.aY || block == Blocks.C) {
                            if (this.Z.nextFloat() < 0.3F) {
                                ++i0;
                            }

                            ++i1;
                        }
                    }
                }
            }
        }

        return i0;
    }

    public void k(boolean flag0) {
        this.a(flag0 ? 0.5F : 1.0F);
    }

    protected final void a(float f0, float f1) {
        boolean flag0 = this.bv > 0.0F && this.bw > 0.0F;

        this.bv = f0;
        this.bw = f1;
        if (!flag0) {
            this.a(1.0F);
        }

    }

    protected final void a(float f0) {
        super.a(this.bv * f0, this.bw * f0);
    }

    class GroupData implements IEntityLivingData {

        public boolean a;
        public boolean b;

        private GroupData(boolean flag0, boolean flag1) {
            this.a = false;
            this.b = false;
            this.a = flag0;
            this.b = flag1;
        }

        GroupData(boolean flag0, boolean flag1, Object object) {
            this(flag0, flag1);
        }
    }

    // CanaryMod
    public int getConvertTicks() {
        return this.bt;
    }

    public void stopConversion() {
        this.z().b(12, Byte.valueOf((byte) 0));
        this.bt = -1;
    }
    //
}
