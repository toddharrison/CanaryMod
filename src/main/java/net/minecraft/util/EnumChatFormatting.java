package net.minecraft.util;


import net.canarymod.api.chat.CanaryChatFormatting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public enum EnumChatFormatting {

    BLACK('0'), //
    DARK_BLUE('1'), //
    DARK_GREEN('2'), //
    DARK_AQUA('3'), //
    DARK_RED('4'), //
    DARK_PURPLE('5'), //
    GOLD('6'), //
    GRAY('7'), //
    DARK_GRAY('8'), //
    BLUE('9'), //
    GREEN('a'), //
    AQUA('b'), //
    RED('c'), //
    LIGHT_PURPLE('d'), //
    YELLOW('e'), //
    WHITE('f'), //
    OBFUSCATED('k', true), //
    BOLD('l', true), //
    STRIKETHROUGH('m', true), //
    UNDERLINE('n', true), //
    ITALIC('o', true), //
    RESET('r');

    private static final Map w = new HashMap();
    private static final Map x = new HashMap();
    private static final Pattern y = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
    private final char z;
    private final boolean A;
    private final String B;
    private final CanaryChatFormatting ccf; // CanaryMod

    private EnumChatFormatting(char c0) {
        this(c0, false);
    }

    private EnumChatFormatting(char c0, boolean flag0) {
        this.z = c0;
        this.A = flag0;
        this.B = "\u00a7" + c0;
        this.ccf = new CanaryChatFormatting(this); // CanaryMod: install wrapper
    }

    public char a() {
        return this.z;
    }

    public boolean b() {
        return this.A;
    }

    public boolean c() {
        return !this.A && this != RESET;
    }

    public String d() {
        return this.name().toLowerCase();
    }

    public String toString() {
        return this.B;
    }

    // CanaryMod
    public CanaryChatFormatting getWrapper() {
        return ccf;
    }

    public static EnumChatFormatting b(String s0) {
        return s0 == null ? null : (EnumChatFormatting) x.get(s0.toLowerCase());
    }

    public static Collection a(boolean flag0, boolean flag1) {
        ArrayList arraylist = new ArrayList();
        EnumChatFormatting[] aenumchatformatting = values();
        int i0 = aenumchatformatting.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EnumChatFormatting enumchatformatting = aenumchatformatting[i1];

            if ((!enumchatformatting.c() || flag0) && (!enumchatformatting.b() || flag1)) {
                arraylist.add(enumchatformatting.d());
            }
        }

        return arraylist;
    }

    static {
        EnumChatFormatting[] aenumchatformatting = values();
        int i0 = aenumchatformatting.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EnumChatFormatting enumchatformatting = aenumchatformatting[i1];

            w.put(Character.valueOf(enumchatformatting.a()), enumchatformatting);
            x.put(enumchatformatting.d(), enumchatformatting);
        }

    }
}
