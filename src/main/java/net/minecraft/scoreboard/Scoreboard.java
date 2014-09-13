package net.minecraft.scoreboard;

import net.canarymod.api.scoreboard.CanaryScoreboard;

import java.util.*;

public class Scoreboard {

    private final Map a = new HashMap();
    private final Map b = new HashMap();
    private final Map c = new HashMap();
    private static final ScoreObjective[] d = new ScoreObjective[3]; // CanaryMod: make static
    private final Map e = new HashMap();
    private final Map f = new HashMap();

    // CanaryMod: Start CanaryScoreboard 
    protected CanaryScoreboard scoreboard;
    
    public Scoreboard() {
        scoreboard = new CanaryScoreboard(this, "scoreboard");
    }
    
    /**@param name The save name associated with this Scoreboard */
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
                object = new ArrayList();
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

        return collection == null ? new ArrayList() : new ArrayList(collection);
    }

    public Score a(String s0, ScoreObjective scoreobjective) {
        Object object = (Map) this.c.get(s0);

        if (object == null) {
            object = new HashMap();
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
        ArrayList arraylist = new ArrayList();
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

    public void c(String s0) {
        Map map = (Map) this.c.remove(s0);

        if (map != null) {
            this.a(s0);
        }
    }

    public Collection e() {
        Collection collection = this.c.values();
        ArrayList arraylist = new ArrayList();
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();

            arraylist.addAll(map.values());
        }

        return arraylist;
    }

    public Map d(String s0) {
        Object object = (Map) this.c.get(s0);

        if (object == null) {
            object = new HashMap();
        }

        return (Map) object;
    }

    public void k(ScoreObjective scoreobjective) {
        this.a.remove(scoreobjective.b());

        for (int i0 = 0; i0 < 3; ++i0) {
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

    public ScorePlayerTeam e(String s0) {
        return (ScorePlayerTeam) this.e.get(s0);
    }

    public ScorePlayerTeam f(String s0) {
        ScorePlayerTeam scoreplayerteam = this.e(s0);

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
            ScorePlayerTeam scoreplayerteam = this.e(s1);
            if (this.i(s0) != null) {
                this.g(s0);
            }

            this.f.put(s0, scoreplayerteam);
            scoreplayerteam.d().add(s0);
            return true;
        }
    }

    public boolean g(String s0) {
        ScorePlayerTeam scoreplayerteam = this.i(s0);

        if (scoreplayerteam != null) {
            this.a(s0, scoreplayerteam);
            return true;
        }
        else {
            return false;
        }
    }

    public void a(String s0, ScorePlayerTeam scoreplayerteam) {
        if (this.i(s0) != scoreplayerteam) {
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

    public ScorePlayerTeam i(String s0) {
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
                return null;
        }
    }

    public static int j(String s0) {
        return s0.equalsIgnoreCase("list") ? 0 : (s0.equalsIgnoreCase("sidebar") ? 1 : (s0.equalsIgnoreCase("belowName") ? 2 : -1));
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
}
