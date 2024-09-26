# Franchise Service

**Author:** [David Fernando Blanco](https://github.com/DavidBlanco2825)

## Overview

The **Franchise Service** is a RESTful API built using **Spring WebFlux**, designed to manage franchises, branches, and products in a reactive and scalable manner. The service follows non-blocking IO operations, enabling efficient handling of concurrent requests with low resource consumption. This project integrates with PostgreSQL for persistent data storage and leverages Docker for easy deployment.

## Features

- **Franchise Management**:
    - Add a new franchise.
    - Update the name of an existing franchise.

- **Branch Management**:
    - Add a new branch to an existing franchise.
    - Update the name of a branch.

- **Product Management**:
    - Add a new product to a branch.
    - Update the name of a product.
    - Modify the stock of a product.
    - Remove a product from a branch.
    - Display the product with the highest stock in each branch for a specific franchise.

### API Documentation for Franchise Management

#### Requests

##### 1. Add a new franchise

- **Type:** POST
- **URL:** `/franchises`
- **JSON:**

    - Request

      ```json
      {
          "name": "New Franchise"
      }
      ```

    - Response:

      ```json
      {
          "id": 1,
          "name": "New Franchise"
      }
      ```

##### 2. Add a new branch to an existing franchise

- **Type:** POST
- **URL:** `/branches/franchise/{franchiseId}`
- **JSON:**
    - Request

      ```json
      {
          "name": "First Branch"
      }
      ```

    - Response:

      ```json
      {
          "id": 1,
          "name": "First Branch",
          "franchiseId": 1
      }
      ```

##### 3. Add a new product to the product list of a branch

- **Type:** POST
- **URL:** `/products/branch/{branchId}`
- **JSON:**

    - Request

      ```json
      {
          "name": "First product",
          "stock": 15
      }
      ```

    - Response:

      ```json
      {
          "id": 1,
          "name": "First product",
          "stock": 15,
          "branchId": 1
      }
      ```

##### 4. Delete a specific product from a branch

- **Type:** DELETE
- **URL:** `/products/{productId}`

##### 5. Update the stock of a specific product

- **Type:** PUT
- **URL:** `/products/{productId}/stock`
- **JSON:**

    - Request

      ```json
      {
          "stock": 100
      }
      ```

    - Response:

      ```json
      {
          "id": 1,
          "name": "First product",
          "stock": 100,
          "branchId": 1
      }
      ```

##### 6. Return the product with the highest stock in a specific branch

- **Type:** GET
- **URL:** `/products/highestStock/franchise/{franchiseId}`

    - Response:

      ```json
      [
          {
              "product": {
                  "id": 1,
                  "name": "First product",
                  "stock": 100,
                  "branchId": 1
              },
              "branchName": "First Branch"
          },
          {
              "product": {
                  "id": 2,
                  "name": "First product",
                  "stock": 15,
                  "branchId": 2
              },
              "branchName": "First Branch"
          },
          {
              "product": {
                  "id": 3,
                  "name": "First product",
                  "stock": 15,
                  "branchId": 3
              },
              "branchName": "First Branch"
          }
      ]
      ```

##### 7. Change the name of a franchise

- **Type:** PUT
- **URL:** `/franchises/{franchiseId}/name`
- **JSON:**

    - Request

      ```json
      {
          "name": "New franchise name"
      }
      ```

    - Response:

      ```json
      {
          "id": 1,
          "name": "New franchise name"
      }
      ```

##### 8. Change the name of a specific branch

- **Type:** PUT
- **URL:** `/branches/{branchId}/name`
- **JSON:**

    - Request

      ```json
      {
          "name": "New Branch name"
      }
      ```

    - Response:

      ```json
      {
          "id": 1,
          "name": "New Branch name",
          "franchiseId": 1
      }
      ```

##### 9. Change the name of a specific product

- **Type:** PUT
- **URL:** `/products/{productId}/name`
- **JSON:**

    - Request

      ```json
      {
          "name": "New product name"
      }
      ```

    - Response:

      ```json
      {
          "id": 1,
          "name": "New product name",
          "stock": 100,
          "branchId": 1
      }
      ```

#### Notes

- Make sure to replace `{franchiseId}`, `{branchId}`, and `{productId}` with the corresponding values when making requests.
- For requests that require a JSON body, ensure you send the correct content to avoid validation errors.

## Running the Franchise Service Locally with Docker

This section explains how to run the **Franchise Service** locally using Docker. The Docker Compose file provisions both the PostgreSQL database and the Spring Boot application in separate containers, allowing for easy setup and deployment on your local machine.

### Requirements

To run this application, ensure the following dependencies are installed:

- **Docker Engine**: 20.10.7 or higher
    - Install Docker Engine from the [Docker website](https://docs.docker.com/engine/install/).
- **Docker Compose**: 1.29.2 or higher
    - Install Docker Compose from the [Docker website](https://docs.docker.com/compose/install/).
- **Java 17**: Ensure it's installed for running the application.
- **Maven 3.6** or higher: Used for building the project and managing dependencies.

### Clone the Repository

```bash
git clone https://github.com/DavidBlanco2825/franchise-service.git
cd franchise-service
```

### Steps to Run Locally

1. **Build and Start the Containers**:
   To start the application locally with Docker, run the following command:

   ```bash
   docker-compose -f docker-compose.local.yml up --build -d
   ```

   This command will:
    - Build the Spring Boot application using the `Dockerfile`.
    - Start the PostgreSQL database.
    - Start the Spring Boot application, connecting it to the PostgreSQL database.

2. **Accessing the Application**:
   Once the containers are up and running, you can access the following services:
    - **Swagger API Documentation**:  
      Visit [http://localhost:8080/webjars/swagger-ui/index.html](http://localhost:8080/webjars/swagger-ui/index.html) to explore and test the API endpoints.
    - **PostgreSQL Database**:  
      The PostgreSQL database is accessible locally on port `5433` (mapped from the container’s default `5432` port).

### Testing the API

A **Postman collection** is included for testing the API endpoints. It contains pre-configured requests for all available endpoints.

#### Steps to Test

1. **Install Postman**:
    - Download and install [Postman](https://www.postman.com/downloads/).

2. **Import the Postman Collection**:
    - Open Postman and click "Import."
    - Select the `Franchise.postman_collection.json` file located in the project root directory.
    - Click "Open" to import the collection.

3. **Run Requests**:
    - Select a request and hit "Send" to test the API.

### Docker Compose Overview

The `docker-compose.yml` file defines two main services:

1. **PostgreSQL Database (`franchise-db`)**:
    - This service uses the official `postgres:13` image to create a PostgreSQL database instance for the application.
    - The database is configured with the following credentials:
        - **Database Name**: `franchise_db`
        - **Username**: `franchise_user`
        - **Password**: `franchise_password`
    - The data for the database is persisted using a named volume (`franchise_postgres_data`), ensuring data is retained even when the container is restarted.
    - The service is exposed on port `5433` on your local machine and mapped to PostgreSQL’s default port `5432` inside the container.

2. **Franchise Service (`franchise-service`)**:
    - This service builds and runs the Spring Boot application using the Dockerfile provided in the root directory.
    - The application is configured to connect to the PostgreSQL database using R2DBC (Reactive Relational Database Connectivity).
    - The service waits until the PostgreSQL database is ready before starting the application (using the `while ! nc` command in the `Dockerfile`).
    - The application is exposed on port `8080`, allowing access to the API.

### Dockerfile Overview

The `Dockerfile` is a multi-stage build that does the following:
1. **Build Stage**:
    - Uses the official Maven image to build the application from the source code.
    - Packages the application as a `.jar` file.

2. **Run Stage**:
    - Uses a lightweight **Eclipse Temurin** JDK image to run the Spring Boot application.
    - Sets up the necessary environment variables for R2DBC to connect to the PostgreSQL instance.
    - Exposes port `8080` for the application.
    - Ensures the application only starts after the PostgreSQL instance is ready.

### Stopping the Application

To stop the running containers, use the following command:

```bash
docker-compose -f docker-compose.local.yml down
```

This will stop and remove the containers but keep the database data intact in the volume.

### Removing All Resources

If you wish to stop the containers and also remove associated resources such as volumes, networks, and images created by Docker Compose, run:

```bash
docker-compose -f docker-compose.local.yml down --volumes --rmi all
```

This command will clean up all resources, including the persisted PostgreSQL data.

### Summary

By using Docker and Docker Compose, you can quickly and easily run the **Franchise Service** locally without needing to manually configure the database or environment. The application is fully containerized, making it simple to test, develop, and deploy in any environment.

## Infrastructure as Code (IaC)

The **Franchise Service** uses Terraform to define and provision the infrastructure required for the application. The infrastructure code is organized into two main components within the `iac/` directory.

### `rds/` Directory

The `rds/` directory contains the Terraform configuration required to provision an **Amazon RDS PostgreSQL instance** for the **Franchise Service**. This RDS instance will be used as the application's database to persist franchise, branch, and product data.

#### Key Files

1. **`main.tf`**:
    - Defines the **RDS PostgreSQL instance** and associated **security group**.
    - The RDS instance is publicly accessible (for testing purposes), but in a production environment, you should restrict access using a more secure CIDR block or IP range.

   ```hcl
   resource "aws_db_instance" "postgres" {
     allocated_storage    = 5
     storage_type         = "gp2"
     engine               = "postgres"
     engine_version       = "13.13"
     instance_class       = "db.t3.micro"
     db_name              = "franchises_db"
     identifier           = "franchises-db"
     username             = var.db_username
     password             = var.db_password
     skip_final_snapshot  = true
     publicly_accessible  = true

     vpc_security_group_ids = [aws_security_group.rds_sg.id]
   }
   ```

2. **`variables.tf`**:
    - Contains the variables for the **RDS username** and **password**.
    - The actual values for these variables should be defined in the `terraform.tfvars` file (not included in the repository for security reasons).

   ```hcl
   variable "db_username" {
     description = "The username for the RDS instance"
     type        = string
     sensitive   = true
   }

   variable "db_password" {
     description = "The password for the RDS instance"
     type        = string
     sensitive   = true
   }
   ```

3. **`outputs.tf`**:
    - Outputs key information about the RDS instance, including the **RDS endpoint** and **port**, which are required for connecting the application to the database.

   ```hcl
   output "rds_endpoint" {
     description = "RDS Endpoint"
     value       = aws_db_instance.postgres.endpoint
   }

   output "rds_port" {
     description = "RDS Port"
     value       = aws_db_instance.postgres.port
   }
   ```

4. **`provider.tf`**:
    - Defines the AWS provider and region used for provisioning the RDS instance.

   ```hcl
   provider "aws" {
     profile = "default"
     region  = "us-east-1"
   }
   ```

5. **`security_group.tf`** (inside `main.tf`):
    - Defines a **security group** that allows incoming traffic on port 5432 (the default PostgreSQL port).
    - **Note:** For testing purposes, the security group allows access from all IP addresses (`0.0.0.0/0`). In a production environment, restrict this to your application's specific IP or CIDR block.

   ```hcl
   resource "aws_security_group" "rds_sg" {
     name        = "rds_security_group"
     description = "Allow PostgreSQL access from specific IP"

     ingress {
       from_port   = 5432
       to_port     = 5432
       protocol    = "tcp"
       cidr_blocks = ["0.0.0.0/0"]  # Restrict in production
     }

     egress {
       from_port   = 0
       to_port     = 0
       protocol    = "-1"
       cidr_blocks = ["0.0.0.0/0"]
     }
   }
   ```

#### `terraform.tfvars`

The `terraform.tfvars` file should contain the actual values for sensitive variables like the **RDS username** and **password**. For security reasons, this file is left out of the repository and should be managed separately. An example of what this file might look like:

```hcl
db_username = "your_db_username"
db_password = "your_secure_db_password"
```

**Note:** Make sure to keep the `terraform.tfvars` file outside of version control to avoid exposing sensitive credentials.

#### Steps to Deploy the RDS PostgreSQL Instance

1. Navigate to the `iac/rds/` directory.
2. Run the following commands to initialize and apply the Terraform configuration:

   ```bash
   terraform init
   terraform validate
   terraform plan
   terraform apply
   ```

3. Terraform will provision the RDS instance and display the **RDS endpoint** and **port** in the output, which can be used by the application to connect to the PostgreSQL database.

### `full-infrastructure/` Directory

The `full-infrastructure/` directory contains the Terraform configuration to provision the **complete infrastructure** for running the **Franchise Service**. This includes the VPC, subnets, security groups, EC2 instance for hosting the application, and the RDS PostgreSQL instance for persistent storage.

#### Key Files

1. **`main.tf`**:
    - Defines the resources required for the full infrastructure, including:
        - **EC2 instance**: Used to run the application, with a user data script that installs Docker, clones the GitHub repository, and runs the application inside a Docker container.
        - **RDS PostgreSQL instance**: Provides persistent data storage for the application.
        - **Security groups**: Protect the EC2 instance and RDS by allowing only the necessary traffic (SSH and HTTP for EC2, PostgreSQL for RDS).
        - **VPC and subnets**: A Virtual Private Cloud with public subnets across multiple availability zones to host the EC2 instance and RDS.

   ```hcl
   # Template for the EC2 user data script
   data "template_file" "user_data" {
     template = file("${path.module}/user_data.tpl")

     vars = {
       rds_endpoint      = aws_db_instance.postgres.address
       database_name     = var.database_name
       database_username = var.database_username
       database_password = var.database_password
       github_repo_url   = var.github_repo_url
       github_repo_name  = var.github_repo_name
     }
   }

   # EC2 instance configuration
   resource "aws_instance" "app_server" {
     ami                         = data.aws_ami.amazon_linux.id
     instance_type               = "t2.micro"
     subnet_id                   = aws_subnet.public_subnet_a.id
     vpc_security_group_ids      = [aws_security_group.ec2_sg.id]
     associate_public_ip_address = true
     key_name                    = var.key_pair_name

     user_data = data.template_file.user_data.rendered

     tags = {
       Name = "app_server"
     }
   }

   # RDS PostgreSQL instance configuration
   resource "aws_db_instance" "postgres" {
     allocated_storage      = 5
     engine                 = "postgres"
     engine_version         = "13.13"
     instance_class         = "db.t3.micro"
     db_name                = var.database_name
     username               = var.database_username
     password               = var.database_password
     publicly_accessible    = false
     vpc_security_group_ids = [aws_security_group.rds_sg.id]
     db_subnet_group_name   = aws_db_subnet_group.main.name
     skip_final_snapshot    = true

     tags = {
       Name = "postgresql-database"
     }
   }
   ```

2. **`outputs.tf`**:
    - Outputs key information about the provisioned resources, such as the **public IP of the EC2 instance** and the **RDS endpoint**.

   ```hcl
   output "ec2_public_ip" {
     description = "Public IP of the EC2 instance"
     value       = aws_instance.app_server.public_ip
   }

   output "rds_endpoint" {
     description = "Endpoint of the RDS PostgreSQL instance"
     value       = aws_db_instance.postgres.endpoint
   }
   ```

3. **`provider.tf`**:
    - Configures the AWS provider and specifies the region for deploying the resources.

   ```hcl
   provider "aws" {
     profile = "default"
     region  = "us-east-1"
   }

   # Fetch the latest Amazon Linux 2 AMI for the EC2 instance
   data "aws_ami" "amazon_linux" {
     most_recent = true

     filter {
       name   = "name"
       values = ["amzn2-ami-hvm-*-x86_64-gp2"]
     }

     filter {
       name   = "owner-alias"
       values = ["amazon"]
     }

     owners = ["amazon"]
   }
   ```

4. **`security_groups.tf`** (within `main.tf`):
    - Defines two security groups:
        - **EC2 security group**: Allows inbound traffic for SSH (port 22) and the application (port 8080).
        - **RDS security group**: Allows PostgreSQL traffic (port 5432) from the EC2 instance.

   ```hcl
   # Security group for the EC2 instance
   resource "aws_security_group" "ec2_sg" {
     name        = "ec2_security_group"
     description = "Allow SSH and HTTP inbound traffic"
     vpc_id      = aws_vpc.main.id

     ingress {
       description = "SSH"
       from_port   = 22
       to_port     = 22
       protocol    = "tcp"
       cidr_blocks = ["0.0.0.0/0"]
     }

     ingress {
       description = "Application Port"
       from_port   = 8080
       to_port     = 8080
       protocol    = "tcp"
       cidr_blocks = ["0.0.0.0/0"]
     }

     egress {
       description = "All outbound"
       from_port   = 0
       to_port     = 0
       protocol    = "-1"
       cidr_blocks = ["0.0.0.0/0"]
     }

     tags = {
       Name = "ec2_security_group"
     }
   }

   # Security group for the RDS instance
   resource "aws_security_group" "rds_sg" {
     name        = "rds_security_group"
     description = "Allow PostgreSQL traffic from EC2"
     vpc_id      = aws_vpc.main.id

     ingress {
       description     = "PostgreSQL"
       from_port       = 5432
       to_port         = 5432
       protocol        = "tcp"
       security_groups = [aws_security_group.ec2_sg.id]
     }

     egress {
       description = "All outbound"
       from_port   = 0
       to_port     = 0
       protocol    = "-1"
       cidr_blocks = ["0.0.0.0/0"]
     }

     tags = {
       Name = "rds_security_group"
     }
   }
   ```

5. **`vpc.tf`**:
    - Defines the **VPC**, **public subnets**, and **Internet Gateway** for the project. The public subnets are spread across two availability zones for high availability.

   ```hcl
   # Create a VPC
   resource "aws_vpc" "main" {
     cidr_block = "10.0.0.0/16"

     tags = {
       Name = "main_vpc"
     }
   }

   # Create an Internet Gateway
   resource "aws_internet_gateway" "igw" {
     vpc_id = aws_vpc.main.id

     tags = {
       Name = "main_igw"
     }
   }

   # Create public subnets
   resource "aws_subnet" "public_subnet_a" {
     vpc_id            = aws_vpc.main.id
     cidr_block        = "10.0.1.0/24"
     availability_zone = "us-east-1a"

     tags = {
       Name = "public_subnet_a"
     }
   }

   resource "aws_subnet" "public_subnet_b" {
     vpc_id            = aws_vpc.main.id
     cidr_block        = "10.0.2.0/24"
     availability_zone = "us-east-1b"

     tags = {
       Name = "public_subnet_b"
     }
   }

   # Create a route table for the public subnets
   resource "aws_route_table" "public_rt" {
     vpc_id = aws_vpc.main.id

     route {
       cidr_block = "0.0.0.0/0"
       gateway_id = aws_internet_gateway.igw.id
     }

     tags = {
       Name = "public_rt"
     }
   }

   # Associate the route table with the public subnets
   resource "aws_route_table_association" "public_assoc_a" {
     subnet_id      = aws_subnet.public_subnet_a.id
     route_table_id = aws_route_table.public_rt.id
   }

   resource "aws_route_table_association" "public_assoc_b" {
     subnet_id      = aws_subnet.public_subnet_b.id
     route_table_id = aws_route_table.public_rt.id
   }
   ```

6. **`user_data.tpl`**:
    - This is the EC2 instance’s **user data script**, which installs Docker, clones the application’s GitHub repository, and runs the application in a Docker container using `docker-compose`.

```bash
   #!/bin/bash
   # Update all packages
   yum update -y

   # Install Docker
   amazon-linux-extras install docker -y
   service docker start
   usermod -a -G docker ec2-user

   # Install Git
   yum install git -y

   # Clone the GitHub repository
   cd /home/ec2-user
   git clone ${github_repo_url}
   cd ${github_repo_name}

   # Create the .env file with environment variables
   cat <<EOT >> .env
   RDS_ENDPOINT=${rds_endpoint}
    DATABASE_NAME=${database_name}
    DATABASE_USERNAME=${database_username}
    DATABASE_PASSWORD=${database_password}
    EOT

    # Build and run the Docker container
    docker build -f ec2.Dockerfile -t franchise-service-ec2 .
    docker-compose -f docker-compose.ec2.yml up --build -d
```
 
#### `terraform.tfvars`

The `terraform.tfvars` file should contain the actual values for sensitive variables like the **RDS username** and **password**. For security reasons, this file is left out of the repository and should be managed separately. An example of what this file might look like:

```hcl
db_username = "your_db_username"
db_password = "your_secure_db_password"
```

**Note:** Make sure to keep the `terraform.tfvars` file outside of version control to avoid exposing sensitive credentials.

#### Steps to Deploy the RDS PostgreSQL Instance

1. Navigate to the `iac/full-infrastructure/` directory.
2. Run the following commands to initialize and apply the Terraform configuration:

   ```bash
   terraform init
   terraform validate
   terraform plan
   terraform apply
   ```