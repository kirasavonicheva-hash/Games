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
    public static EntityType<HorrorEntity> HORROR_ENTITY;
    public static void register() {
        HORROR_ENTITY = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(HorrorMod.MOD_ID, "horror_entity"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HorrorEntity::new)
                        .dimensions(EntityDimensions.fixed(0.8f, 1.95f))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(2)
                        .forceTrackedVelocityUpdates(true)
                        .build()
        );
        HorrorMod.LOGGER.info("Registered Horror Entity");
    }
}