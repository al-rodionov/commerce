# e-commerce platform
Runtime example requires Docker to build a local image

How to run:
1. In project directory run command to build the application:
./gradlew build

2. Create base application image for Docker:
./gradlew bootBuildImage

3. In ${project.dir}/docker run containers:
docker compose up
