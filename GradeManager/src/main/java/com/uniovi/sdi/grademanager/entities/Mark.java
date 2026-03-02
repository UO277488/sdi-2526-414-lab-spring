package com.uniovi.sdi.grademanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class Mark {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "{mark.validation.description.required}")
    @Size(min = 290, message = "{mark.validation.description.minLength}")
    private String description;
    @NotNull(message = "{mark.validation.score.required}")
    @DecimalMin(value = "0.0", message = "{mark.validation.score.range}")
    @DecimalMax(value = "10.0", message = "{mark.validation.score.range}")
    private Double score;
    private Boolean resend = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Mark() {
    }

    public Mark(Long id, String description, Double score) {
        this.id = id;
        this.description = description;
        this.score = score;
    }

    public Mark(String description, Double score, User user) {
        this.description = description;
        this.score = score;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", score=" + score +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getResend() {
        return resend;
    }

    public void setResend(Boolean resend) {
        this.resend = resend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return Objects.equals(id, mark.id);
    }
}
