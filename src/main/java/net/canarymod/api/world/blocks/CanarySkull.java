package net.canarymod.api.world.blocks;

import com.mojang.authlib.GameProfile;
import net.canarymod.Canary;
import net.canarymod.ToolBox;
import net.canarymod.api.PlayerReference;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.minecraft.tileentity.TileEntitySkull;

import java.util.UUID;

/**
 * Skull wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanarySkull extends CanaryTileEntity implements Skull {

    /**
     * Constructs a new wrapper for TileEntitySkull
     *
     * @param tileentity the TileEntitySkull to wrap
     */
    public CanarySkull(TileEntitySkull tileentity) {
        super(tileentity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSkullType() {
        return getTileEntity().b();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSkullType(int type) {
        this.setSkullAndExtraType(type, getExtraType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExtraType() {
        GameProfile profile = getTileEntity().a();
        if (profile == null) {
            return null;
        }
        if (profile.getId() != null) {
            return profile.getId().toString();
        } else {
            return profile.getName();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExtraType(String extra) {
        this.setSkullAndExtraType(getSkullType(), extra);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSkullAndExtraType(int type, String extra) {
        if (extra == null) {
            getTileEntity().a(type);
        } else {
            GameProfile profile = null;
            if (ToolBox.isUUID(extra)) {
                Player player = Canary.getServer().getPlayerFromUUID(extra);
                if (player != null) {
                    profile = ((CanaryPlayer) player).getHandle().bJ();
                }
            } else {
                PlayerReference player = Canary.getServer().matchKnownPlayer(extra);
                if (player != null && player.isOnline()) {
                    profile = ((CanaryPlayer) player).getHandle().bJ();
                } else {
                    profile = new GameProfile((UUID) null, extra);
                }
            }

            if (profile != null) {
                getTileEntity().a(profile);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRotation() {
        return getTileEntity().getRotation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRotation(int rot) {
        getTileEntity().a(rot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileEntitySkull getTileEntity() {
        return (TileEntitySkull) tileentity;
    }
}
