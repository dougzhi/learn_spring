import {createAPI} from '@/utils/request'

export const list = data => createAPI('/department/list', 'get', data)

export const detail = data => createAPI('/department/info', 'get', data)

export const add = data => createAPI('/department/add', 'post', data)

export const update = data => createAPI('/department/update', 'post', data)

export const drop = data => createAPI('/department/delete', 'post', data)
// 保存或更新
export const saveOrUpdate = data => { return data.id?update(data):save(data) }

