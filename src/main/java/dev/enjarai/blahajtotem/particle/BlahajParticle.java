package dev.enjarai.blahajtotem.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class BlahajParticle extends SimpleAnimatedParticle {
    protected BlahajParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int[] colors, SpriteSet spriteProvider) {
        super(world, x, y, z, spriteProvider, 1.25F);
        this.friction = 0.6F;
        this.xd = velocityX;
        this.yd = velocityY;
        this.zd = velocityZ;
        this.quadSize *= 0.75F;
        this.lifetime = 60 + this.random.nextInt(12);
//        this.setSpriteForAge(spriteProvider);

        if (colors.length >= 1) {
            this.setColor(colors[this.random.nextInt(colors.length)]);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<BlahajParticleEffect> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(BlahajParticleEffect effect, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new BlahajParticle(world, x, y, z, velocityX, velocityY, velocityZ, effect.colors(), this.spriteProvider);
        }
    }
}
