# Docker 目录说明

本目录存放 Docker Compose、镜像运行辅助文件及依赖服务配置。

## 文件清单

- `docker-compose.yml`
- `docker-compose1.yml`
- `docker-compose2.yml`
- `database.yml`
- `service-run-command.yml`
- `nginx/conf/nginx.conf`
- `redis/conf/redis.conf`

## 当前用途判断

### `docker-compose.yml`

偏基础依赖编排。

当前包含：

- MySQL
- Nginx
- Redis
- MinIO

### `docker-compose1.yml`

偏全量服务编排。

当前包含：

- 基础依赖
- 主应用镜像
- 监控中心
- SnailJob 服务端

### `docker-compose2.yml`

偏单服务运行或镜像调试。

当前包含：

- 单个 `ruoyi-server1`

## Dockerfile 分布

当前仓库已识别 Dockerfile：

- `ruoyi-admin/Dockerfile`
- `ruoyi-extend/ruoyi-monitor-admin/Dockerfile`
- `ruoyi-extend/ruoyi-snailjob-server/Dockerfile`

说明：

- `ruoyi-monitor-admin` 与 `ruoyi-snailjob-server` 的 Dockerfile 直接 `ADD target/*.jar`
- `ruoyi-admin` 的 Dockerfile 采用运行时挂载 `app.jar` 的方式，不在镜像构建阶段复制 jar
- 三者构建策略并不完全一致，后续如整理镜像交付方式，应先统一约定再改动

构建与启动入口详见：

- [docs/architecture/build-and-run.md](/E:/flower-sharing/docs/architecture/build-and-run.md)

## 当前使用限制

- 文件命名不能直接表达用途
- 说明不足
- 含有敏感口令和宿主机路径耦合
- 不能在未确认场景前直接合并

## 建议使用方式

当前阶段：

- 将这些文件视为“候选部署方案”，不是正式标准部署文档
- 使用前先核对服务、端口、挂载目录和凭据

后续阶段：

- 为每份 compose 明确适用场景
- 重新命名或补清晰说明
- 逐步移除敏感硬编码
- 明确本地、测试、生产的边界

## 待确认事项

- 线上是否正在使用其中某一份 compose
- `database.yml` 是否仍参与部署流程
- `service-run-command.yml` 是否是历史遗留命令样例
