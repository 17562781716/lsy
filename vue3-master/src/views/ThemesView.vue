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
  { id: 'study', name: '学习' },
  { id: 'sports', name: '运动' },
  { id: 'aesthetic', name: '美育' },
  { id: 'psychology', name: '心理' },
  { id: 'culture', name: '文化' },
  { id: 'skills', name: '技能' },
  { id: 'travel', name: '旅行' },
  { id: 'handicraft', name: '手工' },
  { id: 'social', name: '社交' },
  { id: 'life', name: '生活' },
  { id: 'health', name: '健康' },
]);

const activeCategory = ref('all');

const themes = ref([
  // 学习类
  { id: 1, icon: '📖', name: '阅读周', description: '放下手机，沉浸在文字的世界里', category: 'study', tags: ['学习', '阅读'], participants: 512 },
  { id: 2, icon: '🗣️', name: '西语周', description: '学习新语言，打开新世界的大门', category: 'study', tags: ['学习', '语言'], participants: 198 },
  { id: 3, icon: '📚', name: '历史周', description: '穿越时空，了解人类文明的足迹', category: 'study', tags: ['学习', '知识'], participants: 156 },
  { id: 4, icon: '🔬', name: '科学周', description: '探索科学的奥秘，满足好奇心', category: 'study', tags: ['学习', '知识'], participants: 189 },
  { id: 5, icon: '🌍', name: '地理周', description: '了解世界，拓宽视野', category: 'study', tags: ['学习', '知识'], participants: 134 },
  { id: 6, icon: '📖', name: '日语周', description: '学习日语，感受东方文化的魅力', category: 'study', tags: ['学习', '语言'], participants: 156 },
  { id: 7, icon: '🎓', name: '法语周', description: '学习浪漫语言，感受法式风情', category: 'study', tags: ['学习', '语言'], participants: 142 },
  { id: 8, icon: '📐', name: '数学周', description: '重新发现数学之美，锻炼逻辑思维', category: 'study', tags: ['学习', '思维'], participants: 98 },
  
  // 运动类
  { id: 9, icon: '🏃', name: '健身周', description: '动起来，感受身体的力量与活力', category: 'sports', tags: ['运动', '健康'], participants: 425 },
  { id: 10, icon: '🧗', name: '攀岩周', description: '挑战自我，突破极限', category: 'sports', tags: ['运动', '挑战'], participants: 145 },
  { id: 11, icon: '🏊', name: '游泳周', description: '在水中感受自由，锻炼身体', category: 'sports', tags: ['运动', '健康'], participants: 234 },
  { id: 12, icon: '🚴', name: '骑行周', description: '用两个轮子探索城市，感受自由', category: 'sports', tags: ['运动', '户外'], participants: 267 },
  { id: 13, icon: '🏃‍♀️', name: '跑步周', description: '用脚步丈量城市，感受奔跑的快乐', category: 'sports', tags: ['运动', '户外'], participants: 398 },
  { id: 14, icon: '💃', name: '舞蹈周', description: '用身体感受节奏，释放内心激情', category: 'sports', tags: ['运动', '艺术'], participants: 176 },
  { id: 15, icon: '🏸', name: '羽毛球周', description: '挥拍之间，享受运动的乐趣', category: 'sports', tags: ['运动', '球类'], participants: 189 },
  { id: 16, icon: '☯️', name: '太极周', description: '以柔克刚，感受传统武术的魅力', category: 'sports', tags: ['运动', '传统'], participants: 123 },
  
  // 美育类
  { id: 17, icon: '🎭', name: '音乐剧周', description: '走进剧场，感受音乐与戏剧的碰撞', category: 'aesthetic', tags: ['美育', '艺术'], participants: 328 },
  { id: 18, icon: '🎨', name: '绘画周', description: '用画笔记录生活，表达内心世界', category: 'aesthetic', tags: ['美育', '创作'], participants: 143 },
  { id: 19, icon: '📸', name: '摄影周', description: '用镜头捕捉生活中的美好瞬间', category: 'aesthetic', tags: ['美育', '记录'], participants: 234 },
  { id: 20, icon: '🎵', name: '音乐周', description: '聆听不同风格音乐，感受旋律力量', category: 'aesthetic', tags: ['美育', '音乐'], participants: 287 },
  { id: 21, icon: '🎪', name: '戏剧周', description: '走进戏剧世界，体验不同人生故事', category: 'aesthetic', tags: ['美育', '表演'], participants: 112 },
  { id: 22, icon: '🎨', name: '水彩周', description: '用水彩描绘心中的色彩世界', category: 'aesthetic', tags: ['美育', '绘画'], participants: 167 },
  { id: 23, icon: '🎹', name: '钢琴周', description: '在黑白键上弹奏属于自己的旋律', category: 'aesthetic', tags: ['美育', '音乐'], participants: 189 },
  { id: 24, icon: '🎸', name: '吉他周', description: '拨动琴弦，用音乐表达自己', category: 'aesthetic', tags: ['美育', '音乐'], participants: 203 },
  { id: 25, icon: '🎬', name: '观影周', description: '每天一部电影，体验不同的人生', category: 'aesthetic', tags: ['美育', '电影'], participants: 267 },
  { id: 26, icon: '🖼️', name: '看展周', description: '走进美术馆，与艺术对话', category: 'aesthetic', tags: ['美育', '展览'], participants: 156 },
  
  // 心理类
  { id: 27, icon: '🧘', name: '冥想周', description: '静下心来，与自己对话', category: 'psychology', tags: ['心理', '静心'], participants: 356 },
  { id: 28, icon: '🧠', name: '心理学周', description: '了解心理学，更好地认识自己', category: 'psychology', tags: ['心理', '成长'], participants: 267 },
  { id: 29, icon: '🧘', name: '正念周', description: '活在当下，感受每一刻的美好', category: 'psychology', tags: ['心理', '静心'], participants: 256 },
  { id: 30, icon: '😴', name: '睡眠周', description: '改善睡眠质量，让身体充分休息', category: 'psychology', tags: ['心理', '作息'], participants: 289 },
  { id: 31, icon: '📔', name: '日记周', description: '用文字记录内心，梳理情绪', category: 'psychology', tags: ['心理', '记录'], participants: 198 },
  { id: 32, icon: '🌈', name: '感恩周', description: '学会感恩，发现生活中的美好', category: 'psychology', tags: ['心理', '积极'], participants: 234 },
  { id: 33, icon: '🎯', name: '断舍离周', description: '放下执念，轻装前行', category: 'psychology', tags: ['心理', '极简'], participants: 178 },
  
  // 文化类
  { id: 34, icon: '✍️', name: '书法周', description: '在笔墨间修身养性，感受汉字之美', category: 'culture', tags: ['文化', '传统'], participants: 134 },
  { id: 35, icon: '📝', name: '写作周', description: '用文字记录生活，表达内心世界', category: 'culture', tags: ['文化', '创作'], participants: 245 },
  { id: 36, icon: '🎤', name: '演讲周', description: '提升表达能力，勇敢说出想法', category: 'culture', tags: ['文化', '沟通'], participants: 178 },
  { id: 37, icon: '🎯', name: '目标周', description: '设定目标，规划未来', category: 'culture', tags: ['文化', '规划'], participants: 198 },
  { id: 38, icon: '📜', name: '诗词周', description: '品味古典诗词，感受文字之美', category: 'culture', tags: ['文化', '文学'], participants: 167 },
  { id: 39, icon: '🎎', name: '茶道周', description: '学习茶道礼仪，品味生活禅意', category: 'culture', tags: ['文化', '传统'], participants: 123 },
  { id: 40, icon: '🎵', name: '戏曲周', description: '走进传统戏曲，感受国粹魅力', category: 'culture', tags: ['文化', '传统'], participants: 89 },
  
  // 技能类
  { id: 41, icon: '💻', name: '编程周', description: '学习编程，用代码创造可能', category: 'skills', tags: ['技能', '技术'], participants: 312 },
  { id: 42, icon: '🎓', name: '学习周', description: '专注学习一项新技能', category: 'skills', tags: ['技能', '成长'], participants: 234 },
  { id: 43, icon: '🍳', name: '烹饪周', description: '探索美食的奥秘，享受烹饪的乐趣', category: 'skills', tags: ['技能', '美食'], participants: 289 },
  { id: 44, icon: '🎨', name: '设计周', description: '学习设计思维，提升审美能力', category: 'skills', tags: ['技能', '设计'], participants: 178 },
  { id: 45, icon: '📊', name: '数据分析周', description: '用数据说话，发现隐藏规律', category: 'skills', tags: ['技能', '分析'], participants: 156 },
  { id: 46, icon: '🎬', name: '剪辑周', description: '学习视频剪辑，记录精彩瞬间', category: 'skills', tags: ['技能', '创作'], participants: 198 },
  { id: 47, icon: '🎙️', name: '播客周', description: '尝试做一期播客，分享你的故事', category: 'skills', tags: ['技能', '表达'], participants: 134 },
  
  // 旅行类
  { id: 48, icon: '🎒', name: '旅行周', description: '用一周时间，深度探索一个地方', category: 'travel', tags: ['旅行', '探索'], participants: 198 },
  { id: 49, icon: '🗺️', name: '城市漫步周', description: '用脚步丈量城市，发现身边的美', category: 'travel', tags: ['旅行', '城市'], participants: 234 },
  { id: 50, icon: '⛰️', name: '登山周', description: '攀登高峰，俯瞰壮丽风景', category: 'travel', tags: ['旅行', '户外'], participants: 167 },
  { id: 51, icon: '🏖️', name: '海边周', description: '听海浪的声音，感受海风的温柔', category: 'travel', tags: ['旅行', '海边'], participants: 289 },
  { id: 52, icon: '🏕️', name: '露营周', description: '在星空下入睡，与自然亲密接触', category: 'travel', tags: ['旅行', '户外'], participants: 178 },
  { id: 53, icon: '🚂', name: '火车旅行周', description: '坐火车去远方，享受慢旅行', category: 'travel', tags: ['旅行', '慢生活'], participants: 145 },
  { id: 54, icon: '🏘️', name: '古镇周', description: '走进古镇老街，感受岁月静好', category: 'travel', tags: ['旅行', '文化'], participants: 123 },
  
  // 手工类
  { id: 55, icon: '🧶', name: '钩织周', description: '用双手创造温暖，编织生活的美好', category: 'handicraft', tags: ['手工', '生活'], participants: 186 },
  { id: 56, icon: '🏺', name: '陶艺周', description: '感受泥土的温度，塑造独特作品', category: 'handicraft', tags: ['手工', '艺术'], participants: 98 },
  { id: 57, icon: '🌸', name: '插花周', description: '用花艺装点生活，感受自然之美', category: 'handicraft', tags: ['手工', '艺术'], participants: 156 },
  { id: 58, icon: '🎁', name: '手作周', description: '用双手创造独一无二的礼物', category: 'handicraft', tags: ['手工', '创意'], participants: 189 },
  { id: 59, icon: '📓', name: '手帐周', description: '用手帐记录生活，发现小确幸', category: 'handicraft', tags: ['手工', '记录'], participants: 178 },
  { id: 60, icon: '🕯️', name: '香薰蜡烛周', description: '亲手制作香薰蜡烛，营造温馨氛围', category: 'handicraft', tags: ['手工', '疗愈'], participants: 134 },
  { id: 61, icon: '🧵', name: '刺绣周', description: '一针一线，绣出心中的图案', category: 'handicraft', tags: ['手工', '传统'], participants: 112 },
  
  // 社交类
  { id: 62, icon: '🤝', name: '交友周', description: '主动认识新朋友，拓展社交圈', category: 'social', tags: ['社交', '交友'], participants: 156 },
  { id: 63, icon: '❤️', name: '志愿者周', description: '用爱心回馈社会，感受奉献的快乐', category: 'social', tags: ['社交', '公益'], participants: 134 },
  { id: 64, icon: '🎉', name: '聚会周', description: '组织一场聚会，与朋友共度时光', category: 'social', tags: ['社交', '聚会'], participants: 178 },
  { id: 65, icon: '💬', name: '沟通周', description: '提升沟通技巧，建立更好人际关系', category: 'social', tags: ['社交', '沟通'], participants: 198 },
  { id: 66, icon: '🙏', name: '感恩周', description: '学会感恩，发现生活中的美好', category: 'social', tags: ['社交', '心理'], participants: 234 },
  { id: 67, icon: '📱', name: '分享周', description: '分享知识与经验，帮助他人成长', category: 'social', tags: ['社交', '分享'], participants: 167 },
  { id: 68, icon: '👂', name: '倾听周', description: '学会倾听，理解他人的感受', category: 'social', tags: ['社交', '沟通'], participants: 145 },
  { id: 69, icon: '👨‍👩‍👦', name: '陪伴周', description: '花时间陪伴家人，珍惜在一起的时光', category: 'social', tags: ['社交', '家庭'], participants: 212 },
  { id: 70, icon: '🏘️', name: '社区周', description: '参与社区活动，认识身边的邻居', category: 'social', tags: ['社交', '社区'], participants: 98 },
  { id: 71, icon: '🌟', name: '公益周', description: '关注社会问题，用行动传递温暖', category: 'social', tags: ['社交', '公益'], participants: 156 },
  
  // 生活类
  { id: 72, icon: '☕', name: '咖啡周', description: '品味不同产地的咖啡，感受咖啡文化', category: 'life', tags: ['生活', '美食'], participants: 256 },
  { id: 73, icon: '🧹', name: '整理周', description: '整理空间，也整理心情', category: 'life', tags: ['生活', '整理'], participants: 312 },
  { id: 74, icon: '👗', name: '穿搭周', description: '探索个人风格，穿出自信', category: 'life', tags: ['生活', '时尚'], participants: 234 },
  { id: 75, icon: '🏠', name: '极简周', description: '简化生活，回归本质', category: 'life', tags: ['生活', '极简'], participants: 267 },
  { id: 76, icon: '🎶', name: '歌单周', description: '整理心情歌单，用音乐陪伴每一天', category: 'life', tags: ['生活', '音乐'], participants: 223 },
  { id: 77, icon: '🌿', name: '香薰周', description: '用香气疗愈身心，营造舒适氛围', category: 'life', tags: ['生活', '疗愈'], participants: 145 },
  { id: 78, icon: '💅', name: '美甲周', description: '用色彩装饰指尖，展现个性', category: 'life', tags: ['生活', '时尚'], participants: 134 },
  { id: 79, icon: '🍷', name: '品酒周', description: '学习品鉴葡萄酒，感受微醺的快乐', category: 'life', tags: ['生活', '美食'], participants: 167 },
  
  // 健康类
  { id: 80, icon: '🥗', name: '健康饮食周', description: '关注饮食健康，吃出好身体', category: 'health', tags: ['健康', '饮食'], participants: 312 },
  { id: 81, icon: '🧘‍♀️', name: '瑜伽周', description: '在呼吸与伸展中，找到身心平衡', category: 'health', tags: ['健康', '运动'], participants: 345 },
  { id: 82, icon: '🍵', name: '养生周', description: '关注身体信号，学会照顾自己', category: 'health', tags: ['健康', '养生'], participants: 234 },
  { id: 83, icon: '🏋️', name: '力量训练周', description: '增强肌肉力量，塑造好身材', category: 'health', tags: ['健康', '运动'], participants: 187 },
  { id: 84, icon: '🌱', name: '园艺周', description: '与植物为伴，感受生命的力量', category: 'health', tags: ['健康', '自然'], participants: 178 },
  { id: 85, icon: '🧖‍♀️', name: '护肤周', description: '认真护肤，宠爱自己的肌肤', category: 'health', tags: ['健康', '美容'], participants: 267 },
  { id: 86, icon: '🥤', name: '排毒周', description: '清理身体毒素，恢复轻盈状态', category: 'health', tags: ['健康', '养生'], participants: 198 },
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
