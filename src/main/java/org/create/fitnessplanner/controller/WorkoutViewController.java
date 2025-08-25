package org.create.fitnessplanner.controller;

import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.create.fitnessplanner.service.EntityResolverService;
import org.create.fitnessplanner.service.WorkoutStatsService;
import org.create.fitnessplanner.service.WorkoutViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/{username}/workouts")
public class WorkoutViewController {

    private final WorkoutStatsService workoutStatsService;
    private final WorkoutViewService workoutViewService;
    private final EntityResolverService entityResolverService;

    public WorkoutViewController(
            WorkoutStatsService workoutStatsService,
            WorkoutViewService workoutViewService,
            EntityResolverService entityResolverService
    ) {
        this.workoutStatsService = workoutStatsService;
        this.workoutViewService = workoutViewService;
        this.entityResolverService = entityResolverService;
    }

    @GetMapping
    public String showUserCompletedWorkouts(@PathVariable String username, Model model) {
        User user = entityResolverService.getUserOrThrow(username);
        List<Workout> pastWorkouts = workoutStatsService.getPastWorkouts(user);

        workoutViewService.addWorkoutStatsToModel(model, user, pastWorkouts);
        return "user-workouts";
    }

    @GetMapping("/chart")
    public String showWorkoutChart(@PathVariable String username, Model model) {
        User user = entityResolverService.getUserOrThrow(username);

        Map<String, Long> stats = workoutStatsService.getWorkoutTypeStats(username);
        model.addAttribute("workoutStats", stats);
        model.addAttribute("username", username);

        return "workout-chart";
    }
}
