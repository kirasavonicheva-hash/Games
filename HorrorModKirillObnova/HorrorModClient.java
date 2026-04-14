package com.kirillhorror.thekirillhorror.untitled.client;

import com.kirillhorror.thekirillhorror.untitled.entity.HorrorEntityRenderer;
import com.kirillhorror.thekirillhorror.untitled.entity.SecondHorrorEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import com.kirillhorror.thekirillhorror.untitled.entity.HorrorEntityModel;
import com.kirillhorror.thekirillhorror.untitled.entity.SecondHorrorEntityModel;
import com.kirillhorror.thekirillhorror.untitled.init.ModEntities;

public class HorrorModClient implements ClientModInitializer {
        @Override
        public void onInitializeClient() {
                EntityRendererRegistry.register(ModEntities.HORROR_ENTITY, HorrorEntityRenderer::new);
                EntityModelLayerRegistry.registerModelLayer(
                        HorrorEntityRenderer.ModelLayers.HORROR_ENTITY,
                        HorrorEntityModel::getTexturedModelData
                );

                EntityRendererRegistry.register(ModEntities.SECOND_HORROR_ENTITY, SecondHorrorEntityRenderer::new);
                EntityModelLayerRegistry.registerModelLayer(
                        SecondHorrorEntityRenderer.ModelLayers.SECOND_HORROR_ENTITY,
                        SecondHorrorEntityModel::getTexturedModelData
                );
        }
}