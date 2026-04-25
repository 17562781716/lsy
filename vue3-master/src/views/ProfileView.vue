<template>
  <div class="profile-container">
    <div class="profile-sidebar">
      <div class="user-avatar-container">
        <img 
          v-if="user.avatar" 
          :src="user.avatar" 
          alt="用户头像" 
          class="user-avatar"
        >
        <div v-else class="user-avatar-placeholder">
          {{ user.name.charAt(0) }}
        </div>
        <h2 class="user-name">{{ user.name }}</h2>
        <p class="user-member-level">{{ user.memberLevel }}</p>
      </div>
      
      <nav class="profile-nav">
        <ul>
          <li 
            :class="{ active: activeTab === 'profile' }"
            @click="activeTab = 'profile'"
          >
            <span class="nav-icon">👤</span>
            个人信息
          </li>
          <li 
            :class="{ active: activeTab === 'borrow' }"
            @click="activeTab = 'borrow'"
          >
            <span class="nav-icon">📚</span>
            借阅记录
          </li>
          <li 
            :class="{ active: activeTab === 'purchase' }"
            @click="activeTab = 'purchase'"
          >
            <span class="nav-icon">🛒</span>
            购书记录
          </li>
          <li 
            :class="{ active: activeTab === 'consume' }"
            @click="activeTab = 'consume'"
          >
            <span class="nav-icon">☕</span>
            消费记录
          </li>
          <li 
            :class="{ active: activeTab === 'recharge' }"
            @click="activeTab = 'recharge'"
          >
            <span class="nav-icon">💳</span>
            充值记录
          </li>
          <li 
            :class="{ active: activeTab === 'settings' }"
            @click="activeTab = 'settings'"
          >
            <span class="nav-icon">⚙️</span>
            账户设置
          </li>
        </ul>
      </nav>
    </div>
    
    <div class="profile-content">
      <div v-if="activeTab === 'profile'" class="tab-content">
        <h3 class="tab-title">个人信息</h3>
        <UserProfile v-if="!isEditing" @edit="isEditing = true" />
        <ProfileEditor 
          v-else 
          @save="handleSaveProfile" 
          @cancel="isEditing = false"
        />
      </div>
      
      <div v-else-if="activeTab === 'borrow'" class="tab-content">
        <h3 class="tab-title">借阅记录</h3>
        <OrderList recordType="borrow" />
      </div>
      
      <div v-else-if="activeTab === 'purchase'" class="tab-content">
        <h3 class="tab-title">购书记录</h3>
        <OrderList recordType="purchase" />
      </div>
      
      <div v-else-if="activeTab === 'consume'" class="tab-content">
        <h3 class="tab-title">消费记录</h3>
        <OrderList recordType="consume" />
      </div>
      
      <div v-else-if="activeTab === 'recharge'" class="tab-content">
        <h3 class="tab-title">充值记录</h3>
        <OrderList recordType="recharge" />
      </div>
      
      <div v-else-if="activeTab === 'settings'" class="tab-content">
        <h3 class="tab-title">账户设置</h3>
        <div class="settings-section">
          <h4>密码设置</h4>
          <div class="password-form">
            <div class="form-group">
              <label for="oldPassword">旧密码</label>
              <input 
                type="password" 
                id="oldPassword" 
                v-model="passwordForm.oldPassword"
                class="form-input"
              >
            </div>
            <div class="form-group">
              <label for="newPassword">新密码</label>
              <input 
                type="password" 
                id="newPassword" 
                v-model="passwordForm.newPassword"
                class="form-input"
              >
            </div>
            <div class="form-group">
              <label for="confirmPassword">确认新密码</label>
              <input 
                type="password" 
                id="confirmPassword" 
                v-model="passwordForm.confirmPassword"
                class="form-input"
              >
            </div>
            <button 
              class="btn btn-primary"
              @click="handleChangePassword"
              :disabled="isChangingPassword"
            >
              {{ isChangingPassword ? '修改中...' : '修改密码' }}
            </button>
            <p v-if="passwordError" class="error-message">{{ passwordError }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useUserStore } from '../stores/user';
import UserProfile from '../components/UserProfile.vue';
import ProfileEditor from '../components/ProfileEditor.vue';
import OrderList from '../components/OrderList.vue';

const userStore = useUserStore();
const user = computed(() => userStore.user);

// 标签页切换
const activeTab = ref('profile');
const isEditing = ref(false);

// 密码修改
const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
});
const isChangingPassword = ref(false);
const passwordError = ref('');

// 保存个人信息
const handleSaveProfile = (updates) => {
  userStore.updateUser(user.value.id, updates);
  isEditing.value = false;
};

// 修改密码
const handleChangePassword = async () => {
  // 表单验证
  if (!passwordForm.oldPassword || !passwordForm.newPassword || !passwordForm.confirmPassword) {
    passwordError.value = '请填写所有字段';
    return;
  }
  
  if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    passwordError.value = '两次输入的密码不一致';
    return;
  }
  
  isChangingPassword.value = true;
  passwordError.value = '';
  
  try {
    const success = userStore.changePassword(
      user.value.id, 
      passwordForm.oldPassword, 
      passwordForm.newPassword
    );
    
    if (success) {
      // 密码修改成功
      passwordForm.oldPassword = '';
      passwordForm.newPassword = '';
      passwordForm.confirmPassword = '';
      alert('密码修改成功');
    } else {
      passwordError.value = '旧密码错误';
    }
  } catch (err) {
    passwordError.value = '密码修改失败，请稍后重试';
  } finally {
    isChangingPassword.value = false;
  }
};
</script>

<style scoped>
.profile-container {
  display: flex;
  width: 1200px;
  margin: 2rem auto;
  padding: 0 1rem;
}

.profile-sidebar {
  width: 280px;
  background-color: var(--card-background);
  border-radius: var(--border-radius);
  box-shadow: var(--box-shadow);
  padding: 2rem;
  margin-right: 2rem;
  flex-shrink: 0;
}

.user-avatar-container {
  text-align: center;
  margin-bottom: 2rem;
}

.user-avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 1rem;
}

.user-avatar-placeholder {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background-color: #e0e0e0;
  color: #666666;
  font-size: 3rem;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 1rem;
}

.user-name {
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.user-member-level {
  color: var(--text-secondary);
  font-size: 0.9rem;
}

.profile-nav ul {
  list-style: none;
  padding: 0;
}

.profile-nav li {
  display: flex;
  align-items: center;
  padding: 1rem;
  margin-bottom: 0.5rem;
  border-radius: var(--border-radius);
  cursor: pointer;
  transition: all var(--transition-fast);
  color: var(--text-secondary);
  white-space: nowrap;
}

.profile-nav li:hover {
  background-color: var(--background-secondary);
  color: var(--text-primary);
}

.profile-nav li.active {
  background-color: transparent;
  color: var(--text-primary);
  font-weight: bold;
  border-left: 3px solid #2c3e50;
}

.nav-icon {
  margin-right: 0.5rem;
  font-size: 1.2rem;
}

.profile-content {
  width: 850px;
  background-color: var(--card-background);
  border-radius: var(--border-radius);
  box-shadow: var(--box-shadow);
  padding: 2rem;
  flex-shrink: 0;
}

.tab-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--text-primary);
  margin-bottom: 2rem;
}

.settings-section {
  margin-bottom: 2rem;
}

.settings-section h4 {
  font-size: 1.2rem;
  color: var(--text-primary);
  margin-bottom: 1.5rem;
}

.password-form {
  max-width: 500px;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: var(--text-primary);
  font-weight: 500;
}

.form-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid var(--border-color);
  border-radius: var(--border-radius);
  font-size: 1rem;
  transition: border-color var(--transition-fast);
}

.form-input:focus {
  outline: none;
  border-color: #2c3e50;
  box-shadow: 0 0 0 2px rgba(44, 62, 80, 0.1);
}

.btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: var(--border-radius);
  font-size: 1rem;
  cursor: pointer;
  transition: all var(--transition-fast);
}

.btn-primary {
  background-color: transparent;
  color: #2c3e50;
  border: 1px solid #2c3e50;
}

.btn-primary:hover:not(:disabled) {
  background-color: #2c3e50;
  color: white;
  transform: translateY(-1px);
  box-shadow: var(--shadow-medium);
}

.btn-primary:disabled {
  background-color: transparent;
  color: var(--disabled-color);
  border-color: var(--disabled-color);
  cursor: not-allowed;
}

.error-message {
  color: var(--red-color);
  margin-top: 1rem;
}

@media (max-width: 768px) {
  .profile-container {
    flex-direction: column;
  }
  
  .profile-sidebar {
    width: 100%;
    margin-right: 0;
    margin-bottom: 2rem;
  }
}
</style>