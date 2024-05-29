package dev.enjarai.blahajtotem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.Items;

public class ShorkModelGenerator extends FabricModelProvider {
    public ShorkModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.writer.accept(ModelIds.getItemModelId(Items.TOTEM_OF_UNDYING), () -> {
            var model = new JsonObject();
            model.addProperty("parent", BlahajTotem.id("item/shork").toString());

            var overrides = new JsonArray();
            for (int i = 0; i < BlahajTotem.VARIANTS.size(); i++) {
                var variant = BlahajTotem.VARIANTS.get(i).name();

                var override = new JsonObject();

                var predicate = new JsonObject();
                predicate.addProperty(BlahajTotem.id("shork_variant").toString(), (i + 1f) / BlahajTotem.VARIANTS.size());
                override.add("predicate", predicate);

                override.addProperty("model", BlahajTotem.id("item/blahaj_skins/" + variant + "_shark").toString());

                overrides.add(override);
            }
            model.add("overrides", overrides);

            return model;
        });

        BlahajTotem.VARIANTS.forEach(variant -> {
            itemModelGenerator.writer.accept(BlahajTotem.id("item/blahaj_skins/" + variant.name() + "_shark"), () -> {
                var model = new JsonObject();
                model.addProperty("parent", variant.model().toString());

                var textures = new JsonObject();
                textures.addProperty("0", BlahajTotem.id("item/blahaj_skins/" + variant.name() + "_shark").toString());
                model.add("textures", textures);

                return model;
            });
        });
    }
}
