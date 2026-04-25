<template>
  <section class="banner">
    <div class="banner-container">
      <transition-group name="banner-slide" tag="div" class="banner-slides">
        <div 
          v-for="(slide, index) in slides" 
          :key="slide.id" 
          class="banner-slide" 
          :class="{ 'active': index === currentSlide }"
        >
          <div class="slide-content">
            <img :src="slide.image" :alt="slide.title" class="slide-image" />
            <!-- 暂时移除叠加层，以验证图片是否显示 -->
            <!-- <div class="slide-overlay"></div> -->
            <div class="container">
              <div class="slide-text">
                <h1 class="slide-title">{{ slide.title }}</h1>
                <p class="slide-description">{{ slide.description }}</p>
                <RouterLink :to="slide.link" class="btn-primary">
                  {{ slide.ctaText }}
                </RouterLink>
              </div>
            </div>
          </div>
        </div>
      </transition-group>
      
      <!-- 轮播控制按钮 -->
      <button 
        class="banner-control banner-control-prev" 
        @click="prevSlide"
        aria-label="上一张"
      >
        ←
      </button>
      <button 
        class="banner-control banner-control-next" 
        @click="nextSlide"
        aria-label="下一张"
      >
        →
      </button>
      
      <!-- 轮播指示器 -->
      <div class="banner-indicators">
        <button 
          v-for="(slide, index) in slides" 
          :key="slide.id"
          class="banner-indicator"
          :class="{ 'active': index === currentSlide }"
          @click="goToSlide(index)"
          :aria-label="`跳转到第${index + 1}张`"
        ></button>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter, RouterLink } from 'vue-router';

const router = useRouter();

// 轮播图数据 - 使用用户提供的本地图片
const slides = ref([
  {
    id: 1,
    title: '咖啡时光',
    description: '在云边书吧，品味香浓咖啡，享受阅读时光',
    link: '/coffee',
    ctaText: '查看菜单',
    image: new URL('../assets/3.PNG', import.meta.url).href // 用户提供的咖啡时光图片
  },
  {
    id: 2,
    title: '新书上架',
    description: '最新热门书籍已上架，快来抢先阅读',
    link: '/books?new=true',
    ctaText: '查看新书',
    image: 'https://picsum.photos/id/1073/1920/600' // 书籍主题图片 - 精美书籍排列
  },
  {
    id: 3,
    title: '沙龙交流',
    description: '参与我们的沙龙交流，与志同道合的朋友交流',
    link: '/salon',
    ctaText: '了解更多',
    image: new URL('../assets/4.PNG', import.meta.url).href // 用户提供的沙龙交流图片
  }
]);

const currentSlide = ref(0);
let slideInterval = null;
const autoPlayInterval = 5000; // 自动播放间隔（毫秒）

// 自动播放
const startAutoPlay = () => {
  slideInterval = setInterval(() => {
    nextSlide();
  }, autoPlayInterval);
};

// 停止自动播放
const stopAutoPlay = () => {
  if (slideInterval) {
    clearInterval(slideInterval);
    slideInterval = null;
  }
};

// 下一张
const nextSlide = () => {
  currentSlide.value = (currentSlide.value + 1) % slides.value.length;
};

// 上一张
const prevSlide = () => {
  currentSlide.value = (currentSlide.value - 1 + slides.value.length) % slides.value.length;
};

// 跳转到指定幻灯片
const goToSlide = (index) => {
  currentSlide.value = index;
};

onMounted(() => {
  startAutoPlay();
});

onUnmounted(() => {
  stopAutoPlay();
});
</script>

<style scoped>
.banner {
  width: 100%;
  height: 600px;
  overflow: hidden;
  position: relative;
}

.banner-container {
  width: 100%;
  height: 100%;
  position: relative;
}

.banner-slides {
  width: 100%;
  height: 100%;
  position: relative;
}

.banner-slide {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  visibility: hidden;
  transition: all 0.5s ease;
}

.banner-slide.active {
  opacity: 1;
  visibility: visible;
  z-index: 1;
}

.slide-content {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.slide-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

/* 移除了第一个幻灯片的蓝色叠加效果 */

.slide-content:nth-child(3) .slide-overlay {
  background: linear-gradient(to right, rgba(76, 175, 80, 0.6), rgba(0, 0, 0, 0.3));
}

.slide-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to right, rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.2));
}

.slide-text {
  max-width: 600px;
  color: white;
  z-index: 2;
  position: relative;
}

.slide-title {
  font-size: 3rem;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.slide-description {
  font-size: 1.25rem;
  margin-bottom: 2rem;
  line-height: 1.6;
  opacity: 0.9;
}

/* 自定义按钮样式 - 移除蓝色背景和下划线，只保留白色字体 */
.slide-text .btn-primary {
  background-color: transparent;
  color: white;
  border: none;
  text-decoration: none;
}

.slide-text .btn-primary:hover {
  background-color: rgba(255, 255, 255, 0.1);
  box-shadow: none;
}

/* 轮播过渡动画 */
.banner-slide-enter-active,
.banner-slide-leave-active {
  transition: all 0.5s ease;
}

.banner-slide-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.banner-slide-leave-to {
  opacity: 0;
  transform: translateX(-100%);
}

/* 控制按钮 */
.banner-control {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(255, 255, 255, 0.2);
  border: none;
  color: white;
  font-size: 2rem;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  cursor: pointer;
  z-index: 10;
  transition: all 0.2s ease;
}

.banner-control:hover {
  background-color: rgba(255, 255, 255, 0.4);
}

.banner-control-prev {
  left: 20px;
}

.banner-control-next {
  right: 20px;
}

/* 指示器 */
.banner-indicators {
  position: absolute;
  bottom: 30px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
  z-index: 10;
}

.banner-indicator {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.5);
  border: none;
  cursor: pointer;
  transition: all 0.2s ease;
}

.banner-indicator:hover {
  background-color: rgba(255, 255, 255, 0.8);
}

.banner-indicator.active {
  background-color: white;
  width: 40px;
  border-radius: 6px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .banner {
    height: 400px;
  }
  
  .slide-title {
    font-size: 2rem;
  }
  
  .slide-description {
    font-size: 1rem;
  }
  
  .banner-control {
    font-size: 1.5rem;
    width: 40px;
    height: 40px;
  }
  
  .banner-control-prev {
    left: 10px;
  }
  
  .banner-control-next {
    right: 10px;
  }
}

@media (max-width: 480px) {
  .banner {
    height: 300px;
  }
  
  .slide-title {
    font-size: 1.5rem;
  }
  
  .slide-description {
    font-size: 0.9rem;
    margin-bottom: 1rem;
  }
}
</style>