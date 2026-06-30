package dev.enjarai.blahajtotem;

import com.google.gson.JsonObject;
import dev.enjarai.blahajtotem.model.BlahajItemModel;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.world.item.Items;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ShorkModelGenerator extends FabricModelProvider {
    public ShorkModelGenerator(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        var largeSuffixes = List.of("", "_large");

        var variants = new ArrayList<BlahajItemModel.UnbakedVariant>();

        for (int i = 0; i < BlahajTotem.VARIANTS.size(); i++) {
            var variant = BlahajTotem.VARIANTS.get(i);

            variants.add(new BlahajItemModel.UnbakedVariant(
                    Stream.concat(Stream.of(variant.name()), variant.alternatives().stream()).toList(),
                    ItemModelUtils.plainModel(BlahajTotem.id("item/blahaj_skins/" + variant.name() + "_shark")),
                    ItemModelUtils.plainModel(BlahajTotem.id("item/blahaj_skins/" + variant.name() + "_shark_large")),
                    variant.lesser()
            ));
        }

        itemModelGenerator.itemModelOutput.accept(Items.TOTEM_OF_UNDYING, new BlahajItemModel.Unbaked(
                variants,
                ItemModelUtils.plainModel(BlahajTotem.id("item/totem_parent"))
        ));

        largeSuffixes.forEach(suffix -> {
            BlahajTotem.VARIANTS.forEach(variant -> {
                itemModelGenerator.modelOutput.accept(BlahajTotem.id("item/blahaj_skins/" + variant.name() + "_shark" + suffix), () -> {
                    var model = new JsonObject();
                    model.addProperty("parent", variant.model().toString() + suffix);

                    var textures = new JsonObject();
                    textures.addProperty("0", BlahajTotem.id("item/blahaj_skins/" + variant.name() + "_shark").toString());
                    model.add("textures", textures);

                    return model;
                });
            });
        });
    }
}
