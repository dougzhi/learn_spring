<template>
  <div class="dashboard-container">
    <div class="app-container">
      <el-card shadow="never">
        <!-- 数据 -->
        <el-tabs type="border-card" v-model="activeName">
          <el-tab-pane label="企业信息" name="companyInfo">
            <el-form ref="form" :model="companyInfo" label-width="280px">
              <el-form-item v-show="false">
                <el-input v-model="companyInfo.id" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="公司名称" >
                <el-input v-model="companyInfo.name" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="公司地区">
                <el-input v-model="companyInfo.companyArea" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="公司地址">
                <el-input v-model="companyInfo.companyAddress" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="审核状态">
                <el-switch v-model="companyInfo.auditState" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="营业执照编号">
                <el-input v-model="companyInfo.businessLicense" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="法人代表">
                <el-input v-model="companyInfo.legalRepresentative" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="公司电话">
                <el-input v-model="companyInfo.companyPhone" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="companyInfo.mailbox" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="公司规模">
                <el-input v-model="companyInfo.companySize" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="所属行业">
                <el-input v-model="companyInfo.industry" style="width: 580px" disabled/>
              </el-form-item>
              <el-form-item label="备注">
                <el-input type="textarea" v-model="companyInfo.remark" style="width: 580px"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit">审核</el-button>
                <el-button @click="onSubmit">拒绝</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
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
