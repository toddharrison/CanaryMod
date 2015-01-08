package net.minecraft.entity.item;

import net.canarymod.api.entity.CanaryArmorStand;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotations;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class EntityArmorStand extends EntityLivingBase {

    private static final Rotations a = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations b = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations c = new Rotations(-10.0F, 0.0F, -10.0F);
    private static final Rotations d = new Rotations(-15.0F, 0.0F, 10.0F);
    private static final Rotations e = new Rotations(-1.0F, 0.0F, -1.0F);
    private static final Rotations f = new Rotations(1.0F, 0.0F, 1.0F);
    private final ItemStack[] g;
    private boolean h;
    private long i;
    private int bg;
    private Rotations bh;
    private Rotations bi;
    private Rotations bj;
    private Rotations bk;
    private Rotations bl;
    private Rotations bm;

    public EntityArmorStand(World world) {
        super(world);
        this.g = new ItemStack[5];
        this.bh = a;
        this.bi = b;
        this.bj = c;
        this.bk = d;
        this.bl = e;
        this.bm = f;
        this.b(true);
        this.T = this.p();
        this.a(0.5F, 1.975F);
        this.entity = new CanaryArmorStand(this); // CanaryMod: wrap entity
    }

    public EntityArmorStand(World world, double d0, double d1, double d2) {
        this(world);
        this.b(d0, d1, d2);
    }

    public boolean bL() {
        return super.bL() && !this.p();
    }

    protected void h() {
        super.h();
        this.ac.a(10, Byte.valueOf((byte)0));
        this.ac.a(11, a);
        this.ac.a(12, b);
        this.ac.a(13, c);
        this.ac.a(14, d);
        this.ac.a(15, e);
        this.ac.a(16, f);
    }

    public ItemStack bz() {
        return this.g[0];
    }

    public ItemStack p(int i0) {
        return this.g[i0];
    }

    public void c(int i0, ItemStack itemstack) {
        this.g[i0] = itemstack;
    }

    public ItemStack[] at() {
        return this.g;
    }

    public boolean d(int i0, ItemStack itemstack) {
        int i1;

        if (i0 == 99) {
            i1 = 0;
        }
        else {
            i1 = i0 - 100 + 1;
            if (i1 < 0 || i1 >= this.g.length) {
                return false;
            }
        }

        if (itemstack != null && EntityLiving.c(itemstack) != i1 && (i1 != 4 || !(itemstack.b() instanceof ItemBlock))) {
            return false;
        }
        else {
            this.c(i1, itemstack);
            return true;
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.g.length; ++i0) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            if (this.g[i0] != null) {
                this.g[i0].b(nbttagcompound1);
            }

            nbttaglist.a((NBTBase)nbttagcompound1);
        }

        nbttagcompound.a("Equipment", (NBTBase)nbttaglist);
        if (this.aM() && (this.aL() == null || this.aL().length() == 0)) {
            nbttagcompound.a("CustomNameVisible", this.aM());
        }

        nbttagcompound.a("Invisible", this.ay());
        nbttagcompound.a("Small", this.n());
        nbttagcompound.a("ShowArms", this.q());
        nbttagcompound.a("DisabledSlots", this.bg);
        nbttagcompound.a("NoGravity", this.p());
        nbttagcompound.a("NoBasePlate", this.r());
        nbttagcompound.a("Pose", (NBTBase)this.y());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("Equipment", 9)) {
            NBTTagList nbttaglist = nbttagcompound.c("Equipment", 10);

            for (int i0 = 0; i0 < this.g.length; ++i0) {
                this.g[i0] = ItemStack.a(nbttaglist.b(i0));
            }
        }

        this.e(nbttagcompound.n("Invisible"));
        this.a(nbttagcompound.n("Small"));
        this.k(nbttagcompound.n("ShowArms"));
        this.bg = nbttagcompound.f("DisabledSlots");
        this.j(nbttagcompound.n("NoGravity"));
        this.l(nbttagcompound.n("NoBasePlate"));
        this.T = this.p();
        NBTTagCompound nbttagcompound1 = nbttagcompound.m("Pose");

        this.h(nbttagcompound1);
    }

    private void h(NBTTagCompound nbttagcompound) {
        NBTTagList nbttaglist = nbttagcompound.c("Head", 5);

        if (nbttaglist.c() > 0) {
            this.a(new Rotations(nbttaglist));
        }
        else {
            this.a(a);
        }

        NBTTagList nbttaglist1 = nbttagcompound.c("Body", 5);

        if (nbttaglist1.c() > 0) {
            this.b(new Rotations(nbttaglist1));
        }
        else {
            this.b(b);
        }

        NBTTagList nbttaglist2 = nbttagcompound.c("LeftArm", 5);

        if (nbttaglist2.c() > 0) {
            this.c(new Rotations(nbttaglist2));
        }
        else {
            this.c(c);
        }

        NBTTagList nbttaglist3 = nbttagcompound.c("RightArm", 5);

        if (nbttaglist3.c() > 0) {
            this.d(new Rotations(nbttaglist3));
        }
        else {
            this.d(d);
        }

        NBTTagList nbttaglist4 = nbttagcompound.c("LeftLeg", 5);

        if (nbttaglist4.c() > 0) {
            this.e(new Rotations(nbttaglist4));
        }
        else {
            this.e(e);
        }

        NBTTagList nbttaglist5 = nbttagcompound.c("RightLeg", 5);

        if (nbttaglist5.c() > 0) {
            this.f(new Rotations(nbttaglist5));
        }
        else {
            this.f(f);
        }
    }

    private NBTTagCompound y() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        if (!a.equals(this.bh)) {
            nbttagcompound.a("Head", (NBTBase)this.bh.a());
        }

        if (!b.equals(this.bi)) {
            nbttagcompound.a("Body", (NBTBase)this.bi.a());
        }

        if (!c.equals(this.bj)) {
            nbttagcompound.a("LeftArm", (NBTBase)this.bj.a());
        }

        if (!d.equals(this.bk)) {
            nbttagcompound.a("RightArm", (NBTBase)this.bk.a());
        }

        if (!e.equals(this.bl)) {
            nbttagcompound.a("LeftLeg", (NBTBase)this.bl.a());
        }

        if (!f.equals(this.bm)) {
            nbttagcompound.a("RightLeg", (NBTBase)this.bm.a());
        }

        return nbttagcompound;
    }

    public boolean ae() {
        return false;
    }

    protected void s(Entity entity) {
    }

    protected void bK() {
        List list = this.o.b((Entity)this, this.aQ());

        if (list != null && !list.isEmpty()) {
            for (int i0 = 0; i0 < list.size(); ++i0) {
                Entity entity = (Entity)list.get(i0);

                if (entity instanceof EntityMinecart && ((EntityMinecart)entity).s() == EntityMinecart.EnumMinecartType.RIDEABLE && this.h(entity) <= 0.2D) {
                    entity.i(this);
                }
            }
        }
    }

    public boolean a(EntityPlayer entityplayer, Vec3 vec3) {
        if (!this.o.D && !entityplayer.v()) {
            byte b0 = 0;
            ItemStack itemstack = entityplayer.bY();
            boolean flag0 = itemstack != null;

            if (flag0 && itemstack.b() instanceof ItemArmor) {
                ItemArmor itemarmor = (ItemArmor)itemstack.b();

                if (itemarmor.b == 3) {
                    b0 = 1;
                }
                else if (itemarmor.b == 2) {
                    b0 = 2;
                }
                else if (itemarmor.b == 1) {
                    b0 = 3;
                }
                else if (itemarmor.b == 0) {
                    b0 = 4;
                }
            }

            if (flag0 && (itemstack.b() == Items.bX || itemstack.b() == Item.a(Blocks.aU))) {
                b0 = 4;
            }

            double d0 = 0.1D;
            double d1 = 0.9D;
            double d2 = 0.4D;
            double d3 = 1.6D;
            byte b1 = 0;
            boolean flag1 = this.n();
            double d4 = flag1 ? vec3.b * 2.0D : vec3.b;

            if (d4 >= 0.1D && d4 < 0.1D + (flag1 ? 0.8D : 0.45D) && this.g[1] != null) {
                b1 = 1;
            }
            else if (d4 >= 0.9D + (flag1 ? 0.3D : 0.0D) && d4 < 0.9D + (flag1 ? 1.0D : 0.7D) && this.g[3] != null) {
                b1 = 3;
            }
            else if (d4 >= 0.4D && d4 < 0.4D + (flag1 ? 1.0D : 0.8D) && this.g[2] != null) {
                b1 = 2;
            }
            else if (d4 >= 1.6D && this.g[4] != null) {
                b1 = 4;
            }

            boolean flag2 = this.g[b1] != null;

            if ((this.bg & 1 << b1) != 0 || (this.bg & 1 << b0) != 0) {
                b1 = b0;
                if ((this.bg & 1 << b0) != 0) {
                    if ((this.bg & 1) != 0) {
                        return true;
                    }

                    b1 = 0;
                }
            }

            if (flag0 && b0 == 0 && !this.q()) {
                return true;
            }
            else {
                if (flag0) {
                    this.a(entityplayer, b0);
                }
                else if (flag2) {
                    this.a(entityplayer, b1);
                }

                return true;
            }
        }
        else {
            return true;
        }
    }

    private void a(EntityPlayer entityplayer, int i0) {
        ItemStack itemstack = this.g[i0];

        if (itemstack == null || (this.bg & 1 << i0 + 8) == 0) {
            if (itemstack != null || (this.bg & 1 << i0 + 16) == 0) {
                int i1 = entityplayer.bg.c;
                ItemStack itemstack1 = entityplayer.bg.a(i1);
                ItemStack itemstack2;

                if (entityplayer.by.d && (itemstack == null || itemstack.b() == Item.a(Blocks.a)) && itemstack1 != null) {
                    itemstack2 = itemstack1.k();
                    itemstack2.b = 1;
                    this.c(i0, itemstack2);
                }
                else if (itemstack1 != null && itemstack1.b > 1) {
                    if (itemstack == null) {
                        itemstack2 = itemstack1.k();
                        itemstack2.b = 1;
                        this.c(i0, itemstack2);
                        --itemstack1.b;
                    }
                }
                else {
                    this.c(i0, itemstack1);
                    entityplayer.bg.a(i1, itemstack);
                }
            }
        }
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (!this.o.D && !this.h) {
            if (DamageSource.j.equals(damagesource)) {
                this.J();
                return false;
            }
            else if (this.b(damagesource)) {
                return false;
            }
            else if (damagesource.c()) {
                this.C();
                this.J();
                return false;
            }
            else if (DamageSource.a.equals(damagesource)) {
                if (!this.au()) {
                    this.e(5);
                }
                else {
                    this.a(0.15F);
                }

                return false;
            }
            else if (DamageSource.c.equals(damagesource) && this.bm() > 0.5F) {
                this.a(4.0F);
                return false;
            }
            else {
                boolean flag0 = "arrow".equals(damagesource.p());
                boolean flag1 = "player".equals(damagesource.p());

                if (!flag1 && !flag0) {
                    return false;
                }
                else {
                    if (damagesource.i() instanceof EntityArrow) {
                        damagesource.i().J();
                    }

                    if (damagesource.j() instanceof EntityPlayer && !((EntityPlayer)damagesource.j()).by.e) {
                        return false;
                    }
                    else if (damagesource.u()) {
                        this.z();
                        this.J();
                        return false;
                    }
                    else {
                        long i0 = this.o.K();

                        if (i0 - this.i > 5L && !flag0) {
                            this.i = i0;
                        }
                        else {
                            this.A();
                            this.z();
                            this.J();
                        }

                        return false;
                    }
                }
            }
        }
        else {
            return false;
        }
    }

    private void z() {
        if (this.o instanceof WorldServer) {
            ((WorldServer)this.o).a(EnumParticleTypes.BLOCK_DUST, this.s, this.t + (double)this.K / 1.5D, this.u, 10, (double)(this.J / 4.0F), (double)(this.K / 4.0F), (double)(this.J / 4.0F), 0.05D, new int[]{ Block.f(Blocks.f.P()) });
        }
    }

    private void a(float f0) {
        float f1 = this.bm();

        f1 -= f0;
        if (f1 <= 0.5F) {
            this.C();
            this.J();
        }
        else {
            this.h(f1);
        }
    }

    private void A() {
        Block.a(this.o, new BlockPos(this), new ItemStack(Items.cj));
        this.C();
    }

    private void C() {
        for (int i0 = 0; i0 < this.g.length; ++i0) {
            if (this.g[i0] != null && this.g[i0].b > 0) {
                if (this.g[i0] != null) {
                    Block.a(this.o, (new BlockPos(this)).a(), this.g[i0]);
                }

                this.g[i0] = null;
            }
        }
    }

    protected float h(float f0, float f1) {
        this.aH = this.A;
        this.aG = this.y;
        return 0.0F;
    }

    public float aR() {
        return this.i_() ? this.K * 0.5F : this.K * 0.9F;
    }

    public void g(float f0, float f1) {
        if (!this.p()) {
            super.g(f0, f1);
        }
    }

    public void s_() {
        super.s_();
        Rotations rotations = this.ac.h(11);

        if (!this.bh.equals(rotations)) {
            this.a(rotations);
        }

        Rotations rotations1 = this.ac.h(12);

        if (!this.bi.equals(rotations1)) {
            this.b(rotations1);
        }

        Rotations rotations2 = this.ac.h(13);

        if (!this.bj.equals(rotations2)) {
            this.c(rotations2);
        }

        Rotations rotations3 = this.ac.h(14);

        if (!this.bk.equals(rotations3)) {
            this.d(rotations3);
        }

        Rotations rotations4 = this.ac.h(15);

        if (!this.bl.equals(rotations4)) {
            this.e(rotations4);
        }

        Rotations rotations5 = this.ac.h(16);

        if (!this.bm.equals(rotations5)) {
            this.f(rotations5);
        }
    }

    protected void B() {
        this.e(this.h);
    }

    public void e(boolean flag0) {
        this.h = flag0;
        super.e(flag0);
    }

    public boolean i_() {
        return this.n();
    }

    public void G() {
        this.J();
    }

    public boolean aV() {
        return this.ay();
    }

    private void a(boolean flag0) {
        byte b0 = this.ac.a(10);

        if (flag0) {
            b0 = (byte)(b0 | 1);
        }
        else {
            b0 &= -2;
        }

        this.ac.b(10, Byte.valueOf(b0));
    }

    public boolean n() {
        return (this.ac.a(10) & 1) != 0;
    }

    private void j(boolean flag0) {
        byte b0 = this.ac.a(10);

        if (flag0) {
            b0 = (byte)(b0 | 2);
        }
        else {
            b0 &= -3;
        }

        this.ac.b(10, Byte.valueOf(b0));
    }

    public boolean p() {
        return (this.ac.a(10) & 2) != 0;
    }

    private void k(boolean flag0) {
        byte b0 = this.ac.a(10);

        if (flag0) {
            b0 = (byte)(b0 | 4);
        }
        else {
            b0 &= -5;
        }

        this.ac.b(10, Byte.valueOf(b0));
    }

    public boolean q() {
        return (this.ac.a(10) & 4) != 0;
    }

    private void l(boolean flag0) {
        byte b0 = this.ac.a(10);

        if (flag0) {
            b0 = (byte)(b0 | 8);
        }
        else {
            b0 &= -9;
        }

        this.ac.b(10, Byte.valueOf(b0));
    }

    public boolean r() {
        return (this.ac.a(10) & 8) != 0;
    }

    public void a(Rotations rotations) {
        this.bh = rotations;
        this.ac.b(11, rotations);
    }

    public void b(Rotations rotations) {
        this.bi = rotations;
        this.ac.b(12, rotations);
    }

    public void c(Rotations rotations) {
        this.bj = rotations;
        this.ac.b(13, rotations);
    }

    public void d(Rotations rotations) {
        this.bk = rotations;
        this.ac.b(14, rotations);
    }

    public void e(Rotations rotations) {
        this.bl = rotations;
        this.ac.b(15, rotations);
    }

    public void f(Rotations rotations) {
        this.bm = rotations;
        this.ac.b(16, rotations);
    }

    public Rotations s() {
        return this.bh;
    }

    public Rotations t() {
        return this.bi;
    }

    /* CanaryMod: Start helper methods */
    public final void setSmall(boolean small){
        this.a(small);  // 10 (& 0)
    }

    public final void setShowArms(boolean arms){
        this.k(arms);  // 10 & 4
    }

    public final int getDisabledSlots(){
        return this.bg;
    }

    public final void setDisabledSlots(int offsets){
        this.bg = offsets;
    }

    public final void setNoBaseplate(boolean nobaseplate){
        this.l(nobaseplate); // 10 & 8
    }

    public final void setNoGravity(boolean nogravity){
        this.j(nogravity); // 10 & 2
    }

    public Rotations getLeftArmRotations(){
        return this.bj;
    }

    public Rotations getRightArmRotations(){
        return this.bk;
    }

    public Rotations getLeftLegRotations(){
        return this.bl;
    }

    public Rotations getRightLegRotations(){
        return this.bm;
    }
    /* CanaryMod: End */
}
