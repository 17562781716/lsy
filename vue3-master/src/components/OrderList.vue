<template>
  <div class="order-list">
    <div class="order-filters">
      <div class="filter-group">
        <label for="orderStatus">订单状态</label>
        <select 
          id="orderStatus" 
          v-model="filters.status"
          class="form-input"
          @change="handleFilterChange"
        >
          <option value="">全部</option>
          <!-- 根据记录类型显示不同的状态选项 -->
          <template v-if="recordType === 'borrow'">
            <option value="borrowed">已借阅</option>
            <option value="returned">已归还</option>
            <option value="overdue">逾期</option>
          </template>
          <template v-else-if="recordType === 'purchase'">
            <option value="pending">待付款</option>
            <option value="paid">已付款</option>
            <option value="shipped">已发货</option>
            <option value="received">已收货</option>
          </template>
          <template v-else-if="recordType === 'consume'">
            <option value="completed">已完成</option>
            <option value="cancelled">已取消</option>
          </template>
          <template v-else-if="recordType === 'recharge'">
            <option value="success">成功</option>
            <option value="failed">失败</option>
          </template>
        </select>
      </div>
      <div class="filter-group">
        <label for="orderDate">{{ getDateLabel() }}</label>
        <input 
          type="date" 
          id="orderDate" 
          v-model="filters.date"
          class="form-input"
          @change="handleFilterChange"
        >
      </div>
    </div>
    
    <div class="order-table-container">
      <table class="order-table">
        <thead>
          <tr>
            <th>订单编号</th>
            <!-- 根据记录类型显示不同的列 -->
            <template v-if="recordType === 'borrow'">
              <th>书籍信息</th>
              <th>借阅日期</th>
              <th>应还日期</th>
              <th>实际归还日期</th>
            </template>
            <template v-else-if="recordType === 'purchase'">
              <th>商品信息</th>
              <th>购买日期</th>
              <th>数量</th>
              <th>单价</th>
            </template>
            <template v-else-if="recordType === 'consume'">
              <th>消费内容</th>
              <th>消费日期</th>
              <th>消费地点</th>
            </template>
            <template v-else-if="recordType === 'recharge'">
              <th>充值金额</th>
              <th>充值日期</th>
              <th>充值方式</th>
            </template>
            <th>总金额</th>
            <th>订单状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in filteredOrders" :key="order.id">
            <td class="order-id">{{ order.id }}</td>
            <!-- 根据记录类型显示不同的内容 -->
            <template v-if="recordType === 'borrow'">
              <td class="book-info">
                <div class="book-details">
                  <div class="book-title">{{ order.book.title }}</div>
                  <div class="book-author">{{ order.book.author }}</div>
                </div>
              </td>
              <td>{{ formatDate(order.borrowDate) }}</td>
              <td>{{ formatDate(order.dueDate) }}</td>
              <td>{{ order.returnDate ? formatDate(order.returnDate) : '-' }}</td>
            </template>
            <template v-else-if="recordType === 'purchase'">
              <td class="book-info">
                <div class="book-details">
                  <div class="book-title">{{ order.item.name }}</div>
                  <div class="book-author">{{ order.item.category }}</div>
                </div>
              </td>
              <td>{{ formatDate(order.purchaseDate) }}</td>
              <td>{{ order.quantity }}</td>
              <td>¥{{ order.price.toFixed(2) }}</td>
            </template>
            <template v-else-if="recordType === 'consume'">
              <td>{{ order.content }}</td>
              <td>{{ formatDate(order.consumeDate) }}</td>
              <td>{{ order.location }}</td>
            </template>
            <template v-else-if="recordType === 'recharge'">
              <td>¥{{ order.amount.toFixed(2) }}</td>
              <td>{{ formatDate(order.rechargeDate) }}</td>
              <td>{{ order.method }}</td>
            </template>
            <td>¥{{ order.totalAmount.toFixed(2) }}</td>
            <td>
              <span 
                class="status-badge" 
                :class="`status-${order.status}`"
              >
                {{ getStatusText(order.status) }}
              </span>
            </td>
            <td>
              <!-- 根据记录类型显示不同的操作 -->
              <template v-if="recordType === 'borrow'">
                <button 
                  v-if="order.status === 'borrowed'"
                  class="btn btn-primary btn-small"
                  @click="handleReturnOrder(order.id)"
                >
                  归还
                </button>
                <button 
                  v-else-if="order.status === 'overdue'"
                  class="btn btn-danger btn-small"
                  @click="handleReturnOrder(order.id)"
                >
                  立即归还
                </button>
                <span v-else class="no-action">-</span>
              </template>
              <template v-else-if="recordType === 'purchase'">
                <button 
                  v-if="order.status === 'pending'"
                  class="btn btn-primary btn-small"
                  @click="handlePayOrder(order.id)"
                >
                  付款
                </button>
                <button 
                  v-else-if="order.status === 'shipped'"
                  class="btn btn-primary btn-small"
                  @click="handleConfirmReceive(order.id)"
                >
                  确认收货
                </button>
                <span v-else class="no-action">-</span>
              </template>
              <template v-else>
                <span class="no-action">-</span>
              </template>
            </td>
          </tr>
        </tbody>
      </table>
      
      <div v-if="filteredOrders.length === 0" class="empty-state">
        <span class="empty-icon">{{ getEmptyIcon() }}</span>
        <p>{{ getEmptyText() }}</p>
      </div>
    </div>
    
    <!-- 分页 -->
    <div v-if="filteredOrders.length > 0" class="pagination">
      <button 
        class="btn btn-secondary btn-small"
        :disabled="currentPage === 1"
        @click="currentPage--"
      >
        上一页
      </button>
      <span class="page-info">
        第 {{ currentPage }} / {{ totalPages }} 页
      </span>
      <button 
        class="btn btn-secondary btn-small"
        :disabled="currentPage === totalPages"
        @click="currentPage++"
      >
        下一页
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, defineProps } from 'vue';
import { useUserStore } from '../stores/user';
import { useCartStore } from '../stores/cart';

const props = defineProps({
  recordType: {
    type: String,
    default: 'borrow',
    validator: (value) => ['borrow', 'purchase', 'consume', 'recharge'].includes(value)
  }
});

const userStore = useUserStore();
const cartStore = useCartStore();

// 模拟订单数据
const orders = ref([]);

// 过滤器
const filters = ref({
  status: '',
  date: ''
});

// 分页
const currentPage = ref(1);
const pageSize = 5;

// 格式化日期
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('zh-CN');
};

// 获取日期标签
const getDateLabel = () => {
  const labels = {
    borrow: '借阅日期',
    purchase: '购买日期',
    consume: '消费日期',
    recharge: '充值日期'
  };
  return labels[props.recordType] || '日期';
};

// 获取空状态图标
const getEmptyIcon = () => {
  const icons = {
    borrow: '📚',
    purchase: '🛒',
    consume: '☕',
    recharge: '💳'
  };
  return icons[props.recordType] || '📋';
};

// 获取空状态文本
const getEmptyText = () => {
  const texts = {
    borrow: '暂无借阅记录',
    purchase: '暂无购书记录',
    consume: '暂无消费记录',
    recharge: '暂无充值记录'
  };
  return texts[props.recordType] || '暂无记录';
};

// 获取状态文本
const getStatusText = (status) => {
  const statusMaps = {
    borrow: {
      borrowed: '已借阅',
      returned: '已归还',
      overdue: '逾期'
    },
    purchase: {
      pending: '待付款',
      paid: '已付款',
      shipped: '已发货',
      received: '已收货'
    },
    consume: {
      completed: '已完成',
      cancelled: '已取消'
    },
    recharge: {
      success: '成功',
      failed: '失败'
    }
  };
  return statusMaps[props.recordType][status] || status;
};

// 过滤订单
const filteredOrders = computed(() => {
  let result = orders.value;
  
  // 按状态过滤
  if (filters.value.status) {
    result = result.filter(order => order.status === filters.value.status);
  }
  
  // 按日期过滤
  if (filters.value.date) {
    const filterDate = new Date(filters.value.date);
    filterDate.setHours(0, 0, 0, 0);
    
    result = result.filter(order => {
      let orderDate;
      // 根据记录类型获取对应的日期字段
      if (props.recordType === 'borrow') {
        orderDate = new Date(order.borrowDate);
      } else if (props.recordType === 'purchase') {
        orderDate = new Date(order.purchaseDate);
      } else if (props.recordType === 'consume') {
        orderDate = new Date(order.consumeDate);
      } else if (props.recordType === 'recharge') {
        orderDate = new Date(order.rechargeDate);
      }
      
      if (orderDate) {
        orderDate.setHours(0, 0, 0, 0);
        return orderDate.getTime() === filterDate.getTime();
      }
      return false;
    });
  }
  
  return result;
});

// 分页订单
const paginatedOrders = computed(() => {
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return filteredOrders.value.slice(start, end);
});

// 总页数
const totalPages = computed(() => {
  return Math.ceil(filteredOrders.value.length / pageSize);
});

// 处理过滤器变化
const handleFilterChange = () => {
  currentPage.value = 1; // 重置到第一页
};

// 处理支付订单
const handlePayOrder = async (orderId) => {
  try {
    // 模拟支付请求
    await new Promise(resolve => setTimeout(resolve, 500));
    
    // 更新订单状态
    const orderIndex = orders.value.findIndex(order => order.id === orderId);
    if (orderIndex !== -1) {
      orders.value[orderIndex].status = 'paid';
      
      // 更新本地缓存
      localStorage.setItem(`user_${props.recordType}_orders`, JSON.stringify(orders.value));
      
      alert('支付成功');
    }
  } catch (error) {
    console.error('支付失败:', error);
    alert('支付失败，请稍后重试');
  }
};

// 处理确认收货
const handleConfirmReceive = async (orderId) => {
  try {
    // 模拟确认收货请求
    await new Promise(resolve => setTimeout(resolve, 500));
    
    // 更新订单状态
    const orderIndex = orders.value.findIndex(order => order.id === orderId);
    if (orderIndex !== -1) {
      orders.value[orderIndex].status = 'received';
      
      // 更新本地缓存
      localStorage.setItem(`user_${props.recordType}_orders`, JSON.stringify(orders.value));
      
      alert('确认收货成功');
    }
  } catch (error) {
    console.error('确认收货失败:', error);
    alert('确认收货失败，请稍后重试');
  }
};

// 处理归还订单
const handleReturnOrder = async (orderId) => {
  try {
    // 模拟 API 请求
    await new Promise(resolve => setTimeout(resolve, 500));
    
    // 更新订单状态
    const orderIndex = orders.value.findIndex(order => order.id === orderId);
    if (orderIndex !== -1) {
      orders.value[orderIndex] = {
        ...orders.value[orderIndex],
        status: 'returned',
        returnDate: new Date().toISOString()
      };
      
      // 更新本地缓存
      localStorage.setItem(`user_${props.recordType}_orders`, JSON.stringify(orders.value));
      
      alert('归还成功');
    }
  } catch (error) {
    console.error('归还失败:', error);
    alert('归还失败，请稍后重试');
  }
};

// 生成模拟数据
const generateMockOrders = () => {
  if (props.recordType === 'borrow') {
    return generateBorrowOrders();
  } else if (props.recordType === 'purchase') {
    return generatePurchaseOrders();
  } else if (props.recordType === 'consume') {
    return generateConsumeOrders();
  } else if (props.recordType === 'recharge') {
    return generateRechargeOrders();
  }
  return [];
};

// 生成借阅记录
const generateBorrowOrders = () => {
  const mockBooks = [
    { id: '1', title: 'JavaScript高级程序设计', author: 'Nicholas C. Zakas', cover: '' },
    { id: '2', title: 'Vue.js设计与实现', author: '霍春阳', cover: '' },
    { id: '3', title: 'TypeScript入门教程', author: '阮一峰', cover: '' },
    { id: '4', title: '数据结构与算法分析', author: 'Mark Allen Weiss', cover: '' },
    { id: '5', title: '深入理解计算机系统', author: 'Randal E. Bryant', cover: '' },
    { id: '6', title: '算法导论', author: 'Thomas H. Cormen', cover: '' },
    { id: '7', title: '人类简史', author: '尤瓦尔·赫拉利', cover: '' },
    { id: '8', title: '百年孤独', author: '加西亚·马尔克斯', cover: '' }
  ];
  
  const mockOrders = [];
  let overdueCount = 0; // 跟踪逾期记录数量
  
  // 检查当前用户是否为17562781716
  const currentUserStr = sessionStorage.getItem('current_user');
  const isTargetUser = currentUserStr ? JSON.parse(currentUserStr).username === '17562781716' : false;
  
  for (let i = 0; i < 15; i++) {
    // 只生成两个逾期记录，其余为已归还或已借阅
    let status;
    if (overdueCount < 2 && Math.random() < 0.15) {
      status = 'overdue';
      overdueCount++;
    } else {
      status = Math.random() < 0.5 ? 'borrowed' : 'returned';
    }
    
    // 设置借阅日期在最近15天内
    const borrowDate = new Date();
    borrowDate.setDate(borrowDate.getDate() - Math.floor(Math.random() * 15));
    
    // 设置应还日期为借阅后7天
    const dueDate = new Date(borrowDate);
    dueDate.setDate(dueDate.getDate() + 7);
    
    let returnDate = null;
    let totalAmount = 0;
    
    if (isTargetUser) {
      // 针对用户17562781716，严格按照"一天一本一块钱"规则计算
      if (status === 'returned') {
        // 设置归还日期在借阅后1-15天内
        returnDate = new Date(borrowDate);
        const borrowDays = Math.floor(Math.random() * 15) + 1;
        returnDate.setDate(returnDate.getDate() + borrowDays);
        
        // 计算实际借阅天数（一天一本一块钱）
        const daysDiff = Math.ceil((returnDate - borrowDate) / (1000 * 60 * 60 * 24));
        // 控制金额在15元以内
        totalAmount = Math.min(daysDiff, 15);
      } else if (status === 'borrowed' || status === 'overdue') {
        // 对于已借阅或逾期的书籍，计算从借阅日到现在的天数
        const today = new Date();
        const daysDiff = Math.ceil((today - borrowDate) / (1000 * 60 * 60 * 24));
        // 控制金额在15元以内
        totalAmount = Math.min(daysDiff, 15);
      }
    } else {
      // 其他用户保持原有逻辑
      if (status === 'returned') {
        // 设置归还日期在借阅后1-7天内
        returnDate = new Date(borrowDate);
        returnDate.setDate(returnDate.getDate() + Math.floor(Math.random() * 7) + 1);
      } else if (status === 'overdue') {
        // 控制逾期天数不超过15天
        const overdueDays = Math.floor(Math.random() * 15) + 1; // 1-15天
        totalAmount = overdueDays;
      }
    }
    
    mockOrders.push({
      id: `BOR${Date.now().toString().slice(-6)}${i}`,
      book: mockBooks[Math.floor(Math.random() * mockBooks.length)],
      borrowDate: borrowDate.toISOString(),
      dueDate: dueDate.toISOString(),
      returnDate: returnDate ? returnDate.toISOString() : null,
      status: status,
      totalAmount: totalAmount
    });
  }
  
  return mockOrders;
};

// 生成购书记录
const generatePurchaseOrders = () => {
  const mockItems = [
    { id: '1', name: 'JavaScript高级程序设计', category: '技术书籍', cover: '' },
    { id: '2', name: 'Vue.js设计与实现', category: '技术书籍', cover: '' },
    { id: '3', name: '人类简史', category: '历史书籍', cover: '' },
    { id: '4', name: '百年孤独', category: '文学书籍', cover: '' },
    { id: '5', name: '活着', category: '文学书籍', cover: '' },
    { id: '6', name: '小王子', category: '童话书籍', cover: '' }
  ];
  
  const mockOrders = [];
  const statuses = ['pending', 'paid', 'shipped', 'received'];
  
  for (let i = 0; i < 15; i++) {
    const status = statuses[Math.floor(Math.random() * statuses.length)];
    const purchaseDate = new Date();
    purchaseDate.setDate(purchaseDate.getDate() - Math.floor(Math.random() * 90));
    
    const item = mockItems[Math.floor(Math.random() * mockItems.length)];
    const quantity = Math.floor(Math.random() * 3) + 1;
    const price = Math.floor(Math.random() * 100) + 20;
    
    mockOrders.push({
      id: `PUR${Date.now().toString().slice(-6)}${i}`,
      item: item,
      purchaseDate: purchaseDate.toISOString(),
      quantity: quantity,
      price: price,
      totalAmount: quantity * price,
      status: status
    });
  }
  
  return mockOrders;
};

// 生成消费记录
const generateConsumeOrders = () => {
  const mockContents = [
    '美式咖啡',
    '拿铁咖啡',
    '卡布奇诺',
    '摩卡咖啡',
    '巧克力蛋糕',
    '提拉米苏',
    '三明治',
    '沙拉'
  ];
  
  const mockLocations = [
    '书店咖啡区',
    '书店休息区',
    '线上订单'
  ];
  
  const mockOrders = [];
  const statuses = ['completed', 'cancelled'];
  
  for (let i = 0; i < 15; i++) {
    const status = statuses[Math.floor(Math.random() * statuses.length)];
    const consumeDate = new Date();
    consumeDate.setDate(consumeDate.getDate() - Math.floor(Math.random() * 30));
    
    const content = mockContents[Math.floor(Math.random() * mockContents.length)];
    const location = mockLocations[Math.floor(Math.random() * mockLocations.length)];
    const amount = Math.floor(Math.random() * 50) + 15;
    
    mockOrders.push({
      id: `CON${Date.now().toString().slice(-6)}${i}`,
      content: content,
      location: location,
      consumeDate: consumeDate.toISOString(),
      totalAmount: status === 'completed' ? amount : 0,
      status: status
    });
  }
  
  return mockOrders;
};

// 生成充值记录
const generateRechargeOrders = () => {
  const mockMethods = [
    '微信支付',
    '支付宝',
    '银行卡'
  ];
  
  // 检查当前用户是否为17562781716
  const currentUserStr = sessionStorage.getItem('current_user');
  const isTargetUser = currentUserStr ? JSON.parse(currentUserStr).username === '17562781716' : false;
  
  const mockOrders = [];
  
  for (let i = 0; i < 15; i++) {
    const method = mockMethods[Math.floor(Math.random() * mockMethods.length)];
    const rechargeDate = new Date();
    rechargeDate.setDate(rechargeDate.getDate() - Math.floor(Math.random() * 180));
    
    let amount;
    let status;
    
    if (isTargetUser) {
      // 针对用户17562781716，只保留300、400、500的充值金额，且状态全部为成功
      amount = [300, 400, 500][Math.floor(Math.random() * 3)];
      status = 'success';
    } else {
      // 其他用户保持原有逻辑
      status = 'success'; // 只使用success状态
      amount = [50, 100, 200, 500, 1000][Math.floor(Math.random() * 5)];
    }
    
    mockOrders.push({
      id: `REC${Date.now().toString().slice(-6)}${i}`,
      method: method,
      rechargeDate: rechargeDate.toISOString(),
      amount: amount,
      totalAmount: status === 'success' ? amount : 0,
      status: status
    });
  }
  
  return mockOrders;
};

// 加载订单数据
const loadOrders = () => {
  // 检查当前用户是否为17562781716
  const currentUserStr = sessionStorage.getItem('current_user');
  const isTargetUser = currentUserStr ? JSON.parse(currentUserStr).username === '17562781716' : false;
  
  // 如果是消费记录，使用购物车的真实订单历史
  if (props.recordType === 'consume') {
    // 从cart store获取订单历史
    const cartOrders = cartStore.orderHistory;
    
    // 转换为OrderList需要的格式
    orders.value = cartOrders.map(order => {
      // 合并所有商品名称
      const content = order.items.map(item => `${item.name} x${item.quantity}`).join(', ');
      
      return {
        id: order.id,
        content: content,
        location: '云边书吧咖啡区', // 固定地点
        consumeDate: order.date,
        totalAmount: order.totalAmount,
        status: order.status
      };
    });
    
    // 缓存数据
    localStorage.setItem(`user_${props.recordType}_orders`, JSON.stringify(orders.value));
    return;
  }
  
  // 如果是目标用户，无论是借阅记录还是充值记录，始终重新生成数据，不使用缓存
  if (isTargetUser && (props.recordType === 'borrow' || props.recordType === 'recharge')) {
    orders.value = generateMockOrders();
    // 缓存更新后的数据
    localStorage.setItem(`user_${props.recordType}_orders`, JSON.stringify(orders.value));
    return;
  }
  
  // 非目标用户或其他记录类型，尝试从localStorage加载数据
  const cachedOrders = localStorage.getItem(`user_${props.recordType}_orders`);
  
  if (cachedOrders) {
    try {
      orders.value = JSON.parse(cachedOrders);
      return;
    } catch (error) {
      console.error('解析订单数据失败:', error);
    }
  }
  
  // 加载失败或没有缓存，生成新数据
  orders.value = generateMockOrders();
  // 缓存数据
  localStorage.setItem(`user_${props.recordType}_orders`, JSON.stringify(orders.value));
};

// 组件挂载时加载数据
onMounted(() => {
  loadOrders();
});
</script>

<style scoped>
.order-list {
  width: 100%;
}

.order-filters {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.filter-group label {
  color: var(--text-primary);
  font-weight: 500;
}

.filter-group .form-input {
  min-width: 150px;
}

.order-table-container {
  overflow-x: auto;
  margin-bottom: 1.5rem;
  border-radius: var(--border-radius);
  border: 1px solid var(--border-color);
}

.order-table {
  width: 100%;
  border-collapse: collapse;
  background-color: var(--card-background);
}

.order-table th,
.order-table td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid var(--border-color);
}

.order-table th {
  background-color: var(--background-secondary);
  font-weight: bold;
  color: var(--text-primary);
  white-space: nowrap;
}

.order-id {
  font-family: monospace;
  font-size: 0.9rem;
  color: var(--text-secondary);
}

.book-info {
  padding: 0.5rem 0;
}

.book-cover-container {
  flex-shrink: 0;
}

.book-cover {
  width: 50px;
  height: 70px;
  object-fit: cover;
  border-radius: 4px;
  border: 1px solid var(--border-color);
}

.book-cover-placeholder {
  width: 50px;
  height: 70px;
  background-color: #e0e0e0;
  color: #666666;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
  font-weight: bold;
}

.book-details {
  flex: 1;
  min-width: 0;
}

.book-title {
  font-weight: 500;
  color: var(--text-primary);
  margin-bottom: 0.25rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.book-author {
  font-size: 0.9rem;
  color: var(--text-secondary);
}

.status-badge {
  padding: 0.25rem 0.5rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
  white-space: nowrap;
}

.status-borrowed {
  background-color: rgba(44, 62, 80, 0.1);
  color: #2c3e50;
}

.status-returned {
  background-color: rgba(76, 175, 80, 0.1);
  color: #4CAF50;
}

.status-overdue {
  background-color: rgba(244, 67, 54, 0.1);
  color: var(--red-color);
}

.btn-small {
  padding: 0.25rem 0.5rem;
  font-size: 0.8rem;
}

.no-action {
  color: var(--text-secondary);
}

.empty-state {
  text-align: center;
  padding: 3rem;
  color: var(--text-secondary);
}

.empty-icon {
  font-size: 3rem;
  display: block;
  margin-bottom: 1rem;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
}

.page-info {
  color: var(--text-secondary);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .order-filters {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .order-table {
    font-size: 0.9rem;
  }
  
  .order-table th,
  .order-table td {
    padding: 0.5rem;
  }
  
  .book-info {
    gap: 0.5rem;
  }
  
  .book-cover {
    width: 40px;
    height: 60px;
  }
  
  .book-cover-placeholder {
    width: 40px;
    height: 60px;
  }
  
  .btn-small {
    padding: 0.25rem 0.5rem;
    font-size: 0.7rem;
  }
}
</style>