package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryBeacon;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.AxisAlignedBB;

import java.util.Iterator;
import java.util.List;

public class TileEntityBeacon extends TileEntity implements IInventory {

    public static final Potion[][] a = new Potion[][]{ { Potion.c, Potion.e }, { Potion.m, Potion.j }, { Potion.g }, { Potion.l } };
    private boolean k;
    private int l = -1;
    private int m;
    private int n;
    private ItemStack o;
    private String p;

    public TileEntityBeacon() {
        this.complexBlock = new CanaryBeacon(this); // CanaryMod: wrap tile entity
    }

    public void h() {
        if (this.b.H() % 80L == 0L) {
            this.y();
            this.x();
        }

    }

    private void x() {
        if (this.k && this.l > 0 && !this.b.E && this.m > 0) {
            double d0 = (double) (this.l * 10 + 10);
            byte b0 = 0;

            if (this.l >= 4 && this.m == this.n) {
                b0 = 1;
            }

            AxisAlignedBB axisalignedbb = AxisAlignedBB.a().a((double) this.c, (double) this.d, (double) this.e, (double) (this.c + 1), (double) (this.d + 1), (double) (this.e + 1)).b(d0, d0, d0);

            axisalignedbb.e = (double) this.b.Q();
            List list = this.b.a(EntityPlayer.class, axisalignedbb);
            Iterator iterator = list.iterator();

            EntityPlayer entityplayer;

            while (iterator.hasNext()) {
                entityplayer = (EntityPlayer) iterator.next();
                entityplayer.c(new PotionEffect(this.m, 180, b0, true));
            }

            if (this.l >= 4 && this.m != this.n && this.n > 0) {
                iterator = list.iterator();

                while (iterator.hasNext()) {
                    entityplayer = (EntityPlayer) iterator.next();
                    entityplayer.c(new PotionEffect(this.n, 180, 0, true));
                }
            }
        }
    }

    private void y() {
        int i0 = this.l;

        if (!this.b.i(this.c, this.d + 1, this.e)) {
            this.k = false;
            this.l = 0;
        }
        else {
            this.k = true;
            this.l = 0;

            for (int i1 = 1; i1 <= 4; this.l = i1++) {
                int i2 = this.d - i1;

                if (i2 < 0) {
                    break;
                }

                boolean flag0 = true;

                for (int i3 = this.c - i1; i3 <= this.c + i1 && flag0; ++i3) {
                    for (int i4 = this.e - i1; i4 <= this.e + i1; ++i4) {
                        Block block = this.b.a(i3, i2, i4);

                        if (block != Blocks.bE && block != Blocks.R && block != Blocks.ah && block != Blocks.S) {
                            flag0 = false;
                            break;
                        }
                    }
                }

                if (!flag0) {
                    break;
                }
            }

            if (this.l == 0) {
                this.k = false;
            }
        }

        if (!this.b.E && this.l == 4 && i0 < this.l) {
            Iterator iterator = this.b.a(EntityPlayer.class, AxisAlignedBB.a().a((double) this.c, (double) this.d, (double) this.e, (double) this.c, (double) (this.d - 4), (double) this.e).b(10.0D, 5.0D, 10.0D)).iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                entityplayer.a((StatBase) AchievementList.K);
            }
        }

    }

    public int j() {
        return this.m;
    }

    public int k() {
        return this.n;
    }

    public int l() {
        return this.l;
    }

    public void d(int i0) {
        this.m = 0;

        for (int i1 = 0; i1 < this.l && i1 < 3; ++i1) {
            Potion[] apotion = a[i1];
            int i2 = apotion.length;

            for (int i3 = 0; i3 < i2; ++i3) {
                Potion potion = apotion[i3];

                if (potion.H == i0) {
                    this.m = i0;
                    return;
                }
            }
        }
    }

    public void e(int i0) {
        this.n = 0;
        if (this.l >= 4) {
            for (int i1 = 0; i1 < 4; ++i1) {
                Potion[] apotion = a[i1];
                int i2 = apotion.length;

                for (int i3 = 0; i3 < i2; ++i3) {
                    Potion potion = apotion[i3];

                    if (potion.H == i0) {
                        this.n = i0;
                        return;
                    }
                }
            }
        }
    }

    public Packet m() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.b(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.c, this.d, this.e, 3, nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.m = nbttagcompound.f("Primary");
        this.n = nbttagcompound.f("Secondary");
        this.l = nbttagcompound.f("Levels");
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Primary", this.m);
        nbttagcompound.a("Secondary", this.n);
        nbttagcompound.a("Levels", this.l);
    }

    public int a() {
        return 1;
    }

    public ItemStack a(int i0) {
        return i0 == 0 ? this.o : null;
    }

    public ItemStack a(int i0, int i1) {
        if (i0 == 0 && this.o != null) {
            if (i1 >= this.o.b) {
                ItemStack itemstack = this.o;

                this.o = null;
                return itemstack;
            }
            else {
                this.o.b -= i1;
                return new ItemStack(this.o.b(), i1, this.o.k());
            }
        }
        else {
            return null;
        }
    }

    public ItemStack a_(int i0) {
        if (i0 == 0 && this.o != null) {
            ItemStack itemstack = this.o;

            this.o = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        if (i0 == 0) {
            this.o = itemstack;
        }
    }

    public String b() {
        return this.k_() ? this.p : "container.beacon";
    }

    public boolean k_() {
        return this.p != null && this.p.length() > 0;
    }

    public void a(String s0) {
        this.p = s0;
    }

    public int d() {
        return 1;
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.b.o(this.c, this.d, this.e) != this ? false : entityplayer.e((double) this.c + 0.5D, (double) this.d + 0.5D, (double) this.e + 0.5D) <= 64.0D;
    }

    public void f() {
    }

    public void l_() {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return itemstack.b() == Items.bC || itemstack.b() == Items.i || itemstack.b() == Items.k || itemstack.b() == Items.j;
    }

    // CanaryMod
    public void setPrimaryEffectDirectly(int effect) {
        this.m = effect;
    }

    public void setSecondaryEffectDirectly(int effect) {
        this.n = effect;
    }

    public void setLevels(int levels) {
        this.e = levels;
    }

    public CanaryBeacon getCanaryBeacon() {
        return (CanaryBeacon) complexBlock;
    }
    //
}
