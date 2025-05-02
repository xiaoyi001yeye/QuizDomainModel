package com.example.quizdomainmodel.excelimport;

import com.example.quizdomainmodel.domain.model.Choice;
import com.example.quizdomainmodel.domain.model.Question;
import com.example.quizdomainmodel.domain.model.QuestionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExcelImporter {

    public static List<Question> importQuestionsFromExcel(String filePath) throws IOException {
        List<Question> questions = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                
                try {
                    Question question = buildQuestionFromRow(row);
                    questions.add(question);
                } catch (Exception e) {
                    System.err.println("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
                }
            }
        }
        
        return questions;
    }

    private static Question buildQuestionFromRow(Row row) {
        String id = getCellStringValue(row, 0);
        String stem = getCellStringValue(row, 1);
        String typeStr = getCellStringValue(row, 2);
        String choicesStr = getCellStringValue(row, 3);
        String correctAnswerStr = getCellStringValue(row, 4);
        int points = (int) getCellNumericValue(row, 5);
        
        QuestionType type = QuestionType.valueOf(typeStr.toUpperCase().replace(' ', '_'));
        
        List<Choice> choices = parseChoices(choicesStr);
        
        return new Question(id, stem, type, choices, correctAnswerStr, points);
    }

    private static String getCellStringValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return "";
        }
        return cell.getStringCellValue().trim();
    }

    private static double getCellNumericValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return 0;
        }
        return cell.getNumericCellValue();
    }

    private static List<Choice> parseChoices(String choicesStr) {
        List<Choice> choices = new ArrayList<>();
        if (choicesStr == null || choicesStr.isEmpty()) {
            return choices;
        }
        
        String[] choiceStrings = choicesStr.split(";");
        for (String choiceStr : choiceStrings) {
            String trimmed = choiceStr.trim();
            if (!trimmed.isEmpty()) {
                choices.add(new Choice(UUID.randomUUID().toString(), trimmed));
            }
        }
        
        return choices;
    }

    public static void printQuestionsAsJson(List<Question> questions) throws IOException {
        ObjectMapper mapper = new JacksonObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(questions);
        System.out.println(json);
    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: ExcelImporter <excel-file-path>");
            System.exit(1);
        }
        
        try {
            List<Question> questions = importQuestionsFromExcel(args[0]);
            printQuestionsAsJson(questions);
        } catch (Exception e) {
            System.err.println("Error importing Excel file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}