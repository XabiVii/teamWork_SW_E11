package com.example.ecoembes.facade;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecoembes.dto.DumpsterDto;
import com.example.ecoembes.dto.UsageRecordDto;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;
import com.example.ecoembes.entity.UsageRecord;
import com.example.ecoembes.service.AuthService;
import com.example.ecoembes.service.DumpsterService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/dumpsters")
public class DumpsterController {

    private final DumpsterService dumpsterService;
	private final AuthService authService;

    public DumpsterController(DumpsterService dumpsterService, AuthService authService) {
        this.dumpsterService = dumpsterService;
		this.authService = authService;
    }


    // Get all dumpsters
    @GetMapping
    public ResponseEntity<List<DumpsterDto>> getAllDumpsters(
    	    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Authorization token in plain text", required = true)
    	    @RequestHeader("Token") String token) {    
    	Employee employee = authService.getEmployeeByToken(token);
    	
    	if (employee == null) {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}    
    	List<Dumpster> dumpsters = dumpsterService.getAllDumpsters();
        return new ResponseEntity<>(DumpsterDto.Map(dumpsters), dumpsters.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK);
    }  

    // create new Dumpster
    @PostMapping()
    public ResponseEntity<DumpsterDto> CreateDumpster(
	@Parameter(name = "Dumpster", description = "Dumpster", required = true)
	@RequestBody DumpsterDto dumpster,
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Authorization token in plain text", required = true)
	@RequestHeader("Token") String token) {
        try {
        	Employee employee = authService.getEmployeeByToken(token);
	    	
	    	if (employee == null) {
	    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    	}
            Dumpster createDumpster = dumpsterService.createDumpster(dumpster.Map());
            return new ResponseEntity<>(DumpsterDto.Map(createDumpster), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    // update dumpster info
    @PutMapping("/{DumpsterId}/dump_info")
    public ResponseEntity<DumpsterDto> updateDumpsterInfo(
	@Parameter(name = "DumpsterId", description = "Id of the dumpster", required = true, example = "1")
	@PathVariable("DumpsterId") long id,
	@Parameter(name = "currentFill", description = "CurrentFill", required = true, example = "100")
	@RequestBody int currentFill,
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Authorization token in plain text", required = true)
	@RequestHeader("Token") String token) {
        try {
        	Employee employee = authService.getEmployeeByToken(token);
	    	
	    	if (employee == null) {
	    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    	}
            Optional<Dumpster> updateDumpster = dumpsterService.updateDumpsterInfo(id, currentFill);
            if (updateDumpster.isPresent()) {
                return new ResponseEntity<>(DumpsterDto.Map(updateDumpster.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{dumpsterId}/usage")
    public ResponseEntity<?> getDumpsterUsage(
        @Parameter(name = "dumpsterId", description = "Id of the dumpster", required = true, example = "1")
        @PathVariable("dumpsterId") long dumpsterId,
        @Parameter(name = "start_date", description = "Start date (yyyy-MM-dd)", required = true, example = "2025-10-30")
        @RequestParam("start_date") LocalDate startDate,
        @Parameter(name = "end_date", description = "End date (yyyy-MM-dd)", required = true, example = "2025-10-31")
        @RequestParam("end_date") LocalDate endDate,
        @RequestHeader("Token") String token
    ) {
        Employee employee = authService.getEmployeeByToken(token);

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Dumpster> dumpsterOpt = dumpsterService.getDumpsterById(dumpsterId);
        if (dumpsterOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        List<UsageRecord> usageRecords = dumpsterService.getUsageBetweenDates(dumpsterId, startDate, endDate);


        return new ResponseEntity<>(UsageRecordDto.Map(usageRecords), usageRecords.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK);
    }

    @GetMapping("/status/postal_code")
    public ResponseEntity<?> getDumpsterUsage(
        @Parameter(name = "date", description = "date (yyyy-MM-dd)", required = true, example = "2025-10-30")
        @RequestParam("date") LocalDate date,
        @Parameter(name = "postal_code", description = "postal code", required = true, example = "86000")
        @RequestParam("postal_code") String postalCode,
        @RequestHeader("Token") String token
    ) {
        Employee employee = authService.getEmployeeByToken(token);

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        List<Dumpster> dumpsters = dumpsterService.getDumpsterByPostalCodeAndDate(date, postalCode);


        return new ResponseEntity<>(DumpsterDto.Map(dumpsters), dumpsters.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK);
    }

}
