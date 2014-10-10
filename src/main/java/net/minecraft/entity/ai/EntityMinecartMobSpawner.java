package net.minecraft.entity.ai;

import net.canarymod.api.entity.vehicle.CanaryMobSpawnerMinecart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart {

    public final MobSpawnerBaseLogic a = new MobSpawnerBaseLogic() { // CanaryMod: private -> public

        public void a(int p_a_1_) {
            EntityMinecartMobSpawner.this.o.a((Entity) EntityMinecartMobSpawner.this, (byte) p_a_1_);
        }

        public World a() {
            return EntityMinecartMobSpawner.this.o;
        }

        public BlockPos b() {
            return new BlockPos(EntityMinecartMobSpawner.this);
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

    public EntityMinecart.EnumMinecartType s() {
        return EntityMinecart.EnumMinecartType.SPAWNER;
    }

    public IBlockState u() {
        return Blocks.ac.P();
    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a.a(nbttagcompound);
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        this.a.b(nbttagcompound);
    }

    public void s_() {
        super.s_();
        this.a.c();
    }
}
