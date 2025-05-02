package com.example.quizdomainmodel.domain.model;

import lombok.Getter;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class AnswerSheet {

    private final String id;
    private final String quizId;
    private final String userId;
    private final long submissionTime;
    private final List<UserAnswer> userAnswers;

    

   

    /**
     * Calculates total score for the answer sheet.
     * Handles both regular questions and material questions with sub-questions.
     */
    public int calculateTotalScore(List<Question> quizQuestions) {
        int totalScore = 0;
        
        for (UserAnswer userAnswer : userAnswers) {
            for (Question question : quizQuestions) {
                if (question.getId().equals(userAnswer.getQuestionId())) {
                    // Handle material questions
                    if (question instanceof MaterialQuestion) {
                        MaterialQuestion materialQuestion = (MaterialQuestion) question;
                        // Add material question overall score
                        totalScore += materialQuestion.getTotalScore();
                        
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