import { defineStore } from 'pinia';

// 模拟书籍数据 - 丰富的书籍种类
const mockBooks = [
  // 文学小说类
  {
    id: '1',
    title: '百年孤独',
    author: '加西亚·马尔克斯',
    cover: 'https://img3.doubanio.com/view/subject/s/public/s6384948.jpg',
    category: 'literature',
    categoryName: '文学小说',
    publisher: '南海出版公司',
    publishDate: '2011-06-01',
    isbn: '9787544253994',
    description: '《百年孤独》是魔幻现实主义文学的代表作，描写了布恩迪亚家族七代人的传奇故事，以及加勒比海沿岸小镇马孔多的百年兴衰，反映了拉丁美洲一个世纪以来风云变幻的历史。作品融入神话传说、民间故事、宗教典故等神秘因素，巧妙地糅合了现实与虚幻，展现出一个瑰丽的想象世界，成为20世纪最重要的经典文学巨著之一。',
    available: true,
    reviews: [
      { id: '1', name: '文学爱好者', rating: 5, content: '魔幻现实主义的巅峰之作，布恩迪亚家族的故事让人着迷。', date: '2024-01-15' }
    ],
    color: '#FF6B6B'
  },
  {
    id: '2',
    title: '活着',
    author: '余华',
    cover: 'https://img1.doubanio.com/view/subject/s/public/s1074491.jpg',
    category: 'literature',
    categoryName: '文学小说',
    publisher: '作家出版社',
    publishDate: '2012-08-01',
    isbn: '9787506365431',
    description: '《活着》是余华最具代表性的作品，讲述了农村人福贵悲惨的人生遭遇。福贵本是个阔少爷，可他嗜赌如命，终于赌光了家业，一贫如洗。他的父亲被他活活气死，母亲则在穷困中患了重病，福贵前去求药，却在途中被国民党抓去当壮丁。后被解放军俘虏，回到家中，才知道母亲早已去世，妻子家珍含辛茹苦地养大两个儿女。此后更加悲惨的命运一次又一次降临到福贵身上，他的妻子、儿女和孙子相继死去，最后只剩福贵和一头老牛相依为命，但老人依旧活着，仿佛比往日更加洒脱与坚强。',
    available: true,
    reviews: [
      { id: '6', name: '文学青年', rating: 5, content: '平凡人的不平凡故事，让人感受到生命的力量。', date: '2024-03-20' }
    ],
    color: '#FFD166'
  },
  {
    id: '3',
    title: '解忧杂货店',
    author: '东野圭吾',
    cover: 'https://img9.doubanio.com/view/subject/s/public/s28397432.jpg',
    category: 'literature',
    categoryName: '文学小说',
    publisher: '南海出版公司',
    publishDate: '2014-05-01',
    isbn: '9787544270878',
    description: '僻静的街道旁有一家特别的杂货店，只要写下烦恼投进店前门卷帘门的投信口，第二天就会在店后的牛奶箱里得到回答：因男友失业而面临经济困境的女子，因被欺凌而不敢去学校的少年，因迷茫而想要放弃奥运梦想的女孩……他们将困惑写成信投进杂货店，奇妙的事情随即不断发生。',
    available: true,
    color: '#06D6A0'
  },
  
  // 科幻类
  {
    id: '4',
    title: '三体',
    author: '刘慈欣',
    cover: 'https://img2.doubanio.com/view/subject/s/public/s27273802.jpg',
    category: 'science_fiction',
    categoryName: '科幻小说',
    publisher: '重庆出版社',
    publishDate: '2008-01-01',
    isbn: '9787536692930',
    description: '文化大革命如火如荼地进行，天文学家叶文洁在运动中遭受迫害，被送到青海支援建设。她在荒无人烟的雷达站接收到了一段来自宇宙深处的信息。这段信息改变了人类的命运。地球文明向宇宙发出的第一声啼鸣，以太阳为中心，以光速向宇宙深处飞驰……',
    available: true,
    color: '#118AB2'
  },
  {
    id: '5',
    title: '银河帝国：基地',
    author: '艾萨克·阿西莫夫',
    cover: 'https://img1.doubanio.com/view/subject/s/public/s29990448.jpg',
    category: 'science_fiction',
    categoryName: '科幻小说',
    publisher: '江苏凤凰文艺出版社',
    publishDate: '2012-03-01',
    isbn: '9787539952106',
    description: '人类蜗居在银河系的一个小角落——太阳系，在围绕太阳旋转的第三颗行星上，生活了十多万年之久。人类在这个小小的行星（他们称之为「地球」）上，建立了两百多个不同的行政区域（他们称之为「国家」），直到地球上诞生了第一个会思考的机器人。',
    available: false,
    borrower: 'user1',
    borrowDate: '2024-11-01',
    dueDate: '2024-11-30',
    color: '#073B4C'
  },
  
  // 历史类
  {
    id: '6',
    title: '人类简史',
    author: '尤瓦尔·赫拉利',
    cover: 'https://img1.doubanio.com/view/subject/s/public/s29262582.jpg',
    category: 'history',
    categoryName: '历史',
    publisher: '中信出版社',
    publishDate: '2014-11-01',
    isbn: '9787508647357',
    description: '十万年前，地球上至少有六种不同的人。但今日，世界舞台为什么只剩下了我们自己？从认知革命、农业革命到科学革命，我们真的了解自己吗？我们过得更加快乐吗？我们知道如何让自己更加快乐吗？',
    available: false,
    borrower: 'user1',
    borrowDate: '2024-11-01',
    dueDate: '2024-11-30',
    color: '#EF476F'
  },
  {
    id: '7',
    title: '明朝那些事儿',
    author: '当年明月',
    cover: 'https://img3.doubanio.com/view/subject/s/public/s29688432.jpg',
    category: 'history',
    categoryName: '历史',
    publisher: '浙江人民出版社',
    publishDate: '2017-09-01',
    isbn: '9787213083629',
    description: '《明朝那些事儿》以史料为基础，以年代和具体人物为主线，并加入了小说的笔法，语言幽默风趣。对明朝十六帝和其他王公权贵和小人物的命运进行全景展示，尤其对官场政治、战争、帝王心术着墨最多，并加入对当时政治经济制度、人伦道德的演义。',
    available: true,
    color: '#845EC2'
  },
  
  // 心理学类
  {
    id: '8',
    title: '被讨厌的勇气',
    author: '岸见一郎',
    cover: 'https://img2.doubanio.com/view/subject/s/public/s29847577.jpg',
    category: 'psychology',
    categoryName: '心理学',
    publisher: '机械工业出版社',
    publishDate: '2015-03-01',
    isbn: '9787111495486',
    description: '「被讨厌的勇气」并不是要去吸引被讨厌的负向能量，而是，如果这是我生命想绽放出最美的光彩，那么，即使有被讨厌的可能，我都要用自己的双手双脚往那里走去。因为拥有了被讨厌的勇气，于是有了真正幸福的可能。',
    available: true,
    color: '#00B8A9'
  },
  {
    id: '9',
    title: '乌合之众：大众心理研究',
    author: '古斯塔夫·勒庞',
    cover: 'https://img9.doubanio.com/view/subject/s/public/s27191943.jpg',
    category: 'psychology',
    categoryName: '心理学',
    publisher: '中央编译出版社',
    publishDate: '2015-05-01',
    isbn: '9787511728562',
    description: '《乌合之众》是解析群体心理的经典名著，虽然出版于1895年，但其对大众心理的洞察依然深刻。勒庞认为，个体一旦进入群体中，其个性便湮没了，群体的思想占据统治地位，而群体的行为表现为无异议、情绪化和低智商。',
    available: true,
    color: '#F9C80E'
  },
  
  // 经济学类
  {
    id: '10',
    title: '国富论',
    author: '亚当·斯密',
    cover: 'https://img3.doubanio.com/view/subject/s/public/s1746868.jpg',
    category: 'economics',
    categoryName: '经济学',
    publisher: '中央编译出版社',
    publishDate: '2011-05-01',
    isbn: '9787511708262',
    description: '《国富论》被誉为西方经济学的「圣经」，是现代政治经济学研究的起点。亚当·斯密在书中阐述了劳动价值论、分工理论、市场机制等经济学核心概念，对后世经济学发展产生了深远影响。',
    available: true,
    color: '#F72585'
  },
  {
    id: '11',
    title: '牛奶可乐经济学',
    author: '罗伯特·弗兰克',
    cover: 'https://img2.doubanio.com/view/subject/s/public/s28391898.jpg',
    category: 'economics',
    categoryName: '经济学',
    publisher: '中国人民大学出版社',
    publishDate: '2017-07-01',
    isbn: '9787300243871',
    description: '为什么牛奶装在方盒子里卖，可乐却装在圆瓶子里卖？为什么女模特比男模特收入高？为什么很多酒吧喝水要钱，却又提供免费花生？用经济学的眼光看生活，你会发现很多看似不合理的现象背后都有其合理的经济学解释。',
    available: true,
    color: '#4CC9F0'
  },
  
  // 哲学类
  {
    id: '12',
    title: '苏菲的世界',
    author: '乔斯坦·贾德',
    cover: 'https://img1.doubanio.com/view/subject/s/public/s28328894.jpg',
    category: 'philosophy',
    categoryName: '哲学',
    publisher: '作家出版社',
    publishDate: '2017-11-01',
    isbn: '9787506397799',
    description: '《苏菲的世界》是一部关于西方哲学史的小说，通过一名哲学导师向一个叫苏菲的女孩传授哲学知识的过程，揭示了西方哲学史发展的历程。小说形式让哲学变得生动有趣，适合对哲学感兴趣的初学者阅读。',
    available: true,
    color: '#7209B7'
  },
  {
    id: '13',
    title: '理想国',
    author: '柏拉图',
    cover: 'https://img9.doubanio.com/view/subject/s/public/s29613759.jpg',
    category: 'philosophy',
    categoryName: '哲学',
    publisher: '商务印书馆',
    publishDate: '2016-08-01',
    isbn: '9787100124751',
    description: '《理想国》是柏拉图的代表作，也是西方哲学史上最重要的经典著作之一。在书中，柏拉图通过苏格拉底与他人的对话，探讨了正义、国家、教育等重大问题，提出了理想城邦的构想。',
    available: true,
    color: '#F15BB5'
  },
  
  // 艺术类
  {
    id: '14',
    title: '艺术的故事',
    author: '贡布里希',
    cover: 'https://img2.doubanio.com/view/subject/s/public/s29826273.jpg',
    category: 'art',
    categoryName: '艺术',
    publisher: '广西美术出版社',
    publishDate: '2015-11-01',
    isbn: '9787549414240',
    description: '《艺术的故事》是一部关于艺术发展的经典著作，贡布里希用通俗易懂的语言，讲述了从远古时代到现代的艺术发展历程，分析了各个时期的艺术特点、代表作品和艺术家。这本书被公认为是艺术史入门的最佳读物。',
    available: true,
    color: '#9B5DE5'
  },
  
  // 科技类
  {
    id: '15',
    title: '时间简史',
    author: '史蒂芬·霍金',
    cover: 'https://img2.doubanio.com/view/subject/s/public/s28705376.jpg',
    category: 'science',
    categoryName: '科学技术',
    publisher: '湖南科学技术出版社',
    publishDate: '2010-04-01',
    isbn: '9787535764538',
    description: '《时间简史》是霍金最著名的科普著作，它从研究黑洞出发，探索了宇宙的起源和归宿。霍金用通俗易懂的语言，介绍了现代物理学的基本概念，如时空观、相对论、量子力学等，让读者能够理解复杂的宇宙奥秘。',
    available: true,
    color: '#00BBF9'
  },
  
  // 教育类
  {
    id: '16',
    title: '爱弥儿',
    author: '卢梭',
    cover: 'https://img3.doubanio.com/view/subject/s/public/s27216427.jpg',
    category: 'education',
    categoryName: '教育',
    publisher: '商务印书馆',
    publishDate: '2011-06-01',
    isbn: '9787100077484',
    description: '《爱弥儿》是卢梭的教育哲学著作，系统地阐述了他的自然主义教育理论。卢梭主张教育要顺应儿童的天性，让儿童在自然状态下成长，反对传统教育对儿童的束缚和压抑。这本书对现代教育思想产生了深远影响。',
    available: true,
    color: '#00F5D4'
  },
  // 文学小说类 - 继续添加
  { id: '17', title: '红楼梦', author: '曹雪芹', cover: 'https://img1.doubanio.com/view/subject/s/public/s1074491.jpg', category: 'literature', categoryName: '文学小说', publisher: '人民文学出版社', publishDate: '1996-12-01', isbn: '9787020002207', description: '中国古典四大名著之一，描绘了贾、史、王、薛四大家族的兴衰，以及贾宝玉、林黛玉、薛宝钗等人的爱情悲剧。', available: true, color: '#FF6B6B' },
  { id: '18', title: '西游记', author: '吴承恩', cover: 'https://img1.doubanio.com/view/subject/s/public/s1074491.jpg', category: 'literature', categoryName: '文学小说', publisher: '人民文学出版社', publishDate: '1990-10-01', isbn: '9787020002214', description: '中国古典四大名著之一，讲述了唐僧师徒四人西天取经的故事。', available: true, color: '#4ECDC4' },
  { id: '19', title: '水浒传', author: '施耐庵', cover: 'https://img1.doubanio.com/view/subject/s/public/s1074491.jpg', category: 'literature', categoryName: '文学小说', publisher: '人民文学出版社', publishDate: '1997-01-01', isbn: '9787020002221', description: '中国古典四大名著之一，描写了108位好汉在梁山泊聚义的故事。', available: true, color: '#45B7D1' },
  { id: '20', title: '三国演义', author: '罗贯中', cover: 'https://img1.doubanio.com/view/subject/s/public/s1074491.jpg', category: 'literature', categoryName: '文学小说', publisher: '人民文学出版社', publishDate: '1998-05-01', isbn: '9787020002238', description: '中国古典四大名著之一，描写了三国时期魏、蜀、吴三国鼎立的历史故事。', available: true, color: '#F9CA24' },
  { id: '21', title: '围城', author: '钱钟书', cover: 'https://img1.doubanio.com/view/subject/s/public/s1074491.jpg', category: 'literature', categoryName: '文学小说', publisher: '人民文学出版社', publishDate: '1991-02-01', isbn: '9787020002245', description: '现代文学经典，讲述了方鸿渐从留学归国到教书的人生经历。', available: true, color: '#EB4D4B' },
  { id: '22', title: '骆驼祥子', author: '老舍', cover: 'https://img1.doubanio.com/view/subject/s/public/s1074491.jpg', category: 'literature', categoryName: '文学小说', publisher: '人民文学出版社', publishDate: '1999-02-01', isbn: '9787020002252', description: '讲述了人力车夫祥子的人生悲剧。', available: true, color: '#686de0' },
  { id: '23', title: '阿Q正传', author: '鲁迅', cover: 'https://img1.doubanio.com/view/subject/s/public/s1074491.jpg', category: 'literature', categoryName: '文学小说', publisher: '人民文学出版社', publishDate: '1981-01-01', isbn: '9787020002269', description: '现代文学经典，塑造了阿Q这一典型形象。', available: true, color: '#4834d4' },
  
  // 科幻小说类 - 继续添加
  { id: '24', title: '三体2：黑暗森林', author: '刘慈欣', cover: 'https://img2.doubanio.com/view/subject/s/public/s27273802.jpg', category: 'science_fiction', categoryName: '科幻小说', publisher: '重庆出版社', publishDate: '2008-05-01', isbn: '9787536692947', description: '三体系列第二部，讲述了人类文明与三体文明的对决。', available: true, color: '#118AB2' },
  { id: '25', title: '三体3：死神永生', author: '刘慈欣', cover: 'https://img2.doubanio.com/view/subject/s/public/s27273802.jpg', category: 'science_fiction', categoryName: '科幻小说', publisher: '重庆出版社', publishDate: '2010-11-01', isbn: '9787536692954', description: '三体系列第三部，描绘了宇宙文明的终极图景。', available: true, color: '#073B4C' },
  { id: '26', title: '星际穿越', author: '克里斯托弗·诺兰', cover: 'https://img2.doubanio.com/view/subject/s/public/s27273802.jpg', category: 'science_fiction', categoryName: '科幻小说', publisher: '湖南科学技术出版社', publishDate: '2014-11-01', isbn: '9787535783498', description: '根据同名电影改编，讲述了人类穿越虫洞寻找新家园的故事。', available: true, color: '#06D6A0' },
  { id: '27', title: '银河帝国2：基地与帝国', author: '艾萨克·阿西莫夫', cover: 'https://img1.doubanio.com/view/subject/s/public/s29990448.jpg', category: 'science_fiction', categoryName: '科幻小说', publisher: '江苏凤凰文艺出版社', publishDate: '2012-03-01', isbn: '9787539952113', description: '银河帝国系列第二部，讲述了基地与帝国的对抗。', available: true, color: '#FFD166' },
  { id: '28', title: '银河帝国3：第二基地', author: '艾萨克·阿西莫夫', cover: 'https://img1.doubanio.com/view/subject/s/public/s29990448.jpg', category: 'science_fiction', categoryName: '科幻小说', publisher: '江苏凤凰文艺出版社', publishDate: '2012-03-01', isbn: '9787539952120', description: '银河帝国系列第三部，揭示了第二基地的秘密。', available: true, color: '#FF6B6B' },
  { id: '29', title: '沙丘', author: '弗兰克·赫伯特', cover: 'https://img1.doubanio.com/view/subject/s/public/s29990448.jpg', category: 'science_fiction', categoryName: '科幻小说', publisher: '江苏凤凰文艺出版社', publishDate: '2017-01-01', isbn: '9787539952137', description: '科幻经典，讲述了沙漠星球阿拉基斯上的权力斗争。', available: true, color: '#06D6A0' },
  { id: '30', title: '机器人系列：我，机器人', author: '艾萨克·阿西莫夫', cover: 'https://img1.doubanio.com/view/subject/s/public/s29990448.jpg', category: 'science_fiction', categoryName: '科幻小说', publisher: '江苏凤凰文艺出版社', publishDate: '2015-01-01', isbn: '9787539952144', description: '机器人系列经典作品，提出了机器人三定律。', available: true, color: '#118AB2' },
  { id: '31', title: '2001太空漫游', author: '阿瑟·克拉克', cover: 'https://img1.doubanio.com/view/subject/s/public/s29990448.jpg', category: 'science_fiction', categoryName: '科幻小说', publisher: '上海文艺出版社', publishDate: '2019-01-01', isbn: '9787532168031', description: '科幻经典，探索人类与宇宙的关系。', available: true, color: '#073B4C' },
  
  // 历史类 - 继续添加
  { id: '32', title: '中国通史', author: '吕思勉', cover: 'https://img1.doubanio.com/view/subject/s/public/s29262582.jpg', category: 'history', categoryName: '历史', publisher: '中国友谊出版公司', publishDate: '2019-01-01', isbn: '9787505745215', description: '一部全面的中国通史著作。', available: true, color: '#EF476F' },
  { id: '33', title: '明朝那些事儿2', author: '当年明月', cover: 'https://img3.doubanio.com/view/subject/s/public/s29688432.jpg', category: 'history', categoryName: '历史', publisher: '浙江人民出版社', publishDate: '2017-09-01', isbn: '9787213083636', description: '明朝那些事儿系列第二部。', available: true, color: '#845EC2' },
  { id: '34', title: '明朝那些事儿3', author: '当年明月', cover: 'https://img3.doubanio.com/view/subject/s/public/s29688432.jpg', category: 'history', categoryName: '历史', publisher: '浙江人民出版社', publishDate: '2017-09-01', isbn: '9787213083643', description: '明朝那些事儿系列第三部。', available: true, color: '#D65DB1' },
  { id: '35', title: '明朝那些事儿4', author: '当年明月', cover: 'https://img3.doubanio.com/view/subject/s/public/s29688432.jpg', category: 'history', categoryName: '历史', publisher: '浙江人民出版社', publishDate: '2017-09-01', isbn: '9787213083650', description: '明朝那些事儿系列第四部。', available: true, color: '#FF6F91' },
  { id: '36', title: '明朝那些事儿5', author: '当年明月', cover: 'https://img3.doubanio.com/view/subject/s/public/s29688432.jpg', category: 'history', categoryName: '历史', publisher: '浙江人民出版社', publishDate: '2017-09-01', isbn: '9787213083667', description: '明朝那些事儿系列第五部。', available: true, color: '#FF9671' },
  { id: '37', title: '明朝那些事儿6', author: '当年明月', cover: 'https://img3.doubanio.com/view/subject/s/public/s29688432.jpg', category: 'history', categoryName: '历史', publisher: '浙江人民出版社', publishDate: '2017-09-01', isbn: '9787213083674', description: '明朝那些事儿系列第六部。', available: true, color: '#FFC75F' },
  { id: '38', title: '明朝那些事儿7', author: '当年明月', cover: 'https://img3.doubanio.com/view/subject/s/public/s29688432.jpg', category: 'history', categoryName: '历史', publisher: '浙江人民出版社', publishDate: '2017-09-01', isbn: '9787213083681', description: '明朝那些事儿系列第七部。', available: true, color: '#F9F871' },
  { id: '39', title: '全球通史', author: '斯塔夫里阿诺斯', cover: 'https://img1.doubanio.com/view/subject/s/public/s29262582.jpg', category: 'history', categoryName: '历史', publisher: '北京大学出版社', publishDate: '2012-02-01', isbn: '9787301196521', description: '一部讲述全球历史的经典著作。', available: true, color: '#0089BA' },
  
  // 心理学类 - 继续添加
  { id: '40', title: '心理学与生活', author: '理查德·格里格', cover: 'https://img2.doubanio.com/view/subject/s/public/s29847577.jpg', category: 'psychology', categoryName: '心理学', publisher: '人民邮电出版社', publishDate: '2014-11-01', isbn: '9787115371725', description: '心理学入门经典教材。', available: true, color: '#00B8A9' },
  { id: '41', title: '影响力', author: '罗伯特·西奥迪尼', cover: 'https://img9.doubanio.com/view/subject/s/public/s27191943.jpg', category: 'psychology', categoryName: '心理学', publisher: '中国人民大学出版社', publishDate: '2016-09-01', isbn: '9787300230627', description: '关于说服力的经典著作。', available: true, color: '#F9C80E' },
  { id: '42', title: '思考，快与慢', author: '丹尼尔·卡尼曼', cover: 'https://img2.doubanio.com/view/subject/s/public/s29847577.jpg', category: 'psychology', categoryName: '心理学', publisher: '中信出版社', publishDate: '2012-07-01', isbn: '9787508633558', description: '诺贝尔经济学奖得主的经典作品，探讨人类思维的两个系统。', available: true, color: '#845EC2' },
  { id: '43', title: '梦的解析', author: '西格蒙德·弗洛伊德', cover: 'https://img2.doubanio.com/view/subject/s/public/s29847577.jpg', category: 'psychology', categoryName: '心理学', publisher: '国际文化出版公司', publishDate: '2011-04-01', isbn: '9787512501978', description: '精神分析学的经典著作。', available: true, color: '#D65DB1' },
  { id: '44', title: '自卑与超越', author: '阿尔弗雷德·阿德勒', cover: 'https://img2.doubanio.com/view/subject/s/public/s29847577.jpg', category: 'psychology', categoryName: '心理学', publisher: '作家出版社', publishDate: '2017-01-01', isbn: '9787506392870', description: '个体心理学的经典著作。', available: true, color: '#FF6F91' },
  { id: '45', title: '动机与人格', author: '亚伯拉罕·马斯洛', cover: 'https://img2.doubanio.com/view/subject/s/public/s29847577.jpg', category: 'psychology', categoryName: '心理学', publisher: '中国人民大学出版社', publishDate: '2012-04-01', isbn: '9787300151324', description: '人本主义心理学的经典著作，提出了需求层次理论。', available: true, color: '#FF9671' },
  { id: '46', title: '认知心理学', author: '罗伯特·索尔索', cover: 'https://img2.doubanio.com/view/subject/s/public/s29847577.jpg', category: 'psychology', categoryName: '心理学', publisher: '教育科学出版社', publishDate: '2009-01-01', isbn: '9787504146866', description: '认知心理学的经典教材。', available: true, color: '#FFC75F' },
  { id: '47', title: '发展心理学', author: '劳拉·伯克', cover: 'https://img2.doubanio.com/view/subject/s/public/s29847577.jpg', category: 'psychology', categoryName: '心理学', publisher: '中国轻工业出版社', publishDate: '2014-01-01', isbn: '9787501996065', description: '发展心理学的经典教材。', available: true, color: '#F9F871' },
  
  // 经济学类 - 继续添加
  { id: '48', title: '经济学原理', author: 'N·格里高利·曼昆', cover: 'https://img3.doubanio.com/view/subject/s/public/s1746868.jpg', category: 'economics', categoryName: '经济学', publisher: '北京大学出版社', publishDate: '2015-05-01', isbn: '9787301256909', description: '经济学入门经典教材。', available: true, color: '#F72585' },
  { id: '49', title: '资本论', author: '卡尔·马克思', cover: 'https://img3.doubanio.com/view/subject/s/public/s1746868.jpg', category: 'economics', categoryName: '经济学', publisher: '人民出版社', publishDate: '2004-10-01', isbn: '9787010043170', description: '马克思主义政治经济学的经典著作。', available: true, color: '#4CC9F0' },
  { id: '50', title: '就业、利息和货币通论', author: '约翰·梅纳德·凯恩斯', cover: 'https://img3.doubanio.com/view/subject/s/public/s1746868.jpg', category: 'economics', categoryName: '经济学', publisher: '商务印书馆', publishDate: '2009-12-01', isbn: '9787100066637', description: '凯恩斯主义经济学的奠基之作。', available: true, color: '#4361EE' },
  { id: '51', title: '经济学思想史', author: '斯坦利·L·布鲁', cover: 'https://img3.doubanio.com/view/subject/s/public/s1746868.jpg', category: 'economics', categoryName: '经济学', publisher: '北京大学出版社', publishDate: '2018-07-01', isbn: '9787301295463', description: '讲述经济学思想发展的历史。', available: true, color: '#3A0CA3' },
  { id: '52', title: '微观经济学', author: '保罗·萨缪尔森', cover: 'https://img3.doubanio.com/view/subject/s/public/s1746868.jpg', category: 'economics', categoryName: '经济学', publisher: '人民邮电出版社', publishDate: '2012-01-01', isbn: '9787115267300', description: '微观经济学经典教材。', available: true, color: '#7209B7' },
  { id: '53', title: '宏观经济学', author: '保罗·萨缪尔森', cover: 'https://img3.doubanio.com/view/subject/s/public/s1746868.jpg', category: 'economics', categoryName: '经济学', publisher: '人民邮电出版社', publishDate: '2012-01-01', isbn: '9787115267317', description: '宏观经济学经典教材。', available: true, color: '#F72585' },
  { id: '54', title: '货币金融学', author: '弗雷德里克·S·米什金', cover: 'https://img3.doubanio.com/view/subject/s/public/s1746868.jpg', category: 'economics', categoryName: '经济学', publisher: '中国人民大学出版社', publishDate: '2016-08-01', isbn: '9787300230016', description: '货币金融学经典教材。', available: true, color: '#4CC9F0' },
  { id: '55', title: '博弈论与经济行为', author: '约翰·冯·诺依曼', cover: 'https://img3.doubanio.com/view/subject/s/public/s1746868.jpg', category: 'economics', categoryName: '经济学', publisher: '生活·读书·新知三联书店', publishDate: '2004-01-01', isbn: '9787108020252', description: '博弈论的奠基之作。', available: true, color: '#4361EE' },
  
  // 哲学类 - 继续添加
  { id: '56', title: '哲学的故事', author: '威尔·杜兰特', cover: 'https://img1.doubanio.com/view/subject/s/public/s28328894.jpg', category: 'philosophy', categoryName: '哲学', publisher: '新星出版社', publishDate: '2013-01-01', isbn: '9787513309293', description: '用故事形式讲述哲学发展的历史。', available: true, color: '#7209B7' },
  { id: '57', title: '西方哲学史', author: '罗素', cover: 'https://img9.doubanio.com/view/subject/s/public/s29613759.jpg', category: 'philosophy', categoryName: '哲学', publisher: '商务印书馆', publishDate: '2015-08-01', isbn: '9787100114518', description: '罗素的经典哲学史著作。', available: true, color: '#F15BB5' },
  { id: '58', title: '纯粹理性批判', author: '伊曼努尔·康德', cover: 'https://img9.doubanio.com/view/subject/s/public/s29613759.jpg', category: 'philosophy', categoryName: '哲学', publisher: '人民出版社', publishDate: '2004-10-01', isbn: '9787010040585', description: '康德的经典哲学著作。', available: true, color: '#00F5D4' },
  { id: '59', title: '存在与时间', author: '马丁·海德格尔', cover: 'https://img9.doubanio.com/view/subject/s/public/s29613759.jpg', category: 'philosophy', categoryName: '哲学', publisher: '生活·读书·新知三联书店', publishDate: '2006-04-01', isbn: '9787108024415', description: '存在主义哲学的经典著作。', available: true, color: '#00BBF9' },
  { id: '60', title: '查拉图斯特拉如是说', author: '弗里德里希·尼采', cover: 'https://img9.doubanio.com/view/subject/s/public/s29613759.jpg', category: 'philosophy', categoryName: '哲学', publisher: '商务印书馆', publishDate: '2010-10-01', isbn: '9787100070240', description: '尼采的经典哲学著作。', available: true, color: '#FF9E00' },
  { id: '61', title: '论人类不平等的起源和基础', author: '让-雅克·卢梭', cover: 'https://img9.doubanio.com/view/subject/s/public/s29613759.jpg', category: 'philosophy', categoryName: '哲学', publisher: '商务印书馆', publishDate: '1962-01-01', isbn: '9787100011774', description: '卢梭的经典哲学著作。', available: true, color: '#FF0054' },
  { id: '62', title: '形而上学', author: '亚里士多德', cover: 'https://img9.doubanio.com/view/subject/s/public/s29613759.jpg', category: 'philosophy', categoryName: '哲学', publisher: '商务印书馆', publishDate: '1997-02-01', isbn: '9787100011002', description: '亚里士多德的经典哲学著作。', available: true, color: '#9B5DE5' },
  { id: '63', title: '忏悔录', author: '奥古斯丁', cover: 'https://img9.doubanio.com/view/subject/s/public/s29613759.jpg', category: 'philosophy', categoryName: '哲学', publisher: '商务印书馆', publishDate: '1963-07-01', isbn: '9787100011217', description: '奥古斯丁的经典哲学著作。', available: true, color: '#F15BB5' },
  
  // 艺术类 - 继续添加
  { id: '64', title: '西方美术史', author: '贡布里希', cover: 'https://img2.doubanio.com/view/subject/s/public/s29826273.jpg', category: 'art', categoryName: '艺术', publisher: '广西美术出版社', publishDate: '2015-11-01', isbn: '9787549414257', description: '西方美术史的经典著作。', available: true, color: '#9B5DE5' },
  { id: '65', title: '中国美术史', author: '王朝闻', cover: 'https://img2.doubanio.com/view/subject/s/public/s29826273.jpg', category: 'art', categoryName: '艺术', publisher: '人民美术出版社', publishDate: '2014-01-01', isbn: '9787102065758', description: '中国美术史的经典著作。', available: true, color: '#F15BB5' },
  { id: '66', title: '设计心理学', author: '唐纳德·诺曼', cover: 'https://img2.doubanio.com/view/subject/s/public/s29826273.jpg', category: 'art', categoryName: '艺术', publisher: '中信出版社', publishDate: '2015-05-01', isbn: '9787508650179', description: '关于设计的心理学著作。', available: true, color: '#00BBF9' },
  { id: '67', title: '色彩理论', author: '约瑟夫·阿尔伯斯', cover: 'https://img2.doubanio.com/view/subject/s/public/s29826273.jpg', category: 'art', categoryName: '艺术', publisher: '重庆大学出版社', publishDate: '2019-01-01', isbn: '9787568913411', description: '色彩理论的经典著作。', available: true, color: '#00F5D4' },
  { id: '68', title: '艺术与错觉', author: '贡布里希', cover: 'https://img2.doubanio.com/view/subject/s/public/s29826273.jpg', category: 'art', categoryName: '艺术', publisher: '广西美术出版社', publishDate: '2016-01-01', isbn: '9787549416596', description: '贡布里希的艺术理论著作。', available: true, color: '#FF9E00' },
  { id: '69', title: '建筑的故事', author: '乔纳森·格兰西', cover: 'https://img2.doubanio.com/view/subject/s/public/s29826273.jpg', category: 'art', categoryName: '艺术', publisher: '新星出版社', publishDate: '2018-11-01', isbn: '9787513332991', description: '建筑史的通俗读物。', available: true, color: '#FF0054' },
  { id: '70', title: '摄影的艺术', author: '布鲁斯·巴恩博', cover: 'https://img2.doubanio.com/view/subject/s/public/s29826273.jpg', category: 'art', categoryName: '艺术', publisher: '人民邮电出版社', publishDate: '2013-01-01', isbn: '9787115298174', description: '摄影艺术的经典著作。', available: true, color: '#9B5DE5' },
  { id: '71', title: '音乐的故事', author: '保罗·贝克', cover: 'https://img2.doubanio.com/view/subject/s/public/s29826273.jpg', category: 'art', categoryName: '艺术', publisher: '人民音乐出版社', publishDate: '2015-01-01', isbn: '9787103047819', description: '音乐史的通俗读物。', available: true, color: '#F15BB5' },
  
  // 科学技术类 - 继续添加
  { id: '72', title: '果壳中的宇宙', author: '史蒂芬·霍金', cover: 'https://img2.doubanio.com/view/subject/s/public/s28705376.jpg', category: 'science', categoryName: '科学技术', publisher: '湖南科学技术出版社', publishDate: '2002-02-01', isbn: '9787535732309', description: '霍金的另一部科普著作。', available: true, color: '#00BBF9' },
  { id: '73', title: '万物理论', author: '史蒂芬·霍金', cover: 'https://img2.doubanio.com/view/subject/s/public/s28705376.jpg', category: 'science', categoryName: '科学技术', publisher: '湖南科学技术出版社', publishDate: '2014-01-01', isbn: '9787535775378', description: '霍金的科普著作。', available: true, color: '#00F5D4' },
  { id: '74', title: '物理世界奇遇记', author: '乔治·伽莫夫', cover: 'https://img2.doubanio.com/view/subject/s/public/s28705376.jpg', category: 'science', categoryName: '科学技术', publisher: '科学出版社', publishDate: '2008-01-01', isbn: '9787030202249', description: '物理学的通俗读物。', available: true, color: '#FF9E00' },
  { id: '75', title: '化学元素周期表', author: '普里莫·莱维', cover: 'https://img2.doubanio.com/view/subject/s/public/s28705376.jpg', category: 'science', categoryName: '科学技术', publisher: '三联书店', publishDate: '2010-01-01', isbn: '9787108033633', description: '关于化学元素的文学作品。', available: true, color: '#FF0054' },
  { id: '76', title: '基因传', author: '悉达多·穆克吉', cover: 'https://img2.doubanio.com/view/subject/s/public/s28705376.jpg', category: 'science', categoryName: '科学技术', publisher: '中信出版社', publishDate: '2018-01-01', isbn: '9787508682529', description: '基因科学的历史与现状。', available: true, color: '#9B5DE5' },
  { id: '77', title: '物种起源', author: '查尔斯·达尔文', cover: 'https://img2.doubanio.com/view/subject/s/public/s28705376.jpg', category: 'science', categoryName: '科学技术', publisher: '商务印书馆', publishDate: '1997-10-01', isbn: '9787100011576', description: '进化论的奠基之作。', available: true, color: '#F15BB5' },
  { id: '78', title: '相对论通俗解释', author: '爱因斯坦', cover: 'https://img2.doubanio.com/view/subject/s/public/s28705376.jpg', category: 'science', categoryName: '科学技术', publisher: '北京大学出版社', publishDate: '2006-01-01', isbn: '9787301095621', description: '爱因斯坦对相对论的通俗解释。', available: true, color: '#00BBF9' },
  { id: '79', title: '量子力学原理', author: '狄拉克', cover: 'https://img2.doubanio.com/view/subject/s/public/s28705376.jpg', category: 'science', categoryName: '科学技术', publisher: '科学出版社', publishDate: '2008-01-01', isbn: '9787030202447', description: '量子力学的经典教材。', available: true, color: '#00F5D4' },
  
  // 教育类 - 继续添加
  { id: '80', title: '教育心理学', author: '罗伯特·斯莱文', cover: 'https://img3.doubanio.com/view/subject/s/public/s27216427.jpg', category: 'education', categoryName: '教育', publisher: '人民邮电出版社', publishDate: '2016-07-01', isbn: '9787115425916', description: '教育心理学的经典教材。', available: true, color: '#00F5D4' },
  { id: '81', title: '教学原理', author: '理查德·E·梅耶', cover: 'https://img3.doubanio.com/view/subject/s/public/s27216427.jpg', category: 'education', categoryName: '教育', publisher: '华东师范大学出版社', publishDate: '2017-01-01', isbn: '9787567551283', description: '教学理论的经典著作。', available: true, color: '#00BBF9' },
  { id: '82', title: '学习的本质', author: '安德烈·焦尔当', cover: 'https://img3.doubanio.com/view/subject/s/public/s27216427.jpg', category: 'education', categoryName: '教育', publisher: '华东师范大学出版社', publishDate: '2015-01-01', isbn: '9787567525288', description: '关于学习本质的研究著作。', available: true, color: '#FF9E00' },
  { id: '83', title: '民主主义与教育', author: '约翰·杜威', cover: 'https://img3.doubanio.com/view/subject/s/public/s27216427.jpg', category: 'education', categoryName: '教育', publisher: '人民教育出版社', publishDate: '2001-05-01', isbn: '9787107106839', description: '实用主义教育理论的经典著作。', available: true, color: '#FF0054' },
  { id: '84', title: '大教学论', author: '夸美纽斯', cover: 'https://img3.doubanio.com/view/subject/s/public/s27216427.jpg', category: 'education', categoryName: '教育', publisher: '教育科学出版社', publishDate: '2014-01-01', isbn: '9787504181819', description: '近代教育理论的奠基之作。', available: true, color: '#9B5DE5' },
  { id: '85', title: '课程与教学论', author: '张华', cover: 'https://img3.doubanio.com/view/subject/s/public/s27216427.jpg', category: 'education', categoryName: '教育', publisher: '上海教育出版社', publishDate: '2014-01-01', isbn: '9787544449918', description: '课程与教学理论的研究著作。', available: true, color: '#F15BB5' },
  { id: '86', title: '儿童发展', author: '劳拉·E·伯克', cover: 'https://img3.doubanio.com/view/subject/s/public/s27216427.jpg', category: 'education', categoryName: '教育', publisher: '中国轻工业出版社', publishDate: '2014-01-01', isbn: '9787501995778', description: '儿童发展心理学的经典教材。', available: true, color: '#00F5D4' },
  { id: '87', title: '教育研究方法', author: '袁振国', cover: 'https://img3.doubanio.com/view/subject/s/public/s27216427.jpg', category: 'education', categoryName: '教育', publisher: '高等教育出版社', publishDate: '2010-07-01', isbn: '9787040293152', description: '教育研究方法的教材。', available: true, color: '#00BBF9' }
];

export const useBooksStore = defineStore('books', {
  state: () => ({
    books: [],
    borrowedBooks: [],
    loading: false
  }),

  getters: {
    // 获取所有书籍
    allBooks: (state) => state.books,
    
    // 获取可借阅的书籍
    availableBooks: (state) => state.books.filter(book => book.available),
    
    // 根据ID获取书籍
    getBookById: (state) => (id) => {
      return state.books.find(book => book.id === id);
    },
    
    // 获取用户借阅的书籍
    getBorrowedBooksByUser: (state) => (userId) => {
      return state.books.filter(book => 
        !book.available && book.borrower === userId
      );
    }
  },

  actions: {
    // 获取书籍列表
    fetchBooks() {
      this.loading = true;
      // 模拟API请求
      setTimeout(() => {
        this.books = [...mockBooks];
        this.loading = false;
      }, 500);
    },
    
    // 借阅书籍
    borrowBook(bookId, userId) {
      const book = this.getBookById(bookId);
      if (book && book.available) {
        book.available = false;
        book.borrower = userId;
        book.borrowDate = new Date().toISOString().split('T')[0];
        // 设置默认借阅期限为30天
        const dueDate = new Date();
        dueDate.setDate(dueDate.getDate() + 30);
        book.dueDate = dueDate.toISOString().split('T')[0];
        
        // 保存到localStorage
        this.saveToLocalStorage();
        return true;
      }
      return false;
    },
    
    // 归还书籍
    returnBook(bookId, userId) {
      const book = this.getBookById(bookId);
      if (book && !book.available && book.borrower === userId) {
        book.available = true;
        delete book.borrower;
        delete book.borrowDate;
        delete book.dueDate;
        
        // 保存到localStorage
        this.saveToLocalStorage();
        return true;
      }
      return false;
    },
    
    // 续借书籍
    renewBook(bookId, userId) {
      const book = this.getBookById(bookId);
      if (book && !book.available && book.borrower === userId) {
        // 续借30天
        const dueDate = new Date(book.dueDate);
        dueDate.setDate(dueDate.getDate() + 30);
        book.dueDate = dueDate.toISOString().split('T')[0];
        
        // 保存到localStorage
        this.saveToLocalStorage();
        return true;
      }
      return false;
    },
    
    // 保存数据到localStorage
    saveToLocalStorage() {
      localStorage.setItem('books_data', JSON.stringify(this.books));
    },
    
    // 从localStorage加载数据
    loadFromLocalStorage() {
      const saved = localStorage.getItem('books_data');
      if (saved) {
        this.books = JSON.parse(saved);
      } else {
        this.fetchBooks();
      }
    },
    
    // 添加评论
    addReview(bookId, userId, userName, rating, content) {
      const book = this.getBookById(bookId);
      if (book) {
        // 如果书籍没有reviews数组，先创建
        if (!book.reviews) {
          book.reviews = [];
        }
        
        // 创建新评论
        const newReview = {
          id: Date.now().toString(), // 使用时间戳作为唯一ID
          name: userName,
          rating: rating,
          content: content,
          date: new Date().toISOString().split('T')[0]
        };
        
        // 添加到评论数组
        book.reviews.push(newReview);
        
        // 保存到localStorage
        this.saveToLocalStorage();
        return true;
      }
      return false;
    }
  }
});