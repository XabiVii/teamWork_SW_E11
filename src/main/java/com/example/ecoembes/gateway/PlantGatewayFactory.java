package com.example.ecoembes.gateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlantGatewayFactory {

    private static final Map<String, IPlantGateway> cache = new ConcurrentHashMap<>();

    public static IPlantGateway getGateway(String name) {
        return cache.computeIfAbsent(name, key -> {
            switch (key.toLowerCase()) {
                case "contsocket":
                    return ConSocketGateway.getInstance();
                case "plassb":
                    return PlasSBGateway.getInstance();
                default:
                    throw new IllegalArgumentException("Unknown plant gateway: " + name);
            }
        });
    }
}