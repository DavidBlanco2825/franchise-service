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
