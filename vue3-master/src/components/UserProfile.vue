<template>
  <div class="user-profile">
    <div class="profile-header">
      <div class="profile-avatar-container">
        <img 
          v-if="user.avatar" 
          :src="user.avatar" 
          alt="用户头像" 
          class="profile-avatar"
        >
        <div v-else class="profile-avatar-placeholder">
          {{ user.name.charAt(0) }}
        </div>
      </div>
      <button class="btn btn-primary edit-btn" @click="$emit('edit')">
        编辑资料
      </button>
    </div>
    
    <div class="profile-info">
      <div class="info-item">
        <span class="info-label">昵称：</span>
        <span class="info-value">{{ user.name || '未设置' }}</span>
      </div>
      <div class="info-item">
        <span class="info-label">用户名：</span>
        <span class="info-value">{{ user.username }}</span>
      </div>
      <div class="info-item">
        <span class="info-label">邮箱：</span>
        <span class="info-value">{{ user.email || '未设置' }}</span>
      </div>
      <div class="info-item">
        <span class="info-label">手机号码：</span>
        <span class="info-value">{{ user.phone || '未设置' }}</span>
      </div>
      <div class="info-item">
        <span class="info-label">性别：</span>
        <span class="info-value">{{ user.gender || '未设置' }}</span>
      </div>
      <div class="info-item">
        <span class="info-label">生日：</span>
        <span class="info-value">{{ user.birthday ? formatDate(user.birthday) : '未设置' }}</span>
      </div>
      <div class="info-item">
        <span class="info-label">地址：</span>
        <span class="info-value">{{ getFullAddress() || '未设置' }}</span>
      </div>
      <div class="info-item">
        <span class="info-label">会员等级：</span>
        <span class="info-value member-level">{{ user.memberLevel }}</span>
      </div>
      <div class="info-item">
        <span class="info-label">最大借阅数：</span>
        <span class="info-value">{{ user.maxBorrowLimit }}本</span>
      </div>
      <div class="info-item">
        <span class="info-label">账户余额：</span>
        <span class="info-value balance">¥{{ user.balance?.toFixed(2) || '438.70' }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useUserStore } from '../stores/user';

const userStore = useUserStore();
const user = computed(() => userStore.user);

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('zh-CN');
};

// 获取完整地址
const getFullAddress = () => {
  if (!user.value.address) return '';
  const { province, city, district, street } = user.value.address;
  return `${province || ''}${city || ''}${district || ''}${street || ''}`;
};

// 定义事件
const emit = defineEmits(['edit']);
</script>

<style scoped>
.user-profile {
  max-width: 700px;
  padding: 0 2rem;
}

.profile-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid var(--border-color);
}

.profile-avatar-container {
  display: flex;
  align-items: center;
}

.profile-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid var(--border-color);
}

.profile-avatar-placeholder {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background-color: #e0e0e0;
  color: #666666;
  font-size: 3rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid var(--border-color);
}

.edit-btn {
  padding: 0.75rem 2rem;
  border: 1px solid #2c3e50;
  border-radius: var(--border-radius);
  background-color: transparent;
  color: #2c3e50;
  cursor: pointer;
  transition: all var(--transition-fast);
}

.edit-btn:hover {
  background-color: #2c3e50;
  color: white;
  transform: translateY(-1px);
  box-shadow: var(--shadow-medium);
}

.profile-info {
  display: grid;
  grid-template-columns: 1fr;
  gap: 2rem;
}

.member-level {
  color: #2c3e50;
  font-weight: bold;
}

.info-item {
  display: flex;
  align-items: flex-start;
  min-height: 30px;
}

.info-label {
  width: 120px;
  font-weight: bold;
  color: var(--text-primary);
  margin-right: 1.5rem;
  flex-shrink: 0;
}

.info-value {
  flex: 1;
  color: var(--text-secondary);
  word-break: break-all;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.member-level {
  color: var(--primary-color);
  font-weight: bold;
}

.balance {
  color: #27ae60;
  font-weight: bold;
  font-size: 1.1rem;
}

@media (min-width: 768px) {
  .profile-info {
    grid-template-columns: 1fr 1fr;
    column-gap: 2rem;
  }
  
  /* 保持邮箱只显示一行 */
  .info-item:nth-child(3) .info-value {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
</style>