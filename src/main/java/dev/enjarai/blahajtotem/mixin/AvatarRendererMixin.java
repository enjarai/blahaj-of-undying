package dev.enjarai.blahajtotem.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.enjarai.blahajtotem.BlahajTotem;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {
    
    @Inject(
            method = {"extractRenderState"},
            at = @At("TAIL")
    )
    private void extractHumanoidRenderState(final AvatarlikeEntity entity, final AvatarRenderState state, final float partialTicks, CallbackInfo ci) {
        if(state.leftHandItemState.getDataOrDefault(BlahajTotem.HUGGABLE_KEY, false) || state.rightHandItemState.getDataOrDefault(BlahajTotem.HUGGABLE_KEY, false)) {
            state.leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            state.rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
    }
}
