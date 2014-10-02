package net.minecraft.entity.item;

import net.canarymod.api.entity.vehicle.CanaryChestMinecart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
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

    public int n_() {
        return 27;
    }

    public EntityMinecart.EnumMinecartType s() {
        return EntityMinecart.EnumMinecartType.CHEST;
    }

    public IBlockState u() {
        return Blocks.ae.P().a(BlockChest.a, EnumFacing.NORTH);
    }

    public int w() {
        return 8;
    }

    public String k() {
        return "minecraft:chest";
    }

    public Container a(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
        return new ContainerChest(inventoryplayer, this, entityplayer);
    }
}
