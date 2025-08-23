package org.create.fitnessplanner.controller;

import jakarta.validation.Valid;
import org.create.fitnessplanner.dto.WorkoutDto;
import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.create.fitnessplanner.model.WorkoutType;
import org.create.fitnessplanner.repository.UserRepository;
import org.create.fitnessplanner.repository.WorkoutRepository;
import org.create.fitnessplanner.repository.WorkoutTypeRepository;
import org.create.fitnessplanner.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WorkoutController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutTypeRepository workoutTypeRepository;

    @Transactional(readOnly = true)
    @GetMapping("/{username}/workouts")
    public String showUserCompletedWorkouts(@PathVariable String username, Model model) {
        User user = getUserOrThrow(username);
        List<Workout> pastWorkouts = getPastWorkouts(user);

        addWorkoutStatsToModel(model, user, pastWorkouts);

        return "user-workouts";
    }

    private List<Workout> getPastWorkouts(User user) {
        LocalDateTime now = LocalDateTime.now();
        List<Workout> workouts = workoutRepository.findPastWorkoutsWithType(user, now);
        return workouts;
    }

    private void addWorkoutStatsToModel(Model model, User user, List<Workout> workouts) {
        int totalDuration = workouts.stream()
                .mapToInt(Workout::getDurationInMinutes)
                .sum();
        int sessionCount = workouts.size();
        int averageDuration = sessionCount > 0 ? totalDuration / sessionCount : 0;

        model.addAttribute("user", user);
        model.addAttribute("workouts", workouts);
        model.addAttribute("totalDuration", totalDuration);
        model.addAttribute("sessionCount", sessionCount);
        model.addAttribute("averageDuration", averageDuration);
    }


        @GetMapping("/{username}/add-training")
    public String showAddTrainingForm(@PathVariable String username, Model model) {

        User user = getUserOrThrow(username);
        List<WorkoutType> workoutTypes = workoutTypeRepository.findAll();

        model.addAttribute("username", username);
        model.addAttribute("workoutDto", new WorkoutDto());
        model.addAttribute("workoutTypes", workoutTypes);

        return "add-training";
    }


    @PostMapping("/{username}/add-training")
    public ResponseEntity<String> addTraining(
            @PathVariable String username,
            @ModelAttribute @Valid WorkoutDto workoutDto) {

        User user = getUserOrThrow(username);
        WorkoutType workoutType = getWorkoutTypeOrThrow(workoutDto.getWorkoutTypeId());

        Workout workout = new Workout();
        workout.setUser(user);
        workout.setDate(workoutDto.getDate());
        workout.setDurationInMinutes(workoutDto.getDurationInMinutes());
        workout.setWorkoutType(workoutType);

        workoutRepository.save(workout);

        return ResponseEntity.ok("Training added successfully.");
    }

    private User getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private WorkoutType getWorkoutTypeOrThrow(Integer workoutTypeId) {
        return workoutTypeRepository.findById(workoutTypeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout id not found"));}





}
