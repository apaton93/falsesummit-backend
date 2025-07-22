
FROM eclipse-temurin:24-jre

WORKDIR /app

# Copy the pre-built JAR from the correct nested path
COPY target/false-summit-back-1.0-SNAPSHOT.jar app.jar

# Create a non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown appuser:appuser /app/app.jar
USER appuser

EXPOSE 8080

# Set JVM options (optional - adjust memory settings as needed)
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# Run the application with explicit main class
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -cp app.jar com.company.falsesummit.Main"]