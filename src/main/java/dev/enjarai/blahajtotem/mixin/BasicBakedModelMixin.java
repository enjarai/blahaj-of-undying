package dev.enjarai.blahajtotem.mixin;

import dev.enjarai.blahajtotem.pond.BakedHuggableModel;
import net.minecraft.client.render.model.BasicBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BasicBakedModel.class)
public class BasicBakedModelMixin implements BakedHuggableModel {
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
}
