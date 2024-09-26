output "rds_endpoint" {
  description = "RDS Endpoint"
  value       = aws_db_instance.postgres.endpoint
}

output "rds_port" {
  description = "RDS Port"
  value = aws_db_instance.postgres.port
}