package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIBreakDoor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;

public class EntityAIBreakDoor extends EntityAIDoorInteract {

    private int g;
    private int h = -1;
   
    public EntityAIBreakDoor(EntityLiving entityliving) {
        super(entityliving);
        this.canaryAI = new CanaryAIBreakDoor(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (!super.a()) {
            return false;
        } else if (!this.a.o.Q().b("mobGriefing")) {
            return false;
        } else {
            BlockDoor blockdoor = this.c;

            return !BlockDoor.f((IBlockAccess)this.a.o, this.b);
        }
    }

    public void c() {
        super.c();
        this.g = 0;
    }

    public boolean b() {
        double d0 = this.a.b(this.b);
        boolean flag0;

        if (this.g <= 240) {
            BlockDoor blockdoor = this.c;

            if (!BlockDoor.f((IBlockAccess)this.a.o, this.b) && d0 < 4.0D) {
                flag0 = true;
                return flag0;
            }
        }

        flag0 = false;
        return flag0;
    }

    public void d() {
        super.d();
        this.a.o.c(this.a.F(), this.b, -1);
    }

    public void e() {
        super.e();
        if (this.a.bb().nextInt(20) == 0) {
            this.a.o.b(1010, this.b, 0);
        }

        ++this.g;
        int i0 = (int) ((float) this.g / 240.0F * 10.0F);

        if (i0 != this.h) {
            this.a.o.c(this.a.F(), this.b, i0);
            this.h = i0;
        }

        if (this.g == 240 && this.a.o.aa() == EnumDifficulty.HARD) {
            this.a.o.g(this.b);
            this.a.o.b(1012, this.b, 0);
            this.a.o.b(2001, this.b, Block.a((Block) this.c));
        }

    }
}
