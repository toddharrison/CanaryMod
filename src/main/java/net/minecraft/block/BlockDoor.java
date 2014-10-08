package net.minecraft.block;

import com.google.common.base.Predicate;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockPhysicsHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDoor extends Block {

    public static final PropertyDirection a = PropertyDirection.a("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool b = PropertyBool.a("open");
    public static final PropertyEnum M = PropertyEnum.a("hinge", BlockDoor.EnumHingePosition.class);
    public static final PropertyBool N = PropertyBool.a("powered");
    public static final PropertyEnum O = PropertyEnum.a("half", BlockDoor.EnumDoorHalf.class);

    protected BlockDoor(Material material) {
        super(material);
        this.j(this.L.b().a(a, EnumFacing.NORTH).a(b, Boolean.valueOf(false)).a(M, BlockDoor.EnumHingePosition.LEFT).a(N, Boolean.valueOf(false)).a(O, BlockDoor.EnumDoorHalf.LOWER));
    }

    public boolean c() {
        return false;
    }

    public boolean b(IBlockAccess iblockaccess, BlockPos blockpos) {
        return g(e(iblockaccess, blockpos));
    }

    public boolean d() {
        return false;
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.a(world, blockpos);
        return super.a(world, blockpos, iblockstate);
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        this.k(e(iblockaccess, blockpos));
    }

    private void k(int i0) {
        float f0 = 0.1875F;

        this.a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        EnumFacing enumfacing = f(i0);
        boolean flag0 = g(i0);
        boolean flag1 = j(i0);

        if (flag0) {
            if (enumfacing == EnumFacing.EAST) {
                if (!flag1) {
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f0);
                }
                else {
                    this.a(0.0F, 0.0F, 1.0F - f0, 1.0F, 1.0F, 1.0F);
                }
            }
            else if (enumfacing == EnumFacing.SOUTH) {
                if (!flag1) {
                    this.a(1.0F - f0, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else {
                    this.a(0.0F, 0.0F, 0.0F, f0, 1.0F, 1.0F);
                }
            }
            else if (enumfacing == EnumFacing.WEST) {
                if (!flag1) {
                    this.a(0.0F, 0.0F, 1.0F - f0, 1.0F, 1.0F, 1.0F);
                }
                else {
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f0);
                }
            }
            else if (enumfacing == EnumFacing.NORTH) {
                if (!flag1) {
                    this.a(0.0F, 0.0F, 0.0F, f0, 1.0F, 1.0F);
                }
                else {
                    this.a(1.0F - f0, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
        else if (enumfacing == EnumFacing.EAST) {
            this.a(0.0F, 0.0F, 0.0F, f0, 1.0F, 1.0F);
        }
        else if (enumfacing == EnumFacing.SOUTH) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f0);
        }
        else if (enumfacing == EnumFacing.WEST) {
            this.a(1.0F - f0, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else if (enumfacing == EnumFacing.NORTH) {
            this.a(0.0F, 0.0F, 1.0F - f0, 1.0F, 1.0F, 1.0F);
        }
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (this.J == Material.f) {
            return true;
        }
        else {
            // CanaryMod: Block Physics
            if (new BlockPhysicsHook(new CanaryBlock(iblockstate, blockpos, world), false).call().isCanceled()) {
                return true;
            }
            //

            BlockPos blockpos1 = iblockstate.b(O) == BlockDoor.EnumDoorHalf.LOWER ? blockpos : blockpos.b();
            IBlockState iblockstate1 = blockpos.equals(blockpos1) ? iblockstate : world.p(blockpos1);

            if (iblockstate1.c() != this) {
                return false;
            }
            else {
                iblockstate = iblockstate1.a(b);
                world.a(blockpos1, iblockstate, 2);
                world.b(blockpos1, blockpos);
                world.a(entityplayer, ((Boolean)iblockstate.b(b)).booleanValue() ? 1003 : 1006, blockpos, 0);
                return true;
            }
        }
    }

    public void a(World world, BlockPos blockpos, boolean flag0) {
        IBlockState iblockstate = world.p(blockpos);

        if (iblockstate.c() == this) {
            // CanaryMod: Block Physics
            if (new BlockPhysicsHook(new CanaryBlock(iblockstate, blockpos, world), false).call().isCanceled()) {
                return;
            }
            //

            BlockPos blockpos1 = iblockstate.b(O) == BlockDoor.EnumDoorHalf.LOWER ? blockpos : blockpos.b();
            IBlockState iblockstate1 = blockpos == blockpos1 ? iblockstate : world.p(blockpos1);

            if (iblockstate1.c() == this && ((Boolean)iblockstate1.b(b)).booleanValue() != flag0) {
                world.a(blockpos1, iblockstate1.a(b, Boolean.valueOf(flag0)), 2);
                world.b(blockpos1, blockpos);
                world.a((EntityPlayer)null, flag0 ? 1003 : 1006, blockpos, 0);
            }
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (iblockstate.b(O) == BlockDoor.EnumDoorHalf.UPPER) {
            BlockPos blockpos1 = blockpos.b();
            IBlockState iblockstate1 = world.p(blockpos1);

            if (iblockstate1.c() != this) {
                world.g(blockpos);
            }
            else if (block != this) {
                this.a(world, blockpos1, iblockstate1, block);
            }
        }
        else {
            boolean flag0 = false;
            BlockPos blockpos2 = blockpos.a();
            IBlockState iblockstate2 = world.p(blockpos2);

            if (iblockstate2.c() != this) {
                world.g(blockpos);
                flag0 = true;
            }

            if (!World.a((IBlockAccess)world, blockpos.b())) {
                world.g(blockpos);
                flag0 = true;
                if (iblockstate2.c() == this) {
                    world.g(blockpos2);
                }
            }

            if (flag0) {
                if (!world.D) {
                    this.b(world, blockpos, iblockstate, 0);
                }
            }
            else {
                boolean flag1 = world.z(blockpos) || world.z(blockpos2);

                if ((flag1 || block.g()) && block != this && flag1 != ((Boolean)iblockstate2.b(N)).booleanValue()) {
                    world.a(blockpos2, iblockstate2.a(N, Boolean.valueOf(flag1)), 2);
                    if (flag1 != ((Boolean)iblockstate.b(b)).booleanValue()) {
                        world.a(blockpos, iblockstate.a(b, Boolean.valueOf(flag1)), 2);
                        world.b(blockpos, blockpos);
                        world.a((EntityPlayer)null, flag1 ? 1003 : 1006, blockpos, 0);
                    }
                }
            }
        }
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return iblockstate.b(O) == BlockDoor.EnumDoorHalf.UPPER ? null : this.j();
    }

    public MovingObjectPosition a(World world, BlockPos blockpos, Vec3 vec3, Vec3 vec31) {
        this.a(world, blockpos);
        return super.a(world, blockpos, vec3, vec31);
    }

    public boolean c(World world, BlockPos blockpos) {
        return blockpos.o() >= 255 ? false : World.a((IBlockAccess)world, blockpos.b()) && super.c(world, blockpos) && super.c(world, blockpos.a());
    }

    public int i() {
        return 1;
    }

    public static int e(IBlockAccess iblockaccess, BlockPos blockpos) {
        IBlockState iblockstate = iblockaccess.p(blockpos);
        int i0 = iblockstate.c().c(iblockstate);
        boolean flag0 = i(i0);
        IBlockState iblockstate1 = iblockaccess.p(blockpos.b());
        int i1 = iblockstate1.c().c(iblockstate1);
        int i2 = flag0 ? i1 : i0;
        IBlockState iblockstate2 = iblockaccess.p(blockpos.a());
        int i3 = iblockstate2.c().c(iblockstate2);
        int i4 = flag0 ? i0 : i3;
        boolean flag1 = (i4 & 1) != 0;
        boolean flag2 = (i4 & 2) != 0;

        return b(i2) | (flag0 ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
    }

    private Item j() {
        return this == Blocks.aA ? Items.aB : (this == Blocks.ap ? Items.ar : (this == Blocks.aq ? Items.as : (this == Blocks.ar ? Items.at : (this == Blocks.as ? Items.au : (this == Blocks.at ? Items.av : Items.aq)))));
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer) {
        BlockPos blockpos1 = blockpos.b();

        if (entityplayer.by.d && iblockstate.b(O) == BlockDoor.EnumDoorHalf.UPPER && world.p(blockpos1).c() == this) {
            world.g(blockpos1);
        }
    }

    public IBlockState a(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
        IBlockState iblockstate1;

        if (iblockstate.b(O) == BlockDoor.EnumDoorHalf.LOWER) {
            iblockstate1 = iblockaccess.p(blockpos.a());
            if (iblockstate1.c() == this) {
                iblockstate = iblockstate.a(M, iblockstate1.b(M)).a(N, iblockstate1.b(N));
            }
        }
        else {
            iblockstate1 = iblockaccess.p(blockpos.b());
            if (iblockstate1.c() == this) {
                iblockstate = iblockstate.a(a, iblockstate1.b(a)).a(b, iblockstate1.b(b));
            }
        }

        return iblockstate;
    }

    public IBlockState a(int i0) {
        return (i0 & 8) > 0 ? this.P().a(O, BlockDoor.EnumDoorHalf.UPPER).a(M, (i0 & 1) > 0 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).a(N, Boolean.valueOf((i0 & 2) > 0)) : this.P().a(O, BlockDoor.EnumDoorHalf.LOWER).a(a, EnumFacing.b(i0 & 3).f()).a(b, Boolean.valueOf((i0 & 4) > 0));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0;

        if (iblockstate.b(O) == BlockDoor.EnumDoorHalf.UPPER) {
            i0 = b0 | 8;
            if (iblockstate.b(M) == BlockDoor.EnumHingePosition.RIGHT) {
                i0 |= 1;
            }

            if (((Boolean)iblockstate.b(N)).booleanValue()) {
                i0 |= 2;
            }
        }
        else {
            i0 = b0 | ((EnumFacing)iblockstate.b(a)).e().b();
            if (((Boolean)iblockstate.b(b)).booleanValue()) {
                i0 |= 4;
            }
        }

        return i0;
    }

    protected static int b(int i0) {
        return i0 & 7;
    }

    public static boolean f(IBlockAccess iblockaccess, BlockPos blockpos) {
        return g(e(iblockaccess, blockpos));
    }

    public static EnumFacing h(IBlockAccess iblockaccess, BlockPos blockpos) {
        return f(e(iblockaccess, blockpos));
    }

    public static EnumFacing f(int i0) {
        return EnumFacing.b(i0 & 3).f();
    }

    protected static boolean g(int i0) {
        return (i0 & 4) != 0;
    }

    protected static boolean i(int i0) {
        return (i0 & 8) != 0;
    }

    protected static boolean j(int i0) {
        return (i0 & 16) != 0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ O, a, b, M, N });
    }

    public static enum EnumDoorHalf implements IStringSerializable {

        UPPER("UPPER", 0),
        LOWER("LOWER", 1);

        private static final BlockDoor.EnumDoorHalf[] $VALUES = new BlockDoor.EnumDoorHalf[]{ UPPER, LOWER };

        private EnumDoorHalf(String p_i45726_1_, int p_i45726_2_) {
        }

        public String toString() {
            return this.l();
        }

        public String l() {
            return this == UPPER ? "upper" : "lower";
        }

    }

    public static enum EnumHingePosition implements IStringSerializable {

        LEFT("LEFT", 0),
        RIGHT("RIGHT", 1);

        private static final BlockDoor.EnumHingePosition[] $VALUES = new BlockDoor.EnumHingePosition[]{ LEFT, RIGHT };

        private EnumHingePosition(String p_i45725_1_, int p_i45725_2_) {
        }

        public String toString() {
            return this.l();
        }

        public String l() {
            return this == LEFT ? "left" : "right";
        }

    }
}
