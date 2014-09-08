package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryMagmaCube;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaCube extends EntitySlime {

    public EntityMagmaCube(World world) {
        super(world);
        this.ae = true;
        this.entity = new CanaryMagmaCube(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.d).a(0.20000000298023224D);
    }

    public boolean by() {
        return this.o.r != EnumDifficulty.PEACEFUL && this.o.b(this.C) && this.o.a((Entity) this, this.C).isEmpty() && !this.o.d(this.C);
    }

    public int aV() {
        return this.bX() * 3;
    }

    public float d(float f0) {
        return 1.0F;
    }

    protected String bP() {
        return "flame";
    }

    protected EntitySlime bQ() {
        return new EntityMagmaCube(this.o);
    }

    protected Item u() {
        return Items.bs;
    }

    protected void b(boolean flag0, int i0) {
        Item item = this.u();

        if (item != null && this.bX() > 1) {
            int i1 = this.Z.nextInt(4) - 2;

            if (i0 > 0) {
                i1 += this.Z.nextInt(i0 + 1);
            }

            for (int i2 = 0; i2 < i1; ++i2) {
                this.a(item, 1);
            }
        }
    }

    public boolean al() {
        return false;
    }

    protected int bR() {
        return super.bR() * 4;
    }

    protected void bS() {
        this.h *= 0.9F;
    }

    protected void bj() {
        this.w = (double) (0.42F + (float) this.bX() * 0.1F);
        this.al = true;
    }

    protected void b(float f0) {
    }

    protected boolean bT() {
        return true;
    }

    protected int bU() {
        return super.bU() + 2;
    }

    protected String bV() {
        return this.bX() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
    }

    public boolean P() {
        return false;
    }

    protected boolean bW() {
        return true;
    }
}
