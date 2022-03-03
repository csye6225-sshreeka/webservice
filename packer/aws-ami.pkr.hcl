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
variable "aws_secret_key" {
  type= string
  default = env("MY_SECRET_KEY")
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
    volume_size           = "20"
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
  provisioner "shell" {
    inline = [
      "sleep 30",

		"sudo yum -y install java-11",
	        "sudo yum install maven -y",
               	"sudo yum update -y",
		"sudo yum -y install https://dev.mysql.com/get/mysql80-community-release-el7-5.noarch.rpm",
		"echo 'Install epel'",
		"sudo amazon-linux-extras install epel",
		"echo 'Install community server'",
		"sudo yum -y install mysql-community-server",
		"sudo systemctl enable --now mysqld",
		"systemctl status mysqld",
		"echo 'here'",
		"pass=$(sudo grep 'temporary password' /var/log/mysqld.log | awk {'print $13'})",
		"mysql --connect-expired-password -u root -p$pass -e \"ALTER USER 'root'@'localhost' IDENTIFIED BY 'Shreekar_123';\"",
		"mysql -u root -pShreekar_123 -e \"create database users;\"",
		"pwd",
		"mkdir webapp-target",
		"cd webapp-target",
		"sudo cp /tmp/spring-boot-first-web-application-0.0.1-SNAPSHOT.jar spring-boot-first-web-application-0.0.1-SNAPSHOT.jar",
		"pwd",
		"ls",
		"sleep 2m",
		"sudo cp /tmp/app.service /etc/systemd/system/",
		"sudo systemctl daemon-reload",
		"sudo systemctl enable app.service",
		"sudo systemctl start app.service",
                "sudo systemctl status app.service",

    ]
  }
}
