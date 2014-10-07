package net.minecraft.command.server;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.UnknownWorldException;
import net.canarymod.hook.player.TeleportHook;
import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.visualillusionsent.utils.BooleanUtils;

import java.util.List;

public class CommandTeleport extends CommandBase {

    public String c() {
        return "tp";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.tp.usage";
    }

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        if (astring.length < 1) {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
        else {
            byte b0 = 0;
            EntityPlayerMP entityplayermp;
            // CanaryMod: just rewrite this stuff
            if (astring[0].matches("\\d+") || (astring.length < 3 && astring[0].equals(icommandsender.d_())) || (astring.length == 1)) {
                entityplayermp = b(icommandsender);
            }
            else {
                entityplayermp = d(icommandsender, astring[0]);
                b0 = 1;
            }

            if (entityplayermp == null) {
                throw new PlayerNotFoundException();
            }
            //

            /* CanaryMod: Change this whole thing out
            if (astring.length != 3 && astring.length != 4) {
            */
            if (astring.length < 3) {
                EntityPlayerMP entityplayermp1 = d(icommandsender, astring[astring.length - 1]);

                if (entityplayermp1 == null) {
                    throw new PlayerNotFoundException();
                }
                else if (entityplayermp == entityplayermp1) {
                    return;
                }

                // CanaryMod: Allow interdimensional teleporting for those ignoring restrictions
                boolean interdimensional = icommandsender instanceof EntityPlayerMP && ((EntityPlayerMP)icommandsender).getPlayer().canIgnoreRestrictions();
                if (entityplayermp1.o != entityplayermp.o && !interdimensional) {
                    a(icommandsender, this, "commands.tp.notSameDimension", new Object[0]);
                    return;
                }

                ((Entity)entityplayermp).a((Entity)null);
                // CanaryMod: Multiworld Fix and Teleportation Cause
                entityplayermp.a.a(entityplayermp1.s, entityplayermp1.t, entityplayermp1.u, entityplayermp1.y, entityplayermp1.z, entityplayermp1.getCanaryWorld().getType().getId(), entityplayermp1.getCanaryWorld().getName(), TeleportHook.TeleportCause.COMMAND);
                a(icommandsender, this, "commands.tp.success", new Object[]{ entityplayermp.d_(), entityplayermp1.d_() });
            }
            else if (entityplayermp.o != null) {
                // CanaryMod: Redo this int i0 mess
                int i0 = astring[0].matches("\\d+") ? 0 : 1;
                double d0 = a(icommandsender, entityplayermp.s, astring[i0++]);
                double d1 = a(icommandsender, entityplayermp.t, astring[i0++], 0, 0);
                double d2 = a(icommandsender, entityplayermp.u, astring[i0++]);

                // CanaryMod: Add params for world specifications
                CanaryWorld canaryWorld = null;
                boolean load = false;
                if (astring.length > i0) {
                    if (!Canary.getServer().getWorldManager().worldExists(astring[i0])) {
                        a(icommandsender, this, "World non-existent or not loaded", new Object[0]);
                        return;
                    }
                    if (astring.length > i0 + 1) {
                        load = BooleanUtils.parseBoolean(astring[i0 + 1]);
                    }
                    try {
                        canaryWorld = (CanaryWorld)Canary.getServer().getWorldManager().getWorld(astring[i0], load);
                    }
                    catch (UnknownWorldException uwex) {
                        // damagefilter and his pointless exception throwing...
                    }
                }

                if (canaryWorld != null && entityplayermp.o != canaryWorld.getHandle()) {
                    Canary.getServer().getConfigurationManager().switchDimension(entityplayermp.getPlayer(), canaryWorld, false);
                }
                //

                entityplayermp.a((Entity)null);
                entityplayermp.a(d0, d1, d2);

                a(icommandsender, this, "commands.tp.success.coordinates", new Object[]{ entityplayermp.b_(), Double.valueOf(d0), Double.valueOf(d1), Double.valueOf(d2) });
            }
            else {
                throw new WrongUsageException("commands.tp.usage", new Object[0]);
            }
        }
    }

    public List a(ICommandSender icommandsender, String[] astring, BlockPos blockpos) {
        return astring.length != 1 && astring.length != 2 ? null : a(astring, MinecraftServer.M().I());
    }

    public boolean b(String[] astring, int i0) {
        return i0 == 0;
    }
}
