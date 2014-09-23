package net.minecraft.world.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.*;

public class MapData extends WorldSavedData {

    public int a;
    public int b;
    public byte c;
    public byte d;
    public byte[] e = new byte[16384];
    public List f = new ArrayList();
    public Map i = new HashMap(); // CanaryMod: private => public
    public Map g = new LinkedHashMap();
    // CanaryMod
    public boolean mapUpdating = true;
    public boolean playersUpdating = true;
    public boolean isBroken = false;
    public transient String worldName = "";
    //

    public MapData(String s0) {
        super(s0);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.c = nbttagcompound.d("dimension");
        this.a = nbttagcompound.f("xCenter");
        this.b = nbttagcompound.f("zCenter");
        this.d = nbttagcompound.d("scale");
        // CanaryMod: check for keys about map updating itself
        this.mapUpdating = !nbttagcompound.c("mapUpdating") || nbttagcompound.n("mapUpdating");
        this.playersUpdating = !nbttagcompound.c("playersUpdating") || nbttagcompound.n("playersUpdating");
        //
        if (this.d < 0) {
            this.d = 0;
        }

        if (this.d > 4) {
            this.d = 4;
        }

        short short1 = nbttagcompound.e("width");
        short short2 = nbttagcompound.e("height");

        if (short1 == 128 && short2 == 128) {
            this.e = nbttagcompound.k("colors");
        }
        else {
            byte[] abyte = nbttagcompound.k("colors");

            this.e = new byte[16384];
            int i0 = (128 - short1) / 2;
            int i1 = (128 - short2) / 2;

            for (int i2 = 0; i2 < short2; ++i2) {
                int i3 = i2 + i1;

                if (i3 >= 0 || i3 < 128) {
                    for (int i4 = 0; i4 < short1; ++i4) {
                        int i5 = i4 + i0;

                        if (i5 >= 0 || i5 < 128) {
                            this.e[i5 + i3 * 128] = abyte[i4 + i2 * short1];
                        }
                    }
                }
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("dimension", this.c);
        nbttagcompound.a("xCenter", this.a);
        nbttagcompound.a("zCenter", this.b);
        nbttagcompound.a("scale", this.d);
        nbttagcompound.a("width", (short)128);
        nbttagcompound.a("height", (short)128);
        nbttagcompound.a("colors", this.e);
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
            this.f.add(mapdata_mapinfo);
        }

        if (!entityplayer.bm.c(itemstack)) {
            this.g.remove(entityplayer.b_());
        }

        for (int i0 = 0; i0 < this.f.size(); ++i0) {
            MapData.MapInfo mapdata_mapinfo1 = (MapData.MapInfo)this.f.get(i0);

            if (!mapdata_mapinfo1.a.K && (mapdata_mapinfo1.a.bm.c(itemstack) || itemstack.A())) {
                if (!itemstack.A() && mapdata_mapinfo1.a.ap == this.c) {
                    this.a(0, mapdata_mapinfo1.a.o, mapdata_mapinfo1.a.b_(), mapdata_mapinfo1.a.s, mapdata_mapinfo1.a.u, (double)mapdata_mapinfo1.a.y);
                }
            }
            else {
                this.i.remove(mapdata_mapinfo1.a);
                this.f.remove(mapdata_mapinfo1);
            }
        }

        if (itemstack.A()) {
            this.a(1, entityplayer.o, "frame-" + itemstack.B().y(), (double)itemstack.B().b, (double)itemstack.B().d, (double)(itemstack.B().a * 90));
        }
    }

    private void a(int i0, World world, String s0, double d0, double d1, double d2) {
        int i1 = 1 << this.d;
        float f0 = (float)(d0 - (double)this.a) / (float)i1;
        float f1 = (float)(d1 - (double)this.b) / (float)i1;
        byte b0 = (byte)((int)((double)(f0 * 2.0F) + 0.5D));
        byte b1 = (byte)((int)((double)(f1 * 2.0F) + 0.5D));
        byte b2 = 63;
        byte b3;

        if (f0 >= (float)(-b2) && f1 >= (float)(-b2) && f0 <= (float)b2 && f1 <= (float)b2) {
            d2 += d2 < 0.0D ? -8.0D : 8.0D;
            b3 = (byte)((int)(d2 * 16.0D / 360.0D));
            if (this.c < 0) {
                int i2 = (int)(world.N().g() / 10L);

                b3 = (byte)(i2 * i2 * 34187121 + i2 * 121 >> 15 & 15);
            }
        }
        else {
            if (Math.abs(f0) >= 320.0F || Math.abs(f1) >= 320.0F) {
                this.g.remove(s0);
                return;
            }

            i0 = 6;
            b3 = 0;
            if (f0 <= (float)(-b2)) {
                b0 = (byte)((int)((double)(b2 * 2) + 2.5D));
            }

            if (f1 <= (float)(-b2)) {
                b1 = (byte)((int)((double)(b2 * 2) + 2.5D));
            }

            if (f0 >= (float)b2) {
                b0 = (byte)(b2 * 2 + 1);
            }

            if (f1 >= (float)b2) {
                b1 = (byte)(b2 * 2 + 1);
            }
        }

        this.g.put(s0, new MapData.MapCoord((byte)i0, b0, b1, b3));
    }

    public byte[] a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        MapData.MapInfo mapdata_mapinfo = (MapData.MapInfo)this.i.get(entityplayer);

        return mapdata_mapinfo == null ? null : mapdata_mapinfo.a(itemstack);
    }

    public void a(int i0, int i1, int i2) {
        super.c();

        for (int i3 = 0; i3 < this.f.size(); ++i3) {
            MapData.MapInfo mapdata_mapinfo = (MapData.MapInfo)this.f.get(i3);

            if (mapdata_mapinfo.b[i0] < 0 || mapdata_mapinfo.b[i0] > i1) {
                mapdata_mapinfo.b[i0] = i1;
            }

            if (mapdata_mapinfo.c[i0] < 0 || mapdata_mapinfo.c[i0] < i2) {
                mapdata_mapinfo.c[i0] = i2;
            }
        }
    }

    public MapData.MapInfo a(EntityPlayer entityplayer) {
        MapData.MapInfo mapdata_mapinfo = (MapData.MapInfo)this.i.get(entityplayer);

        if (mapdata_mapinfo == null) {
            mapdata_mapinfo = new MapData.MapInfo(entityplayer);
            this.i.put(entityplayer, mapdata_mapinfo);
            this.f.add(mapdata_mapinfo);
        }

        return mapdata_mapinfo;
    }

    public class MapCoord {

        public byte a;
        public byte b;
        public byte c;
        public byte d;

        public MapCoord(byte p_i2139_2_, byte p_i2139_3_, byte p_i2139_4_, byte p_i2139_5_) {
            this.a = p_i2139_2_;
            this.b = p_i2139_3_;
            this.c = p_i2139_4_;
            this.d = p_i2139_5_;
        }
    }

    public class MapInfo {

        public final EntityPlayer a;
        public int[] b = new int[128];
        public int[] c = new int[128];
        private int f;
        private int g;
        private byte[] h;
        public int d;
        private boolean i;

        public MapInfo(EntityPlayer p_i2138_2_) {
            this.a = p_i2138_2_;

            for (int i1 = 0; i1 < this.b.length; ++i1) {
                this.b[i1] = 0;
                this.c[i1] = 127;
            }
        }

        public byte[] a(ItemStack p_a_1_) {
            byte[] abyte;

            if (!this.i) {
                abyte = new byte[]{ (byte)2, MapData.this.d };
                this.i = true;
                return abyte;
            }
            else {
                int i1;
                int i2;

                if (--this.g < 0) {
                    this.g = 4;
                    abyte = new byte[MapData.this.g.size() * 3 + 1];
                    abyte[0] = 1;
                    i1 = 0;

                    for (Iterator iterator = MapData.this.g.values().iterator(); iterator.hasNext(); ++i1) {
                        MapData.MapCoord mapdata_mapcoord = (MapData.MapCoord)iterator.next();

                        abyte[i1 * 3 + 1] = (byte)(mapdata_mapcoord.a << 4 | mapdata_mapcoord.d & 15);
                        abyte[i1 * 3 + 2] = mapdata_mapcoord.b;
                        abyte[i1 * 3 + 3] = mapdata_mapcoord.c;
                    }

                    boolean flag0 = !p_a_1_.A();

                    if (this.h != null && this.h.length == abyte.length) {
                        for (i2 = 0; i2 < abyte.length; ++i2) {
                            if (abyte[i2] != this.h[i2]) {
                                flag0 = false;
                                break;
                            }
                        }
                    }
                    else {
                        flag0 = false;
                    }

                    if (!flag0) {
                        this.h = abyte;
                        return abyte;
                    }
                }

                for (int i3 = 0; i3 < 1; ++i3) {
                    i1 = this.f++ * 11 % 128;
                    if (this.b[i1] >= 0) {
                        int i4 = this.c[i1] - this.b[i1] + 1;

                        i2 = this.b[i1];
                        byte[] abyte1 = new byte[i4 + 3];

                        abyte1[0] = 0;
                        abyte1[1] = (byte)i1;
                        abyte1[2] = (byte)i2;

                        for (int i5 = 0; i5 < abyte1.length - 3; ++i5) {
                            abyte1[i5 + 3] = MapData.this.e[(i5 + i2) * 128 + i1];
                        }

                        this.c[i1] = -1;
                        this.b[i1] = -1;
                        return abyte1;
                    }
                }

                return null;
            }
        }
    }
}
