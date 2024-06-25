# Course Platform Backend
This repository contains the backend services for a course platform similar to Udemy or Coursera. The project is implemented using a microservices architecture. Each service is designed to handle specific functionality within the platform.

# Services
- api-gateway: Handles routing and load balancing for the microservices.
- course-content-service: Manages the content of the courses including materials and resources.
- course-service: Handles the creation and management of courses.
- enrollment-service: Manages user enrollments in courses.
- notification-service: Sends notifications and updates to users.
- payment-service: Developed in JavaScript using Express and Stripe for processing payments.
- user-service: Manages user information and authentication.

# Technologies
- Java: Primary language for the backend services.
- JavaScript: Used for the payment service.
- Express: Framework for the payment service.
- Stripe: Payment processing for the payment service.
