import apiClient from './api';

export const userLogin = (data) => {
  return apiClient.post('/user/login', data);
};

export const userRegister = (data) => {
  return apiClient.post('/user/register', data);
};

export const userLogout = () => {
  return apiClient.post('/user/logout');
};

export const getLoginUser = () => {
  return apiClient.get('/user/get/login');
};

export const updateMyUser = (data) => {
  return apiClient.post('/user/update/my', data);
};

export const getUserVOById = (id) => {
  return apiClient.get(`/user/get/vo?id=${id}`);
};

export const listUserVOByPage = (data) => {
  return apiClient.post('/user/list/page/vo', data);
};