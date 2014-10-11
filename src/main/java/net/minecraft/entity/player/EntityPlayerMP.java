package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import net.canarymod.Canary;
import net.canarymod.api.CanaryNetServerHandler;
import net.canarymod.api.PlayerListAction;
import net.canarymod.api.PlayerListData;
import net.canarymod.api.PlayerListEntry;
import net.canarymod.api.entity.living.animal.CanaryAnimal;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.inventory.CanaryAnimalInventory;
import net.canarymod.api.inventory.CanaryBlockInventory;
import net.canarymod.api.inventory.CanaryEntityInventory;
import net.canarymod.api.inventory.Inventory;
import net.canarymod.api.nbt.CompoundTag;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.position.Location;
import net.canarymod.config.Configuration;
import net.canarymod.config.WorldConfiguration;
import net.canarymod.hook.CancelableHook;
import net.canarymod.hook.entity.DimensionSwitchHook;
import net.canarymod.hook.player.*;
import net.canarymod.util.NMSToolBox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommand;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.*;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class EntityPlayerMP extends EntityPlayer implements ICrafting {

    private static final Logger bF = LogManager.getLogger();
    public String bG = "en_US"; // CanaryMod: private -> public
    public NetHandlerPlayServer a;
    public final MinecraftServer b;
    public final ItemInWorldManager c;
    public double d;
    public double e;
    public final List f = Lists.newLinkedList();
    private final List bH = Lists.newLinkedList();
    private final StatisticsFile bI;
    private float bJ = Float.MIN_VALUE;
    private float bK = -1.0E8F;
    private int bL = -99999999;
    private boolean bM = true;
    private int bN = -99999999;
    private int bO = 60;
    private EnumChatVisibility bP;
    private boolean bQ = true;
    private long bR = System.currentTimeMillis();
    private Entity bS = null;
    private int bT;
    public boolean g;
    public int h;
    public boolean i;

    public EntityPlayerMP(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, ItemInWorldManager iteminworldmanager) {
        super(worldserver, gameprofile);
        WorldConfiguration cfg = Configuration.getWorldConfig(worldserver.getCanaryWorld().getFqName());
        iteminworldmanager.b = this;
        this.c = iteminworldmanager;
        BlockPos blockpos = worldserver.M();

        if (!worldserver.t.o() && worldserver.P().r() != WorldSettings.GameType.ADVENTURE) {
            int i0 = Math.max(5, cfg.getSpawnProtectionSize() - 6); // CanaryMod: Use the world spawn protection size
            int i1 = MathHelper.c(worldserver.af().b((double)blockpos.n(), (double)blockpos.p()));

            if (i1 < i0) {
                i0 = i1;
            }

            if (i1 <= 1) {
                i0 = 1;
            }

            blockpos = worldserver.r(blockpos.a(this.V.nextInt(i0 * 2) - i0, 0, this.V.nextInt(i0 * 2) - i0));
        }

        this.b = minecraftserver;
        this.bI = minecraftserver.an().a((EntityPlayer)this);
        this.S = 0.0F;
        this.a(blockpos, 0.0F, 0.0F);

        while (!worldserver.a((Entity)this, this.aQ()).isEmpty() && this.t < 255.0D) {
            this.b(this.s, this.t + 1.0D, this.u);
        }

        this.entity = new CanaryPlayer(this);
    }

    /* Special Constructor to keep a wrapper reference intact */
    public EntityPlayerMP(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, ItemInWorldManager iteminworldmanager, CanaryPlayer canaryPlayer) {
        super(worldserver, gameprofile);
        WorldConfiguration cfg = Configuration.getWorldConfig(worldserver.getCanaryWorld().getFqName());
        iteminworldmanager.b = this;
        this.c = iteminworldmanager;
        BlockPos blockpos = worldserver.M();

        if (!worldserver.t.o() && worldserver.P().r() != WorldSettings.GameType.ADVENTURE) {
            int i0 = Math.max(5, cfg.getSpawnProtectionSize() - 6);
            int i1 = MathHelper.c(worldserver.af().b((double)blockpos.n(), (double)blockpos.p()));

            if (i1 < i0) {
                i0 = i1;
            }

            if (i1 <= 1) {
                i0 = 1;
            }

            blockpos = worldserver.r(blockpos.a(this.V.nextInt(i0 * 2) - i0, 0, this.V.nextInt(i0 * 2) - i0));
        }

        this.b = minecraftserver;
        this.bI = minecraftserver.an().a((EntityPlayer)this);
        this.S = 0.0F;
        this.a(blockpos, 0.0F, 0.0F);

        while (!worldserver.a((Entity)this, this.aQ()).isEmpty() && this.t < 255.0D) {
            this.b(this.s, this.t + 1.0D, this.u);
        }

        // Why? To reduce load on data access initializing a new Player wrapper,
        // to reduce possible memory leaking on old EntityPlayerMP references
        Canary.log.debug("Keeping CanaryPlayer wrapper intact");
        this.entity = canaryPlayer;
        canaryPlayer.resetNativeEntityReference(this);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("playerGameType", 99)) {
            // CanaryMod: Modify how game mode is set
            //if (MinecraftServer.I().ap()) {
            if (Configuration.getWorldConfig(o.getCanaryWorld().getFqName()).forceDefaultGamemode()) {
                //this.c.a(MinecraftServer.I().i());
                this.c.a(o.P().r());
            }
            else {
                this.c.a(WorldSettings.GameType.a(nbttagcompound.f("playerGameType")));
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("playerGameType", this.c.b().a());
    }

    public void a(int i0) {
        super.a(i0);
        this.bN = -1;
    }

    public void b(int i0) {
        super.b(i0);
        this.bN = -1;
    }

    public void f_() {
        this.bi.a((ICrafting)this);
    }

    public void g_() {
        super.g_();
        this.a.a((Packet)(new S42PacketCombatEvent(this.br(), S42PacketCombatEvent.Event.ENTER_COMBAT)));
    }

    public void j() {
        super.j();
        this.a.a((Packet)(new S42PacketCombatEvent(this.br(), S42PacketCombatEvent.Event.END_COMBAT)));
    }

    public void s_() {
        this.c.a();
        --this.bO;
        if (this.Z > 0) {
            --this.Z;
        }

        this.bi.b();
        if (!this.o.D && !this.bi.a((EntityPlayer)this)) {
            this.n();
            this.bi = this.bh;
        }

        while (!this.bH.isEmpty()) {
            int i0 = Math.min(this.bH.size(), Integer.MAX_VALUE);
            int[] aint = new int[i0];
            Iterator iterator = this.bH.iterator();
            int i1 = 0;

            while (iterator.hasNext() && i1 < i0) {
                aint[i1++] = ((Integer)iterator.next()).intValue();
                iterator.remove();
            }

            this.a.a((Packet)(new S13PacketDestroyEntities(aint)));
        }

        if (!this.f.isEmpty()) {
            ArrayList arraylist = Lists.newArrayList();
            Iterator iterator1 = this.f.iterator();
            ArrayList arraylist1 = Lists.newArrayList();

            Chunk chunk;

            while (iterator1.hasNext() && arraylist.size() < 10) {
                ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator1.next();

                if (chunkcoordintpair != null) {
                    if (this.o.e(new BlockPos(chunkcoordintpair.a << 4, 0, chunkcoordintpair.b << 4))) {
                        chunk = this.o.a(chunkcoordintpair.a, chunkcoordintpair.b);
                        if (chunk.i()) {
                            arraylist.add(chunk);
                            arraylist1.addAll(((WorldServer)this.o).a(chunkcoordintpair.a * 16, 0, chunkcoordintpair.b * 16, chunkcoordintpair.a * 16 + 16, 256, chunkcoordintpair.b * 16 + 16));
                            iterator1.remove();
                        }
                    }
                }
                else {
                    iterator1.remove();
                }
            }

            if (!arraylist.isEmpty()) {
                if (arraylist.size() == 1) {
                    this.a.a((Packet)(new S21PacketChunkData((Chunk)arraylist.get(0), true, '\uffff')));
                }
                else {
                    this.a.a((Packet)(new S26PacketMapChunkBulk(arraylist)));
                }

                Iterator iterator2 = arraylist1.iterator();

                while (iterator2.hasNext()) {
                    TileEntity tileentity = (TileEntity)iterator2.next();

                    this.a(tileentity);
                }

                iterator2 = arraylist.iterator();

                while (iterator2.hasNext()) {
                    chunk = (Chunk)iterator2.next();
                    this.u().s().a(this, chunk);
                }
            }
        }

        Entity entity = this.C();

        if (entity != this) {
            if (!entity.ai()) {
                this.e(this);
            }
            else {
                this.a(entity.s, entity.t, entity.u, entity.y, entity.z);
                this.b.an().d(this);
                if (this.aw()) {
                    this.e(this);
                }
            }
        }
    }

    public void l() {
        try {
            super.s_();

            for (int i0 = 0; i0 < this.bg.n_(); ++i0) {
                ItemStack itemstack = this.bg.a(i0);

                if (itemstack != null && itemstack.b().f()) {
                    Packet packet = ((ItemMapBase)itemstack.b()).c(itemstack, this.o, this);

                    if (packet != null) {
                        this.a.a(packet);
                    }
                }
            }

            // CanaryMod: HealthChange / HealthEnabled
            if (this.bm() != this.bK || this.bL != this.bj.a() || this.bj.e() == 0.0F != this.bM) {
                // updates your health when it is changed.
                if (!Configuration.getWorldConfig(getCanaryWorld().getFqName()).isHealthEnabled()) {
                    super.e(this.bt(), 1.0F);
                    this.I = false;
                }
                else {
                    HealthChangeHook hook = (HealthChangeHook)new HealthChangeHook(getPlayer(), bK, this.bm()).call();
                    if (hook.isCanceled()) {
                        super.e(this.bK, 1.0F);
                    }
                }
            }
            //

            if (this.bm() != this.bK || this.bL != this.bj.a() || this.bj.e() == 0.0F != this.bM) {
                this.a.a((Packet)(new S06PacketUpdateHealth(this.bm(), this.bj.a(), this.bj.e())));
                this.bK = this.bm();
                this.bL = this.bj.a();
                this.bM = this.bj.e() == 0.0F;
            }

            if (this.bm() + this.bM() != this.bJ) {
                this.bJ = this.bm() + this.bM();
                Collection collection = this.co().a(IScoreObjectiveCriteria.g);
                Iterator iterator = collection.iterator();

                while (iterator.hasNext()) {
                    ScoreObjective scoreobjective = (ScoreObjective)iterator.next();

                    this.co().c(this.d_(), scoreobjective).a(Arrays.asList(new EntityPlayer[]{ this }));
                }
            }

            // CanaryMod: ExperienceHook / ExperienceEnabled
            if (!Configuration.getWorldConfig(getCanaryWorld().getFqName()).isExperienceEnabled()) {
                this.bA = 0;
                this.bN = 0;
            }
            else if (this.bA != this.bN) {
                ExperienceHook hook = (ExperienceHook)new ExperienceHook(getPlayer(), this.bN, this.bA).call();

                if (!hook.isCanceled()) {
                    this.bN = this.bA;
                    this.a.a((Packet)(new S1FPacketSetExperience(this.bB, this.bA, this.bz)));
                }
            }
            //

            if (this.W % 20 * 5 == 0 && !this.A().a(AchievementList.L)) {
                this.h_();
            }
        }
        catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Ticking player");
            CrashReportCategory crashreportcategory = crashreport.a("Player being ticked");

            this.a(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    protected void h_() {
        BiomeGenBase biomegenbase = this.o.b(new BlockPos(MathHelper.c(this.s), 0, MathHelper.c(this.u)));
        String s0 = biomegenbase.ah;
        JsonSerializableSet jsonserializableset = (JsonSerializableSet)this.A().b((StatBase)AchievementList.L);

        if (jsonserializableset == null) {
            jsonserializableset = (JsonSerializableSet)this.A().a(AchievementList.L, new JsonSerializableSet());
        }

        jsonserializableset.add(s0);
        if (this.A().b(AchievementList.L) && jsonserializableset.size() >= BiomeGenBase.n.size()) {
            HashSet hashset = Sets.newHashSet(BiomeGenBase.n);
            Iterator iterator = jsonserializableset.iterator();

            while (iterator.hasNext()) {
                String s1 = (String)iterator.next();
                Iterator iterator1 = hashset.iterator();

                while (iterator1.hasNext()) {
                    BiomeGenBase biomegenbase1 = (BiomeGenBase)iterator1.next();

                    if (biomegenbase1.ah.equals(s1)) {
                        iterator1.remove();
                    }
                }

                if (hashset.isEmpty()) {
                    break;
                }
            }

            if (hashset.isEmpty()) {
                this.b((StatBase)AchievementList.L);
            }
        }
    }

    public void a(DamageSource damagesource) {
        // CanaryMod: Start: PlayerDeathHook
        PlayerDeathHook hook = (PlayerDeathHook)new PlayerDeathHook(getPlayer(), damagesource.getCanaryDamageSource(), this.br().b().getWrapper()).call();
        // Check Death Message enabled
        if (Configuration.getServerConfig().isDeathMessageEnabled()) {
            //if (this.o.Q().b("showDeathMessages")) {
            Team team = this.bN();

            if (team != null && team.j() != Team.EnumVisible.ALWAYS) {
                if (team.j() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
                    this.b.an().a((EntityPlayer)this, this.br().b());
                }
                else if (team.j() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
                    this.b.an().b((EntityPlayer)this, this.br().b());
                }
            }
            else {
                this.b.an().a(this.br().b());
            }
        }
        // CanaryMod: End: PlayerDeathHook

        if (!this.o.Q().b("keepInventory")) {
            this.bg.n();
        }

        Collection collection = this.o.Z().a(IScoreObjectiveCriteria.d);
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            ScoreObjective scoreobjective = (ScoreObjective)iterator.next();
            Score score = this.co().c(this.d_(), scoreobjective);

            score.a();
        }

        EntityLivingBase entitylivingbase = this.bs();

        if (entitylivingbase != null) {
            EntityList.EntityEggInfo entitylist_entityegginfo = (EntityList.EntityEggInfo)EntityList.a.get(Integer.valueOf(EntityList.a(entitylivingbase)));

            if (entitylist_entityegginfo != null) {
                this.b(entitylist_entityegginfo.e);
            }

            entitylivingbase.b(this, this.aU);
        }

        this.b(StatList.y);
        this.a(StatList.h);
        this.br().g();
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        }
        else {
            // CanaryMod moved pvp to per-world config
            boolean haspvp = Configuration.getWorldConfig(getCanaryWorld().getFqName()).isPvpEnabled();
            boolean flag0 = haspvp && this.b.ad() && this.cq() && "fall".equals(damagesource.p);

            if (!flag0 && this.bO > 0 && damagesource != DamageSource.j) {
                return false;
            }
            else {
                if (damagesource instanceof EntityDamageSource) {
                    Entity entity = damagesource.j();

                    if (entity instanceof EntityPlayer && !this.a((EntityPlayer)entity)) {
                        return false;
                    }

                    if (entity instanceof EntityArrow) {
                        EntityArrow entityarrow = (EntityArrow)entity;

                        if (entityarrow.c instanceof EntityPlayer && !this.a((EntityPlayer)entityarrow.c)) {
                            return false;
                        }
                    }
                }

                return super.a(damagesource, f0);
            }
        }
    }

    public boolean a(EntityPlayer entityplayer) {
        // CanaryMod moved pvp to per-world config
        boolean haspvp = Configuration.getWorldConfig(getCanaryWorld().getFqName()).isPvpEnabled();
        return haspvp && super.a(entityplayer);
    }

    private boolean cq() {
        return this.b.ah();
    }

    public void c(int i0) {
        if (this.am == 1 && i0 == 1) {
            this.b((StatBase)AchievementList.D);
            this.o.e((Entity)this);
            this.i = true;
            this.a.a((Packet)(new S2BPacketChangeGameState(4, 0.0F)));
        }
        else {
            if (this.am == 0 && i0 == 1) {
                this.b((StatBase)AchievementList.C);
                BlockPos blockpos = this.b.a(i0).m();

                if (blockpos != null) {
                    // CanaryMod: Teleport Cause added
                    this.a.a((double)blockpos.n(), (double)blockpos.o(), (double)blockpos.p(), 0.0F, 0.0F, getCanaryWorld().getType().getId(), getCanaryWorld().getName(), TeleportHook.TeleportCause.PORTAL);
                }

                i0 = 1;
            }
            else {
                this.b((StatBase)AchievementList.y);
            }

            // CanaryMod onPortalUse && onDimensionSwitch
            Location goingTo = simulatePortalUse(i0, MinecraftServer.M().getWorld(getCanaryWorld().getName(), i0));

            PortalUseHook puh = (PortalUseHook)new PortalUseHook(getPlayer(), goingTo).call();
            DimensionSwitchHook dsh = (DimensionSwitchHook)new DimensionSwitchHook(this.getCanaryEntity(), this.getCanaryEntity().getLocation(), goingTo).call();

            if (puh.isCanceled() || dsh.isCanceled()) {
                return;
            } //
            else {
                this.b.an().a(this, getCanaryWorld().getName(), i0);
                this.bN = -1;
                this.bK = -1.0F;
                this.bL = -1;
            } //
        }
    }

    public boolean a(EntityPlayerMP entityplayermp) {
        return entityplayermp.v() ? this.C() == this : (this.v() ? false : super.a(entityplayermp));
    }

    private void a(TileEntity tileentity) {
        if (tileentity != null) {
            // CanaryMod: SignShowHook
            if (tileentity instanceof TileEntitySign) {
                new SignShowHook(this.getPlayer(), ((TileEntitySign)tileentity).getCanarySign()).call();
            }
            //
            Packet packet = tileentity.x_();

            if (packet != null) {
                this.a.a(packet);
            }
        }
    }

    public void a(Entity entity, int i0) {
        super.a(entity, i0);
        this.bi.b();
    }

    public EnumStatus a(BlockPos blockpos) {
        EnumStatus entityplayer_enumstatus = super.a(blockpos);

        if (entityplayer_enumstatus == EnumStatus.OK) {
            S0APacketUseBed s0apacketusebed = new S0APacketUseBed(this, blockpos);

            this.u().s().a((Entity)this, (Packet)s0apacketusebed);
            // CanaryMod: Teleport Cause added
            this.a.a(this.s, this.t, this.u, this.y, this.z, getCanaryWorld().getType().getId(), getCanaryWorld().getName(), TeleportHook.TeleportCause.BED);
            this.a.a((Packet)s0apacketusebed);
        }

        return entityplayer_enumstatus;
    }

    public void a(boolean flag0, boolean flag1, boolean flag2) {
        if (this.bI()) {
            this.u().s().b(this, new S0BPacketAnimation(this, 2));
        }

        super.a(flag0, flag1, flag2);
        if (this.a != null) {
            // CanaryMod: Teleport Cause added
            this.a.a(this.s, this.t, this.u, this.y, this.z, getCanaryWorld().getType().getId(), getCanaryWorld().getName(), TeleportHook.TeleportCause.BED);
        }
    }

    public void a(Entity entity) {
        Entity entity1 = this.m;

        super.a(entity);
        if (entity != entity1) {
            this.a.a((Packet)(new S1BPacketEntityAttach(0, this, this.m)));
            // CanaryMod: Teleport Cause added
            this.a.a(this.s, this.t, this.u, this.y, this.z, getCanaryWorld().getType().getId(), getCanaryWorld().getName(), TeleportHook.TeleportCause.MOUNT_CHANGE);
        }
    }

    protected void a(double d0, boolean flag0, Block block, BlockPos blockpos) {
    }

    public void a(double d0, boolean flag0) {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.t - 0.20000000298023224D);
        int i2 = MathHelper.c(this.u);
        BlockPos blockpos = new BlockPos(i0, i1, i2);
        Block block = this.o.p(blockpos).c();

        if (block.r() == Material.a) {
            Block block1 = this.o.p(blockpos.b()).c();

            if (block1 instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate) {
                blockpos = blockpos.b();
                block = this.o.p(blockpos).c();
            }
        }
        super.a(d0, flag0, block, blockpos);
    }

    public void a(TileEntitySign tileentitysign) {
        tileentitysign.a((EntityPlayer)this);
        this.a.a((Packet)(new S36PacketSignEditorOpen(tileentitysign.v())));
    }

    private void cr() {
        this.bT = this.bT % 100 + 1;
    }

    public void a(IInteractionObject iinteractionobject) {
        // CanaryMod: InventoryHook
        Container container = NMSToolBox.doInventoryHook(iinteractionobject, this);
        if(container == null){
            return;
        }
        //
        this.cr();
        this.a.a((Packet)(new S2DPacketOpenWindow(this.bT, iinteractionobject.k(), iinteractionobject.e_())));
        this.bi = container; // CanaryMod: replace with pass back container
        this.bi.d = this.bT;
        this.bi.a((ICrafting)this);
    }

    public void a(IInventory iinventory) {
        if (this.bi != this.bh) {
            this.n();
        }

        if (iinventory instanceof ILockableContainer) {
            ILockableContainer ilockablecontainer = (ILockableContainer)iinventory;

            if (ilockablecontainer.q_() && !this.a(ilockablecontainer.i()) && !this.v()) {
                this.a.a((Packet)(new S02PacketChat(new ChatComponentTranslation("container.isLocked", new Object[]{ iinventory.e_() }), (byte)2)));
                this.a.a((Packet)(new S29PacketSoundEffect("random.door_close", this.s, this.t, this.u, 1.0F, 1.0F)));
                return;
            }
        }

        // CanaryMod: InventoryHook
        Container container = NMSToolBox.doInventoryHook(iinventory, this);
        if(container == null){
            return;
        }
        //

        this.cr();
        if (iinventory instanceof IInteractionObject) {
            this.a.a((Packet)(new S2DPacketOpenWindow(this.bT, ((IInteractionObject)iinventory).k(), iinventory.e_(), iinventory.n_())));
            this.bi = container; // CanaryMod: Use passed back container
        }
        else {
            this.a.a((Packet)(new S2DPacketOpenWindow(this.bT, "minecraft:container", iinventory.e_(), iinventory.n_())));
            this.bi = container; // CanaryMod: Use passed back container
        }

        this.bi.d = this.bT;
        this.bi.a((ICrafting)this);
    }

    public void a(IMerchant imerchant) {
        this.cr();
        this.bi = new ContainerMerchant(this.bg, imerchant, this.o);
        this.bi.d = this.bT;
        this.bi.a((ICrafting)this);
        InventoryMerchant inventorymerchant = ((ContainerMerchant)this.bi).e();
        IChatComponent ichatcomponent = imerchant.e_();

        this.a.a((Packet)(new S2DPacketOpenWindow(this.bT, "minecraft:villager", ichatcomponent, inventorymerchant.n_())));
        MerchantRecipeList merchantrecipelist = imerchant.b_(this);

        if (merchantrecipelist != null) {
            PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());

            packetbuffer.writeInt(this.bT);
            merchantrecipelist.a(packetbuffer);
            this.a.a((Packet)(new S3FPacketCustomPayload("MC|TrList", packetbuffer)));
        }
    }

    public void a(EntityHorse entityhorse, IInventory iinventory) {
        if (this.bi != this.bh) {
            this.n();
        }

        // CanaryMod: Inventory hook
        Inventory inv = new CanaryAnimalInventory((AnimalChest) iinventory, (CanaryAnimal) entityhorse.getCanaryEntity());
        if(new InventoryHook(getPlayer(), inv, false).call().isCanceled()){
            return;
        }

        this.cr();
        this.a.a((Packet)(new S2DPacketOpenWindow(this.bT, "EntityHorse", iinventory.e_(), iinventory.n_(), entityhorse.F())));
        this.bi = new ContainerHorseInventory(this.bg, iinventory, entityhorse, this);
        ((ContainerHorseInventory)this.bi).setInventory(inv); // CanaryMod: set our inventory
        this.bi.d = this.bT;
        this.bi.a((ICrafting)this);
    }

    public void a(ItemStack itemstack) {
        Item item = itemstack.b();

        if (item == Items.bN) {
            this.a.a((Packet)(new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer()))));
        }
    }

    public void a(Container container, int i0, ItemStack itemstack) {
        if (!(container.a(i0) instanceof SlotCrafting)) {
            if (!this.g) {
                this.a.a((Packet)(new S2FPacketSetSlot(container.d, i0, itemstack)));
            }
        }
    }

    public void a(Container container) {
        this.a(container, container.a());
    }

    public void a(Container container, List list) {
        this.a.a((Packet)(new S30PacketWindowItems(container.d, list)));
        this.a.a((Packet)(new S2FPacketSetSlot(-1, -1, this.bg.p())));
    }

    public void a(Container container, int i0, int i1) {
        this.a.a((Packet)(new S31PacketWindowProperty(container.d, i0, i1)));
    }

    public void a(Container container, IInventory iinventory) {
        for (int i0 = 0; i0 < iinventory.g(); ++i0) {
            this.a.a((Packet)(new S31PacketWindowProperty(container.d, i0, iinventory.a_(i0))));
        }
    }

    public void n() {
        this.a.a((Packet)(new S2EPacketCloseWindow(this.bi.d)));
        this.p();
    }

    public void o() {
        if (!this.g) {
            this.a.a((Packet)(new S2FPacketSetSlot(-1, -1, this.bg.p())));
        }
    }

    public void p() {
        this.bi.b((EntityPlayer)this);
        this.bi = this.bh;
    }

    public void a(float f0, float f1, boolean flag0, boolean flag1) {
        if (this.m != null) {
            if (f0 >= -1.0F && f0 <= 1.0F) {
                this.aX = f0;
            }

            if (f1 >= -1.0F && f1 <= 1.0F) {
                this.aY = f1;
            }

            this.aW = flag0;
            this.c(flag1);
        }
    }

    public void a(StatBase statbase, int i0) {
        if (statbase != null) {
            // CanaryMod: StatGained
            StatGainedHook hook = (StatGainedHook)new StatGainedHook(getPlayer(), statbase.getCanaryStat(), i0).call();
            if (hook.isCanceled()) {
                return;
            }
            this.bI.b(this, statbase, i0);
            Iterator iterator = this.co().a(statbase.k()).iterator();

            while (iterator.hasNext()) {
                ScoreObjective scoreobjective = (ScoreObjective)iterator.next();

                this.co().c(this.d_(), scoreobjective).a(i0);
            }

            if (this.bI.e()) {
                this.bI.a(this);
            }
        }
    }

    public void a(StatBase statbase) {
        if (statbase != null) {
            // CanaryMod: StatGained
            StatGainedHook hook = (StatGainedHook)new StatGainedHook(getPlayer(), statbase.getCanaryStat(), 0).call(); // TODO: is this right?
            if (hook.isCanceled()) {
                return;
            }
            this.bI.a(this, statbase, 0);
            Iterator iterator = this.co().a(statbase.k()).iterator();

            while (iterator.hasNext()) {
                ScoreObjective scoreobjective = (ScoreObjective)iterator.next();

                this.co().c(this.d_(), scoreobjective).c(0);
            }

            if (this.bI.e()) {
                this.bI.a(this);
            }
        }
    }

    public void q() {
        if (this.l != null) {
            this.l.a((Entity)this);
        }

        if (this.bu) {
            this.a(true, false, false);
        }
    }

    public void r() {
        this.bK = -1.0E8F;
    }

    public void b(IChatComponent ichatcomponent) {
        this.a.a((Packet) (new S02PacketChat(ichatcomponent)));
    }

    protected void s() {
        this.a.a((Packet) (new S19PacketEntityStatus(this, (byte) 9)));
        super.s();
    }

    public void a(ItemStack itemstack, int i0) {
        super.a(itemstack, i0);
        if (itemstack != null && itemstack.b() != null && itemstack.b().e(itemstack) == EnumAction.EAT) {
            this.u().s().b(this, new S0BPacketAnimation(this, 3));
        }
    }

    public void a(EntityPlayer entityplayer, boolean flag0) {
        super.a(entityplayer, flag0);
        this.bN = -1;
        this.bK = -1.0F;
        this.bL = -1;
        this.bH.addAll(((EntityPlayerMP) entityplayer).bH);
    }

    protected void a(PotionEffect potioneffect) {
        super.a(potioneffect);
        this.a.a((Packet)(new S1DPacketEntityEffect(this.F(), potioneffect)));
    }

    protected void a(PotionEffect potioneffect, boolean flag0) {
        super.a(potioneffect, flag0);
        this.a.a((Packet)(new S1DPacketEntityEffect(this.F(), potioneffect)));
    }

    protected void b(PotionEffect potioneffect) {
        super.b(potioneffect);
        this.a.a((Packet)(new S1EPacketRemoveEntityEffect(this.F(), potioneffect)));
    }

    public void a(double d0, double d1, double d2) {
        this.a.a(d0, d1, d2, this.y, this.z, this.am, getCanaryWorld().getName(), TeleportHook.TeleportCause.MOVEMENT);
    }

    public void b(Entity entity) {
        this.u().s().b(this, new S0BPacketAnimation(entity, 4));
    }

    public void c(Entity entity) {
        this.u().s().b(this, new S0BPacketAnimation(entity, 5));
    }

    public void t() {
        if (this.a != null) {
            this.a.a((Packet)(new S39PacketPlayerAbilities(this.by)));
            this.B();
        }
    }

    public WorldServer u() {
        return (WorldServer)this.o;
    }

    public void a(WorldSettings.GameType worldsettings_gametype) {
        this.c.a(worldsettings_gametype);
        this.a.a((Packet)(new S2BPacketChangeGameState(3, (float)worldsettings_gametype.a())));
        if (worldsettings_gametype == WorldSettings.GameType.SPECTATOR) {
            this.a((Entity)null);
        }
        else {
            this.e(this);
        }

        this.t();
        this.bO();
    }

    public boolean v() {
        return this.c.b() == WorldSettings.GameType.SPECTATOR;
    }

    public void a(IChatComponent ichatcomponent) {
        this.a.a((Packet)(new S02PacketChat(ichatcomponent)));
    }

    public boolean a(int i0, String s0) {
        // CanaryMod: replace permission checking with ours
        // return "seed".equals(s0) && !this.b.V() ? true : (!"tell".equals(s0) && !"help".equals(s0) && !"me".equals(s0) ? this.b.af().e(this.bu) ? this.b.k() >= i0 : false) : true);
        if (s0.trim().isEmpty()) { // Purely checking for permission level
            return getPlayer().hasPermission("canary.world.commandblock");
        }
        if (s0.startsWith("/")) {
            s0 = s0.substring(1);
        }
        String[] args = s0.split(" ");
        if (Canary.commands().hasCommand(args[0])) {
            return Canary.commands().canUseCommand(getPlayer(), args[0]);
        }
        // Might be vanilla, so just assume
        ICommand icommand = (ICommand)MinecraftServer.M().O().a().get(args[0]);
        if (icommand == null) {
            return false;
        }
        return Canary.ops().isOpped(getPlayer().getName()) || getPlayer().hasPermission("canary.commands.vanilla.".concat(icommand.c()));
        //
    }

    public String w() {
        String s0 = this.a.a.b().toString();

        s0 = s0.substring(s0.indexOf("/") + 1);
        s0 = s0.substring(0, s0.indexOf(":"));
        return s0;
    }

    public void a(C15PacketClientSettings c15packetclientsettings) {
        this.bG = c15packetclientsettings.a();
        this.bP = c15packetclientsettings.c();
        this.bQ = c15packetclientsettings.d();
        this.H().b(10, Byte.valueOf((byte)c15packetclientsettings.e()));
    }

    public EnumChatVisibility y() {
        return this.bP;
    }

    public void a(String s0, String s1) {
        this.a.a((Packet)(new S48PacketResourcePackSend(s0, s1)));
    }

    public BlockPos c() {
        return new BlockPos(this.s, this.t + 0.5D, this.u);
    }

    public void z() {
        // CanaryMod: ReturnFromIdleHook
        long timeidle = MinecraftServer.ax() - this.bR;
        if (timeidle > 10000) {// let them idle at least 10 seconds
            new ReturnFromIdleHook(this.getPlayer(), timeidle).call();
        }
        //
        this.bR = MinecraftServer.ax();
    }

    public StatisticsFile A() {
        return this.bI;
    }

    public void d(Entity entity) {
        if (entity instanceof EntityPlayer) {
            this.a.a((Packet) (new S13PacketDestroyEntities(new int[]{entity.F()})));
        }
        else {
            this.bH.add(Integer.valueOf(entity.F()));
        }
    }

    protected void B() {
        if (this.v()) {
            this.bi();
            this.e(true);
        }
        else {
            super.B();
        }

        this.u().s().a(this);
    }

    public Entity C() {
        return (Entity)(this.bS == null ? this : this.bS);
    }

    public void e(Entity entity) {
        Entity entity1 = this.C();

        this.bS = (Entity)(entity == null ? this : entity);
        if (entity1 != this.bS) {
            this.a.a((Packet)(new S43PacketCamera(this.bS)));
            this.a(this.bS.s, this.bS.t, this.bS.u);
        }
    }

    public void f(Entity entity) {
        if (this.c.b() == WorldSettings.GameType.SPECTATOR) {
            this.e(entity);
        }
        else {
            super.f(entity);
        }
    }

    public long D() {
        return this.bR;
    }

    public IChatComponent E() {
        // CanaryMod: Mojang provides it, we'll abuse it.
        getDisplayName(); // Initialize it if it wasn't already; call super or this will cause
        return displayName;
    }

    // CanayMod: Start
    @Override
    public void setDisplayName(String name) { // Old way
        this.setDisplayNameComponent(name != null && !name.isEmpty() ? new ChatComponentText(name) : null);
    }

    @Override
    public void setDisplayNameComponent(IChatComponent iChatComponent){
        super.setDisplayNameComponent(iChatComponent);
        if(getDisplayName() != null && !getDisplayName().isEmpty()) {
            MinecraftServer.M().an().a(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER, this));
            for (Player player : Canary.getServer().getPlayerList()) {
                if (!player.equals(getPlayer())) {
                    player.sendPacket(new CanaryPacket(new S13PacketDestroyEntities(F())));
                }
            }
            PlayerListData data = getPlayer().getPlayerListData(PlayerListAction.ADD_PLAYER);
            data.setProfile(NMSToolBox.spoofNameAndTexture(this.cc(), iChatComponent.e()));
            MinecraftServer.M().an().a(new S38PacketPlayerListItem(PlayerListAction.ADD_PLAYER, data));
            for (Player player : Canary.getServer().getPlayerList()) {
                if (!player.equals(getPlayer())) {
                    player.sendPacket(new CanaryPacket(new S0CPacketSpawnPlayer(this)));
                }
            }
        }
    }

    public void updateSlot(int windowId, int slotIndex, ItemStack item) {
        this.a.a(new S2FPacketSetSlot(windowId, slotIndex, item));
    }

    public boolean getColorEnabled() {
        return this.bQ;
    }

    public int getViewDistance() {
        return this.bO;
    }

    /**
     * Get the CanaryEntity as CanaryPlayer
     *
     * @return
     */
    public CanaryPlayer getPlayer() {
        return (CanaryPlayer)this.entity;
    }

    public CanaryNetServerHandler getServerHandler() {
        return a.getCanaryServerHandler();
    }

    public void setDimension(CanaryWorld world) {
        super.a(world.getHandle());
        this.c.a((WorldServer)world.getHandle());
    }

    public void changeWorld(WorldServer srv) {
        BlockPos blockpos = srv.m();

        if (blockpos != null) {
            this.a.a(blockpos.n(), blockpos.o(), blockpos.p(), 0.0F, 0.0F, srv.getCanaryWorld().getType().getId(), srv.getCanaryWorld().getName(), TeleportHook.TeleportCause.PLUGIN);
        }

        // CanaryMod: Dimension switch hook.
        Location goingTo = this.simulatePortalUse(srv.getCanaryWorld().getType().getId(), MinecraftServer.M().getWorld(this.getCanaryWorld().getName(), srv.getCanaryWorld().getType().getId()));
        CancelableHook hook = (CancelableHook)new DimensionSwitchHook(this.getCanaryEntity(), this.getCanaryEntity().getLocation(), goingTo).call();
        if (hook.isCanceled()) {
            return;
        }//

        this.b.an().a(this, srv.getCanaryWorld().getName(), srv.getCanaryWorld().getType().getId());
        this.bN = -1;
        this.bK = -1.0F;
        this.bL = -1;
    }

    // CanaryMod: Special methods for remote inventory opening
    public void openContainer(Container container) {
        this.cr();

        Inventory inv = container.getInventory();
        IInventory nativeInv = null;
        if (inv instanceof CanaryEntityInventory) {
            nativeInv = ((CanaryEntityInventory)inv).getHandle();
        }
        else if (inv instanceof CanaryBlockInventory) {
            nativeInv = ((CanaryBlockInventory)inv).getInventoryHandle();
        }

        if (nativeInv != null) {
            if (nativeInv instanceof IInteractionObject) {
                this.a.a((Packet)(new S2DPacketOpenWindow(this.bT, ((IInteractionObject)nativeInv).k(), nativeInv.e_(), nativeInv.n_())));
            }
            else {
                this.a.a((Packet)(new S2DPacketOpenWindow(this.bT, "minecraft:container", nativeInv.e_(), nativeInv.n_())));
            }

            this.bi = container;
            this.bi.d = this.bT;
            this.bi.a((ICrafting)this);
        }
    }

    public void setMetaData(CompoundTag meta) {
        this.metadata = meta;
        this.d_();
    }

    public void saveMeta() {
        super.saveMeta();
        metadata.put("PreviousIP", getPlayer().getIP());
    }

    public String getLastJoined() {
        return metadata.getString("LastJoin");
    }

    public void storeLastJoin(String lastJoin) {
        metadata.put("LastJoin", lastJoin);
    }

    @Override
    public void initializeNewMeta() {
        if (metadata == null) {
            super.initializeNewMeta();
        }
    }
    //
}
