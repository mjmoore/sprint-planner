# Pinguin Sprint Suggester

#### Requirements
  * `JAVA_HOME` is set to Java 13.
  * Gradle 6+ (included).

#### Running

The project can be built and run using gradle. 
From the root directory, run: `./gradlew bootRun`

The application will start and expose a REST API on http://localhost:8080

#### Testing

Tests can be run using gradle. From the root directory, run: `./gradlew test`

#### Initial data

Some sample data will be added to the system on boot. Refer to 
[data.sql](src/main/resources/data.sql) for more details.

Includes:
 * 5 users with generated names.
 * 5 bugs in various states.
 * 5 stories in New and Completed status.
 * 20 estimated stories, ready for sprint planning. 

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