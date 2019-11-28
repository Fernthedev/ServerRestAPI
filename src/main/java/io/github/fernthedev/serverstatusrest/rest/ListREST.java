package io.github.fernthedev.serverstatusrest.rest;

import com.alibaba.fastjson.JSON;
import com.github.fernthedev.fernapi.universal.Universal;
import io.github.fernthedev.serverstatusrest.Constants;
import io.github.fernthedev.serverstatusrest.ServerStatusRest;
import io.github.fernthedev.serverstatusrest.config.ServerData;
import io.github.fernthedev.serverstatusrest.config.ServerStatusList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

@Path(value = Constants.MAIN_PATH + "list/")
public class ListREST {

    private static ServerStatusList serverStatusList;

    public static void setupTask() {
        serverStatusList = new ServerStatusList();
        Universal.getMethods().runSchedule(() -> {
            try {
                Universal.debug("Pinging the servers");
                for (ServerData serverData : ServerStatusRest.getConfigManager().getConfigValues().getServers().values()) {

                    if (serverData.isHidden()) {
                        serverStatusList.getServerMap().remove(serverData.getName());
                        continue;
                    }

                    Universal.getMethods().runAsync(() -> {
                        serverData.ping();
                        Universal.debug("Pinging the server info Name:" + serverData.getName() + " status: " + serverData.isOnline() + " " + serverData);
                        serverStatusList.getServerMap().put(serverData.getName(), serverData.isOnline());
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, ServerStatusRest.getConfigManager().getAverageTimeMS(), TimeUnit.MILLISECONDS);

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Path(value = "/get")
    public String getServerListStatus() {
        return JSON.toJSONString(serverStatusList);
    }

}
