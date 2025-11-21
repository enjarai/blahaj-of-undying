package dev.enjarai.blahajtotem.mixin;

import dev.enjarai.blahajtotem.pond.BakedHuggableModel;
import dev.enjarai.blahajtotem.pond.HuggableItemRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.BasicItemModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BasicItemModel.class)
public class BasicItemModelMixin implements BakedHuggableModel {
    @Unique
    private boolean huggable;

    @Override
    public void blahaj_totem$setHuggable(boolean huggable) {
        this.huggable = huggable;
    }

    @Override
    public boolean blahaj_totem$isHuggable() {
        return huggable;
    }

    @Inject(
            method = "update",
            at = @At("TAIL")
    )
    private void updateHuggability(ItemRenderState state, ItemStack stack, ItemModelManager resolver, ItemDisplayContext displayContext, ClientWorld world, HeldItemContext heldItemContext, int seed, CallbackInfo ci) {
        ((HuggableItemRenderState) state).blahaj_totem$setHuggable(huggable);
    }
}
