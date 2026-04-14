//рендерер (отрисовщик) хоррор-сущности. отвечает за то, как ебланы выглядит в игре — какую модель и текстуру использовать.
package com.example.horrormod.entity;
import com.example.horrormod.HorrorMod;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
public class HorrorEntityRenderer extends MobEntityRenderer<HorrorEntity, HorrorEntityModel> {
    //загрузка 2 скинов
    private static final Identifier[] TEXTURES = new Identifier[5];
    static {
        for (int i = 0; i < 5; i++) {
            TEXTURES[i] = new Identifier(HorrorMod.MOD_ID,
                    String.format("textures/entity/horror_entity/skin_%d.png", i + 1));
        }
    }
    public HorrorEntityRenderer(EntityRendererFactory.Context context) {
        //модель
        //0.6f — размер тени под сущностью
        //HorrorEntityModel — форма/скелет монстра (кубы, конечности)
        //ModelLayers.HORROR_ENTITY — идентификатор модели, который регистрировался в клиентском
        super(context, new HorrorEntityModel(context.getPart(ModelLayers.HORROR_ENTITY)), 0.6f);
    }
    //выбор текстур
    //У каждого экземпляра монстра может быть разный вариант текстуры
    //Метод getTextureVariant() (из класса HorrorEntity) возвращает номер скина
    //Это делает монстров визуально разнообразными — не все одинаковые
    @Override
    public Identifier getTexture(HorrorEntity entity) {
        int variant = entity.getTextureVariant();
        if (variant < 0 || variant >= TEXTURES.length) {
            variant = 0;
        }
        return TEXTURES[variant];
    }
    //Вложенный класс
    //Хранит идентификатор модели, используется при регистрации
    //вызывает рендерер с заранее зарегистрированной моделью
    public static class ModelLayers {
        public static final EntityModelLayer HORROR_ENTITY = new EntityModelLayer(
                new Identifier(HorrorMod.MOD_ID, "horror_entity"), "main");
    }
}