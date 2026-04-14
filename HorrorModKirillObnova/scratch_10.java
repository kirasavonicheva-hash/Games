package com.example.horrormod.entity;
//вторая сущьность
import com.example.horrormod.init.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import java.util.List;
import java.util.Random;

public class SecondHorrorEntity extends HostileEntity {
    private static final Random RANDOM = new Random();
    private int textureVariant;
    private int ambientSoundCooldown = 0;

    public SecondHorrorEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.textureVariant = RANDOM.nextInt(3);
        this.experiencePoints = 10;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.3, true));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.1));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 10.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(20.0);
        this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(4.0);
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.55);
        this.getAttributeInstance(EntityAttributes.FOLLOW_RANGE).setBaseValue(48.0);
        this.getAttributeInstance(EntityAttributes.ARMOR).setBaseValue(0.0);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            if (target instanceof PlayerEntity player) {
                float damage = 3 + RANDOM.nextInt(3);
                player.damage(this.getDamageSources().mobAttack(this), damage);
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.SLOWNESS, 200, 2, false, true));
                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.POOF,
                            target.getX(), target.getY() + 0.5, target.getZ(),
                            15, 0.5, 0.5, 0.5, 0.05);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if (ambientSoundCooldown <= 0) {
                Box box = this.getBoundingBox().expand(12);
                List<PlayerEntity> players = this.getWorld().getNonSpectatingEntities(PlayerEntity.class, box);
                if (!players.isEmpty()) {
                    playHorrorSound();
                    ambientSoundCooldown = 80 + RANDOM.nextInt(80);
                }
            } else {
                ambientSoundCooldown--;
            }
        }
    }

    private void playHorrorSound() {
        int soundIndex = RANDOM.nextInt(2);
        SoundEvent sound;
        if (soundIndex == 0) {
            sound = ModSounds.SECOND_HORROR_AMBIENT1;
        } else {
            sound = ModSounds.SECOND_HORROR_AMBIENT2;
        }
        this.playSound(sound, 0.7f, 1.0f + RANDOM.nextFloat() * 0.5f);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.SECOND_HORROR_AMBIENT1;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SECOND_HORROR_AMBIENT2;
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