package com.breakfast.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "breakfast_item")
public class BreakFastItemEntity implements Serializable {

    private static final long serialVersionUID = 5224811368963241639L;

    public BreakFastItemEntity() {
    }

    public BreakFastItemEntity(String name, BreakFastEntity breakfast) {
        this.name = name;
        this.breakfast = breakfast;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    // Many-to-One relationship with BreakFastEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakfast_id", nullable = false)
    private BreakFastEntity breakfast;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BreakFastEntity getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(BreakFastEntity breakfast) {
        this.breakfast = breakfast;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BreakFastItemEntity that = (BreakFastItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
