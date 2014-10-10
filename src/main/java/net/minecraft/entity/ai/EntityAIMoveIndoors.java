package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIMoveIndoors;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;


public class EntityAIMoveIndoors extends EntityAIBase {

    private EntityCreature a;
    private VillageDoorInfo b;
    private int c = -1;
    private int d = -1;
   
    public EntityAIMoveIndoors(EntityCreature entitycreature) {
        this.a = entitycreature;
        this.a(1);
        this.canaryAI = new CanaryAIMoveIndoors(this); //CanaryMod: set our variable
    }

    public boolean a() {
        BlockPos blockpos = new BlockPos(this.a);

        if ((!this.a.o.w() || this.a.o.S() && !this.a.o.b(blockpos).e()) && !this.a.o.t.o()) {
            if (this.a.bb().nextInt(50) != 0) {
                return false;
            } else if (this.c != -1 && this.a.e((double) this.c, this.a.t, (double) this.d) < 4.0D) {
                return false;
            } else {
                Village village = this.a.o.ae().a(blockpos, 14);

                if (village == null) {
                    return false;
                } else {
                    this.b = village.c(blockpos);
                    return this.b != null;
                }
            }
        } else {
            return false;
        }
    }

    public boolean b() {
        return !this.a.s().m();
    }

    public void c() {
        this.c = -1;
        BlockPos blockpos = this.b.e();
        int i0 = blockpos.n();
        int i1 = blockpos.o();
        int i2 = blockpos.p();

        if (this.a.b(blockpos) > 256.0D) {
            Vec3 vec3 = RandomPositionGenerator.a(this.a, 14, 3, new Vec3((double) i0 + 0.5D, (double) i1, (double) i2 + 0.5D));

            if (vec3 != null) {
                this.a.s().a(vec3.a, vec3.b, vec3.c, 1.0D);
            }
        } else {
            this.a.s().a((double) i0 + 0.5D, (double) i1, (double) i2 + 0.5D, 1.0D);
        }

    }

    public void d() {
        this.c = this.b.e().n();
        this.d = this.b.e().p();
        this.b = null;
    }
}
