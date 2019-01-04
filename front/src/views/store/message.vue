<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.carId" placeholder="车辆编号" style="width: 160px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <!--<el-input v-model="listQuery.title" placeholder="品牌" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>-->
      <!--<el-input v-model="listQuery.series" placeholder="车系" style="width: 120px;" class="filter-item" @keyup.enter.native="handleFilter"/>-->
      <el-select v-model="listQuery.brandId" class="filter-item" placeholder="品牌" style="width: 160px;" @keyup.enter.native="handleFilter">
        <el-option v-for="item in brandOptions" :key="item.brandId" :label="item.brandName" :value="item.brandId"/>
      </el-select>
      <el-select v-model="listQuery.seriesId" class="filter-item" placeholder="车系" style="width: 160px;" @keyup.enter.native="handleFilter">
        <el-option v-for="item in seriesOptions" :key="item.seriesId" :label="item.seriesName" :value="item.seriesId"/>
      </el-select>
      <el-select v-model="listQuery.type" placeholder="状态" clearable class="filter-item" style="width: 110px">
        <el-option v-for="item in carStatusOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="list"
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange">
      <el-table-column label="车辆编号" prop="carId" sortable="custom" align="center" min-width="105px">
        <template slot-scope="scope">
          <!-- <span>{{ scope.row.carId }}</span> -->
          <span>3456798765</span>
        </template>
      </el-table-column>
      <el-table-column label="品牌/车系/型号" align="center" min-width="215px">
        <template slot-scope="scope">
          <!-- <span>{{ scope.row.carId }}</span> -->
          <span>宝马7系</span><br>
          <span>2018款 730Li 领先型 M运动套装</span>
        </template>
      </el-table-column>
      <el-table-column label="颜色" align="center" min-width="65px">
        <template slot-scope="scope">
          <!-- <span>{{ scope.row.carColor }}</span> -->
          <span>矿石白</span>
        </template>
      </el-table-column>
      <el-table-column label="进价" sortable="custom" align="center" min-width="105px">
        <template slot-scope="scope">
          <!-- <span>{{ scope.row.carId }}</span> -->
          <span>￥ 859000</span>
        </template>
      </el-table-column>
      <el-table-column label="售价" sortable="custom" align="center" min-width="105px">
        <template slot-scope="scope">
          <!-- <span>{{ scope.row.carId }}</span> -->
          <span>￥ 889000</span>
        </template>
      </el-table-column>
      <!--<el-table-column label="入库数" prop="storeNum" min-width="85px" sortable="custom" align="center">-->
        <!--<template slot-scope="scope">-->
          <!--<span>30</span>-->
        <!--</template>-->
      <!--</el-table-column>-->
      <el-table-column label="库存数" prop="storeNum" min-width="85px" sortable="custom" align="center">
        <template slot-scope="scope">
          <!-- <span>{{ scope.row.carId }}</span> -->
          <span>24</span>
        </template>
      </el-table-column>
      <el-table-column label="入库时间" prop="date" sortable="custom" min-width="135px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.timestamp | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" min-width="90px">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin'])" label="操作" align="center" min-width="80px">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog v-if="checkPermission(['admin'])" :visible.sync="dialogFormVisible" title="修改库存信息">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="品牌 & 车系" prop="series">
          <!-- <el-input v-model="ruleForm.series"/> -->
          <el-cascader :options="options" v-model="ruleForm.series" placeholder="品牌/车系" style="width: 220px"/>
        </el-form-item>
        <el-form-item label="型号" prop="type">
          <el-input v-model="ruleForm.type"/>
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-input v-model="ruleForm.color"/>
        </el-form-item>
        <el-form-item label="进价" prop="cost">
          <el-input v-model="ruleForm.cost"/>
        </el-form-item>
        <el-form-item label="售价" prop="price">
          <el-input v-model="ruleForm.price"/>
        </el-form-item>
        <el-form-item label="数量" prop="num">
          <el-input-number v-model="ruleForm.num" :min="0"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="updateData">确认</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { fetchList, updateArticle } from '@/api/article'
import waves from '@/directive/waves' // Waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import checkPermission from '@/utils/permission' // 权限判断函数
import { fetchBrand, fetchSeries } from '@/api/init'

const carStatusOptions = [
  { key: '0', display_name: '停售' },
  { key: '1', display_name: '在售' },
  { key: '2', display_name: '缺货' }
]

export default {
  // name: 'ComplexTable',
  components: { Pagination },
  directives: {
    waves
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
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
        limit: 10,
        importance: undefined,
        title: undefined,
        type: undefined
      },
      carStatusOptions,
      // statusOptions: ['在售', '缺货', '停售'],
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      // dialogFormVisible：编辑或添加Form的可见性
      dialogFormVisible: false,
      dialogPvVisible: false,
      ruleForm: {
        series: [],
        type: '',
        color: '',
        cost: '',
        price: '',
        num: ''
      },
      options: [],
      rules: {
        seriesId: [
          { required: true, message: '请输入品牌 & 车系' }
        ],
        type: [
          { required: true, message: '请输入车辆型号', trigger: 'blur' }
        ],
        color: [
          { required: true, message: '请输入车辆颜色', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入车辆进价', trigger: 'blur' },
          { type: 'number', message: '进价必须为数字值' }
        ],
        salePrice: [
          { required: true, message: '请输入车辆售价', trigger: 'blur' },
          { type: 'number', message: '售价必须为数字值' }
        ],
        repertory: [
          { required: true, message: '请输入车辆库存' }
        ]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取series
    getSeriesOpt() {
      this.listLoading = true
      fetchSeries().then(response => {
        this.options = response.data.data
      })
    },
    // 获取brand
    getBrandOpt() {
      this.listLoading = true
      fetchBrand().then(response => {
        this.brandOptions = response.data.data
      })
    },
    checkPermission,
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.data.items
        this.total = response.data.total

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
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
      if (prop === 'id') {
        this.sortByID(order)
      } else if (prop === 'date') {
        // 按日期排序
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          updateArticle(tempData).then(() => {
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
