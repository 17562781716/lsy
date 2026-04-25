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

const router = useRouter();

const categories = ref([
  { id: 'all', name: '全部' },
  { id: 'art', name: '艺术' },
  { id: 'growth', name: '成长' },
  { id: 'health', name: '健康' },
  { id: 'life', name: '生活' },
  { id: 'social', name: '社交' },
]);

const activeCategory = ref('all');

const themes = ref([
  { id: 1, icon: '🎭', name: '音乐剧周', description: '走进剧场，感受音乐与戏剧的碰撞', category: 'art', tags: ['艺术', '音乐'], participants: 328 },
  { id: 2, icon: '📖', name: '阅读周', description: '放下手机，沉浸在文字的世界里', category: 'growth', tags: ['成长', '阅读'], participants: 512 },
  { id: 3, icon: '🧶', name: '钩织周', description: '用双手创造温暖，编织生活的美好', category: 'life', tags: ['手工', '生活'], participants: 186 },
  { id: 4, icon: '🏃', name: '健身周', description: '动起来，感受身体的力量与活力', category: 'health', tags: ['健康', '运动'], participants: 425 },
  { id: 5, icon: '🎬', name: '观影周', description: '每天一部电影，体验不同的人生', category: 'art', tags: ['艺术', '电影'], participants: 267 },
  { id: 6, icon: '🎨', name: '绘画周', description: '用画笔记录生活，表达内心世界', category: 'art', tags: ['艺术', '创作'], participants: 143 },
  { id: 7, icon: '🗣️', name: '西语周', description: '学习新语言，打开新世界的大门', category: 'growth', tags: ['成长', '语言'], participants: 198 },
  { id: 8, icon: '🧘', name: '冥想周', description: '静下心来，与自己对话', category: 'health', tags: ['健康', '心理'], participants: 356 },
  { id: 9, icon: '🍳', name: '烹饪周', description: '探索美食的奥秘，享受烹饪的乐趣', category: 'life', tags: ['生活', '美食'], participants: 289 },
  { id: 10, icon: '📸', name: '摄影周', description: '用镜头捕捉生活中的美好瞬间', category: 'art', tags: ['艺术', '记录'], participants: 234 },
  { id: 11, icon: '🎸', name: '吉他周', description: '拨动琴弦，用音乐表达自己', category: 'art', tags: ['艺术', '音乐'], participants: 203 },
  { id: 12, icon: '🌱', name: '园艺周', description: '与植物为伴，感受生命的力量', category: 'life', tags: ['生活', '自然'], participants: 178 },
  { id: 13, icon: '💃', name: '舞蹈周', description: '用身体感受节奏，释放内心激情', category: 'art', tags: ['艺术', '运动'], participants: 176 },
  { id: 14, icon: '🏺', name: '陶艺周', description: '感受泥土的温度，塑造独特作品', category: 'art', tags: ['艺术', '手工'], participants: 98 },
  { id: 15, icon: '🌸', name: '插花周', description: '用花艺装点生活，感受自然之美', category: 'art', tags: ['艺术', '生活'], participants: 156 },
  { id: 16, icon: '✍️', name: '书法周', description: '在笔墨间修身养性，感受汉字之美', category: 'art', tags: ['艺术', '文化'], participants: 134 },
  { id: 17, icon: '🎵', name: '音乐周', description: '聆听不同风格音乐，感受旋律力量', category: 'art', tags: ['艺术', '音乐'], participants: 287 },
  { id: 18, icon: '🎪', name: '戏剧周', description: '走进戏剧世界，体验不同人生故事', category: 'art', tags: ['艺术', '表演'], participants: 112 },
  { id: 19, icon: '🎨', name: '水彩周', description: '用水彩描绘心中的色彩世界', category: 'art', tags: ['艺术', '绘画'], participants: 167 },
  { id: 20, icon: '🎹', name: '钢琴周', description: '在黑白键上弹奏属于自己的旋律', category: 'art', tags: ['艺术', '音乐'], participants: 189 },
  { id: 21, icon: '📝', name: '写作周', description: '用文字记录生活，表达内心世界', category: 'growth', tags: ['成长', '创作'], participants: 245 },
  { id: 22, icon: '💻', name: '编程周', description: '学习编程，用代码创造可能', category: 'growth', tags: ['成长', '技能'], participants: 312 },
  { id: 23, icon: '🎤', name: '演讲周', description: '提升表达能力，勇敢说出想法', category: 'growth', tags: ['成长', '沟通'], participants: 178 },
  { id: 24, icon: '📚', name: '历史周', description: '穿越时空，了解人类文明的足迹', category: 'growth', tags: ['成长', '知识'], participants: 156 },
  { id: 25, icon: '🔬', name: '科学周', description: '探索科学的奥秘，满足好奇心', category: 'growth', tags: ['成长', '知识'], participants: 189 },
  { id: 26, icon: '🌍', name: '地理周', description: '了解世界，拓宽视野', category: 'growth', tags: ['成长', '知识'], participants: 134 },
  { id: 27, icon: '🧠', name: '心理学周', description: '了解心理学，更好地认识自己', category: 'growth', tags: ['成长', '心理'], participants: 267 },
  { id: 28, icon: '🎯', name: '目标周', description: '设定目标，规划未来', category: 'growth', tags: ['成长', '规划'], participants: 198 },
  { id: 29, icon: '📖', name: '日语周', description: '学习日语，感受东方文化的魅力', category: 'growth', tags: ['成长', '语言'], participants: 156 },
  { id: 30, icon: '🎓', name: '学习周', description: '专注学习一项新技能', category: 'growth', tags: ['成长', '学习'], participants: 234 },
  { id: 31, icon: '🧗', name: '攀岩周', description: '挑战自我，突破极限', category: 'health', tags: ['健康', '运动'], participants: 145 },
  { id: 32, icon: '🏊', name: '游泳周', description: '在水中感受自由，锻炼身体', category: 'health', tags: ['健康', '运动'], participants: 234 },
  { id: 33, icon: '🚴', name: '骑行周', description: '用两个轮子探索城市，感受自由', category: 'health', tags: ['健康', '运动'], participants: 267 },
  { id: 34, icon: '🥗', name: '健康饮食周', description: '关注饮食健康，吃出好身体', category: 'health', tags: ['健康', '饮食'], participants: 312 },
  { id: 35, icon: '😴', name: '睡眠周', description: '改善睡眠质量，让身体充分休息', category: 'health', tags: ['健康', '作息'], participants: 289 },
  { id: 36, icon: '🧘‍♀️', name: '瑜伽周', description: '在呼吸与伸展中，找到身心平衡', category: 'health', tags: ['健康', '运动'], participants: 345 },
  { id: 37, icon: '🏃‍♀️', name: '跑步周', description: '用脚步丈量城市，感受奔跑的快乐', category: 'health', tags: ['健康', '运动'], participants: 398 },
  { id: 38, icon: '🍵', name: '养生周', description: '关注身体信号，学会照顾自己', category: 'health', tags: ['健康', '养生'], participants: 234 },
  { id: 39, icon: '🏋️', name: '力量训练周', description: '增强肌肉力量，塑造好身材', category: 'health', tags: ['健康', '运动'], participants: 187 },
  { id: 40, icon: '🧘', name: '正念周', description: '活在当下，感受每一刻的美好', category: 'health', tags: ['健康', '心理'], participants: 256 },
  { id: 41, icon: '☕', name: '咖啡周', description: '品味不同产地的咖啡，感受咖啡文化', category: 'life', tags: ['生活', '美食'], participants: 256 },
  { id: 42, icon: '🧹', name: '整理周', description: '整理空间，也整理心情', category: 'life', tags: ['生活', '整理'], participants: 312 },
  { id: 43, icon: '👗', name: '穿搭周', description: '探索个人风格，穿出自信', category: 'life', tags: ['生活', '时尚'], participants: 234 },
  { id: 44, icon: '🎒', name: '旅行周', description: '用一周时间，深度探索一个地方', category: 'life', tags: ['生活', '旅行'], participants: 198 },
  { id: 45, icon: '🏠', name: '极简周', description: '简化生活，回归本质', category: 'life', tags: ['生活', '极简'], participants: 267 },
  { id: 46, icon: '🎁', name: '手作周', description: '用双手创造独一无二的礼物', category: 'life', tags: ['生活', '手工'], participants: 189 },
  { id: 47, icon: '🎶', name: '歌单周', description: '整理心情歌单，用音乐陪伴每一天', category: 'life', tags: ['生活', '音乐'], participants: 223 },
  { id: 48, icon: '🌿', name: '香薰周', description: '用香气疗愈身心，营造舒适氛围', category: 'life', tags: ['生活', '疗愈'], participants: 145 },
  { id: 49, icon: '📓', name: '手帐周', description: '用手帐记录生活，发现小确幸', category: 'life', tags: ['生活', '记录'], participants: 178 },
  { id: 50, icon: '💅', name: '美甲周', description: '用色彩装饰指尖，展现个性', category: 'life', tags: ['生活', '时尚'], participants: 134 },
  { id: 51, icon: '🤝', name: '交友周', description: '主动认识新朋友，拓展社交圈', category: 'social', tags: ['社交', '交友'], participants: 156 },
  { id: 52, icon: '❤️', name: '志愿者周', description: '用爱心回馈社会，感受奉献的快乐', category: 'social', tags: ['社交', '公益'], participants: 134 },
  { id: 53, icon: '🎉', name: '聚会周', description: '组织一场聚会，与朋友共度时光', category: 'social', tags: ['社交', '聚会'], participants: 178 },
  { id: 54, icon: '💬', name: '沟通周', description: '提升沟通技巧，建立更好人际关系', category: 'social', tags: ['社交', '沟通'], participants: 198 },
  { id: 55, icon: '🙏', name: '感恩周', description: '学会感恩，发现生活中的美好', category: 'social', tags: ['社交', '心理'], participants: 234 },
  { id: 56, icon: '📱', name: '分享周', description: '分享知识与经验，帮助他人成长', category: 'social', tags: ['社交', '分享'], participants: 167 },
  { id: 57, icon: '👂', name: '倾听周', description: '学会倾听，理解他人的感受', category: 'social', tags: ['社交', '沟通'], participants: 145 },
  { id: 58, icon: '👨‍👩‍‍👦', name: '陪伴周', description: '花时间陪伴家人，珍惜在一起的时光', category: 'social', tags: ['社交', '家庭'], participants: 212 },
  { id: 59, icon: '🏘️', name: '社区周', description: '参与社区活动，认识身边的邻居', category: 'social', tags: ['社交', '社区'], participants: 98 },
  { id: 60, icon: '🌟', name: '公益周', description: '关注社会问题，用行动传递温暖', category: 'social', tags: ['社交', '公益'], participants: 156 },
]);

const filteredThemes = computed(() => {
  if (activeCategory.value === 'all') return themes.value;
  return themes.value.filter(t => t.category === activeCategory.value);
});

const startTheme = (theme) => {
  router.push('/planner');
};
</script>

<style scoped>
.themes-page {
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
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.theme-tags {
  display: flex;
  gap: 8px;
  justify-content: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
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
