package com.example.horrormod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
public class HorrorModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Object HorrorEntityRenderer;
        EntityRendererRegistry.register(ModEntities.HORROR_ENTITY, HorrorEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(
                HorrorEntityRenderer.ModelLayers.HORROR_ENTITY,
                HorrorEntityModel::getTexturedModelData
        );
    }
}
class ModEntities {
    public static EntityType<? extends Entity> HORROR_ENTITY;
}
class HorrorEntityModel {
    public static TexturedModelData getTexturedModelData(){
        return null;
    }
}