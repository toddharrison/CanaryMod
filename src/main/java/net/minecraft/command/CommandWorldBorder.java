package net.minecraft.command;

import net.canarymod.Canary;
import net.canarymod.ToolBox;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.WorldManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.border.WorldBorder;

import java.util.Arrays;
import java.util.List;

public class CommandWorldBorder extends CommandBase {

    public String c() {
        return "worldborder";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.worldborder.usage";
    }

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        if (astring.length < 1) {
            throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
        }
        else {
            WorldBorder worldborder = getBorderFor(icommandsender, astring); // CanaryMod: Correct Border getter
            astring = removeWorldArgs(astring); // CanaryMod: Correct arguments to parse without the world at the end
            double d0;
            double d1;
            long i0;

            if (astring[0].equals("set")) {
                if (astring.length != 2 && astring.length != 3) {
                    throw new WrongUsageException("commands.worldborder.set.usage", new Object[0]);
                }

                d0 = worldborder.j();
                d1 = a(astring[1], 1.0D, 6.0E7D);
                i0 = astring.length > 2 ? a(astring[2], 0L, 9223372036854775L) * 1000L : 0L;
                if (i0 > 0L) {
                    worldborder.a(d0, d1, i0);
                    if (d0 > d1) {
                        a(icommandsender, this, "commands.worldborder.setSlowly.shrink.success", new Object[]{ String.format("%.1f", new Object[]{ Double.valueOf(d1) }), String.format("%.1f", new Object[]{ Double.valueOf(d0) }), Long.toString(i0 / 1000L) });
                    }
                    else {
                        a(icommandsender, this, "commands.worldborder.setSlowly.grow.success", new Object[]{ String.format("%.1f", new Object[]{ Double.valueOf(d1) }), String.format("%.1f", new Object[]{ Double.valueOf(d0) }), Long.toString(i0 / 1000L) });
                    }
                }
                else {
                    worldborder.a(d1);
                    a(icommandsender, this, "commands.worldborder.set.success", new Object[]{ String.format("%.1f", new Object[]{ Double.valueOf(d1) }), String.format("%.1f", new Object[]{ Double.valueOf(d0) }) });
                }
            }
            else if (astring[0].equals("add")) {
                if (astring.length != 2 && astring.length != 3) {
                    throw new WrongUsageException("commands.worldborder.add.usage", new Object[0]);
                }

                d0 = worldborder.h();
                d1 = d0 + a(astring[1], -d0, 6.0E7D - d0);
                i0 = worldborder.i() + (astring.length > 2 ? a(astring[2], 0L, 9223372036854775L) * 1000L : 0L);
                if (i0 > 0L) {
                    worldborder.a(d0, d1, i0);
                    if (d0 > d1) {
                        a(icommandsender, this, "commands.worldborder.setSlowly.shrink.success", new Object[]{ String.format("%.1f", new Object[]{ Double.valueOf(d1) }), String.format("%.1f", new Object[]{ Double.valueOf(d0) }), Long.toString(i0 / 1000L) });
                    }
                    else {
                        a(icommandsender, this, "commands.worldborder.setSlowly.grow.success", new Object[]{ String.format("%.1f", new Object[]{ Double.valueOf(d1) }), String.format("%.1f", new Object[]{ Double.valueOf(d0) }), Long.toString(i0 / 1000L) });
                    }
                }
                else {
                    worldborder.a(d1);
                    a(icommandsender, this, "commands.worldborder.set.success", new Object[]{ String.format("%.1f", new Object[]{ Double.valueOf(d1) }), String.format("%.1f", new Object[]{ Double.valueOf(d0) }) });
                }
            }
            else if (astring[0].equals("center")) {
                if (astring.length != 3) {
                    throw new WrongUsageException("commands.worldborder.center.usage", new Object[0]);
                }

                BlockPos blockpos = icommandsender.c();
                double d2 = b((double)blockpos.n() + 0.5D, astring[1], true);
                double d3 = b((double)blockpos.p() + 0.5D, astring[2], true);

                worldborder.c(d2, d3);
                a(icommandsender, this, "commands.worldborder.center.success", new Object[]{ Double.valueOf(d2), Double.valueOf(d3) });
            }
            else if (astring[0].equals("damage")) {
                if (astring.length < 2) {
                    throw new WrongUsageException("commands.worldborder.damage.usage", new Object[0]);
                }

                if (astring[1].equals("buffer")) {
                    if (astring.length != 3) {
                        throw new WrongUsageException("commands.worldborder.damage.buffer.usage", new Object[0]);
                    }

                    d0 = a(astring[2], 0.0D);
                    d1 = worldborder.m();
                    worldborder.b(d0);
                    a(icommandsender, this, "commands.worldborder.damage.buffer.success", new Object[]{ String.format("%.1f", new Object[]{ Double.valueOf(d0) }), String.format("%.1f", new Object[]{ Double.valueOf(d1) }) });
                }
                else if (astring[1].equals("amount")) {
                    if (astring.length != 3) {
                        throw new WrongUsageException("commands.worldborder.damage.amount.usage", new Object[0]);
                    }

                    d0 = a(astring[2], 0.0D);
                    d1 = worldborder.n();
                    worldborder.c(d0);
                    a(icommandsender, this, "commands.worldborder.damage.amount.success", new Object[]{ String.format("%.2f", new Object[]{ Double.valueOf(d0) }), String.format("%.2f", new Object[]{ Double.valueOf(d1) }) });
                }
            }
            else if (astring[0].equals("warning")) {
                if (astring.length < 2) {
                    throw new WrongUsageException("commands.worldborder.warning.usage", new Object[0]);
                }

                int i1 = a(astring[2], 0);
                int i2;

                if (astring[1].equals("time")) {
                    if (astring.length != 3) {
                        throw new WrongUsageException("commands.worldborder.warning.time.usage", new Object[0]);
                    }

                    i2 = worldborder.p();
                    worldborder.b(i1);
                    a(icommandsender, this, "commands.worldborder.warning.time.success", new Object[]{ Integer.valueOf(i1), Integer.valueOf(i2) });
                }
                else if (astring[1].equals("distance")) {
                    if (astring.length != 3) {
                        throw new WrongUsageException("commands.worldborder.warning.distance.usage", new Object[0]);
                    }

                    i2 = worldborder.q();
                    worldborder.c(i1);
                    a(icommandsender, this, "commands.worldborder.warning.distance.success", new Object[]{ Integer.valueOf(i1), Integer.valueOf(i2) });
                }
            }
            else if (astring[0].equals("get")) {
                d0 = worldborder.h();
                icommandsender.a(CommandResultStats.Type.QUERY_RESULT, MathHelper.c(d0 + 0.5D));
                icommandsender.a(new ChatComponentTranslation("commands.worldborder.get.success", new Object[]{ String.format("%.0f", new Object[]{ Double.valueOf(d0) }) }));
            }
        }
    }

    protected WorldBorder d() {
        return MinecraftServer.M().defaultWorld().af(); // CanaryMod: Compile Fix, set to default
    }

    public List a(ICommandSender icommandsender, String[] astring, BlockPos blockpos) {
        return astring.length == 1 ? a(astring, new String[]{ "set", "center", "damage", "warning", "add", "get" }) : (astring.length == 2 && astring[0].equals("damage") ? a(astring, new String[]{ "buffer", "amount" }) : (astring.length == 2 && astring[0].equals("warning") ? a(astring, new String[]{ "time", "distance" }) : null));
    }

    // CanaryMod: MultiWorld Fix
    public WorldBorder getBorderFor(ICommandSender icommandsender, String[] astring) throws WrongUsageException {
        if (astring.length > 2) {
            String worldArg = astring[astring.length - 2];
            String worldName = astring[astring.length - 1];

            if (worldArg.equals("-w") || worldArg.equals("-world")) {
                String worldFqName = ToolBox.parseWorldName(worldName);
                WorldManager manager = Canary.getServer().getWorldManager();
                if (manager.worldExists(worldFqName)) {
                    if (manager.worldIsLoaded(worldFqName)) {
                        return ((CanaryWorld)ToolBox.parseWorld(worldFqName)).getHandle().af();
                    }
                }
                // CanaryMod: TODO: Should probably do something more descriptive
                throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
            }
        }
        if (icommandsender instanceof EntityPlayer) {
            return icommandsender.e().af();
        }
        return d();
    }

    // Fix arguments
    public String[] removeWorldArgs(String[] astring) {
        if (astring.length < 2) {
            return astring;
        }
        String worldArg = astring[astring.length - 2];
        return worldArg.equals("-w") || worldArg.equals("-world") ? Arrays.copyOfRange(astring, 0, astring.length - 2) : astring;
    }
}
