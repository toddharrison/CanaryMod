package net.canarymod.api;

import net.minecraft.util.AxisAlignedBB;

/**
 * (Axis Aligned) Bounding Box wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBoundingBox implements BoundingBox {
    private final AxisAlignedBB axisAlignedBB;

    public CanaryBoundingBox(AxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
    }

    private static CanaryBoundingBox newBB(AxisAlignedBB axisAlignedBB) {
        return new CanaryBoundingBox(axisAlignedBB);
    }

    @Override
    public BoundingBox addCoordinates(double x, double y, double z) {
        return newBB(this.axisAlignedBB.a(x, y, z));
    }

    @Override
    public BoundingBox expand(double x, double y, double z) {
        return newBB(this.axisAlignedBB.b(x, y, z));
    }

    @Override
    public BoundingBox contract(double x, double y, double z) {
        return newBB(this.axisAlignedBB.d(x, y, z));
    }

    @Override
    public BoundingBox union(BoundingBox boundingBox) {
        return newBB(this.axisAlignedBB.a(((CanaryBoundingBox)boundingBox).getNative()));
    }

    @Override
    public BoundingBox offset(double x, double y, double z) {
        return newBB(this.axisAlignedBB.c(x, y, z));
    }

    @Override
    public double calculateXOffset(BoundingBox boundingBox, double xOffset) {
        return this.axisAlignedBB.a(((CanaryBoundingBox)boundingBox).getNative(), xOffset);
    }

    @Override
    public double calculateYOffset(BoundingBox boundingBox, double yOffset) {
        return this.axisAlignedBB.b(((CanaryBoundingBox)boundingBox).getNative(), yOffset);
    }

    @Override
    public double calculateZOffset(BoundingBox boundingBox, double zOffset) {
        return this.axisAlignedBB.c(((CanaryBoundingBox)boundingBox).getNative(), zOffset);
    }

    @Override
    public boolean intersectsWith(BoundingBox boundingBox) {
        return this.axisAlignedBB.b(((CanaryBoundingBox)boundingBox).getNative());
    }

    @Override
    public double getAverageEdgeLength() {
        return this.axisAlignedBB.a();
    }

    public AxisAlignedBB getNative() {
        return this.axisAlignedBB;
    }
}
