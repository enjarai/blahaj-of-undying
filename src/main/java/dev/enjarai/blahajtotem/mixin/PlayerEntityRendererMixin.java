package dev.enjarai.blahajtotem.mixin;

import dev.enjarai.blahajtotem.BlahajTotem;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(
            method = "getArmPose(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void cuddleBlahaj(PlayerEntity player, ItemStack stack, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        if (BlahajTotem.isHugging(player)) {
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
            cir.cancel();
        }
    }
}
