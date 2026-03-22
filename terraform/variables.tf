variable "aws_region" {
  type    = string
  default = "us-west-2"
}

variable "project_name" {
  type    = string
  default = "spring-blog"
}

variable "environment" {
  type    = string
  default = "dev"
}

variable "vpc_cidr" {
  type    = string
  default = "10.0.0.0/16"
}

variable "public_subnet_1_cidr" {
  type    = string
  default = "10.0.1.0/24"
}

variable "public_subnet_2_cidr" {
  type    = string
  default = "10.0.2.0/24"
}

variable "az_1" {
  type    = string
  default = "us-west-2a"
}

variable "az_2" {
  type    = string
  default = "us-west-2b"
}

variable "app_port" {
  type    = number
  default = 8080
}

variable "container_cpu" {
  type    = number
  default = 512
}

variable "container_memory" {
  type    = number
  default = 1024
}

variable "desired_count" {
  type    = number
  default = 1
}

variable "image_tag" {
  type    = string
  default = "latest"
}

variable "db_name" {
  type    = string
  default = "blogdb"
}

variable "db_username" {
  type    = string
  default = "bloguser"
}

variable "db_password" {
  type      = string
  sensitive = true
}

variable "db_instance_class" {
  type    = string
  default = "db.t3.micro"
}

variable "db_allocated_storage" {
  type    = number
  default = 20
}