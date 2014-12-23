package net.minecraft.entity.ai;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.canarymod.api.ai.CanaryAIEatGrass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class EntityAIEatGrass extends EntityAIBase {

    private static final Predicate b = BlockStateHelper.a((Block) Blocks.H).a(BlockTallGrass.a, Predicates.equalTo(BlockTallGrass.EnumType.GRASS));
    private EntityLiving c;
    private World d;
    int a;
   
    public EntityAIEatGrass(EntityLiving entityliving) {
        this.c = entityliving;
        this.d = entityliving.o;
        this.a(7);
        this.canaryAI = new CanaryAIEatGrass(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.c.bb().nextInt(this.c.i_() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = new BlockPos(this.c.s, this.c.t, this.c.u);

            return b.apply(this.d.p(blockpos)) ? true : this.d.p(blockpos.b()).c() == Blocks.c;
        }
    }

    public void c() {
        this.a = 40;
        this.d.a((Entity) this.c, (byte) 10);
        this.c.s().n();
    }

    public void d() {
        this.a = 0;
    }

    public boolean b() {
        return this.a > 0;
    }

    public int f() {
        return this.a;
    }

    public void e() {
        this.a = Math.max(0, this.a - 1);
        if (this.a == 4) {
            BlockPos blockpos = new BlockPos(this.c.s, this.c.t, this.c.u);

            if (b.apply(this.d.p(blockpos))) {
                if (this.d.Q().b("mobGriefing")) {
                    this.d.b(blockpos, false);
                }

                this.c.v();
            } else {
                BlockPos blockpos1 = blockpos.b();

                if (this.d.p(blockpos1).c() == Blocks.c) {
                    if (this.d.Q().b("mobGriefing")) {
                        this.d.b(2001, blockpos1, Block.a((Block) Blocks.c));
                        this.d.a(blockpos1, Blocks.d.P(), 2);
                    }

                    this.c.v();
                }
            }

        }
    }

}
