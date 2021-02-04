package io.github.vampirestudios.hgm;

import io.github.vampirestudios.hgm.api.AppInfo;
import io.github.vampirestudios.hgm.api.app.Application;
import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.api.theme.Theme;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.object.ThemeInfo;
import io.github.vampirestudios.hgm.system.SystemApplication;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModSetup {

    List<AppInfo> allowedApps;
    private List<ThemeInfo> allowedThemes;

    public Application registerApplication(Identifier identifier, Class<? extends Application> clazz) {
        if (allowedApps == null) {
            allowedApps = new ArrayList<>();
        }
        if (SystemApplication.class.isAssignableFrom(clazz)) {
            allowedApps.add(new AppInfo(identifier, true));
        } else {
            allowedApps.add(new AppInfo(identifier, false));
        }
        return null;
    }

    public boolean registerPrint(Identifier identifier, Class<? extends IPrint> classPrint) {
        return true;
    }

    public boolean hasAllowedApplications() {
        return allowedApps != null;
    }

    public List<AppInfo> getAllowedApplications() {
        if (allowedApps == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(allowedApps);
    }

    /*@SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (allowedApps != null) {
            PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new MessageSyncApplications(allowedApps));
        }
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new MessageSyncConfig());
    }*/

    public void showNotification(CompoundTag tag) {
    }

    public Theme registerTheme(Identifier identifier) {
        if (allowedThemes == null) {
            allowedThemes = new ArrayList<>();
        }
        allowedThemes.add(new ThemeInfo(identifier));
        return null;
    }

    public boolean hasAllowedThemes() {
        return allowedThemes != null;
    }

    public List<ThemeInfo> getAllowedThemes() {
        if (allowedThemes == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(allowedThemes);
    }

    public static class Client {

        public Application registerApplication(Identifier identifier, Class<? extends Application> clazz) {
            if("minecraft".equals(identifier.getNamespace())) {
                throw new IllegalArgumentException("Invalid identifier domain");
            }

            try {
                Application application = clazz.newInstance();
                application.info = generateAppInfo(identifier, clazz);
                BaseDevice.APPLICATIONS.add(application);
                return application;
            } catch(InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return null;
        }

        private AppInfo generateAppInfo(Identifier identifier, Class<? extends Application> clazz) {
            AppInfo info = new AppInfo(identifier, SystemApplication.class.isAssignableFrom(clazz));
            info.reload();
            return info;
        }

    }

}
