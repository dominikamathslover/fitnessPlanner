package org.create.fitnessplanner.service;

import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.create.fitnessplanner.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkoutStatsService {

    private final WorkoutRepository workoutRepository;

    public WorkoutStatsService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    // Returns a map of workout type names and their count for a given user
    public Map<String, Long> getWorkoutTypeStats(String username) {
        List<Object[]> results = workoutRepository.countWorkoutTypesByUsername(username);
        return results.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }

    // Returns a list of past workouts (before now) for a given user
    public List<Workout> getPastWorkouts(User user) {
        LocalDateTime now = LocalDateTime.now();
        return workoutRepository.findPastWorkoutsWithType(user, now);
    }

    // Returns a map of total workout duration per weekday over the last 30 days
    public Map<String, Integer> getWeeklyTimeStats(User user) {
        LocalDateTime start = LocalDateTime.now().minusDays(30);
        List<Workout> workouts = workoutRepository.findByUserAndDateAfter(user, start);

        Map<String, Integer> result = new LinkedHashMap<>(Map.of(
                "Mon", 0, "Tue", 0, "Wed", 0, "Thu", 0, "Fri", 0, "Sat", 0, "Sun", 0
        ));

        for (Workout workout : workouts) {
            String day = switch (workout.getDate().getDayOfWeek()) {
                case MONDAY -> "Mon";
                case TUESDAY -> "Tue";
                case WEDNESDAY -> "Wed";
                case THURSDAY -> "Thu";
                case FRIDAY -> "Fri";
                case SATURDAY -> "Sat";
                case SUNDAY -> "Sun";
            };
            result.put(day, result.get(day) + workout.getDurationInMinutes());
        }

        return result;
    }
}
