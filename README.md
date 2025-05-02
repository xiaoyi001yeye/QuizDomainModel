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
  private String stem;          // 题目内容
  private List<Choice> choices; // 选项列表
  private int points;           // 题目分值
  private QuestionType type;    // 题目类型
  ```

### MaterialQuestion.java
- 阅读理解题实体类
- 继承自Question
- 新增字段：
  ```java
  private int wordLimit;        // 作答字数限制
  ```

### Choice.java
- 定义题目选项及正确性标记
- 核心属性：
  ```java
  private String choiceText;    // 选项内容
  private boolean isCorrect;    // 正确性标记
  ```

## 使用示例
```java
// 阅读理解题示例1 - Python
MaterialQuestion reading1 = new MaterialQuestion(
    "Python是一种动态类型的解释型语言，以简洁易读的语法著称...",
    new ArrayList<>(), 
    "简洁的语法结构", 
    15, 
    List.of(
        new Question("Python的缩进规则主要用于？", QuestionType.SINGLE_CHOICE, 
            List.of(new Choice("代码块结构", true), new Choice("变量声明", false)), 
            "代码块结构", 5)
    )
);
reading1.setWordLimit(50);
UserAnswer userAnswer6 = new UserAnswer();
userAnswer6.setTextAnswer(reading1, "简洁的语法结构和丰富的标准库支持");
// 得分：15

// 阅读理解题示例2 - C++
MaterialQuestion reading2 = new MaterialQuestion(
    "C++是一种静态类型的编译型语言，支持面向对象和泛型编程...",
    new ArrayList<>(), 
    "高性能执行", 
    15, 
    List.of(
        new Question("C++内存管理的主要优势是？", QuestionType.SINGLE_CHOICE, 
            List.of(new Choice("手动控制", true), new Choice("自动回收", false)), 
            "手动控制", 5)
    )
);
reading2.setWordLimit(50);
UserAnswer userAnswer7 = new UserAnswer();
userAnswer7.setTextAnswer(reading2, "高性能执行能力和底层内存控制");
// 得分：15

// 阅读理解题示例3 - JavaScript
MaterialQuestion reading3 = new MaterialQuestion(
    "JavaScript最初作为网页脚本语言设计，现已成为全栈开发的重要工具...",
    new ArrayList<>(), 
    "跨平台执行", 
    15, 
    List.of(
        new Question("Node.js的主要贡献是？", QuestionType.SINGLE_CHOICE, 
            List.of(new Choice("服务器端执行", true), new Choice("DOM操作", false)), 
            "服务器端执行", 5)
    )
);
reading3.setWordLimit(50);
UserAnswer userAnswer8 = new UserAnswer();
userAnswer8.setTextAnswer(reading3, "跨平台执行能力和异步处理机制");
// 得分：15

// 总分计算示例
Quiz quiz = new Quiz();
quiz.addQuestion(reading1);
quiz.addQuestion(reading2);
quiz.addQuestion(reading3);
int totalScore = new AnswerSheet().calculateScore(quiz, userAnswer6);
totalScore += new AnswerSheet().calculateScore(quiz, userAnswer7);
totalScore += new AnswerSheet().calculateScore(quiz, userAnswer8);
System.out.println("阅读理解题总得分：" + totalScore); // 输出: 45
```

## 构建与测试
```bash
# 编译项目
mvn clean install

# 运行测试
mvn test
```

> 项目要求：Java 11+，Maven 3.6+
