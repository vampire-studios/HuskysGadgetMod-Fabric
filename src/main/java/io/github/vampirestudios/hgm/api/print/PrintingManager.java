package io.github.vampirestudios.hgm.api.print;

import com.google.common.collect.HashBiMap;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class PrintingManager {
    private static HashBiMap<String, Class<? extends IPrint>> registeredPrints = HashBiMap.create();

    @Environment(EnvType.CLIENT)
    private static Map<String, IPrint.Renderer> registeredRenders;

    public static void registerPrint(Identifier identifier, Class<? extends IPrint> classPrint) {
        try {
            classPrint.getConstructor().newInstance();
            if (HuskysGadgetMod.SETUP.registerPrint(identifier, classPrint)) {
                HuskysGadgetMod.LOGGER.info("Registering print '" + classPrint.getName() + "'");
                registeredPrints.put(identifier.toString(), classPrint);
            } else {
                HuskysGadgetMod.LOGGER.error("The print '" + classPrint.getName() + "' could not be registered due to a critical error!");
            }
        } catch (NoSuchMethodException e) {
            HuskysGadgetMod.LOGGER.error("The print '" + classPrint.getName() + "' is missing an empty constructor and could not be registered!");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            HuskysGadgetMod.LOGGER.error("The print '" + classPrint.getName() + "' could not be registered due to a critical error!");
        }
    }

    public static boolean isRegisteredPrint(Class<? extends IPrint> clazz) {
        return registeredPrints.containsValue(clazz);
    }

    @Nullable
    public static IPrint getPrint(String identifier) {
        Class<? extends IPrint> clazz = registeredPrints.get(identifier);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Environment(EnvType.CLIENT)
    public static IPrint.Renderer getRenderer(IPrint print) {
        String id = getPrintIdentifier(print);
        return registeredRenders.get(id);
    }

    @Environment(EnvType.CLIENT)
    public static IPrint.Renderer getRenderer(String identifier) {
        return registeredRenders.get(identifier);
    }

    public static String getPrintIdentifier(IPrint print) {
        return registeredPrints.inverse().get(print.getClass());
    }
}
