package net.minecraft.entity.player;

import net.canarymod.api.entity.living.humanoid.CanaryHumanCapabilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerCapabilities {

    public boolean a;
    public boolean b;
    public boolean c;
    public boolean d;
    public boolean e = true;
    public float f = 0.05F; // CanaryMod: private => public
    public float g = 0.1F; // CanaryMod: private => public
    private CanaryHumanCapabilities chc; // CanaryMod: wrapper instance

    public PlayerCapabilities() {
        this.chc = new CanaryHumanCapabilities(this); // CanaryMod: wrap instance
    }

    public void a(NBTTagCompound nbttagcompound) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();

        nbttagcompound1.a("invulnerable", this.a);
        nbttagcompound1.a("flying", this.b);
        nbttagcompound1.a("mayfly", this.c);
        nbttagcompound1.a("instabuild", this.d);
        nbttagcompound1.a("mayBuild", this.e);
        nbttagcompound1.a("flySpeed", this.f);
        nbttagcompound1.a("walkSpeed", this.g);
        nbttagcompound.a("abilities", (NBTBase) nbttagcompound1);
    }

    public void b(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.b("abilities", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.m("abilities");

            this.a = nbttagcompound1.n("invulnerable");
            this.b = nbttagcompound1.n("flying");
            this.c = nbttagcompound1.n("mayfly");
            this.d = nbttagcompound1.n("instabuild");
            if (nbttagcompound1.b("flySpeed", 99)) {
                this.f = nbttagcompound1.h("flySpeed");
                this.g = nbttagcompound1.h("walkSpeed");
            }

            if (nbttagcompound1.b("mayBuild", 1)) {
                this.e = nbttagcompound1.n("mayBuild");
            }
        }

    }

    public float a() {
        return this.f;
    }

    public float b() {
        return this.g;
    }

    public CanaryHumanCapabilities getCanaryCapabilities() {
        return chc;
    }
}
