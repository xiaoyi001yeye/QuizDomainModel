package com.example.quizdomainmodel.domain.model;

/**
 * Represents the type of a question in the quiz.
 */
public enum QuestionType {
    /**
     * Single choice question, only one answer is correct.
     */
    SINGLE_CHOICE,

    /**
     * Multiple choice question, multiple answers can be correct.
     */
    MULTIPLE_CHOICE,

    /**
     * True/False question.
     */
    TRUE_FALSE,

    /**
     * Fill in the blank question.
     */
    FILL_IN_BLANK,

    /**
     * Reading comprehension question with sub-questions
     */
    READING
}