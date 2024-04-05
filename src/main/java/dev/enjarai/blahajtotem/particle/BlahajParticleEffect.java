package dev.enjarai.blahajtotem.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.enjarai.blahajtotem.BlahajTotem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;

public record BlahajParticleEffect(ParticleType<BlahajParticleEffect> type, int[] colors) implements ParticleEffect {
    @SuppressWarnings("deprecation")
    public static final ParticleEffect.Factory<BlahajParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<>() {
        @Override
        public BlahajParticleEffect read(ParticleType<BlahajParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            return new BlahajParticleEffect(type, new int[0]); // This doesn't actually need to work with commands, so dummy impl it is
        }

        @Override
        public BlahajParticleEffect read(ParticleType<BlahajParticleEffect> type, PacketByteBuf buf) {
            return new BlahajParticleEffect(type, buf.readIntArray());
        }
    };

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeIntArray(colors);
    }

    @Override
    public String asString() {
        return Registries.PARTICLE_TYPE.getId(getType()) + "";
    }

    public static int[] getColorsForShork(ItemStack shorkStack) {
        var type = BlahajTotem.getShorkType(shorkStack);
        if (type != null) {
            return type.colors();
        }
        return new int[0];
    }
}
