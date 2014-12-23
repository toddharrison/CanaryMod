package net.minecraft.tileentity;

import com.google.gson.JsonParseException;
import com.mojang.authlib.GameProfile;
import net.canarymod.ToolBox;
import net.canarymod.api.world.blocks.CanarySign;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.UUID;

public class TileEntitySign extends TileEntity {

    public final IChatComponent[] a = new IChatComponent[]{new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("")};
    public int f = -1;
    public boolean g = true; // CanaryMod: private => public; editable
    private EntityPlayer h;
    private final CommandResultStats i = new CommandResultStats();
    private String owner_name; // CanaryMod: Track owner name

    public TileEntitySign() {
        this.complexBlock = new CanarySign(this); // CanaryMod: wrap sign
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        for (int i0 = 0; i0 < 4; ++i0) {
            String s0 = IChatComponent.Serializer.a(this.a[i0]);

            nbttagcompound.a("Text" + (i0 + 1), s0);
        }

        nbttagcompound.a("Owner", this.h != null ? h.aJ().toString() : ""); // CanaryMod: check for an owner name
        this.i.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.g = false;
        super.a(nbttagcompound);
        ICommandSender icommandsender = new ICommandSender() {

            public String d_() {
                return "Sign";
            }

            public IChatComponent e_() {
                return new ChatComponentText(this.d_());
            }

            public void a(IChatComponent nbttagcompound) {
            }

            public boolean a(int nbttagcompound, String p_a_2_) {
                return true;
            }

            public BlockPos c() {
                return TileEntitySign.this.c;
            }

            public Vec3 d() {
                return new Vec3((double) TileEntitySign.this.c.n() + 0.5D, (double) TileEntitySign.this.c.o() + 0.5D, (double) TileEntitySign.this.c.p() + 0.5D);
            }

            public World e() {
                return TileEntitySign.this.b;
            }

            public Entity f() {
                return null;
            }

            public boolean t_() {
                return false;
            }

            public void a(CommandResultStats.Type nbttagcompound, int p_a_2_) {
            }
        };

        for (int i0 = 0; i0 < 4; ++i0) {
            String s0 = nbttagcompound.j("Text" + (i0 + 1));

            try {
                IChatComponent ichatcomponent = IChatComponent.Serializer.a(s0);

                try {
                    this.a[i0] = ChatComponentProcessor.a(icommandsender, ichatcomponent, (Entity) null);
                }
                catch (CommandException commandexception) {
                    this.a[i0] = ichatcomponent;
                }
            }
            catch (JsonParseException jsonparseexception) {
                this.a[i0] = new ChatComponentText(s0);
            }
        }

        this.owner_name = nbttagcompound.j("OwnerName"); // CanaryMod: Add OwnerName
        this.i.a(nbttagcompound);
    }

    public Packet x_() {
        IChatComponent[] aichatcomponent = new IChatComponent[4];

        System.arraycopy(this.a, 0, aichatcomponent, 0, 4);
        return new S33PacketUpdateSign(this.b, this.c, aichatcomponent);
    }

    public boolean b() {
        return this.g;
    }

    public void a(EntityPlayer entityplayer) {
        this.h = entityplayer;
    }

    public EntityPlayer c() {
        // CanaryMod: Set Player owner if not already set and if they are available to be set
        if (this.h == null) {
            if (this.owner_name.isEmpty()) {
                return null;
            }
            if (ToolBox.isUUID(owner_name)) {
                this.h = MinecraftServer.M().an().f(new GameProfile(UUID.fromString(owner_name), null));
            }
            else {
                this.h = MinecraftServer.M().an().f(new GameProfile(UUID.fromString(ToolBox.usernameToUUID(owner_name)), owner_name));
            }
        }
        //
        return this.h;
    }

    public boolean b(final EntityPlayer entityplayer) {
        ICommandSender icommandsender = new ICommandSender() {

            public String d_() {
                return entityplayer.d_();
            }

            public IChatComponent e_() {
                return entityplayer.e_();
            }

            public void a(IChatComponent p_a_1_) {
            }

            public boolean a(int p_a_1_, String p_a_2_) {
                return true;
            }

            public BlockPos c() {
                return TileEntitySign.this.c;
            }

            public Vec3 d() {
                return new Vec3((double) TileEntitySign.this.c.n() + 0.5D, (double) TileEntitySign.this.c.o() + 0.5D, (double) TileEntitySign.this.c.p() + 0.5D);
            }

            public World e() {
                return entityplayer.e();
            }

            public Entity f() {
                return entityplayer;
            }

            public boolean t_() {
                return false;
            }

            public void a(CommandResultStats.Type p_a_1_, int p_a_2_) {
                TileEntitySign.this.i.a(this, p_a_1_, p_a_2_);
            }
        };

        for (int i0 = 0; i0 < this.a.length; ++i0) {
            ChatStyle chatstyle = this.a[i0] == null ? null : this.a[i0].b();

            if (chatstyle != null && chatstyle.h() != null) {
                ClickEvent clickevent = chatstyle.h();

                if (clickevent.a() == ClickEvent.Action.RUN_COMMAND) {
                    MinecraftServer.M().O().a(icommandsender, clickevent.b());
                }
            }
        }

        return true;
    }

    public CommandResultStats d() {
        return this.i;
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
