import request from '@/utils/request'

// 查询流程节点列表
export function listNode(query) {
  return request({
    url: '/system/process/processNode/list',
    method: 'get',
    params: query
  })
}

// 查询流程节点详细
export function getNode(id) {
  return request({
    url: '/system/process/processNode/' + id,
    method: 'get'
  })
}

// 新增流程节点
export function addNode(data) {
  return request({
    url: '/system/process/processNode',
    method: 'post',
    data: data
  })
}

// 修改流程节点
export function updateNode(data) {
  return request({
    url: '/system/process/processNode',
    method: 'put',
    data: data
  })
}

// 删除流程节点
export function delNode(id) {
  return request({
    url: '/system/process/processNode/' + id,
    method: 'delete'
  })
}
