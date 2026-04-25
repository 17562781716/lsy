<template>
  <section class="feature-section">
    <div class="container">
      <div class="section-header">
        <h2 class="section-title">特色功能</h2>
        <p class="section-description">探索云边书吧的核心功能，享受便捷的阅读体验</p>
      </div>
      
      <div class="feature-grid">
        <Card 
          v-for="feature in features" 
          :key="feature.id"
          class="feature-card"
        >
          <div class="feature-icon">
            {{ feature.icon }}
          </div>
          <h3 class="feature-title">{{ feature.title }}</h3>
          <p class="feature-description">{{ feature.description }}</p>
          <RouterLink :to="feature.link" class="btn-secondary">
            了解更多
          </RouterLink>
        </Card>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter, RouterLink } from 'vue-router';
import Card from './Card.vue';

const router = useRouter();

// 功能入口数据
const features = ref([
  {
    id: 1,
    icon: '📚',
    title: '海量藏书',
    description: '超过10万册精选图书，涵盖各类题材，满足不同阅读需求',
    link: '/books'
  },
  {
    id: 2,
    icon: '☕',
    title: '咖啡时光',
    description: '品味香浓咖啡，享受阅读时光，查看我们的咖啡菜单',
    link: '/coffee'
  },
  {
    id: 4,
    icon: '🤝',
    title: '沙龙交流',
    description: '参与各类阅读沙龙交流，与作者和读者面对面交流',
    link: '/salon'
  },
  {
    id: 5,
    icon: '🎁',
    title: '会员福利',
    description: '成为会员享受多重福利：图书8.5折、咖啡8折、沙龙交流半价，还有积分兑换好礼',
    link: '/membership'
  }
]);
</script>

<style scoped>
.feature-section {
  padding: 6rem 0;
  background-color: var(--color-background-light);
}

.section-header {
  text-align: center;
  margin-bottom: 4rem;
}

.section-title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: var(--color-text-primary);
}

.section-description {
  font-size: 1.25rem;
  color: var(--color-text-secondary);
  max-width: 600px;
  margin: 0 auto;
}

.feature-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 2rem;
  max-width: 800px;
  margin: 0 auto;
}

.feature-card {
  padding: 2rem;
  text-align: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.feature-icon {
  font-size: 4rem;
  margin-bottom: 1.5rem;
  display: block;
}

.feature-title {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  color: var(--color-text-primary);
}

.feature-description {
  font-size: 1rem;
  color: var(--color-text-secondary);
  margin-bottom: 1.5rem;
  line-height: 1.6;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .feature-section {
    padding: 4rem 0;
  }
  
  .section-title {
    font-size: 2rem;
  }
  
  .section-description {
    font-size: 1rem;
  }
  
  .feature-grid {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
}
</style>