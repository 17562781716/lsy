import { defineStore } from 'pinia'

export const useJuejinStore = defineStore('juejin', {
  state: () => ({
    // 当前选中的分类
    currentCategory: '综合',
    // 当前选中的标签页
    currentTab: '推荐',
    // 文章数据
    articles: [
      {
        id: 1,
        title: 'Cursor你要毁了我吗',
        description: '最近在使用Cursor进行开发，发现它的AI功能确实很强大，但是有时候也会出现一些让人哭笑不得的情况...',
        author: '程序员肖鹏',
        views: '39k',
        likes: '270',
        tags: ['Cursor', '前端', 'Vue.js'],
        thumbnail: 'https://via.placeholder.com/80x80/FF6B6B/FFFFFF?text=🍽️'
      },
      {
        id: 2,
        title: '7000块帮朋友做了2个小程序加一个后台管理系统,值不值?',
        description: '朋友找我帮忙开发两个小程序和一个后台管理系统，总共给了7000块。这个价格在市场上算是什么水平？',
        author: '王锦涛',
        views: '25k',
        likes: '156',
        tags: ['小程序', '后台管理'],
        thumbnail: 'https://via.placeholder.com/80x80/4ECDC4/FFFFFF?text=🐂'
      },
      {
        id: 3,
        title: '小米汽车这薪资是认真的吗?',
        description: '看到小米汽车发布的招聘信息，薪资待遇确实很诱人，但是要求也很高。大家觉得这个薪资水平合理吗？',
        author: '汽车工程师',
        views: '18k',
        likes: '89',
        tags: ['小米', '汽车', '薪资'],
        thumbnail: 'https://via.placeholder.com/80x80/45B7D1/FFFFFF?text=🚗'
      },
      {
        id: 4,
        title: '分享10个吊炸天的油猴脚本,2025最新!',
        description: '整理了10个非常实用的油猴脚本，包括网页增强、广告屏蔽、功能扩展等，让你的浏览器更加强大！',
        author: '脚本达人',
        views: '32k',
        likes: '234',
        tags: ['油猴脚本', '浏览器'],
        thumbnail: 'https://via.placeholder.com/80x80/96CEB4/FFFFFF?text=🧠'
      },
      {
        id: 5,
        title: '中石化将开源组件二次封装申请专利,这个操作你怎么看?',
        description: '中石化对开源组件进行二次封装后申请专利的行为引发了广泛讨论，这种做法是否符合开源精神？',
        author: '开源爱好者',
        views: '15k',
        likes: '67',
        tags: ['开源', '专利', '中石化'],
        thumbnail: 'https://via.placeholder.com/80x80/FFEAA7/FFFFFF?text=📄'
      },
      {
        id: 6,
        title: '【附源码】牺牲两天摸鱼时间,我做了款大屏',
        description: '利用周末时间开发了一个数据大屏项目，包含实时数据展示、图表可视化等功能，源码已开源！',
        author: '前端工程师',
        views: '28k',
        likes: '189',
        tags: ['大屏', '数据可视化', '源码'],
        thumbnail: 'https://via.placeholder.com/80x80/2C3E50/FFFFFF?text=📊'
      },
      {
        id: 7,
        title: '35岁大龄码农,用cursor独立做副业经历分享',
        description: '35岁的程序员分享如何利用Cursor等AI工具进行独立开发，实现副业收入的经历和心得...',
        author: '大龄码农',
        views: '22k',
        likes: '145',
        tags: ['副业', 'AI工具', '经验分享'],
        thumbnail: 'https://via.placeholder.com/80x80/E17055/FFFFFF?text=💼'
      }
    ],
    // 分类列表
    categories: [
      { name: '关注', icon: '👥' },
      { name: '综合', icon: '📊', active: true },
      { name: '后端', icon: '🔧' },
      { name: '前端', icon: '🎨' },
      { name: 'Android', icon: '📱' },
      { name: 'iOS', icon: '🍎' },
      { name: '人工智能', icon: '🤖' },
      { name: '开发工具', icon: '🛠️' },
      { name: '代码人生', icon: '💻' },
      { name: '阅读', icon: '📚' },
      { name: '排行榜', icon: '🏆' }
    ],
    // 文章榜
    rankingArticles: [
      'Elx开源升级:XMarkdown...',
      '基于Prompt 的DevOps 与终...',
      'Nginx 正式拥抱现代 JavaScript',
      'Vue Vapor真的没有diff算法...',
      '我差点放弃移动开发(以及是...'
    ],
    // 作者榜
    authors: [
      {
        name: '李中锐',
        title: '世界首富，比埃隆马思克更牛的存在',
        avatar: ''
      },
      {
        name: '黄雨建',
        title: '光头强大叔 @跨境',
        avatar: ''
      },
      {
        name: '谷子轩',
        title: '十五年经验三角洲职业选手',
        avatar: ''
      },
      {
        name: '许衍鹏',
        title: '十年部长无人知，一朝教官天下晓',
        avatar: ''
      },
      
      
    ]
  }),
  
  actions: {
    // 切换分类
    setCategory(categoryName) {
      this.currentCategory = categoryName
      // 更新分类的激活状态
      this.categories.forEach(cat => {
        cat.active = cat.name === categoryName
      })
    },
    
    // 切换标签页
    setTab(tabName) {
      this.currentTab = tabName
    },
    
    // 刷新文章榜
    refreshRanking() {
      // 这里可以添加刷新逻辑
      console.log('刷新文章榜')
    },
    
    // 关注作者
    followAuthor(authorName) {
      console.log(`关注作者: ${authorName}`)
    },
    
    // 签到
    checkIn() {
      console.log('用户签到')
    }
  }
}) 