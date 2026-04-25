<template>
  <div class="books-container">
    <header class="books-header">
      <h1>书吧馆藏</h1>
      <p>探索我们的精选书籍，找到适合你的下一本好书</p>
    </header>

    <div class="books-search">
      <input 
        type="text" 
        v-model="searchQuery" 
        placeholder="搜索书名或作者..."
        class="search-input"
      >
      <select v-model="selectedCategory" class="category-select">
        <option value="all">全部分类</option>
        <option value="literature">文学小说</option>
        <option value="science_fiction">科幻小说</option>
        <option value="history">历史</option>
        <option value="psychology">心理学</option>
        <option value="economics">经济学</option>
        <option value="philosophy">哲学</option>
        <option value="art">艺术</option>
        <option value="science">科学技术</option>
        <option value="education">教育</option>
      </select>
    </div>

    <div class="books-grid">
      <div v-for="book in filteredBooks" :key="book.id" class="book-card">
        <div class="book-cover">
        </div>
        <div class="book-info">
          <h3 class="book-title">{{ book.title }}</h3>
          <p class="book-author">{{ book.author }}</p>
          <p class="book-category">{{ book.categoryName }}</p>
          <p class="book-status" :class="{ 'available': book.available, 'borrowed': !book.available }">
            {{ book.available ? '可借阅' : '已借出' }}
          </p>
          <button 
            class="borrow-btn" 
            :disabled="!book.available"
            @click="viewBookDetails(book.id)"
          >
            {{ book.available ? '查看详情' : '已借出' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useBooksStore } from '../stores/books';

const router = useRouter();
const booksStore = useBooksStore();
const searchQuery = ref('');
const selectedCategory = ref('all');

// 获取书籍数据
booksStore.fetchBooks();

// 根据搜索和分类过滤书籍
const filteredBooks = computed(() => {
  let books = booksStore.books;
  
  // 搜索过滤
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase();
    books = books.filter(book => 
      book.title.toLowerCase().includes(query) || 
      book.author.toLowerCase().includes(query)
    );
  }
  
  // 分类过滤
  if (selectedCategory.value !== 'all') {
    books = books.filter(book => book.category === selectedCategory.value);
  }
  
  return books;
});

// 查看书籍详情
const viewBookDetails = (bookId) => {
  router.push(`/books/${bookId}`);
};
</script>

<style scoped>
.books-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.books-header {
  text-align: center;
  margin-bottom: 2rem;
}

.books-header h1 {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 0.5rem;
}

.books-header p {
  color: #666;
  font-size: 1.1rem;
}

.books-search {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  justify-content: center;
}

.search-input {
  padding: 0.75rem 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 300px;
  font-size: 1rem;
}

.category-select {
  padding: 0.75rem 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  background-color: white;
}

.books-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 2rem;
}

.book-card {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: transform 0.2s, box-shadow 0.2s;
}

.book-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.book-cover {
  height: 50px;
  overflow: hidden;
  position: relative;
  min-height: 50px;
}

.book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.book-info {
  padding: 1rem;
}

.book-title {
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
  color: #333;
}

.book-author {
  color: #666;
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

.book-category {
  color: #888;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.book-status {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.85rem;
  margin-bottom: 1rem;
}

.book-status.available {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.book-status.borrowed {
  background-color: #ffebee;
  color: #c62828;
}

.borrow-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #2196f3;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
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
</style>