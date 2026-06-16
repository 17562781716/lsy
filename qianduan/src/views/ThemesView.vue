<template>
  <div class="themes-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">探索主题</h1>
        <p class="page-subtitle">发现属于你的主题周，为生活增添色彩</p>
      </div>
      
      <div class="themes-categories">
        <button 
          v-for="cat in categories" 
          :key="cat.id"
          class="category-btn"
          :class="{ active: activeCategory === cat.id }"
          @click="activeCategory = cat.id"
        >
          {{ cat.name }}
        </button>
      </div>
      
      <div class="themes-grid">
        <div v-for="theme in filteredThemes" :key="theme.id" class="theme-card">
          <div class="theme-icon">{{ theme.icon }}</div>
          <h3 class="theme-name">{{ theme.name }}</h3>
          <p class="theme-desc">{{ theme.description }}</p>
          <div class="theme-tags">
            <span v-for="tag in theme.tags" :key="tag" class="theme-tag">{{ tag }}</span>
          </div>
          <div class="theme-footer">
            <span class="participants">{{ theme.participants }}人参与</span>
            <button class="btn-primary btn-sm" @click="startTheme(theme)">开始</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useThemesStore } from '../stores/themes';

const router = useRouter();
const themesStore = useThemesStore();

const categories = computed(() => themesStore.categories);
const activeCategory = ref('all');

const filteredThemes = computed(() => {
  if (activeCategory.value === 'all') return themesStore.themes;
  return themesStore.themes.filter(t => t.category === activeCategory.value);
});

const startTheme = (theme) => {
  router.push('/planner');
};
</script>

<style scoped>
.themes-page {
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

.themes-categories {
  display: flex;
  gap: 12px;
  justify-content: center;
  margin-bottom: 40px;
  flex-wrap: wrap;
}

.category-btn {
  padding: 8px 20px;
  border-radius: 20px;
  border: 1px solid var(--border-color);
  background: white;
  color: var(--text-dark);
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.category-btn:hover {
  border-color: var(--primary-color);
  color: var(--primary-color);
}

.category-btn.active {
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  color: white;
  border-color: transparent;
}

.themes-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
}

.theme-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 32px;
  text-align: center;
  box-shadow: var(--shadow-light);
  transition: all 0.3s;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.theme-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-medium);
}

.theme-icon {
  font-size: 36px;
  margin-bottom: 16px;
}

.theme-name {
  font-size: 20px;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.theme-desc {
  font-size: 14px;
  color: var(--text-muted);
  margin-bottom: 16px;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  min-height: 22px;
}

.theme-tags {
  display: flex;
  gap: 8px;
  justify-content: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  min-height: 28px;
}

.theme-tag {
  font-size: 12px;
  padding: 4px 12px;
  background: var(--background-secondary);
  color: var(--primary-color);
  border-radius: 12px;
}

.theme-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: auto;
  width: 100%;
}

.participants {
  font-size: 13px;
  color: var(--text-muted);
}

.theme-footer .btn-primary {
  border-radius: 20px;
  border: none;
  padding: 6px 20px;
}

@media (max-width: 1024px) {
  .themes-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .themes-grid {
    grid-template-columns: 1fr;
  }
}
</style>
