package com.eternity.jessemood.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class MinimumHealthUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final int DEFAULT_MINIMUM_HEALTH = 3;
    @Getter
    private static int jesusHealth;

    public static void createJesusHealth() {
        log.info("Create minimum health");
        Path pathToConfig = FabricLoader.getInstance().getConfigDir().resolve("Jeesemood.json");
        if (!Files.exists(pathToConfig)) {
            try {
                log.info("Creating config file");
                Files.createFile(pathToConfig);
                Files.writeString(pathToConfig, mapper.writeValueAsString(new MinimumHealth(DEFAULT_MINIMUM_HEALTH)));
                log.info("Successfully created config file with default value");
            } catch (Exception e) {
                log.error("Failed to create config file", e);
            }
        }

        MinimumHealth minimumHealth = null;
        try {
            log.info("Reading config file");
            minimumHealth = mapper.readValue(Files.newBufferedReader(pathToConfig), MinimumHealth.class);
        } catch (Exception e) {
            log.error("Failed to read config file", e);
        }

        log.info("Setting minimum health");
        int health = minimumHealth == null ? DEFAULT_MINIMUM_HEALTH : minimumHealth.getMinimumHealth();
        jesusHealth = health < 0 || health > 20 ? DEFAULT_MINIMUM_HEALTH : health;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class MinimumHealth {
        private int minimumHealth;
    }
}
