import request from '@/utils/request'

export function saveUser(data) {
  return request({
    url: '/userInfo/save',
    method: 'post',
    data: data
  })
}

export function listUser(params) {
  return request({
    url: '/userInfo/list',
    method: 'post',
    data: params
  })
}
export function deleteUser(params) {
  return request({
    url: '/userInfo/delete',
    method: 'post',
    data: params
  })
}
export function saveUserRole(userRoles) {
  return request({
    url: '/userInfo/saveUserRole',
    method: 'post',
    data: userRoles
  })
}
export function listUserRoles(userRoles) {
  return request({
    url: '/userInfo/listUserRoles',
    method: 'post',
    data: userRoles
  })
}
export function loadViewMenuData(roles) {
  return request({
    url: '/userInfo/viewMenu',
    method: 'post',
    data: roles
  })
}
export function checkLoginNameUnique(roles) {
  return request({
    url: '/userInfo/checkLoginNameUnique',
    method: 'post',
    data: roles
  })
}
export function loadUserDept(userid) {
  return request({
    url: '/userInfo/loadUserDept',
    method: 'post',
    data: { userId: userid }
  })
}
