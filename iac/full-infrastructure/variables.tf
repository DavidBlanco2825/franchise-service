# Sensitive and configurable variables
variable "database_name" {
  description = "Name of the PostgreSQL database"
}

variable "database_username" {
  description = "Username for the PostgreSQL database"
}

variable "database_password" {
  description = "Password for the PostgreSQL database"
}

variable "key_pair_name" {
  description = "The name of the key pair for SSH access to the EC2 instance"
}

variable "github_repo_url" {
  description = "URL of the GitHub repository"
}

variable "github_repo_name" {
  description = "Name of the GitHub repository"
}
