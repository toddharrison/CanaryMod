package net.minecraft.util;


import net.canarymod.api.chat.CanaryChatComponent;

import java.util.Iterator;


public class ChatComponentText extends ChatComponentStyle {

    private final String b;
    private final CanaryChatComponent canaryChatComponent; // CanaryMod

    public ChatComponentText(String s0) {
        this.b = s0;
        this.canaryChatComponent = new CanaryChatComponent(this); // CanaryMod: 
    }

    public String g() {
        return this.b;
    }

    public String e() {
        return this.b;
    }

    public ChatComponentText h() {
        ChatComponentText chatcomponenttext = new ChatComponentText(this.b);

        chatcomponenttext.a(this.b().l());
        Iterator iterator = this.a().iterator();

        while (iterator.hasNext()) {
            IChatComponent ichatcomponent = (IChatComponent) iterator.next();

            chatcomponenttext.a(ichatcomponent.f());
        }

        return chatcomponenttext;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        else if (!(object instanceof ChatComponentText)) {
            return false;
        }
        else {
            ChatComponentText chatcomponenttext = (ChatComponentText) object;

            return this.b.equals(chatcomponenttext.g()) && super.equals(object);
        }
    }

    public String toString() {
        return "TextComponent{text=\'" + this.b + '\'' + ", siblings=" + this.a + ", style=" + this.b() + '}';
    }

    public IChatComponent f() {
        return this.h();
    }

    // CanaryMod
    @Override
    public CanaryChatComponent getWrapper() {
        return canaryChatComponent;
    }
}
