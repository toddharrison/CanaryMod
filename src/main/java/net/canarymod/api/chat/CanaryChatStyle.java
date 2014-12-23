package net.canarymod.api.chat;

/**
 * Wrapper implementation for Minecraft native ChatStyle
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryChatStyle implements ChatStyle {
    private final net.minecraft.util.ChatStyle nmsChatStyle;

    public CanaryChatStyle(net.minecraft.util.ChatStyle nmsChatStyle) {
        this.nmsChatStyle = nmsChatStyle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatFormatting getColor() {
        return getNative().a() != null ? getNative().a().getWrapper() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBold() {
        return getNative().b();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isItalic() {
        return getNative().c();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStrikethrough() {
        return getNative().d();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUnderlined() {
        return getNative().e();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isObfuscated() {
        return getNative().f();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return getNative().g();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClickEvent getChatClickEvent() {
        return getNative().h() != null ? getNative().h().getWrapper() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HoverEvent getChatHoverEvent() {
        return getNative().i() != null ? getNative().i().getWrapper() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle setColor(ChatFormatting color) {
        if(color == null)
            getNative().a((net.minecraft.util.EnumChatFormatting) null);
        else
            getNative().a(((CanaryChatFormatting) color).getNative());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle setBold(boolean bold) {
        getNative().a(bold);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle setItalic(boolean italic) {
        getNative().b(italic);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle setStrikethrough(boolean strikethrough) {
        getNative().c(strikethrough);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle setUnderlined(boolean underlined) {
        getNative().d(underlined);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle setObfuscated(boolean obfuscated) {
        getNative().e(obfuscated);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle setChatClickEvent(ClickEvent clickEvent) {
        if(clickEvent == null)
            getNative().a((net.minecraft.event.ClickEvent) null);
        else
            getNative().a(((CanaryClickEvent) clickEvent).getNative());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle setChatHoverEvent(HoverEvent hoverEvent) {
        if(hoverEvent == null)
            getNative().a((net.minecraft.event.HoverEvent) null);
        else
            getNative().a(((CanaryHoverEvent) hoverEvent).getNative());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle setParentStyle(ChatStyle chatStyle) {
        if(chatStyle == null)
            getNative().a((net.minecraft.util.ChatStyle) null);
        else
            getNative().a(((CanaryChatStyle) chatStyle).getNative());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle getParentStyle() {
        return getNative().n() != null ? getNative().n().getWrapper() : null;
    }

    @Override
    public final ChatStyle clone() {
        return getNative().m().getWrapper();
    }

    public net.minecraft.util.ChatStyle getNative() {
        return nmsChatStyle;
    }
}
