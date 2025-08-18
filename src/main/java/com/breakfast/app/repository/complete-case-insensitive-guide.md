# Case-Insensitive Derived Queries in Spring Data JPA - Complete Guide

This comprehensive guide demonstrates how to implement case-insensitive queries using Spring Data JPA derived query methods, with practical examples and best practices.

## Basic Case-Insensitive Syntax

Spring Data JPA provides two keywords for case-insensitive operations:
- `IgnoreCase` - Standard syntax
- `IgnoringCase` - Alternative syntax (identical functionality)

### Simple Repository Methods

```java
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
    
    // Option 1: Using IgnoreCase for single property
    boolean existsBySocialSecurityNumberIgnoreCase(String socialSecurityNumber);
    
    // Option 2: Using IgnoringCase (alternative syntax)
    boolean existsBySocialSecurityNumberIgnoringCase(String socialSecurityNumber);
    
    // Option 3: Multiple properties with case-insensitive search
    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
    
    // Option 4: Find methods with case-insensitive
    Optional<MyEntity> findBySocialSecurityNumberIgnoreCase(String socialSecurityNumber);
    List<MyEntity> findByFirstNameIgnoreCase(String firstName);
    
    // Option 5: Like operations with case-insensitive
    List<MyEntity> findByFirstNameContainingIgnoreCase(String firstName);
    List<MyEntity> findByLastNameStartingWithIgnoreCase(String lastNamePrefix);
    List<MyEntity> findByEmailEndingWithIgnoreCase(String emailSuffix);
    
    // Option 6: Combining with other conditions
    boolean existsByFirstNameIgnoreCaseAndAgeGreaterThan(String firstName, int age);
    List<MyEntity> findByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);
    
    // Option 7: Ordering with case-insensitive
    List<MyEntity> findByStatusIgnoreCaseOrderByFirstNameIgnoreCase(String status);
    
    // Option 8: Count operations with case-insensitive
    long countByFirstNameIgnoreCase(String firstName);
    
    // Option 9: Delete operations with case-insensitive
    @Modifying
    @Transactional
    void deleteBySocialSecurityNumberIgnoreCase(String socialSecurityNumber);
}
```

## Service Layer Implementation

### Basic Usage Examples

```java
@Service
public class MyEntityService {
    
    @Autowired
    private MyEntityRepository repository;
    
    public boolean isSSNRegistered(String socialSecurityNumber) {
        // This will match regardless of case
        // "123-45-6789", "123-45-6789", "123-45-6789" all match
        return repository.existsBySocialSecurityNumberIgnoreCase(socialSecurityNumber);
    }
    
    public Optional<MyEntity> findUserBySSN(String ssn) {
        return repository.findBySocialSecurityNumberIgnoreCase(ssn);
    }
    
    public List<MyEntity> searchByName(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return repository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
        } else if (firstName != null) {
            return repository.findByFirstNameIgnoreCase(firstName);
        }
        return Collections.emptyList();
    }
    
    public List<MyEntity> searchByPartialName(String namePattern) {
        // This will find names containing the pattern, case-insensitive
        return repository.findByFirstNameContainingIgnoreCase(namePattern);
    }
}
```

## Advanced Repository Examples

### Complex Queries with Multiple Conditions

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Complex case-insensitive query
    @Query("SELECT u FROM User u WHERE " +
           "UPPER(u.email) = UPPER(:email) OR " +
           "UPPER(u.username) = UPPER(:username)")
    Optional<User> findByEmailOrUsernameIgnoreCase(@Param("email") String email, 
                                                   @Param("username") String username);
    
    // Derived query equivalent (Spring Data JPA will handle case-insensitive)
    Optional<User> findByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);
    
    // Case-insensitive exists with multiple conditions
    boolean existsByEmailIgnoreCaseAndStatusIgnoreCase(String email, String status);
    
    // Pagination with case-insensitive
    Page<User> findByFirstNameIgnoreCase(String firstName, Pageable pageable);
    
    // Specification-like behavior with derived queries
    List<User> findByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndEmailContainingIgnoreCase(
        String firstName, String lastName, String emailPattern);
}
```

## Testing Case-Insensitive Queries

```java
@SpringBootTest
class MyEntityRepositoryTest {
    
    @Autowired
    private MyEntityRepository repository;
    
    @Test
    void testCaseInsensitiveExists() {
        // Save entity with specific case
        MyEntity entity = new MyEntity();
        entity.setSocialSecurityNumber("123-45-6789");
        repository.save(entity);
        
        // All these should return true
        assertTrue(repository.existsBySocialSecurityNumberIgnoreCase("123-45-6789"));
        assertTrue(repository.existsBySocialSecurityNumberIgnoreCase("123-45-6789"));
        assertTrue(repository.existsBySocialSecurityNumberIgnoreCase("123-45-6789"));
    }
    
    @Test
    void testCaseInsensitiveFind() {
        MyEntity entity = new MyEntity();
        entity.setFirstName("John");
        entity.setLastName("Doe");
        repository.save(entity);
        
        // All variations should find the entity
        assertThat(repository.findByFirstNameIgnoreCase("john")).isNotEmpty();
        assertThat(repository.findByFirstNameIgnoreCase("JOHN")).isNotEmpty();
        assertThat(repository.findByFirstNameIgnoreCase("John")).isNotEmpty();
    }
}
```

## Custom Implementation for Complex Cases

```java
@Repository
public class MyEntityRepositoryCustomImpl {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public boolean existsBySocialSecurityNumberIgnoreCaseCustom(String ssn) {
        String jpql = "SELECT COUNT(e) > 0 FROM MyEntity e WHERE UPPER(e.socialSecurityNumber) = UPPER(:ssn)";
        Long count = entityManager.createQuery(jpql, Long.class)
            .setParameter("ssn", ssn)
            .getSingleResult();
        return count > 0;
    }
}
```

## Database Configuration

### MySQL Case-Insensitive Configuration

```java
@Configuration
public class DatabaseConfig {
    
    // For MySQL - configure case-insensitive collation
    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8&collation=utf8_general_ci");
        // ... other configurations
        return dataSource;
    }
}
```

## Available Case-Insensitive Keywords

| Keyword | Purpose | Example |
|---------|---------|---------|
| `IgnoreCase` | Exact match, case-insensitive | `findByNameIgnoreCase("john")` |
| `ContainingIgnoreCase` | Partial match, case-insensitive | `findByNameContainingIgnoreCase("oh")` |
| `StartingWithIgnoreCase` | Prefix match, case-insensitive | `findByNameStartingWithIgnoreCase("jo")` |
| `EndingWithIgnoreCase` | Suffix match, case-insensitive | `findByNameEndingWithIgnoreCase("hn")` |

## String Operation Examples

### Practical Use Cases

```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Find products by exact name match (case-insensitive)
    List<Product> findByProductNameIgnoreCase(String productName);
    
    // Find products containing a keyword in name
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
    
    // Find products starting with a prefix
    List<Product> findByProductNameStartingWithIgnoreCase(String prefix);
    
    // Find products ending with a suffix
    List<Product> findByProductNameEndingWithIgnoreCase(String suffix);
    
    // Complex search combining multiple criteria
    List<Product> findByProductNameContainingIgnoreCaseAndCategoryIgnoreCase(
        String nameKeyword, String category);
    
    // Existence check with partial matching
    boolean existsByProductNameContainingIgnoreCase(String keyword);
}
```

## Performance Considerations

### Database Optimization

```sql
-- Create function-based indexes for better performance
CREATE INDEX idx_product_name_upper ON products(UPPER(product_name));
CREATE INDEX idx_ssn_upper ON users(UPPER(social_security_number));

-- For PostgreSQL - case-insensitive indexes
CREATE INDEX idx_email_ci ON users(LOWER(email));

-- For MySQL - use case-insensitive collation
ALTER TABLE users MODIFY COLUMN email VARCHAR(255) COLLATE utf8_general_ci;
```

### Performance Comparison

| Query Type | Performance | Use Case |
|------------|-------------|----------|
| Exact match | Fastest | When case matches exactly |
| `IgnoreCase` | Good | Simple case-insensitive matching |
| `ContainingIgnoreCase` | Slower | Partial text search |
| Full-text search | Variable | Complex text searching |

## Best Practices

### 1. **Choose the Right Method**

```java
// For exact SSN matching (recommended)
boolean existsBySocialSecurityNumberIgnoreCase(String ssn);

// For partial name searches
List<User> findByFirstNameContainingIgnoreCase(String namePattern);

// For email domain searches
List<User> findByEmailEndingWithIgnoreCase(String domain);
```

### 2. **Input Validation and Normalization**

```java
@Service
public class SearchService {
    
    public List<User> searchUsers(String searchTerm) {
        // Validate and normalize input
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        String normalizedTerm = searchTerm.trim();
        return userRepository.findByFirstNameContainingIgnoreCase(normalizedTerm);
    }
}
```

### 3. **Database-Specific Considerations**

#### PostgreSQL
- Use `ILIKE` operator for case-insensitive pattern matching
- Consider using `LOWER()` or `UPPER()` with functional indexes

#### MySQL
- Configure appropriate collation (`utf8_general_ci`)
- Use case-insensitive collations at table or column level

#### Oracle
- Use `UPPER()` or `LOWER()` functions
- Consider linguistic indexes for international characters

### 4. **Error Handling**

```java
@Service
public class UserService {
    
    public Optional<User> findUserByEmail(String email) {
        try {
            if (email == null || !EmailValidator.isValid(email)) {
                return Optional.empty();
            }
            return userRepository.findByEmailIgnoreCase(email.trim());
        } catch (DataAccessException e) {
            log.error("Database error while searching user by email", e);
            throw new ServiceException("Unable to search user", e);
        }
    }
}
```

## Migration from Case-Sensitive to Case-Insensitive

### Step 1: Update Repository Methods

```java
// Before (case-sensitive)
boolean existsByEmail(String email);

// After (case-insensitive)
boolean existsByEmailIgnoreCase(String email);
```

### Step 2: Update Database Schema

```sql
-- Create case-insensitive indexes
CREATE INDEX CONCURRENTLY idx_users_email_ci ON users(LOWER(email));

-- Update existing data if needed
UPDATE users SET email = LOWER(email);
```

### Step 3: Update Application Logic

```java
@Service
public class MigrationService {
    
    // Temporary method during migration
    public boolean existsByEmailCaseInsensitive(String email) {
        // Check both old and new methods during transition
        return userRepository.existsByEmail(email) || 
               userRepository.existsByEmailIgnoreCase(email);
    }
}
```

## Common Pitfalls and Solutions

### 1. **Performance Issues**
- **Problem**: Slow queries with case-insensitive operations
- **Solution**: Create appropriate database indexes

### 2. **International Characters**
- **Problem**: Incorrect matching with accented characters
- **Solution**: Use database-specific collations or normalization

### 3. **Database Compatibility**
- **Problem**: Different behavior across database vendors
- **Solution**: Test thoroughly and use database-specific configurations

### 4. **Index Usage**
- **Problem**: Database not using indexes for case-insensitive queries
- **Solution**: Create function-based indexes with `UPPER()` or `LOWER()`

## Conclusion

Case-insensitive derived queries in Spring Data JPA provide a clean, maintainable way to handle case-insensitive data operations. Key takeaways:

- Use `IgnoreCase` keyword for simple case-insensitive operations
- Consider performance implications and create appropriate indexes
- Test thoroughly across different database platforms
- Validate and normalize input data before querying
- Choose the right query method based on your specific use case

For complex scenarios that derived queries cannot handle, fall back to custom `@Query` methods with JPQL or native SQL.