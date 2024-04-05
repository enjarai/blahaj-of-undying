package dev.enjarai.blahajtotem.particle;

import dev.enjarai.blahajtotem.BlahajTotem;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {
    public static final ParticleType<BlahajParticleEffect> BLAHAJ_OF_UNDYING = FabricParticleTypes.complex(BlahajParticleEffect.PARAMETERS_FACTORY);

    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, BlahajTotem.id("blahaj_of_undying"), BLAHAJ_OF_UNDYING);
        ParticleFactoryRegistry.getInstance().register(BLAHAJ_OF_UNDYING, BlahajParticle.Factory::new);
    }
}
