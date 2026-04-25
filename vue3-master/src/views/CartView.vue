<template>
  <section class="cart-section">
    <div class="container">
      <div class="section-header">
        <h1 class="section-title">我的购物车</h1>
      </div>
      
      <div class="cart-content">
        <!-- 购物车商品列表 -->
        <div v-if="cartStore.totalItems > 0" class="cart-items">
          <div class="cart-item" v-for="item in cartStore.cartItems" :key="`${item.id}-${item.temperature}-${item.sugar}`">
            <div class="cart-item-info">
              <div class="cart-item-details">
                <h3 class="cart-item-name">{{ item.name }}</h3>
                <div class="cart-item-options">
                  <span class="option-item">温度：{{ getTemperatureLabel(item.temperature) }}</span>
                  <span class="option-item">糖度：{{ getSugarLabel(item.sugar) }}</span>
                </div>
                <div class="cart-item-prices">
                  <span class="price-item">非会员价：<span class="regular">{{ item.regularPrice }}</span></span>
                  <span class="price-item">会员价：<span class="member">{{ item.memberPrice }}</span></span>
                </div>
              </div>
              
              <div class="cart-item-actions">
                <div class="quantity-control">
                  <button class="quantity-btn" @click="decreaseQuantity(item)">-</button>
                  <span class="quantity">{{ item.quantity }}</span>
                  <button class="quantity-btn" @click="increaseQuantity(item)">+</button>
                </div>
                <button class="remove-btn" @click="removeFromCart(item)">删除</button>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 空购物车提示 -->
        <div v-else class="empty-cart">
          <h3>购物车是空的</h3>
        </div>
        
        <!-- 购物车结算 -->
        <div v-if="cartStore.totalItems > 0" class="cart-summary">
          <div class="summary-section">
            <h3>订单 summary</h3>
            <div class="summary-item">
              <span class="summary-label">商品总数：</span>
              <span class="summary-value">{{ cartStore.totalItems }}</span>
            </div>
            <div class="summary-item">
              <span class="summary-label">会员等级：</span>
              <span class="summary-value">{{ userStore.user.memberLevel || '非会员' }}</span>
            </div>
            <div class="summary-item">
              <span class="summary-label">当前余额：</span>
              <span class="summary-value balance">¥{{ userStore.user.balance || '0.00' }}</span>
            </div>
            <div class="summary-item total">
              <span class="summary-label">总计：</span>
              <span class="summary-value total-amount">¥{{ cartStore.totalAmount }}</span>
            </div>
          </div>
          
          <div class="checkout-section">
            <div class="checkout-warning" v-if="parseFloat(userStore.user.balance || 0) < parseFloat(cartStore.totalAmount)">
              <p>余额不足，请先充值</p>
            </div>
            <button 
              class="btn-primary checkout-btn" 
              @click="checkout" 
              :disabled="parseFloat(userStore.user.balance || 0) < parseFloat(cartStore.totalAmount)"
            >
              确认结算
            </button>
          </div>
        </div>
      </div>
      
      <!-- 订单历史 -->
      <div v-if="cartStore.orderHistory.length > 0" class="order-history">
        <h3 class="history-title">订单历史</h3>
        <div class="order-item" v-for="order in cartStore.orderHistory" :key="order.id">
          <div class="order-header">
            <span class="order-id">订单号：{{ order.id }}</span>
            <span class="order-date">{{ formatDate(order.date) }}</span>
            <span class="order-status">{{ order.status }}</span>
          </div>
          <div class="order-items">
            <div class="order-item-summary" v-for="item in order.items" :key="`${item.id}-${item.temperature}-${item.sugar}`">
              <span class="order-item-name">{{ item.name }} x{{ item.quantity }}</span>
              <span class="order-item-price">¥{{ item.memberPrice }}</span>
            </div>
          </div>
          <div class="order-total">
            总计：¥{{ order.totalAmount }}
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { computed } from 'vue';
import { useCartStore } from '../stores/cart.js';
import { useUserStore } from '../stores/user.js';
import { useRouter } from 'vue-router';

const cartStore = useCartStore();
const userStore = useUserStore();
const router = useRouter();

// 温度选项
const temperatureOptions = [
  { value: 'hot', label: '热饮' },
  { value: 'iced', label: '冰饮' },
  { value: 'warm', label: '温饮' }
];

// 糖度选项
const sugarOptions = [
  { value: 'no', label: '无糖' },
  { value: 'less', label: '少糖' },
  { value: 'normal', label: '正常糖' },
  { value: 'more', label: '多糖' }
];

// 获取温度标签
const getTemperatureLabel = (value) => {
  const option = temperatureOptions.find(opt => opt.value === value);
  return option ? option.label : value;
};

// 获取糖度标签
const getSugarLabel = (value) => {
  const option = sugarOptions.find(opt => opt.value === value);
  return option ? option.label : value;
};

// 增加商品数量
const increaseQuantity = (item) => {
  cartStore.updateQuantity(item.id, item.temperature, item.sugar, item.quantity + 1);
};

// 减少商品数量
const decreaseQuantity = (item) => {
  if (item.quantity > 1) {
    cartStore.updateQuantity(item.id, item.temperature, item.sugar, item.quantity - 1);
  }
};

// 从购物车移除商品
const removeFromCart = (item) => {
  cartStore.removeFromCart(item.id, item.temperature, item.sugar);
};

// 结算购物车
const checkout = () => {
  // 检查用户是否登录
  if (!userStore.isAuthenticated) {
    alert('请先登录');
    router.push('/login');
    return;
  }
  
  // 执行结算
  const result = cartStore.checkout();
  
  if (result.success) {
    alert(`订单结算成功！\n\n订单号：${result.order.id}\n总金额：¥${result.order.totalAmount}\n剩余余额：¥${userStore.user.balance}`);
  } else {
    alert(result.message);
  }
};

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  });
};
</script>

<style scoped>
.cart-section {
  padding: 4rem 0;
  background-color: var(--color-background-light);
}

.section-header {
  text-align: center;
  margin-bottom: 4rem;
}

.section-title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: var(--color-text-primary);
}

.cart-content {
  display: flex;
  gap: 2rem;
  flex-wrap: wrap;
}

.cart-items {
  flex: 1;
  min-width: 300px;
  background-color: white;
  border-radius: 8px;
  padding: 2rem;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.cart-item {
  border-bottom: 1px solid var(--color-border);
  padding: 1.5rem 0;
}

.cart-item:last-child {
  border-bottom: none;
}

.cart-item-info {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
  gap: 1rem;
}

.cart-item-details {
  flex: 1;
  min-width: 250px;
}

.cart-item-name {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--color-text-primary);
  margin-bottom: 0.5rem;
}

.cart-item-options {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.5rem;
  color: var(--color-text-secondary);
  font-size: 0.9rem;
}

.cart-item-prices {
  display: flex;
  gap: 1rem;
  margin-bottom: 0.5rem;
  color: var(--color-text-secondary);
  font-size: 0.9rem;
}

.regular {
  color: var(--color-text-primary);
  font-weight: 600;
}

.member {
  color: var(--color-primary);
  font-weight: 600;
}

.cart-item-actions {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: flex-end;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.quantity-btn {
  width: 30px;
  height: 30px;
  border: 1px solid var(--color-border);
  background-color: white;
  border-radius: 4px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: 600;
  transition: all 0.3s ease;
}

.quantity-btn:hover {
  border-color: var(--color-primary);
  background-color: var(--color-background-light);
}

.quantity {
  width: 40px;
  text-align: center;
  font-weight: 600;
}

.remove-btn {
  background-color: transparent;
  color: var(--color-error);
  border: 1px solid var(--color-error);
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.remove-btn:hover {
  background-color: var(--color-error);
  color: white;
}

.empty-cart {
  flex: 1;
  min-width: 300px;
  background-color: white;
  border-radius: 8px;
  padding: 4rem 2rem;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.empty-cart h3 {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
  color: var(--color-text-primary);
}

.empty-cart p {
  margin-bottom: 2rem;
  color: var(--color-text-secondary);
}

.cart-summary {
  flex: 1;
  min-width: 300px;
  max-width: 400px;
  background-color: white;
  border-radius: 8px;
  padding: 2rem;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.summary-section h3 {
  font-size: 1.25rem;
  margin-bottom: 1.5rem;
  color: var(--color-text-primary);
  border-bottom: 1px solid var(--color-border);
  padding-bottom: 0.5rem;
}

.summary-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
  color: var(--color-text-secondary);
}

.summary-item.total {
  font-size: 1.25rem;
  font-weight: 600;
  margin-top: 1.5rem;
  padding-top: 1rem;
  border-top: 1px solid var(--color-border);
}

.summary-value.total-amount {
  color: var(--color-primary);
  font-size: 1.5rem;
}

.checkout-section {
  margin-top: 2rem;
}

.checkout-warning {
  background-color: var(--color-warning-light);
  color: var(--color-warning);
  padding: 1rem;
  border-radius: 4px;
  margin-bottom: 1rem;
}

.checkout-btn {
  width: 100%;
  padding: 1rem;
  background-color: var(--color-primary);
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.checkout-btn:hover {
  background-color: var(--color-primary-dark);
}

.checkout-btn:disabled {
  background-color: var(--color-border);
  cursor: not-allowed;
}

.order-history {
  margin-top: 4rem;
  background-color: white;
  border-radius: 8px;
  padding: 2rem;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.history-title {
  font-size: 1.5rem;
  margin-bottom: 2rem;
  color: var(--color-text-primary);
  border-bottom: 1px solid var(--color-border);
  padding-bottom: 0.5rem;
}

.order-item {
  border-bottom: 1px solid var(--color-border);
  padding: 1.5rem 0;
}

.order-item:last-child {
  border-bottom: none;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  font-size: 0.9rem;
}

.order-id {
  font-weight: 600;
  color: var(--color-text-primary);
}

.order-date {
  color: var(--color-text-secondary);
}

.order-status {
  background-color: var(--color-success-light);
  color: var(--color-success);
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.8rem;
}

.order-items {
  margin-bottom: 1rem;
}

.order-item-summary {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  color: var(--color-text-secondary);
}

.order-total {
  text-align: right;
  font-weight: 600;
  color: var(--color-text-primary);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .cart-content {
    flex-direction: column;
  }
  
  .cart-item-info {
    flex-direction: column;
  }
  
  .cart-item-actions {
    flex-direction: row;
    width: 100%;
    justify-content: space-between;
    align-items: center;
  }
  
  .cart-summary {
    max-width: none;
  }
}
</style>