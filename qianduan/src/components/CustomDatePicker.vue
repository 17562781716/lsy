<template>
  <div class="custom-date-picker" @click.stop>
    <div class="date-input-wrapper" @click="togglePicker">
      <input
        :value="displayValue"
        type="text"
        readonly
        placeholder="请选择日期"
        class="date-input"
      >
      <span class="calendar-icon">📅</span>
    </div>

    <div v-if="showPicker" class="picker-dropdown">
      <div class="picker-header">
        <button class="nav-btn" @click="prevYear">«</button>
        <button class="nav-btn" @click="prevMonth">‹</button>
        <div class="current-ym">
          <span class="year-text" @click="showYearSelector = true; showMonthSelector = false">{{ currentYear }}年</span>
          <span class="month-text" @click="showMonthSelector = true; showYearSelector = false">{{ currentMonth + 1 }}月</span>
        </div>
        <button class="nav-btn" @click="nextMonth">›</button>
        <button class="nav-btn" @click="nextYear">»</button>
      </div>

      <div v-if="showYearSelector" class="year-selector">
        <div class="selector-grid">
          <button
            v-for="year in yearRange"
            :key="year"
            :class="['selector-item', { active: year === currentYear }]"
            @click="selectYear(year)"
          >
            {{ year }}
          </button>
        </div>
      </div>

      <div v-else-if="showMonthSelector" class="month-selector">
        <div class="selector-grid">
          <button
            v-for="month in 12"
            :key="month"
            :class="['selector-item', { active: month - 1 === currentMonth }]"
            @click="selectMonth(month - 1)"
          >
            {{ month }}月
          </button>
        </div>
      </div>

      <div v-else class="day-selector">
        <div class="weekdays">
          <span v-for="day in weekdays" :key="day" class="weekday">{{ day }}</span>
        </div>
        <div class="days-grid">
          <button
            v-for="day in calendarDays"
            :key="day.date"
            :class="[
              'day-item',
              {
                'other-month': !day.isCurrentMonth,
                'selected': isSelected(day.date),
                'today': isToday(day.date)
              }
            ]"
            @click="selectDate(day.date)"
          >
            {{ day.day }}
          </button>
        </div>
      </div>

      <div class="picker-footer">
        <button class="today-btn" @click="selectToday">今天</button>
        <button class="clear-btn" @click="clearDate">清除</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['update:modelValue']);

const showPicker = ref(false);
const showYearSelector = ref(false);
const showMonthSelector = ref(false);

const today = new Date();
const currentYear = ref(today.getFullYear());
const currentMonth = ref(today.getMonth());

const selectedDate = ref(props.modelValue ? new Date(props.modelValue) : null);

watch(() => props.modelValue, (newVal) => {
  if (newVal) {
    selectedDate.value = new Date(newVal);
    currentYear.value = selectedDate.value.getFullYear();
    currentMonth.value = selectedDate.value.getMonth();
  } else {
    selectedDate.value = null;
  }
});

const displayValue = computed(() => {
  if (!props.modelValue) return '';
  const date = new Date(props.modelValue);
  return `${date.getFullYear()}/${String(date.getMonth() + 1).padStart(2, '0')}/${String(date.getDate()).padStart(2, '0')}`;
});

const weekdays = ['日', '一', '二', '三', '四', '五', '六'];

const yearRange = computed(() => {
  const start = currentYear.value - 6;
  const end = currentYear.value + 5;
  const years = [];
  for (let i = start; i <= end; i++) {
    years.push(i);
  }
  return years;
});

const calendarDays = computed(() => {
  const year = currentYear.value;
  const month = currentMonth.value;
  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);
  const daysInMonth = lastDay.getDate();
  const startDayOfWeek = firstDay.getDay();

  const days = [];

  // Previous month days
  const prevMonthLastDay = new Date(year, month, 0).getDate();
  for (let i = startDayOfWeek - 1; i >= 0; i--) {
    const day = prevMonthLastDay - i;
    const date = new Date(year, month - 1, day);
    days.push({ day, date: formatDate(date), isCurrentMonth: false });
  }

  // Current month days
  for (let i = 1; i <= daysInMonth; i++) {
    const date = new Date(year, month, i);
    days.push({ day: i, date: formatDate(date), isCurrentMonth: true });
  }

  // Next month days
  const remainingDays = 42 - days.length;
  for (let i = 1; i <= remainingDays; i++) {
    const date = new Date(year, month + 1, i);
    days.push({ day: i, date: formatDate(date), isCurrentMonth: false });
  }

  return days;
});

const formatDate = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const isSelected = (date) => {
  return props.modelValue === date;
};

const isToday = (date) => {
  return formatDate(today) === date;
};

const togglePicker = () => {
  showPicker.value = !showPicker.value;
  showYearSelector.value = false;
  showMonthSelector.value = false;
};

const prevYear = () => {
  currentYear.value--;
};

const nextYear = () => {
  currentYear.value++;
};

const prevMonth = () => {
  if (currentMonth.value === 0) {
    currentMonth.value = 11;
    currentYear.value--;
  } else {
    currentMonth.value--;
  }
};

const nextMonth = () => {
  if (currentMonth.value === 11) {
    currentMonth.value = 0;
    currentYear.value++;
  } else {
    currentMonth.value++;
  }
};

const selectYear = (year) => {
  currentYear.value = year;
  showYearSelector.value = false;
};

const selectMonth = (month) => {
  currentMonth.value = month;
  showMonthSelector.value = false;
};

const selectDate = (date) => {
  emit('update:modelValue', date);
  showPicker.value = false;
};

const selectToday = () => {
  const todayStr = formatDate(today);
  currentYear.value = today.getFullYear();
  currentMonth.value = today.getMonth();
  emit('update:modelValue', todayStr);
  showPicker.value = false;
};

const clearDate = () => {
  emit('update:modelValue', '');
  showPicker.value = false;
};

// Close picker when clicking outside
const closePicker = () => {
  showPicker.value = false;
};

defineExpose({ closePicker });
</script>

<style scoped>
.custom-date-picker {
  position: relative;
  width: 100%;
}

.date-input-wrapper {
  position: relative;
  cursor: pointer;
}

.date-input {
  width: 100%;
  padding: 12px 40px 12px 16px;
  border: 1px solid var(--border-color);
  border-radius: 10px;
  font-size: 14px;
  background: white;
  color: var(--text-primary);
  cursor: pointer;
  height: 48px;
  transition: border-color 0.2s;
}

.date-input:focus {
  outline: none;
  border-color: var(--primary-color);
}

.calendar-icon {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 18px;
  pointer-events: none;
}

.picker-dropdown {
  position: absolute;
  top: calc(100% + 8px);
  left: 0;
  background: white;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
  padding: 16px;
  z-index: 1000;
  min-width: 320px;
  border: 1px solid var(--border-color);
}

.picker-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.nav-btn {
  width: 32px;
  height: 32px;
  border: none;
  background: transparent;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  color: var(--text-secondary);
  transition: all 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav-btn:hover {
  background: var(--primary-light);
  color: var(--primary-color);
}

.current-ym {
  display: flex;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}

.year-text,
.month-text {
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 6px;
  transition: all 0.2s;
}

.year-text:hover,
.month-text:hover {
  background: var(--primary-light);
  color: var(--primary-color);
}

.weekdays {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 4px;
  margin-bottom: 8px;
}

.weekday {
  text-align: center;
  font-size: 12px;
  font-weight: 600;
  color: var(--text-muted);
  padding: 8px 0;
}

.days-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 4px;
}

.day-item {
  aspect-ratio: 1;
  border: none;
  background: transparent;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  color: var(--text-primary);
  transition: all 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.day-item:hover {
  background: var(--primary-light);
  color: var(--primary-color);
}

.day-item.other-month {
  color: var(--text-muted);
  opacity: 0.5;
}

.day-item.selected {
  background: var(--primary-color);
  color: white;
  font-weight: 600;
}

.day-item.today {
  border: 2px solid var(--primary-color);
  color: var(--primary-color);
  font-weight: 600;
}

.day-item.today.selected {
  background: var(--primary-color);
  color: white;
}

.selector-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 8px;
  padding: 8px 0;
}

.selector-item {
  padding: 12px 8px;
  border: 1px solid var(--border-color);
  background: white;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  color: var(--text-primary);
  transition: all 0.2s;
}

.selector-item:hover {
  border-color: var(--primary-color);
  background: var(--primary-light);
  color: var(--primary-color);
}

.selector-item.active {
  background: var(--primary-color);
  color: white;
  border-color: var(--primary-color);
  font-weight: 600;
}

.picker-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 12px;
  padding-top: 12px;
  border-top: 1px solid #f0f0f0;
}

.today-btn,
.clear-btn {
  padding: 8px 16px;
  border: 1px solid var(--border-color);
  background: white;
  border-radius: 8px;
  cursor: pointer;
  font-size: 13px;
  color: var(--text-secondary);
  transition: all 0.2s;
}

.today-btn:hover {
  background: var(--primary-light);
  color: var(--primary-color);
  border-color: var(--primary-color);
}

.clear-btn:hover {
  background: #fff5f5;
  color: #e53e3e;
  border-color: #e53e3e;
}
</style>
