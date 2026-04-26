<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-content">
        <div class="footer-section footer-brand">
          <div class="logo">
            <span class="logo-icon">🌿</span>
            <span class="logo-text">主题周</span>
          </div>
          <p class="footer-slogan">一周一个新主题，解锁多元生活</p>
          <div class="social-links">
            <a href="#" class="social-link" aria-label="微信">📱</a>
            <a href="#" class="social-link" aria-label="微博">🐦</a>
            <a href="#" class="social-link" aria-label="小红书">📝</a>
          </div>
        </div>

        <div class="footer-section footer-links">
          <h3 class="footer-title">探索</h3>
          <ul class="link-list">
            <li><RouterLink to="/themes" class="footer-link">探索主题</RouterLink></li>
            <li><RouterLink to="/custom-week" class="footer-link">自定义周</RouterLink></li>
          </ul>
        </div>

        <div class="footer-section footer-links">
          <h3 class="footer-title">我的</h3>
          <ul class="link-list">
            <li><RouterLink to="/checkin" class="footer-link">打卡中心</RouterLink></li>
            <li><RouterLink to="/planner" class="footer-link">规划工具</RouterLink></li>
            <li><RouterLink to="/my-weeks" class="footer-link">我的主题周</RouterLink></li>
          </ul>
        </div>

        <div class="footer-section footer-links">
          <h3 class="footer-title">关于</h3>
          <ul class="link-list">
            <li><RouterLink to="/about" class="footer-link">关于我们</RouterLink></li>
            <li><RouterLink to="/about" class="footer-link">使用指南</RouterLink></li>
          </ul>
        </div>
      </div>

      <div class="footer-bottom">
        <div class="footer-copyright">
          &copy; {{ new Date().getFullYear() }} 主题周. 保留所有权利.
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup>
import { RouterLink } from 'vue-router';
</script>

<style scoped>
.footer {
  background: linear-gradient(135deg, #1a5c1a 0%, #2e7d32 50%, #1b5e20 100%);
  color: rgba(255, 255, 255, 0.85);
  padding: 4rem 0 2rem;
  margin-top: auto;
}

.footer-content {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  gap: 3rem;
  margin-bottom: 3rem;
}

.footer-brand .logo {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 1.5rem;
  font-weight: bold;
  color: white;
  margin-bottom: 1rem;
}

.footer-slogan {
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 1.5rem;
  line-height: 1.6;
}

.social-links {
  display: flex;
  gap: 1rem;
}

.social-link {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.15);
  color: white;
  text-decoration: none;
  font-size: 1.25rem;
  transition: all 0.3s;
}

.social-link:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-3px);
}

.footer-title {
  color: white;
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
}

.link-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.link-list li {
  margin-bottom: 0.875rem;
}

.footer-link {
  color: rgba(255, 255, 255, 0.75);
  text-decoration: none;
  transition: all 0.3s;
  font-size: 0.9rem;
}

.footer-link:hover {
  color: white;
  padding-left: 5px;
}

.footer-bottom {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 2rem;
  border-top: 1px solid rgba(255, 255, 255, 0.15);
  font-size: 0.875rem;
  color: rgba(255, 255, 255, 0.6);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .footer {
    padding: 3rem 0 1.5rem;
  }

  .footer-content {
    grid-template-columns: 1fr 1fr;
    gap: 2.5rem;
    margin-bottom: 2rem;
  }

  .footer-brand {
    grid-column: 1 / -1;
  }

  .footer-bottom {
    text-align: center;
  }
}

@media (max-width: 480px) {
  .footer-content {
    grid-template-columns: 1fr;
  }
}
</style>
