# Spring Boot + Vue3 博客项目

这是一个前后端分离的简易博客示例：

- 后端：Spring Boot (Java 17)
- 前端：Vue 3 + Vite

## 功能

- 查看文章列表
- 发布新文章
- 内存存储（重启后数据重置）

## 启动方式

### 1) 启动后端

```bash
cd /home/runner/work/test/test/backend
mvn spring-boot:run
```

后端默认地址：`http://localhost:8081`

### 2) 启动前端

```bash
cd /home/runner/work/test/test/frontend
npm install
npm run dev
```

前端默认地址：`http://localhost:5173`

前端已配置 `/api` 代理到后端。

## Docker 基础设施

项目根目录提供了一个开发用 `docker-compose.yml`，用于未来快速启动这些基础设施：

- MySQL 8.4
- Redis 7
- MinIO（对象存储，适合图片、附件等文件）
- Nginx（统一反向代理入口）

### 端口说明

- Nginx: `http://localhost:8088`
- MySQL: `localhost:3306`
- Redis: `localhost:6379`
- MinIO API: `http://localhost:9000`
- MinIO Console: `http://localhost:9001`

### 使用方式

先复制环境变量文件（Windows 可直接复制 `.env.example` 为 `.env`，Linux/macOS 可执行下面命令）：

```bash
cp .env.example .env
```

然后在项目根目录执行：

```bash
docker compose up -d
```

### Nginx 代理规则

- `/` 转发到前端开发服务 `http://host.docker.internal:5173`
- `/api/` 转发到后端服务 `http://host.docker.internal:8081`
- `/minio/` 转发到 MinIO API
- `/minio-console/` 转发到 MinIO 控制台

这意味着当前这份 compose 主要负责基础设施和统一入口，前后端仍按你现在的本地方式运行即可。

## 构建与测试

### 后端

```bash
cd /home/runner/work/test/test/backend
mvn test
mvn package
```

### 前端

```bash
cd /home/runner/work/test/test/frontend
npm run build
```
