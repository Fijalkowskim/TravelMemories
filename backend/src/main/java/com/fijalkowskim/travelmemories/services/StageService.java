package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.photos.Photo;
import com.fijalkowskim.travelmemories.models.travels.Travel;
import com.fijalkowskim.travelmemories.repositories.StageDAORepository;
import com.fijalkowskim.travelmemories.repositories.TravelDAORepository;
import com.fijalkowskim.travelmemories.requestmodels.StageRequest;
import com.fijalkowskim.travelmemories.requestmodels.TravelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fijalkowskim.travelmemories.models.stages.Stage;

import java.util.ArrayList;
import java.util.Optional;

@Transactional
@Service
public class StageService {
    private final StageDAORepository stageDAORepository;
    private final TravelDAORepository travelDAORepository;
    @Autowired
    public StageService(StageDAORepository stageDAORepository,TravelDAORepository travelDAORepository) {
        this.stageDAORepository = stageDAORepository;
        this.travelDAORepository = travelDAORepository;
    }

    public Page<Stage> getStagesOfTravel(long travelId, Pageable pageable, String sort){
        Page<Stage> stages;
        switch(sort.toLowerCase()){
            case "asc":
                stages = stageDAORepository.findAllByTravelIdOrderByStageDateAsc(travelId, pageable);
                break;
            default:
                stages = stageDAORepository.findAllByTravelIdOrderByStageDateDesc(travelId, pageable);
        }
        return stages;
    }
    public Stage getStageById(Long stageId) throws CustomHTTPException {
        Optional<Stage> travel = stageDAORepository.findById(stageId);
        if(travel.isEmpty()) throw new CustomHTTPException("Stage not found", HttpStatus.NOT_FOUND);
        return travel.get();
    }
    public Stage addStage(StageRequest stageRequest) throws CustomHTTPException {
        Stage stage = stageFromRequest(stageRequest);
        return stageDAORepository.save(stage);
    }
    public Stage updateStage(long id, StageRequest stageRequest) throws CustomHTTPException {
        Optional<Stage> oldStage = stageDAORepository.findById(id);
        if(oldStage.isEmpty()) throw new CustomHTTPException("Stage not found", HttpStatus.NOT_FOUND);
        if(oldStage.get().getTravel().getId() != stageRequest.getTravelId()) throw new CustomHTTPException("Given parent travel is incorrect", HttpStatus.BAD_REQUEST);
        Stage stage = stageFromRequest(stageRequest);
        stage.setPhotos(oldStage.get().getPhotos());
        stage.setId(id);
        stage.setThumbnail(oldStage.get().getThumbnail());
        return stageDAORepository.save(stage);
    }
    public void  deleteStage(Long id) throws CustomHTTPException {
        Optional<Stage> stage = stageDAORepository.findById(id);
        if(stage.isEmpty()) throw new CustomHTTPException("Stage not found", HttpStatus.NOT_FOUND);
        stageDAORepository.delete(stage.get());
    }
    Stage stageFromRequest(StageRequest stageRequest) throws CustomHTTPException{
        Optional<Travel> travel = travelDAORepository.findById(stageRequest.getTravelId());
        if(travel.isEmpty()) throw new CustomHTTPException("Travel not found", HttpStatus.NOT_FOUND);
        Stage stage = new Stage();
        stage.setTravel(travel.get());
        stage.setStageDate(stageRequest.getDate());
        stage.setDescription(stageRequest.getDescription());
        stage.setLatitude(stageRequest.getLatitude());
        stage.setLongitude(stageRequest.getLongitude());
        stage.setLocationName(stageRequest.getLocationName());
        stage.setPhotos(new ArrayList<Photo>());
        return stage;
    }
}
