package net.minecraft.network.play.server;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;

public class S23PacketBlockChange implements Packet {
    public BlockPos a; // CanaryMod: private => public;
    public IBlockState b; // CanaryMod: private => public;

    public S23PacketBlockChange() {
    }

    public S23PacketBlockChange(World world, BlockPos blockpos) {
        this.a = blockpos;
        this.b = world.p(blockpos);
    }

    public void a(PacketBuffer packetbuffer) throws IOException {
        this.a = packetbuffer.c();
        this.b = (IBlockState)Block.d.a(packetbuffer.e());
    }

    public void b(PacketBuffer packetbuffer) throws IOException {
        packetbuffer.a(this.a);
        packetbuffer.b(Block.d.b(this.b));
    }

    public void a(INetHandlerPlayClient inethandlerplayclient) {
        inethandlerplayclient.a(this);
    }

    public void a(INetHandler inethandler) {
        this.a((INetHandlerPlayClient)inethandler);
    }
}
