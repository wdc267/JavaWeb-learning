# JavaWeb 学习项目

记录 JavaWeb / Spring Boot 学习过程的示例代码与实战项目，核心是基于 Spring Boot + MyBatis + MySQL 的 **Tlias 部门员工管理系统**。

## 项目结构

```
JavaWebCode/
├── tlias-parent/           # Maven 聚合父工程，统一管理依赖版本
├── tlias-pojo/             # 实体类模块（Dept、Emp、OperateLog、PageBean、Result）
├── tlias-utils/            # 工具模块（JWT 生成/解析、阿里云 OSS 上传）
├── tlias-web-management/   # 核心 Web 模块（部门/员工管理、登录鉴权、文件上传）
├── springboot-quick-start/ # Spring Boot 入门示例（请求参数、XML 解析、静态页面）
├── http-server-demo/       # 手写 HTTP 服务器示例（Gradle）
└── wdc_maven/              # Maven 基础示例
```

## 技术栈

- Java 21
- Spring Boot 4.x（Web MVC、AOP、拦截器、过滤器）
- MyBatis + PageHelper（分页）
- MySQL 8
- JWT（jjwt）
- 阿里云 OSS SDK
- Lombok、dom4j
- Maven / Gradle

## 模块说明

### Tlias 部门员工管理系统

- **tlias-parent**：聚合 `tlias-pojo`、`tlias-utils`、`tlias-web-management`，并在 `dependencyManagement` 中统一管理 JWT、阿里云 OSS 等依赖版本。
- **tlias-pojo**：`Dept`（部门）、`Emp`（员工）、`OperateLog`（操作日志）、`PageBean`（分页结果）、`Result`（统一响应体）。
- **tlias-utils**：
  - `JwtUtils`：生成与解析 JWT 令牌；
  - `AliOSSUtils` / `AliOSSProperties`：上传文件到阿里云 OSS 并返回访问地址。
- **tlias-web-management**：
  - 部门管理：查询、新增、删除；
  - 员工管理：分页条件查询、新增、回显、修改、批量删除；
  - 登录鉴权：账号密码校验，签发 JWT 令牌；`LoginCheckInterceptor` 拦截除 `/login` 外的请求校验令牌；
  - 文件上传：图片上传至阿里云 OSS，配套页面 `static/upload.html`；
  - AOP 操作日志：`@Log` 注解 + `LogAspect` 将操作记录写入 `operate_log` 表；
  - 全局异常处理：`GlobalExceptionHandler`；
  - Cookie / Session 示例：`SessionController`。

### springboot-quick-start

- `/hello`、`/simpleParam`、`/simplePojo`：请求参数绑定示例；
- `/listEmp`：使用 dom4j 解析 `emp.xml`，返回员工列表；
- 静态页面（Element UI + axios + Vue）位于 `src/main/resources/static`。

### http-server-demo

- `Server.java`：基于 `ServerSocket` 手写简易 HTTP 服务器（端口 8080）；
- 同时包含 Spring Boot Web 启动示例与静态页面 `html/a.html`。

### wdc_maven

- Maven 入门示例：`HelloWorld`、`Main`。

## 环境要求

- JDK 21
- Maven 3.9+（各 Maven 模块自带 `mvnw`）
- MySQL 8.0
- Gradle 9.x（`http-server-demo` 自带 `gradlew`）

## 快速开始

### 1. 准备数据库

创建 `tlias` 数据库，并创建 `dept`、`emp`、`operate_log` 三张表：

```sql
CREATE DATABASE tlias DEFAULT CHARACTER SET utf8mb4;
```

### 2. 修改本地配置

`tlias-web-management/src/main/resources/application.yml`：

- 将数据库 `username` / `password` 改为本地实际账号（仓库中密码为占位符 `your_password`）；
- 按需修改 `aliyun.oss` 的 `endpoint`、`bucketName`、`region`。

阿里云 OSS 访问凭证通过**环境变量**读取，请先设置：

```
ALIBABA_CLOUD_ACCESS_KEY_ID
ALIBABA_CLOUD_ACCESS_KEY_SECRET
```

JWT 签名密钥位于 `tlias-utils/src/main/java/com/wdc/utils/JwtUtils.java` 的 `signKey`，仓库中为占位符，本地运行前请替换为自有密钥（32 位以上）。

### 3. 构建与运行

Tlias 多模块项目：

```bash
cd tlias-parent
./mvnw clean install
cd ../tlias-web-management
./mvnw spring-boot:run
```

Windows 下使用 `mvnw.cmd`。默认端口为 8080。

Spring Boot 入门示例：

```bash
cd springboot-quick-start
./mvnw spring-boot:run
```

HTTP 服务器示例（Gradle）：

```bash
cd http-server-demo
./gradlew bootRun
```

## 主要接口（tlias-web-management）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/login` | 登录，成功返回 JWT 令牌 |
| GET | `/depts` | 查询全部部门 |
| POST | `/depts` | 新增部门 |
| DELETE | `/depts/{id}` | 删除部门 |
| GET | `/emps?page=1&pageSize=10&name=&gender=&begin=&end=` | 员工分页条件查询 |
| POST | `/emps` | 新增员工 |
| GET | `/emps/{id}` | 根据 ID 查询员工 |
| PUT | `/emps` | 修改员工 |
| DELETE | `/emps/{ids}` | 批量删除员工 |
| POST | `/upload` | 上传图片到阿里云 OSS |

> 除 `/login` 外，其余接口需在请求头中携带 `token`（登录返回的 JWT）。

## 安全说明

- 仓库中的数据库密码、JWT 签名密钥均已替换为占位符，请勿在提交时写入真实凭证；建议本地配置另行保管。
- 阿里云 OSS 凭证通过环境变量注入，不写入代码或配置文件。

## 说明

本项目用于学习与练习，代码随学习进度持续更新。
