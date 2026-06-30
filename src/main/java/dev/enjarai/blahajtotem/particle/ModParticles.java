package dev.enjarai.blahajtotem.particle;

import dev.enjarai.blahajtotem.BlahajTotem;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticles {
    public static final ParticleType<BlahajParticleEffect> BLAHAJ_OF_UNDYING =
            FabricParticleTypes.complex(BlahajParticleEffect::createCodec, BlahajParticleEffect::createPacketCodec);

    public static void register() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, BlahajTotem.id("blahaj_of_undying"), BLAHAJ_OF_UNDYING);
        ParticleProviderRegistry.getInstance().register(BLAHAJ_OF_UNDYING, BlahajParticle.Factory::new);
    }
}
