package net.minecraft.util;


import com.google.gson.*;
import net.canarymod.api.chat.CanaryChatComponent;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


public interface IChatComponent extends Iterable {

    IChatComponent a(ChatStyle chatstyle);

    ChatStyle b();

    IChatComponent a(String s0);

    IChatComponent a(IChatComponent ichatcomponent);

    String e();

    String c();

    List a();

    IChatComponent f();

    CanaryChatComponent getWrapper(); // CanaryMod

    public static class Serializer implements JsonDeserializer, JsonSerializer {

        private static final Gson a;

        public IChatComponent deserialize(JsonElement object2, Type type2, JsonDeserializationContext jsonserializationcontext2) {
            if (object2.isJsonPrimitive()) {
                return new ChatComponentText(object2.getAsString());
            }
            else if (!object2.isJsonObject()) {
                if (object2.isJsonArray()) {
                    JsonArray object21 = object2.getAsJsonArray();
                    IChatComponent object22 = null;
                    Iterator object23 = object21.iterator();

                    while (object23.hasNext()) {
                        JsonElement object26 = (JsonElement) object23.next();
                        IChatComponent object27 = this.deserialize(object26, object26.getClass(), jsonserializationcontext2);

                        if (object22 == null) {
                            object22 = object27;
                        }
                        else {
                            object22.a(object27);
                        }
                    }

                    return object22;
                }
                else {
                    throw new JsonParseException("Don\'t know how to turn " + object2.toString() + " into a Component");
                }
            }
            else {
                JsonObject jsonobject3 = object2.getAsJsonObject();
                Object jsonarray3;

                if (jsonobject3.has("text")) {
                    jsonarray3 = new ChatComponentText(jsonobject3.get("text").getAsString());
                }
                else {
                    if (!jsonobject3.has("translate")) {
                        throw new JsonParseException("Don\'t know how to turn " + object2.toString() + " into a Component");
                    }

                    String iterator2 = jsonobject3.get("translate").getAsString();

                    if (jsonobject3.has("with")) {
                        JsonArray ichatcomponent3 = jsonobject3.getAsJsonArray("with");
                        Object[] i2 = new Object[ichatcomponent3.size()];

                        for (int i3 = 0; i3 < i2.length; ++i3) {
                            i2[i3] = this.deserialize(ichatcomponent3.get(i3), type2, jsonserializationcontext2);
                            if (i2[i3] instanceof ChatComponentText) {
                                ChatComponentText object20 = (ChatComponentText) i2[i3];

                                if (object20.b().g() && object20.a().isEmpty()) {
                                    i2[i3] = object20.g();
                                }
                            }
                        }

                        jsonarray3 = new ChatComponentTranslation(iterator2, i2);
                    }
                    else {
                        jsonarray3 = new ChatComponentTranslation(iterator2, new Object[0]);
                    }
                }

                if (jsonobject3.has("extra")) {
                    JsonArray object24 = jsonobject3.getAsJsonArray("extra");

                    if (object24.size() <= 0) {
                        throw new JsonParseException("Unexpected empty array of components");
                    }

                    for (int object25 = 0; object25 < object24.size(); ++object25) {
                        ((IChatComponent) jsonarray3).a(this.deserialize(object24.get(object25), type2, jsonserializationcontext2));
                    }
                }

                ((IChatComponent) jsonarray3).a((ChatStyle) jsonserializationcontext2.deserialize(object2, ChatStyle.class));
                return (IChatComponent) jsonarray3;
            }
        }

        private void a(ChatStyle object2, JsonObject type2, JsonSerializationContext jsonserializationcontext2) {
            JsonElement jsonobject3 = jsonserializationcontext2.serialize(object2);

            if (jsonobject3.isJsonObject()) {
                JsonObject jsonarray3 = (JsonObject) jsonobject3;
                Iterator iterator2 = jsonarray3.entrySet().iterator();

                while (iterator2.hasNext()) {
                    Entry ichatcomponent3 = (Entry) iterator2.next();

                    type2.add((String) ichatcomponent3.getKey(), (JsonElement) ichatcomponent3.getValue());
                }
            }

        }

        public JsonElement serialize(IChatComponent object2, Type type2, JsonSerializationContext jsonserializationcontext2) {
            if (object2 instanceof ChatComponentText && object2.b().g() && object2.a().isEmpty()) {
                return new JsonPrimitive(((ChatComponentText) object2).g());
            }
            else {
                JsonObject jsonobject3 = new JsonObject();

                if (!object2.b().g()) {
                    this.a(object2.b(), jsonobject3, jsonserializationcontext2);
                }

                if (!object2.a().isEmpty()) {
                    JsonArray jsonarray3 = new JsonArray();
                    Iterator iterator2 = object2.a().iterator();

                    while (iterator2.hasNext()) {
                        IChatComponent ichatcomponent3 = (IChatComponent) iterator2.next();

                        jsonarray3.add(this.serialize(ichatcomponent3, ichatcomponent3.getClass(), jsonserializationcontext2));
                    }

                    jsonobject3.add("extra", jsonarray3);
                }

                if (object2 instanceof ChatComponentText) {
                    jsonobject3.addProperty("text", ((ChatComponentText) object2).g());
                }
                else {
                    if (!(object2 instanceof ChatComponentTranslation)) {
                        throw new IllegalArgumentException("Don\'t know how to serialize " + object2 + " as a Component");
                    }

                    ChatComponentTranslation object21 = (ChatComponentTranslation) object2;

                    jsonobject3.addProperty("translate", object21.i());
                    if (object21.j() != null && object21.j().length > 0) {
                        JsonArray object22 = new JsonArray();
                        Object[] object23 = object21.j();
                        int i2 = object23.length;

                        for (int i3 = 0; i3 < i2; ++i3) {
                            Object object20 = object23[i3];

                            if (object20 instanceof IChatComponent) {
                                object22.add(this.serialize((IChatComponent) object20, object20.getClass(), jsonserializationcontext2));
                            }
                            else {
                                object22.add(new JsonPrimitive(String.valueOf(object20)));
                            }
                        }

                        jsonobject3.add("with", object22);
                    }
                }

                return jsonobject3;
            }
        }

        public static String a(IChatComponent gsonbuilder) {
            return a.toJson(gsonbuilder);
        }

        public static IChatComponent a(String gsonbuilder) {
            return (IChatComponent) a.fromJson(gsonbuilder, IChatComponent.class);
        }

        public JsonElement serialize(Object object2, Type type2, JsonSerializationContext jsonserializationcontext2) {
            return this.serialize((IChatComponent) object2, type2, jsonserializationcontext2);
        }

        static {
            GsonBuilder gsonbuilder = new GsonBuilder();

            gsonbuilder.registerTypeHierarchyAdapter(IChatComponent.class, new Serializer());
            gsonbuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
            gsonbuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
            a = gsonbuilder.create();
        }
    }
}
