package net.minecraft.network.rcon;

import com.google.common.collect.Maps;
import net.canarymod.config.Configuration;
import net.canarymod.config.ServerConfiguration;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class RConThreadQuery extends RConThreadBase {

    private long h;
    private int i;
    private int j;
    private int k;
    private String l;
    private String m;
    private DatagramSocket n;
    private byte[] o = new byte[1460];
    private DatagramPacket p;
    private Map q;
    private String r;
    private String s;
    private Map t;
    private long u;
    private RConOutputStream v;
    private long w;
    private final ServerConfiguration serverConfig = Configuration.getServerConfig(); // CanaryMod

    public RConThreadQuery(IServer iserver) {
        super(iserver, "Query Listener");
        this.i = serverConfig.getQueryPort();
        this.s = iserver.C();
        this.j = iserver.D();
        this.l = iserver.E();
        this.k = iserver.H();
        this.m = iserver.T();
        this.w = 0L;
        this.r = "0.0.0.0";
        if (0 != this.s.length() && !this.r.equals(this.s)) {
            this.r = this.s;
        }
        else {
            this.s = "0.0.0.0";

            try {
                InetAddress inetaddress = InetAddress.getLocalHost();

                this.r = inetaddress.getHostAddress();
            }
            catch (UnknownHostException unknownhostexception) {
                this.c("Unable to determine local host IP, please set server-ip in \'" + serverConfig.getFile().getFilePath() + "\' : " + unknownhostexception.getMessage());
            }
        }

        if (0 == this.i) {
            this.i = this.j;
            this.b("Setting default query port to " + this.i);
            serverConfig.getFile().setInt("query-port", i);
            iserver.a();
        }

        this.q = Maps.newHashMap();
        this.v = new RConOutputStream(1460);
        this.t = Maps.newHashMap();
        this.u = (new Date()).getTime();
    }

    private void a(byte[] abyte, DatagramPacket datagrampacket) throws IOException {
        this.n.send(new DatagramPacket(abyte, abyte.length, datagrampacket.getSocketAddress()));
    }

    private boolean a(DatagramPacket datagrampacket) throws IOException {
        byte[] abyte = datagrampacket.getData();
        int i0 = datagrampacket.getLength();
        SocketAddress socketaddress = datagrampacket.getSocketAddress();

        this.a("Packet len " + i0 + " [" + socketaddress + "]");
        if (3 <= i0 && -2 == abyte[0] && -3 == abyte[1]) {
            this.a("Packet \'" + RConUtils.a(abyte[2]) + "\' [" + socketaddress + "]");
            switch (abyte[2]) {
                case 0:
                    if (!this.c(datagrampacket).booleanValue()) {
                        this.a("Invalid challenge [" + socketaddress + "]");
                        return false;
                    }
                    else if (15 == i0) {
                        this.a(this.b(datagrampacket), datagrampacket);
                        this.a("Rules [" + socketaddress + "]");
                    }
                    else {
                        RConOutputStream rconoutputstream = new RConOutputStream(1460);

                        rconoutputstream.a((int)0);
                        rconoutputstream.a(this.a(datagrampacket.getSocketAddress()));
                        rconoutputstream.a(this.l);
                        rconoutputstream.a("SMP");
                        rconoutputstream.a(this.m);
                        rconoutputstream.a(Integer.toString(this.d()));
                        rconoutputstream.a(Integer.toString(this.k));
                        rconoutputstream.a((short)this.j);
                        rconoutputstream.a(this.r);
                        this.a(rconoutputstream.a(), datagrampacket);
                        this.a("Status [" + socketaddress + "]");
                    }

                case 9:
                    this.d(datagrampacket);
                    this.a("Challenge [" + socketaddress + "]");
                    return true;

                default:
                    return true;
            }
        }
        else {
            this.a("Invalid packet [" + socketaddress + "]");
            return false;
        }
    }

    private byte[] b(DatagramPacket datagrampacket) throws IOException {
        long i0 = MinecraftServer.ax();

        if (i0 < this.w + 5000L) {
            byte[] abyte = this.v.a();
            byte[] abyte1 = this.a(datagrampacket.getSocketAddress());

            abyte[1] = abyte1[0];
            abyte[2] = abyte1[1];
            abyte[3] = abyte1[2];
            abyte[4] = abyte1[3];
            return abyte;
        }
        else {
            this.w = i0;
            this.v.b();
            this.v.a((int)0);
            this.v.a(this.a(datagrampacket.getSocketAddress()));
            this.v.a("splitnum");
            this.v.a((int)128);
            this.v.a((int)0);
            this.v.a("hostname");
            this.v.a(this.l);
            this.v.a("gametype");
            this.v.a("SMP");
            this.v.a("game_id");
            this.v.a("MINECRAFT");
            this.v.a("version");
            this.v.a(this.b.F());
            this.v.a("plugins");
            this.v.a(this.b.K());
            this.v.a("map");
            this.v.a(this.m);
            this.v.a("numplayers");
            this.v.a("" + this.d());
            this.v.a("maxplayers");
            this.v.a("" + this.k);
            this.v.a("hostport");
            this.v.a("" + this.j);
            this.v.a("hostip");
            this.v.a(this.r);
            this.v.a((int)0);
            this.v.a((int)1);
            this.v.a("player_");
            this.v.a((int)0);
            String[] astring = this.b.I();
            String[] astring1 = astring;
            int i1 = astring.length;

            for (int i2 = 0; i2 < i1; ++i2) {
                String s0 = astring1[i2];

                this.v.a(s0);
            }

            this.v.a((int)0);
            return this.v.a();
        }
    }

    private byte[] a(SocketAddress socketaddress) {
        return ((RConThreadQuery.Auth)this.t.get(socketaddress)).c();
    }

    private Boolean c(DatagramPacket datagrampacket) {
        SocketAddress socketaddress = datagrampacket.getSocketAddress();

        if (!this.t.containsKey(socketaddress)) {
            return Boolean.valueOf(false);
        }
        else {
            byte[] abyte = datagrampacket.getData();

            return ((RConThreadQuery.Auth)this.t.get(socketaddress)).a() != RConUtils.c(abyte, 7, datagrampacket.getLength()) ? Boolean.valueOf(false) : Boolean.valueOf(true);
        }
    }

    private void d(DatagramPacket datagrampacket) throws IOException {
        RConThreadQuery.Auth rconthreadquery_auth = new RConThreadQuery.Auth(datagrampacket);

        this.t.put(datagrampacket.getSocketAddress(), rconthreadquery_auth);
        this.a(rconthreadquery_auth.b(), datagrampacket);
    }

    private void f() {
        if (this.a) {
            long i0 = MinecraftServer.ax();

            if (i0 >= this.h + 30000L) {
                this.h = i0;
                Iterator iterator = this.t.entrySet().iterator();

                while (iterator.hasNext()) {
                    Entry entry = (Entry)iterator.next();

                    if (((RConThreadQuery.Auth)entry.getValue()).a(i0).booleanValue()) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void run() {
        this.b("Query running on " + this.s + ":" + this.i);
        this.h = MinecraftServer.ax();
        this.p = new DatagramPacket(this.o, this.o.length);

        try {
            while (this.a) {
                try {
                    this.n.receive(this.p);
                    this.f();
                    this.a(this.p);
                }
                catch (SocketTimeoutException sockettimeoutexception) {
                    this.f();
                }
                catch (PortUnreachableException portunreachableexception) {
                    ;
                }
                catch (IOException ioexception) {
                    this.a((Exception)ioexception);
                }
            }
        }
        finally {
            this.e();
        }
    }

    public void a() {
        if (!this.a) {
            if (0 < this.i && '\uffff' >= this.i) {
                if (this.g()) {
                    super.a();
                }
            }
            else {
                this.c("Invalid query port " + this.i + " found in \'" + this.b.b() + "\' (queries disabled)");
            }
        }
    }

    private void a(Exception exception) {
        if (this.a) {
            this.c("Unexpected exception, buggy JRE? (" + exception.toString() + ")");
            if (!this.g()) {
                this.d("Failed to recover from buggy JRE, shutting down!");
                this.a = false;
            }
        }
    }

    private boolean g() {
        try {
            this.n = new DatagramSocket(this.i, InetAddress.getByName(this.s));
            this.a(this.n);
            this.n.setSoTimeout(500);
            return true;
        }
        catch (SocketException socketexception) {
            this.c("Unable to initialise query system on " + this.s + ":" + this.i + " (Socket): " + socketexception.getMessage());
        }
        catch (UnknownHostException unknownhostexception) {
            this.c("Unable to initialise query system on " + this.s + ":" + this.i + " (Unknown Host): " + unknownhostexception.getMessage());
        }
        catch (Exception exception) {
            this.c("Unable to initialise query system on " + this.s + ":" + this.i + " (E): " + exception.getMessage());
        }

        return false;
    }

    class Auth {

        private long b = (new Date()).getTime();
        private int c;
        private byte[] d;
        private byte[] e;
        private String f;

        public Auth(DatagramPacket datagrampacket) {
            byte[] abyte = datagrampacket.getData();

            this.d = new byte[4];
            this.d[0] = abyte[3];
            this.d[1] = abyte[4];
            this.d[2] = abyte[5];
            this.d[3] = abyte[6];
            this.f = new String(this.d);
            this.c = (new Random()).nextInt(16777216);
            this.e = String.format("\t%s%d\u0000", new Object[]{ this.f, Integer.valueOf(this.c) }).getBytes();
        }

        public Boolean a(long i0) {
            return Boolean.valueOf(this.b < i0);
        }

        public int a() {
            return this.c;
        }

        public byte[] b() {
            return this.e;
        }

        public byte[] c() {
            return this.d;
        }
    }
}
