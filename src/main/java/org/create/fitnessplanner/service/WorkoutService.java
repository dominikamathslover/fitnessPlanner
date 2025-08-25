package org.create.fitnessplanner.service;

import org.create.fitnessplanner.dto.WorkoutDto;
import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.create.fitnessplanner.model.WorkoutType;
import org.create.fitnessplanner.repository.UserRepository;
import org.create.fitnessplanner.repository.WorkoutRepository;
import org.create.fitnessplanner.repository.WorkoutTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final WorkoutTypeRepository workoutTypeRepository;
    private final EntityResolverService entityResolverService;

    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository, WorkoutTypeRepository workoutTypeRepository, EntityResolverService entityResolverService) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
        this.workoutTypeRepository = workoutTypeRepository;
        this.entityResolverService = entityResolverService;
    }

    @Transactional
    public void addCompletedWorkout(String username, WorkoutDto workoutDto) {
        User user = entityResolverService.getUserOrThrow(username);
        WorkoutType workoutType = entityResolverService.getWorkoutTypeOrThrow(workoutDto.getWorkoutTypeId());

        if (!workoutDto.getDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Completed workout must be in the past");
        }

        Workout completedWorkout = createWorkout(user,workoutDto,workoutType);
        workoutRepository.save(completedWorkout);
    }

    @Transactional
    public void scheduleWorkout(String username, WorkoutDto workoutDto) {
        User user = entityResolverService.getUserOrThrow(username);
        WorkoutType workoutType = entityResolverService.getWorkoutTypeOrThrow(workoutDto.getWorkoutTypeId());

        if (workoutDto.getDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New workout must be in the future");
        }

        Workout plannedWorkout = createWorkout(user,workoutDto,workoutType);

        workoutRepository.save(plannedWorkout);
    }

    public List<Workout> getUserWorkouts(String username) {
        return workoutRepository.findByUserUsernameOrderByDateDesc(username);
    }


    private Workout createWorkout(User user, WorkoutDto dto, WorkoutType type) {
        Workout workout = new Workout();
        workout.setUser(user);
        workout.setDate(dto.getDate());
        workout.setDurationInMinutes(dto.getDurationInMinutes());
        workout.setWorkoutType(type);
        return workout;
    }


}
