# Create a subnet group for RDS
resource "aws_db_subnet_group" "main" {
  name       = "main_subnet_group"
  subnet_ids = [aws_subnet.public_subnet_a.id, aws_subnet.public_subnet_b.id]

  tags = {
    Name = "main_subnet_group"
  }
}

# Provision the RDS PostgreSQL instance
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
