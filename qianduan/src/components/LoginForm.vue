<template>
  <div class="login-form">
    <h2 class="form-title">登录</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label class="form-label">账号</label>
        <input 
          type="text" 
          v-model="form.userAccount" 
          placeholder="请输入账号" 
          class="form-input"
          required
        />
      </div>
      <div class="form-group">
        <label class="form-label">密码</label>
        <input 
          type="password" 
          v-model="form.userPassword" 
          placeholder="请输入密码" 
          class="form-input"
          required
        />
      </div>
      <div v-if="error" class="error-message">{{ error }}</div>
      <button type="submit" class="btn-primary btn-block" :disabled="loading">
        {{ loading ? '登录中...' : '登录' }}
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '../stores/user';

const router = useRouter();
const userStore = useUserStore();
const loading = ref(false);
const error = ref('');

const form = reactive({
  userAccount: '',
  userPassword: ''
});

const handleLogin = async () => {
  error.value = '';
  loading.value = true;
  
  try {
    await userStore.login(form.userAccount, form.userPassword);
    router.push('/');
  } catch (e) {
    error.value = e.message || '登录失败，请重试';
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-form {
  padding: 1rem;
}

.form-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  text-align: center;
  color: var(--text-primary);
}

.form-group {
  margin-bottom: 1rem;
}

.form-label {
  display: block;
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 0.5rem;
}

.form-input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid var(--border-color);
  border-radius: 12px;
  font-size: 14px;
  transition: all 0.3s ease;
  background: var(--bg-secondary);
}

.form-input:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
}

.form-input::placeholder {
  color: var(--text-muted);
}

.error-message {
  color: #ef5350;
  font-size: 13px;
  margin-bottom: 1rem;
  text-align: center;
}

.btn-block {
  width: 100%;
}
</style>