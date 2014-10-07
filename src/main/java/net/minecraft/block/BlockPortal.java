package net.minecraft.block;

import java.util.Random;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.world.PortalCreateHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPortal extends BlockBreakable {

    public static final PropertyEnum a = PropertyEnum.a("axis", EnumFacing.Axis.class, (Enum[]) (new EnumFacing.Axis[]{EnumFacing.Axis.X, EnumFacing.Axis.Z}));

    public BlockPortal() {
        super(Material.E, false);
        this.j(this.L.b().a(a, EnumFacing.Axis.X));
        this.a(true);
    }

    public static int a(EnumFacing.Axis enumfacing_axis) {
        return enumfacing_axis == EnumFacing.Axis.X ? 1 : (enumfacing_axis == EnumFacing.Axis.Z ? 2 : 0);
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        super.b(world, blockpos, iblockstate, random);
        if (world.t.d() && world.Q().b("doMobSpawning") && random.nextInt(2000) < world.aa().a()) {
            int i0 = blockpos.o();

            BlockPos blockpos1;

            for (blockpos1 = blockpos; !World.a((IBlockAccess) world, blockpos1) && blockpos1.o() > 0; blockpos1 = blockpos1.b()) {
                ;
            }

            if (i0 > 0 && !world.p(blockpos1.a()).c().t()) {
                Entity entity = ItemMonsterPlacer.a(world, 57, (double) blockpos1.n() + 0.5D, (double) blockpos1.o() + 1.1D, (double) blockpos1.p() + 0.5D);

                if (entity != null) {
                    entity.aj = entity.ar();
                }
            }
        }

    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        return null;
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        EnumFacing.Axis enumfacing_axis = (EnumFacing.Axis) iblockaccess.p(blockpos).b(a);
        float f0 = 0.125F;
        float f1 = 0.125F;

        if (enumfacing_axis == EnumFacing.Axis.X) {
            f0 = 0.5F;
        }

        if (enumfacing_axis == EnumFacing.Axis.Z) {
            f1 = 0.5F;
        }

        this.a(0.5F - f0, 0.0F, 0.5F - f1, 0.5F + f0, 1.0F, 0.5F + f1);
    }

    public boolean d() {
        return false;
    }

    public boolean d(World world, BlockPos blockpos) {
        BlockPortal.Size blockportal_size = new BlockPortal.Size(world, blockpos, EnumFacing.Axis.X);

        // CanaryMod: PortalCreate
        PortalCreateHook hook;
        if (blockportal_size.b() && blockportal_size.e == 0) {
            hook = (PortalCreateHook) new PortalCreateHook(blockportal_size.getPortalBlocks()).call();
            if (!hook.isCanceled()) {
                blockportal_size.c();
                return true;
            }
        }
        else {
            BlockPortal.Size blockportal_size1 = new BlockPortal.Size(world, blockpos, EnumFacing.Axis.Z);
            if (blockportal_size1.b() && blockportal_size1.e == 0) {
                hook = (PortalCreateHook) new PortalCreateHook(blockportal_size1.getPortalBlocks()).call();
                if (!hook.isCanceled()) {
                    blockportal_size1.c();
                    return true;
                }
            }
        // else {
        //
        // }
        // CanaryMod: End
        }
        return false;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        EnumFacing.Axis enumfacing_axis = (EnumFacing.Axis) iblockstate.b(a);
        BlockPortal.Size blockportal_size;

        if (enumfacing_axis == EnumFacing.Axis.X) {
            blockportal_size = new BlockPortal.Size(world, blockpos, EnumFacing.Axis.X);
            if (!blockportal_size.b() || blockportal_size.e < blockportal_size.h * blockportal_size.g) {
                world.a(blockpos, Blocks.a.P());
            }
        }
        else if (enumfacing_axis == EnumFacing.Axis.Z) {
            blockportal_size = new BlockPortal.Size(world, blockpos, EnumFacing.Axis.Z);
            if (!blockportal_size.b() || blockportal_size.e < blockportal_size.h * blockportal_size.g) {
                world.a(blockpos, Blocks.a.P());
            }
        }

    }

    public int a(Random random) {
        return 0;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Entity entity) {
        if (entity.m == null && entity.l == null) {
            entity.aq();
        }

    }

    public IBlockState a(int i0) {
        return this.P().a(a, (i0 & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
    }

    public int c(IBlockState iblockstate) {
        return a((EnumFacing.Axis) iblockstate.b(a));
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{a});
    }

    public static class Size {
        //TODO: Restore Portal Reconstruct Job/onPortalCreate

        private final World a;
        private final EnumFacing.Axis b;
        private final EnumFacing c;
        private final EnumFacing d;
        private int e = 0;
        private BlockPos f;
        private int g;
        private int h;

        public Size(World p_i45694_1_, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_) {
            this.a = p_i45694_1_;
            this.b = p_i45694_3_;
            if (p_i45694_3_ == EnumFacing.Axis.X) {
                this.d = EnumFacing.EAST;
                this.c = EnumFacing.WEST;
            }
            else {
                this.d = EnumFacing.NORTH;
                this.c = EnumFacing.SOUTH;
            }

            for (BlockPos blockpos1 = p_i45694_2_; p_i45694_2_.o() > blockpos1.o() - 21 && p_i45694_2_.o() > 0 && this.a(p_i45694_1_.p(p_i45694_2_.b()).c()); p_i45694_2_ = p_i45694_2_.b()) {
                ;
            }

            int block = this.a(p_i45694_2_, this.d) - 1;

            if (block >= 0) {
                this.f = p_i45694_2_.a(this.d, block);
                this.h = this.a(this.f, this.c);
                if (this.h < 2 || this.h > 21) {
                    this.f = null;
                    this.h = 0;
                }
            }

            if (this.f != null) {
                this.g = this.a();
            }

        }

        protected int a(BlockPos p_a_1_, EnumFacing p_a_2_) {
            int i4;

            for (i4 = 0; i4 < 22; ++i4) {
                BlockPos blockpos1 = p_a_1_.a(p_a_2_, i4);

                if (!this.a(this.a.p(blockpos1).c()) || this.a.p(blockpos1.b()).c() != Blocks.Z) {
                    break;
                }
            }

            Block block = this.a.p(p_a_1_.a(p_a_2_, i4)).c();

            return block == Blocks.Z ? i4 : 0;
        }

        protected int a() {
            int i3;

            label56:
            for (this.g = 0; this.g < 21; ++this.g) {
                for (i3 = 0; i3 < this.h; ++i3) {
                    BlockPos blockpos3 = this.f.a(this.c, i3).b(this.g);
                    Block i4 = this.a.p(blockpos3).c();

                    if (!this.a(i4)) {
                        break label56;
                    }

                    if (i4 == Blocks.aY) {
                        ++this.e;
                    }

                    if (i3 == 0) {
                        i4 = this.a.p(blockpos3.a(this.d)).c();
                        if (i4 != Blocks.Z) {
                            break label56;
                        }
                    }
                    else if (i3 == this.h - 1) {
                        i4 = this.a.p(blockpos3.a(this.c)).c();
                        if (i4 != Blocks.Z) {
                            break label56;
                        }
                    }
                }
            }

            for (i3 = 0; i3 < this.h; ++i3) {
                if (this.a.p(this.f.a(this.c, i3).b(this.g)).c() != Blocks.Z) {
                    this.g = 0;
                    break;
                }
            }

            if (this.g <= 21 && this.g >= 3) {
                return this.g;
            }
            else {
                this.f = null;
                this.h = 0;
                this.g = 0;
                return 0;
            }
        }

        protected boolean a(Block p_a_1_) {
            return p_a_1_.J == Material.a || p_a_1_ == Blocks.ab || p_a_1_ == Blocks.aY;
        }

        public boolean b() {
            return this.f != null && this.h >= 2 && this.h <= 21 && this.g >= 3 && this.g <= 21;
        }

        public void c() {
            for (int i3 = 0; i3 < this.h; ++i3) {
                BlockPos blockpos3 = this.f.a(this.c, i3);

                for (int i4 = 0; i4 < this.g; ++i4) {
                    this.a.a(blockpos3.b(i4), Blocks.aY.P().a(BlockPortal.a, this.b), 2);
                }
            }

        }

        // CanaryMod: cloning the method above to determine the shape/size of the portal
        public CanaryBlock[][] getPortalBlocks() {
            CanaryBlock[][] portalBlockArray = new CanaryBlock[this.h][this.g];
            for (int i3 = 0; i3 < this.h; ++i3) {
                BlockPos blockpos3 = this.f.a(this.c, i3);

                for (int i4 = 0; i4 < this.g; ++i4) {
                    

                    //this.a.d(i18, i21, i19, Blocks.aO, this.b, 2);
                    portalBlockArray[i3][i4] = (CanaryBlock) this.a.getCanaryWorld().getBlockAt(new BlockPosition(blockpos3));
                }
            }
            return portalBlockArray;
        }
        // CanaryMod: end
    }
}
