package io.github.fernthedev.serverstatusrest.standalone;

import com.github.fernthedev.fernapi.universal.api.FernCommandIssuer;
import com.github.fernthedev.fernapi.universal.api.IFConsole;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.api.OfflineFPlayer;
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
    public boolean isMainThread() {
        return false;
    }

    @Override
    public Logger getLogger() {
        return terminalMain.getLogger();
    }

    @Override
    public ServerType getServerType() {
        return ServerType.OTHER;
    }

    @Override
    public FernAPIPlugin getInstance() {
        return terminalMain;
    }

    /**
     * Converts the command sender to it's IFPlayer instance
     *
     * @param commandSender
     * @return
     */
    @Override
    public FernCommandIssuer convertCommandSenderToAPISender(@NonNull Object commandSender) {
        return null;
    }

    @Override
    public IFPlayer convertPlayerObjectToFPlayer(Object player) {
        return null;
    }

    @Override
    public Object convertFPlayerToPlayer(IFPlayer ifPlayer) {
        return null;
    }


    /**
     * Converts the command sender to it's IFPlayer instance
     *
     * @param commandSender
     * @return
     */
    @Override
    public IFConsole convertConsoleToAPISender(Object commandSender) {
        return null;
    }

    /**
     * Returns player from server
     *
     * @param name Name of player
     * @return The IFPlayer instance. It never returns null, however you can check if the player is null with {@link IFPlayer#isPlayerNull()}
     */
    @Override
    public @NonNull OfflineFPlayer getPlayerFromName(String name) {
        return null;
    }

    /**
     * Returns player from server
     *
     * @param uuid Name of player
     * @return The IFPlayer instance. It never returns null, however you can check if the player is null with {@link IFPlayer#isPlayerNull()}
     */
    @Override
    public @NonNull OfflineFPlayer getPlayerFromUUID(UUID uuid) {
        return null;
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

    /**
     * Returns the uuid of the player from name
     *
     * @param name
     */
    @Override
    public UUID getUUIDFromPlayerName(String name) {
        return null;
    }

    @Override
    public List<IFPlayer> matchPlayerName(String name) {
        return null;
    }


}
