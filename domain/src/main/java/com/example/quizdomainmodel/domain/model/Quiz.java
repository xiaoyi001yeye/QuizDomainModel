package com.example.quizdomainmodel.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a quiz, which is a collection of questions.
 */
public class Quiz {

    private String id;
    private String title;
    private String description;
    private List<Question> questions;

    /**
     * Private constructor for frameworks.
     */
    private Quiz() {}

    /**
     * Creates a new Quiz.
     *
     * @param title The title of the quiz. Cannot be null or empty.
     * @param description A description of the quiz (can be null or empty).
     * @param questions The list of questions for this quiz. Cannot be null (can be empty).
     */
    public Quiz(String title, String description, List<Question> questions) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz title cannot be null or empty.");
        }
        if (questions == null) {
            throw new IllegalArgumentException("Questions list cannot be null.");
        }

        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        // Create a defensive copy of the questions list
        this.questions = new ArrayList<>(questions);
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns an unmodifiable view of the questions list.
     * @return Unmodifiable list of questions.
     */
    public List<Question> getQuestions() {
        // Return an unmodifiable list to prevent external modification
        return Collections.unmodifiableList(questions);
    }

    // --- Business Methods (Examples) ---

    /**
     * Adds a question to the quiz.
     * @param question The question to add. Cannot be null.
     */
    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Cannot add a null question.");
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
        if (questionId == null) {
            return false;
        }
        return this.questions.removeIf(q -> questionId.equals(q.getId()));
    }


    // --- Standard Object methods ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return Objects.equals(id, quiz.id); // Equality based on ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash code based on ID
    }

    @Override
    public String toString() {
        return "Quiz{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", numberOfQuestions=" + questions.size() +
               '}';
    }
}