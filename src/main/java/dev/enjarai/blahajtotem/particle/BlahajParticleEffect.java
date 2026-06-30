package dev.enjarai.blahajtotem.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.enjarai.blahajtotem.BlahajTotem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record BlahajParticleEffect(ParticleType<BlahajParticleEffect> type, int[] colors) implements ParticleOptions {
    public static MapCodec<BlahajParticleEffect> createCodec(ParticleType<BlahajParticleEffect> type) {
        return Codec.INT_STREAM.xmap(
                intStream -> new BlahajParticleEffect(type, intStream.toArray()),
                effect -> IntStream.of(effect.colors)
        ).fieldOf("colors");
    }

    public static StreamCodec<RegistryFriendlyByteBuf, BlahajParticleEffect> createPacketCodec(ParticleType<BlahajParticleEffect> type) {
        return StreamCodec.composite(
                ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.INT, 32), effect -> Arrays.stream(effect.colors).boxed().toList(),
                list -> new BlahajParticleEffect(type, list.stream().mapToInt(i -> i).toArray())
        );
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    public static int[] getColorsForShork(ItemStack shorkStack) {
        var type = BlahajTotem.getShorkType(shorkStack);
        if (type != null) {
            return type.colors();
        }
        return new int[0];
    }
}
