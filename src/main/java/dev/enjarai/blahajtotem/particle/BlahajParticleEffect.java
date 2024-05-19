package dev.enjarai.blahajtotem.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.enjarai.blahajtotem.BlahajTotem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public record BlahajParticleEffect(ParticleType<BlahajParticleEffect> type, int[] colors) implements ParticleEffect {
    public static MapCodec<BlahajParticleEffect> createCodec(ParticleType<BlahajParticleEffect> type) {
        return Codec.INT_STREAM.xmap(
                intStream -> new BlahajParticleEffect(type, intStream.toArray()),
                effect -> IntStream.of(effect.colors)
        ).fieldOf("colors");
    }

    public static PacketCodec<RegistryByteBuf, BlahajParticleEffect> createPacketCodec(ParticleType<BlahajParticleEffect> type) {
        return PacketCodec.tuple(
                PacketCodecs.collection(ArrayList::new, PacketCodecs.INTEGER, 32), effect -> Arrays.stream(effect.colors).boxed().toList(),
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
