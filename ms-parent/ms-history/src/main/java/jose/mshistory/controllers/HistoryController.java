package jose.mshistory.controllers;

import jose.mshistory.dtos.RaceDtoResponse;
import jose.mshistory.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/races/history")
public class HistoryController {

    HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    ResponseEntity<List<RaceDtoResponse>> getAllRaces(){
        List<RaceDtoResponse> allRaces = historyService.getAllRaces();
        return ResponseEntity.ok().body(allRaces);
    }

    @GetMapping("/{id}")
    ResponseEntity<RaceDtoResponse> getRaceById(@PathVariable String id){
        RaceDtoResponse raceById = historyService.getRaceById(id);
        return ResponseEntity.ok().body(raceById);
    }
}
