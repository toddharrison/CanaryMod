package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIHarvestFarmland;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class EntityAIHarvestFarmland extends EntityAIMoveToBlock {

    private final EntityVillager c;
    private boolean d;
    private boolean e;
    private int f;
   
    public EntityAIHarvestFarmland(EntityVillager entityvillager, double d0) {
        super(entityvillager, d0, 16);
        this.c = entityvillager;
        this.canaryAI = new CanaryAIHarvestFarmland(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.a <= 0) {
            if (!this.c.o.Q().b("mobGriefing")) {
                return false;
            }

            this.f = -1;
            this.d = this.c.cs();
            this.e = this.c.cr();
        }

        return super.a();
    }

    public boolean b() {
        return this.f >= 0 && super.b();
    }

    public void c() {
        super.c();
    }

    public void d() {
        super.d();
    }

    public void e() {
        super.e();
        this.c.p().a((double) this.b.n() + 0.5D, (double) (this.b.o() + 1), (double) this.b.p() + 0.5D, 10.0F, (float) this.c.bP());
        if (this.f()) {
            World world = this.c.o;
            BlockPos blockpos = this.b.a();
            IBlockState iblockstate = world.p(blockpos);
            Block block = iblockstate.c();

            if (this.f == 0 && block instanceof BlockCrops && ((Integer) iblockstate.b(BlockCrops.a)).intValue() == 7) {
                world.b(blockpos, true);
            } else if (this.f == 1 && block == Blocks.a) {
                InventoryBasic inventorybasic = this.c.co();

                for (int i0 = 0; i0 < inventorybasic.n_(); ++i0) {
                    ItemStack itemstack = inventorybasic.a(i0);
                    boolean flag0 = false;

                    if (itemstack != null) {
                        if (itemstack.b() == Items.N) {
                            world.a(blockpos, Blocks.aj.P(), 3);
                            flag0 = true;
                        } else if (itemstack.b() == Items.bS) {
                            world.a(blockpos, Blocks.cc.P(), 3);
                            flag0 = true;
                        } else if (itemstack.b() == Items.bR) {
                            world.a(blockpos, Blocks.cb.P(), 3);
                            flag0 = true;
                        }
                    }

                    if (flag0) {
                        --itemstack.b;
                        if (itemstack.b <= 0) {
                            inventorybasic.a(i0, (ItemStack) null);
                        }
                        break;
                    }
                }
            }

            this.f = -1;
            this.a = 10;
        }

    }

    protected boolean a(World world, BlockPos blockpos) {
        Block block = world.p(blockpos).c();

        if (block == Blocks.ak) {
            blockpos = blockpos.a();
            IBlockState iblockstate = world.p(blockpos);

            block = iblockstate.c();
            if (block instanceof BlockCrops && ((Integer) iblockstate.b(BlockCrops.a)).intValue() == 7 && this.e && (this.f == 0 || this.f < 0)) {
                this.f = 0;
                return true;
            }

            if (block == Blocks.a && this.d && (this.f == 1 || this.f < 0)) {
                this.f = 1;
                return true;
            }
        }

        return false;
    }
}
