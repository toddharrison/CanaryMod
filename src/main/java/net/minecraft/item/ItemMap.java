package net.minecraft.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multisets;
import net.canarymod.api.inventory.CanaryMapData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
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
        String s0 = "map_" + itemstack.i();
        world = CanaryMapData.getMapWorld(itemstack, world); // CanaryMod: correct world
        if (world == null) {
            // Bail!
            MapData brokenMap = new MapData(s0);
            brokenMap.mapUpdating = false;
            brokenMap.isBroken = true;
            return brokenMap;
        }

        MapData mapdata = (MapData)world.a(MapData.class, s0);

        if (mapdata == null && !world.D) {
            itemstack.b(world.b("map"));
            s0 = "map_" + itemstack.i();
            mapdata = new MapData(s0);
            mapdata.e = 3;
            mapdata.a((double)world.P().c(), (double)world.P().e(), mapdata.e);
            mapdata.d = (byte)world.t.q();
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
        if (world.t.q() == mapdata.d && entity instanceof EntityPlayer) {
            int i0 = 1 << mapdata.e;
            int i1 = mapdata.b;
            int i2 = mapdata.c;
            int i3 = MathHelper.c(entity.s - (double)i1) / i0 + 64;
            int i4 = MathHelper.c(entity.u - (double)i2) / i0 + 64;
            int i5 = 128 / i0;

            if (world.t.o()) {
                i5 /= 2;
            }

            MapData.MapInfo mapdata_mapinfo = mapdata.a((EntityPlayer)entity);

            ++mapdata_mapinfo.b;
            boolean flag0 = false;

            for (int i6 = i3 - i5 + 1; i6 < i3 + i5; ++i6) {
                if ((i6 & 15) == (mapdata_mapinfo.b & 15) || flag0) {
                    flag0 = false;
                    double d0 = 0.0D;

                    for (int i7 = i4 - i5 - 1; i7 < i4 + i5; ++i7) {
                        if (i6 >= 0 && i7 >= -1 && i6 < 128 && i7 < 128) {
                            int i8 = i6 - i3;
                            int i9 = i7 - i4;
                            boolean flag1 = i8 * i8 + i9 * i9 > (i5 - 2) * (i5 - 2);
                            int i10 = (i1 / i0 + i6 - 64) * i0;
                            int i11 = (i2 / i0 + i7 - 64) * i0;
                            HashMultiset hashmultiset = HashMultiset.create();
                            Chunk chunk = world.f(new BlockPos(i10, 0, i11));

                            if (!chunk.f()) {
                                int i12 = i10 & 15;
                                int i13 = i11 & 15;
                                int i14 = 0;
                                double d1 = 0.0D;
                                int i15;

                                if (world.t.o()) {
                                    i15 = i10 + i11 * 231871;
                                    i15 = i15 * i15 * 31287121 + i15 * 11;
                                    if ((i15 >> 20 & 1) == 0) {
                                        hashmultiset.add(Blocks.d.g(Blocks.d.P().a(BlockDirt.a, BlockDirt.DirtType.DIRT)), 10);
                                    }
                                    else {
                                        hashmultiset.add(Blocks.b.g(Blocks.b.P().a(BlockStone.a, BlockStone.EnumType.STONE)), 100);
                                    }

                                    d1 = 100.0D;
                                }
                                else {
                                    for (i15 = 0; i15 < i0; ++i15) {
                                        for (int i16 = 0; i16 < i0; ++i16) {
                                            int i17 = chunk.b(i15 + i12, i16 + i13) + 1;
                                            IBlockState iblockstate = Blocks.a.P();

                                            if (i17 > 1) {
                                                do {
                                                    --i17;
                                                    iblockstate = chunk.g(new BlockPos(i15 + i12, i17, i16 + i13));
                                                }
                                                while (iblockstate.c().g(iblockstate) == MapColor.b && i17 > 0);

                                                if (i17 > 0 && iblockstate.c().r().d()) {
                                                    int i18 = i17 - 1;

                                                    Block block;

                                                    do {
                                                        block = chunk.a(i15 + i12, i18--, i16 + i13);
                                                        ++i14;
                                                    }
                                                    while (i18 > 0 && block.r().d());
                                                }
                                            }

                                            d1 += (double)i17 / (double)(i0 * i0);
                                            hashmultiset.add(iblockstate.c().g(iblockstate));
                                        }
                                    }
                                }

                                i14 /= i0 * i0;
                                double d2 = (d1 - d0) * 4.0D / (double)(i0 + 4) + ((double)(i6 + i7 & 1) - 0.5D) * 0.4D;
                                byte b0 = 1;

                                if (d2 > 0.6D) {
                                    b0 = 2;
                                }

                                if (d2 < -0.6D) {
                                    b0 = 0;
                                }

                                MapColor mapcolor = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(hashmultiset), MapColor.b);

                                if (mapcolor == MapColor.n) {
                                    d2 = (double)i14 * 0.1D + (double)(i6 + i7 & 1) * 0.2D;
                                    b0 = 1;
                                    if (d2 < 0.5D) {
                                        b0 = 2;
                                    }

                                    if (d2 > 0.9D) {
                                        b0 = 0;
                                    }
                                }

                                d0 = d1;
                                if (i7 >= 0 && i8 * i8 + i9 * i9 < i5 * i5 && (!flag1 || (i6 + i7 & 1) != 0)) {
                                    byte b1 = mapdata.f[i6 + i7 * 128];
                                    byte b2 = (byte)(mapcolor.M * 4 + b0);

                                    if (b1 != b2) {
                                        mapdata.f[i6 + i7 * 128] = b2;
                                        mapdata.a(i6, i7);
                                        flag0 = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void a(ItemStack itemstack, World world, Entity entity, int i0, boolean flag0) {
        if (!world.D) {
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
        return this.a(itemstack, world).a(itemstack, world, entityplayer);
    }

    public void d(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (itemstack.n() && itemstack.o().n("map_is_scaling")) {
            MapData mapdata = Items.bd.a(itemstack, world);

            itemstack.b(world.b("map"));
            MapData mapdata1 = new MapData("map_" + itemstack.i());

            mapdata1.e = (byte)(mapdata.e + 1);
            if (mapdata1.e > 4) {
                mapdata1.e = 4;
            }

            mapdata1.a((double)mapdata.b, (double)mapdata.c, mapdata1.e);
            mapdata1.d = mapdata.d;
            mapdata1.c();
            world.a("map_" + itemstack.i(), (WorldSavedData)mapdata1);
        }
    }
}
