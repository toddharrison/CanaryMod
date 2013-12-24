package net.minecraft.tileentity;

import net.canarymod.api.CanaryMobSpawnerLogic;
import net.canarymod.api.MobSpawnerLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class MobSpawnerBaseLogic {

    public int b = 20;
    private String a = "Pig";
    private List e;
    private WeightedRandomMinecart f;
    public double c;
    public double d;
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

    public String e() {
        if (this.i() == null) {
            if (this.a.equals("Minecart")) {
                this.a = "MinecartRideable";
            }

            return this.a;
        }
        else {
            return this.i().c;
        }
    }

    public void a(String s0) {
        this.a = s0;
    }

    public boolean f() {
        return this.a().a((double) this.b() + 0.5D, (double) this.c() + 0.5D, (double) this.d() + 0.5D, (double) this.l) != null;
    }

    public void g() {
        if (this.f()) {
            double d0;

            if (this.a().E) {
                double d1 = (double) ((float) this.b() + this.a().s.nextFloat());
                double d2 = (double) ((float) this.c() + this.a().s.nextFloat());

                d0 = (double) ((float) this.d() + this.a().s.nextFloat());
                this.a().a("smoke", d1, d2, d0, 0.0D, 0.0D, 0.0D);
                this.a().a("flame", d1, d2, d0, 0.0D, 0.0D, 0.0D);
                if (this.b > 0) {
                    --this.b;
                }

                this.d = this.c;
                this.c = (this.c + (double) (1000.0F / ((float) this.b + 200.0F))) % 360.0D;
            }
            else {
                if (this.b == -1) {
                    this.j();
                }

                if (this.b > 0) {
                    --this.b;
                    return;
                }

                boolean flag0 = false;

                for (int i0 = 0; i0 < this.i; ++i0) {
                    Entity entity = EntityList.a(this.e(), this.a());

                    if (entity == null) {
                        return;
                    }

                    int i1 = this.a().a(entity.getClass(), AxisAlignedBB.a().a((double) this.b(), (double) this.c(), (double) this.d(), (double) (this.b() + 1), (double) (this.c() + 1), (double) (this.d() + 1)).b((double) (this.m * 2), 4.0D, (double) (this.m * 2))).size();

                    if (i1 >= this.k) {
                        this.j();
                        return;
                    }

                    d0 = (double) this.b() + (this.a().s.nextDouble() - this.a().s.nextDouble()) * (double) this.m;
                    double d3 = (double) (this.c() + this.a().s.nextInt(3) - 1);
                    double d4 = (double) this.d() + (this.a().s.nextDouble() - this.a().s.nextDouble()) * (double) this.m;
                    EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving) entity : null;

                    entity.b(d0, d3, d4, this.a().s.nextFloat() * 360.0F, 0.0F);
                    if (entityliving == null || entityliving.bw()) {
                        this.a(entity);
                        this.a().c(2004, this.b(), this.c(), this.d(), 0);
                        if (entityliving != null) {
                            entityliving.s();
                        }

                        flag0 = true;
                    }
                }

                if (flag0) {
                    this.j();
                }
            }
        }
    }

    public Entity a(Entity entity) {
        if (this.i() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            entity.d(nbttagcompound);
            Iterator iterator = this.i().b.c().iterator();

            while (iterator.hasNext()) {
                String s0 = (String) iterator.next();
                NBTBase nbtbase = this.i().b.a(s0);

                nbttagcompound.a(s0, nbtbase.b());
            }

            entity.f(nbttagcompound);
            if (entity.p != null) {
                entity.p.d(entity);
            }

            NBTTagCompound nbttagcompound1;

            for (Entity entity1 = entity; nbttagcompound.b("Riding", 10); nbttagcompound = nbttagcompound1) {
                nbttagcompound1 = nbttagcompound.m("Riding");
                Entity entity2 = EntityList.a(nbttagcompound1.j("id"), entity.p);

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
                    entity2.b(entity1.t, entity1.u, entity1.v, entity1.z, entity1.A);
                    if (entity.p != null) {
                        entity.p.d(entity2);
                    }

                    entity1.a(entity2);
                }

                entity1 = entity2;
            }
        }
        else if (entity instanceof EntityLivingBase && entity.p != null) {
            ((EntityLiving) entity).a((EntityLivingData) null);
            this.a().d(entity);
        }

        return entity;
    }

    private void j() {
        if (this.h <= this.g) {
            this.b = this.g;
        }
        else {
            int i0 = this.h - this.g;

            this.b = this.g + this.a().s.nextInt(i0);
        }

        if (this.e != null && this.e.size() > 0) {
            this.a((WeightedRandomMinecart) WeightedRandom.a(this.a().s, (Collection) this.e));
        }

        this.a(1);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.a = nbttagcompound.j("EntityId");
        this.b = nbttagcompound.e("Delay");
        if (nbttagcompound.b("SpawnPotentials", 9)) {
            this.e = new ArrayList();
            NBTTagList nbttaglist = nbttagcompound.c("SpawnPotentials", 10);

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                this.e.add(new WeightedRandomMinecart(nbttaglist.b(i0)));
            }
        }
        else {
            this.e = null;
        }

        if (nbttagcompound.b("SpawnData", 10)) {
            this.a(new WeightedRandomMinecart(nbttagcompound.m("SpawnData"), this.a));
        }
        else {
            this.a((WeightedRandomMinecart) null);
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

        if (this.a() != null && this.a().E) {
            this.j = null;
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("EntityId", this.e());
        nbttagcompound.a("Delay", (short) this.b);
        nbttagcompound.a("MinSpawnDelay", (short) this.g);
        nbttagcompound.a("MaxSpawnDelay", (short) this.h);
        nbttagcompound.a("SpawnCount", (short) this.i);
        nbttagcompound.a("MaxNearbyEntities", (short) this.k);
        nbttagcompound.a("RequiredPlayerRange", (short) this.l);
        nbttagcompound.a("SpawnRange", (short) this.m);
        if (this.i() != null) {
            nbttagcompound.a("SpawnData", this.i().b.b());
        }

        if (this.i() != null || this.e != null && this.e.size() > 0) {
            NBTTagList nbttaglist = new NBTTagList();

            if (this.e != null && this.e.size() > 0) {
                Iterator iterator = this.e.iterator();

                while (iterator.hasNext()) {
                    WeightedRandomMinecart mobspawnerbaselogic_weightedrandomminecart = (WeightedRandomMinecart) iterator.next();

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
        if (i0 == 1 && this.a().E) {
            this.b = this.g;
            return true;
        }
        else {
            return false;
        }
    }

    public WeightedRandomMinecart i() {
        return this.f;
    }

    public void a(WeightedRandomMinecart mobspawnerbaselogic_weightedrandomminecart) {
        this.f = mobspawnerbaselogic_weightedrandomminecart;
    }

    public abstract void a(int i0);

    public abstract World a();

    public abstract int b();

    public abstract int c();

    public abstract int d();

    public class WeightedRandomMinecart extends WeightedRandom.Item {

        public final NBTTagCompound b;
        public final String c;

        public WeightedRandomMinecart(NBTTagCompound nbttagcompound2) {
            super(nbttagcompound2.f("Weight"));
            NBTTagCompound s1 = nbttagcompound2.m("Properties");
            String s0 = nbttagcompound2.j("Type");

            if (s0.equals("Minecart")) {
                if (s1 != null) {
                    switch (s1.f("Type")) {
                        case 0:
                            s0 = "MinecartRideable";
                            break;

                        case 1:
                            s0 = "MinecartChest";
                            break;

                        case 2:
                            s0 = "MinecartFurnace";
                    }
                }
                else {
                    s0 = "MinecartRideable";
                }
            }

            this.b = s1;
            this.c = s0;
        }

        public WeightedRandomMinecart(NBTTagCompound nbttagcompound2, String s1) {
            super(1);
            if (s1.equals("Minecart")) {
                if (nbttagcompound2 != null) {
                    switch (nbttagcompound2.f("Type")) {
                        case 0:
                            s1 = "MinecartRideable";
                            break;

                        case 1:
                            s1 = "MinecartChest";
                            break;

                        case 2:
                            s1 = "MinecartFurnace";
                    }
                }
                else {
                    s1 = "MinecartRideable";
                }
            }

            this.b = nbttagcompound2;
            this.c = s1;
        }

        public NBTTagCompound a() {
            NBTTagCompound nbttagcompound3 = new NBTTagCompound();

            nbttagcompound3.a("Properties", (NBTBase) this.b);
            nbttagcompound3.a("Type", this.c);
            nbttagcompound3.a("Weight", this.a);
            return nbttagcompound3;
        }
    }
}
