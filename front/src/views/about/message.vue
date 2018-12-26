<template>
  <div class="my-container">
    <h2>我的信息</h2>
    <hr>
    <div>
      <ul>
        <el-row :gutter="32">
          <el-col :xs="24" :sm="24" :lg="12">
            <li>
              <label>姓名：</label>
              <span>{{ info.name }}</span>
            </li>
            <li>
              <label>员工编号：</label>
              <span>{{ info.id }}</span>
            </li>
            <li>
              <label>角色：</label>
              <span>{{ info.role }}</span>
            </li>
            <li>
              <label>入职时间：</label>
              <span>{{ info.entryTime }}</span>
            </li>
            <li>
              <label>薪资：</label>
              <span>{{ info.salary }}</span>
            </li>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="12">
            <li>
              <label>联系电话：</label>
              <span>{{ info.phone }}</span>
            </li>
            <li>
              <label>身份证号：</label>
              <span>{{ info.idCard }}</span>
            </li>
            <li>
              <label>性别：</label>
              <span>{{ info.gender }}</span>
            </li>
            <li>
              <label>年龄：</label>
              <span>{{ info.age }}</span>
            </li>
            <li>
              <label>状态：</label>
              <span>{{ info.status }}</span>
            </li>
          </el-col>
        </el-row>
      </ul>
      <div class="edit-button">
        <el-button type="primary" @click="handleUpdate()">修改信息</el-button>
        <el-button type="primary" @click="passwordUpdate()">修改密码</el-button>
      </div>
    </div>

    <el-dialog :visible.sync="dialogFormVisible" title="修改信息">
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
        <el-form-item label="年龄" prop="age">
          <el-input v-model.number="temp.age"/>
        </el-form-item>
        <el-form-item label="薪资/月" prop="salary">
          <el-input v-model="temp.salary"/>
        </el-form-item>
        <el-form-item label="入职日期" prop="date">
          <el-date-picker v-model="temp.date" type="date" placeholder="选择日期" style="width: 100%;"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status" placeholder="请选择">
            <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateData()">确 认</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="dialogPassVisible" title="修改密码">
      <el-form ref="dataForm" :rules="rules" :model="ruleForm" label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="旧密码" prop="oldPass">
          <el-input v-model="ruleForm.oldPass" type="password" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="新密码" prop="newPass">
          <el-input v-model="ruleForm.newPass" type="password" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input v-model="ruleForm.checkPass" type="password" autocomplete="off"/>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogPassVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateData()">确 认</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { updateArticle } from '@/api/article'
import { validateIdCard, validateAge, validateSalary } from '@/utils/validate'

export default {
  data() {
    var validateOldPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入旧密码'))
      } else if (value !== this.info.pass) {
        callback(new Error('旧密码不正确!'))
      } else {
        callback()
      }
    }
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          // this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.newPass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      info: {
        name: '张三',
        id: '2345623456',
        role: '销售',
        entryTime: '2018年09月18日',
        salary: 5600,
        phone: '12345612345',
        idCard: '431762198708133546',
        gender: '男',
        age: 28,
        status: '在职',
        pass: '123'
      },
      temp: {
        name: '',
        id: '',
        role: '',
        entryTime: '',
        salary: undefined,
        phone: '',
        idCard: '',
        gender: '',
        age: undefined
      },
      ruleForm: {
        oldPass: '',
        newPass: '',
        checkPass: ''
      },
      dialogFormVisible: false,
      dialogPassVisible: false,
      statusOptions: ['在职', '已离职'],
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
        date: [
          { type: 'date', required: true, message: '请选择入职日期', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择员工就职状态', trigger: 'blur' }
        ],
        salary: [
          { validator: validateSalary, required: true, trigger: 'blur' }
        ],
        age: [
          { validator: validateAge, required: true, trigger: 'blur' }
        ],
        oldPass: [
          { validator: validateOldPass, required: true, trigger: 'blur' }
        ],
        newPass: [
          { validator: validatePass, required: true, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, required: true, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleUpdate() {
      this.temp = Object.assign({}, this.info) // copy obj
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    passwordUpdate() {
      this.dialogPassVisible = true
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
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.my-container {
  padding: 32px;
}
hr {
  border: 0.5px solid #dcdfe6;
}
h2 {
  font-family: Microsoft YaHei;
  text-align: center;
  font-weight: normal;
}
ul li {
  list-style: none;
  padding: 20px;
  font-size: 16px;
  color: #606266;
}
li > label:first-child {
  width: 100px;
  text-align: right;
  float: left;
}
li > span {
  margin-left: 15px;
}
.edit-button {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}
</style>
