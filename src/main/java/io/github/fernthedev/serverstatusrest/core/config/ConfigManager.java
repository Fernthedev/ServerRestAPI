package io.github.fernthedev.serverstatusrest.core.config;

import com.github.fernthedev.config.common.exceptions.ConfigLoadException;
import com.github.fernthedev.config.gson.GsonConfig;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.fernapi.universal.data.chat.ChatColor;
import com.github.fernthedev.fernapi.universal.data.network.IServerInfo;
import lombok.Getter;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ConfigManager {

    private final GsonConfig<ConfigValues> gsonConfig;

    public ConfigManager(File file) {
        gsonConfig = new GsonConfig<>(new ConfigValues(), file);
        sync(true);
    }

    /**
     *
     * @param async
     * @return time taken to reload
     */
    public long sync(boolean async) {
        try {
            gsonConfig.load();
        } catch (ConfigLoadException e) {
            throw new IllegalStateException(e);
        }

        Map<String, ServerData> serverDataMap = getConfigValues().getServers();

        AtomicLong timeTaken = new AtomicLong(-1);

        Runnable runnable = () -> {
            long timeStart = System.nanoTime();

            if (Universal.getNetworkHandler() != null) {
                Universal.getMethods().getLogger().info(ChatColor.GREEN + "Updating server info in config.");
                for (String serverName : Universal.getNetworkHandler().getServers().keySet()) {
                    IServerInfo info = Universal.getNetworkHandler().getServer(serverName);

                    if (!serverDataMap.containsKey(serverName)) {
                        serverDataMap.put(serverName, new ServerData(serverName, new ServerData.AddressPortPair(info.getAddress().getHostString(), info.getAddress().getPort()), 2000));
                    } else {
                        ServerData serverData = serverDataMap.get(serverName);

                        serverData.setAddressPortPair(new ServerData.AddressPortPair(info.getAddress().getHostString(), info.getAddress().getPort()));
                        serverData.setName(info.getName());
                        serverDataMap.put(serverName, serverData);
                    }
                }
            }

            long totalMS = 0;
            int amount = getConfigValues().getServers().size();

            if (amount <= 0) {
                serverDataMap.put("test", new ServerData("test", new ServerData.AddressPortPair("localhost", 25566), 2000));
                serverDataMap.put("test2", new ServerData("test2", new ServerData.AddressPortPair("localhost", 25567), 2000));
                amount = getConfigValues().getServers().size();
            }

            for (ServerData serverData : getConfigValues().getServers().values()) {
                totalMS += serverData.getTimeoutMS();
            }
            averageTimeMS = totalMS / amount;


            try {
                gsonConfig.save();
            } catch (ConfigLoadException e) {
                e.printStackTrace();
            }
            long timeEnd = System.nanoTime();
            timeTaken.set((timeEnd - timeStart) / 1000000);
        };

        if(async) {
            Universal.getScheduler().runAsync(runnable);
        } else {
            runnable.run();
        }

        Universal.getMethods().getLogger().info("Took " + timeTaken.get() + "ms to sync config.");

        return timeTaken.get();
    }

    public ConfigValues getConfigValues() {
        return gsonConfig.getConfigData();
    }

    @Getter
    private long averageTimeMS = -1;


}
