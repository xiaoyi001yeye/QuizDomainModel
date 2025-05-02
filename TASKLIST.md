# 试题领域模型实现任务清单

- [x] 1. **项目设置:** 使用 Maven 或 Gradle 初始化 Java 项目结构。
- [x] 2. **定义枚举:** 创建 `com.example.quiz.domain.model.QuestionType` 枚举。
- [x] 3. **实现 Choice 类:** 创建 `com.example.quiz.domain.model.Choice` 类及其属性 (`id`, `text`)。
- [x] 4. **实现 Question 类:** 创建 `com.example.quiz.domain.model.Question` 类及其属性 (`id`, `stem`, `type`, `choices`, `correctAnswer`)。
- [x] 5. **实现 Quiz 类:** 创建 `com.example.quiz.domain.model.Quiz` 类及其属性 (`id`, `title`, `description`, `questions`)。
- [x] 6. **实现 UserAnswer 类:** 创建 `com.example.quiz.domain.model.UserAnswer` 类及其属性 (`questionId`, `selectedChoiceIds`, `filledText`)。
- [x] 7. **实现 AnswerSheet 类:** 创建 `com.example.quiz.domain.model.AnswerSheet` 类及其属性 (`id`, `quizId`, `userId`, `submissionTime`, `userAnswers`)。
- [x] 8. **添加基础方法:** 为所有模型类添加构造函数、getter 和 setter。
- [x] 9. **ID 生成策略:** 确定并初步实现 ID 生成方式 (例如，使用 `java.util.UUID`)。
- [x] 10. **基础验证:** 添加简单的验证逻辑 (例如，确保 `Question` 的 `choices` 不为空，如果类型是选择题)。
- [ ] 11. **单元测试:** 为核心模型类编写基础的单元测试。
- [ ] 12. **创建MaterialQuestion类:** 实现材料题专用类，包含材料内容、子题列表、解析和总分字段
- [ ] 13. **重构AnswerSheet:** 支持材料题的复合评分逻辑（子题评分+材料整体评分）
- [ ] 14. **迁移READING题型:** 将原有READING题型实例改为使用MaterialQuestion
- [ ] 15. **添加材料解析功能:** 在MaterialQuestion中实现材料解析方法