package dev.enjarai.blahajtotem.mixin;

import dev.enjarai.blahajtotem.pond.BakedHuggableModel;
import dev.enjarai.blahajtotem.pond.HuggableItemRenderState;
import net.fabricmc.fabric.api.client.model.loading.v1.UnwrappableBakedModel;
import net.minecraft.client.render.item.ItemRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemRenderState.class)
public class ItemRenderStateMixin implements HuggableItemRenderState {
    @Shadow private ItemRenderState.LayerRenderState[] layers;

    @Override
    public boolean blahaj_totem$isHuggable() {
        for (var layer : layers) {
            var model = ((LayerRenderStateAccessor) layer).getModel();
            if (UnwrappableBakedModel.unwrap(model) instanceof BakedHuggableModel huggable && huggable.blahaj_totem$isHuggable()) {
                return true;
            }
        }
        return false;
    }
}
