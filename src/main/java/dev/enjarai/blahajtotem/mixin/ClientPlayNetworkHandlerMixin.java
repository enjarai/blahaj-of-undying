package dev.enjarai.blahajtotem.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.enjarai.blahajtotem.particle.BlahajParticleEffect;
import dev.enjarai.blahajtotem.particle.ModParticles;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ClientPacketListener.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @ModifyArg(
            method = "handleEntityEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleEngine;createTrackingEmitter(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/particles/ParticleOptions;I)V"
            ),
            index = 1
    )
    private ParticleOptions modifyTotemParticle(ParticleOptions original, @Local Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            var colors = BlahajParticleEffect.getColorsForShork(getActiveTotemOfUndyingForAnyEntity(livingEntity));

            if (colors.length > 0) {
                return new BlahajParticleEffect(ModParticles.BLAHAJ_OF_UNDYING, colors);
            }
        }

        return original;
    }

    @Unique
    private static ItemStack getActiveTotemOfUndyingForAnyEntity(LivingEntity entity) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = entity.getItemInHand(hand);
            if (itemStack.is(Items.TOTEM_OF_UNDYING)) {
                return itemStack;
            }
        }

        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }
}
