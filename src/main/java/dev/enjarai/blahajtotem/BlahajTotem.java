package dev.enjarai.blahajtotem;

import com.mojang.datafixers.util.Pair;
import dev.enjarai.blahajtotem.model.BlahajItemModel;
import dev.enjarai.blahajtotem.particle.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.item.model.ItemModelTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import nl.enjarai.cicada.api.render.RenderStateKey;
import nl.enjarai.cicada.api.render.RenderStateUpdateEvent;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public class BlahajTotem implements ClientModInitializer, DataGeneratorEntrypoint {
    public static final String MOD_ID = "blahaj-totem";
    public static final String NAMESPACE = "blahaj_totem";

    public static final List<String> LARGE_KEYWORDS = List.of("large", "big", "chonker");
    public static final List<BlahajType> VARIANTS = List.of(
            new BlahajType("grey", List.of("gray", "grahaj", "gråhaj", "klappar haj"), 0x888885, 0xa9a9a7, 0xc3d0d3),
            new BlahajType("red", 0x9a3f43, 0xbf5b5f, 0xc3d0d3),
            new BlahajType("orange", 0xac7646, 0xd19864, 0xc3d0d3),
            new BlahajType("yellow", 0xb98f4b, 0xdeb36a, 0xc3d0d3),
            new BlahajType("lime", 0x87a242, 0xa8c75f, 0xc3d0d3),
            new BlahajType("green", 0x637730, 0x839c4a, 0xc3d0d3),
            new BlahajType("cyan", 0x327c76, 0x4ca19c, 0xc3d0d3),
            new BlahajType("blue", 0x39508e, 0x546bb3, 0xc3d0d3),
            new BlahajType("purple", 0x563481, 0x784ea6, 0xc3d0d3),
            new BlahajType("magenta", 0x973e9b, 0xbf5ac0, 0xc3d0d3),
            new BlahajType("pink", List.of("rosahaj"), 0xce80a9, 0xf3a7cd, 0xc3d0d3),
            new BlahajType("black", 0x222222, 0x444444, 0xc3d0d3),
            new BlahajType("white", 0xeeeeee, 0xdddddd, 0xc3d0d3),

            new BlahajType("ace", 0x382b3b, 0x5b5c75, 0x5e416e, 0xc0d0d6),
            new BlahajType("agender", 0x98d888, 0x82989d, 0x383a3a, 0xc4d3d9),
            new BlahajType("aro", 0x939e9e, 0x9fc57d, 0x5f9d64, 0xd2e0e5),
            new BlahajType("aroace", 0x6f9fba, 0xd3b665, 0xd99165, 0x55648f, 0xd2e0e5),
            new BlahajType("bi", List.of("bisexual"), 0x4c4ea5, 0x8f47cf, 0xda4a7d),
            new BlahajType("demiboy", 0x6999b4, 0x8aa3a9, 0x5a6464, 0xcbdae0),
            new BlahajType("demigirl", 0xd578b3, 0x8aa3a9, 0x5a6464, 0xcbdae0),
            new BlahajType("demiromantic", 0x5fa259, 0x272638, 0xa8bacc, 0xcbdae0),
            new BlahajType("demisexual", 0x7558d1, 0x272638, 0xa8bacc, 0xcbdae0),
            new BlahajType("enby", List.of("non binary", "non-binary"), 0xf2ce7d, 0x74569c, 0x2c1d41, 0xcbdae0),
            new BlahajType("gay", List.of("homosexual", "yaoi"), 0x6999b4, 0x3f5076, 0x8ecfa1, 0x529873, 0xcbdae0),
            new BlahajType("genderfluid", 0xb04ec4, 0xf48bc7, 0x342b36, 0x434e93, 0xcbdae0),
            new BlahajType("genderqueer", 0x689f6c, 0x926ab6, 0xcbdae0),
            new BlahajType("greyromantic", List.of("grayromantic"), 0x668364, 0x9f9f9c, 0xcbdae0),
            new BlahajType("greyrose", List.of("grayrose"), 0x696a98, 0x9f9f9c, 0xcbdae0),
            new BlahajType("greysexual", List.of("graysexual"), 0x9462b4, 0x9f9f9c, 0xcbdae0),
            new BlahajType("intersex", 0xbc8f45, 0xd5b072, 0xd5b072, 0x744e98),
            new BlahajType("lesbian", List.of("yuri"), 0xd071ae, 0x9a4366, 0xcd5b48, 0xe89e5e, 0xcbdae0),
            new BlahajType("pan", List.of("pansexual"), 0xf2ce7d, 0xd668b9, 0x6595b0),
            new BlahajType("poly", 0xe34160, 0x6d9ff8, 0x2f2545, 0xffae3b),
            new BlahajType("pride", List.of("lgbt"), 0xde585b, 0xf07f5d, 0xe4bd5c, 0x96df5e, 0x6261a1, 0x704c9e),
            new BlahajType("prider", List.of("lgbtqia"), 0xde585b, 0xf07f5d, 0xe4bd5c, 0x96df5e, 0x6261a1, 0x704c9e, 0x6f9fba, 0xe882b6, 0xc6d3d6),
            new BlahajType("trans", List.of("transgender", "transhaj"), 0x6f9fba, 0xe882b6, 0xc6d3d6),

            new BlahajType("whale", List.of("blavingad", "blåvingad"), BlahajTotem.id("item/whal"), 0x39508e, 0x546bb3, 0xc3d0d3),
            new BlahajType("shark", List.of( // Can't have enough options :D
                    "blahaj", "blåhaj", "shork", "shonk", "sharky", "sharkie", "haj", "haai", "hai",
                    "biter", "chomper", "chompy", "muncher", "megalodon", "meg", "meggy", "gawr", "finn"
            ), true, 0x56839d, 0x74a4bf, 0xc3d0d3),
            new BlahajType("bread", List.of("actual baguette"), BlahajTotem.id("item/bread"), 0xd0a245, 0xae7d17, 0x66490f),
            new BlahajType("bridget", List.of( // Options are not optional here
                    "biscuit", "basket", "bucket", "baguette", "bridge", "budget", "brisket", "bidet", "brexit",
                    "bracket", "brigade", "bingus", "blanket"
            ), BlahajTotem.id("item/basket"), 0x6287c5, 0x8fafde, 0xebc186),
            new BlahajType("astolfo_bean", List.of("bean", "astolfo bean"), BlahajTotem.id("item/astolfo_bean"), 0xffbfdc, 0xecf1f6, 0x141616, 0xf2ca7d, 0x91392f),
            new BlahajType("mahiro_school", List.of("mahiro", "oyama", "onimai"), BlahajTotem.id("item/mahiro"), 0xeddbd6, 0xffaaa7, 0x79819a),
            new BlahajType("mahiro_lazy", List.of(
                    "lazy mahiro", "lazy oyama", "lazy onimai", "eepy mahiro", "eepy oyama", "eepy onimai"
            ), BlahajTotem.id("item/mahiro"), 0xeddbd6, 0xffaaa7, 0xf38b9a, 0xebf6fa)
    );

    public static final RenderStateKey<Boolean> HUGGABLE_KEY = RenderStateKey.of(id("huggable"), false);

    @Override
    public void onInitializeClient() {
        ItemModelTypes.ID_MAPPER.put(BlahajTotem.id("blahaj"), BlahajItemModel.Unbaked.CODEC);

        ResourceManagerHelper.registerBuiltinResourcePack(
                BlahajTotem.id("default_to_totem"), FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
                Text.translatable("blahaj_totem.resourcepack.default_to_totem"), ResourcePackActivationType.NORMAL
        );

        ModParticles.register();

        ClientCommandRegistrationCallback.EVENT.register(BlahajCommand::register);

        RenderStateUpdateEvent.get(PlayerEntity.class).register((player, entityRenderState, tickDelta) -> {
            HUGGABLE_KEY.put(entityRenderState, isHugging(player));
        });
    }

    public static boolean isHugging(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            ItemStack lv = player.getStackInHand(hand);
            if (BlahajFlags.isHuggable(lv, player)) {
                return true;
            }
        }
        return false;
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
            var name = new HashSet<>(Arrays.asList(stack.getName().getString().toLowerCase(Locale.ROOT).split("[ \\-_]")));
            Pair<String, BlahajType> type = null;

            for (var variant : SEARCHABLE_VARIANTS) {
                String vName = variant.getFirst();
                List<String> vSplit = Arrays.asList(vName.split("[ \\-_]"));

                if (name.containsAll(vSplit) && (type == null || (vName.length() > type.getFirst().length()
                        && !variant.getSecond().lesser()) || type.getSecond().lesser())) {
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
