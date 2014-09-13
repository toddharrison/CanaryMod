package net.minecraft.command;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.GameRules;

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

    public void b(ICommandSender icommandsender, String[] astring) {
        String s0;

        if (astring.length == 2) {
            s0 = astring[0];
            String s1 = astring[1];
            // CanaryMod: Fixes for MultiWorld
            GameRules gamerules =
                    icommandsender instanceof EntityPlayerMP ? ((EntityPlayerMP) icommandsender).o.O()
                            : icommandsender instanceof TileEntityCommandBlock ? ((TileEntityCommandBlock) icommandsender).w().O()
                            : icommandsender instanceof EntityMinecartCommandBlock ? this.d() //NOT YET IMPLEMENTED
                            : this.d();
            //

            if (gamerules.e(s0)) {
                gamerules.b(s0, s1);
                a(icommandsender, this, "commands.gamerule.success", new Object[0]);
            } else {
                a(icommandsender, this, "commands.gamerule.norule", new Object[]{s0});
            }
        } else if (astring.length == 1) {
            s0 = astring[0];
            // CanaryMod: Fixes for MultiWorld
            GameRules gamerules1 =
                    icommandsender instanceof EntityPlayerMP ? ((EntityPlayerMP) icommandsender).o.O() //
                            : icommandsender instanceof TileEntityCommandBlock ? ((TileEntityCommandBlock) icommandsender).w().O() //
                            : icommandsender instanceof EntityMinecartCommandBlock ? this.d() //NOT YET IMPLEMENTED
                            : this.d(); //
            //

            if (gamerules1.e(s0)) {
                String s2 = gamerules1.a(s0);

                icommandsender.a((new ChatComponentText(s0)).a(" = ").a(s2));
            } else {
                a(icommandsender, this, "commands.gamerule.norule", new Object[]{s0});
            }
        } else if (astring.length == 0) {
            GameRules gamerules2 = this.d();

            icommandsender.a(new ChatComponentText(a(gamerules2.b())));
        } else {
            throw new WrongUsageException("commands.gamerule.usage", new Object[0]);
        }
    }

    public List a(ICommandSender icommandsender, String[] astring) {
        // CanaryMod: Fixes for MultiWorld
        String[] rules =
                icommandsender instanceof EntityPlayerMP ? ((EntityPlayerMP) icommandsender).o.O().b() //
                        : icommandsender instanceof TileEntityCommandBlock ? ((TileEntityCommandBlock) icommandsender).w().O().b() //
                        : icommandsender instanceof EntityMinecartCommandBlock ? this.d().b() //NOT YET IMPLEMENTED
                        : this.d().b(); //
        //
        return astring.length == 1 ? a(astring, rules) : (astring.length == 2 ? a(astring, new String[]{"true", "false"}) : null);
    }

    private GameRules d() {
        // CanaryMod: Fixes for MultiWorld
        // return MinecraftServer.G().a(0).N();
        return ((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle().O();
        //
    }
}
