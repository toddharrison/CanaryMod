package net.minecraft.command;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.GameRules;

import java.util.Iterator;
import java.util.List;

public class CommandGameRule extends CommandBase {

    public String c() {
        return "gamerule";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.gamerule.usage";
    }

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        // CanaryMod: multiworld fixes
        GameRules gamerules = this.getGameRules(icommandsender);
        //
        String s0 = astring.length > 0 ? astring[0] : "";
        String s1 = astring.length > 1 ? a(astring, 1) : "";
            
        switch (astring.length) {
            case 0:
                icommandsender.a(new ChatComponentText(a(gamerules.b())));
                break;

            case 1:
                if (!gamerules.e(s0)) {
                    throw new CommandException("commands.gamerule.norule", new Object[] { s0});
                }

                String s2 = gamerules.a(s0);

                icommandsender.a((new ChatComponentText(s0)).a(" = ").a(s2));
                icommandsender.a(CommandResultStats.Type.QUERY_RESULT, gamerules.c(s0));
                break;

            default:
                if (gamerules.a(s0, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(s1) && !"false".equals(s1)) {
                    throw new CommandException("commands.generic.boolean.invalid", new Object[] { s1});
                }

                gamerules.a(s0, s1);
                a(gamerules, s0);
                a(icommandsender, this, "commands.gamerule.success", new Object[0]);
        }
    }

    public static void a(GameRules gamerules, String s0) {
        if ("reducedDebugInfo".equals(s0)) {
            int i0 = gamerules.b(s0) ? 22 : 23;
            Iterator iterator = MinecraftServer.M().an().e.iterator();

            while (iterator.hasNext()) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) iterator.next();

                entityplayermp.a.a((Packet) (new S19PacketEntityStatus(entityplayermp, (byte) i0)));
            }
        }

    }

    public List a(ICommandSender icommandsender, String[] astring) {
        // CanaryMod: Fixes for MultiWorld
        String[] rules = getGameRules(icommandsender).b();
        //
        
        if (rules.length == 1) {
            return a(astring, this.d().b());
        } else {
            if (rules.length == 2) {
                // CanaryMod: Fixes for Multiworld
                GameRules gamerules = getGameRules(icommandsender);

                if (gamerules.a(astring[0], GameRules.ValueType.BOOLEAN_VALUE)) {
                    return a(rules, new String[] { "true", "false"});
                }
                //
            }

            return null;
        }    
    }

    private GameRules d() {
        // CanaryMod: Fixes for MultiWorld
        // return MinecraftServer.M().a(0).Q();
        return ((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle().O();
        //
    }

    // CanaryMod: convenience method
    private GameRules getGameRules(ICommandSender icommandsender) {
        // CanaryMod: Fixes for MultiWorld
        GameRules gamerules = null;
        if (icommandsender instanceof EntityPlayerMP) {
            gamerules = ((EntityPlayerMP) icommandsender).o.O();
        } 
        else if (icommandsender instanceof TileEntityCommandBlock) {
            gamerules = ((TileEntityCommandBlock) icommandsender).w().O()
        }
        else if (icommandsender instanceof EntityMinecartCommandBlock) {
            gamerules = this.d(); //NOT YET IMPLEMENTED
        }
        else {
            gamerules = this.d();
        }
        //
        return gamerules;
    }
}
