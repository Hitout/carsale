<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.id" placeholder="客户编号" clearable style="width: 160px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.name" placeholder="客户姓名" clearable style="width: 150px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.phone" placeholder="手机号" clearable style="width: 150px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.idCard" placeholder="身份证号" clearable style="width: 190px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange">
      <el-table-column label="客户编号" prop="id" sortable="custom" align="center" min-width="105px">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" prop="name" width="80px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="联系电话" prop="phone" width="115px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="身份证号" prop="idCard" align="center" min-width="150px">
        <template slot-scope="scope">
          <span>{{ scope.row.idCard }}</span>
        </template>
      </el-table-column>
      <el-table-column label="注册时间" prop="createTime" sortable="custom" min-width="135px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :visible.sync="dialogFormVisible" title="修改客户信息">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="temp.name"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="temp.phone"/>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="temp.idCard"/>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="updateData()">确认</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import { fetchList, updateCustomer } from '@/api/customer'
import waves from '@/directive/waves' // Waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { validateIdCard } from '@/utils/validate'

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 5,
        id: undefined,
        name: null,
        phone: undefined,
        idCard: undefined,
        orderBy: null
      },
      temp: {
        name: '',
        phone: '',
        idCard: ''
      },
      dialogFormVisible: false,
      rules: {
        name: [
          { required: true, message: '请输入客户姓名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入客户联系电话', trigger: 'blur' }
        ],
        idCard: [
          { required: true, validator: validateIdCard, trigger: 'blur' }
        ]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        if (response.data.code === 20000) {
          this.list = response.data.data.items
          this.total = response.data.data.total
        } else {
          this.$message({
            message: response.data.message,
            type: 'error'
          })
        }
        this.listLoading = false

        // Just to simulate the time of the request
        // setTimeout(() => {
        //   this.listLoading = false
        // }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },
    sortChange(data) {
      const { prop, order } = data
      if (order === 'ascending') {
        this.listQuery.orderBy = prop + ' asc'
      } else {
        this.listQuery.orderBy = prop + ' desc'
      }
      this.handleFilter()
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.createTime = null
          updateCustomer(tempData).then(response => {
            if (response.data.code === 20000) {
              for (const v of this.list) {
                if (v.id === this.temp.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.temp)
                  break
                }
              }
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
            } else {
              this.$notify({
                title: '错误',
                message: response.data.message,
                type: 'error',
                duration: 2000
              })
            }
          })
        }
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['客户编号', '姓名', '手机号', '身份证号', '注册时间']
        const filterVal = ['id', 'name', 'phone', 'idCard', 'createTime']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '客户信息'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'createTime') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>
