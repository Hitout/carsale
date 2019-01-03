<template>
  <div class="app-container">
    <el-header>
      <h3 style="font-family: Microsoft YaHei">新订单</h3>
      <hr style="border: 0.5px solid #dcdfe6">
    </el-header>
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="130px" class="demo-ruleForm" style="width: 500px; margin-left:50px;">
      <el-form-item label="客户身份证号" prop="idCard" >
        <el-input v-model="ruleForm.idCard" />
      </el-form-item>
      <el-form-item label="支付状态" prop="status">
        <el-select v-model="ruleForm.status" class="filter-item" placeholder="Please select">
          <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item"/>
        </el-select>
      </el-form-item>
      <div
        v-for="(domain, index) in ruleForm.domains"
        :label="'车辆' + (index+1)"
        :key="domain.key">
        <hr style="border: 0.5px solid #dcdfe6">
        <el-form-item>
          <el-button class="el-icon-remove-outline" @click.prevent="removeDomain(domain)"> 撤销</el-button>
        </el-form-item>
        <el-form-item
          :rules="{ required: true, message: '必须选择车型', trigger: 'change' }"
          :prop="'domains.' + index + '.selectedOptions'"
          label="品牌/车系/型号">
          <el-cascader :options="options" v-model="domain.selectedOptions" placeholder="品牌/车系/型号" />
        </el-form-item>
        <el-form-item
          :rules="{ required: true, message: '必须选择颜色', trigger: 'change' }"
          :prop="'domains.' + index + '.color'"
          label="颜色">
          <el-select v-model="domain.color" class="filter-item" placeholder="Please select">
            <el-option v-for="item in colorOptions" :key="item" :label="item" :value="item"/>
          </el-select>
        </el-form-item>
        <el-form-item
          :rules="{ required: true, message: '必须填入数量', trigger: 'blur' }"
          :prop="'domains.' + index + '.num'"
          label="数量">
          <el-input-number v-model="domain.num" :min="1"/>
        </el-form-item>
      </div>
      <el-form-item>
        <el-button class="el-icon-circle-plus-outline" @click="addDomain"> 添加车辆</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { validateIdCard } from '@/utils/validate'
import { fetchSeries } from '@/api/init'

export default {
  data() {
    return {
      ruleForm: {
        customerIdCard: undefined,
        status: '未支付',
        domains: [{
          // value: ''
          selectedOptions: [],
          color: undefined,
          num: undefined
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
      statusOptions: ['已支付', '未支付', '已取消'],
      colorOptions: ['曜石黑', '未支付', '已取消'],
      options: [],
      selectedOptions: []
    }
  },
  created() {
    this.getSeriesOpt()
  },
  methods: {
    // 获取options
    getSeriesOpt() {
      this.listLoading = true
      fetchSeries().then(response => {
        this.options = response.data.data
      })
    },

    // 动态表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    removeDomain(item) {
      var index = this.ruleForm.domains.indexOf(item)
      if (index !== 0) {
        this.ruleForm.domains.splice(index, 1)
      }
    },
    addDomain() {
      this.ruleForm.domains.push({
        value: '',
        key: Date.now()
      })
    }
  }
}
</script>
