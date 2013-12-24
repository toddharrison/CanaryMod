package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanarySkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntitySkull extends TileEntity {

    private int a;
    private int i;
    private String j = "";

    public TileEntitySkull() {
        this.complexBlock = new CanarySkull(this); // CanaryMod: wrap tile entity
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("SkullType", (byte) (this.a & 255));
        nbttagcompound.a("Rot", (byte) (this.i & 255));
        nbttagcompound.a("ExtraType", this.j);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.d("SkullType");
        this.i = nbttagcompound.d("Rot");
        if (nbttagcompound.b("ExtraType", 8)) {
            this.j = nbttagcompound.j("ExtraType");
        }
    }

    public Packet m() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.b(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.c, this.d, this.e, 4, nbttagcompound);
    }

    public void a(int i0, String s0) {
        this.a = i0;
        this.j = s0;
    }

    public int a() {
        return this.a;
    }

    public void a(int i0) {
        this.i = i0;
    }

    public String c() {
        return this.j;
    }

    // CanaryMod
    public int getRotation() {
        return this.b;
    }

    public CanarySkull getCanarySkull() {
        return (CanarySkull) complexBlock;
    }
}
