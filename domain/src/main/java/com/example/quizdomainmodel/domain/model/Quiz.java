package com.example.quizdomainmodel.domain.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a quiz, which is a collection of questions.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id") // Ensure equality is based only on ID, matching previous behavior
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Private no-args constructor for frameworks
public class Quiz {

    private String id;
    private String title;
    private String description;
    // Mark for custom getter/setter
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) private List<Question> questions;

    /**
     * Creates a new Quiz.
     * Keeping this constructor for validation and defensive copy.
     *
     * @param title The title of the quiz. Cannot be null or empty.
     * @param description A description of the quiz (can be null or empty).
     * @param questions The list of questions for this quiz. Cannot be null (can be empty).
     */
    public Quiz(String title, String description, List<Question> questions) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz title cannot be null or empty.");
        }
        // questions validation is handled by setQuestions

        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        // Use custom setter for validation and defensive copy
        setQuestions(questions);
    }

    /**
     * Returns an unmodifiable view of the questions list.
     * Keeping custom getter to enforce immutability.
     * @return Unmodifiable list of questions.
     */
    public List<Question> getQuestions() {
        // Return an unmodifiable list to prevent external modification
        // Ensure list is initialized before returning
        return Collections.unmodifiableList(this.questions != null ? this.questions : Collections.emptyList());
    }

    /**
     * Custom setter for questions to perform defensive copying.
     */
    public void setQuestions(List<Question> questions) {
        if (questions == null) {
            throw new IllegalArgumentException("Questions list cannot be null.");
        }
        this.questions = new ArrayList<>(questions);
    }

    /**
     * Adds a question to the quiz.
     * @param question The question to add. Cannot be null.
     */
    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Cannot add a null question.");
        }
        // Ensure list is initialized
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }
        // Prevent duplicates based on ID? Or allow? For now, allow.
        this.questions.add(question);
    }

    /**
     * Removes a question from the quiz by its ID.
     * @param questionId The ID of the question to remove.
     * @return true if a question was removed, false otherwise.
     */
    public boolean removeQuestion(String questionId) {
        if (questionId == null || this.questions == null) {
            return false;
        }
        return this.questions.removeIf(q -> questionId.equals(q.getId()));
    }
}