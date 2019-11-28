package io.github.fernthedev.serverstatusrest.config;

import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.gson.GsonConfig;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

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
        gsonConfig.load();

        Map<String, ServerData> serverDataMap = getConfigValues().getServers();

        AtomicLong timeTaken = new AtomicLong(-1);

        Runnable runnable = () -> {
            long timeStart = System.nanoTime();
            for (String serverName : ProxyServer.getInstance().getServers().keySet()) {
                ServerInfo info = ProxyServer.getInstance().getServerInfo(serverName);

                if(!serverDataMap.containsKey(serverName)) {
                    serverDataMap.put(serverName, new ServerData(serverName, new ServerData.AddressPortPair(info.getAddress().getHostString(), info.getAddress().getPort()), 2000));
                } else {
                    ServerData serverData = serverDataMap.get(serverName);

                    serverData.setAddressPortPair(new ServerData.AddressPortPair(info.getAddress().getHostString(), info.getAddress().getPort()));
                    serverData.setName(info.getName());
                    serverDataMap.put(serverName, serverData);
                }
            }
            long totalMS = 0;
            int amount = getConfigValues().getServers().size();
            for (ServerData serverData : getConfigValues().getServers().values()) {
                totalMS += serverData.getTimeoutMS();
            }
            averageTimeMS = totalMS /amount;

            gsonConfig.save();
            long timeEnd = System.nanoTime();
            timeTaken.set((timeEnd - timeStart) / 1000000);
        };

        if(async) {
            Universal.getMethods().runAsync(runnable);
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
    private long averageTimeMS;


}
