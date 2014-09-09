package net.minecraft.item;

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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.*;

public class ItemPotion extends Item {

    private HashMap a = new HashMap();
    private static final Map b = new LinkedHashMap();

    public ItemPotion() {
        this.e(1);
        this.a(true);
        this.f(0);
        this.a(CreativeTabs.k);
    }

    public List g(ItemStack itemstack) {
        if (itemstack.p() && itemstack.q().b("CustomPotionEffects", 9)) {
            ArrayList arraylist = new ArrayList();
            NBTTagList nbttaglist = itemstack.q().c("CustomPotionEffects", 10);

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                NBTTagCompound nbttagcompound = nbttaglist.b(i0);
                PotionEffect potioneffect = PotionEffect.b(nbttagcompound);

                if (potioneffect != null) {
                    arraylist.add(potioneffect);
                }
            }

            return arraylist;
        } else {
            List list = (List) this.a.get(Integer.valueOf(itemstack.k()));

            if (list == null) {
                list = PotionHelper.b(itemstack.k(), false);
                this.a.put(Integer.valueOf(itemstack.k()), list);
            }

            return list;
        }
    }

    public List c(int i0) {
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

        if (!world.E) {
            List list = this.g(itemstack);

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
        entityplayer.bQ().a(hook.getLevelGain(), hook.getSaturationGain());
        // And finally add the effects
        if (hook.getPotionEffects() != null) {
            for (net.canarymod.api.potion.PotionEffect effect : hook.getPotionEffects()) {
                if (effect != null) {
                    entityplayer.c(((CanaryPotionEffect) effect).getHandle());
                }
            }
        }
        //

        if (!entityplayer.bE.d) { // moved
            --itemstack.b;
        }

        if (!entityplayer.bE.d) {
            if (itemstack.b <= 0) {
                return new ItemStack(Items.bo);
            }

            entityplayer.bm.a(new ItemStack(Items.bo));
        }

        return itemstack;
    }

    public int d_(ItemStack itemstack) {
        return 32;
    }

    public EnumAction d(ItemStack itemstack) {
        return EnumAction.drink;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (g(itemstack.k())) {
            if (!entityplayer.bE.d) {
                --itemstack.b;
            }

            world.a((Entity) entityplayer, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
            if (!world.E) {
                world.d((Entity) (new EntityPotion(world, entityplayer, itemstack)));
            }

            return itemstack;
        } else {
            entityplayer.a(itemstack, this.d_(itemstack));
            return itemstack;
        }
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        return false;
    }

    public static boolean g(int i0) {
        return (i0 & 16384) != 0;
    }

    public String n(ItemStack itemstack) {
        if (itemstack.k() == 0) {
            return StatCollector.a("item.emptyPotion.name").trim();
        } else {
            String s0 = "";

            if (g(itemstack.k())) {
                s0 = StatCollector.a("potion.prefix.grenade").trim() + " ";
            }

            List list = Items.bn.g(itemstack);
            String s1;

            if (list != null && !list.isEmpty()) {
                s1 = ((PotionEffect) list.get(0)).f();
                s1 = s1 + ".postfix";
                return s0 + StatCollector.a(s1).trim();
            } else {
                s1 = PotionHelper.c(itemstack.k());
                return StatCollector.a(s1).trim() + " " + super.n(itemstack);
            }
        }
    }
}
