package com.example.horrormod;
import com.example.horrormod.init.ModEntities;
import com.example.horrormod.init.ModSounds;
import com.example.horrormod.world.HorrorSpawner;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class HorrorMod implements ModInitializer {
    public static final String MOD_ID = "horrormod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Horror Mod...");
        ModEntities.register();
        ModSounds.register();
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                HorrorSpawner.trySpawnHorror(world);
            }
        });
        LOGGER.info("Horror Mod initialized!");
    }
    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}