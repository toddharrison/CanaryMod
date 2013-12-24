package net.minecraft.entity.effect;

import net.canarymod.api.entity.CanaryLightningBolt;
import net.canarymod.api.entity.LightningBolt;
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
        this.a = this.aa.nextLong();
        this.c = this.aa.nextInt(3) + 1;
        this.entity = new CanaryLightningBolt(this); // CanaryMod: Wrap Entity

        if (!world.E && world.N().b("doFireTick") && (world.r == EnumDifficulty.NORMAL || world.r == EnumDifficulty.HARD) && world.a(MathHelper.c(d0), MathHelper.c(d1), MathHelper.c(d2), 10)) {
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
                i1 = MathHelper.c(d0) + this.aa.nextInt(3) - 1;
                i2 = MathHelper.c(d1) + this.aa.nextInt(3) - 1;
                int i3 = MathHelper.c(d2) + this.aa.nextInt(3) - 1;

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
            this.p.a(this.t, this.u, this.v, "ambient.weather.thunder", 10000.0F, 0.8F + this.aa.nextFloat() * 0.2F);
            this.p.a(this.t, this.u, this.v, "random.explode", 2.0F, 0.5F + this.aa.nextFloat() * 0.2F);
            // CanaryMod: LightningStrike
            new LightningStrikeHook((LightningBolt) this.getCanaryEntity()).call();
            //
        }

        --this.b;
        if (this.b < 0) {
            if (this.c == 0) {
                this.B();
            }
            else if (this.b < -this.aa.nextInt(10)) {
                --this.c;
                this.b = 1;
                this.a = this.aa.nextLong();
                if (!this.p.E && this.p.N().b("doFireTick") && this.p.a(MathHelper.c(this.t), MathHelper.c(this.u), MathHelper.c(this.v), 10)) {
                    int i0 = MathHelper.c(this.t);
                    int i1 = MathHelper.c(this.u);
                    int i2 = MathHelper.c(this.v);

                    if (this.p.a(i0, i1, i2).o() == Material.a && Blocks.ab.c(this.p, i0, i1, i2)) {
                        // CanaryMod: Ignition
                        CanaryBlock ignited = (CanaryBlock) q.getCanaryWorld().getBlockAt(i0, i1, i2);
                        ignited.setStatus((byte) 5); // LightningBolt Status 5
                        IgnitionHook hook = (IgnitionHook) new IgnitionHook(ignited, null, null, IgnitionCause.LIGHTNING_STRIKE).call();
                        if (!hook.isCanceled()) {
                            this.p.b(i0, i1, i2, (Block) Blocks.ab);
                        }
                        //
                    }
                }
            }
        }

        if (this.b >= 0) {
            if (this.p.E) {
                this.p.q = 2;
            }
            else {
                double d0 = 3.0D;
                List list = this.p.b((Entity) this, AxisAlignedBB.a().a(this.t - d0, this.u - d0, this.v - d0, this.t + d0, this.u + 6.0D + d0, this.v + d0));

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
