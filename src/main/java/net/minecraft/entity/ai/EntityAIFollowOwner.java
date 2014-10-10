package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIFollowOwner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class EntityAIFollowOwner extends EntityAIBase {

    private EntityTameable d;
    private EntityLivingBase e;
    World a;
    private double f;
    private PathNavigate g;
    private int h;
    float b;
    float c;
    private boolean i;
   
    public EntityAIFollowOwner(EntityTameable entitytameable, double d0, float f0, float f1) {
        this.d = entitytameable;
        this.a = entitytameable.o;
        this.f = d0;
        this.g = entitytameable.s();
        this.c = f0;
        this.b = f1;
        this.a(3);
        if (!(entitytameable.s() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
        this.canaryAI = new CanaryAIFollowOwner(this); //CanaryMod: set our variable
    }

    public boolean a() {
        EntityLivingBase entitylivingbase = this.d.cm();

        if (entitylivingbase == null) {
            return false;
        } else if (this.d.cl()) {
            return false;
        } else if (this.d.h(entitylivingbase) < (double) (this.c * this.c)) {
            return false;
        } else {
            this.e = entitylivingbase;
            return true;
        }
    }

    public boolean b() {
        return !this.g.m() && this.d.h(this.e) > (double) (this.b * this.b) && !this.d.cl();
    }

    public void c() {
        this.h = 0;
        this.i = ((PathNavigateGround) this.d.s()).e();
        ((PathNavigateGround) this.d.s()).a(false);
    }

    public void d() {
        this.e = null;
        this.g.n();
        ((PathNavigateGround) this.d.s()).a(true);
    }

    public void e() {
        this.d.p().a(this.e, 10.0F, (float) this.d.bP());
        if (!this.d.cl()) {
            if (--this.h <= 0) {
                this.h = 10;
                if (!this.g.a((Entity) this.e, this.f)) {
                    if (!this.d.cb()) {
                        if (this.d.h(this.e) >= 144.0D) {
                            int i0 = MathHelper.c(this.e.s) - 2;
                            int i1 = MathHelper.c(this.e.u) - 2;
                            int i2 = MathHelper.c(this.e.aQ().b);

                            for (int i3 = 0; i3 <= 4; ++i3) {
                                for (int i4 = 0; i4 <= 4; ++i4) {
                                    if ((i3 < 1 || i4 < 1 || i3 > 3 || i4 > 3) && World.a((IBlockAccess) this.a, new BlockPos(i0 + i3, i2 - 1, i1 + i4)) && !this.a.p(new BlockPos(i0 + i3, i2, i1 + i4)).c().d() && !this.a.p(new BlockPos(i0 + i3, i2 + 1, i1 + i4)).c().d()) {
                                        this.d.b((double) ((float) (i0 + i3) + 0.5F), (double) i2, (double) ((float) (i1 + i4) + 0.5F), this.d.y, this.d.z);
                                        this.g.n();
                                        return;
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
