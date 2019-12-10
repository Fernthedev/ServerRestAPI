package io.github.fernthedev.serverstatusrest.minecraft.bungee;

import com.github.fernthedev.fernapi.server.bungee.FernBungeeAPI;
import io.github.fernthedev.serverstatusrest.core.Core;

public final class Bungee extends FernBungeeAPI {

    @Override
    public void onEnable() {
        super.onEnable();

        Core.init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Core.shutdown();
    }
}
