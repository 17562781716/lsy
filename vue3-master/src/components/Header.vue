<template>
  <header class="header" :class="{ 'header-scrolled': scrolled }">
    <div class="container">
      <div class="header-content">
        <div class="logo">
          <RouterLink to="/" class="logo-link">
            <span class="logo-icon">🌿</span>
            <span class="logo-text">主题周</span>
          </RouterLink>
        </div>
        
        <nav class="nav">
          <ul class="nav-links">
            <li><RouterLink to="/" class="nav-link" exact-active-class="active">首页</RouterLink></li>
            <li><RouterLink to="/themes" class="nav-link" active-class="active">探索主题</RouterLink></li>
            <li><RouterLink to="/checkin" class="nav-link" active-class="active">打卡中心</RouterLink></li>
            <li><RouterLink to="/planner" class="nav-link" active-class="active">规划工具</RouterLink></li>
          </ul>
        </nav>
        
        <div class="header-actions">
          <template v-if="userStore.isAuthenticated">
            <div class="user-dropdown" ref="dropdownContainer">
              <button class="user-dropdown-toggle" @click="toggleUserDropdown">
                <span class="welcome-text">欢迎，{{ userStore.user.name }}</span>
                <span class="dropdown-arrow">{{ userDropdownOpen ? '▼' : '▲' }}</span>
              </button>
              <div class="user-dropdown-menu" v-if="userDropdownOpen">
                <button class="dropdown-item" @click="navigateTo('/profile')">个人中心</button>
                <button class="dropdown-item" @click="navigateTo('/my-weeks')">我的主题周</button>
                <button class="dropdown-item logout-btn" @click="logout">退出登录</button>
              </div>
            </div>
          </template>
          <RouterLink v-else to="/login" class="btn-primary btn-sm">登录</RouterLink>
        </div>
        
        <button class="mobile-menu-btn" @click="toggleMobileMenu" aria-label="移动端菜单">
          <span class="mobile-menu-icon">☰</span>
        </button>
      </div>
    </div>
    
    <!-- 移动端导航菜单 -->
    <div class="mobile-nav" v-if="mobileMenuOpen">
      <ul class="mobile-nav-links">
        <li><RouterLink to="/" class="mobile-nav-link" @click="closeMobileMenu">首页</RouterLink></li>
        <li><RouterLink to="/themes" class="mobile-nav-link" @click="closeMobileMenu">探索主题</RouterLink></li>
        <li><RouterLink to="/checkin" class="mobile-nav-link" @click="closeMobileMenu">打卡中心</RouterLink></li>
        <li><RouterLink to="/planner" class="mobile-nav-link" @click="closeMobileMenu">规划工具</RouterLink></li>
        <li v-if="!userStore.isAuthenticated">
          <RouterLink to="/login" class="mobile-nav-link" @click="closeMobileMenu">登录</RouterLink>
        </li>
      </ul>
    </div>
  </header>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter, RouterLink } from 'vue-router';
import { useUserStore } from '../stores/user';

const userStore = useUserStore();

// 个人中心下拉菜单控制
const userDropdownOpen = ref(false);
const dropdownContainer = ref(null);

const toggleUserDropdown = () => {
  userDropdownOpen.value = !userDropdownOpen.value;
};

const closeUserDropdown = () => {
  userDropdownOpen.value = false;
};

// 监听全局点击事件，点击外部时关闭下拉菜单
const handleGlobalClick = (e) => {
  if (userDropdownOpen.value && dropdownContainer.value && !dropdownContainer.value.contains(e.target)) {
    closeUserDropdown();
  }
};

// 获取路由实例
const router = useRouter();

// 导航到指定页面
const navigateTo = (path) => {
  router.push(path);
  closeUserDropdown();
};

// 退出登录功能
const logout = () => {
  userStore.logout();
  closeUserDropdown();
  router.push('/');
};

const scrolled = ref(false);
const mobileMenuOpen = ref(false);

// 监听滚动事件
const handleScroll = () => {
  scrolled.value = window.scrollY > 50;
};

// 组件挂载时添加全局点击事件监听器
onMounted(() => {
  window.addEventListener('scroll', handleScroll);
  window.addEventListener('click', handleGlobalClick);
});

// 组件卸载时移除全局点击事件监听器
onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
  window.removeEventListener('click', handleGlobalClick);
});

// 移动端菜单控制
const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value;
};

const closeMobileMenu = () => {
  mobileMenuOpen.value = false;
};

// 监听路由变化关闭移动端菜单
router.afterEach(() => {
  closeMobileMenu();
});
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  padding: 1rem 0;
  transition: all 0.3s ease;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid var(--border-color);
}

.header-scrolled {
  box-shadow: var(--shadow-medium);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-link {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  text-decoration: none;
  color: inherit;
  font-size: 1.5rem;
  font-weight: bold;
  transition: opacity 0.2s;
}

.logo-link:hover {
  opacity: 0.8;
}

.logo-icon {
  font-size: 1.8rem;
}

.logo-text {
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.nav-links {
  display: flex;
  gap: 2rem;
  list-style: none;
  margin: 0;
  padding: 0;
}

.nav-link {
  text-decoration: none;
  color: var(--text-dark);
  font-size: 0.95rem;
  font-weight: 500;
  padding: 0.5rem 0;
  position: relative;
  transition: color 0.2s;
}

.nav-link:hover {
  color: var(--primary-color);
}

.nav-link.active {
  color: var(--primary-color);
}

.nav-link.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: linear-gradient(90deg, var(--primary-color), var(--primary-color-light));
  border-radius: 1px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.btn-sm {
  padding: 0.5rem 1.25rem;
  font-size: 0.875rem;
  border-radius: 20px;
}

.user-dropdown {
  position: relative;
}

.user-dropdown-toggle {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  background: var(--background-secondary);
  border: 1px solid var(--border-color);
  border-radius: 20px;
  cursor: pointer;
  font-size: 0.875rem;
  color: var(--text-primary);
  transition: all 0.2s;
}

.user-dropdown-toggle:hover {
  background: var(--background-tertiary);
}

.welcome-text {
  font-weight: 500;
}

.dropdown-arrow {
  font-size: 0.75rem;
  color: var(--text-muted);
}

.user-dropdown-menu {
  position: absolute;
  top: calc(100% + 0.5rem);
  right: 0;
  background: white;
  border-radius: 12px;
  box-shadow: var(--shadow-large);
  padding: 0.5rem;
  min-width: 160px;
  z-index: 1001;
}

.dropdown-item {
  display: block;
  width: 100%;
  padding: 0.75rem 1rem;
  text-align: left;
  background: none;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.875rem;
  color: var(--text-dark);
  transition: all 0.2s;
}

.dropdown-item:hover {
  background: var(--background-secondary);
  color: var(--primary-color);
}

.logout-btn {
  color: var(--red-color);
}

.logout-btn:hover {
  background: #ffebee;
}

.mobile-menu-btn {
  display: none;
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 0.5rem;
  color: var(--text-dark);
}

.mobile-nav {
  display: none;
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: white;
  box-shadow: var(--shadow-large);
  padding: 1rem;
}

.mobile-nav-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.mobile-nav-link {
  display: block;
  padding: 0.875rem 1rem;
  text-decoration: none;
  color: var(--text-dark);
  font-size: 1rem;
  border-radius: 8px;
  transition: all 0.2s;
}

.mobile-nav-link:hover {
  background: var(--background-secondary);
  color: var(--primary-color);
}

@media (max-width: 768px) {
  .nav {
    display: none;
  }
  
  .header-actions {
    display: none;
  }
  
  .mobile-menu-btn {
    display: block;
  }
  
  .mobile-nav {
    display: block;
  }
}
</style>
