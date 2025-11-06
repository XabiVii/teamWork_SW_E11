package com.example.ecoembes.facade;

import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.service.EcoembesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EcoembesController {

    @Autowired
    private EcoembesService service;

    //LOGIN
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password) {
        return service.login(email, password);
    }

    //LOGOUT 
    @DeleteMapping("/logout")
    public void logout(@RequestParam("token") String token) {
        service.logout(token);
    }

    //CREATE DUMPSTER
    @PostMapping("/dumpsters")
    public Dumpster createDumpster(@RequestParam("token") String token,
                                   @RequestBody Dumpster d) {
        return service.createDumpster(d);
    }

    //UPDATE DUMPSTER INFO
    @PutMapping("/dumpsters/{id}")
    public void updateDumpsterInfo(@RequestParam("token") String token,
                                   @PathVariable("id") String id,
                                   @RequestParam("estimatedNumCont") int estimatedNumCont,
                                   @RequestParam("fillLevel") String fillLevel) {
        service.updateDumpsterInfo(id, estimatedNumCont, fillLevel);
    }

    //GET ALL DUMPSTERS
    @GetMapping("/dumpsters")
    public Map<String, Dumpster> getAll(@RequestParam("token") String token) {
        return service.getDumpsters();
    }
    
    //QUERY DUMPSTER USAGE
    @GetMapping("/dumpsters/{dump_id}/usage")
    public Map<String, Object> getDumpsterUsage(@RequestParam("token") String token,
                                                @PathVariable("dump_id") int dumpId,
                                                @RequestParam("start_date") String startDate,
                                                @RequestParam("end_date") String endDate) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("dumpster_id", dumpId);
        result.put("address", "Calle Mayor 1, Bilbao");

        List<Map<String, Object>> usage = new ArrayList<>();
        usage.add(Map.of("date", "2025-11-04", "estimated_num_cont", 4, "fill_level", "HIGH"));
        usage.add(Map.of("date", "2025-11-05", "estimated_num_cont", 2, "fill_level", "LOW"));
        usage.add(Map.of("date", "2025-11-06", "estimated_num_cont", 3, "fill_level", "MEDIUM"));

        result.put("usage", usage);
        return result;
    }

    //CHECK DUMPSTER STATUS
    @GetMapping("/dumpster/status")
    public Map<String, Object> getDumpsterStatus(@RequestParam("token") String token,
                                                 @RequestParam("postal_code") int postalCode,
                                                 @RequestParam("date") String date) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("postal_code", postalCode);
        result.put("date", date);

        List<Map<String, Object>> statuses = new ArrayList<>();
        statuses.add(Map.of("dumpster_id", 1, "address", "Calle Bilbao 1", "status", "GREEN"));
        statuses.add(Map.of("dumpster_id", 2, "address", "Calle Bilbao 2", "status", "ORANGE"));
        statuses.add(Map.of("dumpster_id", 3, "address", "Calle Bilbao 3", "status", "RED"));

        result.put("statuses", statuses);
        return result;
    }

    //CHECK RECYCLING PLANT CAPACITY
    @GetMapping("/recyclingPlants/{plant_id}/capacity")
    public Map<String, Object> getPlantCapacity(@RequestParam("token") String token,
                                                @PathVariable("plant_id") int plantId,
                                                @RequestParam("date") String date) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("plant_id", plantId);
        result.put("date", date);
        result.put("capacity", "86%");
        return result;
    }

    //ASSIGN DUMPSTERS TO RECYCLING PLANTS
    @PostMapping("/recyclingPlants/assignDumpsters")
    public String assignDumpsters(@RequestParam("token") String token,
                                  @RequestBody Map<String, Object> body) {
        return "Dumpsters assigned successfully";
    }

    //OPTIONAL: SATURATION INDICATOR
    @GetMapping("/dumpster/saturation")
    public Map<String, Object> getSaturation(@RequestParam("token") String token,
                                             @RequestParam("date") String date,
                                             @RequestParam("zone") String zone) {
        Map<String, Object> json = new HashMap<>();
        json.put("date", date);
        json.put("zone", zone);
        json.put("saturation_level", "MEDIUM");
        return json;
    }
    
}
