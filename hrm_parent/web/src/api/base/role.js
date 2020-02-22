import {createAPI} from '@/utils/request'

export const list = data => createAPI('/sys/role/findAll', 'get', data)
export const detail = data => createAPI('/sys/role/findById', 'get', data)
export const add = data => createAPI('/sys/role/create', 'post', data)
export const update = data => createAPI('/sys/role/update', 'put', data)
export const remove = data => createAPI('/sys/role/deleteById', 'delete', data)
export const assignPrem = data => createAPI('/sys/role/assignPrem', 'post', data)
