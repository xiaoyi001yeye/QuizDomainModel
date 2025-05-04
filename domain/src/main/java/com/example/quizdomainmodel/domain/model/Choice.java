package com.example.quizdomainmodel.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a possible choice for a question, particularly for multiple-choice or true/false questions.
 */
@Data // Includes @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor // Add a no-args constructor
@AllArgsConstructor // Add an all-args constructor
public class Choice {

    private String id;
    private String text;
    // private boolean isCorrect; // As per design doc, correctness might be handled elsewhere (e.g., in Question or scoring logic)

    // Validation logic can be moved to a builder or kept in constructors if needed.
    // For simplicity with Lombok, we'll assume validation happens elsewhere or in setters if @Data is used.
    // If validation is critical in the constructor, keep the AllArgsConstructor manual or use a builder.

    // Example of keeping validation in the AllArgsConstructor if needed:
    /*
    public Choice(String id, String text, boolean isCorrect) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Choice ID cannot be null or empty.");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Choice text cannot be null or empty.");
        }
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
    }
    */

}