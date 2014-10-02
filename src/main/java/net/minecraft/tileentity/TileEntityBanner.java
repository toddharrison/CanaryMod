package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryBanner;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import java.util.List;

public class TileEntityBanner extends TileEntity {

    private int a;
    private NBTTagList f;
    private boolean g;
    private List h;
    private List i;
    private String j;

    public TileEntityBanner() {
        this.complexBlock = new CanaryBanner(this);
    }

    public void a(ItemStack itemstack) {
        this.f = null;
        if (itemstack.n() && itemstack.o().b("BlockEntityTag", 10)) {
            NBTTagCompound nbttagcompound = itemstack.o().m("BlockEntityTag");

            if (nbttagcompound.c("Patterns")) {
                this.f = (NBTTagList)nbttagcompound.c("Patterns", 10).b();
            }

            if (nbttagcompound.b("Base", 99)) {
                this.a = nbttagcompound.f("Base");
            }
            else {
                this.a = itemstack.i() & 15;
            }
        }
        else {
            this.a = itemstack.i() & 15;
        }

        this.h = null;
        this.i = null;
        this.j = "";
        this.g = true;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Base", this.a);
        if (this.f != null) {
            nbttagcompound.a("Patterns", (NBTBase)this.f);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.f("Base");
        this.f = nbttagcompound.c("Patterns", 10);
        this.h = null;
        this.i = null;
        this.j = null;
        this.g = true;
    }

    public Packet x_() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.b(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.c, 6, nbttagcompound);
    }

    public int b() {
        return this.a;
    }

    public static int b(ItemStack itemstack) {
        NBTTagCompound nbttagcompound = itemstack.a("BlockEntityTag", false);

        return nbttagcompound != null && nbttagcompound.c("Base") ? nbttagcompound.f("Base") : itemstack.i();
    }

    public static int c(ItemStack itemstack) {
        NBTTagCompound nbttagcompound = itemstack.a("BlockEntityTag", false);

        return nbttagcompound != null && nbttagcompound.c("Patterns") ? nbttagcompound.c("Patterns", 10).c() : 0;
    }

    public static void e(ItemStack itemstack) {
        NBTTagCompound nbttagcompound = itemstack.a("BlockEntityTag", false);

        if (nbttagcompound != null && nbttagcompound.b("Patterns", 9)) {
            NBTTagList nbttaglist = nbttagcompound.c("Patterns", 10);

            if (nbttaglist.c() > 0) {
                nbttaglist.a(nbttaglist.c() - 1);
                if (nbttaglist.c_()) {
                    itemstack.o().o("BlockEntityTag");
                    if (itemstack.o().c_()) {
                        itemstack.d((NBTTagCompound)null);
                    }
                }
            }
        }
    }

    public static enum EnumBannerPattern {

        BASE("BASE", 0, "base", "b"),
        SQUARE_BOTTOM_LEFT("SQUARE_BOTTOM_LEFT", 1, "square_bottom_left", "bl", "   ", "   ", "#  "),
        SQUARE_BOTTOM_RIGHT("SQUARE_BOTTOM_RIGHT", 2, "square_bottom_right", "br", "   ", "   ", "  #"),
        SQUARE_TOP_LEFT("SQUARE_TOP_LEFT", 3, "square_top_left", "tl", "#  ", "   ", "   "),
        SQUARE_TOP_RIGHT("SQUARE_TOP_RIGHT", 4, "square_top_right", "tr", "  #", "   ", "   "),
        STRIPE_BOTTOM("STRIPE_BOTTOM", 5, "stripe_bottom", "bs", "   ", "   ", "###"),
        STRIPE_TOP("STRIPE_TOP", 6, "stripe_top", "ts", "###", "   ", "   "),
        STRIPE_LEFT("STRIPE_LEFT", 7, "stripe_left", "ls", "#  ", "#  ", "#  "),
        STRIPE_RIGHT("STRIPE_RIGHT", 8, "stripe_right", "rs", "  #", "  #", "  #"),
        STRIPE_CENTER("STRIPE_CENTER", 9, "stripe_center", "cs", " # ", " # ", " # "),
        STRIPE_MIDDLE("STRIPE_MIDDLE", 10, "stripe_middle", "ms", "   ", "###", "   "),
        STRIPE_DOWNRIGHT("STRIPE_DOWNRIGHT", 11, "stripe_downright", "drs", "#  ", " # ", "  #"),
        STRIPE_DOWNLEFT("STRIPE_DOWNLEFT", 12, "stripe_downleft", "dls", "  #", " # ", "#  "),
        STRIPE_SMALL("STRIPE_SMALL", 13, "small_stripes", "ss", "# #", "# #", "   "),
        CROSS("CROSS", 14, "cross", "cr", "# #", " # ", "# #"),
        STRAIGHT_CROSS("STRAIGHT_CROSS", 15, "straight_cross", "sc", " # ", "###", " # "),
        TRIANGLE_BOTTOM("TRIANGLE_BOTTOM", 16, "triangle_bottom", "bt", "   ", " # ", "# #"),
        TRIANGLE_TOP("TRIANGLE_TOP", 17, "triangle_top", "tt", "# #", " # ", "   "),
        TRIANGLES_BOTTOM("TRIANGLES_BOTTOM", 18, "triangles_bottom", "bts", "   ", "# #", " # "),
        TRIANGLES_TOP("TRIANGLES_TOP", 19, "triangles_top", "tts", " # ", "# #", "   "),
        DIAGONAL_LEFT("DIAGONAL_LEFT", 20, "diagonal_left", "ld", "## ", "#  ", "   "),
        DIAGONAL_RIGHT("DIAGONAL_RIGHT", 21, "diagonal_up_right", "rd", "   ", "  #", " ##"),
        DIAGONAL_LEFT_MIRROR("DIAGONAL_LEFT_MIRROR", 22, "diagonal_up_left", "lud", "   ", "#  ", "## "),
        DIAGONAL_RIGHT_MIRROR("DIAGONAL_RIGHT_MIRROR", 23, "diagonal_right", "rud", " ##", "  #", "   "),
        CIRCLE_MIDDLE("CIRCLE_MIDDLE", 24, "circle", "mc", "   ", " # ", "   "),
        RHOMBUS_MIDDLE("RHOMBUS_MIDDLE", 25, "rhombus", "mr", " # ", "# #", " # "),
        HALF_VERTICAL("HALF_VERTICAL", 26, "half_vertical", "vh", "## ", "## ", "## "),
        HALF_HORIZONTAL("HALF_HORIZONTAL", 27, "half_horizontal", "hh", "###", "###", "   "),
        HALF_VERTICAL_MIRROR("HALF_VERTICAL_MIRROR", 28, "half_vertical_right", "vhr", " ##", " ##", " ##"),
        HALF_HORIZONTAL_MIRROR("HALF_HORIZONTAL_MIRROR", 29, "half_horizontal_bottom", "hhb", "   ", "###", "###"),
        BORDER("BORDER", 30, "border", "bo", "###", "# #", "###"),
        CURLY_BORDER("CURLY_BORDER", 31, "curly_border", "cbo", new ItemStack(Blocks.bn)),
        CREEPER("CREEPER", 32, "creeper", "cre", new ItemStack(Items.bX, 1, 4)),
        GRADIENT("GRADIENT", 33, "gradient", "gra", "# #", " # ", " # "),
        GRADIENT_UP("GRADIENT_UP", 34, "gradient_up", "gru", " # ", " # ", "# #"),
        BRICKS("BRICKS", 35, "bricks", "bri", new ItemStack(Blocks.V)),
        SKULL("SKULL", 36, "skull", "sku", new ItemStack(Items.bX, 1, 1)),
        FLOWER("FLOWER", 37, "flower", "flo", new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.b())),
        MOJANG("MOJANG", 38, "mojang", "moj", new ItemStack(Items.ao, 1, 1));
        private String N;
        private String O;
        private String[] P;
        private ItemStack Q;

        private static final TileEntityBanner.EnumBannerPattern[] $VALUES = new TileEntityBanner.EnumBannerPattern[]{ BASE, SQUARE_BOTTOM_LEFT, SQUARE_BOTTOM_RIGHT, SQUARE_TOP_LEFT, SQUARE_TOP_RIGHT, STRIPE_BOTTOM, STRIPE_TOP, STRIPE_LEFT, STRIPE_RIGHT, STRIPE_CENTER, STRIPE_MIDDLE, STRIPE_DOWNRIGHT, STRIPE_DOWNLEFT, STRIPE_SMALL, CROSS, STRAIGHT_CROSS, TRIANGLE_BOTTOM, TRIANGLE_TOP, TRIANGLES_BOTTOM, TRIANGLES_TOP, DIAGONAL_LEFT, DIAGONAL_RIGHT, DIAGONAL_LEFT_MIRROR, DIAGONAL_RIGHT_MIRROR, CIRCLE_MIDDLE, RHOMBUS_MIDDLE, HALF_VERTICAL, HALF_HORIZONTAL, HALF_VERTICAL_MIRROR, HALF_HORIZONTAL_MIRROR, BORDER, CURLY_BORDER, CREEPER, GRADIENT, GRADIENT_UP, BRICKS, SKULL, FLOWER, MOJANG };

        private EnumBannerPattern(String p_i45670_1_, int p_i45670_2_, String p_i45670_3_, String p_i45670_4_) {
            this.P = new String[3];
            this.N = p_i45670_3_;
            this.O = p_i45670_4_;
        }

        private EnumBannerPattern(String p_i45671_1_, int p_i45671_2_, String p_i45671_3_, String p_i45671_4_, ItemStack p_i45671_5_) {
            this(p_i45671_1_, p_i45671_2_, p_i45671_3_, p_i45671_4_);
            this.Q = p_i45671_5_;
        }

        private EnumBannerPattern(String p_i45672_1_, int p_i45672_2_, String p_i45672_3_, String p_i45672_4_, String p_i45672_5_, String p_i45672_6_, String p_i45672_7_) {
            this(p_i45672_1_, p_i45672_2_, p_i45672_3_, p_i45672_4_);
            this.P[0] = p_i45672_5_;
            this.P[1] = p_i45672_6_;
            this.P[2] = p_i45672_7_;
        }

        public String b() {
            return this.O;
        }

        public String[] c() {
            return this.P;
        }

        public boolean d() {
            return this.Q != null || this.P[0] != null;
        }

        public boolean e() {
            return this.Q != null;
        }

        public ItemStack f() {
            return this.Q;
        }

    }
}
