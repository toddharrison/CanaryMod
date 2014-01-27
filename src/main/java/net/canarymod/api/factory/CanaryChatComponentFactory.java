package net.canarymod.api.factory;

import net.canarymod.api.chat.CanaryChatComponent;
import net.canarymod.api.chat.CanaryClickEventAction;
import net.canarymod.api.chat.CanaryHoverEventAction;
import net.canarymod.api.chat.ChatComponent;
import net.canarymod.api.chat.ChatFormatting;
import net.canarymod.api.chat.ClickEvent;
import net.canarymod.api.chat.ClickEventAction;
import net.canarymod.api.chat.HoverEvent;
import net.canarymod.api.chat.HoverEventAction;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * Implementation of ChatComponentFactory
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryChatComponentFactory implements ChatComponentFactory {

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
