<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.id" placeholder="员工编号" style="width: 160px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.name" placeholder="员工姓名" style="width: 150px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.idCard" placeholder="身份证号" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.phone" placeholder="电话号码" style="width: 160px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.status" placeholder="状态" clearable class="filter-item" style="width: 110px" @keyup.enter.native="handleFilter">
        <el-option v-for="item in statusOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
      </el-select>
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
      <el-table-column label="员工编号" prop="id" sortable="custom" align="center" min-width="105px">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" min-width="80px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电话号码" min-width="115px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center" min-width="50px">
        <template slot-scope="scope">
          <span>{{ scope.row.gender }}</span>
        </template>
      </el-table-column>
      <!--<el-table-column label="年龄" align="center" min-width="50px">-->
      <!--<template slot-scope="scope">-->
      <!--<span>{{ scope.row.age }}</span>-->
      <!--</template>-->
      <!--</el-table-column>-->
      <el-table-column label="身份证号" align="center" min-width="150px">
        <template slot-scope="scope">
          <span>{{ scope.row.idCard }}</span>
        </template>
      </el-table-column>
      <el-table-column label="薪资/月" prop="salary" sortable="custom" align="center" min-width="105px">
        <template slot-scope="scope">
          <span>￥ {{ scope.row.salary }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入职时间" prop="entryTime" sortable="custom" min-width="135px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.entryTime | parseTime('{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" min-width="90px">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | typeFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :visible.sync="dialogFormVisible" title="修改员工信息">
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
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="temp.gender">
            <el-radio label="男"/>
            <el-radio label="女"/>
          </el-radio-group>
        </el-form-item>
        <!--<el-form-item label="年龄" prop="age">-->
        <!--<el-input v-model.number="temp.age"/>-->
        <!--</el-form-item>-->
        <el-form-item label="薪资/月" prop="salary">
          <el-input v-model.number="temp.salary"/>
        </el-form-item>
        <!--<el-form-item label="入职日期" prop="entryTime">-->
        <!--<el-date-picker v-model="temp.entryTime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%;"/>-->
        <!--</el-form-item>-->
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status" placeholder="请选择">
            <el-option v-for="item in statusOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="updateData">确认</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import waves from '@/directive/waves' // Waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { validateIdCard, validateSalary } from '@/utils/validate'
import { fetchList, updateEmployee } from '@/api/employee'

const statusOptions = [
  { key: '1', display_name: '在职' },
  { key: '0', display_name: '已离职' }
]

const statusTypeKeyValue = statusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: 'danger',
        1: 'success'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return statusTypeKeyValue[type]
    }
  },
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
        name: undefined,
        phone: undefined,
        idCard: undefined,
        status: undefined,
        orderBy: undefined
      },
      statusOptions,
      temp: {
        id: undefined,
        name: '',
        phone: '',
        idCard: '',
        gender: undefined,
        salary: 0,
        status: undefined
      },
      dialogFormVisible: false,
      downloadLoading: false,
      rules: {
        name: [
          { required: true, message: '请输入员工姓名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入员工联系电话', trigger: 'blur' }
        ],
        idCard: [
          { required: true, validator: validateIdCard, trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择员工性别', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择员工就职状态', trigger: 'blur' }
        ],
        salary: [
          { validator: validateSalary, required: true, trigger: 'blur' }
        ]
      }
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
          tempData.entryTime = null
          updateEmployee(tempData).then(response => {
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
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>
