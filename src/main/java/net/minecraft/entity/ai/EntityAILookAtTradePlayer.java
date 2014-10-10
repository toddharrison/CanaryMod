package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;


public class EntityAILookAtTradePlayer extends EntityAIWatchClosest {

    private final EntityVillager e;
   
    public EntityAILookAtTradePlayer(EntityVillager entityvillager) {
        super(entityvillager, EntityPlayer.class, 8.0F);
        this.e = entityvillager;
        this.canaryAI = new CanaryAILookAtTradePlayer(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.e.cm()) {
            this.b = this.e.u_();
            return true;
        } else {
            return false;
        }
    }
}
