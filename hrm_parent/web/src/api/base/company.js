import {createAPI, createFormAPI} from '@/utils/request'

export const list = data => createAPI('/company/list', 'get', data)

export const detail = data => createAPI('/company/info/', 'get', data)

export const add = data => createAPI('/company/add', 'post', data)

export const update = data => createAPI('/company/update', 'post', data)

export const drop = data => createAPI('/company/delete', 'post', data)

