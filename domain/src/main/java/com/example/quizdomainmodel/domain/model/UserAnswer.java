package com.example.quizdomainmodel.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user's answer to a single question within an AnswerSheet.
 * This class is designed to hold the submitted answer data, not necessarily the correctness.
 */
public class UserAnswer {

    private String questionId; // The ID of the question this answer corresponds to
    private List<String> selectedChoiceIds; // List of selected choice IDs (for SINGLE/MULTIPLE_CHOICE, TRUE_FALSE)
    private String filledText; // The text entered by the user (for FILL_IN_BLANK)
    private List<UserAnswer> subAnswers; // Answers for sub-questions (for READING type questions)

    /**
     * Private constructor for frameworks.
     */
    private UserAnswer() {}

    /**
     * Constructor for choice-based answers.
     *
     * @param questionId The ID of the related question. Cannot be null.
     * @param selectedChoiceIds The list of selected choice IDs. Cannot be null (can be empty).
     */
    private UserAnswer(String questionId, List<String> selectedChoiceIds) {
        if (questionId == null) {
            throw new IllegalArgumentException("Question ID cannot be null.");
        }
        if (selectedChoiceIds == null) {
            throw new IllegalArgumentException("Selected choice IDs list cannot be null.");
        }
        this.questionId = questionId;
        this.selectedChoiceIds = new ArrayList<>(selectedChoiceIds); // Defensive copy
        this.filledText = null; // Not applicable for this constructor
        this.subAnswers = Collections.emptyList(); // No sub-questions for this type
    }
    
    /**
     * Factory method for choice-based answers.
     */
    public static UserAnswer createChoiceAnswer(String questionId, List<String> selectedChoiceIds) {
        return new UserAnswer(questionId, selectedChoiceIds);
    }

    /**
     * Constructor for fill-in-the-blank answers.
     *
     * @param questionId The ID of the related question. Cannot be null.
     * @param filledText The text entered by the user. Can be null or empty.
     */
    public UserAnswer(String questionId, String filledText) {
        if (questionId == null) {
            throw new IllegalArgumentException("Question ID cannot be null.");
        }
        this.questionId = questionId;
        this.selectedChoiceIds = Collections.emptyList(); // Not applicable
        this.filledText = filledText;
        this.subAnswers = Collections.emptyList(); // No sub-questions for this type
    }

    /**
     * Constructor for answers to questions with sub-questions (e.g., READING type).
     *
     * @param questionId The ID of the related question. Cannot be null.
     * @param subAnswers List of answers to sub-questions. Cannot be null.
     */
    private UserAnswer(String questionId, List<UserAnswer> subAnswers, boolean forSubAnswers) {
        if (questionId == null) {
            throw new IllegalArgumentException("Question ID cannot be null.");
        }
        if (subAnswers == null) {
            throw new IllegalArgumentException("Sub-answers list cannot be null.");
        }
        this.questionId = questionId;
        this.subAnswers = new ArrayList<>(subAnswers); // Defensive copy
        this.selectedChoiceIds = Collections.emptyList(); // Not applicable
        this.filledText = null; // Not applicable
    }

    // --- Getters ---

    public String getQuestionId() {
        return questionId;
    }

    /**
     * Returns an unmodifiable view of the selected choice IDs.
     * @return Unmodifiable list of selected choice IDs.
     */
    public List<String> getSelectedChoiceIds() {
        return Collections.unmodifiableList(selectedChoiceIds);
    }

    public String getFilledText() {
        return filledText;
    }

    /**
     * Returns an unmodifiable view of the sub-answers (for questions with sub-questions).
     * @return Unmodifiable list of sub-answers.
     */
    public List<UserAnswer> getSubAnswers() {
        return Collections.unmodifiableList(subAnswers);
    }

    // --- Standard Object methods ---

    // Note: Equality and hashCode might need refinement depending on use case.
    // Basing it on questionId alone might be insufficient if multiple attempts are stored.
    // For now, let's base it on content.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAnswer that = (UserAnswer) o;
        return Objects.equals(questionId, that.questionId) &&
               Objects.equals(selectedChoiceIds, that.selectedChoiceIds) &&
               Objects.equals(filledText, that.filledText) &&
               Objects.equals(subAnswers, that.subAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, selectedChoiceIds, filledText, subAnswers);
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
               "questionId='" + questionId + '\'' +
               ", selectedChoiceIds=" + selectedChoiceIds +
               ", filledText='" + filledText + '\'' +
               ", subAnswers=" + subAnswers +
               '}';
    }
}