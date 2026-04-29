<template>
  <div class="checkin-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">打卡中心</h1>
        <p class="page-subtitle">记录每一天的坚持，见证自己的成长</p>
      </div>

      <div v-if="currentWeek" class="checkin-layout">
        <!-- 左侧：当前主题周 -->
        <div class="current-week">
          <div class="week-card">
            <div class="week-header">
              <span class="week-icon">{{ currentWeek.icon }}</span>
              <div class="week-info">
                <h2 class="week-name">{{ currentWeek.name }}</h2>
                <p class="week-date">{{ formatDateRange(currentWeek.startDate, currentWeek.endDate) }}</p>
              </div>
              <span class="week-status ongoing">进行中</span>
            </div>

            <div class="week-progress">
              <div class="progress-bar">
                <div class="progress-fill" :style="{ width: currentWeek.progress + '%' }"></div>
              </div>
              <p class="progress-text">已完成 {{ checkedDaysCount }}/7 天</p>
            </div>

            <div class="week-days">
              <div
                v-for="(day, index) in weekDays"
                :key="index"
                class="day-item"
                :class="{ checked: currentWeek.dayCheckins && currentWeek.dayCheckins[index], today: day.isToday }"
                @click="handleDayClick(index)"
              >
                <span class="day-week">{{ day.weekDay }}</span>
                <span class="day-date">{{ day.date }}</span>
                <span class="day-check">{{ currentWeek.dayCheckins && currentWeek.dayCheckins[index] ? '✓' : '+' }}</span>
              </div>
            </div>
          </div>

          <!-- 打卡内容 -->
          <div class="checkin-form" v-if="selectedDayIndex !== null">
            <h3 class="form-title">
              {{ selectedDayInfo.isToday ? '今日打卡' : `${selectedDayInfo.weekDay}打卡` }}
            </h3>
            <div class="form-group">
              <label class="form-label">今天做了什么？</label>
              <textarea
                v-model="checkinContent"
                class="form-textarea"
                placeholder="记录今天的主题周活动..."
                rows="4"
              ></textarea>
            </div>
            <div class="form-actions">
              <button class="btn-primary" @click="submitCheckin">提交打卡</button>
              <button
                v-if="currentWeek.dayCheckins && currentWeek.dayCheckins[selectedDayIndex]"
                class="btn-secondary"
                @click="cancelCheckin"
              >
                取消打卡
              </button>
            </div>
          </div>

          <!-- 任务列表 -->
          <div class="tasks-section" v-if="currentWeek.tasks && currentWeek.tasks.length">
            <h3 class="section-title">本周任务</h3>
            <div class="task-list">
              <div v-for="(task, idx) in currentWeek.tasks" :key="idx" class="task-item">
                <input
                  type="checkbox"
                  :id="'checkin-task-' + idx"
                  :checked="currentWeek.taskCompleted && currentWeek.taskCompleted[idx]"
                  @change="themesStore.toggleTask(currentWeek.id, idx)"
                  class="task-checkbox"
                />
                <label :for="'checkin-task-' + idx" class="task-label">{{ task }}</label>
              </div>
            </div>
          </div>
        </div>

        <!-- 右侧：打卡记录 -->
        <div class="checkin-history">
          <h3 class="history-title">打卡记录</h3>
          <div class="history-list" v-if="currentWeek.checkins && currentWeek.checkins.length">
            <div v-for="record in currentWeek.checkins" :key="record.date" class="history-item">
              <div class="history-date">{{ formatDate(record.date) }}</div>
              <div class="history-content">{{ record.content }}</div>
            </div>
          </div>
          <div v-else class="history-empty">
            <p>还没有打卡记录，快去开始第一天吧！</p>
          </div>
        </div>
      </div>

      <!-- 无进行中的主题周 -->
      <div v-else class="empty-state">
        <div class="empty-card">
          <span class="empty-icon">📅</span>
          <h3 class="empty-title">暂无进行中的主题周</h3>
          <p class="empty-desc">去规划工具创建一个主题周，开始你的打卡之旅吧！</p>
          <router-link to="/planner" class="btn-primary">去创建主题周</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useThemesStore } from '../stores/themes.js';

const themesStore = useThemesStore();

const currentWeek = computed(() => themesStore.getCurrentWeek);
const selectedDayIndex = ref(null);
const checkinContent = ref('');

// 计算7天的日期
const weekDays = computed(() => {
  if (!currentWeek.value) return [];

  const weekDaysNames = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
  const start = new Date(currentWeek.value.startDate);
  const today = new Date();
  today.setHours(0, 0, 0, 0);

  return Array.from({ length: 7 }, (_, i) => {
    const date = new Date(start);
    date.setDate(date.getDate() + i);
    const dateStr = `${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`;
    const isToday = date.getTime() === today.getTime();
    return {
      weekDay: weekDaysNames[i],
      date: dateStr,
      isToday,
    };
  });
});

const selectedDayInfo = computed(() => {
  if (selectedDayIndex.value === null || !weekDays.value.length) return {};
  return weekDays.value[selectedDayIndex.value];
});

const checkedDaysCount = computed(() => {
  if (!currentWeek.value || !currentWeek.value.dayCheckins) return 0;
  return currentWeek.value.dayCheckins.filter(Boolean).length;
});

const handleDayClick = (index) => {
  selectedDayIndex.value = index;

  // 预填充已有打卡内容
  if (currentWeek.value && currentWeek.value.dayCheckins && currentWeek.value.dayCheckins[index]) {
    const start = new Date(currentWeek.value.startDate);
    const checkDate = new Date(start);
    checkDate.setDate(checkDate.getDate() + index);
    const dateStr = checkDate.toISOString().split('T')[0];
    const record = currentWeek.value.checkins.find(c => c.date === dateStr);
    checkinContent.value = record ? record.content : '';
  } else {
    checkinContent.value = '';
  }
};

const submitCheckin = () => {
  if (selectedDayIndex.value === null || !currentWeek.value) return;

  themesStore.submitCheckinContent(
    currentWeek.value.id,
    selectedDayIndex.value,
    checkinContent.value
  );

  checkinContent.value = '';
};

const cancelCheckin = () => {
  if (selectedDayIndex.value === null || !currentWeek.value) return;

  themesStore.toggleDayCheckin(
    currentWeek.value.id,
    selectedDayIndex.value
  );
  checkinContent.value = '';
};

const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  return `${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`;
};

const formatDateRange = (startStr, endStr) => {
  const start = new Date(startStr);
  const end = new Date(endStr);
  const s = `${start.getFullYear()}.${String(start.getMonth() + 1).padStart(2, '0')}.${String(start.getDate()).padStart(2, '0')}`;
  const e = `${String(end.getMonth() + 1).padStart(2, '0')}.${String(end.getDate()).padStart(2, '0')}`;
  return `${s} - ${e}`;
};
</script>

<style scoped>
.checkin-page {
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

.checkin-layout {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;
}

.week-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 32px;
  box-shadow: var(--shadow-light);
  margin-bottom: 24px;
}

.week-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
}

.week-icon {
  font-size: 40px;
}

.week-info {
  flex: 1;
}

.week-name {
  font-size: 20px;
  color: var(--text-primary);
}

.week-date {
  font-size: 13px;
  color: var(--text-muted);
}

.week-status {
  font-size: 12px;
  padding: 6px 16px;
  border-radius: 20px;
}

.week-status.ongoing {
  background: #fff3e0;
  color: #ff9800;
}

.week-progress {
  margin-bottom: 24px;
}

.progress-bar {
  height: 8px;
  background: var(--background-secondary);
  border-radius: 4px;
  overflow: hidden;
  margin-bottom: 8px;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, var(--primary-color), var(--primary-color-light));
  border-radius: 4px;
  transition: width 0.5s ease;
}

.progress-text {
  font-size: 13px;
  color: var(--text-muted);
}

.week-days {
  display: flex;
  gap: 12px;
}

.day-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  padding: 12px 8px;
  border-radius: 12px;
  background: var(--background-secondary);
  cursor: pointer;
  transition: all 0.2s;
}

.day-item:hover {
  background: var(--background-tertiary);
}

.day-item.checked {
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  color: white;
}

.day-item.today {
  border: 2px solid var(--primary-color);
}

.day-week {
  font-size: 12px;
}

.day-date {
  font-size: 14px;
  font-weight: 600;
}

.day-check {
  font-size: 16px;
  font-weight: bold;
}

.checkin-form {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 32px;
  box-shadow: var(--shadow-light);
  margin-bottom: 24px;
}

.checkin-form .form-title {
  font-size: 18px;
  color: var(--text-primary);
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  display: block;
  font-size: 14px;
  color: var(--text-dark);
  margin-bottom: 8px;
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

.form-actions {
  display: flex;
  gap: 12px;
}

.btn-secondary {
  padding: 12px 24px;
  background: var(--background-secondary);
  border: none;
  border-radius: 8px;
  color: var(--text-dark);
  cursor: pointer;
  font-size: 14px;
  transition: background 0.2s;
}

.btn-secondary:hover {
  background: var(--background-tertiary);
}

.tasks-section {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 32px;
  box-shadow: var(--shadow-light);
}

.section-title {
  font-size: 18px;
  color: var(--text-primary);
  margin-bottom: 20px;
}

.task-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.task-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  background: var(--background-secondary);
  border-radius: 8px;
}

.task-checkbox {
  width: 18px;
  height: 18px;
  cursor: pointer;
}

.task-label {
  font-size: 14px;
  color: var(--text-dark);
  cursor: pointer;
}

.checkin-history {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 32px;
  box-shadow: var(--shadow-light);
}

.history-title {
  font-size: 18px;
  color: var(--text-primary);
  margin-bottom: 20px;
}

.history-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.history-item {
  padding: 16px;
  background: var(--background-secondary);
  border-radius: 12px;
}

.history-date {
  font-size: 12px;
  color: var(--text-muted);
  margin-bottom: 4px;
}

.history-content {
  font-size: 14px;
  color: var(--text-dark);
  line-height: 1.6;
}

.history-empty {
  text-align: center;
  padding: 40px 20px;
  color: var(--text-muted);
}

/* 空状态 */
.empty-state {
  display: flex;
  justify-content: center;
  padding: 60px 20px;
}

.empty-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 48px;
  text-align: center;
  box-shadow: var(--shadow-light);
  max-width: 400px;
}

.empty-icon {
  font-size: 48px;
  margin-bottom: 16px;
  display: block;
}

.empty-title {
  font-size: 20px;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.empty-desc {
  font-size: 14px;
  color: var(--text-muted);
  margin-bottom: 24px;
  line-height: 1.6;
}

@media (max-width: 1024px) {
  .checkin-layout {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .week-days {
    flex-wrap: wrap;
  }

  .day-item {
    min-width: calc(33.33% - 8px);
  }
}
</style>
