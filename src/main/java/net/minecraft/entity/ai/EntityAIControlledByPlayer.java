package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIControlledByPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.pathfinder.WalkNodeProcessor;


public class EntityAIControlledByPlayer extends EntityAIBase {

    private final EntityLiving a;
    private final float b;
    private float c;
    private boolean d;
    private int e;
    private int f;
   
    public EntityAIControlledByPlayer(EntityLiving entityliving, float f0) {
        this.a = entityliving;
        this.b = f0;
        this.a(7);
        this.canaryAI = new CanaryAIControlledByPlayer(this); //CanaryMod: set our variable
    }

    public void c() {
        this.c = 0.0F;
    }

    public void d() {
        this.d = false;
        this.c = 0.0F;
    }

    public boolean a() {
        return this.a.ai() && this.a.l != null && this.a.l instanceof EntityPlayer && (this.d || this.a.bV());
    }

    public void e() {
        EntityPlayer entityplayer = (EntityPlayer) this.a.l;
        EntityCreature entitycreature = (EntityCreature) this.a;
        float f0 = MathHelper.g(entityplayer.y - this.a.y) * 0.5F;

        if (f0 > 5.0F) {
            f0 = 5.0F;
        }

        if (f0 < -5.0F) {
            f0 = -5.0F;
        }

        this.a.y = MathHelper.g(this.a.y + f0);
        if (this.c < this.b) {
            this.c += (this.b - this.c) * 0.01F;
        }

        if (this.c > this.b) {
            this.c = this.b;
        }

        int i0 = MathHelper.c(this.a.s);
        int i1 = MathHelper.c(this.a.t);
        int i2 = MathHelper.c(this.a.u);
        float f1 = this.c;

        if (this.d) {
            if (this.e++ > this.f) {
                this.d = false;
            }

            f1 += f1 * 1.15F * MathHelper.a((float) this.e / (float) this.f * 3.1415927F);
        }

        float f2 = 0.91F;

        if (this.a.C) {
            f2 = this.a.o.p(new BlockPos(MathHelper.d((float) i0), MathHelper.d((float) i1) - 1, MathHelper.d((float) i2))).c().K * 0.91F;
        }

        float f3 = 0.16277136F / (f2 * f2 * f2);
        float f4 = MathHelper.a(entitycreature.y * 3.1415927F / 180.0F);
        float f5 = MathHelper.b(entitycreature.y * 3.1415927F / 180.0F);
        float f6 = entitycreature.bH() * f3;
        float f7 = Math.max(f1, 1.0F);

        f7 = f6 / f7;
        float f8 = f1 * f7;
        float f9 = -(f8 * f4);
        float f10 = f8 * f5;

        if (MathHelper.e(f9) > MathHelper.e(f10)) {
            if (f9 < 0.0F) {
                f9 -= this.a.J / 2.0F;
            }

            if (f9 > 0.0F) {
                f9 += this.a.J / 2.0F;
            }

            f10 = 0.0F;
        } else {
            f9 = 0.0F;
            if (f10 < 0.0F) {
                f10 -= this.a.J / 2.0F;
            }

            if (f10 > 0.0F) {
                f10 += this.a.J / 2.0F;
            }
        }

        int i3 = MathHelper.c(this.a.s + (double) f9);
        int i4 = MathHelper.c(this.a.u + (double) f10);
        int i5 = MathHelper.d(this.a.J + 1.0F);
        int i6 = MathHelper.d(this.a.K + entityplayer.K + 1.0F);
        int i7 = MathHelper.d(this.a.J + 1.0F);

        if (i0 != i3 || i2 != i4) {
            Block block = this.a.o.p(new BlockPos(i0, i1, i2)).c();
            boolean flag0 = !this.a(block) && (block.r() != Material.a || !this.a(this.a.o.p(new BlockPos(i0, i1 - 1, i2)).c()));

            if (flag0 && 0 == WalkNodeProcessor.a(this.a.o, this.a, i3, i1, i4, i5, i6, i7, false, false, true) && 1 == WalkNodeProcessor.a(this.a.o, this.a, i0, i1 + 1, i2, i5, i6, i7, false, false, true) && 1 == WalkNodeProcessor.a(this.a.o, this.a, i3, i1 + 1, i4, i5, i6, i7, false, false, true)) {
                entitycreature.r().a();
            }
        }

        if (!entityplayer.by.d && this.c >= this.b * 0.5F && this.a.bb().nextFloat() < 0.006F && !this.d) {
            ItemStack itemstack = entityplayer.bz();

            if (itemstack != null && itemstack.b() == Items.bY) {
                itemstack.a(1, (EntityLivingBase) entityplayer);
                if (itemstack.b == 0) {
                    ItemStack itemstack1 = new ItemStack(Items.aR);

                    itemstack1.d(itemstack.o());
                    entityplayer.bg.a[entityplayer.bg.c] = itemstack1;
                }
            }
        }

        this.a.g(0.0F, f1);
    }

    private boolean a(Block block) {
        return block instanceof BlockStairs || block instanceof BlockSlab;
    }

    public boolean f() {
        return this.d;
    }

    public void g() {
        this.d = true;
        this.e = 0;
        this.f = this.a.bb().nextInt(841) + 140;
    }

    public boolean h() {
        return !this.f() && this.c > this.b * 0.3F;
    }
}
