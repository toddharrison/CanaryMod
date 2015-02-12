package net.canarymod;

import net.canarymod.api.factory.CanaryFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CanaryModTest {

    public static void enableInstance() throws Exception {
        if (Canary.instance() == null) {
            Constructor<CanaryMod> cm = CanaryMod.class.getDeclaredConstructor(Object.class);
            cm.setAccessible(true);
            CanaryMod canaryMod = cm.newInstance(new Object());

            Field instance = Canary.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(canaryMod, canaryMod);
        }
    }

    public static void enableFactories() throws Exception {
        enableInstance();
        if (Canary.factory() == null) {
            Field factory = Canary.class.getDeclaredField("factory");
            factory.setAccessible(true);
            factory.set(Canary.instance(), new CanaryFactory());
        }
    }
}