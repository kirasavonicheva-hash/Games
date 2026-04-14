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
//Аннотация и интерфейс
//@ModInitializer — Fabric требует интерфейс для модов
//Метод onInitialize() вызывается один раз при загрузке игры
public class HorrorMod implements ModInitializer {
    // Константы
    // MOD_ID — уникальный идентификатор мода (используется в ресурсах и идентификаторах)
    //LOGGER — для вывода сообщений в консоль (debug/info/error)
    public static final String MOD_ID = "horrormod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Horror Mod");
        ModEntities.register();
        ModSounds.register();
        // Спавнер
        //Каждый тик сервера (20 раз в секунду) вызывается код
        //Перебирает все миры (верхний мир, нетер, энд)
        //В каждом мире пытается заспавнить монстра через HorrorSpawner
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