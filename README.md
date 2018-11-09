# Build
mvn clean package

# Artifacts
- core/target/user-post-core-1.0.jar - Core functionality library for using in another java code
- cli/target/user-post-cli-1.0.jar - Command line application
- web/target/user-post-web-1.0.jar - Web application

# Run from CLI
java -jar cli/target/user-post-cli-1.0.jar 

# Run and call Web application
java -jar web/target/user-post-web-1.0.jar
and call http://localhost:8080/users/{userId} 

