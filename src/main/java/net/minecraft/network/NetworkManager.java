package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.authlib.properties.Property;
import io.netty.channel.*;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import javax.crypto.SecretKey;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.UUID;

public class NetworkManager extends SimpleChannelInboundHandler {
    private static final Logger f = LogManager.getLogger();
    public static final Marker a = MarkerManager.getMarker("NETWORK");
    public static final Marker b = MarkerManager.getMarker("NETWORK_PACKETS", a);
    public static final AttributeKey c = AttributeKey.valueOf("protocol");
    public static final LazyLoadBase d = new LazyLoadBase() {
        protected NioEventLoopGroup a() {
            return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
        }

        protected Object b() {
            return this.a();
        }
    };
    public static final LazyLoadBase e = new LazyLoadBase() {

        protected LocalEventLoopGroup a() {
            return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
        }

        protected Object b() {
            return this.a();
        }
    };
    private final EnumPacketDirection g;
    private final Queue h = Queues.newConcurrentLinkedQueue();
    private Channel i;
    //CanaryMod Start - Bungeecord support
    public SocketAddress j; //CanaryMod - Bungeecord support
    public UUID spoofedUUID;
    public Property[] spoofedProfile;
    //CanaryMod End - Bungeecord support
    private INetHandler k;
    private IChatComponent l;
    private boolean m;
    private boolean n;

    public NetworkManager(EnumPacketDirection enumpacketdirection) {
        this.g = enumpacketdirection;
    }

    public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
        super.channelActive(channelhandlercontext);
        this.i = channelhandlercontext.channel();
        this.j = this.i.remoteAddress();

        try {
            this.a(EnumConnectionState.HANDSHAKING);
        }
        catch (Throwable throwable) {
            f.fatal(throwable);
        }
    }

    public void a(EnumConnectionState enumconnectionstate) {
        this.i.attr(c).set(enumconnectionstate);
        this.i.config().setAutoRead(true);
        f.debug("Enabled auto read");
    }

    public void channelInactive(ChannelHandlerContext channelhandlercontext) {
        this.a((IChatComponent)(new ChatComponentTranslation("disconnect.endOfStream", new Object[0])));
    }

    public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
        f.debug("Disconnecting " + this.b(), throwable);
        this.a((IChatComponent)(new ChatComponentTranslation("disconnect.genericReason", new Object[]{ "Internal Exception: " + throwable })));
    }

    protected void channelRead0(ChannelHandlerContext channelhandlercontext, Packet packet) {
        if (this.i.isOpen()) {
            try {
                packet.a(this.k);
            }
            catch (ThreadQuickExitException threadquickexitexception) {
                ;
            }
        }
    }

    public void a(INetHandler inethandler) {
        Validate.notNull(inethandler, "packetListener", new Object[0]);
        f.debug("Set listener of {} to {}", new Object[]{ this, inethandler });
        this.k = inethandler;
    }

    public void a(Packet packet) {
        if (this.i != null && this.i.isOpen()) {
            this.m();
            this.a(packet, (GenericFutureListener[])null);
        }
        else {
            this.h.add(new NetworkManager.InboundHandlerTuplePacketListener(packet, (GenericFutureListener[])null));
        }
    }

    public void a(Packet packet, GenericFutureListener genericfuturelistener, GenericFutureListener... agenericfuturelistener) {
        if (this.i != null && this.i.isOpen()) {
            this.m();
            this.a(packet, (GenericFutureListener[])ArrayUtils.add(agenericfuturelistener, 0, genericfuturelistener));
        }
        else {
            this.h.add(new NetworkManager.InboundHandlerTuplePacketListener(packet, (GenericFutureListener[])ArrayUtils.add(agenericfuturelistener, 0, genericfuturelistener)));
        }
    }

    private void a(final Packet packet, final GenericFutureListener[] agenericfuturelistener) {
        final EnumConnectionState enumconnectionstate = EnumConnectionState.a(packet);
        final EnumConnectionState enumconnectionstate1 = (EnumConnectionState)this.i.attr(c).get();

        if (enumconnectionstate1 != enumconnectionstate) {
            f.debug("Disabled auto read");
            this.i.config().setAutoRead(false);
        }

        if (this.i.eventLoop().inEventLoop()) {
            if (enumconnectionstate != enumconnectionstate1) {
                this.a(enumconnectionstate);
            }

            ChannelFuture channelfuture = this.i.writeAndFlush(packet);

            if (agenericfuturelistener != null) {
                channelfuture.addListeners(agenericfuturelistener);
            }
            channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
        else {
            this.i.eventLoop().execute(new Runnable() {
                                           public void run() {
                                               if (enumconnectionstate != enumconnectionstate1) {
                                                   NetworkManager.this.a(enumconnectionstate);
                                               }

                                               ChannelFuture channelfuture1 = NetworkManager.this.i.writeAndFlush(packet);

                                               if (agenericfuturelistener != null) {
                                                   channelfuture1.addListeners(agenericfuturelistener);
                                               }
                                               channelfuture1.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                                           }
                                       }
                                      );
        }
    }

    private void m() {
        if (this.i != null && this.i.isOpen()) {
            while (!this.h.isEmpty()) {
                NetworkManager.InboundHandlerTuplePacketListener networkmanager_inboundhandlertuplepacketlistener = (NetworkManager.InboundHandlerTuplePacketListener)this.h.poll();

                this.a(networkmanager_inboundhandlertuplepacketlistener.a, networkmanager_inboundhandlertuplepacketlistener.b);
            }
        }
    }

    public void a() {
        this.m();
        if (this.k instanceof IUpdatePlayerListBox) {
            ((IUpdatePlayerListBox)this.k).c();
        }

        this.i.flush();
    }

    public SocketAddress b() {
        return this.j;
    }

    public void a(IChatComponent ichatcomponent) {
        if (this.i.isOpen()) {
            this.i.close().awaitUninterruptibly();
            this.l = ichatcomponent;
        }
    }

    public boolean c() {
        return this.i instanceof LocalChannel || this.i instanceof LocalServerChannel;
    }

    public void a(SecretKey secretkey) {
        this.m = true;
        this.i.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.a(2, secretkey)));
        this.i.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.a(1, secretkey)));
    }

    public boolean g() {
        return this.i != null && this.i.isOpen();
    }

    public boolean h() {
        return this.i == null;
    }

    public INetHandler i() {
        return this.k;
    }

    public IChatComponent j() {
        return this.l;
    }

    public void k() {
        this.i.config().setAutoRead(false);
    }

    public void a(int i0) {
        if (i0 >= 0) {
            if (this.i.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                ((NettyCompressionDecoder)this.i.pipeline().get("decompress")).a(i0);
            }
            else {
                this.i.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(i0));
            }

            if (this.i.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                ((NettyCompressionEncoder)this.i.pipeline().get("decompress")).a(i0);
            }
            else {
                this.i.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(i0));
            }
        }
        else {
            if (this.i.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
                this.i.pipeline().remove("decompress");
            }

            if (this.i.pipeline().get("compress") instanceof NettyCompressionEncoder) {
                this.i.pipeline().remove("compress");
            }
        }
    }

    public void l() {
        if (!this.h() && !this.g() && !this.n) {
            this.n = true;
            if (this.j() != null) {
                this.i().a(this.j());
            }
            else if (this.i() != null) {
                this.i().a(new ChatComponentText("Disconnected"));
            }
        }
    }

    protected void channelRead0(ChannelHandlerContext channelhandlercontext, Object object) {
        this.channelRead0(channelhandlercontext, (Packet)object);
    }

    static class InboundHandlerTuplePacketListener {
        private final Packet a;
        private final GenericFutureListener[] b;

        public InboundHandlerTuplePacketListener(Packet p_i45146_1_, GenericFutureListener... p_i45146_2_) {
            this.a = p_i45146_1_;
            this.b = p_i45146_2_;
        }
    }
}