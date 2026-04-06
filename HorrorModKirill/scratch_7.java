package com.example.horrormod.world;
import com.example.horrormod.entity.HorrorEntity;
import com.example.horrormod.init.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import java.util.Random;
public class HorrorSpawner {
    private static final Random RANDOM = new Random();
    private static int spawnCooldown = 0;
    public static void trySpawnHorror(ServerWorld world) {
        if (spawnCooldown > 0) {
            spawnCooldown--;
            return;
        }
        long time = world.getTimeOfDay() % 24000;
        if (time < 13000 || time > 23000) {
            return;
        }
        if (RANDOM.nextInt(100) > 5) {
            return;
        }
        var players = world.getPlayers();
        if (players.isEmpty()) return;
        var player = players.get(RANDOM.nextInt(players.size()));
        BlockPos playerPos = player.getBlockPos();
        for (int attempt = 0; attempt < 20; attempt++) {
            int x = playerPos.getX() + (RANDOM.nextInt(48) - 24);
            int z = playerPos.getZ() + (RANDOM.nextInt(48) - 24);
            int y = getSurfaceHeight(world, x, z);
            BlockPos spawnPos = new BlockPos(x, y, z);
            if (world.getLightLevel(LightType.BLOCK, spawnPos) < 7 &&
                    world.getLightLevel(LightType.SKY, spawnPos) < 7) {
                Biome biome = world.getBiome(spawnPos).value();
                if (biome == BiomeKeys.PLAINS ||
                        biome == BiomeKeys.DARK_FOREST ||
                        biome == BiomeKeys.TAIGA ||
                        biome == BiomeKeys.OLD_GROWTH_TAIGA ||
                        biome == BiomeKeys.FOREST) {
                    BlockState ground = world.getBlockState(spawnPos.down());
                    if (ground.isSolid() && world.isAir(spawnPos)) {
                        HorrorEntity horror = ModEntities.HORROR_ENTITY.create(world);
                        if (horror != null) {
                            horror.refreshPositionAndAngles(spawnPos, 0, 0);
                            world.spawnEntity(horror);
                            spawnCooldown = 100;
                            return;
                        }
                    }
                }
            }
        }
    }
    private static int getSurfaceHeight(ServerWorld world, int x, int z) {
        return world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z);
    }
}