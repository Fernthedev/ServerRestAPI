package io.github.fernthedev.serverstatusrest.minecraft.bungee;

import com.github.fernthedev.fernapi.server.bungee.FernBungeeAPI;
import io.github.fernthedev.serverstatusrest.Core;
import lombok.Getter;

public final class Bungee extends FernBungeeAPI {



    @Getter
    private static Bungee instance;



    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        Core.init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Core.shutdown();
    }
}
