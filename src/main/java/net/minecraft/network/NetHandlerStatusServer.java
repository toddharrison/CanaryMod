package net.minecraft.network;

import io.netty.util.concurrent.GenericFutureListener;
import net.canarymod.hook.system.ServerListPingHook;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class NetHandlerStatusServer implements INetHandlerStatusServer {

    private final MinecraftServer a;
    private final INetworkManager b;

    public NetHandlerStatusServer(MinecraftServer minecraftserver, INetworkManager inetworkmanager) {
        this.a = minecraftserver;
        this.b = inetworkmanager;
    }

    public void a(IChatComponent ichatcomponent) {
    }

    public void a(EnumConnectionState enumconnectionstate, EnumConnectionState enumconnectionstate1) {
        if (enumconnectionstate1 != EnumConnectionState.STATUS) {
            throw new UnsupportedOperationException("Unexpected change in protocol to " + enumconnectionstate1);
        }
    }

    public void a() {
    }

    public void a(C00PacketServerQuery c00packetserverquery) {
        // CanaryMod: ServerListPingHook
        ServerStatusResponse ssr = this.a.at();
        ServerListPingHook hook = (ServerListPingHook) new ServerListPingHook(ssr.a().e(), ssr.b().b(), ssr.b().a()).call();
        if (hook.isCanceled()) {
            // Response Denied!
            return;
        }
        String serv_favicon = ssr.d(); //Maybe at some point i will make it to where this can be changed but for now I would rather not make a mess of it
        // Recreate the ServerStatusResponse to be sent so that the default isn't destroyed
        ssr = new ServerStatusResponse();
        ssr.a(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.7.2", 4)); //Protocol (do not change this at all!)
        ssr.a(new ServerStatusResponse.PlayerCountData(hook.getMaxPlayers(), hook.getCurrentPlayers())); //Max/Online Players
        ssr.a((IChatComponent) (new ChatComponentText(hook.getMotd()))); //MOTD
        ssr.a(serv_favicon); // Server Favicon
        this.b.a(new S00PacketServerInfo(ssr), new GenericFutureListener[0]);
        //
    }

    public void a(C01PacketPing c01packetping) {
        this.b.a(new S01PacketPong(c01packetping.c()), new GenericFutureListener[0]);
    }
}
