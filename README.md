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

后端默认地址：`http://localhost:8080`

### 2) 启动前端

```bash
cd /home/runner/work/test/test/frontend
npm install
npm run dev
```

前端默认地址：`http://localhost:5173`

前端已配置 `/api` 代理到后端。

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
