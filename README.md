# Task statement

## Create an application which allows the user to do the following:

- Browse through the paginated list of books.
- Search by the book's title ignoring case.
- Search books by tag's name.
- Order books.
- Signin and signup to application. (Bonus - 'Forgot your password?' feature)

## Use the following entities:

- Book: id title price imageUrl createdAt updatedAt
- Author: id firstName lastName
- Tag: id name  (Example: Action, Adventure, Comic, Detective, Fantasy)
- User: id firstName lastName email password createdAt updatedAt
- Role: id, name
- Authority: id, name
- Order: prepare fields by yourself.

#### Notice

Book has only one author.

## Application requirements

- Spring Boot Framework the latest version.
- Application package root: com.academia
- Gradle latest version.
- JDK version: >=11. Use margin size 120 characters.
- Database: PostgreSQL or MySQL latest version in Docker.
- Hibernate should be used as a JPA implementation for data access.
- Use Spring Data Repositories.
- Use Flyway or Liquibase for Database Migrations.
- Testing: JUnit 5, Hamcrest, Assertj, Mockito, Test containers or Embedded/memory database.
- For keeping images use Amazon S3 bucket or Google bucket.
- Use Swagger / OpenAPI 3 Library for spring-boot to demonstrate endpoints.
- For Security use JWT or OAuth2.0 provider (Google/Facebook/Okta/GitHub) or Keycloak.
- Application roles and authorities: ROLE_ADMIN, ROLE_USER, read, write, edit, delete.
- Use lombok.
- Use Mapstruct for mapping entities to dto and vise versa.
- Code must be uploaded on GitHub. Use Pull request to merge features from master-dev-feature branches. Assign your
  mentor as reviewer.
- Create docker-compose.yml and dockerize your database and springboot service.

## Result

- Demonstrate API on demo.
- Answer theoretical questions.

## Useful links:

- https://spring.io/quickstart
- https://start.spring.io/
- https://www.udemy.com/course/spring-hibernate-tutorial
- https://www.baeldung.com/spring-data-jpa-pagination-sorting
- https://docs.spring.io/spring-boot/docs/2.5.5/reference/html/features.html#features
- https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/html/howto-database-initialization.html

### Swagger

- https://github.com/springdoc/springdoc-openapi
- https://habr.com/ru/post/541592/

### Generate fake data

- https://github.com/DiUS/java-faker
- https://www.baeldung.com/java-faker

### Security

- https://www.youtube.com/watch?v=7uxROJ1nduk
- https://www.youtube.com/watch?v=yRnSUDx3Y8k&t=10s
- https://www.udemy.com/course/oauth2-in-spring-boot-applications
- https://www.keycloak.org/

### AMAZON

- https://aws.amazon.com/
- https://docs.aws.amazon.com/AmazonS3/latest/userguide/GetStartedWithS3.html
- https://www.baeldung.com/aws-s3-java

### Google

- https://console.cloud.google.com/
- https://cloud.google.com/storage/docs/uploading-objects#storage-upload-object-code-sample
- https://www.baeldung.com/java-google-cloud-storage

### Database Migrations tools

- https://flywaydb.org/
- https://www.liquibase.org/

### Test

- https://www.testcontainers.org/
- https://dzone.com/articles/spring-boot-2-with-junit-5-and-mockito-2-for-unit
- https://docs.spring.io/spring-boot/docs/2.5.5/reference/html/features.html#features.testing
- https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/html/boot-features-testing.html

### Other

- https://www.baeldung.com/java-optional
- https://habr.com/ru/company/luxoft/blog/270383/
- https://mapstruct.org/documentation/stable/reference/html/
- https://projectlombok.org/setup/gradle