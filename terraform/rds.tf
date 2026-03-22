resource "aws_db_subnet_group" "main" {
  name = "${local.name_prefix}-db-subnet-group"
  subnet_ids = [
    aws_subnet.public_1.id,
    aws_subnet.public_2.id
  ]

  tags = {
    Name = "${local.name_prefix}-db-subnet-group"
  }
}

resource "aws_db_instance" "mysql" {
  identifier             = "${local.name_prefix}-mysql"
  allocated_storage      = var.db_allocated_storage
  engine                 = "mysql"
  engine_version         = "8.0"
  instance_class         = var.db_instance_class
  db_name                = var.db_name
  username               = var.db_username
  password               = var.db_password
  db_subnet_group_name   = aws_db_subnet_group.main.name
  vpc_security_group_ids = [aws_security_group.rds_sg.id]

  skip_final_snapshot = true
  publicly_accessible = false
  multi_az            = false

  tags = {
    Name = "${local.name_prefix}-mysql"
  }
}
