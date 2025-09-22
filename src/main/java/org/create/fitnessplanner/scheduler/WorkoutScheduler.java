package org.create.fitnessplanner.scheduler;

import org.create.fitnessplanner.model.Workout;
import org.create.fitnessplanner.repository.UserRepository;
import org.create.fitnessplanner.service.NotificationService;
import org.create.fitnessplanner.service.WorkoutService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class WorkoutScheduler {

    private final WorkoutService workoutService;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public WorkoutScheduler(WorkoutService workoutService, UserRepository userRepository, NotificationService notificationService) {
        this.workoutService = workoutService;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "${cron.expression}")
    public void notifyUpcomingTrainings() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfNextWeek = today.plusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDateTime endOfNextWeek = startOfNextWeek.plusDays(6);


        userRepository.findAll().forEach(user -> {
            List<Workout> userWorkouts = workoutService.getUserWorkoutsBetween(user, startOfNextWeek, endOfNextWeek);
            notificationService.sendTrainingReminder(user, userWorkouts);
        });


        System.out.println("Training notifications for the upcoming week have been successfully dispatched.");
    }

}
