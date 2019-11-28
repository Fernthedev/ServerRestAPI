package io.github.fernthedev.serverstatusrest;

import com.github.fernthedev.fernapi.server.bungee.FernBungeeAPI;
import com.github.fernthedev.fernapi.universal.Universal;
import io.github.fernthedev.serverstatusrest.config.ConfigManager;
import io.github.fernthedev.serverstatusrest.rest.ListREST;
import io.servicetalk.http.netty.HttpServers;
import io.servicetalk.http.router.jersey.HttpJerseyRouterBuilder;
import io.servicetalk.transport.api.ServerContext;
import lombok.Getter;

import java.io.File;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public final class ServerStatusRest extends FernBungeeAPI {


    @Getter
    private static final List<Class<?>> restHandlers = new ArrayList<>();

    @Getter
    private static ConfigManager configManager;

    private static ServerContext context;

    @Getter
    private static SocketAddress socketAddress;

    @Getter
    private static ServerStatusRest instance;

    private boolean started = false;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

//        Universal.setDebug(true);
        restHandlers.add(App.class);
        restHandlers.add(ListREST.class);

        if(!getDataFolder().exists()) getDataFolder().mkdir();

        configManager = new ConfigManager(new File(getDataFolder(),"config.json"));
        // Plugin startup logic

        runServer();

        Universal.getCommandHandler().registerFernCommand(new MainCommand());

        ListREST.setupTask();
    }

    public void stopServer() {
        try {
            getLogger().info("Closing ServiceTalk Jersey Server gracefully");

            context.closeGracefully();
            started = false;
            getLogger().info("Closed ServiceTalk Jersey Server gracefully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        if (started) stopServer();
        runServer();
    }

    public void runServer() {

        try {


            context = HttpServers.forPort(configManager.getConfigValues().getRestAPIPort())
//                    .protocols(h1()) // Configure HTTP/2 Prior-Knowledge
//                    .secure().commit(DefaultTestCerts::loadServerPem, DefaultTestCerts::loadServerKey)
                    .listenStreamingAndAwait(new HttpJerseyRouterBuilder().
                            buildStreaming(new App()));

            socketAddress = context.listenAddress();
            started = true;

            getLogger().info("ServiceTalk Jersey Server listening on " + socketAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info("Shutting down ServiceTalk Jersey Server.");
        try {
            context.closeGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLogger().info("Shut down ServiceTalk Jersey Server.");
        // Plugin shutdown logic
    }
}
