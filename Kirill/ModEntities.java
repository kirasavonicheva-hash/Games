package com.kirillhorror.thekirillhorror.init;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
public class ModEntities {
    public static final EntityType<Entity> HORROR_ENTITY;
    static {
        HORROR_ENTITY = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(HorrorMod.MOD_ID, "horror_entity"),
                FabricEntityTypeBuilder.create()
                        .dimensions(EntityDimensions.fixed(0.8f, 1.95f))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(2)
                        .forceTrackedVelocityUpdates(true)
                        .build()
        );
    }
}
class HorrorMod {
    public static String MOD_ID;
}
