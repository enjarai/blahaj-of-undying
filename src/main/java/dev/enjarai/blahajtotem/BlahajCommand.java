package dev.enjarai.blahajtotem;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;

import java.net.URI;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class BlahajCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(literal("blahaj")
                .then(literal("wiki")
                        .executes(BlahajCommand::showWiki)
                )
        );
    }

    private static int showWiki(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(
                Text.translatable("blahaj_totem.command.show_wiki",
                        Text.literal("Blåhaj").withColor(0x77dbff)
                                .append(Text.literal(" of ").withColor(0xffffff))
                                .append(Text.literal("Undying").withColor(0xf8abb9))
                ).append("\n").append(
                        Text.literal("https://blahaj-of-undying.enjarai.dev/")
                                .styled(style -> style
                                        .withColor(0x6666ff)
                                        .withUnderline(true)
                                        .withClickEvent(new ClickEvent.OpenUrl(URI.create(
                                                "https://blahaj-of-undying.enjarai.dev/"
                                        )))
                                )
                )
        );
        return 1;
    }
}
