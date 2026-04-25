<template>
  <div class="captcha-container">
    <div class="captcha-input-group">
      <input
        v-model="inputValue"
        type="text"
        class="form-input"
        placeholder="请输入验证码"
        :maxlength="captchaText.length"
        @input="emitInput"
      >
      <button class="captcha-refresh" @click="refreshCaptcha" aria-label="刷新验证码">
        🔄
      </button>
    </div>
    <div class="captcha-image" @click="refreshCaptcha">
      <canvas ref="captchaCanvas" :width="width" :height="height"></canvas>
    </div>
    <p v-if="error" class="error-message">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';

const props = defineProps({
  width: {
    type: Number,
    default: 120
  },
  height: {
    type: Number,
    default: 40
  },
  length: {
    type: Number,
    default: 4
  },
  validate: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['update:validate', 'refresh']);

const captchaCanvas = ref(null);
const captchaText = ref('');
const inputValue = ref('');
const error = ref('');

// 生成随机验证码文本
const generateCaptchaText = () => {
  const chars = 'ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789';
  let text = '';
  for (let i = 0; i < props.length; i++) {
    text += chars.charAt(Math.floor(Math.random() * chars.length));
  }
  captchaText.value = text;
  return text;
};

// 绘制验证码
const drawCaptcha = () => {
  const canvas = captchaCanvas.value;
  if (!canvas) return;
  
  const ctx = canvas.getContext('2d');
  ctx.clearRect(0, 0, props.width, props.height);
  
  // 绘制背景
  ctx.fillStyle = '#f5f5f5';
  ctx.fillRect(0, 0, props.width, props.height);
  
  // 绘制噪点
  for (let i = 0; i < 50; i++) {
    ctx.fillStyle = `rgba(${Math.random() * 100}, ${Math.random() * 100}, ${Math.random() * 100}, 0.3)`;
    ctx.fillRect(Math.random() * props.width, Math.random() * props.height, 2, 2);
  }
  
  // 绘制干扰线
  for (let i = 0; i < 3; i++) {
    ctx.strokeStyle = `rgba(${Math.random() * 100}, ${Math.random() * 100}, ${Math.random() * 100}, 0.5)`;
    ctx.lineWidth = 1;
    ctx.beginPath();
    ctx.moveTo(Math.random() * props.width, Math.random() * props.height);
    ctx.lineTo(Math.random() * props.width, Math.random() * props.height);
    ctx.stroke();
  }
  
  // 绘制验证码文本
  const text = generateCaptchaText();
  ctx.font = 'bold 24px Arial';
  ctx.textBaseline = 'middle';
  
  for (let i = 0; i < text.length; i++) {
    // 随机旋转角度
    const angle = (Math.random() - 0.5) * 0.5;
    ctx.save();
    ctx.translate(20 + i * 25, props.height / 2);
    ctx.rotate(angle);
    
    // 随机颜色
    ctx.fillStyle = `rgba(${Math.random() * 100}, ${Math.random() * 100}, ${Math.random() * 100}, 0.8)`;
    ctx.fillText(text[i], 0, 0);
    ctx.restore();
  }
};

// 刷新验证码
const refreshCaptcha = () => {
  drawCaptcha();
  inputValue.value = '';
  error.value = '';
  emit('refresh');
};

// 验证验证码
const validateCaptcha = () => {
  if (!inputValue.value) {
    error.value = '请输入验证码';
    return false;
  }
  
  if (inputValue.value.toLowerCase() !== captchaText.value.toLowerCase()) {
    error.value = '验证码错误';
    refreshCaptcha();
    return false;
  }
  
  error.value = '';
  return true;
};

// 输入事件处理
const emitInput = () => {
  emit('update:validate', inputValue.value);
};

// 监听验证属性变化
watch(
  () => props.validate,
  (newVal) => {
    if (newVal !== undefined && newVal !== inputValue.value) {
      inputValue.value = newVal;
    }
  }
);

// 组件挂载时绘制验证码
onMounted(() => {
  drawCaptcha();
});

// 暴露方法
defineExpose({
  validate: validateCaptcha,
  refresh: refreshCaptcha
});
</script>

<style scoped>
.captcha-container {
  margin-bottom: 1.5rem;
}

.captcha-input-group {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.5rem;
}

.captcha-input-group .form-input {
  flex: 1;
}

.captcha-refresh {
  padding: 0.75rem;
  background-color: #f5f5f5;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.captcha-refresh:hover {
  background-color: #e0e0e0;
}

.captcha-image {
  cursor: pointer;
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
  display: inline-block;
}

.captcha-image canvas {
  display: block;
}

.error-message {
  color: #f44336;
  margin-top: 0.5rem;
  font-size: 0.875rem;
}
</style>