                                                                Smart Waste Management & Recycling Tracker

Introduction

The Smart Waste Management & Recycling Tracker is a full-stack, scalable, and secure platform that empowers citizens and municipalities to manage waste more effectively. The project starts with foundational features like secure user authentication, profile management, and file uploads — and extends to AI-based waste categorization, real-time alerts, recycling center mapping, and gamified recycling participation.

This project is built using Spring Boot and MongoDB, with a RESTful architecture and industry-standard practices including JWT security, modular services, and clean, maintainable code.


Core System Features

Phase 1: Authentication & Basic Setup
Secure user registration with details (name, email, phone, etc.)
User login with JWT token generation
MongoDB database integration
JWT-based authentication and protected endpoints
Spring Security configuration
Testing using Postman
GitHub setup with version control

Phase 2: User Profile Management
GET /api/users/me`: Get logged-in user details from JWT
PUT /api/users/update`: Update profile info
Upload and store profile pictures
Store image URLs in MongoDB
Secure file uploads with size limits

Phase 3: AI Waste Categorization 
Upload images of waste to categorize as plastic, organic, metal, etc.
Integrate with TensorFlow or OpenCV
Build and deploy a classification model

Phase 4: Garbage Collection Alerts
User inputs scheduled collection dates
Send SMS/email alerts using Twilio
Admin alert system

Phase 5: Recycling Center Locator
Google Maps API integration
Show nearby centers for each waste type
Directions & navigation support

Phase 6: Gamification & Contribution Tracking
Track and display user contribution metrics
Badges for active users
Monthly leaderboards
Community campaigns


Technologies Used

| Layer		 | 			Tech Used 		|
|-------------------|-------------------------------|
| Backend 		    | Java, Spring Boot 		    |
| Security          | Spring Security, JWT (JJWT) 	|
| Database 		    | MongoDB 				        |
| File Upload 		| Spring MultipartFile 		    |
| Testing 		    | Postman 				        |
| Build Tool 		| Maven 				        |
| AI Model 		    |  TensorFlow 				    |
| Maps 		        | Google Maps API 			    |
| Alerts 		    | Twilio 				        |
| Deployment 		| (Optional) AWS / Heroku 		|



Installation Guide

Prerequisites
- Java 17+
- Maven
- MongoDB installed and running (default port 27017)
- IDE (Spring Tool Suite)
- Postman for API testing
- GitHub CLI or Git

Setup Steps

Clone the Repository
bash
git clone https://github.com/your-username/smart-waste-tracker.git
cd smart-waste-tracker


Project Structure
src/
 └── com.akatsuki.project
      ├── config         		--> Security config classes
      ├── controller     	    --> API controllers
      ├── dto            		--> Request/response DTOs
      ├── filter         		--> JWT filters
      ├── model          	    --> MongoDB data models
      ├── repository     	    --> MongoDB repositories
      ├── service        	    --> Business logic
      └── util           		--> Utility classes (JWT, Token)


License
MIT License

Copyright (c) 2025 Team Akatsuki

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, subject to the following conditions:

- The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
- The Software is provided “as is”, without warranty of any kind, express or implied, including but not limited to warranties of merchantability, fitness for a particular purpose, and non-infringement.

This project is a completely original idea conceptualized, planned, and developed by Team Akatsuki. No external publications, research papers, or other software systems were used or referenced in its design or implementation.

You are free to use or build upon this project, but crediting Team Akatsuki is appreciated.



Support

For any technical support, guidance, or questions related to the Smart Waste Management & Recycling Tracker, please reach out to:

Team : Akatsuki
Email: s-siriki@wiu.edu
GitHub Issues: https://github.com/team-akatsuki/smart-waste-tracker/issues

We are open to contributions and collaboration. If you'd like to enhance or expand the platform with us, feel free to fork, clone, or raise a pull request.


                                                                        ******THANK YOU******




