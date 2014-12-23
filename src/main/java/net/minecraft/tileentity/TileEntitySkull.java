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
    private int f;
    private GameProfile g = null;

    public TileEntitySkull() {
        this.complexBlock = new CanarySkull(this); // CanaryMod: wrap tile entity
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("SkullType", (byte)(this.a & 255));
        nbttagcompound.a("Rot", (byte)(this.f & 255));
        if (this.g != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            NBTUtil.a(nbttagcompound1, this.g);
            nbttagcompound.a("Owner", (NBTBase)nbttagcompound1);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.d("SkullType");
        this.f = nbttagcompound.d("Rot");
        if (this.a == 3) {
            if (nbttagcompound.b("Owner", 10)) {
                this.g = NBTUtil.a(nbttagcompound.m("Owner"));
            }
            else if (nbttagcompound.b("ExtraType", 8)) {
                String s0 = nbttagcompound.j("ExtraType");

                if (!StringUtils.b(s0)) {
                    this.g = new GameProfile((UUID)null, s0);
                    this.e();
                }
            }
        }
    }

    public GameProfile b() {
        return this.g;
    }

    public Packet x_() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.b(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.c, 4, nbttagcompound);
    }

    public void a(int i0) {
        this.a = i0;
        this.g = null;
    }

    public void a(GameProfile gameprofile) {
        this.a = 3;
        this.g = gameprofile;
        this.e();
    }

    private void e() {
        this.g = b(this.g);
        this.o_();
    }

    public static GameProfile b(GameProfile gameprofile) {
        if (gameprofile != null && !StringUtils.b(gameprofile.getName())) {
            if (gameprofile.isComplete() && gameprofile.getProperties().containsKey("textures")) {
                return gameprofile;
            }
            else if (MinecraftServer.M() == null) {
                return gameprofile;
            }
            else {
                GameProfile gameprofile1 = MinecraftServer.M().aD().a(gameprofile.getName());

                if (gameprofile1 == null) {
                    return gameprofile;
                }
                else {
                    Property property = (Property)Iterables.getFirst(gameprofile1.getProperties().get("textures"), (Object)null);

                    if (property == null) {
                        gameprofile1 = MinecraftServer.M().aB().fillProfileProperties(gameprofile1, true);
                    }

                    return gameprofile1;
                }
            }
        }
        else {
            return gameprofile;
        }
    }

    public int c() {
        return this.a;
    }

    public void b(int i0) {
        this.f = i0;
    }

    // CanaryMod
    public int getRotation() {
        return this.f;
    }

    public CanarySkull getCanarySkull() {
        return (CanarySkull)complexBlock;
    }
}
