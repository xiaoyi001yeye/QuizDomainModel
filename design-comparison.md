# 材料题设计对比分析

## 当前设计背景
- Question类已包含READING题型枚举
- 现有评分逻辑基于Question的isCorrect属性
- Question类包含基础字段：questionText, choices, points

## 方案对比

### 方案一：单独MaterialQuestion类
```java
class MaterialQuestion {
    -String id
    -String materialContent  // 阅读材料内容
    -List<Question> subQuestions  // 复用现有Question类
    -String explanation      // 材料整体解析
    -Integer totalScore      // 材料题总分
}
```

**优势**：
1. 高内聚：材料题专属属性集中管理
2. 可扩展：新增材料解析等行为不影响基础题型
3. 结构清晰：子题复用现有Question实现
4. 评分灵活：可实现材料级/子题级双重评分

**挑战**：
1. 需要新增类关系管理
2. 评分逻辑需适配复合结构
3. 数据持久化需处理嵌套结构

### 方案二：复用Question类
**实现方式**：
```java
class Question {
    // 现有字段...
    private String materialContent; // 仅当type=READING时有效
    private List<Question> subQuestions; // 仅当type=READING时有效
}
```

**优势**：
1. 统一题型接口
2. 保持现有评分逻辑兼容
3. 简化类关系

**局限**：
1. 类污染：非材料题实例包含冗余字段
2. 扩展困难：新增材料题特性需修改基础类
3. 行为混杂：材料解析逻辑与基础题型耦合

## 推荐方案
建议采用**方案一：单独MaterialQuestion类**，理由：
1. 符合开闭原则：新增材料题不修改现有Question类
2. 保持单一职责：材料题特性独立封装
3. 支持未来扩展：便于添加材料解析等专项功能
4. 结构更清晰：领域模型准确反映业务现实

## 实施建议
1. 保持QuestionType中的READING枚举作为标识
2. MaterialQuestion实现与Question相同的评分接口
3. 在AnswerSheet中添加对MaterialQuestion的特殊处理：
   ```java
   // 材料题评分逻辑示例
   if (question instanceof MaterialQuestion) {
       MaterialQuestion mq = (MaterialQuestion) question;
       // 先评分子题
       int subScore = scoreSubQuestions(mq.getSubQuestions());
       // 再计算材料整体得分
       return calculateMaterialScore(subScore, mq.getExplanation());
   }
   ```
4. 使用组合模式统一处理普通题和材料题

## 迁移路径
1. 新增MaterialQuestion类
2. 重构AnswerSheet支持复合题型
3. 逐步迁移现有READING题型实例
4. 保留Question类的向后兼容能力