package io.github.vampirestudios.hgm;

import com.electronwill.nightconfig.core.conversion.SpecIntInRange;
import io.github.vampirestudios.hgm.utils.Constants;
import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1.serializer.PartitioningSerializer;
import net.minecraft.nbt.CompoundTag;

@Config(name = "hgm")
@Config.Gui.Background("textures/block/dirt.png")
public class ModConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("laptop_settings")
    @ConfigEntry.Gui.TransitiveObject
    public LaptopSettings laptopSettings = new LaptopSettings();

    @ConfigEntry.Category("router_settings")
    @ConfigEntry.Gui.TransitiveObject
    public RouterSettings routerSettings = new RouterSettings();

    @ConfigEntry.Category("printer_settings")
    @ConfigEntry.Gui.TransitiveObject
    public PrinterSettings printerSettings = new PrinterSettings();

    @ConfigEntry.Category("application_settings")
    @ConfigEntry.Gui.TransitiveObject
    public ApplicationSettings applicationSettings = new ApplicationSettings();

    @Config(name = "laptop_settings")
    public static class LaptopSettings implements ConfigData {

        @SpecIntInRange(min = 1, max = 200)
        public int pingRate = 20;

    }

    @Config(name = "router_settings")
    public static class RouterSettings implements ConfigData {

        @SpecIntInRange(min = 10, max = 100)
        public int signalRange = 20;

        @SpecIntInRange(min = 1, max = 200)
        public int beaconInterval = 20;

        @SpecIntInRange(min = 1, max = 1000)
        public int maxDevices = 16;

    }

    @Config(name = "printer_settings")
    public static class PrinterSettings implements ConfigData {

        public boolean overridePrintSpeed = false;

        @SpecIntInRange(min = 1, max = 600)
        public int customPrintSpeed = 20;

        @SpecIntInRange(min = 0, max = 99)
        public int maxPaperCount = 64;

    }

    @Config(name = "application_settings")
    public static class ApplicationSettings implements ConfigData {

        public boolean renderPrintedIn3D = false;

    }

    public void readSyncTag(CompoundTag tag) {
        if (tag.containsKey("pingRate", Constants.NBT.TAG_INT)) {
           this.laptopSettings.pingRate = tag.getInt("pingRate");
        }
        if (tag.containsKey("signalRange", Constants.NBT.TAG_INT)) {
            this.routerSettings.signalRange = tag.getInt("signalRange");
        }
    }

    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("pingRate", this.laptopSettings.pingRate);
        tag.putInt("signalRange", this.routerSettings.signalRange);
        return tag;
    }

}
