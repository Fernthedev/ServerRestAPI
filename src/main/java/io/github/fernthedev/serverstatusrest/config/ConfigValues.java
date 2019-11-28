package io.github.fernthedev.serverstatusrest.config;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Data
public class ConfigValues {

    private int RestAPIPort = 3000;

    private Map<String, ServerData> servers = new HashMap<>();

}
