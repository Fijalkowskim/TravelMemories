package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.models.stages.Stage;
import com.fijalkowskim.travelmemories.models.travels.Travel;
import com.fijalkowskim.travelmemories.models.travels.TravelOverview;
import com.fijalkowskim.travelmemories.models.users.User;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import com.fijalkowskim.travelmemories.requestmodels.TravelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fijalkowskim.travelmemories.repositories.TravelDAORepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class TravelService {
    private final TravelDAORepository travelDAORepository;
    private final UserDAORepository userDAORepository;
    @Autowired
    public TravelService(TravelDAORepository travelDAORepository,UserDAORepository userDAORepository){
        this.travelDAORepository = travelDAORepository;
        this.userDAORepository = userDAORepository;
    }

    public Page<Travel> getTravels(Pageable pageable, String sort){
        Page<Travel> travels;
        switch(sort.toLowerCase()){
            case "asc":
                travels = travelDAORepository.findAllByOrderByTravelDateAsc(pageable);
                break;
            default:
                travels = travelDAORepository.findAllByOrderByTravelDateDesc(pageable);
        }
        return travels;
    }
    public Travel getTravelById(Long travelId) throws CustomHTTPException{
        Optional<Travel> travel = travelDAORepository.findById(travelId);
        if(travel.isEmpty()) throw new CustomHTTPException("Travel not found", HttpStatus.NOT_FOUND);
        return travel.get();
    }
    public Page<TravelOverview> getTravelsOverviews(long userId, Pageable pageable,String sort) throws  CustomHTTPException{
        Optional<User> user = userDAORepository.findById(userId);
        if(user.isEmpty()) throw new CustomHTTPException("User not found", HttpStatus.NOT_FOUND);
        Page<Travel> travels = getTravels(pageable,sort);
        List<TravelOverview> travelOverviews = travels.getContent().stream()
                .map(TravelOverview::new)
                .collect(Collectors.toList());

        return new PageImpl<>(travelOverviews, pageable, travels.getTotalElements());
    }
    public void  deleteTravel(Long id) throws CustomHTTPException {
        Optional<Travel> travel = travelDAORepository.findById(id);
        if(travel.isEmpty()) throw new CustomHTTPException("Travel not found", HttpStatus.NOT_FOUND);
        travelDAORepository.delete(travel.get());
    }
    public Travel addTravel(TravelRequest travelRequest) throws CustomHTTPException {
        Travel travel = travelFromRequest(travelRequest);
        return travelDAORepository.save(travel);
    }
    public Travel updateTravel(long id, TravelRequest travelRequest) throws CustomHTTPException {
        Optional<Travel> oldTravel = travelDAORepository.findById(id);
        if(oldTravel.isEmpty()) throw new CustomHTTPException("Travel not found", HttpStatus.NOT_FOUND);
        if(oldTravel.get().getUser().getId() != travelRequest.getUserId()) throw new CustomHTTPException("Given user is not the owner", HttpStatus.BAD_REQUEST);
        Travel travel = travelFromRequest(travelRequest);
        travel.setStages(oldTravel.get().getStages());
        travel.setId(id);
        travel.setThumbnail(oldTravel.get().getThumbnail());
        return travelDAORepository.save(travel);
    }
    Travel travelFromRequest(TravelRequest travelRequest) throws CustomHTTPException{
        Optional<User> user = userDAORepository.findById(travelRequest.getUserId());
        if(user.isEmpty()) throw new CustomHTTPException("User not found", HttpStatus.NOT_FOUND);
        Travel travel = new Travel();
        travel.setUser(user.get());
        travel.setTravelDate(travelRequest.getTravelDate());
        travel.setDescription(travelRequest.getDescription());
        travel.setLatitude(travelRequest.getLatitude());
        travel.setLongitude(travelRequest.getLongitude());
        travel.setLocationName(travelRequest.getLocationName());
        travel.setStages(new ArrayList<Stage>());
        return travel;
    }
}
