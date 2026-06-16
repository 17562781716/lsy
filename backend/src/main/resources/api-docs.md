# ThemeWeek API Documentation

## Base URL
```
http://localhost:8080
```

---

## 1. User Management (用户管理)

### POST /user/register - User Registration
**Description:** Register a new user account

**Request Body:**
```json
{
  "userAccount": "string",
  "userPassword": "string",
  "checkPassword": "string",
  "userName": "string"
}
```

**Response:**
```json
{
  "code": 0,
  "data": 1234567890,
  "message": "ok"
}
```

---

### POST /user/login - User Login
**Description:** Login with account and password

**Request Body:**
```json
{
  "userAccount": "string",
  "userPassword": "string"
}
```

**Response:**
```json
{
  "code": 0,
  "data": {
    "id": 1234567890,
    "userName": "string",
    "userAvatar": "string",
    "token": "string"
  },
  "message": "ok"
}
```

---

### POST /user/logout - User Logout
**Description:** Logout current user

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### GET /user/get/login - Get Current User
**Description:** Get current logged-in user info

**Response:**
```json
{
  "code": 0,
  "data": {
    "id": 1234567890,
    "userAccount": "string",
    "userName": "string",
    "userAvatar": "string",
    "userProfile": "string",
    "userRole": "user"
  },
  "message": "ok"
}
```

---

### POST /user/update/my - Update My Profile
**Description:** Update current user's profile

**Request Body:**
```json
{
  "userName": "string",
  "userAvatar": "string",
  "userProfile": "string"
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### POST /user/add - Add User (Admin)
**Description:** Admin creates a new user

**Request Body:**
```json
{
  "userAccount": "string",
  "userPassword": "string",
  "userName": "string",
  "userRole": "user"
}
```

**Response:**
```json
{
  "code": 0,
  "data": 1234567890,
  "message": "ok"
}
```

---

### POST /user/delete - Delete User (Admin)
**Description:** Admin deletes a user

**Request Body:**
```json
{
  "id": 1234567890
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### POST /user/update - Update User (Admin)
**Description:** Admin updates user info

**Request Body:**
```json
{
  "id": 1234567890,
  "userName": "string",
  "userAvatar": "string",
  "userProfile": "string"
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### GET /user/get - Get User By ID (Admin)
**Description:** Admin gets user by ID

**Query:** `id=1234567890`

**Response:**
```json
{
  "code": 0,
  "data": {
    "id": 1234567890,
    "userAccount": "string",
    "userName": "string",
    "userAvatar": "string",
    "userRole": "user",
    "createTime": "2024-01-01 00:00:00"
  },
  "message": "ok"
}
```

---

### POST /user/list/page - List Users (Admin)
**Description:** Paginated user list for admin

**Request Body:**
```json
{
  "current": 1,
  "pageSize": 10,
  "userAccount": "string"
}
```

**Response:**
```json
{
  "code": 0,
  "data": {
    "records": [...],
    "total": 100,
    "size": 10,
    "current": 1
  },
  "message": "ok"
}
```

---

## 2. Theme Category (主题分类)

### GET /theme/category/list - List All Categories
**Description:** Get all theme categories

**Response:**
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "categoryName": "study",
      "categoryIcon": "📖",
      "categoryDescription": "learning",
      "sortOrder": 1
    }
  ],
  "message": "ok"
}
```

---

### GET /theme/category/get - Get Category By ID
**Description:** Get a specific category

**Query:** `id=1`

**Response:**
```json
{
  "code": 0,
  "data": {
    "id": 1,
    "categoryName": "study",
    "categoryIcon": "📖",
    "categoryDescription": "learning",
    "sortOrder": 1
  },
  "message": "ok"
}
```

---

### POST /theme/category/add - Add Category
**Description:** Create a new category

**Request Body:**
```json
{
  "categoryName": "string",
  "categoryIcon": "string",
  "categoryDescription": "string",
  "sortOrder": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

### POST /theme/category/update - Update Category
**Description:** Update a category

**Request Body:**
```json
{
  "id": 1,
  "categoryName": "string",
  "categoryIcon": "string",
  "categoryDescription": "string",
  "sortOrder": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### POST /theme/category/delete - Delete Category
**Description:** Delete a category

**Request Body:**
```json
{
  "id": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 3. Theme Template (主题模板)

### GET /theme/template/list - List All Templates
**Description:** Get all theme templates

**Response:**
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "categoryId": 1,
      "templateName": "Reading Week",
      "templateIcon": "📖",
      "templateDescription": "Put down phone and immerse in books",
      "templateTags": "study,reading",
      "templateIdeas": "[\"Finish a book\",\"Write notes\"]",
      "participants": 512,
      "difficulty": "easy"
    }
  ],
  "message": "ok"
}
```

---

### GET /theme/template/get - Get Template By ID
**Query:** `id=1`

**Response:**
```json
{
  "code": 0,
  "data": {
    "id": 1,
    "categoryId": 1,
    "templateName": "Reading Week",
    "templateIcon": "📖",
    "templateDescription": "...",
    "templateTags": "...",
    "templateIdeas": "...",
    "participants": 512,
    "difficulty": "easy"
  },
  "message": "ok"
}
```

---

### POST /theme/template/add - Add Template
**Description:** Create a new template

**Request Body:**
```json
{
  "categoryId": 1,
  "templateName": "string",
  "templateIcon": "string",
  "templateDescription": "string",
  "templateTags": "string",
  "templateIdeas": "string",
  "difficulty": "easy"
}
```

**Response:**
```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

### POST /theme/template/update - Update Template
**Request Body:**
```json
{
  "id": 1,
  "categoryId": 1,
  "templateName": "string",
  "templateIcon": "string",
  "templateDescription": "string",
  "templateTags": "string",
  "templateIdeas": "string",
  "difficulty": "easy"
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### POST /theme/template/delete - Delete Template
**Request Body:**
```json
{
  "id": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 4. User Theme Week (用户主题周)

### GET /user/theme/week/my - Get My Theme Weeks
**Description:** Get current user's theme weeks

**Response:**
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "userId": 1234567890,
      "templateId": 1,
      "weekIcon": "📖",
      "weekName": "My Reading Week",
      "weekDescription": "...",
      "weekDifficulty": "easy",
      "weekStatus": "ongoing",
      "startDate": "2024-01-01",
      "endDate": "2024-01-07",
      "progress": 50
    }
  ],
  "message": "ok"
}
```

---

### GET /user/theme/week/get - Get Theme Week By ID
**Query:** `id=1`

**Response:**
```json
{
  "code": 0,
  "data": {
    "id": 1,
    "userId": 1234567890,
    "templateId": 1,
    "weekIcon": "📖",
    "weekName": "My Reading Week",
    "weekStatus": "ongoing",
    "startDate": "2024-01-01",
    "endDate": "2024-01-07",
    "progress": 50
  },
  "message": "ok"
}
```

---

### POST /user/theme/week/add - Create Theme Week
**Description:** Create a new theme week for current user

**Request Body:**
```json
{
  "templateId": 1,
  "weekName": "string",
  "weekIcon": "📖",
  "weekDescription": "string",
  "weekDifficulty": "easy",
  "startDate": "2024-01-01",
  "endDate": "2024-01-07"
}
```

**Response:**
```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

### POST /user/theme/week/update - Update Theme Week
**Request Body:**
```json
{
  "id": 1,
  "weekName": "string",
  "weekDescription": "string",
  "weekStatus": "completed",
  "progress": 100
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### POST /user/theme/week/delete - Delete Theme Week
**Request Body:**
```json
{
  "id": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 5. Week Task (周任务)

### GET /week/task/list - Get Tasks By Week
**Query:** `weekId=1`

**Response:**
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "weekId": 1,
      "taskName": "Read 30 pages",
      "taskOrder": 1,
      "isCompleted": 1
    }
  ],
  "message": "ok"
}
```

---

### POST /week/task/add - Add Task
**Request Body:**
```json
{
  "weekId": 1,
  "taskName": "string",
  "taskOrder": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

### POST /week/task/update - Update Task
**Request Body:**
```json
{
  "id": 1,
  "taskName": "string",
  "taskOrder": 1,
  "isCompleted": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### POST /week/task/delete - Delete Task
**Request Body:**
```json
{
  "id": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 6. Checkin Record (打卡记录)

### GET /checkin/list - Get Checkins By Week
**Query:** `weekId=1`

**Response:**
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "weekId": 1,
      "userId": 1234567890,
      "checkinDate": "2024-01-01",
      "dayIndex": 0,
      "checkinContent": "Read 30 pages",
      "checkinDuration": 60
    }
  ],
  "message": "ok"
}
```

---

### POST /checkin/add - Add Checkin
**Request Body:**
```json
{
  "weekId": 1,
  "dayIndex": 0,
  "checkinContent": "string",
  "checkinDuration": 60,
  "checkinDate": "2024-01-01"
}
```

**Response:**
```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

### POST /checkin/delete - Delete Checkin
**Request Body:**
```json
{
  "id": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### GET /checkin/statistics - Get Checkin Statistics
**Query:** `weekId=1`

**Response:**
```json
{
  "code": 0,
  "data": {
    "totalDays": 7,
    "checkinDays": 5,
    "completionRate": 0.71,
    "continuousDays": 3
  },
  "message": "ok"
}
```

---

## 7. User Favorite (用户收藏)

### GET /user/favorite/my - Get My Favorites
**Response:**
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "userId": 1234567890,
      "templateId": 1,
      "createTime": "2024-01-01 00:00:00"
    }
  ],
  "message": "ok"
}
```

---

### POST /user/favorite/add - Add Favorite
**Query:** `templateId=1`

**Response:**
```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

### POST /user/favorite/delete - Delete Favorite
**Request Body:**
```json
{
  "id": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 8. Browse History (浏览历史)

### GET /user/browse/my - Get My Browse History
**Response:**
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "userId": 1234567890,
      "templateId": 1,
      "createTime": "2024-01-01 00:00:00"
    }
  ],
  "message": "ok"
}
```

---

### POST /user/browse/add - Add Browse History
**Query:** `templateId=1`

**Response:**
```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

### POST /user/browse/delete - Delete Browse History
**Request Body:**
```json
{
  "id": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 9. User Message (用户消息)

### GET /user/message/my - Get My Messages
**Response:**
```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "userId": 1234567890,
      "messageType": "system",
      "messageContent": "Welcome to ThemeWeek!",
      "isRead": 0,
      "createTime": "2024-01-01 00:00:00"
    }
  ],
  "message": "ok"
}
```

---

### GET /user/message/unread/count - Get Unread Count
**Response:**
```json
{
  "code": 0,
  "data": 5,
  "message": "ok"
}
```

---

### POST /user/message/mark/read - Mark Message As Read
**Query:** `messageId=1`

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

### POST /user/message/delete - Delete Message
**Request Body:**
```json
{
  "id": 1
}
```

**Response:**
```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## Database Tables

| Table Name | Description |
|------------|-------------|
| user | User account information |
| post | Posts/shares |
| post_favour | Post favorites |
| post_thumb | Post likes |
| theme_category | Theme categories (12 categories) |
| theme_template | Theme templates (25 templates) |
| user_theme_week | User's theme week instances |
| week_task | Tasks within a theme week |
| checkin_record | Daily checkin records |
| user_favorite | User's favorite templates |
| browse_history | User's browsing history |
| user_message | User messages |

---

## Error Codes

| Code | Description |
|------|-------------|
| 0 | Success |
| 40000 | PARAMS_ERROR |
| 40100 | NOT_LOGIN_ERROR |
| 40101 | NO_AUTH_ERROR |
| 40300 | FORBIDDEN_ERROR |
| 40400 | NOT_FOUND_ERROR |
| 50000 | SYSTEM_ERROR |
| 50100 | OPERATION_ERROR |
