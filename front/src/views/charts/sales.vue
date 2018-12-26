<template>
  <div class="chart-container">
    <chart height="100%" width="100%"/>
    <el-form ref="form" :model="sizeForm" label-width="80px" size="mini" class="month-select">
      <el-form-item label="时间">
        <el-col :span="11">
          <!-- <el-date-picker v-model="sizeForm.date1" type="date" placeholder="选择日期" style="width: 100%;"/> -->
          <el-select v-model="month1" placeholder="月份" style="width: 100%;">
            <el-option
              v-for="item in options"
              :key="item"
              :label="item"
              :value="item"/>
          </el-select>
        </el-col>
        <el-col :span="2" class="line">-</el-col>
        <el-col :span="11">
          <!-- <el-time-picker v-model="sizeForm.date2" type="fixed-time" placeholder="选择时间" style="width: 100%;"/> -->
          <el-select v-model="month2" placeholder="月份" style="width: 100%;">
            <el-option
              v-for="item in options"
              :key="item"
              :label="item"
              :value="item"/>
          </el-select>
        </el-col>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Chart from './components/mixChart'

export default {
  name: 'MixChart',
  components: { Chart },
  data() {
    return {
      options: ['一月', '二月'],
      month1: undefined,
      month2: undefined,
      sizeForm: {
        date1: '',
        date2: ''
      }
    }
  }
}
</script>

<style scoped>
.chart-container{
  position: relative;
  width: 100%;
  height: calc(100vh - 84px);
}
.month-select{
  position: absolute;
  top: 12%;
  right: 9%;
  color: aliceblue;
}
.line{
  text-align: center;
}
</style>

<style>
.el-form-item__label{
  color: aliceblue;
}
</style>
