package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandSetSpawnpoint extends CommandBase {

    public String c() {
        return "spawnpoint";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.spawnpoint.usage";
    }

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        if (astring.length > 0 && astring.length < 4) {
            throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
        }
        else {
            EntityPlayerMP entityplayermp = astring.length > 0 ? a(icommandsender, astring[0]) : b(icommandsender);
            BlockPos blockpos = astring.length > 3 ? a(icommandsender, astring, 1, true) : entityplayermp.c();

            if (entityplayermp.o != null) {
                entityplayermp.a(blockpos, true, true); // CanaryMod: a wild boolean broke this
                a(icommandsender, this, "commands.spawnpoint.success", new Object[]{ entityplayermp.d_(), Integer.valueOf(blockpos.n()), Integer.valueOf(blockpos.o()), Integer.valueOf(blockpos.p()) });
            }
        }
    }

    public List a(ICommandSender icommandsender, String[] astring, BlockPos blockpos) {
        return astring.length == 1 ? a(astring, MinecraftServer.M().I()) : (astring.length > 1 && astring.length <= 4 ? a(astring, 1, blockpos) : null);
    }

    public boolean b(String[] astring, int i0) {
        return i0 == 0;
    }
}
