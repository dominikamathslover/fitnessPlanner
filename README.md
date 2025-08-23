# Fitness planner
An application where users can record their activities,
trigger further actions and plan activities.

---

### Getting Started 
**Home Page:** 
http://localhost:8080/  
**H2 Database Console:** 
http://localhost:8080/h2-console

---

## Features & Endpoints

1. View user's training history and basic statistics GET http://localhost:8080/{username}/workouts 
2. Add workouts for a user  
GET http://localhost:8080/{username}/add-training  
POST http://localhost:8080/{username}/add-training  
   x-www-form-urlencoded  
   Key : workoutTypeId, date, durationInMinutes

 ---------------
    To do

3. Weekly/monthly statistics, charts
4. Training reminder - plan your future training, push notifications
5. List of planned training sessions
6. Save/view as a graphic calendar
7. Add progress measurements - progress entity 
(id, user_id, date, weight, bodyFat, ... )
8. Edit and delete trainings
9. Registration and login
10. Profiles (dev, prod)
11. Database (other than H2)

## Technologies Used 
Java/ Spring Boot / Maven / H2 Database / Hibernate /
Spring Data JPA / Thymeleaf / Flyway
