# Maven 入口策略

本文档用于说明当前仓库在 Maven 构建入口上的现状、限制和后续建议。

目标：

- 明确当前为什么还没有直接补入 Maven Wrapper
- 给出当前阶段可执行的标准入口
- 为后续是否引入 Maven Wrapper 提供判断依据

## 1. 当前现状

已确认事实：

- 仓库当前没有 `mvnw`
- 仓库当前没有 `mvnw.cmd`
- 仓库当前没有 `.mvn/`
- 当前会话环境 `mvn` 不在 PATH 中
- 当前会话环境 `java` 可用

补充说明：

- `.gitignore` 已允许 `.mvn/wrapper/maven-wrapper.jar` 被纳入版本控制
- 这意味着仓库规则层面并不排斥后续引入 Maven Wrapper

## 2. 当前标准入口

在 Maven Wrapper 尚未存在前，当前标准构建入口仍然是：

```bash
mvn -q -DskipTests compile
```

或：

```bash
mvn clean package -DskipTests
```

前提：

- 本机已安装 Maven 3.9+
- `mvn` 或 `mvn.cmd` 可用

在执行编译前，建议先运行：

```powershell
.\script\bin\check-env.ps1
```

## 3. 当前不直接引入 Wrapper 的原因

当前不直接把 Maven Wrapper 补进仓库，原因有三个：

### 3.1 当前环境缺少 Maven CLI

标准生成方式通常依赖本机 Maven，例如：

```bash
mvn -N wrapper:wrapper
```

但当前会话环境没有可用的 `mvn`，因此无法在本地稳定生成 Wrapper 文件。

### 3.2 当前网络环境受限

即便手工补入 Wrapper 文件，后续首次执行仍需要下载对应 Maven 发行包。

在当前受限环境下，这一步是否可成功并不确定，因此不适合在未验证前直接作为默认入口。

### 3.3 当前阶段优先级仍是“先打通基线”

现阶段更重要的是先把：

- 环境检查入口
- 构建前置条件
- 编译与启动验证顺序

固定下来，而不是在没有验证条件时先引入新的工具文件。

## 4. 何时适合引入 Maven Wrapper

满足以下条件后，可以考虑引入：

- 有一台可用 Maven CLI 的维护机器
- 已确认团队希望仓库自带统一 Maven 入口
- 已确认目标环境允许下载 Wrapper 所需 Maven 发行包，或可提前缓存
- 已准备好一次完整的编译验证

## 5. 引入后的预期收益

如果后续引入 Maven Wrapper，收益包括：

- 新机器无需预先手工安装 Maven
- 人和 AI 都能使用统一入口
- 构建版本更稳定
- 文档与脚本可统一改写为 `mvnw` / `mvnw.cmd`

## 6. 当前建议

当前建议不是“放弃 Wrapper”，而是：

1. 先用 `check-env.ps1` 固定环境检查入口
2. 先在具备 Maven 的环境中跑通编译
3. 再决定是否将 Maven Wrapper 正式纳入仓库

也就是说，当前的结论是：

- `值得评估引入`
- `但不适合在当前条件不足时盲目补入`
