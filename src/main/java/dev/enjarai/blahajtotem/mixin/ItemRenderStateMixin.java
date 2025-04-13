package dev.enjarai.blahajtotem.mixin;

import dev.enjarai.blahajtotem.pond.HuggableItemRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemRenderState.class)
public class ItemRenderStateMixin implements HuggableItemRenderState {
    @Unique
    private boolean huggable = false;

    @Override
    public boolean blahaj_totem$isHuggable() {
        return huggable;
    }

    @Override
    public void blahaj_totem$setHuggable(boolean huggable) {
        this.huggable = huggable;
    }
}
