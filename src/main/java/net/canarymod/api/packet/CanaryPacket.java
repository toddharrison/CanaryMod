package net.canarymod.api.packet;

public class CanaryPacket implements net.canarymod.api.packet.Packet {

    protected final net.minecraft.network.Packet packet;

    public CanaryPacket(net.minecraft.network.Packet packet) {
        this.packet = packet;
    }

    @Override
    public int getPacketSize() {
        //return this.packet.();
        return 0; // Packet Size Unknown
    }

    @Override
    public int getPacketId() {
        //return this.packet.n();
        return 0; // Packet ID unknown
    }

    public net.minecraft.network.Packet getPacket() {
        return this.packet;
    }

}
