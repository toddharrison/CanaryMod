package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryMagmaCube;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;


public class EntityMagmaCube extends EntitySlime {

    public EntityMagmaCube(World world) {
        super(world);
        this.ab = true;
        this.entity = new CanaryMagmaCube(this); // CanaryMod: Wrap Entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.d).a(0.20000000298023224D);
    }

    public boolean bQ() {
        return this.o.aa() != EnumDifficulty.PEACEFUL;
    }

    public boolean bR() {
        return this.o.a(this.aQ(), (Entity) this) && this.o.a((Entity) this, this.aQ()).isEmpty() && !this.o.d(this.aQ());
    }

    public int bq() {
        return this.ck() * 3;
    }

    public float c(float f0) {
        return 1.0F;
    }

    protected EnumParticleTypes n() {
        return EnumParticleTypes.FLAME;
    }

    protected EntitySlime cd() {
        return new EntityMagmaCube(this.o);
    }

    protected Item A() {
        return Items.bE;
    }

    protected void b(boolean flag0, int i0) {
        Item item = this.A();

        if (item != null && this.ck() > 1) {
            int i1 = this.V.nextInt(4) - 2;

            if (i0 > 0) {
                i1 += this.V.nextInt(i0 + 1);
            }

            for (int i2 = 0; i2 < i1; ++i2) {
                this.a(item, 1);
            }
        }

    }

    public boolean au() {
        return false;
    }

    protected int ce() {
        return super.ce() * 4;
    }

    protected void cf() {
        this.a *= 0.9F;
    }

    protected void bE() {
        this.w = (double) (0.42F + (float) this.ck() * 0.1F);
        this.ai = true;
    }

    protected void bG() {
        this.w = (double) (0.22F + (float) this.ck() * 0.05F);
        this.ai = true;
    }

    public void e(float f0, float f1) {}

    protected boolean cg() {
        return true;
    }

    protected int ch() {
        return super.ch() + 2;
    }

    protected String ci() {
        return this.ck() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
    }

    protected boolean cj() {
        return true;
    }
}
