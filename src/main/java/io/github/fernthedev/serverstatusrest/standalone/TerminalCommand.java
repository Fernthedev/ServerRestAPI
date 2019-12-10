package io.github.fernthedev.serverstatusrest.standalone;

import com.github.fernthedev.fernapi.universal.api.CommandSender;

public abstract class TerminalCommand {

    public abstract void execute(CommandSender sender, String[] args);

}
