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
// 单选题示例
Question singleChoice = new Question("Java接口的关键字？");
singleChoice.setPoints(5);
singleChoice.setType(QuestionType.SINGLE_CHOICE);
singleChoice.addChoice(new Choice("interface", true));
singleChoice.addChoice(new Choice("class", false));
UserAnswer userAnswer1 = new UserAnswer();
userAnswer1.selectChoice(singleChoice, singleChoice.getChoices().get(0));
// 得分：5

// 多选题示例
Question multipleChoice = new Question("以下哪些是Java集合框架？");
multipleChoice.setPoints(10);
multipleChoice.setType(QuestionType.MULTIPLE_CHOICE);
multipleChoice.addChoice(new Choice("ArrayList", true));
multipleChoice.addChoice(new Choice("HashMap", true));
multipleChoice.addChoice(new Choice("String", false));
UserAnswer userAnswer2 = new UserAnswer();
userAnswer2.selectChoice(multipleChoice, multipleChoice.getChoices().get(0));
userAnswer2.selectChoice(multipleChoice, multipleChoice.getChoices().get(1));
// 得分：10

// 判断题示例
Question trueFalse = new Question("Java中String是不可变对象");
trueFalse.setPoints(3);
trueFalse.setType(QuestionType.TRUE_FALSE);
trueFalse.addChoice(new Choice("正确", true));
trueFalse.addChoice(new Choice("错误", false));
UserAnswer userAnswer3 = new UserAnswer();
userAnswer3.selectChoice(trueFalse, trueFalse.getChoices().get(0));
// 得分：3

// 填空题示例
Question fillInBlank = new Question("Java的跨平台特性通过______实现");
fillInBlank.setPoints(7);
fillInBlank.setType(QuestionType.FILL_IN_BLANK);
fillInBlank.addChoice(new Choice("JVM", true));
UserAnswer userAnswer4 = new UserAnswer();
userAnswer4.setTextAnswer(fillInBlank, "JVM");
// 得分：7

// 阅读理解题示例
MaterialQuestion reading = new MaterialQuestion();
reading.setMaterialText("Java是一种广泛使用的编程语言...");
reading.setQuestionText("根据材料，Java的主要优势是什么？");
reading.setPoints(15);
reading.setType(QuestionType.READING);
reading.setWordLimit(50);
UserAnswer userAnswer5 = new UserAnswer();
userAnswer5.setTextAnswer(reading, "跨平台特性和面向对象设计");
// 得分：15

// 计算总分
Quiz quiz = new Quiz();
quiz.addQuestion(singleChoice);
quiz.addQuestion(multipleChoice);
quiz.addQuestion(trueFalse);
quiz.addQuestion(fillInBlank);
quiz.addQuestion(reading);
int score = new AnswerSheet().calculateScore(quiz, userAnswer1);
score += new AnswerSheet().calculateScore(quiz, userAnswer2);
score += new AnswerSheet().calculateScore(quiz, userAnswer3);
score += new AnswerSheet().calculateScore(quiz, userAnswer4);
score += new AnswerSheet().calculateScore(quiz, userAnswer5);
System.out.println("总得分：" + score); // 输出: 40
```

## 构建与测试
```bash
# 编译项目
mvn clean install

# 运行测试
mvn test
```

> 项目要求：Java 11+，Maven 3.6+
