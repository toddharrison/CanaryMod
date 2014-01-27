package net.canarymod.api.chat;

/**
 * Wrapper implementation for Minecraft native HoverEvent
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryHoverEvent implements HoverEvent {
    private final net.minecraft.event.HoverEvent nmsHoverEvent;

    public CanaryHoverEvent(net.minecraft.event.HoverEvent nmsHoverEvent) {
        this.nmsHoverEvent = nmsHoverEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HoverEventAction getAction() {
        return getNative().a().getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatComponent getValue() {
        return getNative().b().getWrapper();
    }

    public final net.minecraft.event.HoverEvent getNative() {
        return nmsHoverEvent;
    }
}
