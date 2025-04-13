package dev.enjarai.blahajtotem.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.enjarai.blahajtotem.pond.BakedHuggableModel;
import dev.enjarai.blahajtotem.pond.UnbakedHuggableModel;
import net.minecraft.client.render.item.model.BasicItemModel;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.model.BakedSimpleModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BasicItemModel.Unbaked.class)
public class BasicItemModel$UnbakedMixin {
    @ModifyReturnValue(
            method = "bake",
            at = @At("RETURN")
    )
    private ItemModel addFieldToBakedModel(ItemModel original, @Local BakedSimpleModel model) {
        if (original instanceof BakedHuggableModel huggableModel) {
            while (true) {
                if (model.getModel() instanceof UnbakedHuggableModel unbakedHuggableModel && unbakedHuggableModel.blahaj_totem$isHuggable() != null) {
                    huggableModel.blahaj_totem$setHuggable(unbakedHuggableModel.blahaj_totem$isHuggable());
                    break;
                } else if (model.getParent() != null && model.getParent() != model) {
                    model = model.getParent();
                } else {
                    break;
                }
            }
        }
        return original;
    }
}
