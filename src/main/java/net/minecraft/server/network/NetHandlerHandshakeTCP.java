package net.minecraft.server.network;

import com.google.gson.Gson;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import net.canarymod.config.Configuration;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.net.InetSocketAddress;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer {
    private final MinecraftServer a;
    private final NetworkManager b;

    public NetHandlerHandshakeTCP(MinecraftServer minecraftserver, NetworkManager networkmanager) {
        this.a = minecraftserver;
        this.b = networkmanager;
    }

    public void a(C00Handshake c00handshake) {
        switch (NetHandlerHandshakeTCP.SwitchEnumConnectionState.a[c00handshake.a().ordinal()]) {
            case 1:
                this.b.a(EnumConnectionState.LOGIN);
                ChatComponentText chatcomponenttext;

                if (c00handshake.b() > 47) {
                    chatcomponenttext = new ChatComponentText("Outdated server! I\'m still on 1.8");
                    this.b.a((Packet)(new S00PacketDisconnect(chatcomponenttext)));
                    this.b.a((IChatComponent)chatcomponenttext);
                }
                else if (c00handshake.b() < 47) {
                    chatcomponenttext = new ChatComponentText("Outdated client! Please use 1.8");
                    this.b.a((Packet)(new S00PacketDisconnect(chatcomponenttext)));
                    this.b.a((IChatComponent)chatcomponenttext);
                }
                else {
                    this.b.a((INetHandler)(new NetHandlerLoginServer(this.a, this.b)));
                    // CanaryMod Start - Bungeecord!
                    if (Configuration.getServerConfig().getBungeecordSupport()) {
                        String[] split = c00handshake.b.split("\00");
                        if (split.length >= 3) {
                            c00handshake.b = split[0];
                            b.j = new InetSocketAddress(split[1], ((InetSocketAddress)b.j).getPort());
                            b.spoofedUUID = UUIDTypeAdapter.fromString(split[2]);

                            if (split.length == 4) {
                                b.spoofedProfile = new Gson().fromJson(split[3], Property[].class);
                            }
                        }
                        else {
                            chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
                            this.b.a(new S00PacketDisconnect(chatcomponenttext));
                            this.b.a(chatcomponenttext);
                            return;
                        }
                    }
                    //CanaryMod End - Bungeecord!
                }
                break;
            case 2:
                this.b.a(EnumConnectionState.STATUS);
                this.b.a((INetHandler)(new NetHandlerStatusServer(this.a, this.b)));
                break;
            default:
                throw new UnsupportedOperationException("Invalid intention " + c00handshake.a());
        }
    }

    public void a(IChatComponent ichatcomponent) {
    }

    static final class SwitchEnumConnectionState {

        static final int[] a = new int[EnumConnectionState.values().length];

        static {
            try {
                a[EnumConnectionState.LOGIN.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[EnumConnectionState.STATUS.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }
        }
    }
}