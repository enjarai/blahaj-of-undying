package dev.enjarai.blahajtotem;

import net.minecraft.util.Identifier;

public record BlahajType(String name, Identifier model, int... colors) {
    public BlahajType(String name, int... colors) {
        this(name, BlahajTotem.id("item/shork"), colors);
    }
}
