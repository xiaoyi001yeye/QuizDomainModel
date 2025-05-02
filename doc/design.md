# 试题领域模型设计文档

## 1. 项目目标

设计并实现一个基于 Java 的领域模型，用于表示和管理在线测验系统中的试题、选项、测验及其相关的答题数据。

## 2. 核心领域概念

*   **Question (问题):** 代表测验中的一个单独题目。
    *   属性:
        *   `id`: 唯一标识符 (例如 UUID 或 Long)
        *   `stem`: 问题题干 (String)
        *   `type`: 问题类型 (枚举 `QuestionType`: SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE, FILL_IN_BLANK)
        *   `choices`: 问题的选项列表 (List<Choice>)，仅适用于选择题和判断题。
        *   `correctAnswer`: 正确答案的表示 (根据类型可能不同，例如 Choice ID 列表，布尔值，或标准答案文本)。
*   **Choice (选项):** 代表选择题或判断题的一个选项。
    *   属性:
        *   `id`: 唯一标识符 (例如 UUID 或 Long)
        *   `text`: 选项内容 (String)
        *   `isCorrect`: 标记该选项是否为正确答案的一部分 (boolean)，主要用于选择题。判断题可通过特定 Choice 文本或 `isCorrect` 区分。
*   **Quiz (测验):** 代表一组问题构成的完整测验。
    *   属性:
        *   `id`: 唯一标识符 (例如 UUID 或 Long)
        *   `title`: 测验标题 (String)
        *   `description`: 测验描述 (String)
        *   `questions`: 测验包含的问题列表 (List<Question>)
*   **AnswerSheet (答卷):** 代表用户完成一次测验的记录。
    *   属性:
        *   `id`: 唯一标识符 (例如 UUID 或 Long)
        *   `quizId`: 关联的测验 ID
        *   `userId`: 提交答卷的用户 ID (String 或 Long)
        *   `submissionTime`: 提交时间 (Timestamp/LocalDateTime)
        *   `userAnswers`: 用户对每个问题的回答映射 (Map<QuestionId, UserAnswer>)
*   **UserAnswer (用户答案):** 代表用户对单个问题的具体回答。
    *   属性:
        *   `questionId`: 关联的问题 ID
        *   `selectedChoiceIds`: 用户选择的选项 ID 列表 (List<ChoiceId>)，适用于选择题和判断题。
        *   `filledText`: 用户填写的答案文本 (String)，适用于填空题。
        *   `isCorrect`: (计算得出) 该回答是否正确。

## 3. 关系

*   `Quiz` 与 `Question`: 一对多 (一个测验包含多个问题)。在 `Quiz` 类中通过 `List<Question>` 体现。
*   `Question` 与 `Choice`: 一对多 (一个问题可以有多个选项)。在 `Question` 类中通过 `List<Choice>` 体现。
*   `AnswerSheet` 与 `Quiz`: 多对一 (多份答卷可以对应同一个测验)。通过 `quizId` 关联。
*   `AnswerSheet` 与 `UserAnswer`: 一对多 (一份答卷包含多个用户答案)。通过 `Map<QuestionId, UserAnswer>` 体现。
*   `UserAnswer` 与 `Question`: 多对一 (多个用户答案可以对应同一个问题)。通过 `questionId` 关联。
*   `UserAnswer` 与 `Choice`: 多对多 (一个用户答案可能选择多个选项，一个选项可能被多个答案选择)。通过 `selectedChoiceIds` 列表体现。

## 4. 枚举类型

*   **QuestionType:**
    *   `SINGLE_CHOICE` (单选题)
    *   `MULTIPLE_CHOICE` (多选题)
    *   `TRUE_FALSE` (判断题)
    *   `FILL_IN_BLANK` (填空题)

## 5. 初步类结构 (Java 伪代码)

```java
enum QuestionType { SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE, FILL_IN_BLANK }

class Choice {
    String id;
    String text;
    // boolean isCorrect; // 正确性判断可能移到 Question 或 AnswerSheet 评分逻辑中
}

class Question {
    String id;
    String stem; // Changed from text
    QuestionType type;
    List<Choice> choices;
    // Object correctAnswer; // 具体类型取决于 QuestionType
}

class Quiz {
    String id;
    String title;
    String description;
    List<Question> questions;
}

class UserAnswer {
    String questionId;
    List<String> selectedChoiceIds; // Choice IDs
    String filledText;
}

class AnswerSheet {
    String id;
    String quizId;
    String userId;
    LocalDateTime submissionTime;
    Map<String, UserAnswer> userAnswers; // Key: Question ID

    // Optional: Method to calculate score
    // int calculateScore();
}
```

## 6. 后续考虑

*   ID 生成策略 (UUID, 数据库自增等)。
*   添加验证逻辑 (例如，确保单选题只有一个答案)。
*   实现评分逻辑。
*   持久化方案 (JPA, MyBatis等)。
*   包结构设计。

## 7. 建议包结构

```
com.example.quizdomainmodel
├── domain
│   ├── model          // 核心领域模型类
│   │   ├── Choice.java
│   │   ├── Question.java
│   │   ├── QuestionType.java (enum)
│   │   ├── Quiz.java
│   │   ├── UserAnswer.java
│   │   └── AnswerSheet.java
│   ├── service        // 领域服务 (例如，评分逻辑)
│   │   └── ScoringService.java
│   └── repository     // 仓库接口 (定义数据访问契约)
│       ├── QuizRepository.java
│       └── AnswerSheetRepository.java
├── application      // 应用层 (用例、应用服务)
│   └── QuizApplicationService.java
├── infrastructure   // 基础设施层 (具体实现)
│   ├── persistence    // 持久化实现 (例如 JPA 实现 Repository 接口)
│   │   ├── JpaQuizRepository.java
│   │   └── JpaAnswerSheetRepository.java
│   └── config         // 配置类
└── main             // 程序入口 (如果需要)
    └── MainApplication.java