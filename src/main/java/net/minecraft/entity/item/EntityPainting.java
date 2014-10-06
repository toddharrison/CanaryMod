package net.minecraft.entity.item;

import com.google.common.collect.Lists;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EntityPainting extends EntityHanging {

    public EntityPainting.EnumArt c;

    public EntityPainting(World world) {
        super(world);
        this.entity = new CanaryPainting(this); // CanaryMod: Wrap Entity
    }

    public EntityPainting(World world, BlockPos blockpos, EnumFacing enumfacing) {
        super(world, blockpos);
        ArrayList arraylist = Lists.newArrayList();
        EntityPainting.EnumArt[] aentitypainting_enumart = EntityPainting.EnumArt.values();
        int i0 = aentitypainting_enumart.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EntityPainting.EnumArt entitypainting_enumart = aentitypainting_enumart[i1];

            this.c = entitypainting_enumart;
            this.a(enumfacing);
            if (this.j()) {
                arraylist.add(entitypainting_enumart);
            }
        }

        if (!arraylist.isEmpty()) {
            this.c = (EntityPainting.EnumArt) arraylist.get(this.V.nextInt(arraylist.size()));
        }

        this.a(enumfacing);
        this.entity = new CanaryPainting(this); // CanaryMod: Wrap Entity
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Motive", this.c.B);
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        String s0 = nbttagcompound.j("Motive");
        EntityPainting.EnumArt[] aentitypainting_enumart = EntityPainting.EnumArt.values();
        int i0 = aentitypainting_enumart.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EntityPainting.EnumArt entitypainting_enumart = aentitypainting_enumart[i1];

            if (entitypainting_enumart.B.equals(s0)) {
                this.c = entitypainting_enumart;
            }
        }

        if (this.c == null) {
            this.c = EntityPainting.EnumArt.KEBAB;
        }

        super.a(nbttagcompound);
    }

    public int l() {
        return this.c.C;
    }

    public int m() {
        return this.c.D;
    }

    public void b(Entity entity) {
        if (this.o.Q().b("doTileDrops")) {
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

                if (entityplayer.by.d) {
                    return;
                }
            }

            this.a(new ItemStack(Items.an), 0.0F);
        }
    }

    public void b(double d0, double d1, double d2, float f0, float f1) {
        BlockPos blockpos = new BlockPos(d0 - this.s, d1 - this.t, d2 - this.u);
        BlockPos blockpos1 = this.a.a((Vec3i) blockpos);

        this.b((double) blockpos1.n(), (double) blockpos1.o(), (double) blockpos1.p());
    }

    public static enum EnumArt {

        KEBAB("KEBAB", 0, "Kebab", 16, 16, 0, 0), AZTEC("AZTEC", 1, "Aztec", 16, 16, 16, 0), ALBAN("ALBAN", 2, "Alban", 16, 16, 32, 0), AZTEC_2("AZTEC_2", 3, "Aztec2", 16, 16, 48, 0), BOMB("BOMB", 4, "Bomb", 16, 16, 64, 0), PLANT("PLANT", 5, "Plant", 16, 16, 80, 0), WASTELAND("WASTELAND", 6, "Wasteland", 16, 16, 96, 0), POOL("POOL", 7, "Pool", 32, 16, 0, 32), COURBET("COURBET", 8, "Courbet", 32, 16, 32, 32), SEA("SEA", 9, "Sea", 32, 16, 64, 32), SUNSET("SUNSET", 10, "Sunset", 32, 16, 96, 32), CREEBET("CREEBET", 11, "Creebet", 32, 16, 128, 32), WANDERER("WANDERER", 12, "Wanderer", 16, 32, 0, 64), GRAHAM("GRAHAM", 13, "Graham", 16, 32, 16, 64), MATCH("MATCH", 14, "Match", 32, 32, 0, 128), BUST("BUST", 15, "Bust", 32, 32, 32, 128), STAGE("STAGE", 16, "Stage", 32, 32, 64, 128), VOID("VOID", 17, "Void", 32, 32, 96, 128), SKULL_AND_ROSES("SKULL_AND_ROSES", 18, "SkullAndRoses", 32, 32, 128, 128), WITHER("WITHER", 19, "Wither", 32, 32, 160, 128), FIGHTERS("FIGHTERS", 20, "Fighters", 64, 32, 0, 96), POINTER("POINTER", 21, "Pointer", 64, 64, 0, 192), PIGSCENE("PIGSCENE", 22, "Pigscene", 64, 64, 64, 192), BURNING_SKULL("BURNING_SKULL", 23, "BurningSkull", 64, 64, 128, 192), SKELETON("SKELETON", 24, "Skeleton", 64, 48, 192, 64), DONKEY_KONG("DONKEY_KONG", 25, "DonkeyKong", 64, 48, 192, 112);
        private static final EntityPainting.EnumArt[] $VALUES = new EntityPainting.EnumArt[]{KEBAB, AZTEC, ALBAN, AZTEC_2, BOMB, PLANT, WASTELAND, POOL, COURBET, SEA, SUNSET, CREEBET, WANDERER, GRAHAM, MATCH, BUST, STAGE, VOID, SKULL_AND_ROSES, WITHER, FIGHTERS, POINTER, PIGSCENE, BURNING_SKULL, SKELETON, DONKEY_KONG};
        public static final int A = "SkullAndRoses".length();
        public final String B;
        public final int C;
        public final int D;
        public final int E;
        public final int F;

        private EnumArt(String p_i1598_1_, int p_i1598_2_, String p_i1598_3_, int p_i1598_4_, int p_i1598_5_, int p_i1598_6_, int p_i1598_7_) {
            this.B = p_i1598_3_;
            this.C = p_i1598_4_;
            this.D = p_i1598_5_;
            this.E = p_i1598_6_;
            this.F = p_i1598_7_;
        }

    }
}
