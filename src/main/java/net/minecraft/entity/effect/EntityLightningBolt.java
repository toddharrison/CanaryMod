package net.minecraft.entity.effect;

import net.canarymod.api.entity.effect.CanaryLightningBolt;
import net.canarymod.api.entity.effect.LightningBolt;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.entity.EntityLightningStruckHook;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.canarymod.hook.world.LightningStrikeHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public class EntityLightningBolt extends EntityWeatherEffect {

    public int b; // CanaryMod: private => public; lightningState
    public long a;
    public int c; // CanaryMod: private => public; livingTime

    public EntityLightningBolt(World world, double d0, double d1, double d2) {
        super(world);
        this.b(d0, d1, d2, 0.0F, 0.0F);
        this.b = 2;
        this.a = this.Z.nextLong();
        this.c = this.Z.nextInt(3) + 1;
        this.entity = new CanaryLightningBolt(this); // CanaryMod: Wrap Entity

        if (!world.E && world.O().b("doFireTick") && (world.r == EnumDifficulty.NORMAL || world.r == EnumDifficulty.HARD) && world.a(MathHelper.c(d0), MathHelper.c(d1), MathHelper.c(d2), 10)) {
            int i0 = MathHelper.c(d0);
            int i1 = MathHelper.c(d1);
            int i2 = MathHelper.c(d2);

            if (world.a(i0, i1, i2).o() == Material.a && Blocks.ab.c(world, i0, i1, i2)) {

                // CanaryMod: Ignition
                CanaryBlock ignited = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);

                ignited.setStatus((byte) 5); // LightningBolt Status 5
                IgnitionHook hook = (IgnitionHook) new IgnitionHook(ignited, null, null, IgnitionCause.LIGHTNING_STRIKE).call();
                if (!hook.isCanceled()) {
                    world.b(i0, i1, i2, (Block) Blocks.ab);
                }
                //

            }

            for (i0 = 0; i0 < 4; ++i0) {
                i1 = MathHelper.c(d0) + this.Z.nextInt(3) - 1;
                i2 = MathHelper.c(d1) + this.Z.nextInt(3) - 1;
                int i3 = MathHelper.c(d2) + this.Z.nextInt(3) - 1;

                if (world.a(i1, i2, i3).o() == Material.a && Blocks.ab.c(world, i1, i2, i3)) {

                    // CanaryMod: Ignition
                    CanaryBlock ignited = (CanaryBlock) world.getCanaryWorld().getBlockAt(i1, i2, i3);

                    ignited.setStatus((byte) 5); // LightningBolt Status 5
                    IgnitionHook hook = (IgnitionHook) new IgnitionHook(ignited, null, null, IgnitionCause.LIGHTNING_STRIKE).call();
                    if (!hook.isCanceled()) {
                        world.b(i1, i2, i3, (Block) Blocks.ab);
                    }
                    //

                }
            }
        }
    }

    public void h() {
        super.h();

        if (this.b == 2) {
            this.o.a(this.s, this.t, this.u, "ambient.weather.thunder", 10000.0F, 0.8F + this.Z.nextFloat() * 0.2F);
            this.o.a(this.s, this.t, this.u, "random.explode", 2.0F, 0.5F + this.Z.nextFloat() * 0.2F);
            // CanaryMod: LightningStrike
            new LightningStrikeHook((LightningBolt) this.getCanaryEntity()).call();
            //
        }

        --this.b;
        if (this.b < 0) {
            if (this.c == 0) {
                this.B();
            } else if (this.b < -this.Z.nextInt(10)) {
                --this.c;
                this.b = 1;
                this.a = this.Z.nextLong();
                if (!this.o.E && this.o.O().b("doFireTick") && this.o.a(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u), 10)) {
                    int i0 = MathHelper.c(this.s);
                    int i1 = MathHelper.c(this.t);
                    int i2 = MathHelper.c(this.u);

                    if (this.o.a(i0, i1, i2).o() == Material.a && Blocks.ab.c(this.o, i0, i1, i2)) {
                        // CanaryMod: Ignition
                        CanaryBlock ignited = (CanaryBlock) getCanaryWorld().getBlockAt(i0, i1, i2);
                        ignited.setStatus((byte) 5); // LightningBolt Status 5
                        IgnitionHook hook = (IgnitionHook) new IgnitionHook(ignited, null, null, IgnitionCause.LIGHTNING_STRIKE).call();
                        if (!hook.isCanceled()) {
                            this.o.b(i0, i1, i2, (Block) Blocks.ab);
                        }
                        //
                    }
                }
            }
        }

        if (this.b >= 0) {
            if (this.o.E) {
                this.o.q = 2;
            } else {
                double d0 = 3.0D;
                List list = this.o.b((Entity) this, AxisAlignedBB.a(this.s - d0, this.t - d0, this.u - d0, this.s + d0, this.t + 6.0D + d0, this.u + d0));

                for (int i3 = 0; i3 < list.size(); ++i3) {
                    Entity entity = (Entity) list.get(i3);

                    // CanaryMod: EntityLightningStruck
                    EntityLightningStruckHook hook = (EntityLightningStruckHook) new EntityLightningStruckHook((LightningBolt) this.getCanaryEntity(), entity.getCanaryEntity()).call();
                    if (!hook.isCanceled()) {
                        entity.a(this);
                    }
                    //
                }
            }
        }
    }

    protected void c() {
    }

    protected void a(NBTTagCompound nbttagcompound) {
    }

    protected void b(NBTTagCompound nbttagcompound) {
    }
}
