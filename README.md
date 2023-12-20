# QuickTest - 在线考试系统

**项目概述**：QuickTest 是我在大学时开发的一个在线考试系统。这是一个前后端不分离的项目，包含学生考试模块、教师模块以及后台管理模块。

## 技术栈

- **后端框架**：
  - Spring Boot 2
  - Mybatis
  - Thymeleaf
- **持久层技术**：
  - MySQL
  - Redis
- **前端**：
  - HTML/CSS/JavaScript：用于构建用户界面和交互。
  - Bootstrap：用于响应式布局和组件样式。
  - Thymeleaf：作为模板引擎渲染页面。
  - Vue 2.js（非模块化）：处理前端业务逻辑。适合初学者学习 Vue 2 的基本语法。

## 功能概述

- **CRUD 操作**：
  - 学生、教师、专业和后台用户的增删改查（CRUD）。
- **试卷管理**：
  - 试卷的创建、管理和题库维护。
- **自动阅卷**：
  - 实现了对客观题目（单选、多选、判断题）的自动阅卷。
  - 题型分数设置较为固定，逻辑简单。
- **考试时间控制**：
  - 使用 Redis 控制每个学生的考试时间。

## 项目回顾

回顾这个项目，虽然有些代码看起来有些幼稚，但总体来说，这个项目是一个不错的学习实践，特别适合想学习 Spring Boot、Mybatis 基础使用，以及对 HTML、CSS、JavaScript 和 Vue 2 语法有基本了解需求的同学。
<img width="1275" alt="quicktest-admin" src="https://github.com/DongDongYu02/online_exam/assets/99881989/4cebfffc-99ea-41eb-be5c-6e5d0f9f772e">
<img width="1275" alt="quicktest-index" src="https://github.com/DongDongYu02/online_exam/assets/99881989/7d744ba4-5852-4af3-a839-6d3acb01de74">
<img width="1275" alt="Snipaste_2023-12-20_17-54-21" src="https://github.com/DongDongYu02/online_exam/assets/99881989/30c8e44b-1ec0-4e92-a5f8-844990851ee8">
<img width="1275" alt="Snipaste_2023-12-20_17-55-56" src="https://github.com/DongDongYu02/online_exam/assets/99881989/accea2ac-f8cb-48e0-9bbb-9214a5219666">
<img width="1275" alt="Snipaste_2023-12-20_17-57-05" src="https://github.com/DongDongYu02/online_exam/assets/99881989/2249560a-56d9-4bfd-9165-1b829b42d517">
<img width="1275" alt="Snipaste_2023-12-20_18-00-19" src="https://github.com/DongDongYu02/online_exam/assets/99881989/c4732eca-f223-4ff4-b024-12dff8ce5012">
<img width="1275" alt="Snipaste_2023-12-20_18-00-31" src="https://github.com/DongDongYu02/online_exam/assets/99881989/7df12bcb-af69-46d9-89ad-85d31d1caa88">






