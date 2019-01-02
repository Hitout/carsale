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
import { fetchOpt } from '@/api/order'

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
        value: 'zujian',
        label: '组件',
        children: [{
          value: 'basic',
          label: 'Basic',
          children: [{
            value: 'layout',
            label: 'Layout 布局'
          }, {
            value: 'color',
            label: 'Color 色彩'
          }, {
            value: 'typography',
            label: 'Typography 字体'
          }, {
            value: 'icon',
            label: 'Icon 图标'
          }, {
            value: 'button',
            label: 'Button 按钮'
          }]
        }, {
          value: 'form',
          label: 'Form',
          children: [{
            value: 'radio',
            label: 'Radio 单选框'
          }, {
            value: 'checkbox',
            label: 'Checkbox 多选框'
          }, {
            value: 'input',
            label: 'Input 输入框'
          }, {
            value: 'input-number',
            label: 'InputNumber 计数器'
          }, {
            value: 'select',
            label: 'Select 选择器'
          }, {
            value: 'cascader',
            label: 'Cascader 级联选择器'
          }, {
            value: 'switch',
            label: 'Switch 开关'
          }, {
            value: 'slider',
            label: 'Slider 滑块'
          }, {
            value: 'time-picker',
            label: 'TimePicker 时间选择器'
          }, {
            value: 'date-picker',
            label: 'DatePicker 日期选择器'
          }, {
            value: 'datetime-picker',
            label: 'DateTimePicker 日期时间选择器'
          }, {
            value: 'upload',
            label: 'Upload 上传'
          }, {
            value: 'rate',
            label: 'Rate 评分'
          }, {
            value: 'form',
            label: 'Form 表单'
          }]
        }, {
          value: 'data',
          label: 'Data',
          children: [{
            value: 'table',
            label: 'Table 表格'
          }, {
            value: 'tag',
            label: 'Tag 标签'
          }, {
            value: 'progress',
            label: 'Progress 进度条'
          }, {
            value: 'tree',
            label: 'Tree 树形控件'
          }, {
            value: 'pagination',
            label: 'Pagination 分页'
          }, {
            value: 'badge',
            label: 'Badge 标记'
          }]
        }, {
          value: 'notice',
          label: 'Notice',
          children: [{
            value: 'alert',
            label: 'Alert 警告'
          }, {
            value: 'loading',
            label: 'Loading 加载'
          }, {
            value: 'message',
            label: 'Message 消息提示'
          }, {
            value: 'message-box',
            label: 'MessageBox 弹框'
          }, {
            value: 'notification',
            label: 'Notification 通知'
          }]
        }, {
          value: 'navigation',
          label: 'Navigation',
          children: [{
            value: 'menu',
            label: 'NavMenu 导航菜单'
          }, {
            value: 'tabs',
            label: 'Tabs 标签页'
          }, {
            value: 'breadcrumb',
            label: 'Breadcrumb 面包屑'
          }, {
            value: 'dropdown',
            label: 'Dropdown 下拉菜单'
          }, {
            value: 'steps',
            label: 'Steps 步骤条'
          }]
        }, {
          value: 'others',
          label: 'Others',
          children: [{
            value: 'dialog',
            label: 'Dialog 对话框'
          }, {
            value: 'tooltip',
            label: 'Tooltip 文字提示'
          }, {
            value: 'popover',
            label: 'Popover 弹出框'
          }, {
            value: 'card',
            label: 'Card 卡片'
          }, {
            value: 'carousel',
            label: 'Carousel 走马灯'
          }, {
            value: 'collapse',
            label: 'Collapse 折叠面板'
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
      }],
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
      fetchOpt().then(response => {
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
