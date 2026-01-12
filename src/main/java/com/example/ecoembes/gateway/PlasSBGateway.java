package com.example.ecoembes.gateway;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.ecoembes.gateway.dto.AssignResponseDto;
import com.example.ecoembes.dto.RecyclingPlantDto;
import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.gateway.dto.AssignRequestDto;

@Component("plassb")
public class PlasSBGateway implements IPlantGateway {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public PlasSBGateway(@Value("${plassb.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    @Override
    public RecyclingPlantDto getPlant() {
        return restTemplate.getForObject(baseUrl + "/plant", RecyclingPlantDto.class);

    }

    @Override
    public void assignDumpster(int totalDumpster, int filling) {
        AssignRequestDto request = new AssignRequestDto(totalDumpster, filling);
    	String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/recyclingPlant/assignDumpster")
                .toUriString();
        restTemplate.postForObject(url, request, AssignResponseDto.class);
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
