package net.minecraft.block;

import java.util.Random;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockDaylightDetector extends BlockContainer {

    public static final PropertyInteger a = PropertyInteger.a("power", 0, 15);
    private final boolean b;

    public BlockDaylightDetector(boolean flag0) {
        super(Material.d);
        this.b = flag0;
        this.j(this.L.b().a(a, Integer.valueOf(0)));
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
        this.a(CreativeTabs.d);
        this.c(0.2F);
        this.a(f);
        this.c("daylightDetector");
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return ((Integer) iblockstate.b(a)).intValue();
    }

    public void d(World world, BlockPos blockpos) {
        if (!world.t.o()) {
            IBlockState iblockstate = world.p(blockpos);
            int i0 = world.b(EnumSkyBlock.SKY, blockpos) - world.ab();
            float f0 = world.d(1.0F);
            float f1 = f0 < 3.1415927F ? 0.0F : 6.2831855F;

            f0 += (f1 - f0) * 0.2F;
            i0 = Math.round((float) i0 * MathHelper.b(f0));
            i0 = MathHelper.a(i0, 0, 15);
            if (this.b) {
                i0 = 15 - i0;
            }

            if (((Integer) iblockstate.b(a)).intValue() != i0) {
                // CanaryMod: RedstoneChange; Comparator change
                RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), i0, ((Integer) iblockstate.b(a)).intValue()).call();
                if (hook.isCanceled()) {
                    return;
                }
                //
                world.a(blockpos, iblockstate.a(a, Integer.valueOf(i0)), 3);
            }

        }
    }

/* FIXME
    // CanaryMod: include break method so we can do a redstone change on destruction
    public void a(World world, int i0, int i1, int i2, Block block, int i4) {
        // CanaryMod: Redstone Change; broken
        int oldLvl = world.b(EnumSkyBlock.Sky, i0, i1, i2) - world.j;
        float f0 = world.d(1.0F);
        if (f0 < 3.1415927F) {
            f0 += (0.0F - f0) * 0.2F;
        }
        else {
            f0 += (6.2831855F - f0) * 0.2F;
        }
        oldLvl = Math.round((float) oldLvl * MathHelper.b(f0));
        if (oldLvl < 0) {
            oldLvl = 0;
        }
        if (oldLvl > 15) {
            oldLvl = 15;
        }
        if (oldLvl != 0) {
            new RedstoneChangeHook(new CanaryBlock(BlockType.DaylightSensor.getId(), (short) 2, i0, i1, i2, world.getCanaryWorld()), oldLvl, 0).call();
        }
        //
        super.a(world, i0, i1, i2, block, i4); // CanaryMod: call super
    }
*/

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (entityplayer.cm()) {
            if (world.D) {
                return true;
            }
            else {
                if (this.b) {
                    world.a(blockpos, Blocks.cl.P().a(a, iblockstate.b(a)), 4);
                    Blocks.cl.d(world, blockpos);
                }
                else {
                    world.a(blockpos, Blocks.cm.P().a(a, iblockstate.b(a)), 4);
                    Blocks.cm.d(world, blockpos);
                }

                return true;
            }
        }
        else {
            return super.a(world, blockpos, iblockstate, entityplayer, enumfacing, f0, f1, f2);
        }
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return Item.a((Block) Blocks.cl);
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public int b() {
        return 3;
    }

    public boolean g() {
        return true;
    }

    public TileEntity a(World world, int i0) {
        return new TileEntityDaylightDetector();
    }

    public IBlockState a(int i0) {
        return this.P().a(a, Integer.valueOf(i0));
    }

    public int c(IBlockState iblockstate) {
        return ((Integer) iblockstate.b(a)).intValue();
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{a});
    }

}
