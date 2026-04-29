<template>
  <div class="profile-editor">
    <div class="editor-header">
      <h3>编辑个人信息</h3>
    </div>
    
    <form @submit.prevent="handleSubmit">
      <!-- 头像上传 -->
      <div class="form-group">
        <label for="avatar">头像</label>
        <div class="avatar-upload-container">
          <div 
            class="avatar-upload-area"
            @dragover.prevent
            @drop.prevent="handleFileDrop"
            @click="triggerFileInput"
          >
            <img 
              v-if="formData.avatar" 
              :src="formData.avatar" 
              alt="预览头像" 
              class="avatar-preview"
            >
            <div v-else class="avatar-placeholder">
              <span class="upload-icon">📷</span>
              <p>点击或拖拽上传头像</p>
              <p class="upload-hint">支持 JPG、PNG 格式，小于 2MB</p>
            </div>
            <input 
              type="file" 
              id="avatar" 
              ref="fileInput"
              accept="image/jpeg, image/png"
              @change="handleFileChange"
              class="file-input"
            >
          </div>
        </div>
      </div>
      
      <!-- 基本信息 -->
      <div class="form-row">
        <div class="form-group">
          <label for="name">昵称</label>
          <input 
            type="text" 
            id="name" 
            v-model="formData.name"
            class="form-input"
            placeholder="请输入昵称"
          >
          <p v-if="errors.name" class="error-message">{{ errors.name }}</p>
        </div>
        
        <div class="form-group">
          <label for="gender">性别</label>
          <select 
            id="gender" 
            v-model="formData.gender"
            class="form-input"
          >
            <option value="">请选择</option>
            <option value="男">男</option>
            <option value="女">女</option>
          </select>
        </div>
      </div>
      
      <div class="form-row">
        <div class="form-group">
          <label for="birthday">生日</label>
          <CustomDatePicker v-model="formData.birthday" />
        </div>
        
        <div class="form-group">
          <label for="phone">手机号码</label>
          <div class="phone-input-group">
            <input 
              type="tel" 
              id="phone" 
              v-model="formData.phone"
              class="form-input"
              placeholder="请输入手机号码"
              @input="validatePhone"
            >
            <button 
              type="button" 
              class="btn btn-secondary verify-btn"
              :disabled="!canVerifyPhone"
              @click="showPhoneVerification = true"
            >
              {{ phoneVerified ? '已验证' : '验证' }}
            </button>
          </div>
          <p v-if="errors.phone" class="error-message">{{ errors.phone }}</p>
        </div>
      </div>
      
      <div class="form-group">
        <label for="email">邮箱</label>
        <input 
          type="email" 
          id="email" 
          v-model="formData.email"
          class="form-input"
          placeholder="请输入邮箱"
        >
      </div>
      
      <!-- 地址三级联动 -->
      <div class="form-group">
        <label>地址</label>
        <div class="address-selectors">
          <select 
            v-model="formData.address.province"
            class="form-input address-select"
            @change="onProvinceChange"
          >
            <option value="">请选择省份</option>
            <option v-for="province in provinces" :key="province" :value="province">
              {{ province }}
            </option>
          </select>
          
          <select 
            v-model="formData.address.city"
            class="form-input address-select"
            @change="onCityChange"
          >
            <option value="">请选择城市</option>
            <option v-for="city in cities" :key="city" :value="city">
              {{ city }}
            </option>
          </select>
          
          <select 
            v-model="formData.address.district"
            class="form-input address-select"
          >
            <option value="">请选择区县</option>
            <option v-for="district in districts" :key="district" :value="district">
              {{ district }}
            </option>
          </select>
        </div>
        
        <input 
          type="text" 
          v-model="formData.address.street"
          class="form-input"
          placeholder="详细地址"
          style="margin-top: 0.5rem;"
        >
      </div>
      
      <!-- 表单操作按钮 -->
      <div class="form-actions">
        <button type="button" class="btn btn-secondary" @click="handleCancel">
          取消
        </button>
        <button type="submit" class="btn btn-primary" :disabled="isSubmitting">
          {{ isSubmitting ? '保存中...' : '保存' }}
        </button>
      </div>
    </form>
    
    <!-- 手机号验证弹窗 -->
    <div v-if="showPhoneVerification" class="modal-overlay">
      <div class="modal-content">
        <div class="modal-header">
          <h4>手机号码验证</h4>
          <button class="close-btn" @click="showPhoneVerification = false">&times;</button>
        </div>
        <div class="modal-body">
          <p>我们将发送验证码到您的新手机号：{{ formData.phone }}</p>
          <div class="verification-form">
            <div class="form-group">
              <label for="verificationCode">验证码</label>
              <div class="code-input-group">
                <input 
                  type="text" 
                  id="verificationCode" 
                  v-model="verificationCode"
                  class="form-input"
                  placeholder="请输入验证码"
                >
                <button 
                  type="button" 
                  class="btn btn-secondary"
                  :disabled="countdown > 0"
                  @click="sendVerificationCode"
                >
                  {{ countdown > 0 ? `${countdown}秒后重试` : '发送验证码' }}
                </button>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" @click="showPhoneVerification = false">取消</button>
          <button class="btn btn-primary" @click="verifyPhoneCode">验证</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, watch } from 'vue';
import { useUserStore } from '../stores/user';
import { debounce } from 'lodash-es';
import CustomDatePicker from './CustomDatePicker.vue';

const userStore = useUserStore();
const user = computed(() => userStore.user);

// 表单数据
const formData = reactive({
  name: user.value.name || '',
  avatar: user.value.avatar || '',
  gender: user.value.gender || '',
  birthday: user.value.birthday || '',
  phone: user.value.phone || '',
  email: user.value.email || '',
  address: {
    province: user.value.address?.province || '',
    city: user.value.address?.city || '',
    district: user.value.address?.district || '',
    street: user.value.address?.street || ''
  }
});

// 表单验证
const errors = reactive({});
const isSubmitting = ref(false);
const today = computed(() => new Date().toISOString().split('T')[0]);

// 文件上传
const fileInput = ref(null);
const triggerFileInput = () => {
  fileInput.value?.click();
};

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    handleFileUpload(file);
  }
};

const handleFileDrop = (event) => {
  const file = event.dataTransfer.files[0];
  if (file) {
    handleFileUpload(file);
  }
};

const handleFileUpload = (file) => {
  // 验证文件类型和大小
  if (!['image/jpeg', 'image/png'].includes(file.type)) {
    alert('只支持 JPG 和 PNG 格式的图片');
    return;
  }
  
  if (file.size > 2 * 1024 * 1024) {
    alert('图片大小不能超过 2MB');
    return;
  }
  
  // 预览图片
  const reader = new FileReader();
  reader.onload = (e) => {
    formData.avatar = e.target.result;
  };
  reader.readAsDataURL(file);
};

// 地址三级联动（模拟数据）
const provinces = ref(['北京市', '上海市', '广东省', '江苏省', '浙江省']);
const cities = ref([]);
const districts = ref([]);

// 模拟城市数据
const cityData = {
  '北京市': ['北京市'],
  '上海市': ['上海市'],
  '广东省': ['广州市', '深圳市', '珠海市'],
  '江苏省': ['南京市', '苏州市', '无锡市'],
  '浙江省': ['杭州市', '宁波市', '温州市']
};

// 模拟区县数据
const districtData = {
  '北京市': ['东城区', '西城区', '朝阳区', '海淀区'],
  '上海市': ['黄浦区', '徐汇区', '长宁区', '静安区'],
  '广州市': ['越秀区', '荔湾区', '海珠区', '天河区'],
  '深圳市': ['罗湖区', '福田区', '南山区', '宝安区'],
  '珠海市': ['香洲区', '斗门区', '金湾区'],
  '南京市': ['玄武区', '秦淮区', '建邺区', '鼓楼区'],
  '苏州市': ['姑苏区', '虎丘区', '吴中区', '相城区'],
  '无锡市': ['梁溪区', '锡山区', '惠山区', '滨湖区'],
  '杭州市': ['上城区', '下城区', '江干区', '拱墅区'],
  '宁波市': ['海曙区', '江北区', '北仑区', '镇海区'],
  '温州市': ['鹿城区', '龙湾区', '瓯海区', '洞头区']
};

const onProvinceChange = () => {
  formData.address.city = '';
  formData.address.district = '';
  cities.value = cityData[formData.address.province] || [];
  districts.value = [];
};

const onCityChange = () => {
  formData.address.district = '';
  districts.value = districtData[formData.address.city] || [];
};

// 手机号验证
const canVerifyPhone = computed(() => {
  return /^1[3-9]\d{9}$/.test(formData.phone) && formData.phone !== user.value.phone;
});

const showPhoneVerification = ref(false);
const verificationCode = ref('');
const phoneVerified = ref(false);
const countdown = ref(0);

// 防抖验证手机号
const validatePhone = debounce((value) => {
  if (value && !/^1[3-9]\d{9}$/.test(value)) {
    errors.phone = '请输入正确的手机号码';
  } else {
    errors.phone = '';
  }
}, 500);

const sendVerificationCode = () => {
  // 模拟发送验证码
  alert(`验证码已发送到 ${formData.phone}`);
  phoneVerified.value = true; // 模拟验证通过
  countdown.value = 60;
  
  // 倒计时
  const timer = setInterval(() => {
    countdown.value--;
    if (countdown.value <= 0) {
      clearInterval(timer);
    }
  }, 1000);
};

const verifyPhoneCode = () => {
  // 模拟验证验证码
  if (verificationCode.value) {
    phoneVerified.value = true;
    showPhoneVerification.value = false;
    alert('验证成功');
  } else {
    alert('请输入验证码');
  }
};

// 表单验证
const validateForm = () => {
  const newErrors = {};
  
  if (!formData.name.trim()) {
    newErrors.name = '昵称不能为空';
  } else if (formData.name.length < 2 || formData.name.length > 20) {
    newErrors.name = '昵称长度应在 2-20 个字符之间';
  }
  
  // 如果修改了手机号且未验证
  if (formData.phone !== user.value.phone && !phoneVerified.value) {
    newErrors.phone = '请验证新手机号码';
  }
  
  Object.assign(errors, newErrors);
  return Object.keys(newErrors).length === 0;
};

// 提交表单
const handleSubmit = async () => {
  if (!validateForm()) return;
  
  isSubmitting.value = true;
  
  try {
    // 构建提交数据
    const submitData = {
      ...formData,
      // 只在修改了手机号且已验证的情况下提交新手机号
      phone: phoneVerified.value ? formData.phone : user.value.phone
    };
    
    // 模拟 API 请求
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // 提交成功
    $emit('save', submitData);
  } catch (error) {
    console.error('保存失败:', error);
    alert('保存失败，请稍后重试');
  } finally {
    isSubmitting.value = false;
  }
};

// 取消编辑
const handleCancel = () => {
  $emit('cancel');
};

// 初始化地址数据
onMounted(() => {
  if (formData.address.province) {
    cities.value = cityData[formData.address.province] || [];
    if (formData.address.city) {
      districts.value = districtData[formData.address.city] || [];
    }
  }
});

// 定义事件
const emit = defineEmits(['save', 'cancel']);
</script>

<style scoped>
.profile-editor {
  max-width: 800px;
}

.editor-header {
  margin-bottom: 2rem;
}

.editor-header h3 {
  font-size: 1.5rem;
  color: var(--text-primary);
}

.form-row {
  display: flex;
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}

.form-group {
  flex: 1;
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: var(--text-primary);
  font-weight: 500;
}

.form-input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid var(--border-color);
  border-radius: var(--border-radius);
  font-size: 1rem;
  transition: border-color var(--transition-fast);
  background-color: var(--background-primary);
  color: var(--text-primary);
}

.form-input:focus {
  outline: none;
  border-color: #2c3e50;
  box-shadow: 0 0 0 2px rgba(44, 62, 80, 0.1);
}

/* 头像上传 */
.avatar-upload-container {
  margin-top: 0.5rem;
}

.avatar-upload-area {
  width: 150px;
  height: 150px;
  border: 2px dashed var(--border-color);
  border-radius: var(--border-radius);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all var(--transition-fast);
  overflow: hidden;
  background-color: var(--background-secondary);
}

.avatar-upload-area:hover {
  border-color: var(--primary-color);
  background-color: rgba(30, 128, 255, 0.05);
}

.avatar-preview {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-placeholder {
  text-align: center;
  color: var(--text-secondary);
}

.upload-icon {
  font-size: 2rem;
  display: block;
  margin-bottom: 0.5rem;
}

.upload-hint {
  font-size: 0.8rem;
  margin-top: 0.5rem;
}

.file-input {
  display: none;
}

/* 地址选择器 */
.address-selectors {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}

.address-select {
  flex: 1;
}

/* 手机号验证 */
.phone-input-group {
  display: flex;
  gap: 0.5rem;
}

.verify-btn {
  white-space: nowrap;
}

/* 验证码输入 */
.code-input-group {
  display: flex;
  gap: 0.5rem;
}

/* 表单操作 */
.form-actions {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
  padding-top: 1.5rem;
  border-top: 1px solid var(--border-color);
}

.btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: var(--border-radius);
  font-size: 1rem;
  cursor: pointer;
  transition: all var(--transition-fast);
}

.btn-primary {
  background-color: transparent;
  color: #2c3e50;
  border: 1px solid #2c3e50;
}

.btn-primary:hover:not(:disabled) {
  background-color: #2c3e50;
  color: white;
  transform: translateY(-1px);
  box-shadow: var(--shadow-medium);
}

.btn-primary:disabled {
  background-color: transparent;
  color: var(--disabled-color);
  border-color: var(--disabled-color);
  cursor: not-allowed;
}

.btn-secondary {
  background-color: transparent;
  color: #666666;
  border: 1px solid #666666;
}

.btn-secondary:hover:not(:disabled) {
  background-color: #666666;
  color: white;
}

.btn-secondary:disabled {
  background-color: var(--disabled-color);
  color: var(--text-secondary);
  cursor: not-allowed;
}

/* 错误信息 */
.error-message {
  color: var(--red-color);
  font-size: 0.8rem;
  margin-top: 0.5rem;
}

/* 模态框 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background-color: var(--card-background);
  border-radius: var(--border-radius);
  box-shadow: var(--shadow-large);
  width: 90%;
  max-width: 500px;
  max-height: 80vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem;
  border-bottom: 1px solid var(--border-color);
}

.modal-header h4 {
  margin: 0;
  color: var(--text-primary);
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: var(--text-secondary);
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: background-color var(--transition-fast);
}

.close-btn:hover {
  background-color: var(--background-secondary);
}

.modal-body {
  padding: 1.5rem;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  padding: 1.5rem;
  border-top: 1px solid var(--border-color);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .form-row {
    flex-direction: column;
  }
  
  .address-selectors {
    flex-direction: column;
  }
  
  .phone-input-group,
  .code-input-group {
    flex-direction: column;
  }
}
</style>