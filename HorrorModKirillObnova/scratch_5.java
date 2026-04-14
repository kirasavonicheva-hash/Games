package com.example.horrormod.entity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
public class HorrorEntityModel extends SinglePartEntityModel<HorrorEntity> {
    //структура тела
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    public HorrorEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }
    //геометрическая постановка
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create()
                        .uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData leftArm = modelPartData.addChild("left_arm", ModelPartBuilder.create()
                        .uv(40, 16).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(5.0F, 2.0F, 0.0F));
        ModelPartData rightArm = modelPartData.addChild("right_arm", ModelPartBuilder.create()
                        .uv(40, 16).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        ModelPartData leftLeg = modelPartData.addChild("left_leg", ModelPartBuilder.create()
                        .uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(2.0F, 12.0F, 0.0F));
        ModelPartData rightLeg = modelPartData.addChild("right_leg", ModelPartBuilder.create()
                        .uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(HorrorEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        //Голова следит за игроком/поворачивается при движении
        this.head.yaw = headYaw * 0.017453292F;   // поворот влево-вправо
        this.head.pitch = headPitch * 0.017453292F; // наклон вверх-вниз

        // Левая рука и левая нога движутся синхронно
        leftArm.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        // Правая рука и нога — в противофазе (сдвиг на π радиан)
        rightArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
    }
    //корневая часть модели возращение метода
    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
