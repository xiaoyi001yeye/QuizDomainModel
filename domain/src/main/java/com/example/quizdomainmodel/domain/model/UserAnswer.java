package com.example.quizdomainmodel.domain.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a user's answer to a single question within an AnswerSheet.
 * This class is designed to hold the submitted answer data, not necessarily the correctness.
 */
@Getter // Generate getters for all fields unless overridden
@ToString
@EqualsAndHashCode // Generate standard equals/hashCode based on all fields
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Provide the private no-args constructor
public class UserAnswer {

    private String questionId; // The ID of the question this answer corresponds to
    @Getter(AccessLevel.NONE) private List<String> selectedChoiceIds; // Custom getter below
    private String filledText; // The text entered by the user (for FILL_IN_BLANK)
    @Getter(AccessLevel.NONE) private List<UserAnswer> subAnswers; // Custom getter below

    /**
     * Constructor for choice-based answers. Kept for logic control.
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
        // Defensive copy
        this.selectedChoiceIds = new ArrayList<>(selectedChoiceIds);
        this.filledText = null; // Not applicable for this constructor
        // Initialize to empty list for consistency
        this.subAnswers = Collections.emptyList();
    }

    /**
     * Factory method for choice-based answers. Kept.
     */
    public static UserAnswer createChoiceAnswer(String questionId, List<String> selectedChoiceIds) {
        return new UserAnswer(questionId, selectedChoiceIds);
    }

    /**
     * Constructor for fill-in-the-blank answers. Kept.
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
     * Returns an unmodifiable view of the selected choice IDs.
     * Keeping custom getter for immutability.
     * @return Unmodifiable list of selected choice IDs.
     */
    public List<String> getSelectedChoiceIds() {
        // Ensure list is initialized before returning
        return Collections.unmodifiableList(this.selectedChoiceIds != null ? this.selectedChoiceIds : Collections.emptyList());
    }

    /**
     * Returns an unmodifiable view of the sub-answers (for questions with sub-questions).
     * Keeping custom getter for immutability.
     * @return Unmodifiable list of sub-answers.
     */
    public List<UserAnswer> getSubAnswers() {
        // Ensure list is initialized before returning
        return Collections.unmodifiableList(this.subAnswers != null ? this.subAnswers : Collections.emptyList());
    }
}