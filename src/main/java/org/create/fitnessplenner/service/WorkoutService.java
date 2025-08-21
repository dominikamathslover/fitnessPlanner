package org.create.fitnessplenner.service;

import org.create.fitnessplenner.dto.WorkoutDto;
import org.create.fitnessplenner.model.User;
import org.create.fitnessplenner.model.Workout;
import org.create.fitnessplenner.model.WorkoutType;
import org.create.fitnessplenner.repository.UserRepository;
import org.create.fitnessplenner.repository.WorkoutRepository;
import org.create.fitnessplenner.repository.WorkoutTypeRepository;
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

    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository, WorkoutTypeRepository workoutTypeRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
        this.workoutTypeRepository = workoutTypeRepository;
    }

    @Transactional
    public void addCompletedWorkout(String username, WorkoutDto workoutDto) {
        User user = getUserOrThrow(username);
        WorkoutType workoutType = getWorkoutTypeOrThrow(workoutDto.getWorkoutTypeId());

        if (!workoutDto.getDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Completed workout must be in the past");
        }

        Workout completedWorkout = createWorkout(user,workoutDto,workoutType);
        workoutRepository.save(completedWorkout);
    }

    @Transactional
    public void scheduleWorkout(String username, WorkoutDto workoutDto) {
        User user = getUserOrThrow(username);
        WorkoutType workoutType = getWorkoutTypeOrThrow(workoutDto.getWorkoutTypeId());

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

    private User getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private WorkoutType getWorkoutTypeOrThrow(Integer id) {
        return workoutTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout type not found"));
    }



}
