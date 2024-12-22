package dev.enjarai.blahajtotem.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.enjarai.blahajtotem.pond.BakedHuggableModel;
import dev.enjarai.blahajtotem.pond.UnbakedHuggableModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(JsonUnbakedModel.class)
public class JsonUnbakedModelMixin implements UnbakedHuggableModel {
    @Shadow @Nullable protected JsonUnbakedModel parent;
    @Unique @Nullable
    private Boolean huggable;

    @Override
    public void blahaj_totem$setHuggable(boolean huggable) {
        this.huggable = huggable;
    }

    @Override
    public boolean blahaj_totem$isHuggable() {
        if (huggable != null) {
            return huggable;
        } else if (parent != null) {
            return ((UnbakedHuggableModel) parent).blahaj_totem$isHuggable();
        } else {
            return false;
        }
    }

    @ModifyReturnValue(
            method = "bake(Ljava/util/function/Function;Lnet/minecraft/client/render/model/ModelBakeSettings;Z)Lnet/minecraft/client/render/model/BakedModel;",
            at = @At("RETURN")
    )
    private BakedModel addFieldToBakedModel(BakedModel original) {
        if (original instanceof BakedHuggableModel huggableModel) {
            huggableModel.blahaj_totem$setHuggable(blahaj_totem$isHuggable());
        }
        return original;
    }
}
