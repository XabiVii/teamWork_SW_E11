package com.example.ecoembes.gateway;

import java.time.LocalDate;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Value;
import com.example.ecoembes.gateway.dto.AssignResponseDto;
import com.example.ecoembes.dto.RecyclingPlantDto;
import com.example.ecoembes.entity.AssignmentRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component("contsocket")
public class ConSocketGateway implements IPlantGateway {

    private final String host;
    private final int port;
    private final ObjectMapper objectMapper;

    public ConSocketGateway(
            @Value("${consocket.host}") String host,
            @Value("${consocket.port}") int port) {
        this.host = host;
        this.port = port;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private String sendCommand(String cmd) throws IOException {
    	
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
        	String welcome = in.readLine();
            if ("CONNECTED".equals(welcome)) {
                System.out.println("Server says CONNECTED");
            }
            out.println(cmd);
            return in.readLine();
        }
    }

    @Override
    public RecyclingPlantDto getPlant() {
        try {
            String response = sendCommand("PLANT");
            return objectMapper.readValue(response, RecyclingPlantDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void assignDumpster(int totalContainer, int filling) {
        try {
            String cmd = String.format("ADD_ASSIGNMENT;totalDumpsters=%d;filling=%d", totalContainer, filling);
            String response = sendCommand(cmd);
            if (!response.equals("200")) {
                throw new RuntimeException("Server error: " + response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getRemainingCapacity(LocalDate date) {
        try {
            String cmd = String.format("GET_CAPACITY;date=%s", date);
            String response = sendCommand(cmd);
            return "ERROR".equals(response) ? null : Integer.parseInt(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
