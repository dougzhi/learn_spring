<template>
  <div class="dashboard-container">
    <div class="app-container">
      <el-card shadow="never">
        <!-- 数据 -->
        <el-table
          :data="dataList"
          border
          style="width: 100%">
          <el-table-column
            fixed
            type="index"
            label="序号"
            width="50">
          </el-table-column>
          <el-table-column
            fixed
            prop="name"
            label="企业名称"
            width="200">
          </el-table-column>
          <el-table-column
            prop="version"
            label="当前版本"
            width="120">
          </el-table-column>
          <el-table-column
            prop="legalRepresentative"
            label="联系人"
            width="120">
          </el-table-column>
          <el-table-column
            prop="companyPhone"
            label="联系电话"
            width="300">
          </el-table-column>
          <el-table-column
            prop="companyArea"
            label="所在地区"
            width="120">
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="申请时间"
            width="120">
          </el-table-column>
          <el-table-column
            prop="zip"
            label="结束时间"
            width="120">
          </el-table-column>
          <el-table-column
            prop="auditState"
            label="状态"
            width="120">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.auditState"
                active-value = "Audited"
                inactive-value = "Unreviewed"
                disabled>
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="100">
            <template slot-scope="scope">
              <router-link :to="'/company/detail/' + scope.row.id">查看</router-link>
            </template>
          </el-table-column>
        </el-table>
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
        this.dataList = res.data.data
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
