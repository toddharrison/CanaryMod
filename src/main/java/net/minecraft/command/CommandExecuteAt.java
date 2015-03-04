package net.minecraft.command;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class CommandExecuteAt extends CommandBase {

    public String c() {
        return "execute";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.execute.usage";
    }

    public void a(final ICommandSender icommandsender, String[] astring) throws CommandException {
        if (astring.length < 5) {
            throw new WrongUsageException("commands.execute.usage", new Object[0]);
        }
        else {
            final Entity entity = a(icommandsender, astring[0], Entity.class);
            final double d0 = b(entity.s, astring[1], false);
            final double d1 = b(entity.t, astring[2], false);
            final double d2 = b(entity.u, astring[3], false);
            final BlockPos minecraftserver0 = new BlockPos(d0, d1, d2);
            byte minecraftserver1 = 4;

            if ("detect".equals(astring[4]) && astring.length > 10) {
                World minecraftserver2 = icommandsender.e();
                double minecraftserver3 = b(d0, astring[5], false);
                double minecraftserver5 = b(d1, astring[6], false);
                double minecraftserver7 = b(d2, astring[7], false);
                Block minecraftserver9 = g(icommandsender, astring[8]);
                int i0 = a(astring[9], -1, 15);
                BlockPos blockpos1 = new BlockPos(minecraftserver3, minecraftserver5, minecraftserver7);
                IBlockState iblockstate = minecraftserver2.p(blockpos1);

                if (iblockstate.c() != minecraftserver9 || i0 >= 0 && iblockstate.c().c(iblockstate) != i0) {
                    throw new CommandException("commands.execute.failed", new Object[]{ "detect", entity.d_() });
                }

                minecraftserver1 = 10;
            }

            String s0 = a(astring, minecraftserver1);
            ICommandSender minecraftserver4 = new ICommandSender() {

                public String d_() {
                    return entity.d_();
                }

                public IChatComponent e_() {
                    return entity.e_();
                }

                public void a(IChatComponent icommandsenderx) {
                    icommandsender.a(icommandsenderx);
                }

                public boolean a(int icommandsenderx, String astring) {
                    return icommandsender.a(icommandsenderx, astring);
                }

                public BlockPos c() {
                    return minecraftserver0;
                }

                public Vec3 d() {
                    return new Vec3(d0, d1, d2);
                }

                public World e() {
                    return entity.o;
                }

                public Entity f() {
                    return entity;
                }

                public boolean t_() {
                    MinecraftServer minecraftserver = MinecraftServer.M();

                    //                                CanaryMod: Multiworld Fix
                    return minecraftserver == null || icommandsender.e().Q().b("commandBlockOutput");
                }

                public void a(CommandResultStats.Type icommandsenderx, int astring) {
                    entity.a(icommandsenderx, astring);
                }
            };
            ICommandManager icommandmanager = MinecraftServer.M().O();

            try {
                int i1 = icommandmanager.a(minecraftserver4, s0);

                if (i1 < 1) {
                    throw new CommandException("commands.execute.allInvocationsFailed", new Object[]{ s0 });
                }
            }
            catch (Throwable throwable) {
                throw new CommandException("commands.execute.failed", new Object[]{ s0, entity.d_() });
            }
        }
    }

    public List a(ICommandSender icommandsender, String[] astring, BlockPos blockpos) {
        return astring.length == 1 ? a(astring, MinecraftServer.M().I()) : (astring.length > 1 && astring.length <= 4 ? a(astring, 1, blockpos) : (astring.length > 5 && astring.length <= 8 && "detect".equals(astring[4]) ? a(astring, 5, blockpos) : (astring.length == 9 && "detect".equals(astring[4]) ? a(astring, Block.c.c()) : null)));
    }

    public boolean b(String[] astring, int i0) {
        return i0 == 0;
    }
}
