package io.github.fernthedev.serverstatusrest.minecraft.velocity;

import com.github.fernthedev.fernapi.server.velocity.FernVelocityAPI;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.fernthedev.serverstatusrest.core.Core;
import org.slf4j.Logger;

@Plugin(id = "server_status_rest", name = "Server Status REST", version = "1.1.0",
        description = "A REST API for getting server status info.", authors = {"Fernthedev"})
public final class Velocity extends FernVelocityAPI {

    @Override
    public void onProxyInitialization(ProxyInitializeEvent event) {
        super.onProxyInitialization(event);


        Core.init();
    }

    @Override
    public void onProxyStop(ProxyShutdownEvent event) {
        super.onProxyStop(event);
        Core.shutdown();

        // Plugin shutdown logic
    }

    public Velocity(ProxyServer server, Logger logger) {
        super(server, logger);
    }




}
