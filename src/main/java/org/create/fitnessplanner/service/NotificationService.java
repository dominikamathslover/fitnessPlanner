package org.create.fitnessplanner.service;

import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;

import java.util.List;

public interface NotificationService {
    void sendTrainingReminder(User user, List<Workout> workouts);
}
