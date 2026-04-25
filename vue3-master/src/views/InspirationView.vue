<template>
  <div class="inspiration-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">灵感库</h1>
        <p class="page-subtitle">发现无限可能的主题周灵感</p>
      </div>
      
      <div class="inspiration-categories">
        <div 
          v-for="cat in categories" 
          :key="cat.id"
          class="insp-category-card"
          :style="{ background: cat.gradient }"
        >
          <span class="cat-icon">{{ cat.icon }}</span>
          <h3 class="cat-name">{{ cat.name }}</h3>
          <p class="cat-count">{{ cat.count }}个主题</p>
        </div>
      </div>
      
      <div class="inspiration-themes">
        <h2 class="section-title">精选主题灵感</h2>
        <div class="insp-themes-grid">
          <div v-for="theme in inspirationThemes" :key="theme.id" class="insp-theme-card">
            <div class="insp-theme-header">
              <span class="insp-theme-icon">{{ theme.icon }}</span>
              <div class="insp-theme-info">
                <h3 class="insp-theme-name">{{ theme.name }}</h3>
                <span class="insp-theme-category">{{ theme.category }}</span>
              </div>
            </div>
            <p class="insp-theme-desc">{{ theme.description }}</p>
            <div class="insp-theme-ideas">
              <h4 class="ideas-title">灵感点子</h4>
              <ul class="ideas-list">
                <li v-for="(idea, idx) in theme.ideas" :key="idx">{{ idea }}</li>
              </ul>
            </div>
            <button class="btn-primary btn-sm btn-full" @click="useTheme(theme)">使用这个主题</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const categories = ref([
  { id: 'art', name: '艺术创意', icon: '🎨', count: 24, gradient: 'linear-gradient(135deg, #fce4ec, #f8bbd0)' },
  { id: 'growth', name: '自我成长', icon: '🌱', count: 18, gradient: 'linear-gradient(135deg, #e8f5e9, #c8e6c9)' },
  { id: 'health', name: '健康生活', icon: '💪', count: 15, gradient: 'linear-gradient(135deg, #e3f2fd, #bbdefb)' },
  { id: 'life', name: '生活美学', icon: '🏠', count: 21, gradient: 'linear-gradient(135deg, #fff3e0, #ffe0b2)' },
  { id: 'social', name: '社交连接', icon: '🤝', count: 12, gradient: 'linear-gradient(135deg, #f3e5f5, #e1bee7)' },
  { id: 'skill', name: '技能学习', icon: '📚', count: 19, gradient: 'linear-gradient(135deg, #e0f2f1, #b2dfdb)' },
]);

const inspirationThemes = ref([
  {
    id: 1,
    icon: '🎭',
    name: '音乐剧周',
    category: '艺术创意',
    description: '走进剧场，感受音乐与戏剧的碰撞，开启艺术之旅',
    ideas: ['观看一场现场音乐剧', '学唱一首经典音乐剧选段', '了解音乐剧的发展历史', '观看音乐剧纪录片'],
  },
  {
    id: 2,
    icon: '🗣️',
    name: '西语周',
    category: '技能学习',
    description: '学习西班牙语，打开通往新世界的大门',
    ideas: ['学习基础发音和问候语', '看一部西班牙语电影', '用西班牙语写一段自我介绍', '学一首西班牙语歌'],
  },
  {
    id: 3,
    icon: '🧶',
    name: '钩织周',
    category: '艺术创意',
    description: '用双手创造温暖，编织生活的美好',
    ideas: ['学会基础钩织针法', '完成一个小杯垫', '尝试钩织一朵花', '设计自己的图案'],
  },
  {
    id: 4,
    icon: '📸',
    name: '摄影周',
    category: '艺术创意',
    description: '用镜头捕捉生活中的美好瞬间',
    ideas: ['每天拍一张主题照片', '学习构图技巧', '尝试黑白摄影', '制作一本摄影集'],
  },
  {
    id: 5,
    icon: '🧘',
    name: '冥想周',
    category: '健康生活',
    description: '静下心来，与自己对话，寻找内心的平静',
    ideas: ['每天冥想10分钟', '尝试不同的冥想方式', '记录冥想感受', '学习呼吸技巧'],
  },
  {
    id: 6,
    icon: '🍳',
    name: '烹饪周',
    category: '生活美学',
    description: '探索美食的奥秘，享受烹饪的乐趣',
    ideas: ['学做一道新菜', '尝试不同国家的料理', '记录食谱和心得', '为家人做一顿大餐'],
  },
]);

const useTheme = (theme) => {
  router.push('/planner');
};
</script>

<style scoped>
.inspiration-page {
  padding: 100px 0 60px;
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

.inspiration-categories {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 16px;
  margin-bottom: 48px;
}

.insp-category-card {
  border-radius: 16px;
  padding: 24px 16px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
}

.insp-category-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-medium);
}

.cat-icon {
  font-size: 36px;
  margin-bottom: 8px;
  display: block;
}

.cat-name {
  font-size: 15px;
  color: var(--text-dark);
  margin-bottom: 4px;
}

.cat-count {
  font-size: 12px;
  color: var(--text-muted);
}

.inspiration-themes {
  margin-top: 48px;
}

.section-title {
  font-size: 24px;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.insp-themes-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
}

.insp-theme-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 28px;
  box-shadow: var(--shadow-light);
  transition: all 0.3s;
}

.insp-theme-card:hover {
  box-shadow: var(--shadow-medium);
}

.insp-theme-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.insp-theme-icon {
  font-size: 36px;
}

.insp-theme-info {
  flex: 1;
}

.insp-theme-name {
  font-size: 18px;
  color: var(--text-primary);
}

.insp-theme-category {
  font-size: 12px;
  color: var(--text-secondary);
  background: var(--background-secondary);
  padding: 2px 8px;
  border-radius: 4px;
}

.insp-theme-desc {
  font-size: 14px;
  color: var(--text-muted);
  margin-bottom: 16px;
  line-height: 1.6;
}

.insp-theme-ideas {
  margin-bottom: 20px;
}

.ideas-title {
  font-size: 13px;
  color: var(--text-dark);
  margin-bottom: 8px;
}

.ideas-list {
  list-style: none;
  padding: 0;
}

.ideas-list li {
  font-size: 13px;
  color: var(--text-muted);
  padding: 4px 0;
  padding-left: 16px;
  position: relative;
}

.ideas-list li::before {
  content: '•';
  position: absolute;
  left: 0;
  color: var(--primary-color);
}

.btn-full {
  width: 100%;
}

@media (max-width: 1024px) {
  .inspiration-categories {
    grid-template-columns: repeat(3, 1fr);
  }
  
  .insp-themes-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .inspiration-categories {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
