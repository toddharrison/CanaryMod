package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneWire extends Block {

    public static final PropertyEnum a = PropertyEnum.a("north", BlockRedstoneWire.EnumAttachPosition.class);
    public static final PropertyEnum b = PropertyEnum.a("east", BlockRedstoneWire.EnumAttachPosition.class);
    public static final PropertyEnum M = PropertyEnum.a("south", BlockRedstoneWire.EnumAttachPosition.class);
    public static final PropertyEnum N = PropertyEnum.a("west", BlockRedstoneWire.EnumAttachPosition.class);
    public static final PropertyInteger O = PropertyInteger.a("power", 0, 15);
    private boolean P = true;
    private final Set Q = Sets.newHashSet();

    public BlockRedstoneWire() {
        super(Material.q);
        this.j(this.L.b().a(a, BlockRedstoneWire.EnumAttachPosition.NONE).a(b, BlockRedstoneWire.EnumAttachPosition.NONE).a(M, BlockRedstoneWire.EnumAttachPosition.NONE).a(N, BlockRedstoneWire.EnumAttachPosition.NONE).a(O, Integer.valueOf(0)));
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    public IBlockState a(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
        iblockstate = iblockstate.a(N, this.c(iblockaccess, blockpos, EnumFacing.WEST));
        iblockstate = iblockstate.a(b, this.c(iblockaccess, blockpos, EnumFacing.EAST));
        iblockstate = iblockstate.a(a, this.c(iblockaccess, blockpos, EnumFacing.NORTH));
        iblockstate = iblockstate.a(M, this.c(iblockaccess, blockpos, EnumFacing.SOUTH));
        return iblockstate;
    }

    private BlockRedstoneWire.EnumAttachPosition c(IBlockAccess iblockaccess, BlockPos blockpos, EnumFacing enumfacing) {
        BlockPos blockpos1 = blockpos.a(enumfacing);
        Block block = iblockaccess.p(blockpos.a(enumfacing)).c();

        if (!a(iblockaccess.p(blockpos1), enumfacing) && (block.s() || !d(iblockaccess.p(blockpos1.b())))) {
            Block block1 = iblockaccess.p(blockpos.a()).c();

            return !block1.s() && block.s() && d(iblockaccess.p(blockpos1.a())) ? BlockRedstoneWire.EnumAttachPosition.UP : BlockRedstoneWire.EnumAttachPosition.NONE;
        }
        else {
            return BlockRedstoneWire.EnumAttachPosition.SIDE;
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

    public boolean c(World world, BlockPos blockpos) {
        return World.a((IBlockAccess) world, blockpos.b()) || world.p(blockpos.b()).c() == Blocks.aX;
    }

    private IBlockState e(World world, BlockPos blockpos, IBlockState iblockstate) {
        iblockstate = this.a(world, blockpos, blockpos, iblockstate);
        ArrayList arraylist = Lists.newArrayList(this.Q);

        this.Q.clear();
        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext()) {
            BlockPos blockpos1 = (BlockPos) iterator.next();

            world.c(blockpos1, (Block) this);
        }

        return iblockstate;
    }

    private IBlockState a(World world, BlockPos blockpos, BlockPos blockpos1, IBlockState iblockstate) {
        IBlockState iblockstate1 = iblockstate;
        int i0 = ((Integer) iblockstate.b(O)).intValue();
        byte b0 = 0;
        int i1 = this.a(world, blockpos1, b0);

        this.P = false;
        int i2 = world.A(blockpos);

        this.P = true;
        if (i2 > 0 && i2 > i1 - 1) {
            i1 = i2;
        }

        int i3 = 0;
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumFacing enumfacing = (EnumFacing) iterator.next();
            BlockPos blockpos2 = blockpos.a(enumfacing);
            boolean flag0 = blockpos2.n() != blockpos1.n() || blockpos2.p() != blockpos1.p();

            if (flag0) {
                i3 = this.a(world, blockpos2, i3);
            }

            if (world.p(blockpos2).c().t() && !world.p(blockpos.a()).c().t()) {
                if (flag0 && blockpos.o() >= blockpos1.o()) {
                    i3 = this.a(world, blockpos2.a(), i3);
                }
            }
            else if (!world.p(blockpos2).c().t() && flag0 && blockpos.o() <= blockpos1.o()) {
                i3 = this.a(world, blockpos2.b(), i3);
            }
        }

        if (i3 > i1) {
            i1 = i3 - 1;
        }
        else if (i1 > 0) {
            --i1;
        }
        else {
            i1 = 0;
        }

        if (i2 > i1 - 1) {
            i1 = i2;
        }

        // CanaryMod: RedstoneChange  // TODO : does this fuck up redstone change?
        if (i0 != i1) {
            RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), i2, i3).call();
            if (hook.isCanceled()) {
                return iblockstate;
            }
        }
        //

        if (i0 != i1) {
            iblockstate = iblockstate.a(O, Integer.valueOf(i1));
            if (world.p(blockpos) == iblockstate1) {
                world.a(blockpos, iblockstate, 2);
            }

            this.Q.add(blockpos);
            EnumFacing[] aenumfacing = EnumFacing.values();
            int i4 = aenumfacing.length;

            for (int i5 = 0; i5 < i4; ++i5) {
                EnumFacing enumfacing1 = aenumfacing[i5];

                this.Q.add(blockpos.a(enumfacing1));
            }
        }

        return iblockstate;
    }

    private void d(World world, BlockPos blockpos) {
        if (world.p(blockpos).c() == this) {
            world.c(blockpos, (Block) this);
            EnumFacing[] aenumfacing = EnumFacing.values();
            int i0 = aenumfacing.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EnumFacing enumfacing = aenumfacing[i1];

                world.c(blockpos.a(enumfacing), (Block) this);
            }

        }
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (!world.D) {
            this.e(world, blockpos, iblockstate);
            Iterator iterator = EnumFacing.Plane.VERTICAL.iterator();

            EnumFacing enumfacing;

            while (iterator.hasNext()) {
                enumfacing = (EnumFacing) iterator.next();
                world.c(blockpos.a(enumfacing), (Block) this);
            }

            iterator = EnumFacing.Plane.HORIZONTAL.iterator();

            while (iterator.hasNext()) {
                enumfacing = (EnumFacing) iterator.next();
                this.d(world, blockpos.a(enumfacing));
            }

            iterator = EnumFacing.Plane.HORIZONTAL.iterator();

            while (iterator.hasNext()) {
                enumfacing = (EnumFacing) iterator.next();
                BlockPos blockpos1 = blockpos.a(enumfacing);

                if (world.p(blockpos1).c().t()) {
                    this.d(world, blockpos1.a());
                }
                else {
                    this.d(world, blockpos1.b());
                }
            }

        }
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        super.b(world, blockpos, iblockstate);
        if (!world.D) {
            // CanaryMod: RedstoneChange (Wire Destroy)
            int lvl = world.A(blockpos) - 1; // Subtract 1 from current in
            if (lvl > 0) {
                BlockPosition bp = new BlockPosition(blockpos);
                new RedstoneChangeHook(new CanaryBlock(BlockType.RedstoneWire.getId(), (short) iblockstate.c().c(iblockstate), bp.getBlockX(), bp.getBlockY(), bp.getBlockZ(), world.getCanaryWorld()), lvl, 0).call();
            }
            //
            EnumFacing[] aenumfacing = EnumFacing.values();
            int i0 = aenumfacing.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EnumFacing enumfacing = aenumfacing[i1];

                world.c(blockpos.a(enumfacing), (Block) this);
            }

            this.e(world, blockpos, iblockstate);
            Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

            EnumFacing enumfacing1;

            while (iterator.hasNext()) {
                enumfacing1 = (EnumFacing) iterator.next();
                this.d(world, blockpos.a(enumfacing1));
            }

            iterator = EnumFacing.Plane.HORIZONTAL.iterator();

            while (iterator.hasNext()) {
                enumfacing1 = (EnumFacing) iterator.next();
                BlockPos blockpos1 = blockpos.a(enumfacing1);

                if (world.p(blockpos1).c().t()) {
                    this.d(world, blockpos1.a());
                }
                else {
                    this.d(world, blockpos1.b());
                }
            }

        }
    }

    private int a(World world, BlockPos blockpos, int i0) {
        if (world.p(blockpos).c() != this) {
            return i0;
        }
        else {
            int i1 = ((Integer) world.p(blockpos).b(O)).intValue();

            return i1 > i0 ? i1 : i0;
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (!world.D) {
            if (this.c(world, blockpos)) {
                this.e(world, blockpos, iblockstate);
            }
            else {
                this.b(world, blockpos, iblockstate, 0);
                world.g(blockpos);
            }

        }
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return Items.aC;
    }

    public int b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return !this.P ? 0 : this.a(iblockaccess, blockpos, iblockstate, enumfacing);
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        if (!this.P) {
            return 0;
        }
        else {
            int i0 = ((Integer) iblockstate.b(O)).intValue();

            if (i0 == 0) {
                return 0;
            }
            else if (enumfacing == EnumFacing.UP) {
                return i0;
            }
            else {
                EnumSet enumset = EnumSet.noneOf(EnumFacing.class);
                Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                while (iterator.hasNext()) {
                    EnumFacing enumfacing1 = (EnumFacing) iterator.next();

                    if (this.d(iblockaccess, blockpos, enumfacing1)) {
                        enumset.add(enumfacing1);
                    }
                }

                if (enumfacing.k().c() && enumset.isEmpty()) {
                    return i0;
                }
                else if (enumset.contains(enumfacing) && !enumset.contains(enumfacing.f()) && !enumset.contains(enumfacing.e())) {
                    return i0;
                }
                else {
                    return 0;
                }
            }
        }
    }

    private boolean d(IBlockAccess iblockaccess, BlockPos blockpos, EnumFacing enumfacing) {
        BlockPos blockpos1 = blockpos.a(enumfacing);
        IBlockState iblockstate = iblockaccess.p(blockpos1);
        Block block = iblockstate.c();
        boolean flag0 = block.t();
        boolean flag1 = iblockaccess.p(blockpos.a()).c().t();

        return !flag1 && flag0 && e(iblockaccess, blockpos1.a()) ? true : (a(iblockstate, enumfacing) ? true : (block == Blocks.bc && iblockstate.b(BlockRedstoneDiode.N) == enumfacing ? true : !flag0 && e(iblockaccess, blockpos1.b())));
    }

    protected static boolean e(IBlockAccess iblockaccess, BlockPos blockpos) {
        return d(iblockaccess.p(blockpos));
    }

    protected static boolean d(IBlockState iblockstate) {
        return a(iblockstate, (EnumFacing) null);
    }

    protected static boolean a(IBlockState iblockstate, EnumFacing enumfacing) {
        Block block = iblockstate.c();

        if (block == Blocks.af) {
            return true;
        }
        else if (Blocks.bb.e(block)) {
            EnumFacing enumfacing1 = (EnumFacing) iblockstate.b(BlockRedstoneRepeater.N);

            return enumfacing1 == enumfacing || enumfacing1.d() == enumfacing;
        }
        else {
            return block.g() && enumfacing != null;
        }
    }

    public boolean g() {
        return this.P;
    }

    public IBlockState a(int i0) {
        return this.P().a(O, Integer.valueOf(i0));
    }

    public int c(IBlockState iblockstate) {
        return ((Integer) iblockstate.b(O)).intValue();
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{a, b, M, N, O});
    }

    static enum EnumAttachPosition implements IStringSerializable {

        UP("UP", 0, "up"), SIDE("SIDE", 1, "side"), NONE("NONE", 2, "none");
        private final String d;

        private static final BlockRedstoneWire.EnumAttachPosition[] $VALUES = new BlockRedstoneWire.EnumAttachPosition[]{UP, SIDE, NONE};

        private EnumAttachPosition(String p_i45689_1_, int p_i45689_2_, String p_i45689_3_) {
            this.d = p_i45689_3_;
        }

        public String toString() {
            return this.l();
        }

        public String l() {
            return this.d;
        }

    }
}
