package net.minecraft.item;

import com.mojang.authlib.GameProfile;
import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.UUID;

public class ItemSkull extends Item {

    private static final String[] a = new String[]{ "skeleton", "wither", "zombie", "char", "creeper" };

    public ItemSkull() {
        this.a(CreativeTabs.c);
        this.d(0);
        this.a(true);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (enumfacing == EnumFacing.DOWN) {
            return false;
        }
        else {
            IBlockState iblockstate = world.p(blockpos);
            Block block = iblockstate.c();
            boolean flag0 = block.f(world, blockpos);

            // CanaryMod: BlockPlaceHook
            BlockPosition cbp = new BlockPosition(blockpos); // Translate native block pos
            CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp); // Store Clicked
            BlockFace cbf = BlockFace.fromByte((byte)enumfacing.a()); // Get the click face
            clicked.setFaceClicked(cbf); // Set face clicked
            cbp = cbp.safeClone(); // Remake BlockPosition
            cbp.transform(cbf); // Adjust position based on face
            //

            if (!flag0) {
                if (!world.p(blockpos).c().r().a()) {
                    return false;
                }

                blockpos = blockpos.a(enumfacing);
            }

            if (!entityplayer.a(blockpos, enumfacing, itemstack)) {
                return false;
            }
            else if (!Blocks.ce.c(world, blockpos)) {
                return false;
            }
            else {
                if (!world.D) {
                    CanaryBlock placed = new CanaryBlock(BlockType.SkeletonHead, (short)itemstack.h(), cbp, world.getCanaryWorld());
                    BlockPlaceHook hook = (BlockPlaceHook)new BlockPlaceHook(((EntityPlayerMP)entityplayer).getPlayer(), clicked, placed).call();
                    if (hook.isCanceled()) {
                        return false;
                    }
                    //
                    world.a(blockpos, Blocks.ce.P().a(BlockSkull.a, enumfacing), 3);
                    int i0 = 0;

                    if (enumfacing == EnumFacing.UP) {
                        i0 = MathHelper.c((double)(entityplayer.y * 16.0F / 360.0F) + 0.5D) & 15;
                    }

                    TileEntity tileentity = world.s(blockpos);

                    if (tileentity instanceof TileEntitySkull) {
                        TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;

                        if (itemstack.i() == 3) {
                            GameProfile gameprofile = null;

                            if (itemstack.n()) {
                                NBTTagCompound nbttagcompound = itemstack.o();

                                if (nbttagcompound.b("SkullOwner", 10)) {
                                    gameprofile = NBTUtil.a(nbttagcompound.m("SkullOwner"));
                                }
                                else if (nbttagcompound.b("SkullOwner", 8) && nbttagcompound.j("SkullOwner").length() > 0) {
                                    gameprofile = new GameProfile((UUID)null, nbttagcompound.j("SkullOwner"));
                                }
                            }

                            tileentityskull.a(gameprofile);
                        }
                        else {
                            tileentityskull.a(itemstack.i());
                        }

                        tileentityskull.b(i0);
                        Blocks.ce.a(world, blockpos, tileentityskull);
                    }

                    --itemstack.b;
                }

                return true;
            }
        }
    }

    public int a(int i0) {
        return i0;
    }

    public String e_(ItemStack itemstack) {
        int i0 = itemstack.i();

        if (i0 < 0 || i0 >= a.length) {
            i0 = 0;
        }

        return super.a() + "." + a[i0];
    }

    public String a(ItemStack itemstack) {
        if (itemstack.i() == 3 && itemstack.n()) {
            if (itemstack.o().b("SkullOwner", 8)) {
                return StatCollector.a("item.skull.player.name", new Object[]{ itemstack.o().j("SkullOwner") });
            }

            if (itemstack.o().b("SkullOwner", 10)) {
                NBTTagCompound nbttagcompound = itemstack.o().m("SkullOwner");

                if (nbttagcompound.b("Name", 8)) {
                    return StatCollector.a("item.skull.player.name", new Object[]{ nbttagcompound.j("Name") });
                }
            }
        }

        return super.a(itemstack);
    }

    public boolean a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("SkullOwner", 8) && nbttagcompound.j("SkullOwner").length() > 0) {
            GameProfile gameprofile = new GameProfile((UUID)null, nbttagcompound.j("SkullOwner"));

            gameprofile = TileEntitySkull.b(gameprofile);
            nbttagcompound.a("SkullOwner", (NBTBase)NBTUtil.a(new NBTTagCompound(), gameprofile));
            return true;
        }
        else {
            return false;
        }
    }
}
