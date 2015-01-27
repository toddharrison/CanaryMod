package net.minecraft.server.management;

import com.google.common.collect.Lists;
import net.canarymod.Canary;
import net.canarymod.api.CanaryPlayerManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerManager {

    private static final Logger a = LogManager.getLogger();
    private final WorldServer b;
    private final List c = Lists.newArrayList();
    private final LongHashMap d = new LongHashMap();
    private final List e = Lists.newArrayList();
    private final List f = Lists.newArrayList();
    private int g;
    private long h;
    private final int[][] i = new int[][]{ { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    // CanaryMod
    private CanaryPlayerManager playerManager;

    //
    public PlayerManager(WorldServer worldserver) {
        this.b = worldserver;
        this.a(worldserver.r().an().t());
        playerManager = new CanaryPlayerManager(this, worldserver.getCanaryWorld());
    }

    public WorldServer a() {
        return this.b;
    }

    public void b() {
        long i0 = this.b.K();
        int i1;
        PlayerManager.PlayerInstance playermanager_playerinstance;

        if (i0 - this.h > 8000L) {
            this.h = i0;

            for (i1 = 0; i1 < this.f.size(); ++i1) {
                playermanager_playerinstance = (PlayerManager.PlayerInstance)this.f.get(i1);
                playermanager_playerinstance.b();
                playermanager_playerinstance.a();
            }
        }
        else {
            for (i1 = 0; i1 < this.e.size(); ++i1) {
                try {
                    playermanager_playerinstance = (PlayerManager.PlayerInstance) this.e.get(i1);
                    playermanager_playerinstance.b();
                }
                catch (NullPointerException npex){
                    Canary.log.debug("NullPointer supressed in PlayerManager", npex);
                }
            }
        }

        this.e.clear();
        if (this.c.isEmpty()) {
            WorldProvider worldprovider = this.b.t;

            if (!worldprovider.e()) {
                this.b.b.b();
            }
        }
    }

    public boolean a(int i0, int i1) {
        long i2 = (long)i0 + 2147483647L | (long)i1 + 2147483647L << 32;

        return this.d.a(i2) != null;
    }

    private PlayerManager.PlayerInstance a(int i0, int i1, boolean flag0) {
        long i2 = (long)i0 + 2147483647L | (long)i1 + 2147483647L << 32;
        PlayerManager.PlayerInstance playermanager_playerinstance = (PlayerManager.PlayerInstance)this.d.a(i2);

        if (playermanager_playerinstance == null && flag0) {
            playermanager_playerinstance = new PlayerManager.PlayerInstance(i0, i1);
            this.d.a(i2, playermanager_playerinstance);
            this.f.add(playermanager_playerinstance);
        }

        return playermanager_playerinstance;
    }

    public void a(BlockPos blockpos) {
        int i0 = blockpos.n() >> 4;
        int i1 = blockpos.p() >> 4;
        PlayerManager.PlayerInstance playermanager_playerinstance = this.a(i0, i1, false);

        if (playermanager_playerinstance != null) {
            playermanager_playerinstance.a(blockpos.n() & 15, blockpos.o(), blockpos.p() & 15);
        }
    }

    public void a(EntityPlayerMP entityplayermp) {
        int i0 = (int)entityplayermp.s >> 4;
        int i1 = (int)entityplayermp.u >> 4;

        entityplayermp.d = entityplayermp.s;
        entityplayermp.e = entityplayermp.u;

        for (int i2 = i0 - this.g; i2 <= i0 + this.g; ++i2) {
            for (int i3 = i1 - this.g; i3 <= i1 + this.g; ++i3) {
                this.a(i2, i3, true).a(entityplayermp);
            }
        }

        this.c.add(entityplayermp);
        this.b(entityplayermp);
    }

    public void b(EntityPlayerMP entityplayermp) {
        ArrayList arraylist = Lists.newArrayList(entityplayermp.f);
        int i0 = 0;
        int i1 = this.g;
        int i2 = (int)entityplayermp.s >> 4;
        int i3 = (int)entityplayermp.u >> 4;
        int i4 = 0;
        int i5 = 0;
        ChunkCoordIntPair chunkcoordintpair = this.a(i2, i3, true).c;

        entityplayermp.f.clear();
        if (arraylist.contains(chunkcoordintpair)) {
            entityplayermp.f.add(chunkcoordintpair);
        }

        int i6;

        for (i6 = 1; i6 <= i1 * 2; ++i6) {
            for (int i7 = 0; i7 < 2; ++i7) {
                int[] aint = this.i[i0++ % 4];

                for (int i8 = 0; i8 < i6; ++i8) {
                    i4 += aint[0];
                    i5 += aint[1];
                    chunkcoordintpair = this.a(i2 + i4, i3 + i5, true).c;
                    if (arraylist.contains(chunkcoordintpair)) {
                        entityplayermp.f.add(chunkcoordintpair);
                    }
                }
            }
        }

        i0 %= 4;

        for (i6 = 0; i6 < i1 * 2; ++i6) {
            i4 += this.i[i0][0];
            i5 += this.i[i0][1];
            chunkcoordintpair = this.a(i2 + i4, i3 + i5, true).c;
            if (arraylist.contains(chunkcoordintpair)) {
                entityplayermp.f.add(chunkcoordintpair);
            }
        }
    }

    public void c(EntityPlayerMP entityplayermp) {
        int i0 = (int)entityplayermp.d >> 4;
        int i1 = (int)entityplayermp.e >> 4;

        for (int i2 = i0 - this.g; i2 <= i0 + this.g; ++i2) {
            for (int i3 = i1 - this.g; i3 <= i1 + this.g; ++i3) {
                PlayerManager.PlayerInstance playermanager_playerinstance = this.a(i2, i3, false);

                if (playermanager_playerinstance != null) {
                    playermanager_playerinstance.b(entityplayermp);
                }
            }
        }

        this.c.remove(entityplayermp);
    }

    private boolean a(int i0, int i1, int i2, int i3, int i4) {
        int i5 = i0 - i2;
        int i6 = i1 - i3;

        return i5 >= -i4 && i5 <= i4 ? i6 >= -i4 && i6 <= i4 : false;
    }

    public void d(EntityPlayerMP entityplayermp) {
        int i0 = (int)entityplayermp.s >> 4;
        int i1 = (int)entityplayermp.u >> 4;
        double d0 = entityplayermp.d - entityplayermp.s;
        double d1 = entityplayermp.e - entityplayermp.u;
        double d2 = d0 * d0 + d1 * d1;

        if (d2 >= 64.0D) {
            int i2 = (int)entityplayermp.d >> 4;
            int i3 = (int)entityplayermp.e >> 4;
            int i4 = this.g;
            int i5 = i0 - i2;
            int i6 = i1 - i3;

            if (i5 != 0 || i6 != 0) {
                for (int i7 = i0 - i4; i7 <= i0 + i4; ++i7) {
                    for (int i8 = i1 - i4; i8 <= i1 + i4; ++i8) {
                        if (!this.a(i7, i8, i2, i3, i4)) {
                            this.a(i7, i8, true).a(entityplayermp);
                        }

                        if (!this.a(i7 - i5, i8 - i6, i0, i1, i4)) {
                            PlayerManager.PlayerInstance playermanager_playerinstance = this.a(i7 - i5, i8 - i6, false);

                            if (playermanager_playerinstance != null) {
                                playermanager_playerinstance.b(entityplayermp);
                            }
                        }
                    }
                }

                this.b(entityplayermp);
                entityplayermp.d = entityplayermp.s;
                entityplayermp.e = entityplayermp.u;
            }
        }
    }

    public boolean a(EntityPlayerMP entityplayermp, int i0, int i1) {
        PlayerManager.PlayerInstance playermanager_playerinstance = this.a(i0, i1, false);

        return playermanager_playerinstance != null && playermanager_playerinstance.b.contains(entityplayermp) && !entityplayermp.f.contains(playermanager_playerinstance.c);
    }

    public void a(int i0) {
        i0 = MathHelper.a(i0, 3, 32);
        if (i0 != this.g) {
            int i1 = i0 - this.g;
            ArrayList arraylist = Lists.newArrayList(this.c);
            Iterator iterator = arraylist.iterator();

            while (iterator.hasNext()) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
                int i2 = (int)entityplayermp.s >> 4;
                int i3 = (int)entityplayermp.u >> 4;
                int i4;
                int i5;

                if (i1 > 0) {
                    for (i4 = i2 - i0; i4 <= i2 + i0; ++i4) {
                        for (i5 = i3 - i0; i5 <= i3 + i0; ++i5) {
                            PlayerManager.PlayerInstance playermanager_playerinstance = this.a(i4, i5, true);

                            if (!playermanager_playerinstance.b.contains(entityplayermp)) {
                                playermanager_playerinstance.a(entityplayermp);
                            }
                        }
                    }
                }
                else {
                    for (i4 = i2 - this.g; i4 <= i2 + this.g; ++i4) {
                        for (i5 = i3 - this.g; i5 <= i3 + this.g; ++i5) {
                            if (!this.a(i4, i5, i2, i3, i0)) {
                                this.a(i4, i5, true).b(entityplayermp);
                            }
                        }
                    }
                }
            }

            this.g = i0;
        }
    }

    public static int b(int i0) {
        return i0 * 16 - 16;
    }

    class PlayerInstance {

        private final List b = Lists.newArrayList();
        private final ChunkCoordIntPair c;
        private short[] d = new short[64];
        private int e;
        private int f;
        private long g;

        public PlayerInstance(int i0, int i1) {
            this.c = new ChunkCoordIntPair(i0, i1);
            PlayerManager.this.a().b.c(i0, i1);
        }

        public void a(EntityPlayerMP entityplayermp) {
            if (this.b.contains(entityplayermp)) {
                PlayerManager.a.debug("Failed to add player. " + entityplayermp + " already is in chunk " + this.c.a + ", " + this.c.b);
            }
            else {
                if (this.b.isEmpty()) {
                    this.g = PlayerManager.this.b.K();
                }

                this.b.add(entityplayermp);
                entityplayermp.f.add(this.c);
            }
        }

        public void b(EntityPlayerMP entityplayermp) {
            if (this.b.contains(entityplayermp)) {
                Chunk chunk = PlayerManager.this.b.a(this.c.a, this.c.b);

                if (chunk.i()) {
                    entityplayermp.a.a((Packet)(new S21PacketChunkData(chunk, true, 0)));
                }

                this.b.remove(entityplayermp);
                entityplayermp.f.remove(this.c);
                if (this.b.isEmpty()) {
                    long i5 = (long)this.c.a + 2147483647L | (long)this.c.b + 2147483647L << 32;

                    this.a(chunk);
                    PlayerManager.this.d.d(i5);
                    PlayerManager.this.f.remove(this);
                    if (this.e > 0) {
                        PlayerManager.this.e.remove(this);
                    }

                    PlayerManager.this.a().b.b(this.c.a, this.c.b);
                }
            }
        }

        public void a() {
            this.a(PlayerManager.this.b.a(this.c.a, this.c.b));
        }

        private void a(Chunk chunk) {
            chunk.c(chunk.w() + PlayerManager.this.b.K() - this.g);
            this.g = PlayerManager.this.b.K();
        }

        public void a(int i0, int i1, int i2) {
            if (this.e == 0) {
                PlayerManager.this.e.add(this);
            }

            this.f |= 1 << (i1 >> 4);
            if (this.e < 64) {
                short short1 = (short)(i0 << 12 | i2 << 8 | i1);

                for (int list = 0; list < this.e; ++list) {
                    if (this.d[list] == short1) {
                        return;
                    }
                }

                this.d[this.e++] = short1;
            }
        }

        public void a(Packet packet) {
            for (int i0 = 0; i0 < this.b.size(); ++i0) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)this.b.get(i0);

                if (!entityplayermp.f.contains(this.c)) {
                    entityplayermp.a.a(packet);
                }
            }
        }

        public void b() {
            if (this.e != 0) {
                int i3;
                int packet;
                int i5;

                if (this.e == 1) {
                    i3 = (this.d[0] >> 12 & 15) + this.c.a * 16;
                    packet = this.d[0] & 255;
                    i5 = (this.d[0] >> 8 & 15) + this.c.b * 16;
                    BlockPos blockpos = new BlockPos(i3, packet, i5);

                    this.a((Packet)(new S23PacketBlockChange(PlayerManager.this.b, blockpos)));
                    if (PlayerManager.this.b.p(blockpos).c().x()) {
                        this.a(PlayerManager.this.b.s(blockpos));
                    }
                }
                else {
                    int i6;

                    if (this.e == 64) {
                        i3 = this.c.a * 16;
                        packet = this.c.b * 16;
                        this.a((Packet)(new S21PacketChunkData(PlayerManager.this.b.a(this.c.a, this.c.b), false, this.f)));

                        for (i5 = 0; i5 < 16; ++i5) {
                            if ((this.f & 1 << i5) != 0) {
                                i6 = i5 << 4;
                                List list = PlayerManager.this.b.a(i3, i6, packet, i3 + 16, i6 + 16, packet + 16);

                                for (int i7 = 0; i7 < list.size(); ++i7) {
                                    this.a((TileEntity)list.get(i7));
                                }
                            }
                        }
                    }
                    else {
                        this.a((Packet)(new S22PacketMultiBlockChange(this.e, this.d, PlayerManager.this.b.a(this.c.a, this.c.b))));

                        for (i3 = 0; i3 < this.e; ++i3) {
                            packet = (this.d[i3] >> 12 & 15) + this.c.a * 16;
                            i5 = this.d[i3] & 255;
                            i6 = (this.d[i3] >> 8 & 15) + this.c.b * 16;
                            BlockPos blockpos1 = new BlockPos(packet, i5, i6);

                            if (PlayerManager.this.b.p(blockpos1).c().x()) {
                                this.a(PlayerManager.this.b.s(blockpos1));
                            }
                        }
                    }
                }

                this.e = 0;
                this.f = 0;
            }
        }

        private void a(TileEntity tileentity) {
            if (tileentity != null) {
                Packet packet = tileentity.x_();

                if (packet != null) {
                    this.a(packet);
                }
            }
        }
    }

    /* CanaryMod */
    public CanaryPlayerManager getPlayerManager() {
        return playerManager;
    }

    public List<EntityPlayerMP> getManagedPlayers() {
        return c;
    }

    public int getPlayerViewRadius() {
        return g;
    }
    /* CanaryMod */
}
