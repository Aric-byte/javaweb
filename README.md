# javaweb —— 在线考试系统

基于 **JSP + Servlet + JDBC (HikariCP) + MySQL** 的 Java Web 实验项目，MVC 三层架构。

## 功能

- 用户注册 / 登录（验证码、防恶意登录：连续失败锁定）/ 注销 / 修改个人信息
- 在线考试：开始考试、提交试卷、查看成绩
- 管理员后台：用户管理、题目管理（增 / 删 / 查）
- 访问统计过滤器、登录校验过滤器、自定义 404/500 页面

## 环境要求

| 软件 | 版本 |
| --- | --- |
| JDK | 17+ |
| Maven | 3.6+ |
| MySQL | 8.x |
| Tomcat | **10.1+**（项目使用 `jakarta.servlet`，Tomcat 9 及以下不可用） |

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/aric-byte/javaweb.git
```

### 2. 初始化数据库

```bash
mysql -u root -p < database/exam_system.sql
```

脚本会创建 `exam_system` 库、表和示例数据。默认管理员账号：`admin` / `admin123`。

### 3. 配置数据库连接

`db.properties` 含本地密码，未纳入版本控制。复制模板后按本机情况修改：

```bash
cp src/main/resources/db.properties.example src/main/resources/db.properties
```

至少修改 `jdbc.username` 和 `jdbc.password`。

### 4. 构建

```bash
mvn clean package
```

生成 `target/test1.war`。

### 5. 部署运行

- **命令行**：将 `target/test1.war` 复制到 Tomcat 的 `webapps/` 目录，启动 Tomcat，访问 <http://localhost:8080/test1/>
- **IDEA**：以 Maven 项目打开 → Run/Debug Configurations 添加 Tomcat Server (Local) → Deployment 中添加 `test1:war exploded` → 运行

## 项目结构

```
├── pom.xml                         # Maven 构建配置
├── database/exam_system.sql        # 建库建表及示例数据
└── src/main
    ├── java/com/hx
    │   ├── dao/        # 数据访问层 UsersDAO / QuestionDAO
    │   ├── entity/     # 实体 Users / Question
    │   ├── filter/     # 登录安全、访问统计过滤器
    │   ├── listener/   # 应用上下文监听器
    │   ├── servlet/    # 控制层（admin/ 为后台管理）
    │   ├── test/       # 连接池性能测试
    │   └── util/       # JdbcUtil（HikariCP）、CaptchaUtil
    ├── resources/db.properties.example
    └── webapp/         # JSP 页面、WEB-INF/web.xml
```

## 更多文档

- [系统架构说明](系统架构说明.md)
- [连接池优化说明](连接池优化说明.md)
- [防恶意登录机制说明](防恶意登录机制说明.md)

## 常见问题

- **`ClassNotFoundException: jakarta.servlet...`**：Tomcat 版本过低，请使用 10.1+。
- **`Access denied for user 'root'`**：检查 `db.properties` 用户名和密码。
- **`Unknown database 'exam_system'`**：尚未执行第 2 步的 SQL 脚本。
- **中文乱码**：确保数据库字符集为 `utf8mb4`，并保留 JDBC URL 中的 `characterEncoding=utf8`。
