package dev.enjarai.blahajtotem.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.enjarai.blahajtotem.pond.BakedHuggableModel;
import dev.enjarai.blahajtotem.pond.UnbakedHuggableModel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.ResolvedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CuboidItemModelWrapper.Unbaked.class)
public class BasicItemModel$UnbakedMixin {
    @ModifyReturnValue(
            method = "bake",
            at = @At("RETURN")
    )
    private ItemModel addFieldToBakedModel(ItemModel original, @Local ResolvedModel model) {
        if (original instanceof BakedHuggableModel huggableModel) {
            while (true) {
                if (model.wrapped() instanceof UnbakedHuggableModel unbakedHuggableModel && unbakedHuggableModel.blahaj_totem$isHuggable() != null) {
                    huggableModel.blahaj_totem$setHuggable(unbakedHuggableModel.blahaj_totem$isHuggable());
                    break;
                } else if (model.parent() != null && model.parent() != model) {
                    model = model.parent();
                } else {
                    break;
                }
            }
        }
        return original;
    }
}
