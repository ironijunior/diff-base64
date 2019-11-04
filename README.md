# diff-base64
This solution is used to differ two JSON base64 encoded binary data.

## Tech aspects

### Stack
- **Java 11**: latest Java's long term version
- **Spring Boot**: for a quickly implementation start-up
- **MongoDB**: document oriented NoSql database, for data storage
- **Docker**: for containerizing the solution

### Architecture
The diff-base64 solution consists of two parts. The *api* and the *differ*.
The *api* is responsible for maintaning the objects to be compared, this part contains the public endpoints to be used by the application users.
The *differ* is the step responsible for the asynchronous process of object differentiation.

#### api
Consist of three endpoints:
- [POST] - `<host>/v1/diff/<ID>/left`
Sets the older data to be compared.
  - The `<ID>` is a unique identification String for the comparison.
  - Accepts a JSON object

- [POST] - `<host>/v1/diff/<ID>/right`
Sets the newer data to be compared with the older one.
  - The `<ID>` is a unique identification String for the comparison.
  - Accepts a JSON object
  
- [GET] - `<host>/v1/diff/<ID>`
Obtains the result of the comparison among `left` and `right` objects.
  - Possible results are:
    - if the objects are equal than returns:
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
      "result" : "Objects are different",
      "differences" : [{
          "initial_offset": "x",
          "final_offset": "y",
          "lenght": "z"
      }]
    }
    ```

When the `left` or `right` endpoint receives data it will check if the other side already have data as well, if so then it sends a "message" to the `differ` to start the process of comparison.

#### differ
Process the comparison among the two objects.

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
