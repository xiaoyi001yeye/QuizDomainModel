package com.example.quizdomainmodel.domain.model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void testQuestionCreation() {
        Question question = new Question(
            "What is Java?",
            QuestionType.SINGLE_CHOICE,
            Arrays.asList(new Choice("A programming language"), new Choice("A coffee brand")),
            "A programming language",
            5
        );
        
        assertNotNull(question.getId());
        assertEquals("What is Java?", question.getStem());
        assertEquals(QuestionType.SINGLE_CHOICE, question.getType());
        assertEquals(2, question.getChoices().size());
        assertEquals("A programming language", question.getCorrectAnswer());
        assertEquals(5, question.getPoints());
    }

    @Test
    void testEmptyId() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Question(
                "",
                QuestionType.FILL_IN_BLANK,
                Collections.emptyList(),
                "Java",
                3
            )
        );
    }

    @Test
    void testNullType() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Question(
                "What is Java?",
                null,
                Collections.emptyList(),
                "Java",
                3
            )
        );
    }

    @Test
    void testNullChoices() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Question(
                "What is Java?",
                QuestionType.SINGLE_CHOICE,
                null,
                "Java",
                3
            )
        );
    }

    @Test
    void testNullCorrectAnswer() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Question(
                "What is Java?",
                QuestionType.FILL_IN_BLANK,
                Collections.emptyList(),
                null,
                3
            )
        );
    }

    @Test
    void testNegativePoints() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Question(
                "What is Java?",
                QuestionType.FILL_IN_BLANK,
                Collections.emptyList(),
                "Java",
                -1
            )
        );
    }

    @Test
    void testChoiceBasedQuestionWithNoChoices() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Question(
                "What is Java?",
                QuestionType.SINGLE_CHOICE,
                Collections.emptyList(),
                "Java",
                3
            )
        );
    }

    @Test
    void testSetPoints() {
        Question question = new Question(
            "What is Java?",
            QuestionType.FILL_IN_BLANK,
            Collections.emptyList(),
            "Java",
            3
        );
        
        question.setPoints(5);
        assertEquals(5, question.getPoints());
        
        assertThrows(IllegalArgumentException.class, () -> question.setPoints(-1));
    }
}