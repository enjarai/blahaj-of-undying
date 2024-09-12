package dev.enjarai.blahajtotem;

import dev.enjarai.blahajtotem.pond.BakedHuggableModel;
import net.fabricmc.fabric.api.renderer.v1.model.WrapperBakedModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class BlahajFlags {
    public static boolean isHuggable(ItemStack itemStack, LivingEntity entity) {
        var model = MinecraftClient.getInstance().getItemRenderer().getModel(itemStack, entity.getWorld(), entity, 0);
        model = WrapperBakedModel.unwrap(model);
        return model instanceof BakedHuggableModel huggableModel && huggableModel.blahaj_totem$isHuggable();
    }
}
