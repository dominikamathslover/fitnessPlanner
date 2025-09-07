package org.create.fitnessplanner.service;
import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
public class WorkoutViewService {

    public void addWorkoutStatsToModel(Model model, User user, List<Workout> workouts,  Map<String, Long> stats) {
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
        model.addAttribute("workoutStats", stats);
    }
}

