# 在线测验系统核心模块

本模块实现了一个基于Java的测验系统领域模型，包含问题管理、选项验证和自动评分功能。

## 项目结构

```
src/main/java/
└── com/example/quizdomainmodel/domain/model/
    ├── Question.java         # 问题实体（包含题目内容和选项）
    ├── Choice.java           # 选项实体（标记正确答案）
    ├── Quiz.java             # 测验聚合根（管理问题集合）
    ├── UserAnswer.java       # 用户答案记录
    ├── AnswerSheet.java      # 自动评分逻辑
    ├── QuestionType.java     # 题目类型枚举（单选/多选/判断/填空/阅读理解）
    └── MaterialQuestion.java # 阅读理解题实体（继承Question）
```

## 核心类说明

### QuestionType.java
- 定义题目类型枚举
- 包含类型：
  - SINGLE_CHOICE（单选）
  - MULTIPLE_CHOICE（多选）
  - TRUE_FALSE（判断）
  - FILL_IN_BLANK（填空）
  - READING（阅读理解）

### Question.java
- 管理题目内容、选项和分值
- 关键字段：
  ```java
  private String questionText;    // 题目内容
  private List<Choice> choices;   // 选项列表
  private int points;             // 题目分值
  private QuestionType type;      // 题目类型（新增字段）
  ```

### MaterialQuestion.java
- 阅读理解题实体类
- 继承自Question
- 新增字段：
  ```java
  private String materialText;    // 阅读材料内容
  private int wordLimit;          // 作答字数限制
  ```

### Choice.java
- 定义题目选项及正确性标记
- 核心属性：
  ```java
  private String choiceText;     // 选项内容
  private boolean isCorrect;     // 正确性标记
  ```

### 评分流程
1. 用户通过UserAnswer提交答案
2. AnswerSheet验证答案并计算分数：
   ```java
   public class AnswerSheet {
       public int calculateScore(Quiz quiz, UserAnswer userAnswer) {
           int total = 0;
           for (Question question : quiz.getQuestions()) {
               if (userAnswer.getSelectedChoice(question).isCorrect()) {
                   total += question.getPoints();
               }
           }
           return total;
       }
   }
   ```

## 使用示例
```java
// 创建问题
Question question = new Question("Java接口的关键字？");
question.setPoints(5);
question.setType(QuestionType.SINGLE_CHOICE); // 设置题目类型
question.addChoice(new Choice("interface", true));
question.addChoice(new Choice("class", false));

// 用户选择答案
UserAnswer userAnswer = new UserAnswer();
userAnswer.selectChoice(question, question.getChoices().get(0));

// 计算得分
Quiz quiz = new Quiz();
quiz.addQuestion(question);
int score = new AnswerSheet().calculateScore(quiz, userAnswer);
System.out.println("得分：" + score); // 输出: 5
```

## 构建与测试
```bash
# 编译项目
mvn clean install

# 运行测试
mvn test
```

> 项目要求：Java 11+，Maven 3.6+
