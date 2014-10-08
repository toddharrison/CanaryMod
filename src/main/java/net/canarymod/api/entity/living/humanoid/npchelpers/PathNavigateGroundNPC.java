package net.canarymod.api.entity.living.humanoid.npchelpers;


import java.util.Iterator;
import net.canarymod.api.entity.living.humanoid.EntityNonPlayableCharacter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.WalkNodeProcessor;


public class PathNavigateGroundNPC extends PathNavigateNPC {

    protected WalkNodeProcessor a;
    private boolean f;
   
    public PathNavigateGroundNPC(EntityNonPlayableCharacter entityNPC, World world) {
        super(entityNPC, world);
    }

    @Override
    protected net.minecraft.pathfinding.PathFinder a() {
        this.a = new WalkNodeProcessor();
        this.a.a(true);
        return new PathFinder(this.a);
    }

    protected boolean b() {
        return this.b.C || this.h() && this.o();
    }

    protected Vec3 c() {
        return new Vec3(this.b.s, (double) this.p(), this.b.u);
    }

    private int p() {
        if (this.b.V() && this.h()) {
            int i0 = (int) this.b.aQ().b;
            Block block = this.c.p(new BlockPos(MathHelper.c(this.b.s), i0, MathHelper.c(this.b.u))).c();
            int i1 = 0;

            do {
                if (block != Blocks.i && block != Blocks.j) {
                    return i0;
                }

                ++i0;
                block = this.c.p(new BlockPos(MathHelper.c(this.b.s), i0, MathHelper.c(this.b.u))).c();
                ++i1;
            } while (i1 <= 16);

            return (int) this.b.aQ().b;
        } else {
            return (int) (this.b.aQ().b + 0.5D);
        }
    }

    protected void d() {
        super.d();
        if (this.f) {
            if (this.c.i(new BlockPos(MathHelper.c(this.b.s), (int) (this.b.aQ().b + 0.5D), MathHelper.c(this.b.u)))) {
                return;
            }

            for (int i0 = 0; i0 < this.d.d(); ++i0) {
                PathPoint pathpoint = this.d.a(i0);

                if (this.c.i(new BlockPos(pathpoint.a, pathpoint.b, pathpoint.c))) {
                    this.d.b(i0 - 1);
                    return;
                }
            }
        }

    }

    protected boolean a(Vec3 vec3, Vec3 vec31, int i0, int i1, int i2) {
        int i3 = MathHelper.c(vec3.a);
        int i4 = MathHelper.c(vec3.c);
        double d0 = vec31.a - vec3.a;
        double d1 = vec31.c - vec3.c;
        double d2 = d0 * d0 + d1 * d1;

        if (d2 < 1.0E-8D) {
            return false;
        } else {
            double d3 = 1.0D / Math.sqrt(d2);

            d0 *= d3;
            d1 *= d3;
            i0 += 2;
            i2 += 2;
            if (!this.a(i3, (int) vec3.b, i4, i0, i1, i2, vec3, d0, d1)) {
                return false;
            } else {
                i0 -= 2;
                i2 -= 2;
                double d4 = 1.0D / Math.abs(d0);
                double d5 = 1.0D / Math.abs(d1);
                double d6 = (double) (i3 * 1) - vec3.a;
                double d7 = (double) (i4 * 1) - vec3.c;

                if (d0 >= 0.0D) {
                    ++d6;
                }

                if (d1 >= 0.0D) {
                    ++d7;
                }

                d6 /= d0;
                d7 /= d1;
                int i5 = d0 < 0.0D ? -1 : 1;
                int i6 = d1 < 0.0D ? -1 : 1;
                int i7 = MathHelper.c(vec31.a);
                int i8 = MathHelper.c(vec31.c);
                int i9 = i7 - i3;
                int i10 = i8 - i4;

                do {
                    if (i9 * i5 <= 0 && i10 * i6 <= 0) {
                        return true;
                    }

                    if (d6 < d7) {
                        d6 += d4;
                        i3 += i5;
                        i9 = i7 - i3;
                    } else {
                        d7 += d5;
                        i4 += i6;
                        i10 = i8 - i4;
                    }
                } while (this.a(i3, (int) vec3.b, i4, i0, i1, i2, vec3, d0, d1));

                return false;
            }
        }
    }

    private boolean a(int i0, int i1, int i2, int i3, int i4, int i5, Vec3 vec3, double d0, double d1) {
        int i6 = i0 - i3 / 2;
        int i7 = i2 - i5 / 2;

        if (!this.b(i6, i1, i7, i3, i4, i5, vec3, d0, d1)) {
            return false;
        } else {
            for (int i8 = i6; i8 < i6 + i3; ++i8) {
                for (int i9 = i7; i9 < i7 + i5; ++i9) {
                    double d2 = (double) i8 + 0.5D - vec3.a;
                    double d3 = (double) i9 + 0.5D - vec3.c;

                    if (d2 * d0 + d3 * d1 >= 0.0D) {
                        Block block = this.c.p(new BlockPos(i8, i1 - 1, i9)).c();
                        Material material = block.r();

                        if (material == Material.a) {
                            return false;
                        }

                        if (material == Material.h && !this.b.V()) {
                            return false;
                        }

                        if (material == Material.i) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean b(int i0, int i1, int i2, int i3, int i4, int i5, Vec3 vec3, double d0, double d1) {
        Iterator iterator = BlockPos.a(new BlockPos(i0, i1, i2), new BlockPos(i0 + i3 - 1, i1 + i4 - 1, i2 + i5 - 1)).iterator();

        while (iterator.hasNext()) {
            BlockPos blockpos = (BlockPos) iterator.next();
            double d2 = (double) blockpos.n() + 0.5D - vec3.a;
            double d3 = (double) blockpos.p() + 0.5D - vec3.c;

            if (d2 * d0 + d3 * d1 >= 0.0D) {
                Block block = this.c.p(blockpos).c();

                if (!block.b(this.c, blockpos)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void a(boolean flag0) {
        this.a.c(flag0);
    }

    public boolean e() {
        return this.a.e();
    }

    public void b(boolean flag0) {
        this.a.b(flag0);
    }

    public void c(boolean flag0) {
        this.a.a(flag0);
    }

    public boolean g() {
        return this.a.b();
    }

    public void d(boolean flag0) {
        this.a.d(flag0);
    }

    public boolean h() {
        return this.a.d();
    }

    public void e(boolean flag0) {
        this.f = flag0;
    }

}
