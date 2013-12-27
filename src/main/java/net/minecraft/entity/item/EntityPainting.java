package net.minecraft.entity.item;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.DamageType;
import net.canarymod.api.entity.hanging.CanaryPainting;
import net.canarymod.api.entity.hanging.HangingEntity;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.hook.entity.HangingEntityDestroyHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EntityPainting extends EntityHanging {

    public EnumArt e;

    public EntityPainting(World world) {
        super(world);
        this.entity = new CanaryPainting(this); // CanaryMod: Wrap Entity
    }

    public EntityPainting(World world, int i0, int i1, int i2, int i3) {
        super(world, i0, i1, i2, i3);
        ArrayList arraylist = new ArrayList();
        EnumArt[] aentitypainting_enumart = EnumArt.values();
        int i4 = aentitypainting_enumart.length;

        for (int i5 = 0; i5 < i4; ++i5) {
            EnumArt entitypainting_enumart = aentitypainting_enumart[i5];

            this.e = entitypainting_enumart;
            this.a(i3);
            if (this.e()) {
                arraylist.add(entitypainting_enumart);
            }
        }

        if (!arraylist.isEmpty()) {
            this.e = (EnumArt) arraylist.get(this.aa.nextInt(arraylist.size()));
        }

        this.a(i3);
        this.entity = new CanaryPainting(this); // CanaryMod: Wrap Entity
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Motive", this.e.B);
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        String s0 = nbttagcompound.j("Motive");
        EnumArt[] aentitypainting_enumart = EnumArt.values();
        int i0 = aentitypainting_enumart.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EnumArt entitypainting_enumart = aentitypainting_enumart[i1];

            if (entitypainting_enumart.B.equals(s0)) {
                this.e = entitypainting_enumart;
            }
        }

        if (this.e == null) {
            this.e = EnumArt.Kebab;
        }

        super.a(nbttagcompound);
    }

    public int f() {
        return this.e.C;
    }

    public int i() {
        return this.e.D;
    }

    public void b(Entity entity) {
        //CanaryMod start
        HangingEntityDestroyHook hook = null;
        boolean isPlayer = false;
        if (entity instanceof EntityPlayer) {
            isPlayer = true;
            hook = (HangingEntityDestroyHook) new HangingEntityDestroyHook((HangingEntity) this.getCanaryEntity(), (Player) entity.getCanaryEntity(), CanaryDamageSource.getDamageSourceFromType(DamageType.GENERIC)).call();
        }
        else {
            hook = (HangingEntityDestroyHook) new HangingEntityDestroyHook((HangingEntity) this.getCanaryEntity(), null, CanaryDamageSource.getDamageSourceFromType(DamageType.GENERIC)).call();
        }
        if (hook.isCanceled()) {
            return;
        }
        //CanaryMod end

        //CanaryMod changed to spare an instanceof
        if (isPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entity;

            if (entityplayer.bF.d) {
                return;
            }
        }

        this.a(new ItemStack(Items.an), 0.0F);
    }

    public static enum EnumArt {

        Kebab("Kebab", 0, "Kebab", 16, 16, 0, 0), Aztec("Aztec", 1, "Aztec", 16, 16, 16, 0), Alban("Alban", 2, "Alban", 16, 16, 32, 0), Aztec2("Aztec2", 3, "Aztec2", 16, 16, 48, 0), Bomb("Bomb", 4, "Bomb", 16, 16, 64, 0), Plant("Plant", 5, "Plant", 16, 16, 80, 0), Wasteland("Wasteland", 6, "Wasteland", 16, 16, 96, 0), Pool("Pool", 7, "Pool", 32, 16, 0, 32), Courbet("Courbet", 8, "Courbet", 32, 16, 32, 32), Sea("Sea", 9, "Sea", 32, 16, 64, 32), Sunset("Sunset", 10, "Sunset", 32, 16, 96, 32), Creebet("Creebet", 11, "Creebet", 32, 16, 128, 32), Wanderer("Wanderer", 12, "Wanderer", 16, 32, 0, 64), Graham("Graham", 13, "Graham", 16, 32, 16, 64), Match("Match", 14, "Match", 32, 32, 0, 128), Bust("Bust", 15, "Bust", 32, 32, 32, 128), Stage("Stage", 16, "Stage", 32, 32, 64, 128), Void("Void", 17, "Void", 32, 32, 96, 128), SkullAndRoses("SkullAndRoses", 18, "SkullAndRoses", 32, 32, 128, 128), Wither("Wither", 19, "Wither", 32, 32, 160, 128), Fighters("Fighters", 20, "Fighters", 64, 32, 0, 96), Pointer("Pointer", 21, "Pointer", 64, 64, 0, 192), Pigscene("Pigscene", 22, "Pigscene", 64, 64, 64, 192), BurningSkull("BurningSkull", 23, "BurningSkull", 64, 64, 128, 192), Skeleton("Skeleton", 24, "Skeleton", 64, 48, 192, 64), DonkeyKong("DonkeyKong", 25, "DonkeyKong", 64, 48, 192, 112);
        public static final int A = "SkullAndRoses".length();
        public final String B;
        public final int C;
        public final int D;
        public final int E;
        public final int F;

        private static final EnumArt[] $VALUES = new EnumArt[]{ Kebab, Aztec, Alban, Aztec2, Bomb, Plant, Wasteland, Pool, Courbet, Sea, Sunset, Creebet, Wanderer, Graham, Match, Bust, Stage, Void, SkullAndRoses, Wither, Fighters, Pointer, Pigscene, BurningSkull, Skeleton, DonkeyKong };

        private EnumArt(String s0, int i0, String s1, int i1, int i2, int i3, int i4) {
            this.B = s1;
            this.C = i1;
            this.D = i2;
            this.E = i3;
            this.F = i4;
        }
    }
}
