package net.minecraft.block;

import com.google.common.base.Predicate;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockChest extends BlockContainer {

    public static final PropertyDirection a = PropertyDirection.a("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    private final Random M = new Random();
    public final int b;
    private int oldLvl; // CanaryMod: store old

    protected BlockChest(int i0) {
        super(Material.d);
        this.j(this.L.b().a(a, EnumFacing.NORTH));
        this.b = i0;
        this.a(CreativeTabs.c);
        this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 2;
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        if (iblockaccess.p(blockpos.c()).c() == this) {
            this.a(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
        }
        else if (iblockaccess.p(blockpos.d()).c() == this) {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
        }
        else if (iblockaccess.p(blockpos.e()).c() == this) {
            this.a(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
        else if (iblockaccess.p(blockpos.f()).c() == this) {
            this.a(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
        }
        else {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.e(world, blockpos, iblockstate);
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumFacing enumfacing = (EnumFacing)iterator.next();
            BlockPos blockpos1 = blockpos.a(enumfacing);
            IBlockState iblockstate1 = world.p(blockpos1);

            if (iblockstate1.c() == this) {
                this.e(world, blockpos1, iblockstate1);
            }
        }
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        return this.P().a(a, entitylivingbase.aO());
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        EnumFacing enumfacing = EnumFacing.b(MathHelper.c((double)(entitylivingbase.y * 4.0F / 360.0F) + 0.5D) & 3).d();

        iblockstate = iblockstate.a(a, enumfacing);
        BlockPos blockpos1 = blockpos.c();
        BlockPos blockpos2 = blockpos.d();
        BlockPos blockpos3 = blockpos.e();
        BlockPos blockpos4 = blockpos.f();
        boolean flag0 = this == world.p(blockpos1).c();
        boolean flag1 = this == world.p(blockpos2).c();
        boolean flag2 = this == world.p(blockpos3).c();
        boolean flag3 = this == world.p(blockpos4).c();

        if (!flag0 && !flag1 && !flag2 && !flag3) {
            world.a(blockpos, iblockstate, 3);
        }
        else if (enumfacing.k() == EnumFacing.Axis.X && (flag0 || flag1)) {
            if (flag0) {
                world.a(blockpos1, iblockstate, 3);
            }
            else {
                world.a(blockpos2, iblockstate, 3);
            }

            world.a(blockpos, iblockstate, 3);
        }
        else if (enumfacing.k() == EnumFacing.Axis.Z && (flag2 || flag3)) {
            if (flag2) {
                world.a(blockpos3, iblockstate, 3);
            }
            else {
                world.a(blockpos4, iblockstate, 3);
            }

            world.a(blockpos, iblockstate, 3);
        }

        if (itemstack.s()) {
            TileEntity tileentity = world.s(blockpos);

            if (tileentity instanceof TileEntityChest) {
                ((TileEntityChest)tileentity).a(itemstack.q());
            }
        }
    }

    public IBlockState e(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (world.D) {
            return iblockstate;
        }
        else {
            IBlockState iblockstate1 = world.p(blockpos.c());
            IBlockState iblockstate2 = world.p(blockpos.d());
            IBlockState iblockstate3 = world.p(blockpos.e());
            IBlockState iblockstate4 = world.p(blockpos.f());
            EnumFacing enumfacing = (EnumFacing)iblockstate.b(a);
            Block block = iblockstate1.c();
            Block block1 = iblockstate2.c();
            Block block2 = iblockstate3.c();
            Block block3 = iblockstate4.c();

            if (block != this && block1 != this) {
                boolean flag0 = block.m();
                boolean flag1 = block1.m();

                if (block2 == this || block3 == this) {
                    BlockPos blockpos1 = block2 == this ? blockpos.e() : blockpos.f();
                    IBlockState iblockstate5 = world.p(blockpos1.c());
                    IBlockState iblockstate6 = world.p(blockpos1.d());

                    enumfacing = EnumFacing.SOUTH;
                    EnumFacing enumfacing1;

                    if (block2 == this) {
                        enumfacing1 = (EnumFacing)iblockstate3.b(a);
                    }
                    else {
                        enumfacing1 = (EnumFacing)iblockstate4.b(a);
                    }

                    if (enumfacing1 == EnumFacing.NORTH) {
                        enumfacing = EnumFacing.NORTH;
                    }

                    Block block4 = iblockstate5.c();
                    Block block5 = iblockstate6.c();

                    if ((flag0 || block4.m()) && !flag1 && !block5.m()) {
                        enumfacing = EnumFacing.SOUTH;
                    }

                    if ((flag1 || block5.m()) && !flag0 && !block4.m()) {
                        enumfacing = EnumFacing.NORTH;
                    }
                }
            }
            else {
                BlockPos blockpos2 = block == this ? blockpos.c() : blockpos.d();
                IBlockState iblockstate7 = world.p(blockpos2.e());
                IBlockState iblockstate8 = world.p(blockpos2.f());

                enumfacing = EnumFacing.EAST;
                EnumFacing enumfacing2;

                if (block == this) {
                    enumfacing2 = (EnumFacing)iblockstate1.b(a);
                }
                else {
                    enumfacing2 = (EnumFacing)iblockstate2.b(a);
                }

                if (enumfacing2 == EnumFacing.WEST) {
                    enumfacing = EnumFacing.WEST;
                }

                Block block6 = iblockstate7.c();
                Block block7 = iblockstate8.c();

                if ((block2.m() || block6.m()) && !block3.m() && !block7.m()) {
                    enumfacing = EnumFacing.EAST;
                }

                if ((block3.m() || block7.m()) && !block2.m() && !block6.m()) {
                    enumfacing = EnumFacing.WEST;
                }
            }

            iblockstate = iblockstate.a(a, enumfacing);
            world.a(blockpos, iblockstate, 3);
            return iblockstate;
        }
    }

    public IBlockState f(World world, BlockPos blockpos, IBlockState iblockstate) {
        EnumFacing enumfacing = null;
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumFacing enumfacing1 = (EnumFacing)iterator.next();
            IBlockState iblockstate1 = world.p(blockpos.a(enumfacing1));

            if (iblockstate1.c() == this) {
                return iblockstate;
            }

            if (iblockstate1.c().m()) {
                if (enumfacing != null) {
                    enumfacing = null;
                    break;
                }

                enumfacing = enumfacing1;
            }
        }

        if (enumfacing != null) {
            return iblockstate.a(a, enumfacing.d());
        }
        else {
            EnumFacing enumfacing2 = (EnumFacing)iblockstate.b(a);

            if (world.p(blockpos.a(enumfacing2)).c().m()) {
                enumfacing2 = enumfacing2.d();
            }

            if (world.p(blockpos.a(enumfacing2)).c().m()) {
                enumfacing2 = enumfacing2.e();
            }

            if (world.p(blockpos.a(enumfacing2)).c().m()) {
                enumfacing2 = enumfacing2.d();
            }

            return iblockstate.a(a, enumfacing2);
        }
    }

    public boolean c(World world, BlockPos blockpos) {
        int i0 = 0;
        BlockPos blockpos1 = blockpos.e();
        BlockPos blockpos2 = blockpos.f();
        BlockPos blockpos3 = blockpos.c();
        BlockPos blockpos4 = blockpos.d();

        if (world.p(blockpos1).c() == this) {
            if (this.e(world, blockpos1)) {
                return false;
            }

            ++i0;
        }

        if (world.p(blockpos2).c() == this) {
            if (this.e(world, blockpos2)) {
                return false;
            }

            ++i0;
        }

        if (world.p(blockpos3).c() == this) {
            if (this.e(world, blockpos3)) {
                return false;
            }

            ++i0;
        }

        if (world.p(blockpos4).c() == this) {
            if (this.e(world, blockpos4)) {
                return false;
            }

            ++i0;
        }

        return i0 <= 1;
    }

    private boolean e(World world, BlockPos blockpos) {
        if (world.p(blockpos).c() != this) {
            return false;
        }
        else {
            Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

            EnumFacing enumfacing;

            do {
                if (!iterator.hasNext()) {
                    return false;
                }

                enumfacing = (EnumFacing)iterator.next();
            }
            while (world.p(blockpos.a(enumfacing)).c() != this);

            return true;
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        super.a(world, blockpos, iblockstate, block);
        TileEntity tileentity = world.s(blockpos);

        if (tileentity instanceof TileEntityChest) {
            tileentity.E();
        }
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        TileEntity tileentity = world.s(blockpos);

        if (tileentity instanceof IInventory) {
            InventoryHelper.a(world, blockpos, (IInventory)tileentity);
            world.e(blockpos, this);
        }

        super.b(world, blockpos, iblockstate);
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (world.D) {
            return true;
        }
        else {
            ILockableContainer ilockablecontainer = this.d(world, blockpos);

            if (ilockablecontainer != null) {
                entityplayer.a((IInventory)ilockablecontainer);
            }

            return true;
        }
    }

    public ILockableContainer d(World world, BlockPos blockpos) {
        TileEntity tileentity = world.s(blockpos);

        if (!(tileentity instanceof TileEntityChest)) {
            return null;
        }
        else {
            Object object = (TileEntityChest)tileentity;

            if (this.m(world, blockpos)) {
                return null;
            }
            else {
                Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                while (iterator.hasNext()) {
                    EnumFacing enumfacing = (EnumFacing)iterator.next();
                    BlockPos blockpos1 = blockpos.a(enumfacing);
                    Block block = world.p(blockpos1).c();

                    if (block == this) {
                        if (this.m(world, blockpos1)) {
                            return null;
                        }

                        TileEntity tileentity1 = world.s(blockpos1);

                        if (tileentity1 instanceof TileEntityChest) {
                            if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
                                object = new InventoryLargeChest("container.chestDouble", (ILockableContainer)object, (TileEntityChest)tileentity1);
                            }
                            else {
                                object = new InventoryLargeChest("container.chestDouble", (TileEntityChest)tileentity1, (ILockableContainer)object);
                            }
                        }
                    }
                }

                return (ILockableContainer)object;
            }
        }
    }

    public TileEntity a(World world, int i0) {
        return new TileEntityChest();
    }

    public boolean g() {
        return this.b == 1;
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        if (!this.g()) {
            return 0;
        }
        else {
            int i0 = 0;
            TileEntity tileentity = iblockaccess.s(blockpos);

            if (tileentity instanceof TileEntityChest) {
                i0 = ((TileEntityChest)tileentity).l;
            }
            // CanaryMod: RedstoneChange
            int newLvl = MathHelper.a(i0, 0, 15);
            if (newLvl != oldLvl) {
                RedstoneChangeHook hook = (RedstoneChangeHook)new RedstoneChangeHook(((World)iblockaccess).getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), oldLvl, newLvl).call();
                if (hook.isCanceled()) {
                    return oldLvl;
                }
            }
            return oldLvl = newLvl;
            //
        }
    }

    public int b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return enumfacing == EnumFacing.UP ? this.a(iblockaccess, blockpos, iblockstate, enumfacing) : 0;
    }

    private boolean m(World world, BlockPos blockpos) {
        return this.n(world, blockpos) || this.o(world, blockpos);
    }

    private boolean n(World world, BlockPos blockpos) {
        return world.p(blockpos.a()).c().t();
    }

    private boolean o(World world, BlockPos blockpos) {
        Iterator iterator = world.a(EntityOcelot.class, new AxisAlignedBB((double)blockpos.n(), (double)(blockpos.o() + 1), (double)blockpos.p(), (double)(blockpos.n() + 1), (double)(blockpos.o() + 2), (double)(blockpos.p() + 1))).iterator();

        EntityOcelot entityocelot;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            Entity entity = (Entity)iterator.next();

            entityocelot = (EntityOcelot)entity;
        }
        while (!entityocelot.cl());

        return true;
    }

    public boolean N() {
        return true;
    }

    public int l(World world, BlockPos blockpos) {
        return Container.b((IInventory)this.d(world, blockpos));
    }

    public IBlockState a(int i0) {
        EnumFacing enumfacing = EnumFacing.a(i0);

        if (enumfacing.k() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.P().a(a, enumfacing);
    }

    public int c(IBlockState iblockstate) {
        return ((EnumFacing)iblockstate.b(a)).a();
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a });
    }
}
