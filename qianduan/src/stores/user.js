import { defineStore } from 'pinia';
import { userLogin, userRegister, userLogout, getLoginUser, updateMyUser } from '../utils/userApi';

export const useUserStore = defineStore('user', {
  state: () => ({
    currentUser: null,
    isLoggedIn: false,
    token: null,
  }),

  getters: {
    user: (state) => state.currentUser || {},
    isAuthenticated: (state) => state.isLoggedIn,
  },

  actions: {
    async initUsers() {
      const savedUserInfo = localStorage.getItem('user_info');
      if (savedUserInfo) {
        try {
          const userInfo = JSON.parse(savedUserInfo);
          this.currentUser = userInfo;
          this.token = userInfo.token;
          this.isLoggedIn = true;
          const res = await getLoginUser();
          if (res.data) {
            this.currentUser = res.data;
          }
        } catch (e) {
          localStorage.removeItem('user_info');
          this.currentUser = null;
          this.token = null;
          this.isLoggedIn = false;
        }
      }
    },

    async login(username, password, rememberMe = false) {
      try {
        const res = await userLogin({
          userAccount: username,
          userPassword: password,
        });

        if (res.data) {
          const userInfo = {
            id: res.data.id,
            username: res.data.userName || res.data.userAccount,
            name: res.data.userName,
            email: res.data.email,
            token: res.data.token || res.data.id?.toString(),
            avatar: res.data.userAvatar,
            phone: res.data.phone,
          };

          this.currentUser = userInfo;
          this.token = userInfo.token;
          this.isLoggedIn = true;

          if (rememberMe) {
            localStorage.setItem('user_info', JSON.stringify(userInfo));
          } else {
            sessionStorage.setItem('user_info', JSON.stringify(userInfo));
          }

          return { success: true };
        }
        throw new Error('登录失败');
      } catch (error) {
        throw new Error(error.message || '登录失败');
      }
    },

    async register(userAccount, userPassword, userName = '') {
      try {
        const res = await userRegister({
          userAccount,
          userPassword,
          checkPassword: userPassword,
          userName,
        });

        if (res.data) {
          return { success: true };
        }
        throw new Error('注册失败');
      } catch (error) {
        throw new Error(error.message || '注册失败');
      }
    },

    async logout() {
      try {
        await userLogout();
      } catch (e) {
        // 忽略退出登录的错误
      }
      this.currentUser = null;
      this.isLoggedIn = false;
      this.token = null;
      localStorage.removeItem('user_info');
      sessionStorage.removeItem('user_info');
    },

    async updateUser(updates) {
      try {
        const res = await updateMyUser(updates);
        if (res.data) {
          this.currentUser = { ...this.currentUser, ...updates };
          const savedUserInfo = localStorage.getItem('user_info') || sessionStorage.getItem('user_info');
          if (savedUserInfo) {
            const userInfo = JSON.parse(savedUserInfo);
            userInfo = { ...userInfo, ...updates };
            if (localStorage.getItem('user_info')) {
              localStorage.setItem('user_info', JSON.stringify(userInfo));
            } else {
              sessionStorage.setItem('user_info', JSON.stringify(userInfo));
            }
          }
          return { success: true };
        }
        return { success: false, message: '更新失败' };
      } catch (error) {
        return { success: false, message: error.message || '更新失败' };
      }
    },

    getUserInfo() {
      return this.currentUser;
    },
  },
});