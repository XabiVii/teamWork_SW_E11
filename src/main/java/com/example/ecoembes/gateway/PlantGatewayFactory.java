package com.example.ecoembes.gateway;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PlantGatewayFactory {

    private final Map<String, IPlantGateway> gateways;

    public PlantGatewayFactory(Map<String, IPlantGateway> gateways) {
        this.gateways = gateways;
    }

    public IPlantGateway getGateway(String name) {
        return gateways.get(name.toLowerCase());
    }
    
    public Map<String, IPlantGateway> getAllGateways() {
        return gateways;
    }
}
	