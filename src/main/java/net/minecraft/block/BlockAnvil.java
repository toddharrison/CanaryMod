package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockAnvil extends BlockFalling {

    public static final String[] a = new String[]{"intact", "slightlyDamaged", "veryDamaged"};
    private static final String[] N = new String[]{"anvil_top_damaged_0", "anvil_top_damaged_1", "anvil_top_damaged_2"};

    protected BlockAnvil() {
        super(Material.g);
        this.g(0);
        this.a(CreativeTabs.c);
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public void a(World world, int i0, int i1, int i2, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        int i3 = MathHelper.c((double) (entitylivingbase.y * 4.0F / 360.0F) + 0.5D) & 3;
        int i4 = world.e(i0, i1, i2) >> 2;

        ++i3;
        i3 %= 4;
        if (i3 == 0) {
            world.a(i0, i1, i2, 2 | i4 << 2, 2);
        }

        if (i3 == 1) {
            world.a(i0, i1, i2, 3 | i4 << 2, 2);
        }

        if (i3 == 2) {
            world.a(i0, i1, i2, 0 | i4 << 2, 2);
        }

        if (i3 == 3) {
            world.a(i0, i1, i2, 1 | i4 << 2, 2);
        }

    }

    public boolean a(World world, int i0, int i1, int i2, EntityPlayer entityplayer, int i3, float f0, float f1, float f2) {
        if (world.E) {
            return true;
        }
        else {
            entityplayer.c(i0, i1, i2);
            return true;
        }
    }

    public int b() {
        return 35;
    }

    public int a(int i0) {
        return i0 >> 2;
    }

    public void a(IBlockAccess iblockaccess, int i0, int i1, int i2) {
        int i3 = iblockaccess.e(i0, i1, i2) & 3;

        if (i3 != 3 && i3 != 1) {
            this.a(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
        }
        else {
            this.a(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
        }

    }

    protected void a(EntityFallingBlock entityfallingblock) {
        entityfallingblock.a(true);
    }

    public void a(World world, int i0, int i1, int i2, int i3) {
        world.c(1022, i0, i1, i2, 0);
    }

    // CanaryMod start: fix hitbox
    @Override
    public AxisAlignedBB a(World world, int i0, int i1, int i2) {
        this.a((IBlockAccess) world, i0, i1, i2);
        return super.a(world, i0, i1, i2);
    } // CanaryMod end

}
