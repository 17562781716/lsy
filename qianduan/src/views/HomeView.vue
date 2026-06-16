<template>
  <div class="home-container">
    <!-- Hero 区域 -->
    <section class="hero-section">
      <div class="container">
        <div class="hero-content">
          <div class="hero-left">
            <div class="hero-slogan">
              <p class="slogan-line">心无旁骛，缓行自愈</p>
              <p class="slogan-line">一年，为三十六程短暂</p>
              <p class="slogan-line">一月，是四番轮回朝夕</p>
            </div>
          </div>
          <div class="hero-center">
            <h1 class="hero-title">主题周</h1>
            <p class="hero-subtitle">为自己的人生扩容</p>
            <div class="hero-actions">
              <RouterLink to="/themes" class="hero-btn-primary">探索主题</RouterLink>
              <RouterLink to="/checkin" class="hero-btn-secondary">开始打卡</RouterLink>
            </div>
          </div>
          <div class="hero-right">
            <div class="theme-categories">
              <RouterLink to="/themes?category=art" class="theme-category-item item-1">
                <span class="category-icon">🧘</span>
                <span class="category-name">冥想周</span>
              </RouterLink>
              <RouterLink to="/themes?category=growth" class="theme-category-item item-2">
                <span class="category-icon">🗣️</span>
                <span class="category-name">西语周</span>
              </RouterLink>
              <RouterLink to="/themes?category=art" class="theme-category-item item-3">
                <span class="category-icon">🎨</span>
                <span class="category-name">绘画周</span>
              </RouterLink>
              <RouterLink to="/themes?category=life" class="theme-category-item item-4">
                <span class="category-icon">🍳</span>
                <span class="category-name">烹饪周</span>
              </RouterLink>
              <RouterLink to="/custom-week" class="theme-category-item item-5 custom">
                <span class="category-icon">✨</span>
                <span class="category-name">自定义周</span>
              </RouterLink>
              <RouterLink to="/themes?category=art" class="theme-category-item item-6">
                <span class="category-icon">📸</span>
                <span class="category-name">摄影周</span>
              </RouterLink>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 什么是主题周 -->
    <section class="about-section">
      <div class="container">
        <div class="about-card">
          <h2 class="about-title">什么是主题周？</h2>
          <div class="about-description">
            <p>把一周定为一个主题，专心做好一件小事。</p>
            <p>不用长期坚持，只需要专注七天就好。</p>
            <p>在平淡的生活里，换一种不一样的生活节奏。</p>
          </div>
          <div class="stats-row">
            <div class="stat-item">
              <div class="stat-number">52</div>
              <div class="stat-label">周/年</div>
            </div>
            <div class="stat-item">
              <div class="stat-number">4</div>
              <div class="stat-label">周/月</div>
            </div>
            <div class="stat-item">
              <div class="stat-number">7</div>
              <div class="stat-label">天/周</div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 热门主题周 -->
    <section class="hot-themes-section">
      <div class="container">
        <h2 class="section-title">热门主题周</h2>
        <div class="themes-grid">
          <div v-for="theme in hotThemes" :key="theme.id" class="theme-card">
            <div class="theme-card-icon">{{ theme.icon }}</div>
            <h3 class="theme-card-name">{{ theme.name }}</h3>
            <p class="theme-card-desc">{{ theme.description }}</p>
            <div class="theme-card-meta">
              <span class="theme-card-count">{{ theme.participants }}人参与</span>
              <span class="theme-card-tag">{{ theme.tag }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 热门主题周数据
const hotThemes = ref([
  {
    id: 1,
    icon: '🎭',
    name: '音乐剧周',
    description: '走进剧场，感受音乐与戏剧的碰撞',
    participants: 328,
    tag: '艺术'
  },
  {
    id: 2,
    icon: '📖',
    name: '阅读周',
    description: '放下手机，沉浸在文字的世界里',
    participants: 512,
    tag: '成长'
  },
  {
    id: 3,
    icon: '🧶',
    name: '钩织周',
    description: '用双手创造温暖，编织生活的美好',
    participants: 186,
    tag: '手工'
  },
  {
    id: 4,
    icon: '🏃',
    name: '健身周',
    description: '动起来，感受身体的力量与活力',
    participants: 425,
    tag: '健康'
  },
  {
    id: 5,
    icon: '🎬',
    name: '观影周',
    description: '每天一部电影，体验不同的人生',
    participants: 267,
    tag: '艺术'
  },
  {
    id: 6,
    icon: '🎨',
    name: '绘画周',
    description: '用画笔记录生活，表达内心世界',
    participants: 143,
    tag: '艺术'
  }
]);
</script>

<style scoped>
.home-container {
  min-height: 100vh;
}

/* Hero 区域 */
.hero-section {
  padding: 60px 0 40px;
  background: linear-gradient(135deg, #ffffff 0%, #e8f5e9 50%, #c8e6c9 100%);
}

.hero-content {
  display: grid;
  grid-template-columns: 1fr 1.2fr 1fr;
  gap: 20px;
  align-items: center;
  min-height: 340px;
  max-width: 900px;
  margin: 0 auto;
}

.hero-left {
  text-align: left;
}

.slogan-line {
  font-size: 18px;
  color: var(--text-muted);
  line-height: 2;
}

.hero-center {
  text-align: center;
}

.hero-title {
  font-size: 64px;
  font-weight: 700;
  color: #000000;
  margin-bottom: 16px;
  letter-spacing: 8px;
  font-family: 'Songti SC', 'SimSun', 'STSong', 'FangSong', serif;
}

.hero-subtitle {
  font-size: 24px;
  color: #000000;
  margin-bottom: 32px;
  font-family: 'Songti SC', 'SimSun', 'STSong', 'FangSong', serif;
}

.hero-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
}

.hero-btn-primary {
  padding: 12px 32px;
  font-size: 16px;
  border-radius: 24px;
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  color: white;
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  gap: 4px;
  transition: all 0.2s;
  box-shadow: 0 2px 8px rgba(46, 125, 50, 0.3);
  border: none;
}

.hero-btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(46, 125, 50, 0.4);
}

.hero-btn-secondary {
  padding: 12px 32px;
  font-size: 16px;
  border-radius: 24px;
  background-color: transparent;
  color: var(--primary-color);
  border: 1px solid var(--primary-color);
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  gap: 4px;
  transition: all 0.2s;
}

.hero-btn-secondary:hover {
  background-color: var(--background-secondary);
}

.hero-right {
  display: flex;
  justify-content: flex-end;
  position: relative;
}

.theme-categories {
  position: relative;
  width: 280px;
  height: 320px;
}

.theme-category-item {
  position: absolute;
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 24px;
  box-shadow: var(--shadow-light);
  transition: all 0.3s ease;
  cursor: pointer;
  animation: float 3s ease-in-out infinite;
  text-decoration: none;
  color: inherit;
}

.theme-category-item:hover {
  box-shadow: var(--shadow-medium);
  z-index: 10;
}

.theme-category-item.custom {
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  color: white;
}

/* 不规则分布位置 */
.item-1 {
  top: 10px;
  right: 40px;
  animation-delay: 0s;
}

.item-2 {
  top: 60px;
  right: 100px;
  animation-delay: 0.5s;
}

.item-3 {
  top: 120px;
  right: 20px;
  animation-delay: 1s;
}

.item-4 {
  top: 170px;
  right: 90px;
  animation-delay: 1.5s;
}

.item-5 {
  top: 220px;
  right: 30px;
  animation-delay: 2s;
}

.item-6 {
  top: 270px;
  right: 110px;
  animation-delay: 2.5s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-8px);
  }
}

.category-icon {
  font-size: 18px;
}

.category-name {
  font-size: 13px;
  font-weight: 500;
  white-space: nowrap;
}

/* 什么是主题周 */
.about-section {
  padding: 60px 0;
}

.about-card {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 24px;
  padding: 48px;
  text-align: center;
  box-shadow: var(--shadow-medium);
  backdrop-filter: blur(10px);
}

.about-title {
  font-size: 28px;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.about-description {
  margin-bottom: 40px;
}

.about-description p {
  font-size: 16px;
  color: var(--text-muted);
  line-height: 2;
}

.stats-row {
  display: flex;
  justify-content: center;
  gap: 60px;
}

.stat-item {
  text-align: center;
}

.stat-number {
  font-size: 56px;
  font-weight: 700;
  color: var(--primary-color);
  line-height: 1;
}

.stat-label {
  font-size: 14px;
  color: var(--text-muted);
  margin-top: 8px;
}

/* 热门主题周 */
.hot-themes-section {
  padding: 60px 0;
}

.section-title {
  font-size: 28px;
  color: var(--text-primary);
  text-align: center;
  margin-bottom: 40px;
}

.themes-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.theme-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 32px;
  text-align: center;
  box-shadow: var(--shadow-light);
  transition: all 0.3s;
  cursor: pointer;
}

.theme-card:hover {
  transform: translateY(-8px);
  box-shadow: var(--shadow-large);
}

.theme-card-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.theme-card-name {
  font-size: 20px;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.theme-card-desc {
  font-size: 14px;
  color: var(--text-muted);
  margin-bottom: 16px;
  line-height: 1.6;
}

.theme-card-meta {
  display: flex;
  justify-content: center;
  gap: 12px;
  font-size: 12px;
}

.theme-card-count {
  color: var(--text-secondary);
}

.theme-card-tag {
  background: var(--background-secondary);
  color: var(--primary-color);
  padding: 2px 8px;
  border-radius: 4px;
}

/* 响应式 */
@media (max-width: 1024px) {
  .hero-content {
    grid-template-columns: 1fr;
    text-align: center;
  }
  
  .hero-left,
  .hero-right {
    display: none;
  }
  
  .themes-grid,
  .insights-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .hero-title {
    font-size: 42px;
  }
  
  .stats-row {
    gap: 30px;
  }
  
  .stat-number {
    font-size: 40px;
  }
  
  .themes-grid,
  .insights-grid {
    grid-template-columns: 1fr;
  }
  
  .about-card {
    padding: 24px;
  }
}
</style>
