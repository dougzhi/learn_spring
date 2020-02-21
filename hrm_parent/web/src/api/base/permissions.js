import {createAPI} from '@/utils/request'

export const list = data => createAPI('/sys/permission/findAll', 'get', data)
export const detail = data => createAPI('/sys/permission/findById', 'get', data)
export const add = data => createAPI('/sys/permission/create', 'post', data)
export const update = data => createAPI('/sys/permission/update', 'put', data)
export const remove = data => createAPI('/sys/permission/deleteById', 'delete', data)
export const saveOrUpdate = data => {return data.id?update(data):add(data)}
