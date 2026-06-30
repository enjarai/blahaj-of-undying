package dev.enjarai.blahajtotem.mixin;

import dev.enjarai.blahajtotem.BlahajTotem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class BipedEntityModelMixin<T extends HumanoidRenderState> {

    @Shadow
    public @Final ModelPart rightArm;

    @Shadow
    public @Final ModelPart leftArm;

    @Inject(method = { "poseRightArm", "poseLeftArm" }, at = @At("TAIL"))
    public void poseArms(T state, CallbackInfo ci) {
        var left = state.leftHandItemState.getDataOrDefault(BlahajTotem.HUGGABLE_KEY, false);
        var right = state.rightHandItemState.getDataOrDefault(BlahajTotem.HUGGABLE_KEY, false);
        if (left || right) {
            this.rightArm.xRot = -0.95F;
            this.rightArm.yRot = (float) (-Math.PI / 8);
            this.leftArm.xRot = -0.90F;
            this.leftArm.yRot = (float) (Math.PI / 8);
        }
    }
}
