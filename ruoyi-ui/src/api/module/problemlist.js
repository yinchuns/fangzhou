import request from '@/utils/request'

// 查询问题清单列表
export function listList(query) {
  return request({
    url: '/common/module/problemlist/list',
    method: 'get',
    params: query
  })
}

// 查询问题清单详细
export function getList(id) {
  return request({
    url: '/common/module/problemlist/' + id,
    method: 'get'
  })
}

// 新增问题清单
export function addList(data) {
  return request({
    url: '/common/module/problemlist',
    method: 'post',
    data: data
  })
}

// 修改问题清单
export function updateList(data) {
  return request({
    url: '/common/module/problemlist',
    method: 'put',
    data: data
  })
}

// 删除问题清单
export function delList(id) {
  return request({
    url: '/common/module/problemlist/' + id,
    method: 'delete'
  })
}
