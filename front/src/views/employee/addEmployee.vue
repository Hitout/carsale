<template>
  <div class="app-container">
    <el-header>
      <h3 style="font-family: Microsoft YaHei">添加员工</h3>
      <hr style="border: 0.5px solid #dcdfe6">
    </el-header>
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
      <el-row :gutter="24">
        <el-col :xs="24" :sm="24" :lg="12" style="width: 400px; margin-left:50px;">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="ruleForm.name"/>
          </el-form-item>
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="ruleForm.phone"/>
          </el-form-item>
          <el-form-item label="身份证号" prop="idCard">
            <el-input v-model="ruleForm.idCard"/>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio-group v-model="ruleForm.gender">
              <el-radio label="男"/>
              <el-radio label="女"/>
            </el-radio-group>
          </el-form-item>
          <!--<el-form-item label="年龄" prop="age">-->
          <!--<el-input v-model.number="ruleForm.age"/>-->
          <!--</el-form-item>-->
          <el-form-item label="入职日期" prop="entryTime">
            <el-date-picker v-model="ruleForm.entryTime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%;"/>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="12" style="width: 400px; margin-left:50px;">
          <el-form-item label="薪资/月" prop="salary">
            <el-input v-model.number="ruleForm.salary"/>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="ruleForm.status" placeholder="请选择">
              <el-option v-for="item in statusOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
            </el-select>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="ruleForm.password" type="password" auto-complete="off"/>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input v-model="ruleForm.checkPass" type="password" auto-complete="off"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="7">
          <el-form-item style="margin: 0;">
            <el-button type="primary" @click="submitForm('ruleForm')">添加</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { validateIdCard, validateAge, validateSalary } from '@/utils/validate'
import { addEmployee } from '@/api/employee'

const statusOptions = [
  { key: '1', display_name: '在职' },
  { key: '0', display_name: '已离职' }
]

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (value.length < 6) {
          callback(new Error('密码长度至少大于六位'))
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass')
          }
          callback()
        }
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {
        name: '',
        phone: '',
        idCard: '',
        // age: '',
        gender: undefined,
        salary: undefined,
        entryTime: undefined,
        status: '1',
        password: '',
        checkPass: ''
      },
      statusOptions,
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
        entryTime: [
          { required: true, message: '请选择入职日期', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择员工就职状态', trigger: 'blur' }
        ],
        salary: [
          { validator: validateSalary, required: true, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass, required: true, trigger: 'blur' }
        ],
        age: [
          { validator: validateAge, required: true, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, required: true, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          addEmployee(this.ruleForm).then(response => {
            if (response.data.code === 20000) {
              this.$notify({
                title: '成功',
                message: '添加成功',
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
            this.resetForm(formName)
          })
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
