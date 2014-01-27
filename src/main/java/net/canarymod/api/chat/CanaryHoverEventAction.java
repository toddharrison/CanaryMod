package net.canarymod.api.chat;

import net.minecraft.event.HoverEvent;

/**
 * Wrapper implementation for Minecraft native HoverEvent.Action
 *
 * @author Jason (darkdiplomat)
 */
public final class CanaryHoverEventAction implements HoverEventAction {
    private final HoverEvent.Action nmsHEA;

    public CanaryHoverEventAction(HoverEvent.Action nmsHEA) {
        this.nmsHEA = nmsHEA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean allowedInChat() {
        return getNative().a();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return getNative().b();
    }

    public final HoverEvent.Action getNative() {
        return nmsHEA;
    }
}
