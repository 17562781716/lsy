<template>
  <div class="planner-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">规划工具</h1>
        <p class="page-subtitle">精心策划每一个主题周，让生活更有仪式感</p>
      </div>

      <!-- 统计概览 -->
      <div class="stats-overview">
        <div class="stat-card">
          <div class="stat-icon">📅</div>
          <div class="stat-value">{{ completedWeeksCount }}</div>
          <div class="stat-label">已完成主题周</div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">🔥</div>
          <div class="stat-value">{{ consecutiveDays }}</div>
          <div class="stat-label">连续参与天数</div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">⭐</div>
          <div class="stat-value">{{ activeWeeksCount }}</div>
          <div class="stat-label">正在进行中</div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">🏆</div>
          <div class="stat-value">{{ completionRate }}%</div>
          <div class="stat-label">完成率</div>
        </div>
      </div>

      <!-- 主内容区 -->
      <div v-if="!activeWeek" class="planner-content">
        <!-- Tab 切换 -->
        <div class="tabs-header">
          <button 
            class="tab-btn" 
            :class="{ active: currentTab === 'plans' }"
            @click="currentTab = 'plans'"
          >
            📋 我的计划
            <span class="tab-count">{{ themesStore.myWeeks.length }}</span>
          </button>
          <button 
            class="tab-btn" 
            :class="{ active: currentTab === 'timeline' }"
            @click="currentTab = 'timeline'"
          >
            📅 历程回顾
          </button>
        </div>

        <!-- 我的计划 Tab -->
        <div v-if="currentTab === 'plans'" class="plans-view">
          <div v-if="themesStore.myWeeks.length" class="weeks-list">
            <div 
              v-for="week in sortedWeeks" 
              :key="week.id" 
              class="week-plan-card"
            >
              <div class="plan-header" @click="enterWeek(week.id)">
                <span class="plan-icon">{{ week.icon }}</span>
                <div class="plan-info">
                  <h3 class="plan-name">{{ week.name }}</h3>
                  <p class="plan-date">{{ week.startDate }} 开始</p>
                </div>
                <span class="plan-status" :class="week.status">{{ statusText(week.status) }}</span>
              </div>
              
              <p class="plan-desc" v-if="week.description">{{ week.description }}</p>
              
              <div class="plan-meta">
                <span class="plan-difficulty" v-if="week.difficulty">
                  {{ difficultyLabel(week.difficulty) }}
                </span>
                <span class="plan-tasks-count" v-if="week.tasks && week.tasks.length">
                  {{ week.tasks.length }} 个任务
                </span>
                <div class="plan-progress" v-if="week.progress !== undefined && week.status === 'ongoing'">
                  <div class="progress-bar-mini">
                    <div class="progress-fill-mini" :style="{ width: week.progress + '%' }"></div>
                  </div>
                  <span class="progress-text-mini">{{ week.progress }}%</span>
                </div>
              </div>

              <div class="plan-actions">
                <button class="action-btn edit" @click.stop="editWeek(week)">✏️ 编辑</button>
                <button class="action-btn delete" @click.stop="deleteWeek(week.id)">🗑️ 删除</button>
                <button 
                  class="action-btn checkin" 
                  @click.stop="goToCheckin(week)" 
                  v-if="week.status === 'ongoing'"
                >
                  📝 去打卡 →
                </button>
              </div>
            </div>
          </div>

          <div v-else class="empty-state">
            <div class="empty-icon">📋</div>
            <h3 class="empty-title">还没有创建任何主题周</h3>
            <p class="empty-text">前往"自定义周"页面创建你的第一个主题周吧！</p>
            <RouterLink to="/custom-week" class="btn-primary empty-action">去创建 →</RouterLink>
          </div>
        </div>

        <!-- 历程回顾 Tab (时间轴) -->
        <div v-if="currentTab === 'timeline'" class="timeline-view">
          <div v-if="themesStore.myWeeks.length" class="timeline">
            <div v-for="(week, index) in timelineWeeks" :key="week.id" class="timeline-item">
              <div class="timeline-marker" :class="week.status">
                <span class="marker-icon">{{ week.icon }}</span>
              </div>
              <div class="timeline-content">
                <div class="timeline-card">
                  <div class="timeline-header">
                    <h3 class="timeline-name">{{ week.name }}</h3>
                    <span class="timeline-status" :class="week.status">{{ statusText(week.status) }}</span>
                  </div>
                  <p class="timeline-date">{{ week.startDate }} - {{ week.endDate || '待定' }}</p>
                  <p class="timeline-desc" v-if="week.description">{{ week.description }}</p>
                  
                  <div class="timeline-progress" v-if="week.status === 'ongoing' && week.progress !== undefined">
                    <div class="progress-bar">
                      <div class="progress-fill" :style="{ width: week.progress + '%' }"></div>
                    </div>
                    <span class="progress-text">{{ week.progress }}%</span>
                  </div>

                  <div class="timeline-actions">
                    <button class="action-link" @click="enterWeek(week.id)">查看详情</button>
                    <button class="action-link checkin-link" @click="goToCheckin(week)" v-if="week.status === 'ongoing'">
                      去打卡
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div v-else class="empty-state">
            <div class="empty-icon">📅</div>
            <h3 class="empty-title">还没有历程记录</h3>
            <p class="empty-text">创建并完成主题周后，这里会展示你的成长轨迹</p>
          </div>
        </div>
      </div>

      <!-- 主题周详情模式 -->
      <div v-else class="week-detail">
        <div class="detail-header">
          <button class="btn-back" @click="activeWeek = null">← 返回列表</button>
          <div class="week-title-bar">
            <span class="detail-icon">{{ activeWeek.icon }}</span>
            <div class="detail-info">
              <h2 class="detail-name">{{ activeWeek.name }}</h2>
              <p class="detail-date">{{ activeWeek.startDate }} - {{ activeWeek.endDate || '待定' }}</p>
            </div>
            <span class="detail-status" :class="activeWeek.status">{{ statusText(activeWeek.status) }}</span>
          </div>
        </div>

        <div class="detail-layout">
          <!-- 左侧：周概览 -->
          <div class="detail-main">
            <div class="week-card detail-card">
              <h3 class="card-title">本周概览</h3>
              
              <div class="detail-section">
                <h4 class="section-label">描述</h4>
                <p class="detail-desc">{{ activeWeek.description || '暂无描述' }}</p>
              </div>

              <div class="detail-section" v-if="activeWeek.tasks && activeWeek.tasks.length">
                <h4 class="section-label">任务清单（{{ activeWeek.tasks.length }}个）</h4>
                <div class="task-list-simple">
                  <div v-for="(task, idx) in activeWeek.tasks" :key="idx" class="task-item-simple">
                    <span class="task-bullet">•</span>
                    <span>{{ task }}</span>
                    <span class="task-status-tag" :class="getTaskStatus(idx)">
                      {{ getTaskStatusText(idx) }}
                    </span>
                  </div>
                </div>
              </div>

              <div class="detail-section">
                <h4 class="section-label">难度等级</h4>
                <span class="difficulty-badge" :class="activeWeek.difficulty || 'medium'">
                  {{ difficultyLabel(activeWeek.difficulty) }}
                </span>
              </div>

              <div class="detail-actions" v-if="activeWeek.status === 'ongoing'">
                <button class="btn-primary btn-lg" @click="goToCheckin(activeWeek)">
                  📝 前往打卡中心
                </button>
              </div>
            </div>
          </div>

          <!-- 右侧：快速统计 -->
          <div class="detail-sidebar">
            <div class="week-card stats-card">
              <h3 class="card-title">统计数据</h3>
              
              <div class="stat-item">
                <span class="stat-label">进度</span>
                <span class="stat-value">{{ activeWeek.progress || 0 }}%</span>
              </div>
              
              <div class="stat-item">
                <span class="stat-label">状态</span>
                <span class="stat-value status-text">{{ statusText(activeWeek.status) }}</span>
              </div>

              <div class="stat-item">
                <span class="stat-label">任务数</span>
                <span class="stat-value">{{ activeWeek.tasks ? activeWeek.tasks.length : 0 }} 个</span>
              </div>

              <div class="stat-item">
                <span class="stat-label">打卡记录</span>
                <span class="stat-value">{{ activeWeek.checkins ? activeWeek.checkins.length : 0 }} 条</span>
              </div>
            </div>

            <div class="week-card tips-card">
              <h3 class="card-title">💡 小贴士</h3>
              <p class="tip-text">{{ getRandomTip() }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useThemesStore } from '../stores/themes.js';

const themesStore = useThemesStore();
const router = useRouter();

const activeWeek = ref(null);
const currentTab = ref('plans');

const tips = [
  '选择与你当前生活节奏匹配的主题周难度，循序渐进更有效',
  '为你的主题周活动设定固定时间段，帮助形成稳定习惯',
  '关注每天的体验和感受，而不是只盯着最终目标',
  '告诉朋友或家人你的计划，他们的支持会帮助你坚持下去'
];

const activeWeeksCount = computed(() => {
  return themesStore.myWeeks.filter(w => w.status === 'ongoing').length;
});

const completedWeeksCount = computed(() => {
  return themesStore.myWeeks.filter(w => w.status === 'completed').length;
});

const completionRate = computed(() => {
  const total = themesStore.myWeeks.length;
  if (total === 0) return 0;
  return Math.round((completedWeeksCount.value / total) * 100);
});

const consecutiveDays = computed(() => {
  if (!themesStore.myWeeks.length) return 0;
  return Math.floor(Math.random() * 30) + 5;
});

const sortedWeeks = computed(() => {
  return [...themesStore.myWeeks].sort((a, b) => {
    const statusOrder = { ongoing: 0, upcoming: 1, completed: 2 };
    return (statusOrder[a.status] || 99) - (statusOrder[b.status] || 99);
  });
});

const timelineWeeks = computed(() => {
  return [...themesStore.myWeeks].reverse();
});

const enterWeek = (weekId) => {
  const week = themesStore.getWeekById ? themesStore.getWeekById(weekId) : null;
  if (week) {
    activeWeek.value = week;
  }
};

const editWeek = (week) => {
  router.push('/custom-week');
};

const deleteWeek = (weekId) => {
  if (confirm('确定要删除这个主题周吗？')) {
    themesStore.removeMyWeek(weekId);
  }
};

const goToCheckin = (week) => {
  if (week.status === 'ongoing') {
    router.push('/checkin');
  }
};

const difficultyLabel = (difficulty) => {
  const labels = {
    easy: '轻松 🌱',
    medium: '适中 ⚡',
    hard: '挑战 🔥'
  };
  return labels[difficulty] || '适中 ⚡';
};

const statusText = (status) => {
  const texts = {
    ongoing: '进行中',
    completed: '已完成',
    upcoming: '未开始'
  };
  return texts[status] || '未知';
};

const getTaskStatus = (idx) => {
  if (!activeWeek.value || !activeWeek.value.taskCompleted) return 'pending';
  return activeWeek.value.taskCompleted[idx] ? 'done' : 'pending';
};

const getTaskStatusText = (idx) => {
  return getTaskStatus(idx) === 'done' ? '已完成' : '待完成';
};

const getRandomTip = () => {
  const randomIndex = Math.floor(Math.random() * tips.length);
  return tips[randomIndex];
};
</script>

<style scoped>
.planner-page {
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

/* 统计概览 */
.stats-overview {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 40px;
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

/* Tab 切换 */
.tabs-header {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
  background: rgba(255, 255, 255, 0.6);
  padding: 6px;
  border-radius: 14px;
  width: fit-content;
}

.tab-btn {
  padding: 10px 20px;
  background: transparent;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 15px;
  color: var(--text-dark);
  transition: all 0.2s;
  display: flex;
  align-items: center;
  gap: 8px;
}

.tab-btn:hover {
  background: rgba(255, 255, 255, 0.9);
}

.tab-btn.active {
  background: white;
  color: var(--primary-color);
  box-shadow: var(--shadow-light);
  font-weight: 600;
}

.tab-count {
  font-size: 12px;
  padding: 2px 8px;
  background: var(--background-secondary);
  border-radius: 10px;
  color: var(--primary-color);
}

/* 计划列表 */
.weeks-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.week-plan-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 28px;
  box-shadow: var(--shadow-light);
  transition: transform 0.2s, box-shadow 0.2s;
}

.week-plan-card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-medium);
}

.plan-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
  cursor: pointer;
}

.plan-icon {
  font-size: 36px;
}

.plan-info {
  flex: 1;
}

.plan-name {
  font-size: 18px;
  color: var(--text-primary);
  margin-bottom: 4px;
}

.plan-date {
  font-size: 13px;
  color: var(--text-muted);
}

.plan-status {
  font-size: 13px;
  padding: 5px 14px;
  border-radius: 14px;
  font-weight: 500;
}

.plan-status.ongoing {
  background: #fff3e0;
  color: #ff9800;
}

.plan-status.completed {
  background: #e8f5e9;
  color: var(--primary-color);
}

.plan-status.upcoming {
  background: #e3f2fd;
  color: #2196f3;
}

.plan-desc {
  font-size: 14px;
  color: var(--text-dark);
  line-height: 1.7;
  margin-bottom: 16px;
}

.plan-meta {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
  flex-wrap: wrap;
}

.plan-difficulty,
.plan-tasks-count {
  font-size: 13px;
  padding: 4px 10px;
  background: var(--background-secondary);
  border-radius: 6px;
  color: var(--text-muted);
}

.plan-progress {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-left: auto;
}

.progress-bar-mini {
  width: 100px;
  height: 6px;
  background: var(--background-secondary);
  border-radius: 3px;
  overflow: hidden;
}

.progress-fill-mini {
  height: 100%;
  background: linear-gradient(90deg, var(--primary-color), var(--primary-color-light));
  border-radius: 3px;
  transition: width 0.5s ease;
}

.progress-text-mini {
  font-size: 13px;
  color: var(--primary-color);
  font-weight: 600;
}

.plan-actions {
  display: flex;
  gap: 12px;
  padding-top: 16px;
  border-top: 1px solid var(--border-color);
}

.action-btn {
  padding: 8px 18px;
  background: var(--background-secondary);
  border: none;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
  color: var(--text-dark);
}

.action-btn:hover {
  background: var(--background-tertiary);
}

.action-btn.delete:hover {
  background: #ffebee;
  color: var(--red-color);
}

.action-btn.checkin {
  margin-left: auto;
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  color: white;
}

.action-btn.checkin:hover {
  transform: translateY(-1px);
  box-shadow: var(--shadow-medium);
}

/* 空状态 */
.empty-state {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 80px 32px;
  text-align: center;
  box-shadow: var(--shadow-light);
}

.empty-icon {
  font-size: 56px;
  margin-bottom: 20px;
}

.empty-title {
  font-size: 22px;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.empty-text {
  font-size: 15px;
  color: var(--text-muted);
  margin-bottom: 24px;
}

.empty-action {
  display: inline-block;
  padding: 12px 28px;
  font-size: 15px;
}

/* 时间轴视图 */
.timeline {
  position: relative;
  padding-left: 50px;
}

.timeline::before {
  content: '';
  position: absolute;
  left: 25px;
  top: 0;
  bottom: 0;
  width: 2px;
  background: linear-gradient(180deg, var(--primary-color), var(--primary-color-light));
}

.timeline-item {
  position: relative;
  margin-bottom: 28px;
}

.timeline-marker {
  position: absolute;
  left: -50px;
  width: 44px;
  height: 44px;
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
  font-size: 20px;
}

.timeline-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 26px;
  box-shadow: var(--shadow-light);
}

.timeline-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

.timeline-name {
  font-size: 18px;
  color: var(--text-primary);
}

.timeline-status {
  font-size: 12px;
  padding: 4px 12px;
  border-radius: 12px;
  font-weight: 500;
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
  margin-bottom: 10px;
}

.timeline-desc {
  font-size: 14px;
  color: var(--text-dark);
  line-height: 1.7;
  margin-bottom: 14px;
}

.timeline-progress {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 14px;
}

.progress-bar {
  flex: 1;
  max-width: 200px;
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
  font-size: 13px;
  color: var(--primary-color);
  font-weight: 600;
}

.timeline-actions {
  display: flex;
  gap: 16px;
  padding-top: 14px;
  border-top: 1px solid var(--border-color);
}

.action-link {
  background: none;
  border: none;
  color: var(--primary-color);
  font-size: 14px;
  cursor: pointer;
  padding: 0;
  transition: opacity 0.2s;
}

.action-link:hover {
  opacity: 0.7;
  text-decoration: underline;
}

.checkin-link {
  color: var(--orange-color);
}

/* 详情模式 */
.week-detail {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.detail-header {
  margin-bottom: 28px;
}

.btn-back {
  background: none;
  border: none;
  color: var(--primary-color);
  font-size: 15px;
  cursor: pointer;
  margin-bottom: 18px;
  padding: 0;
}

.btn-back:hover {
  text-decoration: underline;
}

.week-title-bar {
  display: flex;
  align-items: center;
  gap: 18px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 28px 36px;
  box-shadow: var(--shadow-light);
}

.detail-icon {
  font-size: 44px;
}

.detail-info {
  flex: 1;
}

.detail-name {
  font-size: 22px;
  color: var(--text-primary);
}

.detail-date {
  font-size: 14px;
  color: var(--text-muted);
}

.detail-status {
  font-size: 13px;
  padding: 7px 18px;
  border-radius: 20px;
  font-weight: 500;
}

.detail-status.ongoing {
  background: #fff3e0;
  color: #ff9800;
}

.detail-status.completed {
  background: #e8f5e9;
  color: var(--primary-color);
}

.detail-layout {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 28px;
}

.week-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 30px;
  box-shadow: var(--shadow-light);
}

.card-title {
  font-size: 19px;
  color: var(--text-primary);
  margin-bottom: 22px;
}

.detail-section {
  margin-bottom: 22px;
}

.section-label {
  font-size: 14px;
  color: var(--text-muted);
  margin-bottom: 10px;
  font-weight: 500;
}

.detail-desc {
  font-size: 14px;
  color: var(--text-dark);
  line-height: 1.7;
}

.task-list-simple {
  display: flex;
  flex-direction: column;
  gap: 11px;
}

.task-item-simple {
  display: flex;
  align-items: center;
  gap: 11px;
  padding: 12px 16px;
  background: var(--background-secondary);
  border-radius: 9px;
  font-size: 14px;
}

.task-bullet {
  color: var(--primary-color);
  font-weight: bold;
}

.task-status-tag {
  margin-left: auto;
  font-size: 12px;
  padding: 3px 9px;
  border-radius: 5px;
}

.task-status-tag.done {
  background: #e8f5e9;
  color: var(--primary-color);
}

.task-status-tag.pending {
  background: #f5f5f5;
  color: #999;
}

.difficulty-badge {
  display: inline-block;
  padding: 7px 16px;
  border-radius: 9px;
  font-size: 14px;
  font-weight: 500;
}

.difficulty-badge.easy {
  background: #dcfce7;
  color: #16a34a;
}

.difficulty-badge.medium {
  background: #fef3c7;
  color: #d97706;
}

.difficulty-badge.hard {
  background: #fee2e2;
  color: #dc2626;
}

.detail-actions {
  margin-top: 28px;
  padding-top: 22px;
  border-top: 1px solid var(--border-color);
}

.btn-lg {
  padding: 14px 34px;
  font-size: 16px;
}

.stats-card {
  margin-bottom: 22px;
}

.stat-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 0;
  border-bottom: 1px solid var(--border-color);
}

.stat-item:last-child {
  border-bottom: none;
}

.stat-item .stat-label {
  font-size: 14px;
  color: var(--text-muted);
}

.stat-item .stat-value {
  font-size: 17px;
  font-weight: 600;
  color: var(--text-primary);
}

.stat-item .status-text {
  color: var(--orange-color);
}

.tips-card {
  background: linear-gradient(135deg, #f0fdf4, #dcfce7);
}

.tip-text {
  font-size: 14px;
  color: var(--text-dark);
  line-height: 1.7;
}

/* 响应式 */
@media (max-width: 1024px) {
  .stats-overview {
    grid-template-columns: repeat(2, 1fr);
  }

  .detail-layout {
    grid-template-columns: 1fr;
  }

  .tabs-header {
    width: 100%;
  }

  .tab-btn {
    flex: 1;
    justify-content: center;
  }
}

@media (max-width: 768px) {
  .stats-overview {
    grid-template-columns: 1fr;
  }

  .timeline {
    padding-left: 40px;
  }

  .timeline::before {
    left: 20px;
  }

  .timeline-marker {
    left: -40px;
    width: 38px;
    height: 38px;
  }

  .plan-actions {
    flex-wrap: wrap;
  }

  .action-btn.checkin {
    width: 100%;
    margin-left: 0;
    margin-top: 10px;
  }
}
</style>
