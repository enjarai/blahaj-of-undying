package dev.enjarai.blahajtotem.model;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4fc;

import java.util.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import static dev.enjarai.blahajtotem.BlahajTotem.HUGGABLE_KEY;
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
    public void update(ItemStackRenderState state, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel world, @Nullable ItemOwner heldItemContext, int seed) {
        if (stack.has(DataComponents.CUSTOM_NAME)) {
            var name = new HashSet<>(Arrays.asList(stack.getHoverName().getString().toLowerCase(Locale.ROOT).split("[ \\-_]")));
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
                    type.getSecond().large().update(state, stack, resolver, displayContext, world, heldItemContext, seed);
                } else {
                    type.getSecond().normal().update(state, stack, resolver, displayContext, world, heldItemContext, seed);
                }
                return;
            }
        }

        fallback.update(state, stack, resolver, displayContext, world, heldItemContext, seed);
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
        public static final MapCodec<dev.enjarai.blahajtotem.model.BlahajItemModel.Unbaked> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                UnbakedVariant.CODEC.codec().listOf().fieldOf("variants").forGetter(dev.enjarai.blahajtotem.model.BlahajItemModel.Unbaked::variants),
                ItemModels.CODEC.fieldOf("fallback").forGetter(dev.enjarai.blahajtotem.model.BlahajItemModel.Unbaked::fallback)
        ).apply(instance, dev.enjarai.blahajtotem.model.BlahajItemModel.Unbaked::new));

        @Override
        public MapCodec<? extends ItemModel.Unbaked> type() {
            return CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc transformation) {
            return new BlahajItemModel(
                    variants().stream().map($ -> $.bake(context, transformation)).toList(),
                    fallback().bake(context, transformation)
            );
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            variants().forEach($ -> $.resolve(resolver));
            fallback().resolveDependencies(resolver);
        }
    }

    public record UnbakedVariant(List<String> keywords, ItemModel.Unbaked normal, ItemModel.Unbaked large, boolean lesser) {
        public static final MapCodec<UnbakedVariant> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.listOf().fieldOf("keywords").forGetter(UnbakedVariant::keywords),
                ItemModels.CODEC.fieldOf("normal").forGetter(UnbakedVariant::normal),
                ItemModels.CODEC.fieldOf("large").forGetter(UnbakedVariant::large),
                Codec.BOOL.optionalFieldOf("lesser", false).forGetter(UnbakedVariant::lesser)
        ).apply(instance, UnbakedVariant::new));

        public Variant bake(BakingContext context, Matrix4fc transformation) {
            return new Variant(keywords(), normal().bake(context, transformation), large().bake(context, transformation), lesser());
        }

        public void resolve(ResolvableModel.Resolver resolver) {
            normal().resolveDependencies(resolver);
            large().resolveDependencies(resolver);
        }
    }
}
