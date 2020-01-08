<template>
  <div class="dashboard-container">
    <div class="app-container">
      <el-card shadow="never">
        <!-- 数据 -->
        企业管理
        <!-- end -->
      </el-card>
     </div>
  </div>
</template>

<script>
import {list} from '@/api/base/company'
export default {
  name: 'company-table-index',
  data() {
    return {
      dataList: []
    }
  },
  methods: {
    getList(){
      list().then(res => {
        console.log(res)
      })
    }
  },
  // 创建完毕状态
  created() {
    this.getList()
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
