package dev.enjarai.blahajtotem;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import java.net.URI;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;

public class BlahajCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext registryAccess) {
        dispatcher.register(literal("blahaj")
                .then(literal("wiki")
                        .executes(BlahajCommand::showWiki)
                )
        );
    }

    private static int showWiki(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(
                Component.translatable("blahaj_totem.command.show_wiki",
                        Component.literal("Blåhaj").withColor(0x77dbff)
                                .append(Component.literal(" of ").withColor(0xffffff))
                                .append(Component.literal("Undying").withColor(0xf8abb9))
                ).append("\n").append(
                        Component.literal("https://blahaj-of-undying.enjarai.dev/")
                                .withStyle(style -> style
                                        .withColor(0x6666ff)
                                        .withUnderlined(true)
                                        .withClickEvent(new ClickEvent.OpenUrl(URI.create(
                                                "https://blahaj-of-undying.enjarai.dev/"
                                        )))
                                )
                )
        );
        return 1;
    }
}
