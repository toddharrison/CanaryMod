package net.minecraft.entity.item;

import net.canarymod.api.entity.vehicle.CanaryChestMinecart;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartChest extends EntityMinecartContainer {

    public EntityMinecartChest(World world) {
        super(world);
        this.entity = new CanaryChestMinecart(this); // CanaryMod: Wrap Entity
    }

    public EntityMinecartChest(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanaryChestMinecart(this); // CanaryMod: Wrap Entity
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        this.a(Item.a((Block) Blocks.ae), 1, 0.0F);
    }

    public int a() {
        return 27;
    }

    public int m() {
        return 1;
    }

    public Block o() {
        return Blocks.ae;
    }

    public int s() {
        return 8;
    }
}
