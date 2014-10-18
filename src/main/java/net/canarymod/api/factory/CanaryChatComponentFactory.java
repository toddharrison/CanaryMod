package net.canarymod.api.factory;

import net.canarymod.Canary;
import net.canarymod.api.chat.*;
import net.canarymod.api.chat.ChatStyle;
import net.canarymod.api.chat.ClickEvent;
import net.canarymod.api.chat.HoverEvent;
import net.minecraft.util.*;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Implementation of ChatComponentFactory
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryChatComponentFactory implements ChatComponentFactory {
    private final Pattern urlPattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");


    /**
     * {@inheritDoc}
     */
    @Override
    public ChatComponent newChatComponent(String text) {
        return new ChatComponentText(text).getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatComponent compileChatComponent(String text){
        CanaryChatComponent toSend = new ChatComponentText("").getWrapper();
        StringTokenizer tokenizer = new StringTokenizer(text, "\u00A7", true); // Need to know if it actually starts with a style char
        CanaryChatComponent working = new ChatComponentText("").getWrapper();
        while (tokenizer.hasMoreTokens()) {
            String workingMsg = tokenizer.nextToken();
            boolean doStyle = workingMsg.equals("\u00A7");
            if (doStyle) {
                if (!tokenizer.hasMoreTokens()) {
                    break; // dangling style; just drop it
                }
                workingMsg = tokenizer.nextToken();
                if (((CanaryChatStyle)working.getChatStyle()).getNative().equals(net.minecraft.util.ChatStyle.k)) {
                    working.setChatStyle(new net.minecraft.util.ChatStyle().getWrapper());
                }
                CanaryChatStyle style = (CanaryChatStyle)working.getChatStyle();
                char code = workingMsg.toLowerCase().charAt(0);
                EnumChatFormatting ecf = enumChatFormattingFromChar(code);
                if (ecf != null) {
                    style.setColor(ecf.getWrapper());
                }
                else {
                    switch (code) {
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
                        case 'r':
                            style.setColor(EnumChatFormatting.RESET.getWrapper());
                            break;
                    }
                }

                if (workingMsg.length() > 1) { // Only reset things if there was something beyond the style
                    eventFormat(toSend, working, workingMsg, true);
                }
            }
            else if (workingMsg.length() > 1) {
                eventFormat(toSend, working, workingMsg, false);
            }
            else {
                toSend.appendSibling(working.setText(workingMsg));
            }
            working = new ChatComponentText("").getWrapper();
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

    private void eventFormat(CanaryChatComponent toSend, CanaryChatComponent working, String workingMsg, boolean styleSet) {
        List<String> playerNames = Arrays.asList(Canary.getServer().getPlayerNameList()); // store this so we aren't converting every check.
        String[] subWorking = styleSet ? workingMsg.substring(1).split(" ") : workingMsg.split(" "); // Find the URL
        boolean addSpaces = workingMsg.contains(" "); // if its only one string then there probably was no spaces
        StringBuilder subRebuild = new StringBuilder(); // Rebuild everything before and after the URL
        net.canarymod.api.chat.ChatStyle workingStyle = working.getChatStyle().clone();
        for (String sub : subWorking) {
            /* check for urls */
            if (urlPattern.matcher(sub).matches()) {
                net.canarymod.api.chat.ChatStyle urlStyle = new net.minecraft.util.ChatStyle().getWrapper();
                toSend.appendSibling(working.setText(subRebuild.toString())); // append everything before the URL
                working = new ChatComponentText("").getWrapper(); // reset working

                net.minecraft.event.ClickEvent clickEvent = new net.minecraft.event.ClickEvent(net.minecraft.event.ClickEvent.Action.OPEN_URL, sub); // Create the ClickEvent Action for the URL
                if (styleSet) {
                    urlStyle = workingStyle.clone(); // Need to pull the formatting in as well
                }
                urlStyle.setChatClickEvent(clickEvent.getWrapper()); // Append the action
                toSend.appendSibling(working.setChatStyle(urlStyle).setText(sub.concat(addSpaces ? " " : ""))); // Append URL

                working = new ChatComponentText("").getWrapper(); // reset working again
                working.setChatStyle(workingStyle.clone()); // reset working style

                subRebuild.delete(0, subRebuild.length()); // Reset the subRebuilder to handle things after the URL
            }
            /* check for playername */
            else if (sub.matches("([a-zA-Z0-9_]){1,20}$") && playerNames.contains(sub)) {
                net.canarymod.api.chat.ChatStyle urlStyle = new net.minecraft.util.ChatStyle().getWrapper();
                toSend.appendSibling(working.setText(subRebuild.toString())); // append everything before the URL
                working = new ChatComponentText("").getWrapper(); // reset working

                net.minecraft.event.ClickEvent clickEvent = new net.minecraft.event.ClickEvent(net.minecraft.event.ClickEvent.Action.SUGGEST_COMMAND, "/msg " + sub); // Create the ClickEvent Action for the URL
                if (styleSet) {
                    urlStyle = workingStyle.clone(); // Need to pull the formatting in as well
                }
                urlStyle.setChatClickEvent(clickEvent.getWrapper()); // Append the action
                toSend.appendSibling(working.setChatStyle(urlStyle).setText(sub.concat(addSpaces ? " " : ""))); // Append URL

                working = new ChatComponentText("").getWrapper(); // reset working again
                working.setChatStyle(workingStyle.clone()); // reset working style

                subRebuild.delete(0, subRebuild.length()); // Reset the subRebuilder to handle things after the URL
            }
            else {
                subRebuild.append(sub);
                if (addSpaces) {
                    subRebuild.append(" "); // Append that there space
                }
            }
        }
        if (subRebuild.length() > 0) {
            toSend.appendSibling(working.setText(subRebuild.toString())); // Append whats left over
        }
    }

    @Override
    public String decompileChatComponent(ChatComponent chatComponent) {
        StringBuilder stringBuilder = new StringBuilder();

        ChatStyle chatStyle = chatComponent.getChatStyle();
        if(chatStyle.isBold()){
            stringBuilder.append("\u00A7").append(getFormattingByName("bold").getFormattingCode());
        }
        if(chatStyle.isItalic()){
            stringBuilder.append("\u00A7").append(getFormattingByName("italic").getFormattingCode());
        }
        if(chatStyle.isStrikethrough()){
            stringBuilder.append("\u00A7").append(getFormattingByName("strikethrough").getFormattingCode());
        }
        if(chatStyle.isUnderlined()){
            stringBuilder.append("\u00A7").append(getFormattingByName("underlined").getFormattingCode());
        }
        if(chatStyle.isObfuscated()){
            stringBuilder.append("\u00A7").append(getFormattingByName("obfuscated").getFormattingCode());
        }
        if(chatStyle.getColor() != null){
            stringBuilder.append("\u00A7").append(chatStyle.getColor().getFormattingCode());
        }
        stringBuilder.append(chatComponent.getText());
        stringBuilder.append("\u00A7R");

        iterateChatComponentSiblings(chatComponent, stringBuilder);

        return stringBuilder.toString();
    }

    private void iterateChatComponentSiblings(ChatComponent chatComponent, StringBuilder builder){
        for(ChatComponent sibling : chatComponent.getSiblings()){
            ChatStyle chatStyle = chatComponent.getChatStyle();
            if(chatStyle.isBold()){
                builder.append("\u00A7").append(getFormattingByName("bold").getFormattingCode());
            }
            if(chatStyle.isItalic()){
                builder.append("\u00A7").append(getFormattingByName("italic").getFormattingCode());
            }
            if(chatStyle.isStrikethrough()){
                builder.append("\u00A7").append(getFormattingByName("strikethrough").getFormattingCode());
            }
            if(chatStyle.isUnderlined()){
                builder.append("\u00A7").append(getFormattingByName("underlined").getFormattingCode());
            }
            if(chatStyle.isObfuscated()){
                builder.append("\u00A7").append(getFormattingByName("obfuscated").getFormattingCode());
            }
            if(chatStyle.getColor() != null){
                builder.append("\u00A7").append(chatStyle.getColor().getFormattingCode());
            }
            builder.append(chatComponent.getText());
            builder.append("\u00A7R");
            iterateChatComponentSiblings(sibling, builder);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatComponent deserialize(String json) {
        return IChatComponent.Serializer.a(json).getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting getFormattingByName(String name) {
        return EnumChatFormatting.b(name).getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorBlack() {
        return getFormattingByName("black");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorDarkBlue() {
        return getFormattingByName("dark_blue");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorDarkGreen() {
        return getFormattingByName("dark_green");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorDarkAqua() {
        return getFormattingByName("dark_aqua");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorDarkRed() {
        return getFormattingByName("dark_red");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorDarkPurple() {
        return getFormattingByName("dark_purple");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorGold() {
        return getFormattingByName("gold");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorGray() {
        return getFormattingByName("gray");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorDarkGray() {
        return getFormattingByName("dark_gray");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorBlue() {
        return getFormattingByName("blue");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorGreen() {
        return getFormattingByName("green");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorAqua() {
        return getFormattingByName("aqua");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorRed() {
        return getFormattingByName("red");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorLightPurple() {
        return getFormattingByName("light_purple");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorYellow() {
        return getFormattingByName("yellow");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting colorWhite() {
        return getFormattingByName("white");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting styleObfuscated() {
        return getFormattingByName("obfuscated");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting styleBold() {
        return getFormattingByName("bold");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting styleStrikethrough() {
        return getFormattingByName("strikethrough");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting styleUnderline() {
        return getFormattingByName("underline");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting styleItalic() {
        return getFormattingByName("italic");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting styleReset() {
        return getFormattingByName("reset");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClickEvent newClickEvent(ClickEventAction clickEventAction, String value) {
        return new net.minecraft.event.ClickEvent(((CanaryClickEventAction) clickEventAction).getNative(), value).getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClickEventAction getClickEventActionByName(String name) {
        return net.minecraft.event.ClickEvent.Action.a(name).getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClickEventAction getOpenURL() {
        return getClickEventActionByName("open_url");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClickEventAction getOpenFile() {
        return getClickEventActionByName("open_file");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClickEventAction getRunCommand() {
        return getClickEventActionByName("run_command");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClickEventAction getSuggestCommand() {
        return getClickEventActionByName("suggest_command");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HoverEvent newHoverEvent(HoverEventAction hoverEventAction, ChatComponent value) {
        return new net.minecraft.event.HoverEvent(((CanaryHoverEventAction) hoverEventAction).getNative(), ((CanaryChatComponent) value).getNative()).getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HoverEventAction getHoverEventActionByName(String name) {
        return net.minecraft.event.HoverEvent.Action.a(name).getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HoverEventAction getShowText() {
        return getHoverEventActionByName("show_text");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HoverEventAction getShowAchievement() {
        return getHoverEventActionByName("show_achievement");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HoverEventAction getShowItem() {
        return getHoverEventActionByName("show_item");
    }
}
