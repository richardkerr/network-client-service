# network-client-service

A two part network client service.
The first, request-handler, accepts multiple incoming socket requests and initiates a connection to the second, calculator, and forwards all communication on the request socket to the calculator service and vice versa.  The calculator service evaluates math expressions and returns the result.

# How to compile & run

From the project root
```mvn clean install```

Both the request-handler and calculator services are configured to create shaded jars, so execution is as simple as:

```java -jar calculator/target/calculator-1.0-SNAPSHOT-shaded.jar```

```java -jar request-handler/target/request-handler-1.0-SNAPSHOT-shaded.jar```

The request-handler socket server will listen on port 2468 and will communicate with the calculator service on port 8642

# Project structure

* socket-server - contains interfaces and classes shared between the two services
* calculator - the calculator service
* request-handler - the request handler service
* it - contains a simple integration test for the completed application (assumes a windows environment for the startup of the services)

# Design decisions

Where practical no external libraries were used to implement this task.  One obvious exception to this rule is the use of exp4j for the math processing, this decision was made to focus on the task at hand rather than implementing a [well documented algorithm](http://en.wikipedia.org/wiki/Shunting-yard_algorithm).

# Improvements

* Currently all configuration is hard coded, including port numbers and the ip address of the calculator service
* There is quite a lot of code duplication between CalculatorSocketProcessor and SocketForwarder
* Unit tests could be improved


