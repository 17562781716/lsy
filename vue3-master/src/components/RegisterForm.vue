<template>
  <div class="register-form">
    <form @submit.prevent="handleRegister">
      <div class="form-group">
        <label for="phone">手机号码</label>
        <input
          type="tel"
          id="phone"
          class="form-input"
          v-model="phone"
          placeholder="请输入手机号码"
          :class="{ 'error': errors.phone }"
        >
        <p v-if="errors.phone" class="error-message">{{ errors.phone }}</p>
      </div>
      
      <div class="form-group">
        <label for="name">姓名</label>
        <input
          type="text"
          id="name"
          class="form-input"
          v-model="name"
          placeholder="请输入您的姓名"
          :class="{ 'error': errors.name }"
        >
        <p v-if="errors.name" class="error-message">{{ errors.name }}</p>
      </div>
      
      <div class="form-group">
        <label for="password">密码</label>
        <div class="password-input-container">
          <input
            :type="showPassword ? 'text' : 'password'"
            id="password"
            class="form-input"
            v-model="password"
            placeholder="请设置密码"
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
          <div class="requirement-item" :class="{ 'met': hasLowerCase }">
            <span class="requirement-icon">{{ hasLowerCase ? '✓' : '✗' }}</span>
            包含至少一个小写字母
          </div>
          <div class="requirement-item" :class="{ 'met': password.length >= 8 }">
            <span class="requirement-icon">{{ password.length >= 8 ? '✓' : '✗' }}</span>
            至少8个字符
          </div>
        </div>
      </div>
      
      <div class="form-group">
        <label for="confirmPassword">确认密码</label>
        <input
          :type="showPassword ? 'text' : 'password'"
          id="confirmPassword"
          class="form-input"
          v-model="confirmPassword"
          placeholder="请确认密码"
          :class="{ 'error': errors.confirmPassword }"
        >
        <p v-if="errors.confirmPassword" class="error-message">{{ errors.confirmPassword }}</p>
      </div>
      
      <!-- 移除验证码 -->
      
      <button class="register-btn" type="submit" :disabled="isLoading">
        {{ isLoading ? '注册中...' : '注册' }}
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

// 密码强度验证规则
const passwordSchema = Yup.string()
  .required('密码不能为空')
  .min(8, '密码长度至少为8位')
  .matches(/[A-Z]/, '密码必须包含至少一个大写字母')
  .matches(/[a-z]/, '密码必须包含至少一个小写字母')
  .matches(/[0-9]/, '密码必须包含至少一个数字')
  .matches(/[!@#$%^&*(),.?":{}|<>]/, '密码必须包含至少一个特殊字符');

const emit = defineEmits(['show-login']);

const router = useRouter();
const userStore = useUserStore();

// 表单数据
const phone = ref('');
const name = ref('');
const password = ref('');
const confirmPassword = ref('');
const showPassword = ref(false);
const isLoading = ref(false);
const generalError = ref('');
const errors = reactive({});

// 密码要求检查
const hasUpperCase = computed(() => /[A-Z]/.test(password.value));
const hasLowerCase = computed(() => /[a-z]/.test(password.value));
const hasSpecialChar = computed(() => /[!@#$%^&*(),.?":{}|<>]/.test(password.value));
const hasNumber = computed(() => /[0-9]/.test(password.value));

// 检查密码要求的方法
const checkPasswordRequirements = () => {
  // 重置密码错误
  if (errors.password) {
    delete errors.password;
  }
  // 重置确认密码错误
  if (errors.confirmPassword) {
    delete errors.confirmPassword;
  }
};

// 手机号码验证规则
const phoneSchema = Yup.string()
  .required('手机号码不能为空')
  .matches(/^1[3-9]\d{9}$/, '请输入有效的手机号码');

// 姓名验证规则
const nameSchema = Yup.string()
  .required('姓名不能为空')
  .min(2, '姓名长度至少为2位')
  .max(20, '姓名长度不能超过20位');

// 表单验证函数
const validateForm = async () => {
  let isValid = true;
  
  // 重置错误信息
  Object.keys(errors).forEach(key => delete errors[key]);
  generalError.value = '';
  
  try {
    // 验证手机号码
    await phoneSchema.validate(phone.value);
  } catch (err) {
    errors.phone = err.errors[0];
    isValid = false;
  }
  
  try {
    // 验证姓名
    await nameSchema.validate(name.value);
  } catch (err) {
    errors.name = err.errors[0];
    isValid = false;
  }
  
  try {
    // 验证密码
    await passwordSchema.validate(password.value);
  } catch (err) {
    errors.password = err.errors[0];
    isValid = false;
  }
  
  try {
    // 验证确认密码
    await Yup.string()
      .required('请确认密码')
      .oneOf([password.value], '两次输入的密码不一致')
      .validate(confirmPassword.value);
  } catch (err) {
    errors.confirmPassword = err.errors[0];
    isValid = false;
  }
  
  // 移除验证码验证
  
  return isValid;
};

// 处理注册
const handleRegister = async () => {
  // 验证表单
  const isValid = await validateForm();
  if (!isValid) return;
  
  isLoading.value = true;
  
  try {
    // 模拟注册请求
    const success = await userStore.register({
      username: phone.value,
      name: name.value,
      password: password.value
    });
    
    if (success) {
      // 注册成功后自动登录
      const loginSuccess = await userStore.login(phone.value, password.value, false);
      
      if (loginSuccess) {
        // 登录成功后跳转到首页
        router.push('/');
      } else {
        generalError.value = '注册成功，但自动登录失败，请手动登录';
        emit('show-login');
      }
    } else {
      generalError.value = '手机号已被注册';
    }
  } catch (err) {
    generalError.value = '注册失败，请稍后重试';
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
.register-form {
  max-width: 400px;
  margin: 0 auto;
  position: relative;
}

.register-form h1 {
  text-align: center;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.register-subtitle {
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

.close-btn {
  position: absolute;
  top: -40px;
  right: 0;
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: var(--text-secondary);
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: background-color 0.2s;
}

.close-btn:hover {
  background-color: var(--color-card-background);
}

.register-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: transparent;
  color: #2c3e50;
  border: 1px solid #2c3e50;
  border-radius: var(--border-radius);
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.2s;
  margin-bottom: 1.5rem;
}

.register-btn:hover:not(:disabled) {
  background-color: #2c3e50;
  color: white;
}

.register-btn:disabled {
  background-color: transparent;
  color: var(--disabled-color);
  border-color: var(--disabled-color);
  cursor: not-allowed;
}

.login-link {
  text-align: center;
  color: var(--text-secondary);
  margin-bottom: 1rem;
}

.login-link a {
  color: var(--primary-color);
  text-decoration: none;
}

.login-link a:hover {
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
  .register-form {
    padding: 0 1rem;
  }
  
  .close-btn {
    top: -30px;
  }
}
</style>