package io.github.fernthedev.serverstatusrest.command;

import com.github.fernthedev.fernapi.universal.api.CommandSender;

@FunctionalInterface
public interface ArgumentRunnable {

    void run(CommandSender sender);
}
