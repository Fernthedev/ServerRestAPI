package io.github.fernthedev.serverstatusrest.command;

import com.github.fernthedev.fernapi.universal.api.CommandSender;
import com.github.fernthedev.fernapi.universal.api.UniversalCommand;
import io.github.fernthedev.serverstatusrest.Core;

import java.util.HashMap;
import java.util.Map;

public class MainCommand extends UniversalCommand {
    private Map<String, ArgumentRunnable> argMap = new HashMap<>();

    /**
     * Construct a new command.
     *
     */
    public MainCommand() {
        super("restapi", "restapi.command", "srest");
        argMap.put("reload", sender -> {
            if(!sender.hasPermission("restapi.command.reload")) {
                sendMessage(sender, "&cYou do not have permission to use this command.");
                return;
            }
            sendMessage(sender, "&aReloading config.");
            long timeMS = Core.getConfigManager().sync(false);


            sendMessage(sender, "&aReloaded Config. Took: " + timeMS + "ms");

        });

        argMap.put("stop", sender -> {
            if(!sender.hasPermission("restapi.command.stop")) {
                sendMessage(sender, "&cYou do not have permission to use this command.");
                return;
            }
            sendMessage(sender,"&6Stopping server.");
            Core.shutdown();
            sendMessage(sender,"&aStopping server.");
        });

        argMap.put("start", sender -> {
            if(!sender.hasPermission("restapi.command.start")) {
                sendMessage(sender, "&cYou do not have permission to use this command.");
                return;
            }
            sendMessage(sender,"&6Starting server.");
            Core.runServer();
            sendMessage(sender,"&aStarted server.");
        });

        argMap.put("restart", sender -> {
            if(!sender.hasPermission("restapi.command.restart")) {
                sendMessage(sender, "&cYou do not have permission to use this command.");
                return;
            }

            sendMessage(sender,"&6Restarting server.");
            Core.restart();
            sendMessage(sender,"&aRestarted server.");
        });
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
            sendMessage(sender, "&aThe REST API is running on " + Core.getSocketAddress());
        } else {
            String arg0 = args[0];


            ArgumentRunnable argumentRunnable = argMap.get(arg0.toLowerCase());

            if (argumentRunnable == null) {
                sendMessage(sender, "&cUnknown argument &6{&e" + arg0 + "&6} Possible args: " + argMap.keySet());
            } else {
                argumentRunnable.run(sender);
            }
        }
    }
}
