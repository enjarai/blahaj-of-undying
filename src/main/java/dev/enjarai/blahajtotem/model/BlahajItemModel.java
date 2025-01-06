package dev.enjarai.blahajtotem.model;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelTypes;
import net.minecraft.client.render.model.ResolvableModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static dev.enjarai.blahajtotem.BlahajTotem.LARGE_KEYWORDS;

public final class BlahajItemModel implements ItemModel {
    private final List<Variant> variants;
    private final ItemModel fallback;

    private final LinkedList<Pair<String, Variant>> index;

    public BlahajItemModel(List<Variant> variants, ItemModel fallback) {
        this.variants = variants;
        this.fallback = fallback;
        this.index = new LinkedList<>(variants.stream()
                .flatMap(type -> type.keywords().stream().map(alt -> Pair.of(alt, type)))
                .toList());
    }


    @Override
    public void update(ItemRenderState state, ItemStack stack, ItemModelManager resolver, ModelTransformationMode transformationMode, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed) {
        if (stack.contains(DataComponentTypes.CUSTOM_NAME)) {
            var name = new HashSet<>(Arrays.asList(stack.getName().getString().toLowerCase(Locale.ROOT).split("[ \\-_]")));
            Pair<String, Variant> type = null;

            for (var variant : index) {
                String vName = variant.getFirst();
                List<String> vSplit = Arrays.asList(vName.split("[ \\-_]"));

                if (name.containsAll(vSplit) && (type == null || (vName.length() > type.getFirst().length()
                        && !variant.getSecond().lesser()) || type.getSecond().lesser())) {
                    type = variant;
                }
            }

            if (type != null) {
                if (isLarge(name)) {
                    type.getSecond().large().update(state, stack, resolver, transformationMode, world, user, seed);
                } else {
                    type.getSecond().normal().update(state, stack, resolver, transformationMode, world, user, seed);
                }
                return;
            }
        }

        fallback.update(state, stack, resolver, transformationMode, world, user, seed);
    }

    private boolean isLarge(Set<String> name) {
        for (var keyword : LARGE_KEYWORDS) {
            if (name.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    public record Variant(List<String> keywords, ItemModel normal, ItemModel large, boolean lesser) {
    }

    public record Unbaked(List<UnbakedVariant> variants, ItemModel.Unbaked fallback) implements ItemModel.Unbaked {
        public static final MapCodec<Unbaked> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                UnbakedVariant.CODEC.codec().listOf().fieldOf("variants").forGetter(Unbaked::variants),
                ItemModelTypes.CODEC.fieldOf("fallback").forGetter(Unbaked::fallback)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends ItemModel.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public ItemModel bake(BakeContext context) {
            return new BlahajItemModel(
                    variants().stream().map($ -> $.bake(context)).toList(),
                    fallback().bake(context)
            );
        }

        @Override
        public void resolve(Resolver resolver) {
            variants().forEach($ -> $.resolve(resolver));
            fallback().resolve(resolver);
        }
    }

    public record UnbakedVariant(List<String> keywords, ItemModel.Unbaked normal, ItemModel.Unbaked large, boolean lesser) {
        public static final MapCodec<UnbakedVariant> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.listOf().fieldOf("keywords").forGetter(UnbakedVariant::keywords),
                ItemModelTypes.CODEC.fieldOf("normal").forGetter(UnbakedVariant::normal),
                ItemModelTypes.CODEC.fieldOf("large").forGetter(UnbakedVariant::large),
                Codec.BOOL.optionalFieldOf("lesser", false).forGetter(UnbakedVariant::lesser)
        ).apply(instance, UnbakedVariant::new));

        public Variant bake(BakeContext context) {
            return new Variant(keywords(), normal().bake(context), large().bake(context), lesser());
        }

        public void resolve(ResolvableModel.Resolver resolver) {
            normal().resolve(resolver);
            large().resolve(resolver);
        }
    }
}
