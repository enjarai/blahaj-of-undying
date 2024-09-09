package dev.enjarai.blahajtotem;

import dev.enjarai.blahajtotem.pond.BakedHuggableModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BlahajFlags {
    public static boolean isHuggable(ItemStack itemStack, LivingEntity entity) {
        var model = MinecraftClient.getInstance().getItemRenderer().getModel(itemStack, entity.getWorld(), entity, 0);
        return model instanceof BakedHuggableModel huggableModel && huggableModel.blahaj_totem$isHuggable();
    }
}
