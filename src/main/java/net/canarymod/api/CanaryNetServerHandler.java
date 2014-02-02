package net.canarymod.api;

import net.canarymod.api.chat.CanaryChatComponent;
import net.canarymod.api.chat.CanaryChatStyle;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.packet.Packet;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.StringTokenizer;

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
        if (!(((CanaryPacket) chatPacket).getPacket() instanceof S02PacketChat)) {
            return;
        }
        handler.a((S02PacketChat) ((CanaryPacket) chatPacket).getPacket());

    }

    @Override
    public void sendMessage(String msg) {
        //The char limit no longer applies
        handler.a(new S02PacketChat(compileChat(msg).getNative()));
        // Wait, compileChat(WAT)????, well it helps put things together properly, and not just stuffing raw chat into a container
    }

    @Override
    public void handleCommand(String[] command) {
        getUser().executeCommand(command);

    }

    @Override
    public void handleRespawn(Packet respawnPacket) {
        if (!(((CanaryPacket) respawnPacket).getPacket() instanceof S07PacketRespawn)) {
            return;
        }
        handler.a((S07PacketRespawn) ((CanaryPacket) respawnPacket).getPacket());
    }

    @Override
    public Player getUser() {
        return handler.b.getPlayer();
    }

    /**
     * Designed to properly handle text style
     */
    private CanaryChatComponent compileChat(String msg) {
        CanaryChatComponent toSend = new ChatComponentText("").getWrapper();
        StringTokenizer tokenizer = new StringTokenizer(msg, "\u00A7", true); // Need to know if it actually starts with a style char
        CanaryChatComponent working = new ChatComponentText("").getWrapper();
        while (tokenizer.hasMoreTokens()) {
            String workingMsg = tokenizer.nextToken();
            boolean doStyle = workingMsg.equals("\u00A7");
            if (doStyle) {
                if (!tokenizer.hasMoreTokens()) {
                    break; // dangling style; just drop it
                }
                workingMsg = tokenizer.nextToken();
                if (((CanaryChatStyle) working.getChatStyle()).getNative().equals(ChatStyle.j)) {
                    working.setChatStyle(new ChatStyle().getWrapper());
                }
                CanaryChatStyle style = (CanaryChatStyle) working.getChatStyle();
                EnumChatFormatting ecf = enumChatFormattingFromChar(workingMsg.toLowerCase().charAt(0));
                if (ecf != null) {
                    style.setColor(ecf.getWrapper());
                }
                else {
                    switch (workingMsg.toLowerCase().charAt(0)) {
                        case 'k':
                            style.setObfuscated(true);
                            break;
                        case 'l':
                            style.setBold(true);
                            break;
                        case 'm':
                            style.setStrikethrough(true);
                            break;
                        case 'n':
                            style.setUnderlined(true);
                            break;
                        case 'o':
                            style.setItalic(true);
                            break;
                    }
                }
                if (workingMsg.length() > 1) { // Only reset things if there was something beyond the style
                    toSend.appendSibling(working.appendText(workingMsg.substring(1)));
                    working = new ChatComponentText("").getWrapper();
                }
            }
            else {
                //No style in this part
                toSend.appendSibling(working.appendText(workingMsg));
                working = new ChatComponentText("").getWrapper();
            }
        }
        return toSend;
    }

    private EnumChatFormatting enumChatFormattingFromChar(char color) {
        switch (color) {
            case '0':
                return EnumChatFormatting.BLACK;
            case '1':
                return EnumChatFormatting.DARK_BLUE;
            case '2':
                return EnumChatFormatting.DARK_GREEN;
            case '3':
                return EnumChatFormatting.DARK_AQUA;
            case '4':
                return EnumChatFormatting.DARK_RED;
            case '5':
                return EnumChatFormatting.DARK_PURPLE;
            case '6':
                return EnumChatFormatting.GOLD;
            case '7':
                return EnumChatFormatting.GRAY;
            case '8':
                return EnumChatFormatting.DARK_GRAY;
            case '9':
                return EnumChatFormatting.BLUE;
            case 'a':
                return EnumChatFormatting.GREEN;
            case 'b':
                return EnumChatFormatting.AQUA;
            case 'c':
                return EnumChatFormatting.RED;
            case 'd':
                return EnumChatFormatting.LIGHT_PURPLE;
            case 'e':
                return EnumChatFormatting.YELLOW;
            case 'f':
                return EnumChatFormatting.WHITE;
            //Skip Stylizations
            default:
                return null;
        }
    }
}
