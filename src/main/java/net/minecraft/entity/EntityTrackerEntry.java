package net.minecraft.entity;

import net.canarymod.api.CanaryEntityTrackerEntry;
import net.canarymod.api.entity.living.humanoid.EntityNonPlayableCharacter;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class EntityTrackerEntry {

    private static final Logger p = LogManager.getLogger();
    public Entity a;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public double j;
    public double k;
    public double l;
    public int m;
    private double q;
    private double r;
    private double s;
    private boolean t;
    private boolean u;
    private int v;
    private Entity w;
    private boolean x;
    public boolean n;
    public Set o = new HashSet();

    private CanaryEntityTrackerEntry canaryEntry;

    public EntityTrackerEntry(Entity entity, int i0, int i1, boolean flag0) {
        this.a = entity;
        this.b = i0;
        this.c = i1;
        this.u = flag0;
        this.d = MathHelper.c(entity.s * 32.0D);
        this.e = MathHelper.c(entity.t * 32.0D);
        this.f = MathHelper.c(entity.u * 32.0D);
        this.g = MathHelper.d(entity.y * 256.0F / 360.0F);
        this.h = MathHelper.d(entity.z * 256.0F / 360.0F);
        this.i = MathHelper.d(entity.au() * 256.0F / 360.0F);

        canaryEntry = new CanaryEntityTrackerEntry(this);
    }

    public boolean equals(Object object) {
        return object instanceof EntityTrackerEntry ? ((EntityTrackerEntry) object).a.y() == this.a.y() : false;
    }

    public int hashCode() {
        return this.a.y();
    }

    public void a(List list) {
        this.n = false;
        if (!this.t || this.a.e(this.q, this.r, this.s) > 16.0D) {
            this.q = this.a.s;
            this.r = this.a.t;
            this.s = this.a.u;
            this.t = true;
            this.n = true;
            this.b(list);
        }

        if (this.w != this.a.m || this.a.m != null && this.m % 60 == 0) {
            this.w = this.a.m;
            this.a((Packet) (new S1BPacketEntityAttach(0, this.a, this.a.m)));
        }

        if (this.a instanceof EntityItemFrame && this.m % 10 == 0) {
            EntityItemFrame i03 = (EntityItemFrame) this.a;
            ItemStack i04 = i03.j();

            if (i04 != null && i04.b() instanceof ItemMap) {
                MapData i06 = Items.aY.a(i04, this.a.o);
                Iterator i07 = list.iterator();

                while (i07.hasNext()) {
                    EntityPlayer i08 = (EntityPlayer) i07.next();
                    EntityPlayerMP i09 = (EntityPlayerMP) i08;

                    i06.a(i09, i04);
                    Packet i10 = Items.aY.c(i04, this.a.o, i09);

                    if (i10 != null) {
                        i09.a.a(i10);
                    }
                }
            }

            this.b();
        }
        else if (this.m % this.c == 0 || this.a.al || this.a.z().a()) {
            int i0;
            int i1;

            if (this.a.m == null) {
                ++this.v;
                i0 = this.a.as.a(this.a.s);
                i1 = MathHelper.c(this.a.t * 32.0D);
                int i2 = this.a.as.a(this.a.u);
                int i3 = MathHelper.d(this.a.y * 256.0F / 360.0F);
                int i4 = MathHelper.d(this.a.z * 256.0F / 360.0F);
                int i5 = i0 - this.d;
                int i6 = i1 - this.e;
                int i7 = i2 - this.f;
                Object object = null;
                boolean flag0 = Math.abs(i5) >= 4 || Math.abs(i6) >= 4 || Math.abs(i7) >= 4 || this.m % 60 == 0;
                boolean flag1 = Math.abs(i3 - this.g) >= 4 || Math.abs(i4 - this.h) >= 4;

                if (this.m > 0 || this.a instanceof EntityArrow) {
                    if (i5 >= -128 && i5 < 128 && i6 >= -128 && i6 < 128 && i7 >= -128 && i7 < 128 && this.v <= 400 && !this.x) {
                        if (flag0 && flag1) {
                            object = new S14PacketEntity.S17PacketEntityLookMove(this.a.y(), (byte) i5, (byte) i6, (byte) i7, (byte) i3, (byte) i4);
                        }
                        else if (flag0) {
                            object = new S14PacketEntity.S15PacketEntityRelMove(this.a.y(), (byte) i5, (byte) i6, (byte) i7);
                        }
                        else if (flag1) {
                            object = new S14PacketEntity.S16PacketEntityLook(this.a.y(), (byte) i3, (byte) i4);
                        }
                    }
                    else {
                        this.v = 0;
                        object = new S18PacketEntityTeleport(this.a.y(), i0, i1, i2, (byte) i3, (byte) i4);
                    }
                }

                if (this.u) {
                    double d0 = this.a.v - this.j;
                    double d1 = this.a.w - this.k;
                    double d2 = this.a.x - this.l;
                    double d3 = 0.02D;
                    double d4 = d0 * d0 + d1 * d1 + d2 * d2;

                    if (d4 > d3 * d3 || d4 > 0.0D && this.a.v == 0.0D && this.a.w == 0.0D && this.a.x == 0.0D) {
                        this.j = this.a.v;
                        this.k = this.a.w;
                        this.l = this.a.x;
                        this.a((Packet) (new S12PacketEntityVelocity(this.a.y(), this.j, this.k, this.l)));
                    }
                }

                if (object != null) {
                    this.a((Packet) object);
                }

                this.b();
                if (flag0) {
                    this.d = i0;
                    this.e = i1;
                    this.f = i2;
                }

                if (flag1) {
                    this.g = i3;
                    this.h = i4;
                }

                this.x = false;
            }
            else {
                i0 = MathHelper.d(this.a.y * 256.0F / 360.0F);
                i1 = MathHelper.d(this.a.z * 256.0F / 360.0F);
                boolean flag2 = Math.abs(i0 - this.g) >= 4 || Math.abs(i1 - this.h) >= 4;

                if (flag2) {
                    this.a((Packet) (new S14PacketEntity.S16PacketEntityLook(this.a.y(), (byte) i0, (byte) i1)));
                    this.g = i0;
                    this.h = i1;
                }

                this.d = this.a.as.a(this.a.s);
                this.e = MathHelper.c(this.a.t * 32.0D);
                this.f = this.a.as.a(this.a.u);
                this.b();
                this.x = true;
            }

            i0 = MathHelper.d(this.a.au() * 256.0F / 360.0F);
            if (Math.abs(i0 - this.i) >= 4) {
                this.a((Packet) (new S19PacketEntityHeadLook(this.a, (byte) i0)));
                this.i = i0;
            }

            this.a.al = false;
        }

        ++this.m;
        if (this.a.H) {
            this.b((Packet) (new S12PacketEntityVelocity(this.a)));
            this.a.H = false;
        }
    }

    private void b() {
        DataWatcher datawatcher = this.a.z();

        if (datawatcher.a()) {
            this.b((Packet) (new S1CPacketEntityMetadata(this.a.y(), datawatcher, false)));
        }

        if (this.a instanceof EntityLivingBase) {
            ServersideAttributeMap serversideattributemap = (ServersideAttributeMap) ((EntityLivingBase) this.a).bc();
            Set set = serversideattributemap.b();

            if (!set.isEmpty()) {
                this.b((Packet) (new S20PacketEntityProperties(this.a.y(), set)));
            }

            set.clear();
        }
    }

    public void a(Packet packet) {
        Iterator iterator = this.o.iterator();

        while (iterator.hasNext()) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) iterator.next();

            entityplayermp.a.a(packet);
        }
    }

    public void b(Packet packet) {
        this.a(packet);
        if (this.a instanceof EntityPlayerMP) {
            ((EntityPlayerMP) this.a).a.a(packet);
        }
    }

    public void a() {
        Iterator iterator = this.o.iterator();

        while (iterator.hasNext()) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) iterator.next();

            entityplayermp.d(this.a);
        }
    }

    public void a(EntityPlayerMP entityplayermp) {
        if (this.o.contains(entityplayermp)) {
            entityplayermp.d(this.a);
            this.o.remove(entityplayermp);
        }
    }

    public void b(EntityPlayerMP entityplayermp) {
        // CanaryMod: Our check to block updates if entityis player and hidden to target
        if (this.a instanceof EntityPlayerMP) {
            if (this.a.getCanaryWorld().getEntityTracker().isPlayerHidden(entityplayermp.getPlayer(), ((EntityPlayerMP) this.a).getPlayer())) {
                // CanaryMod: return because this entity is hidden to the other, don't update!
                return;
            }
        }
        // CanaryMod: End
        if (entityplayermp != this.a) {
            double d0 = entityplayermp.s - (double) (this.d / 32);
            double d1 = entityplayermp.u - (double) (this.f / 32);

            if (d0 >= (double) (-this.b) && d0 <= (double) this.b && d1 >= (double) (-this.b) && d1 <= (double) this.b) {
                if (!this.o.contains(entityplayermp) && (this.d(entityplayermp) || this.a.n)) {
                    this.o.add(entityplayermp);
                    Packet packet = this.c();

                    entityplayermp.a.a(packet);
                    if (!this.a.z().d()) {
                        entityplayermp.a.a((Packet) (new S1CPacketEntityMetadata(this.a.y(), this.a.z(), true)));
                    }

                    if (this.a instanceof EntityLivingBase) {
                        ServersideAttributeMap serversideattributemap = (ServersideAttributeMap) ((EntityLivingBase) this.a).bc();
                        Collection collection = serversideattributemap.c();

                        if (!collection.isEmpty()) {
                            entityplayermp.a.a((Packet) (new S20PacketEntityProperties(this.a.y(), collection)));
                        }
                    }

                    this.j = this.a.v;
                    this.k = this.a.w;
                    this.l = this.a.x;
                    if (this.u && !(packet instanceof S0FPacketSpawnMob)) {
                        entityplayermp.a.a((Packet) (new S12PacketEntityVelocity(this.a.y(), this.a.v, this.a.w, this.a.x)));
                    }

                    if (this.a.m != null) {
                        entityplayermp.a.a((Packet) (new S1BPacketEntityAttach(0, this.a, this.a.m)));
                    }

                    if (this.a instanceof EntityLiving && ((EntityLiving) this.a).bO() != null) {
                        entityplayermp.a.a((Packet) (new S1BPacketEntityAttach(1, this.a, ((EntityLiving) this.a).bO())));
                    }

                    if (this.a instanceof EntityLivingBase) {
                        for (int i0 = 0; i0 < 5; ++i0) {
                            ItemStack itemstack = ((EntityLivingBase) this.a).q(i0);

                            if (itemstack != null) {
                                entityplayermp.a.a((Packet) (new S04PacketEntityEquipment(this.a.y(), i0, itemstack)));
                            }
                        }
                    }

                    if (this.a instanceof EntityPlayer) {
                        EntityPlayer entityplayer = (EntityPlayer) this.a;

                        if (entityplayer.bm()) {
                            entityplayermp.a.a((Packet) (new S0APacketUseBed(entityplayer, MathHelper.c(this.a.s), MathHelper.c(this.a.t), MathHelper.c(this.a.u))));
                        }
                    }

                    if (this.a instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase) this.a;
                        Iterator iterator = entitylivingbase.aQ().iterator();

                        while (iterator.hasNext()) {
                            PotionEffect potioneffect = (PotionEffect) iterator.next();

                            entityplayermp.a.a((Packet) (new S1DPacketEntityEffect(this.a.y(), potioneffect)));
                        }
                    }
                }
            }
            else if (this.o.contains(entityplayermp)) {
                this.o.remove(entityplayermp);
                entityplayermp.d(this.a);
            }
        }
    }

    private boolean d(EntityPlayerMP entityplayermp) {
        return entityplayermp.r().t().a(entityplayermp, this.a.ah, this.a.aj);
    }

    public void b(List list) {
        for (int i0 = 0; i0 < list.size(); ++i0) {
            this.b((EntityPlayerMP) list.get(i0));
        }
    }

    private Packet c() {
        if (this.a.K) {
            p.warn("Fetching addPacket for removed entity");
        }

        if (this.a instanceof EntityItem) {
            return new S0EPacketSpawnObject(this.a, 2, 1);
        }
        else if (this.a instanceof EntityPlayerMP) {
            return new S0CPacketSpawnPlayer((EntityPlayer) this.a);
        }
        else if (this.a instanceof EntityNonPlayableCharacter) { // CanaryMod: NPC
            return new S0CPacketSpawnPlayer((EntityPlayer) this.a);
        }
        else if (this.a instanceof EntityMinecart) {
            EntityMinecart entityminecart = (EntityMinecart) this.a;

            return new S0EPacketSpawnObject(this.a, 10, entityminecart.m());
        }
        else if (this.a instanceof EntityBoat) {
            return new S0EPacketSpawnObject(this.a, 1);
        }
        else if (!(this.a instanceof IAnimals) && !(this.a instanceof EntityDragon)) {
            if (this.a instanceof EntityFishHook) {
                EntityPlayer entityplayer = ((EntityFishHook) this.a).b;

                return new S0EPacketSpawnObject(this.a, 90, entityplayer != null ? entityplayer.y() : this.a.y());
            }
            else if (this.a instanceof EntityArrow) {
                Entity entity = ((EntityArrow) this.a).c;

                return new S0EPacketSpawnObject(this.a, 60, entity != null ? entity.y() : this.a.y());
            }
            else if (this.a instanceof EntitySnowball) {
                return new S0EPacketSpawnObject(this.a, 61);
            }
            else if (this.a instanceof EntityPotion) {
                return new S0EPacketSpawnObject(this.a, 73, ((EntityPotion) this.a).k());
            }
            else if (this.a instanceof EntityExpBottle) {
                return new S0EPacketSpawnObject(this.a, 75);
            }
            else if (this.a instanceof EntityEnderPearl) {
                return new S0EPacketSpawnObject(this.a, 65);
            }
            else if (this.a instanceof EntityEnderEye) {
                return new S0EPacketSpawnObject(this.a, 72);
            }
            else if (this.a instanceof EntityFireworkRocket) {
                return new S0EPacketSpawnObject(this.a, 76);
            }
            else {
                S0EPacketSpawnObject s0epacketspawnobject;

                if (this.a instanceof EntityFireball) {
                    EntityFireball entityfireball = (EntityFireball) this.a;

                    s0epacketspawnobject = null;
                    byte b0 = 63;

                    if (this.a instanceof EntitySmallFireball) {
                        b0 = 64;
                    }
                    else if (this.a instanceof EntityWitherSkull) {
                        b0 = 66;
                    }

                    if (entityfireball.a != null) {
                        s0epacketspawnobject = new S0EPacketSpawnObject(this.a, b0, ((EntityFireball) this.a).a.y());
                    }
                    else {
                        s0epacketspawnobject = new S0EPacketSpawnObject(this.a, b0, 0);
                    }

                    s0epacketspawnobject.d((int) (entityfireball.b * 8000.0D));
                    s0epacketspawnobject.e((int) (entityfireball.c * 8000.0D));
                    s0epacketspawnobject.f((int) (entityfireball.d * 8000.0D));
                    return s0epacketspawnobject;
                }
                else if (this.a instanceof EntityEgg) {
                    return new S0EPacketSpawnObject(this.a, 62);
                }
                else if (this.a instanceof EntityTNTPrimed) {
                    return new S0EPacketSpawnObject(this.a, 50);
                }
                else if (this.a instanceof EntityEnderCrystal) {
                    return new S0EPacketSpawnObject(this.a, 51);
                }
                else if (this.a instanceof EntityFallingBlock) {
                    EntityFallingBlock entityfallingblock = (EntityFallingBlock) this.a;

                    return new S0EPacketSpawnObject(this.a, 70, Block.b(entityfallingblock.f()) | entityfallingblock.a << 16);
                }
                else if (this.a instanceof EntityPainting) {
                    return new S10PacketSpawnPainting((EntityPainting) this.a);
                }
                else if (this.a instanceof EntityItemFrame) {
                    EntityItemFrame entityitemframe = (EntityItemFrame) this.a;

                    s0epacketspawnobject = new S0EPacketSpawnObject(this.a, 71, entityitemframe.a);
                    s0epacketspawnobject.a(MathHelper.d((float) (entityitemframe.b * 32)));
                    s0epacketspawnobject.b(MathHelper.d((float) (entityitemframe.c * 32)));
                    s0epacketspawnobject.c(MathHelper.d((float) (entityitemframe.d * 32)));
                    return s0epacketspawnobject;
                }
                else if (this.a instanceof EntityLeashKnot) {
                    EntityLeashKnot entityleashknot = (EntityLeashKnot) this.a;

                    s0epacketspawnobject = new S0EPacketSpawnObject(this.a, 77);
                    s0epacketspawnobject.a(MathHelper.d((float) (entityleashknot.b * 32)));
                    s0epacketspawnobject.b(MathHelper.d((float) (entityleashknot.c * 32)));
                    s0epacketspawnobject.c(MathHelper.d((float) (entityleashknot.d * 32)));
                    return s0epacketspawnobject;
                }
                else if (this.a instanceof EntityXPOrb) {
                    return new S11PacketSpawnExperienceOrb((EntityXPOrb) this.a);
                }
                else {
                    throw new IllegalArgumentException("Don\'t know how to add " + this.a.getClass() + "!");
                }
            }
        }
        else {
            this.i = MathHelper.d(this.a.au() * 256.0F / 360.0F);
            return new S0FPacketSpawnMob((EntityLivingBase) this.a);
        }
    }

    public void c(EntityPlayerMP entityplayermp) {
        if (this.o.contains(entityplayermp)) {
            this.o.remove(entityplayermp);
            entityplayermp.d(this.a);
        }
    }

    /**
     * get canaryMod EntityTracker entry
     *
     * @return
     */
    public CanaryEntityTrackerEntry getCanaryTrackerEntry() {
        return canaryEntry;
    }
}
