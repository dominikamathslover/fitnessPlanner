package org.create.fitnessplanner.service;
import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.WorkoutType;
import org.create.fitnessplanner.repository.UserRepository;
import org.create.fitnessplanner.repository.WorkoutTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EntityResolverService {

    private final UserRepository userRepository;
    private final WorkoutTypeRepository workoutTypeRepository;

    public EntityResolverService(UserRepository userRepository, WorkoutTypeRepository workoutTypeRepository) {
        this.userRepository = userRepository;
        this.workoutTypeRepository = workoutTypeRepository;
    }

    public User getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public WorkoutType getWorkoutTypeOrThrow(Integer id) {
        return workoutTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout type not found"));
    }

    public List<WorkoutType> getAllWorkoutTypes() {
        return workoutTypeRepository.findAll();
    }
}
