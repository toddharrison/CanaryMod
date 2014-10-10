package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIOpenDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;


public class EntityAIOpenDoor extends EntityAIDoorInteract {

    boolean g;
    int h;
   
    public EntityAIOpenDoor(EntityLiving entityliving, boolean flag0) {
        super(entityliving);
        this.a = entityliving;
        this.g = flag0;
        this.canaryAI = new CanaryAIOpenDoor(this); //CanaryMod: set our variable
    }

    public boolean b() {
        return this.g && this.h > 0 && super.b();
    }

    public void c() {
        this.h = 20;
        this.c.a(this.a.o, this.b, true);
    }

    public void d() {
        if (this.g) {
            this.c.a(this.a.o, this.b, false);
        }

    }

    public void e() {
        --this.h;
        super.e();
    }
}
