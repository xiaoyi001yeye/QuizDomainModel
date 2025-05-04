# Use a Maven image with JDK 17 as the base
FROM maven:3.9-eclipse-temurin-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the root pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .

# Copy the domain module's pom.xml
COPY domain/pom.xml ./domain/

# Download project dependencies. This layer is cached if pom files don't change.
# Using -pl domain -am ensures dependencies for the domain module and its requirements are downloaded.
RUN mvn dependency:go-offline -pl domain -am

# Copy the rest of the project source code
# Copying src separately for potential caching improvements if only src changes
COPY domain/src ./domain/src

# Compile the domain module and run its tests
# Using -pl domain ensures only the domain module is targeted
# Using -am (also-make) ensures any dependent modules are built if necessary
RUN mvn clean test -pl domain -am

# The final image could be leaner, but for just compilation and testing,
# this builder stage is sufficient.
# If you needed a runtime image, you'd add another stage copying artifacts.
