<template>
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper" style="padding-bottom:5px;">
          <el-header>
            <h3>添加库存</h3>
            <hr>
          </el-header>
          <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px" style="width: 400px; margin-left:40px;">
            <!-- <el-form-item label="品牌/车系/型号" prop="selectedOptions">
              <el-cascader :options="options" v-model="temp.selectedOptions" placeholder="品牌/车系/型号" />
            </el-form-item> -->
            <el-form-item label="品牌 & 车系" prop="series">
              <el-cascader :options="options" v-model="ruleForm.series" placeholder="品牌/车系" style="width: 220px"/>
            </el-form-item>
            <el-form-item label="型号" prop="version">
              <el-input v-model="ruleForm.version"/>
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
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">添加</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <el-header>
            <h3>添加品牌</h3>
            <hr>
          </el-header>
          <el-form ref="addBrand" :model="addBrand" :inline="true" label-width="90px" style="width: 420px; margin-left:50px;">
            <el-form-item label="品牌名称" prop="brand" required>
              <el-input v-model="addBrand.brand"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('addBrand')">添加</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="chart-wrapper">
          <el-header>
            <h3>删除品牌</h3>
            <hr>
          </el-header>
          <el-form ref="delBrand" :model="delBrand" :inline="true" label-width="90px" style="width: 420px; margin-left:50px;">
            <el-form-item label="品牌名称" prop="brandOptions" required>
              <el-select v-model="delBrand.brand" class="filter-item" placeholder="请选择">
                <el-option v-for="item in brandOptions" :key="item" :label="item" :value="item"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="danger" @click="submitForm('delBrand')">删除</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="chart-wrapper">
          <el-header>
            <h3>添加车系</h3>
            <hr>
          </el-header>
          <el-form ref="delBrand" :model="addSeries" :inline="true" label-width="90px" style="width: 420px; margin-left:50px;">
            <el-form-item label="车辆品牌" required>
              <el-select v-model="addSeries.brand" class="filter-item" placeholder="请选择">
                <el-option v-for="item in brandOptions" :key="item" :label="item" :value="item"/>
              </el-select>
            </el-form-item>
            <br>
            <el-form-item label="车系名称" required>
              <el-input v-model="addSeries.series" style="width: 220px"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('addBrand')">添加</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="chart-wrapper">
          <el-header>
            <h3>删除车系</h3>
            <hr>
          </el-header>
          <el-form ref="delBrand" :model="delSeries" :inline="true" label-width="100px" style="width: 420px; margin-left:50px;">
            <el-form-item label="品牌 & 车系" required>
              <el-cascader :options="options" v-model="delSeries.series" placeholder="品牌/车系" style="width: 220px"/>
            </el-form-item>
            <el-form-item>
              <el-button type="danger" @click="submitForm('delSeries')">删除</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ruleForm: {
        series: [],
        version: '',
        color: '',
        cost: '',
        price: '',
        num: ''
      },
      addBrand: {
        brand: ''
      },
      brandOptions: ['宝马', '奔驰'],
      delBrand: {
        brand: ''
      },
      addSeries: {
        brand: '',
        series: ''
      },
      delSeries: {
        series: []
      },
      rules: {
        series: [
          { required: true, message: '请输入品牌 & 车系', trigger: 'blur' }
        ],
        version: [
          { required: true, message: '请输入车辆型号', trigger: 'blur' }
        ],
        color: [
          { required: true, message: '请输入车辆颜色', trigger: 'blur' }
        ],
        cost: [
          { required: true, message: '请输入车辆进价', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入车辆售价', trigger: 'blur' }
        ],
        num: [
          { required: true, message: '请输入车辆库存', trigger: 'blur' }
        ]
      },
      options: [{
        value: 'zhinan',
        label: '指南',
        children: [{
          value: 'shejiyuanze',
          label: '设计原则',
          children: [{
            value: 'yizhi',
            label: '一致'
          }, {
            value: 'fankui',
            label: '反馈'
          }, {
            value: 'xiaolv',
            label: '效率'
          }, {
            value: 'kekong',
            label: '可控'
          }]
        }, {
          value: 'daohang',
          label: '导航',
          children: [{
            value: 'cexiangdaohang',
            label: '侧向导航'
          }, {
            value: 'dingbudaohang',
            label: '顶部导航'
          }]
        }]
      }, {
        value: 'ziyuan',
        label: '资源',
        children: [{
          value: 'axure',
          label: 'Axure Components'
        }, {
          value: 'sketch',
          label: 'Sketch Templates'
        }, {
          value: 'jiaohu',
          label: '组件交互文档'
        }]
      }]
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

<style rel="stylesheet/scss" lang="scss" scoped>
hr {
  border: 0.5px solid #dcdfe6;
}
h3 {
  font-family: Microsoft YaHei;
}
.app-container {
  padding: 24px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 8px 8px 0;
    margin-bottom: 16px;
  }
}
</style>
