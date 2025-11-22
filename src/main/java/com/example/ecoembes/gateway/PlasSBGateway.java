package com.example.ecoembes.gateway;

import java.time.LocalDate;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.ecoembes.gateway.dto.AssignResponseDto;
import com.example.ecoembes.dto.RecyclingPlantDto;
import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.gateway.dto.AssignRequestDto;

public class PlasSBGateway implements IPlantGateway {
	
	private static PlasSBGateway instance;

    private final String baseUrl = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();

    private PlasSBGateway() {
    }
    
    public static synchronized PlasSBGateway getInstance() {
        if (instance == null) {
            instance = new PlasSBGateway();
        }
        return instance;
    }

    @Override
    public RecyclingPlantDto getPlant() {
        return restTemplate.getForObject(baseUrl + "/plant", RecyclingPlantDto.class);

    }

    @Override
    public AssignmentRecord assignDumpster(Long dumpsterId, Long employeeId, int filling) {
        AssignRequestDto request = new AssignRequestDto(dumpsterId, employeeId, filling);
    	String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/recyclingPlant/assignDumpster")
                .toUriString();
        AssignResponseDto response = restTemplate.postForObject(url, request, AssignResponseDto.class);
        return AssignResponseDto.map("PlasSB", response.getDumpsterId(), response.getEmployeeId(), response.getDate());
    }

    @Override
    public Integer getRemainingCapacity(LocalDate date) {
    	String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/recyclingPlant/capacity")
                .queryParam("date", date)
                .toUriString();
        return restTemplate.getForObject(url, Integer.class);
    }
}
