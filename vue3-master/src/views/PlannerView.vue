<template>
  <div class="planner-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">规划工具</h1>
        <p class="page-subtitle">规划你的主题周，让生活更有仪式感</p>
      </div>

      <!-- 创建模式 -->
      <div v-if="!activeWeek" class="planner-layout">
        <!-- 左侧：创建主题周 -->
        <div class="planner-form">
          <div class="form-card">
            <h2 class="form-title">创建新主题周</h2>
            <div class="form-group">
              <label class="form-label">主题名称</label>
              <input
                v-model="newWeek.name"
                type="text"
                class="form-input"
                placeholder="例如：音乐剧周、钩织周..."
              />
            </div>
            <div class="form-group">
              <label class="form-label">主题图标</label>
              <div class="icon-selector">
                <button
                  v-for="icon in icons"
                  :key="icon"
                  class="icon-btn"
                  :class="{ active: newWeek.icon === icon }"
                  @click="newWeek.icon = icon"
                >
                  {{ icon }}
                </button>
              </div>
            </div>
            <div class="form-group">
              <label class="form-label">开始日期</label>
              <CustomDatePicker v-model="newWeek.startDate" />
            </div>
            <div class="form-group">
              <label class="form-label">主题描述</label>
              <textarea
                v-model="newWeek.description"
                class="form-textarea"
                placeholder="描述一下这个主题周的目标和计划..."
                rows="3"
              ></textarea>
            </div>
            <div class="form-group">
              <label class="form-label">每日任务（选填）</label>
              <div class="tasks-list">
                <div v-for="(task, index) in newWeek.tasks" :key="index" class="task-input-row">
                  <input
                    v-model="newWeek.tasks[index]"
                    type="text"
                    class="form-input"
                    :placeholder="`任务 ${index + 1}`"
                  />
                  <button class="btn-remove" @click="removeTask(index)">✕</button>
                </div>
                <button class="btn-add-task" @click="addTask">
                  <span>+</span> 添加任务
                </button>
              </div>
            </div>
            <button class="btn-primary btn-block" @click="createWeek">开始我的主题周</button>
          </div>
        </div>

        <!-- 右侧：我的计划 -->
        <div class="planner-list">
          <h2 class="list-title">我的主题周计划</h2>
          <div class="weeks-list">
            <div v-for="week in themesStore.myWeeks" :key="week.id" class="week-plan-card" @click="enterWeek(week.id)">
              <div class="plan-header">
                <span class="plan-icon">{{ week.icon }}</span>
                <div class="plan-info">
                  <h3 class="plan-name">{{ week.name }}</h3>
                  <p class="plan-date">{{ week.startDate }} 开始</p>
                </div>
                <span class="plan-status" :class="week.status">{{ week.statusText }}</span>
              </div>
              <p class="plan-desc">{{ week.description }}</p>
              <div class="plan-tasks" v-if="week.tasks && week.tasks.length">
                <div v-for="(task, idx) in week.tasks" :key="idx" class="plan-task">
                  <span class="task-check">☐</span>
                  <span class="task-text">{{ task }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 主题周详情/管理模式 -->
      <div v-else class="week-detail">
        <div class="detail-header">
          <button class="btn-back" @click="activeWeek = null">← 返回创建页</button>
          <div class="week-title-bar">
            <span class="detail-icon">{{ activeWeek.icon }}</span>
            <div class="detail-info">
              <h2 class="detail-name">{{ activeWeek.name }}</h2>
              <p class="detail-date">{{ activeWeek.startDate }} - {{ activeWeek.endDate }}</p>
            </div>
            <span class="detail-status" :class="activeWeek.status">{{ activeWeek.statusText }}</span>
          </div>
        </div>

        <div class="detail-layout">
          <!-- 左侧：打卡区域 -->
          <div class="checkin-section">
            <div class="week-card">
              <div class="week-progress">
                <div class="progress-bar">
                  <div class="progress-fill" :style="{ width: activeWeek.progress + '%' }"></div>
                </div>
                <p class="progress-text">已完成 {{ checkedDaysCount }}/7 天</p>
              </div>

              <div class="week-days">
                <div
                  v-for="(day, index) in weekDays"
                  :key="index"
                  class="day-item"
                  :class="{ checked: activeWeek.dayCheckins && activeWeek.dayCheckins[index], today: day.isToday }"
                  @click="handleDayClick(index)"
                >
                  <span class="day-week">{{ day.weekDay }}</span>
                  <span class="day-date">{{ day.date }}</span>
                  <span class="day-check">{{ activeWeek.dayCheckins && activeWeek.dayCheckins[index] ? '✓' : '+' }}</span>
                </div>
              </div>
            </div>

            <!-- 今日打卡内容 -->
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
                  v-if="activeWeek.dayCheckins && activeWeek.dayCheckins[selectedDayIndex]"
                  class="btn-secondary"
                  @click="cancelCheckin"
                >
                  取消打卡
                </button>
              </div>
            </div>

            <!-- 任务列表 -->
            <div class="tasks-section" v-if="activeWeek.tasks && activeWeek.tasks.length">
              <h3 class="section-title">本周任务</h3>
              <div class="task-list">
                <div v-for="(task, idx) in activeWeek.tasks" :key="idx" class="task-item">
                  <input
                    type="checkbox"
                    :id="'task-' + idx"
                    :checked="activeWeek.taskCompleted && activeWeek.taskCompleted[idx]"
                    @change="themesStore.toggleTask(activeWeek.id, idx)"
                    class="task-checkbox"
                  />
                  <label :for="'task-' + idx" class="task-label">{{ task }}</label>
                </div>
              </div>
            </div>
          </div>

          <!-- 右侧：打卡记录 -->
          <div class="checkin-history">
            <h3 class="history-title">打卡记录</h3>
            <div class="history-list" v-if="activeWeek.checkins && activeWeek.checkins.length">
              <div v-for="record in activeWeek.checkins" :key="record.date" class="history-item">
                <div class="history-date">{{ formatDate(record.date) }}</div>
                <div class="history-content">{{ record.content }}</div>
              </div>
            </div>
            <div v-else class="history-empty">
              <p>还没有打卡记录，快去开始第一天吧！</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import CustomDatePicker from '../components/CustomDatePicker.vue';
import { useThemesStore } from '../stores/themes.js';

const themesStore = useThemesStore();
const router = useRouter();

const icons = ref(['🎭', '📖', '🧶', '🏃', '🎬', '🎨', '🗣️', '🧘', '🍳', '📸', '🎸', '🌱', '✨', '📚', '🎵']);

const newWeek = ref({
  name: '',
  icon: '🎭',
  startDate: new Date().toISOString().split('T')[0],
  description: '',
  tasks: ['', '', ''],
});

const activeWeek = ref(null);
const selectedDayIndex = ref(null);
const checkinContent = ref('');

const addTask = () => {
  newWeek.value.tasks.push('');
};

const removeTask = (index) => {
  newWeek.value.tasks.splice(index, 1);
};

const createWeek = () => {
  if (!newWeek.value.name) return;

  const weekData = {
    ...newWeek.value,
    tasks: newWeek.value.tasks.filter(t => t),
  };

  themesStore.addMyWeek(weekData);

  // 重置表单
  newWeek.value = {
    name: '',
    icon: '🎭',
    startDate: new Date().toISOString().split('T')[0],
    description: '',
    tasks: ['', '', ''],
  };

  // 跳转到打卡中心
  router.push('/checkin');
};

const enterWeek = (weekId) => {
  const week = themesStore.getWeekById(weekId);
  if (week) {
    activeWeek.value = week;
    selectedDayIndex.value = null;
    checkinContent.value = '';
    taskCompleted.value = Array(week.tasks ? week.tasks.length : 0).fill(false);
  }
};

// 计算7天的日期
const weekDays = computed(() => {
  if (!activeWeek.value) return [];

  const weekDaysNames = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
  const start = new Date(activeWeek.value.startDate);
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
  if (!activeWeek.value || !activeWeek.value.dayCheckins) return 0;
  return activeWeek.value.dayCheckins.filter(Boolean).length;
});

const handleDayClick = (index) => {
  selectedDayIndex.value = index;

  // 预填充已有打卡内容
  if (activeWeek.value && activeWeek.value.dayCheckins && activeWeek.value.dayCheckins[index]) {
    const start = new Date(activeWeek.value.startDate);
    const checkDate = new Date(start);
    checkDate.setDate(checkDate.getDate() + index);
    const dateStr = checkDate.toISOString().split('T')[0];
    const record = activeWeek.value.checkins.find(c => c.date === dateStr);
    checkinContent.value = record ? record.content : '';
  } else {
    checkinContent.value = '';
  }
};

const submitCheckin = () => {
  if (selectedDayIndex.value === null || !activeWeek.value) return;

  themesStore.submitCheckinContent(
    activeWeek.value.id,
    selectedDayIndex.value,
    checkinContent.value
  );

  // 保持选中状态，清空内容
  checkinContent.value = '';
};

const cancelCheckin = () => {
  if (selectedDayIndex.value === null || !activeWeek.value) return;

  themesStore.toggleDayCheckin(
    activeWeek.value.id,
    selectedDayIndex.value
  );
  checkinContent.value = '';
};

const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  return `${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`;
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

/* 创建模式布局 */
.planner-layout {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
}

.form-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 32px;
  box-shadow: var(--shadow-light);
  position: sticky;
  top: 100px;
}

.form-title {
  font-size: 20px;
  color: var(--text-primary);
  margin-bottom: 24px;
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

.icon-selector {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.icon-btn {
  font-size: 24px;
  padding: 8px;
  background: var(--background-secondary);
  border: 2px solid transparent;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
}

.icon-btn:hover {
  background: var(--background-tertiary);
}

.icon-btn.active {
  border-color: var(--primary-color);
  background: var(--background-tertiary);
}

.tasks-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.task-input-row {
  display: flex;
  gap: 8px;
  align-items: center;
}

.btn-remove {
  background: none;
  border: none;
  color: var(--red-color);
  cursor: pointer;
  font-size: 16px;
  padding: 4px;
}

.btn-add-task {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px;
  background: none;
  border: 1px dashed var(--border-color);
  border-radius: 8px;
  color: var(--primary-color);
  cursor: pointer;
  font-size: 14px;
  transition: all 0.2s;
}

.btn-add-task:hover {
  border-color: var(--primary-color);
  background: var(--background-secondary);
}

.btn-block {
  width: 100%;
  padding: 12px;
  font-size: 16px;
}

.planner-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.list-title {
  font-size: 20px;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.week-plan-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 24px;
  box-shadow: var(--shadow-light);
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.week-plan-card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-medium);
}

.plan-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.plan-icon {
  font-size: 32px;
}

.plan-info {
  flex: 1;
}

.plan-name {
  font-size: 16px;
  color: var(--text-primary);
}

.plan-date {
  font-size: 12px;
  color: var(--text-muted);
}

.plan-status {
  font-size: 12px;
  padding: 4px 12px;
  border-radius: 12px;
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
  color: var(--text-muted);
  margin-bottom: 12px;
  line-height: 1.6;
}

.plan-tasks {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.plan-task {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  color: var(--text-dark);
}

.task-check {
  color: var(--primary-color);
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
  margin-bottom: 24px;
}

.btn-back {
  background: none;
  border: none;
  color: var(--primary-color);
  font-size: 14px;
  cursor: pointer;
  margin-bottom: 16px;
  padding: 0;
}

.btn-back:hover {
  text-decoration: underline;
}

.week-title-bar {
  display: flex;
  align-items: center;
  gap: 16px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 24px 32px;
  box-shadow: var(--shadow-light);
}

.detail-icon {
  font-size: 40px;
}

.detail-info {
  flex: 1;
}

.detail-name {
  font-size: 20px;
  color: var(--text-primary);
}

.detail-date {
  font-size: 13px;
  color: var(--text-muted);
}

.detail-status {
  font-size: 12px;
  padding: 6px 16px;
  border-radius: 20px;
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
  gap: 24px;
}

.week-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 32px;
  box-shadow: var(--shadow-light);
  margin-bottom: 24px;
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

@media (max-width: 1024px) {
  .planner-layout,
  .detail-layout {
    grid-template-columns: 1fr;
  }

  .form-card {
    position: static;
  }

  .week-days {
    flex-wrap: wrap;
  }

  .day-item {
    min-width: calc(33.33% - 8px);
  }
}
</style>
