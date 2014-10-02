package net.minecraft.command.server;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.*;
import net.canarymod.Canary;
import net.canarymod.api.scoreboard.CanaryScoreboard;
import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;


public class CommandScoreboard extends CommandBase {

    public String c() {
        return "scoreboard";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.scoreboard.usage";
    }

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        if (!this.b(icommandsender, astring)) {
            if (astring.length < 1) {
                throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
            } else {
                if (astring[0].equalsIgnoreCase("objectives")) {
                    if (astring.length == 1) {
                        throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                    }

                    if (astring[1].equalsIgnoreCase("list")) {
                        this.d(icommandsender);
                    } else if (astring[1].equalsIgnoreCase("add")) {
                        if (astring.length < 4) {
                            throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
                        }

                        this.b(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("remove")) {
                        if (astring.length != 3) {
                            throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
                        }

                        this.h(icommandsender, astring[2]);
                    } else {
                        if (!astring[1].equalsIgnoreCase("setdisplay")) {
                            throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                        }

                        if (astring.length != 3 && astring.length != 4) {
                            throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                        }

                        this.j(icommandsender, astring, 2);
                    }
                } else if (astring[0].equalsIgnoreCase("players")) {
                    if (astring.length == 1) {
                        throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                    }

                    if (astring[1].equalsIgnoreCase("list")) {
                        if (astring.length > 3) {
                            throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
                        }

                        this.k(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("add")) {
                        if (astring.length < 5) {
                            throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
                        }

                        this.l(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("remove")) {
                        if (astring.length < 5) {
                            throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
                        }

                        this.l(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("set")) {
                        if (astring.length < 5) {
                            throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
                        }

                        this.l(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("reset")) {
                        if (astring.length != 3 && astring.length != 4) {
                            throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
                        }

                        this.m(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("enable")) {
                        if (astring.length != 4) {
                            throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
                        }

                        this.n(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("test")) {
                        if (astring.length != 5 && astring.length != 6) {
                            throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
                        }

                        this.o(icommandsender, astring, 2);
                    } else {
                        if (!astring[1].equalsIgnoreCase("operation")) {
                            throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                        }

                        if (astring.length != 7) {
                            throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
                        }

                        this.p(icommandsender, astring, 2);
                    }
                } else if (astring[0].equalsIgnoreCase("teams")) {
                    if (astring.length == 1) {
                        throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                    }

                    if (astring[1].equalsIgnoreCase("list")) {
                        if (astring.length > 3) {
                            throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
                        }

                        this.f(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("add")) {
                        if (astring.length < 3) {
                            throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
                        }

                        this.c(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("remove")) {
                        if (astring.length != 3) {
                            throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
                        }

                        this.e(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("empty")) {
                        if (astring.length != 3) {
                            throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
                        }

                        this.i(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("join")) {
                        if (astring.length < 4 && (astring.length != 3 || !(icommandsender instanceof EntityPlayer))) {
                            throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
                        }

                        this.g(icommandsender, astring, 2);
                    } else if (astring[1].equalsIgnoreCase("leave")) {
                        if (astring.length < 3 && !(icommandsender instanceof EntityPlayer)) {
                            throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                        }

                        this.h(icommandsender, astring, 2);
                    } else {
                        if (!astring[1].equalsIgnoreCase("option")) {
                            throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                        }

                        if (astring.length != 4 && astring.length != 5) {
                            throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                        }

                        this.d(icommandsender, astring, 2);
                    }
                }

            }
        }
    }

    private boolean b(ICommandSender icommandsender, String[] astring) throws CommandException {
        int i0 = -1;

        for (int i1 = 0; i1 < astring.length; ++i1) {
            if (this.b(astring, i1) && "*".equals(astring[i1])) {
                if (i0 >= 0) {
                    throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
                }

                i0 = i1;
            }
        }

        if (i0 < 0) {
            return false;
        } else {
            ArrayList arraylist = Lists.newArrayList(this.d().d());
            String s0 = astring[i0];
            ArrayList arraylist1 = Lists.newArrayList();
            Iterator iterator = arraylist.iterator();

            while (iterator.hasNext()) {
                String s1 = (String) iterator.next();

                astring[i0] = s1;

                try {
                    this.a(icommandsender, astring);
                    arraylist1.add(s1);
                } catch (CommandException commandexception) {
                    ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(commandexception.getMessage(), commandexception.a());

                    chatcomponenttranslation.b().a(EnumChatFormatting.RED);
                    icommandsender.a(chatcomponenttranslation);
                }
            }

            astring[i0] = s0;
            icommandsender.a(CommandResultStats.Type.AFFECTED_ENTITIES, arraylist1.size());
            if (arraylist1.size() == 0) {
                throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
            } else {
                return true;
            }
        }
    }

    protected Scoreboard d() {
        // CanaryMod: get the default scoreboard
        return ((CanaryScoreboard)Canary.scoreboards().getScoreboard()).getHandle();
        // CanaryMod: end
    }

    protected ScoreObjective a(String s0, boolean flag0) throws CommandException {
        Scoreboard scoreboard = this.d();
        ScoreObjective scoreobjective = scoreboard.b(s0);

        if (scoreobjective == null) {
            throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { s0});
        } else if (flag0 && scoreobjective.c().b()) {
            throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { s0});
        } else {
            return scoreobjective;
        }
    }

    protected ScorePlayerTeam e(String s0) throws CommandException {
        Scoreboard scoreboard = this.d();
        ScorePlayerTeam scoreplayerteam = scoreboard.d(s0);

        if (scoreplayerteam == null) {
            throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { s0});
        } else {
            return scoreplayerteam;
        }
    }

    protected void b(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        String s0 = astring[i0++];
        String s1 = astring[i0++];
        Scoreboard scoreboard = this.d();
        IScoreObjectiveCriteria iscoreobjectivecriteria = (IScoreObjectiveCriteria) IScoreObjectiveCriteria.a.get(s1);

        if (iscoreobjectivecriteria == null) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { s1});
        } else if (scoreboard.b(s0) != null) {
            throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { s0});
        } else if (s0.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { s0, Integer.valueOf(16)});
        } else if (s0.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
        } else {
            if (astring.length > i0) {
                String s2 = a(icommandsender, astring, i0).c();

                if (s2.length() > 32) {
                    throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { s2, Integer.valueOf(32)});
                }

                if (s2.length() > 0) {
                    scoreboard.a(s0, iscoreobjectivecriteria).a(s2);
                } else {
                    scoreboard.a(s0, iscoreobjectivecriteria);
                }
            } else {
                scoreboard.a(s0, iscoreobjectivecriteria);
            }

            a(icommandsender, this, "commands.scoreboard.objectives.add.success", new Object[] { s0});
        }
    }

    protected void c(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        String s0 = astring[i0++];
        Scoreboard scoreboard = this.d();

        if (scoreboard.d(s0) != null) {
            throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { s0});
        } else if (s0.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { s0, Integer.valueOf(16)});
        } else if (s0.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
        } else {
            if (astring.length > i0) {
                String s1 = a(icommandsender, astring, i0).c();

                if (s1.length() > 32) {
                    throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { s1, Integer.valueOf(32)});
                }

                if (s1.length() > 0) {
                    scoreboard.e(s0).a(s1);
                } else {
                    scoreboard.e(s0);
                }
            } else {
                scoreboard.e(s0);
            }

            a(icommandsender, this, "commands.scoreboard.teams.add.success", new Object[] { s0});
        }
    }

    protected void d(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        ScorePlayerTeam scoreplayerteam = this.e(astring[i0++]);

        if (scoreplayerteam != null) {
            String s0 = astring[i0++].toLowerCase();

            if (!s0.equalsIgnoreCase("color") && !s0.equalsIgnoreCase("friendlyfire") && !s0.equalsIgnoreCase("seeFriendlyInvisibles") && !s0.equalsIgnoreCase("nametagVisibility") && !s0.equalsIgnoreCase("deathMessageVisibility")) {
                throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
            } else if (astring.length == 4) {
                if (s0.equalsIgnoreCase("color")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s0, a(EnumChatFormatting.a(true, false))});
                } else if (!s0.equalsIgnoreCase("friendlyfire") && !s0.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    if (!s0.equalsIgnoreCase("nametagVisibility") && !s0.equalsIgnoreCase("deathMessageVisibility")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                    } else {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s0, a(Team.EnumVisible.a())});
                    }
                } else {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s0, a(Arrays.asList(new String[] { "true", "false"}))});
                }
            } else {
                String s1 = astring[i0];

                if (s0.equalsIgnoreCase("color")) {
                    EnumChatFormatting enumchatformatting = EnumChatFormatting.b(s1);

                    if (enumchatformatting == null || enumchatformatting.c()) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s0, a(EnumChatFormatting.a(true, false))});
                    }

                    scoreplayerteam.a(enumchatformatting);
                    scoreplayerteam.b(enumchatformatting.toString());
                    scoreplayerteam.c(EnumChatFormatting.RESET.toString());
                } else if (s0.equalsIgnoreCase("friendlyfire")) {
                    if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s0, a(Arrays.asList(new String[] { "true", "false"}))});
                    }

                    scoreplayerteam.a(s1.equalsIgnoreCase("true"));
                } else if (s0.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s0, a(Arrays.asList(new String[] { "true", "false"}))});
                    }

                    scoreplayerteam.b(s1.equalsIgnoreCase("true"));
                } else {
                    Team.EnumVisible team_enumvisible;

                    if (s0.equalsIgnoreCase("nametagVisibility")) {
                        team_enumvisible = Team.EnumVisible.a(s1);
                        if (team_enumvisible == null) {
                            throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s0, a(Team.EnumVisible.a())});
                        }

                        scoreplayerteam.a(team_enumvisible);
                    } else if (s0.equalsIgnoreCase("deathMessageVisibility")) {
                        team_enumvisible = Team.EnumVisible.a(s1);
                        if (team_enumvisible == null) {
                            throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s0, a(Team.EnumVisible.a())});
                        }

                        scoreplayerteam.b(team_enumvisible);
                    }
                }

                a(icommandsender, this, "commands.scoreboard.teams.option.success", new Object[] { s0, scoreplayerteam.b(), s1});
            }
        }
    }

    protected void e(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();
        ScorePlayerTeam scoreplayerteam = this.e(astring[i0]);

        if (scoreplayerteam != null) {
            scoreboard.d(scoreplayerteam);
            a(icommandsender, this, "commands.scoreboard.teams.remove.success", new Object[] { scoreplayerteam.b()});
        }
    }

    protected void f(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();

        if (astring.length > i0) {
            ScorePlayerTeam scoreplayerteam = this.e(astring[i0]);

            if (scoreplayerteam == null) {
                return;
            }

            Collection collection = scoreplayerteam.d();

            icommandsender.a(CommandResultStats.Type.QUERY_RESULT, collection.size());
            if (collection.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { scoreplayerteam.b()});
            }

            ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(collection.size()), scoreplayerteam.b()});

            chatcomponenttranslation.b().a(EnumChatFormatting.DARK_GREEN);
            icommandsender.a(chatcomponenttranslation);
            icommandsender.a(new ChatComponentText(a(collection.toArray())));
        } else {
            Collection collection1 = scoreboard.g();

            icommandsender.a(CommandResultStats.Type.QUERY_RESULT, collection1.size());
            if (collection1.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
            }

            ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(collection1.size())});

            chatcomponenttranslation1.b().a(EnumChatFormatting.DARK_GREEN);
            icommandsender.a(chatcomponenttranslation1);
            Iterator iterator = collection1.iterator();

            while (iterator.hasNext()) {
                ScorePlayerTeam scoreplayerteam1 = (ScorePlayerTeam) iterator.next();

                icommandsender.a(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { scoreplayerteam1.b(), scoreplayerteam1.c(), Integer.valueOf(scoreplayerteam1.d().size())}));
            }
        }

    }

    protected void g(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();
        String s0 = astring[i0++];
        HashSet hashset = Sets.newHashSet();
        HashSet hashset1 = Sets.newHashSet();
        String s1;

        if (icommandsender instanceof EntityPlayer && i0 == astring.length) {
            s1 = b(icommandsender).d_();
            if (scoreboard.a(s1, s0)) {
                hashset.add(s1);
            } else {
                hashset1.add(s1);
            }
        } else {
            while (i0 < astring.length) {
                s1 = astring[i0++];
                if (s1.startsWith("@")) {
                    List list = c(icommandsender, s1);
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        Entity entity = (Entity) iterator.next();
                        String s2 = e(icommandsender, entity.aJ().toString());

                        if (scoreboard.a(s2, s0)) {
                            hashset.add(s2);
                        } else {
                            hashset1.add(s2);
                        }
                    }
                } else {
                    String s3 = e(icommandsender, s1);

                    if (scoreboard.a(s3, s0)) {
                        hashset.add(s3);
                    } else {
                        hashset1.add(s3);
                    }
                }
            }
        }

        if (!hashset.isEmpty()) {
            icommandsender.a(CommandResultStats.Type.AFFECTED_ENTITIES, hashset.size());
            a(icommandsender, this, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(hashset.size()), s0, a(hashset.toArray(new String[0]))});
        }

        if (!hashset1.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { Integer.valueOf(hashset1.size()), s0, a(hashset1.toArray(new String[0]))});
        }
    }

    protected void h(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();
        HashSet hashset = Sets.newHashSet();
        HashSet hashset1 = Sets.newHashSet();
        String s0;

        if (icommandsender instanceof EntityPlayer && i0 == astring.length) {
            s0 = b(icommandsender).d_();
            if (scoreboard.f(s0)) {
                hashset.add(s0);
            } else {
                hashset1.add(s0);
            }
        } else {
            while (i0 < astring.length) {
                s0 = astring[i0++];
                if (s0.startsWith("@")) {
                    List list = c(icommandsender, s0);
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        Entity entity = (Entity) iterator.next();
                        String s1 = e(icommandsender, entity.aJ().toString());

                        if (scoreboard.f(s1)) {
                            hashset.add(s1);
                        } else {
                            hashset1.add(s1);
                        }
                    }
                } else {
                    String s2 = e(icommandsender, s0);

                    if (scoreboard.f(s2)) {
                        hashset.add(s2);
                    } else {
                        hashset1.add(s2);
                    }
                }
            }
        }

        if (!hashset.isEmpty()) {
            icommandsender.a(CommandResultStats.Type.AFFECTED_ENTITIES, hashset.size());
            a(icommandsender, this, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(hashset.size()), a(hashset.toArray(new String[0]))});
        }

        if (!hashset1.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(hashset1.size()), a(hashset1.toArray(new String[0]))});
        }
    }

    protected void i(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();
        ScorePlayerTeam scoreplayerteam = this.e(astring[i0]);

        if (scoreplayerteam != null) {
            ArrayList arraylist = Lists.newArrayList(scoreplayerteam.d());

            icommandsender.a(CommandResultStats.Type.AFFECTED_ENTITIES, arraylist.size());
            if (arraylist.isEmpty()) {
                throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { scoreplayerteam.b()});
            } else {
                Iterator iterator = arraylist.iterator();

                while (iterator.hasNext()) {
                    String s0 = (String) iterator.next();

                    scoreboard.a(s0, scoreplayerteam);
                }

                a(icommandsender, this, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(arraylist.size()), scoreplayerteam.b()});
            }
        }
    }

    protected void h(ICommandSender icommandsender, String s0) throws CommandException {
        Scoreboard scoreboard = this.d();
        ScoreObjective scoreobjective = this.a(s0, false);

        scoreboard.k(scoreobjective);
        a(icommandsender, this, "commands.scoreboard.objectives.remove.success", new Object[] { s0});
    }

    protected void d(ICommandSender icommandsender) throws CommandException {
        Scoreboard scoreboard = this.d();
        Collection collection = scoreboard.c();

        if (collection.size() <= 0) {
            throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
        } else {
            ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(collection.size())});

            chatcomponenttranslation.b().a(EnumChatFormatting.DARK_GREEN);
            icommandsender.a(chatcomponenttranslation);
            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                ScoreObjective scoreobjective = (ScoreObjective) iterator.next();

                icommandsender.a(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { scoreobjective.b(), scoreobjective.d(), scoreobjective.c().a()}));
            }

        }
    }

    protected void j(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();
        String s0 = astring[i0++];
        int i1 = Scoreboard.i(s0);
        ScoreObjective scoreobjective = null;

        if (astring.length == 4) {
            scoreobjective = this.a(astring[i0], false);
        }

        if (i1 < 0) {
            throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { s0});
        } else {
            scoreboard.a(i1, scoreobjective);
            if (scoreobjective != null) {
                a(icommandsender, this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.b(i1), scoreobjective.b()});
            } else {
                a(icommandsender, this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.b(i1)});
            }

        }
    }

    protected void k(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();

        if (astring.length > i0) {
            String s0 = e(icommandsender, astring[i0]);
            Map map = scoreboard.c(s0);

            icommandsender.a(CommandResultStats.Type.QUERY_RESULT, map.size());
            if (map.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { s0});
            }

            ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(map.size()), s0});

            chatcomponenttranslation.b().a(EnumChatFormatting.DARK_GREEN);
            icommandsender.a(chatcomponenttranslation);
            Iterator iterator = map.values().iterator();

            while (iterator.hasNext()) {
                Score score = (Score) iterator.next();

                icommandsender.a(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(score.c()), score.d().d(), score.d().b()}));
            }
        } else {
            Collection collection = scoreboard.d();

            icommandsender.a(CommandResultStats.Type.QUERY_RESULT, collection.size());
            if (collection.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
            }

            ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(collection.size())});

            chatcomponenttranslation1.b().a(EnumChatFormatting.DARK_GREEN);
            icommandsender.a(chatcomponenttranslation1);
            icommandsender.a(new ChatComponentText(a(collection.toArray())));
        }

    }

    protected void l(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        String s0 = astring[i0 - 1];
        int i1 = i0;
        String s1 = e(icommandsender, astring[i0++]);
        ScoreObjective scoreobjective = this.a(astring[i0++], true);
        int i2 = s0.equalsIgnoreCase("set") ? a(astring[i0++]) : a(astring[i0++], 0);

        if (astring.length > i0) {
            Entity entity = b(icommandsender, astring[i1]);

            try {
                NBTTagCompound nbttagcompound = JsonToNBT.a(a(astring, i0));
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                entity.e(nbttagcompound1);
                if (!CommandTestForBlock.a(nbttagcompound, nbttagcompound1, true)) {
                    throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] { s1});
                }
            } catch (NBTException nbtexception) {
                throw new CommandException("commands.scoreboard.players.set.tagError", new Object[] { nbtexception.getMessage()});
            }
        }

        Scoreboard scoreboard = this.d();
        Score score = scoreboard.c(s1, scoreobjective);

        if (s0.equalsIgnoreCase("set")) {
            score.c(i2);
        } else if (s0.equalsIgnoreCase("add")) {
            score.a(i2);
        } else {
            score.b(i2);
        }

        a(icommandsender, this, "commands.scoreboard.players.set.success", new Object[] { scoreobjective.b(), s1, Integer.valueOf(score.c())});
    }

    protected void m(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();
        String s0 = e(icommandsender, astring[i0++]);

        if (astring.length > i0) {
            ScoreObjective scoreobjective = this.a(astring[i0++], false);

            scoreboard.d(s0, scoreobjective);
            a(icommandsender, this, "commands.scoreboard.players.resetscore.success", new Object[] { scoreobjective.b(), s0});
        } else {
            scoreboard.d(s0, (ScoreObjective) null);
            a(icommandsender, this, "commands.scoreboard.players.reset.success", new Object[] { s0});
        }

    }

    protected void n(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();
        String s0 = d(icommandsender, astring[i0++]);
        ScoreObjective scoreobjective = this.a(astring[i0], false);

        if (scoreobjective.c() != IScoreObjectiveCriteria.c) {
            throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] { scoreobjective.b()});
        } else {
            Score score = scoreboard.c(s0, scoreobjective);

            score.a(false);
            a(icommandsender, this, "commands.scoreboard.players.enable.success", new Object[] { scoreobjective.b(), s0});
        }
    }

    protected void o(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();
        String s0 = e(icommandsender, astring[i0++]);
        ScoreObjective scoreobjective = this.a(astring[i0++], false);

        if (!scoreboard.b(s0, scoreobjective)) {
            throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] { scoreobjective.b(), s0});
        } else {
            int i1 = astring[i0].equals("*") ? Integer.MIN_VALUE : a(astring[i0]);

            ++i0;
            int i2 = i0 < astring.length && !astring[i0].equals("*") ? a(astring[i0], i1) : Integer.MAX_VALUE;
            Score score = scoreboard.c(s0, scoreobjective);

            if (score.c() >= i1 && score.c() <= i2) {
                a(icommandsender, this, "commands.scoreboard.players.test.success", new Object[] { Integer.valueOf(score.c()), Integer.valueOf(i1), Integer.valueOf(i2)});
            } else {
                throw new CommandException("commands.scoreboard.players.test.failed", new Object[] { Integer.valueOf(score.c()), Integer.valueOf(i1), Integer.valueOf(i2)});
            }
        }
    }

    protected void p(ICommandSender icommandsender, String[] astring, int i0) throws CommandException {
        Scoreboard scoreboard = this.d();
        String s0 = e(icommandsender, astring[i0++]);
        ScoreObjective scoreobjective = this.a(astring[i0++], true);
        String s1 = astring[i0++];
        String s2 = e(icommandsender, astring[i0++]);
        ScoreObjective scoreobjective1 = this.a(astring[i0], false);
        Score score = scoreboard.c(s0, scoreobjective);

        if (!scoreboard.b(s2, scoreobjective1)) {
            throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] { scoreobjective1.b(), s2});
        } else {
            Score score1 = scoreboard.c(s2, scoreobjective1);

            if (s1.equals("+=")) {
                score.c(score.c() + score1.c());
            } else if (s1.equals("-=")) {
                score.c(score.c() - score1.c());
            } else if (s1.equals("*=")) {
                score.c(score.c() * score1.c());
            } else if (s1.equals("/=")) {
                if (score1.c() != 0) {
                    score.c(score.c() / score1.c());
                }
            } else if (s1.equals("%=")) {
                if (score1.c() != 0) {
                    score.c(score.c() % score1.c());
                }
            } else if (s1.equals("=")) {
                score.c(score1.c());
            } else if (s1.equals("<")) {
                score.c(Math.min(score.c(), score1.c()));
            } else if (s1.equals(">")) {
                score.c(Math.max(score.c(), score1.c()));
            } else {
                if (!s1.equals("><")) {
                    throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] { s1});
                }

                int i1 = score.c();

                score.c(score1.c());
                score1.c(i1);
            }

            a(icommandsender, this, "commands.scoreboard.players.operation.success", new Object[0]);
        }
    }

    public List a(ICommandSender icommandsender, String[] astring, BlockPos blockpos) {
        if (astring.length == 1) {
            return a(astring, new String[] { "objectives", "players", "teams"});
        } else {
            if (astring[0].equalsIgnoreCase("objectives")) {
                if (astring.length == 2) {
                    return a(astring, new String[] { "list", "add", "remove", "setdisplay"});
                }

                if (astring[1].equalsIgnoreCase("add")) {
                    if (astring.length == 4) {
                        Set set = IScoreObjectiveCriteria.a.keySet();

                        return a(astring, set);
                    }
                } else if (astring[1].equalsIgnoreCase("remove")) {
                    if (astring.length == 3) {
                        return a(astring, this.a(false));
                    }
                } else if (astring[1].equalsIgnoreCase("setdisplay")) {
                    if (astring.length == 3) {
                        return a(astring, Scoreboard.h());
                    }

                    if (astring.length == 4) {
                        return a(astring, this.a(false));
                    }
                }
            } else if (astring[0].equalsIgnoreCase("players")) {
                if (astring.length == 2) {
                    return a(astring, new String[] { "set", "add", "remove", "reset", "list", "enable", "test", "operation"});
                }

                if (!astring[1].equalsIgnoreCase("set") && !astring[1].equalsIgnoreCase("add") && !astring[1].equalsIgnoreCase("remove") && !astring[1].equalsIgnoreCase("reset")) {
                    if (astring[1].equalsIgnoreCase("enable")) {
                        if (astring.length == 3) {
                            return a(astring, MinecraftServer.M().I());
                        }

                        if (astring.length == 4) {
                            return a(astring, this.e());
                        }
                    } else if (!astring[1].equalsIgnoreCase("list") && !astring[1].equalsIgnoreCase("test")) {
                        if (astring[1].equalsIgnoreCase("operation")) {
                            if (astring.length == 3) {
                                return a(astring, this.d().d());
                            }

                            if (astring.length == 4) {
                                return a(astring, this.a(true));
                            }

                            if (astring.length == 5) {
                                return a(astring, new String[] { "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><"});
                            }

                            if (astring.length == 6) {
                                return a(astring, MinecraftServer.M().I());
                            }

                            if (astring.length == 7) {
                                return a(astring, this.a(false));
                            }
                        }
                    } else {
                        if (astring.length == 3) {
                            return a(astring, this.d().d());
                        }

                        if (astring.length == 4 && astring[1].equalsIgnoreCase("test")) {
                            return a(astring, this.a(false));
                        }
                    }
                } else {
                    if (astring.length == 3) {
                        return a(astring, MinecraftServer.M().I());
                    }

                    if (astring.length == 4) {
                        return a(astring, this.a(true));
                    }
                }
            } else if (astring[0].equalsIgnoreCase("teams")) {
                if (astring.length == 2) {
                    return a(astring, new String[] { "add", "remove", "join", "leave", "empty", "list", "option"});
                }

                if (astring[1].equalsIgnoreCase("join")) {
                    if (astring.length == 3) {
                        return a(astring, this.d().f());
                    }

                    if (astring.length >= 4) {
                        return a(astring, MinecraftServer.M().I());
                    }
                } else {
                    if (astring[1].equalsIgnoreCase("leave")) {
                        return a(astring, MinecraftServer.M().I());
                    }

                    if (!astring[1].equalsIgnoreCase("empty") && !astring[1].equalsIgnoreCase("list") && !astring[1].equalsIgnoreCase("remove")) {
                        if (astring[1].equalsIgnoreCase("option")) {
                            if (astring.length == 3) {
                                return a(astring, this.d().f());
                            }

                            if (astring.length == 4) {
                                return a(astring, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility"});
                            }

                            if (astring.length == 5) {
                                if (astring[3].equalsIgnoreCase("color")) {
                                    return a(astring, EnumChatFormatting.a(true, false));
                                }

                                if (astring[3].equalsIgnoreCase("nametagVisibility") || astring[3].equalsIgnoreCase("deathMessageVisibility")) {
                                    return a(astring, Team.EnumVisible.a());
                                }

                                if (astring[3].equalsIgnoreCase("friendlyfire") || astring[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                                    return a(astring, new String[] { "true", "false"});
                                }
                            }
                        }
                    } else if (astring.length == 3) {
                        return a(astring, this.d().f());
                    }
                }
            }

            return null;
        }
    }

    protected List a(boolean flag0) {
        Collection collection = this.d().c();
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            ScoreObjective scoreobjective = (ScoreObjective) iterator.next();

            if (!flag0 || !scoreobjective.c().b()) {
                arraylist.add(scoreobjective.b());
            }
        }

        return arraylist;
    }

    protected List e() {
        Collection collection = this.d().c();
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            ScoreObjective scoreobjective = (ScoreObjective) iterator.next();

            if (scoreobjective.c() == IScoreObjectiveCriteria.c) {
                arraylist.add(scoreobjective.b());
            }
        }

        return arraylist;
    }

    public boolean b(String[] astring, int i0) {
        return !astring[0].equalsIgnoreCase("players") ? (astring[0].equalsIgnoreCase("teams") ? i0 == 2 : false) : (astring.length > 1 && astring[1].equalsIgnoreCase("operation") ? i0 == 2 || i0 == 5 : i0 == 2);
    }
}
