import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useThemesStore = defineStore('themes', () => {
  // 主题分类
  const categories = ref([
    { id: 'all', name: '全部' },
    { id: 'art', name: '艺术' },
    { id: 'growth', name: '成长' },
    { id: 'health', name: '健康' },
    { id: 'life', name: '生活' },
    { id: 'social', name: '社交' },
  ]);

  // 主题列表
  const themes = ref([
    {
      id: 1,
      icon: '🎭',
      name: '音乐剧周',
      description: '走进剧场，感受音乐与戏剧的碰撞',
      category: 'art',
      tags: ['艺术', '音乐'],
      participants: 328,
      ideas: ['观看一场现场音乐剧', '学唱一首经典音乐剧选段', '了解音乐剧的发展历史'],
    },
    {
      id: 2,
      icon: '📖',
      name: '阅读周',
      description: '放下手机，沉浸在文字的世界里',
      category: 'growth',
      tags: ['成长', '阅读'],
      participants: 512,
      ideas: ['读完一本书', '写读书笔记', '分享读书心得'],
    },
    {
      id: 3,
      icon: '🧶',
      name: '钩织周',
      description: '用双手创造温暖，编织生活的美好',
      category: 'life',
      tags: ['手工', '生活'],
      participants: 186,
      ideas: ['学会基础钩织针法', '完成一个小作品', '尝试新花样'],
    },
    {
      id: 4,
      icon: '🏃',
      name: '健身周',
      description: '动起来，感受身体的力量与活力',
      category: 'health',
      tags: ['健康', '运动'],
      participants: 425,
      ideas: ['每天运动30分钟', '尝试新的运动方式', '记录身体变化'],
    },
    {
      id: 5,
      icon: '🎬',
      name: '观影周',
      description: '每天一部电影，体验不同的人生',
      category: 'art',
      tags: ['艺术', '电影'],
      participants: 267,
      ideas: ['每天看一部电影', '写影评', '了解电影制作'],
    },
    {
      id: 6,
      icon: '🎨',
      name: '绘画周',
      description: '用画笔记录生活，表达内心世界',
      category: 'art',
      tags: ['艺术', '创作'],
      participants: 143,
      ideas: ['每天画一幅画', '尝试不同画材', '学习构图技巧'],
    },
    {
      id: 7,
      icon: '🗣️',
      name: '西语周',
      description: '学习一门新语言，打开新世界的大门',
      category: 'growth',
      tags: ['成长', '语言'],
      participants: 198,
      ideas: ['学习基础发音', '看一部西语电影', '学会日常对话'],
    },
    {
      id: 8,
      icon: '🧘',
      name: '冥想周',
      description: '静下心来，与自己对话',
      category: 'health',
      tags: ['健康', '心理'],
      participants: 356,
      ideas: ['每天冥想10分钟', '尝试不同冥想方式', '记录内心感受'],
    },
    {
      id: 9,
      icon: '🍳',
      name: '烹饪周',
      description: '探索美食的奥秘，享受烹饪的乐趣',
      category: 'life',
      tags: ['生活', '美食'],
      participants: 289,
      ideas: ['学做一道新菜', '尝试不同国家料理', '记录食谱'],
    },
    {
      id: 10,
      icon: '📸',
      name: '摄影周',
      description: '用镜头捕捉生活中的美好瞬间',
      category: 'art',
      tags: ['艺术', '记录'],
      participants: 234,
      ideas: ['每天拍一张主题照片', '学习构图技巧', '制作摄影集'],
    },
    {
      id: 11,
      icon: '💃',
      name: '舞蹈周',
      description: '用身体感受节奏，释放内心激情',
      category: 'art',
      tags: ['艺术', '运动'],
      participants: 176,
      ideas: ['学习一种舞蹈风格', '每天练习基本功', '录制一段舞蹈视频'],
    },
    {
      id: 12,
      icon: '🏺',
      name: '陶艺周',
      description: '感受泥土的温度，塑造独特的作品',
      category: 'art',
      tags: ['艺术', '手工'],
      participants: 98,
      ideas: ['学习拉坯技巧', '制作一个杯子', '尝试釉上彩'],
    },
    {
      id: 13,
      icon: '🌸',
      name: '插花周',
      description: '用花艺装点生活，感受自然之美',
      category: 'art',
      tags: ['艺术', '生活'],
      participants: 156,
      ideas: ['学习基础插花技巧', '完成一个花艺作品', '了解花材搭配'],
    },
    {
      id: 14,
      icon: '✍️',
      name: '书法周',
      description: '在笔墨间修身养性，感受汉字之美',
      category: 'art',
      tags: ['艺术', '文化'],
      participants: 134,
      ideas: ['练习基本笔画', '临摹一幅字帖', '完成一幅书法作品'],
    },
    {
      id: 15,
      icon: '🎵',
      name: '音乐周',
      description: '聆听不同风格的音乐，感受旋律的力量',
      category: 'art',
      tags: ['艺术', '音乐'],
      participants: 287,
      ideas: ['每天听一张专辑', '学习一种乐器', '了解音乐流派'],
    },
    {
      id: 16,
      icon: '🎪',
      name: '戏剧周',
      description: '走进戏剧世界，体验不同的人生故事',
      category: 'art',
      tags: ['艺术', '表演'],
      participants: 112,
      ideas: ['观看一场话剧', '尝试即兴表演', '了解戏剧流派'],
    },
    {
      id: 17,
      icon: '🎸',
      name: '吉他周',
      description: '拨动琴弦，用音乐表达自己',
      category: 'art',
      tags: ['艺术', '音乐'],
      participants: 203,
      ideas: ['学会基础和弦', '弹唱一首歌', '练习指法'],
    },
    {
      id: 18,
      icon: '️',
      name: '水彩周',
      description: '用水彩描绘心中的色彩世界',
      category: 'art',
      tags: ['艺术', '绘画'],
      participants: 167,
      ideas: ['学习水彩基础技法', '完成一幅风景画', '尝试湿画法'],
    },
    {
      id: 19,
      icon: '📝',
      name: '写作周',
      description: '用文字记录生活，表达内心世界',
      category: 'growth',
      tags: ['成长', '创作'],
      participants: 245,
      ideas: ['每天写一篇日记', '尝试写一首诗', '完成一篇短篇小说'],
    },
    {
      id: 20,
      icon: '💻',
      name: '编程周',
      description: '学习编程，用代码创造可能',
      category: 'growth',
      tags: ['成长', '技能'],
      participants: 312,
      ideas: ['完成一个编程入门课程', '写一个小程序', '学习一种新语言'],
    },
    {
      id: 21,
      icon: '🎤',
      name: '演讲周',
      description: '提升表达能力，勇敢说出自己的想法',
      category: 'growth',
      tags: ['成长', '沟通'],
      participants: 178,
      ideas: ['每天练习演讲5分钟', '录制一段演讲视频', '参加一次公开演讲'],
    },
    {
      id: 22,
      icon: '📚',
      name: '历史周',
      description: '穿越时空，了解人类文明的足迹',
      category: 'growth',
      tags: ['成长', '知识'],
      participants: 156,
      ideas: ['读一本历史书', '看一部历史纪录片', '了解一个历史时期'],
    },
    {
      id: 23,
      icon: '🔬',
      name: '科学周',
      description: '探索科学的奥秘，满足好奇心',
      category: 'growth',
      tags: ['成长', '知识'],
      participants: 189,
      ideas: ['做一个科学小实验', '看一部科普纪录片', '了解一个科学原理'],
    },
    {
      id: 24,
      icon: '🌍',
      name: '地理周',
      description: '了解世界，拓宽视野',
      category: 'growth',
      tags: ['成长', '知识'],
      participants: 134,
      ideas: ['了解一个国家', '看一部地理纪录片', '学习地理知识'],
    },
    {
      id: 25,
      icon: '🧠',
      name: '心理学周',
      description: '了解心理学，更好地认识自己和他人',
      category: 'growth',
      tags: ['成长', '心理'],
      participants: 267,
      ideas: ['读一本心理学书籍', '了解一种心理学理论', '尝试心理测试'],
    },
    {
      id: 26,
      icon: '🎯',
      name: '目标周',
      description: '设定目标，规划未来',
      category: 'growth',
      tags: ['成长', '规划'],
      participants: 198,
      ideas: ['制定年度目标', '分解月度计划', '每天复盘'],
    },
    {
      id: 27,
      icon: '',
      name: '攀岩周',
      description: '挑战自我，突破极限',
      category: 'health',
      tags: ['健康', '运动'],
      participants: 145,
      ideas: ['体验一次室内攀岩', '学习基本技巧', '完成一条线路'],
    },
    {
      id: 28,
      icon: '🏊',
      name: '游泳周',
      description: '在水中感受自由，锻炼身体',
      category: 'health',
      tags: ['健康', '运动'],
      participants: 234,
      ideas: ['每天游泳30分钟', '学习一种泳姿', '提高游泳速度'],
    },
    {
      id: 29,
      icon: '🚴',
      name: '骑行周',
      description: '用两个轮子探索城市，感受风的自由',
      category: 'health',
      tags: ['健康', '运动'],
      participants: 267,
      ideas: ['每天骑行5公里', '探索一条新路线', '记录骑行轨迹'],
    },
    {
      id: 30,
      icon: '🥗',
      name: '健康饮食周',
      description: '关注饮食健康，吃出好身体',
      category: 'health',
      tags: ['健康', '饮食'],
      participants: 312,
      ideas: ['每天吃五种蔬菜', '减少糖分摄入', '学习营养搭配'],
    },
    {
      id: 31,
      icon: '😴',
      name: '睡眠周',
      description: '改善睡眠质量，让身体充分休息',
      category: 'health',
      tags: ['健康', '作息'],
      participants: 289,
      ideas: ['每天11点前入睡', '睡前不玩手机', '记录睡眠时长'],
    },
    {
      id: 32,
      icon: '🧘‍♀️',
      name: '瑜伽周',
      description: '在呼吸与伸展中，找到身心的平衡',
      category: 'health',
      tags: ['健康', '运动'],
      participants: 345,
      ideas: ['每天练习瑜伽20分钟', '学习基础体式', '尝试冥想瑜伽'],
    },
    {
      id: 33,
      icon: '🏃‍♀️',
      name: '跑步周',
      description: '用脚步丈量城市，感受奔跑的快乐',
      category: 'health',
      tags: ['健康', '运动'],
      participants: 398,
      ideas: ['每天跑步3公里', '尝试间歇跑', '参加一次跑步活动'],
    },
    {
      id: 34,
      icon: '',
      name: '养生周',
      description: '关注身体信号，学会照顾自己',
      category: 'health',
      tags: ['健康', '养生'],
      participants: 234,
      ideas: ['每天泡脚', '学习穴位按摩', '尝试养生茶饮'],
    },
    {
      id: 35,
      icon: '🌿',
      name: '园艺周',
      description: '与植物为伴，感受生命的力量',
      category: 'life',
      tags: ['生活', '自然'],
      participants: 178,
      ideas: ['种植一盆植物', '学习浇水技巧', '了解植物习性'],
    },
    {
      id: 36,
      icon: '☕',
      name: '咖啡周',
      description: '品味不同产地的咖啡，感受咖啡文化',
      category: 'life',
      tags: ['生活', '美食'],
      participants: 256,
      ideas: ['品尝不同产地咖啡', '学习手冲咖啡', '了解咖啡知识'],
    },
    {
      id: 37,
      icon: '🧹',
      name: '整理周',
      description: '整理空间，也整理心情',
      category: 'life',
      tags: ['生活', '整理'],
      participants: 312,
      ideas: ['整理一个房间', '断舍离不需要的物品', '建立收纳系统'],
    },
    {
      id: 38,
      icon: '👗',
      name: '穿搭周',
      description: '探索个人风格，穿出自信',
      category: 'life',
      tags: ['生活', '时尚'],
      participants: 234,
      ideas: ['尝试不同风格', '整理衣柜', '学习色彩搭配'],
    },
    {
      id: 39,
      icon: '🎒',
      name: '旅行周',
      description: '用一周时间，深度探索一个地方',
      category: 'life',
      tags: ['生活', '旅行'],
      participants: 198,
      ideas: ['规划一次短途旅行', '探索城市角落', '记录旅行见闻'],
    },
    {
      id: 40,
      icon: '🏠',
      name: '极简周',
      description: '简化生活，回归本质',
      category: 'life',
      tags: ['生活', '极简'],
      participants: 267,
      ideas: ['减少不必要的消费', '简化日常流程', '专注重要事物'],
    },
    {
      id: 41,
      icon: '🎁',
      name: '手作周',
      description: '用双手创造独一无二的礼物',
      category: 'life',
      tags: ['生活', '手工'],
      participants: 189,
      ideas: ['制作一个手工礼物', '学习一种手工技能', '尝试不同材料'],
    },
    {
      id: 42,
      icon: '🎶',
      name: '歌单周',
      description: '整理心情歌单，用音乐陪伴每一天',
      category: 'life',
      tags: ['生活', '音乐'],
      participants: 223,
      ideas: ['创建主题歌单', '发现新音乐', '分享喜欢的歌曲'],
    },
    {
      id: 43,
      icon: '🤝',
      name: '交友周',
      description: '主动认识新朋友，拓展社交圈',
      category: 'social',
      tags: ['社交', '交友'],
      participants: 156,
      ideas: ['主动认识一个新朋友', '参加一次社交活动', '约朋友喝咖啡'],
    },
    {
      id: 44,
      icon: '❤️',
      name: '志愿者周',
      description: '用爱心回馈社会，感受奉献的快乐',
      category: 'social',
      tags: ['社交', '公益'],
      participants: 134,
      ideas: ['参加一次志愿活动', '帮助一个需要帮助的人', '了解公益组织'],
    },
    {
      id: 45,
      icon: '🎉',
      name: '聚会周',
      description: '组织一场聚会，与朋友共度美好时光',
      category: 'social',
      tags: ['社交', '聚会'],
      participants: 178,
      ideas: ['组织一次朋友聚会', '尝试新的聚会形式', '准备一个惊喜'],
    },
    {
      id: 46,
      icon: '💬',
      name: '沟通周',
      description: '提升沟通技巧，建立更好的人际关系',
      category: 'social',
      tags: ['社交', '沟通'],
      participants: 198,
      ideas: ['练习倾听技巧', '学习表达感受', '进行一次深度对话'],
    },
    {
      id: 47,
      icon: '🙏',
      name: '感恩周',
      description: '学会感恩，发现生活中的美好',
      category: 'social',
      tags: ['社交', '心理'],
      participants: 234,
      ideas: ['每天写感恩日记', '向一个人表达感谢', '发现生活中的小确幸'],
    },
    {
      id: 48,
      icon: '📱',
      name: '分享周',
      description: '分享知识与经验，帮助他人成长',
      category: 'social',
      tags: ['社交', '分享'],
      participants: 167,
      ideas: ['分享一个技能', '写一篇经验分享', '帮助一个朋友'],
    },
    {
      id: 49,
      icon: '👂',
      name: '倾听周',
      description: '学会倾听，理解他人的感受',
      category: 'social',
      tags: ['社交', '沟通'],
      participants: 145,
      ideas: ['专注倾听一个人', '不打断别人说话', '练习共情能力'],
    },
    {
      id: 50,
      icon: '👨‍👩‍👧‍',
      name: '陪伴周',
      description: '花时间陪伴家人，珍惜在一起的时光',
      category: 'social',
      tags: ['社交', '家庭'],
      participants: 212,
      ideas: ['陪家人吃一顿饭', '陪父母散步', '和家人一起看电影'],
    },
    {
      id: 51,
      icon: '🏘️',
      name: '社区周',
      description: '参与社区活动，认识身边的邻居',
      category: 'social',
      tags: ['社交', '社区'],
      participants: 98,
      ideas: ['参加一次社区活动', '认识一位邻居', '了解社区资源'],
    },
    {
      id: 52,
      icon: '🌟',
      name: '公益周',
      description: '关注社会问题，用行动传递温暖',
      category: 'social',
      tags: ['社交', '公益'],
      participants: 156,
      ideas: ['了解一个社会问题', '参与一次公益活动', '捐赠闲置物品'],
    },
  ]);

  // 我的主题周
  const myWeeks = ref([
    {
      id: 1,
      icon: '🎭',
      name: '音乐剧周',
      startDate: '2026-04-20',
      endDate: '2026-04-26',
      description: '走进剧场，感受音乐与戏剧的碰撞',
      status: 'ongoing',
      statusText: '进行中',
      progress: 60,
      tasks: ['看一场音乐剧', '学一首音乐剧歌曲', '了解音乐剧历史'],
      checkins: [
        { date: '2026-04-20', content: '今天去看了《歌剧魅影》', mood: 5 },
        { date: '2026-04-21', content: '听了《悲惨世界》原声带', mood: 4 },
        { date: '2026-04-22', content: '了解音乐剧历史', mood: 4 },
        { date: '2026-04-23', content: '学唱《Do You Hear the People Sing》', mood: 5 },
      ],
    },
    {
      id: 2,
      icon: '📖',
      name: '阅读周',
      startDate: '2026-04-13',
      endDate: '2026-04-19',
      description: '放下手机，沉浸在文字的世界里',
      status: 'completed',
      statusText: '已完成',
      progress: 100,
      achievements: ['读完《百年孤独》', '写读书笔记3篇'],
    },
    {
      id: 3,
      icon: '🧶',
      name: '钩织周',
      startDate: '2026-04-06',
      endDate: '2026-04-12',
      description: '用双手创造温暖，编织生活的美好',
      status: 'completed',
      statusText: '已完成',
      progress: 100,
      achievements: ['学会基础针法', '完成杯垫2个'],
    },
  ]);

  // 感悟墙
  const insights = ref([
    {
      id: 1,
      avatar: '👧',
      user: '小叶子',
      theme: '🎭 音乐剧周',
      date: '2026-04-20',
      content: '第一次走进剧场看音乐剧，被《歌剧魅影》深深震撼了！',
      tags: ['#音乐剧', '#新体验'],
      likes: 24,
      comments: 6,
    },
    {
      id: 2,
      avatar: '🌸',
      user: '樱花酱',
      theme: '🧶 钩织周',
      date: '2026-04-18',
      content: '这一周学会了钩织小雏菊杯垫！超有成就感。',
      tags: ['#钩织', '#手工'],
      likes: 31,
      comments: 8,
    },
  ]);

  // 统计
  const stats = ref({
    totalWeeks: 6,
    ongoingWeeks: 1,
    completedWeeks: 5,
    totalCheckins: 42,
    streakDays: 7,
  });

  // 按分类筛选主题
  const getThemesByCategory = (categoryId) => {
    if (categoryId === 'all') return themes.value;
    return themes.value.filter(t => t.category === categoryId);
  };

  // 添加新主题周
  const addMyWeek = (weekData) => {
    const newWeek = {
      id: Date.now(),
      ...weekData,
      status: 'upcoming',
      statusText: '即将开始',
      progress: 0,
      checkins: [],
    };
    myWeeks.value.unshift(newWeek);
    saveToLocalStorage();
    return newWeek;
  };

  // 打卡
  const checkin = (weekId, checkinData) => {
    const week = myWeeks.value.find(w => w.id === weekId);
    if (week) {
      week.checkins.push(checkinData);
      week.progress = Math.round((week.checkins.length / 7) * 100);
      if (week.progress >= 100) {
        week.status = 'completed';
        week.statusText = '已完成';
      }
      saveToLocalStorage();
    }
  };

  // 发布感悟
  const publishInsight = (insightData) => {
    const newInsight = {
      id: Date.now(),
      ...insightData,
      likes: 0,
      comments: 0,
    };
    insights.value.unshift(newInsight);
    saveToLocalStorage();
    return newInsight;
  };

  // 点赞
  const likeInsight = (insightId) => {
    const insight = insights.value.find(i => i.id === insightId);
    if (insight) {
      insight.likes++;
      saveToLocalStorage();
    }
  };

  // 本地存储
  const saveToLocalStorage = () => {
    const data = {
      myWeeks: myWeeks.value,
      insights: insights.value,
      stats: stats.value,
    };
    localStorage.setItem('themes_data', JSON.stringify(data));
  };

  const loadFromLocalStorage = () => {
    const saved = localStorage.getItem('themes_data');
    if (saved) {
      try {
        const data = JSON.parse(saved);
        myWeeks.value = data.myWeeks || myWeeks.value;
        insights.value = data.insights || insights.value;
        stats.value = data.stats || stats.value;
      } catch (e) {
        console.error('Failed to load themes data:', e);
      }
    }
  };

  return {
    categories,
    themes,
    myWeeks,
    insights,
    stats,
    getThemesByCategory,
    addMyWeek,
    checkin,
    publishInsight,
    likeInsight,
    saveToLocalStorage,
    loadFromLocalStorage,
  };
});
