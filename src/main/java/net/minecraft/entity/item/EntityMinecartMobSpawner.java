package net.minecraft.entity.item;

import net.canarymod.api.entity.vehicle.CanaryMobSpawnerMinecart;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart {

    public final MobSpawnerBaseLogic a = new MobSpawnerBaseLogic() { // CanaryMod: private -> public

        public void a(int var1) {
            EntityMinecartMobSpawner.this.p.a(EntityMinecartMobSpawner.this, (byte) var1);
        }

        public World a() {
            return EntityMinecartMobSpawner.this.p;
        }

        public int b() {
            return MathHelper.c(EntityMinecartMobSpawner.this.t);
        }

        public int c() {
            return MathHelper.c(EntityMinecartMobSpawner.this.u);
        }

        public int d() {
            return MathHelper.c(EntityMinecartMobSpawner.this.v);
        }
    };

    public EntityMinecartMobSpawner(World world) {
        super(world);
        this.entity = new CanaryMobSpawnerMinecart(this); // Wrap entity
    }

    public EntityMinecartMobSpawner(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanaryMobSpawnerMinecart(this); // Wrap entity
    }

    public int m() {
        return 4;
    }

    public Block o() {
        return Blocks.ac;
    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a.a(nbttagcompound);
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        this.a.b(nbttagcompound);
    }

    public void h() {
        super.h();
        this.a.g();
    }
}
