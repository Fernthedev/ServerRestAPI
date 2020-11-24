package io.github.fernthedev.serverstatusrest.standalone;

import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.data.network.IPMessageHandler;
import com.github.fernthedev.fernapi.universal.data.network.PluginMessageData;
import com.github.fernthedev.fernapi.universal.handlers.PluginMessageHandler;

public class TerminalMessageHandler extends IPMessageHandler {
    @Override
    public void registerMessageHandler(PluginMessageHandler pluginMessageHandler) {

    }

    /**
     * This sends plugin dataInfo.
     *
     * @param data The dataInfo to be sent, player will be specified added automatically
     */
    @Override
    public void sendPluginData(PluginMessageData data) {

    }

    /**
     * This sends plugin dataInfo.
     *
     * @param player The player can be null, not necessary
     * @param data   The dataInfo to be sent, player will be specified added automatically
     */
    @Override
    public void sendPluginData(IFPlayer<?> player, PluginMessageData data) {

    }
}
