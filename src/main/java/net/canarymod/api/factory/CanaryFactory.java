package net.canarymod.api.factory;

public final class CanaryFactory implements Factory {

    private final ItemFactory itemFactory = new CanaryItemFactory();
    private final PotionFactory potionFactory = new CanaryPotionFactory();
    private final EntityFactory entityFactory = new CanaryEntityFactory();
    private final ObjectFactory objFactory = new CanaryObjectFactory();
    private final NBTFactory nbtFactory = new CanaryNBTFactory();
    private final PacketFactory pcktFactory = new CanaryPacketFactory();
    private final ChatComponentFactory chatCompFactory = new CanaryChatComponentFactory();

    @Override
    public ItemFactory getItemFactory() {
        return itemFactory;
    }

    @Override
    public PotionFactory getPotionFactory() {
        return potionFactory;
    }

    @Override
    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    @Override
    public ObjectFactory getObjectFactory() {
        return objFactory;
    }

    @Override
    public NBTFactory getNBTFactory() {
        return nbtFactory;
    }

    @Override
    public PacketFactory getPacketFactory() {
        return pcktFactory;
    }

    @Override
    public ChatComponentFactory getChatComponentFactory() {
        return chatCompFactory;
    }

}
