<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.orderId" placeholder="订单编号" style="width: 170px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.customerName" placeholder="客户姓名" style="width: 170px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.employeeName" placeholder="销售员姓名" style="width: 170px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.status" placeholder="状态" clearable class="filter-item" style="width: 130px" @keyup.enter.native="handleFilter">
        <el-option v-for="item in orderStatusOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <el-table
      :key="tableKey"
      :data="list"
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange">
      <el-table-column type="expand" width="30">
        <template slot-scope="props">
          <el-table :data="props.row.details" border>
            <el-table-column label="订单详情编号" min-width="110" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.id }}</span>
              </template>
            </el-table-column>
            <el-table-column label="汽车编号" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.carId }}</span>
              </template>
            </el-table-column>
            <el-table-column label="型号" min-width="210" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.brandName }}/{{ scope.row.seriesName }}</span><br>
                <span>{{ scope.row.type }}</span>
              </template>
            </el-table-column>
            <el-table-column label="颜色" min-width="70" prop="color" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.color }}</span>
              </template>
            </el-table-column>
            <el-table-column label="售价" min-width="70" prop="salePrice" align="center">
              <template slot-scope="scope">
                <span>￥ {{ scope.row.salePrice }}</span>
              </template>
            </el-table-column>
            <el-table-column label="数量" min-width="60" prop="carNumber" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.carNumber }}</span>
              </template>
            </el-table-column>
            <el-table-column v-if="props.row.status === '0'" label="操作" min-width="110" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="handleUpdate(scope.row, props.row.orderId)">编辑</el-button>
                <el-button v-if="props.row.details.length > 1" type="danger" size="mini" @click="handleDelete(scope.row, props.row.orderId)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="订单编号" prop="orderId" sortable="custom" align="center" min-width="105px">
        <template slot-scope="scope">
          <span>{{ scope.row.orderId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户姓名" width="80px" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>客户编号: {{ scope.row.customerId }}</p>
            <p>姓名: {{ scope.row.customerName }}</p>
            <p>联系电话: {{ scope.row.customerPhone }}</p>
            <p>身份证号: {{ scope.row.customerIdCard }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.customerName }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="总价" prop="totalPrice" sortable="custom" align="center" min-width="105px">
        <template slot-scope="scope">
          <span>￥ {{ scope.row.totalPrice }}</span>
        </template>
      </el-table-column>
      <el-table-column label="销售员" width="105px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.employeeName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" sortable="custom" min-width="135px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime" sortable="custom" min-width="135px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" min-width="95px">
        <template slot-scope="scope">
          <el-popover v-if="scope.row.payTime != null" trigger="hover" placement="top">
            <p>支付时间: {{ scope.row.payTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | typeFilter }}</el-tag>
            </div>
          </el-popover>
          <el-tag v-else :type="scope.row.status | statusFilter">{{ scope.row.status | typeFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="100px" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === '0'" type="primary" size="mini" @click="handleUpdateStatus(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :visible.sync="dialogFormVisible" title="修改订单信息">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="140px" style="width: 400px; margin-left:50px;">
        <el-form-item label="品牌 & 车系" prop="selectedOptions">
          <el-cascader :options="options" v-model="temp.selectedOptions" placeholder="品牌/车系" @change="getStore"/>
        </el-form-item>
        <el-form-item
          :rules="{ required: true, message: '必须选择型号', trigger: 'change' }"
          label="型号 & 颜色"
          prop="carId">
          <el-select v-model="temp.carId" class="filter-item" placeholder="Please select" style="width: 100%" @change="getPrice">
            <el-option
              v-for="item in temp.store"
              :key="item.id"
              :label="item.type + ' ' + item.color"
              :value="item.id">
              <span>{{ item.type }}</span>
              <el-tag>{{ item.color }}</el-tag>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="carNumber">
          <el-input-number v-model="temp.carNumber" :min="1"/>
        </el-form-item>
        <el-form-item label="售价">
          <span v-if="temp.carId">{{ temp.salePrice }} 元/辆</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="updateData">确认</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogStatusVisible" title="更新订单状态" width="450px">
      <el-form ref="statusForm" :model="updateStatus" label-position="left" label-width="70px" style="margin-left:50px;">
        <el-form-item label="状态">
          <el-select v-model="updateStatus.status" class="filter-item" required placeholder="Please select">
            <el-option v-for="item in orderStatusOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogStatusVisible = false">取消</el-button>
        <el-button type="primary" @click="updateStatusData">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // Waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { validateIdCard } from '@/utils/validate'
import { fetchList, updateStatus, updateDetail, deleteDetail } from '@/api/order'
import { fetchStore, fetchSeries } from '@/api/init'

const orderStatusOptions = [
  { key: '0', display_name: '未支付' },
  { key: '1', display_name: '已支付' },
  { key: '2', display_name: '已取消' }
]

const orderTypeKeyValue = orderStatusOptions.reduce((acc, cur) => {
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
        1: 'success',
        2: 'info'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return orderTypeKeyValue[type]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listQuery: {
        page: 1,
        limit: 5,
        orderId: undefined,
        customerName: undefined,
        employeeName: undefined,
        status: undefined,
        orderBy: 'orderId desc'
      },
      orderStatusOptions,
      options: [],
      temp: {
        orderId: null,
        selectedOptions: [],
        type: null,
        color: null,
        carId: null,
        carNumber: null,
        store: null
      },
      updateStatus: {
        status: null
      },
      dialogFormVisible: false,
      dialogStatusVisible: false,
      rules: {
        type: [{ required: true, message: '必须选择型号', trigger: 'change' }],
        carNumber: [{ required: true, message: '必须填入数量', trigger: 'blur' }],
        selectedOptions: [{ required: true, message: '必须选择车型', trigger: 'change' }],
        customerIdCard: [{ required: true, validator: validateIdCard, trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
    this.getSeriesOpt()
  },
  methods: {
    getList() {
      fetchList(this.listQuery).then(response => {
        if (response.data.code === 20000) {
          this.list = response.data.data.items
          this.total = response.data.data.total
          this.listLoading = false
        } else {
          this.$message({
            message: response.data.message,
            type: 'error'
          })
          this.listLoading = false
        }
      })
    },
    // 获取series
    getSeriesOpt() {
      fetchSeries().then(response => {
        this.options = response.data.data
      })
    },
    getStore() {
      const seriesId = this.temp.selectedOptions[1]
      fetchStore(seriesId).then(response => {
        this.temp.store = response.data.data
        const brandId = this.temp.selectedOptions[0]
        this.temp.brandId = brandId
        this.temp.seriesId = seriesId
        for (const v of this.options) {
          if (v.value === brandId) {
            this.temp.brandName = v.label
            for (const u of v.children) {
              if (u.value === seriesId) {
                this.temp.seriesName = u.label
                break
              }
            }
            break
          }
        }
        this.temp.carId = null
      })
    },
    getPrice() {
      for (const v of this.temp.store) {
        if (v.id === this.temp.carId) {
          this.temp.salePrice = v.salePrice
          break
        }
      }
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
    handleUpdate(row, orderId) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.selectedOptions = [row.brandId, row.seriesId]
      this.temp.orderId = orderId
      fetchStore(this.temp.seriesId).then(response => {
        this.temp.store = response.data.data
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      })
    },
    handleUpdateStatus(row) {
      this.updateStatus = Object.assign({}, row) // copy obj
      this.dialogStatusVisible = true
      this.$nextTick(() => {
        this.$refs['statusForm'].clearValidate()
      })
    },
    updateStatusData() {
      this.$refs['statusForm'].validate((valid) => {
        if (valid) {
          updateStatus({
            orderId: this.updateStatus.orderId,
            status: this.updateStatus.status
          }).then(response => {
            if (response.data.code === 20000) {
              this.updateStatus.payTime = new Date()
              this.updateStatus.updateTime = new Date()
              for (const v of this.list) {
                if (v.orderId === this.updateStatus.orderId) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.updateStatus)
                  break
                }
              }
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
            this.dialogStatusVisible = false
          })
        }
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          for (const v of this.temp.store) {
            if (v.id === this.temp.carId) {
              tempData.salePrice = v.salePrice
              tempData.type = v.type
              tempData.color = v.color
            }
          }
          updateDetail({
            id: tempData.id,
            carId: tempData.carId,
            orderId: tempData.orderId,
            carNumber: tempData.carNumber
          }).then(response => {
            if (response.data.code === 20000) {
              for (const v of this.list) {
                if (v.orderId === this.temp.orderId) {
                  let totalPrice = 0
                  const index = this.list.indexOf(v)
                  const update = this.list[index]
                  for (const u of update.details) {
                    if (u.id === this.temp.id) {
                      const i = update.details.indexOf(u)
                      update.details.splice(i, 1, tempData)
                      break
                    }
                  }
                  for (const u of update.details) {
                    totalPrice += u.salePrice * u.carNumber
                  }
                  update.totalPrice = totalPrice
                  update.updateTime = new Date()
                  this.list.splice(index, 1, update)
                  break
                }
              }
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
            this.dialogFormVisible = false
          })
        }
      })
    },
    handleDelete(row, orderId) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        const tempData = Object.assign({}, row)
        deleteDetail({
          id: tempData.id
        }).then(response => {
          if (response.data.code === 20000) {
            for (const v of this.list) {
              if (v.orderId === orderId) {
                let totalPrice = 0
                const index = this.list.indexOf(v)
                const update = this.list[index]
                for (const u of update.details) {
                  if (u.id === tempData.id) {
                    const i = update.details.indexOf(u)
                    update.details.splice(i, 1)
                    break
                  }
                }
                for (const u of update.details) {
                  totalPrice += u.salePrice * u.carNumber
                }
                update.totalPrice = totalPrice
                this.list.splice(index, 1, update)
                break
              }
            }
            this.$notify({
              title: '成功',
              message: '删除成功',
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
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['订单编号', '客户编号', '客户姓名', '创建时间', '更新时间', '状态']
        const filterVal = ['orderId', 'customerId', 'customerName', 'createTime', 'updateTime', 'status']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '销售订单'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'createTime') {
          return parseTime(v[j])
        } else if (j === 'updateTime') {
          return parseTime(v[j])
        } else if (j === 'status') {
          return orderTypeKeyValue[v[j]]
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>
