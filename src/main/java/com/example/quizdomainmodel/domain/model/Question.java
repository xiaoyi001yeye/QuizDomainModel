package com.example.quizdomainmodel.domain.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a single question within a quiz.
 */
@Data
public class Question {

    private String id;
    private String stem; // The actual text/content of the question
    private QuestionType type;
    private List<Choice> choices; // List of possible choices (relevant for SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE)
    private List<Question> subQuestions; // List of sub-questions for READING type
    private Object correctAnswer; // Representation of the correct answer(s). Type depends on QuestionType.
    private int points; // 题目分值

    /**
     * Protected constructor for frameworks and subclasses.
     */
    protected Question() {}

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
            // Allow empty list for certain types, but not null
            throw new IllegalArgumentException("Choices list cannot be null.");
        }
        if (correctAnswer == null) {
            throw new IllegalArgumentException("Correct answer cannot be null.");
        }
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative.");
        }

        // Basic validation based on type (can be expanded)
        if ((type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE || type == QuestionType.TRUE_FALSE) && choices.isEmpty()) {
             throw new IllegalArgumentException("Choice-based questions must have at least one choice.");
        }

        this.id = id;
        this.stem = stem;
        this.type = type;
        // Create a defensive copy of the choices list
        this.choices = new ArrayList<>(choices);
        this.correctAnswer = correctAnswer; // Store the provided answer object
        this.points = points;
    }

    /**
     * Sets the points value for this question.
     * @param points The new points value. Must be >= 0.
     */
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