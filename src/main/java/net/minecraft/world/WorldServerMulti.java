package net.minecraft.world;


import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ISaveHandler;

public class WorldServerMulti extends WorldServer {

    public WorldServerMulti(MinecraftServer minecraftserver, ISaveHandler isavehandler, String s0, int i0, WorldSettings worldsettings, WorldServer worldserver, Profiler profiler) {
        super(minecraftserver, isavehandler, s0, i0, worldsettings, profiler);
        this.z = worldserver.z;
        this.D = worldserver.W();
        this.x = new DerivedWorldInfo(worldserver.M());
    }
    // CanaryMod - allow this world to have level.dat etc too
    // protected void a() throws MinecraftException {}
}
