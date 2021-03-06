package io.github.fernthedev.serverstatusrest.standalone;

import co.aikar.commands.TerminalCommandHandler;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.fernapi.universal.handlers.FernAPIPlugin;
import io.github.fernthedev.serverstatusrest.core.Core;
import lombok.Getter;

import java.util.logging.Logger;

public class TerminalMain implements FernAPIPlugin {

    @Getter
    private final Logger logger = Logger.getLogger(getClass().getName());

    private final TerminalScheduler scheduler = new TerminalScheduler();

    public static void main(String[] args) {
        if(System.console() == null) {
            System.err.println("No console detected.");
            return;
        }
        new TerminalMain();
    }

    private TerminalMain() {
        Universal.getInstance().setup(new TerminalInterface(this),
                this,
                null,
                new TerminalMessageHandler(),
                new TerminalCommandHandler(),
                null,
                scheduler,
                null);

        Core.init();
    }

    public void stop() {
        Universal.getInstance().onDisable();
        scheduler.cancelAllTasks();
    }

}
