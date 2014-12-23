package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.canarymod.api.chat.CanaryChatComponent;
import net.canarymod.hook.system.ServerListPingHook;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.net.InetSocketAddress;

public class NetHandlerStatusServer implements INetHandlerStatusServer {

    private final MinecraftServer a;
    private final NetworkManager b;

    public NetHandlerStatusServer(MinecraftServer minecraftserver, NetworkManager networkmanager) {
        this.a = minecraftserver;
        this.b = networkmanager;
    }

    public void a(IChatComponent ichatcomponent) {
    }

    public void a(C00PacketServerQuery c00packetserverquery) {
        // CanaryMod: ServerListPingHook
        ServerStatusResponse ssr = this.a.aE();
        ServerListPingHook hook = (ServerListPingHook)new ServerListPingHook(((InetSocketAddress)this.b.b()).getAddress(), ((ChatComponentText)ssr.a()).getWrapper(), ssr.b().b(), ssr.b().a(), ssr.d(), Lists.newArrayList(ssr.b().c())).call();
        if (hook.isCanceled()) {
            // Response Denied!
            return;
        }
        // Recreate the ServerStatusResponse to be sent so that the default isn't destroyed
        ssr = new ServerStatusResponse();
        ssr.a(new ServerStatusResponse.MinecraftProtocolVersionIdentifier(this.a.F(), this.a.getProtocolVersion()));
        ServerStatusResponse.PlayerCountData ssrpcd = new ServerStatusResponse.PlayerCountData(hook.getMaxPlayers(), hook.getCurrentPlayers());
        ssrpcd.a(hook.getProfiles().toArray(new GameProfile[hook.getProfiles().size()]));
        ssr.a(ssrpcd); //Max/Online Players & GameProfiles
        ssr.a(((CanaryChatComponent) hook.getMotd()).getNative()); // MOTD
        ssr.a(hook.getFavicon()); // Server Favicon

        this.b.a((Packet)new S00PacketServerInfo(ssr));
        //
    }

    public void a(C01PacketPing c01packetping) {
        this.b.a((Packet)(new S01PacketPong(c01packetping.a())));
    }
}
