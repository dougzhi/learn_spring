import {createAPI} from '@/utils/request'

export const list = data => createAPI('/sys/user/findAll', 'get', data)
export const detail = data => createAPI('/sys/user/findById', 'get', data)
export const add = data => createAPI('/sys/user/create', 'post', data)
export const update = data => createAPI('/sys/user/update', 'put', data)
export const remove = data => createAPI('/sys/user/deleteById', 'delete', data)
