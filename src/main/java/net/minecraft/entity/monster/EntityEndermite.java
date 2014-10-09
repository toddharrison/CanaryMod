package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryEndermite;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityEndermite extends EntityMob {

    private int b = 0;
    private boolean c = false;

    public EntityEndermite(World world) {
        super(world);
        this.b_ = 3;
        this.a(0.4F, 0.3F);
        this.i.a(1, new EntityAISwimming(this));
        this.i.a(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.i.a(3, new EntityAIWander(this, 1.0D));
        this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(8, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.entity = new CanaryEndermite(this);  // CanaryMod: Wrap Entity
    }

    public float aR() {
        return 0.1F;
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(8.0D);
        this.a(SharedMonsterAttributes.d).a(0.25D);
        this.a(SharedMonsterAttributes.e).a(2.0D);
    }

    protected boolean r_() {
        return false;
    }

    protected String z() {
        return "mob.silverfish.say";
    }

    protected String bn() {
        return "mob.silverfish.hit";
    }

    protected String bo() {
        return "mob.silverfish.kill";
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.silverfish.step", 0.15F, 1.0F);
    }

    protected Item A() {
        return null;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.b = nbttagcompound.f("Lifetime");
        this.c = nbttagcompound.n("PlayerSpawned");
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Lifetime", this.b);
        nbttagcompound.a("PlayerSpawned", this.c);
    }

    public void s_() {
        this.aG = this.y;
        super.s_();
    }

    public boolean n() {
        return this.c;
    }

    public void a(boolean flag0) {
        this.c = flag0;
    }

    public void m() {
        super.m();
        if (this.o.D) {
            for (int i0 = 0; i0 < 2; ++i0) {
                this.o.a(EnumParticleTypes.PORTAL, this.s + (this.V.nextDouble() - 0.5D) * (double)this.J, this.t + this.V.nextDouble() * (double)this.K, this.u + (this.V.nextDouble() - 0.5D) * (double)this.J, (this.V.nextDouble() - 0.5D) * 2.0D, -this.V.nextDouble(), (this.V.nextDouble() - 0.5D) * 2.0D, new int[0]);
            }
        }
        else {
            if (!this.bY()) {
                ++this.b;
            }

            if (this.b >= 2400) {
                this.J();
            }
        }
    }

    protected boolean m_() {
        return true;
    }

    public boolean bQ() {
        if (super.bQ()) {
            EntityPlayer entityplayer = this.o.a(this, 5.0D);

            return entityplayer == null;
        }
        else {
            return false;
        }
    }

    public EnumCreatureAttribute by() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
}
