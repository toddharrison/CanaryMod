package net.minecraft.command.server;

import net.canarymod.BlockIterator;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.List;

public class CommandSummon extends CommandBase {

    public String c() {
        return "summon";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.summon.usage";
    }

    public void b(ICommandSender icommandsender, String[] astring) {
        if (astring.length < 1) {
            throw new WrongUsageException("commands.summon.usage", new Object[0]);
        }
        else {
            String s0 = astring[0];
            // CanaryMod: Inject some LineTracer
            CanaryBlock targetBlock = new CanaryBlock(BlockType.Air, 0, 0, 0, icommandsender.d().getCanaryWorld());
            if (icommandsender instanceof EntityPlayerMP) {
                BlockIterator iterator = new BlockIterator(((EntityPlayerMP)icommandsender).getPlayer());
                while (iterator.hasNext() && targetBlock.getType() == BlockType.Air) {
                    targetBlock = (CanaryBlock)iterator.next();
                }
            }

            boolean isAir = targetBlock.getType() == BlockType.Air;
            double d0 = (!isAir ? targetBlock.getX() : icommandsender.f_().a) + 0.5D;
            double d1 = (!isAir ? targetBlock.getY() + 1.1D : icommandsender.f_().b);
            double d2 = (!isAir ? targetBlock.getZ() : icommandsender.f_().c) + 0.5D;
            //

            if (astring.length >= 4) {
                d0 = a(icommandsender, d0, astring[1]);
                d1 = a(icommandsender, d1, astring[2]);
                d2 = a(icommandsender, d2, astring[3]);
            }

            World world = icommandsender.d();

            if (!world.d((int)d0, (int)d1, (int)d2)) {
                a(icommandsender, this, "commands.summon.outOfWorld", new Object[0]);
            }
            else {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                boolean flag0 = false;

                if (astring.length >= 5) {
                    IChatComponent ichatcomponent = a(icommandsender, astring, 4);

                    try {
                        NBTBase nbtbase = JsonToNBT.a(ichatcomponent.c());

                        if (!(nbtbase instanceof NBTTagCompound)) {
                            a(icommandsender, this, "commands.summon.tagError", new Object[]{ "Not a valid tag" });
                            return;
                        }

                        nbttagcompound = (NBTTagCompound)nbtbase;
                        flag0 = true;
                    }
                    catch (NBTException nbtexception) {
                        a(icommandsender, this, "commands.summon.tagError", new Object[]{ nbtexception.getMessage() });
                        return;
                    }
                }

                nbttagcompound.a("id", s0);
                Entity entity = EntityList.a(nbttagcompound, world);

                if (entity == null) {
                    a(icommandsender, this, "commands.summon.failed", new Object[0]);
                }
                else {
                    entity.b(d0, d1, d2, entity.y, entity.z);
                    if (!flag0 && entity instanceof EntityLiving) {
                        ((EntityLiving)entity).a((IEntityLivingData)null);
                    }

                    // CanaryMod: Actually check that it spawns
                    if (!world.d(entity)) {
                        a(icommandsender, this, "commands.summon.failed", new Object[0]);
                        return;
                    }
                    //
                    Entity entity1 = entity;

                    for (NBTTagCompound nbttagcompound1 = nbttagcompound; entity1 != null && nbttagcompound1.b("Riding", 10); nbttagcompound1 = nbttagcompound1.m("Riding")) {
                        Entity entity2 = EntityList.a(nbttagcompound1.m("Riding"), world);

                        if (entity2 != null) {
                            entity2.b(d0, d1, d2, entity2.y, entity2.z);
                            world.d(entity2);
                            entity1.a(entity2);
                        }

                        entity1 = entity2;
                    }

                    a(icommandsender, this, "commands.summon.success", new Object[0]);
                }
            }
        }
    }

    public List a(ICommandSender icommandsender, String[] astring) {
        return astring.length == 1 ? a(astring, this.d()) : null;
    }

    protected String[] d() {
        return (String[])EntityList.b().toArray(new String[0]);
    }
}
