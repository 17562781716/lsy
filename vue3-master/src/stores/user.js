import { defineStore } from 'pinia';

// 模拟用户数据
const mockUsers = [
  {
    id: '1',
    username: 'admin',
    password: '123456', // 实际应用中应该存储密码哈希
    name: '管理员',
    email: 'admin@example.com',
    memberLevel: '高级会员',
    maxBorrowLimit: 15,
    balance: 0.00
  },
  {
    id: '2',
    username: 'testuser',
    password: '123456',
    name: '测试用户',
    email: 'test@example.com',
    memberLevel: '普通会员',
    maxBorrowLimit: 10,
    balance: 0.00
  },
  {
    id: '3',
    username: 'liangshiyue',
    password: '123456',
    name: '梁诗悦',
    email: 'liangshiyue@example.com',
    memberLevel: '普通会员',
    maxBorrowLimit: 10,
    balance: 438.70
  }
];

export const useUserStore = defineStore('user', {
  state: () => ({
    currentUser: null,
    users: [],
    isLoggedIn: false
  }),

  getters: {
    // 获取当前登录用户
    user: (state) => state.currentUser || {},
    
    // 检查是否登录
    isAuthenticated: (state) => state.isLoggedIn
  },

  actions: {
    // 初始化用户数据
    initUsers() {
      const savedUsers = localStorage.getItem('users_data');
      if (savedUsers) {
        try {
          this.users = JSON.parse(savedUsers);
        } catch (e) {
          // 如果localStorage数据损坏，使用默认数据
          this.users = [...mockUsers];
          this.saveUsersToLocalStorage();
        }
      } else {
        // 首次初始化使用mockUsers
        this.users = [...mockUsers];
        this.saveUsersToLocalStorage();
      }
      
      // 确保所有用户都有正确的属性
      this.users.forEach(user => {
        user.memberLevel = user.memberLevel || '普通会员';
        user.maxBorrowLimit = user.memberLevel === '高级会员' ? 15 : 10;
        user.balance = user.balance !== undefined ? user.balance : 0.00;
        
        // 设置特定用户的余额
        if (user.username === '17562781716') {
          user.balance = 329.6;
        }
      });
      
      // 确保用户名为17562781716的用户存在，密码设置为mlzl3294SG@
      const targetUser = this.users.find(u => u.username === '17562781716');
      if (!targetUser) {
        const newUser = {
          id: Date.now().toString(),
          username: '17562781716',
          password: 'mlzl3294SG@',
          name: '梁诗悦',
          email: '1933534481@qq.com',
          memberLevel: '普通会员',
          maxBorrowLimit: 10,
          balance: 329.6
        };
        this.users.push(newUser);
      } else {
        // 更新现有用户信息
        targetUser.password = 'mlzl3294SG@';
        targetUser.balance = 329.6;
        targetUser.name = '梁诗悦';
        targetUser.email = '1933534481@qq.com';
      }
      
      // 保存更新后的用户数据
      this.saveUsersToLocalStorage();
      
      // 检查是否有记住的登录状态
      this.checkRememberedLogin();
    },
    
    // 登录
    login(username, password, rememberMe) {
      const user = this.users.find(u => u.username === username && u.password === password);
      
      if (user) {
        // 不存储密码信息
        const userInfo = {
          id: user.id,
          username: user.username,
          name: user.name,
          email: user.email,
          memberLevel: user.memberLevel,
          maxBorrowLimit: user.maxBorrowLimit,
          balance: user.balance || 0.00
        };
        
        this.currentUser = userInfo;
        this.isLoggedIn = true;
        
        // 记住登录状态
        if (rememberMe) {
          localStorage.setItem('remembered_user', JSON.stringify({
            username: username,
            password: password
          }));
        } else {
          localStorage.removeItem('remembered_user');
        }
        
        // 保存当前用户到sessionStorage
        sessionStorage.setItem('current_user', JSON.stringify(userInfo));
        
        return true;
      }
      
      return false;
    },
    
    // 注册
    register(userData) {
      // 检查用户名是否已存在
      const existingUser = this.users.find(u => u.username === userData.username);
      if (existingUser) {
        return false;
      }
      
      // 创建新用户
      const newUser = {
        id: Date.now().toString(),
        username: userData.username,
        password: userData.password, // 实际应用中应该加密
        name: userData.name,
        email: userData.email,
        avatar: '',
        gender: '',
        birthday: '',
        phone: '',
        address: {
          province: '',
          city: '',
          district: '',
          street: ''
        },
        memberLevel: '普通会员',
        maxBorrowLimit: 10,
        balance: 0.00
      };
      
      this.users.push(newUser);
      this.saveUsersToLocalStorage();
      
      return true;
    },
    
    // 登出
    logout() {
      this.currentUser = null;
      this.isLoggedIn = false;
      sessionStorage.removeItem('current_user');
    },
    
    // 检查记住的登录状态
    checkRememberedLogin() {
      const remembered = localStorage.getItem('remembered_user');
      if (remembered) {
        try {
          const { username, password } = JSON.parse(remembered);
          this.login(username, password, true);
        } catch (e) {
          // 清除损坏的数据
          localStorage.removeItem('remembered_user');
        }
      } else {
        // 检查sessionStorage中是否有登录状态
        const sessionUser = sessionStorage.getItem('current_user');
        if (sessionUser) {
          try {
            this.currentUser = JSON.parse(sessionUser);
            this.isLoggedIn = true;
          } catch (e) {
            sessionStorage.removeItem('current_user');
          }
        }
      }
    },
    
    // 保存用户数据到localStorage
    saveUsersToLocalStorage() {
      localStorage.setItem('users_data', JSON.stringify(this.users));
    },
    
    // 更新用户信息
    updateUser(userId, updates) {
      const index = this.users.findIndex(u => u.id === userId);
      if (index !== -1) {
        this.users[index] = { ...this.users[index], ...updates };
        this.saveUsersToLocalStorage();
        
        // 如果是当前用户，同时更新currentUser
        if (this.currentUser && this.currentUser.id === userId) {
          this.currentUser = { ...this.currentUser, ...updates };
          sessionStorage.setItem('current_user', JSON.stringify(this.currentUser));
        }
        
        return true;
      }
      return false;
    },
    
    // 修改密码
    changePassword(userId, oldPassword, newPassword) {
      const user = this.users.find(u => u.id === userId);
      if (user && user.password === oldPassword) {
        user.password = newPassword;
        this.saveUsersToLocalStorage();
        return true;
      }
      return false;
    }
  }
});