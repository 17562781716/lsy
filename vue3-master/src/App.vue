<script setup>
import { RouterView } from 'vue-router'
import { onMounted } from 'vue'
import { useUserStore } from './stores/user'
import Header from './components/Header.vue'

const userStore = useUserStore()

onMounted(() => {
  userStore.initUsers()
})
</script>

<template>
  <div class="app-container">
    <!-- 导航栏 -->
    <Header />

    <!-- 主内容区 -->
    <main class="main-content">
      <RouterView />
    </main>

    <!-- 页脚 -->
    <footer class="footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-brand">
            <div class="footer-logo">
              <span class="logo-icon">🌿</span>
              <span class="logo-text">主题周</span>
            </div>
            <p class="footer-slogan">为自己的人生扩容</p>
          </div>
          <div class="footer-links">
            <div class="footer-section">
              <h4 class="footer-title">探索</h4>
              <ul>
                <li><RouterLink to="/themes">全部主题</RouterLink></li>
                <li><RouterLink to="/inspiration">灵感库</RouterLink></li>
                <li><RouterLink to="/insights">感悟墙</RouterLink></li>
              </ul>
            </div>
            <div class="footer-section">
              <h4 class="footer-title">我的</h4>
              <ul>
                <li><RouterLink to="/checkin">打卡中心</RouterLink></li>
                <li><RouterLink to="/planner">规划工具</RouterLink></li>
                <li><RouterLink to="/my-weeks">我的主题周</RouterLink></li>
              </ul>
            </div>
            <div class="footer-section">
              <h4 class="footer-title">关于</h4>
              <ul>
                <li><RouterLink to="/about">关于我们</RouterLink></li>
                <li><a href="#">使用指南</a></li>
                <li><a href="#">反馈建议</a></li>
              </ul>
            </div>
          </div>
        </div>
        <div class="footer-bottom">
          <p class="copyright">© 2026 主题周 - 一周一个新主题，解锁多元生活</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<style>
@import './assets/main.css';

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background: var(--gradient-main);
  background-attachment: fixed;
  color: var(--text-dark);
  min-height: 100vh;
}

.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

/* 主内容区 */
.main-content {
  flex: 1;
  padding-top: 80px;
}

/* 页脚样式 */
.footer {
  background: linear-gradient(135deg, #1b5e20, #2e7d32);
  color: white;
  padding: 3rem 0 1.5rem;
  margin-top: auto;
}

.footer-content {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 3rem;
  margin-bottom: 2rem;
}

.footer-brand {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.footer-logo {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 1.5rem;
  font-weight: bold;
}

.footer-logo .logo-icon {
  font-size: 1.8rem;
}

.footer-slogan {
  font-size: 0.875rem;
  opacity: 0.8;
}

.footer-links {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 2rem;
}

.footer-section h4 {
  font-size: 0.875rem;
  font-weight: 600;
  margin-bottom: 1rem;
  opacity: 0.9;
}

.footer-section ul {
  list-style: none;
  padding: 0;
}

.footer-section li {
  margin-bottom: 0.5rem;
}

.footer-section a {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  font-size: 0.875rem;
  transition: color 0.2s;
}

.footer-section a:hover {
  color: white;
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  padding-top: 1.5rem;
  text-align: center;
}

.copyright {
  font-size: 0.875rem;
  opacity: 0.7;
}

@media (max-width: 768px) {
  .footer-content {
    grid-template-columns: 1fr;
  }
  
  .footer-links {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
