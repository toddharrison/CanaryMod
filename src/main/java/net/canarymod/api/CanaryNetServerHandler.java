package net.canarymod.api;

import java.net.SocketAddress;
import net.canarymod.api.chat.CanaryChatComponent;
import net.canarymod.api.chat.ChatComponent;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.packet.Packet;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;

import net.canarymod.Canary;
import net.minecraft.util.ChatComponentStyle;

/**
 * Wrap up NetServerHandler to minimize entry point to notch code
 *
 * @author Chris Ksoll (damagefilter)
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryNetServerHandler implements NetServerHandler {

    private NetHandlerPlayServer handler;

    public CanaryNetServerHandler(NetHandlerPlayServer handle) {
        handler = handle;
    }

    @Override
    public void sendPacket(Packet packet) {
        handler.a(((CanaryPacket)packet).getPacket());
    }

    public void sendPacket(net.minecraft.network.Packet packet) {
        handler.a(packet);
    }

    @Override
    public void handleChat(Packet chatPacket) {
        if (!(((CanaryPacket)chatPacket).getPacket() instanceof S02PacketChat)) {
            return;
        }
        sendPacket(chatPacket);
    }

    @Override
    public void sendMessage(String msg) {
        sendMessage(Canary.factory().getChatComponentFactory().compileChatComponent(msg));
    }

    @Override
    public void sendMessage(ChatComponent chatComponent){
        sendPacket(new S02PacketChat(((CanaryChatComponent)chatComponent).getNative()));
    }

    @Override
    public void handleCommand(String[] command) {
        getUser().executeCommand(command);
    }

    @Override
    public void handleRespawn(Packet respawnPacket) {
        if (!(((CanaryPacket)respawnPacket).getPacket() instanceof S07PacketRespawn)) {
            return;
        }
        sendPacket(respawnPacket);
    }

    @Override
    public Player getUser() {
        return handler.b.getPlayer();
    }
    
    @Override
    public SocketAddress getSocketAdress() {
        return handler.a.j;
    }
}
