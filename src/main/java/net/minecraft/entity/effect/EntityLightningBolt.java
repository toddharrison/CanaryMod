package net.minecraft.entity.effect;

import net.canarymod.api.entity.effect.CanaryLightningBolt;
import net.canarymod.api.entity.effect.LightningBolt;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.entity.EntityLightningStruckHook;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.canarymod.hook.world.LightningStrikeHook;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public class EntityLightningBolt extends EntityWeatherEffect {

    public int b; // CanaryMod: private => public; lightningState
    public long a;
    public int c; // CanaryMod: private => public; livingTime
    {
        if (this.o.D) {
            this.o.c(2);
        }
        else {
            double d0 = 3.0D;
            List list = this.o.b((Entity) this, new AxisAlignedBB(this.s - d0, this.t - d0, this.u - d0, this.s + d0, this.t + 6.0D + d0, this.u + d0));

            for (int i0 = 0; i0 < list.size(); ++i0) {
                Entity entity = (Entity) list.get(i0);

                // CanaryMod: EntityLightningStruck
                EntityLightningStruckHook hook = (EntityLightningStruckHook) new EntityLightningStruckHook((LightningBolt) this.getCanaryEntity(), entity.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    entity.a(this);
                }
                //
            }
        }
    }

    public EntityLightningBolt(World world, double d0, double d1, double d2) {
        super(world);
        this.b(d0, d1, d2, 0.0F, 0.0F);
        this.b = 2;
        this.a = this.V.nextLong();
        this.c = this.V.nextInt(3) + 1;
        this.entity = new CanaryLightningBolt(this); // CanaryMod: Wrap Entity
        if (!world.D && world.Q().b("doFireTick") && (world.aa() == EnumDifficulty.NORMAL || world.aa() == EnumDifficulty.HARD) && world.a(new BlockPos(this), (int) 10)) {
            BlockPos blockpos = new BlockPos(this);

            if (world.p(blockpos).c().r() == Material.a && Blocks.ab.c(world, blockpos)) {

                // CanaryMod: Ignition
                CanaryBlock ignited = (CanaryBlock) world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos));

                ignited.setStatus((byte) 5); // LightningBolt Status 5
                IgnitionHook hook = (IgnitionHook) new IgnitionHook(ignited, null, null, IgnitionCause.LIGHTNING_STRIKE).call();
                if (!hook.isCanceled()) {
                    world.a(blockpos, Blocks.ab.P());
                }
                //

            }

            for (int i0 = 0; i0 < 4; ++i0) {
                BlockPos blockpos1 = blockpos.a(this.V.nextInt(3) - 1, this.V.nextInt(3) - 1, this.V.nextInt(3) - 1);

                if (world.p(blockpos1).c().r() == Material.a && Blocks.ab.c(world, blockpos1)) {

                    // CanaryMod: Ignition
                    CanaryBlock ignited = (CanaryBlock) world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos1));

                    ignited.setStatus((byte) 5); // LightningBolt Status 5
                    IgnitionHook hook = (IgnitionHook) new IgnitionHook(ignited, null, null, IgnitionCause.LIGHTNING_STRIKE).call();
                    if (!hook.isCanceled()) {
                        world.a(blockpos1, Blocks.ab.P());
                    }
                    //

                }
            }
        }

    }

    public void s_() {
        super.s_();
        if (this.b == 2) {
            this.o.a(this.s, this.t, this.u, "ambient.weather.thunder", 10000.0F, 0.8F + this.V.nextFloat() * 0.2F);
            this.o.a(this.s, this.t, this.u, "random.explode", 2.0F, 0.5F + this.V.nextFloat() * 0.2F);
            // CanaryMod: LightningStrike
            new LightningStrikeHook((LightningBolt) this.getCanaryEntity()).call();
            //
        }

        --this.b;
        if (this.b < 0) {
            if (this.c == 0) {
                this.J();
            }
            else if (this.b < -this.V.nextInt(10)) {
                --this.c;
                this.b = 1;
                this.a = this.V.nextLong();
                BlockPos blockpos = new BlockPos(this);

                if (!this.o.D && this.o.Q().b("doFireTick") && this.o.a(blockpos, (int) 10) && this.o.p(blockpos).c().r() == Material.a && Blocks.ab.c(this.o, blockpos)) {
                    // CanaryMod: Ignition
                    CanaryBlock ignited = (CanaryBlock) getCanaryWorld().getBlockAt(new BlockPosition(blockpos));
                    ignited.setStatus((byte) 5); // LightningBolt Status 5
                    IgnitionHook hook = (IgnitionHook) new IgnitionHook(ignited, null, null, IgnitionCause.LIGHTNING_STRIKE).call();
                    if (!hook.isCanceled()) {
                        this.o.a(blockpos, Blocks.ab.P());
                    }
                    //
                }
            }
        }
    }

    protected void h() {
    }

    protected void a(NBTTagCompound nbttagcompound) {
    }

    protected void b(NBTTagCompound nbttagcompound) {
    }
}
