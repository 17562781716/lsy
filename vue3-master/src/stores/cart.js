import { defineStore } from 'pinia';
import { useUserStore } from './user.js';

export const useCartStore = defineStore('cart', {
  state: () => ({
    items: [],
    orders: []
  }),

  getters: {
    // 获取购物车商品总数
    totalItems: (state) => {
      return state.items.reduce((total, item) => total + item.quantity, 0);
    },
    
    // 获取购物车总金额
    totalAmount: (state) => {
      const userStore = useUserStore();
      return state.items.reduce((total, item) => {
        // 检查用户是否登录以及是否为会员
        const isMember = userStore.isAuthenticated && userStore.user.memberLevel;
        const price = isMember ? item.memberPrice : item.regularPrice;
        // 提取数字部分并转换为浮点数
        let priceValue = 0;
        try {
          // 确保price是字符串
          const priceStr = String(price);
          // 提取数字部分
          const numericStr = priceStr.replace(/[^0-9.]/g, '');
          priceValue = parseFloat(numericStr) || 0;
        } catch (e) {
          console.error('提取价格失败:', e);
          priceValue = 0;
        }
        return total + (priceValue * item.quantity);
      }, 0).toFixed(2);
    },
    
    // 获取购物车商品列表
    cartItems: (state) => state.items,
    
    // 获取订单历史
    orderHistory: (state) => state.orders
  },

  actions: {
    // 添加商品到购物车
    addToCart(coffee, options) {
      // 检查商品是否已存在于购物车
      const existingItemIndex = this.items.findIndex(item => 
        item.id === coffee.id && 
        item.temperature === options.temperature && 
        item.sugar === options.sugar
      );
      
      if (existingItemIndex >= 0) {
        // 如果商品已存在，增加数量
        this.items[existingItemIndex].quantity++;
      } else {
        // 如果商品不存在，添加新商品
        this.items.push({
          ...coffee,
          temperature: options.temperature,
          sugar: options.sugar,
          quantity: 1
        });
      }
      
      this.saveCartToLocalStorage();
    },
    
    // 更新商品数量
    updateQuantity(itemId, temperature, sugar, quantity) {
      const itemIndex = this.items.findIndex(item => 
        item.id === itemId && 
        item.temperature === temperature && 
        item.sugar === sugar
      );
      
      if (itemIndex >= 0) {
        if (quantity <= 0) {
          // 如果数量为0或负数，移除商品
          this.removeFromCart(itemId, temperature, sugar);
        } else {
          // 更新数量
          this.items[itemIndex].quantity = quantity;
          this.saveCartToLocalStorage();
        }
      }
    },
    
    // 从购物车移除商品
    removeFromCart(itemId, temperature, sugar) {
      this.items = this.items.filter(item => 
        !(item.id === itemId && item.temperature === temperature && item.sugar === sugar)
      );
      this.saveCartToLocalStorage();
    },
    
    // 清空购物车
    clearCart() {
      this.items = [];
      this.saveCartToLocalStorage();
    },
    
    // 结算购物车
    checkout() {
      const userStore = useUserStore();
      const totalAmount = parseFloat(this.totalAmount);
      
      // 检查购物车是否为空
      if (this.items.length === 0) {
        return { success: false, message: '购物车为空' };
      }
      
      // 检查用户是否登录
      if (!userStore.isAuthenticated) {
        return { success: false, message: '请先登录' };
      }
      
      // 获取当前用户信息
      const currentUser = userStore.user;
      
      // 检查余额是否充足
      if (currentUser.balance < totalAmount) {
        return { success: false, message: `余额不足，当前余额：¥${currentUser.balance}，需支付：¥${totalAmount}` };
      }
      
      // 创建订单
      const order = {
        id: Date.now().toString(),
        items: [...this.items],
        totalAmount: totalAmount,
        date: new Date().toISOString(),
        status: '已完成'
      };
      
      // 更新用户余额
      userStore.updateUser(currentUser.id, {
        balance: parseFloat((currentUser.balance - totalAmount).toFixed(2))
      });
      
      // 保存订单
      this.orders.push(order);
      
      // 清空购物车
      this.clearCart();
      
      // 保存订单到localStorage
      this.saveOrdersToLocalStorage();
      
      return { success: true, message: `订单结算成功！已扣款¥${totalAmount}，剩余余额：¥${(currentUser.balance - totalAmount).toFixed(2)}`, order };
    },
    
    // 保存购物车到localStorage
    saveCartToLocalStorage() {
      localStorage.setItem('cart_items', JSON.stringify(this.items));
    },
    
    // 保存订单到localStorage
    saveOrdersToLocalStorage() {
      localStorage.setItem('cart_orders', JSON.stringify(this.orders));
    },
    
    // 从localStorage加载购物车
    loadCartFromLocalStorage() {
      const savedCart = localStorage.getItem('cart_items');
      if (savedCart) {
        try {
          const parsedCart = JSON.parse(savedCart);
          // 确保购物车数据格式正确
          if (Array.isArray(parsedCart)) {
            this.items = parsedCart;
          } else {
            console.error('购物车数据格式错误:', parsedCart);
            this.items = [];
          }
        } catch (e) {
          console.error('加载购物车数据失败:', e);
          this.items = [];
        }
      }
    },
    
    // 从localStorage加载订单
    loadOrdersFromLocalStorage() {
      const savedOrders = localStorage.getItem('cart_orders');
      if (savedOrders) {
        try {
          this.orders = JSON.parse(savedOrders);
        } catch (e) {
          console.error('加载订单数据失败:', e);
          this.orders = [];
        }
      }
    },
    
    // 初始化购物车数据
    initCart() {
      this.loadCartFromLocalStorage();
      this.loadOrdersFromLocalStorage();
    }
  }
});