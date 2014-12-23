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
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EntityZombie extends EntityMob {

    protected static final IAttribute b = (new RangedAttribute((IAttribute) null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).a("Spawn Reinforcements Chance");
    private static final UUID c = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
    private static final AttributeModifier bk = new AttributeModifier(c, "Baby speed boost", 0.5D, 1);
    private final EntityAIBreakDoor bl = new EntityAIBreakDoor(this);
    private int bm;
    private boolean bn = false;
    private float bo = -1.0F;
    private float bp;

    public EntityZombie(World world) {
        super(world);
        ((PathNavigateGround) this.s()).b(true);
        this.i.a(0, new EntityAISwimming(this));
        this.i.a(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.i.a(2, this.a);
        this.i.a(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.i.a(7, new EntityAIWander(this, 1.0D));
        this.i.a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(8, new EntityAILookIdle(this));
        this.n();
        this.a(0.6F, 1.95F);
        this.entity = new CanaryZombie(this); // CanaryMod: Wrap Entity
    }

    protected void n() {
        this.i.a(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
        this.i.a(4, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0D, true));
        this.i.a(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.bg.a(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityPigZombie.class}));
        this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.b).a(35.0D);
        this.a(SharedMonsterAttributes.d).a(0.23000000417232513D);
        this.a(SharedMonsterAttributes.e).a(3.0D);
        this.bx().b(b).a(this.V.nextDouble() * 0.10000000149011612D);
    }

    protected void h() {
        super.h();
        this.H().a(12, Byte.valueOf((byte) 0));
        this.H().a(13, Byte.valueOf((byte) 0));
        this.H().a(14, Byte.valueOf((byte) 0));
    }

    public int bq() {
        int i0 = super.bq() + 2;

        if (i0 > 20) {
            i0 = 20;
        }

        return i0;
    }

    public boolean cl() {
        return this.bn;
    }

    public void a(boolean flag0) {
        if (this.bn != flag0) {
            this.bn = flag0;
            if (flag0) {
                this.i.a(1, this.bl);
            }
            else {
                this.i.a((EntityAIBase) this.bl);
            }
        }

    }

    public boolean i_() {
        return this.H().a(12) == 1;
    }

    protected int b(EntityPlayer entityplayer) {
        if (this.i_()) {
            this.b_ = (int) ((float) this.b_ * 2.5F);
        }

        return super.b(entityplayer);
    }

    public void l(boolean flag0) {
        this.H().b(12, Byte.valueOf((byte) (flag0 ? 1 : 0)));
        if (this.o != null && !this.o.D) {
            IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

            iattributeinstance.c(bk);
            if (flag0) {
                iattributeinstance.b(bk);
            }
        }

        this.n(flag0);
    }

    public boolean cm() {
        return this.H().a(13) == 1;
    }

    public void m(boolean flag0) {
        this.H().b(13, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

    public void m() {
        if (this.o.w() && !this.o.D && !this.i_()) {
            float f0 = this.c(1.0F);
            BlockPos blockpos = new BlockPos(this.s, (double) Math.round(this.t), this.u);

            if (f0 > 0.5F && this.V.nextFloat() * 30.0F < (f0 - 0.4F) * 2.0F && this.o.i(blockpos)) {
                boolean flag0 = true;
                ItemStack itemstack = this.p(4);

                if (itemstack != null) {
                    if (itemstack.e()) {
                        itemstack.b(itemstack.h() + this.V.nextInt(2));
                        if (itemstack.h() >= itemstack.j()) {
                            this.b(itemstack);
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

        if (this.av() && this.u() != null && this.m instanceof EntityChicken) {
            ((EntityLiving) this.m).s().a(this.s().j(), 1.5D);
        }

        super.m();
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (super.a(damagesource, f0)) {
            EntityLivingBase entitylivingbase = this.u();

            if (entitylivingbase == null && damagesource.j() instanceof EntityLivingBase) {
                entitylivingbase = (EntityLivingBase) damagesource.j();
            }

            if (entitylivingbase != null && this.o.aa() == EnumDifficulty.HARD && (double) this.V.nextFloat() < this.a(b).e()) {
                int i0 = MathHelper.c(this.s);
                int i1 = MathHelper.c(this.t);
                int i2 = MathHelper.c(this.u);
                EntityZombie entityzombie = new EntityZombie(this.o);

                for (int i3 = 0; i3 < 50; ++i3) {
                    int i4 = i0 + MathHelper.a(this.V, 7, 40) * MathHelper.a(this.V, -1, 1);
                    int i5 = i1 + MathHelper.a(this.V, 7, 40) * MathHelper.a(this.V, -1, 1);
                    int i6 = i2 + MathHelper.a(this.V, 7, 40) * MathHelper.a(this.V, -1, 1);

                    if (World.a((IBlockAccess) this.o, new BlockPos(i4, i5 - 1, i6)) && this.o.l(new BlockPos(i4, i5, i6)) < 10) {
                        entityzombie.b((double) i4, (double) i5, (double) i6);
                        if (!this.o.b((double) i4, (double) i5, (double) i6, 7.0D) && this.o.a(entityzombie.aQ(), (Entity) entityzombie) && this.o.a((Entity) entityzombie, entityzombie.aQ()).isEmpty() && !this.o.d(entityzombie.aQ())) {
                            this.o.d((Entity) entityzombie);
                            entityzombie.d(entitylivingbase);
                            entityzombie.a(this.o.E(new BlockPos(entityzombie)), (IEntityLivingData) null);
                            this.a(b).b(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
                            entityzombie.a(b).b(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
                            break;
                        }
                    }
                }
            }

            return true;
        }
        else {
            return false;
        }
    }

    public void s_() {
        if (!this.o.D && this.cn()) {
            int i0 = this.cp();

            this.bm -= i0;
            if (this.bm <= 0) {
                this.co();
            }
        }

        super.s_();
    }

    public boolean r(Entity entity) {
        boolean flag0 = super.r(entity);

        if (flag0) {
            int i0 = this.o.aa().a();

            if (this.bz() == null && this.au() && this.V.nextFloat() < (float) i0 * 0.3F) {
                entity.e(2 * i0);
            }
        }

        return flag0;
    }

    protected String z() {
        return "mob.zombie.say";
    }

    protected String bn() {
        return "mob.zombie.hurt";
    }

    protected String bo() {
        return "mob.zombie.death";
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.zombie.step", 0.15F, 1.0F);
    }

    protected Item A() {
        return Items.bt;
    }

    public EnumCreatureAttribute by() {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected void bp() {
        switch (this.V.nextInt(3)) {
            case 0:
                this.a(Items.j, 1);
                break;

            case 1:
                this.a(Items.bR, 1);
                break;

            case 2:
                this.a(Items.bS, 1);
        }

    }

    protected void a(DifficultyInstance difficultyinstance) {
        super.a(difficultyinstance);
        if (this.V.nextFloat() < (this.o.aa() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
            int i0 = this.V.nextInt(3);

            if (i0 == 0) {
                this.c(0, new ItemStack(Items.l));
            }
            else {
                this.c(0, new ItemStack(Items.a));
            }
        }

    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.i_()) {
            nbttagcompound.a("IsBaby", true);
        }

        if (this.cm()) {
            nbttagcompound.a("IsVillager", true);
        }

        nbttagcompound.a("ConversionTime", this.cn() ? this.bm : -1);
        nbttagcompound.a("CanBreakDoors", this.cl());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.n("IsBaby")) {
            this.l(true);
        }

        if (nbttagcompound.n("IsVillager")) {
            this.m(true);
        }

        if (nbttagcompound.b("ConversionTime", 99) && nbttagcompound.f("ConversionTime") > -1) {
            this.a(nbttagcompound.f("ConversionTime"));
        }

        this.a(nbttagcompound.n("CanBreakDoors"));
    }

    public void a(EntityLivingBase entitylivingbase) {
        super.a(entitylivingbase);
        if ((this.o.aa() == EnumDifficulty.NORMAL || this.o.aa() == EnumDifficulty.HARD) && entitylivingbase instanceof EntityVillager) {
            if (this.o.aa() != EnumDifficulty.HARD && this.V.nextBoolean()) {
                return;
            }

            EntityZombie entityzombie = new EntityZombie(this.o);

            entityzombie.m(entitylivingbase);
            this.o.e((Entity) entitylivingbase);
            entityzombie.a(this.o.E(new BlockPos(entityzombie)), (IEntityLivingData) null);
            entityzombie.m(true);
            if (entitylivingbase.i_()) {
                entityzombie.l(true);
            }

            this.o.d((Entity) entityzombie);
            this.o.a((EntityPlayer) null, 1016, new BlockPos((int) this.s, (int) this.t, (int) this.u), 0);
        }

    }

    public float aR() {
        float f0 = 1.74F;

        if (this.i_()) {
            f0 = (float) ((double) f0 - 0.81D);
        }

        return f0;
    }

    protected boolean a(ItemStack itemstack) {
        return itemstack.b() == Items.aP && this.i_() && this.av() ? false : super.a(itemstack);
    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        Object ientitylivingdata1 = super.a(difficultyinstance, ientitylivingdata);
        float f0 = difficultyinstance.c();

        this.j(this.V.nextFloat() < 0.55F * f0);
        if (ientitylivingdata1 == null) {
            ientitylivingdata1 = new EntityZombie.GroupData(this.o.s.nextFloat() < 0.05F, this.o.s.nextFloat() < 0.05F, null);
        }

        if (ientitylivingdata1 instanceof EntityZombie.GroupData) {
            EntityZombie.GroupData entityzombie_groupdata = (EntityZombie.GroupData) ientitylivingdata1;

            if (entityzombie_groupdata.b) {
                this.m(true);
            }

            if (entityzombie_groupdata.a) {
                this.l(true);
                if ((double) this.o.s.nextFloat() < 0.05D) {
                    List list = this.o.a(EntityChicken.class, this.aQ().b(5.0D, 3.0D, 5.0D), IEntitySelector.b);

                    if (!list.isEmpty()) {
                        EntityChicken entitychicken = (EntityChicken) list.get(0);

                        entitychicken.l(true);
                        this.a((Entity) entitychicken);
                    }
                }
                else if ((double) this.o.s.nextFloat() < 0.05D) {
                    EntityChicken entitychicken1 = new EntityChicken(this.o);

                    entitychicken1.b(this.s, this.t, this.u, this.y, 0.0F);
                    entitychicken1.a(difficultyinstance, (IEntityLivingData) null);
                    entitychicken1.l(true);
                    this.o.d((Entity) entitychicken1);
                    this.a((Entity) entitychicken1);
                }
            }
        }

        this.a(this.V.nextFloat() < f0 * 0.1F);
        this.a(difficultyinstance);
        this.b(difficultyinstance);
        if (this.p(4) == null) {
            Calendar calendar = this.o.Y();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.V.nextFloat() < 0.25F) {
                this.c(4, new ItemStack(this.V.nextFloat() < 0.1F ? Blocks.aZ : Blocks.aU));
                this.bh[4] = 0.0F;
            }
        }

        this.a(SharedMonsterAttributes.c).b(new AttributeModifier("Random spawn bonus", this.V.nextDouble() * 0.05000000074505806D, 0));
        double d0 = this.V.nextDouble() * 1.5D * (double) f0;

        if (d0 > 1.0D) {
            this.a(SharedMonsterAttributes.b).b(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
        }

        if (this.V.nextFloat() < f0 * 0.05F) {
            this.a(b).b(new AttributeModifier("Leader zombie bonus", this.V.nextDouble() * 0.25D + 0.5D, 0));
            this.a(SharedMonsterAttributes.a).b(new AttributeModifier("Leader zombie bonus", this.V.nextDouble() * 3.0D + 1.0D, 2));
            this.a(true);
        }

        return (IEntityLivingData) ientitylivingdata1;
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bY();

        if (itemstack != null && itemstack.b() == Items.ao && itemstack.i() == 0 && this.cm() && this.a(Potion.t)) {
            if (!entityplayer.by.d) {
                --itemstack.b;
            }

            if (itemstack.b <= 0) {
                entityplayer.bg.a(entityplayer.bg.c, (ItemStack) null);
            }

            if (!this.o.D) {
                this.a(this.V.nextInt(2401) + 3600);
            }

            return true;
        }
        else {
            return false;
        }
    }

    protected void a(int i0) {
        this.bm = i0;
        this.H().b(14, Byte.valueOf((byte) 1));
        this.m(Potion.t.H);
        this.c(new PotionEffect(Potion.g.H, i0, Math.min(this.o.aa().a() - 1, 0)));
        this.o.a((Entity) this, (byte) 16);
    }

    protected boolean C() {
        return !this.cn();
    }

    public boolean cn() {
        return this.H().a(14) == 1;
    }

    protected void co() {
        EntityVillager entityvillager = new EntityVillager(this.o);

        entityvillager.m(this);
        entityvillager.a(this.o.E(new BlockPos(entityvillager)), (IEntityLivingData) null);
        entityvillager.cn();
        if (this.i_()) {
            entityvillager.b(-24000);
        }

        this.o.e((Entity) this);
        this.o.d((Entity) entityvillager);
        entityvillager.c(new PotionEffect(Potion.k.H, 200, 0));
        this.o.a((EntityPlayer) null, 1017, new BlockPos((int) this.s, (int) this.t, (int) this.u), 0);
    }

    protected int cp() {
        int i0 = 1;

        if (this.V.nextFloat() < 0.01F) {
            int i1 = 0;

            for (int i2 = (int) this.s - 4; i2 < (int) this.s + 4 && i1 < 14; ++i2) {
                for (int i3 = (int) this.t - 4; i3 < (int) this.t + 4 && i1 < 14; ++i3) {
                    for (int i4 = (int) this.u - 4; i4 < (int) this.u + 4 && i1 < 14; ++i4) {
                        Block block = this.o.p(new BlockPos(i2, i3, i4)).c();

                        if (block == Blocks.bi || block == Blocks.C) {
                            if (this.V.nextFloat() < 0.3F) {
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

    public void n(boolean flag0) {
        this.a(flag0 ? 0.5F : 1.0F);
    }

    protected final void a(float f0, float f1) {
        boolean flag0 = this.bo > 0.0F && this.bp > 0.0F;

        this.bo = f0;
        this.bp = f1;
        if (!flag0) {
            this.a(1.0F);
        }

    }

    protected final void a(float f0) {
        super.a(this.bo * f0, this.bp * f0);
    }

    public double am() {
        return super.am() - 0.5D;
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (damagesource.j() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper) damagesource.j()).n() && ((EntityCreeper) damagesource.j()).cn()) {
            ((EntityCreeper) damagesource.j()).co();
            this.a(new ItemStack(Items.bX, 1, 2), 0.0F);
        }

    }

    // CanaryMod
    public int getConvertTicks() {
        return this.bm;
    }
    
    public void setConversionTime(int i0) {
        this.a(i0);
    }

    public void stopConversion() {
        this.H().b(12, Byte.valueOf((byte) 0));
        this.bm = -1;
    }

    class GroupData implements IEntityLivingData {

        public boolean a;
        public boolean b;

        private GroupData(boolean p_i2348_2_, boolean p_i2348_3_) {
            this.a = false;
            this.b = false;
            this.a = p_i2348_2_;
            this.b = p_i2348_3_;
        }

        GroupData(boolean p_i2349_2_, boolean p_i2349_3_, Object p_i2349_4_) {
            this(p_i2349_2_, p_i2349_3_);
        }
    }
    //
}
