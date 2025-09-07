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
@RequestMapping("/{username}/workouts/planned/")
public class WorkoutPlannedViewController {

    private final WorkoutStatsService workoutStatsService;
    private final WorkoutViewService workoutViewService;
    private final EntityResolverService entityResolverService;

    public WorkoutPlannedViewController(
            WorkoutStatsService workoutStatsService,
            WorkoutViewService workoutViewService,
            EntityResolverService entityResolverService
    ) {
        this.workoutStatsService = workoutStatsService;
        this.workoutViewService = workoutViewService;
        this.entityResolverService = entityResolverService;
    }

    @GetMapping("/all")
    public String showUserCompletedWorkouts(@PathVariable String username, Model model) {
        User user = entityResolverService.getUserOrThrow(username);
        List<Workout> futureWorkouts = workoutStatsService.getFutureWorkouts(user);
        Map<String, Long> stats = workoutStatsService.getFutureWorkoutTypeStats(user);

        workoutViewService.addWorkoutStatsToModel(model, user, futureWorkouts,stats);
        return "user/user-workouts";
    }

    @GetMapping("/month")
    public String showUserCompletedWorkoutsThisMonth(@PathVariable String username, Model model) {
        User user = entityResolverService.getUserOrThrow(username);
        List<Workout> futureWorkouts = workoutStatsService.getFutureWorkoutsThisMonth(user);
        Map<String, Long> stats = workoutStatsService.getFutureWorkoutTypeStatsThisMonth(user);

        workoutViewService.addWorkoutStatsToModel(model, user, futureWorkouts,stats);
        return "user/user-workouts";
    }

}

