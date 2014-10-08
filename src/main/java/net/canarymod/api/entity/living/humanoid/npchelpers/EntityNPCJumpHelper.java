package net.canarymod.api.entity.living.humanoid.npchelpers;

import net.canarymod.api.entity.living.humanoid.EntityNonPlayableCharacter;

public class EntityNPCJumpHelper {

    private EntityNonPlayableCharacter b;
    private boolean a;

    public EntityNPCJumpHelper(EntityNonPlayableCharacter enpc) {
        this.b = enpc;
    }

    public void a() {
        this.a = true;
    }

    public void b() {
        this.b.i(this.a);
        this.a = false;
    }
}
