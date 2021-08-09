# Start with a base image containing Java runtime
FROM java:8

# Add Author info
LABEL maintainer="kjc3590@naver.com"

# Add a volume to /tmp
VOLUME /tmp

## Make port 8080 available to the world outside this container
#EXPOSE 8080

# The application's jar file
ARG JAR_FILE=/build/libs/pbzuul.jar

# Add the application's jar to the container
ADD ${JAR_FILE} to-do.jar

# Run the jar file
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev -Duser.timezone=KST","/to-do.jar"]
