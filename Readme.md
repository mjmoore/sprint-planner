# Pinguin Sprint Suggester

## Setup

### Requirements
  * `JAVA_HOME` is set to Java 13.
  * Gradle 6+ (included).

### Running

The application can be started with either gradle or docker.

When started, a REST API will be exposed on http://localhost:8080

#### Gradle
    
    From the root directory, run:
     
     `pinguin-app$ ./gradlew bootRun`

#### Docker
 
    From the root directory, run: 
    
        pinguin-app$ docker build --tag app .
        pinguin-app$ docker run --interactive --tty --rm --publish 8080:8080 app
     

### Testing

Tests can be run using gradle. From the root directory, run: 

`pinguin-app$ ./gradlew test`

Tests are explicitly run as part of a build.

### Initial data

Some sample data will be added to the system on boot. Refer to 
[data.sql](src/main/resources/data.sql) for more details.

Includes:
 * 5 users with generated names.
 * 5 bugs in various states.
 * 5 stories in New and Completed status.
 * 20 estimated stories, ready for a new sprint. 

## Design

### Rest API

There are a few resources exposed via REST:

#### [/stories](http://localhost:8080/stories)


 * `GET`: `/stories`
 * `POST`: `/stories`, see [StoryDto](src/main/java/io/mjmoore/dto/StoryDto.java)
 * `POST`: `/stories/{storyId}/complete`
 * `POST`: `stories/{storyId}/estimate/{estimation}`
 * `POST`: `/stories/{storyId}/assign/{userId}`
 * `PATCH`: `/stories/{storyId}`, see [StoryDto](src/main/java/io/mjmoore/dto/StoryDto.java)

#### [/bugs](http://localhost:8080/bugs)

 * `GET`: `/bugs`
 * `POST`: `/bugs`, see [BugDto](src/main/java/io/mjmoore/dto/BugDto.java)
 * `POST`: `/bugs/{bugId}/verify`
 * `POST`: `/bugs/{bugId}/resolve`
 * `POST`: `/bugs/{bugId}/assign/{userId}`
 * `PATCH`: `/bugs/{bugId}`, see [BugDto](src/main/java/io/mjmoore/dto/BugDto.java)
 
#### [/users](http://localhost:8080/users)

 * `GET`: `/users`
 * `POST`: `/users`, see [UserDto](src/main/java/io/mjmoore/dto/UserDto.java)
 * `PATCH`: `/users/{bugId}`, see [UserDto](src/main/java/io/mjmoore/dto/UserDto.java)
 
#### [~~/sprint~~](http://localhost:8080/sprint)

 * `GET`: `/sprint`
   
   ~~This resource will naïvely return a list of stories which 
   can be assigned to existing users in the system.~~
   
   Deprecated, check the `/sprints` resource.

#### [/sprints](http://localhost:8080/sprints)

 * `GET`: `/sprint`
   
   This resource will naïvely return a list of [SprintDto](src/main/java/io/mjmoore/dto/SprintDto.java)s which 
   can be assigned to existing users in the system.
   
### Application design

There shouldn't be anything surprising with the structure of the project:

 * Controllers sit at the front, exposing data via REST paths.

   Incoming data is accepted either as a DTO or in the form of path variables.

 * Contollers call services.
 
 * Services will validate data and call repositories as needed.
 
 * Repositories are designated as `RepositoryRestController`'s and exported as needed.
 
   As such, they will expose HATEOAS data for some resources.
   
#### Testing

 * Basic unit tests have been added where applicable and code coverage sits at ~95%.
 * Integration tests are missing.
 * End 2 end tests are missing. 

## Other Info

#### Assumptions 

 * A story must be completable in one sprint by one dev. 
 * A story must not be 0 effort.  

#### Limitations

 * No graphical frontend. Everything is exposed via a REST API.
 * Sprints are not persisted in any way.
 * Deleting a user does not remove assignments

#### Issues

 * Sprint capacity calculation is faulty. Assigned work is not taken into account.
    
   Assume there is a team of 7 developers.
   
   2 devs with assigned issues worth 5 points each.
   
   5 devs with assigned issues worth 1 points each.
   
   Capacity now stands as:
    * Total: 70
    * Used: 15
    * Remaining: 55
    
    There are no devs which have the capacity to take a 10-point story along 
    with their current workload, but it is still suggested.