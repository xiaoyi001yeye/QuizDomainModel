package com.example.quizdomainmodel.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a single question within a quiz.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    private String id;
    private String stem; 
    private QuestionType type;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) private List<Choice> choices;
    private Object correctAnswer; 
    @Setter(AccessLevel.NONE) private int points; 

    /**
     * Creates a new Question with auto-generated ID.
     *
     * @param stem The question text. Cannot be null or empty.
     * @param type The type of the question. Cannot be null.
     * @param choices The list of choices (can be empty for FILL_IN_BLANK). Cannot be null.
     * @param correctAnswer The correct answer representation. Cannot be null.
     * @param points The points value for this question. Must be >= 0.
     */
    public Question(String stem, QuestionType type, List<Choice> choices, Object correctAnswer, int points) {
        this(UUID.randomUUID().toString(), stem, type, choices, correctAnswer, points);
    }

    /**
     * Creates a new Question with specified ID.
     *
     * @param id The unique identifier for this question
     * @param stem The question text. Cannot be null or empty.
     * @param type The type of the question. Cannot be null.
     * @param choices The list of choices (can be empty for FILL_IN_BLANK). Cannot be null.
     * @param correctAnswer The correct answer representation. Cannot be null.
     * @param points The points value for this question. Must be >= 0.
     */
    public Question(String id, String stem, QuestionType type, List<Choice> choices, Object correctAnswer, int points) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Question ID cannot be null or empty.");
        }
        if (stem == null || stem.trim().isEmpty()) {
            throw new IllegalArgumentException("Question stem cannot be null or empty.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Question type cannot be null.");
        }
        if (choices == null) {
            throw new IllegalArgumentException("Choices list cannot be null.");
        }
        if (correctAnswer == null) {
            throw new IllegalArgumentException("Correct answer cannot be null.");
        }
        setPoints(points);

        if ((type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE || type == QuestionType.TRUE_FALSE) && choices.isEmpty()) {
             throw new IllegalArgumentException("Choice-based questions must have at least one choice.");
        }

        this.id = id;
        this.stem = stem;
        this.type = type;
        this.choices = new ArrayList<>(choices);
        this.correctAnswer = correctAnswer; 
    }

    public List<Choice> getChoices() {
        return Collections.unmodifiableList(this.choices);
    }

    public void setChoices(List<Choice> choices) {
        if (choices == null) {
            throw new IllegalArgumentException("Choices list cannot be null.");
        }
        this.choices = new ArrayList<>(choices);
    }

    public void setPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative.");
        }
        this.points = points;
    }

    // Potential future methods:
    // - addChoice(Choice choice)
    // - removeChoice(String choiceId)
    // - setCorrectAnswer(...) with type checking
}