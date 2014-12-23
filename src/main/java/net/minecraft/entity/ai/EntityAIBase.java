package net.minecraft.entity.ai;

import net.canarymod.api.ai.CanaryAIBase;


public abstract class EntityAIBase {

    private int a;
    
    protected CanaryAIBase canaryAI;// CanaryMod: our variable
   
    public abstract boolean a();

    public boolean b() {
        return this.a();
    }

    public boolean i() {
        return true;
    }

    public void c() {}

    public void d() {}

    public void e() {}

    public void a(int i0) {
        this.a = i0;
    }

    public int j() {
        return this.a;
    }
    
    // CanaryMod: our methods
    public CanaryAIBase getCanaryAIBase() {
        return this.canaryAI;
    }
}
