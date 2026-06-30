package dev.enjarai.blahajtotem.mixin;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.enjarai.blahajtotem.pond.UnbakedHuggableModel;
import net.minecraft.client.resources.model.cuboid.CuboidModel;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CuboidModel.Deserializer.class)
public class JsonUnbakedModel$DeserializerMixin {
    @ModifyReturnValue(
            method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/resources/model/cuboid/CuboidModel;",
            at = @At("RETURN")
    )
    private CuboidModel deserializeAdditionalField(CuboidModel original, @Local(argsOnly = true) JsonElement element) {
        if (element.getAsJsonObject().has("huggable")) {
            ((UnbakedHuggableModel) (Object) original).blahaj_totem$setHuggable(
                    GsonHelper.getAsBoolean(element.getAsJsonObject(), "huggable"));
        }
        return original;
    }
}
