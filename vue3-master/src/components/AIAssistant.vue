<template>
  <div class="ai-assistant">
    <button class="ai-toggle-btn" @click="toggleAssistant" aria-label="AI助手">
      <span class="ai-icon">📚</span>
    </button>
    
    <div v-if="isOpen" class="ai-dialog">
      <div class="ai-header">
        <h3>AI助手</h3>
        <button class="ai-close-btn" @click="toggleAssistant" aria-label="关闭AI助手">&times;</button>
      </div>
      
      <div class="ai-messages">
        <div class="ai-message" v-for="message in messages" :key="message.id">
          <div class="message-content" :class="message.role">
            <span class="message-icon">{{ message.role === 'user' ? '👤' : '📚' }}</span>
            <span class="message-text">{{ message.text }}</span>
          </div>
        </div>
      </div>
      
      <div class="ai-input-area">
        <input 
          type="text" 
          v-model="userInput" 
          @keypress.enter="sendMessage" 
          placeholder="输入您的问题..." 
          class="ai-input"
          :disabled="isLoading"
        >
        <button @click="sendMessage" class="ai-send-btn" :disabled="isLoading || !userInput.trim()">
          <span v-if="isLoading">⏳</span>
          <span v-else>发送</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const isOpen = ref(false);
const messages = ref([
  {
    id: 1,
    role: 'ai',
    text: '您好！我是云边书吧的AI助手，有什么可以帮助您的吗？'
  }
]);
const userInput = ref('');
const isLoading = ref(false);

// 切换AI助手显示状态
const toggleAssistant = () => {
  isOpen.value = !isOpen.value;
};

// 发送消息
const sendMessage = async () => {
  if (!userInput.value.trim() || isLoading.value) return;
  
  const userMessage = userInput.value.trim();
  
  // 添加用户消息
  messages.value.push({
    id: Date.now(),
    role: 'user',
    text: userMessage
  });
  
  // 清空输入框
  userInput.value = '';
  
  // 显示加载状态
  isLoading.value = true;
  
  // 模拟AI回复
  setTimeout(() => {
    const aiResponse = generateResponse(userMessage);
    
    messages.value.push({
      id: Date.now() + 1,
      role: 'ai',
      text: aiResponse
    });
    
    // 关闭加载状态
    isLoading.value = false;
    
    // 滚动到底部
    const messagesContainer = document.querySelector('.ai-messages');
    if (messagesContainer) {
      messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }
  }, 1000);
};

// 生成AI回复
const generateResponse = (userMessage) => {
  const message = userMessage.toLowerCase();
  
  // 更精准的AI回复逻辑
  // 咖啡相关
  if (message.includes('咖啡') && (message.includes('种类') || message.includes('菜单') || message.includes('有什么'))) {
    return '我们提供多种咖啡选择，包括美式、拿铁、卡布奇诺、摩卡等。您可以在咖啡时光页面查看详细菜单和价格。';
  } else if (message.includes('咖啡') && message.includes('价格')) {
    return '咖啡价格从28元到45元不等，会员可享受9折优惠。您可以在咖啡时光页面查看具体价格。';
  }
  // 书籍相关
  else if (message.includes('书籍') && (message.includes('有什么') || message.includes('种类') || message.includes('分类'))) {
    return '我们有丰富的书籍资源，涵盖文学小说、科幻小说、历史、心理学、经济学、哲学、艺术、科学技术、教育等多个类别。您可以在书籍页面浏览。';
  } else if (message.includes('书籍') && (message.includes('借阅') || message.includes('怎么借'))) {
    return '您需要先登录账号，然后在书籍详情页面点击借阅按钮即可。借阅期限为30天，可续借一次。';
  } else if (message.includes('书籍') && message.includes('归还')) {
    return '您可以在个人中心的"我的书籍"页面查看借阅的书籍，并点击归还按钮进行归还。';
  } else if (message.includes('新书上架') || message.includes('新书')) {
    return '我们会定期更新新书，您可以在首页或书籍页面查看最新上架的书籍。';
  }
  // 会员相关
  else if (message.includes('会员') && (message.includes('怎么办理') || message.includes('如何加入'))) {
    return '您可以在会员福利页面点击"立即加入"按钮办理会员，会员年费为199元。';
  } else if (message.includes('会员') && message.includes('福利')) {
    return '会员可享受书籍借阅折扣、咖啡会员价、优先参加沙龙活动等福利。详细信息请查看会员福利页面。';
  }
  // 沙龙相关
  else if (message.includes('沙龙') && (message.includes('活动') || message.includes('有什么'))) {
    return '我们定期举办读书沙龙活动，您可以在沙龙交流页面查看最新活动信息和报名方式。';
  } else if (message.includes('沙龙') && message.includes('怎么参加')) {
    return '您需要先登录账号，然后在沙龙交流页面点击报名按钮即可参加。';
  }
  // 登录相关
  else if (message.includes('登录') && (message.includes('怎么') || message.includes('如何'))) {
    return '您可以点击页面右上角的登录按钮进入登录页面，输入您的用户名和密码即可登录。';
  } else if (message.includes('注册') || message.includes('账号')) {
    return '您可以在登录页面点击"立即注册"按钮创建新账号，填写必要信息即可。';
  }
  // 购物车相关
  else if (message.includes('购物车')) {
    return '您可以点击页面右上角的购物车图标查看您的购物车商品，并进行结算。';
  }
  // 个人中心相关
  else if (message.includes('个人中心') || message.includes('我的')) {
    return '您可以点击页面右上角的用户名，在下拉菜单中选择"个人中心"查看您的个人信息和借阅记录。';
  }
  // 导航相关
  else if (message.includes('首页')) {
    return '您可以点击页面左上角的"云边书吧"logo返回首页。';
  } else if (message.includes('关于')) {
    return '您可以点击导航栏中的"关于"链接了解云边书吧的更多信息。';
  }
  // 帮助相关
  else if (message.includes('帮助') || message.includes('咨询')) {
    return '我可以帮助您了解云边书吧的各种服务，包括书籍借阅、咖啡购买、会员福利等。您有什么具体问题吗？';
  }
  // 其他
  else if (message.includes('谢谢') || message.includes('感谢')) {
    return '不客气！如果您有任何其他问题，随时可以问我。';
  } else {
    return '感谢您的咨询！针对您的问题，我需要更具体的信息才能给出准确回答。请您详细描述您的问题，好吗？';
  }
};
</script>

<style scoped>
.ai-assistant {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
}

.ai-toggle-btn {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background-color: var(--color-primary);
  border: none;
  font-size: 2rem;
  cursor: pointer;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
}

.ai-toggle-btn:hover {
  transform: scale(1.1);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.3);
}

.ai-dialog {
  position: absolute;
  bottom: 80px;
  right: 0;
  width: 350px;
  max-width: 90vw;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  animation: slideUp 0.3s ease;
}

.ai-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid var(--color-border);
  background-color: var(--color-background-light);
}

.ai-header h3 {
  margin: 0;
  font-size: 1.1rem;
  color: var(--color-text-primary);
}

.ai-close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: var(--color-text-secondary);
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.ai-close-btn:hover {
  background-color: var(--color-border);
}

.ai-messages {
  padding: 1rem;
  max-height: 300px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.ai-message {
  display: flex;
  justify-content: flex-start;
}

.ai-message .user {
  justify-content: flex-end;
}

.message-content {
  display: flex;
  align-items: flex-start;
  gap: 0.5rem;
  max-width: 80%;
}

.message-content.user {
  flex-direction: row-reverse;
}

.message-icon {
  font-size: 1.2rem;
  margin-top: 0.25rem;
}

.message-text {
  padding: 0.75rem 1rem;
  border-radius: 18px;
  line-height: 1.4;
  word-wrap: break-word;
}

.message-content.ai .message-text {
  background-color: var(--color-background-light);
  color: var(--color-text-primary);
  border-bottom-left-radius: 4px;
}

.message-content.user .message-text {
  background-color: var(--color-primary);
  color: white;
  border-bottom-right-radius: 4px;
}

.ai-input-area {
  display: flex;
  gap: 0.5rem;
  padding: 1rem;
  border-top: 1px solid var(--color-border);
  background-color: var(--color-background-light);
}

.ai-input {
  flex: 1;
  padding: 0.75rem 1rem;
  border: 1px solid var(--color-border);
  border-radius: 20px;
  font-size: 0.9rem;
  outline: none;
  transition: border-color 0.3s ease;
}

.ai-input:focus {
  border-color: var(--color-primary);
}

.ai-input:disabled {
  background-color: var(--color-border);
  cursor: not-allowed;
}

.ai-send-btn {
  padding: 0.75rem 1.25rem;
  background-color: var(--color-primary);
  color: white;
  border: none;
  border-radius: 20px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.ai-send-btn:hover:not(:disabled) {
  background-color: var(--color-primary-dark);
}

.ai-send-btn:disabled {
  background-color: var(--color-border);
  cursor: not-allowed;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .ai-dialog {
    width: 100%;
    right: 0;
    left: 0;
    margin: 0 20px;
    bottom: 80px;
  }
}
</style>