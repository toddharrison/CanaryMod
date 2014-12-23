package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAITradePlayer;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;


public class EntityAITradePlayer extends EntityAIBase {

    private EntityVillager a;
   
    public EntityAITradePlayer(EntityVillager entityvillager) {
        this.a = entityvillager;
        this.a(5);
        this.canaryAI = new CanaryAITradePlayer(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (!this.a.ai()) {
            return false;
        } else if (this.a.V()) {
            return false;
        } else if (!this.a.C) {
            return false;
        } else if (this.a.G) {
            return false;
        } else {
            EntityPlayer entityplayer = this.a.u_();

            return entityplayer == null ? false : (this.a.h(entityplayer) > 16.0D ? false : entityplayer.bi instanceof Container);
        }
    }

    public void c() {
        this.a.s().n();
    }

    public void d() {
        this.a.a_((EntityPlayer) null);
    }
}
