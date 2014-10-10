package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIDoorInteract;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;


public abstract class EntityAIDoorInteract extends EntityAIBase {

    protected EntityLiving a;
    protected BlockPos b;
    protected BlockDoor c;
    boolean d;
    float e;
    float f;
   
    public EntityAIDoorInteract(EntityLiving entityliving) {
        this.b = BlockPos.a;
        this.a = entityliving;
        if (!(entityliving.s() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
        this.canaryAI = new CanaryAIDoorInteract(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (!this.a.D) {
            return false;
        } else {
            PathNavigateGround pathnavigateground = (PathNavigateGround) this.a.s();
            PathEntity pathentity = pathnavigateground.j();

            if (pathentity != null && !pathentity.b() && pathnavigateground.g()) {
                for (int i0 = 0; i0 < Math.min(pathentity.e() + 2, pathentity.d()); ++i0) {
                    PathPoint pathpoint = pathentity.a(i0);

                    this.b = new BlockPos(pathpoint.a, pathpoint.b + 1, pathpoint.c);
                    if (this.a.e((double) this.b.n(), this.a.t, (double) this.b.p()) <= 2.25D) {
                        this.c = this.a(this.b);
                        if (this.c != null) {
                            return true;
                        }
                    }
                }

                this.b = (new BlockPos(this.a)).a();
                this.c = this.a(this.b);
                return this.c != null;
            } else {
                return false;
            }
        }
    }

    public boolean b() {
        return !this.d;
    }

    public void c() {
        this.d = false;
        this.e = (float) ((double) ((float) this.b.n() + 0.5F) - this.a.s);
        this.f = (float) ((double) ((float) this.b.p() + 0.5F) - this.a.u);
    }

    public void e() {
        float f0 = (float) ((double) ((float) this.b.n() + 0.5F) - this.a.s);
        float f1 = (float) ((double) ((float) this.b.p() + 0.5F) - this.a.u);
        float f2 = this.e * f0 + this.f * f1;

        if (f2 < 0.0F) {
            this.d = true;
        }

    }

    private BlockDoor a(BlockPos blockpos) {
        Block block = this.a.o.p(blockpos).c();

        return block instanceof BlockDoor && block.r() == Material.d ? (BlockDoor) block : null;
    }
}
