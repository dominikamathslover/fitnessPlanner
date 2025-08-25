package org.create.fitnessplanner.controller;

import jakarta.validation.Valid;
import org.create.fitnessplanner.dto.WorkoutDto;
import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.WorkoutType;
import org.create.fitnessplanner.service.EntityResolverService;
import org.create.fitnessplanner.service.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/{username}/add-training")
public class WorkoutFormController {

    private final WorkoutService workoutService;
    private final EntityResolverService entityResolverService;

    public WorkoutFormController(WorkoutService workoutService, EntityResolverService entityResolverService) {
        this.workoutService = workoutService;
        this.entityResolverService = entityResolverService;
    }

    @GetMapping
    public String showAddTrainingForm(@PathVariable String username, Model model) {
        User user = entityResolverService.getUserOrThrow(username);
        List<WorkoutType> workoutTypes = entityResolverService.getAllWorkoutTypes();

        model.addAttribute("username", username);
        model.addAttribute("workoutDto", new WorkoutDto());
        model.addAttribute("workoutTypes", workoutTypes);

        return "add-training";
    }

    @PostMapping
    public ResponseEntity<String> addTraining(@PathVariable String username,
                                              @ModelAttribute @Valid WorkoutDto workoutDto) {
        if (workoutDto.getDate().isBefore(java.time.LocalDateTime.now())) {
            workoutService.addCompletedWorkout(username, workoutDto);
            return ResponseEntity.ok("You have saved the completed training.");
        } else {
            workoutService.scheduleWorkout(username, workoutDto);
            return ResponseEntity.ok("You have scheduled the future training.");
        }
    }
}
