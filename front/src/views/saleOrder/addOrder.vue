<template>
  <div class="app-container">
    <el-header>
      <h3 style="font-family: Microsoft YaHei">新订单</h3>
      <hr style="border: 0.5px solid #dcdfe6">
    </el-header>
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="130px" class="demo-ruleForm" style="width: 500px; margin-left:50px;">
      <el-form-item label="客户身份证号" prop="idCard" >
        <el-input v-model="ruleForm.idCard" @change="getCustomer"/>
      </el-form-item>
      <el-form-item label="客户姓名">
        <span style="margin-left: 15px" v-html="ruleForm.customer.name"/>
      </el-form-item>
      <el-form-item label="支付状态" prop="status">
        <el-select v-model="ruleForm.status" class="filter-item" placeholder="Please select">
          <el-option v-for="item in orderStatusOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
        </el-select>
      </el-form-item>
      <div
        v-for="(domain, index) in ruleForm.domains"
        :key="domain.key">
        <hr style="border: 0.5px solid #dcdfe6">
        <el-form-item>
          <el-button class="el-icon-remove-outline" @click.prevent="removeDomain(domain)"> 撤销</el-button>
        </el-form-item>
        <el-form-item
          :rules="{ required: true, message: '必须选择车系', trigger: 'change' }"
          :prop="'domains.' + index + '.selectedOptions'"
          label="品牌 & 车系">
          <el-cascader :options="options" v-model="domain.selectedOptions" placeholder="品牌/车系" @change="getStore(index)"/>
        </el-form-item>
        <el-form-item
          :rules="{ required: true, message: '必须选择型号', trigger: 'change' }"
          :prop="'domains.' + index + '.carId'"
          label="型号 & 颜色">
          <el-select v-model="domain.storeIndex" class="filter-item" placeholder="Please select" style="width: 100%" @change="getPrice(index)">
            <el-option
              v-for="(item, storeIndex) in domain.store"
              :key="item.id"
              :label="item.type + ' ' + item.color"
              :value="storeIndex">
              <span>{{ item.type }}</span>
              <el-tag>{{ item.color }}</el-tag>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :rules="{ required: true, message: '必须填入数量', trigger: 'blur' }"
          :prop="'domains.' + index + '.carNumber'"
          label="数量">
          <el-input-number v-model="domain.carNumber" :min="1" @change="getTotalPrice"/>
        </el-form-item>
        <el-form-item label="售价">
          <span v-if="domain.storeIndex !== null" style="margin-left: 15px">{{ domain.salePrice }} 元/辆</span>
        </el-form-item>
      </div>
      <el-form-item>
        <el-button class="el-icon-circle-plus-outline" @click="addDomain"> 添加车辆</el-button>
      </el-form-item>
      <hr style="border: 0.5px solid #dcdfe6">
      <el-form-item label="总价">
        <span style="margin-left: 15px">{{ ruleForm.totalPrice }}</span> 元
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { validateIdCard, validIdCard } from '@/utils/validate'
import { fetchSeries, fetchStore, fetchCustomer } from '@/api/init'
import { addOrder } from '@/api/order'

const orderStatusOptions = [
  { key: '0', display_name: '未支付' },
  { key: '1', display_name: '已支付' }
]

export default {
  data() {
    return {
      ruleForm: {
        idCard: undefined,
        customer: {
          id: undefined,
          name: ''
        },
        status: '0',
        totalPrice: null,
        domains: [{
          storeIndex: null,
          carId: null,
          carNumber: 0,
          salePrice: 0,
          selectedOptions: [],
          store: null
        }]
      },
      rules: {
        status: [
          { required: true, message: '请选择支付状态', trigger: 'blur' }
        ],
        idCard: [
          { required: true, validator: validateIdCard, trigger: 'blur' }
        ]
      },
      orderStatusOptions,
      options: [],
      commitAble: false
    }
  },
  created() {
    this.getSeriesOpt()
  },
  methods: {
    // 获取options
    getSeriesOpt() {
      fetchSeries().then(response => {
        this.options = response.data.data
      })
    },

    // 获取库存
    getStore(index) {
      fetchStore(this.ruleForm.domains[index].selectedOptions[1]).then(response => {
        this.ruleForm.domains[index].store = response.data.data
        this.ruleForm.domains[index].carId = null
        this.ruleForm.domains[index].storeIndex = null
      })
    },

    getPrice(index) {
      const storeIndex = this.ruleForm.domains[index].storeIndex
      this.ruleForm.domains[index].salePrice = this.ruleForm.domains[index].store[storeIndex].salePrice
      this.ruleForm.domains[index].carId = this.ruleForm.domains[index].store[storeIndex].id
      this.getTotalPrice()
    },

    getTotalPrice() {
      const domains = this.ruleForm.domains
      let totalPrice = 0
      for (let i = 0; i < domains.length; i++) {
        if (!domains[i]) {
          break
        }
        totalPrice += domains[i].salePrice * domains[i].carNumber
      }
      this.ruleForm.totalPrice = totalPrice
    },

    // 获取用户
    getCustomer() {
      this.resetCustomer()
      this.ruleForm.customerId = null
      if (validIdCard(this.ruleForm.idCard)) {
        fetchCustomer(this.ruleForm.idCard).then(response => {
          if (response.data.code === 20000) {
            this.ruleForm.customer = response.data.data
            this.commitAble = true
          } else {
            this.commitAble = false
            this.ruleForm.customer.name = "<small style='color: red'>" + response.data.message + '</small>'
          }
        })
      }
    },
    resetRuleForm() {
      this.ruleForm = {
        idCard: undefined,
        customer: {
          id: undefined,
          name: ''
        },
        status: '0',
        totalPrice: null,
        domains: [{
          storeIndex: null,
          carId: null,
          carNumber: 0,
          salePrice: 0,
          selectedOptions: [],
          store: null
        }]
      }
    },
    resetCustomer() {
      this.ruleForm.customer = {
        id: undefined,
        name: ''
      }
    },
    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid && this.commitAble) {
          const tempData = Object.assign({}, this.ruleForm)
          const employeeId = this.$store.getters.id
          if (employeeId === null && employeeId === '') {
            this.$message({
              message: '登录信息有误，请重新登录!',
              type: 'error'
            })
          }
          addOrder({
            employeeId: employeeId,
            customerId: tempData.customer.id,
            status: tempData.status,
            totalPrice: tempData.totalPrice,
            detailVos: tempData.domains
          }).then(response => {
            if (response.data.code === 20000) {
              this.$notify({
                title: '成功',
                message: '添加成功',
                type: 'success',
                duration: 2000
              })
              this.resetForm('ruleForm')
            } else {
              this.$notify({
                title: '错误',
                message: response.data.message,
                type: 'error',
                duration: 2000
              })
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.resetRuleForm()
      this.$refs[formName].resetFields()
    },
    removeDomain(item) {
      const index = this.ruleForm.domains.indexOf(item)
      if (index !== 0) {
        this.ruleForm.domains.splice(index, 1)
      }
    },
    addDomain() {
      this.ruleForm.domains.push({
        storeIndex: null,
        carId: null,
        carNumber: 0,
        salePrice: 0,
        selectedOptions: [],
        store: null,
        key: Date.now()
      })
    }
  }
}
</script>
