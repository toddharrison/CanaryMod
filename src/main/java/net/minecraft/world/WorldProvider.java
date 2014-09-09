package net.minecraft.world;

import net.canarymod.api.world.CanaryChunkProviderCustom;
import net.canarymod.api.world.ChunkProviderCustom;
import net.canarymod.api.world.DimensionType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.FlatGeneratorInfo;

public abstract class WorldProvider {

    public static final float[] a = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
    public World b;
    public WorldType c;
    public String d;
    public WorldChunkManager e;
    public boolean f;
    public boolean g;
    public float[] h = new float[16];
    public int i;
    private float[] j = new float[4];
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
        this.c = world.N().u();
        this.d = world.N().y();
        this.b();
        this.a();
    }

    protected void a() {
        float f0 = 0.0F;

        for (int i0 = 0; i0 <= 15; ++i0) {
            float f1 = 1.0F - (float) i0 / 15.0F;

            this.h[i0] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f0) + f0;
        }
    }

    protected void b() {
        if (this.b.N().u() == WorldType.c) {
            FlatGeneratorInfo flatgeneratorinfo = FlatGeneratorInfo.a(this.b.N().y());

            this.e = new WorldChunkManagerHell(BiomeGenBase.d(flatgeneratorinfo.a()), 0.5F);
        }
        else {
            this.e = new WorldChunkManager(this.b);
        }
    }

    public IChunkProvider c() {
        //CanaryMod changed that to load custom generators from dim types
        if (this.canaryDimensionType.hasChunkProvider()) {
            IChunkProvider nmsProvider = (IChunkProvider) (this.c == WorldType.c ? new ChunkProviderFlat(this.b, this.b.G(), this.b.M().s(), this.d) : new ChunkProviderGenerate(this.b, this.b.G(), this.b.M().s()));
            ChunkProviderCustom dimProvider = canaryDimensionType.getChunkProvider();
            dimProvider.setWorld(this.b.getCanaryWorld());
            return new CanaryChunkProviderCustom(dimProvider, nmsProvider);
        }
        else {
            return (IChunkProvider) (this.c == WorldType.c ? new ChunkProviderFlat(this.b, this.b.G(), this.b.M().s(), this.d) : new ChunkProviderGenerate(this.b, this.b.G(), this.b.M().s()));
        }
        //
//        return (IChunkProvider) (this.c == WorldType.c ? new ChunkProviderFlat(this.b, this.b.G(), this.b.M().s(), this.d) : new ChunkProviderGenerate(this.b, this.b.G(), this.b.M().s()));
    }

    public boolean a(int i0, int i1) {
        return this.b.b(i0, i1) == Blocks.c;
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

    public ChunkCoordinates h() {
        return null;
    }

    public int i() {
        return this.c == WorldType.c ? 4 : 64;
    }

    public abstract String l();

}
