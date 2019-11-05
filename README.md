# diff-base64
This solution is used to differ two JSON base64 encoded binary data.

## Tech aspects

### Stack
- **Java 11**: latest Java's long term version
- **Spring Boot**: for a quickly implementation start-up
- **MongoDB**: document oriented NoSql database, for data storage
- **Lombok**: for helping with the boilerplate code on POJO objects
- **Docker**: for containerizing the solution

### Architecture
The diff-base64 solution consists of two domains. The *api* and the *differ*.
The *api* is responsible for maintaning the objects to be compared, this part contains the public endpoints to be used by the application users.
The *differ* is the domain responsible for the asynchronous process of object differentiation.

#### api
Consist of three endpoints:
- [POST] - `<host>/v1/diff/<ID>/left`
Sets the older data to be compared.
  - The `<ID>` is a unique identification String for the comparison and can't be `null`.
  - If the `right` side is already setted when this endpoint is called, then the async `differ` process will be started for this diff.

- [POST] - `<host>/v1/diff/<ID>/right`
Sets the newer data to be compared with the older one.
  - The `<ID>` is a unique identification String for the comparison and can't be `null`.
  - If the `left` side is already setted when this endpoint is called, then the async `differ` process will be started for this diff.
  
- [GET] - `<host>/v1/diff/<ID>`
Obtains the result of the comparison among `left` and `right` objects.
This endpoint is not responsible for realizing the comparison. The `diff` process is asynchronous and it starts once you have both sides informed.
  - Possible results are:
    - if the objects are equals than returns:
    ```json
    {"result" : "Objects are equals"}
    ```
    - if the objects are *not* equals on size than returns:
    ```json
    {"result" : "Objects are different in size"}
    ```
    - if the objects are same size but different content, provides insight in where the diffs are:
    ```json
    {
      "status" : "Objects are different",
      "differences" : [{
          "initialOffset": 19,
          "finalOffset": 20,
          "length": 1
      }]
    }
    ```

#### Diff process
Comparison process among both sides of the object.
When the `left` or `right` endpoint receives data it'll check if the other side already have data as well, if so then 
an event will be published to the Spring Application Context, and then consumed by the `ApplicationListener`
and processed by the `DiffProcessor`. The `DiffProcessor` runs the process of comparison asynchronously using `CompletableFuture`.

The idea to use Spring Application Context and CompletableFuture is to have a pre visualization on how the 
solution would behave using a distributed architecture. In the future we could migrate
the Events and the CompletableFuture to a message broker (RabbitMQ, Kafka, etc), and why not spread the solution in two microservices (e.g. api and differ).

### How to run

#### Unit tests and Integration tests
```sh
~: cd diff-base64
~: mvn clean test
```

#### Running
```sh
~: cd diff-base64
~: mvn springboot:run
```

#### Container
This application could be containerized using the `docker-compose.yml` file in the root project. To do it, run:
```sh
~: cd diff-base64
~: <add docker command>
```

### Improvements
There are some points that would be nice to improve:
- 
- 
- 
