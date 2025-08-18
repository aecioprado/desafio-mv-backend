# Social Security Number Existence Checking - Complete Guide

This guide demonstrates multiple approaches for checking the existence of a Social Security Number (SSN) in a database using Spring Data JPA.

## Repository Methods

### Option 1: Derived Query Method (Recommended)

```java
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
    
    /**
     * Checks if an entity exists with the given social security number
     * @param socialSecurityNumber the SSN to check
     * @return true if exists, false otherwise
     */
    boolean existsBySocialSecurityNumber(String socialSecurityNumber);
}
```

**Why this is recommended:**
- Spring Data JPA automatically generates the optimized query
- Clean, readable method name following naming conventions
- No custom JPQL or SQL required
- Most maintainable approach

### Option 2: Custom Query with JPQL

```java
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
    
    @Query("SELECT COUNT(e) > 0 FROM MyEntity e WHERE e.socialSecurityNumber = :ssn")
    boolean existsBySocialSecurityNumber(@Param("ssn") String socialSecurityNumber);
}
```

**When to use:**
- Need more control over the query
- Complex conditions that derived methods cannot handle
- Performance optimization requirements

### Option 3: Native SQL Query

```java
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
    
    @Query(value = "SELECT CASE WHEN EXISTS(SELECT 1 FROM my_entity WHERE social_security_number = :ssn) THEN TRUE ELSE FALSE END", 
           nativeQuery = true)
    boolean existsBySocialSecurityNumber(@Param("ssn") String socialSecurityNumber);
}
```

**When to use:**
- Database-specific optimizations needed
- Complex SQL features not available in JPQL
- Working with legacy database schemas

### Option 4: Case-Insensitive Check

```java
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
    
    @Query("SELECT COUNT(e) > 0 FROM MyEntity e WHERE UPPER(e.socialSecurityNumber) = UPPER(:ssn)")
    boolean existsBySocialSecurityNumberIgnoreCase(@Param("ssn") String socialSecurityNumber);
}
```

**When to use:**
- Need case-insensitive matching
- Data consistency issues in existing database
- User input normalization requirements

## Service Layer Implementation

```java
@Service
public class MyEntityService {
    
    @Autowired
    private MyEntityRepository repository;
    
    /**
     * Checks if SSN is already registered in the system
     */
    public boolean isSSNAlreadyRegistered(String socialSecurityNumber) {
        return repository.existsBySocialSecurityNumber(socialSecurityNumber);
    }
    
    /**
     * Validates that SSN is unique before saving
     * @throws IllegalArgumentException if SSN already exists
     */
    public void validateUniqueSSN(String socialSecurityNumber) {
        if (repository.existsBySocialSecurityNumber(socialSecurityNumber)) {
            throw new IllegalArgumentException("Social Security Number already exists in database");
        }
    }
}
```

## Usage Examples

### Basic Existence Check

```java
@RestController
public class UserController {
    
    @Autowired
    private MyEntityService service;
    
    @PostMapping("/validate-ssn")
    public ResponseEntity<String> validateSSN(@RequestBody String ssn) {
        if (service.isSSNAlreadyRegistered(ssn)) {
            return ResponseEntity.badRequest()
                .body("SSN already exists in the system");
        }
        return ResponseEntity.ok("SSN is available");
    }
}
```

### Before Entity Creation

```java
@Service
public class UserRegistrationService {
    
    @Autowired
    private MyEntityService entityService;
    
    @Transactional
    public MyEntity registerUser(UserRegistrationDto dto) {
        // Validate SSN uniqueness before creating entity
        entityService.validateUniqueSSN(dto.getSocialSecurityNumber());
        
        MyEntity entity = new MyEntity();
        entity.setSocialSecurityNumber(dto.getSocialSecurityNumber());
        // ... set other properties
        
        return repository.save(entity);
    }
}
```

## Performance Considerations

### Database Optimization

```sql
-- Add index for faster SSN lookups
CREATE INDEX idx_social_security_number ON my_entity(social_security_number);

-- For case-insensitive searches
CREATE INDEX idx_social_security_number_upper ON my_entity(UPPER(social_security_number));
```

### Method Comparison

| Method | Performance | Use Case |
|--------|-------------|----------|
| `existsBySocialSecurityNumber()` | Excellent | Simple existence check |
| `@Query COUNT > 0` | Very Good | Custom conditions needed |
| `@Query EXISTS` | Very Good | Database-specific optimizations |
| `findBySocialSecurityNumber().isPresent()` | Good | Need entity data after check |

## Security Best Practices

### Data Protection

```java
@Entity
public class MyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Consider encryption for sensitive data
    @Column(name = "social_security_number", unique = true)
    @Encrypted // Using a library like Jasypt
    private String socialSecurityNumber;
    
    // ... other fields
}
```

### Input Validation

```java
@Service
public class SSNValidationService {
    
    private static final Pattern SSN_PATTERN = 
        Pattern.compile("^\\d{3}-?\\d{2}-?\\d{4}$");
    
    public boolean isValidSSNFormat(String ssn) {
        return ssn != null && SSN_PATTERN.matcher(ssn).matches();
    }
    
    public String normalizeSSN(String ssn) {
        return ssn.replaceAll("[^\\d]", "");
    }
}
```

## Error Handling

```java
@Service
public class MyEntityService {
    
    public void validateUniqueSSN(String socialSecurityNumber) {
        try {
            if (repository.existsBySocialSecurityNumber(socialSecurityNumber)) {
                throw new DuplicateSSNException("SSN already exists: " + 
                    maskSSN(socialSecurityNumber));
            }
        } catch (DataAccessException e) {
            throw new SystemException("Database error during SSN validation", e);
        }
    }
    
    private String maskSSN(String ssn) {
        if (ssn.length() >= 4) {
            return "***-**-" + ssn.substring(ssn.length() - 4);
        }
        return "***-**-****";
    }
}
```

## Choosing the Right Approach

1. **For simple SSN existence checks**: Use derived query method (`existsBySocialSecurityNumber`)
2. **For complex conditions**: Use `@Query` with JPQL
3. **For database-specific optimizations**: Use native SQL queries
4. **When you also need the entity data**: Consider `findBySocialSecurityNumber().isPresent()`

## Additional Considerations

- **Privacy Compliance**: Ensure GDPR, CCPA compliance when handling SSNs
- **Audit Logging**: Log SSN access for security monitoring
- **Rate Limiting**: Implement rate limiting for SSN validation endpoints
- **Caching**: Consider caching strategies for frequently checked SSNs (with proper expiration)
- **Batch Processing**: For bulk SSN validations, consider batch queries to reduce database round trips