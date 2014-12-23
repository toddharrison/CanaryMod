package net.minecraft.entity.ai;


import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.canarymod.api.ai.CanaryAIMoveThroughVillage;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;


public class EntityAIMoveThroughVillage extends EntityAIBase {

    private EntityCreature a;
    private double b;
    private PathEntity c;
    private VillageDoorInfo d;
    private boolean e;
    private List f = Lists.newArrayList();
   
    public EntityAIMoveThroughVillage(EntityCreature entitycreature, double d0, boolean flag0) {
        this.a = entitycreature;
        this.b = d0;
        this.e = flag0;
        this.a(1);
        if (!(entitycreature.s() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
        }
        this.canaryAI = new CanaryAIMoveThroughVillage(this); //CanaryMod: set our variable
    }

    public boolean a() {
        this.f();
        if (this.e && this.a.o.w()) {
            return false;
        } else {
            Village village = this.a.o.ae().a(new BlockPos(this.a), 0);

            if (village == null) {
                return false;
            } else {
                this.d = this.a(village);
                if (this.d == null) {
                    return false;
                } else {
                    PathNavigateGround pathnavigateground = (PathNavigateGround) this.a.s();
                    boolean flag0 = pathnavigateground.g();

                    pathnavigateground.b(false);
                    this.c = pathnavigateground.a(this.d.d());
                    pathnavigateground.b(flag0);
                    if (this.c != null) {
                        return true;
                    } else {
                        Vec3 vec3 = RandomPositionGenerator.a(this.a, 10, 7, new Vec3((double) this.d.d().n(), (double) this.d.d().o(), (double) this.d.d().p()));

                        if (vec3 == null) {
                            return false;
                        } else {
                            pathnavigateground.b(false);
                            this.c = this.a.s().a(vec3.a, vec3.b, vec3.c);
                            pathnavigateground.b(flag0);
                            return this.c != null;
                        }
                    }
                }
            }
        }
    }

    public boolean b() {
        if (this.a.s().m()) {
            return false;
        } else {
            float f0 = this.a.J + 4.0F;

            return this.a.b(this.d.d()) > (double) (f0 * f0);
        }
    }

    public void c() {
        this.a.s().a(this.c, this.b);
    }

    public void d() {
        if (this.a.s().m() || this.a.b(this.d.d()) < 16.0D) {
            this.f.add(this.d);
        }

    }

    private VillageDoorInfo a(Village village) {
        VillageDoorInfo villagedoorinfo = null;
        int i0 = Integer.MAX_VALUE;
        List list = village.f();
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            VillageDoorInfo villagedoorinfo1 = (VillageDoorInfo) iterator.next();
            int i1 = villagedoorinfo1.b(MathHelper.c(this.a.s), MathHelper.c(this.a.t), MathHelper.c(this.a.u));

            if (i1 < i0 && !this.a(villagedoorinfo1)) {
                villagedoorinfo = villagedoorinfo1;
                i0 = i1;
            }
        }

        return villagedoorinfo;
    }

    private boolean a(VillageDoorInfo villagedoorinfo) {
        Iterator iterator = this.f.iterator();

        VillageDoorInfo villagedoorinfo1;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            villagedoorinfo1 = (VillageDoorInfo) iterator.next();
        } while (!villagedoorinfo.d().equals(villagedoorinfo1.d()));

        return true;
    }

    private void f() {
        if (this.f.size() > 15) {
            this.f.remove(0);
        }

    }
}
