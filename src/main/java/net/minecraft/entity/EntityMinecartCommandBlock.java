package net.minecraft.entity;

import net.canarymod.api.entity.vehicle.CanaryCommandBlockMinecart;
import net.minecraft.block.Block;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public class EntityMinecartCommandBlock extends EntityMinecart {

    private final CommandBlockLogic a = new CommandBlockLogic() {

        public void e() {
            EntityMinecartCommandBlock.this.z().b(23, this.i());
            EntityMinecartCommandBlock.this.z().b(24, IChatComponent.Serializer.a(this.h()));
        }

        public ChunkCoordinates f_() {
            return new ChunkCoordinates(MathHelper.c(EntityMinecartCommandBlock.this.t), MathHelper.c(EntityMinecartCommandBlock.this.u + 0.5D), MathHelper.c(EntityMinecartCommandBlock.this.v));
        }

        public World d() {
            return EntityMinecartCommandBlock.this.p;
        }

        public net.canarymod.api.CommandBlockLogic getReference() {
            return (net.canarymod.api.CommandBlockLogic) EntityMinecartCommandBlock.this.getCanaryEntity();
        }
    };
    private int b = 0;

    public EntityMinecartCommandBlock(World world) {
        super(world);
        this.entity = new CanaryCommandBlockMinecart(this); // CanaryMod: Wrap Entity
    }

    public EntityMinecartCommandBlock(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanaryCommandBlockMinecart(this); // CanaryMod: Wrap Entity
    }

    protected void c() {
        super.c();
        this.z().a(23, "");
        this.z().a(24, "");
    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a.b(nbttagcompound);
        this.z().b(23, this.e().i());
        this.z().b(24, IChatComponent.Serializer.a(this.e().h()));
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        this.a.a(nbttagcompound);
    }

    public int m() {
        return 6;
    }

    public Block o() {
        return Blocks.bI;
    }

    public CommandBlockLogic e() {
        return this.a;
    }

    public void a(int i0, int i1, int i2, boolean flag0) {
        if (flag0 && this.ab - this.b >= 4) {
            this.e().a(this.p);
            this.b = this.ab;
        }

    }

    public boolean c(EntityPlayer entityplayer) {
        if (this.p.E) {
            entityplayer.a(this.e());
        }

        return super.c(entityplayer);
    }

    public void i(int i0) {
        super.i(i0);
        if (i0 == 24) {
            try {
                this.a.b(IChatComponent.Serializer.a(this.z().e(24)));
            } catch (Throwable throwable) {
                ;
            }
        } else if (i0 == 23) {
            this.a.a(this.z().e(23));
        }

    }

    // CanaryMod
    public CommandBlockLogic getLogic() {
        return this.a;
    }
    //
}
