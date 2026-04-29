<template>
  <div class="custom-week-page" @click="closeEmojiPicker">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">自定义主题周</h1>
        <p class="page-subtitle">创建属于你的主题周，为生活增添新体验</p>
      </div>
      
      <div class="create-card" @click.stop>
        <h2 class="card-title">创建你的主题周</h2>
        
        <div class="form-row">
          <div class="form-group">
            <label>主题名称</label>
            <input 
              v-model="formData.name" 
              type="text" 
              placeholder="例如：咖啡品鉴周、插花周..."
              class="form-input"
            >
          </div>

          <div class="form-group">
            <label>选择图标</label>
            <div class="icon-selector">
              <button class="icon-select-btn" @click="toggleEmojiPicker">
                <span class="selected-icon">{{ formData.icon }}</span>
                <span class="btn-text" v-if="!formData.icon">选择图标</span>
                <span class="dropdown-arrow">{{ showEmojiPicker ? '▲' : '▼' }}</span>
              </button>
              
              <div v-if="showEmojiPicker" class="emoji-picker-modal" @click.self="toggleEmojiPicker">
                <div class="emoji-picker">
                  <div class="emoji-search-container">
                    <input 
                      v-model="emojiSearchQuery"
                      type="text" 
                      placeholder="搜索 emoji，如：书、运动、音乐..."
                      class="emoji-search-input"
                    >
                  </div>
                  <div class="emoji-categories">
                    <div 
                      v-for="category in filteredEmojiCategories" 
                      :key="category.name"
                      class="emoji-category"
                    >
                      <h4 class="category-title">{{ category.name }}</h4>
                      <div class="emoji-grid">
                        <button 
                          v-for="emoji in category.emojis" 
                          :key="emoji"
                          class="emoji-option"
                          :class="{ active: formData.icon === emoji }"
                          @click="selectEmoji(emoji)"
                        >
                          {{ emoji }}
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label>主题描述</label>
          <textarea 
            v-model="formData.description" 
            placeholder="描述一下你的主题周想做什么..."
            class="form-textarea"
            rows="3"
          ></textarea>
        </div>

        <div class="form-group">
          <label>每日任务（最多 7 个）</label>
          <div class="tasks-list">
            <div v-for="(task, index) in formData.tasks" :key="index" class="task-item">
              <span class="task-number">Day {{ index + 1 }}</span>
              <input 
                v-model="formData.tasks[index]" 
                type="text" 
                :placeholder="'输入第' + (index + 1) + '天的任务'"
                class="task-input"
              >
              <button 
                v-if="formData.tasks.length > 1" 
                class="task-remove" 
                @click="removeTask(index)"
              >
                ×
              </button>
            </div>
          </div>
          <button 
            v-if="formData.tasks.length < 7" 
            class="add-task-btn" 
            @click="addTask"
          >
            + 添加任务
          </button>
        </div>

        <div class="form-group">
          <label>开始日期</label>
          <CustomDatePicker v-model="formData.startDate" />
        </div>

        <button class="submit-btn" @click="createWeek">
          开始我的主题周
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useThemesStore } from '../stores/themes';
import CustomDatePicker from '../components/CustomDatePicker.vue';

const router = useRouter();
const themesStore = useThemesStore();

const formData = ref({
  name: '',
  icon: '🌟',
  description: '',
  tasks: [''],
  startDate: new Date().toISOString().split('T')[0],
});

const showEmojiPicker = ref(false);
const emojiSearchQuery = ref('');

const emojiCategories = [
  {
    name: '学习',
    emojis: ['📚', '📖', '📝', '📓', '📐', '📗', '📘', '📙', '🎓', '🗒️', '📏', '🔭', '🔍', '🗄️', '⌨️', '🖊️', '✏️', '📊', '📈', '📉', '🧠', '📋', '🗂️', '📁', '🔍', '🔖', '📅', '📆', '🗓️', '📉', '📎', '📌'],
  },
  {
    name: '运动',
    emojis: ['🏃', '🏃‍♀️', '🏊', '🤽', '🚴', '🚴‍♀️', '🧘', '🤸', '💃', '🕺', '🤼', '🏋️‍♀️', '🤾', '🤾‍♀️', '🎾', '🏐', '🏀', '🏈', '🏉', '⚾', '🏓', '🏸', '🥅', '🥌', '🎿', '🏂', '🏹', '🛷', '🥋', '🤸', '⛹️', '🤺', '🏌️', '🚵', '🚣', '🧗', '🪂'],
  },
  {
    name: '美育',
    emojis: ['🎨', '🎵', '🎶', '🎼', '🎹', '🎸', '🎻', '🎺', '🎷', '🪗', '🥁', '🪕', '🎭', '🖼️', '🎤', '🎧', '🎥', '🎬', '📽️', '📸', '🎞️', '🧵', '🪡', '🪢', '🪞', '🖼️', '✏️', '🖍️', '🖊️', '🖋️'],
  },
  {
    name: '心理',
    emojis: ['🧘', '🧘‍♀️', '🧘‍♂️', '💆', '💆‍♀️', '🛀', '💤', '💝', '💖', '💗', '💓', '💞', '💕', '💘', '❤️', '❤️‍🔥', '🤍', '🤎', '💙', '💚', '💛', '🧡', '⭐', '🌟', '✨', '💫', '🦋', '🌸', '🍃', '😌', '🌈', '🕯️', '💭', '🌙'],
  },
  {
    name: '文化',
    emojis: ['🏛️', '🏰', '🏯', '⛩️', '🗽', '🗼', '🕌', '🕍', '📜', '🏺', '📿', '🎎', '🎏', '🎐', '🧧', '📖', '📕', '📗', '📘', '📙', '📔', '📓', '📒', '📝', '📰', '🎬', '🎞️', '🎭', '🎨', '🎪'],
  },
  {
    name: '技能',
    emojis: ['💻', '👨‍💻', '👩‍💻', '⚒️', '🛠️', '🔧', '🔨', '⛏️', '🔩', '⚙️', '🧰', '📱', '⌨️', '🖱️', '💾', '💿', '📀', '🔌', '🔋', '🔋', '🔬', '🧪', '📡', '🛰️', '🖥️', '🖨️', '📟', '📠', '📞', '☎️', '⚡', '🔥', '💡'],
  },
  {
    name: '旅行',
    emojis: ['🎒', '🗺️', '⛰️', '🏔️', '🏖️', '🏕️', '⛺', '🏜️', '🏞️', '🏟️', '🏝️', '🏘️', '🏡', '🏠', '🏚️', '🚂', '🚗', '🚙', '✈️', '🚁', '🛬', '🚀', '🛸', '🛳️', '🚤', '🛥️', '🚢', '🚲', '🛴', '🛹', '🚦', '🚥'],
  },
  {
    name: '手工',
    emojis: ['🧶', '🏺', '🧵', '🧣', '🪢', '🎨', '🖼️', '✂️', '📐', '📏', '📎', '🪚', '🧷', '📌', '🪜', '🪞', '✏️', '🖋️', '📝', '🎁', '🎀', '🧺', '🗑️', '🪣', '🧽', '🧴', '🧼', '🪥', '💅'],
  },
  {
    name: '社交',
    emojis: ['🤝', '❤️', '🎉', '🎊', '🙏', '👥', '👨‍👩‍👧', '👨‍👧‍👦', '👨‍👨‍👦', '👩‍👩‍👦', '👪', '👶', '🍼', '🎂', '🎁', '🎊', '🎈', '🤗', '💌', '🍻', '🥂', '🍾', '🌹', '🌷', '👫', '👬', '👭', '👪', '👶', '👼', '🎠', '🎊'],
  },
  {
    name: '生活',
    emojis: ['🍵', '☕', '🍳', '🍞', '🥐', '🥖', '🥨', '🧀', '🥚', '🍖', '🥩', '🥓', '🍔', '🍟', '🍕', '🪴', '🏠', '🏡', '🏘️', '🛋️', '🛏️', '🕯️', '🛁', '🚿', '🧻', '💡', '🪴', '🪥', '🧼', '💄', '🧴'],
  },
  {
    name: '健康',
    emojis: ['🥗', '🥬', '🥦', '🥕', '🌽', '🥔', '🍅', '🍆', '🍋', '🍌', '🍎', '🍊', '🍐', '🍑', '🍒', '🍓', '🥝', '🥥', '🥑', '🍇', '🥬', '🍄', '🌰', '🥜', '🧖', '🧖‍♀️', '💉', '💊', '🩺', '🏥', '🧘', '🥛', '🍯'],
  },
  {
    name: '语言',
    emojis: ['🇨🇳', '🇺🇸', '🇬🇧', '🇯🇵', '🇰🇷', '🇫🇷', '🇩🇪', '🇪🇸', '🇮🇹', '🇵🇹', '🇷🇺', '🇧🇷', '🇮🇳', '🇹🇭', '🇻🇳', '🇸🇦', '🇹🇷', '🇳🇱', '🇸🇪', '🇳🇴', '🇩🇰', '🇫🇮', '🇵🇱', '🇨🇿', '🇬🇷', '🇮🇱', '🇮🇩', '🇲🇾', '🇵🇭', '🇿🇦', '🇦🇺', '🇨'],
  },
  {
    name: '动物',
    emojis: ['🐱', '🐶', '🐕', '🦮', '🐩', '🐁', '🐹', '🐰', '🦊', '🦝', '🐻', '🐨', '🐯', '🦁', '🐮', '🐷', '🐸', '🐵', '🐔', '🙊', '🐒', '🐔', '🐧', '🦃', '🦜', '🦢', '🦩', '🦚', '🦉', '🦅', '🦆', '🦇', '🐺'],
  },
  {
    name: '植物',
    emojis: ['🌸', '💐', '🌺', '🌹', '🌷', '🌻', '🌱', '🌿', '🌾', '🌲', '🌴', '🌵', '🌳', '🌷', '🍂', '🍁', '🍃', '🌰', '🌹', '🌺', '🌼', '🌼', '🌱', '🌲', '🌳', '🌴', '🌵', '🌾', '🌿', '🍀', '🍁', '🍂'],
  },
  {
    name: '食物',
    emojis: ['☕', '🍵', '🧋', '🍶', '🍾', '🍷', '🍸', '🍹', '🥃', '🍻', '🥂', '🥤', '🧃', '🧉', '🍼', '🍕', '🍜', '🍲', '🍔', '🍟', '🌭', '🍿', '🍰', '🎂', '🍪', '🍩', '🍫', '🍬', '🍭', '🍮', '🧁', '🥐'],
  },
  {
    name: '科技',
    emojis: ['🤖', '👾', '👽', '🚀', '🛸', '🔭', '🔬', '🧪', '🖥️', '🖨️', '⌨️', '🖱️', '🖲️', '💻', '📱', '📲', '📹', '📷', '💿', '📀', '📼', '🔌', '🕯️', '🧯', '⚡', '🔥', '💡', '🔦', '🔋', '📡'],
  },
  {
    name: '天气',
    emojis: ['☀️', '🌤️', '⛅', '🌥️', '☁️', '🌦️', '🌧️', '⛈️', '🌩️', '🌨️', '❄️', '⛄', '🌡️', '💨', '🌪️', '🌫️', '🌈', '💧', '💦', '🌊', '🌊', '🌙', '🌕', '🌖', '🌗', '🌘', '🌑', '🌒', '🌓', '🌔', '⭐', '🌟'],
  },
  {
    name: '其他',
    emojis: ['⭐', '🌟', '✨', '💫', '💥', '💖', '💢', '🎪', '🎡', '🎢', '🎠', '🎡', '🚢', '🚗', '🚕', '🚙', '🛹', '🚦', '🚥', '🚧', '⚠️', '🚫', '🚭', '🚯', '🚰', '🚹', '🚺', '🚼', '♿', '🛗', '🎄', '🎅'],
  },
];

const emojiNames = {
  '📚': '书 书籍 学习', '📖': '书 阅读', '📝': '笔记 写字', '📓': '笔记本', '🎓': '毕业 学位',
  '📐': '尺子 数学', '🔬': '显微镜 科学', '🔭': '望远镜', '💻': '电脑 工作', '✏️': '铅笔',
  '📏': '尺子', '📊': '图表 数据', '📈': '增长 图表', '🧠': '大脑 思考', '📋': '剪贴板',
  '🗂️': '文件夹', '📁': '文件', '🔖': '书签', '📑': '标签', '🗒️': '便签',
  '📅': '日历', '📆': '日历', '🗓️': '日历', '📉': '下降 图表', '🗄️': '文件柜',
  '🏃': '跑步', '🏊': '游泳', '🚴': '骑车', '🏃‍♀️': '跑步 女', '🧘': '瑜伽',
  '💃': '跳舞', '🏋️': '健身', '🤾': '手球', '🎾': '网球', '🏐': '排球',
  '⚽': '足球', '🏀': '篮球', '🏈': '橄榄球', '🏉': '橄榄球', '🎱': '台球',
  '🏓': '乒乓球', '🏸': '羽毛球', '🥅': '球门', '⛳': '高尔夫', '🎿': '滑雪',
  '🏂': '单板滑雪', '🏹': '射箭', '🥊': '拳击', '🥋': '武术', '🤸': '体操',
  '⛹️': '篮球', '🤺': '击剑', '🏌️': '高尔夫', '🚵': '山地车', '🚣': '划船',
  '🎨': '艺术 画画', '🎵': '音乐 音符', '🎶': '音乐', '🎹': '钢琴', '🎸': '吉他',
  '🎻': '小提琴', '🎬': '电影', '🎪': '马戏团', '🖼️': '画框', '🎤': '麦克风',
  '🥁': '鼓', '🎼': '乐谱', '🎷': '萨克斯', '🎺': '小号', '🪗': '手风琴',
  '🎭': '戏剧', '🎞️': '胶片', '📷': '相机', '📸': '拍照', '🎥': '摄像机',
  '📹': '录像', '🧵': '线', '🖌️': '画笔',
  '🧘': '冥想', '💆': '按摩', '🛀': '洗澡', '💤': '睡觉', '💆‍♀️': '按摩',
  '💝': '爱心', '⭐': '星星', '💭': '思考', '💖': '爱心', '✨': '闪光',
  '🦋': '蝴蝶', '🌸': '花', '🍃': '叶子', '💕': '爱心', '😌': '放松',
  '🧘‍♀️': '瑜伽', '🌟': '星星', '💫': '星星', '💗': '爱心', '🌈': '彩虹',
  '🕯️': '蜡烛', '🧘‍♂️': '冥想', '💓': '心跳', '💞': '爱心', '💘': '爱心',
  '❤️‍': '爱心', '🤍': '白色爱心', '🤎': '棕色爱心', '💜': '紫色爱心',
  '🏛️': '博物馆', '📜': '卷轴', '🏺': '陶器', '🗿': '雕像', '🎎': '日本娃娃',
  '🎏': '鲤鱼旗', '🏯': '城堡', '🏰': '城堡', '⛩️': '神社', '🗽': '自由女神',
  '🗼': '东京塔', '🕌': '清真寺', '🎬': '电影', '📰': '报纸',
  '👨‍': '程序员', '‍💻': '程序员', '⚒️': '工具', '🛠️': '工具', '🔩': '螺丝',
  '⚙️': '齿轮', '🧰': '工具箱', '📱': '手机', '⌨️': '键盘', '🖱️': '鼠标',
  '💾': '磁盘', '💿': '光盘', '🔌': '插头', '🔋': '电池', '🔆': '亮度',
  '📡': '卫星', '🛰️': '卫星', '🖥️': '电脑', '🖨️': '打印机', '📟': '传呼机',
  '📠': '传真', '📞': '电话', '☎️': '电话', '🔧': '扳手', '🔨': '锤子',
  '⚡': '闪电', '🔥': '火',
  '🎒': '背包', '🗺️': '地图', '⛰️': '山', '🏖️': '海滩', '🏕️': '露营',
  '🚂': '火车', '🚗': '汽车', '✈️': '飞机', '🚙': '汽车', '🛳️': '轮船',
  '🚀': '火箭', '🛸': '飞碟', '⛺': '帐篷', '🏜️': '沙漠', '🏞️': '公园',
  '🏟️': '体育场', '🏝️': '岛屿', '🏘️': '房屋', '🏡': '房子', '🏠': '房子',
  '🏚️': '旧房子', '🏔️': '雪山', '🚁': '直升机', '🛶': '独木舟', '🚤': '快艇',
  '🛥️': '游艇', '🚢': '船',
  '🧶': '毛线', '🎁': '礼物', '🧵': '线', '🪡': '针', '✂️': '剪刀',
  '🧷': '别针', '🪢': '绳结', '🧹': '扫帚', '🧺': '篮子', '🧻': '纸巾',
  '🗑️': '垃圾桶', '🪣': '桶', '🧽': '海绵', '🧴': '瓶子', '🧼': '肥皂',
  '🪥': '牙刷',
  '🤝': '握手', '❤️': '爱心', '🎉': '庆祝', '💬': '聊天', '🙏': '感谢',
  '👥': '人群', '👨‍‍👦': '家庭', '👨‍👧‍👦': '家庭', '🎊': '庆祝', '🌟': '星星',
  '👋': '挥手', '🤗': '拥抱', '💌': '情书', '🍻': '干杯', '💐': '花束',
  '🌹': '玫瑰', '🌷': '郁金香', '👫': '情侣', '👬': '朋友', '👭': '朋友',
  '👪': '家庭', '👶': '婴儿', '👼': '天使', '🎂': '蛋糕',
  '☕': '咖啡', '🍳': '烹饪', '🥐': '面包', '🌱': '植物', '🏠': '房子',
  '🛋️': '沙发', '💅': '美甲', '🌿': '植物', '🕯️': '蜡烛', '🛁': '浴缸',
  '🛏️': '床', '🚪': '门', '🪴': '盆栽', '💡': '灯泡', '🍞': '面包',
  '🥖': '面包', '🥯': '面包', '🧀': '奶酪', '🥚': '鸡蛋', '🍖': '肉',
  '🥩': '牛排', '🥓': '培根', '🍔': '汉堡', '🍟': '薯条',
  '🥗': '沙拉', '🍵': '茶', '🏋️': '举重', '💊': '药丸', '💪': '肌肉',
  '🩺': '听诊器', '🍎': '苹果', '🍊': '橙子', '🥕': '胡萝卜', '🍋': '柠檬',
  '🍌': '香蕉', '🍉': '西瓜', '🍇': '葡萄', '🍓': '草莓', '🥥': '椰子',
  '🥑': '牛油果', '🥦': '西兰花', '🥬': '生菜', '🍏': '苹果', '🍐': '梨',
  '🥝': '猕猴桃', '🍑': '桃子', '🍒': '樱桃', '🫐': '蓝莓',
  '🇨🇳': '中国 中文', '🇺': '美国 英语', '🇬🇧': '英国 英语', '🇯🇵': '日本 日语', '🇰🇷': '韩国 韩语',
  '🇫🇷': '法国 法语', '🇩🇪': '德国 德语', '🇪': '西班牙 西班牙语', '🇮🇹': '意大利 意大利语', '🇵🇹': '葡萄牙 葡萄牙语',
  '🇷🇺': '俄罗斯 俄语', '🇧': '巴西 葡萄牙语', '🇮🇳': '印度 印地语', '🇹🇭': '泰国 泰语', '🇻🇳': '越南 越南语',
  '🇸🇦': '阿拉伯 阿拉伯语', '🇹🇷': '土耳其 土耳其语', '🇳🇱': '荷兰 荷兰语', '🇸🇪': '瑞典 瑞典语', '🇳🇴': '挪威 挪威语',
  '🇩🇰': '丹麦 丹麦语', '🇫🇮': '芬兰 芬兰语', '🇵🇱': '波兰 波兰语', '🇨🇿': '捷克 捷克语', '🇬🇷': '希腊 希腊语',
  '🇮🇱': '以色列 希伯来语', '🇮🇩': '印尼 印尼语', '🇲🇾': '马来西亚 马来语', '🇵🇭': '菲律宾 菲律宾语', '🇿🇦': '南非',
  '🐱': '猫', '🐶': '狗', '🦊': '狐狸', '🐻': '熊', '🐼': '熊猫',
  '🐨': '考拉', '🐯': '老虎', '🦁': '狮子', '🐷': '猪', '🐸': '青蛙',
  '🦓': '斑马', '🦒': '长颈鹿', '🦍': '大猩猩', '🦧': '猩猩', '🦮': '导盲犬',
  '🐕': '狗', '🐩': '贵宾犬', '🐈': '猫', '🐓': '公鸡', '🦃': '火鸡',
  '🦜': '鹦鹉', '🦢': '天鹅', '🦩': '火烈鸟', '🦚': '孔雀', '🦉': '猫头鹰',
  '🦅': '鹰', '🦆': '鸭子', '🦇': '蝙蝠', '🐺': '狼',
  '🌺': '花', '🌼': '花', '🌷': '郁金香', '🌹': '玫瑰', '🍂': '落叶',
  '🌳': '树', '🌴': '棕榈树', '🌵': '仙人掌', '🌲': '树', '🌾': '稻子',
  '🌿': '草', '🍀': '四叶草', '🍁': '枫叶', '🍄': '蘑菇', '🌻': '向日葵',
  '🌱': '幼苗', '🌶️': '辣椒', '🌽': '玉米', '🥔': '土豆', '🍠': '红薯',
  '🌰': '栗子',
  '🍵': '茶', '🧋': '奶茶', '🍕': '披萨', '🍜': '面条', '🍩': '甜甜圈',
  '🍪': '饼干', '🎂': '蛋糕', '🍰': '蛋糕', '🧁': '纸杯蛋糕', '🥧': '派',
  '🍫': '巧克力', '🍬': '糖果', '🍭': '棒棒糖', '🍮': '布丁', '🍯': '蜂蜜',
  '🥛': '牛奶', '🍼': '奶瓶', '🧃': '饮料', '🧉': '茶', '🍶': '酒',
  '🍾': '香槟', '🍷': '红酒', '🍸': '鸡尾酒', '🍹': '果汁',
  '🤖': '机器人', '👽': '外星人', '👾': '游戏', '🛰️': '卫星', '🚀': '火箭',
  '🛸': '飞碟', '🔭': '望远镜', '📲': '手机', '📹': '录像',
  '💿': '光盘', '📀': '光盘',
  '☀️': '太阳', '⛅': '多云', '☁️': '云', '🌤️': '晴转多云', '⛈️': '雷雨',
  '🌧️': '下雨', '🌨️': '下雪', '🌩️': '闪电', '🌪️': '龙卷风', '🌫️': '雾',
  '🌈': '彩虹', '❄️': '雪花', '⛄': '雪人', '🌡️': '温度计', '💧': '水滴',
  '💦': '汗水', '🌊': '海浪', '🌀': '台风', '🌙': '月亮', '🌕': '满月',
  '🌖': '月亮', '🌗': '月亮', '🌘': '月亮', '🌑': '新月',
  '💎': '钻石', '🎢': '过山车', '🎠': '旋转木马', '⚓': '锚', '🚲': '自行车',
  '🛴': '滑板车', '🛹': '滑板', '🚦': '交通灯', '🚥': '交通灯', '🚧': '施工',
  '⚠️': '警告', '🚫': '禁止', '🚭': '禁止吸烟', '🚯': '禁止乱扔', '🚰': '饮用水',
  '🚹': '男厕', '🚺': '女厕', '🚼': '婴儿', '♿': '无障碍', '🛗': '电梯',
};

const getEmojiName = (emoji) => {
  return emojiNames[emoji] || '';
};

const filteredEmojiCategories = computed(() => {
  if (!emojiSearchQuery.value.trim()) {
    return emojiCategories;
  }
  
  const query = emojiSearchQuery.value.toLowerCase().trim();
  
  return emojiCategories.map(category => {
    return {
      ...category,
      emojis: category.emojis.filter(emoji => {
        const emojiName = getEmojiName(emoji).toLowerCase();
        return emojiName.includes(query) || emoji.includes(query);
      })
    };
  }).filter(category => category.emojis.length > 0);
});

const toggleEmojiPicker = () => {
  showEmojiPicker.value = !showEmojiPicker.value;
};

const selectEmoji = (emoji) => {
  formData.value.icon = emoji;
  showEmojiPicker.value = false;
};

const closeEmojiPicker = () => {
  showEmojiPicker.value = false;
};

const addTask = () => {
  if (formData.value.tasks.length < 7) {
    formData.value.tasks.push('');
  }
};

const removeTask = (index) => {
  formData.value.tasks.splice(index, 1);
};

const createWeek = () => {
  if (!formData.value.name) {
    alert('请输入主题名称');
    return;
  }

  const newWeek = {
    icon: formData.value.icon,
    name: formData.value.name,
    description: formData.value.description,
    startDate: formData.value.startDate,
    tasks: formData.value.tasks.filter(t => t.trim()),
  };

  themesStore.addMyWeek(newWeek);
  router.push('/checkin');
};
</script>

<style scoped>
.custom-week-page {
  min-height: 100vh;
  padding: 80px 0 60px;
  background: white;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
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

.create-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 32px;
  box-shadow: var(--shadow-medium);
  max-width: 800px;
  margin: 0 auto 32px;
}

.card-title {
  font-size: 22px;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.form-group {
  margin-bottom: 20px;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
}

.form-group label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.form-input {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid var(--border-color);
  border-radius: 10px;
  font-size: 14px;
  background: white;
  color: var(--text-primary);
  transition: border-color 0.2s;
  height: 48px;
}

.form-input:focus {
  outline: none;
  border-color: var(--primary-color);
}

.form-textarea {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid var(--border-color);
  border-radius: 10px;
  font-size: 14px;
  background: white;
  color: var(--text-primary);
  resize: vertical;
  font-family: inherit;
  height: 48px;
}

.form-textarea:focus {
  outline: none;
  border-color: var(--primary-color);
}

.icon-selector {
  position: relative;
}

.icon-select-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 12px 16px;
  background: white;
  border: 1px solid var(--border-color);
  border-radius: 10px;
  cursor: pointer;
  font-size: 14px;
  color: var(--text-primary);
  transition: all 0.2s;
  width: 100%;
  height: 48px;
}

.icon-select-btn:hover {
  border-color: var(--primary-color);
  background: var(--background-hover);
}

.selected-icon {
  font-size: 20px;
}

.btn-text {
  color: var(--text-secondary);
}

.dropdown-arrow {
  font-size: 12px;
  color: var(--text-muted);
}

.emoji-picker-modal {
  position: absolute;
  top: 100%;
  left: 0;
  margin-top: 8px;
  z-index: 1000;
  background: white;
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.12);
  max-width: 500px;
  max-height: 500px;
  overflow: hidden;
}

.emoji-picker {
  display: flex;
  flex-direction: column;
  max-height: 500px;
}

.emoji-search-container {
  padding: 16px;
  border-bottom: 1px solid var(--border-color);
  background: var(--background-secondary);
}

.emoji-search-input {
  width: 100%;
  padding: 10px 14px;
  border: 1px solid var(--border-color);
  border-radius: 10px;
  font-size: 14px;
  background: white;
  color: var(--text-primary);
  transition: border-color 0.2s;
}

.emoji-search-input:focus {
  outline: none;
  border-color: var(--primary-color);
}

.emoji-categories {
  overflow-y: auto;
  padding: 16px;
}

.emoji-category {
  margin-bottom: 20px;
}

.category-title {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 1px solid var(--border-color);
}

.emoji-grid {
  display: grid;
  grid-template-columns: repeat(8, 1fr);
  gap: 6px;
}

.emoji-option {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  background: var(--background-secondary);
  cursor: pointer;
  font-size: 18px;
  border: 2px solid transparent;
  transition: all 0.2s;
}

.emoji-option:hover {
  background: var(--background-hover);
  transform: scale(1.1);
}

.emoji-option.active {
  background: var(--primary-light);
  border-color: var(--primary-color);
}

.tasks-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.task-item {
  display: flex;
  align-items: center;
  gap: 12px;
}

.task-number {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-secondary);
  min-width: 60px;
}

.task-input {
  flex: 1;
  padding: 12px 16px;
  border: 1px solid var(--border-color);
  border-radius: 10px;
  font-size: 14px;
  background: white;
  color: var(--text-primary);
  transition: border-color 0.2s;
  height: 48px;
}

.task-input:focus {
  outline: none;
  border-color: var(--primary-color);
}

.task-remove {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  border: none;
  background: transparent;
  color: var(--text-muted);
  font-size: 20px;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.task-remove:hover {
  background: #ffebee;
  color: #e53935;
}

.add-task-btn {
  margin-top: 8px;
  padding: 8px 16px;
  background: var(--background-secondary);
  border: 1px dashed var(--border-color);
  border-radius: 10px;
  color: var(--text-secondary);
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.add-task-btn:hover {
  border-color: var(--primary-color);
  color: var(--primary-color);
}

.submit-btn {
  width: 100%;
  padding: 14px;
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  margin-top: 24px;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 16px rgba(46, 125, 50, 0.2);
}

@media (max-width: 768px) {
  .page-title {
    font-size: 28px;
  }
  
  .create-card {
    padding: 24px;
  }
  
  .form-row {
    grid-template-columns: 1fr;
  }
}
</style>
