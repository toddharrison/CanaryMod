package net.minecraft.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multisets;
import net.canarymod.api.inventory.CanaryMapData;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;

public class ItemMap extends ItemMapBase {

    protected ItemMap() {
        this.a(true);
    }

    public MapData a(ItemStack itemstack, World world) {
        String s0 = "map_" + itemstack.k();
        world = CanaryMapData.getMapWorld(itemstack, world); // CanaryMod: correct world
        if (world == null) {
            // Bail!
            MapData brokenMap = new MapData(s0);
            brokenMap.mapUpdating = false;
            brokenMap.isBroken = true;
            return brokenMap;
        }

        MapData mapdata = (MapData)world.a(MapData.class, s0);

        if (mapdata == null && !world.E) {
            itemstack.b(world.b("map"));
            s0 = "map_" + itemstack.k();
            mapdata = new MapData(s0);
            mapdata.d = 3;
            int i0 = 128 * (1 << mapdata.d);

            mapdata.a = Math.round((float)world.N().c() / (float)i0) * i0;
            mapdata.b = Math.round((float)(world.N().e() / i0)) * i0;
            mapdata.c = (byte)world.t.i;
            mapdata.c();
            world.a(s0, (WorldSavedData)mapdata);
        }

        if (mapdata != null && mapdata.worldName.isEmpty()) {
            mapdata.worldName = world.getCanaryWorld().getFqName();
        }
        return mapdata;
    }

    public void a(World world, Entity entity, MapData mapdata) {
        // CanaryMod: Check mapdata for mapUpdating and fix for multiworld causing overriding maps
        if (!mapdata.mapUpdating || !mapdata.worldName.equals(world.getCanaryWorld().getFqName()) || mapdata.isBroken) {
            return;
        }
        //
        if (world.t.i == mapdata.c && entity instanceof EntityPlayer) {
            int i0 = 1 << mapdata.d;
            int i1 = mapdata.a;
            int i2 = mapdata.b;
            int i3 = MathHelper.c(entity.s - (double)i1) / i0 + 64;
            int i4 = MathHelper.c(entity.u - (double)i2) / i0 + 64;
            int i5 = 128 / i0;

            if (world.t.g) {
                i5 /= 2;
            }

            MapData.MapInfo mapdata_mapinfo = mapdata.a((EntityPlayer)entity);

            ++mapdata_mapinfo.d;

            for (int i6 = i3 - i5 + 1; i6 < i3 + i5; ++i6) {
                if ((i6 & 15) == (mapdata_mapinfo.d & 15)) {
                    int i7 = 255;
                    int i8 = 0;
                    double d0 = 0.0D;

                    for (int i9 = i4 - i5 - 1; i9 < i4 + i5; ++i9) {
                        if (i6 >= 0 && i9 >= -1 && i6 < 128 && i9 < 128) {
                            int i10 = i6 - i3;
                            int i11 = i9 - i4;
                            boolean flag0 = i10 * i10 + i11 * i11 > (i5 - 2) * (i5 - 2);
                            int i12 = (i1 / i0 + i6 - 64) * i0;
                            int i13 = (i2 / i0 + i9 - 64) * i0;
                            HashMultiset hashmultiset = HashMultiset.create();
                            Chunk chunk = world.d(i12, i13);

                            if (!chunk.g()) {
                                int i14 = i12 & 15;
                                int i15 = i13 & 15;
                                int i16 = 0;
                                double d1 = 0.0D;
                                int i17;

                                if (world.t.g) {
                                    i17 = i12 + i13 * 231871;
                                    i17 = i17 * i17 * 31287121 + i17 * 11;
                                    if ((i17 >> 20 & 1) == 0) {
                                        hashmultiset.add(Blocks.d.f(0), 10);
                                    }
                                    else {
                                        hashmultiset.add(Blocks.b.f(0), 100);
                                    }

                                    d1 = 100.0D;
                                }
                                else {
                                    for (i17 = 0; i17 < i0; ++i17) {
                                        for (int i18 = 0; i18 < i0; ++i18) {
                                            int i19 = chunk.b(i17 + i14, i18 + i15) + 1;
                                            Block block = Blocks.a;
                                            int i20 = 0;

                                            if (i19 > 1) {
                                                do {
                                                    --i19;
                                                    block = chunk.a(i17 + i14, i19, i18 + i15);
                                                    i20 = chunk.c(i17 + i14, i19, i18 + i15);
                                                }
                                                while (block.f(i20) == MapColor.b && i19 > 0);

                                                if (i19 > 0 && block.o().d()) {
                                                    int i21 = i19 - 1;

                                                    Block block1;

                                                    do {
                                                        block1 = chunk.a(i17 + i14, i21--, i18 + i15);
                                                        ++i16;
                                                    }
                                                    while (i21 > 0 && block1.o().d());
                                                }
                                            }

                                            d1 += (double)i19 / (double)(i0 * i0);
                                            hashmultiset.add(block.f(i20));
                                        }
                                    }
                                }

                                i16 /= i0 * i0;
                                double d2 = (d1 - d0) * 4.0D / (double)(i0 + 4) + ((double)(i6 + i9 & 1) - 0.5D) * 0.4D;
                                byte b0 = 1;

                                if (d2 > 0.6D) {
                                    b0 = 2;
                                }

                                if (d2 < -0.6D) {
                                    b0 = 0;
                                }

                                MapColor mapcolor = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(hashmultiset), MapColor.b);

                                if (mapcolor == MapColor.n) {
                                    d2 = (double)i16 * 0.1D + (double)(i6 + i9 & 1) * 0.2D;
                                    b0 = 1;
                                    if (d2 < 0.5D) {
                                        b0 = 2;
                                    }

                                    if (d2 > 0.9D) {
                                        b0 = 0;
                                    }
                                }

                                d0 = d1;
                                if (i9 >= 0 && i10 * i10 + i11 * i11 < i5 * i5 && (!flag0 || (i6 + i9 & 1) != 0)) {
                                    byte b1 = mapdata.e[i6 + i9 * 128];
                                    byte b2 = (byte)(mapcolor.M * 4 + b0);

                                    if (b1 != b2) {
                                        if (i7 > i9) {
                                            i7 = i9;
                                        }

                                        if (i8 < i9) {
                                            i8 = i9;
                                        }

                                        mapdata.e[i6 + i9 * 128] = b2;
                                    }
                                }
                            }
                        }
                    }

                    if (i7 <= i8) {
                        mapdata.a(i6, i7, i8);
                    }
                }
            }
        }
    }

    public void a(ItemStack itemstack, World world, Entity entity, int i0, boolean flag0) {
        if (!world.E) {
            MapData mapdata = this.a(itemstack, world);

            // CanaryMod: Check mapdata for mapUpdating and fix for multiworld causing overriding maps
            if (!mapdata.mapUpdating || !mapdata.worldName.equals(world.getCanaryWorld().getFqName()) || mapdata.isBroken) {
                return;
            }
            //

            if (entity instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer)entity;

                mapdata.a(entityplayer, itemstack);
            }

            if (flag0) {
                this.a(world, entity, mapdata);
            }
        }
    }

    public Packet c(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        byte[] abyte = this.a(itemstack, world).a(itemstack, world, entityplayer);

        return abyte == null ? null : new S34PacketMaps(itemstack.k(), abyte);
    }

    public void d(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (itemstack.p() && itemstack.q().n("map_is_scaling")) {
            MapData mapdata = Items.aY.a(itemstack, world);

            itemstack.b(world.b("map"));
            MapData mapdata1 = new MapData("map_" + itemstack.k());

            mapdata1.d = (byte)(mapdata.d + 1);
            if (mapdata1.d > 4) {
                mapdata1.d = 4;
            }

            mapdata1.a = mapdata.a;
            mapdata1.b = mapdata.b;
            mapdata1.c = mapdata.c;
            mapdata1.c();
            world.a("map_" + itemstack.k(), (WorldSavedData)mapdata1);
        }
    }
}
