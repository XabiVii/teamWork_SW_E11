package com.example.ecoembes.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecoembes.dao.EmployeeRepository;
import com.example.ecoembes.entity.Employee;

@Service
public class AuthService {

    private final EmployeeRepository employeeRepository;
    
    // Storage to keep the session of the employees that are logged in
    private static Map<String, Employee> tokenStore = new HashMap<>(); 

    public AuthService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
  
    // Login method that checks if the employee exists in the database and validates the password
    public Optional<String> login(String email, String password) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isPresent() && employee.get().checkPassword(password)) {
            String token = generateToken();  // Generate a random token for the session
            tokenStore.put(token, employee.get());  // Store the token and associate it with the employee

            return Optional.of(token);
        } else {
            return Optional.empty();
        }
    }

    // Logout method to remove the token from the session store
    public Optional<Boolean> logout(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);

            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }

    // Method to get the employee based on the token
    public Employee getEmployeeByToken(String token) {
        return tokenStore.get(token);
    }

    // Synchronized method to guarantee unique token generation
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }

}
