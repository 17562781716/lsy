<template>
  <div class="checkin-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">打卡中心</h1>
        <p class="page-subtitle">记录每一天的坚持，见证自己的成长</p>
      </div>
      
      <div class="checkin-layout">
        <!-- 左侧：当前主题周 -->
        <div class="current-week">
          <div class="week-card">
            <div class="week-header">
              <span class="week-icon">🎭</span>
              <div class="week-info">
                <h2 class="week-name">音乐剧周</h2>
                <p class="week-date">2026.04.20 - 2026.04.26</p>
              </div>
              <span class="week-status ongoing">进行中</span>
            </div>
            
            <div class="week-progress">
              <div class="progress-bar">
                <div class="progress-fill" :style="{ width: (checkedDays.length / 7 * 100) + '%' }"></div>
              </div>
              <p class="progress-text">已完成 {{ checkedDays.length }}/7 天</p>
            </div>
            
            <div class="week-days">
              <div 
                v-for="day in weekDays" 
                :key="day.date"
                class="day-item"
                :class="{ checked: day.checked, today: day.isToday }"
                @click="toggleDay(day)"
              >
                <span class="day-week">{{ day.weekDay }}</span>
                <span class="day-date">{{ day.date }}</span>
                <span class="day-check">{{ day.checked ? '✓' : '+' }}</span>
              </div>
            </div>
          </div>
          
          <!-- 今日打卡内容 -->
          <div class="checkin-form" v-if="todayCheckin">
            <h3 class="form-title">今日打卡</h3>
            <div class="form-group">
              <label class="form-label">今天做了什么？</label>
              <textarea 
                v-model="todayCheckin.content" 
                class="form-textarea"
                placeholder="记录今天的主题周活动..."
                rows="4"
              ></textarea>
            </div>
            <button class="btn-primary" @click="submitCheckin">提交打卡</button>
          </div>
        </div>
        
        <!-- 右侧：打卡记录 -->
        <div class="checkin-history">
          <h3 class="history-title">打卡记录</h3>
          <div class="history-list">
            <div v-for="record in checkinRecords" :key="record.id" class="history-item">
              <div class="history-date">{{ record.date }}</div>
              <div class="history-content">{{ record.content }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const weekDays = ref([
  { weekDay: '周一', date: '04.20', checked: true, isToday: false },
  { weekDay: '周二', date: '04.21', checked: true, isToday: false },
  { weekDay: '周三', date: '04.22', checked: true, isToday: false },
  { weekDay: '周四', date: '04.23', checked: true, isToday: false },
  { weekDay: '周五', date: '04.24', checked: false, isToday: true },
  { weekDay: '周六', date: '04.25', checked: false, isToday: false },
  { weekDay: '周日', date: '04.26', checked: false, isToday: false },
]);

const checkedDays = computed(() => weekDays.value.filter(d => d.checked));

const toggleDay = (day) => {
  day.checked = !day.checked;
};

const todayCheckin = ref({
  content: '',
});

const checkinRecords = ref([
  { id: 1, date: '04.24', content: '今天去看了《歌剧魅影》，太震撼了！' },
  { id: 2, date: '04.23', content: '听了《悲惨世界》的原声带，学会了《Do You Hear the People Sing》' },
  { id: 3, date: '04.22', content: '了解了音乐剧的历史，从百老汇到西区' },
  { id: 4, date: '04.21', content: '第一次走进剧场，看了《猫》' },
]);

const submitCheckin = () => {
  const today = weekDays.value.find(d => d.isToday);
  if (today) {
    today.checked = true;
  }
  checkinRecords.value.unshift({
    id: Date.now(),
    date: '04.24',
    content: todayCheckin.value.content,
  });
  todayCheckin.value.content = '';
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
}

.checkin-form .btn-primary {
  padding: 12px 32px;
  font-size: 16px;
}

.form-title {
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
