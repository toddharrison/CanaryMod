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
        this.af = true;
        this.entity = new CanaryMagmaCube(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.d).a(0.20000000298023224D);
    }

    public boolean bw() {
        return this.p.r != EnumDifficulty.PEACEFUL && this.p.b(this.D) && this.p.a((Entity) this, this.D).isEmpty() && !this.p.d(this.D);
    }

    public int aV() {
        return this.bV() * 3;
    }

    public float d(float f0) {
        return 1.0F;
    }

    protected String bN() {
        return "flame";
    }

    protected EntitySlime bO() {
        return new EntityMagmaCube(this.p);
    }

    protected Item u() {
        return Items.bs;
    }

    protected void b(boolean flag0, int i0) {
        Item item = this.u();

        if (item != null && this.bV() > 1) {
            int i1 = this.aa.nextInt(4) - 2;

            if (i0 > 0) {
                i1 += this.aa.nextInt(i0 + 1);
            }

            for (int i2 = 0; i2 < i1; ++i2) {
                this.a(item, 1);
            }
        }
    }

    public boolean al() {
        return false;
    }

    protected int bP() {
        return super.bP() * 4;
    }

    protected void bQ() {
        this.h *= 0.9F;
    }

    protected void bj() {
        this.x = (double) (0.42F + (float) this.bV() * 0.1F);
        this.am = true;
    }

    protected void b(float f0) {
    }

    protected boolean bR() {
        return true;
    }

    protected int bS() {
        return super.bS() + 2;
    }

    protected String bT() {
        return this.bV() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
    }

    public boolean P() {
        return false;
    }

    protected boolean bU() {
        return true;
    }
}
