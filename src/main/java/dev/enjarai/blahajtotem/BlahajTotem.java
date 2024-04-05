package dev.enjarai.blahajtotem;

import dev.enjarai.blahajtotem.particle.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BlahajTotem implements ClientModInitializer, DataGeneratorEntrypoint {
    public static final String MOD_ID = "blahaj-totem";
    public static final String NAMESPACE = "blahaj_totem";

    public static final List<BlahajType> VARIANTS = List.of(
            new BlahajType("gray", 0x888885, 0xa9a9a7, 0xc3d0d3),
            new BlahajType("grey", 0x888885, 0xa9a9a7, 0xc3d0d3),
            new BlahajType("red", 0x9a3f43, 0xbf5b5f, 0xc3d0d3),
            new BlahajType("orange", 0xac7646, 0xd19864, 0xc3d0d3),
            new BlahajType("yellow", 0xb98f4b, 0xdeb36a, 0xc3d0d3),
            new BlahajType("lime", 0x87a242, 0xa8c75f, 0xc3d0d3),
            new BlahajType("green", 0x637730, 0x839c4a, 0xc3d0d3),
            new BlahajType("cyan", 0x327c76, 0x4ca19c, 0xc3d0d3),
            new BlahajType("blue", 0x39508e, 0x546bb3, 0xc3d0d3),
            new BlahajType("purple", 0x563481, 0x784ea6, 0xc3d0d3),
            new BlahajType("magenta", 0x973e9b, 0xbf5ac0, 0xc3d0d3),
            new BlahajType("pink", 0xce80a9, 0xf3a7cd, 0xc3d0d3),

            new BlahajType("ace", 0x382b3b, 0x5b5c75, 0x5e416e, 0xc0d0d6),
            new BlahajType("agender", 0x98d888, 0x82989d, 0x383a3a, 0xc4d3d9),
            new BlahajType("aro"),
            new BlahajType("aroace"),
            new BlahajType("bi"),
            new BlahajType("demiboy"),
            new BlahajType("demigirl"),
            new BlahajType("demiromantic"),
            new BlahajType("demisexual"),
            new BlahajType("enby"),
            new BlahajType("gay"),
            new BlahajType("genderfluid"),
            new BlahajType("genderqueer"),
            new BlahajType("greyromantic"),
            new BlahajType("greyrose"),
            new BlahajType("greysexual"),
            new BlahajType("intersex"),
            new BlahajType("lesbian"),
            new BlahajType("pan"),
            new BlahajType("poly"),
            new BlahajType("pride"),
            new BlahajType("trans", 0x6f9fba, 0xe882b6, 0xc6d3d6)
    );

    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(Items.TOTEM_OF_UNDYING, id("shork_variant"), (stack, world, entity, seed) -> {
            var type = getShorkType(stack);
            if (type != null) {
                return (VARIANTS.indexOf(type) + 1f) / VARIANTS.size();
            }
            return 0f;
        });

        ModParticles.register();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack().addProvider(ShorkModelGenerator::new);
    }

    @Nullable
    public static BlahajType getShorkType(ItemStack stack) {
        if (stack.isOf(Items.TOTEM_OF_UNDYING) && stack.hasCustomName()) {
            var name = Arrays.asList(stack.getName().getString().toLowerCase(Locale.ROOT).split("[ \\-_]"));
            var variant = VARIANTS.stream().filter(v -> name.contains(v.name())).reduce((v1, v2) -> v2.name().length() > v1.name().length() ? v2 : v1);

            return variant.orElse(null);
        }

        return null;
    }

    public static Identifier id(String path) {
        return new Identifier(NAMESPACE, path);
    }
}
