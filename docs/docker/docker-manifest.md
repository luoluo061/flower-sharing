# Docker 文件清单

## 1. 当前文件

- `script/docker/docker-compose.yml`
- `script/docker/docker-compose1.yml`
- `script/docker/docker-compose2.yml`
- `script/docker/database.yml`
- `script/docker/service-run-command.yml`
- `script/docker/nginx/conf/nginx.conf`
- `script/docker/redis/conf/redis.conf`

## 2. 初步用途判断

### `docker-compose.yml`

倾向于基础依赖编排，当前包含：

- MySQL
- Nginx
- Redis
- MinIO

### `docker-compose1.yml`

倾向于全量服务编排，当前包含：

- MySQL
- Nginx
- Redis
- MinIO
- `ruoyi-server1`
- `ruoyi-server2`
- `ruoyi-monitor-admin`
- `ruoyi-snailjob-server`

### `docker-compose2.yml`

倾向于单服务镜像运行或调试用途，当前只包含：

- `ruoyi-server1`

## 3. 当前存在的问题

- 文件命名无法表达用途
- 缺少使用场景说明
- 都使用了较强的宿主机耦合方式
- 文件中存在账号密码等敏感信息
- 未说明哪个文件适用于本地、测试、生产或镜像构建阶段

## 4. 当前治理策略

本阶段：

- 只补说明
- 不直接合并 compose
- 不直接重写部署模型

后续阶段：

- 为每个 compose 明确命名和定位
- 识别哪些是历史遗留，哪些仍在使用
- 逐步清理敏感信息和硬编码

## 5. 待确认事项

- 当前线上实际使用的是哪份 compose
- `database.yml` 和 `service-run-command.yml` 是否仍被实际使用
- 是否存在仓库外的 Dockerfile 或 CI/CD 部署流程
