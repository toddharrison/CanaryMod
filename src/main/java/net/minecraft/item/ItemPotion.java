package net.minecraft.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.canarymod.api.potion.CanaryPotionEffect;
import net.canarymod.hook.player.EatHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ItemPotion extends Item {

    private Map a = Maps.newHashMap();
    private static final Map b = Maps.newLinkedHashMap();

    public ItemPotion() {
        this.c(1);
        this.a(true);
        this.d(0);
        this.a(CreativeTabs.k);
    }

    public List h(ItemStack itemstack) {
        if (itemstack.n() && itemstack.o().b("CustomPotionEffects", 9)) {
            ArrayList arraylist = Lists.newArrayList();
            NBTTagList nbttaglist = itemstack.o().c("CustomPotionEffects", 10);

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                NBTTagCompound nbttagcompound = nbttaglist.b(i0);
                PotionEffect potioneffect = PotionEffect.b(nbttagcompound);

                if (potioneffect != null) {
                    arraylist.add(potioneffect);
                }
            }

            return arraylist;
        }
        else {
            List list = (List) this.a.get(Integer.valueOf(itemstack.i()));

            if (list == null) {
                list = PotionHelper.b(itemstack.i(), false);
                this.a.put(Integer.valueOf(itemstack.i()), list);
            }

            return list;
        }
    }

    public List e(int i0) {
        List list = (List) this.a.get(Integer.valueOf(i0));

        if (list == null) {
            list = PotionHelper.b(i0, false);
            this.a.put(Integer.valueOf(i0), list);
        }

        return list;
    }

    public ItemStack b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        // CanaryMod: Eat
        net.canarymod.api.potion.PotionEffect[] effects = null;

        if (!world.D) {
            List list = this.h(itemstack);

            if (list != null) {
                effects = new net.canarymod.api.potion.PotionEffect[list.size()];
                Iterator iterator = list.iterator();

                int index = 0;

                while (iterator.hasNext()) {
                    PotionEffect potioneffect = (PotionEffect) iterator.next();

                    // entityplayer.c(new PotionEffect(potioneffect));
                    // add to the array first
                    effects[index] = new CanaryPotionEffect(new PotionEffect(potioneffect)); // CLONE CLONE CLONE CLONE!
                    index++;
                    //
                }
            }
        }

        // Call Hook
        EatHook hook = (EatHook) new EatHook(((EntityPlayerMP) entityplayer).getPlayer(), itemstack.getCanaryItem(), 0, 0, effects).call();
        if (hook.isCanceled()) {
            return itemstack;
        }
        // Apply food changes
        entityplayer.ck().a(hook.getLevelGain(), hook.getSaturationGain());
        // And finally add the effects
        if (hook.getPotionEffects() != null) {
            for (net.canarymod.api.potion.PotionEffect effect : hook.getPotionEffects()) {
                if (effect != null) {
                    entityplayer.c(((CanaryPotionEffect) effect).getHandle());
                }
            }
        }
        //

        if (!entityplayer.by.d) { // CanaryMod: MOVED
            --itemstack.b;
        }

        entityplayer.b(StatList.J[Item.b((Item) this)]);
        if (!entityplayer.by.d) {
            if (itemstack.b <= 0) {
                return new ItemStack(Items.bA);
            }

            entityplayer.bg.a(new ItemStack(Items.bA));
        }

        return itemstack;
    }

    public int d(ItemStack itemstack) {
        return 32;
    }

    public EnumAction e(ItemStack itemstack) {
        return EnumAction.DRINK;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (f(itemstack.i())) {
            if (!entityplayer.by.d) {
                --itemstack.b;
            }

            world.a((Entity) entityplayer, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
            if (!world.D) {
                world.d((Entity) (new EntityPotion(world, entityplayer, itemstack)));
            }

            entityplayer.b(StatList.J[Item.b((Item) this)]);
            return itemstack;
        }
        else {
            entityplayer.a(itemstack, this.d(itemstack));
            return itemstack;
        }
    }

    public static boolean f(int i0) {
        return (i0 & 16384) != 0;
    }

    public String a(ItemStack itemstack) {
        if (itemstack.i() == 0) {
            return StatCollector.a("item.emptyPotion.name").trim();
        }
        else {
            String s0 = "";

            if (f(itemstack.i())) {
                s0 = StatCollector.a("potion.prefix.grenade").trim() + " ";
            }

            List list = Items.bz.h(itemstack);
            String s1;

            if (list != null && !list.isEmpty()) {
                s1 = ((PotionEffect) list.get(0)).g();
                s1 = s1 + ".postfix";
                return s0 + StatCollector.a(s1).trim();
            }
            else {
                s1 = PotionHelper.c(itemstack.i());
                return StatCollector.a(s1).trim() + " " + super.a(itemstack);
            }
        }
    }

}
