<template>
  <div class="card" :class="className">
    <slot></slot>
  </div>
</template>

<script setup>

const props = defineProps({
  className: {
    type: String,
    default: ''
  }
});
</script>

<style scoped>
.card {
  background-color: var(--color-card-background);
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  transition: box-shadow 0.3s ease, transform 0.3s ease;
}

.card:hover {
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}
</style>