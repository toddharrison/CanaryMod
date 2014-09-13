package net.canarymod.api.scoreboard;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.canarymod.Canary;
import static net.canarymod.Canary.log;

import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.DimensionType;
import net.canarymod.api.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.SaveHandler;


/**
 * @author Somners
 */
public class CanaryScoreboardManager implements ScoreboardManager {
    
    private final File worldFolder = new File("worlds");
    private MapStorage mapStorage;
    private SaveHandler saveHandler;
    private final List<CanaryScoreboard> scoreboards = new ArrayList<CanaryScoreboard>();
    
    public CanaryScoreboardManager() {
        saveHandler = new SaveHandler(worldFolder, "scoreboards");
        mapStorage = new MapStorage(saveHandler);
    }

    @Override
    public Scoreboard getScoreboard() {
        return getScoreboard("scoreboard");
    }

    @Override
    public ScoreObjectiveCriteria getScoreCriteria(String name) {
        return ((net.minecraft.scoreboard.ScoreDummyCriteria) net.minecraft.scoreboard.IScoreObjectiveCriteria.a.get(name)).getCanaryScoreObjectiveCriteria();
    }

    @Override
    public void registerScoreCriteria(String name, Class<? extends ScoreObjectiveCriteria> criteria) {
        if (net.minecraft.scoreboard.IScoreObjectiveCriteria.a.containsKey(name)) {
            return;
        }
        try {
            new ScoreCanaryCriteria(criteria.newInstance());
        }
        catch (InstantiationException ex) {
            log.error("Exception Registering ScoreObjectiveCritera: " + criteria.getName());
        }
        catch (IllegalAccessException ex) {
            log.error("Exception Registering ScoreObjectiveCritera: " + criteria.getName());
        }
    }

    @Override
    public Scoreboard getScoreboard(String name) {
        /* Check to see if it's already loaded */
        for (Scoreboard s : scoreboards) {
            if (((CanaryScoreboard)s).getSaveName().equals(name))
                return s;
        }
        /* Not loaded, Create a Scoreboard */
        ServerScoreboard serverScoreboard = new ServerScoreboard(MinecraftServer.I());
        serverScoreboard.getCanaryScoreboard().saveName = name;
        /* Load the scoreboard (will be null if it doesn't exist */
        ScoreboardSaveData scoreboardSaveData = (ScoreboardSaveData)this.mapStorage.a(ScoreboardSaveData.class, name);

        if (scoreboardSaveData == null) {
            /* null! create a new savedata instance */
            scoreboardSaveData = new ScoreboardSaveData(name);
            this.mapStorage.a(name, scoreboardSaveData);
        }
        /* tell the ServerScoreboard and ScoreboardSaveData about each other <3 */
        scoreboardSaveData.a(serverScoreboard);
        ((ServerScoreboard)serverScoreboard).a(scoreboardSaveData);
        /* Cache It */
        scoreboards.add(serverScoreboard.getCanaryScoreboard());
        for (Player p : Canary.getServer().getConfigurationManager().getAllPlayers()) {
            EntityPlayerMP entityplayermp =  ((CanaryPlayer) p).getHandle();
            if (!name.equalsIgnoreCase("scoreboard")) this.updateClient(entityplayermp, serverScoreboard);
        }
        /* Finally lets return that darned scoreboard */
        return serverScoreboard.getCanaryScoreboard();
    }

    @Override
    public void saveAllScoreboards() {
        mapStorage.a();
    }

    /**
     * Updates all scoreboards to the client
     */
    public void updateClientAll(EntityPlayerMP entityplayermp) {
        /* Cycle through all the scoreboards */
        for (CanaryScoreboard scoreboard : scoreboards) {
            this.updateClient(entityplayermp,scoreboard.getHandle());
        }
    }

    public void updateClient(EntityPlayerMP entityplayermp, net.minecraft.scoreboard.Scoreboard serverScoreboard) {
            /* Send all the Team Data */
            Iterator iterator = serverScoreboard.g().iterator();

            while (iterator.hasNext()) {
                ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam) iterator.next();

                entityplayermp.a.a((Packet) (new S3EPacketTeams(scoreplayerteam, 0)));
            }

            /* Send all the Scores and ScoreObjective Data */
            for (Object o : serverScoreboard.c()) {
                net.minecraft.scoreboard.ScoreObjective scoreobjective = (net.minecraft.scoreboard.ScoreObjective) o;
                if (scoreobjective != null) {
                    List list = ((ServerScoreboard)serverScoreboard).d(scoreobjective);
                    Iterator iterator1 = list.iterator();

                    while (iterator1.hasNext()) {
                        Packet packet = (Packet) iterator1.next();

                        entityplayermp.a.a(packet);
                    }
                    entityplayermp.a.a(new S3BPacketScoreboardObjective(scoreobjective, 2));
                }
            }

    }

}
