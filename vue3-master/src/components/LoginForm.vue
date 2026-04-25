<template>
  <div class="login-form">
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">用户名</label>
        <input
          type="text"
          id="username"
          class="form-input"
          v-model="username"
          placeholder="请输入用户名"
          :class="{ 'error': errors.username }"
        >
        <p v-if="errors.username" class="error-message">{{ errors.username }}</p>
      </div>
      
      <div class="form-group">
        <label for="password">密码</label>
        <div class="password-input-container">
          <input
            :type="showPassword ? 'text' : 'password'"
            id="password"
            class="form-input"
            v-model="password"
            placeholder="请输入密码"
            :class="{ 'error': errors.password }"
            @input="checkPasswordRequirements"
          >
          <button 
            type="button" 
            class="password-toggle-btn"
            @click="togglePasswordVisibility"
            aria-label="切换密码可见性"
          >
            {{ showPassword ? '👁️‍🗨️' : '👁️' }}
          </button>
        </div>
        <p v-if="errors.password" class="error-message">{{ errors.password }}</p>
        
        <!-- 密码要求提示 -->
        <div class="password-requirements" v-if="password.length > 0">
          <div class="requirement-item" :class="{ 'met': hasUpperCase }">
            <span class="requirement-icon">{{ hasUpperCase ? '✓' : '✗' }}</span>
            包含至少一个大写字母
          </div>
          <div class="requirement-item" :class="{ 'met': hasSpecialChar }">
            <span class="requirement-icon">{{ hasSpecialChar ? '✓' : '✗' }}</span>
            包含至少一个特殊字符
          </div>
          <div class="requirement-item" :class="{ 'met': hasNumber }">
            <span class="requirement-icon">{{ hasNumber ? '✓' : '✗' }}</span>
            包含至少一个数字
          </div>
          <div class="requirement-item" :class="{ 'met': password.length >= 8 }">
            <span class="requirement-icon">{{ password.length >= 8 ? '✓' : '✗' }}</span>
            至少8个字符
          </div>
        </div>
      </div>
      
      <!-- 移除验证码 -->
      
      <div class="form-options">
        <label class="remember-me">
          <input type="checkbox" v-model="rememberMe">
          记住我
        </label>
        <a href="#" class="forgot-password">忘记密码？</a>
      </div>
      
      <button class="login-btn" type="submit" :disabled="isLoading">
        {{ isLoading ? '登录中...' : '登录' }}
      </button>
    </form>
    
    <p v-if="generalError" class="error-message">{{ generalError }}</p>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '../stores/user';
import * as Yup from 'yup';

const emit = defineEmits(['show-register']);

const router = useRouter();
const userStore = useUserStore();

// 表单数据
const username = ref('');
const password = ref('');
const showPassword = ref(false);
const rememberMe = ref(false);
const isLoading = ref(false);
const generalError = ref('');
const errors = reactive({});

// 密码要求检查
const hasUpperCase = computed(() => /[A-Z]/.test(password.value));
const hasSpecialChar = computed(() => /[!@#$%^&*(),.?":{}|<>]/.test(password.value));
const hasNumber = computed(() => /[0-9]/.test(password.value));

// 检查密码要求的方法
const checkPasswordRequirements = () => {
  // 重置密码错误
  if (errors.password) {
    delete errors.password;
  }
};

// 移除登录失败次数计数，不再需要验证码

// 密码强度验证规则 - 为了兼容演示数据，简化密码验证
const passwordSchema = Yup.string()
  .required('密码不能为空');
  // 实际应用中应该使用更强的密码规则
  // .min(8, '密码长度至少为8位')
  // .matches(/[A-Z]/, '密码必须包含至少一个大写字母')
  // .matches(/[a-z]/, '密码必须包含至少一个小写字母')
  // .matches(/[0-9]/, '密码必须包含至少一个数字')
  // .matches(/[!@#$%^&*(),.?":{}|<>]/, '密码必须包含至少一个特殊字符');

// 用户名验证规则
const usernameSchema = Yup.string()
  .required('用户名不能为空')
  .matches(/^[a-zA-Z0-9]{4,16}$/, '用户名必须是4-16位字母或数字的组合');

// 导出验证规则，供其他组件使用
defineExpose({
  passwordSchema
});

// 表单验证函数
const validateForm = async () => {
  let isValid = true;
  
  // 重置错误信息
  Object.keys(errors).forEach(key => delete errors[key]);
  generalError.value = '';
  
  try {
    // 验证用户名
    await usernameSchema.validate(username.value, { abortEarly: false });
  } catch (err) {
    errors.username = err.errors[0];
    isValid = false;
  }
  
  try {
    // 验证密码
    await passwordSchema.validate(password.value, { abortEarly: false });
  } catch (err) {
    errors.password = err.errors[0];
    isValid = false;
  }
  
  // 移除验证码验证
  
  return isValid;
};

// 处理登录
const handleLogin = async () => {
  // 验证表单
  const isValid = await validateForm();
  if (!isValid) return;
  
  isLoading.value = true;
  
  try {
      // 模拟登录请求
      const success = await userStore.login(username.value, password.value, rememberMe.value);
      
      if (success) {
        // 登录成功后跳转到首页或上一个页面
        const redirect = sessionStorage.getItem('redirectTo') || '/';
        sessionStorage.removeItem('redirectTo');
        router.push(redirect);
      } else {
        generalError.value = '用户名或密码错误';
      }
    } catch (err) {
      generalError.value = '登录失败，请稍后重试';
    } finally {
      isLoading.value = false;
    }
};

// 切换密码可见性
const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
};
</script>

<style scoped>
.login-form {
  max-width: 400px;
  margin: 0 auto;
}

.login-form h1 {
  text-align: center;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.login-subtitle {
  text-align: center;
  color: var(--text-secondary);
  margin-bottom: 2rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: var(--text-primary);
  font-weight: 500;
}

.form-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid var(--border-color);
  border-radius: var(--border-radius);
  font-size: 1rem;
  transition: border-color 0.2s;
  background-color: var(--input-background);
  color: var(--text-primary);
}

.form-input:focus {
  outline: none;
  border-color: #2c3e50;
  box-shadow: 0 0 0 2px rgba(44, 62, 80, 0.1);
}

.form-input.error {
  border-color: #f44336;
}

.password-input-container {
  position: relative;
}

.password-toggle-btn {
  position: absolute;
  right: 0.75rem;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.2rem;
  padding: 0.25rem;
  color: var(--text-secondary);
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.remember-me {
  display: flex;
  align-items: center;
  color: var(--text-primary);
  cursor: pointer;
}

.remember-me input {
  margin-right: 0.5rem;
}

.forgot-password {
  color: var(--primary-color);
  text-decoration: none;
}

.forgot-password:hover {
  text-decoration: underline;
}

.login-btn {
  width: 100%;
  padding: 0.75rem;
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  color: white;
  border: none;
  border-radius: var(--border-radius);
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.2s;
  margin-bottom: 1.5rem;
  box-shadow: 0 2px 8px rgba(46, 125, 50, 0.3);
}

.login-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(46, 125, 50, 0.4);
}

.login-btn:disabled {
  background: var(--disabled-color);
  color: white;
  cursor: not-allowed;
  box-shadow: none;
}

.register-link {
  text-align: center;
  color: var(--text-secondary);
  margin-bottom: 1rem;
}

.register-link a {
  color: var(--text-secondary);
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}

.error-message {
  color: #f44336;
  text-align: center;
  margin-top: 0.5rem;
  font-size: 0.875rem;
}

.password-requirements {
  margin-top: 0.75rem;
  padding: 0.75rem;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: var(--border-radius);
  border: 1px solid var(--border-color);
}

.requirement-item {
  display: flex;
  align-items: center;
  margin-bottom: 0.5rem;
  font-size: 0.875rem;
  color: var(--text-secondary);
}

.requirement-item:last-child {
  margin-bottom: 0;
}

.requirement-icon {
  margin-right: 0.5rem;
  font-weight: bold;
  width: 1rem;
  text-align: center;
}

.requirement-item.met {
  color: #4CAF50;
}

.requirement-item.met .requirement-icon {
  color: #4CAF50;
}

.requirement-item:not(.met) .requirement-icon {
  color: #f44336;
}

.general-error {
  margin-top: 1rem;
}

@media (max-width: 480px) {
  .login-form {
    padding: 0 1rem;
  }
}
</style>