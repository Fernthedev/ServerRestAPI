package io.github.fernthedev.serverstatusrest.core;

import com.github.fernthedev.fernapi.universal.Universal;
import io.github.fernthedev.serverstatusrest.minecraft.command.MainCommand;
import io.github.fernthedev.serverstatusrest.core.config.ConfigManager;
import io.github.fernthedev.serverstatusrest.core.rest.ListREST;
import io.servicetalk.http.netty.HttpServers;
import io.servicetalk.http.router.jersey.HttpJerseyRouterBuilder;
import io.servicetalk.transport.api.ServerContext;
import lombok.Getter;

import java.io.File;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Core {
    @Getter
    private static final List<Class<?>> restHandlers = new ArrayList<>();

    @Getter
    private static ConfigManager configManager;

    private static ServerContext context;

    @Getter
    private static SocketAddress socketAddress;

    private static Core instance;
    private static boolean started = false;

    private Core() {}

    public static void init() {
        if (instance == null) {
            instance = new Core();
//            Universal.setDebug(true);
            restHandlers.add(App.class);
            restHandlers.add(ListREST.class);

            File dataDir = Universal.getMethods().getDataFolder();

            if (!dataDir.exists()) dataDir.mkdir();

            configManager = new ConfigManager(new File(dataDir, "config.json"));
            // Plugin startup logic

            runServer();


            Universal.getCommandHandler().registerCommand(new MainCommand());

            ListREST.setupTask();
        } else throw new IllegalStateException("Already initialized");
    }

    public static void runServer() {
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

    public static void shutdown() {
        try {
            getLogger().info("Closing ServiceTalk Jersey Server gracefully");

            context.closeGracefully();
            started = false;
            getLogger().info("Closed ServiceTalk Jersey Server gracefully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static java.util.logging.Logger getLogger() {
        return Universal.getMethods().getLogger();
    }

    public static void restart() {
        if (started) shutdown();
        runServer();
    }
}
