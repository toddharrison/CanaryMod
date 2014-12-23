package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.canarymod.api.scoreboard.CanaryScoreboard;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

public class Scoreboard {

    private final Map a = Maps.newHashMap();
    private final Map b = Maps.newHashMap();
    private final Map c = Maps.newHashMap();
    private static final ScoreObjective[] d = new ScoreObjective[19]; // CanaryMod: make static
    private final Map e = Maps.newHashMap();
    private final Map f = Maps.newHashMap();
    private static String[] g = null;

    // CanaryMod: Start CanaryScoreboard 
    protected CanaryScoreboard scoreboard;

    public Scoreboard() {
        scoreboard = new CanaryScoreboard(this, "scoreboard");
    }

    /**
     * @param name
     *         The save name associated with this Scoreboard
     */
    public Scoreboard(String name) {
        scoreboard = new CanaryScoreboard(this, name);
    }
    // CanaryMod: End CanaryScoreboard

    public ScoreObjective b(String s0) {
        return (ScoreObjective) this.a.get(s0);
    }

    public ScoreObjective a(String s0, IScoreObjectiveCriteria iscoreobjectivecriteria) {
        ScoreObjective scoreobjective = this.b(s0);

        if (scoreobjective != null) {
            throw new IllegalArgumentException("An objective with the name \'" + s0 + "\' already exists!");
        }
        else {
            scoreobjective = new ScoreObjective(this, s0, iscoreobjectivecriteria);
            Object object = (List) this.b.get(iscoreobjectivecriteria);

            if (object == null) {
                object = Lists.newArrayList();
                this.b.put(iscoreobjectivecriteria, object);
            }

            ((List) object).add(scoreobjective);
            this.a.put(s0, scoreobjective);
            this.a(scoreobjective);
            return scoreobjective;
        }
    }

    public Collection a(IScoreObjectiveCriteria iscoreobjectivecriteria) {
        Collection collection = (Collection) this.b.get(iscoreobjectivecriteria);

        return collection == null ? Lists.newArrayList() : Lists.newArrayList(collection);
    }

    public boolean b(String s0, ScoreObjective scoreobjective) {
        Map map = (Map) this.c.get(s0);

        if (map == null) {
            return false;
        }
        else {
            Score score = (Score) map.get(scoreobjective);

            return score != null;
        }
    }

    public Score c(String s0, ScoreObjective scoreobjective) {
        Object object = (Map) this.c.get(s0);

        if (object == null) {
            object = Maps.newHashMap();
            this.c.put(s0, object);
        }

        Score score = (Score) ((Map) object).get(scoreobjective);

        if (score == null) {
            score = new Score(this, scoreobjective, s0);
            ((Map) object).put(scoreobjective, score);
        }

        return score;
    }

    public Collection i(ScoreObjective scoreobjective) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.c.values().iterator();

        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();
            Score score = (Score) map.get(scoreobjective);

            if (score != null) {
                arraylist.add(score);
            }
        }

        Collections.sort(arraylist, Score.a);
        return arraylist;
    }

    public Collection c() {
        return this.a.values();
    }

    public Collection d() {
        return this.c.keySet();
    }

    public void d(String s0, ScoreObjective scoreobjective) {
        Map map;

        if (scoreobjective == null) {
            map = (Map) this.c.remove(s0);
            if (map != null) {
                this.a(s0);
            }
        }
        else {
            map = (Map) this.c.get(s0);
            if (map != null) {
                Score score = (Score) map.remove(scoreobjective);

                if (map.size() < 1) {
                    Map map1 = (Map) this.c.remove(s0);

                    if (map1 != null) {
                        this.a(s0);
                    }
                }
                else if (score != null) {
                    this.a(s0, scoreobjective);
                }
            }
        }

    }

    public Collection e() {
        Collection collection = this.c.values();
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();

            arraylist.addAll(map.values());
        }

        return arraylist;
    }

    public Map c(String s0) {
        Object object = (Map) this.c.get(s0);

        if (object == null) {
            object = Maps.newHashMap();
        }

        return (Map) object;
    }

    public void k(ScoreObjective scoreobjective) {
        this.a.remove(scoreobjective.b());

        for (int i0 = 0; i0 < 19; ++i0) {
            if (this.a(i0) == scoreobjective) {
                this.a(i0, (ScoreObjective) null);
            }
        }

        List list = (List) this.b.get(scoreobjective.c());

        if (list != null) {
            list.remove(scoreobjective);
        }

        Iterator iterator = this.c.values().iterator();

        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();

            map.remove(scoreobjective);
        }

        this.c(scoreobjective);
    }

    public void a(int i0, ScoreObjective scoreobjective) {
        this.d[i0] = scoreobjective;
    }

    public ScoreObjective a(int i0) {
        return this.d[i0];
    }

    public ScorePlayerTeam d(String s0) {
        return (ScorePlayerTeam) this.e.get(s0);
    }

    public ScorePlayerTeam e(String s0) {
        ScorePlayerTeam scoreplayerteam = this.d(s0);

        if (scoreplayerteam != null) {
            throw new IllegalArgumentException("A team with the name \'" + s0 + "\' already exists!");
        }
        else {
            scoreplayerteam = new ScorePlayerTeam(this, s0);
            this.e.put(s0, scoreplayerteam);
            this.a(scoreplayerteam);
            return scoreplayerteam;
        }
    }

    public void d(ScorePlayerTeam scoreplayerteam) {
        this.e.remove(scoreplayerteam.b());
        Iterator iterator = scoreplayerteam.d().iterator();

        while (iterator.hasNext()) {
            String s0 = (String) iterator.next();

            this.f.remove(s0);
        }

        this.c(scoreplayerteam);
    }

    public boolean a(String s0, String s1) {
        if (!this.e.containsKey(s1)) {
            return false;
        }
        else {
            ScorePlayerTeam scoreplayerteam = this.d(s1);

            if (this.h(s0) != null) {
                this.f(s0);
            }

            this.f.put(s0, scoreplayerteam);
            scoreplayerteam.d().add(s0);
            return true;
        }
    }

    public boolean f(String s0) {
        ScorePlayerTeam scoreplayerteam = this.h(s0);

        if (scoreplayerteam != null) {
            this.a(s0, scoreplayerteam);
            return true;
        }
        else {
            return false;
        }
    }

    public void a(String s0, ScorePlayerTeam scoreplayerteam) {
        if (this.h(s0) != scoreplayerteam) {
            throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team \'" + scoreplayerteam.b() + "\'.");
        }
        else {
            this.f.remove(s0);
            scoreplayerteam.d().remove(s0);
        }
    }

    public Collection f() {
        return this.e.keySet();
    }

    public Collection g() {
        return this.e.values();
    }

    public ScorePlayerTeam h(String s0) {
        return (ScorePlayerTeam) this.f.get(s0);
    }

    public void a(ScoreObjective scoreobjective) {
    }

    public void b(ScoreObjective scoreobjective) {
    }

    public void c(ScoreObjective scoreobjective) {
    }

    public void a(Score score) {
    }

    public void a(String s0) {
    }

    public void a(String s0, ScoreObjective scoreobjective) {
    }

    public void a(ScorePlayerTeam scoreplayerteam) {
    }

    public void b(ScorePlayerTeam scoreplayerteam) {
    }

    public void c(ScorePlayerTeam scoreplayerteam) {
    }

    public static String b(int i0) {
        switch (i0) {
            case 0:
                return "list";

            case 1:
                return "sidebar";

            case 2:
                return "belowName";

            default:
                if (i0 >= 3 && i0 <= 18) {
                    EnumChatFormatting enumchatformatting = EnumChatFormatting.a(i0 - 3);

                    if (enumchatformatting != null && enumchatformatting != EnumChatFormatting.RESET) {
                        return "sidebar.team." + enumchatformatting.e();
                    }
                }

                return null;
        }
    }

    public static int i(String s0) {
        if (s0.equalsIgnoreCase("list")) {
            return 0;
        }
        else if (s0.equalsIgnoreCase("sidebar")) {
            return 1;
        }
        else if (s0.equalsIgnoreCase("belowName")) {
            return 2;
        }
        else {
            if (s0.startsWith("sidebar.team.")) {
                String s1 = s0.substring("sidebar.team.".length());
                EnumChatFormatting enumchatformatting = EnumChatFormatting.b(s1);

                if (enumchatformatting != null && enumchatformatting.b() >= 0) {
                    return enumchatformatting.b() + 3;
                }
            }

            return -1;
        }
    }

    public static String[] h() {
        if (g == null) {
            g = new String[19];

            for (int i0 = 0; i0 < 19; ++i0) {
                g[i0] = b(i0);
            }
        }

        return g;
    }

    // CanaryMod: our methods
    public CanaryScoreboard getCanaryScoreboard() {
        return this.scoreboard;
    }

    public ScoreObjective getScoreObjective(String name) {
        return (ScoreObjective) this.a.get(name);
    }

    public Collection getAllScoreObjective() {
        return this.a.values();
    }
    
    public ScorePlayerTeam addTeam(ScorePlayerTeam team)
    {
        ScorePlayerTeam scoreplayerteam = this.d(team.b());

        if (scoreplayerteam != null) {
            throw new IllegalArgumentException("A team with the name \'" + team.b() + "\' already exists!");
        }
        else {
            scoreplayerteam = team;
            this.e.put(team.b(), scoreplayerteam);
            this.a(scoreplayerteam);
            return scoreplayerteam;
        }
    }
}
