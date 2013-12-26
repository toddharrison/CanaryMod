package net.canarymod.api;

import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.packet.Packet;
import net.canarymod.chat.TextFormat;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.util.ChatComponentText;

/**
 * Wrap up NetServerHandler to minimize entry point to notch code
 *
 * @author Chris Ksoll
 */
public class CanaryNetServerHandler implements NetServerHandler {

    private NetHandlerPlayServer handler;

    public CanaryNetServerHandler(NetHandlerPlayServer handle) {
        handler = handle;
    }

    @Override
    public void sendPacket(Packet packet) {
        handler.a(((CanaryPacket) packet).getPacket());

    }

    public void sendPacket(net.minecraft.network.Packet packet) {
        handler.a(packet);
    }

    @Override
    public void handleChat(Packet chatPacket) {
        if (chatPacket.getPacketId() != 3) {
            return; // Not a chat packet :O
        }
        handler.a((S02PacketChat) ((CanaryPacket) chatPacket).getPacket());

    }

    @Override
    public void sendMessage(String msg) {
        if (msg.length() >= 119) {
            String cutMsg = msg.substring(0, 118);
            int finalCut = cutMsg.lastIndexOf(" ");
            if (finalCut == -1) {
                finalCut = 118;
            }
            String subCut = cutMsg.substring(0, finalCut);
            String newMsg = msg.substring(finalCut);

            handler.a(new S02PacketChat(new ChatComponentText(subCut)));
            String lastColor = TextFormat.getLastColor(subCut);
            sendMessage((lastColor == null ? "" : lastColor) + newMsg);
        }
        else {
            handler.a(new S02PacketChat(new ChatComponentText(msg)));
        }
    }

    @Override
    public void handleCommand(String[] command) {
        getUser().executeCommand(command);

    }

    @Override
    public void handleRespawn(Packet respawnPacket) {
        if (respawnPacket.getPacketId() != 9) {
            return; // Not a respawning packet :O
        }
        handler.a((S07PacketRespawn) ((CanaryPacket) respawnPacket).getPacket());
    }

    @Override
    public Player getUser() {
        return handler.b.getPlayer();
    }

}
