package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import net.canarymod.api.world.blocks.BlockFace;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public enum EnumFacing implements IStringSerializable {

    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0)),
    UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0)),
    NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1)),
    SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1)),
    WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0)),
    EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0));

    private final int g;
    private final int h;
    private final int i;
    private final String j;
    private final Axis k;
    private final AxisDirection l;
    private final Vec3i m;
    private static final EnumFacing[] n = new EnumFacing[6];
    private static final EnumFacing[] o = new EnumFacing[4];
    private static final Map p = Maps.newHashMap();

    private EnumFacing(int i0, int i1, int i2, String s0, AxisDirection enumfacing_axisdirection, Axis enumfacing_axis, Vec3i vec3i) {
        this.g = i0;
        this.i = i2;
        this.h = i1;
        this.j = s0;
        this.k = enumfacing_axis;
        this.l = enumfacing_axisdirection;
        this.m = vec3i;
    }

    public int a() {
        return this.g;
    }

    public int b() {
        return this.i;
    }

    public AxisDirection c() {
        return this.l;
    }

    public EnumFacing d() {
        return a(this.h);
    }

    public EnumFacing e() {
        switch (SwitchPlane.b[this.ordinal()]) {
            case 1:
                return EAST;

            case 2:
                return SOUTH;

            case 3:
                return WEST;

            case 4:
                return NORTH;

            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    public EnumFacing f() {
        switch (SwitchPlane.b[this.ordinal()]) {
            case 1:
                return WEST;

            case 2:
                return NORTH;

            case 3:
                return EAST;

            case 4:
                return SOUTH;

            default:
                throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    public int g() {
        return this.k == Axis.X ? this.l.a() : 0;
    }

    public int h() {
        return this.k == Axis.Y ? this.l.a() : 0;
    }

    public int i() {
        return this.k == Axis.Z ? this.l.a() : 0;
    }

    public String j() {
        return this.j;
    }

    public Axis k() {
        return this.k;
    }

    public static EnumFacing a(int i0) {
        return n[MathHelper.a(i0 % n.length)];
    }

    public static EnumFacing b(int i0) {
        return o[MathHelper.a(i0 % o.length)];
    }

    public static EnumFacing a(double d0) {
        return b(MathHelper.c(d0 / 90.0D + 0.5D) & 3);
    }

    public static EnumFacing a(Random random) {
        return values()[random.nextInt(values().length)];
    }

    public String toString() {
        return this.j;
    }

    public String l() {
        return this.j;
    }

    static {
        EnumFacing[] aenumfacing = values();
        int i0 = aenumfacing.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EnumFacing enumfacing = aenumfacing[i1];

            n[enumfacing.g] = enumfacing;
            if (enumfacing.k().c()) {
                o[enumfacing.i] = enumfacing;
            }

            p.put(enumfacing.j().toLowerCase(), enumfacing);
        }
    }

    public static enum Axis implements Predicate, IStringSerializable {

        X("X", 0, "x", Plane.HORIZONTAL),
        Y("Y", 1, "y", Plane.VERTICAL),
        Z("Z", 2, "z", Plane.HORIZONTAL);
        private static final Map d = Maps.newHashMap();
        private final String e;
        private final Plane f;

        private static final Axis[] $VALUES = new Axis[]{ X, Y, Z };

        private Axis(String p_i46015_1_, int p_i46015_2_, String p_i46015_3_, Plane p_i46015_4_) {
            this.e = p_i46015_3_;
            this.f = p_i46015_4_;
        }

        public String a() {
            return this.e;
        }

        public boolean b() {
            return this.f == Plane.VERTICAL;
        }

        public boolean c() {
            return this.f == Plane.HORIZONTAL;
        }

        public String toString() {
            return this.e;
        }

        public boolean a(EnumFacing p_a_1_) {
            return p_a_1_ != null && p_a_1_.k() == this;
        }

        public Plane d() {
            return this.f;
        }

        public String l() {
            return this.e;
        }

        public boolean apply(Object p_apply_1_) {
            return this.a((EnumFacing)p_apply_1_);
        }

        static {
            Axis[] aenumfacing_axis = values();
            int i0 = aenumfacing_axis.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                Axis enumfacing_axis = aenumfacing_axis[i1];

                d.put(enumfacing_axis.a().toLowerCase(), enumfacing_axis);
            }
        }
    }

    public static enum AxisDirection {

        POSITIVE("POSITIVE", 0, 1, "Towards positive"),
        NEGATIVE("NEGATIVE", 1, -1, "Towards negative");
        private final int c;
        private final String d;

        private static final AxisDirection[] $VALUES = new AxisDirection[]{ POSITIVE, NEGATIVE };

        private AxisDirection(String p_i46014_1_, int p_i46014_2_, int p_i46014_3_, String p_i46014_4_) {
            this.c = p_i46014_3_;
            this.d = p_i46014_4_;
        }

        public int a() {
            return this.c;
        }

        public String toString() {
            return this.d;
        }

    }

    public static enum Plane implements Predicate, Iterable {

        HORIZONTAL("HORIZONTAL", 0),
        VERTICAL("VERTICAL", 1);

        private static final Plane[] $VALUES = new Plane[]{ HORIZONTAL, VERTICAL };

        private Plane(String p_i46013_1_, int p_i46013_2_) {
        }

        public EnumFacing[] a() {
            switch (SwitchPlane.c[this.ordinal()]) {
                case 1:
                    return new EnumFacing[]{ EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };

                case 2:
                    return new EnumFacing[]{ EnumFacing.UP, EnumFacing.DOWN };

                default:
                    throw new Error("Someone\'s been tampering with the universe!");
            }
        }

        public EnumFacing a(Random p_a_1_) {
            EnumFacing[] aenumfacing = this.a();

            return aenumfacing[p_a_1_.nextInt(aenumfacing.length)];
        }

        public boolean a(EnumFacing p_a_1_) {
            return p_a_1_ != null && p_a_1_.k().d() == this;
        }

        public Iterator iterator() {
            return Iterators.forArray(this.a());
        }

        public boolean apply(Object p_apply_1_) {
            return this.a((EnumFacing)p_apply_1_);
        }

    }

    static final class SwitchPlane {

        static final int[] a;

        static final int[] b;

        static final int[] c = new int[Plane.values().length];

        static {
            try {
                c[Plane.HORIZONTAL.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror101) {
                ;
            }

            try {
                c[Plane.VERTICAL.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror100) {
                ;
            }

            b = new int[EnumFacing.values().length];

            try {
                b[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                b[EnumFacing.EAST.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                b[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                b[EnumFacing.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                b[EnumFacing.UP.ordinal()] = 5;
            }
            catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                b[EnumFacing.DOWN.ordinal()] = 6;
            }
            catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            a = new int[Axis.values().length];

            try {
                a[Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                a[Axis.Y.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

            try {
                a[Axis.Z.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror10) {
                ;
            }
        }
    }

    // CanaryMod: cause, lazy
    public BlockFace asBlockFace() {
        return BlockFace.fromByte((byte)this.ordinal());
    }
}
