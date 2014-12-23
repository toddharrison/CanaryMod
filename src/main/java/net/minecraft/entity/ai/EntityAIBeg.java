package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIBeg;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class EntityAIBeg extends EntityAIBase {

    private EntityWolf a;
    private EntityPlayer b;
    private World c;
    private float d;
    private int e;
   
    public EntityAIBeg(EntityWolf entitywolf, float f0) {
        this.a = entitywolf;
        this.c = entitywolf.o;
        this.d = f0;
        this.a(2);
        this.canaryAI = new CanaryAIBeg(this); //CanaryMod: set our variable
    }

    public boolean a() {
        this.b = this.c.a(this.a, (double) this.d);
        return this.b == null ? false : this.a(this.b);
    }

    public boolean b() {
        return !this.b.ai() ? false : (this.a.h(this.b) > (double) (this.d * this.d) ? false : this.e > 0 && this.a(this.b));
    }

    public void c() {
        this.a.p(true);
        this.e = 40 + this.a.bb().nextInt(40);
    }

    public void d() {
        this.a.p(false);
        this.b = null;
    }

    public void e() {
        this.a.p().a(this.b.s, this.b.t + (double) this.b.aR(), this.b.u, 10.0F, (float) this.a.bP());
        --this.e;
    }

    private boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        return itemstack == null ? false : (!this.a.cj() && itemstack.b() == Items.aX ? true : this.a.d(itemstack));
    }
}
