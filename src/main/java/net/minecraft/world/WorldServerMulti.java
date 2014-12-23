package net.minecraft.world;


import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class WorldServerMulti extends WorldServer {

    private WorldServer a;

    // CanaryMod added extra worldinfo to signature
    public WorldServerMulti(MinecraftServer minecraftserver, ISaveHandler isavehandler, int i0, WorldServer worldserver, Profiler profiler, WorldInfo worldInfo) {
        super(minecraftserver, isavehandler, worldInfo, i0, profiler);
        this.a = worldserver;
        worldserver.af().a(new IBorderListener() {

            public void a(WorldBorder p_a_1_, double p_a_2_) {
                WorldServerMulti.this.af().a(p_a_2_);
            }

            public void a(WorldBorder p_a_1_, double p_a_2_, double p_a_4_, long p_a_6_) {
                WorldServerMulti.this.af().a(p_a_2_, p_a_4_, p_a_6_);
            }

            public void a(WorldBorder p_a_1_, double p_a_2_, double p_a_4_) {
                WorldServerMulti.this.af().c(p_a_2_, p_a_4_);
            }

            public void a(WorldBorder p_a_1_, int p_a_2_) {
                WorldServerMulti.this.af().b(p_a_2_);
            }

            public void b(WorldBorder p_b_1_, int p_b_2_) {
                WorldServerMulti.this.af().c(p_b_2_);
            }

            public void b(WorldBorder p_b_1_, double p_b_2_) {
                WorldServerMulti.this.af().c(p_b_2_);
            }

            public void c(WorldBorder p_c_1_, double p_c_2_) {
                WorldServerMulti.this.af().b(p_c_2_);
            }
        });
    }

    // CanaryMod - allow this world to have level.dat etc too
    // protected void a() throws MinecraftException {}

    public World b() {
        this.z = this.a.T();
        this.C = this.a.Z();
        String s0 = VillageCollection.a(this.t);
        VillageCollection villagecollection = (VillageCollection) this.z.a(VillageCollection.class, s0);

        if (villagecollection == null) {
            this.A = new VillageCollection(this);
            this.z.a(s0, (WorldSavedData) this.A);
        }
        else {
            this.A = villagecollection;
            this.A.a((World) this);
        }

        return this;
    }
}
