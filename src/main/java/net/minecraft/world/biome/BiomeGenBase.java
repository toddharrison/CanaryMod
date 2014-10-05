package net.minecraft.world.biome;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.canarymod.api.world.CanaryBiome;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public abstract class BiomeGenBase {

    private static final Logger aD = LogManager.getLogger();
    protected static final BiomeGenBase.Height a = new BiomeGenBase.Height(0.1F, 0.2F);
    protected static final BiomeGenBase.Height b = new BiomeGenBase.Height(-0.5F, 0.0F);
    protected static final BiomeGenBase.Height c = new BiomeGenBase.Height(-1.0F, 0.1F);
    protected static final BiomeGenBase.Height d = new BiomeGenBase.Height(-1.8F, 0.1F);
    protected static final BiomeGenBase.Height e = new BiomeGenBase.Height(0.125F, 0.05F);
    protected static final BiomeGenBase.Height f = new BiomeGenBase.Height(0.2F, 0.2F);
    protected static final BiomeGenBase.Height g = new BiomeGenBase.Height(0.45F, 0.3F);
    protected static final BiomeGenBase.Height h = new BiomeGenBase.Height(1.5F, 0.025F);
    protected static final BiomeGenBase.Height i = new BiomeGenBase.Height(1.0F, 0.5F);
    protected static final BiomeGenBase.Height j = new BiomeGenBase.Height(0.0F, 0.025F);
    protected static final BiomeGenBase.Height k = new BiomeGenBase.Height(0.1F, 0.8F);
    protected static final BiomeGenBase.Height l = new BiomeGenBase.Height(0.2F, 0.3F);
    protected static final BiomeGenBase.Height m = new BiomeGenBase.Height(-0.2F, 0.1F);
    private static final BiomeGenBase[] aE = new BiomeGenBase[256];
    public static final Set n = Sets.newHashSet();
    public static final Map o = Maps.newHashMap();
    public static final BiomeGenBase p = (new BiomeGenOcean(0)).b(112).a("Ocean").a(c);
    public static final BiomeGenBase q = (new BiomeGenPlains(1)).b(9286496).a("Plains");
    public static final BiomeGenBase r = (new BiomeGenDesert(2)).b(16421912).a("Desert").b().a(2.0F, 0.0F).a(e);
    public static final BiomeGenBase s = (new BiomeGenHills(3, false)).b(6316128).a("Extreme Hills").a(i).a(0.2F, 0.3F);
    public static final BiomeGenBase t = (new BiomeGenForest(4, 0)).b(353825).a("Forest");
    public static final BiomeGenBase u = (new BiomeGenTaiga(5, 0)).b(747097).a("Taiga").a(5159473).a(0.25F, 0.8F).a(f);
    public static final BiomeGenBase v = (new BiomeGenSwamp(6)).b(522674).a("Swampland").a(9154376).a(m).a(0.8F, 0.9F);
    public static final BiomeGenBase w = (new BiomeGenRiver(7)).b(255).a("River").a(b);
    public static final BiomeGenBase x = (new BiomeGenHell(8)).b(16711680).a("Hell").b().a(2.0F, 0.0F);
    public static final BiomeGenBase y = (new BiomeGenEnd(9)).b(8421631).a("The End").b();
    public static final BiomeGenBase z = (new BiomeGenOcean(10)).b(9474208).a("FrozenOcean").c().a(c).a(0.0F, 0.5F);
    public static final BiomeGenBase A = (new BiomeGenRiver(11)).b(10526975).a("FrozenRiver").c().a(b).a(0.0F, 0.5F);
    public static final BiomeGenBase B = (new BiomeGenSnow(12, false)).b(16777215).a("Ice Plains").c().a(0.0F, 0.5F).a(e);
    public static final BiomeGenBase C = (new BiomeGenSnow(13, false)).b(10526880).a("Ice Mountains").c().a(g).a(0.0F, 0.5F);
    public static final BiomeGenBase D = (new BiomeGenMushroomIsland(14)).b(16711935).a("MushroomIsland").a(0.9F, 1.0F).a(l);
    public static final BiomeGenBase E = (new BiomeGenMushroomIsland(15)).b(10486015).a("MushroomIslandShore").a(0.9F, 1.0F).a(j);
    public static final BiomeGenBase F = (new BiomeGenBeach(16)).b(16440917).a("Beach").a(0.8F, 0.4F).a(j);
    public static final BiomeGenBase G = (new BiomeGenDesert(17)).b(13786898).a("DesertHills").b().a(2.0F, 0.0F).a(g);
    public static final BiomeGenBase H = (new BiomeGenForest(18, 0)).b(2250012).a("ForestHills").a(g);
    public static final BiomeGenBase I = (new BiomeGenTaiga(19, 0)).b(1456435).a("TaigaHills").a(5159473).a(0.25F, 0.8F).a(g);
    public static final BiomeGenBase J = (new BiomeGenHills(20, true)).b(7501978).a("Extreme Hills Edge").a(i.a()).a(0.2F, 0.3F);
    public static final BiomeGenBase K = (new BiomeGenJungle(21, false)).b(5470985).a("Jungle").a(5470985).a(0.95F, 0.9F);
    public static final BiomeGenBase L = (new BiomeGenJungle(22, false)).b(2900485).a("JungleHills").a(5470985).a(0.95F, 0.9F).a(g);
    public static final BiomeGenBase M = (new BiomeGenJungle(23, true)).b(6458135).a("JungleEdge").a(5470985).a(0.95F, 0.8F);
    public static final BiomeGenBase N = (new BiomeGenOcean(24)).b(48).a("Deep Ocean").a(d);
    public static final BiomeGenBase O = (new BiomeGenStoneBeach(25)).b(10658436).a("Stone Beach").a(0.2F, 0.3F).a(k);
    public static final BiomeGenBase P = (new BiomeGenBeach(26)).b(16445632).a("Cold Beach").a(0.05F, 0.3F).a(j).c();
    public static final BiomeGenBase Q = (new BiomeGenForest(27, 2)).a("Birch Forest").b(3175492);
    public static final BiomeGenBase R = (new BiomeGenForest(28, 2)).a("Birch Forest Hills").b(2055986).a(g);
    public static final BiomeGenBase S = (new BiomeGenForest(29, 3)).b(4215066).a("Roofed Forest");
    public static final BiomeGenBase T = (new BiomeGenTaiga(30, 0)).b(3233098).a("Cold Taiga").a(5159473).c().a(-0.5F, 0.4F).a(f).c(16777215);
    public static final BiomeGenBase U = (new BiomeGenTaiga(31, 0)).b(2375478).a("Cold Taiga Hills").a(5159473).c().a(-0.5F, 0.4F).a(g).c(16777215);
    public static final BiomeGenBase V = (new BiomeGenTaiga(32, 1)).b(5858897).a("Mega Taiga").a(5159473).a(0.3F, 0.8F).a(f);
    public static final BiomeGenBase W = (new BiomeGenTaiga(33, 1)).b(4542270).a("Mega Taiga Hills").a(5159473).a(0.3F, 0.8F).a(g);
    public static final BiomeGenBase X = (new BiomeGenHills(34, true)).b(5271632).a("Extreme Hills+").a(i).a(0.2F, 0.3F);
    public static final BiomeGenBase Y = (new BiomeGenSavanna(35)).b(12431967).a("Savanna").a(1.2F, 0.0F).b().a(e);
    public static final BiomeGenBase Z = (new BiomeGenSavanna(36)).b(10984804).a("Savanna Plateau").a(1.0F, 0.0F).b().a(h);
    public static final BiomeGenBase aa = (new BiomeGenMesa(37, false, false)).b(14238997).a("Mesa");
    public static final BiomeGenBase ab = (new BiomeGenMesa(38, false, true)).b(11573093).a("Mesa Plateau F").a(h);
    public static final BiomeGenBase ac = (new BiomeGenMesa(39, false, false)).b(13274213).a("Mesa Plateau").a(h);
    public static final BiomeGenBase ad = p;
    protected static final NoiseGeneratorPerlin ae;
    protected static final NoiseGeneratorPerlin af;
    protected static final WorldGenDoublePlant ag;
    public String ah;
    public int ai;
    public int aj;
    public IBlockState ak;
    public IBlockState al;
    public int am;
    public float an;
    public float ao;
    public float ap;
    public float aq;
    public int ar;
    public BiomeDecorator as;
    protected List at;
    protected List au;
    protected List av;
    protected List aw;
    protected boolean ax;
    protected boolean ay;
    public final int az;
    protected WorldGenTrees aA;
    protected WorldGenBigTree aB;
    protected WorldGenSwamp aC;
    private CanaryBiome biome = new CanaryBiome(this); // CanaryMod

    protected BiomeGenBase(int i0) {
        this.ak = Blocks.c.P();
        this.al = Blocks.d.P();
        this.am = 5169201;
        this.an = a.a;
        this.ao = a.b;
        this.ap = 0.5F;
        this.aq = 0.5F;
        this.ar = 16777215;
        this.at = Lists.newArrayList();
        this.au = Lists.newArrayList();
        this.av = Lists.newArrayList();
        this.aw = Lists.newArrayList();
        this.ay = true;
        this.aA = new WorldGenTrees(false);
        this.aB = new WorldGenBigTree(false);
        this.aC = new WorldGenSwamp();
        this.az = i0;
        aE[i0] = this;
        this.as = this.a();
        this.au.add(new BiomeGenBase.SpawnListEntry(EntitySheep.class, 12, 4, 4));
        this.au.add(new BiomeGenBase.SpawnListEntry(EntityRabbit.class, 10, 3, 3));
        this.au.add(new BiomeGenBase.SpawnListEntry(EntityPig.class, 10, 4, 4));
        this.au.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 4, 4));
        this.au.add(new BiomeGenBase.SpawnListEntry(EntityCow.class, 8, 4, 4));
        this.at.add(new BiomeGenBase.SpawnListEntry(EntitySpider.class, 100, 4, 4));
        this.at.add(new BiomeGenBase.SpawnListEntry(EntityZombie.class, 100, 4, 4));
        this.at.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.at.add(new BiomeGenBase.SpawnListEntry(EntityCreeper.class, 100, 4, 4));
        this.at.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 100, 4, 4));
        this.at.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
        this.at.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 5, 1, 1));
        this.av.add(new BiomeGenBase.SpawnListEntry(EntitySquid.class, 10, 4, 4));
        this.aw.add(new BiomeGenBase.SpawnListEntry(EntityBat.class, 10, 8, 8));
    }

    protected BiomeDecorator a() {
        return new BiomeDecorator();
    }

    protected BiomeGenBase a(float f0, float f1) {
        if (f0 > 0.1F && f0 < 0.2F) {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        else {
            this.ap = f0;
            this.aq = f1;
            return this;
        }
    }

    protected final BiomeGenBase a(BiomeGenBase.Height biomegenbase_height) {
        this.an = biomegenbase_height.a;
        this.ao = biomegenbase_height.b;
        return this;
    }

    protected BiomeGenBase b() {
        this.ay = false;
        return this;
    }

    public WorldGenAbstractTree a(Random random) {
        return (WorldGenAbstractTree) (random.nextInt(10) == 0 ? this.aB : this.aA);
    }

    public WorldGenerator b(Random random) {
        return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    }

    public BlockFlower.EnumFlowerType a(Random random, BlockPos blockpos) {
        return random.nextInt(3) > 0 ? BlockFlower.EnumFlowerType.DANDELION : BlockFlower.EnumFlowerType.POPPY;
    }

    protected BiomeGenBase c() {
        this.ax = true;
        return this;
    }

    protected BiomeGenBase a(String s0) {
        this.ah = s0;
        return this;
    }

    public BiomeGenBase a(int i0) { // CanaryMod: protected to public
        this.am = i0;
        return this;
    }

    protected BiomeGenBase b(int i0) {
        this.a(i0, false);
        return this;
    }

    protected BiomeGenBase c(int i0) {
        this.aj = i0;
        return this;
    }

    protected BiomeGenBase a(int i0, boolean flag0) {
        this.ai = i0;
        if (flag0) {
            this.aj = (i0 & 16711422) >> 1;
        }
        else {
            this.aj = i0;
        }

        return this;
    }

    public List a(EnumCreatureType enumcreaturetype) {
        switch (BiomeGenBase.SwitchEnumCreatureType.a[enumcreaturetype.ordinal()]) {
            case 1:
                return this.at;

            case 2:
                return this.au;

            case 3:
                return this.av;

            case 4:
                return this.aw;

            default:
                return Collections.emptyList();
        }
    }

    public boolean d() {
        return this.j();
    }

    public boolean e() {
        return this.j() ? false : this.ay;
    }

    public boolean f() {
        return this.aq > 0.85F;
    }

    public float g() {
        return 0.1F;
    }

    public final int h() {
        return (int) (this.aq * 65536.0F);
    }

    public final float a(BlockPos blockpos) {
        if (blockpos.o() > 64) {
            float f0 = (float) (ae.a((double) blockpos.n() * 1.0D / 8.0D, (double) blockpos.p() * 1.0D / 8.0D) * 4.0D);

            return this.ap - (f0 + (float) blockpos.o() - 64.0F) * 0.05F / 30.0F;
        }
        else {
            return this.ap;
        }
    }

    public void a(World world, Random random, BlockPos blockpos) {
        this.as.a(world, random, this, blockpos);
    }

    public boolean j() {
        return this.ax;
    }

    public void a(World world, Random random, ChunkPrimer chunkprimer, int i0, int i1, double d0) {
        this.b(world, random, chunkprimer, i0, i1, d0);
    }

    public final void b(World world, Random random, ChunkPrimer chunkprimer, int i0, int i1, double d0) {
        boolean flag0 = true;
        IBlockState iblockstate = this.ak;
        IBlockState iblockstate1 = this.al;
        int i2 = -1;
        int i3 = (int) (d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int i4 = i0 & 15;
        int i5 = i1 & 15;

        for (int i6 = 255; i6 >= 0; --i6) {
            if (i6 <= random.nextInt(5)) {
                chunkprimer.a(i5, i6, i4, Blocks.h.P());
            }
            else {
                IBlockState iblockstate2 = chunkprimer.a(i5, i6, i4);

                if (iblockstate2.c().r() == Material.a) {
                    i2 = -1;
                }
                else if (iblockstate2.c() == Blocks.b) {
                    if (i2 == -1) {
                        if (i3 <= 0) {
                            iblockstate = null;
                            iblockstate1 = Blocks.b.P();
                        }
                        else if (i6 >= 59 && i6 <= 64) {
                            iblockstate = this.ak;
                            iblockstate1 = this.al;
                        }

                        if (i6 < 63 && (iblockstate == null || iblockstate.c().r() == Material.a)) {
                            if (this.a(new BlockPos(i0, i6, i1)) < 0.15F) {
                                iblockstate = Blocks.aI.P();
                            }
                            else {
                                iblockstate = Blocks.j.P();
                            }
                        }

                        i2 = i3;
                        if (i6 >= 62) {
                            chunkprimer.a(i5, i6, i4, iblockstate);
                        }
                        else if (i6 < 56 - i3) {
                            iblockstate = null;
                            iblockstate1 = Blocks.b.P();
                            chunkprimer.a(i5, i6, i4, Blocks.n.P());
                        }
                        else {
                            chunkprimer.a(i5, i6, i4, iblockstate1);
                        }
                    }
                    else if (i2 > 0) {
                        --i2;
                        chunkprimer.a(i5, i6, i4, iblockstate1);
                        if (i2 == 0 && iblockstate1.c() == Blocks.m) {
                            i2 = random.nextInt(4) + Math.max(0, i6 - 63);
                            iblockstate1 = iblockstate1.b(BlockSand.a) == BlockSand.EnumType.RED_SAND ? Blocks.cM.P() : Blocks.A.P();
                        }
                    }
                }
            }
        }

    }

    protected BiomeGenBase k() {
        return this.d(this.az + 128);
    }

    protected BiomeGenBase d(int i0) {
        return new BiomeGenMutated(i0, this);
    }

    public Class l() {
        return this.getClass();
    }

    public boolean a(BiomeGenBase biomegenbase) {
        return biomegenbase == this ? true : (biomegenbase == null ? false : this.l() == biomegenbase.l());
    }

    public BiomeGenBase.TempCategory m() {
        return (double) this.ap < 0.2D ? BiomeGenBase.TempCategory.COLD : ((double) this.ap < 1.0D ? BiomeGenBase.TempCategory.MEDIUM : BiomeGenBase.TempCategory.WARM);
    }

    public static BiomeGenBase[] n() {
        return aE;
    }

    public static BiomeGenBase e(int i0) {
        return a(i0, (BiomeGenBase) null);
    }

    public static BiomeGenBase a(int i0, BiomeGenBase biomegenbase) {
        if (i0 >= 0 && i0 <= aE.length) {
            BiomeGenBase biomegenbase1 = aE[i0];

            return biomegenbase1 == null ? biomegenbase : biomegenbase1;
        }
        else {
            aD.warn("Biome ID is out of bounds: " + i0 + ", defaulting to 0 (Ocean)");
            return p;
        }
    }

    static {
        q.k();
        r.k();
        t.k();
        u.k();
        v.k();
        B.k();
        K.k();
        M.k();
        T.k();
        Y.k();
        Z.k();
        aa.k();
        ab.k();
        ac.k();
        Q.k();
        R.k();
        S.k();
        V.k();
        s.k();
        X.k();
        V.d(W.az + 128).a("Redwood Taiga Hills M");
        BiomeGenBase[] abiomegenbase = aE;
        int i0 = abiomegenbase.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            BiomeGenBase biomegenbase = abiomegenbase[i1];

            if (biomegenbase != null) {
                if (o.containsKey(biomegenbase.ah)) {
                    throw new Error("Biome \"" + biomegenbase.ah + "\" is defined as both ID " + ((BiomeGenBase) o.get(biomegenbase.ah)).az + " and " + biomegenbase.az);
                }

                o.put(biomegenbase.ah, biomegenbase);
                if (biomegenbase.az < 128) {
                    n.add(biomegenbase);
                }
            }
        }

        n.remove(x);
        n.remove(y);
        n.remove(z);
        n.remove(J);
        ae = new NoiseGeneratorPerlin(new Random(1234L), 1);
        af = new NoiseGeneratorPerlin(new Random(2345L), 1);
        ag = new WorldGenDoublePlant();
    }

    public static class Height {

        public float a;
        public float b;

        public Height(float f0, float f1) {
            this.a = f0;
            this.b = f1;
        }

        public BiomeGenBase.Height a() {
            return new BiomeGenBase.Height(this.a * 0.8F, this.b * 0.6F);
        }
    }


    public static class SpawnListEntry extends WeightedRandom.Item {

        public Class b;
        public int c;
        public int d;

        public SpawnListEntry(Class oclass0, int i0, int i1, int i2) {
            super(i0);
            this.b = oclass0;
            this.c = i1;
            this.d = i2;
        }

        public String toString() {
            return this.b.getSimpleName() + "*(" + this.c + "-" + this.d + "):" + this.a;
        }
    }

    static final class SwitchEnumCreatureType {

        static final int[] a = new int[EnumCreatureType.values().length];

        static {
            try {
                a[EnumCreatureType.MONSTER.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[EnumCreatureType.CREATURE.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                a[EnumCreatureType.WATER_CREATURE.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                a[EnumCreatureType.AMBIENT.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }

    public static enum TempCategory {

        OCEAN("OCEAN", 0), COLD("COLD", 1), MEDIUM("MEDIUM", 2), WARM("WARM", 3);

        private static final TempCategory[] $VALUES = new TempCategory[]{OCEAN, COLD, MEDIUM, WARM};

        private TempCategory(String s0, int i0) {
        }

    }

    /*
     * Convenience Methods
     */
    public void setCanSnow(boolean bool) {
        this.aw = bool;
    }

    public void setCanRain(boolean bool) {
        this.ax = bool;
    }

    public boolean canRain() {
        return this.ax;
    }

    public void setTemperatureAndPrecipitation(float temp, float precipitation) {
        this.a(temp, precipitation);
    }

    public CanaryBiome getCanaryBiome() {
        return this.biome;
    }
}
