package io.github.fernthedev.serverstatusrest.standalone;

import com.github.fernthedev.fernapi.universal.api.CommandSender;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.handlers.FernAPIPlugin;
import com.github.fernthedev.fernapi.universal.handlers.MethodInterface;
import com.github.fernthedev.fernapi.universal.handlers.ServerType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class TerminalInterface implements MethodInterface {
    @NonNull
    private TerminalMain terminalMain;

    @Override
    public Logger getLogger() {
        return terminalMain.getLogger();
    }

    @Override
    public ServerType getServerType() {
        return null;
    }

    @Override
    public FernAPIPlugin getInstance() {
        return terminalMain;
    }

    @Override
    public IFPlayer convertPlayerObjectToFPlayer(Object player) {
        return null;
    }

    @Override
    public Object convertFPlayerToPlayer(IFPlayer ifPlayer) {
        return null;
    }

    @Override
    public CommandSender convertCommandSenderToAPISender(@NonNull Object commandSender) {
        return null;
    }

    @Override
    public IFPlayer getPlayerFromName(String name) {
        return null;
    }

    @Override
    public IFPlayer getPlayerFromUUID(UUID uuid) {
        return null;
    }

    @Override
    public void runAsync(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.start();
    }

    @Override
    public List<IFPlayer> getPlayers() {
        return new ArrayList<>();
    }

    @Override
    public File getDataFolder() {
        return new File(".");
    }

    @Override
    public String getNameFromPlayer(UUID uuid) {
        return null;
    }

    @Override
    public UUID getUUIDFromPlayer(String name) {
        return null;
    }


}
