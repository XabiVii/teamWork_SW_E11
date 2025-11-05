package com.example.ecoembes.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecoembes.Dto.DumpsterDto;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.service.DumpsterService;

@RestController
@RequestMapping("/dumpsters")
public class DumpsterController {

    private final DumpsterService dumpsterService;

    public DumpsterController(DumpsterService dumpsterService) {
        this.dumpsterService = dumpsterService;
    }


    // Get all dumpsters
    @GetMapping
    public ResponseEntity<List<DumpsterDto>> getAllDumpsters() {        
    	List<Dumpster> dumpsters = dumpsterService.getAllDumpsters();
        return new ResponseEntity<>(DumpsterDto.Map(dumpsters), dumpsters.isEmpty() ? HttpStatus.NO_CONTENT: HttpStatus.OK);
    }  
    
    // update dumpster info
    @PutMapping("/{id}/dump_info")
    public ResponseEntity<DumpsterDto> updateOrderStatus(@PathVariable("id") Long id, @RequestBody int currentFill) {
        try {
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

}
