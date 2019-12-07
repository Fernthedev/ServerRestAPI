package io.github.fernthedev.serverstatusrest.command;

import com.github.fernthedev.fernapi.universal.api.CommandSender;
import com.github.fernthedev.fernapi.universal.api.UniversalCommand;
import io.github.fernthedev.serverstatusrest.Core;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends UniversalCommand {


    /**
     * Construct a new command.
     *
     */
    public MainCommand() {
        super("restapi", "restapi.command", "srest");
        addArgument(new Argument("reload", (sender, args) -> {
            if(!sender.hasPermission("restapi.command.reload")) {
                sendMessage(sender, "&cYou do not have permission to use this command.");
                return;
            }
            sendMessage(sender, "&aReloading config.");
            long timeMS = Core.getConfigManager().sync(false);


            sendMessage(sender, "&aReloaded Config. Took: " + timeMS + "ms");
        }), new Argument("stop", (sender, args) -> {
            if(!sender.hasPermission("restapi.command.stop")) {
                sendMessage(sender, "&cYou do not have permission to use this command.");
                return;
            }
            sendMessage(sender,"&6Stopping server.");
            Core.shutdown();
            sendMessage(sender,"&aStopping server.");
        }), new Argument("start", (sender, args) -> {
                    if(!sender.hasPermission("restapi.command.start")) {
                        sendMessage(sender, "&cYou do not have permission to use this command.");
                        return;
                    }
                    sendMessage(sender,"&6Starting server.");
                    Core.runServer();
                    sendMessage(sender,"&aStarted server.");
        }), new Argument("restart", (sender, args) -> {
                    if(!sender.hasPermission("restapi.command.restart")) {
                        sendMessage(sender, "&cYou do not have permission to use this command.");
                        return;
                    }

                    sendMessage(sender,"&6Restarting server.");
                    Core.restart();
                    sendMessage(sender,"&aRestarted server.");
        })
        );
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


            try {
                ArgumentRunnable argumentRunnable = handleArguments(sender, args);
                argumentRunnable.run(sender, args);
            } catch (ArgumentNotFoundException e) {
                List<String> listString = new ArrayList<>();

                for (Argument argument : arguments) {
                    listString.add(argument.getName());
                }

                sendMessage(sender, "&cUnknown argument &6{&e" + arg0 + "&6} &cPossible args: &6" + listString);
            }




        }
    }
}
