package net.canarymod.api.entity.living.humanoid;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.living.humanoid.npc.ai.*;
import net.canarymod.api.entity.living.humanoid.npchelpers.EntityNPCJumpHelper;
import net.canarymod.api.entity.living.humanoid.npchelpers.EntityNPCLookHelper;
import net.canarymod.api.entity.living.humanoid.npchelpers.EntityNPCMoveHelper;
import net.canarymod.api.entity.living.humanoid.npchelpers.PathNavigateGroundNPC;
import net.canarymod.api.entity.living.humanoid.npchelpers.PathNavigateNPC;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.position.Location;
import net.canarymod.util.NMSToolBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * NonPlayableCharacter (NPC) Entity class
 *
 * @author Jason (darkdiplomat)
 */
public final class EntityNonPlayableCharacter extends EntityPlayer {
    private EntityNPCMoveHelper move_helper;
    private EntityNPCJumpHelper jump_helper;
    private EntityNPCLookHelper look_helper;
    private PathNavigateNPC path_navigate;

    private static GameProfile genFakeProfile(UUID uuid, String name, String skinName) {
        GameProfile ofNPC = new GameProfile(uuid, name);
        ofNPC.getProperties().put("textures", NMSToolBox.getSkinProperty(skinName));
        return ofNPC;
    }

    public EntityNonPlayableCharacter(UUID uuid, String name, String skinName, Location location) {
        super(((CanaryWorld) location.getWorld()).getHandle(), genFakeProfile(uuid, name, skinName));
        World world = ((CanaryWorld) location.getWorld()).getHandle();
        this.move_helper = new EntityNPCMoveHelper(this);
        this.jump_helper = new EntityNPCJumpHelper(this);
        this.look_helper = new EntityNPCLookHelper(this);
        this.path_navigate = new PathNavigateGroundNPC(this, ((CanaryWorld) location.getWorld()).getHandle());
        /*this.path_navigate.a(false);
        this.path_navigate.b(false);
        this.path_navigate.d(false);
        this.path_navigate.e(true);*/
        this.S = 0.0F;
        this.J = 0.0F;
        this.b((double) location.getX(), location.getY(), location.getZ(), location.getRotation(), location.getPitch());
        while (!world.a(this, this.aQ()).isEmpty()) {
            this.b(this.s, this.t + 1.0D, this.u);
        }
        this.entity = new CanaryNonPlayableCharacter(this);
    }

    public EntityNonPlayableCharacter(String name, Location location) {
        this(UUID.randomUUID(), name, name, location);
    }

    @Override
    public void c(int i0) { // NO Portal Use
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) { // NO NBTTag yet
    }

    @Override
    public void b(NBTTagCompound nbttagcompound) { // NO NBTTag yet
    }

    @Override
    public boolean c(NBTTagCompound nbttagcompound) { // NO NBTTag yet
        return false;
    }

    @Override
    public boolean d(NBTTagCompound nbttagcompound) { // NO NBTTag yet
        return false;
    }

    @Override
    public void e(NBTTagCompound nbttagcompound) { // NO NBTTag yet
    }

    @Override
    public void f(NBTTagCompound nbttagcompound) { // NO NBTTag yet
    }

    @Override
    public void j() {
        if (!this.I) {
            new Update().call(getNPC());
        }
        super.j();
    }

    @Override
    protected void bJ() {
        this.getPathNavigate().k();
        //this.E();
        this.getMoveHelper().c();
        this.getLook_helper().a();
        this.getJumpHelper().b();
    }

    @Override
    public boolean e(EntityPlayer entityplayer) { // RightClicked
        super.c(entityplayer);
        if (this.entity != null) {
            new Clicked(((EntityPlayerMP) entityplayer).getPlayer()).call(getNPC());
            return true;
        }
        return false;
    }

    @Override
    public boolean a(DamageSource damagesource, float i0) {
        boolean toRet = super.a(damagesource, i0);

        if (toRet && damagesource.j() != null) {
            CanaryEntity atk = damagesource.j().getCanaryEntity();
            new Attacked(LivingBase.class.cast(atk)).call(getNPC());
        }
        return toRet;
    }

    @Override
    public void J() {
        super.B();
        new Destroyed().call(getNPC());
    }

    /*protected void bi() {
        this.move_helper.c();
    }*/

    public void entityliving_j_clone(float f0) {
        super.j(f0);
        //this.m(f0);
        this.aY = f0;
    }

    public EntityNPCJumpHelper getJumpHelper() {
        return jump_helper;
    }

    public EntityNPCMoveHelper getMoveHelper() {
        return move_helper;
    }

    public EntityNPCLookHelper getLook_helper() {
        return look_helper;
    }

    public PathNavigateNPC getPathNavigate() {
        return this.path_navigate;
    }

    public void a(float f0) {
    } // Food Exhaustion stuff

    /*public float g() { // Eye Height (in EntityPlayer its set to 0.12F so we need to override that
        return 1.62F;
    }*/

    // ICommandSender things
    public void a(IChatComponent ichatcomponent) {
    }

    public boolean a(int i0, String s0) {
        return false;
    }

    public BlockPos c() {
        return new BlockPos(this.s, this.t + 0.5D, this.u);
    }

    /*
    * onPickUp Method, used to call NPCAI implementation PickupItem
    */
    @Override
    public void a(Entity entity, int i0) {
        if (!entity.I && !this.o.D) {
            super.a(entity, i0);
            if (entity instanceof EntityItem) {
                new PickupItem(((EntityItem) entity).getEntityItem().getItem()).call(this.getNPC());
            }

        }
    }

    public CanaryNonPlayableCharacter getNPC() {
        return (CanaryNonPlayableCharacter) entity;
    }
    
    /* 
    *  Entity Living Methods 
    *  These are the few extra methods needed for path finding and Vanilla AI 
    *  system to work.
    */


    public void m(float f0) {
        this.aY = f0;
    }

    public void j(float f0) {
        super.j(f0);
        this.m(f0);
    }

    protected boolean b() {
        return true;
    }

    //protected void E() { }

    public int bP() {
        return 40;
    }

    public void a(Entity entity, float f0, float f1) {
        double d0 = entity.s - this.s;
        double d1 = entity.u - this.u;
        double d2;

        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

            d2 = entitylivingbase.t + (double) entitylivingbase.aR() - (this.t + (double) this.aR());
        } else {
            d2 = (entity.aQ().b + entity.aQ().e) / 2.0D - (this.t + (double) this.aR());
        }

        double d3 = (double) MathHelper.a(d0 * d0 + d1 * d1);
        float f2 = (float) (Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
        float f3 = (float) (-(Math.atan2(d2, d3) * 180.0D / 3.1415927410125732D));

        this.z = this.b(this.z, f3, f1);
        this.y = this.b(this.y, f2, f0);
    }

    private float b(float f0, float f1, float f2) {
        float f3 = MathHelper.g(f1 - f0);

        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f0 + f3;
    }

    // RAndom method that needs to be overriden
    @Override
    public boolean v() {
        return false;
    }
}
