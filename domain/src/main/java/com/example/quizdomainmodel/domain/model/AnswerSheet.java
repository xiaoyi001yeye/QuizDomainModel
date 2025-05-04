package com.example.quizdomainmodel.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter // Keep getter for id, quizId, userId, submissionTime
@AllArgsConstructor // Keep constructor initializing all final fields
@ToString // Add toString generation
@EqualsAndHashCode // Add equals/hashCode generation
public class AnswerSheet {

    private final String id;
    private final String quizId;
    private final String userId;
    private final long submissionTime;
    // Customize getter for immutability
    @Getter(AccessLevel.NONE) private final List<UserAnswer> userAnswers;

    /**
     * Custom getter for userAnswers to return an unmodifiable list.
     */
    public List<UserAnswer> getUserAnswers() {
        return Collections.unmodifiableList(this.userAnswers != null ? this.userAnswers : Collections.emptyList());
    }

    /**
     * Calculates total score for the answer sheet.
     * Handles both regular questions and material questions with sub-questions.
     */
    public int calculateTotalScore(List<Question> quizQuestions) {
        int totalScore = 0;
        
        for (UserAnswer userAnswer : getUserAnswers()) {
            for (Question question : quizQuestions) {
                if (question.getId().equals(userAnswer.getQuestionId())) {
                    // Handle material questions
                    if (question instanceof MaterialQuestion) {
                        MaterialQuestion materialQuestion = (MaterialQuestion) question;
                        // Add material question overall score - FIX: Use calculateTotalScore()
                        totalScore += materialQuestion.calculateTotalScore();
                        
                        // Add individual sub-question scores
                        for (Question subQuestion : materialQuestion.getSubQuestions()) {
                            for (UserAnswer subAnswer : userAnswer.getSubAnswers()) {
                                if (subAnswer.getQuestionId().equals(subQuestion.getId())) {
                                    totalScore += calculateQuestionScore(subAnswer, subQuestion);
                                }
                            }
                        }
                    } 
                    // Handle regular questions
                    else {
                        totalScore += calculateQuestionScore(userAnswer, question);
                    }
                    break;
                }
            }
        }
        
        return totalScore;
    }

    /**
     * Calculates score for a single question based on user's answer.
     */
    private int calculateQuestionScore(UserAnswer userAnswer, Question question) {
        // Simple implementation - in real scenario would have more complex scoring logic
        if (userAnswer.getSelectedChoiceIds() != null && 
            userAnswer.getSelectedChoiceIds().equals(question.getCorrectAnswer())) {
            return question.getPoints();
        }
        return 0;
    }

    // Additional methods for answer sheet management could be added here
}