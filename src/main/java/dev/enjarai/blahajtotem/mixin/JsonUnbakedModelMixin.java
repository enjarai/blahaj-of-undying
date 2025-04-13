package dev.enjarai.blahajtotem.mixin;

import dev.enjarai.blahajtotem.pond.UnbakedHuggableModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(JsonUnbakedModel.class)
public class JsonUnbakedModelMixin implements UnbakedHuggableModel {
    @Unique @Nullable
    private Boolean huggable;

    @Override
    public void blahaj_totem$setHuggable(boolean huggable) {
        this.huggable = huggable;
    }

    @Override
    public Boolean blahaj_totem$isHuggable() {
        return huggable;
    }
}
