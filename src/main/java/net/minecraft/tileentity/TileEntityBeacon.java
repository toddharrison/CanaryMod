package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import net.canarymod.api.world.blocks.CanaryBeacon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TileEntityBeacon extends TileEntityLockable implements IUpdatePlayerListBox, IInventory {

    public static final Potion[][] a = new Potion[][]{ { Potion.c, Potion.e }, { Potion.m, Potion.j }, { Potion.g }, { Potion.l } };
    private final List f = Lists.newArrayList();
    private boolean i;
    private int j = -1;
    private int k;
    private int l;
    private ItemStack m;
    private String n;

    public TileEntityBeacon() {
        this.complexBlock = new CanaryBeacon(this); // CanaryMod: wrap tile entity
    }

    public void c() {
        if (this.b.K() % 80L == 0L) {
            this.m();
        }
    }

    public void m() {
        this.B();
        this.A();
    }

    private void A() {
        if (this.i && this.j > 0 && !this.b.D && this.k > 0) {
            double d0 = (double)(this.j * 10 + 10);
            byte b0 = 0;

            if (this.j >= 4 && this.k == this.l) {
                b0 = 1;
            }

            int i0 = this.c.n();
            int i1 = this.c.o();
            int i2 = this.c.p();
            AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double)i0, (double)i1, (double)i2, (double)(i0 + 1), (double)(i1 + 1), (double)(i2 + 1))).b(d0, d0, d0).a(0.0D, (double)this.b.U(), 0.0D);
            List list = this.b.a(EntityPlayer.class, axisalignedbb);
            Iterator iterator = list.iterator();

            EntityPlayer entityplayer;

            while (iterator.hasNext()) {
                entityplayer = (EntityPlayer)iterator.next();
                entityplayer.c(new PotionEffect(this.k, 180, b0, true, true));
            }

            if (this.j >= 4 && this.k != this.l && this.l > 0) {
                iterator = list.iterator();

                while (iterator.hasNext()) {
                    entityplayer = (EntityPlayer)iterator.next();
                    entityplayer.c(new PotionEffect(this.l, 180, 0, true, true));
                }
            }
        }
    }

    private void B() {
        int i0 = this.j;
        int i1 = this.c.n();
        int i2 = this.c.o();
        int i3 = this.c.p();

        this.j = 0;
        this.f.clear();
        this.i = true;
        TileEntityBeacon.BeamSegment tileentitybeacon_beamsegment = new TileEntityBeacon.BeamSegment(EntitySheep.a(EnumDyeColor.WHITE));

        this.f.add(tileentitybeacon_beamsegment);
        boolean flag0 = true;

        int i4;

        for (i4 = i2 + 1; i4 < this.b.V(); ++i4) {
            BlockPos blockpos = new BlockPos(i1, i4, i3);
            IBlockState iblockstate = this.b.p(blockpos);
            float[] afloat;

            if (iblockstate.c() == Blocks.cG) {
                afloat = EntitySheep.a((EnumDyeColor)iblockstate.b(BlockStainedGlass.a));
            }
            else {
                if (iblockstate.c() != Blocks.cH) {
                    if (iblockstate.c().n() >= 15) {
                        this.i = false;
                        this.f.clear();
                        break;
                    }
                    tileentitybeacon_beamsegment.a();
                    continue;
                }
                afloat = EntitySheep.a((EnumDyeColor)iblockstate.b(BlockStainedGlassPane.a));
            }

            if (!flag0) {
                afloat = new float[]{ (tileentitybeacon_beamsegment.b()[0] + afloat[0]) / 2.0F, (tileentitybeacon_beamsegment.b()[1] + afloat[1]) / 2.0F, (tileentitybeacon_beamsegment.b()[2] + afloat[2]) / 2.0F };
            }
            if (Arrays.equals(afloat, tileentitybeacon_beamsegment.b())) {
                tileentitybeacon_beamsegment.a();
            }
            else {
                tileentitybeacon_beamsegment = new TileEntityBeacon.BeamSegment(afloat);
                this.f.add(tileentitybeacon_beamsegment);
            }

            flag0 = false;
        }
        if (this.i) {
            for (i4 = 1; i4 <= 4; this.j = i4++) {
                int i5 = i2 - i4;

                if (i5 < 0) {
                    break;
                }

                boolean flag1 = true;

                for (int i6 = i1 - i4; i6 <= i1 + i4 && flag1; ++i6) {
                    for (int i7 = i3 - i4; i7 <= i3 + i4; ++i7) {
                        Block block = this.b.p(new BlockPos(i6, i5, i7)).c();

                        if (block != Blocks.bT && block != Blocks.R && block != Blocks.ah && block != Blocks.S) {
                            flag1 = false;
                            break;
                        }
                    }
                }

                if (!flag1) {
                    break;
                }
            }

            if (this.j == 0) {
                this.i = false;
            }
        }

        if (!this.b.D && this.j == 4 && i0 < this.j) {
            Iterator iterator = this.b.a(EntityPlayer.class, (new AxisAlignedBB((double)i1, (double)i2, (double)i3, (double)i1, (double)(i2 - 4), (double)i3)).b(10.0D, 5.0D, 10.0D)).iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();

                entityplayer.b((StatBase)AchievementList.K);
            }
        }
    }

    public Packet x_() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.b(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.c, 3, nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.k = nbttagcompound.f("Primary");
        this.l = nbttagcompound.f("Secondary");
        this.j = nbttagcompound.f("Levels");
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Primary", this.k);
        nbttagcompound.a("Secondary", this.l);
        nbttagcompound.a("Levels", this.j);
    }

    public int n_() {
        return 1;
    }

    public ItemStack a(int i0) {
        return i0 == 0 ? this.m : null;
    }

    public ItemStack a(int i0, int i1) {
        if (i0 == 0 && this.m != null) {
            if (i1 >= this.m.b) {
                ItemStack itemstack = this.m;

                this.m = null;
                return itemstack;
            }
            else {
                this.m.b -= i1;
                return new ItemStack(this.m.b(), i1, this.m.i());
            }
        }
        else {
            return null;
        }
    }

    public ItemStack b(int i0) {
        if (i0 == 0 && this.m != null) {
            ItemStack itemstack = this.m;

            this.m = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        if (i0 == 0) {
            this.m = itemstack;
        }
    }

    public String d_() {
        return this.k_() ? this.n : "container.beacon";
    }

    public boolean k_() {
        return this.n != null && this.n.length() > 0;
    }

    public void a(String s0) {
        this.n = s0;
    }

    public int p_() {
        return 1;
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.b.s(this.c) != this ? false : entityplayer.e((double)this.c.n() + 0.5D, (double)this.c.o() + 0.5D, (double)this.c.p() + 0.5D) <= 64.0D;
    }

    public void b(EntityPlayer entityplayer) {
    }

    public void c(EntityPlayer entityplayer) {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return itemstack.b() == Items.bO || itemstack.b() == Items.i || itemstack.b() == Items.k || itemstack.b() == Items.j;
    }

    public String k() {
        return "minecraft:beacon";
    }

    public Container a(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
        return new ContainerBeacon(inventoryplayer, this);
    }

    public int a_(int i0) {
        switch (i0) {
            case 0:
                return this.j;
            case 1:
                return this.k;

            case 2:
                return this.l;

            default:
                return 0;
        }
    }

    public void b(int i0, int i1) {
        switch (i0) {
            case 0:
                this.j = i1;
                break;

            case 1:
                this.k = i1;
                break;

            case 2:
                this.l = i1;
        }
    }

    public int g() {
        return 3;
    }

    public void l() {
        this.m = null;
    }

    public boolean c(int i0, int i1) {
        if (i0 == 1) {
            this.m();
            return true;
        }
        else {
            return super.c(i0, i1);
        }
    }

    public static class BeamSegment {

        private final float[] a;
        private int b;

        public BeamSegment(float[] p_i45669_1_) {
            this.a = p_i45669_1_;
            this.b = 1;
        }

        protected void a() {
            ++this.b;
        }

        public float[] b() {
            return this.a;
        }
    }

    // CanaryMod
    public void setPrimaryEffectDirectly(int effect) {
        this.k = effect;
    }

    public void setSecondaryEffectDirectly(int effect) {
        this.l = effect;
    }

    public void setLevels(int levels) {
        this.j = levels;
    }

    public CanaryBeacon getCanaryBeacon() {
        return (CanaryBeacon)complexBlock;
    }
    //
}
