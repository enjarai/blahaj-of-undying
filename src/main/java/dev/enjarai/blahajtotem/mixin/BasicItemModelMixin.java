package dev.enjarai.blahajtotem.mixin;

import dev.enjarai.blahajtotem.BlahajTotem;
import dev.enjarai.blahajtotem.pond.BakedHuggableModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CuboidItemModelWrapper.class)
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
    private void updateHuggability(ItemStackRenderState state, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, ClientLevel world, ItemOwner heldItemContext, int seed, CallbackInfo ci) {
        state.setData(BlahajTotem.HUGGABLE_KEY, huggable);
    }
}
