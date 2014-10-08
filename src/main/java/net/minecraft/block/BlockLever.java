package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;

public class BlockLever extends Block {

    public static final PropertyEnum a = PropertyEnum.a("facing", BlockLever.EnumOrientation.class);
    public static final PropertyBool b = PropertyBool.a("powered");

    protected BlockLever() {
        super(Material.q);
        this.j(this.L.b().a(a, BlockLever.EnumOrientation.NORTH).a(b, Boolean.valueOf(false)));
        this.a(CreativeTabs.d);
    }

    public static int a(EnumFacing enumfacing) {
        switch (BlockLever.SwitchEnumFacing.a[enumfacing.ordinal()]) {
            case 1:
                return 0;

            case 2:
                return 5;

            case 3:
                return 4;

            case 4:
                return 3;

            case 5:
                return 2;

            case 6:
                return 1;

            default:
                return -1;
        }
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public boolean a(World world, BlockPos blockpos, EnumFacing enumfacing) {
        return enumfacing == EnumFacing.UP && World.a((IBlockAccess)world, blockpos.b()) ? true : this.d(world, blockpos.a(enumfacing.d()));
    }

    public boolean c(World world, BlockPos blockpos) {
        return this.d(world, blockpos.e()) ? true : (this.d(world, blockpos.f()) ? true : (this.d(world, blockpos.c()) ? true : (this.d(world, blockpos.d()) ? true : (World.a((IBlockAccess)world, blockpos.b()) ? true : this.d(world, blockpos.a())))));
    }

    protected boolean d(World world, BlockPos blockpos) {
        return world.p(blockpos).c().t();
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        IBlockState iblockstate = this.P().a(b, Boolean.valueOf(false));

        if (this.d(world, blockpos.a(enumfacing.d()))) {
            return iblockstate.a(a, BlockLever.EnumOrientation.a(enumfacing, entitylivingbase.aO()));
        }
        else {
            Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

            EnumFacing enumfacing1;

            do {
                if (!iterator.hasNext()) {
                    if (World.a((IBlockAccess)world, blockpos.b())) {
                        return iblockstate.a(a, BlockLever.EnumOrientation.a(EnumFacing.UP, entitylivingbase.aO()));
                    }

                    return iblockstate;
                }

                enumfacing1 = (EnumFacing)iterator.next();
            }
            while (enumfacing1 == enumfacing || !this.d(world, blockpos.a(enumfacing1.d())));

            return iblockstate.a(a, BlockLever.EnumOrientation.a(enumfacing1, entitylivingbase.aO()));
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (this.e(world, blockpos) && !this.d(world, blockpos.a(((BlockLever.EnumOrientation)iblockstate.b(a)).c().d()))) {
            this.b(world, blockpos, iblockstate, 0);
            world.g(blockpos);
        }
    }

    private boolean e(World world, BlockPos blockpos) {
        if (this.c(world, blockpos)) {
            return true;
        }
        else {
            this.b(world, blockpos, world.p(blockpos), 0);
            world.g(blockpos);
            return false;
        }
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        float f0 = 0.1875F;

        switch (BlockLever.SwitchEnumFacing.b[((BlockLever.EnumOrientation)iblockaccess.p(blockpos).b(a)).ordinal()]) {
            case 1:
                this.a(0.0F, 0.2F, 0.5F - f0, f0 * 2.0F, 0.8F, 0.5F + f0);
                break;

            case 2:
                this.a(1.0F - f0 * 2.0F, 0.2F, 0.5F - f0, 1.0F, 0.8F, 0.5F + f0);
                break;

            case 3:
                this.a(0.5F - f0, 0.2F, 0.0F, 0.5F + f0, 0.8F, f0 * 2.0F);
                break;

            case 4:
                this.a(0.5F - f0, 0.2F, 1.0F - f0 * 2.0F, 0.5F + f0, 0.8F, 1.0F);
                break;

            case 5:
            case 6:
                f0 = 0.25F;
                this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 0.6F, 0.5F + f0);
                break;

            case 7:
            case 8:
                f0 = 0.25F;
                this.a(0.5F - f0, 0.4F, 0.5F - f0, 0.5F + f0, 1.0F, 0.5F + f0);
        }
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (world.D) {
            return true;
        }
        else {
            iblockstate = iblockstate.a(b);
            world.a(blockpos, iblockstate, 3);
            world.a((double)blockpos.n() + 0.5D, (double)blockpos.o() + 0.5D, (double)blockpos.p() + 0.5D, "random.click", 0.3F, ((Boolean)iblockstate.b(b)).booleanValue() ? 0.6F : 0.5F);
            world.c(blockpos, (Block)this);
            EnumFacing enumfacing1 = ((BlockLever.EnumOrientation)iblockstate.b(a)).c();

            world.c(blockpos.a(enumfacing1.d()), (Block)this);
            return true;
        }
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (((Boolean)iblockstate.b(b)).booleanValue()) {
            world.c(blockpos, (Block)this);
            EnumFacing enumfacing = ((BlockLever.EnumOrientation)iblockstate.b(a)).c();

            world.c(blockpos.a(enumfacing.d()), (Block)this);
        }

        super.b(world, blockpos, iblockstate);
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return ((Boolean)iblockstate.b(b)).booleanValue() ? 15 : 0;
    }

    public int b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return !((Boolean)iblockstate.b(b)).booleanValue() ? 0 : (((BlockLever.EnumOrientation)iblockstate.b(a)).c() == enumfacing ? 15 : 0);
    }

    public boolean g() {
        return true;
    }

    public IBlockState a(int i0) {
        return this.P().a(a, BlockLever.EnumOrientation.a(i0 & 7)).a(b, Boolean.valueOf((i0 & 8) > 0));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((BlockLever.EnumOrientation)iblockstate.b(a)).a();

        if (((Boolean)iblockstate.b(b)).booleanValue()) {
            i0 |= 8;
        }

        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a, b });
    }

    public static enum EnumOrientation implements IStringSerializable {

        DOWN_X("DOWN_X", 0, 0, "down_x", EnumFacing.DOWN),
        EAST("EAST", 1, 1, "east", EnumFacing.EAST),
        WEST("WEST", 2, 2, "west", EnumFacing.WEST),
        SOUTH("SOUTH", 3, 3, "south", EnumFacing.SOUTH),
        NORTH("NORTH", 4, 4, "north", EnumFacing.NORTH),
        UP_Z("UP_Z", 5, 5, "up_z", EnumFacing.UP),
        UP_X("UP_X", 6, 6, "up_x", EnumFacing.UP),
        DOWN_Z("DOWN_Z", 7, 7, "down_z", EnumFacing.DOWN);
        private static final BlockLever.EnumOrientation[] i = new BlockLever.EnumOrientation[values().length];
        private static final BlockLever.EnumOrientation[] $VALUES = new BlockLever.EnumOrientation[]{ DOWN_X, EAST, WEST, SOUTH, NORTH, UP_Z, UP_X, DOWN_Z };
        private final int j;
        private final String k;
        private final EnumFacing l;

        private EnumOrientation(String p_i45709_1_, int p_i45709_2_, int p_i45709_3_, String p_i45709_4_, EnumFacing p_i45709_5_) {
            this.j = p_i45709_3_;
            this.k = p_i45709_4_;
            this.l = p_i45709_5_;
        }

        public static BlockLever.EnumOrientation a(int p_a_0_) {
            if (p_a_0_ < 0 || p_a_0_ >= i.length) {
                p_a_0_ = 0;
            }

            return i[p_a_0_];
        }

        public static BlockLever.EnumOrientation a(EnumFacing p_a_0_, EnumFacing p_a_1_) {
            switch (BlockLever.SwitchEnumFacing.a[p_a_0_.ordinal()]) {
                case 1:
                    switch (BlockLever.SwitchEnumFacing.c[p_a_1_.k().ordinal()]) {
                        case 1:
                            return DOWN_X;

                        case 2:
                            return DOWN_Z;

                        default:
                            throw new IllegalArgumentException("Invalid entityFacing " + p_a_1_ + " for facing " + p_a_0_);
                    }

                case 2:
                    switch (BlockLever.SwitchEnumFacing.c[p_a_1_.k().ordinal()]) {
                        case 1:
                            return UP_X;

                        case 2:
                            return UP_Z;

                        default:
                            throw new IllegalArgumentException("Invalid entityFacing " + p_a_1_ + " for facing " + p_a_0_);
                    }

                case 3:
                    return NORTH;

                case 4:
                    return SOUTH;

                case 5:
                    return WEST;

                case 6:
                    return EAST;

                default:
                    throw new IllegalArgumentException("Invalid facing: " + p_a_0_);
            }
        }

        public int a() {
            return this.j;
        }

        public EnumFacing c() {
            return this.l;
        }

        public String toString() {
            return this.k;
        }

        public String l() {
            return this.k;
        }

        static {
            BlockLever.EnumOrientation[] ablocklever_enumorientation = values();
            int i0 = ablocklever_enumorientation.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                BlockLever.EnumOrientation blocklever_enumorientation = ablocklever_enumorientation[i1];

                i[blocklever_enumorientation.a()] = blocklever_enumorientation;
            }
        }
    }

    static final class SwitchEnumFacing {

        static final int[] a;

        static final int[] b;

        static final int[] c = new int[EnumFacing.Axis.values().length];

        static {
            try {
                c[EnumFacing.Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror156) {
                ;
            }

            try {
                c[EnumFacing.Axis.Z.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror155) {
                ;
            }

            b = new int[BlockLever.EnumOrientation.values().length];

            try {
                b[BlockLever.EnumOrientation.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror154) {
                ;
            }

            try {
                b[BlockLever.EnumOrientation.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror153) {
                ;
            }

            try {
                b[BlockLever.EnumOrientation.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror152) {
                ;
            }

            try {
                b[BlockLever.EnumOrientation.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror151) {
                ;
            }

            try {
                b[BlockLever.EnumOrientation.UP_Z.ordinal()] = 5;
            }
            catch (NoSuchFieldError nosuchfielderror150) {
                ;
            }

            try {
                b[BlockLever.EnumOrientation.UP_X.ordinal()] = 6;
            }
            catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                b[BlockLever.EnumOrientation.DOWN_X.ordinal()] = 7;
            }
            catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                b[BlockLever.EnumOrientation.DOWN_Z.ordinal()] = 8;
            }
            catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            a = new int[EnumFacing.values().length];

            try {
                a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }

            try {
                a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror11) {
                ;
            }

            try {
                a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror12) {
                ;
            }

            try {
                a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror13) {
                ;
            }

            try {
                a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError nosuchfielderror14) {
                ;
            }

            try {
                a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError nosuchfielderror15) {
                ;
            }
        }
    }
}
