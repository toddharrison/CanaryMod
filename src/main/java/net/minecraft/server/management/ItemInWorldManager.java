package net.minecraft.server.management;

import net.canarymod.api.GameMode;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockDestroyHook;
import net.canarymod.hook.player.BlockLeftClickHook;
import net.canarymod.hook.player.GameModeChangeHook;
import net.canarymod.hook.player.ItemUseHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;

public class ItemInWorldManager {

    public World a;
    public EntityPlayerMP b;
    private WorldSettings.GameType c;
    private boolean d;
    private int e;
    private BlockPos f;
    private int g;
    private boolean h;
    private BlockPos i;
    private int j;
    private int k;

    public ItemInWorldManager(World world) {
        this.c = WorldSettings.GameType.NOT_SET;
        this.f = BlockPos.a;
        this.i = BlockPos.a;
        this.k = -1;
        this.a = world;
    }

    public void a(WorldSettings.GameType worldsettings_gametype) {
        // CanaryMod: GameModeChangeHook
        if (this.c != worldsettings_gametype) {
            GameModeChangeHook gmch = (GameModeChangeHook) new GameModeChangeHook(this.b.getPlayer(), GameMode.fromId(worldsettings_gametype.a())).call();
            if (gmch.isCanceled()) {
                return; //Blocked mode change
            }
        }
        //
        this.c = worldsettings_gametype;
        worldsettings_gametype.a(this.b.by);
        this.b.t();
        this.b.b.an().a((Packet)(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_GAME_MODE, new EntityPlayerMP[]{ this.b })));
    }

    public WorldSettings.GameType b() {
        return this.c;
    }

    public boolean c() {
        return this.c.e();
    }

    public boolean d() {
        return this.c.d();
    }

    public void b(WorldSettings.GameType worldsettings_gametype) {
        if (this.c == WorldSettings.GameType.NOT_SET) {
            this.c = worldsettings_gametype;
        }

        this.a(this.c);
    }

    public void a() {
        ++this.g;
        float f0;
        int i0;

        if (this.h) {
            int i1 = this.g - this.j;
            Block block = this.a.p(this.i).c();

            if (block.r() == Material.a) {
                this.h = false;
            }
            else {
                f0 = block.a((EntityPlayer)this.b, this.b.o, this.i) * (float)(i1 + 1);
                i0 = (int) (f0 * 10.0F);
                if (i0 != this.k) {
                    this.a.c(this.b.F(), this.i, i0);
                    this.k = i0;
                }

                if (f0 >= 1.0F) {
                    this.h = false;
                    this.b(this.i);
                }
            }
        }
        else if (this.d) {
            Block block1 = this.a.p(this.f).c();

            if (block1.r() == Material.a) {
                this.a.c(this.b.F(), this.f, -1);
                this.k = -1;
                this.d = false;
            }
            else {
                int i2 = this.g - this.e;

                f0 = block1.a((EntityPlayer)this.b, this.b.o, this.i) * (float)(i2 + 1);
                i0 = (int) (f0 * 10.0F);
                if (i0 != this.k) {
                    this.a.c(this.b.F(), this.f, i0);
                    this.k = i0;
                }
            }
        }
    }

    public void a(BlockPos blockpos, EnumFacing enumfacing) {
        // CanaryMod: BlockLeftClick
        net.canarymod.api.world.blocks.Block cblock = this.b.getCanaryWorld().getBlockAt(i0, i1, i2);
        BlockLeftClickHook blcHook = (BlockLeftClickHook) new BlockLeftClickHook(this.b.getPlayer(), cblock).call();
        if (blcHook.isCanceled()) {
            if (this.c.d()) { // Fix ghosting for creative players
                this.b.a.a((Packet) (new S23PacketBlockChange(i0, i1, i2, this.a)));
            }
            return;
        }
        //
        if (this.d()) {
            if (!this.a.a((EntityPlayer)null, blockpos, enumfacing)) {
                this.b(blockpos);
            }
        }
        else {
            Block block = this.a.p(blockpos).c();

            if (this.c.c()) {
                if (this.c == WorldSettings.GameType.SPECTATOR) {
                    return;
                }

                if (!this.b.cm()) {
                    ItemStack itemstack = this.b.bY();

                    if (itemstack == null) {
                        return;
                    }

                    if (!itemstack.c(block)) {
                        return;
                    }
                }
            }

            this.a.a((EntityPlayer)null, blockpos, enumfacing);
            this.e = this.g;
            float f0 = 1.0F;

            if (block.r() != Material.a) {
                block.a(this.a, blockpos, (EntityPlayer)this.b);
                f0 = block.a((EntityPlayer)this.b, this.b.o, blockpos);
            }

            if (block.r() != Material.a && f0 >= 1.0F) {
                this.b(blockpos);
            }
            else {
                this.d = true;
                this.f = blockpos;
                int i0 = (int)(f0 * 10.0F);

                this.a.c(this.b.F(), blockpos, i0);
                this.k = i0;
            }

        }
    }

    public void a(BlockPos blockpos) {
        if (blockpos.equals(this.f)) {
            int i0 = this.g - this.e;
            Block block = this.a.p(blockpos).c();

            if (block.r() != Material.a) {
                float f0 = block.a((EntityPlayer)this.b, this.b.o, blockpos) * (float)(i0 + 1);

                if (f0 >= 0.7F) {
                    this.d = false;
                    this.a.c(this.b.F(), blockpos, -1);
                    this.b(blockpos);
                }
                else if (!this.h) {
                    this.d = false;
                    this.h = true;
                    this.i = blockpos;
                    this.j = this.e;
                }
            }
        }
    }

    public void e() {
        this.d = false;
        this.a.c(this.b.F(), this.f, -1);
    }

    private boolean c(BlockPos blockpos) {
        IBlockState iblockstate = this.a.p(blockpos);
        // CanaryMod: BlockDestroyHook
        net.canarymod.api.world.blocks.Block cblock = this.b.getCanaryWorld().getBlockAt(i0, i1, i2);
        BlockDestroyHook hook = (BlockDestroyHook) new BlockDestroyHook(this.b.getPlayer(), cblock).call();
        if (hook.isCanceled()) {
            this.e();
            return false;
        }
        //
        iblockstate.c().a(this.a, blockpos, iblockstate, (EntityPlayer)this.b);
        boolean flag0 = this.a.g(blockpos);

        if (flag0) {
            iblockstate.c().d(this.a, blockpos, iblockstate);
        }

        return flag0;
    }

    public boolean b(BlockPos blockpos) {
        if (this.c.d() && this.b.bz() != null && this.b.bz().b() instanceof ItemSword) {
            return false;
        }
        else {
            IBlockState iblockstate = this.a.p(blockpos);
            TileEntity tileentity = this.a.s(blockpos);

            if (this.c.c()) {
                if (this.c == WorldSettings.GameType.SPECTATOR) {
            return false;
        }
                if (!this.b.cm()) {
                    ItemStack itemstack = this.b.bY();

                    if (itemstack == null) {
                        return false;
                    }
                    if (!itemstack.c(iblockstate.c())) {
                        return false;
                    }
                }
            }

            this.a.a(this.b, 2001, blockpos, Block.f(iblockstate));
            boolean flag0 = this.c(blockpos);

            if (this.d()) {
                this.b.a.a((Packet)(new S23PacketBlockChange(this.a, blockpos)));
            }
            else {
                ItemStack itemstack1 = this.b.bY();
                boolean flag1 = this.b.b(iblockstate.c());

                if (itemstack1 != null) {
                    itemstack1.a(this.a, iblockstate.c(), blockpos, this.b);
                    if (itemstack1.b == 0) {
                        this.b.bZ();
                    }
                }

                if (flag0 && flag1) {
                    iblockstate.c().a(this.a, (EntityPlayer)this.b, blockpos, iblockstate, tileentity);
                }
            }

            return flag0;
        }
    }

    // CanaryMod: ItemUseHook
    public boolean itemUsed(CanaryPlayer player, World world, ItemStack itemstack, CanaryBlock clicked) {
        ItemUseHook hook = (ItemUseHook) new ItemUseHook(player, itemstack.getCanaryItem(), clicked).call();
        if (hook.isCanceled()) {
            return false;
        }
        return this.a(player.getHandle(), world, itemstack);
    }
    //

    public boolean a(EntityPlayer entityplayer, World world, ItemStack itemstack) {
        if (this.c == WorldSettings.GameType.SPECTATOR) {
            return false;
        }
        else {
        int i0 = itemstack.b;
            int i1 = itemstack.i();
            ItemStack itemstack1 = itemstack.a(world, entityplayer);

            if (itemstack1 == itemstack && (itemstack1 == null || itemstack1.b == i0 && itemstack1.l() <= 0 && itemstack1.i() == i1)) {
                return false;
            }
            else {
                entityplayer.bg.a[entityplayer.bg.c] = itemstack1;
                if (this.d()) {
                    itemstack1.b = i0;
                    if (itemstack1.e()) {
                        itemstack1.b(i1);
                    }
                }

                if (itemstack1.b == 0) {
                    entityplayer.bg.a[entityplayer.bg.c] = null;
                }

                if (!entityplayer.bR()) {
                    ((EntityPlayerMP)entityplayer).a(entityplayer.bh);
                }

                return true;
            }
        }
    }

    public boolean a(EntityPlayer entityplayer, World world, ItemStack itemstack, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (this.c == WorldSettings.GameType.SPECTATOR) {
            TileEntity tileentity = world.s(blockpos);

            if (tileentity instanceof ILockableContainer) {
                Block block = world.p(blockpos).c();
                ILockableContainer ilockablecontainer = (ILockableContainer)tileentity;

                if (ilockablecontainer instanceof TileEntityChest && block instanceof BlockChest) {
                    ilockablecontainer = ((BlockChest)block).d(world, blockpos);
                }

                if (ilockablecontainer != null) {
                    entityplayer.a((IInventory)ilockablecontainer);
            return true;
        }
            }
            else if (tileentity instanceof IInventory) {
                entityplayer.a((IInventory)tileentity);
                return true;
            }
            return false;
        }
        else {
            if (!entityplayer.aw() || entityplayer.bz() == null) {
                IBlockState iblockstate = world.p(blockpos);

                if (iblockstate.c().a(world, blockpos, iblockstate, entityplayer, enumfacing, f0, f1, f2)) {
                    return true;
        }
            }

            if (itemstack == null) {
                return false;
            }
            else if (this.d()) {
                int i0 = itemstack.i();
                int i1 = itemstack.b;
                boolean flag0 = itemstack.a(entityplayer, world, blockpos, enumfacing, f0, f1, f2);

                itemstack.b(i0);
                itemstack.b = i1;
            return flag0;
            }
            else {
                return itemstack.a(entityplayer, world, blockpos, enumfacing, f0, f1, f2);
            }
        }
    }

    public void a(WorldServer worldserver) {
        this.a = worldserver;
    }
}
