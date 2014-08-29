package net.canarymod.api.entity.living.humanoid;

import com.mojang.authlib.GameProfile;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.living.humanoid.npc.ai.Attacked;
import net.canarymod.api.entity.living.humanoid.npc.ai.Clicked;
import net.canarymod.api.entity.living.humanoid.npc.ai.Destroyed;
import net.canarymod.api.entity.living.humanoid.npc.ai.Update;
import net.canarymod.api.entity.living.humanoid.npchelpers.EntityNPCJumpHelper;
import net.canarymod.api.entity.living.humanoid.npchelpers.EntityNPCLookHelper;
import net.canarymod.api.entity.living.humanoid.npchelpers.EntityNPCMoveHelper;
import net.canarymod.api.entity.living.humanoid.npchelpers.PathNavigateNPC;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.position.Location;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.UUID;

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

    private static GameProfile genFakeProfile(String name) {
        UUID five$SaysthisisntUniqueAtAll = UUID.randomUUID();
        return new GameProfile(five$SaysthisisntUniqueAtAll.toString().replaceAll("-", ""), name);
    }

    public EntityNonPlayableCharacter(String name, Location location) {
        super(((CanaryWorld) location.getWorld()).getHandle(), genFakeProfile(name));
        World world = ((CanaryWorld) location.getWorld()).getHandle();
        this.move_helper = new EntityNPCMoveHelper(this);
        this.jump_helper = new EntityNPCJumpHelper(this);
        this.look_helper = new EntityNPCLookHelper(this);
        this.path_navigate = new PathNavigateNPC(this, ((CanaryWorld) location.getWorld()).getHandle());
        this.X = 0.0F;
        this.M = 0.0F;
        this.b((double) location.getX(), location.getY(), location.getZ(), location.getRotation(), location.getPitch());
        while (!world.a(this, this.D).isEmpty()) {
            this.b(this.t, this.u + 1.0D, this.v);
        }
        this.entity = new CanaryNonPlayableCharacter(this);
    }

    @Override
    public void b(int i0) { // NO Portal Use
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
    public void h() {
        super.h();
        if (!this.L) {
            new Update().call(getNPC());
            getNPC().update();
            getLook_helper().a(); //Update Looking
        }
    }

    @Override
    public boolean c(EntityPlayer entityplayer) { // RightClicked
        super.c(entityplayer);
        if (this.entity != null) {
            new Clicked(((EntityPlayerMP) entityplayer).getPlayer()).call(getNPC());
            getNPC().clicked(((EntityPlayerMP) entityplayer).getPlayer());
            return true;
        }
        return false;
    }

    @Override
    public boolean a(DamageSource damagesource, float i0) {
        boolean toRet = super.a(damagesource, i0);

        if (toRet && damagesource.j() != null) {
            CanaryEntity atk = damagesource.j().getCanaryEntity();
            if (atk instanceof LivingBase) {
                new Attacked(LivingBase.class.cast(atk)).call(getNPC());
            }
            getNPC().attacked(atk);
        }
        return toRet;
    }

    @Override
    public void B() {
        super.B();
        new Destroyed().call(getNPC());
        getNPC().destroyed();
    }

    protected void bi() {
        this.move_helper.c();
    }

    public void entityliving_n_clone(float f0) {
        this.bf = f0;
        super.i(f0);
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

    public float g() { // Eye Height (in EntityPlayer its set to 0.12F so we need to override that
        return 1.62F;
    }

    // ICommandSender things
    //String b_();
    //IChatComponent c_();
    public void a(IChatComponent ichatcomponent) {
    }

    public boolean a(int i0, String s0) {
        return false;
    }

    public ChunkCoordinates f_() {
        return new ChunkCoordinates(MathHelper.c(this.t), MathHelper.c(this.u + 0.5D), MathHelper.c(this.v));
    }
    //World d();
    //

    public CanaryNonPlayableCharacter getNPC() {
        return (CanaryNonPlayableCharacter) entity;
    }
}
