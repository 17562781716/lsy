<template>
  <div class="profile-page">
    <div class="profile-layout">
      <div class="profile-sidebar">
        <div class="avatar-section">
          <div class="avatar-wrapper" @click="triggerFileInput">
            <img v-if="avatarUrl" :src="avatarUrl" alt="头像" class="avatar-image">
            <div v-else class="avatar-placeholder">
              <span class="avatar-icon">📷</span>
              <span class="avatar-text">点击上传头像</span>
            </div>
            <div class="avatar-overlay">
              <span>更换头像</span>
            </div>
          </div>
          <input 
            type="file" 
            ref="fileInput" 
            accept="image/*" 
            @change="handleAvatarUpload" 
            style="display: none"
          >
        </div>
        
        <div class="info-section">
          <h1 class="username">{{ user.name }}</h1>
          <p class="user-phone">{{ user.username }}</p>
        </div>
      </div>

      <div class="profile-main">
        <div class="main-content">
          <div class="section-card">
            <div class="section-header">
              <h2>我的主题周</h2>
              <RouterLink to="/my-weeks" class="view-all">查看全部</RouterLink>
            </div>
            <div class="weeks-list">
              <div class="week-item" v-for="week in recentWeeks" :key="week.id">
                <div class="week-icon">{{ week.icon }}</div>
                <div class="week-info">
                  <h3 class="week-name">{{ week.name }}</h3>
                  <p class="week-date">{{ week.date }}</p>
                </div>
                <div class="week-status">
                  <span class="status-badge" :class="week.status">{{ week.statusText }}</span>
                </div>
              </div>
            </div>
          </div>

          <div class="section-card">
            <div class="section-header">
              <h2>打卡记录</h2>
              <RouterLink to="/checkin" class="view-all">去打卡</RouterLink>
            </div>
            <div class="checkin-stats">
              <div class="checkin-stat-item">
                <div class="checkin-stat-value">{{ stats.totalCheckins }}</div>
                <div class="checkin-stat-label">总打卡天数</div>
              </div>
              <div class="checkin-stat-item">
                <div class="checkin-stat-value">{{ stats.currentStreak }}</div>
                <div class="checkin-stat-label">连续打卡</div>
              </div>
              <div class="checkin-stat-item">
                <div class="checkin-stat-value">{{ stats.totalHours }}h</div>
                <div class="checkin-stat-label">累计时长</div>
              </div>
            </div>
            <div class="checkin-calendar">
              <div class="calendar-header">
                <span>最近 7 天</span>
              </div>
              <div class="calendar-days">
                <div 
                  class="calendar-day" 
                  v-for="(day, index) in last7Days" 
                  :key="index"
                  :class="{ checked: day.checked }"
                >
                  <div class="day-label">{{ day.label }}</div>
                  <div class="day-dot"></div>
                </div>
              </div>
            </div>
          </div>

          <div class="section-card">
            <div class="section-header">
              <h2>个人数据</h2>
            </div>
            <div class="data-grid">
              <div class="data-item">
                <div class="data-icon">📅</div>
                <div class="data-content">
                  <div class="data-value">{{ stats.totalWeeks }}</div>
                  <div class="data-label">参与主题周</div>
                </div>
              </div>
              <div class="data-item">
                <div class="data-icon">✅</div>
                <div class="data-content">
                  <div class="data-value">{{ stats.completedWeeks }}</div>
                  <div class="data-label">完成主题周</div>
                </div>
              </div>
              <div class="data-item">
                <div class="data-icon">📝</div>
                <div class="data-content">
                  <div class="data-value">{{ stats.totalNotes }}</div>
                  <div class="data-label">心得体会</div>
                </div>
              </div>
              <div class="data-item">
                <div class="data-icon">⭐</div>
                <div class="data-content">
                  <div class="data-value">{{ stats.favoriteThemes }}</div>
                  <div class="data-label">收藏主题</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="settings-toggle" @click="showSettings = !showSettings">
      <span class="toggle-icon">️</span>
    </div>

    <div class="settings-drawer" :class="{ open: showSettings }">
      <div class="drawer-header">
        <h3>设置</h3>
        <button class="close-drawer" @click="showSettings = false">×</button>
      </div>
      <div class="drawer-body">
        <button class="settings-item" @click="showPasswordModal = true; showSettings = false">
          <span class="settings-icon"></span>
          <span>修改密码</span>
          <span class="settings-arrow">›</span>
        </button>
        <button class="settings-item">
          <span class="settings-icon">🔔</span>
          <span>通知设置</span>
          <span class="settings-arrow">›</span>
        </button>
        <button class="settings-item">
          <span class="settings-icon">🌐</span>
          <span>语言设置</span>
          <span class="settings-arrow">›</span>
        </button>
        <button class="settings-item logout-btn" @click="handleLogout">
          <span class="settings-icon">🚪</span>
          <span>退出登录</span>
        </button>
      </div>
    </div>

    <div class="drawer-overlay" v-if="showSettings" @click="showSettings = false"></div>

    <div class="password-modal" v-if="showPasswordModal" @click="showPasswordModal = false">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>修改密码</h3>
          <button class="close-btn" @click="showPasswordModal = false">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>旧密码</label>
            <input type="password" v-model="passwordForm.oldPassword" placeholder="输入旧密码">
          </div>
          <div class="form-group">
            <label>新密码</label>
            <input type="password" v-model="passwordForm.newPassword" placeholder="输入新密码">
          </div>
          <div class="form-group">
            <label>确认新密码</label>
            <input type="password" v-model="passwordForm.confirmPassword" placeholder="再次输入新密码">
          </div>
          <p v-if="passwordError" class="error-text">{{ passwordError }}</p>
          <p v-if="passwordSuccess" class="success-text">{{ passwordSuccess }}</p>
        </div>
        <div class="modal-footer">
          <button class="btn-secondary" @click="showPasswordModal = false">取消</button>
          <button class="btn-primary" @click="handleChangePassword" :disabled="isChangingPassword">
            {{ isChangingPassword ? '修改中...' : '确认修改' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useUserStore } from '../stores/user';
import { useRouter, RouterLink } from 'vue-router';

const userStore = useUserStore();
const router = useRouter();
const user = computed(() => userStore.user);

const fileInput = ref(null);
const avatarUrl = ref('');

const showPasswordModal = ref(false);
const showSettings = ref(false);
const isChangingPassword = ref(false);
const passwordError = ref('');
const passwordSuccess = ref('');

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
});

const stats = reactive({
  totalCheckins: 23,
  currentStreak: 7,
  totalHours: 12,
  totalWeeks: 5,
  completedWeeks: 2,
  totalNotes: 15,
  favoriteThemes: 8
});

const recentWeeks = [
  { id: 1, icon: '📚', name: '阅读周', date: '2024.03.01 - 2024.03.07', status: 'completed', statusText: '已完成' },
  { id: 2, icon: '🧘', name: '冥想周', date: '2024.03.08 - 2024.03.14', status: 'ongoing', statusText: '进行中' },
  { id: 3, icon: '🎨', name: '绘画周', date: '2024.02.15 - 2024.02.21', status: 'completed', statusText: '已完成' }
];

const last7Days = [
  { label: '一', checked: true },
  { label: '二', checked: true },
  { label: '三', checked: true },
  { label: '四', checked: false },
  { label: '五', checked: true },
  { label: '六', checked: true },
  { label: '日', checked: false }
];

const triggerFileInput = () => {
  fileInput.value.click();
};

const handleAvatarUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    if (file.size > 5 * 1024 * 1024) {
      alert('图片大小不能超过5MB');
      return;
    }
    const reader = new FileReader();
    reader.onload = (e) => {
      avatarUrl.value = e.target.result;
      localStorage.setItem('user_avatar', e.target.result);
    };
    reader.readAsDataURL(file);
  }
};

const loadAvatar = () => {
  const savedAvatar = localStorage.getItem('user_avatar');
  if (savedAvatar) {
    avatarUrl.value = savedAvatar;
  }
};

loadAvatar();

const handleChangePassword = () => {
  passwordError.value = '';
  passwordSuccess.value = '';

  if (!passwordForm.oldPassword || !passwordForm.newPassword || !passwordForm.confirmPassword) {
    passwordError.value = '请填写所有字段';
    return;
  }
  
  if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    passwordError.value = '两次输入的密码不一致';
    return;
  }
  
  isChangingPassword.value = true;
  
  try {
    const success = userStore.changePassword(
      user.value.id, 
      passwordForm.oldPassword, 
      passwordForm.newPassword
    );
    
    if (success) {
      passwordSuccess.value = '密码修改成功';
      passwordForm.oldPassword = '';
      passwordForm.newPassword = '';
      passwordForm.confirmPassword = '';
      setTimeout(() => {
        showPasswordModal.value = false;
      }, 1500);
    } else {
      passwordError.value = '旧密码错误';
    }
  } catch (err) {
    passwordError.value = '密码修改失败，请稍后重试';
  } finally {
    isChangingPassword.value = false;
  }
};

const handleLogout = () => {
  userStore.logout();
  router.push('/');
};
</script>

<style scoped>
.profile-page {
  min-height: 100vh;
  background: #fafbfc;
}

.profile-layout {
  display: flex;
  min-height: calc(100vh - 60px);
  padding: 2rem;
  gap: 2rem;
}

.profile-sidebar {
  width: 30%;
  background: white;
  border-radius: 12px;
  padding: 3rem 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.avatar-section {
  margin-bottom: 1.5rem;
}

.avatar-wrapper {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  position: relative;
  cursor: pointer;
  overflow: hidden;
  border: 3px solid #e8f5e9;
  transition: all 0.3s;
}

.avatar-wrapper:hover {
  border-color: #4CAF50;
  transform: scale(1.05);
}

.avatar-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-placeholder {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #e8f5e9, #c8e6c9);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.avatar-icon {
  font-size: 2rem;
}

.avatar-text {
  font-size: 0.75rem;
  color: #4CAF50;
  font-weight: 500;
}

.avatar-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(76, 175, 80, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s;
  color: white;
  font-weight: 500;
  font-size: 0.85rem;
}

.avatar-wrapper:hover .avatar-overlay {
  opacity: 1;
}

.info-section {
  width: 100%;
}

.username {
  font-size: 1.5rem;
  font-weight: 600;
  color: #333;
  margin: 0 0 0.5rem 0;
}

.user-phone {
  color: #666;
  font-size: 0.9rem;
  margin: 0;
}

.profile-main {
  width: 70%;
  padding: 2rem;
  overflow-y: auto;
}

.main-content {
  max-width: 800px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.section-card {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.25rem;
  padding-bottom: 0.75rem;
  border-bottom: 1px solid #f0f0f0;
}

.section-header h2 {
  font-size: 1.1rem;
  color: #333;
  margin: 0;
  font-weight: 600;
}

.view-all {
  color: #4CAF50;
  text-decoration: none;
  font-size: 0.85rem;
  font-weight: 500;
}

.view-all:hover {
  text-decoration: underline;
}

.weeks-list {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.week-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 0.75rem;
  border-radius: 8px;
  background: #fafbfc;
  transition: all 0.2s;
}

.week-item:hover {
  background: #f5f5f5;
}

.week-icon {
  font-size: 1.5rem;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: white;
  border-radius: 8px;
}

.week-info {
  flex: 1;
}

.week-name {
  font-size: 0.95rem;
  color: #333;
  margin: 0 0 0.25rem 0;
  font-weight: 500;
}

.week-date {
  font-size: 0.8rem;
  color: #999;
  margin: 0;
}

.status-badge {
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.75rem;
  font-weight: 500;
}

.status-badge.completed {
  background: #e8f5e9;
  color: #4CAF50;
}

.status-badge.ongoing {
  background: #fff3e0;
  color: #ff9800;
}

.checkin-stats {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.checkin-stat-item {
  text-align: center;
  padding: 1rem;
  background: #fafbfc;
  border-radius: 8px;
}

.checkin-stat-value {
  font-size: 1.5rem;
  font-weight: 600;
  color: #4CAF50;
  margin-bottom: 0.25rem;
}

.checkin-stat-label {
  font-size: 0.8rem;
  color: #666;
}

.checkin-calendar {
  background: #fafbfc;
  border-radius: 8px;
  padding: 1rem;
}

.calendar-header {
  font-size: 0.85rem;
  color: #666;
  margin-bottom: 0.75rem;
}

.calendar-days {
  display: flex;
  justify-content: space-between;
  gap: 0.5rem;
}

.calendar-day {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.day-label {
  font-size: 0.75rem;
  color: #999;
}

.day-dot {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: #e0e0e0;
  transition: all 0.2s;
}

.calendar-day.checked .day-dot {
  background: #4CAF50;
}

.data-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
}

.data-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  background: #fafbfc;
  border-radius: 8px;
}

.data-icon {
  font-size: 1.5rem;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: white;
  border-radius: 8px;
}

.data-content {
  flex: 1;
}

.data-value {
  font-size: 1.25rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 0.25rem;
}

.data-label {
  font-size: 0.8rem;
  color: #666;
}

.settings-toggle {
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  width: 56px;
  height: 56px;
  background: white;
  border-radius: 50%;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  z-index: 100;
  transition: all 0.3s;
}

.settings-toggle:hover {
  transform: scale(1.1);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.2);
}

.toggle-icon {
  font-size: 1.5rem;
}

.settings-drawer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: white;
  border-radius: 20px 20px 0 0;
  box-shadow: 0 -4px 20px rgba(0, 0, 0, 0.1);
  z-index: 200;
  transform: translateY(100%);
  transition: transform 0.3s ease;
  max-height: 70vh;
  overflow-y: auto;
}

.settings-drawer.open {
  transform: translateY(0);
}

.drawer-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.25rem 1.5rem;
  border-bottom: 1px solid #eee;
}

.drawer-header h3 {
  margin: 0;
  font-size: 1.1rem;
  color: #333;
}

.close-drawer {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #666;
  padding: 0;
  line-height: 1;
}

.drawer-body {
  padding: 1rem 1.5rem;
}

.drawer-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  z-index: 150;
}

.settings-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  width: 100%;
  padding: 0.875rem;
  border: none;
  background: #fafbfc;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 0.95rem;
  color: #333;
  margin-bottom: 0.5rem;
}

.settings-item:hover {
  background: #f5f5f5;
}

.settings-icon {
  font-size: 1.25rem;
}

.settings-arrow {
  margin-left: auto;
  color: #999;
  font-size: 1.25rem;
}

.logout-btn {
  color: #f44336;
}

.logout-btn:hover {
  background: #ffebee;
}

.password-modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  border-radius: 12px;
  padding: 1.5rem;
  width: 90%;
  max-width: 400px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.modal-header h3 {
  margin: 0;
  color: #333;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #666;
}

.modal-body {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-size: 0.9rem;
  font-weight: 500;
  color: #333;
}

.form-group input {
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.2s;
}

.form-group input:focus {
  outline: none;
  border-color: #4CAF50;
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.1);
}

.error-text {
  color: #f44336;
  font-size: 0.85rem;
  margin: 0;
}

.success-text {
  color: #4CAF50;
  font-size: 0.85rem;
  margin: 0;
}

.modal-footer {
  display: flex;
  gap: 1rem;
  margin-top: 1.5rem;
}

.btn-primary,
.btn-secondary {
  flex: 1;
  padding: 0.75rem;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-primary {
  background: #4CAF50;
  color: white;
}

.btn-primary:hover:not(:disabled) {
  background: #45a049;
}

.btn-primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn-secondary {
  background: #f5f5f5;
  color: #333;
}

.btn-secondary:hover {
  background: #e0e0e0;
}

@media (max-width: 1024px) {
  .profile-layout {
    flex-direction: column;
  }
  
  .profile-sidebar {
    width: 100%;
    border-right: none;
    border-bottom: 1px solid #e8e8e8;
    padding: 2rem;
  }
  
  .profile-main {
    width: 100%;
    padding: 1.5rem;
  }
}

@media (max-width: 768px) {
  .avatar-wrapper {
    width: 100px;
    height: 100px;
  }
  
  .username {
    font-size: 1.25rem;
  }
  
  .checkin-stats {
    grid-template-columns: 1fr;
  }
  
  .data-grid {
    grid-template-columns: 1fr;
  }
}
</style>
