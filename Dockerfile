# 使用官方Maven镜像作为构建阶段
FROM maven:3.8.6-eclipse-temurin-17 AS build

# 设置工作目录
WORKDIR /app

# 将pom.xml和源代码复制到容器中
COPY pom.xml .
COPY src ./src

# 使用Maven构建项目
RUN mvn clean package -DskipTests

# 使用官方OpenJDK 17镜像作为运行阶段
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 从构建阶段复制jar文件到运行阶段
COPY --from=build /app/target/*.jar app.jar

# 暴露应用程序的端口
EXPOSE 8080

# 运行Spring Boot应用
ENTRYPOINT ["java", "-jar", "app.jar"]