package com.kirillhorror.thekirillhorror.untitled;
import net.fabricmc.api.ModInitializer;
public class HorrorMod implements ModInitializer {
    public static String MOD_ID;
    public static Process LOGGER;
    @Override
    public void onInitialize() {
        // код инициализации здесь
        System.out.println("Hello Fabric world!");
    }
}
