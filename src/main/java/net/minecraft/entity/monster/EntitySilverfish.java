package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanarySilverfish;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class EntitySilverfish extends EntityMob {

    public int bp; // CanaryMod: private => public; allySummonCooldown

    public EntitySilverfish(World world) {
        super(world);
        this.a(0.3F, 0.7F);
        this.entity = new CanarySilverfish(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(8.0D);
        this.a(SharedMonsterAttributes.d).a(0.6000000238418579D);
        this.a(SharedMonsterAttributes.e).a(1.0D);
    }

    protected boolean g_() {
        return false;
    }

    protected Entity bP() {
        double d0 = 8.0D;

        return this.p.b(this, d0);
    }

    protected String t() {
        return "mob.silverfish.say";
    }

    protected String aT() {
        return "mob.silverfish.hit";
    }

    protected String aU() {
        return "mob.silverfish.kill";
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        }
        else {
            if (this.bp <= 0 && (damagesource instanceof EntityDamageSource || damagesource == DamageSource.k)) {
                this.bp = 20;
            }

            return super.a(damagesource, f0);
        }
    }

    protected void a(Entity entity, float f0) {
        if (this.aC <= 0 && f0 < 1.2F && entity.D.e > this.D.b && entity.D.b < this.D.e) {
            this.aC = 20;
            this.m(entity);
        }
    }

    protected void a(int i0, int i1, int i2, Block block) {
        this.a("mob.silverfish.step", 0.15F, 1.0F);
    }

    protected Item u() {
        return Item.d(0);
    }

    public void h() {
        this.aN = this.z;
        super.h();
    }

    protected void bq() {
        super.bq();
        if (!this.p.E) {
            int i0;
            int i1;
            int i2;
            int i3;

            if (this.bp > 0) {
                --this.bp;
                if (this.bp == 0) {
                    i0 = MathHelper.c(this.t);
                    i1 = MathHelper.c(this.u);
                    i2 = MathHelper.c(this.v);
                    boolean flag0 = false;

                    for (int i4 = 0; !flag0 && i4 <= 5 && i4 >= -5; i4 = i4 <= 0 ? 1 - i4 : 0 - i4) {
                        for (i3 = 0; !flag0 && i3 <= 10 && i3 >= -10; i3 = i3 <= 0 ? 1 - i3 : 0 - i3) {
                            for (int i5 = 0; !flag0 && i5 <= 10 && i5 >= -10; i5 = i5 <= 0 ? 1 - i5 : 0 - i5) {
                                if (this.p.a(i0 + i3, i1 + i4, i2 + i5) == Blocks.aU) {
                                    if (!this.p.N().b("mobGriefing")) {
                                        int i6 = this.p.e(i0 + i3, i1 + i4, i2 + i5);
                                        ImmutablePair immutablepair = BlockSilverfish.b(i6);

                                        this.p.d(i0 + i3, i1 + i4, i2 + i5, (Block) immutablepair.getLeft(), ((Integer) immutablepair.getRight()).intValue(), 3);
                                    }
                                    else {
                                        this.p.a(i0 + i3, i1 + i4, i2 + i5, false);
                                    }

                                    Blocks.aU.b(this.p, i0 + i3, i1 + i4, i2 + i5, 0);
                                    if (this.aa.nextBoolean()) {
                                        flag0 = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (this.j == null && !this.bQ()) {
                i0 = MathHelper.c(this.t);
                i1 = MathHelper.c(this.u + 0.5D);
                i2 = MathHelper.c(this.v);
                int i7 = this.aa.nextInt(6);
                Block block = this.p.a(i0 + Facing.b[i7], i1 + Facing.c[i7], i2 + Facing.d[i7]);

                i3 = this.p.e(i0 + Facing.b[i7], i1 + Facing.c[i7], i2 + Facing.d[i7]);
                if (BlockSilverfish.a(block)) {
                    this.p.d(i0 + Facing.b[i7], i1 + Facing.c[i7], i2 + Facing.d[i7], Blocks.aU, BlockSilverfish.a(block, i3), 3);
                    this.s();
                    this.B();
                }
                else {
                    this.bO();
                }
            }
            else if (this.j != null && !this.bQ()) {
                this.j = null;
            }
        }
    }

    public float a(int i0, int i1, int i2) {
        return this.p.a(i0, i1 - 1, i2) == Blocks.b ? 10.0F : super.a(i0, i1, i2);
    }

    protected boolean j_() {
        return true;
    }

    public boolean bw() {
        if (super.bw()) {
            EntityPlayer entityplayer = this.p.a(this, 5.0D);

            return entityplayer == null;
        }
        else {
            return false;
        }
    }

    public EnumCreatureAttribute bd() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
}
