package org.create.fitnessplanner.service;

import org.create.fitnessplanner.model.User;
import org.create.fitnessplanner.model.Workout;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailNotificationService implements NotificationService {


    private final JavaMailSender mailSender;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendTrainingReminder(User user, List<Workout> workouts) {
        String to = user.getEmail();
        String subject = "Your workouts for next week";
        String body = buildEmailBody(user, workouts);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("fitnessplanner2025app@gmail.com");

        mailSender.send(message);
    }

    private String buildEmailBody(User user, List<Workout> workouts) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append("Hi ").append(user.getUsername()).append(",\n\n");
        sb.append("Here are your workouts for next week:\n");

        for (Workout workout : workouts) {
            String formattedDate = workout.getDate().format(formatter);
            String workoutName = workout.getWorkoutType().getName();
            sb.append("- ").append(formattedDate)
                    .append(" • ").append(workoutName)
                    .append("\n");
        }

        sb.append("\nStay strong!\nYour Training App 💪");
        return sb.toString();
    }

}
