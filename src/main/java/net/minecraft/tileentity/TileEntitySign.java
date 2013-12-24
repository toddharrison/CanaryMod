package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanarySign;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.server.MinecraftServer;

public class TileEntitySign extends TileEntity {

    public String[] a = new String[]{ "", "", "", "" };
    public int i = -1;
    public boolean j = true; // CanaryMod: private => public; editable
    private EntityPlayer k;
    private String owner_name; // CanaryMod: Track owner name

    public TileEntitySign() {
        this.complexBlock = new CanarySign(this); // CanaryMod: wrap sign
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Text1", this.a[0]);
        nbttagcompound.a("Text2", this.a[1]);
        nbttagcompound.a("Text3", this.a[2]);
        nbttagcompound.a("Text4", this.a[3]);
        nbttagcompound.a("Owner", this.k != null ? k.b_() : "");
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.j = false;
        super.a(nbttagcompound);

        for (int i0 = 0; i0 < 4; ++i0) {
            this.a[i0] = nbttagcompound.j("Text" + (i0 + 1));
            if (this.a[i0].length() > 15) {
                this.a[i0] = this.a[i0].substring(0, 15);
            }
        }
        this.owner_name = nbttagcompound.j("OwnerName");
    }

    public Packet m() {
        String[] astring = new String[4];

        System.arraycopy(this.a, 0, astring, 0, 4);
        return new S33PacketUpdateSign(this.c, this.d, this.e, astring);
    }

    public boolean a() {
        return this.j;
    }

    public void a(EntityPlayer entityplayer) {
        this.k = entityplayer;
    }

    public EntityPlayer b() {
        // CanaryMod: Set Player owner if not already set and if they are available to be set
        if (this.k == null) {
            if (this.owner_name.isEmpty()) {
                return null;
            }
            this.k = MinecraftServer.G().af().e(owner_name);
        }
        //
        return this.k;
    }

    // CanaryMod
    public CanarySign getCanarySign() {
        return (CanarySign) complexBlock;
    }

    public String getOwnerName() {
        return owner_name;
    }
    //
}
