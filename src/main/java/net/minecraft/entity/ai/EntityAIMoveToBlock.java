package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIMoveToBlock;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public abstract class EntityAIMoveToBlock extends EntityAIBase {

    private final EntityCreature c;
    private final double d;
    protected int a;
    private int e;
    private int f;
    protected BlockPos b;
    private boolean g;
    private int h;
   
    public EntityAIMoveToBlock(EntityCreature entitycreature, double d0, int i0) {
        this.b = BlockPos.a;
        this.c = entitycreature;
        this.d = d0;
        this.h = i0;
        this.a(5);
        this.canaryAI = new CanaryAIMoveToBlock(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.a > 0) {
            --this.a;
            return false;
        } else {
            this.a = 200 + this.c.bb().nextInt(200);
            return this.g();
        }
    }

    public boolean b() {
        return this.e >= -this.f && this.e <= 1200 && this.a(this.c.o, this.b);
    }

    public void c() {
        this.c.s().a((double) ((float) this.b.n()) + 0.5D, (double) (this.b.o() + 1), (double) ((float) this.b.p()) + 0.5D, this.d);
        this.e = 0;
        this.f = this.c.bb().nextInt(this.c.bb().nextInt(1200) + 1200) + 1200;
    }

    public void d() {}

    public void e() {
        if (this.c.c(this.b.a()) > 1.0D) {
            this.g = false;
            ++this.e;
            if (this.e % 40 == 0) {
                this.c.s().a((double) ((float) this.b.n()) + 0.5D, (double) (this.b.o() + 1), (double) ((float) this.b.p()) + 0.5D, this.d);
            }
        } else {
            this.g = true;
            --this.e;
        }

    }

    protected boolean f() {
        return this.g;
    }

    private boolean g() {
        int i0 = this.h;
        boolean flag0 = true;
        BlockPos blockpos = new BlockPos(this.c);

        for (int i1 = 0; i1 <= 1; i1 = i1 > 0 ? -i1 : 1 - i1) {
            for (int i2 = 0; i2 < i0; ++i2) {
                for (int i3 = 0; i3 <= i2; i3 = i3 > 0 ? -i3 : 1 - i3) {
                    for (int i4 = i3 < i2 && i3 > -i2 ? i2 : 0; i4 <= i2; i4 = i4 > 0 ? -i4 : 1 - i4) {
                        BlockPos blockpos1 = blockpos.a(i3, i1 - 1, i4);

                        if (this.c.d(blockpos1) && this.a(this.c.o, blockpos1)) {
                            this.b = blockpos1;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    protected abstract boolean a(World world, BlockPos blockpos);
}
