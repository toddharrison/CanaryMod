package net.minecraft.command.server;

import net.canarymod.BlockIterator;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
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

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        if (astring.length < 1) {
            throw new WrongUsageException("commands.summon.usage", new Object[0]);
        }
        else {
            String s0 = astring[0];
            BlockPos blockpos = icommandsender.c();
            Vec3 vec3 = icommandsender.d();

            // CanaryMod: Inject some LineTracer
            CanaryBlock targetBlock = new CanaryBlock(Blocks.a.P());
            if (icommandsender instanceof EntityPlayerMP) {
                BlockIterator iterator = new BlockIterator(((EntityPlayerMP)icommandsender).getPlayer());
                while (iterator.hasNext() && targetBlock.getType() == BlockType.Air) {
                    targetBlock = (CanaryBlock)iterator.next();
                }
            }

            boolean isAir = targetBlock.getType() == BlockType.Air;
            double d0 = !isAir ? targetBlock.getX() + 0.5D : vec3.a;
            double d1 = !isAir ? targetBlock.getY() + 1.1D : vec3.b;
            double d2 = !isAir ? targetBlock.getZ() + 0.5D : vec3.c;
            //

            if (astring.length >= 4) {
                d0 = b(d0, astring[1], true);
                d1 = b(d1, astring[2], false);
                d2 = b(d2, astring[3], true);
                blockpos = new BlockPos(d0, d1, d2);
            }

            World world = icommandsender.e();

            if (!world.e(blockpos)) {
                throw new CommandException("commands.summon.outOfWorld", new Object[0]);
            }
            else if ("LightningBolt".equals(s0)) {
                world.c((Entity)(new EntityLightningBolt(world, d0, d1, d2)));
                a(icommandsender, this, "commands.summon.success", new Object[0]);
            }
            else {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                boolean flag0 = false;

                if (astring.length >= 5) {
                    IChatComponent ichatcomponent = a(icommandsender, astring, 4);

                    try {
                        nbttagcompound = JsonToNBT.a(ichatcomponent.c());
                        flag0 = true;
                    }
                    catch (NBTException nbtexception) {
                        throw new CommandException("commands.summon.tagError", new Object[]{ nbtexception.getMessage() });
                    }
                }

                nbttagcompound.a("id", s0);

                Entity entity;

                try {
                    entity = EntityList.a(nbttagcompound, world);
                }
                catch (RuntimeException runtimeexception) {
                    throw new CommandException("commands.summon.failed", new Object[0]);
                }

                if (entity == null) {
                    throw new CommandException("commands.summon.failed", new Object[0]);
                }
                else {
                    entity.b(d0, d1, d2, entity.y, entity.z);
                    if (!flag0 && entity instanceof EntityLiving) {
                        ((EntityLiving)entity).a(world.E(new BlockPos(entity)), (IEntityLivingData)null);
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

    public List a(ICommandSender icommandsender, String[] astring, BlockPos blockpos) {
        return astring.length == 1 ? a(astring, EntityList.b()) : (astring.length > 1 && astring.length <= 4 ? a(astring, 1, blockpos) : null);
    }
}
