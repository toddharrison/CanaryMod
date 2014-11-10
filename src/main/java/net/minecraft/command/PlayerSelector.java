package net.minecraft.command;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.canarymod.Canary;
import net.canarymod.api.scoreboard.CanaryScoreboard;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PlayerSelector {

    private static final Pattern a = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
    private static final Pattern b = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
    private static final Pattern c = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
    private static final Set d = Sets.newHashSet(new String[]{"x", "y", "z", "dx", "dy", "dz", "rm", "r"});

    public static EntityPlayerMP a(ICommandSender icommandsender, String s0) {
        return (EntityPlayerMP) a(icommandsender, s0, EntityPlayerMP.class);
    }

    public static Entity a(ICommandSender icommandsender, String s0, Class oclass0) {
        List list = b(icommandsender, s0, oclass0);

        return list.size() == 1 ? (Entity) list.get(0) : null;
    }

    public static IChatComponent b(ICommandSender icommandsender, String s0) {
        List list = b(icommandsender, s0, Entity.class);

        if (list.isEmpty()) {
            return null;
        }
        else {
            ArrayList arraylist = Lists.newArrayList();
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Entity entity = (Entity) iterator.next();

                arraylist.add(entity.e_());
            }

            return CommandBase.a((List) arraylist);
        }
    }

    public static List b(ICommandSender icommandsender, String s0, Class oclass0) {
        Matcher matcher = a.matcher(s0);

        if (matcher.matches() && icommandsender.a(1, "@")) {
            Map map = c(matcher.group(2));

            if (!b(icommandsender, map)) {
                return Collections.emptyList();
            }
            else {
                String s1 = matcher.group(1);
                BlockPos blockpos = b(map, icommandsender.c());
                List list = a(icommandsender, map);
                ArrayList arraylist = Lists.newArrayList();
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    World world = (World) iterator.next();

                    if (world != null) {
                        ArrayList arraylist1 = Lists.newArrayList();

                        arraylist1.addAll(a(map, s1));
                        arraylist1.addAll(b(map));
                        arraylist1.addAll(c(map));
                        arraylist1.addAll(d(map));
                        arraylist1.addAll(e(map));
                        arraylist1.addAll(f(map));
                        arraylist1.addAll(a(map, blockpos));
                        arraylist1.addAll(g(map));
                        arraylist.addAll(a(map, oclass0, (List) arraylist1, s1, world, blockpos));
                    }
                }

                return a((List) arraylist, map, icommandsender, oclass0, s1, blockpos);
            }
        }
        else {
            return Collections.emptyList();
        }
    }

    private static List a(ICommandSender icommandsender, Map map) {
        ArrayList arraylist = Lists.newArrayList();

        if (h(map)) {
            arraylist.add(icommandsender.e());
        }
        else {
            // fixing the c field call (array of the 3 worlds)
            for (net.canarymod.api.world.World world : Canary.getServer().getWorldManager().getAllWorlds())
                arraylist.add(((net.canarymod.api.world.CanaryWorld) world).getHandle());
            // Collections.addAll(arraylist, MinecraftServer.M().c)
        }

        return arraylist;
    }

    private static boolean b(ICommandSender icommandsender, Map map) {
        String s0 = b(map, "type");

        s0 = s0 != null && s0.startsWith("!") ? s0.substring(1) : s0;
        if (s0 != null && !EntityList.b(s0)) {
            ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.entity.invalidType", new Object[]{s0});

            chatcomponenttranslation.b().a(EnumChatFormatting.RED);
            icommandsender.a(chatcomponenttranslation);
            return false;
        }
        else {
            return true;
        }
    }

    private static List a(Map map, String s0) {
        ArrayList arraylist = Lists.newArrayList();
        String s1 = b(map, "type");
        final boolean flag0 = s1 != null && s1.startsWith("!");

        if (flag0) {
            s1 = s1.substring(1);
        }

        boolean flag1 = !s0.equals("e");
        boolean flag2 = s0.equals("r") && s1 != null;

        if ((s1 == null || !s0.equals("e")) && !flag2) {
            if (flag1) {
                arraylist.add(new Predicate() {

                    public boolean a(Entity s0) {
                        return s0 instanceof EntityPlayer;
                    }

                    public boolean apply(Object p_apply_1_) {
                        return this.a((Entity) p_apply_1_);
                    }
                });
            }
        }
        else {
            final String s2 = s1;
            arraylist.add(new Predicate() {

                public boolean a(Entity s0) {
                    return EntityList.a(s0, s2) != flag0;
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            });
        }

        return arraylist;
    }

    private static List b(Map map) {
        ArrayList arraylist = Lists.newArrayList();
        final int entityplayermp = a(map, "lm", -1);
        final int i1 = a(map, "l", -1);

        if (entityplayermp > -1 || i1 > -1) {
            arraylist.add(new Predicate() {

                public boolean a(Entity p_a_1_) {
                    if (!(p_a_1_ instanceof EntityPlayerMP)) {
                        return false;
                    }
                    else {
                        EntityPlayerMP entityplayermpx = (EntityPlayerMP) p_a_1_;

                        return (entityplayermp <= -1 || entityplayermpx.bz >= entityplayermp) && (i1 <= -1 || entityplayermpx.bz <= i1);
                    }
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            });
        }

        return arraylist;
    }

    private static List c(Map map) {
        ArrayList arraylist = Lists.newArrayList();
        final int entityplayermp = a(map, "m", WorldSettings.GameType.NOT_SET.a());

        if (entityplayermp != WorldSettings.GameType.NOT_SET.a()) {
            arraylist.add(new Predicate() {

                public boolean a(Entity p_a_1_) {
                    if (!(p_a_1_ instanceof EntityPlayerMP)) {
                        return false;
                    }
                    else {
                        EntityPlayerMP entityplayermpx = (EntityPlayerMP) p_a_1_;

                        return entityplayermpx.c.b().a() == entityplayermp;
                    }
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            });
        }

        return arraylist;
    }

    private static List d(Map map) {
        ArrayList arraylist = Lists.newArrayList();
        String s0 = b(map, "team");
        final boolean team = s0 != null && s0.startsWith("!");

        if (team) {
            s0 = s0.substring(1);
        }

        if (s0 != null) {
            final String s1 = s0;
            arraylist.add(new Predicate() {

                public boolean a(Entity p_a_1_) {
                    if (!(p_a_1_ instanceof EntityLivingBase)) {
                        return false;
                    }
                    else {
                        EntityLivingBase entitylivingbasex = (EntityLivingBase) p_a_1_;
                        Team teamx = entitylivingbasex.bN();
                        String s1 = teamx == null ? "" : teamx.b();

                        return s1.equals(s1) != team;
                    }
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            });
        }

        return arraylist;
    }

    private static List e(Map map) {
        ArrayList arraylist = Lists.newArrayList();
        final Map scoreboard = a(map);

        if (scoreboard != null && scoreboard.size() > 0) {
            arraylist.add(new Predicate() {

                public boolean a(Entity p_a_1_) {
                    // CanaryMod: Get the default scoreboard from us instead!
                    Scoreboard scoreboardx = ((CanaryScoreboard)Canary.scoreboards().getScoreboard()).getHandle();
                    Iterator iterator = scoreboard.entrySet().iterator();

                    Entry entry;
                    boolean flag0;
                    int i0;

                    do {
                        if (!iterator.hasNext()) {
                            return true;
                        }

                        entry = (Entry) iterator.next();
                        String s0 = (String) entry.getKey();

                        flag0 = false;
                        if (s0.endsWith("_min") && s0.length() > 4) {
                            flag0 = true;
                            s0 = s0.substring(0, s0.length() - 4);
                        }

                        ScoreObjective scoreobjective = scoreboardx.b(s0);

                        if (scoreobjective == null) {
                            return false;
                        }

                        String s1 = p_a_1_ instanceof EntityPlayerMP ? p_a_1_.d_() : p_a_1_.aJ().toString();

                        if (!scoreboardx.b(s1, scoreobjective)) {
                            return false;
                        }

                        Score score = scoreboardx.c(s1, scoreobjective);

                        i0 = score.c();
                        if (i0 < ((Integer) entry.getValue()).intValue() && flag0) {
                            return false;
                        }
                    } while (i0 <= ((Integer) entry.getValue()).intValue() || flag0);

                    return false;
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            });
        }

        return arraylist;
    }

    private static List f(Map map) {
        ArrayList arraylist = Lists.newArrayList();
        String s0 = b(map, "name");
        final boolean flag0 = s0 != null && s0.startsWith("!");

        if (flag0) {
            s0 = s0.substring(1);
        }

        if (s0 != null) {
            final String s1 = s0;
            arraylist.add(new Predicate() {

                public boolean a(Entity p_a_1_) {
                    return p_a_1_.d_().equals(s1) != flag0;
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            });
        }

        return arraylist;
    }

    private static List a(Map map, final BlockPos blockpos) {
        ArrayList i4 = Lists.newArrayList();
        final int i0 = a(map, "rm", -1);
        final int i1 = a(map, "r", -1);

        if (blockpos != null && (i0 >= 0 || i1 >= 0)) {
            final int i2 = i0 * i0;
            final int i3 = i1 * i1;

            i4.add(new Predicate() {

                public boolean a(Entity blockposx) {
                    int i4 = (int) blockposx.c(blockpos);

                    return (i0 < 0 || i4 >= i2) && (i1 < 0 || i4 <= i3);
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            });
        }

        return i4;
    }

    private static List g(Map map) {
        ArrayList arraylist = Lists.newArrayList();
        final int i3 = a(a(map, "rym", 0));
        final int i1 = a(a(map, "ry", 359));

        if (map.containsKey("rym") || map.containsKey("ry")) {

            arraylist.add(new Predicate() {

                public boolean a(Entity p_a_1_) {
                    int i3x = PlayerSelector.a((int) Math.floor((double) p_a_1_.y));

                    return i3 > i1 ? i3x >= i3 || i3x <= i1 : i3x >= i3 && i3x <= i1;
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            });
        }

        if (map.containsKey("rxm") || map.containsKey("rx")) {
            arraylist.add(new Predicate() {

                public boolean a(Entity p_a_1_) {
                    int i3x = PlayerSelector.a((int) Math.floor((double) p_a_1_.z));

                    return i3 > i1 ? i3x >= i3 || i3x <= i1 : i3x >= i3 && i3x <= i1;
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            });
        }

        return arraylist;
    }

    private static List a(Map map, Class oclass0, List list, String s0, World world, BlockPos blockpos) {
        ArrayList arraylist = Lists.newArrayList();
        String s1 = b(map, "type");

        s1 = s1 != null && s1.startsWith("!") ? s1.substring(1) : s1;
        boolean flag0 = !s0.equals("e");
        boolean flag1 = s0.equals("r") && s1 != null;
        int i0 = a(map, "dx", 0);
        int i1 = a(map, "dy", 0);
        int i2 = a(map, "dz", 0);
        int i3 = a(map, "r", -1);
        Predicate predicate = Predicates.and(list);
        Predicate predicate1 = Predicates.and(IEntitySelector.a, predicate);

        if (blockpos != null) {
            int i4 = world.j.size();
            int i5 = world.f.size();
            boolean flag2 = i4 < i5 / 16;
            final AxisAlignedBB axisalignedbb;

            if (!map.containsKey("dx") && !map.containsKey("dy") && !map.containsKey("dz")) {
                if (i3 >= 0) {
                    axisalignedbb = new AxisAlignedBB((double) (blockpos.n() - i3), (double) (blockpos.o() - i3), (double) (blockpos.p() - i3), (double) (blockpos.n() + i3 + 1), (double) (blockpos.o() + i3 + 1), (double) (blockpos.p() + i3 + 1));
                    if (flag0 && flag2 && !flag1) {
                        arraylist.addAll(world.b(oclass0, predicate1));
                    }
                    else {
                        arraylist.addAll(world.a(oclass0, axisalignedbb, predicate1));
                    }
                }
                else if (s0.equals("a")) {
                    arraylist.addAll(world.b(oclass0, predicate));
                }
                else if (!s0.equals("p") && (!s0.equals("r") || flag1)) {
                    arraylist.addAll(world.a(oclass0, predicate1));
                }
                else {
                    arraylist.addAll(world.b(oclass0, predicate1));
                }
            }
            else {
                axisalignedbb = a(blockpos, i0, i1, i2);
                if (flag0 && flag2 && !flag1) {
                    Predicate predicate2 = new Predicate() {

                        public boolean a(Entity oclass0) {
                            return oclass0.s >= axisalignedbb.a && oclass0.t >= axisalignedbb.b && oclass0.u >= axisalignedbb.c ? oclass0.s < axisalignedbb.d && oclass0.t < axisalignedbb.e && oclass0.u < axisalignedbb.f : false;
                        }

                        public boolean apply(Object p_apply_1_) {
                            return this.a((Entity) p_apply_1_);
                        }
                    };

                    arraylist.addAll(world.b(oclass0, Predicates.and(predicate1, predicate2)));
                }
                else {
                    arraylist.addAll(world.a(oclass0, axisalignedbb, predicate1));
                }
            }
        }
        else if (s0.equals("a")) {
            arraylist.addAll(world.b(oclass0, predicate));
        }
        else if (!s0.equals("p") && (!s0.equals("r") || flag1)) {
            arraylist.addAll(world.a(oclass0, predicate1));
        }
        else {
            arraylist.addAll(world.b(oclass0, predicate1));
        }

        return arraylist;
    }

    private static List a(List list, Map map, ICommandSender icommandsender, Class oclass0, String s0, final BlockPos blockpos) {
        int i0 = a(map, "c", !s0.equals("a") && !s0.equals("e") ? 1 : 0);

        if (!s0.equals("p") && !s0.equals("a") && !s0.equals("e")) {
            if (s0.equals("r")) {
                Collections.shuffle((List) list);
            }
        }
        else if (blockpos != null) {
            Collections.sort((List) list, new Comparator() {

                public int a(Entity map, Entity icommandsender) {
                    return ComparisonChain.start().compare(map.b(blockpos), icommandsender.b(blockpos)).result();
                }

                public int compare(Object p_compare_1_, Object p_compare_2_) {
                    return this.a((Entity) p_compare_1_, (Entity) p_compare_2_);
                }
            });
        }

        Entity entity = icommandsender.f();

        if (entity != null && oclass0.isAssignableFrom(entity.getClass()) && i0 == 1 && ((List) list).contains(entity) && !"r".equals(s0)) {
            list = Lists.newArrayList(new Entity[]{entity});
        }

        if (i0 != 0) {
            if (i0 < 0) {
                Collections.reverse((List) list);
            }

            list = ((List) list).subList(0, Math.min(Math.abs(i0), ((List) list).size()));
        }

        return (List) list;
    }

    private static AxisAlignedBB a(BlockPos blockpos, int i0, int i1, int i2) {
        boolean flag0 = i0 < 0;
        boolean flag1 = i1 < 0;
        boolean flag2 = i2 < 0;
        int i3 = blockpos.n() + (flag0 ? i0 : 0);
        int i4 = blockpos.o() + (flag1 ? i1 : 0);
        int i5 = blockpos.p() + (flag2 ? i2 : 0);
        int i6 = blockpos.n() + (flag0 ? 0 : i0) + 1;
        int i7 = blockpos.o() + (flag1 ? 0 : i1) + 1;
        int i8 = blockpos.p() + (flag2 ? 0 : i2) + 1;

        return new AxisAlignedBB((double) i3, (double) i4, (double) i5, (double) i6, (double) i7, (double) i8);
    }

    public static int a(int i0) {
        i0 %= 360;
        if (i0 >= 160) {
            i0 -= 360;
        }

        if (i0 < 0) {
            i0 += 360;
        }

        return i0;
    }

    private static BlockPos b(Map map, BlockPos blockpos) {
        return new BlockPos(a(map, "x", blockpos.n()), a(map, "y", blockpos.o()), a(map, "z", blockpos.p()));
    }

    private static boolean h(Map map) {
        Iterator iterator = d.iterator();

        String s0;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            s0 = (String) iterator.next();
        } while (!map.containsKey(s0));

        return true;
    }

    private static int a(Map map, String s0, int i0) {
        return map.containsKey(s0) ? MathHelper.a((String) map.get(s0), i0) : i0;
    }

    private static String b(Map map, String s0) {
        return (String) map.get(s0);
    }

    public static Map a(Map map) {
        HashMap hashmap = Maps.newHashMap();
        Iterator iterator = map.keySet().iterator();

        while (iterator.hasNext()) {
            String s0 = (String) iterator.next();

            if (s0.startsWith("score_") && s0.length() > "score_".length()) {
                hashmap.put(s0.substring("score_".length()), Integer.valueOf(MathHelper.a((String) map.get(s0), 1)));
            }
        }

        return hashmap;
    }

    public static boolean a(String s0) {
        Matcher matcher = a.matcher(s0);

        if (!matcher.matches()) {
            return false;
        }
        else {
            Map map = c(matcher.group(2));
            String s1 = matcher.group(1);
            int i0 = !"a".equals(s1) && !"e".equals(s1) ? 1 : 0;

            return a(map, "c", i0) != 1;
        }
    }

    public static boolean b(String s0) {
        return a.matcher(s0).matches();
    }

    private static Map c(String s0) {
        HashMap hashmap = Maps.newHashMap();

        if (s0 == null) {
            return hashmap;
        }
        else {
            int i0 = 0;
            int i1 = -1;

            for (Matcher matcher = b.matcher(s0); matcher.find(); i1 = matcher.end()) {
                String s1 = null;

                switch (i0++) {
                    case 0:
                        s1 = "x";
                        break;

                    case 1:
                        s1 = "y";
                        break;

                    case 2:
                        s1 = "z";
                        break;

                    case 3:
                        s1 = "r";
                }

                if (s1 != null && matcher.group(1).length() > 0) {
                    hashmap.put(s1, matcher.group(1));
                }
            }

            if (i1 < s0.length()) {
                Matcher matcher1 = c.matcher(i1 == -1 ? s0 : s0.substring(i1));

                while (matcher1.find()) {
                    hashmap.put(matcher1.group(1), matcher1.group(2));
                }
            }

            return hashmap;
        }
    }

}
