package dev.enjarai.blahajtotem;

import net.minecraft.util.Identifier;

import java.util.List;

public record BlahajType(String name, List<String> alternatives, Identifier model, int... colors) {
    public BlahajType(String name, int... colors) {
        this(name, List.of(), BlahajTotem.id("item/shork"), colors);
    }

    public BlahajType(String name, List<String> alternatives, int... colors) {
        this(name, alternatives, BlahajTotem.id("item/shork"), colors);
    }
}
