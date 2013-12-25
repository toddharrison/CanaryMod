package net.minecraft.item;

import net.canarymod.api.potion.CanaryPotionEffect;
import net.canarymod.hook.player.EatHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFood extends Item {

    public final int a;
    private final int b;
    private final float c;
    private final boolean d;
    private boolean m;
    private int n;
    private int o;
    private int p;
    private float q;

    public ItemFood(int i0, float f0, boolean flag0) {
        this.a = 32;
        this.b = i0;
        this.d = flag0;
        this.c = f0;
        this.a(CreativeTabs.h);
    }

    public ItemFood(int i0, boolean flag0) {
        this(i0, 0.6F, flag0);
    }

    public ItemStack b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        // CanaryMod: Eat
        net.canarymod.api.potion.PotionEffect[] effects = null;

        if (this instanceof ItemAppleGold && !world.E) {
            if (itemstack.k() > 0) {
                effects = new net.canarymod.api.potion.PotionEffect[]{
                        new CanaryPotionEffect(new PotionEffect(Potion.x.H, 2400, 0)), new CanaryPotionEffect(new PotionEffect(Potion.l.H, 600, 4)), new CanaryPotionEffect(new PotionEffect(Potion.m.H, 6000, 0)), new CanaryPotionEffect(new PotionEffect(Potion.n.H, 6000, 0))
                };
            }
            else {
                effects = new net.canarymod.api.potion.PotionEffect[]{ new CanaryPotionEffect(new PotionEffect(Potion.x.H, 2400, 0)) };
            }
        }
        else if (!world.E && this.n > 0 && world.s.nextFloat() < this.q) {
            effects = new net.canarymod.api.potion.PotionEffect[]{ new CanaryPotionEffect(new PotionEffect(this.n, this.o * 20, this.p)) };
        }
        EatHook hook = (EatHook) new EatHook(((EntityPlayerMP) entityplayer).getPlayer(), itemstack.getCanaryItem(), this.b, this.c, effects).call();
        if (!hook.isCanceled()) {
            --itemstack.b;
            entityplayer.bO().a(hook.getLevelGain(), hook.getSaturationGain());
            world.a((Entity) entityplayer, "random.burp", 0.5F, world.s.nextFloat() * 0.1F + 0.9F);
            // this.c(itemstack, world, entityplayer); moved above and below
            if (hook.getPotionEffects() != null) {
                for (net.canarymod.api.potion.PotionEffect effect : hook.getPotionEffects()) {
                    if (effect != null) {
                        entityplayer.c(((CanaryPotionEffect) effect).getHandle());
                    }
                }
            }
        }
        //
        return itemstack;
    }

    protected void c(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (!world.E && this.n > 0 && world.s.nextFloat() < this.q) {
            entityplayer.c(new PotionEffect(this.n, this.o * 20, this.p));
        }
    }

    public int d_(ItemStack itemstack) {
        return 32;
    }

    public EnumAction d(ItemStack itemstack) {
        return EnumAction.eat;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (entityplayer.g(this.m)) {
            entityplayer.a(itemstack, this.d_(itemstack));
        }

        return itemstack;
    }

    public int g(ItemStack itemstack) {
        return this.b;
    }

    public float h(ItemStack itemstack) {
        return this.c;
    }

    public boolean i() {
        return this.d;
    }

    public ItemFood a(int i0, int i1, int i2, float f0) {
        this.n = i0;
        this.o = i1;
        this.p = i2;
        this.q = f0;
        return this;
    }

    public ItemFood j() {
        this.m = true;
        return this;
    }
}
