package net.canarymod.api.chat;

import net.minecraft.event.ClickEvent;

/**
 * Wrapper implementation for Minecraft native ClickEvent.Action
 *
 * @author Jason (darkdiplomat)
 */
public final class CanaryClickEventAction implements ClickEventAction {
    private final ClickEvent.Action nmsCEA;

    public CanaryClickEventAction(ClickEvent.Action nmsCEA) {
        this.nmsCEA = nmsCEA;
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

    public final ClickEvent.Action getNative() {
        return nmsCEA;
    }
}
