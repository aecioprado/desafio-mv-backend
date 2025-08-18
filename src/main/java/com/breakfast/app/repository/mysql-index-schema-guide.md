# MySQL Database Indexes in Spring Boot - Complete Guide

This guide explains how to create and manage MySQL database indexes using `schema.sql` in Spring Boot applications, with practical examples and best practices.

## Understanding Database Indexes

Database indexes are data structures that improve query performance by creating shortcuts to find data quickly. They work like an index in a book - instead of scanning every page, you can jump directly to the relevant content.

### Benefits of Indexes
- **Faster SELECT queries** - Dramatically reduce query execution time
- **Improved JOIN performance** - Speed up table joins
- **Efficient sorting** - ORDER BY operations become faster
- **Unique constraints** - Enforce data uniqueness

### Trade-offs
- **Storage overhead** - Indexes consume additional disk space
- **Slower INSERT/UPDATE/DELETE** - Indexes need to be maintained
- **Memory usage** - Indexes are loaded into memory

## Spring Boot Configuration

### 1. Application Properties Setup

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Initialize database schema
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql

# JPA Configuration
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# For development - drop and create schema
# spring.sql.init.mode=always
# spring.sql.init.continue-on-error=false
```

### 2. Alternative YAML Configuration

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  sql:
    init:
      mode: always
      schema-locations: classpath:schema.sql
      data-locations: classpath:data.sql
      continue-on-error: false
  
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

## Creating schema.sql File

### Basic Structure

Create `src/main/resources/schema.sql`:

```sql
-- schema.sql
-- Drop tables if they exist (for development)
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

-- Create tables
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    social_security_number VARCHAR(11) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
```

## Index Types and Creation

### 1. Primary Index (Unique Index)

```sql
-- Primary key automatically creates a unique clustered index
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Automatic unique index
    -- other columns...
);

-- Alternative explicit syntax
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT,
    -- other columns...
    PRIMARY KEY (id)  -- Creates unique clustered index
);
```

### 2. Unique Indexes

```sql
-- Single column unique index
CREATE UNIQUE INDEX idx_users_social_security_number 
ON users(social_security_number);

-- Multi-column unique index
CREATE UNIQUE INDEX idx_users_email_username 
ON users(email, username);

-- Alternative syntax using ALTER TABLE
ALTER TABLE users 
ADD UNIQUE INDEX idx_users_email (email);
```

### 3. Regular (Non-Unique) Indexes

```sql
-- Single column index
CREATE INDEX idx_users_last_name 
ON users(last_name);

-- Multi-column composite index
CREATE INDEX idx_users_name_status 
ON users(first_name, last_name, status);

-- Index with specific length (for VARCHAR optimization)
CREATE INDEX idx_users_first_name_partial 
ON users(first_name(10));
```

### 4. Case-Insensitive Indexes

```sql
-- Functional index for case-insensitive searches
CREATE INDEX idx_users_email_upper 
ON users((UPPER(email)));

CREATE INDEX idx_users_username_lower 
ON users((LOWER(username)));

-- For case-insensitive SSN searches
CREATE INDEX idx_users_ssn_upper 
ON users((UPPER(social_security_number)));
```

### 5. Partial Indexes with Conditions

```sql
-- Index only active users (MySQL 8.0+)
CREATE INDEX idx_users_active_email 
ON users(email) WHERE status = 'ACTIVE';

-- Index for recent records
CREATE INDEX idx_users_recent 
ON users(created_at) WHERE created_at >= '2024-01-01';
```

### 6. Full-Text Indexes

```sql
-- Full-text search index
CREATE FULLTEXT INDEX idx_users_fulltext_search 
ON users(first_name, last_name, email);

-- Usage in queries:
-- SELECT * FROM users WHERE MATCH(first_name, last_name, email) AGAINST('john doe' IN NATURAL LANGUAGE MODE);
```

## Complete schema.sql Example

```sql
-- Complete schema.sql with indexes
-- Drop existing objects
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

-- Create users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    social_security_number VARCHAR(11) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create roles table
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create junction table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

-- =============================================================================
-- INDEXES SECTION
-- =============================================================================

-- Unique indexes for business constraints
CREATE UNIQUE INDEX idx_users_social_security_number 
ON users(social_security_number);

CREATE UNIQUE INDEX idx_users_email 
ON users(email);

CREATE UNIQUE INDEX idx_users_username 
ON users(username);

CREATE UNIQUE INDEX idx_roles_name 
ON roles(name);

-- Regular indexes for common queries
CREATE INDEX idx_users_last_name 
ON users(last_name);

CREATE INDEX idx_users_first_name 
ON users(first_name);

CREATE INDEX idx_users_status 
ON users(status);

CREATE INDEX idx_users_created_at 
ON users(created_at);

-- Composite indexes for complex queries
CREATE INDEX idx_users_name_composite 
ON users(first_name, last_name);

CREATE INDEX idx_users_status_created 
ON users(status, created_at);

-- Case-insensitive indexes for search operations
CREATE INDEX idx_users_email_upper 
ON users((UPPER(email)));

CREATE INDEX idx_users_ssn_upper 
ON users((UPPER(social_security_number)));

CREATE INDEX idx_users_username_lower 
ON users((LOWER(username)));

-- Full-text search index
CREATE FULLTEXT INDEX idx_users_search 
ON users(first_name, last_name);

-- Foreign key indexes (for JOIN performance)
CREATE INDEX idx_user_roles_user_id 
ON user_roles(user_id);

CREATE INDEX idx_user_roles_role_id 
ON user_roles(role_id);

-- Add foreign key constraints
ALTER TABLE user_roles 
ADD CONSTRAINT fk_user_roles_user_id 
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_roles 
ADD CONSTRAINT fk_user_roles_role_id 
FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE;
```

## JPA Entity Annotations

### Using @Index Annotation

```java
@Entity
@Table(name = "users", 
       indexes = {
           @Index(name = "idx_users_social_security_number", 
                  columnList = "socialSecurityNumber", 
                  unique = true),
           @Index(name = "idx_users_email", 
                  columnList = "email", 
                  unique = true),
           @Index(name = "idx_users_name_composite", 
                  columnList = "firstName,lastName"),
           @Index(name = "idx_users_status_created", 
                  columnList = "status,createdAt")
       })
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "social_security_number", nullable = false, unique = true)
    private String socialSecurityNumber;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    // Getters and setters...
}
```

## Index Management Strategies

### 1. Development vs Production

```sql
-- development-schema.sql
-- More aggressive dropping for development
DROP DATABASE IF EXISTS mydb_dev;
CREATE DATABASE mydb_dev;
USE mydb_dev;

-- Include all table and index creation scripts...
```

```sql
-- production-schema.sql
-- Safe migrations for production
-- Use IF NOT EXISTS for production safety

CREATE TABLE IF NOT EXISTS users (
    -- table definition
);

-- Create indexes only if they don't exist
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
```

### 2. Schema Versioning

```sql
-- schema-v1.sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL
);

-- schema-v2.sql (migration)
ALTER TABLE users ADD COLUMN social_security_number VARCHAR(11);
CREATE UNIQUE INDEX idx_users_ssn ON users(social_security_number);
```

### 3. Environment-Specific Configuration

```properties
# application-dev.properties
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema-dev.sql

# application-prod.properties
spring.sql.init.mode=never
# Use Flyway or Liquibase for production migrations
```

## Performance Analysis and Optimization

### 1. Query Performance Analysis

```sql
-- Analyze query execution plans
EXPLAIN SELECT * FROM users WHERE social_security_number = '123-45-6789';

EXPLAIN SELECT * FROM users WHERE UPPER(email) = UPPER('john@example.com');

-- Check index usage
SHOW INDEX FROM users;

-- Monitor slow queries
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 1;
```

### 2. Index Effectiveness Monitoring

```sql
-- Check index statistics
SELECT 
    TABLE_NAME,
    INDEX_NAME,
    SEQ_IN_INDEX,
    COLUMN_NAME,
    CARDINALITY
FROM information_schema.STATISTICS 
WHERE TABLE_SCHEMA = 'mydb'
ORDER BY TABLE_NAME, INDEX_NAME, SEQ_IN_INDEX;

-- Check index usage
SELECT 
    OBJECT_SCHEMA,
    OBJECT_NAME,
    INDEX_NAME,
    COUNT_READ,
    COUNT_WRITE,
    COUNT_READ / (COUNT_READ + COUNT_WRITE) * 100 AS read_percentage
FROM performance_schema.table_io_waits_summary_by_index_usage
WHERE OBJECT_SCHEMA = 'mydb';
```

## Best Practices

### 1. Index Naming Convention

```sql
-- Follow consistent naming patterns
-- idx_{table}_{column(s)}
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_name_status ON users(first_name, last_name, status);

-- For unique indexes
-- uk_{table}_{column(s)} or idx_{table}_{column(s)}_unique
CREATE UNIQUE INDEX uk_users_ssn ON users(social_security_number);
```

### 2. Composite Index Order

```sql
-- Order columns by selectivity (most selective first)
-- Good: status has few values, created_at has many
CREATE INDEX idx_users_created_status ON users(created_at, status);

-- Bad: less efficient for range queries on created_at
CREATE INDEX idx_users_status_created ON users(status, created_at);
```

### 3. Index Maintenance

```sql
-- Regular maintenance queries
ANALYZE TABLE users;
OPTIMIZE TABLE users;

-- Check for unused indexes
SELECT 
    OBJECT_SCHEMA,
    OBJECT_NAME,
    INDEX_NAME
FROM performance_schema.table_io_waits_summary_by_index_usage
WHERE COUNT_READ = 0 AND COUNT_WRITE = 0
  AND OBJECT_SCHEMA = 'mydb';
```

## Common Pitfalls and Solutions

### 1. Over-Indexing
- **Problem**: Too many indexes slow down writes
- **Solution**: Monitor index usage and remove unused indexes

### 2. Wrong Index Type
- **Problem**: Using regular index instead of unique index
- **Solution**: Use appropriate index type for data constraints

### 3. Case-Sensitive Searches
- **Problem**: Index not used for case-insensitive queries
- **Solution**: Create functional indexes with UPPER() or LOWER()

### 4. Composite Index Order
- **Problem**: Wrong column order in composite indexes
- **Solution**: Order by selectivity and query patterns

## Migration with Flyway Integration

### 1. Add Flyway Dependency

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency>
```

### 2. Migration Files

```sql
-- src/main/resources/db/migration/V1__Create_users_table.sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    social_security_number VARCHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- src/main/resources/db/migration/V2__Add_user_indexes.sql
CREATE UNIQUE INDEX idx_users_social_security_number 
ON users(social_security_number);

CREATE UNIQUE INDEX idx_users_email 
ON users(email);

-- src/main/resources/db/migration/V3__Add_case_insensitive_indexes.sql
CREATE INDEX idx_users_email_upper 
ON users((UPPER(email)));
```

### 3. Flyway Configuration

```properties
# application.properties
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
spring.flyway.validate-on-migrate=true
```

## Testing Index Performance

```java
@SpringBootTest
@TestPropertySource(properties = {
    "spring.sql.init.mode=always",
    "spring.sql.init.schema-locations=classpath:test-schema.sql"
})
class IndexPerformanceTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void testIndexPerformance() {
        // Create test data
        for (int i = 0; i < 10000; i++) {
            User user = new User();
            user.setSocialSecurityNumber(String.format("%09d", i));
            user.setEmail(String.format("user%d@example.com", i));
            userRepository.save(user);
        }
        
        // Test query performance
        long startTime = System.currentTimeMillis();
        boolean exists = userRepository
            .existsBySocialSecurityNumberIgnoreCase("000001234");
        long endTime = System.currentTimeMillis();
        
        System.out.println("Query executed in: " + (endTime - startTime) + "ms");
        assertTrue(exists);
    }
}
```

This comprehensive guide covers everything you need to know about creating and managing MySQL indexes in Spring Boot applications using schema.sql files, including performance optimization, best practices, and common pitfalls to avoid.