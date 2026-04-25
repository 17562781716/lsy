<template>
  <div class="book-detail-container">
    <router-link to="/books" class="back-link">← 返回书架</router-link>
    
    <div v-if="book" class="book-detail">
      <div class="book-main-info">
        <div class="book-info">
          <h1 class="book-title">{{ book.title }}</h1>
          <p class="book-author">作者: {{ book.author }}</p>
          <p class="book-category">分类: {{ book.categoryName }}</p>
          <p class="book-publisher">出版社: {{ book.publisher }}</p>
          <p class="book-publish-date">出版日期: {{ book.publishDate }}</p>
          <p class="book-isbn">ISBN: {{ book.isbn }}</p>
          <p class="book-status" :class="{ 'available': book.available, 'borrowed': !book.available }">
            {{ book.available ? '可借阅' : '已借出' }}
          </p>
          <div class="borrow-btn-container">
            <button 
              class="borrow-btn" 
              :disabled="!book.available"
              @click="borrowBook"
            >
              {{ book.available ? '立即借阅' : '已借出' }}
            </button>
          </div>
        </div>
      </div>
      
      <div class="book-description">
        <h2>内容简介</h2>
        <p>{{ book.description }}</p>
      </div>
      
      <div class="book-reviews">
        <h2>读者评论</h2>
        <div v-if="book.reviews && book.reviews.length > 0" class="reviews-list">
          <div v-for="review in book.reviews" :key="review.id" class="review-item">
            <p class="reviewer-name">{{ review.name }}</p>
            <div class="review-rating">
              <span v-for="i in 5" :key="i" class="star" :class="{ 'filled': i <= review.rating }">{{ i <= review.rating ? '★' : '☆' }}</span>
            </div>
            <p class="review-content">{{ review.content }}</p>
            <p class="review-date">{{ review.date }}</p>
          </div>
        </div>
        <p v-else class="no-reviews">暂无评论</p>
        
        <!-- 用户评论表单 -->
        <div class="review-form-container">
          <h3>发表评论</h3>
          <div v-if="userStore.isLoggedIn" class="review-form">
            <div class="form-group">
              <label for="review-rating">评分:</label>
              <div class="rating-selector">
                <span v-for="i in 5" :key="i" 
                      class="star rating-star" 
                      :class="{ 'filled': i <= newReview.rating }"
                      @click="setRating(i)">
                  ★
                </span>
              </div>
            </div>
            <div class="form-group">
              <label for="review-content">评论内容:</label>
              <textarea 
                id="review-content" 
                v-model="newReview.content" 
                placeholder="请输入您的评论..." 
                rows="4"
                required
              ></textarea>
            </div>
            <button class="submit-review-btn" @click="submitReview">发表评论</button>
          </div>
          <div v-else class="login-prompt">
            <p>请先<router-link to="/login">登录</router-link>后再发表评论</p>
          </div>
        </div>
      </div>
    </div>
    
    <div v-else class="loading">
      <p>加载中...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useBooksStore } from '../stores/books';
import { useUserStore } from '../stores/user';

const route = useRoute();
const router = useRouter();
const booksStore = useBooksStore();
const userStore = useUserStore();
const bookId = computed(() => route.params.id);
const book = ref(null);

// 新评论数据
const newReview = ref({
  rating: 5,  // 默认5星评分
  content: ''
});

// 获取书籍详情
onMounted(() => {
  booksStore.fetchBooks();
  // 延迟一下确保数据已加载
  setTimeout(() => {
    book.value = booksStore.getBookById(bookId.value);
  }, 100);
});

// 借阅书籍
const borrowBook = () => {
  // 检查用户是否已登录
  if (!userStore.isLoggedIn) {
    alert('请先登录后再借阅书籍');
    router.push('/login');
    return;
  }
  
  // 执行借阅操作
  if (booksStore.borrowBook(bookId.value, userStore.currentUser.id)) {
    alert(`成功借阅《${book.value.title}》`);
    book.value.available = false;
    router.push('/my-books');
  } else {
    alert('借阅失败，请稍后重试');
  }
};

// 设置评分
const setRating = (rating) => {
  newReview.value.rating = rating;
};

// 提交评论
const submitReview = () => {
  // 检查用户是否已登录
  if (!userStore.isLoggedIn) {
    alert('请先登录后再发表评论');
    router.push('/login');
    return;
  }
  
  // 检查评分和内容是否有效
  if (!newReview.value.rating || !newReview.value.content.trim()) {
    alert('请填写评分和评论内容');
    return;
  }
  
  // 调用store的addReview方法
  if (booksStore.addReview(
    bookId.value, 
    userStore.currentUser.id, 
    userStore.currentUser.name, 
    newReview.value.rating, 
    newReview.value.content.trim()
  )) {
    // 评论成功后重置表单
    newReview.value.rating = 5;
    newReview.value.content = '';
    alert('评论发表成功！');
    
    // 重新获取书籍详情以更新评论列表
    setTimeout(() => {
      book.value = booksStore.getBookById(bookId.value);
    }, 100);
  } else {
    alert('评论发表失败，请稍后重试');
  }
};
</script>

<style scoped>
.book-detail-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.back-link {
  display: inline-block;
  margin-bottom: 1rem;
  color: #2196f3;
  text-decoration: none;
}

.back-link:hover {
  text-decoration: underline;
}

.book-detail {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 2rem;
}

.book-main-info {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  align-items: center;
}

.book-info {
  flex: 1;
}

.book-title {
  font-size: 2rem;
  color: #333;
  margin-bottom: 1rem;
}

.book-author, .book-category, .book-publisher, .book-publish-date, .book-isbn {
  font-size: 1.1rem;
  color: #666;
  margin-bottom: 0.75rem;
}

.book-status {
  display: inline-block;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 1rem;
  margin: 1rem 0;
}

.book-status.available {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.book-status.borrowed {
  background-color: #ffebee;
  color: #c62828;
}

.borrow-btn-container {
  margin-top: 2rem;
}

.borrow-btn {
  padding: 1rem 2rem;
  background-color: #2196f3;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1.1rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.borrow-btn:hover:not(:disabled) {
  background-color: #1976d2;
}

.borrow-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.book-description, .book-reviews {
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 1px solid #eee;
}

.book-description h2, .book-reviews h2 {
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 1rem;
}

.book-description p {
  line-height: 1.6;
  color: #555;
}

.reviews-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.review-item {
  padding: 1.5rem;
  background-color: #f9f9f9;
  border-radius: 4px;
}

.reviewer-name {
  font-weight: bold;
  color: #333;
  margin-bottom: 0.5rem;
}

.review-rating {
  margin-bottom: 0.75rem;
}

.star {
  color: #ddd;
  font-size: 1.2rem;
}

.star.filled {
  color: #ffd700;
}

.review-content {
  line-height: 1.6;
  color: #555;
  margin-bottom: 0.5rem;
}

.review-date {
  color: #999;
  font-size: 0.9rem;
}

.no-reviews {
  color: #999;
  font-style: italic;
}

/* 评论表单样式 */
.review-form-container {
  margin-top: 2rem;
  padding: 1.5rem;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.review-form-container h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 1.5rem;
}

.review-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-weight: bold;
  color: #555;
}

.rating-selector {
  display: flex;
  gap: 0.5rem;
}

.rating-star {
  color: #ddd;
  font-size: 1.5rem;
  cursor: pointer;
  transition: color 0.2s;
}

.rating-star:hover,
.rating-star.filled {
  color: #ffd700;
}

textarea {
  padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  resize: vertical;
  font-family: inherit;
}

.submit-review-btn {
  padding: 0.75rem 1.5rem;
  background-color: #2196f3;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
  align-self: flex-start;
}

.submit-review-btn:hover {
  background-color: #1976d2;
}

.login-prompt {
  padding: 1rem 0;
  color: #666;
}

.login-prompt a {
  color: #2196f3;
  text-decoration: none;
}

.login-prompt a:hover {
  text-decoration: underline;
}

.loading {
  text-align: center;
  padding: 4rem;
  color: #666;
}

@media (max-width: 768px) {
  .book-main-info {
    flex-direction: column;
    align-items: center;
  }
  
  .book-info {
    text-align: center;
  }
}
</style>