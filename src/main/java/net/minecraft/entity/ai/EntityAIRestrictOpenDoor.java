package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIRestrictOpenDoor;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;


public class EntityAIRestrictOpenDoor extends EntityAIBase {

    private EntityCreature a;
    private VillageDoorInfo b;
   
    public EntityAIRestrictOpenDoor(EntityCreature entitycreature) {
        this.a = entitycreature;
        if (!(entitycreature.s() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
        }
        this.canaryAI = new CanaryAIRestrictOpenDoor(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.a.o.w()) {
            return false;
        } else {
            BlockPos blockpos = new BlockPos(this.a);
            Village village = this.a.o.ae().a(blockpos, 16);

            if (village == null) {
                return false;
            } else {
                this.b = village.b(blockpos);
                return this.b == null ? false : (double) this.b.b(blockpos) < 2.25D;
            }
        }
    }

    public boolean b() {
        return this.a.o.w() ? false : !this.b.i() && this.b.c(new BlockPos(this.a));
    }

    public void c() {
        ((PathNavigateGround) this.a.s()).b(false);
        ((PathNavigateGround) this.a.s()).c(false);
    }

    public void d() {
        ((PathNavigateGround) this.a.s()).b(true);
        ((PathNavigateGround) this.a.s()).c(true);
        this.b = null;
    }

    public void e() {
        this.b.b();
    }
}
