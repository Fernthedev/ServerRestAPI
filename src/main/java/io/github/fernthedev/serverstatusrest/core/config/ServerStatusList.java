package io.github.fernthedev.serverstatusrest.core.config;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ServerStatusList {

    @Getter
    private Map<String, Boolean> serverMap = new HashMap<>();

}
