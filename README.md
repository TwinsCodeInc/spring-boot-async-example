# Spring Boot Async Example

## Summary

Project to show examples of asynchronous programming using Java and Spring Boot based on "The future is asynchronous: Tools for scalable services" talk in JAX Java conference 2016.

## References

1. [Jax talk](https://www.youtube.com/watch?v=F32XoAPijTo)
2. [Github project](https://github.com/chbatey/java-async-talk)

### Asynchronous Futures

| Future            | Framework   |
| ----------------- |-------------|
| Future            | Java        |
| ListenableFuture  | Guava       |
| CompletableFuture | Java        | 

## Testing
### Building

```
mvn clean install
```

### Running

```
java -jar target/spring-boot-async-example-0.0.1-SNAPSHOT.jar
```

### Commands

Synchronous

```
time curl -v localhost:8080/springsync/watch/test/permission1/test
```

Asynchronous Future

```
time curl -v localhost:8080/springfuture/watch/test/permission1/test
```

Asynchronous ListenableFuture

```
time curl -v localhost:8080/springlistenablefuture/watch/test/permission1/test
```

Asynchronous CompletableFuture

```
time curl -v localhost:8080/springcompletablefuture/watch/test/permission1/test
```