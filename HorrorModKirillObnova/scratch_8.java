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
        //Регистрирует рендерер (отвечает за отрисовку сущности в мире).
        //Когда игра встречает ModEntities.HORROR_ENTITY, она будет использовать HorrorEntityRenderer для показа модели, анимации и текстур.
        EntityRendererRegistry.register(ModEntities.HORROR_ENTITY, HorrorEntityRenderer::new);
        //EntityModelLayerRegistry.registerModelLayer(...)
        //Регистрирует модель сущности (ее геометрию).
        //HorrorEntityModel::getTexturedModelData определяет форму сущности (кубы, размеры, pivot-точки), а привязанный ModelLayers.HORROR_ENTITY — это уникальный идентификатор модели.
        EntityModelLayerRegistry.registerModelLayer(
                HorrorEntityRenderer.ModelLayers.HORROR_ENTITY,
                HorrorEntityModel::getTexturedModelData
        );
    }
}
