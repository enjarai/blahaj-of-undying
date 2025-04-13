package dev.enjarai.blahajtotem;

import dev.enjarai.blahajtotem.pond.HuggableItemRenderState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;

public class BlahajFlags {
    private static final ItemRenderState reusableRenderState = new ItemRenderState();

    public static boolean isHuggable(ItemStack itemStack, LivingEntity entity) {
        reusableRenderState.clear();
        MinecraftClient.getInstance().getItemModelManager().update(reusableRenderState, itemStack, ItemDisplayContext.NONE, entity.getWorld(), entity, 0);
        return ((HuggableItemRenderState) reusableRenderState).blahaj_totem$isHuggable();
    }
}
