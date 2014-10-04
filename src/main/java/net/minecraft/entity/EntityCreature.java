package net.minecraft.entity;

import net.canarymod.hook.entity.MobTargetHook;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityCreature extends EntityLiving {

    public static final UUID bi = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
    public static final AttributeModifier bj = (new AttributeModifier(bi, "Fleeing speed bonus", 2.0D, 2)).a(false);
    private BlockPos a;
    private float b;
    private EntityAIBase c;
    private boolean bk;
   
    public EntityCreature(World world) {
        super(world);
        this.a = BlockPos.a;
        this.b = -1.0F;
        this.c = new EntityAIMoveTowardsRestriction(this, 1.0D);
    }

    public float a(BlockPos blockpos) {
        return 0.0F;
    }

    public boolean bQ() {
        return super.bQ() && this.a(new BlockPos(this.s, this.aQ().b, this.u)) >= 0.0F;
    }

    public boolean cd() {
        return !this.h.m();
    }

    public boolean ce() {
        return this.d(new BlockPos(this));
    }

    public boolean d(BlockPos blockpos) {
        return this.b == -1.0F ? true : this.a.i(blockpos) < (double) (this.b * this.b);
    }

    public void a(BlockPos blockpos, int i0) {
        this.a = blockpos;
        this.b = (float) i0;
    }

    public BlockPos cf() {
        return this.a;
    }

    public float cg() {
        return this.b;
    }

    public void ch() {
        this.b = -1.0F;
    }

    public boolean ci() {
        return this.b != -1.0F;
    }

    protected void bZ() {
        super.bZ();
        if (this.cb() && this.cc() != null && this.cc().o == this.o) {
            Entity entity = this.cc();

            this.a(new BlockPos((int) entity.s, (int) entity.t, (int) entity.u), 5);
            float f0 = this.g(entity);

            if (this instanceof EntityTameable && ((EntityTameable) this).cl()) {
                if (f0 > 10.0F) {
                    this.a(true, true);
                }

                return;
            }

            if (!this.bk) {
                this.i.a(2, this.c);
                if (this.s() instanceof PathNavigateGround) {
                    ((PathNavigateGround) this.s()).a(false);
                }

                this.bk = true;
            }

            this.n(f0);
            if (f0 > 4.0F) {
                this.s().a(entity, 1.0D);
            }

            if (f0 > 6.0F) {
                double d0 = (entity.s - this.s) / (double) f0;
                double d1 = (entity.t - this.t) / (double) f0;
                double d2 = (entity.u - this.u) / (double) f0;

                this.v += d0 * Math.abs(d0) * 0.4D;
                this.w += d1 * Math.abs(d1) * 0.4D;
                this.x += d2 * Math.abs(d2) * 0.4D;
            }

            if (f0 > 10.0F) {
                this.a(true, true);
            }
        } else if (!this.cb() && this.bk) {
            this.bk = false;
            this.i.a(this.c);
            if (this.s() instanceof PathNavigateGround) {
                ((PathNavigateGround) this.s()).a(true);
            }

            this.ch();
        }

    }

    protected void n(float f0) {}

}
