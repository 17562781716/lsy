<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-brand">
        <span class="brand-icon">🌿</span>
        <h1 class="brand-name">主题周</h1>
      </div>
      
      <!-- 登录表单 -->
      <div v-if="!showRegister" class="form-wrapper">
        <LoginForm />
      </div>
      
      <!-- 注册表单 -->
      <div v-else class="form-wrapper">
        <RegisterForm />
      </div>
      
      <div class="toggle-link">
        <a href="#" @click.prevent="showRegister = !showRegister">
          {{ showRegister ? '返回登录' : '立即注册' }}
        </a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import LoginForm from '../components/LoginForm.vue';
import RegisterForm from '../components/RegisterForm.vue';

const showRegister = ref(false);
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  background: linear-gradient(135deg, #e8f5e9 0%, #c8e6c9 50%, #a5d6a7 100%);
}

.login-container {
  width: 100%;
  max-width: 480px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 24px;
  padding: 3rem;
  box-shadow: var(--shadow-large);
  backdrop-filter: blur(10px);
}

.login-brand {
  text-align: center;
  margin-bottom: 2rem;
}

.brand-icon {
  font-size: 48px;
  display: block;
  margin-bottom: 0.5rem;
}

.brand-name {
  font-size: 28px;
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 0.5rem;
}

.form-wrapper {
  position: relative;
}

.toggle-link {
  text-align: center;
  margin-top: 1.5rem;
}

.toggle-link a {
  color: var(--text-secondary);
  text-decoration: none;
  font-size: 14px;
}

.toggle-link a:hover {
  text-decoration: underline;
}

@media (max-width: 480px) {
  .login-container {
    padding: 2rem 1.5rem;
  }
}
</style>
