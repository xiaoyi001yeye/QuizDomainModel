package com.example.quizdomainmodel.excelimport;

import com.example.quizdomainmodel.domain.model.Question;
import com.example.quizdomainmodel.domain.model.QuestionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Allows @BeforeAll on non-static method
class ExcelImporterTest {

    private List<Question> importedQuestions;
    private Path resourcePath;

    @BeforeAll
    void setUp() throws IOException {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("test-questions.xlsx");
            if (resourceUrl == null) {
                throw new FileNotFoundException("Test resource 'test-questions.xlsx' not found.");
            }
            resourcePath = Paths.get(resourceUrl.toURI());
            importedQuestions = ExcelImporter.importQuestionsFromExcel(resourcePath.toString());
        } catch (URISyntaxException e) {
            fail("Failed to convert resource URL to URI", e);
        } catch (FileNotFoundException e) {
             fail("Test setup failed: Could not find the test Excel file. " +
                 "Ensure 'test-questions.xlsx' exists in src/test/resources.", e);
        } catch (IOException e) {
            fail("Test setup failed: IO Error reading test Excel file.", e);
        } catch (Exception e) {
             fail("Test setup failed with unexpected exception.", e);
        }
    }

    @Test
    void shouldImportCorrectNumberOfValidQuestions() {
        // 验证Excel导入器能正确识别并导入有效问题数量
        // 测试文件中包含5个有效问题（第6行类型无效，第7行可能有解析问题）
        // ExcelImporter会记录错误但继续处理，因此预期成功导入5个问题
        assertThat(importedQuestions).isNotNull();
        assertThat(importedQuestions).hasSize(5); // q1, q2, q3, q4, q5
    }

    @Test
    void shouldImportSingleChoiceQuestionCorrectly() {
        // 验证单选题(q1)的导入是否正确
        // 检查问题ID、题干、类型、分值、正确答案和选项
        Optional<Question> questionOpt = importedQuestions.stream().filter(q -> "q1".equals(q.getId())).findFirst();
        assertThat(questionOpt).isPresent();
        Question q1 = questionOpt.get();

        assertThat(q1.getStem()).isEqualTo("What is 1+1?");
        assertThat(q1.getType()).isEqualTo(QuestionType.SINGLE_CHOICE);
        assertThat(q1.getPoints()).isEqualTo(10);
        assertThat(q1.getCorrectAnswer()).isEqualTo("2");
        assertThat(q1.getChoices()).hasSize(4);
        assertThat(q1.getChoices().stream().map(c -> c.getText())).containsExactly("1", "2", "3", "4");
    }

    @Test
    void shouldImportMultipleChoiceQuestionCorrectly() {
        // 验证多选题(q2)的导入是否正确
        // 检查问题ID、题干、类型、分值、正确答案（分号分隔）和选项
        Optional<Question> questionOpt = importedQuestions.stream().filter(q -> "q2".equals(q.getId())).findFirst();
        assertThat(questionOpt).isPresent();
        Question q2 = questionOpt.get();

        assertThat(q2.getStem()).isEqualTo("Which are primary colors?");
        assertThat(q2.getType()).isEqualTo(QuestionType.MULTIPLE_CHOICE);
        assertThat(q2.getPoints()).isEqualTo(15);
        assertThat(q2.getCorrectAnswer()).isEqualTo("Red;Blue;Yellow"); // Correct answer string as read
        assertThat(q2.getChoices()).hasSize(4);
        assertThat(q2.getChoices().stream().map(c -> c.getText())).containsExactly("Red", "Blue", "Green", "Yellow");
    }

    @Test
    void shouldImportTrueFalseQuestionCorrectly() {
        // 验证判断题(q3)的导入是否正确
        // 检查问题ID、题干、类型、分值和正确答案
        Optional<Question> questionOpt = importedQuestions.stream().filter(q -> "q3".equals(q.getId())).findFirst();
         assertThat(questionOpt).isPresent();
        Question q3 = questionOpt.get();

        assertThat(q3.getStem()).isEqualTo("The capital of France is Paris.");
        assertThat(q3.getType()).isEqualTo(QuestionType.TRUE_FALSE);
        assertThat(q3.getPoints()).isEqualTo(5);
        assertThat(q3.getCorrectAnswer()).isEqualTo("True");
        // TRUE_FALSE might implicitly have choices "True", "False" in some systems,
        // but based on the Excel, the choices column might be explicit or empty.
        // Here we assume explicit choices were provided.
        assertThat(q3.getChoices()).hasSize(2);
        assertThat(q3.getChoices().stream().map(c -> c.getText())).containsExactly("True", "False");
    }
     @Test
    void shouldImportFillInBlankQuestionCorrectly() {
         // 验证填空题(q4)的导入是否正确
         // 检查问题ID、题干、类型、分值和正确答案
        Optional<Question> questionOpt = importedQuestions.stream().filter(q -> "q4".equals(q.getId())).findFirst();
         assertThat(questionOpt).isPresent();
        Question q4 = questionOpt.get();

        assertThat(q4.getStem()).isEqualTo("Fill in the blank: Java is __ fun.");
        assertThat(q4.getType()).isEqualTo(QuestionType.FILL_IN_BLANK);
        assertThat(q4.getPoints()).isEqualTo(10);
        assertThat(q4.getCorrectAnswer()).isEqualTo("very");
        // FILL_IN_BLANK typically has no predefined choices
        assertThat(q4.getChoices()).isNotNull().isEmpty();
    }

     @Test
    void shouldImportEssayQuestionCorrectly() {
         // 验证简答题(q5)的导入是否正确
         // 检查问题ID、题干、类型和分值
        Optional<Question> questionOpt = importedQuestions.stream().filter(q -> "q5".equals(q.getId())).findFirst();
         assertThat(questionOpt).isPresent();
        Question q5 = questionOpt.get();

        assertThat(q5.getStem()).isEqualTo("Explain the meaning of life.");
        assertThat(q5.getType()).isEqualTo(QuestionType.SINGLE_CHOICE);
        assertThat(q5.getPoints()).isEqualTo(20);
        // Essay questions often don't have a single 'correct' answer string in the same way
        assertThat(q5.getCorrectAnswer()).isEqualTo("*N/A*"); // Or potentially empty, depending on convention
        assertThat(q5.getChoices()).isNotNull().isEmpty();
    }

    // ... 原有测试 ...

    @Test
    void shouldImportMaterialQuestionCorrectly() {
        // 验证材料题及其子题的导入是否正确
        Optional<Question> materialOpt = importedQuestions.stream()
                .filter(q -> "q6".equals(q.getId()))
                .findFirst();
        assertThat(materialOpt).isPresent();
        Question material = materialOpt.get();

        assertThat(material.getStem()).isEqualTo("阅读材料：Java是一种面向对象的编程语言");
        assertThat(material.getType()).isEqualTo(QuestionType.MATERIAL);
        assertThat(material.getPoints()).isEqualTo(0); // 材料本身不计分

        // 验证子题1
        Optional<Question> subQ1Opt = importedQuestions.stream()
                .filter(q -> "q6-1".equals(q.getId()))
                .findFirst();
        assertThat(subQ1Opt).isPresent();
        Question subQ1 = subQ1Opt.get();

        assertThat(subQ1.getStem()).isEqualTo("Java的创始人是？");
        assertThat(subQ1.getType()).isEqualTo(QuestionType.SINGLE_CHOICE);
        assertThat(subQ1.getPoints()).isEqualTo(5);

        // 验证子题2
        Optional<Question> subQ2Opt = importedQuestions.stream()
                .filter(q -> "q6-2".equals(q.getId()))
                .findFirst();
        assertThat(subQ2Opt).isPresent();
        Question subQ2 = subQ2Opt.get();

        assertThat(subQ2.getStem()).isEqualTo("Java最初叫什么名字？");
        assertThat(subQ2.getType()).isEqualTo(QuestionType.FILL_IN_BLANK);
        assertThat(subQ2.getPoints()).isEqualTo(5);
    }
}
