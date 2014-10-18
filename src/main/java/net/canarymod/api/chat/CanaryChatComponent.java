package net.canarymod.api.chat;

import com.google.common.collect.Lists;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper implementation for IChatComponent
 *
 * @author Jason (darkdiplomat)
 */
public final class CanaryChatComponent implements ChatComponent {
    private final IChatComponent iChatComponent;

    public CanaryChatComponent(IChatComponent iChatComponent) {
        this.iChatComponent = iChatComponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatComponent setChatStyle(ChatStyle chatStyle) {
        getNative().a(((CanaryChatStyle) chatStyle).getNative());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatStyle getChatStyle() {
        return getNative().b().getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatComponent setText(String text){
        if(getNative() instanceof ChatComponentText) {
            ((ChatComponentText)getNative()).setText(text);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatComponent appendText(String text) {
        return getNative().a(text).getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatComponent appendSibling(ChatComponent chatComponent) {
        return getNative().a(((CanaryChatComponent) chatComponent).getNative()).getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return getNative().e();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFullText() {
        return getNative().c();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ChatComponent> getSiblings() {
        ArrayList<ChatComponent> components = Lists.newArrayList();
        for (IChatComponent iChatComponent1 : (List<IChatComponent>) getNative().a()) {
            components.add(iChatComponent1.getWrapper());
        }
        return components;
    }

    @Override
    public String serialize() {
        return IChatComponent.Serializer.a(getNative());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CanaryChatComponent clone() {
        return (CanaryChatComponent) getNative().f().getWrapper();
    }

    public IChatComponent getNative() {
        return iChatComponent;
    }
}
