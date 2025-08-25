package org.create.fitnessplanner.controller;
import jakarta.validation.Valid;
import org.create.fitnessplanner.dto.WorkoutDto;
import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.create.fitnessplanner.model.WorkoutType;
import org.create.fitnessplanner.repository.UserRepository;
import org.create.fitnessplanner.repository.WorkoutRepository;
import org.create.fitnessplanner.repository.WorkoutTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/{username}/add-training")
public class WorkoutFormController {

    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private WorkoutTypeRepository workoutTypeRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showAddTrainingForm(@PathVariable String username, Model model) {
        User user = getUserOrThrow(username);
        List<WorkoutType> workoutTypes = workoutTypeRepository.findAll();

        model.addAttribute("username", username);
        model.addAttribute("workoutDto", new WorkoutDto());
        model.addAttribute("workoutTypes", workoutTypes);

        return "add-training";
    }

    @PostMapping
    public ResponseEntity<String> addTraining(@PathVariable String username,
                                              @ModelAttribute @Valid WorkoutDto workoutDto) {
        User user = getUserOrThrow(username);
        WorkoutType workoutType = getWorkoutTypeOrThrow(workoutDto.getWorkoutTypeId());

        Workout workout = new Workout();
        workout.setUser(user);
        workout.setDate(workoutDto.getDate());
        workout.setDurationInMinutes(workoutDto.getDurationInMinutes());
        workout.setWorkoutType(workoutType);

        workoutRepository.save(workout);

        String answer = getAnswerCompletedOrNew(workout);
        return ResponseEntity.ok(answer);
    }

    private User getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private WorkoutType getWorkoutTypeOrThrow(Integer workoutTypeId) {
        return workoutTypeRepository.findById(workoutTypeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout id not found"));
    }

    private static String getAnswerCompletedOrNew(Workout workout) {
        return LocalDateTime.now().isBefore(workout.getDate())
                ? "You have scheduled the future training."
                : "You have saved the completed training.";
    }
}
