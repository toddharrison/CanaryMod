package net.minecraft.tileentity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.canarymod.api.world.blocks.CanarySkull;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;

import java.util.UUID;

public class TileEntitySkull extends TileEntity {

    private int a;
    private int i;
    private GameProfile j = null;

    public TileEntitySkull() {
        this.complexBlock = new CanarySkull(this); // CanaryMod: wrap tile entity
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("SkullType", (byte) (this.a & 255));
        nbttagcompound.a("Rot", (byte) (this.i & 255));
        if (this.j != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            NBTUtil.a(nbttagcompound1, this.j);
            nbttagcompound.a("Owner", (NBTBase) nbttagcompound1);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.d("SkullType");
        this.i = nbttagcompound.d("Rot");
        if (this.a == 3) {
            if (nbttagcompound.b("Owner", 10)) {
                this.j = NBTUtil.a(nbttagcompound.m("Owner"));
            }
            else if (nbttagcompound.b("ExtraType", 8) && !StringUtils.b(nbttagcompound.j("ExtraType"))) {
                this.j = new GameProfile((UUID) null, nbttagcompound.j("ExtraType"));
                this.d();
            }
        }

    }

    public GameProfile a() {
        return this.j;
    }

    public Packet m() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.b(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.c, this.d, this.e, 4, nbttagcompound);
    }

    public void a(int i0) {
        this.a = i0;
        this.j = null;
    }

    public void a(GameProfile gameprofile) {
        this.a = 3;
        this.j = gameprofile;
        this.d();
    }

    private void d() {
        if (this.j != null && !StringUtils.b(this.j.getName())) {
            if (!this.j.isComplete() || !this.j.getProperties().containsKey("textures")) {
                GameProfile gameprofile = MinecraftServer.I().ax().a(this.j.getName());

                if (gameprofile != null) {
                    Property property = (Property) Iterables.getFirst(gameprofile.getProperties().get("textures"), (Object) null);

                    if (property == null) {
                        gameprofile = MinecraftServer.I().av().fillProfileProperties(gameprofile, true);
                    }

                    this.j = gameprofile;
                    this.e();
                }
            }
        }
    }

    public int b() {
        return this.a;
    }

    public void b(int i0) {
        this.i = i0;
    }

    // CanaryMod
    public int getRotation() {
        return this.i;
    }

    public CanarySkull getCanarySkull() {
        return (CanarySkull) complexBlock;
    }
}
