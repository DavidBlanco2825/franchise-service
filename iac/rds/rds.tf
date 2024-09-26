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
