<template>
  <div class="about-page">
    <section class="about-hero">
      <div class="container">
        <h1 class="about-title">关于主题周</h1>
        <p class="about-subtitle">一周一个新主题，解锁多元生活</p>
      </div>
    </section>

    <section class="about-intro">
      <div class="container">
        <div class="intro-card">
          <h2 class="intro-title">我们的理念</h2>
          <div class="intro-content">
            <p>一年其实是36个十天，这个数字听起来惊人的恐怖。</p>
            <p>所以我们想：每一周都做点新鲜的事情，或者重拾之前的爱好。</p>
            <p>给自己准备很多自定义周——音乐剧周、西语周、钩织周……</p>
            <p>不用长期坚持，只需要专注七天就好。</p>
            <p>在平淡的生活里，换一种不一样的生活节奏。</p>
          </div>
        </div>
      </div>
    </section>

    <section class="about-features">
      <div class="container">
        <h2 class="section-title">核心功能</h2>
        <div class="features-grid">
          <div class="feature-card">
            <div class="feature-icon">🎯</div>
            <h3 class="feature-name">主题探索</h3>
            <p class="feature-desc">发现各种有趣的主题周，从艺术到生活，从健康到学习</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">📅</div>
            <h3 class="feature-name">打卡记录</h3>
            <p class="feature-desc">每天记录你的主题周活动，见证自己的坚持与成长</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">📝</div>
            <h3 class="feature-name">规划工具</h3>
            <p class="feature-desc">自定义你的主题周，设定目标和每日任务</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">💬</div>
            <h3 class="feature-name">感悟墙</h3>
            <p class="feature-desc">分享你的主题周故事，与志同道合的朋友交流</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">📊</div>
            <h3 class="feature-name">成长轨迹</h3>
            <p class="feature-desc">回顾你走过的每一周，见证人生的扩容</p>
          </div>
        </div>
      </div>
    </section>

    <section class="about-story">
      <div class="container">
        <div class="story-card">
          <h2 class="story-title">灵感来源</h2>
          <div class="story-content">
            <p>女孩们，我之前刷到"一年其实是36个十天"的说法，感觉惊人的恐怖。</p>
            <p>所以想每一周都做点新鲜的事情，或者重拾之前的爱好。</p>
            <p>于是给自己准备很多自定义周：音乐剧周、西语周、钩织周……</p>
            <p>从3月1号开始到现在已经完成了六个自定义周！</p>
            <p>感觉每一周都特别充实，这一个多月生活更幸福了。</p>
            <p>首先是收获了新爱好看音乐剧，这是之前从未涉猎过的领域。</p>
            <p>而且其实每一周过去之后会发现，这个习惯不会结尾——我们的生活因此扩容！</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.about-page {
  min-height: 100vh;
}

.about-hero {
  padding: 120px 0 60px;
  text-align: center;
  background: linear-gradient(135deg, #ffffff 0%, #e8f5e9 50%, #c8e6c9 100%);
}

.about-title {
  font-size: 42px;
  color: var(--text-primary);
  margin-bottom: 16px;
}

.about-subtitle {
  font-size: 18px;
  color: var(--text-secondary);
}

.about-intro {
  padding: 60px 0;
}

.intro-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 24px;
  padding: 48px;
  text-align: center;
  box-shadow: var(--shadow-medium);
}

.intro-title {
  font-size: 28px;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.intro-content p {
  font-size: 16px;
  color: var(--text-dark);
  line-height: 2.2;
  margin-bottom: 8px;
}

.about-features {
  padding: 60px 0;
}

.section-title {
  font-size: 28px;
  color: var(--text-primary);
  text-align: center;
  margin-bottom: 40px;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.feature-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  padding: 32px;
  text-align: center;
  box-shadow: var(--shadow-light);
  transition: all 0.3s;
}

.feature-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-medium);
}

.feature-icon {
  font-size: 40px;
  margin-bottom: 16px;
}

.feature-name {
  font-size: 18px;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.feature-desc {
  font-size: 14px;
  color: var(--text-muted);
  line-height: 1.6;
}

.about-story {
  padding: 60px 0 80px;
}

.story-card {
  background: linear-gradient(135deg, var(--primary-color), var(--primary-color-light));
  border-radius: 24px;
  padding: 48px;
  color: white;
  text-align: center;
}

.story-title {
  font-size: 28px;
  margin-bottom: 24px;
}

.story-content p {
  font-size: 16px;
  line-height: 2.2;
  margin-bottom: 8px;
  opacity: 0.95;
}

@media (max-width: 1024px) {
  .features-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .features-grid {
    grid-template-columns: 1fr;
  }
  
  .intro-card,
  .story-card {
    padding: 24px;
  }
  
  .about-title {
    font-size: 32px;
  }
}
</style>
