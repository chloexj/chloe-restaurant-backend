# Restaurant Management System-Backend

A **Spring Boot**-based backend for a restaurant management system. This monolithic application provides RESTful APIs for **employee management**, **menu management**, and other administrative functions. It supports **image uploads to AWS S3** and uses **JWT authentication** for secure access.

## 🛠 Technology Stack

- **Backend:** Spring Boot, MyBatis, MySQL, Redis
- **Authentication:** JWT
- **Storage:** AWS S3 for image uploads
- **Frontend (Admin Panel):** Vue.js
- **Frontend (User Ordering Interface):** React.js (planned, not implemented yet)
- **WebSockets:** Real-time notifications for order updates
- **Caching:** Redis caching for optimized performance

## 🚀 Features

- Employee management (CRUD operations for employee accounts)
- Menu management (CRUD operations for dishes and categories)
- Secure authentication with JWT
- Image upload and storage in AWS S3
- RESTful API endpoints for seamless communication
- Real-time order notifications using WebSockets
- Redis caching to improve performance
- AOP (Aspect-Oriented Programming) for logging, security, and transaction management

## ⚙️ Configure the application

Update `application.yml` with your **MySQL**, **Redis**, and **AWS S3** credentials.

Also, create and configure `application-dev.yml` with development-specific settings. **This file is not included in version control and needs to be manually filled.**



## 📌 Frontend Projects

### Admin Panel (Vue.js)

The admin panel is built with Vue.js and provides an intuitive interface for managing employees and menu items. **Repository:** [Frontend-Admin](https://github.com/chloexj/chloe-restaurant-admin-frontend)

### User Ordering System (React.js)

The customer ordering interface will be built using React.js, allowing users to browse the menu and place orders online. *(Development not started yet)*



  ![Restaurant Dashboard](https://s3.us-east-1.amazonaws.com/com.chloe.testaws/project-restaurant-pic3.png)



![Setmeal](https://s3.us-east-1.amazonaws.com/com.chloe.testaws/project-restaurant-pic2.png)
