package io.github.fernthedev.serverstatusrest.minecraft.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.github.fernthedev.fernapi.universal.api.FernCommandIssuer;
import io.github.fernthedev.serverstatusrest.core.Core;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@CommandAlias("restapi|srest")
@CommandPermission("restapi.command")
public class MainCommand extends BaseCommand {


    @Subcommand("restart")
    @CommandPermission("restapi.command.restart")
    public void restart(FernCommandIssuer sender) {
        sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("&6Restarting server."));
        Core.restart();
        sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("&aRestarted server."));
    }

    @Subcommand("start")
    @CommandPermission("restapi.command.start")
    public void start(FernCommandIssuer sender) {
        sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("&6Starting server."));
        Core.runServer();
        sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("&aStarted server."));
    }

    @Subcommand("stop")
    @CommandPermission("restapi.command.stop")
    public void stop(FernCommandIssuer sender) {

        sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("&6Stopping server."));
        Core.shutdown();
        sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("&aStopping server."));
    }

    @Subcommand("reload")
    @CommandPermission("restapi.command.reload")
    public void reload(FernCommandIssuer sender) {
        if(!sender.hasPermission("restapi.command.reload")) {
            sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&cYou do not have permission to use this command."));
            return;
        }
        sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&aReloading config."));
        long timeMS = Core.getConfigManager().sync(false);


        sender.sendMessage(LegacyComponentSerializer.legacySection().deserialize("&aReloaded Config. Took: " + timeMS + "ms"));
    }

    @Default
    public void defaultCommand(FernCommandIssuer sender) {
        sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&aThe REST API is running on " + Core.getSocketAddress()));
    }

}
