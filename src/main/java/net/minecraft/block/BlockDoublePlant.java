package net.minecraft.block;


import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;


public class BlockDoublePlant extends BlockBush implements IGrowable {

    public static final PropertyEnum a = PropertyEnum.a("variant", EnumPlantType.class);
    public static final PropertyEnum b = PropertyEnum.a("half", EnumBlockHalf.class);

    public BlockDoublePlant() {
        super(Material.l);
        this.j(this.L.b().a(a, EnumPlantType.SUNFLOWER).a(b, EnumBlockHalf.LOWER));
        this.c(0.0F);
        this.a(h);
        this.c("doublePlant");
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public EnumPlantType e(IBlockAccess iblockaccess, BlockPos blockpos) {
        IBlockState iblockstate = iblockaccess.p(blockpos);

        if (iblockstate.c() == this) {
            iblockstate = this.a(iblockstate, iblockaccess, blockpos);
            return (EnumPlantType) iblockstate.b(a);
        } else {
            return EnumPlantType.FERN;
        }
    }

    public boolean c(World world, BlockPos blockpos) {
        return super.c(world, blockpos) && world.d(blockpos.a());
    }

    public boolean f(World world, BlockPos blockpos) {
        IBlockState iblockstate = world.p(blockpos);

        if (iblockstate.c() != this) {
            return true;
        } else {
            EnumPlantType blockdoubleplant_enumplanttype = (EnumPlantType) this.a(iblockstate, world, blockpos).b(a);

            return blockdoubleplant_enumplanttype == EnumPlantType.FERN || blockdoubleplant_enumplanttype == EnumPlantType.GRASS;
        }
    }

    protected void e(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (!this.f(world, blockpos, iblockstate)) {
            boolean flag0 = iblockstate.b(b) == EnumBlockHalf.UPPER;
            BlockPos blockpos1 = flag0 ? blockpos : blockpos.a();
            BlockPos blockpos2 = flag0 ? blockpos.b() : blockpos;
            Object object = flag0 ? this : world.p(blockpos1).c();
            Object object1 = flag0 ? world.p(blockpos2).c() : this;

            if (object == this) {
                world.a(blockpos1, Blocks.a.P(), 3);
            }

            if (object1 == this) {
                world.a(blockpos2, Blocks.a.P(), 3);
                if (!flag0) {
                    this.b(world, blockpos2, iblockstate, 0);
                }
            }

        }
    }

    public boolean f(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (iblockstate.b(b) == EnumBlockHalf.UPPER) {
            return world.p(blockpos.b()).c() == this;
        } else {
            IBlockState iblockstate1 = world.p(blockpos.a());

            return iblockstate1.c() == this && super.f(world, blockpos, iblockstate1);
        }
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        if (iblockstate.b(b) == EnumBlockHalf.UPPER) {
            return null;
        } else {
            EnumPlantType blockdoubleplant_enumplanttype = (EnumPlantType) iblockstate.b(a);

            return blockdoubleplant_enumplanttype == EnumPlantType.FERN ? null : (blockdoubleplant_enumplanttype == EnumPlantType.GRASS ? (random.nextInt(8) == 0 ? Items.N : null) : Item.a((Block) this));
        }
    }

    public int a(IBlockState iblockstate) {
        return iblockstate.b(b) != EnumBlockHalf.UPPER && iblockstate.b(a) != EnumPlantType.GRASS ? ((EnumPlantType) iblockstate.b(a)).a() : 0;
    }

    public void a(World world, BlockPos blockpos, EnumPlantType blockdoubleplant_enumplanttype, int i0) {
        world.a(blockpos, this.P().a(b, EnumBlockHalf.LOWER).a(a, blockdoubleplant_enumplanttype), i0);
        world.a(blockpos.a(), this.P().a(b, EnumBlockHalf.UPPER), i0);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        world.a(blockpos.a(), this.P().a(b, EnumBlockHalf.UPPER), 2);
    }

    public void a(World world, EntityPlayer entityplayer, BlockPos blockpos, IBlockState iblockstate, TileEntity tileentity) {
        if (world.D || entityplayer.bY() == null || entityplayer.bY().b() != Items.be || iblockstate.b(b) != EnumBlockHalf.LOWER || !this.b(world, blockpos, iblockstate, entityplayer)) {
            super.a(world, entityplayer, blockpos, iblockstate, tileentity);
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer) {
        if (iblockstate.b(b) == EnumBlockHalf.UPPER) {
            if (world.p(blockpos.b()).c() == this) {
                if (!entityplayer.by.d) {
                    IBlockState iblockstate1 = world.p(blockpos.b());
                    EnumPlantType blockdoubleplant_enumplanttype = (EnumPlantType) iblockstate1.b(a);

                    if (blockdoubleplant_enumplanttype != EnumPlantType.FERN && blockdoubleplant_enumplanttype != EnumPlantType.GRASS) {
                        world.b(blockpos.b(), true);
                    } else if (!world.D) {
                        if (entityplayer.bY() != null && entityplayer.bY().b() == Items.be) {
                            this.b(world, blockpos, iblockstate1, entityplayer);
                            world.g(blockpos.b());
                        } else {
                            world.b(blockpos.b(), true);
                        }
                    } else {
                        world.g(blockpos.b());
                    }
                } else {
                    world.g(blockpos.b());
                }
            }
        } else if (entityplayer.by.d && world.p(blockpos.a()).c() == this) {
            world.a(blockpos.a(), Blocks.a.P(), 2);
        }

        super.a(world, blockpos, iblockstate, entityplayer);
    }

    private boolean b(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer) {
        EnumPlantType blockdoubleplant_enumplanttype = (EnumPlantType) iblockstate.b(a);

        if (blockdoubleplant_enumplanttype != EnumPlantType.FERN && blockdoubleplant_enumplanttype != EnumPlantType.GRASS) {
            return false;
        } else {
            entityplayer.b(StatList.H[Block.a((Block) this)]);
            int i0 = (blockdoubleplant_enumplanttype == EnumPlantType.GRASS ? BlockTallGrass.EnumType.GRASS : BlockTallGrass.EnumType.FERN).a();

            a(world, blockpos, new ItemStack(Blocks.H, 2, i0));
            return true;
        }
    }

    public int j(World world, BlockPos blockpos) {
        return this.e(world, blockpos).a();
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, boolean flag0) {
        EnumPlantType blockdoubleplant_enumplanttype = this.e(world, blockpos);

        return blockdoubleplant_enumplanttype != EnumPlantType.GRASS && blockdoubleplant_enumplanttype != EnumPlantType.FERN;
    }

    public boolean a(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        return true;
    }

    public void b(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        a(world, blockpos, new ItemStack(this, 1, this.e(world, blockpos).a()));
    }

    public IBlockState a(int i0) {
        return (i0 & 8) > 0 ? this.P().a(b, EnumBlockHalf.UPPER) : this.P().a(b, EnumBlockHalf.LOWER).a(a, EnumPlantType.a(i0 & 7));
    }

    public IBlockState a(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
        if (iblockstate.b(b) == EnumBlockHalf.UPPER) {
            IBlockState iblockstate1 = iblockaccess.p(blockpos.b());

            if (iblockstate1.c() == this) {
                iblockstate = iblockstate.a(a, iblockstate1.b(a));
            }
        }

        return iblockstate;
    }

    public int c(IBlockState iblockstate) {
        return iblockstate.b(b) == EnumBlockHalf.UPPER ? 8 : ((EnumPlantType) iblockstate.b(a)).a();
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[] { b, a});
    }

    public static enum EnumBlockHalf implements IStringSerializable { // CanaryMod: package private => public

        UPPER("UPPER", 0), LOWER("LOWER", 1);

        private static final EnumBlockHalf[] $VALUES = new EnumBlockHalf[] { UPPER, LOWER};

        private EnumBlockHalf(String p_i45724_1_, int p_i45724_2_) {}

        public String toString() {
            return this.l();
        }

        public String l() {
            return this == UPPER ? "upper" : "lower";
        }

    }


    public static enum EnumPlantType implements IStringSerializable {

        SUNFLOWER("SUNFLOWER", 0, 0, "sunflower"), SYRINGA("SYRINGA", 1, 1, "syringa"), GRASS("GRASS", 2, 2, "double_grass", "grass"), FERN("FERN", 3, 3, "double_fern", "fern"), ROSE("ROSE", 4, 4, "double_rose", "rose"), PAEONIA("PAEONIA", 5, 5, "paeonia");
        private static final EnumPlantType[] g = new EnumPlantType[values().length];
        private final int h;
        private final String i;
        private final String j;

        private static final EnumPlantType[] $VALUES = new EnumPlantType[] { SUNFLOWER, SYRINGA, GRASS, FERN, ROSE, PAEONIA};

        private EnumPlantType(String p_i45722_1_, int p_i45722_2_, int p_i45722_3_, String p_i45722_4_) {
            this(p_i45722_1_, p_i45722_2_, p_i45722_3_, p_i45722_4_, p_i45722_4_);
        }

        private EnumPlantType(String p_i45723_1_, int p_i45723_2_, int p_i45723_3_, String p_i45723_4_, String p_i45723_5_) {
            this.h = p_i45723_3_;
            this.i = p_i45723_4_;
            this.j = p_i45723_5_;
        }

        public int a() {
            return this.h;
        }

        public String toString() {
            return this.i;
        }

        public static EnumPlantType a(int p_a_0_) {
            if (p_a_0_ < 0 || p_a_0_ >= g.length) {
                p_a_0_ = 0;
            }

            return g[p_a_0_];
        }

        public String l() {
            return this.i;
        }

        public String c() {
            return this.j;
        }

        static {
            EnumPlantType[] ablockdoubleplant_enumplanttype = values();
            int i0 = ablockdoubleplant_enumplanttype.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EnumPlantType blockdoubleplant_enumplanttype = ablockdoubleplant_enumplanttype[i1];

                g[blockdoubleplant_enumplanttype.a()] = blockdoubleplant_enumplanttype;
            }

        }
    }
}
