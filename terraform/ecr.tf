resource "aws_ecr_repository" "app" {
  name                 = "${local.name_prefix}-repo"
  image_tag_mutability = "MUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Name = "${local.name_prefix}-repo"
    Environment = var.environment
    Project     = var.project_name
  }
}
