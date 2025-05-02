package com.example.quizdomainmodel.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a material question that contains sub-questions (e.g., for reading comprehension).
 * Extends the base Question class with additional functionality for managing sub-questions.
 */
public class MaterialQuestion extends Question {

    private List<Question> subQuestions;

    /**
     * Creates a new MaterialQuestion with auto-generated ID.
     *
     * @param stem The question text. Cannot be null or empty.
     * @param choices The list of choices (can be empty for FILL_IN_BLANK). Cannot be null.
     * @param correctAnswer The correct answer representation. Cannot be null.
     * @param points The points value for this question. Must be >= 0.
     * @param subQuestions The list of sub-questions associated with this material question
     */
    public MaterialQuestion(String stem, List<Choice> choices, Object correctAnswer, int points, List<Question> subQuestions) {
        this(UUID.randomUUID().toString(), stem, choices, correctAnswer, points, subQuestions);
    }

    /**
     * Creates a new MaterialQuestion with specified ID.
     *
     * @param id The unique identifier for this question
     * @param stem The question text. Cannot be null or empty.
     * @param choices The list of choices (can be empty for FILL_IN_BLANK). Cannot be null.
     * @param correctAnswer The correct answer representation. Cannot be null.
     * @param points The points value for this question. Must be >= 0.
     * @param subQuestions The list of sub-questions associated with this material question
     */
    public MaterialQuestion(String id, String stem, List<Choice> choices, Object correctAnswer, int points, List<Question> subQuestions) {
        super(id, stem, QuestionType.READING, choices, correctAnswer, points);
        
        if (subQuestions == null) {
            throw new IllegalArgumentException("Sub-questions list cannot be null.");
        }
        
        // Create a defensive copy of the sub-questions list
        this.subQuestions = new ArrayList<>(subQuestions);
    }

    /**
     * Default constructor for frameworks.
     */
    protected MaterialQuestion() {
        super();
        this.subQuestions = new ArrayList<>();
    }

    /**
     * Returns an unmodifiable view of the sub-questions list.
     * @return Unmodifiable list of sub-questions.
     */
    public List<Question> getSubQuestions() {
        return Collections.unmodifiableList(subQuestions);
    }

    /**
     * Adds a sub-question to this material question.
     * @param subQuestion The sub-question to add. Cannot be null.
     */
    public void addSubQuestion(Question subQuestion) {
        if (subQuestion == null) {
            throw new IllegalArgumentException("Cannot add a null sub-question.");
        }
        this.subQuestions.add(subQuestion);
    }

    /**
     * Removes a sub-question by its ID.
     * @param questionId The ID of the sub-question to remove.
     * @return true if a sub-question was removed, false otherwise.
     */
    public boolean removeSubQuestion(String questionId) {
        if (questionId == null) {
            return false;
        }
        return this.subQuestions.removeIf(q -> questionId.equals(q.getId()));
    }

    /**
     * Calculates the total possible score for this material question based on sub-questions.
     * @return The sum of points from all sub-questions.
     */
    public int calculateTotalScore() {
        int total = 0;
        for (Question subQuestion : subQuestions) {
            total += subQuestion.getPoints();
        }
        return total;
    }

    /**
     * Returns the total score for this material question.
     * @return The total score, which is the sum of sub-questions' points.
     */
    public int getTotalScore() {
        return calculateTotalScore();
    }
}