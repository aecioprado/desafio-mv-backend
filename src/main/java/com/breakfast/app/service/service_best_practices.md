# Best Practices for Service & Repository Usage in Spring Boot

This guide outlines improvements and best practices for using **services** and **repositories** in Spring Boot, based on the example `BreakFastServiceImpl` implementation.

---

## ✅ What’s Good in the Current Code

1. **Service Abstraction**\
   You have a `BreakFastService` interface and an `impl` class. This is good for testing and flexibility.

2. **Constructor Injection**\
   Correct use of constructor injection instead of field injection (`@Autowired`).

3. **Business Validation in the Service Layer**\
   Checks for duplicate Social Security Numbers (SSNs) or items are correctly placed in the service layer, not the repository.

---

## ⚡ Improvements & Best Practices

### 1. Naming & Readability

- Rename `BreakFastServiceImp` → `BreakFastServiceImpl`.
- Improve method names:
  - `checkIfAnyItemAlreayExists` → `anyItemAlreadyExists`.
- Make exception messages clearer:

```java
throw new BreakFastException(
    String.format("Social Security Number %s already exists", breakFastEntity.getSocialSecurityNumber())
);
```

---

### 2. Batch Existence Check (Performance)

Currently, the code checks each item with a separate DB call. This can cause **N queries** for N items. Instead, fetch all names in one query.

**Repository:**

```java
boolean existsByNameIn(List<String> names);
```

**Service method:**

```java
private boolean anyItemAlreadyExists(List<BreakFastItemEntity> items) {
    List<String> names = items.stream()
                              .map(BreakFastItemEntity::getName)
                              .toList();

    return breakFastItemRepository.existsByNameIn(names);
}
```

This reduces DB calls from **N → 1**.

---

### 3. Transaction Management

If multiple DB operations happen in the service method, wrap it with `@Transactional` to ensure atomicity.

```java
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Override
public BreakFastEntity save(BreakFastEntity breakFastEntity) {
    ...
}
```

---

### 4. Service vs Repository Responsibility

- **Repositories**: Only handle database operations (CRUD, queries).
- **Services**: Enforce business rules (e.g., SSN must be unique).

The current setup is correct. Just ensure business logic stays in services.

---

### 5. Custom Exceptions

Instead of one generic exception, define more specific ones for better error handling.

```java
public class DuplicateSocialSecurityException extends BreakFastException { ... }
public class DuplicateItemException extends BreakFastException { ... }
```

This helps controllers map errors to proper HTTP responses (e.g., 400 Bad Request).

---

### 6. Validation Layer (Optional)

If validation logic grows, extract it into a dedicated **validator class**.

```java
@Component
public class BreakFastValidator {
    private final BreakFastRepository breakFastRepository;
    private final BreakFastItemRepository breakFastItemRepository;

    public void validate(BreakFastEntity entity) {
        if (breakFastRepository.existsBySocialSecurityNumber(entity.getSocialSecurityNumber())) {
            throw new DuplicateSocialSecurityException(...);
        }
        if (breakFastItemRepository.existsByNameIn(entity.getItems().stream().map(BreakFastItemEntity::getName).toList())) {
            throw new DuplicateItemException(...);
        }
    }
}
```

Then the service remains clean:

```java
@Override
@Transactional
public BreakFastEntity save(BreakFastEntity entity) {
    validator.validate(entity);
    return breakFastRepository.save(entity);
}
```

---

## 📌 Summary of Best Practices

- ✅ Keep **business logic** in the service, **DB queries** in the repository.
- ✅ Use meaningful names (`Impl`, `anyItemAlreadyExists`).
- ✅ Minimize DB calls (batch queries).
- ✅ Use `@Transactional` for multiple DB operations.
- ✅ Define specific exceptions for clarity.
- ✅ (Optional) Extract validations into a validator class if they grow.

---

By applying these practices, your service layer will remain **clean, performant, and maintainable**.

