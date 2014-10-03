package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryGiantZombie;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class EntityGiantZombie extends EntityMob {

    public EntityGiantZombie(World world) {
        super(world);
        this.entity = new CanaryGiantZombie(this); // CanaryMod: Wrap Entity
    }

    public float aR() {
        return 10.440001F;
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(100.0D);
        this.a(SharedMonsterAttributes.d).a(0.5D);
        this.a(SharedMonsterAttributes.e).a(50.0D);
    }

    public float a(BlockPos blockpos) {
        return this.o.o(blockpos) - 0.5F;
    }
}
