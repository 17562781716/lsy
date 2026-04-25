<template>
  <section class="membership-section">
    <div class="container">
      <div class="section-header">
        <h1 class="section-title">会员福利</h1>
        <p class="section-description">成为云边书吧会员，享受多重专属福利</p>
      </div>
      
      <!-- 会员等级卡片 -->
      <div class="membership-levels">
        <Card class="level-card basic">
          <div class="level-header">
            <h2 class="level-title">基础会员</h2>
            <div class="level-price">免费</div>
          </div>
          <div class="level-benefits">
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">图书借阅权限</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">图书购买9折优惠</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">咖啡9折优惠</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">沙龙交流8折优惠</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">消费1元累积1积分</span>
            </div>
          </div>
          <Button type="primary" size="large" block>立即开通</Button>
        </Card>
        
        <Card class="level-card premium">
          <div class="level-header">
            <h2 class="level-title">高级会员</h2>
            <div class="level-price">¥99/年</div>
          </div>
          <div class="level-benefits">
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">所有基础会员福利</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">图书购买8.5折优惠</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">咖啡8折优惠</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">沙龙交流5折优惠</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">消费1元累积1.5积分</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">每月免费咖啡一杯</span>
            </div>
            <div class="benefit-item">
              <span class="benefit-icon">✅</span>
              <span class="benefit-text">生日当月双倍积分</span>
            </div>
          </div>
          <Button type="primary" size="large" block>立即开通</Button>
        </Card>
      </div>
      
      <!-- 积分兑换规则 -->
      <div class="points-section">
        <h2 class="section-subtitle">积分兑换</h2>
        <div class="points-grid">
          <Card class="points-card">
            <div class="points-icon">📚</div>
            <div class="points-info">
              <div class="points-item">100积分</div>
              <div class="points-item">兑换10元图书券</div>
            </div>
          </Card>

          <Card class="points-card">
            <div class="points-icon">🎟️</div>
            <div class="points-info">
              <div class="points-item">200积分</div>
              <div class="points-item">兑换免费沙龙门票</div>
            </div>
          </Card>
          <Card class="points-card">
            <div class="points-icon">🎁</div>
            <div class="points-info">
              <div class="points-item">500积分</div>
              <div class="points-item">兑换精美图书周边</div>
            </div>
          </Card>
        </div>
      </div>
      
      <!-- 会员特权说明 -->
      <div class="privileges-section">
        <h2 class="section-subtitle">会员特权</h2>
        <div class="privileges-grid">
          <div class="privilege-item">
            <div class="privilege-icon">📖</div>
            <div class="privilege-content">
              <h3 class="privilege-title">图书特权</h3>
              <p class="privilege-description">高级会员享受8.5折购书优惠，基础会员享受9折优惠</p>
            </div>
          </div>
          <div class="privilege-item">
            <div class="privilege-icon">☕</div>
            <div class="privilege-content">
              <h3 class="privilege-title">咖啡特权</h3>
              <p class="privilege-description">高级会员享受8折咖啡优惠，基础会员享受9折优惠</p>
            </div>
          </div>
          <div class="privilege-item">
            <div class="privilege-icon">🎤</div>
            <div class="privilege-content">
              <h3 class="privilege-title">沙龙特权</h3>
              <p class="privilege-description">高级会员享受沙龙交流5折优惠，基础会员享受8折优惠</p>
            </div>
          </div>

        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import Card from '../components/Card.vue';
import Button from '../components/Button.vue';
</script>

<style scoped>
.membership-section {
  padding: 4rem 0;
  background-color: var(--color-background-light);
}

.section-header {
  text-align: center;
  margin-bottom: 3rem;
}

.section-title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: var(--color-text-primary);
}

.section-description {
  font-size: 1.1rem;
  color: var(--color-text-secondary);
  max-width: 700px;
  margin: 0 auto;
}

.section-subtitle {
  font-size: 2rem;
  margin-bottom: 2rem;
  color: var(--color-text-primary);
  text-align: center;
}

/* 会员等级卡片 */
.membership-levels {
  display: flex;
  gap: 2rem;
  justify-content: center;
  margin-bottom: 4rem;
  flex-wrap: wrap;
}

.level-card {
  padding: 2rem;
  flex: 1;
  min-width: 300px;
  max-width: 400px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.level-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}

.level-card.premium {
  border: 2px solid var(--color-primary);
  position: relative;
}

.level-card.premium::before {
  content: '推荐';
  position: absolute;
  top: -10px;
  right: 20px;
  background-color: var(--color-primary);
  color: white;
  padding: 0.3rem 1rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
}

.level-header {
  text-align: center;
  margin-bottom: 2rem;
}

.level-title {
  font-size: 1.8rem;
  margin: 0 0 0.5rem 0;
  color: var(--color-text-primary);
}

.level-price {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--color-primary);
}

.level-benefits {
  margin-bottom: 2rem;
}

.benefit-item {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
  font-size: 0.95rem;
}

.benefit-item:last-child {
  margin-bottom: 0;
}

.benefit-icon {
  margin-right: 0.8rem;
  color: var(--color-primary);
  font-size: 1rem;
}

.benefit-text {
  color: var(--color-text-secondary);
}

/* 积分兑换 */
.points-section {
  margin-bottom: 4rem;
}

.points-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
}

.points-card {
  padding: 1.5rem;
  display: flex;
  align-items: center;
  transition: transform 0.3s ease;
}

.points-card:hover {
  transform: translateY(-5px);
}

.points-icon {
  font-size: 3rem;
  margin-right: 1.5rem;
  width: 60px;
  text-align: center;
}

.points-info {
  flex: 1;
}

.points-info .points-item:first-child {
  font-weight: 600;
  color: var(--color-primary);
  font-size: 1.1rem;
  margin-bottom: 0.3rem;
}

.points-info .points-item:last-child {
  color: var(--color-text-secondary);
  font-size: 0.9rem;
}

/* 会员特权 */
.privileges-section {
  margin-bottom: 2rem;
}

.privileges-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1.5rem;
}

.privilege-item {
  display: flex;
  align-items: flex-start;
  background-color: white;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease;
}

.privilege-item:hover {
  transform: translateY(-5px);
}

.privilege-icon {
  font-size: 2.5rem;
  margin-right: 1.5rem;
  color: var(--color-primary);
}

.privilege-content {
  flex: 1;
}

.privilege-title {
  font-size: 1.2rem;
  margin: 0 0 0.5rem 0;
  color: var(--color-text-primary);
}

.privilege-description {
  font-size: 0.95rem;
  line-height: 1.6;
  color: var(--color-text-secondary);
  margin: 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .membership-section {
    padding: 2rem 0;
  }
  
  .section-title {
    font-size: 2rem;
  }
  
  .section-subtitle {
    font-size: 1.5rem;
  }
  
  .membership-levels {
    flex-direction: column;
    align-items: center;
  }
  
  .level-card {
    max-width: 100%;
  }
  
  .points-grid {
    grid-template-columns: 1fr;
  }
  
  .privileges-grid {
    grid-template-columns: 1fr;
  }
}
</style>