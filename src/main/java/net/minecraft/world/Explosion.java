package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.Explosive;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.world.ExplosionHook;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;

import java.util.*;

public class Explosion {

    private final boolean a;
    private final boolean b;
    private final Random c = new Random();
    private final World d;
    private final double e;
    private final double f;
    private final double g;
    private final Entity h;
    private final float i;
    private final List j = Lists.newArrayList();
    private final Map k = Maps.newHashMap();

    public Explosion(World world, Entity entity, double d0, double d1, double d2, float f0, boolean flag0, boolean flag1) {
        this.d = world;
        this.h = entity;
        this.i = f0;
        this.e = d0;
        this.f = d1;
        this.g = d2;
        this.a = flag0;
        this.b = flag1;
    }

    @SuppressWarnings("unchecked")
    public void a() {
        HashSet hashset = Sets.newHashSet();
        boolean flag0 = true;

        // CanaryMod: Ground Zero
        CanaryBlock gzero = (CanaryBlock) this.k.getCanaryWorld().getBlockAt((int) Math.floor(c), (int) Math.floor(d), (int) Math.floor(e));
        //

        int i0;
        int i1;

        for (int i2 = 0; i2 < 16; ++i2) {
            for (i0 = 0; i0 < 16; ++i0) {
                for (i1 = 0; i1 < 16; ++i1) {
                    if (i2 == 0 || i2 == 15 || i0 == 0 || i0 == 15 || i1 == 0 || i1 == 15) {
                        double d0 = (double) ((float) i2 / 15.0F * 2.0F - 1.0F);
                        double d1 = (double) ((float) i0 / 15.0F * 2.0F - 1.0F);
                        double d2 = (double) ((float) i1 / 15.0F * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);

                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f0 = this.i * (0.7F + this.d.s.nextFloat() * 0.6F);
                        double d4 = this.e;
                        double d5 = this.f;
                        double d6 = this.g;

                        for (float f1 = 0.3F; f0 > 0.0F; f0 -= 0.22500001F) {
                            BlockPos blockpos = new BlockPos(d4, d5, d6);
                            IBlockState iblockstate = this.d.p(blockpos);

                            if (iblockstate.c().r() != Material.a) {
                                float f2 = this.h != null ? this.h.a(this, this.d, blockpos, iblockstate) : iblockstate.c().a((Entity) null);

                                f0 -= (f2 + 0.3F) * 0.3F;
                            }

                            if (f0 > 0.0F && (this.h == null || this.h.a(this, this.d, blockpos, iblockstate, f0))) {
                                hashset.add(blockpos);
                            }

                            d4 += d0 * 0.30000001192092896D;
                            d5 += d1 * 0.30000001192092896D;
                            d6 += d2 * 0.30000001192092896D;
                        }
                    }
                }
            }
        }

        // Check entity if instance of Explosive and can damage world
        //        null     or                non-Explosive                     or                      Explosive can damage world
        if (this.h == null || !(this.h.getCanaryEntity() instanceof Explosive) || ((Explosive) this.h.getCanaryEntity()).canDamageWorld()) {
            // CanaryMod: Add affected blocks into a List of Blocks.
            List<net.canarymod.api.world.blocks.Block> blkAff = new ArrayList<net.canarymod.api.world.blocks.Block>(hashset.size());

            for (ChunkPosition ocp : (HashSet<ChunkPosition>) hashset) {
                blkAff.add(this.k.getCanaryWorld().getBlockAt(ocp.a, ocp.b, ocp.c));
            }
            // Explosion call
            ExplosionHook exp = (ExplosionHook) new ExplosionHook(gzero, this.f != null ? this.f.getCanaryEntity() : null, blkAff).call();
            // if cancelled, don't populate this.j at all.
            if (!exp.isCanceled()) {
                // Repopulate hashset according to blocksAffected.
                hashset.clear();
                for (net.canarymod.api.world.blocks.Block affected : exp.getAffectedBlocks()) {
                    hashset.add(new ChunkPosition(affected.getX(), affected.getY(), affected.getZ()));
                }
                this.j.addAll(hashset);
            }
        }
        //
        float f3 = this.i * 2.0F;

        i0 = MathHelper.c(this.e - (double) f3 - 1.0D);
        i1 = MathHelper.c(this.e + (double) f3 + 1.0D);
        int i3 = MathHelper.c(this.f - (double) f3 - 1.0D);
        int i4 = MathHelper.c(this.f + (double) f3 + 1.0D);
        int i5 = MathHelper.c(this.g - (double) f3 - 1.0D);
        int i6 = MathHelper.c(this.g + (double) f3 + 1.0D);
        List list = this.d.b(this.h, new AxisAlignedBB((double) i0, (double) i3, (double) i5, (double) i1, (double) i4, (double) i6));
        Vec3 vec3 = new Vec3(this.e, this.f, this.g);

        for (int i7 = 0; i7 < list.size(); ++i7) {
            Entity entity = (Entity) list.get(i7);

            if (!entity.aV()) {
                double d7 = entity.f(this.e, this.f, this.g) / (double) f3;

                if (d7 <= 1.0D) {
                    double d8 = entity.s - this.e;
                    double d9 = entity.t + (double) entity.aR() - this.f;
                    double d10 = entity.u - this.g;
                    double d11 = (double) MathHelper.a(d8 * d8 + d9 * d9 + d10 * d10);

                    if (d11 != 0.0D) {
                        d8 /= d11;
                        d9 /= d11;
                        d10 /= d11;
                        double d12 = (double) this.d.a(vec3, entity.aQ());
                        double d13 = (1.0D - d7) * d12;
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

                        double d14 = EnchantmentProtection.a(entity, d13);

                        entity.v += d8 * d14;
                        entity.w += d9 * d14;
                        entity.x += d10 * d14;
                        if (entity instanceof EntityPlayer) {
                            this.k.put((EntityPlayer) entity, new Vec3(d8 * d13, d9 * d13, d10 * d13));
                        }
                    }
                }
            }
        }
    }

    public void a(boolean flag0) {
        this.d.a(this.e, this.f, this.g, "random.explode", 4.0F, (1.0F + (this.d.s.nextFloat() - this.d.s.nextFloat()) * 0.2F) * 0.7F);
        if (this.i >= 2.0F && this.b) {
            this.d.a(EnumParticleTypes.EXPLOSION_HUGE, this.e, this.f, this.g, 1.0D, 0.0D, 0.0D, new int[0]);
        }
        else {
            this.d.a(EnumParticleTypes.EXPLOSION_LARGE, this.e, this.f, this.g, 1.0D, 0.0D, 0.0D, new int[0]);
        }

        Iterator iterator;
        BlockPos blockpos;

        if (this.b) {
            iterator = this.j.iterator();

            while (iterator.hasNext()) {
                blockpos = (BlockPos) iterator.next();
                Block block = this.d.p(blockpos).c();

                if (flag0) {
                    double d0 = (double) ((float) blockpos.n() + this.d.s.nextFloat());
                    double d1 = (double) ((float) blockpos.o() + this.d.s.nextFloat());
                    double d2 = (double) ((float) blockpos.p() + this.d.s.nextFloat());
                    double d3 = d0 - this.e;
                    double d4 = d1 - this.f;
                    double d5 = d2 - this.g;
                    double d6 = (double) MathHelper.a(d3 * d3 + d4 * d4 + d5 * d5);

                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double) this.i + 0.1D);

                    d7 *= (double) (this.d.s.nextFloat() * this.d.s.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.d.a(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.e * 1.0D) / 2.0D, (d1 + this.f * 1.0D) / 2.0D, (d2 + this.g * 1.0D) / 2.0D, d3, d4, d5, new int[0]);
                    this.d.a(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
                }

                if (block.r() != Material.a) {
                    if (block.a(this)) {
                        block.a(this.d, blockpos, this.d.p(blockpos), 1.0F / this.i, 0);
                    }

                    this.d.a(blockpos, Blocks.a.P(), 3);
                    block.a(this.d, blockpos, this);
                }
            }
        }

        if (this.a) {
            iterator = this.j.iterator();

            while (iterator.hasNext()) {
                blockpos = (BlockPos) iterator.next();
                if (this.d.p(blockpos).c().r() == Material.a && this.d.p(blockpos.b()).c().m() && this.c.nextInt(3) == 0) {
                    // CanaryMod ignition from EntityLargeFireball
                    CanaryBlock cBlock = (CanaryBlock) this.k.getCanaryWorld().getBlockAt(blockpos.n(), blockpos.o() - 1, blockpos.p());
                    cBlock.setStatus((byte) 7); // 7 fireball hit
                    IgnitionHook ignitionHook = (IgnitionHook) new IgnitionHook(cBlock, null, null, IgnitionCause.FIREBALL_HIT).call();
                    if (!ignitionHook.isCanceled()) {
                        this.d.a(blockpos, Blocks.ab.P());
                    }
                    // CanaryMod end
                }
            }
        }
    }

    public Map b() {
        return this.k;
    }

    public EntityLivingBase c() {
        return this.h == null ? null : (this.h instanceof EntityTNTPrimed ? ((EntityTNTPrimed) this.h).j() : (this.h instanceof EntityLivingBase ? (EntityLivingBase) this.h : null));
    }

    public void d() {
        this.j.clear();
    }

    public List e() {
        return this.j;
    }
}
