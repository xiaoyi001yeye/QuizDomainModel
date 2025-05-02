package com.example.quizdomainmodel.domain.model;

import java.util.Objects;
import java.util.UUID; // Using UUID for ID generation initially

/**
 * Represents a possible choice for a question, particularly for multiple-choice or true/false questions.
 */
public class Choice {

    private String id;
    private String text;
    // private boolean isCorrect; // As per design doc, correctness might be handled elsewhere (e.g., in Question or scoring logic)

    /**
     * Default constructor for frameworks/libraries.
     */
    private Choice() {}

    /**
     * Creates a new Choice with a generated ID.
     * @param text The text content of the choice. Cannot be null or empty.
     */
    public Choice(String text) {
        // Basic validation
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Choice text cannot be null or empty.");
        }
        this.id = UUID.randomUUID().toString(); // Simple ID generation for now
        this.text = text;
    }

    // Getters are needed for accessing state
    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    // --- Standard Object methods ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return Objects.equals(id, choice.id); // Equality based on ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash code based on ID
    }

    @Override
    public String toString() {
        return "Choice{" +
               "id='" + id + '\'' +
               ", text='" + text + '\'' +
               '}';
    }
}