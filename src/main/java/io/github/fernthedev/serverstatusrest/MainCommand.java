package io.github.fernthedev.serverstatusrest;

import com.github.fernthedev.fernapi.universal.api.CommandSender;
import com.github.fernthedev.fernapi.universal.api.UniversalCommand;

public class MainCommand extends UniversalCommand {
    /**
     * Construct a new command.
     *
     */
    public MainCommand() {
        super("restapi", "restapi.command", "srest");
    }

    /**
     * Called when executing the command
     *
     * @param sender The source
     * @param args   The arguments provided
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sendMessage(sender, "&aThe REST API is running on " + ServerStatusRest.getSocketAddress());
        } else {
            String arg0 = args[0];
            switch (arg0.toLowerCase()) {
                case "reload":
                    if(!sender.hasPermission("restapi.command.reload")) {
                        sendMessage(sender, "&cYou do not have permission to use this command.");
                        return;
                    }
                    sendMessage(sender, "&aReloading config.");
                    long timeMS = ServerStatusRest.getConfigManager().sync(false);


                    sendMessage(sender, "&aReloaded Config. Took: " + timeMS);

                    break;

                case "stop":
                    if(!sender.hasPermission("restapi.command.stop")) {
                        sendMessage(sender, "&cYou do not have permission to use this command.");
                        return;
                    }
                    sendMessage(sender,"&6Stopping server.");
                    ServerStatusRest.getInstance().stopServer();
                    sendMessage(sender,"&aStopping server.");
                    break;

                case "start":
                    if(!sender.hasPermission("restapi.command.start")) {
                        sendMessage(sender, "&cYou do not have permission to use this command.");
                        return;
                    }
                    sendMessage(sender,"&6Starting server.");
                    ServerStatusRest.getInstance().runServer();
                    sendMessage(sender,"&aStarted server.");
                    break;

                case "restart":
                    if(!sender.hasPermission("restapi.command.restart")) {
                        sendMessage(sender, "&cYou do not have permission to use this command.");
                        return;
                    }

                    sendMessage(sender,"&6Restarting server.");
                    ServerStatusRest.getInstance().restart();
                    sendMessage(sender,"&aRestarted server.");

                    break;
                default:
                    sendMessage(sender, "&cUnknown argument &6{&e" + arg0 + "&6}");
            }
        }
    }
}
