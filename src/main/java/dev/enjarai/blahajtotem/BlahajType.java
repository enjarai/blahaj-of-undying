package dev.enjarai.blahajtotem;

import java.util.List;
import net.minecraft.resources.Identifier;

public record BlahajType(String name, List<String> alternatives, Identifier model, boolean lesser, int... colors) {
    public BlahajType(String name, int... colors) {
        this(name, List.of(), BlahajTotem.id("item/shork"), false, colors);
    }

    public BlahajType(String name, List<String> alternatives, int... colors) {
        this(name, alternatives, BlahajTotem.id("item/shork"), false, colors);
    }

    public BlahajType(String name, List<String> alternatives, Identifier model, int... colors) {
        this(name, alternatives, model, false, colors);
    }

    public BlahajType(String name, boolean lesser, int... colors) {
        this(name, List.of(), BlahajTotem.id("item/shork"), lesser, colors);
    }

    public BlahajType(String name, List<String> alternatives, boolean lesser, int... colors) {
        this(name, alternatives, BlahajTotem.id("item/shork"), lesser, colors);
    }
}
