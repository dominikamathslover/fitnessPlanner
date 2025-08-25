package org.create.fitnessplanner.controller;

import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.create.fitnessplanner.repository.UserRepository;
import org.create.fitnessplanner.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/{username}/workouts")
public class WorkoutViewController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showUserCompletedWorkouts(@PathVariable String username, Model model) {
        User user = getUserOrThrow(username);
        List<Workout> pastWorkouts = workoutService.getPastWorkouts(user);

        workoutService.addWorkoutStatsToModel(model, user, pastWorkouts);
        return "user-workouts";
    }

    @GetMapping("/chart")
    public String showWorkoutChart(@PathVariable String username, Model model) {
        User user = getUserOrThrow(username);

        Map<String, Long> stats = workoutService.getWorkoutTypeStats(username);
        model.addAttribute("workoutStats", stats);
        model.addAttribute("username", username);

        return "workout-chart";
    }

    private User getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }



}
