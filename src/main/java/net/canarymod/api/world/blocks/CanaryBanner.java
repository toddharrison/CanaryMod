package net.canarymod.api.world.blocks;

import com.google.common.collect.Lists;
import net.canarymod.api.DyeColor;
import net.canarymod.api.inventory.helper.BannerPattern;
import net.canarymod.api.nbt.CanaryBaseTag;
import net.canarymod.api.nbt.CompoundTag;
import net.canarymod.api.nbt.ListTag;
import net.minecraft.tileentity.TileEntityBanner;

import java.util.List;

/**
 * Banner wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBanner extends CanaryTileEntity implements Banner {

    public CanaryBanner(TileEntityBanner banner) {
        super(banner);
    }

    @Override
    public void setColor(DyeColor dyeColor) {
        getTileEntity().setBaseColor(dyeColor.getDyeColorCode());
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.fromDyeColorCode(getTileEntity().b());
    }

    @Override
    public List<BannerPattern> getPatterns() {
        if (getTileEntity().getPatternsList() != null) {
            List<BannerPattern> patterns = Lists.newArrayList();
            ListTag listTag = (ListTag)CanaryBaseTag.wrap(getTileEntity().getPatternsList());
            for (Object tag : listTag) {
                patterns.add(BannerPattern.fromCompoundTag((CompoundTag)tag));
            }
            return patterns;
        }
        return null;
    }

    @Override
    public void setPatterns(List<BannerPattern> patterns) {
        if (getTileEntity().getPatternsList() == null) {
            getTileEntity().initializePatternsList();
        }
        ListTag listTag = (ListTag)CanaryBaseTag.wrap(getTileEntity().getPatternsList());
        for (BannerPattern pattern : patterns) {
            listTag.add(pattern.asCompoundTag());
        }
    }

    @Override
    public boolean addPattern(BannerPattern bannerPattern) {
        if (getTileEntity().getPatternsList() == null) {
            getTileEntity().initializePatternsList();
        }
        return ((ListTag)CanaryBaseTag.wrap(getTileEntity().getPatternsList())).add(bannerPattern.asCompoundTag());
    }

    @Override
    public boolean removePattern(BannerPattern bannerPattern) {
        return getTileEntity().getPatternsList() != null && ((ListTag)CanaryBaseTag.wrap(getTileEntity().getPatternsList())).remove(bannerPattern.asCompoundTag());
    }

    @Override
    public TileEntityBanner getTileEntity() {
        return (TileEntityBanner)tileentity;
    }
}
