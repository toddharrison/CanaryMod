package net.minecraft.pathfinding;

import net.canarymod.api.CanaryPathFinder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class PathNavigate {

    protected EntityLiving b;
    protected World c;
    protected PathEntity d;
    public double e; //CanaryMod: protected>>public
    private final IAttributeInstance a;
    private int f;
    private int g;
    private Vec3 h = new Vec3(0.0D, 0.0D, 0.0D);
    private float i = 1.0F;
    private final PathFinder j;

    private CanaryPathFinder nav = new CanaryPathFinder(this); // CanaryMod: our var

    public PathNavigate(EntityLiving entityliving, World world) {
        this.b = entityliving;
        this.c = world;
        this.a = entityliving.a(SharedMonsterAttributes.b);
        this.j = this.a();
    }

    protected abstract PathFinder a();

    public void a(double d0) {
        this.e = d0;
    }

    public float i() {
        return (float) this.a.e();
    }

    public final PathEntity a(double d0, double d1, double d2) {
        return this.a(new BlockPos(MathHelper.c(d0), (int) d1, MathHelper.c(d2)));
    }

    public PathEntity a(BlockPos blockpos) {
        if (!this.b()) {
            return null;
        }
        else {
            float f0 = this.i();

            this.c.B.a("pathfind");
            BlockPos blockpos1 = new BlockPos(this.b);
            int i0 = (int) (f0 + 8.0F);
            ChunkCache chunkcache = new ChunkCache(this.c, blockpos1.a(-i0, -i0, -i0), blockpos1.a(i0, i0, i0), 0);
            PathEntity pathentity = this.j.a((IBlockAccess) chunkcache, (Entity) this.b, blockpos, f0);

            this.c.B.b();
            return pathentity;
        }
    }

    public boolean a(double d0, double d1, double d2, double d3) {
        PathEntity pathentity = this.a((double) MathHelper.c(d0), (double) ((int) d1), (double) MathHelper.c(d2));

        return this.a(pathentity, d3);
    }

    public void a(float f0) {
        this.i = f0;
    }

    public PathEntity a(Entity entity) {
        if (!this.b()) {
            return null;
        }
        else {
            float f0 = this.i();

            this.c.B.a("pathfind");
            BlockPos blockpos = (new BlockPos(this.b)).a();
            int i0 = (int) (f0 + 16.0F);
            ChunkCache chunkcache = new ChunkCache(this.c, blockpos.a(-i0, -i0, -i0), blockpos.a(i0, i0, i0), 0);
            PathEntity pathentity = this.j.a((IBlockAccess) chunkcache, (Entity) this.b, entity, f0);

            this.c.B.b();
            return pathentity;
        }
    }

    public boolean a(Entity entity, double d0) {
        PathEntity pathentity = this.a(entity);

        return pathentity != null ? this.a(pathentity, d0) : false;
    }

    public boolean a(PathEntity pathentity, double d0) {
        if (pathentity == null) {
            this.d = null;
            // Canary.logInfo("path was null");
            return false;
        }
        else {
            if (!pathentity.a(this.d)) {
                this.d = pathentity;
            }

            this.d();
            if (this.d.d() == 0) {
                return false;
            }
            else {
                this.e = d0;
                Vec3 vec3 = this.c();

                this.g = this.f;
                this.h = vec3;
                return true;
            }
        }
    }

    public PathEntity j() {
        return this.d;
    }

    public void k() {
        ++this.f;
        if (!this.m()) {
            Vec3 vec3;

            if (this.b()) {
                this.l();
            }
            else if (this.d != null && this.d.e() < this.d.d()) {
                vec3 = this.c();
                Vec3 vec31 = this.d.a(this.b, this.d.e());

                if (vec3.b > vec31.b && !this.b.C && MathHelper.c(vec3.a) == MathHelper.c(vec31.a) && MathHelper.c(vec3.c) == MathHelper.c(vec31.c)) {
                    this.d.c(this.d.e() + 1);
                }
            }

            if (!this.m()) {
                vec3 = this.d.a((Entity) this.b);
                if (vec3 != null) {
                    this.b.q().a(vec3.a, vec3.b, vec3.c, this.e);
                }
            }
        }
    }

    protected void l() {
        Vec3 vec3 = this.c();
        int i0 = this.d.d();

        for (int i1 = this.d.e(); i1 < this.d.d(); ++i1) {
            if (this.d.a(i1).b != (int) vec3.b) {
                i0 = i1;
                break;
            }
        }

        float f0 = this.b.J * this.b.J * this.i;

        int i2;

        for (i2 = this.d.e(); i2 < i0; ++i2) {
            Vec3 vec31 = this.d.a(this.b, i2);

            if (vec3.g(vec31) < (double) f0) {
                this.d.c(i2 + 1);
            }
        }

        i2 = MathHelper.f(this.b.J);
        int i3 = (int) this.b.K + 1;
        int i4 = i2;

        for (int i5 = i0 - 1; i5 >= this.d.e(); --i5) {
            if (this.a(vec3, this.d.a(this.b, i5), i2, i3, i4)) {
                this.d.c(i5);
                break;
            }
        }

        this.a(vec3);
    }

    protected void a(Vec3 vec3) {
        if (this.f - this.g > 100) {
            if (vec3.g(this.h) < 2.25D) {
                this.n();
            }

            this.g = this.f;
            this.h = vec3;
        }

    }

    public boolean m() {
        return this.d == null || this.d.b();
    }

    public void n() {
        this.d = null;
    }

    protected abstract Vec3 c();

    protected abstract boolean b();

    protected boolean o() {
        return this.b.V() || this.b.ab();
    }

    protected void d() {
    }

    protected abstract boolean a(Vec3 vec3, Vec3 vec31, int i0, int i1, int i2);

    // CanaryMod
    public CanaryPathFinder getCanaryPathFinder() {
        return this.nav;
    }
}
