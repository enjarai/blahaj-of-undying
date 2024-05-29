package dev.enjarai.blahajtotem;

import com.mojang.datafixers.util.Pair;
import dev.enjarai.blahajtotem.particle.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

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
            new BlahajType("aro", 0x939e9e, 0x9fc57d, 0x5f9d64, 0xd2e0e5),
            new BlahajType("aroace", 0x6f9fba, 0xd3b665, 0xd99165, 0x55648f, 0xd2e0e5),
            new BlahajType("bi", 0x4c4ea5, 0x8f47cf, 0xda4a7d),
            new BlahajType("demiboy", 0x6999b4, 0x8aa3a9, 0x5a6464, 0xcbdae0),
            new BlahajType("demigirl", 0xd578b3, 0x8aa3a9, 0x5a6464, 0xcbdae0),
            new BlahajType("demiromantic", 0x5fa259, 0x272638, 0xa8bacc, 0xcbdae0),
            new BlahajType("demisexual", 0x7558d1, 0x272638, 0xa8bacc, 0xcbdae0),
            new BlahajType("enby", 0xf2ce7d, 0x74569c, 0x2c1d41, 0xcbdae0),
            new BlahajType("gay", 0x6999b4, 0x3f5076, 0x8ecfa1, 0x529873, 0xcbdae0),
            new BlahajType("genderfluid", 0xb04ec4, 0xf48bc7, 0x342b36, 0x434e93, 0xcbdae0),
            new BlahajType("genderqueer", 0x689f6c, 0x926ab6, 0xcbdae0),
            new BlahajType("greyromantic", 0x668364, 0x9f9f9c, 0xcbdae0),
            new BlahajType("greyrose", 0x696a98, 0x9f9f9c, 0xcbdae0),
            new BlahajType("greysexual", 0x9462b4, 0x9f9f9c, 0xcbdae0),
            new BlahajType("intersex", 0xbc8f45, 0xd5b072, 0xd5b072, 0x744e98),
            new BlahajType("lesbian", 0xd071ae, 0x9a4366, 0xcd5b48, 0xe89e5e, 0xcbdae0),
            new BlahajType("pan", 0xf2ce7d, 0xd668b9, 0x6595b0),
            new BlahajType("poly", 0xe34160, 0x6d9ff8, 0x2f2545, 0xffae3b),
            new BlahajType("pride", 0xde585b, 0xf07f5d, 0xe4bd5c, 0x96df5e, 0x6261a1, 0x704c9e),
            new BlahajType("prider", 0xde585b, 0xf07f5d, 0xe4bd5c, 0x96df5e, 0x6261a1, 0x704c9e, 0x6f9fba, 0xe882b6, 0xc6d3d6),
            new BlahajType("trans", 0x6f9fba, 0xe882b6, 0xc6d3d6),

            new BlahajType("whale", List.of("blavingad", "blåvingad"), BlahajTotem.id("item/whal"), 0x39508e, 0x546bb3, 0xc3d0d3),
            new BlahajType("shark", List.of( // Can't have enough options :D
                    "blahaj", "blåhaj", "shork", "shonk", "sharky", "sharkie", "haj", "haai", "hai",
                    "biter", "chomper", "chompy", "muncher", "megalodon", "meg", "meggy", "gawr", "finn"
            ), 0x56839d, 0x74a4bf, 0xc3d0d3)
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

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new BlahajFlags());
        ResourceManagerHelper.registerBuiltinResourcePack(
                BlahajTotem.id("default_to_totem"), FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
                Text.translatable("blahaj_totem.resourcepack.default_to_totem"), ResourcePackActivationType.NORMAL
        );

        ModParticles.register();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack().addProvider(ShorkModelGenerator::new);
    }

    public static final LinkedList<Pair<String, BlahajType>> SEARCHABLE_VARIANTS = new LinkedList<>(VARIANTS.stream()
            .flatMap(type -> Stream.concat(
                    Stream.of(Pair.of(type.name(), type)),
                    type.alternatives().stream().map(alt -> Pair.of(alt, type))
            ))
            .toList());

    @Nullable
    public static BlahajType getShorkType(ItemStack stack) {
        if (stack.isOf(Items.TOTEM_OF_UNDYING) && stack.contains(DataComponentTypes.CUSTOM_NAME)) {
            var name = Arrays.asList(stack.getName().getString().toLowerCase(Locale.ROOT).split("[ \\-_]"));
            Pair<String, BlahajType> type = null;

            for (var variant : SEARCHABLE_VARIANTS) {
                var vName = variant.getFirst();
                if (name.contains(vName) && (type == null || vName.length() > type.getFirst().length())) {
                    type = variant;
                }
            }

            if (type != null) {
                return type.getSecond();
            }
        }

        return null;
    }

    public static Identifier id(String path) {
        return Identifier.of(NAMESPACE, path);
    }
}
