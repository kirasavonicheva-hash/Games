package com.example.horrormod.init;
import com.example.horrormod.HorrorMod;
import com.example.horrormod.entity.HorrorEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
public class ModEntities {
    //появление еблана
    //Хранит тип сущности (как шаблон для создания монстров)
    //Generic <HorrorEntity> указывает на хоррор-класс
    public static EntityType<HorrorEntity> HORROR_ENTITY;
    //метод регестрациии
    //Вызывается при инициализации мода
    //Регистрирует сущность в реєстре сущностей Minecraft
    public static void register() {
        HORROR_ENTITY = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(HorrorMod.MOD_ID, "horror_entity"),
                //Параметры сущности базовые настройки
                //SpawnGroup.MONSTER — относится к монстрам (как зомби, скелеты)
                //HorrorEntity::new — конструктор для создания экземпляров
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HorrorEntity::new)
                        //размеры сущности
                        //Ширина: 0.8 блока (чуть уже стандартного блока)
                        //Высота: 1.95 блока (чуть выше игрока — 1.8 блока)
                        //Монстр на ровне с игроком
                        .dimensions(EntityDimensions.fixed(0.8f, 1.95f))
                        //отслеживание игрока
                        .trackRangeBlocks(64)          // виден игроку с расстояния 64 блока
                        .trackedUpdateRate(2)          // обновляет позицию каждые 2 тика
                        .forceTrackedVelocityUpdates(true) // всегда отправляет обновления скорости
                        .build()
        );
        HorrorMod.LOGGER.info("Registered Horror Entity");
    }
}

