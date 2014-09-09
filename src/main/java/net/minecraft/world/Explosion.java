package net.minecraft.world;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.Explosive;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.world.ExplosionHook;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.*;

public class Explosion {

    public boolean a;
    public boolean b = true;
    private int i = 16;
    private Random j = new Random();
    private World k;
    public double c;
    public double d;
    public double e;
    public Entity f;
    public float g;
    public List h = new ArrayList();
    private Map l = new HashMap();

    public Explosion(World world, Entity entity, double d0, double d1, double d2, float f0) {
        this.k = world;
        this.f = entity;
        this.g = f0;
        this.c = d0;
        this.d = d1;
        this.e = d2;
    }

    @SuppressWarnings("unchecked")
    public void a() {
        float f0 = this.g;
        HashSet hashset = new HashSet();

        // CanaryMod: Ground Zero
        CanaryBlock gzero = (CanaryBlock) this.k.getCanaryWorld().getBlockAt((int) Math.floor(c), (int) Math.floor(d), (int) Math.floor(e));
        //

        int i0;
        int i1;
        int i2;
        double d0;
        double d1;
        double d2;

        for (i0 = 0; i0 < this.i; ++i0) {
            for (i1 = 0; i1 < this.i; ++i1) {
                for (i2 = 0; i2 < this.i; ++i2) {
                    if (i0 == 0 || i0 == this.i - 1 || i1 == 0 || i1 == this.i - 1 || i2 == 0 || i2 == this.i - 1) {
                        double d3 = (double) ((float) i0 / ((float) this.i - 1.0F) * 2.0F - 1.0F);
                        double d4 = (double) ((float) i1 / ((float) this.i - 1.0F) * 2.0F - 1.0F);
                        double d5 = (double) ((float) i2 / ((float) this.i - 1.0F) * 2.0F - 1.0F);
                        double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);

                        d3 /= d6;
                        d4 /= d6;
                        d5 /= d6;
                        float f1 = this.g * (0.7F + this.k.s.nextFloat() * 0.6F);

                        d0 = this.c;
                        d1 = this.d;
                        d2 = this.e;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
                            int i3 = MathHelper.c(d0);
                            int i4 = MathHelper.c(d1);
                            int i5 = MathHelper.c(d2);
                            Block block = this.k.a(i3, i4, i5);

                            if (block.o() != Material.a) {
                                float f3 = this.f != null ? this.f.a(this, this.k, i3, i4, i5, block) : block.a(this.f);

                                f1 -= (f3 + 0.3F) * f2;
                            }

                            if (f1 > 0.0F && (this.f == null || this.f.a(this, this.k, i3, i4, i5, block, f1))) {
                                hashset.add(new ChunkPosition(i3, i4, i5));
                            }

                            d0 += d3 * (double) f2;
                            d1 += d4 * (double) f2;
                            d2 += d5 * (double) f2;
                        }
                    }
                }
            }
        }

        // Check entity if instance of Explosive and can damage world
        //        null     or                non-Explosive                     or                      Explosive can damage world
        if (this.f == null || !(this.f.getCanaryEntity() instanceof Explosive) || ((Explosive) this.f.getCanaryEntity()).canDamageWorld()) {
            // CanaryMod: Add affected blocks into a List of Blocks.
            List<net.canarymod.api.world.blocks.Block> blkAff = new ArrayList<net.canarymod.api.world.blocks.Block>(hashset.size());

            for (ChunkPosition ocp : (HashSet<ChunkPosition>) hashset) {
                blkAff.add(this.k.getCanaryWorld().getBlockAt(ocp.a, ocp.b, ocp.c));
            }
            // Explosion call
            ExplosionHook exp = (ExplosionHook) new ExplosionHook(gzero, this.f != null ? this.f.getCanaryEntity() : null, blkAff).call();
            // if cancelled, don't populate this.h at all.
            if (!exp.isCanceled()) {
                // Repopulate hashset according to blocksAffected.
                hashset.clear();
                for (net.canarymod.api.world.blocks.Block affected : exp.getAffectedBlocks()) {
                    hashset.add(new ChunkPosition(affected.getX(), affected.getY(), affected.getZ()));
                }
                this.h.addAll(hashset);
            }
        }
        //
        this.g *= 2.0F;
        i0 = MathHelper.c(this.c - (double) this.g - 1.0D);
        i1 = MathHelper.c(this.c + (double) this.g + 1.0D);
        i2 = MathHelper.c(this.d - (double) this.g - 1.0D);
        int i6 = MathHelper.c(this.d + (double) this.g + 1.0D);
        int i7 = MathHelper.c(this.e - (double) this.g - 1.0D);
        int i8 = MathHelper.c(this.e + (double) this.g + 1.0D);
        List list = this.k.b(this.f, AxisAlignedBB.a().a((double) i0, (double) i2, (double) i7, (double) i1, (double) i6, (double) i8));
        Vec3 vec3 = this.k.U().a(this.c, this.d, this.e);

        for (int i9 = 0; i9 < list.size(); ++i9) {
            Entity entity = (Entity) list.get(i9);
            double d7 = entity.f(this.c, this.d, this.e) / (double) this.g;

            if (d7 <= 1.0D) {
                d0 = entity.t - this.c;
                d1 = entity.u + (double) entity.g() - this.d;
                d2 = entity.v - this.e;
                double d8 = (double) MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);

                if (d8 != 0.0D) {
                    d0 /= d8;
                    d1 /= d8;
                    d2 /= d8;
                    double d9 = (double) this.k.a(vec3, entity.D);
                    double d10 = (1.0D - d7) * d9;

                    // Check entity if instance of Explosive and can damage entities
                    //        null     or                non-Explosive                     or                      Explosive can damage entities
                    if (this.f == null || !(this.f.getCanaryEntity() instanceof Explosive) || ((Explosive) this.f.getCanaryEntity()).canDamageEntities()) {
                        // CanaryMod Damage hook: Explosions
                        float damage = (float) ((int) ((d10 * d10 + d10) / 2.0D * 8.0D * (double) this.g + 1.0D));
                        CanaryDamageSource source = DamageSource.a(this).getCanaryDamageSource();
                        DamageHook dmg = (DamageHook) new DamageHook(this.f != null ? this.f.getCanaryEntity() : null, entity.getCanaryEntity(), source, damage).call();
                        if (!dmg.isCanceled()) {
                            entity.a((((CanaryDamageSource) dmg.getDamageSource()).getHandle()), damage);
                        }
                        //
                    }

                    double d11 = EnchantmentProtection.a(entity, d10);

                    entity.w += d0 * d11;
                    entity.x += d1 * d11;
                    entity.y += d2 * d11;
                    if (entity instanceof EntityPlayer) {
                        this.l.put((EntityPlayer) entity, this.k.U().a(d0 * d10, d1 * d10, d2 * d10));
                    }
                }
            }
        }

        this.g = f0;
    }

    public void a(boolean flag0) {
        this.k.a(this.c, this.d, this.e, "random.explode", 4.0F, (1.0F + (this.k.s.nextFloat() - this.k.s.nextFloat()) * 0.2F) * 0.7F);
        if (this.g >= 2.0F && this.b) {
            this.k.a("hugeexplosion", this.c, this.d, this.e, 1.0D, 0.0D, 0.0D);
        }
        else {
            this.k.a("largeexplode", this.c, this.d, this.e, 1.0D, 0.0D, 0.0D);
        }

        Iterator iterator;
        ChunkPosition chunkposition;
        int i0;
        int i1;
        int i2;
        Block block;

        if (this.b) {
            iterator = this.h.iterator();

            while (iterator.hasNext()) {
                chunkposition = (ChunkPosition) iterator.next();
                i0 = chunkposition.a;
                i1 = chunkposition.b;
                i2 = chunkposition.c;
                block = this.k.a(i0, i1, i2);
                if (flag0) {
                    double d0 = (double) ((float) i0 + this.k.s.nextFloat());
                    double d1 = (double) ((float) i1 + this.k.s.nextFloat());
                    double d2 = (double) ((float) i2 + this.k.s.nextFloat());
                    double d3 = d0 - this.c;
                    double d4 = d1 - this.d;
                    double d5 = d2 - this.e;
                    double d6 = (double) MathHelper.a(d3 * d3 + d4 * d4 + d5 * d5);

                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double) this.g + 0.1D);

                    d7 *= (double) (this.k.s.nextFloat() * this.k.s.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.k.a("explode", (d0 + this.c * 1.0D) / 2.0D, (d1 + this.d * 1.0D) / 2.0D, (d2 + this.e * 1.0D) / 2.0D, d3, d4, d5);
                    this.k.a("smoke", d0, d1, d2, d3, d4, d5);
                }

                if (block.o() != Material.a) {

                    if (block.a(this)) {
                        block.a(this.k, i0, i1, i2, this.k.e(i0, i1, i2), 1.0F / this.g, 0);
                    }

                    this.k.d(i0, i1, i2, Blocks.a, 0, 3);
                    block.a(this.k, i0, i1, i2, this);
                }
            }
        }

        if (this.a) {
            iterator = this.h.iterator();

            while (iterator.hasNext()) {
                chunkposition = (ChunkPosition) iterator.next();
                i0 = chunkposition.a;
                i1 = chunkposition.b;
                i2 = chunkposition.c;
                block = this.k.a(i0, i1, i2);
                Block block1 = this.k.a(i0, i1 - 1, i2);

                if (block.o() == Material.a && block1.j() && this.j.nextInt(3) == 0) {
                    // CanaryMod ignition from EntityLargeFireball
                    CanaryBlock cBlock = (CanaryBlock) this.k.getCanaryWorld().getBlockAt(i0, i1 - 1, i2);
                    cBlock.setStatus((byte) 7); // 7 fireball hit
                    IgnitionHook ignitionHook = (IgnitionHook) new IgnitionHook(cBlock, null, null, IgnitionCause.FIREBALL_HIT).call();
                    if (!ignitionHook.isCanceled()) {
                        this.k.b(i0, i1, i2, (Block) Blocks.ab);
                    }
                    // CanaryMod end
                }
            }
        }
    }

    public Map b() {
        return this.l;
    }

    public EntityLivingBase c() {
        return this.f == null ? null : (this.f instanceof EntityTNTPrimed ? ((EntityTNTPrimed) this.f).e() : (this.f instanceof EntityLivingBase ? (EntityLivingBase) this.f : null));
    }
}
