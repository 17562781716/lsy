<template>
  <section class="salon-section">
    <div class="container">
      <div class="section-header">
        <h1 class="section-title">沙龙交流</h1>
        <p class="section-description">参与云边书吧的阅读沙龙交流，与作者和读者面对面交流</p>
      </div>
      
      <div class="salon-list">
        <Card 
          v-for="salon in salonEvents" 
          :key="salon.id"
          class="salon-card"
        >
          <div class="salon-header">
            <h3 class="salon-title">{{ salon.title }}</h3>
            <div class="salon-date">{{ formatDate(salon.date) }}</div>
          </div>
          <div class="salon-content">
            <p class="salon-description">{{ salon.description }}</p>
          </div>
          <div class="salon-details">
            <div class="salon-info">
              <span class="salon-info-label">时间：</span>
              <span class="salon-info-value">{{ formatDate(salon.date) }} {{ salon.time }}</span>
            </div>
            <div class="salon-info">
              <span class="salon-info-label">地点：</span>
              <span class="salon-info-value">{{ salon.location }}</span>
            </div>
          </div>
          <div class="salon-footer">
            <Button type="primary" size="small" @click="handleSignUp">立即报名</Button>
          </div>
        </Card>
      </div>
    </div>
    
    <!-- 报名成功弹窗 -->
    <div v-if="showSuccessModal" class="success-modal-overlay">
      <div class="success-modal">
        <button class="close-button" @click="closeModal">×</button>
        <div class="success-icon">✓</div>
        <h3 class="success-title">报名成功</h3>
        <p class="success-message">您已成功报名该沙龙活动！</p>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import Card from '../components/Card.vue';
import Button from '../components/Button.vue';

// 格式化日期
const formatDate = (date) => {
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1);
  const day = String(d.getDate());
  return `${year}年${month}月${day}日`;
};

// 报名成功弹窗
const showSuccessModal = ref(false);
const handleSignUp = () => {
  showSuccessModal.value = true;
  // 3秒后自动关闭弹窗
  setTimeout(() => {
    showSuccessModal.value = false;
  }, 3000);
};

// 关闭弹窗
const closeModal = () => {
  showSuccessModal.value = false;
};

// 沙龙交流数据
const salonEvents = ref([
  {
    id: 4,
    title: '儿童文学创作工坊',
    description: '专为青少年设计的文学创作工坊，通过互动游戏和创意写作，激发孩子们的想象力和表达能力。',
    date: '2025-11-25',
    time: '10:00 - 12:00',
    location: '云边书吧 一楼儿童阅读区',
    speaker: '林老师（儿童文学作家）'
  },
  {
    id: 1,
    title: '当代文学创作分享会',
    description: '邀请知名作家张明分享他的创作历程和最新作品《城市之光》的创作心得，探讨当代都市文学的发展趋势。',
    date: '2025-12-15',
    time: '19:00 - 21:00',
    location: '云边书吧 三楼多功能厅',
    speaker: '张明（知名作家）'
  },
  {
    id: 2,
    title: '经典名著读书会',
    description: '一起精读《百年孤独》，分享阅读体验和感悟，探讨马尔克斯的魔幻现实主义写作风格。',
    date: '2025-12-20',
    time: '14:30 - 16:30',
    location: '云边书吧 二楼阅读区',
    speaker: '李教授（文学评论家）'
  },
  {
    id: 3,
    title: '科幻小说的未来展望',
    description: '探讨科幻文学在AI时代的新发展，邀请科幻作家王宇和读者一起畅想未来世界的可能性。',
    date: '2025-12-28',
    time: '19:00 - 21:00',
    location: '云边书吧 三楼多功能厅',
    speaker: '王宇（科幻作家）'
  }
]);
</script>

<style scoped>
.salon-section {
  padding: 4rem 0;
  background-color: var(--color-background-light);
}

.section-header {
  text-align: center;
  margin-bottom: 3rem;
}

.section-title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: var(--color-text-primary);
}

.section-description {
  font-size: 1.1rem;
  color: var(--color-text-secondary);
  max-width: 700px;
  margin: 0 auto;
}

.salon-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(420px, 1fr));
  gap: 2rem;
}

.salon-card {
  padding: 1.5rem;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  display: flex;
  flex-direction: column;
  height: 100%;
  min-width: 420px;
}

.salon-footer {
  margin-top: auto;
  padding-top: 1rem;
}

.salon-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.salon-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid var(--color-border);
  flex-wrap: wrap;
}

.salon-title {
  font-size: 1.1rem;
  margin: 0;
  color: var(--color-text-primary);
  flex: 1;
  min-width: 250px;
  white-space: nowrap;
}

.salon-date {
  background-color: var(--color-primary);
  color: white;
  padding: 0.3rem 0.8rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 500;
  margin-left: 1rem;
  white-space: nowrap;
  flex-shrink: 0;
}

.salon-content {
  margin-bottom: 1.5rem;
  min-height: 80px;
}

.salon-description {
  font-size: 0.95rem;
  line-height: 1.6;
  color: var(--color-text-secondary);
  margin: 0;
}

.salon-details {
  background-color: var(--color-background-lighter);
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 1.5rem;
}

.salon-info {
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
}

.salon-info:last-child {
  margin-bottom: 0;
}

.salon-info-label {
  font-weight: 500;
  color: var(--color-text-primary);
  min-width: 60px;
}

.salon-info-value {
  color: var(--color-text-secondary);
  flex: 1;
}

.salon-footer {
  display: flex;
  justify-content: flex-end;
}

/* 报名成功弹窗样式 */
.success-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  animation: fadeIn 0.3s ease;
}

.success-modal {
  background-color: white;
  padding: 2rem;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  animation: slideUp 0.3s ease;
  max-width: 400px;
  width: 90%;
  position: relative;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #333;
  cursor: pointer;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  transition: background-color 0.2s ease;
}

.close-button:hover {
  background-color: #f0f0f0;
}

.success-icon {
  width: 60px;
  height: 60px;
  background-color: #4CAF50;
  color: white;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2rem;
  margin: 0 auto 1rem;
}

.success-title {
  font-size: 1.5rem;
  color: var(--color-text-primary);
  margin: 0 0 0.5rem;
}

.success-message {
  font-size: 1rem;
  color: var(--color-text-secondary);
  margin: 0;
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

@keyframes slideUp {
  from {
    transform: translateY(30px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .salon-section {
    padding: 2rem 0;
  }
  
  .section-title {
    font-size: 2rem;
  }
  
  .section-description {
    font-size: 1rem;
  }
  
  .salon-list {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
  
  .salon-header {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .salon-date {
    margin-left: 0;
    margin-top: 0.5rem;
  }
}
</style>