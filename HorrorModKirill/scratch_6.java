package com.example.horrormod.entity;
import com.example.horrormod.HorrorMod;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
public class HorrorEntityRenderer extends MobEntityRenderer<HorrorEntity, HorrorEntityModel> {
    private static final Identifier[] TEXTURES = new Identifier[5];
    static {
        for (int i = 0; i < 5; i++) {
            TEXTURES[i] = new Identifier(HorrorMod.MOD_ID,
                    String.format("textures/entity/horror_entity/skin_%d.png", i + 1));
        }
    }
    public HorrorEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HorrorEntityModel(context.getPart(ModelLayers.HORROR_ENTITY)), 0.6f);
    }
    @Override
    public Identifier getTexture(HorrorEntity entity) {
        int variant = entity.getTextureVariant();
        if (variant < 0 || variant >= TEXTURES.length) {
            variant = 0;
        }
        return TEXTURES[variant];
    }
    public static class ModelLayers {
        public static final EntityModelLayer HORROR_ENTITY = new EntityModelLayer(
                new Identifier(HorrorMod.MOD_ID, "horror_entity"), "main");
    }
}