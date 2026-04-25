import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { useUserStore } from '../stores/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/themes',
      name: 'themes',
      component: () => import('../views/ThemesView.vue'),
    },
    {
      path: '/checkin',
      name: 'checkin',
      component: () => import('../views/CheckinView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/planner',
      name: 'planner',
      component: () => import('../views/PlannerView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('../views/ProfileView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/my-weeks',
      name: 'myWeeks',
      component: () => import('../views/MyWeeksView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/custom-week',
      name: 'customWeek',
      component: () => import('../views/CustomWeekView.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },
  ],
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  const isAuthenticated = userStore.isAuthenticated
  
  if (requiresAuth && !isAuthenticated) {
    sessionStorage.setItem('redirectTo', to.fullPath)
    next('/login')
  } else {
    next()
  }
})

export default router
