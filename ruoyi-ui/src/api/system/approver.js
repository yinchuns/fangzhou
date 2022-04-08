import request from '@/utils/request'

// 查询节点审核人列表
export function listApprover(query) {
  return request({
    url: '/system/process/approver/list',
    method: 'get',
    params: query
  })
}

// 查询节点审核人详细
export function getApprover(id) {
  return request({
    url: '/system/process/approver/' + id,
    method: 'get'
  })
}

// 新增节点审核人
export function addApprover(data) {
  return request({
    url: '/system/process/approver',
    method: 'post',
    data: data
  })
}

// 修改节点审核人
export function updateApprover(data) {
  return request({
    url: '/system/process/approver',
    method: 'put',
    data: data
  })
}

// 删除节点审核人
export function delApprover(id) {
  return request({
    url: '/system/process/approver/' + id,
    method: 'delete'
  })
}
