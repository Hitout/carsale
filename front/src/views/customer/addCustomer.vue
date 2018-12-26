<template>
  <div class="app-container">
    <el-header>
      <h3 style="font-family: Microsoft YaHei">添加客户</h3>
      <hr style="border: 0.5px solid #dcdfe6">
    </el-header>
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm" style="width: 400px; margin-left:50px;">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="ruleForm.name"/>
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input v-model="ruleForm.phone"/>
      </el-form-item>
      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="ruleForm.idCard"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">添加</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { validateIdCard } from '@/utils/validate'

export default {
  data() {
    // var validateIdCard = (rule, value, callback) => {
    //   if (value === '' || value === undefined) {
    //     callback(new Error('请输入身份证号'))
    //   } else {
    //     if (!validateIdCard(value)) {
    //       callback(new Error('请输入正确的身份证号'))
    //     }
    //     callback()
    //   }
    // }
    return {
      ruleForm: {
        name: '',
        phone: '',
        idCard: ''
      },
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
      }
    }
  },
  methods: {
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
    }
  }
}
</script>
