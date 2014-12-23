package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.canarymod.Canary;
import net.canarymod.chat.MessageReceiver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;

public class CommandHandler implements ICommandManager {

    private static final Logger a = LogManager.getLogger();
    private final Map b = Maps.newHashMap();
    private final Set c = Sets.newHashSet();

    public int a(ICommandSender icommandsender, String s0) {
        s0 = s0.trim();
        if (s0.startsWith("/")) {
            s0 = s0.substring(1);
        }

        String[] astring = s0.split(" ");
        String s1 = astring[0];

        astring = a(astring);
        ICommand icommand = (ICommand)this.b.get(s1);
        int i0 = this.a(icommand, astring);
        int i1 = 0;

        ChatComponentTranslation chatcomponenttranslation;

        if (icommand == null) {
            chatcomponenttranslation = new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
            chatcomponenttranslation.b().a(EnumChatFormatting.RED);
            icommandsender.a(chatcomponenttranslation);
        }
        else if (icommand.a(icommandsender)) {
            if (i0 > -1) {
                List list = PlayerSelector.b(icommandsender, astring[i0], Entity.class);
                String s2 = astring[i0];

                icommandsender.a(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    Entity entity = (Entity)iterator.next();

                    astring[i0] = entity.aJ().toString();
                    if (this.a(icommandsender, astring, icommand, s0)) {
                        ++i1;
                    }
                }

                astring[i0] = s2;
            }
            else {
                icommandsender.a(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
                if (this.a(icommandsender, astring, icommand, s0)) {
                    ++i1;
                }
            }
        }
        else {
            chatcomponenttranslation = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
            chatcomponenttranslation.b().a(EnumChatFormatting.RED);
            icommandsender.a(chatcomponenttranslation);
        }

        icommandsender.a(CommandResultStats.Type.SUCCESS_COUNT, i1);
        return i1;
    }

    protected boolean a(ICommandSender icommandsender, String[] astring, ICommand icommand, String s0) {
        ChatComponentTranslation chatcomponenttranslation;

        try {
            icommand.a(icommandsender, astring);
            return true;
        }
        catch (WrongUsageException wrongusageexception) {
            chatcomponenttranslation = new ChatComponentTranslation("commands.generic.usage", new Object[]{ new ChatComponentTranslation(wrongusageexception.getMessage(), wrongusageexception.a()) });
            chatcomponenttranslation.b().a(EnumChatFormatting.RED);
            icommandsender.a(chatcomponenttranslation);
        }
        catch (CommandException commandexception) {
            chatcomponenttranslation = new ChatComponentTranslation(commandexception.getMessage(), commandexception.a());
            chatcomponenttranslation.b().a(EnumChatFormatting.RED);
            icommandsender.a(chatcomponenttranslation);
        }
        catch (Throwable throwable) {
            chatcomponenttranslation = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
            chatcomponenttranslation.b().a(EnumChatFormatting.RED);
            icommandsender.a(chatcomponenttranslation);
            a.error("Couldn\'t process command: \'" + s0 + "\'", throwable);
        }

        return false;
    }

    public ICommand a(ICommand icommand) {
        this.b.put(icommand.c(), icommand);
        this.c.add(icommand);
        Iterator iterator = icommand.b().iterator();

        while (iterator.hasNext()) {
            String s0 = (String)iterator.next();
            ICommand icommand1 = (ICommand)this.b.get(s0);

            if (icommand1 == null || !icommand1.c().equals(s0)) {
                this.b.put(s0, icommand);
            }
        }

        return icommand;
    }

    private static String[] a(String[] astring) {
        String[] astring1 = new String[astring.length - 1];

        System.arraycopy(astring, 1, astring1, 0, astring.length - 1);
        return astring1;
    }

    public List a(ICommandSender icommandsender, String s0, BlockPos blockpos) {
        String[] astring = s0.split(" ", -1);
        String s1 = astring[0];

        if (astring.length == 1) {
            ArrayList arraylist = Lists.newArrayList();
            Iterator iterator = this.b.entrySet().iterator();

            //CanaryMod: Add possible Canary command matches
            MessageReceiver msgrec = icommandsender instanceof EntityPlayerMP ? ((EntityPlayerMP)icommandsender).getPlayer() : Canary.getServer(); // I don't see a command block doing tab complete
            arraylist.addAll(Canary.commands().matchCommandNames(msgrec, s1, false));
            //

            while (iterator.hasNext()) {
                Entry entry = (Entry)iterator.next();

                if (CommandBase.a(s1, (String)entry.getKey()) && ((ICommand)entry.getValue()).a(icommandsender)) {
                    arraylist.add(entry.getKey());
                }
            }

            return arraylist;
        }
        else {
            if (astring.length > 1) {
                // CanaryMod: Inject out commands then revert to vanilla commands
                MessageReceiver msgrec = icommandsender instanceof EntityPlayerMP ? ((EntityPlayerMP)icommandsender).getPlayer() : Canary.getServer(); // I don't see a command block doing tab complete
                List<String> rez = Canary.commands().tabComplete(msgrec, s1, a(astring));
                if (rez != null) {
                    return rez;
                }
                //

                ICommand icommand = (ICommand)this.b.get(s1);

                if (icommand != null && icommand.a(icommandsender)) {
                    return icommand.a(icommandsender, a(astring), blockpos);
                }
            }

            return null;
        }
    }

    public List a(ICommandSender icommandsender) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.c.iterator();

        //CanaryMod: Add possible Canary command matches
        MessageReceiver msgrec = icommandsender instanceof EntityPlayerMP ? ((EntityPlayerMP)icommandsender).getPlayer() : Canary.getServer(); // I don't see a command block doing tab complete
        arraylist.addAll(Canary.commands().matchCommandNames(msgrec, "", false));
        //

        while (iterator.hasNext()) {
            ICommand icommand = (ICommand)iterator.next();

            if (icommand.a(icommandsender)) {
                arraylist.add(icommand);
            }
        }

        return arraylist;
    }

    public Map a() {
        return this.b;
    }

    private int a(ICommand icommand, String[] astring) {
        if (icommand == null) {
            return -1;
        }
        else {
            for (int i0 = 0; i0 < astring.length; ++i0) {
                if (icommand.b(astring, i0) && PlayerSelector.a(astring[i0])) {
                    return i0;
                }
            }

            return -1;
        }
    }
}
