package com.example.horrormod.init;
import com.example.horrormod.HorrorMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
public class ModSounds {
    public static SoundEvent HORROR_AMBIENT1;
    public static SoundEvent HORROR_AMBIENT2;
    public static SoundEvent HORROR_AMBIENT3;
    public static void register() {
        HORROR_AMBIENT1 = registerSound("horror_ambient1");
        HORROR_AMBIENT2 = registerSound("horror_ambient2");
        HORROR_AMBIENT3 = registerSound("horror_ambient3");
    }
    private static SoundEvent registerSound(String id) {
        Identifier identifier = new Identifier(HorrorMod.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }
}