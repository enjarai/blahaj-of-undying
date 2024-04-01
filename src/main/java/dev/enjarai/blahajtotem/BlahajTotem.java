package dev.enjarai.blahajtotem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BlahajTotem implements ClientModInitializer, DataGeneratorEntrypoint {
    public static final String MOD_ID = "blahaj-totem";
    public static final String NAMESPACE = "blahaj_totem";

    public static final List<String> VARIANTS = List.of(
            "gray",
            "grey",
            "red",
            "orange",
            "yellow",
            "lime",
            "green",
            "cyan",
            "blue",
            "purple",
            "magenta",
            "pink",

            "ace",
            "agender",
            "aro",
            "aroace",
            "bi",
            "demiboy",
            "demigirl",
            "demiromantic",
            "demisexual",
            "enby",
            "gay",
            "genderfluid",
            "genderqueer",
            "greyromantic",
            "greyrose",
            "greysexual",
            "intersex",
            "lesbian",
            "pan",
            "poly",
            "pride",
            "trans"
    );

    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(Items.TOTEM_OF_UNDYING, id("shork_variant"), (stack, world, entity, seed) -> {
            if (stack.hasCustomName()) {
                var name = Arrays.asList(stack.getName().getString().toLowerCase(Locale.ROOT).split("[ \\-_]"));
                var variant = VARIANTS.stream().filter(name::contains).reduce((v1, v2) -> v2.length() > v1.length() ? v2 : v1);
                return variant.map(v -> (VARIANTS.indexOf(v) + 1f) / VARIANTS.size()).orElse(0f);
            }
            return 0f;
        });
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack().addProvider(ShorkModelGenerator::new);
    }

    public static Identifier id(String path) {
        return new Identifier(NAMESPACE, path);
    }
}
