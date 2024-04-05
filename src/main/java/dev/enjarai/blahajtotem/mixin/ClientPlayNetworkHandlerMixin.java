package dev.enjarai.blahajtotem.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.enjarai.blahajtotem.particle.BlahajParticleEffect;
import dev.enjarai.blahajtotem.particle.ModParticles;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @ModifyArg(
            method = "onEntityStatus",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleManager;addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;I)V"
            ),
            index = 1
    )
    private ParticleEffect modifyTotemParticle(ParticleEffect original, @Local Entity entity) {
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
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = entity.getStackInHand(hand);
            if (itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
                return itemStack;
            }
        }

        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }
}
