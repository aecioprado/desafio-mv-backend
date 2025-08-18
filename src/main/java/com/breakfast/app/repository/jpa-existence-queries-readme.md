# JPA Existence Queries Guide

## Using @Query with COUNT > 0 for Custom Existence Queries

For more complex existence checks that cannot be expressed through derived query methods, you can use the `@Query` annotation with a custom JPQL or native SQL query. A common pattern is to count matching records and check if the count is greater than zero.

### Example Implementation

```java
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
    
    // JPQL query with COUNT > 0
    @Query("SELECT COUNT(e) > 0 FROM MyEntity e WHERE e.someProperty = :value")
    boolean existsBySomeProperty(@Param("value") String value);

    // Native SQL query with EXISTS clause
    @Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM my_entity WHERE another_property = :anotherValue) THEN TRUE ELSE FALSE END", 
           nativeQuery = true)
    boolean existsByAnotherPropertyNative(@Param("anotherValue") String anotherValue);
}
```

## Choosing the Best Method

### `existsById()`
- **Use for**: Simple ID-based existence checks
- **Benefits**: Most optimized for this specific purpose
- **Best when**: You only need to check if an entity exists by its primary key

### Derived Query Methods
- **Use for**: Common property-based existence checks
- **Benefits**: Good readability and automatic query generation
- **Best when**: Standard existence checks on entity properties
- **Example**: `existsByUsername(String username)`

### `@Query` with COUNT > 0
- **Use for**: Complex or custom existence logic
- **Benefits**: Full control over query logic and conditions
- **Best when**: Derived methods cannot handle the required complexity
- **Examples**: Multiple conditions, joins, subqueries

### `findById().isPresent()`
- **Use for**: When you might need the entity's data in addition to checking existence
- **Benefits**: Returns the actual entity if it exists
- **Best when**: You need both existence check and potential data retrieval

## Performance Considerations

- `existsById()` and derived `exists` methods are generally more performant than `findById().isPresent()` for pure existence checks
- Custom `@Query` methods with `COUNT > 0` or `EXISTS` clauses are optimized for existence checking without loading full entities
- Choose the method that best matches your specific use case and performance requirements