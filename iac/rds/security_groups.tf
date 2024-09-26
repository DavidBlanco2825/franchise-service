resource "aws_security_group" "rds_sg" {
  name        = "rds_security_group"
  description = "Allow PostgreSQL access from specific IP"

  ingress {
    from_port   = 5432  # PostgreSQL default port
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]  # Only fo testing purposes, but restrict it to your application's CIDR block or IP range for production
  }

  egress {
    from_port   = 0  # Allow all outbound traffic
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
