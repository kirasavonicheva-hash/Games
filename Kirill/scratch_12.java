package com.example.horrormod.init;
import com.kirillhorror.thekirillhorror.untitled.HorrorMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
public class ModEntities {
    public static EntityType<HorrorEntity> HORROR_ENTITY;
    public static EntityType<SecondHorrorEntity> SECOND_HORROR_ENTITY;
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
        SECOND_HORROR_ENTITY = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(HorrorMod.MOD_ID, "second_horror_entity"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SecondHorrorEntity::new)
                        .dimensions(EntityDimensions.fixed(0.6f, 1.2f))
                        .trackRangeBlocks(48)
                        .trackedUpdateRate(2)
                        .forceTrackedVelocityUpdates(true)
                        .build()
        );
        HorrorMod.LOGGER.info();
    }
}
class HorrorEntity extends Entity {
    public HorrorEntity(EntityType<?> type, World world) {
        super(type, world);
    }
    @Override
    protected void initDataTracker() {
    }
    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }
    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}