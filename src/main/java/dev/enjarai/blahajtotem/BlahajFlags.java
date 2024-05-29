package dev.enjarai.blahajtotem;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class BlahajFlags implements IdentifiableResourceReloadListener, SimpleSynchronousResourceReloadListener {
    public static boolean defaultToTotem = false;

    public static boolean isBlahaj(ItemStack itemStack) {
        if (itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
            return !BlahajFlags.defaultToTotem || BlahajTotem.getShorkType(itemStack) != null;
        }
        return false;
    }

    @Override
    public Identifier getFabricId() {
        return BlahajTotem.id("flags");
    }

    @Override
    public void reload(ResourceManager manager) {
        defaultToTotem = manager.getResource(BlahajTotem.id("default_to_totem")).isPresent();
    }
}
