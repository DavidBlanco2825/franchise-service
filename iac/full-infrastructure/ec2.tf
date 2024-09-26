# Template for the EC2 user data script remains the same
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

# Create the EC2 instance
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
