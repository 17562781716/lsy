<template>
  <div class="insights-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">感悟墙</h1>
        <p class="page-subtitle">分享你的主题周故事，记录成长的点滴</p>
      </div>
      
      <div class="insights-layout">
        <!-- 左侧：发布感悟 -->
        <div class="insights-left">
          <div class="post-card">
            <h2 class="post-title">发布感悟</h2>
            <div class="form-group">
              <label class="form-label">选择主题周</label>
              <select v-model="newPost.theme" class="form-input">
                <option value="">请选择</option>
                <option v-for="theme in myThemes" :key="theme.id" :value="theme.name">
                  {{ theme.icon }} {{ theme.name }}
                </option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">分享你的感悟</label>
              <textarea 
                v-model="newPost.content" 
                class="form-textarea"
                placeholder="记录这一周的收获、感受和成长..."
                rows="5"
              ></textarea>
            </div>
            <div class="form-group">
              <label class="form-label">添加标签</label>
              <div class="tag-input">
                <input 
                  v-model="tagInput" 
                  type="text" 
                  class="form-input"
                  placeholder="输入标签后按回车"
                  @keyup.enter="addTag"
                />
              </div>
              <div class="tags-display">
                <span v-for="(tag, idx) in newPost.tags" :key="idx" class="tag-item">
                  {{ tag }}
                  <button class="tag-remove" @click="removeTag(idx)">✕</button>
                </span>
              </div>
            </div>
            <button class="btn-primary btn-block" @click="publishPost">发布感悟</button>
          </div>
          
          <!-- 热门标签 -->
          <div class="hot-tags-card">
            <h3 class="card-title">热门标签</h3>
            <div class="hot-tags">
              <span v-for="tag in hotTags" :key="tag" class="hot-tag">{{ tag }}</span>
            </div>
          </div>
        </div>
        
        <!-- 右侧：感悟列表 -->
        <div class="insights-right">
          <div class="insights-filter">
            <button 
              v-for="filter in filters" 
              :key="filter.id"
              class="filter-btn"
              :class="{ active: activeFilter === filter.id }"
              @click="activeFilter = filter.id"
            >
              {{ filter.name }}
            </button>
          </div>
          
          <div class="insights-list">
            <div v-for="post in filteredPosts" :key="post.id" class="insight-post">
              <div class="post-header">
                <span class="post-avatar">{{ post.avatar }}</span>
                <div class="post-user-info">
                  <span class="post-username">{{ post.user }}</span>
                  <span class="post-theme">{{ post.theme }}</span>
                </div>
                <span class="post-date">{{ post.date }}</span>
              </div>
              <p class="post-content">{{ post.content }}</p>
              <div class="post-tags">
                <span v-for="tag in post.tags" :key="tag" class="post-tag">{{ tag }}</span>
              </div>
              <div class="post-actions">
                <button class="action-btn" @click="likePost(post)">
                  <span class="action-icon">❤️</span>
                  <span class="action-count">{{ post.likes }}</span>
                </button>
                <button class="action-btn">
                  <span class="action-icon">💬</span>
                  <span class="action-count">{{ post.comments }}</span>
                </button>
                <button class="action-btn">
                  <span class="action-icon">📤</span>
                  <span>分享</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const myThemes = ref([
  { id: 1, icon: '🎭', name: '音乐剧周' },
  { id: 2, icon: '📖', name: '阅读周' },
  { id: 3, icon: '🧶', name: '钩织周' },
  { id: 4, icon: '🏃', name: '健身周' },
]);

const newPost = ref({
  theme: '',
  content: '',
  tags: [],
});

const tagInput = ref('');

const addTag = () => {
  if (tagInput.value && !newPost.value.tags.includes(tagInput.value)) {
    newPost.value.tags.push(tagInput.value);
    tagInput.value = '';
  }
};

const removeTag = (idx) => {
  newPost.value.tags.splice(idx, 1);
};

const publishPost = () => {
  if (!newPost.value.content) return;
  
  posts.value.unshift({
    id: Date.now(),
    avatar: '👤',
    user: '我',
    theme: newPost.value.theme || '自定义周',
    date: '刚刚',
    content: newPost.value.content,
    tags: [...newPost.value.tags],
    likes: 0,
    comments: 0,
  });
  
  newPost.value = { theme: '', content: '', tags: [] };
};

const hotTags = ref(['#音乐剧', '#阅读', '#钩织', '#健身', '#成长', '#生活方式', '#自我提升', '#新体验']);

const filters = ref([
  { id: 'all', name: '全部' },
  { id: 'hot', name: '最热' },
  { id: 'latest', name: '最新' },
  { id: 'following', name: '关注' },
]);

const activeFilter = ref('all');

const posts = ref([
  {
    id: 1,
    avatar: '👧',
    user: '小叶子',
    theme: '🎭 音乐剧周',
    date: '2026-04-20',
    content: '第一次走进剧场看音乐剧，被《歌剧魅影》深深震撼了！原来现场演出比录音带更有感染力，那种沉浸感真的无法用语言形容。这一周让我收获了全新的爱好，生活因此扩容了！',
    tags: ['#音乐剧', '#新体验', '#艺术'],
    likes: 24,
    comments: 6,
  },
  {
    id: 2,
    avatar: '🌸',
    user: '樱花酱',
    theme: '🧶 钩织周',
    date: '2026-04-18',
    content: '这一周学会了钩织小雏菊杯垫！虽然手指酸痛，但看到成品的那一刻超有成就感。现在已经停不下来，准备挑战钩织包包了~ 原来手工可以这么治愈！',
    tags: ['#钩织', '#手工', '#治愈'],
    likes: 31,
    comments: 8,
  },
  {
    id: 3,
    avatar: '📚',
    user: '书虫阿明',
    theme: '📖 阅读周',
    date: '2026-04-15',
    content: '七天读完了《百年孤独》，马尔克斯的魔幻现实主义真的太绝了！这一周每天下班后都迫不及待地翻开书，生活变得好充实。阅读周让我重新找回了阅读的乐趣。',
    tags: ['#阅读', '#马尔克斯', '#成长'],
    likes: 45,
    comments: 12,
  },
  {
    id: 4,
    avatar: '💪',
    user: '健身达人',
    theme: '🏃 健身周',
    date: '2026-04-12',
    content: '坚持了一周的晨跑，从一开始的3公里到现在的5公里，身体感觉轻盈了很多。最重要的是养成了早起的习惯，一天的时间都变长了！',
    tags: ['#健身', '#晨跑', '#自律'],
    likes: 38,
    comments: 9,
  },
  {
    id: 5,
    avatar: '🎨',
    user: '画画的小王',
    theme: '🎨 绘画周',
    date: '2026-04-10',
    content: '从来没有学过画画，这一周每天画一幅小画。从最简单的线条开始，慢慢能画出完整的小风景了。虽然画得不好，但过程中的专注和放松是最珍贵的。',
    tags: ['#绘画', '#零基础', '#专注'],
    likes: 27,
    comments: 5,
  },
]);

const filteredPosts = computed(() => {
  if (activeFilter.value === 'hot') {
    return [...posts.value].sort((a, b) => b.likes - a.likes);
  }
  return posts.value;
});

const likePost = (post) => {
  post.likes++;
};
</script>

<style scoped>
.insights-page {
  padding: 80px 0 60px;
  min-height: 100vh;
}

.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.page-title {
  font-size: 36px;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.page-subtitle {
  font-size: 16px;
  color: var(--text-muted);
}

.insights-layout {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 24px;
}

.post-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 28px;
  box-shadow: var(--shadow-light);
  margin-bottom: 24px;
  position: sticky;
  top: 100px;
}

.post-title {
  font-size: 18px;
  color: var(--text-primary);
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 16px;
}

.form-label {
  display: block;
  font-size: 13px;
  color: var(--text-dark);
  margin-bottom: 6px;
}

.form-input {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid var(--border-color);
  border-radius: 8px;
  font-size: 14px;
  transition: border-color 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: var(--primary-color);
}

.form-textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid var(--border-color);
  border-radius: 8px;
  font-size: 14px;
  resize: vertical;
  transition: border-color 0.2s;
}

.form-textarea:focus {
  outline: none;
  border-color: var(--primary-color);
}

.tags-display {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 8px;
}

.tag-item {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 10px;
  background: var(--background-secondary);
  color: var(--primary-color);
  border-radius: 12px;
  font-size: 12px;
}

.tag-remove {
  background: none;
  border: none;
  color: var(--text-muted);
  cursor: pointer;
  font-size: 12px;
}

.btn-block {
  width: 100%;
  padding: 12px;
}

.hot-tags-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 24px;
  box-shadow: var(--shadow-light);
}

.card-title {
  font-size: 16px;
  color: var(--text-primary);
  margin-bottom: 16px;
}

.hot-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.hot-tag {
  padding: 6px 12px;
  background: var(--background-secondary);
  color: var(--primary-color);
  border-radius: 16px;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.2s;
}

.hot-tag:hover {
  background: var(--primary-color);
  color: white;
}

.insights-filter {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}

.filter-btn {
  padding: 8px 20px;
  border-radius: 20px;
  border: 1px solid var(--border-color);
  background: white;
  color: var(--text-dark);
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.filter-btn:hover {
  border-color: var(--primary-color);
  color: var(--primary-color);
}

.filter-btn.active {
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  color: white;
  border-color: transparent;
}

.insights-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.insight-post {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 24px;
  box-shadow: var(--shadow-light);
  transition: all 0.2s;
}

.insight-post:hover {
  box-shadow: var(--shadow-medium);
}

.post-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.post-avatar {
  font-size: 32px;
}

.post-user-info {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.post-username {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
}

.post-theme {
  font-size: 12px;
  color: var(--text-secondary);
}

.post-date {
  font-size: 12px;
  color: var(--text-muted);
}

.post-content {
  font-size: 14px;
  color: var(--text-dark);
  line-height: 1.8;
  margin-bottom: 12px;
}

.post-tags {
  display: flex;
  gap: 8px;
  margin-bottom: 16px;
  flex-wrap: wrap;
}

.post-tag {
  font-size: 12px;
  color: var(--primary-color);
  background: var(--background-secondary);
  padding: 4px 10px;
  border-radius: 12px;
}

.post-actions {
  display: flex;
  gap: 16px;
  padding-top: 12px;
  border-top: 1px solid var(--border-color);
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 4px;
  background: none;
  border: none;
  color: var(--text-muted);
  font-size: 13px;
  cursor: pointer;
  transition: color 0.2s;
}

.action-btn:hover {
  color: var(--primary-color);
}

.action-icon {
  font-size: 16px;
}

@media (max-width: 1024px) {
  .insights-layout {
    grid-template-columns: 1fr;
  }
  
  .post-card {
    position: static;
  }
}
</style>
