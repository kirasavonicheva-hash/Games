package com.example.horrormod.entity;
import com.example.horrormod.init.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;
import java.util.Random;
public class HorrorEntity extends HostileEntity {
    private static final Random RANDOM = new Random();
    private int textureVariant;
    private int ambientSoundCooldown = 0;
    protected HorrorEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        //Разнообразие текстур
        this.textureVariant = RANDOM.nextInt(5); // 0-4
        this.experiencePoints = 15;
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }
    //Поведение цели и задачи
    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(40.0);
        this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(7.0); // 6-8 среднее
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
        this.getAttributeInstance(EntityAttributes.FOLLOW_RANGE).setBaseValue(32.0);
        this.getAttributeInstance(EntityAttributes.ARMOR).setBaseValue(2.0);
    }
    @Override
    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            if (target instanceof PlayerEntity player) {
                float damage = 6 + RANDOM.nextInt(3);
                //Атака на игрока
                player.damage(this.getDamageSources().mobAttack(this), damage);
                player.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
                        net.minecraft.entity.effect.StatusEffects.BLINDNESS, 1200, 1, false, true));
                player.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
                        net.minecraft.entity.effect.StatusEffects.SLOWNESS, 1200, 1, false, true));
                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.SQUID_INK,
                            target.getX(), target.getY() + 1, target.getZ(),
                            20, 0.5, 1, 0.5, 0.1);
                }
            }
            return true;
        }
        return false;
    }
    @Override
    //Амбиентные звуки
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if (ambientSoundCooldown <= 0) {
                Box box = this.getBoundingBox().expand(10);
                List<PlayerEntity> players = this.getWorld().getNonSpectatingEntities(PlayerEntity.class, box);
                if (!players.isEmpty()) {
                    playHorrorSound();
                    ambientSoundCooldown = 100 + RANDOM.nextInt(100);
                }
            } else {
                ambientSoundCooldown--;
            }
        }
    }
    private void playHorrorSound() {
        int soundIndex = RANDOM.nextInt(3);
        SoundEvent sound;
        switch (soundIndex) {
            case 0 -> sound = ModSounds.HORROR_AMBIENT1;
            case 1 -> sound = ModSounds.HORROR_AMBIENT2;
            default -> sound = ModSounds.HORROR_AMBIENT3;
        }
        this.playSound(sound, 0.8f, 0.8f + RANDOM.nextFloat() * 0.4f);
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.HORROR_AMBIENT2;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.HORROR_AMBIENT3;
    }
    public int getTextureVariant() {
        return textureVariant;
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("TextureVariant", textureVariant);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("TextureVariant")) {
            textureVariant = nbt.getInt("TextureVariant");
        }
    }
    @Override
    public boolean isPersistent() {
        return true;
    }
    @Override
    public boolean cannotDespawn() {
        return true;
    }
    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }
}