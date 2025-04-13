package dev.enjarai.blahajtotem.mixin;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.enjarai.blahajtotem.pond.UnbakedHuggableModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(JsonUnbakedModel.Deserializer.class)
public class JsonUnbakedModel$DeserializerMixin {
    @ModifyReturnValue(
            method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/render/model/json/JsonUnbakedModel;",
            at = @At("RETURN")
    )
    private JsonUnbakedModel deserializeAdditionalField(JsonUnbakedModel original, @Local(argsOnly = true) JsonElement element) {
        if (element.getAsJsonObject().has("huggable")) {
            ((UnbakedHuggableModel) (Object) original).blahaj_totem$setHuggable(
                    JsonHelper.getBoolean(element.getAsJsonObject(), "huggable"));
        }
        return original;
    }
}
