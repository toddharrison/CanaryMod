package net.minecraft.world;

import net.canarymod.api.world.CanaryChunkProviderCustom;
import net.canarymod.api.world.ChunkProviderCustom;
import net.canarymod.api.world.DimensionType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderDebug;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.FlatGeneratorInfo;

public abstract class WorldProvider {

    public static final float[] a = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
    protected World b;
    private WorldType h;
    private String i;
    protected WorldChunkManager c;
    protected boolean d;
    protected boolean e;
    protected final float[] f = new float[16];
    protected int g;
    private final float[] j = new float[4];

    //CanaryMod adds DimensionType
    protected DimensionType canaryDimensionType;

    //
    public WorldProvider() {
    }

    //CanaryMod enable setting of dimensiontype
    public void setCanaryDimensionType(DimensionType t) {
        this.canaryDimensionType = t;
    }

    //
    public final void a(World world) {
        this.b = world;
        this.h = world.P().u();
        this.i = world.P().B();
        this.b();
        this.a();
    }

    protected void a() {
        float f0 = 0.0F;

        for (int i0 = 0; i0 <= 15; ++i0) {
            float f1 = 1.0F - (float) i0 / 15.0F;

            this.f[i0] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f0) + f0;
        }
    }

    protected void b() {
        WorldType worldtype = this.b.P().u();

        if (worldtype == WorldType.c) {
            FlatGeneratorInfo flatgeneratorinfo = FlatGeneratorInfo.a(this.b.P().B());

            this.c = new WorldChunkManagerHell(BiomeGenBase.a(flatgeneratorinfo.a(), BiomeGenBase.ad), 0.5F);
        }
        else if (worldtype == WorldType.g) {
            this.c = new WorldChunkManagerHell(BiomeGenBase.q, 0.0F);
        }
        else {
            this.c = new WorldChunkManager(this.b);
        }

    }

    public IChunkProvider c() {
        //CanaryMod changed that to load custom generators from dim types
        if (this.canaryDimensionType.hasChunkProvider()) {
            IChunkProvider nmsProvider = (IChunkProvider) (this.h == WorldType.c ? new ChunkProviderFlat(this.b, this.b.J(), this.b.P().s(), this.i) : (this.h == WorldType.g ? new ChunkProviderDebug(this.b) : (this.h == WorldType.f ? new ChunkProviderGenerate(this.b, this.b.J(), this.b.P().s(), this.i) : new ChunkProviderGenerate(this.b, this.b.J(), this.b.P().s(), this.i))));
            ChunkProviderCustom dimProvider = canaryDimensionType.getChunkProvider();
            dimProvider.setWorld(this.b.getCanaryWorld());
            return new CanaryChunkProviderCustom(dimProvider, nmsProvider);
        }
        else {
            return (IChunkProvider) (this.h == WorldType.c ? new ChunkProviderFlat(this.b, this.b.J(), this.b.P().s(), this.i) : (this.h == WorldType.g ? new ChunkProviderDebug(this.b) : (this.h == WorldType.f ? new ChunkProviderGenerate(this.b, this.b.J(), this.b.P().s(), this.i) : new ChunkProviderGenerate(this.b, this.b.J(), this.b.P().s(), this.i))));
        }
        //
//      return (IChunkProvider) (this.c == WorldType.c ? new ChunkProviderFlat(this.b, this.b.G(), this.b.M().s(), this.d) : new ChunkProviderGenerate(this.b, this.b.G(), this.b.M().s()));
    }

    public boolean a(int i0, int i1) {
        return this.b.c(new BlockPos(i0, 0, i1)) == Blocks.c;
    }

    public float a(long i0, float f0) {
        int i1 = (int) (i0 % 24000L);
        float f1 = ((float) i1 + f0) / 24000.0F - 0.25F;

        if (f1 < 0.0F) {
            ++f1;
        }

        if (f1 > 1.0F) {
            --f1;
        }

        float f2 = f1;

        f1 = 1.0F - (float) ((Math.cos((double) f1 * 3.141592653589793D) + 1.0D) / 2.0D);
        f1 = f2 + (f1 - f2) / 3.0F;
        return f1;
    }

    public int a(long i0) {
        return (int) (i0 / 24000L % 8L + 8L) % 8;
    }

    public boolean d() {
        return true;
    }

    public boolean e() {
        return true;
    }

    public static WorldProvider a(int i0) {
        return (WorldProvider) (i0 == -1 ? new WorldProviderHell() : (i0 == 0 ? new WorldProviderSurface() : (i0 == 1 ? new WorldProviderEnd() : null)));
    }

    public BlockPos h() {
        return null;
    }

    public int i() {
        return this.h == WorldType.c ? 4 : 64;
    }

    public abstract String k();

    public abstract String l();

    public WorldChunkManager m() {
        return this.c;
    }

    public boolean n() {
        return this.d;
    }

    public boolean o() {
        return this.e;
    }

    public float[] p() {
        return this.f;
    }

    public int q() {
        return this.g;
    }

    public WorldBorder r() {
        return new WorldBorder();
    }

}
