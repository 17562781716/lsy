<template>
  <section class="coffee-section">
    <div class="container">
      <div class="section-header">
        <h1 class="section-title">咖啡时光</h1>
        <p class="section-description">品味香浓咖啡，享受阅读时光</p>
      </div>
      
      <div class="coffee-categories">
        <div 
          v-for="category in coffeeCategories" 
          :key="category.id"
          class="coffee-category"
        >
          <h2 class="category-title">{{ category.name }}</h2>
          <div class="coffee-menu">
            <Card 
              v-for="coffee in category.coffees" 
              :key="`${category.id}-${coffee.id}`"
              class="coffee-card"
              @click="selectCoffee(coffee)"
            >
              <div class="coffee-header">
                <h3 class="coffee-name">{{ coffee.name }}</h3>
              </div>
              <div class="coffee-content">
                <p class="coffee-description">{{ coffee.description }}</p>
              </div>
              <div class="coffee-prices">
                <div class="price-item">
                  <span class="price-label">非会员价：</span>
                  <span class="price-value regular">{{ coffee.regularPrice }}</span>
                  <span class="price-divider"> | </span>
                  <span class="price-label">会员价：</span>
                  <span class="price-value member">{{ coffee.memberPrice }}</span>
                </div>
              </div>
            </Card>
          </div>
        </div>
      </div>
    </div>

    <!-- 咖啡结账弹窗 -->
    <div v-if="selectedCoffee" class="checkout-modal-overlay" @click="closeModal">
      <div class="checkout-modal" @click.stop>
        <div class="modal-header">
          <h3>{{ selectedCoffee.name }}</h3>
          <button class="close-btn" @click="closeModal">&times;</button>
        </div>
        <!-- 选项选择步骤 -->
        <div class="modal-body">
          <p class="coffee-desc">{{ selectedCoffee.description }}</p>
          <div class="coffee-prices-modal">
            <div class="price-item">
              <span class="price-label">非会员价：</span>
              <span class="price-value regular">{{ selectedCoffee.regularPrice }}</span>
            </div>
            <div class="price-item">
              <span class="price-label">会员价：</span>
              <span class="price-value member">{{ selectedCoffee.memberPrice }}</span>
            </div>
          </div>
          
          <!-- 温度选择 -->
          <div class="option-group">
            <h4>温度选择</h4>
            <div class="option-selector">
              <label v-for="temperature in temperatureOptions" :key="temperature.value" class="option-item">
                <input 
                  type="radio" 
                  v-model="selectedOptions.temperature" 
                  :value="temperature.value"
                >
                <span>{{ temperature.label }}</span>
              </label>
            </div>
          </div>
          
          <!-- 糖度选择 -->
          <div class="option-group">
            <h4>糖度选择</h4>
            <div class="option-selector">
              <label v-for="sugar in sugarOptions" :key="sugar.value" class="option-item">
                <input 
                  type="radio" 
                  v-model="selectedOptions.sugar" 
                  :value="sugar.value"
                >
                <span>{{ sugar.label }}</span>
              </label>
            </div>
          </div>
        </div>
        

        
        <!-- 动态底部按钮 -->
        <div class="modal-footer">
          <!-- 选项选择步骤的按钮 -->
          <button class="btn-secondary" @click="closeModal">取消</button>
          <button class="btn-primary" @click="addToCart">加入购物车</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import Card from '../components/Card.vue';
import { useCartStore } from '../stores/cart.js';

// 引入购物车store
const cartStore = useCartStore();

// 咖啡选择状态
const selectedCoffee = ref(null);
const selectedOptions = ref({
  temperature: 'hot',
  sugar: 'normal'
});

// 温度选项
const temperatureOptions = ref([
  { value: 'hot', label: '热饮' },
  { value: 'iced', label: '冰饮' },
  { value: 'warm', label: '温饮' }
]);

// 糖度选项
const sugarOptions = ref([
  { value: 'no', label: '无糖' },
  { value: 'less', label: '少糖' },
  { value: 'normal', label: '正常糖' },
  { value: 'more', label: '多糖' }
]);

// 选择咖啡
const selectCoffee = (coffee) => {
  selectedCoffee.value = coffee;
  // 重置默认选项
  selectedOptions.value = {
    temperature: 'hot',
    sugar: 'normal'
  };
};

// 关闭弹窗
const closeModal = () => {
  selectedCoffee.value = null;
};

// 添加到购物车
const addToCart = () => {
  if (selectedCoffee.value) {
    cartStore.addToCart(selectedCoffee.value, selectedOptions.value);
    
    // 获取温度和糖度的中文名称
    const temperatureLabel = temperatureOptions.value.find(t => t.value === selectedOptions.value.temperature)?.label;
    const sugarLabel = sugarOptions.value.find(s => s.value === selectedOptions.value.sugar)?.label;
    
    // 显示确认信息
    alert(`已添加到购物车！\n\n您的选择：\n${selectedCoffee.value.name}\n温度：${temperatureLabel}\n糖度：${sugarLabel}`);
    closeModal();
  }
};

// 咖啡菜单数据（分类展示）
const coffeeCategories = ref([
  {
    id: 1,
    name: '经典咖啡',
    coffees: [
      {
        id: 1,
        name: '美式咖啡',
        description: '经典黑咖啡，醇厚浓郁',
        regularPrice: '¥22',
        memberPrice: '¥18'
      },
      {
        id: 2,
        name: '拿铁咖啡',
        description: '浓缩咖啡搭配丝滑牛奶，奶香浓郁',
        regularPrice: '¥28',
        memberPrice: '¥23'
      },
      {
        id: 3,
        name: '卡布奇诺',
        description: '浓缩咖啡与蒸汽牛奶的完美结合，顶部有奶泡',
        regularPrice: '¥28',
        memberPrice: '¥23'
      },
      {
        id: 4,
        name: '摩卡咖啡',
        description: '浓缩咖啡、巧克力酱、牛奶和鲜奶油的组合',
        regularPrice: '¥32',
        memberPrice: '¥26'
      },
      {
        id: 5,
        name: '焦糖玛奇朵',
        description: '浓缩咖啡与牛奶的经典组合，顶部有焦糖酱',
        regularPrice: '¥34',
        memberPrice: '¥28'
      },
      {
        id: 6,
        name: '冰美式咖啡',
        description: '夏日必备，经典黑咖啡加冰',
        regularPrice: '¥24',
        memberPrice: '¥20'
      }
    ]
  },
  {
    id: 2,
    name: '特调系列',
    coffees: [
      {
        id: 1,
        name: '苹果流沙美式',
        description: '苹果风味与美式咖啡的创新融合，口感层次丰富',
        regularPrice: '¥38',
        memberPrice: '¥32'
      },
      {
        id: 2,
        name: '咸奶油美式',
        description: '咸甜交错的奶油与美式咖啡碰撞出独特风味',
        regularPrice: '¥36',
        memberPrice: '¥30'
      },
      {
        id: 3,
        name: '茉莉美式',
        description: '茉莉花茶的清香与美式咖啡的醇厚完美结合',
        regularPrice: '¥35',
        memberPrice: '¥29'
      },
      {
        id: 4,
        name: '巧克力可可拿铁',
        description: '浓郁巧克力与丝滑拿铁的甜蜜组合',
        regularPrice: '¥38',
        memberPrice: '¥32'
      },
      {
        id: 5,
        name: '抹茶dirty',
        description: '抹茶的清香与浓缩咖啡的醇厚在杯中交织',
        regularPrice: '¥40',
        memberPrice: '¥34'
      },
      {
        id: 6,
        name: '山楂黄油dirty',
        description: '酸甜山楂与浓郁黄油碰撞出独特的dirty风味',
        regularPrice: '¥42',
        memberPrice: '¥36'
      }
    ]
  }
]);
</script>

<style scoped>
.coffee-section {
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

.section-description {
  font-size: 1.25rem;
  color: var(--color-text-secondary);
  max-width: 600px;
  margin: 0 auto;
}

.coffee-categories {
  display: flex;
  flex-direction: column;
  gap: 4rem;
}

.coffee-category {
  margin-bottom: 2rem;
}

.category-title {
  font-size: 1.8rem;
  font-weight: 600;
  color: var(--color-text-primary);
  margin-bottom: 2rem;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid #2c3e50;
  display: inline-block;
}

.coffee-menu {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2rem;
}

.coffee-card {
  padding: 2rem;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.coffee-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.coffee-header {
  margin-bottom: 1rem;
}

.coffee-name {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--color-text-primary);
  margin: 0;
}

.coffee-content {
  margin-bottom: 1.5rem;
}

.coffee-description {
  color: var(--color-text-secondary);
  line-height: 1.6;
  margin: 0;
}

.coffee-prices {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding-top: 1rem;
  border-top: 1px solid var(--color-border);
}

.price-item {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.price-label {
  font-size: 0.9rem;
  color: var(--color-text-secondary);
  margin-right: 0.5rem;
}

.price-divider {
  color: var(--color-border);
  margin: 0 0.5rem;
}

.price-value {
  font-size: 1.2rem;
  font-weight: 600;
}

.price-value.regular {
  color: var(--color-text-primary);
}

.price-value.member {
  color: #2c3e50;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .coffee-section {
    padding: 2rem 0;
  }
  
  .section-title {
    font-size: 2rem;
  }
  
  .section-description {
    font-size: 1rem;
  }
  
  .coffee-menu {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
  
  .coffee-card {
    padding: 1.5rem;
  }
  
  .coffee-prices {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }
  
  .price-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }
  
  .price-divider {
    display: none;
  }
}

/* 弹窗样式 */
.checkout-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.checkout-modal {
  background-color: white;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  animation: modalSlideIn 0.3s ease;
}

@keyframes modalSlideIn {
  from {
    opacity: 0;
    transform: translateY(-30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem;
  border-bottom: 1px solid var(--color-border);
  background-color: var(--color-background-light);
}

.modal-header h3 {
  margin: 0;
  color: var(--color-text-primary);
  font-size: 1.5rem;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: var(--color-text-secondary);
  cursor: pointer;
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.close-btn:hover {
  background-color: var(--color-border);
}

.modal-body {
  padding: 1.5rem;
}

.coffee-desc {
  color: var(--color-text-secondary);
  margin-bottom: 1.5rem;
  line-height: 1.6;
}

.coffee-prices-modal {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid var(--color-border);
}

.option-group {
  margin-bottom: 1.5rem;
}

.option-group h4 {
  margin-bottom: 1rem;
  color: var(--color-text-primary);
  font-size: 1.1rem;
}

.option-selector {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.option-item {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0.5rem 1rem;
  border: 1px solid var(--color-border);
  border-radius: 20px;
  transition: all 0.3s ease;
}

.option-item:hover {
  border-color: #2c3e50;
}

.option-item input[type="radio"] {
  margin-right: 0.5rem;
}

.option-item input[type="radio"]:checked + span {
  color: #2c3e50;
  font-weight: 600;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  padding: 1.5rem;
  border-top: 1px solid var(--color-border);
  background-color: var(--color-background-light);
}

.btn-secondary, .btn-primary {
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  border: none;
}

.btn-secondary {
  background-color: var(--color-border);
  color: var(--color-text-primary);
}

.btn-secondary:hover {
  background-color: var(--color-text-secondary);
  color: white;
}

.btn-primary {
  background-color: #2c3e50;
  color: white;
}

.btn-primary:hover {
  background-color: #34495e;
}

/* 确认订单部分样式 */
.confirm-section {
  margin-bottom: 1.5rem;
}

.confirm-section h4 {
  margin-bottom: 1rem;
  color: var(--color-text-primary);
  font-size: 1.1rem;
  border-bottom: 1px solid var(--color-border);
  padding-bottom: 0.5rem;
}

.confirm-item {
  display: flex;
  margin-bottom: 0.75rem;
  align-items: flex-start;
}

.confirm-label {
  width: 80px;
  font-weight: 600;
  color: var(--color-text-primary);
  margin-right: 1rem;
}

.confirm-value {
  color: var(--color-text-secondary);
  flex: 1;
  word-break: break-word;
}

.confirm-value.regular {
  color: var(--color-text-primary);
  font-weight: 600;
}

.confirm-value.member {
  color: #2c3e50;
  font-weight: 600;
}

.confirm-prices {
  padding-top: 0.75rem;
  margin-top: 0.75rem;
  border-top: 1px solid var(--color-border);
}

.confirm-note {
  background-color: var(--color-background-light);
  padding: 1rem;
  border-radius: 4px;
  border-left: 4px solid #2c3e50;
}

.confirm-note p {
  margin: 0;
  color: var(--color-text-secondary);
  font-size: 0.9rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .coffee-prices-modal {
    flex-direction: column;
    gap: 0.5rem;
  }
  
  .option-selector {
    flex-direction: column;
    gap: 0.75rem;
  }
  
  .modal-footer {
    flex-direction: column;
  }
  
  .btn-secondary, .btn-primary {
    width: 100%;
  }
}
</style>