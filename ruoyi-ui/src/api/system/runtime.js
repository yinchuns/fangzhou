import request from '@/utils/request'

// 查询流程实例列表
export function listRuntime(query) {
  return request({
    url: '/system/process/processRuntime/list',
    method: 'get',
    params: query
  })
}

// 查询流程实例详细
export function getRuntime(id) {
  return request({
    url: '/system/process/processRuntime/' + id,
    method: 'get'
  })
}

// 新增流程实例
export function addRuntime(data) {
  return request({
    url: '/system/process/processRuntime',
    method: 'post',
    data: data
  })
}

// 修改流程实例
export function updateRuntime(data) {
  return request({
    url: '/system/process/processRuntime',
    method: 'put',
    data: data
  })
}

// 删除流程实例
export function delRuntime(id) {
  return request({
    url: '/system/process/processRuntime/' + id,
    method: 'delete'
  })
}
