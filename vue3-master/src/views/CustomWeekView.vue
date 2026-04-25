<template>
  <div class="custom-week-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">✨ 自定义主题周</h1>
        <p class="page-subtitle">创造属于你的专属主题，开启不一样的七天</p>
      </div>

      <div class="custom-week-content">
        <div class="left-panel">
          <div class="create-card">
            <h2 class="card-title">创建你的主题周</h2>
            
            <div class="form-group">
              <label>主题名称</label>
              <input 
                v-model="formData.name" 
                type="text" 
                placeholder="例如：咖啡品鉴周、插花周..."
                class="form-input"
              >
            </div>

            <div class="form-group">
              <label>选择图标</label>
              <div class="icon-selector">
                <div 
                  v-for="icon in iconOptions" 
                  :key="icon"
                  class="icon-option"
                  :class="{ active: formData.icon === icon }"
                  @click="formData.icon = icon"
                >
                  {{ icon }}
                </div>
              </div>
            </div>

            <div class="form-group">
              <label>主题描述</label>
              <textarea 
                v-model="formData.description" 
                placeholder="描述一下你的主题周想做什么..."
                class="form-textarea"
                rows="3"
              ></textarea>
            </div>

            <div class="form-group">
              <label>每日任务（最多7个）</label>
              <div class="tasks-list">
                <div v-for="(task, index) in formData.tasks" :key="index" class="task-item">
                  <span class="task-number">Day {{ index + 1 }}</span>
                  <input 
                    v-model="formData.tasks[index]" 
                    type="text" 
                    :placeholder="'输入第' + (index + 1) + '天的任务'"
                    class="task-input"
                  >
                  <button 
                    v-if="formData.tasks.length > 1" 
                    class="task-remove" 
                    @click="removeTask(index)"
                  >
                    ×
                  </button>
                </div>
              </div>
              <button 
                v-if="formData.tasks.length < 7" 
                class="add-task-btn" 
                @click="addTask"
              >
                + 添加任务
              </button>
            </div>

            <div class="form-group">
              <label>开始日期</label>
              <input 
                v-model="formData.startDate" 
                type="date" 
                class="form-input"
              >
            </div>

            <button class="submit-btn" @click="createWeek">
              开始我的主题周
            </button>
          </div>
        </div>

        <div class="right-panel">
          <div class="inspiration-card">
            <h3 class="inspiration-title">💡 灵感推荐</h3>
            <div class="inspiration-list">
              <div 
                v-for="idea in inspirationIdeas" 
                :key="idea.name"
                class="inspiration-item"
                @click="useInspiration(idea)"
              >
                <span class="inspiration-icon">{{ idea.icon }}</span>
                <div class="inspiration-info">
                  <span class="inspiration-name">{{ idea.name }}</span>
                  <span class="inspiration-desc">{{ idea.desc }}</span>
                </div>
              </div>
            </div>
          </div>

          <div class="tips-card">
            <h3 class="tips-title">📝 小贴士</h3>
            <ul class="tips-list">
              <li>主题周不需要完美，重要的是体验</li>
              <li>可以从简单的主题开始，慢慢尝试更有趣的</li>
              <li>每天花一点时间专注主题，不用太多压力</li>
              <li>完成后可以在感悟墙分享你的收获</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useThemesStore } from '../stores/themes';

const router = useRouter();
const themesStore = useThemesStore();

const formData = ref({
  name: '',
  icon: '✨',
  description: '',
  tasks: [''],
  startDate: new Date().toISOString().split('T')[0],
});

const iconOptions = ['✨', '🎵', '🧶', '🧘', '🍳', '🌱', '🌸', '🎪', '💃', '🏺', '🎬', '📖'];

const inspirationIdeas = [
  { icon: '☕', name: '咖啡品鉴周', desc: '每天尝试一种不同的咖啡' },
  { icon: '🌸', name: '插花周', desc: '学习基础插花技巧' },
  { icon: '🎵', name: '古典音乐周', desc: '每天听一首古典名曲' },
  { icon: '🎮', name: '独立游戏周', desc: '探索有趣的独立游戏' },
  { icon: '📚', name: '诗歌周', desc: '每天读一首诗' },
  { icon: '🧘', name: '瑜伽周', desc: '每天练习15分钟瑜伽' },
  { icon: '🎨', name: '水彩画周', desc: '尝试用水彩记录生活' },
  { icon: '🍳', name: '烘焙周', desc: '学习制作甜点' },
];

const addTask = () => {
  if (formData.value.tasks.length < 7) {
    formData.value.tasks.push('');
  }
};

const removeTask = (index) => {
  formData.value.tasks.splice(index, 1);
};

const useInspiration = (idea) => {
  formData.value.name = idea.name;
  formData.value.icon = idea.icon;
  formData.value.description = idea.desc;
};

const createWeek = () => {
  if (!formData.value.name) {
    alert('请输入主题名称');
    return;
  }

  const newWeek = {
    icon: formData.value.icon,
    name: formData.value.name,
    description: formData.value.description,
    startDate: formData.value.startDate,
    tasks: formData.value.tasks.filter(t => t.trim()),
  };

  themesStore.addMyWeek(newWeek);
  router.push('/my-weeks');
};
</script>

<style scoped>
.custom-week-page {
  min-height: 100vh;
  padding: 40px 0 80px;
  background: linear-gradient(135deg, #ffffff 0%, #e8f5e9 50%, #c8e6c9 100%);
}

.page-header {
  text-align: center;
  margin-bottom: 48px;
}

.page-title {
  font-size: 36px;
  font-weight: 700;
  color: #000000;
  margin-bottom: 12px;
  font-family: 'Songti SC', 'SimSun', 'STSong', 'FangSong', serif;
}

.page-subtitle {
  font-size: 16px;
  color: var(--text-muted);
}

.custom-week-content {
  display: grid;
  grid-template-columns: 1.2fr 1fr;
  gap: 32px;
  max-width: 1000px;
  margin: 0 auto;
}

.left-panel {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.create-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 32px;
  box-shadow: var(--shadow-medium);
}

.card-title {
  font-size: 22px;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.form-input {
  width: 100%;
  padding: 10px 14px;
  border: 1px solid var(--border-color);
  border-radius: 10px;
  font-size: 14px;
  background: var(--input-background);
  color: var(--text-primary);
  transition: border-color 0.2s;
}

.form-input:focus {
  outline: none;
  border-color: var(--primary-color);
}

.form-textarea {
  width: 100%;
  padding: 10px 14px;
  border: 1px solid var(--border-color);
  border-radius: 10px;
  font-size: 14px;
  background: var(--input-background);
  color: var(--text-primary);
  resize: vertical;
  font-family: inherit;
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

.icon-option {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  background: var(--background-secondary);
  cursor: pointer;
  font-size: 20px;
  transition: all 0.2s;
}

.icon-option:hover {
  transform: scale(1.1);
}

.icon-option.active {
  background: var(--primary-color);
  box-shadow: 0 2px 8px rgba(46, 125, 50, 0.3);
}

.tasks-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.task-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.task-number {
  font-size: 12px;
  color: var(--text-muted);
  white-space: nowrap;
  min-width: 50px;
}

.task-input {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid var(--border-color);
  border-radius: 8px;
  font-size: 14px;
  background: var(--input-background);
  color: var(--text-primary);
}

.task-input:focus {
  outline: none;
  border-color: var(--primary-color);
}

.task-remove {
  width: 24px;
  height: 24px;
  border: none;
  background: #ffebee;
  color: #f44336;
  border-radius: 50%;
  cursor: pointer;
  font-size: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.add-task-btn {
  padding: 8px 16px;
  border: 1px dashed var(--primary-color);
  background: transparent;
  color: var(--primary-color);
  border-radius: 8px;
  cursor: pointer;
  font-size: 13px;
  margin-top: 8px;
  transition: all 0.2s;
}

.add-task-btn:hover {
  background: var(--background-secondary);
}

.submit-btn {
  width: 100%;
  padding: 14px;
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
  box-shadow: 0 2px 8px rgba(46, 125, 50, 0.3);
  margin-top: 8px;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(46, 125, 50, 0.4);
}

.right-panel {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.inspiration-card,
.tips-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 24px;
  box-shadow: var(--shadow-light);
}

.inspiration-title,
.tips-title {
  font-size: 18px;
  color: var(--text-primary);
  margin-bottom: 16px;
}

.inspiration-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.inspiration-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 14px;
  border-radius: 12px;
  background: var(--background-secondary);
  cursor: pointer;
  transition: all 0.2s;
}

.inspiration-item:hover {
  background: #e8f5e9;
  transform: translateX(4px);
}

.inspiration-icon {
  font-size: 24px;
}

.inspiration-info {
  display: flex;
  flex-direction: column;
}

.inspiration-name {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
}

.inspiration-desc {
  font-size: 12px;
  color: var(--text-muted);
}

.tips-list {
  list-style: none;
  padding: 0;
}

.tips-list li {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.8;
  padding-left: 16px;
  position: relative;
}

.tips-list li::before {
  content: '•';
  position: absolute;
  left: 0;
  color: var(--primary-color);
}

@media (max-width: 768px) {
  .custom-week-content {
    grid-template-columns: 1fr;
  }
}
</style>
