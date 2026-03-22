# 🚀 Spring Boot Blog REST API
A blog backend API built with Spring Boot and deployed to AWS using Docker, ECS Fargate, and Terraform.

---
## 📌 Overview

This project is a backend REST API for a blog platform built with **Spring Boot**.

It provides core features such as:
- Creating and managing blog posts
- Organizing content with categories
- User authentication and authorization

The application follows a typical layered architecture (Controller → Service → Repository) and uses **MySQL** for data persistence.

In addition to backend development, this project demonstrates how to take an application from local development to cloud deployment using modern DevOps practices:

- Containerizing the application with Docker
- Managing infrastructure using Terraform
- Deploying the application to AWS ECS Fargate
- Exposing the service through an Application Load Balancer

---
## 🧱 Architecture
                ┌──────────────────────────┐
                │        Internet          │
                └────────────┬─────────────┘
                             │
                             ▼
                ┌──────────────────────────┐
                │  Application Load Balancer│
                │           (ALB)          │
                └────────────┬─────────────┘
                             │
                             ▼
                ┌──────────────────────────┐
                │   ECS Fargate Service    │
                │   (Spring Boot App)      │
                └────────────┬─────────────┘
                             │
                             ▼
                ┌──────────────────────────┐
                │     Amazon RDS MySQL     │
                └──────────────────────────┘


        ┌────────────────────────────────────────────┐
        │              Supporting Services           │
        │                                            │
        │  • Amazon ECR (Docker Image Registry)      │
        │  • CloudWatch Logs (Logging)               │
        │  • Terraform (Infrastructure as Code)      │
        └────────────────────────────────────────────┘
---
## 🛠️ Tech Stack

- **Backend:** Spring Boot (Java)
- **Database:** MySQL (RDS)
- **Container:** Docker
- **Registry:** Amazon ECR
- **Compute:** ECS Fargate
- **Infrastructure:** Terraform
- **Load Balancer:** ALB
- **Logging:** CloudWatch

## 🗃️ Deployment Steps

### 1. Build the application

```bash
mvn clean package -DskipTests
```

### 2. Provision infrastructure
```bash
cd terraform

terraform init
terraform plan
terraform apply
```

### 3. Build & push Docker image
```bash
docker build -t spring-blog .

docker tag spring-blog:latest <ECR_REPO_URL>:latest

docker push <ECR_REPO_URL>:latest
```

### 4. Access the application
After deployment, access the ALB DNS - 
```bash
http://<alb_dns_name>
```
