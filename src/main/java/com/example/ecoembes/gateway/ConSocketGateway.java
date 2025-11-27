package com.example.ecoembes.gateway;

import java.time.LocalDate;
import java.io.*;
import java.net.Socket;

import com.example.ecoembes.gateway.dto.AssignResponseDto;
import com.example.ecoembes.dto.RecyclingPlantDto;
import com.example.ecoembes.entity.AssignmentRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ConSocketGateway implements IPlantGateway {

    private static ConSocketGateway instance;

    private final String host = "localhost";
    private final int port = 8081;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ConSocketGateway() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static synchronized ConSocketGateway getInstance() {
        if (instance == null) {
            instance = new ConSocketGateway();
        }
        return instance;
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
    public AssignmentRecord assignDumpster(Long dumpsterId, Long employeeId, int filling) {
        try {
            String cmd = String.format("ADD_ASSIGNMENT;dumpsterId=%d;employeeId=%d;filling=%d", dumpsterId, employeeId, filling);
            String response = sendCommand(cmd);
            if (response.contains("\"error\"")) {
                throw new RuntimeException("Server error: " + response);
            }
            AssignResponseDto assignment = objectMapper.readValue(response, AssignResponseDto.class);
            return AssignResponseDto.map("ContSocket", assignment.getDumpsterId(), assignment.getEmployeeId(), assignment.getDate());
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
