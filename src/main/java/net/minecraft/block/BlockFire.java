package net.minecraft.block;

import com.google.common.collect.Maps;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

import java.util.Map;
import java.util.Random;

public class BlockFire extends Block {

    public static final PropertyInteger a = PropertyInteger.a("age", 0, 15);
    public static final PropertyBool b = PropertyBool.a("flip");
    public static final PropertyBool M = PropertyBool.a("alt");
    public static final PropertyBool N = PropertyBool.a("north");
    public static final PropertyBool O = PropertyBool.a("east");
    public static final PropertyBool P = PropertyBool.a("south");
    public static final PropertyBool Q = PropertyBool.a("west");
    public static final PropertyInteger R = PropertyInteger.a("upper", 0, 2);
    private final Map S = Maps.newIdentityHashMap();
    private final Map T = Maps.newIdentityHashMap();

    protected BlockFire() {
        super(Material.o);
        this.j(this.L.b().a(a, Integer.valueOf(0)).a(b, Boolean.valueOf(false)).a(M, Boolean.valueOf(false)).a(N, Boolean.valueOf(false)).a(O, Boolean.valueOf(false)).a(P, Boolean.valueOf(false)).a(Q, Boolean.valueOf(false)).a(R, Integer.valueOf(0)));
        this.a(true);
    }

    public static void j() {
        Blocks.ab.a(Blocks.f, 5, 20);
        Blocks.ab.a(Blocks.bL, 5, 20);
        Blocks.ab.a(Blocks.bM, 5, 20);
        Blocks.ab.a(Blocks.bo, 5, 20);
        Blocks.ab.a(Blocks.bp, 5, 20);
        Blocks.ab.a(Blocks.bq, 5, 20);
        Blocks.ab.a(Blocks.br, 5, 20);
        Blocks.ab.a(Blocks.bs, 5, 20);
        Blocks.ab.a(Blocks.bt, 5, 20);
        Blocks.ab.a(Blocks.aO, 5, 20);
        Blocks.ab.a(Blocks.aP, 5, 20);
        Blocks.ab.a(Blocks.aQ, 5, 20);
        Blocks.ab.a(Blocks.aR, 5, 20);
        Blocks.ab.a(Blocks.aS, 5, 20);
        Blocks.ab.a(Blocks.aT, 5, 20);
        Blocks.ab.a(Blocks.ad, 5, 20);
        Blocks.ab.a(Blocks.bV, 5, 20);
        Blocks.ab.a(Blocks.bU, 5, 20);
        Blocks.ab.a(Blocks.bW, 5, 20);
        Blocks.ab.a(Blocks.r, 5, 5);
        Blocks.ab.a(Blocks.s, 5, 5);
        Blocks.ab.a(Blocks.t, 30, 60);
        Blocks.ab.a(Blocks.u, 30, 60);
        Blocks.ab.a(Blocks.X, 30, 20);
        Blocks.ab.a(Blocks.W, 15, 100);
        Blocks.ab.a(Blocks.H, 60, 100);
        Blocks.ab.a(Blocks.cF, 60, 100);
        Blocks.ab.a(Blocks.N, 60, 100);
        Blocks.ab.a(Blocks.O, 60, 100);
        Blocks.ab.a(Blocks.I, 60, 100);
        Blocks.ab.a(Blocks.L, 30, 60);
        Blocks.ab.a(Blocks.bn, 15, 100);
        Blocks.ab.a(Blocks.cA, 5, 5);
        Blocks.ab.a(Blocks.cx, 60, 20);
        Blocks.ab.a(Blocks.cy, 60, 20);
    }

    public IBlockState a(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
        int i0 = blockpos.n();
        int i1 = blockpos.o();
        int i2 = blockpos.p();

        if (!World.a(iblockaccess, blockpos.b()) && !Blocks.ab.e(iblockaccess, blockpos.b())) {
            boolean flag0 = (i0 + i1 + i2 & 1) == 1;
            boolean flag1 = (i0 / 2 + i1 / 2 + i2 / 2 & 1) == 1;
            int i3 = 0;

            if (this.e(iblockaccess, blockpos.a())) {
                i3 = flag0 ? 1 : 2;
            }

            return iblockstate.a(N, Boolean.valueOf(this.e(iblockaccess, blockpos.c()))).a(O, Boolean.valueOf(this.e(iblockaccess, blockpos.f()))).a(P, Boolean.valueOf(this.e(iblockaccess, blockpos.d()))).a(Q, Boolean.valueOf(this.e(iblockaccess, blockpos.e()))).a(R, Integer.valueOf(i3)).a(b, Boolean.valueOf(flag1)).a(M, Boolean.valueOf(flag0));
        }
        else {
            return this.P();
        }
    }

    public void a(Block block, int i0, int i1) {
        this.S.put(block, Integer.valueOf(i0));
        this.T.put(block, Integer.valueOf(i1));
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int a(Random random) {
        return 0;
    }

    public int a(World world) {
        return 30;
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (world.Q().b("doFireTick")) {
            if (!this.c(world, blockpos)) {
                world.g(blockpos);
            }

            Block block = world.p(blockpos.b()).c();
            boolean flag0 = block == Blocks.aV;

            if (world.t instanceof WorldProviderEnd && block == Blocks.h) {
                flag0 = true;
            }

            if (!flag0 && world.S() && this.d(world, blockpos)) {
                world.g(blockpos);
            }
            else {
                int i0 = ((Integer)iblockstate.b(a)).intValue();

                if (i0 < 15) {
                    iblockstate = iblockstate.a(a, Integer.valueOf(i0 + random.nextInt(3) / 2));
                    world.a(blockpos, iblockstate, 4);
                }

                world.a(blockpos, (Block)this, this.a(world) + random.nextInt(10));
                if (!flag0) {
                    if (!this.e(world, blockpos)) {
                        if (!World.a((IBlockAccess)world, blockpos.b()) || i0 > 3) {
                            world.g(blockpos);
                        }

                        return;
                    }

                    if (!this.e((IBlockAccess)world, blockpos.b()) && i0 == 15 && random.nextInt(4) == 0) {
                        world.g(blockpos);
                        return;
                    }
                }

                boolean flag1 = world.D(blockpos);
                byte b0 = 0;

                if (flag1) {
                    b0 = -50;
                }

                this.a(world, blockpos.f(), 300 + b0, random, i0);
                this.a(world, blockpos.e(), 300 + b0, random, i0);
                this.a(world, blockpos.b(), 250 + b0, random, i0);
                this.a(world, blockpos.a(), 250 + b0, random, i0);
                this.a(world, blockpos.c(), 300 + b0, random, i0);
                this.a(world, blockpos.d(), 300 + b0, random, i0);

                for (int i1 = -1; i1 <= 1; ++i1) {
                    for (int i2 = -1; i2 <= 1; ++i2) {
                        for (int i3 = -1; i3 <= 4; ++i3) {
                            if (i1 != 0 || i3 != 0 || i2 != 0) {
                                int i4 = 100;

                                if (i3 > 1) {
                                    i4 += (i3 - 1) * 100;
                                }

                                BlockPos blockpos1 = blockpos.a(i1, i3, i2);
                                int i5 = this.m(world, blockpos1);

                                if (i5 > 0) {
                                    int i6 = (i5 + 40 + world.aa().a() * 7) / (i0 + 30);

                                    if (flag1) {
                                        i6 /= 2;
                                    }

                                    if (i6 > 0 && random.nextInt(i4) <= i6 && (!world.S() || !this.d(world, blockpos1))) {
                                        int i7 = i0 + random.nextInt(5) / 4;

                                        if (i7 > 15) {
                                            i7 = 15;
                                        }

                                        // CanaryMod: Ignition
                                        // Spread Status 3
                                        if (!new IgnitionHook(new CanaryBlock(world.p(blockpos1), blockpos1, world, (byte)3), null, null, IgnitionCause.FIRE_SPREAD).call().isCanceled()) {
                                            world.a(blockpos1, iblockstate.a(a, Integer.valueOf(i7)), 3);
                                        }
                                        //
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean d(World world, BlockPos blockpos) {
        return world.C(blockpos) || world.C(blockpos.e()) || world.C(blockpos.f()) || world.C(blockpos.c()) || world.C(blockpos.d());
    }

    public boolean M() {
        return false;
    }

    private int c(Block block) {
        Integer integer = (Integer)this.T.get(block);

        return integer == null ? 0 : integer.intValue();
    }

    private int d(Block block) {
        Integer integer = (Integer)this.S.get(block);

        return integer == null ? 0 : integer.intValue();
    }

    private void a(World world, BlockPos blockpos, int i0, Random random, int i1) {
        int i2 = this.c(world.p(blockpos).c());
        // CanaryBlock: Ignition
        CanaryBlock ignited = new CanaryBlock(world.p(blockpos), blockpos, world);
        //

        if (random.nextInt(i0) < i2) {
            IBlockState iblockstate = world.p(blockpos);

            if (random.nextInt(i1 + 10) < 5 && !world.C(blockpos)) {
                int i3 = i1 + random.nextInt(5) / 4;

                if (i3 > 15) {
                    i3 = 15;
                }
                // CanaryMod: Ignition
                ignited.setStatus((byte)3); // Spread Status 3
                IgnitionHook hook = (IgnitionHook)new IgnitionHook(ignited, null, null, IgnitionCause.FIRE_SPREAD).call();
                if (!hook.isCanceled()) {
                    world.a(blockpos, this.P().a(a, Integer.valueOf(i3)), 3);
                }
                //
            }
            else {
                // CanaryMod: Ignition
                ignited.setStatus((byte)4); // Burned Up Status 4
                IgnitionHook hook = (IgnitionHook)new IgnitionHook(ignited, null, null, IgnitionCause.BURNT).call();
                if (!hook.isCanceled()) {
                    world.g(blockpos);
                }
                //
            }

            if (iblockstate.c() == Blocks.W) {
                Blocks.W.d(world, blockpos, iblockstate.a(BlockTNT.a, Boolean.valueOf(true)));
            }
        }
    }

    private boolean e(World world, BlockPos blockpos) {
        EnumFacing[] aenumfacing = EnumFacing.values();
        int i0 = aenumfacing.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EnumFacing enumfacing = aenumfacing[i1];

            if (this.e((IBlockAccess)world, blockpos.a(enumfacing))) {
                return true;
            }
        }

        return false;
    }

    private int m(World world, BlockPos blockpos) {
        if (!world.d(blockpos)) {
            return 0;
        }
        else {
            int i0 = 0;
            EnumFacing[] aenumfacing = EnumFacing.values();
            int i1 = aenumfacing.length;

            for (int i2 = 0; i2 < i1; ++i2) {
                EnumFacing enumfacing = aenumfacing[i2];

                i0 = Math.max(this.d(world.p(blockpos.a(enumfacing)).c()), i0);
            }

            return i0;
        }
    }

    public boolean y() {
        return false;
    }

    public boolean e(IBlockAccess iblockaccess, BlockPos blockpos) {
        return this.d(iblockaccess.p(blockpos).c()) > 0;
    }

    public boolean c(World world, BlockPos blockpos) {
        return World.a((IBlockAccess)world, blockpos.b()) || this.e(world, blockpos);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (!World.a((IBlockAccess)world, blockpos.b()) && !this.e(world, blockpos)) {
            world.g(blockpos);
        }
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (world.t.q() > 0 || !Blocks.aY.d(world, blockpos)) {
            if (!World.a((IBlockAccess)world, blockpos.b()) && !this.e(world, blockpos)) {
                world.g(blockpos);
            }
            else {
                world.a(blockpos, (Block)this, this.a(world) + world.s.nextInt(10));
            }
        }
    }

    public MapColor g(IBlockState iblockstate) {
        return MapColor.f;
    }

    public IBlockState a(int i0) {
        return this.P().a(a, Integer.valueOf(i0));
    }

    public int c(IBlockState iblockstate) {
        return ((Integer)iblockstate.b(a)).intValue();
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a, N, O, P, Q, R, b, M });
    }
}
