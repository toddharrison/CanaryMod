package net.minecraft.command;


import net.canarymod.Canary;
import net.canarymod.chat.MessageReceiver;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class CommandHandler implements ICommandManager {

    private static final Logger a = LogManager.getLogger();
    private final Map b = new HashMap();
    private final Set c = new HashSet();

    public int a(ICommandSender icommandsender, String s0) {
        s0 = s0.trim();
        if (s0.startsWith("/")) {
            s0 = s0.substring(1);
        }

        String[] astring = s0.split(" ");
        String s1 = astring[0];

        astring = a(astring);
        ICommand icommand = (ICommand) this.b.get(s1);
        int i0 = this.a(icommand, astring);
        int i1 = 0;

        ChatComponentTranslation chatcomponenttranslation;

        try {
            if (icommand == null) {
                throw new CommandNotFoundException();
            }

            if (icommand.a(icommandsender)) {
                if (i0 > -1) {
                    EntityPlayerMP[] aentityplayermp = PlayerSelector.c(icommandsender, astring[i0]);
                    String s2 = astring[i0];
                    EntityPlayerMP[] aentityplayermp1 = aentityplayermp;
                    int i2 = aentityplayermp.length;

                    for (int i3 = 0; i3 < i2; ++i3) {
                        EntityPlayerMP entityplayermp = aentityplayermp1[i3];

                        astring[i0] = entityplayermp.b_();

                        try {
                            icommand.b(icommandsender, astring);
                            ++i1;
                        }
                        catch (CommandException commandexception) {
                            ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation(commandexception.getMessage(), commandexception.a());

                            chatcomponenttranslation1.b().a(EnumChatFormatting.RED);
                            icommandsender.a(chatcomponenttranslation1);
                        }
                    }

                    astring[i0] = s2;
                }
                else {
                    icommand.b(icommandsender, astring);
                    ++i1;
                }
            }
            else {
                ChatComponentTranslation chatcomponenttranslation2 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);

                chatcomponenttranslation2.b().a(EnumChatFormatting.RED);
                icommandsender.a(chatcomponenttranslation2);
            }
        }
        catch (WrongUsageException wrongusageexception) {
            chatcomponenttranslation = new ChatComponentTranslation("commands.generic.usage", new Object[]{ new ChatComponentTranslation(wrongusageexception.getMessage(), wrongusageexception.a()) });
            chatcomponenttranslation.b().a(EnumChatFormatting.RED);
            icommandsender.a(chatcomponenttranslation);
        }
        catch (CommandException commandexception1) {
            chatcomponenttranslation = new ChatComponentTranslation(commandexception1.getMessage(), commandexception1.a());
            chatcomponenttranslation.b().a(EnumChatFormatting.RED);
            icommandsender.a(chatcomponenttranslation);
        }
        catch (Throwable throwable) {
            chatcomponenttranslation = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
            chatcomponenttranslation.b().a(EnumChatFormatting.RED);
            icommandsender.a(chatcomponenttranslation);
            a.error("Couldn\'t process command", throwable);
        }

        return i1;
    }

    public ICommand a(ICommand icommand) {
        List list = icommand.b();

        this.b.put(icommand.c(), icommand);
        this.c.add(icommand);
        if (list != null) {
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                String s0 = (String) iterator.next();
                ICommand icommand1 = (ICommand) this.b.get(s0);

                if (icommand1 == null || !icommand1.c().equals(s0)) {
                    this.b.put(s0, icommand);
                }
            }
        }

        return icommand;
    }

    private static String[] a(String[] astring) {
        String[] astring1 = new String[astring.length - 1];

        for (int i0 = 1; i0 < astring.length; ++i0) {
            astring1[i0 - 1] = astring[i0];
        }

        return astring1;
    }

    public List b(ICommandSender icommandsender, String s0) {
        String[] astring = s0.split(" ", -1);
        String s1 = astring[0];

        if (astring.length == 1) {
            ArrayList arraylist = new ArrayList();
            Iterator iterator = this.b.entrySet().iterator();

            //CanaryMod: Add possible Canary command matches
            MessageReceiver msgrec = icommandsender instanceof EntityPlayerMP ? ((EntityPlayerMP) icommandsender).getPlayer() : Canary.getServer(); // I don't see a command block doing tab complete
            arraylist.addAll(Canary.commands().matchCommandNames(msgrec, s1, false));
            //

            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();

                if (CommandBase.a(s1, (String) entry.getKey()) && ((ICommand) entry.getValue()).a(icommandsender)) {
                    arraylist.add(entry.getKey());
                }
            }

            return arraylist;
        }
        else {
            if (astring.length > 1) {
                // CanaryMod: Inject out commands then revert to vanilla commands
                MessageReceiver msgrec = icommandsender instanceof EntityPlayerMP ? ((EntityPlayerMP) icommandsender).getPlayer() : Canary.getServer(); // I don't see a command block doing tab complete
                List<String> rez = Canary.commands().tabComplete(msgrec, s1, a(astring));
                if (rez != null) {
                    return rez;
                }
                //

                ICommand icommand = (ICommand) this.b.get(s1);

                if (icommand != null) {
                    return icommand.a(icommandsender, a(astring));
                }
            }

            return null;
        }
    }

    public List a(ICommandSender icommandsender) {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = this.c.iterator();

        //CanaryMod: Add possible Canary command matches
        MessageReceiver msgrec = icommandsender instanceof EntityPlayerMP ? ((EntityPlayerMP) icommandsender).getPlayer() : Canary.getServer(); // I don't see a command block doing tab complete
        arraylist.addAll(Canary.commands().matchCommandNames(msgrec, "", false));
        //

        while (iterator.hasNext()) {
            ICommand icommand = (ICommand) iterator.next();

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
                if (icommand.a(astring, i0) && PlayerSelector.a(astring[i0])) {
                    return i0;
                }
            }

            return -1;
        }
    }

}
