package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.authlib.properties.Property;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
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
    private static final Logger i = LogManager.getLogger();
    public static final Marker a = MarkerManager.getMarker("NETWORK");
    public static final Marker b = MarkerManager.getMarker("NETWORK_PACKETS", a);
    public static final Marker c = MarkerManager.getMarker("NETWORK_STAT", a);
    public static final AttributeKey d = new AttributeKey("protocol");
    public static final AttributeKey e = new AttributeKey("receivable_packets");
    public static final AttributeKey f = new AttributeKey("sendable_packets");
    public static final NioEventLoopGroup g = new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
    public static final NetworkStatistics h = new NetworkStatistics();
    private final boolean j;
    private final Queue k = Queues.newConcurrentLinkedQueue();
    private final Queue l = Queues.newConcurrentLinkedQueue();
    private Channel m;
    //CanaryMod Start - Bungeecord support
    public SocketAddress n; //CanaryMod - Bungeecord support
    public UUID spoofedUUID;
    public Property[] spoofedProfile;
    //CanaryMod End - Bungeecord support
    private INetHandler o;
    private EnumConnectionState p;
    private IChatComponent q;
    private boolean r;

    public NetworkManager(boolean flag0) {
        this.j = flag0;
    }

    public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
        super.channelActive(channelhandlercontext);
        this.m = channelhandlercontext.channel();
        this.n = this.m.remoteAddress();
        a(EnumConnectionState.HANDSHAKING);
    }

    public void a(EnumConnectionState enumconnectionstate) {
        this.p = ((EnumConnectionState)this.m.attr(d).getAndSet(enumconnectionstate));
        this.m.attr(e).set(enumconnectionstate.a(this.j));
        this.m.attr(f).set(enumconnectionstate.b(this.j));
        this.m.config().setAutoRead(true);
        i.debug("Enabled auto read");
    }

    public void channelInactive(ChannelHandlerContext channelhandlercontext) {
        a(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
    }

    public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
        ChatComponentTranslation chatcomponenttranslation;
        if ((throwable instanceof TimeoutException)) {
            chatcomponenttranslation = new ChatComponentTranslation("disconnect.timeout", new Object[0]);
        }
        else {
            chatcomponenttranslation = new ChatComponentTranslation("disconnect.genericReason", new Object[]{ "Internal Exception: " + throwable });
        }

        a(chatcomponenttranslation);
    }

    protected void channelRead0(ChannelHandlerContext channelhandlercontext, Packet packet) {
        if (this.m.isOpen()) {
            if (packet.a()) {
                packet.a(this.o);
            }
            else {
                this.k.add(packet);
            }
        }
    }

    public void a(INetHandler inethandler) {
        Validate.notNull(inethandler, "packetListener", new Object[0]);
        i.debug("Set listener of {} to {}", new Object[]{ this, inethandler });
        this.o = inethandler;
    }

    public void a(Packet packet, GenericFutureListener[] agenericfuturelistener) {
        if ((this.m != null) && (this.m.isOpen())) {
            i();
            b(packet, agenericfuturelistener);
        }
        else {
            this.l.add(new InboundHandlerTuplePacketListener(packet, agenericfuturelistener));
        }
    }

    private void b(final Packet packet, final GenericFutureListener[] agenericfuturelistener) {
        final EnumConnectionState enumconnectionstate = EnumConnectionState.a(packet);
        final EnumConnectionState enumconnectionstate1 = (EnumConnectionState)this.m.attr(d).get();

        if (enumconnectionstate1 != enumconnectionstate) {
            i.debug("Disabled auto read");
            this.m.config().setAutoRead(false);
        }

        if (this.m.eventLoop().inEventLoop()) {
            if (enumconnectionstate != enumconnectionstate1) {
                a(enumconnectionstate);
            }

            this.m.writeAndFlush(packet).addListeners(agenericfuturelistener).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
        else {
            this.m.eventLoop().execute(new Runnable() {
                                           public void run() {
                                               if (enumconnectionstate != enumconnectionstate1) {
                                                   NetworkManager.this.a(enumconnectionstate);
                                               }

                                               NetworkManager.this.m.writeAndFlush(packet).addListeners(agenericfuturelistener).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                                           }
                                       }
                                      );
        }
    }

    private void i() {
        if ((this.m != null) && (this.m.isOpen())) {
            while (!this.l.isEmpty()) {
                InboundHandlerTuplePacketListener networkmanager_inboundhandlertuplepacketlistener = (InboundHandlerTuplePacketListener)this.l.poll();

                b(networkmanager_inboundhandlertuplepacketlistener.a, networkmanager_inboundhandlertuplepacketlistener.b);
            }
        }
    }

    public void a() {
        i();
        EnumConnectionState enumconnectionstate = (EnumConnectionState)this.m.attr(d).get();

        if (this.p != enumconnectionstate) {
            if (this.p != null) {
                this.o.a(this.p, enumconnectionstate);
            }

            this.p = enumconnectionstate;
        }

        if (this.o != null) {
            for (int i0 = 1000; (!this.k.isEmpty()) && (i0 >= 0); i0--) {
                Packet packet = (Packet)this.k.poll();

                packet.a(this.o);
            }

            this.o.a();
        }

        this.m.flush();
    }

    public SocketAddress b() {
        return this.n;
    }

    public void a(IChatComponent ichatcomponent) {
        if (this.m.isOpen()) {
            this.m.close();
            this.q = ichatcomponent;
        }
    }

    public boolean c() {
        return ((this.m instanceof LocalChannel)) || ((this.m instanceof LocalServerChannel));
    }

    public void a(SecretKey secretkey) {
        this.m.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.a(2, secretkey)));
        this.m.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.a(1, secretkey)));
        this.r = true;
    }

    public boolean d() {
        return (this.m != null) && (this.m.isOpen());
    }

    public INetHandler e() {
        return this.o;
    }

    public IChatComponent f() {
        return this.q;
    }

    public void g() {
        this.m.config().setAutoRead(false);
    }

    protected void channelRead0(ChannelHandlerContext channelhandlercontext, Object object) {
        channelRead0(channelhandlercontext, (Packet)object);
    }

    static class InboundHandlerTuplePacketListener {
        private final Packet a;
        private final GenericFutureListener[] b;

        public InboundHandlerTuplePacketListener(Packet p_i45146_1_, GenericFutureListener[] p_i45146_2_) {
            this.a = p_i45146_1_;
            this.b = p_i45146_2_;
        }
    }
}