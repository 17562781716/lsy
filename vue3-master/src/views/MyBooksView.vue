<template>
  <div class="my-books-container">
    <header class="my-books-header">
      <h1>我的借阅</h1>
      <p>查看和管理您借阅的书籍</p>
    </header>

    <div v-if="userStore.isLoggedIn" class="my-books-content">
      <div v-if="borrowedBooks.length > 0" class="books-list">
        <div v-for="book in borrowedBooks" :key="book.id" class="borrowed-book-item">
          <div class="book-basic-info">
            <div class="book-cover-small">
            </div>
            <div class="book-info">
              <h3 class="book-title">{{ book.title }}</h3>
              <p class="book-author">{{ book.author }}</p>
              <p class="borrow-date">借阅日期: {{ book.borrowDate }}</p>
              <p class="due-date">到期日期: {{ book.dueDate }}</p>
              <p class="return-status" :class="{ 'overdue': isOverdue(book.dueDate) }">
                {{ isOverdue(book.dueDate) ? '已逾期' : '正常' }}
              </p>
            </div>
          </div>
          <div class="book-actions">
            <button class="return-btn" @click="returnBook(book.id)">归还书籍</button>
            <button class="renew-btn" @click="renewBook(book.id)">续借</button>
          </div>
        </div>
      </div>
      <div v-else class="no-books">
        <p>您目前没有借阅任何书籍</p>
        <router-link to="/books" class="browse-btn">浏览书架</router-link>
      </div>

      <div class="user-profile">
        <h2>个人信息</h2>
        <div class="profile-info">
          <p><strong>用户名:</strong> {{ userStore.currentUser.username }}</p>
          <p><strong>姓名:</strong> {{ userStore.currentUser.name }}</p>
          <p><strong>邮箱:</strong> {{ userStore.currentUser.email }}</p>
          <p><strong>会员等级:</strong> {{ userStore.currentUser.memberLevel }}</p>
          <p><strong>已借阅:</strong> {{ borrowedBooks.length }} / {{ userStore.currentUser.maxBorrowLimit }}</p>
        </div>
        <button class="logout-btn" @click="logout">退出登录</button>
      </div>
    </div>

    <div v-else class="not-logged-in">
      <p>请先登录后查看您的借阅记录</p>
      <router-link to="/login" class="login-btn">去登录</router-link>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useBooksStore } from '../stores/books';
import { useUserStore } from '../stores/user';

const router = useRouter();
const booksStore = useBooksStore();
const userStore = useUserStore();

// 获取用户借阅的书籍
const borrowedBooks = computed(() => {
  if (!userStore.isLoggedIn) return [];
  return booksStore.getBorrowedBooksByUser(userStore.currentUser.id);
});

// 检查是否逾期
const isOverdue = (dueDate) => {
  const today = new Date();
  const due = new Date(dueDate);
  return today > due;
};

// 归还书籍
const returnBook = (bookId) => {
  if (booksStore.returnBook(bookId, userStore.currentUser.id)) {
    alert('书籍归还成功');
  } else {
    alert('归还失败，请稍后重试');
  }
};

// 续借书籍
const renewBook = (bookId) => {
  if (booksStore.renewBook(bookId, userStore.currentUser.id)) {
    alert('续借成功');
  } else {
    alert('续借失败，请稍后重试');
  }
};

// 退出登录
const logout = () => {
  userStore.logout();
  router.push('/');
};
</script>

<style scoped>
.my-books-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.my-books-header {
  text-align: center;
  margin-bottom: 2rem;
}

.my-books-header h1 {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 0.5rem;
}

.my-books-header p {
  color: #666;
  font-size: 1.1rem;
}

.my-books-content {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 2rem;
}

/* 确保在所有屏幕尺寸下都保持两列布局 */
@media (max-width: 1024px) {
  .my-books-content {
    grid-template-columns: 2fr 1fr;
  }
}

@media (max-width: 768px) {
  .my-books-content {
    grid-template-columns: 1fr;
  }
}

.books-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.borrowed-book-item {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.book-basic-info {
  display: flex;
  gap: 1rem;
  align-items: center;
  flex: 1;
}

.book-cover-small {
  width: 80px;
  height: 120px;
  overflow: hidden;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  min-height: 120px;
  background-color: #f5f5f5;
}

.book-cover-small img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.book-info {
  flex: 1;
}

.book-title {
  font-size: 1.3rem;
  color: #333;
  margin-bottom: 0.5rem;
}

.book-author {
  color: #666;
  margin-bottom: 0.5rem;
}

.borrow-date, .due-date {
  color: #888;
  font-size: 0.95rem;
  margin-bottom: 0.25rem;
}

.return-status {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.85rem;
  margin-top: 0.5rem;
}

.return-status.overdue {
  background-color: #ffebee;
  color: #c62828;
}

.return-status {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.book-actions {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.return-btn, .renew-btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.return-btn {
  background-color: #f44336;
  color: white;
}

.return-btn:hover {
  background-color: #d32f2f;
}

.renew-btn {
  background-color: #4caf50;
  color: white;
}

.renew-btn:hover {
  background-color: #388e3c;
}

.no-books {
  text-align: center;
  padding: 4rem;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.no-books p {
  color: #666;
  font-size: 1.1rem;
  margin-bottom: 1.5rem;
}

.browse-btn {
  display: inline-block;
  padding: 0.75rem 1.5rem;
  background-color: #2196f3;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.browse-btn:hover {
  background-color: #1976d2;
}

.user-profile {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
}

.user-profile h2 {
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 1.5rem;
  border-bottom: 1px solid #eee;
  padding-bottom: 0.75rem;
}

.profile-info p {
  margin-bottom: 1rem;
  color: #666;
}

.profile-info strong {
  color: #333;
}

.logout-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 1rem;
  transition: background-color 0.2s;
}

.logout-btn:hover {
  background-color: #d32f2f;
}

.not-logged-in {
  text-align: center;
  padding: 6rem;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.not-logged-in p {
  color: #666;
  font-size: 1.3rem;
  margin-bottom: 2rem;
}

.login-btn {
  display: inline-block;
  padding: 1rem 2rem;
  background-color: transparent;
  color: #2c3e50;
  text-decoration: none;
  border: 1px solid #2c3e50;
  border-radius: 4px;
  font-size: 1.1rem;
  transition: all 0.2s;
}

.login-btn:hover {
  background-color: #2c3e50;
  color: white;
}

@media (max-width: 768px) {
  .my-books-content {
    grid-template-columns: 1fr;
  }
  
  .borrowed-book-item {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .book-basic-info {
    margin-bottom: 1.5rem;
  }
  
  .book-actions {
    width: 100%;
    flex-direction: row;
  }
  
  .return-btn, .renew-btn {
    flex: 1;
  }
}
</style>