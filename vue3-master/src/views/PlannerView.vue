<template>
  <div class="planner-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">规划工具</h1>
        <p class="page-subtitle">规划你的主题周，让生活更有仪式感</p>
      </div>
      
      <div class="planner-layout">
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
              <input 
                v-model="newWeek.startDate" 
                type="date" 
                class="form-input"
              />
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
            <button class="btn-primary btn-block" @click="createWeek">创建主题周</button>
          </div>
        </div>
        
        <!-- 右侧：我的计划 -->
        <div class="planner-list">
          <h2 class="list-title">我的主题周计划</h2>
          <div class="weeks-list">
            <div v-for="week in myWeeks" :key="week.id" class="week-plan-card">
              <div class="plan-header">
                <span class="plan-icon">{{ week.icon }}</span>
                <div class="plan-info">
                  <h3 class="plan-name">{{ week.name }}</h3>
                  <p class="plan-date">{{ week.startDate }} 开始</p>
                </div>
                <span class="plan-status" :class="week.status">{{ week.statusText }}</span>
              </div>
              <p class="plan-desc">{{ week.description }}</p>
              <div class="plan-tasks" v-if="week.tasks.length">
                <div v-for="(task, idx) in week.tasks" :key="idx" class="plan-task">
                  <span class="task-check">☐</span>
                  <span class="task-text">{{ task }}</span>
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

const icons = ref(['🎭', '📖', '🧶', '🏃', '🎬', '🎨', '🗣️', '🧘', '🍳', '📸', '🎸', '🌱', '✨', '📚', '🎵']);

const newWeek = ref({
  name: '',
  icon: '🎭',
  startDate: new Date().toISOString().split('T')[0],
  description: '',
  tasks: ['', '', ''],
});

const addTask = () => {
  newWeek.value.tasks.push('');
};

const removeTask = (index) => {
  newWeek.value.tasks.splice(index, 1);
};

const myWeeks = ref([
  {
    id: 1,
    icon: '🎭',
    name: '音乐剧周',
    startDate: '2026-04-20',
    description: '走进剧场，感受音乐与戏剧的碰撞',
    status: 'ongoing',
    statusText: '进行中',
    tasks: ['看一场音乐剧', '学一首音乐剧歌曲', '了解音乐剧历史'],
  },
  {
    id: 2,
    icon: '📖',
    name: '阅读周',
    startDate: '2026-04-13',
    description: '放下手机，沉浸在文字的世界里',
    status: 'completed',
    statusText: '已完成',
    tasks: ['读完一本书', '写读书笔记', '分享读书心得'],
  },
  {
    id: 3,
    icon: '🧶',
    name: '钩织周',
    startDate: '2026-04-06',
    description: '用双手创造温暖，编织生活的美好',
    status: 'completed',
    statusText: '已完成',
    tasks: ['学会基础针法', '完成一个小作品', '尝试新花样'],
  },
]);

const createWeek = () => {
  if (!newWeek.value.name) return;
  
  myWeeks.value.unshift({
    id: Date.now(),
    ...newWeek.value,
    status: 'upcoming',
    statusText: '即将开始',
    tasks: newWeek.value.tasks.filter(t => t),
  });
  
  newWeek.value = {
    name: '',
    icon: '🎭',
    startDate: new Date().toISOString().split('T')[0],
    description: '',
    tasks: ['', '', ''],
  };
};
</script>

<style scoped>
.planner-page {
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

@media (max-width: 1024px) {
  .planner-layout {
    grid-template-columns: 1fr;
  }
  
  .form-card {
    position: static;
  }
}
</style>
