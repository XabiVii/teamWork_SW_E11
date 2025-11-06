package com.example.ecoembes.service;

import com.example.ecoembes.entity.Dumpster;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EcoembesService {

    private final Map<String, String> tokens = new HashMap<>();
    private final Map<String, Dumpster> dumpsters = new HashMap<>();

    public String login(String email, String password) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        String token = LocalDateTime.now().format(formatter);
        tokens.put(token, email);
        return token;
    }


    public void logout(String token) {
        tokens.remove(token);
    }

    public Dumpster createDumpster(Dumpster d) {
        dumpsters.put(String.valueOf(d.getId()), d);
        return d;
    }


    public void updateDumpsterInfo(String dumpId, int estimatedNumCont, String fillLevel) {
        Dumpster d = dumpsters.get(dumpId);
        if (d != null) d.setFillLevel(fillLevel);
    }

    public Map<String, Dumpster> getDumpsters() {
        return dumpsters;
    }
    
    public Map<String, Object> getDumpsterUsage(int dumpId, String startDate, String endDate) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("dumpster_id", dumpId);
        result.put("address", "Calle Mayor 1, Bilbao");

        List<Map<String, Object>> usage = new ArrayList<>();
        usage.add(Map.of("date", startDate, "estimated_num_cont", 3, "fill_level", "ORANGE"));
        usage.add(Map.of("date", endDate, "estimated_num_cont", 4, "fill_level", "GREEN"));

        result.put("usage", usage);
        return result;
    }

    public Map<String, Object> getDumpsterStatus(int postalCode, String date) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("postal_code", postalCode);
        result.put("date", date);

        List<Map<String, Object>> statuses = new ArrayList<>();
        statuses.add(Map.of("identifier", 1, "address", "Calle Bilbao 1", "postal_code", postalCode, "initial_capacity", 100.0, "status", "GREEN"));
        statuses.add(Map.of("identifier", 2, "address", "Calle Bilbao 2", "postal_code", postalCode, "initial_capacity", 80.0, "status", "ORANGE"));
        statuses.add(Map.of("identifier", 3, "address", "Calle Bilbao 3", "postal_code", postalCode, "initial_capacity", 60.0, "status", "RED"));

        result.put("statuses", statuses);
        return result;
    }

    public Map<String, Object> getPlantCapacity(int plantId, String date) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("plant_id", plantId);
        result.put("date", date);
        result.put("capacity", "85%");
        return result;
    }

    public String assignDumpsters(Map<String, Object> body) {
        return "Dumpsters assigned successfully to recycling plant";
    }

    public Map<String, Object> getSaturation(String date, String zone) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("date", date);
        result.put("zone", zone);
        result.put("saturation_level", "MEDIUM");
        return result;
    }
    
}
