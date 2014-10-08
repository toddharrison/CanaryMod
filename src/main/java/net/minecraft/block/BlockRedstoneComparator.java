package net.minecraft.block;

import com.google.common.base.Predicate;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockPhysicsHook;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider {

    public static final PropertyBool a = PropertyBool.a("powered");
    public static final PropertyEnum b = PropertyEnum.a("mode", Mode.class);

    public BlockRedstoneComparator(boolean flag0) {
        super(flag0);
        this.j(this.L.b().a(N, EnumFacing.NORTH).a(a, Boolean.valueOf(false)).a(b, Mode.COMPARE));
        this.A = true;
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return Items.ce;
    }

    protected int d(IBlockState iblockstate) {
        return 2;
    }

    protected IBlockState e(IBlockState iblockstate) {
        Boolean obool = (Boolean)iblockstate.b(a);
        Mode blockredstonecomparator_mode = (Mode)iblockstate.b(b);
        EnumFacing enumfacing = (EnumFacing)iblockstate.b(N);

        return Blocks.ck.P().a(N, enumfacing).a(a, obool).a(b, blockredstonecomparator_mode);
    }

    protected IBlockState k(IBlockState iblockstate) {
        Boolean obool = (Boolean)iblockstate.b(a);
        Mode blockredstonecomparator_mode = (Mode)iblockstate.b(b);
        EnumFacing enumfacing = (EnumFacing)iblockstate.b(N);

        return Blocks.cj.P().a(N, enumfacing).a(a, obool).a(b, blockredstonecomparator_mode);
    }

    protected boolean l(IBlockState iblockstate) {
        return this.M || ((Boolean)iblockstate.b(a)).booleanValue();
    }

    protected int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate) {
        TileEntity tileentity = iblockaccess.s(blockpos);

        return tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).b() : 0;
    }

    private int j(World world, BlockPos blockpos, IBlockState iblockstate) {
        return iblockstate.b(b) == Mode.SUBTRACT ? Math.max(this.f(world, blockpos, iblockstate) - this.c((IBlockAccess)world, blockpos, iblockstate), 0) : this.f(world, blockpos, iblockstate);
    }

    protected boolean e(World world, BlockPos blockpos, IBlockState iblockstate) {
        int i0 = this.f(world, blockpos, iblockstate);

        if (i0 >= 15) {
            return true;
        }
        else if (i0 == 0) {
            return false;
        }
        else {
            int i1 = this.c((IBlockAccess)world, blockpos, iblockstate);

            return i1 == 0 ? true : i0 >= i1;
        }
    }

    protected int f(World world, BlockPos blockpos, IBlockState iblockstate) {
        int i0 = super.f(world, blockpos, iblockstate);
        EnumFacing enumfacing = (EnumFacing)iblockstate.b(N);
        BlockPos blockpos1 = blockpos.a(enumfacing);
        Block block = world.p(blockpos1).c();

        if (block.N()) {
            i0 = block.l(world, blockpos1);
        }
        else if (i0 < 15 && block.t()) {
            blockpos1 = blockpos1.a(enumfacing);
            block = world.p(blockpos1).c();
            if (block.N()) {
                i0 = block.l(world, blockpos1);
            }
            else if (block.r() == Material.a) {
                EntityItemFrame entityitemframe = this.a(world, enumfacing, blockpos1);

                if (entityitemframe != null) {
                    i0 = entityitemframe.q();
                }
            }
        }

        return i0;
    }

    private EntityItemFrame a(World world, final EnumFacing enumfacing, BlockPos blockpos) {
        List list = world.a(EntityItemFrame.class, new AxisAlignedBB((double)blockpos.n(), (double)blockpos.o(), (double)blockpos.p(), (double)(blockpos.n() + 1), (double)(blockpos.o() + 1), (double)(blockpos.p() + 1)), new Predicate() {

                                public boolean a(Entity world) {
                                    return world != null && world.aO() == enumfacing;
                                }

                                public boolean apply(Object p_apply_1_) {
                                    return this.a((Entity)p_apply_1_);
                                }
                            }
                           );

        return list.size() == 1 ? (EntityItemFrame)list.get(0) : null;
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        // CanaryMod: Block Physics
        if (new BlockPhysicsHook(new CanaryBlock(iblockstate, blockpos, world), false).call().isCanceled()) {
            return false;
        }
        //
        if (!entityplayer.by.e) {
            return false;
        }
        else {
            iblockstate = iblockstate.a(b);
            world.a((double)blockpos.n() + 0.5D, (double)blockpos.o() + 0.5D, (double)blockpos.p() + 0.5D, "random.click", 0.3F, iblockstate.b(b) == Mode.SUBTRACT ? 0.55F : 0.5F);
            world.a(blockpos, iblockstate, 2);
            this.k(world, blockpos, iblockstate);
            return true;
        }
    }

    protected void g(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (!world.a(blockpos, (Block)this)) {
            int i0 = this.j(world, blockpos, iblockstate);
            TileEntity tileentity = world.s(blockpos);
            int i1 = tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).b() : 0;

            if (i0 != i1 || this.l(iblockstate) != this.e(world, blockpos, iblockstate)) {
                if (this.i(world, blockpos, iblockstate)) {
                    world.a(blockpos, this, 2, -1);
                }
                else {
                    world.a(blockpos, this, 2, 0);
                }
            }
        }
    }

    private void k(World world, BlockPos blockpos, IBlockState iblockstate) {
        int i0 = this.j(world, blockpos, iblockstate);
        TileEntity tileentity = world.s(blockpos);
        int i1 = 0;

        if (tileentity instanceof TileEntityComparator) {
            TileEntityComparator tileentitycomparator = (TileEntityComparator)tileentity;

            i1 = tileentitycomparator.b();
            tileentitycomparator.a(i0);
        }

        if (i1 != i0 || iblockstate.b(b) == Mode.COMPARE) {
            boolean flag0 = this.e(world, blockpos, iblockstate);
            boolean flag1 = this.l(iblockstate);

            // CanaryMod: RedstoneChange; Comparator change
            if (new RedstoneChangeHook(new CanaryBlock(iblockstate, blockpos, world), i0, i1).call().isCanceled()) {
                return;
            }
            //
            if (flag1 && !flag0) {
                world.a(blockpos, iblockstate.a(a, Boolean.valueOf(false)), 2);
            }
            else if (!flag1 && flag0) {
                world.a(blockpos, iblockstate.a(a, Boolean.valueOf(true)), 2);
            }

            this.h(world, blockpos, iblockstate);
        }
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (this.M) {
            world.a(blockpos, this.k(iblockstate).a(a, Boolean.valueOf(true)), 4);
        }

        this.k(world, blockpos, iblockstate);
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        super.c(world, blockpos, iblockstate);
        world.a(blockpos, this.a(world, 0));
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        // CanaryMod: Comparator break
        int oldLvl = ((TileEntityComparator)world.s(blockpos)).b();
        if (oldLvl != 0) {
            new RedstoneChangeHook(new CanaryBlock(iblockstate, blockpos, world), oldLvl, 0).call();
        }
        //
        super.b(world, blockpos, iblockstate);
        world.t(blockpos);
        this.h(world, blockpos, iblockstate);
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, int i0, int i1) {
        super.a(world, blockpos, iblockstate, i0, i1);
        TileEntity tileentity = world.s(blockpos);

        return tileentity == null ? false : tileentity.c(i0, i1);
    }

    public TileEntity a(World world, int i0) {
        return new TileEntityComparator();
    }

    public IBlockState a(int i0) {
        return this.P().a(N, EnumFacing.b(i0)).a(a, Boolean.valueOf((i0 & 8) > 0)).a(b, (i0 & 4) > 0 ? Mode.SUBTRACT : Mode.COMPARE);
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((EnumFacing)iblockstate.b(N)).b();

        if (((Boolean)iblockstate.b(a)).booleanValue()) {
            i0 |= 8;
        }

        if (iblockstate.b(b) == Mode.SUBTRACT) {
            i0 |= 4;
        }

        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ N, b, a });
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        return this.P().a(N, entitylivingbase.aO().d()).a(a, Boolean.valueOf(false)).a(b, Mode.COMPARE);
    }

    public static enum Mode implements IStringSerializable {

        COMPARE("COMPARE", 0, "compare"),
        SUBTRACT("SUBTRACT", 1, "subtract");
        private static final Mode[] $VALUES = new Mode[]{ COMPARE, SUBTRACT };
        private final String c;

        private Mode(String p_i45731_1_, int p_i45731_2_, String p_i45731_3_) {
            this.c = p_i45731_3_;
        }

        public String toString() {
            return this.c;
        }

        public String l() {
            return this.c;
        }

    }
}
