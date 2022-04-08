import request from '@/utils/request'


// 删除节点
export function delNode(id) {
  return request({
    url: '/system/process/processNode/' + id,
    method: 'delete'
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

//查询节点列表
export function listNode(query) {
  return request({
    url: '/system/process/processNode/list',
    method: 'get',
    params: query
  })
}

// 查询流程列表
export function listProcess(query) {
  return request({
    url: '/system/process/process/list',
    method: 'get',
    params: query
  })
}

// 查询流程详细
export function getProcess(id) {
  return request({
    url: '/system/process/process/' + id,
    method: 'get'
  })
}

// 新增流程
export function addProcess(data) {
  return request({
    url: '/system/process/process',
    method: 'post',
    data: data
  })
}

// 修改流程
export function updateProcess(data) {
  return request({
    url: '/system/process/process',
    method: 'put',
    data: data
  })
}

// 删除流程
export function delProcess(id) {
  return request({
    url: '/system/process/process/' + id,
    method: 'delete'
  })
}
