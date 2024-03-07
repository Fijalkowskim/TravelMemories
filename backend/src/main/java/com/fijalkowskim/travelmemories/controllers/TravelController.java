package com.fijalkowskim.travelmemories.controllers;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.requestmodels.TravelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fijalkowskim.travelmemories.models.travels.Travel;
import com.fijalkowskim.travelmemories.services.PhotoService;
import com.fijalkowskim.travelmemories.services.StageService;
import com.fijalkowskim.travelmemories.services.TravelService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/travels")
@CrossOrigin("http://localhost:3000")
public class TravelController {
    PhotoService photoService;
    StageService stageService;
    TravelService travelService;

    @Autowired
    public TravelController(PhotoService photoService, StageService stageService, TravelService travelService) {
        this.photoService = photoService;
        this.stageService = stageService;
        this.travelService = travelService;
    }

    @GetMapping("")
    public Page<Travel> getTravels(
            @RequestParam(name = "sort", defaultValue = "latest") String sort,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return travelService.getTravels(pageRequest, sort);
    }
//    @GetMapping("/travel/public-photos")
//    public List<Travel> getTravelsPublicPhotos(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "pageSize", defaultValue = "40") int pageSize){
//        PageRequest pageRequest = PageRequest.of(page, pageSize);
//        return travelService.getTravelsWithPublicPhotos(pageRequest);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStage(@PathVariable long id) throws CustomHTTPException {
        travelService.deleteTravel(id);
        return ResponseEntity.ok("Travel deleted successfully.");
    }

    @PutMapping("")
    public ResponseEntity<Travel> addTravel(@RequestBody TravelRequest travelRequest) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.CREATED).body(travelService.addTravel(travelRequest));
    }
    @PostMapping("/{id}")
    public ResponseEntity<Travel> addTravel(@PathVariable long id, @RequestBody TravelRequest travelRequest) throws CustomHTTPException{
        return ResponseEntity.status(HttpStatus.OK).body(travelService.updateTravel(id,travelRequest));
    }
}