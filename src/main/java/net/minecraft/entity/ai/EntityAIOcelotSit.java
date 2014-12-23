package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIOcelotSit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class EntityAIOcelotSit extends EntityAIMoveToBlock {

    private final EntityOcelot c;
   
    public EntityAIOcelotSit(EntityOcelot entityocelot, double d0) {
        super(entityocelot, d0, 8);
        this.c = entityocelot;
        this.canaryAI = new CanaryAIOcelotSit(this); //CanaryMod: set our variable
    }

    public boolean a() {
        return this.c.cj() && !this.c.cl() && super.a();
    }

    public boolean b() {
        return super.b();
    }

    public void c() {
        super.c();
        this.c.cn().a(false);
    }

    public void d() {
        super.d();
        this.c.n(false);
    }

    public void e() {
        super.e();
        this.c.cn().a(false);
        if (!this.f()) {
            this.c.n(false);
        } else if (!this.c.cl()) {
            this.c.n(true);
        }

    }

    protected boolean a(World world, BlockPos blockpos) {
        if (!world.d(blockpos.a())) {
            return false;
        } else {
            IBlockState iblockstate = world.p(blockpos);
            Block block = iblockstate.c();

            if (block == Blocks.ae) {
                TileEntity tileentity = world.s(blockpos);

                if (tileentity instanceof TileEntityChest && ((TileEntityChest) tileentity).l < 1) {
                    return true;
                }
            } else {
                if (block == Blocks.am) {
                    return true;
                }

                if (block == Blocks.C && iblockstate.b(BlockBed.a) != BlockBed.EnumPartType.HEAD) {
                    return true;
                }
            }

            return false;
        }
    }
}
