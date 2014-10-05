package net.minecraft.village;

import com.google.common.collect.Lists;
import net.canarymod.api.world.CanaryVillage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class Village {

    private World a;
    private final List b = Lists.newArrayList();
    private BlockPos c;
    private BlockPos d;
    private int e;
    private int f;
    private int g;
    private int h;
    public int i; // CanaryMod: private => public
    private TreeMap j;
    private List k;
    public int l; // CanaryMod: private => public

    // CanaryMod
    private CanaryVillage ville;

    //

    public Village() {
        this.c = BlockPos.a;
        this.d = BlockPos.a;
        this.j = new TreeMap();
        this.k = Lists.newArrayList();
        this.ville = new CanaryVillage(this); // CanaryMod: Wrap Village
    }

    public Village(World world) {
        this.c = BlockPos.a;
        this.d = BlockPos.a;
        this.j = new TreeMap();
        this.k = Lists.newArrayList();
        this.a = world;
        this.ville = new CanaryVillage(this); // CanaryMod: Wrap Village
    }

    public void a(World world) {
        this.a = world;
    }

    public void a(int i0) {
        this.g = i0;
        this.m();
        this.l();
        if (i0 % 20 == 0) {
            this.k();
        }

        if (i0 % 30 == 0) {
            this.j();
        }

        int i1 = this.h / 10;

        if (this.l < i1 && this.b.size() > 20 && this.a.s.nextInt(7000) == 0) {
            Vec3 vec3 = this.a(this.d, 2, 4, 2);

            if (vec3 != null) {
                EntityIronGolem entityirongolem = new EntityIronGolem(this.a);

                entityirongolem.b(vec3.a, vec3.b, vec3.c);
                this.a.d((Entity) entityirongolem);
                ++this.l;
            }
        }
    }

    private Vec3 a(BlockPos blockpos, int i0, int i1, int i2) {
        for (int i3 = 0; i3 < 10; ++i3) {
            BlockPos blockpos1 = blockpos.a(this.a.s.nextInt(16) - 8, this.a.s.nextInt(6) - 3, this.a.s.nextInt(16) - 8);

            if (this.a(blockpos1) && this.a(new BlockPos(i0, i1, i2), blockpos1)) {
                return new Vec3((double) blockpos1.n(), (double) blockpos1.o(), (double) blockpos1.p());
            }
        }

        return null;
    }

    private boolean a(BlockPos blockpos, BlockPos blockpos1) {
        if (!World.a((IBlockAccess) this.a, blockpos1.b())) {
            return false;
        }
        else {
            int i0 = blockpos1.n() - blockpos.n() / 2;
            int i1 = blockpos1.p() - blockpos.p() / 2;

            for (int i2 = i0; i2 < i0 + blockpos.n(); ++i2) {
                for (int i3 = blockpos1.o(); i3 < blockpos1.o() + blockpos.o(); ++i3) {
                    for (int i4 = i1; i4 < i1 + blockpos.p(); ++i4) {
                        if (this.a.p(new BlockPos(i2, i3, i4)).c().t()) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private void j() {
        List list = this.a.a(EntityIronGolem.class, new AxisAlignedBB((double) (this.d.n() - this.e), (double) (this.d.o() - 4), (double) (this.d.p() - this.e), (double) (this.d.n() + this.e), (double) (this.d.o() + 4), (double) (this.d.p() + this.e)));

        this.l = list.size();
    }

    private void k() {
        List list = this.a.a(EntityVillager.class, new AxisAlignedBB((double) (this.d.n() - this.e), (double) (this.d.o() - 4), (double) (this.d.p() - this.e), (double) (this.d.n() + this.e), (double) (this.d.o() + 4), (double) (this.d.p() + this.e)));

        this.h = list.size();
        if (this.h == 0) {
            this.j.clear();
        }
    }

    public BlockPos a() {
        return this.d;
    }

    public int b() {
        return this.e;
    }

    public int c() {
        return this.b.size();
    }

    public int d() {
        return this.g - this.f;
    }

    public int e() {
        return this.h;
    }

    public boolean a(BlockPos blockpos) {
        return this.d.i(blockpos) < (double) (this.e * this.e);
    }

    public List f() {
        return this.b;
    }

    public VillageDoorInfo b(BlockPos blockpos) {
        VillageDoorInfo villagedoorinfo = null;
        int i0 = Integer.MAX_VALUE;
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            VillageDoorInfo villagedoorinfo1 = (VillageDoorInfo) iterator.next();
            int i1 = villagedoorinfo1.a(blockpos);

            if (i1 < i0) {
                villagedoorinfo = villagedoorinfo1;
                i0 = i1;
            }
        }

        return villagedoorinfo;
    }

    public VillageDoorInfo c(BlockPos blockpos) {
        VillageDoorInfo villagedoorinfo = null;
        int i0 = Integer.MAX_VALUE;
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            VillageDoorInfo villagedoorinfo1 = (VillageDoorInfo) iterator.next();
            int i1 = villagedoorinfo1.a(blockpos);

            if (i1 > 256) {
                i1 *= 1000;
            }
            else {
                i1 = villagedoorinfo1.c();
            }

            if (i1 < i0) {
                villagedoorinfo = villagedoorinfo1;
                i0 = i1;
            }
        }

        return villagedoorinfo;
    }

    public VillageDoorInfo e(BlockPos blockpos) {
        if (this.d.i(blockpos) > (double) (this.e * this.e)) {
            return null;
        }
        else {
            Iterator iterator = this.b.iterator();

            VillageDoorInfo villagedoorinfo;

            do {
                if (!iterator.hasNext()) {
                    return null;
                }

                villagedoorinfo = (VillageDoorInfo) iterator.next();
            }
            while (villagedoorinfo.d().n() != blockpos.n() || villagedoorinfo.d().p() != blockpos.p() || Math.abs(villagedoorinfo.d().o() - blockpos.o()) > 1);

            return villagedoorinfo;
        }
    }

    public void a(VillageDoorInfo villagedoorinfo) {
        this.b.add(villagedoorinfo);
        this.c = this.c.a((Vec3i) villagedoorinfo.d());
        this.n();
        this.f = villagedoorinfo.h();
    }

    public boolean g() {
        return this.b.isEmpty();
    }

    public void a(EntityLivingBase entitylivingbase) {
        Iterator iterator = this.k.iterator();

        Village.VillageAgressor village_villageagressor;

        do {
            if (!iterator.hasNext()) {
                this.k.add(new Village.VillageAgressor(entitylivingbase, this.g));
                return;
            }

            village_villageagressor = (Village.VillageAgressor) iterator.next();
        } while (village_villageagressor.a != entitylivingbase);

        village_villageagressor.b = this.g;
    }

    public EntityLivingBase b(EntityLivingBase entitylivingbase) {
        double d0 = Double.MAX_VALUE;
        Village.VillageAgressor village_villageagressor = null;

        for (int i0 = 0; i0 < this.k.size(); ++i0) {
            Village.VillageAgressor village_villageagressor1 = (Village.VillageAgressor) this.k.get(i0);
            double d1 = village_villageagressor1.a.h(entitylivingbase);

            if (d1 <= d0) {
                village_villageagressor = village_villageagressor1;
                d0 = d1;
            }
        }

        return village_villageagressor != null ? village_villageagressor.a : null;
    }

    public EntityPlayer c(EntityLivingBase entitylivingbase) {
        double d0 = Double.MAX_VALUE;
        EntityPlayer entityplayer = null;
        Iterator iterator = this.j.keySet().iterator();

        while (iterator.hasNext()) {
            String s0 = (String) iterator.next();

            if (this.d(s0)) {
                EntityPlayer entityplayer1 = this.a.a(s0);

                if (entityplayer1 != null) {
                    double d1 = entityplayer1.h(entitylivingbase);

                    if (d1 <= d0) {
                        entityplayer = entityplayer1;
                        d0 = d1;
                    }
                }
            }
        }

        return entityplayer;
    }

    private void l() {
        Iterator iterator = this.k.iterator();

        while (iterator.hasNext()) {
            Village.VillageAgressor village_villageagressor = (Village.VillageAgressor) iterator.next();

            if (!village_villageagressor.a.ai() || Math.abs(this.g - village_villageagressor.b) > 300) {
                iterator.remove();
            }
        }
    }

    private void m() {
        boolean flag0 = false;
        boolean flag1 = this.a.s.nextInt(50) == 0;
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo) iterator.next();

            if (flag1) {
                villagedoorinfo.a();
            }

            if (!this.f(villagedoorinfo.d()) || Math.abs(this.g - villagedoorinfo.h()) > 1200) {
                this.c = this.c.a((Vec3i) villagedoorinfo.d().a(-1));
                flag0 = true;
                villagedoorinfo.a(true);
                iterator.remove();
            }
        }

        if (flag0) {
            this.n();
        }
    }

    private boolean f(BlockPos blockpos) {
        Block block = this.a.p(blockpos).c();

        return block instanceof BlockDoor ? block.r() == Material.d : false;
    }

    private void n() {
        int i0 = this.b.size();

        if (i0 == 0) {
            this.d = new BlockPos(0, 0, 0);
            this.e = 0;
        }
        else {
            this.d = new BlockPos(this.c.n() / i0, this.c.o() / i0, this.c.p() / i0);
            int i1 = 0;

            VillageDoorInfo villagedoorinfo;

            for (Iterator iterator = this.b.iterator(); iterator.hasNext(); i1 = Math.max(villagedoorinfo.a(this.d), i1)) {
                villagedoorinfo = (VillageDoorInfo) iterator.next();
            }

            this.e = Math.max(32, (int) Math.sqrt((double) i1) + 1);
        }
    }

    public int a(String s0) {
        Integer integer = (Integer) this.j.get(s0);

        return integer != null ? integer.intValue() : 0;
    }

    public int a(String s0, int i0) {
        int i1 = this.a(s0);
        int i2 = MathHelper.a(i1 + i0, -30, 10);

        this.j.put(s0, Integer.valueOf(i2));
        return i2;
    }

    public boolean d(String s0) {
        return this.a(s0) <= -15;
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.h = nbttagcompound.f("PopSize");
        this.e = nbttagcompound.f("Radius");
        this.l = nbttagcompound.f("Golems");
        this.f = nbttagcompound.f("Stable");
        this.g = nbttagcompound.f("Tick");
        this.i = nbttagcompound.f("MTick");
        this.d = new BlockPos(nbttagcompound.f("CX"), nbttagcompound.f("CY"), nbttagcompound.f("CZ"));
        this.c = new BlockPos(nbttagcompound.f("ACX"), nbttagcompound.f("ACY"), nbttagcompound.f("ACZ"));
        NBTTagList nbttaglist = nbttagcompound.c("Doors", 10);

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
            VillageDoorInfo villagedoorinfo = new VillageDoorInfo(new BlockPos(nbttagcompound1.f("X"), nbttagcompound1.f("Y"), nbttagcompound1.f("Z")), nbttagcompound1.f("IDX"), nbttagcompound1.f("IDZ"), nbttagcompound1.f("TS"));

            this.b.add(villagedoorinfo);
        }

        NBTTagList nbttaglist1 = nbttagcompound.c("Players", 10);

        for (int i1 = 0; i1 < nbttaglist1.c(); ++i1) {
            NBTTagCompound nbttagcompound2 = nbttaglist1.b(i1);

            this.j.put(nbttagcompound2.j("Name"), Integer.valueOf(nbttagcompound2.f("S")));
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("PopSize", this.h);
        nbttagcompound.a("Radius", this.e);
        nbttagcompound.a("Golems", this.l);
        nbttagcompound.a("Stable", this.f);
        nbttagcompound.a("Tick", this.g);
        nbttagcompound.a("MTick", this.i);
        nbttagcompound.a("CX", this.d.n());
        nbttagcompound.a("CY", this.d.o());
        nbttagcompound.a("CZ", this.d.p());
        nbttagcompound.a("ACX", this.c.n());
        nbttagcompound.a("ACY", this.c.o());
        nbttagcompound.a("ACZ", this.c.p());
        NBTTagList nbttaglist = new NBTTagList();
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo) iterator.next();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            nbttagcompound1.a("X", villagedoorinfo.d().n());
            nbttagcompound1.a("Y", villagedoorinfo.d().o());
            nbttagcompound1.a("Z", villagedoorinfo.d().p());
            nbttagcompound1.a("IDX", villagedoorinfo.f());
            nbttagcompound1.a("IDZ", villagedoorinfo.g());
            nbttagcompound1.a("TS", villagedoorinfo.h());
            nbttaglist.a((NBTBase) nbttagcompound1);
        }

        nbttagcompound.a("Doors", (NBTBase) nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();
        Iterator iterator1 = this.j.keySet().iterator();

        while (iterator1.hasNext()) {
            String s0 = (String) iterator1.next();
            NBTTagCompound nbttagcompound2 = new NBTTagCompound();

            nbttagcompound2.a("Name", s0);
            nbttagcompound2.a("S", ((Integer) this.j.get(s0)).intValue());
            nbttaglist1.a((NBTBase) nbttagcompound2);
        }

        nbttagcompound.a("Players", (NBTBase) nbttaglist1);
    }

    public void h() {
        this.i = this.g;
    }

    public boolean i() {
        return this.i == 0 || this.g - this.i >= 3600;
    }

    public void b(int i0) {
        Iterator iterator = this.j.keySet().iterator();

        while (iterator.hasNext()) {
            String s0 = (String) iterator.next();

            this.a(s0, i0);
        }
    }

    class VillageAgressor {

        public EntityLivingBase a;
        public int b;

        VillageAgressor(EntityLivingBase entitylivingbase, int i0) {
            this.a = entitylivingbase;
            this.b = i0;
        }
    }

    // CanaryMod
    public CanaryVillage getCanaryVillage() {
        return ville;
    }
    //
}
