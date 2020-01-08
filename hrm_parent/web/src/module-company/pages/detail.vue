<template>
  <div class="dashboard-container">
    <div class="app-container">
      <el-card shadow="never">
        <!-- 数据 -->
        <el-tabs type="border-card" v-model="activeName">
          <el-tab-pane label="企业信息" name="companyInfo">{{companyInfo}}</el-tab-pane>
          <el-tab-pane label="账户信息" name="accountInfo">账户信息</el-tab-pane>
          <el-tab-pane label="交易记录" name="transactionRecord">交易记录</el-tab-pane>
        </el-tabs>
        <!-- end -->
      </el-card>
     </div>
  </div>
</template>

<script>
import {detail} from '@/api/base/company'
export default {
  name: 'company-table-detail',
  data() {
    return {
      activeName: 'companyInfo',
      companyInfo: {},
    }
  },
  methods: {
    getInfo(id){
      detail({id: id}).then(res => {
        this.companyInfo = res.data.data
      })
    }
  },
  // 创建完毕状态
  created() {
    var id = this.$route.params.id
    this.getInfo(id)
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
