package com.example.horrormod;
import com.example.horrormod.entity.HorrorEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import com.example.horrormod.entity.HorrorEntityModel;
import com.example.horrormod.init.ModEntities;
public class HorrorModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.HORROR_ENTITY, HorrorEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(
                HorrorEntityRenderer.ModelLayers.HORROR_ENTITY,
                HorrorEntityModel::getTexturedModelData
        );
    }
}