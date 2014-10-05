package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import net.canarymod.api.CanaryMobSpawnerLogic;
import net.canarymod.api.MobSpawnerLogic;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public abstract class MobSpawnerBaseLogic {

    public int a = 20;
    private String b = "Pig";
    private final List c = Lists.newArrayList();
    private WeightedRandomMinecart d;
    public double e;
    public double f;
    public int g = 200; // CanaryMod: private >> public
    public int h = 800; // CanaryMod: private >> public
    public int i = 4; // CanaryMod: private >> public
    private Entity j;
    public int k = 6; // CanaryMod: private >> public
    public int l = 16; // CanaryMod: private >> public
    public int m = 4; // CanaryMod: private >> public

    // CanaryMod: Variable Declaration
    public MobSpawnerLogic logic = (MobSpawnerLogic) new CanaryMobSpawnerLogic(this);

    // CanaryMod: End

    public String f() {
        if (this.i() == null) {
            if (this.b.equals("Minecart")) {
                this.b = "MinecartRideable";
            }

            return this.b;
        }
        else {
            return this.i().d;
        }
    }

    public void a(String s0) {
        this.b = s0;
    }

    private boolean g() {
        BlockPos blockpos = this.b();

        return this.a().b((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.5D, (double) blockpos.p() + 0.5D, (double) this.l);
    }

    public void c() {
        if (this.g()) {
            BlockPos blockpos = this.b();
            double d0;

            if (this.a().D) {
                double d1 = (double) ((float) blockpos.n() + this.a().s.nextFloat());
                double d2 = (double) ((float) blockpos.o() + this.a().s.nextFloat());

                d0 = (double) ((float) blockpos.p() + this.a().s.nextFloat());
                this.a().a(EnumParticleTypes.SMOKE_NORMAL, d1, d2, d0, 0.0D, 0.0D, 0.0D, new int[0]);
                this.a().a(EnumParticleTypes.FLAME, d1, d2, d0, 0.0D, 0.0D, 0.0D, new int[0]);
                if (this.a > 0) {
                    --this.a;
                }

                this.f = this.e;
                this.e = (this.e + (double) (1000.0F / ((float) this.a + 200.0F))) % 360.0D;
            }
            else {
                if (this.a == -1) {
                    this.h();
                }

                if (this.a > 0) {
                    --this.a;
                    return;
                }

                boolean flag0 = false;

                for (int i0 = 0; i0 < this.i; ++i0) {
                    Entity entity = EntityList.a(this.f(), this.a());

                    if (entity == null) {
                        return;
                    }

                    int i1 = this.a().a(entity.getClass(), (new AxisAlignedBB((double) blockpos.n(), (double) blockpos.o(), (double) blockpos.p(), (double) (blockpos.n() + 1), (double) (blockpos.o() + 1), (double) (blockpos.p() + 1))).b((double) this.m, (double) this.m, (double) this.m)).size();

                    if (i1 >= this.k) {
                        this.h();
                        return;
                    }

                    d0 = (double) blockpos.n() + (this.a().s.nextDouble() - this.a().s.nextDouble()) * (double) this.m + 0.5D;
                    double d3 = (double) (blockpos.o() + this.a().s.nextInt(3) - 1);
                    double d4 = (double) blockpos.p() + (this.a().s.nextDouble() - this.a().s.nextDouble()) * (double) this.m + 0.5D;
                    EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving) entity : null;

                    entity.b(d0, d3, d4, this.a().s.nextFloat() * 360.0F, 0.0F);
                    if (entityliving == null || entityliving.bQ() && entityliving.bR()) {
                        this.a(entity, true);
                        this.a().b(2004, blockpos, 0);
                        if (entityliving != null) {
                            entityliving.y();
                        }

                        flag0 = true;
                    }
                }

                if (flag0) {
                    this.h();
                }
            }
        }
    }

    private Entity a(Entity entity, boolean flag0) {
        if (this.i() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            entity.d(nbttagcompound);
            Iterator iterator = this.i().c.c().iterator();

            while (iterator.hasNext()) {
                String s0 = (String) iterator.next();
                NBTBase nbtbase = this.i().c.a(s0);

                nbttagcompound.a(s0, nbtbase.b());
            }

            entity.f(nbttagcompound);
            if (entity.o != null && flag0) {
                entity.o.d(entity);
            }

            NBTTagCompound nbttagcompound1;

            for (Entity entity1 = entity; nbttagcompound.b("Riding", 10); nbttagcompound = nbttagcompound1) {
                nbttagcompound1 = nbttagcompound.m("Riding");
                Entity entity2 = EntityList.a(nbttagcompound1.j("id"), entity.o);

                if (entity2 != null) {
                    NBTTagCompound nbttagcompound2 = new NBTTagCompound();

                    entity2.d(nbttagcompound2);
                    Iterator iterator1 = nbttagcompound1.c().iterator();

                    while (iterator1.hasNext()) {
                        String s1 = (String) iterator1.next();
                        NBTBase nbtbase1 = nbttagcompound1.a(s1);

                        nbttagcompound2.a(s1, nbtbase1.b());
                    }

                    entity2.f(nbttagcompound2);
                    entity2.b(entity1.s, entity1.t, entity1.u, entity1.y, entity1.z);
                    if (entity.o != null && flag0) {
                        entity.o.d(entity2);
                    }

                    entity1.a(entity2);
                }

                entity1 = entity2;
            }
        }
        else if (entity instanceof EntityLivingBase && entity.o != null && flag0) {
            ((EntityLiving) entity).a(entity.o.E(new BlockPos(entity)), (IEntityLivingData) null);
            entity.o.d(entity);
        }

        return entity;
    }

    private void h() {
        if (this.h <= this.g) {
            this.a = this.g;
        }
        else {
            int i0 = this.h - this.g;

            this.a = this.g + this.a().s.nextInt(i0);
        }

        if (this.c.size() > 0) {
            this.a((MobSpawnerBaseLogic.WeightedRandomMinecart) WeightedRandom.a(this.a().s, this.c));
        }

        this.a(1);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.b = nbttagcompound.j("EntityId");
        this.a = nbttagcompound.e("Delay");
        this.c.clear();
        if (nbttagcompound.b("SpawnPotentials", 9)) {
            NBTTagList nbttaglist = nbttagcompound.c("SpawnPotentials", 10);

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                this.c.add(new MobSpawnerBaseLogic.WeightedRandomMinecart(nbttaglist.b(i0)));
            }
        }

        if (nbttagcompound.b("SpawnData", 10)) {
            this.a(new MobSpawnerBaseLogic.WeightedRandomMinecart(nbttagcompound.m("SpawnData"), this.b));
        }
        else {
            this.a((MobSpawnerBaseLogic.WeightedRandomMinecart) null);
        }

        if (nbttagcompound.b("MinSpawnDelay", 99)) {
            this.g = nbttagcompound.e("MinSpawnDelay");
            this.h = nbttagcompound.e("MaxSpawnDelay");
            this.i = nbttagcompound.e("SpawnCount");
        }

        if (nbttagcompound.b("MaxNearbyEntities", 99)) {
            this.k = nbttagcompound.e("MaxNearbyEntities");
            this.l = nbttagcompound.e("RequiredPlayerRange");
        }

        if (nbttagcompound.b("SpawnRange", 99)) {
            this.m = nbttagcompound.e("SpawnRange");
        }

        if (this.a() != null) {
            this.j = null;
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("EntityId", this.f());
        nbttagcompound.a("Delay", (short) this.a);
        nbttagcompound.a("MinSpawnDelay", (short) this.g);
        nbttagcompound.a("MaxSpawnDelay", (short) this.h);
        nbttagcompound.a("SpawnCount", (short) this.i);
        nbttagcompound.a("MaxNearbyEntities", (short) this.k);
        nbttagcompound.a("RequiredPlayerRange", (short) this.l);
        nbttagcompound.a("SpawnRange", (short) this.m);
        if (this.i() != null) {
            nbttagcompound.a("SpawnData", this.i().c.b());
        }

        if (this.i() != null || this.c.size() > 0) {
            NBTTagList nbttaglist = new NBTTagList();

            if (this.c.size() > 0) {
                Iterator iterator = this.c.iterator();

                while (iterator.hasNext()) {
                    MobSpawnerBaseLogic.WeightedRandomMinecart mobspawnerbaselogic_weightedrandomminecart = (MobSpawnerBaseLogic.WeightedRandomMinecart) iterator.next();

                    nbttaglist.a((NBTBase) mobspawnerbaselogic_weightedrandomminecart.a());
                }
            }
            else {
                nbttaglist.a((NBTBase) this.i().a());
            }

            nbttagcompound.a("SpawnPotentials", (NBTBase) nbttaglist);
        }
    }

    public boolean b(int i0) {
        if (i0 == 1 && this.a().D) {
            this.a = this.g;
            return true;
        }
        else {
            return false;
        }
    }

    private MobSpawnerBaseLogic.WeightedRandomMinecart i() {
        return this.d;
    }

    public void a(MobSpawnerBaseLogic.WeightedRandomMinecart mobspawnerbaselogic_weightedrandomminecart) {
        this.d = mobspawnerbaselogic_weightedrandomminecart;
    }

    public abstract void a(int i0);

    public abstract World a();

    public abstract BlockPos b();

    public class WeightedRandomMinecart extends WeightedRandom.Item {

        private final NBTTagCompound c;
        private final String d;

        public WeightedRandomMinecart(NBTTagCompound p_i1945_2_) {
            this(p_i1945_2_.m("Properties"), p_i1945_2_.j("Type"), p_i1945_2_.f("Weight"));
        }

        public WeightedRandomMinecart(NBTTagCompound p_i1946_2_, String p_i1946_3_) {
            this(p_i1946_2_, p_i1946_3_, 1);
        }

        private WeightedRandomMinecart(NBTTagCompound p_i45757_2_, String p_i45757_3_, int p_i45757_4_) {
            super(p_i45757_4_);
            if (p_i45757_3_.equals("Minecart")) {
                if (p_i45757_2_ != null) {
                    p_i45757_3_ = EntityMinecart.EnumMinecartType.a(p_i45757_2_.f("Type")).b();
                }
                else {
                    p_i45757_3_ = "MinecartRideable";
                }
            }

            this.c = p_i45757_2_;
            this.d = p_i45757_3_;
        }

        public NBTTagCompound a() {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            nbttagcompound.a("Properties", (NBTBase) this.c);
            nbttagcompound.a("Type", this.d);
            nbttagcompound.a("Weight", this.a);
            return nbttagcompound;
        }
    }
}
