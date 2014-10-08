package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockAnvil extends BlockFalling {

    public static final PropertyDirection a = PropertyDirection.a("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    public static final PropertyInteger b = PropertyInteger.a("damage", 0, 2);

    protected BlockAnvil() {
        super(Material.g);
        this.j(this.L.b().a(a, EnumFacing.NORTH).a(b, Integer.valueOf(0)));
        this.e(0);
        this.a(CreativeTabs.c);
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        EnumFacing enumfacing1 = entitylivingbase.aO().e();

        return super.a(world, blockpos, enumfacing, f0, f1, f2, i0, entitylivingbase).a(a, enumfacing1).a(b, Integer.valueOf(i0 >> 2));
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (!world.D) {
            entityplayer.a((IInteractionObject)(new BlockAnvil.Anvil(world, blockpos)));
        }

        return true;
    }

    public int a(IBlockState iblockstate) {
        return ((Integer)iblockstate.b(b)).intValue();
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        EnumFacing enumfacing = (EnumFacing)iblockaccess.p(blockpos).b(a);

        if (enumfacing.k() == EnumFacing.Axis.X) {
            this.a(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
        }
        else {
            this.a(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
        }
    }

    protected void a(EntityFallingBlock entityfallingblock) {
        entityfallingblock.a(true);
    }

    public void a_(World world, BlockPos blockpos) {
        world.b(1022, blockpos, 0);
    }

    public IBlockState a(int i0) {
        return this.P().a(a, EnumFacing.b(i0 & 3)).a(b, Integer.valueOf((i0 & 15) >> 2));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((EnumFacing)iblockstate.b(a)).b();

        i0 |= ((Integer)iblockstate.b(b)).intValue() << 2;
        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a, b });
    }

    public static class Anvil implements IInteractionObject {

        private final World a;
        private final BlockPos b;

        public Anvil(World p_i45741_1_, BlockPos p_i45741_2_) {
            this.a = p_i45741_1_;
            this.b = p_i45741_2_;
        }

        public String d_() {
            return "anvil";
        }

        public boolean k_() {
            return false;
        }

        public IChatComponent e_() {
            return new ChatComponentTranslation(Blocks.cf.a() + ".name", new Object[0]);
        }

        public Container a(InventoryPlayer p_a_1_, EntityPlayer p_a_2_) {
            return new ContainerRepair(p_a_1_, this.a, this.b, p_a_2_);
        }

        public String k() {
            return "minecraft:anvil";
        }
    }
}
