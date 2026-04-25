<template>
  <div class="my-weeks-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">我的主题周</h1>
        <p class="page-subtitle">回顾你走过的每一周，见证成长的轨迹</p>
      </div>

      <div class="stats-overview">
        <div class="stat-card">
          <div class="stat-icon">📅</div>
          <div class="stat-value">6</div>
          <div class="stat-label">已完成主题周</div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">🔥</div>
          <div class="stat-value">42</div>
          <div class="stat-label">连续打卡天数</div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">⭐</div>
          <div class="stat-value">3</div>
          <div class="stat-label">正在进行中</div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">🏆</div>
          <div class="stat-value">98%</div>
          <div class="stat-label">完成率</div>
        </div>
      </div>

      <div class="weeks-timeline">
        <h2 class="section-title">主题周历程</h2>
        <div class="timeline">
          <div v-for="(week, index) in myWeeksList" :key="week.id" class="timeline-item">
            <div class="timeline-marker" :class="week.status">
              <span class="marker-icon">{{ week.icon }}</span>
            </div>
            <div class="timeline-content">
              <div class="timeline-card">
                <div class="timeline-header">
                  <h3 class="timeline-name">{{ week.name }}</h3>
                  <span class="timeline-status" :class="week.status">{{ week.statusText }}</span>
                </div>
                <p class="timeline-date">{{ week.startDate }} - {{ week.endDate }}</p>
                <p class="timeline-desc">{{ week.description }}</p>
                <div class="timeline-progress" v-if="week.status === 'ongoing'">
                  <div class="progress-bar">
                    <div class="progress-fill" :style="{ width: week.progress + '%' }"></div>
                  </div>
                  <span class="progress-text">{{ week.progress }}%</span>
                </div>
                <div class="timeline-achievements" v-if="week.achievements">
                  <span v-for="(ach, idx) in week.achievements" :key="idx" class="achievement-tag">{{ ach }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const myWeeksList = ref([
  {
    id: 1,
    icon: '🎭',
    name: '音乐剧周',
    startDate: '2026.04.20',
    endDate: '2026.04.26',
    description: '走进剧场，感受音乐与戏剧的碰撞',
    status: 'ongoing',
    statusText: '进行中',
    progress: 60,
  },
  {
    id: 2,
    icon: '📖',
    name: '阅读周',
    startDate: '2026.04.13',
    endDate: '2026.04.19',
    description: '放下手机，沉浸在文字的世界里',
    status: 'completed',
    statusText: '已完成',
    achievements: ['读完《百年孤独》', '写读书笔记3篇'],
  },
  {
    id: 3,
    icon: '🧶',
    name: '钩织周',
    startDate: '2026.04.06',
    endDate: '2026.04.12',
    description: '用双手创造温暖，编织生活的美好',
    status: 'completed',
    statusText: '已完成',
    achievements: ['学会基础针法', '完成杯垫2个'],
  },
  {
    id: 4,
    icon: '🏃',
    name: '健身周',
    startDate: '2026.03.30',
    endDate: '2026.04.05',
    description: '动起来，感受身体的力量与活力',
    status: 'completed',
    statusText: '已完成',
    achievements: ['晨跑5公里', '减重1.5kg'],
  },
  {
    id: 5,
    icon: '🗣️',
    name: '西语周',
    startDate: '2026.03.23',
    endDate: '2026.03.29',
    description: '学习西班牙语，打开新世界的大门',
    status: 'completed',
    statusText: '已完成',
    achievements: ['掌握基础问候', '学会数字1-100'],
  },
  {
    id: 6,
    icon: '🎬',
    name: '观影周',
    startDate: '2026.03.16',
    endDate: '2026.03.22',
    description: '每天一部电影，体验不同的人生',
    status: 'completed',
    statusText: '已完成',
    achievements: ['观影7部', '写影评3篇'],
  },
  {
    id: 7,
    icon: '🌱',
    name: '冥想周',
    startDate: '2026.03.09',
    endDate: '2026.03.15',
    description: '静下心来，与自己对话',
    status: 'completed',
    statusText: '已完成',
    achievements: ['每日冥想10分钟', '睡眠质量提升'],
  },
]);
</script>

<style scoped>
.my-weeks-page {
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

.stats-overview {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 48px;
}

.stat-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 28px;
  text-align: center;
  box-shadow: var(--shadow-light);
  transition: all 0.3s;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-medium);
}

.stat-icon {
  font-size: 36px;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  color: var(--primary-color);
  margin-bottom: 4px;
}

.stat-label {
  font-size: 13px;
  color: var(--text-muted);
}

.section-title {
  font-size: 24px;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.timeline {
  position: relative;
  padding-left: 40px;
}

.timeline::before {
  content: '';
  position: absolute;
  left: 20px;
  top: 0;
  bottom: 0;
  width: 2px;
  background: linear-gradient(180deg, var(--primary-color), var(--primary-color-light));
}

.timeline-item {
  position: relative;
  margin-bottom: 24px;
}

.timeline-marker {
  position: absolute;
  left: -40px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: white;
  border: 2px solid var(--border-color);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1;
}

.timeline-marker.ongoing {
  border-color: var(--primary-color);
  background: var(--background-secondary);
}

.timeline-marker.completed {
  border-color: var(--primary-color);
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
}

.marker-icon {
  font-size: 18px;
}

.timeline-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 24px;
  box-shadow: var(--shadow-light);
}

.timeline-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
}

.timeline-name {
  font-size: 18px;
  color: var(--text-primary);
}

.timeline-status {
  font-size: 12px;
  padding: 4px 12px;
  border-radius: 12px;
}

.timeline-status.ongoing {
  background: #fff3e0;
  color: #ff9800;
}

.timeline-status.completed {
  background: #e8f5e9;
  color: var(--primary-color);
}

.timeline-date {
  font-size: 13px;
  color: var(--text-muted);
  margin-bottom: 8px;
}

.timeline-desc {
  font-size: 14px;
  color: var(--text-dark);
  margin-bottom: 12px;
}

.timeline-progress {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.progress-bar {
  flex: 1;
  height: 6px;
  background: var(--background-secondary);
  border-radius: 3px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, var(--primary-color), var(--primary-color-light));
  border-radius: 3px;
  transition: width 0.5s ease;
}

.progress-text {
  font-size: 12px;
  color: var(--primary-color);
  font-weight: 600;
}

.timeline-achievements {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.achievement-tag {
  font-size: 12px;
  padding: 4px 10px;
  background: var(--background-secondary);
  color: var(--primary-color);
  border-radius: 8px;
}

@media (max-width: 1024px) {
  .stats-overview {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .stats-overview {
    grid-template-columns: 1fr;
  }
}
</style>
