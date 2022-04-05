packer {
  required_plugins {
    amazon = {
      version = ">= 0.0.1"
      source  = "github.com/hashicorp/amazon"
    }
  }
}
variable "github_repo" {
  default = env("GITHUB_REPO_PATH")
}
variable "aws_access_key" {
  type= string
  default = env("MY_ACCESS_KEY")
}
variable "AWS_CODE_DEPLOY_BUCKET" {
  type= string
  default = env("MY_BUCKET_NAME")
}
variable "aws_secret_key" {
  type= string
  default = env("MY_SECRET_KEY")
}

variable "region" {
  type= string
  default = env("MY_REGION")
}


source "amazon-ebs" "ami-image" {
  ami_name      = "csye6225_ami_img_{{timestamp}}"
  ami_users     = ["771822191110"]
  instance_type = "t2.micro"
  source_ami_filter {
    filters = {
      virtualization-type = "hvm"
      name                = "amzn2-ami-kernel-5.10-hvm*"
      root-device-type    = "ebs"
    }
    owners      = ["amazon"]
    most_recent = true
  }
  launch_block_device_mappings {
    device_name           = "/dev/xvda"
    volume_type           = "gp2"
    volume_size           = "8"
    delete_on_termination = true
  }
  region       = "us-east-1"
  access_key   = "${var.aws_access_key}"
  secret_key   = "${var.aws_secret_key}"
  ssh_username = "ec2-user"
}
build {
  sources = [
    "source.amazon-ebs.ami-image"
  ]
  provisioner "file" {
    destination = "/tmp/app.service"
    source      = "${var.github_repo}/packer/app.service"
  }
  provisioner "file" {
      destination = "/tmp/spring-boot-first-web-application-0.0.1-SNAPSHOT.jar"
      source      = "${var.github_repo}/target/spring-boot-first-web-application-0.0.1-SNAPSHOT.jar"
  }

  provisioner "file" {
      destination = "/home/ec2-user/cloudwatch-config.json"
      source      = "${var.github_repo}/cloudwatch_config.json"
  }


  provisioner "shell" {
    inline = [
      "sleep 30",

		"sudo yum -y install java-11",
    "sudo yum install maven -y",
    "sudo yum update -y",
    "sudo yum install wget -y",
    "sudo pip3 install awscli",
		"echo 'Install epel'",
		"sudo amazon-linux-extras install epel",
    "sudo yum install -y ruby",
    "wget https://aws-codedeploy-us-east-2.s3.us-east-2.amazonaws.com/latest/install",
    "chmod +x ./install",
    "sudo ./install auto",
    "sudo service codedeploy-agent start",
    "sudo service codedeploy-agent status",
    "sudo yum install amazon-cloudwatch-agent -y",
    "sudo systemctl enable amazon-cloudwatch-agent",
    "sudo systemctl start amazon-cloudwatch-agent",
		"pwd",
		"mkdir webapp-target",
		"cd webapp-target",
		"sudo cp /tmp/spring-boot-first-web-application-0.0.1-SNAPSHOT.jar spring-boot-first-web-application-0.0.1-SNAPSHOT.jar",
		"pwd",
		"ls",
    "sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/home/ec2-user/cloudwatch-config.json \
    -s"
    ]
  }
}
