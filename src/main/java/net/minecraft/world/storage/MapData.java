package net.minecraft.world.storage;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MapData extends WorldSavedData {

    public int b;
    public int c;
    public byte d;
    public byte e;
    public byte[] f = new byte[16384];
    public List g = Lists.newArrayList();
    public Map i = Maps.newHashMap(); // CanaryMod: private => public
    public Map h = Maps.newLinkedHashMap();

    // CanaryMod
    public boolean mapUpdating = true;
    public boolean playersUpdating = true;
    public boolean isBroken = false;
    public transient String worldName = "";
    //

    public MapData(String s0) {
        super(s0);
    }

    public void a(double d0, double d1, int i0) {
        int i1 = 128 * (1 << i0);
        int i2 = MathHelper.c((d0 + 64.0D) / (double) i1);
        int i3 = MathHelper.c((d1 + 64.0D) / (double) i1);

        this.b = i2 * i1 + i1 / 2 - 64;
        this.c = i3 * i1 + i1 / 2 - 64;
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.d = nbttagcompound.d("dimension");
        this.b = nbttagcompound.f("xCenter");
        this.c = nbttagcompound.f("zCenter");
        this.e = nbttagcompound.d("scale");
        this.e = (byte) MathHelper.a(this.e, 0, 4);
        // CanaryMod: check for keys about map updating itself
        this.mapUpdating = !nbttagcompound.c("mapUpdating") || nbttagcompound.n("mapUpdating");
        this.playersUpdating = !nbttagcompound.c("playersUpdating") || nbttagcompound.n("playersUpdating");
        //

        short short1 = nbttagcompound.e("width");
        short short2 = nbttagcompound.e("height");

        if (short1 == 128 && short2 == 128) {
            this.f = nbttagcompound.k("colors");
        }
        else {
            byte[] abyte = nbttagcompound.k("colors");

            this.f = new byte[16384];
            int i0 = (128 - short1) / 2;
            int i1 = (128 - short2) / 2;

            for (int i2 = 0; i2 < short2; ++i2) {
                int i3 = i2 + i1;

                if (i3 >= 0 || i3 < 128) {
                    for (int i4 = 0; i4 < short1; ++i4) {
                        int i5 = i4 + i0;

                        if (i5 >= 0 || i5 < 128) {
                            this.f[i5 + i3 * 128] = abyte[i4 + i2 * short1];
                        }
                    }
                }
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("dimension", this.d);
        nbttagcompound.a("xCenter", this.b);
        nbttagcompound.a("zCenter", this.c);
        nbttagcompound.a("scale", this.e);
        nbttagcompound.a("width", (short) 128);
        nbttagcompound.a("height", (short) 128);
        nbttagcompound.a("colors", this.f);
        // CanaryMod: set our map updating keys
        nbttagcompound.a("mapUpdating", mapUpdating);
        nbttagcompound.a("playersUpdating", playersUpdating);
    }

    public void a(EntityPlayer entityplayer, ItemStack itemstack) {
        // CanaryMod: Check mapdata for mapUpdating and fix for multiworld causing overriding maps
        if (!playersUpdating || !worldName.equals(entityplayer.o.getCanaryWorld().getFqName()) || isBroken) {
            return;
        }
        if (!this.i.containsKey(entityplayer)) {
            MapData.MapInfo mapdata_mapinfo = new MapData.MapInfo(entityplayer);

            this.i.put(entityplayer, mapdata_mapinfo);
            this.g.add(mapdata_mapinfo);
        }

        if (!entityplayer.bg.c(itemstack)) {
            this.h.remove(entityplayer.d_());
        }

        for (int i0 = 0; i0 < this.g.size(); ++i0) {
            MapData.MapInfo mapdata_mapinfo1 = (MapData.MapInfo) this.g.get(i0);

            if (!mapdata_mapinfo1.a.I && (mapdata_mapinfo1.a.bg.c(itemstack) || itemstack.y())) {
                if (!itemstack.y() && mapdata_mapinfo1.a.am == this.d) {
                    this.a(0, mapdata_mapinfo1.a.o, mapdata_mapinfo1.a.d_(), mapdata_mapinfo1.a.s, mapdata_mapinfo1.a.u, (double) mapdata_mapinfo1.a.y);
                }
            }
            else {
                this.i.remove(mapdata_mapinfo1.a);
                this.g.remove(mapdata_mapinfo1);
            }
        }

        if (itemstack.y()) {
            EntityItemFrame entityitemframe = itemstack.z();
            BlockPos blockpos = entityitemframe.n();

            this.a(1, entityplayer.o, "frame-" + entityitemframe.F(), (double) blockpos.n(), (double) blockpos.p(), (double) (entityitemframe.b.b() * 90));
        }

        if (itemstack.n() && itemstack.o().b("Decorations", 9)) {
            NBTTagList nbttaglist = itemstack.o().c("Decorations", 10);

            for (int i1 = 0; i1 < nbttaglist.c(); ++i1) {
                NBTTagCompound nbttagcompound = nbttaglist.b(i1);

                if (!this.h.containsKey(nbttagcompound.j("id"))) {
                    this.a(nbttagcompound.d("type"), entityplayer.o, nbttagcompound.j("id"), nbttagcompound.i("x"), nbttagcompound.i("z"), nbttagcompound.i("rot"));
                }
            }
        }

    }

    private void a(int i0, World world, String s0, double d0, double d1, double d2) {
        // CanaryMod: Check mapdata for mapUpdating and fix for multiworld causing overriding maps
        if (!mapUpdating || !worldName.equals(world.getCanaryWorld().getFqName()) || isBroken) {
            return;
        }
        int i1 = 1 << this.e;
        float f0 = (float) (d0 - (double) this.b) / (float) i1;
        float f1 = (float) (d1 - (double) this.c) / (float) i1;
        byte b0 = (byte) ((int) ((double) (f0 * 2.0F) + 0.5D));
        byte b1 = (byte) ((int) ((double) (f1 * 2.0F) + 0.5D));
        byte b2 = 63;
        byte b3;

        if (f0 >= (float) (-b2) && f1 >= (float) (-b2) && f0 <= (float) b2 && f1 <= (float) b2) {
            d2 += d2 < 0.0D ? -8.0D : 8.0D;
            b3 = (byte) ((int) (d2 * 16.0D / 360.0D));
            if (this.d < 0) {
                int i2 = (int) (world.P().g() / 10L);

                b3 = (byte) (i2 * i2 * 34187121 + i2 * 121 >> 15 & 15);
            }
        }
        else {
            if (Math.abs(f0) >= 320.0F || Math.abs(f1) >= 320.0F) {
                this.h.remove(s0);
                return;
            }

            i0 = 6;
            b3 = 0;
            if (f0 <= (float) (-b2)) {
                b0 = (byte) ((int) ((double) (b2 * 2) + 2.5D));
            }

            if (f1 <= (float) (-b2)) {
                b1 = (byte) ((int) ((double) (b2 * 2) + 2.5D));
            }

            if (f0 >= (float) b2) {
                b0 = (byte) (b2 * 2 + 1);
            }

            if (f1 >= (float) b2) {
                b1 = (byte) (b2 * 2 + 1);
            }
        }

        this.h.put(s0, new Vec4b((byte) i0, b0, b1, b3));
    }

    public Packet a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        MapData.MapInfo mapdata_mapinfo = (MapData.MapInfo) this.i.get(entityplayer);

        return mapdata_mapinfo == null ? null : mapdata_mapinfo.a(itemstack);
    }

    public void a(int i0, int i1) {
        super.c();
        Iterator iterator = this.g.iterator();

        while (iterator.hasNext()) {
            MapData.MapInfo mapdata_mapinfo = (MapData.MapInfo) iterator.next();

            mapdata_mapinfo.a(i0, i1);
        }
    }

    public MapData.MapInfo a(EntityPlayer entityplayer) {
        // CanaryMod: Check mapdata for mapUpdating and fix for multiworld causing overriding maps
        if (!playersUpdating || !worldName.equals(entityplayer.o.getCanaryWorld().getFqName()) || isBroken) {
            return null;
        }
        MapData.MapInfo mapdata_mapinfo = (MapData.MapInfo) this.i.get(entityplayer);

        if (mapdata_mapinfo == null) {
            mapdata_mapinfo = new MapData.MapInfo(entityplayer);
            this.i.put(entityplayer, mapdata_mapinfo);
            this.g.add(mapdata_mapinfo);
        }

        return mapdata_mapinfo;
    }

    public class MapInfo {

        public final EntityPlayer a;
        private boolean d = true;
        private int e = 0;
        private int f = 0;
        private int g = 127;
        private int h = 127;
        private int i;
        public int b;

        public MapInfo(EntityPlayer p_i2138_2_) {
            this.a = p_i2138_2_;
        }

        public Packet a(ItemStack p_a_1_) {
            if (this.d) {
                this.d = false;
                return new S34PacketMaps(p_a_1_.i(), MapData.this.e, MapData.this.h.values(), MapData.this.f, this.e, this.f, this.g + 1 - this.e, this.h + 1 - this.f);
            }
            else {
                return this.i++ % 5 == 0 ? new S34PacketMaps(p_a_1_.i(), MapData.this.e, MapData.this.h.values(), MapData.this.f, 0, 0, 0, 0) : null;
            }
        }

        public void a(int p_a_1_, int p_a_2_) {
            if (this.d) {
                this.e = Math.min(this.e, p_a_1_);
                this.f = Math.min(this.f, p_a_2_);
                this.g = Math.max(this.g, p_a_1_);
                this.h = Math.max(this.h, p_a_2_);
            }
            else {
                this.d = true;
                this.e = p_a_1_;
                this.f = p_a_2_;
                this.g = p_a_1_;
                this.h = p_a_2_;
            }

        }
    }
}
