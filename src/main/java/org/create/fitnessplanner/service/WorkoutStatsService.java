package org.create.fitnessplanner.service;

import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.create.fitnessplanner.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkoutStatsService {

    private final WorkoutRepository workoutRepository;

    public WorkoutStatsService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }


    public Map<String, Long> getWorkoutTypeStats(User user) {
        List<Object[]> results = workoutRepository.sumWorkoutDurationByUserId(user.getId());
        return results.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }

    public Map<String, Long> getPastWorkoutTypeStats(User user) {
        List<Object[]> results = workoutRepository.sumPastWorkoutDurationByUserId(user.getId());
        return results.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }

    public Map<String, Long> getPastWorkoutTypeStatsThisMonth(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.toLocalDate().withDayOfMonth(1).atStartOfDay();
        List<Object[]> results = workoutRepository.sumPastWorkoutDurationThisMonth(user.getId(),startOfMonth,now);
        return results.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }


    public Map<String, Long> getFutureWorkoutTypeStats(User user) {
        List<Object[]> results = workoutRepository.sumFutureWorkoutDurationByUserId(user.getId());
        return results.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }

    public Map<String, Long> getFutureWorkoutTypeStatsThisMonth(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);
        List<Object[]> results = workoutRepository.sumFutureWorkoutDurationThisMonth(user.getId(),now,endOfMonth);
        return results.stream()
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }

    public List<Workout> getPastWorkouts(User user) {
        return workoutRepository.findPastWorkoutsWithType(user);
    }

    public List<Workout> getPastWorkoutsThisMonth(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.toLocalDate().withDayOfMonth(1).atStartOfDay();
        return workoutRepository.findWorkoutsWithTypeBetweenDatesByUserId(user.getId(), startOfMonth, now);
    }

    public List<Workout> getFutureWorkouts(User user) {
        return workoutRepository.findFutureWorkoutsWithType(user);
    }

    public List<Workout> getFutureWorkoutsThisMonth(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX);
        return workoutRepository.findWorkoutsWithTypeBetweenDatesByUserId(user.getId(), now, endOfMonth);
    }
}
