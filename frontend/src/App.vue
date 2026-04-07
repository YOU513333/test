<script setup>
import { onMounted, ref } from 'vue'

const posts = ref([])
const loading = ref(false)
const error = ref('')
const submitting = ref(false)

const form = ref({
  title: '',
  content: '',
  author: ''
})

async function loadPosts() {
  loading.value = true
  error.value = ''
  try {
    const response = await fetch('/api/posts')
    if (!response.ok) {
      throw new Error('加载文章失败')
    }
    posts.value = await response.json()
  } catch (err) {
    error.value = err instanceof Error ? err.message : '加载文章失败'
  } finally {
    loading.value = false
  }
}

async function submitPost() {
  if (!form.value.title || !form.value.content || !form.value.author) {
    error.value = '请完整填写标题、内容和作者'
    return
  }

  submitting.value = true
  error.value = ''
  try {
    const response = await fetch('/api/posts', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(form.value)
    })

    if (!response.ok) {
      throw new Error('发布失败，请稍后重试')
    }

    form.value = {
      title: '',
      content: '',
      author: ''
    }
    await loadPosts()
  } catch (err) {
    error.value = err instanceof Error ? err.message : '发布失败'
  } finally {
    submitting.value = false
  }
}

onMounted(loadPosts)
</script>

<template>
  <div class="container">
    <h1>简易博客</h1>

    <section class="panel">
      <h2>发布文章</h2>
      <div class="form-row">
        <input v-model.trim="form.title" type="text" placeholder="文章标题" maxlength="120" />
      </div>
      <div class="form-row">
        <input v-model.trim="form.author" type="text" placeholder="作者" maxlength="60" />
      </div>
      <div class="form-row">
        <textarea v-model.trim="form.content" rows="6" placeholder="写点内容..." maxlength="5000" />
      </div>
      <button :disabled="submitting" @click="submitPost">
        {{ submitting ? '发布中...' : '发布文章' }}
      </button>
    </section>

    <p v-if="error" class="error">{{ error }}</p>

    <section class="panel">
      <h2>文章列表</h2>
      <p v-if="loading">加载中...</p>
      <p v-else-if="posts.length === 0">还没有文章，先发布一篇吧。</p>
      <article v-for="post in posts" :key="post.id" class="post-card">
        <h3>{{ post.title }}</h3>
        <p class="meta">
          <span>{{ post.author }}</span>
          <span>{{ new Date(post.createdAt).toLocaleString() }}</span>
        </p>
        <p class="content">{{ post.content }}</p>
      </article>
    </section>
  </div>
</template>

<style scoped>
.container {
  max-width: 880px;
  margin: 0 auto;
  padding: 1.5rem;
}

h1 {
  margin-bottom: 1.25rem;
}

.panel {
  background: #ffffff;
  border: 1px solid #e8e8e8;
  border-radius: 10px;
  padding: 1rem;
  margin-bottom: 1rem;
}

.form-row {
  margin-bottom: 0.75rem;
}

input,
textarea {
  width: 100%;
  padding: 0.65rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  font: inherit;
}

button {
  border: none;
  background: #2f7af8;
  color: #fff;
  border-radius: 8px;
  padding: 0.65rem 1rem;
  cursor: pointer;
}

button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.error {
  color: #d93025;
  margin: 0.5rem 0 1rem;
}

.post-card {
  border-top: 1px solid #f0f0f0;
  padding-top: 0.85rem;
  margin-top: 0.85rem;
}

.post-card:first-of-type {
  border-top: none;
  margin-top: 0;
  padding-top: 0;
}

.meta {
  color: #666;
  display: flex;
  gap: 0.7rem;
  font-size: 0.9rem;
  margin: 0.35rem 0 0.65rem;
}

.content {
  white-space: pre-wrap;
  line-height: 1.6;
}
</style>
