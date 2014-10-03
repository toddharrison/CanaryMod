package net.minecraft.server.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginServer
        implements INetHandlerLoginServer
{
    private static final AtomicInteger b = new AtomicInteger(0);
    private static final Logger c = LogManager.getLogger();
    private static final Random d = new Random();
    private final byte[] e = new byte[4];
    private final MinecraftServer f;
    public final NetworkManager a;
    private LoginState g;
    private int h;
    private GameProfile i;
    private String j;
    private SecretKey k;

    public NetHandlerLoginServer(MinecraftServer minecraftserver, NetworkManager networkmanager)
    {
        this.g = LoginState.HELLO;
        this.j = "";
        this.f = minecraftserver;
        this.a = networkmanager;
        d.nextBytes(this.e);
    }

    public void a() {
        if (this.g == LoginState.READY_TO_ACCEPT) {
            c();
        }

        if (this.h++ == 600)
            a("Took too long to log in");
    }

    public void a(String s0)
    {
        try
        {
            c.info("Disconnecting " + d() + ": " + s0);
            ChatComponentText chatcomponenttext = new ChatComponentText(s0);

            this.a.a(new S00PacketDisconnect(chatcomponenttext), new GenericFutureListener[0]);
            this.a.a(chatcomponenttext);
        }
        catch (Exception exception) {
            c.error("Error whilst disconnecting player", exception);
        }
    }

    public void c()
    {
        if (!this.i.isComplete()) {
            this.i = a(this.i);
        }

        String s0 = this.f.ah().a(this.a.b(), this.i);

        if (s0 != null) {
            a(s0);
        }
        else {
            this.g = LoginState.ACCEPTED;
            this.a.a(new S02PacketLoginSuccess(this.i), new GenericFutureListener[0]);
            this.f.ah().a(this.a, this.f.ah().f(this.i));
        }
    }

    public void a(IChatComponent ichatcomponent)
    {
        c.info(d() + " lost connection: " + ichatcomponent.c());
    }

    public String d() {
        return this.i != null ? this.i.toString() + " (" + this.a.b().toString() + ")" : String.valueOf(this.a.b());
    }

    public void a(EnumConnectionState enumconnectionstate, EnumConnectionState enumconnectionstate1) {
        Validate.validState((this.g == LoginState.ACCEPTED) || (this.g == LoginState.HELLO), "Unexpected change in protocol", new Object[0]);
        Validate.validState((enumconnectionstate1 == EnumConnectionState.PLAY) || (enumconnectionstate1 == EnumConnectionState.LOGIN), "Unexpected protocol " + enumconnectionstate1, new Object[0]);
    }

    public void a(C00PacketLoginStart c00packetloginstart) {
        Validate.validState(this.g == LoginState.HELLO, "Unexpected hello packet", new Object[0]);
        this.i = c00packetloginstart.c();
        if ((this.f.Y()) && (!this.a.c())) {
            this.g = LoginState.KEY;
            this.a.a(new S01PacketEncryptionRequest(this.j, this.f.K().getPublic(), this.e), new GenericFutureListener[0]);
        }
        else {
            this.g = LoginState.READY_TO_ACCEPT;
        }
    }

    public void a(C01PacketEncryptionResponse c01packetencryptionresponse)
    {
        Validate.validState(this.g == LoginState.KEY, "Unexpected key packet", new Object[0]);
        PrivateKey s0 = this.f.K().getPrivate();

        if (!Arrays.equals(this.e, c01packetencryptionresponse.b(s0))) {
            throw new IllegalStateException("Invalid nonce!");
        }

        this.k = c01packetencryptionresponse.a(s0);
        this.g = LoginState.AUTHENTICATING;
        this.a.a(this.k);
        new Thread("User Authenticator #" + b.incrementAndGet())
        {
            public void run() {
                GameProfile gameprofile = NetHandlerLoginServer.this.i;
                try
                {
                    String s0 = new BigInteger(CryptManager.a(NetHandlerLoginServer.this.j, NetHandlerLoginServer.this.f.K().getPublic(), NetHandlerLoginServer.this.k)).toString(16);

                    NetHandlerLoginServer.this.i = NetHandlerLoginServer.this.f.av().hasJoinedServer(new GameProfile((UUID)null, gameprofile.getName()), s0);
                    if (NetHandlerLoginServer.this.i != null) {
                        NetHandlerLoginServer.c.info("UUID of player " + NetHandlerLoginServer.this.i.getName() + " is " + NetHandlerLoginServer.this.i.getId());
                        NetHandlerLoginServer.this.g = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
                    }
                    else if (NetHandlerLoginServer.this.f.N()) {
                        NetHandlerLoginServer.c.warn("Failed to verify username but will let them in anyway!");
                        NetHandlerLoginServer.this.i = NetHandlerLoginServer.this.a(gameprofile);
                        NetHandlerLoginServer.this.g = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
                    }
                    else {
                        NetHandlerLoginServer.this.a("Failed to verify username!");
                        NetHandlerLoginServer.c.error("Username '" + NetHandlerLoginServer.this.i.getName() + "' tried to join with an invalid session");
                    }
                }
                catch (AuthenticationUnavailableException authenticationunavailableexception) {
                    if (NetHandlerLoginServer.this.f.N()) {
                        NetHandlerLoginServer.c.warn("Authentication servers are down but will let them in anyway!");
                        NetHandlerLoginServer.this.i = NetHandlerLoginServer.this.a(gameprofile);
                        NetHandlerLoginServer.this.g = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
                    }
                    else {
                        NetHandlerLoginServer.this.a("Authentication servers are down. Please try again later, sorry!");
                        NetHandlerLoginServer.c.error("Couldn't verify username because servers are unavailable");
                    }
                }
            }
        }
                .start();
    }

    protected GameProfile a(GameProfile gameprofile)
    {
        UUID uuid;
        //CanaryMod Start - Bungeecord support!
        if (a.spoofedUUID != null) {
            uuid = a.spoofedUUID;
        } else {
            uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameprofile.getName()).getBytes(Charsets.UTF_8));
        }
        gameprofile = new GameProfile(uuid, gameprofile.getName());
        if (a.spoofedProfile != null) {
            for (Property property : a.spoofedProfile) {
                this.i.getProperties().put(property.getName(), property);
            }
        }
        return gameprofile;
        //CanaryMod End - Bungeecord support!
    }

    static enum LoginState
    {
        HELLO("HELLO", 0),
        KEY("KEY" ,1),
        AUTHENTICATING("AUTHENTICATING", 2),
        READY_TO_ACCEPT("READY_TO_ACCEPT", 3),
        ACCEPTED("ACCEPTED", 4);
        private static final LoginState[] $VALUES = { HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, ACCEPTED };

        private LoginState(String p_i45297_1_, int p_i45297_2_)
        {
        }
    }
}