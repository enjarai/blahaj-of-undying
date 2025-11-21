package dev.enjarai.blahajtotem.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class BlahajParticle extends AnimatedParticle {
    protected BlahajParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int[] colors, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 1.25F);
        this.velocityMultiplier = 0.6F;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.scale *= 0.75F;
        this.maxAge = 60 + this.random.nextInt(12);
//        this.setSpriteForAge(spriteProvider);

        if (colors.length >= 1) {
            this.setColor(colors[this.random.nextInt(colors.length)]);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<BlahajParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(BlahajParticleEffect effect, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new BlahajParticle(world, x, y, z, velocityX, velocityY, velocityZ, effect.colors(), this.spriteProvider);
        }
    }
}
