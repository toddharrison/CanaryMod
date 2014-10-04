package net.minecraft.entity;

import net.canarymod.api.CanaryEntityTracker;
import net.canarymod.api.entity.living.humanoid.EntityNonPlayableCharacter;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.Packet;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

public class EntityTracker {

    private static final Logger a = LogManager.getLogger();
    private final WorldServer b;
    private Set c = Sets.newHashSet();
    private IntHashMap d = new IntHashMap();
    private int e;

    private CanaryEntityTracker canaryTracker;

    public EntityTracker(WorldServer worldserver) {
        this.b = worldserver;
        this.e = worldserver.r().an().d();
        canaryTracker = new CanaryEntityTracker(this, worldserver.getCanaryWorld());
    }

    public void a(Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            this.a(entity, 512, 2);
            EntityPlayerMP entityplayermp = (EntityPlayerMP) entity;
            Iterator iterator = this.c.iterator();

            while (iterator.hasNext()) {
                EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

                if (entitytrackerentry.a != entityplayermp) {
                    entitytrackerentry.b(entityplayermp);
                }
            }
        } else if (entity instanceof EntityFishHook) {
            this.a(entity, 64, 5, true);
        } else if (entity instanceof EntityArrow) {
            this.a(entity, 64, 20, false);
        } else if (entity instanceof EntitySmallFireball) {
            this.a(entity, 64, 10, false);
        } else if (entity instanceof EntityFireball) {
            this.a(entity, 64, 10, false);
        } else if (entity instanceof EntitySnowball) {
            this.a(entity, 64, 10, true);
        } else if (entity instanceof EntityEnderPearl) {
            this.a(entity, 64, 10, true);
        } else if (entity instanceof EntityEnderEye) {
            this.a(entity, 64, 4, true);
        } else if (entity instanceof EntityEgg) {
            this.a(entity, 64, 10, true);
        } else if (entity instanceof EntityPotion) {
            this.a(entity, 64, 10, true);
        } else if (entity instanceof EntityExpBottle) {
            this.a(entity, 64, 10, true);
        } else if (entity instanceof EntityFireworkRocket) {
            this.a(entity, 64, 10, true);
        } else if (entity instanceof EntityItem) {
            this.a(entity, 64, 20, true);
        } else if (entity instanceof EntityMinecart) {
            this.a(entity, 80, 3, true);
        } else if (entity instanceof EntityBoat) {
            this.a(entity, 80, 3, true);
        } else if (entity instanceof EntitySquid) {
            this.a(entity, 64, 3, true);
        } else if (entity instanceof EntityWither) {
            this.a(entity, 80, 3, false);
        } else if (entity instanceof EntityBat) {
            this.a(entity, 80, 3, false);
        } else if (entity instanceof EntityDragon) {
            this.a(entity, 160, 3, true);
        } else if (entity instanceof IAnimals) {
            this.a(entity, 80, 3, true);
        } else if (entity instanceof EntityTNTPrimed) {
            this.a(entity, 160, 10, true);
        } else if (entity instanceof EntityFallingBlock) {
            this.a(entity, 160, 20, true);
        } else if (entity instanceof EntityHanging) {
            this.a(entity, 160, Integer.MAX_VALUE, false);
        } else if (entity instanceof EntityArmorStand) {
            this.a(entity, 160, 3, true);
        } else if (entity instanceof EntityXPOrb) {
            this.a(entity, 160, 20, true);
        } else if (entity instanceof EntityEnderCrystal) {
            this.a(entity, 256, Integer.MAX_VALUE, false);
        }

    }

    public void a(Entity entity, int i0, int i1) {
        this.a(entity, i0, i1, false);
    }

    public void a(Entity entity, int i0, final int i1, boolean flag0) {
        if (i0 > this.e) {
            i0 = this.e;
        }

        try {
            if (this.d.b(entity.F())) {
                throw new IllegalStateException("Entity is already tracked!");
            }

            EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entity, i0, i1, flag0);

            this.c.add(entitytrackerentry);
            this.d.a(entity.F(), entitytrackerentry);

            // CanaryMod: update hidden player tracking state
            if (entity instanceof EntityPlayerMP) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) entity;
                canaryTracker.forceHiddenPlayerUpdate(entityplayermp.getPlayer());
            }
            // CanaryMod: end
            entitytrackerentry.b(this.b.j);
        } catch (Throwable s01) {
            CrashReport crashreport = CrashReport.a(s01, "Adding entity to track");
            CrashReportCategory crashreportcategory = crashreport.a("Entity To Track");

            crashreportcategory.a("Tracking range", (Object) (i0 + " blocks"));
            crashreportcategory.a("Update interval", new Callable() {

                public String call() {
                    String s0 = "Once per " + i1 + " ticks";

                    if (i1 == Integer.MAX_VALUE) {
                        s0 = "Maximum (" + s0 + ")";
                    }

                    return s0;
                }
            });
            entity.a(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.a("Entity That Is Already Tracked");

            ((EntityTrackerEntry) this.d.a(entity.y())).a.a(crashreportcategory1);

            try {
                throw new ReportedException(crashreport);
            } catch (ReportedException reportedexception) {
                a.error("\"Silently\" catching entity tracking error.", reportedexception);
            }
        }

    }

    public void b(Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) entity;
            Iterator iterator = this.c.iterator();
            // CanaryMod: update hidden player tracking state
            canaryTracker.clearHiddenPlayerData(entityplayermp.getPlayer());

            while (iterator.hasNext()) {
                EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

                entitytrackerentry.a(entityplayermp);
            }
        }

        EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry) this.d.d(entity.F());

        if (entitytrackerentry1 != null) {
            this.c.remove(entitytrackerentry1);
            entitytrackerentry1.a();
        }

    }

    public void a() {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.c.iterator();

        while (iterator.hasNext()) {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

            entitytrackerentry.a(this.b.j);
            if (entitytrackerentry.n && entitytrackerentry.a instanceof EntityPlayerMP) {
                arraylist.add((EntityPlayerMP) entitytrackerentry.a);
            }
        }

        for (int i0 = 0; i0 < arraylist.size(); ++i0) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) arraylist.get(i0);
            Iterator iterator1 = this.c.iterator();

            while (iterator1.hasNext()) {
                EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry) iterator1.next();

                if (entitytrackerentry1.a != entityplayermp) {
                    entitytrackerentry1.b(entityplayermp);
                }
            }
        }

    }

    public void a(EntityPlayerMP entityplayermp) {
        Iterator iterator = this.c.iterator();

        while (iterator.hasNext()) {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

            if (entitytrackerentry.a == entityplayermp) {
                entitytrackerentry.b(this.b.j);
            } else {
                entitytrackerentry.b(entityplayermp);
            }
        }

    }

    public void a(Entity entity, Packet packet) {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) this.d.a(entity.F());

        if (entitytrackerentry != null) {
            entitytrackerentry.a(packet);
        }

    }

    public void b(Entity entity, Packet packet) {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) this.d.a(entity.F());

        if (entitytrackerentry != null) {
            entitytrackerentry.b(packet);
        }

    }

    public void b(EntityPlayerMP entityplayermp) {
        Iterator iterator = this.c.iterator();

        while (iterator.hasNext()) {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

            entitytrackerentry.d(entityplayermp);
        }

    }

    public void a(EntityPlayerMP entityplayermp, Chunk chunk) {
        Iterator iterator = this.c.iterator();

        while (iterator.hasNext()) {
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry) iterator.next();

            if (entitytrackerentry.a != entityplayermp && entitytrackerentry.a.ae == chunk.a && entitytrackerentry.a.ag == chunk.b) {
                entitytrackerentry.b(entityplayermp);
            }
        }
    }

    /**
     * Get the CanaryEntityTracker wrapper
     *
     * @return
     */
    public CanaryEntityTracker getCanaryEntityTracker() {
        return canaryTracker;
    }

    /**
     * Get the HashSet of tracked entity entries
     *
     * @return
     */
    public Set<EntityTrackerEntry> getTrackedEntities() {
        return c;
    }

    /**
     * Gets teh EntityTrackerEntry for the entity with the given uuid
     *
     * @param uuid
     * @return
     */
    public EntityTrackerEntry getTrackerEntry(int uuid) {
        return (EntityTrackerEntry) d.a(uuid);
    }
}
