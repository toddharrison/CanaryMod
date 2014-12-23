package net.minecraft.command.server;


import java.util.EnumSet;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.UnknownWorldException;
import net.canarymod.hook.player.TeleportHook;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.visualillusionsent.utils.BooleanUtils;


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
        } else {
            byte b0 = 0;
            Entity entity; // CanaryMod- Object object to Entity entity

            if (astring.length == 1 || astring[0].matches("~?\\d*")) {
                entity = b(icommandsender);
            } else {
                entity = b(icommandsender, astring[0]);
                b0 = 1;
            }

            if (astring.length != 1 && astring.length != 2) {
                if (astring.length < b0 + 3) {
                    throw new WrongUsageException("commands.tp.usage", new Object[0]);
                } else if (entity.o != null) {
                    int i0 = b0 + 1;
                    CommandBase.CoordinateArg commandbase_coordinatearg = a(entity.s, astring[b0], true);
                    CommandBase.CoordinateArg commandbase_coordinatearg1 = a(entity.t, astring[i0++], 0, 0, false);
                    CommandBase.CoordinateArg commandbase_coordinatearg2 = a(entity.u, astring[i0++], true);
                    CommandBase.CoordinateArg commandbase_coordinatearg3 = a((double) entity.y, astring.length > i0 ? astring[i0++] : "~", false);
                    CommandBase.CoordinateArg commandbase_coordinatearg4 = a((double) entity.z, astring.length > i0 ? astring[i0] : "~", false);
                    float f0;
                    
                    if (entity instanceof EntityPlayerMP) {
                        EnumSet enumset = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);

                        if (commandbase_coordinatearg.c()) {
                            enumset.add(S08PacketPlayerPosLook.EnumFlags.X);
                        }

                        if (commandbase_coordinatearg1.c()) {
                            enumset.add(S08PacketPlayerPosLook.EnumFlags.Y);
                        }

                        if (commandbase_coordinatearg2.c()) {
                            enumset.add(S08PacketPlayerPosLook.EnumFlags.Z);
                        }

                        if (commandbase_coordinatearg4.c()) {
                            enumset.add(S08PacketPlayerPosLook.EnumFlags.X_ROT);
                        }

                        if (commandbase_coordinatearg3.c()) {
                            enumset.add(S08PacketPlayerPosLook.EnumFlags.Y_ROT);
                        }

                        f0 = (float) commandbase_coordinatearg3.b();
                        if (!commandbase_coordinatearg3.c()) {
                            f0 = MathHelper.g(f0);
                        }

                        float f1 = (float) commandbase_coordinatearg4.b();

                        if (!commandbase_coordinatearg4.c()) {
                            f1 = MathHelper.g(f1);
                        }

                        if (f1 > 90.0F || f1 < -90.0F) {
                            f1 = MathHelper.g(180.0F - f1);
                            f0 = MathHelper.g(f0 + 180.0F);
                        }
                        
                        // CanaryMod: InterWorld
                        CanaryWorld canaryWorld = entity.getCanaryWorld();
                        if(astring.length > i0+1)
                        {
                            boolean load = false;
                            String worldName = astring[i0+1];
                            if (!Canary.getServer().getWorldManager().worldExists(worldName)) {
                                a(icommandsender, this, "That world do not exists", new Object[0]);
                                return;
                            }

                            if (astring.length > i0 + 2) {
                                load = BooleanUtils.parseBoolean(astring[i0 + 2]);
                            }

                            if (!Canary.getServer().getWorldManager().worldIsLoaded(worldName) && !load) {
                                a(icommandsender, this, "That world is not loaded", new Object[0]);
                                return;
                            }

                            try {
                                canaryWorld = (CanaryWorld)Canary.getServer().getWorldManager().getWorld(worldName, load);
                            }
                            catch (UnknownWorldException uwex) {
                                // damagefilter and his pointless exception throwing...
                            }
                        }

                        entity.a((Entity) null);
                        ((EntityPlayerMP) entity).a.a(commandbase_coordinatearg.b(), commandbase_coordinatearg1.b(), commandbase_coordinatearg2.b(), f0, f1, enumset, canaryWorld.getType().getId(), canaryWorld.getName(), TeleportHook.TeleportCause.COMMAND);
                        entity.f(f0);
                    } else {
                        float f2 = (float) MathHelper.g(commandbase_coordinatearg3.a());

                        f0 = (float) MathHelper.g(commandbase_coordinatearg4.a());
                        if (f0 > 90.0F || f0 < -90.0F) {
                            f0 = MathHelper.g(180.0F - f0);
                            f2 = MathHelper.g(f2 + 180.0F);
                        }

                        entity.b(commandbase_coordinatearg.a(), commandbase_coordinatearg1.a(), commandbase_coordinatearg2.a(), f2, f0);
                        entity.f(f2);
                    }

                    a(icommandsender, this, "commands.tp.success.coordinates", new Object[] { entity.d_(), Double.valueOf(commandbase_coordinatearg.a()), Double.valueOf(commandbase_coordinatearg1.a()), Double.valueOf(commandbase_coordinatearg2.a())});
                }
            } else {
                Entity entity1 = b(icommandsender, astring[astring.length - 1]);
                
                // CanaryMod: Interdimensional
                boolean interdimensional = icommandsender instanceof EntityPlayerMP && ((EntityPlayerMP)icommandsender).getPlayer().canIgnoreRestrictions();
                if (entity1.o != entity.o && !interdimensional) {
                    
                    throw new CommandException("commands.tp.notSameDimension", new Object[0]);
                } else {
                    entity.a((Entity) null);
                    if (entity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP) entity).a.a(entity1.s, entity1.t, entity1.u, entity1.y, entity1.z, entity1.getCanaryWorld().getType().getId(), entity1.getCanaryWorld().getName(), TeleportHook.TeleportCause.COMMAND);
                    } else {
                        entity.b(entity1.s, entity1.t, entity1.u, entity1.y, entity1.z);
                    }

                    a(icommandsender, this, "commands.tp.success", new Object[] { entity.d_(), entity1.d_()});
                }
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
