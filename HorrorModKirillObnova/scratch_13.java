package com.example.horrormod.init;
//звуки для 2 сущностей
import com.example.horrormod.HorrorMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    // Звуки для первой сущности (Stalker)
    public static SoundEvent HORROR_AMBIENT1;
    public static SoundEvent HORROR_AMBIENT2;
    public static SoundEvent HORROR_AMBIENT3;

    // Звуки для второй сущности (Looter)
    public static SoundEvent SECOND_HORROR_AMBIENT1;
    public static SoundEvent SECOND_HORROR_AMBIENT2;

    public static void register() {
        // Первая сущность
        HORROR_AMBIENT1 = registerSound("horror_ambient1");
        HORROR_AMBIENT2 = registerSound("horror_ambient2");
        HORROR_AMBIENT3 = registerSound("horror_ambient3");

        // Вторая сущность
        SECOND_HORROR_AMBIENT1 = registerSound("second_horror_ambient1");
        SECOND_HORROR_AMBIENT2 = registerSound("second_horror_ambient2");
    }

    private static SoundEvent registerSound(String id) {
        Identifier identifier = new Identifier(HorrorMod.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}