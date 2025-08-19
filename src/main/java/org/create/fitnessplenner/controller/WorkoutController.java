package org.create.fitnessplenner.controller;

import org.create.fitnessplenner.model.User;
import org.create.fitnessplenner.model.Workout;
import org.create.fitnessplenner.repository.UserRepository;
import org.create.fitnessplenner.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class WorkoutController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutRepository workoutRepository;

    @Transactional(readOnly = true)
    @GetMapping("/{username}/workouts")
    public String showUserWorkouts(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<Workout> workouts = workoutRepository.findWorkoutsWithTypeByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("workouts", workouts);

        int totalDuration = user.getWorkouts().stream()
                .mapToInt(Workout::getDurationInMinutes)
                .sum();
        int sessionCount = user.getWorkouts().size();
        int averageDuration = sessionCount > 0 ? (int) totalDuration / sessionCount : 0;

        model.addAttribute("totalDuration", totalDuration);
        model.addAttribute("sessionCount", sessionCount);
        model.addAttribute("averageDuration", averageDuration);

        return "user-workouts";
    }

}
