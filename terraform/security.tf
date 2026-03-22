resource "aws_security_group" "alb_sg" {
  name        = "${local.name_prefix}-alb-sg"
  description = "Allow HTTP traffic to ALB"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "HTTP from internet"
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${local.name_prefix}-alb-sg"
  }
}

resource "aws_security_group" "ecs_sg" {
  name        = "${local.name_prefix}-ecs-sg"
  description = "Allow app traffic from ALB"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port       = var.app_port
    to_port         = var.app_port
    protocol        = "tcp"
    security_groups = [aws_security_group.alb_sg.id]
    description     = "App traffic from ALB"
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${local.name_prefix}-ecs-sg"
  }
}

resource "aws_security_group" "rds_sg" {
  name        = "${local.name_prefix}-rds-sg"
  description = "Allow MySQL from ECS only"
  vpc_id      = aws_vpc.main.id

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [aws_security_group.ecs_sg.id]
    description     = "MySQL from ECS"
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${local.name_prefix}-rds-sg"
  }
}
