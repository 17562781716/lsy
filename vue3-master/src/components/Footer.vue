<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-content">
        <div class="footer-section footer-about">
            <div class="logo">
              <span class="logo-icon">📚</span>
              <span class="logo-text">云边书吧</span>
            </div>
            <div class="social-links">
              <a href="#" class="social-link" aria-label="微信">
                📱
              </a>
              <a href="#" class="social-link" aria-label="微博">
                🐦
              </a>
              <a href="#" class="social-link" aria-label="抖音">
                🎵
              </a>
              <a href="#" class="social-link" aria-label="小红书">
                📝
              </a>
            </div>
        </div>
        
        <div class="footer-section footer-links">
          <h3 class="footer-title">快速链接</h3>
          <ul class="link-list">
            <li><a href="/" class="footer-link">首页</a></li>
            <li><a href="/books" class="footer-link">书籍</a></li>
            <li><a href="/coffee" class="footer-link">咖啡时光</a></li>
            <li><a href="/salon" class="footer-link">沙龙交流</a></li>
            <li><a href="/membership" class="footer-link">会员福利</a></li>
            <li><a href="/about" class="footer-link">关于我们</a></li>
          </ul>
        </div>
        
        <div class="footer-section footer-support">
          <h3 class="footer-title">帮助支持</h3>
          <ul class="link-list">
            <li><a href="/help" class="footer-link">帮助中心</a></li>
            <li><a href="/contact" class="footer-link">联系我们</a></li>
            <li><a href="/faq" class="footer-link">常见问题</a></li>
            <li><a href="/terms" class="footer-link">使用条款</a></li>
          </ul>
        </div>
        
        <div class="footer-section footer-newsletter">
          <h3 class="footer-title">订阅通讯</h3>
          <p class="footer-description">
            订阅我们的通讯，获取最新的书籍推荐和活动信息
          </p>
          <div class="newsletter-form">
            <input 
              type="email" 
              placeholder="您的邮箱地址" 
              class="newsletter-input"
            >
            <button class="btn-primary newsletter-btn">
              订阅
            </button>
          </div>
        </div>
      </div>
      
      <div class="footer-bottom">
        <div class="footer-copyright">
          &copy; {{ new Date().getFullYear() }} 云边书吧. 保留所有权利.
        </div>
        <div class="footer-policies">
          <a href="/privacy" class="policy-link">隐私政策</a>
          <a href="/cookies" class="policy-link">Cookie政策</a>
          <a href="/sitemap" class="policy-link">网站地图</a>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup>
//  Footer组件逻辑
</script>

<style scoped>
.footer {
  background-color: var(--background-secondary);
  color: var(--text-secondary);
  padding: 4rem 0 2rem;
  margin-top: auto;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.footer-section h3.footer-title {
  color: var(--text-primary);
  font-size: 1.25rem;
  margin-bottom: 1.5rem;
}

.footer-about .logo {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--primary-color);
  margin-bottom: 1rem;
}

.footer-description {
  margin-bottom: 1.5rem;
  line-height: 1.6;
  font-size: 0.9rem;
  padding-left: 3.2rem; /* 精确对齐到"云边书吧"文字的起始位置 */
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
  background-color: var(--card-background);
  color: var(--text-primary);
  text-decoration: none;
  font-size: 1.25rem;
  transition: all var(--transition-fast);
  box-shadow: var(--shadow-light);
}

.social-link:hover {
  transform: translateY(-3px);
  box-shadow: var(--shadow-medium);
  color: var(--primary-color);
}

.link-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.link-list li {
  margin-bottom: 1rem;
}

.footer-link {
  color: var(--text-secondary);
  text-decoration: none;
  transition: all var(--transition-fast);
  font-size: 0.9rem;
}

.footer-link:hover {
  color: var(--primary-color);
  padding-left: 5px;
}

.newsletter-form {
  display: flex;
  gap: 0.5rem;
  margin-top: 1rem;
}

.newsletter-input {
  flex: 1;
  padding: 0.75rem 1.5rem;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  background-color: var(--card-background);
  color: var(--text-primary);
  font-size: 0.9rem;
}

.newsletter-input:focus {
  outline: none;
  border-color: #2196f3;
  box-shadow: 0 0 0 2px rgba(33, 150, 243, 0.1);
}

.newsletter-btn {
  border-radius: 8px;
  padding: 0.75rem 1.5rem;
  border: none;
  min-width: 80px;
  white-space: nowrap;
}

.footer-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 2rem;
  border-top: 1px solid var(--border-color);
  font-size: 0.875rem;
}

.footer-copyright {
  color: var(--text-tertiary);
}

.footer-policies {
  display: flex;
  gap: 1.5rem;
}

.policy-link {
  color: var(--text-tertiary);
  text-decoration: none;
  transition: color var(--transition-fast);
}

.policy-link:hover {
  color: var(--primary-color);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .footer {
    padding: 3rem 0 1.5rem;
  }
  
  .footer-content {
    grid-template-columns: 1fr;
    gap: 2.5rem;
    margin-bottom: 2rem;
  }
  
  .newsletter-form {
    flex-direction: column;
  }
  
  .footer-bottom {
    flex-direction: column;
    gap: 1rem;
    text-align: center;
  }
  
  .footer-policies {
    flex-wrap: wrap;
    justify-content: center;
    gap: 1rem;
  }
}
</style>