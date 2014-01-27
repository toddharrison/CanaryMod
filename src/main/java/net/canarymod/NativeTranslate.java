package net.canarymod;

import net.minecraft.util.StatCollector;

/**
 * The Pot O' Gold
 *
 * @author Jason (darkdiplomat)
 */
final class NativeTranslate extends NativeTranslateBridge {

    static {
        $ = new NativeTranslate();
    }

    static void initialize() {
    }

    @Override
    public String nativeTranslate(String key) {
        return StatCollector.a(key);
    }

    @Override
    public String nativeTranslate(String key, Object... args) {
        return StatCollector.a(key, args);
    }

    @Override
    public boolean nativeCanTranslate(String key) {
        return StatCollector.c(key);
    }
}
