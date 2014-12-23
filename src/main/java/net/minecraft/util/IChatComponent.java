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

        public IChatComponent deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) {
            if (p_deserialize_1_.isJsonPrimitive()) {
                return new ChatComponentText(p_deserialize_1_.getAsString());
            }
            else if (!p_deserialize_1_.isJsonObject()) {
                if (p_deserialize_1_.isJsonArray()) {
                    JsonArray chatcomponenttranslation = p_deserialize_1_.getAsJsonArray();
                    IChatComponent chatcomponentscore = null;
                    Iterator jsonobject4 = chatcomponenttranslation.iterator();

                    while (jsonobject4.hasNext()) {
                        JsonElement jsonelement = (JsonElement) jsonobject4.next();
                        IChatComponent ichatcomponent1 = this.deserialize(jsonelement, jsonelement.getClass(), p_deserialize_3_);

                        if (chatcomponentscore == null) {
                            chatcomponentscore = ichatcomponent1;
                        }
                        else {
                            chatcomponentscore.a(ichatcomponent1);
                        }
                    }

                    return chatcomponentscore;
                }
                else {
                    throw new JsonParseException("Don\'t know how to turn " + p_deserialize_1_.toString() + " into a Component");
                }
            }
            else {
                JsonObject jsonobject3 = p_deserialize_1_.getAsJsonObject();
                Object jsonarray3;

                if (jsonobject3.has("text")) {
                    jsonarray3 = new ChatComponentText(jsonobject3.get("text").getAsString());
                }
                else if (jsonobject3.has("translate")) {
                    String iterator2 = jsonobject3.get("translate").getAsString();

                    if (jsonobject3.has("with")) {
                        JsonArray ichatcomponent2 = jsonobject3.getAsJsonArray("with");
                        Object[] i2 = new Object[ichatcomponent2.size()];

                        for (int i3 = 0; i3 < i2.length; ++i3) {
                            i2[i3] = this.deserialize(ichatcomponent2.get(i3), p_deserialize_2_, p_deserialize_3_);
                            if (i2[i3] instanceof ChatComponentText) {
                                ChatComponentText object1 = (ChatComponentText) i2[i3];

                                if (object1.b().g() && object1.a().isEmpty()) {
                                    i2[i3] = object1.g();
                                }
                            }
                        }

                        jsonarray3 = new ChatComponentTranslation(iterator2, i2);
                    }
                    else {
                        jsonarray3 = new ChatComponentTranslation(iterator2, new Object[0]);
                    }
                }
                else if (jsonobject3.has("score")) {
                    JsonObject chatcomponentselector = jsonobject3.getAsJsonObject("score");

                    if (!chatcomponentselector.has("name") || !chatcomponentselector.has("objective")) {
                        throw new JsonParseException("A score component needs a least a name and an objective");
                    }

                    jsonarray3 = new ChatComponentScore(JsonUtils.h(chatcomponentselector, "name"), JsonUtils.h(chatcomponentselector, "objective"));
                    if (chatcomponentselector.has("value")) {
                        ((ChatComponentScore) jsonarray3).b(JsonUtils.h(chatcomponentselector, "value"));
                    }
                }
                else {
                    if (!jsonobject3.has("selector")) {
                        throw new JsonParseException("Don\'t know how to turn " + p_deserialize_1_.toString() + " into a Component");
                    }

                    jsonarray3 = new ChatComponentSelector(JsonUtils.h(jsonobject3, "selector"));
                }

                if (jsonobject3.has("extra")) {
                    JsonArray jsonarray4 = jsonobject3.getAsJsonArray("extra");

                    if (jsonarray4.size() <= 0) {
                        throw new JsonParseException("Unexpected empty array of components");
                    }

                    for (int aobject1 = 0; aobject1 < jsonarray4.size(); ++aobject1) {
                        ((IChatComponent) jsonarray3).a(this.deserialize(jsonarray4.get(aobject1), p_deserialize_2_, p_deserialize_3_));
                    }
                }

                ((IChatComponent) jsonarray3).a((ChatStyle) p_deserialize_3_.deserialize(p_deserialize_1_, ChatStyle.class));
                return (IChatComponent) jsonarray3;
            }
        }

        private void a(ChatStyle p_a_1_, JsonObject p_a_2_, JsonSerializationContext p_a_3_) {
            JsonElement jsonobject3 = p_a_3_.serialize(p_a_1_);

            if (jsonobject3.isJsonObject()) {
                JsonObject jsonarray3 = (JsonObject) jsonobject3;
                Iterator iterator2 = jsonarray3.entrySet().iterator();

                while (iterator2.hasNext()) {
                    Entry ichatcomponent2 = (Entry) iterator2.next();

                    p_a_2_.add((String) ichatcomponent2.getKey(), (JsonElement) ichatcomponent2.getValue());
                }
            }

        }

        public JsonElement serialize(IChatComponent p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
            if (p_serialize_1_ instanceof ChatComponentText && p_serialize_1_.b().g() && p_serialize_1_.a().isEmpty()) {
                return new JsonPrimitive(((ChatComponentText) p_serialize_1_).g());
            }
            else {
                JsonObject jsonobject3 = new JsonObject();

                if (!p_serialize_1_.b().g()) {
                    this.a(p_serialize_1_.b(), jsonobject3, p_serialize_3_);
                }

                if (!p_serialize_1_.a().isEmpty()) {
                    JsonArray jsonarray3 = new JsonArray();
                    Iterator iterator2 = p_serialize_1_.a().iterator();

                    while (iterator2.hasNext()) {
                        IChatComponent ichatcomponent2 = (IChatComponent) iterator2.next();

                        jsonarray3.add(this.serialize(ichatcomponent2, ichatcomponent2.getClass(), p_serialize_3_));
                    }

                    jsonobject3.add("extra", jsonarray3);
                }

                if (p_serialize_1_ instanceof ChatComponentText) {
                    jsonobject3.addProperty("text", ((ChatComponentText) p_serialize_1_).g());
                }
                else if (p_serialize_1_ instanceof ChatComponentTranslation) {
                    ChatComponentTranslation chatcomponenttranslation = (ChatComponentTranslation) p_serialize_1_;

                    jsonobject3.addProperty("translate", chatcomponenttranslation.i());
                    if (chatcomponenttranslation.j() != null && chatcomponenttranslation.j().length > 0) {
                        JsonArray jsonarray4 = new JsonArray();
                        Object[] aobject1 = chatcomponenttranslation.j();
                        int i2 = aobject1.length;

                        for (int i3 = 0; i3 < i2; ++i3) {
                            Object object1 = aobject1[i3];

                            if (object1 instanceof IChatComponent) {
                                jsonarray4.add(this.serialize((IChatComponent) object1, object1.getClass(), p_serialize_3_));
                            }
                            else {
                                jsonarray4.add(new JsonPrimitive(String.valueOf(object1)));
                            }
                        }

                        jsonobject3.add("with", jsonarray4);
                    }
                }
                else if (p_serialize_1_ instanceof ChatComponentScore) {
                    ChatComponentScore chatcomponentscore = (ChatComponentScore) p_serialize_1_;
                    JsonObject jsonobject4 = new JsonObject();

                    jsonobject4.addProperty("name", chatcomponentscore.g());
                    jsonobject4.addProperty("objective", chatcomponentscore.h());
                    jsonobject4.addProperty("value", chatcomponentscore.e());
                    jsonobject3.add("score", jsonobject4);
                }
                else {
                    if (!(p_serialize_1_ instanceof ChatComponentSelector)) {
                        throw new IllegalArgumentException("Don\'t know how to serialize " + p_serialize_1_ + " as a Component");
                    }

                    ChatComponentSelector chatcomponentselector = (ChatComponentSelector) p_serialize_1_;

                    jsonobject3.addProperty("selector", chatcomponentselector.g());
                }

                return jsonobject3;
            }
        }

        public static String a(IChatComponent p_a_0_) {
            return a.toJson(p_a_0_);
        }

        public static IChatComponent a(String p_a_0_) {
            return (IChatComponent) a.fromJson(p_a_0_, IChatComponent.class);
        }

        public JsonElement serialize(Object p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
            return this.serialize((IChatComponent) p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }

        static {
            GsonBuilder gsonbuilder = new GsonBuilder();

            gsonbuilder.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer());
            gsonbuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
            gsonbuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
            a = gsonbuilder.create();
        }
    }
}
