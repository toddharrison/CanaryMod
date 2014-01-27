package net.canarymod.api.chat;

/**
 * Wrapper implementation for Minecraft native ClickEvent
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryClickEvent implements ClickEvent {
    private final net.minecraft.event.ClickEvent nmsClickEvent;

    public CanaryClickEvent(net.minecraft.event.ClickEvent nmsClickEvent) {
        this.nmsClickEvent = nmsClickEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClickEventAction getAction() {
        return getNative().a().getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return getNative().b();
    }

    public final net.minecraft.event.ClickEvent getNative() {
        return nmsClickEvent;
    }
}
