package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.travels.Travel;
import com.fijalkowskim.travelmemories.requestmodels.StageRequest;
import com.fijalkowskim.travelmemories.requestmodels.TravelRequest;
import com.fijalkowskim.travelmemories.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fijalkowskim.travelmemories.models.stages.Stage;
import com.fijalkowskim.travelmemories.services.StageService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/stages")
@CrossOrigin("http://localhost:3000")
public class StageController {
    private final StageService stageService;
    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }
    @GetMapping("/travel/{travelId}")
    public Page<Stage> getStagesOfTravel(
            @PathVariable long travelId,
            @RequestParam(name = "sort", defaultValue = "") String sort,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) throws CustomHTTPException {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return stageService.getStagesOfTravel(travelId, pageRequest, sort);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stage> getStage(
            @PathVariable long id) throws CustomHTTPException {
        return ResponseEntity.ok(stageService.getStageById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStage(@PathVariable long id) throws CustomHTTPException {
        stageService.deleteStage(id);
        return ResponseEntity.ok("Stage deleted successfully.");
    }

    @PutMapping("")
    public ResponseEntity<Stage> addStage(@RequestBody StageRequest stageRequest) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.CREATED).body(stageService.addStage(stageRequest));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Stage> updateStage(@PathVariable long id, @RequestBody StageRequest stageRequest) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.OK).body(stageService.updateStage(id,stageRequest));
    }

}
