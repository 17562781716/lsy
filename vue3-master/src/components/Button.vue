<template>
  <button 
    :class="[
      'btn',
      `btn-${variant}`,
      `btn-${size}`,
      { 'btn-full': fullWidth },
      { 'btn-disabled': disabled },
      className
    ]"
    :disabled="disabled"
    @click="handleClick"
    :type="type"
  >
    <slot></slot>
  </button>
</template>

<script setup>
import { defineEmits } from 'vue';

const props = defineProps({
  variant: {
    type: String,
    default: 'primary',
    validator: (value) => ['primary', 'secondary', 'outline', 'text'].includes(value)
  },
  size: {
    type: String,
    default: 'md',
    validator: (value) => ['sm', 'md', 'lg'].includes(value)
  },
  fullWidth: {
    type: Boolean,
    default: false
  },
  disabled: {
    type: Boolean,
    default: false
  },
  type: {
    type: String,
    default: 'button',
    validator: (value) => ['button', 'submit', 'reset'].includes(value)
  },
  className: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['click']);

const handleClick = (event) => {
  if (!props.disabled) {
    emit('click', event);
  }
};
</script>

<style scoped>
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 600;
  text-decoration: none;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
  font-size: 1rem;
  line-height: 1;
}

/* 变体样式 */
.btn-primary {
  background-color: #2196f3;
  color: white;
}

.btn-primary:hover:not(.btn-disabled) {
  background-color: #1976d2;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(33, 150, 243, 0.3);
}

.btn-secondary {
  background-color: #f5f5f5;
  color: #333;
}

.btn-secondary:hover:not(.btn-disabled) {
  background-color: #e0e0e0;
  transform: translateY(-1px);
}

.btn-outline {
  background-color: transparent;
  color: #2196f3;
  border: 2px solid #2196f3;
}

.btn-outline:hover:not(.btn-disabled) {
  background-color: rgba(33, 150, 243, 0.1);
  transform: translateY(-1px);
}

.btn-text {
  background-color: transparent;
  color: #2196f3;
}

.btn-text:hover:not(.btn-disabled) {
  background-color: rgba(33, 150, 243, 0.1);
  transform: translateY(-1px);
}

/* 尺寸样式 */
.btn-sm {
  padding: 0.5rem 1rem;
  font-size: 0.875rem;
}

.btn-md {
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
}

.btn-lg {
  padding: 1rem 2rem;
  font-size: 1.125rem;
}

/* 全宽样式 */
.btn-full {
  width: 100%;
}

/* 禁用样式 */
.btn-disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none !important;
  box-shadow: none !important;
}


</style>