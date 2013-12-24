package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryMobSpawner;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity {

    private final MobSpawnerBaseLogic a = new MobSpawnerBaseLogic() {

        public void a(int var1) {
            TileEntityMobSpawner.this.b.c(TileEntityMobSpawner.this.c, TileEntityMobSpawner.this.d, TileEntityMobSpawner.this.e, Blocks.ac, var1, 0);
        }

        public World a() {
            return TileEntityMobSpawner.this.b;
        }

        public int b() {
            return TileEntityMobSpawner.this.c;
        }

        public int c() {
            return TileEntityMobSpawner.this.d;
        }

        public int d() {
            return TileEntityMobSpawner.this.e;
        }

        public void a(WeightedRandomMinecart var1) {
            super.a(var1);
            if (this.a() != null) {
                this.a().g(TileEntityMobSpawner.this.c, TileEntityMobSpawner.this.d, TileEntityMobSpawner.this.e);
            }

        }
    };

    public TileEntityMobSpawner() {
        this.complexBlock = new CanaryMobSpawner(this);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a.a(nbttagcompound);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        this.a.b(nbttagcompound);
    }

    public void h() {
        this.a.g();
        super.h();
    }

    public Packet m() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.b(nbttagcompound);
        nbttagcompound.o("SpawnPotentials");
        return new S35PacketUpdateTileEntity(this.c, this.d, this.e, 1, nbttagcompound);
    }

    public boolean c(int i0, int i1) {
        return this.a.b(i0) ? true : super.c(i0, i1);
    }

    public MobSpawnerBaseLogic a() {
        return this.a;
    }

    // CanaryMod
    public CanaryMobSpawner getCanaryMobSpawner() {
        return (CanaryMobSpawner) complexBlock;
    }
}
