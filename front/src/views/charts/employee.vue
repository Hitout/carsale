<template>
  <div class="chart-container">
    <chart height="100%" width="100%"/>
    <div class="block month-select">
      <el-date-picker
        v-model="month"
        type="month"
        placeholder="月份"/>
    </div>
    <!--<el-select v-model="month" placeholder="月份" class="month-select">
      <el-option
        v-for="item in options"
        :key="item"
        :label="item"
        :value="item"/>
    </el-select>-->
  </div>
</template>

<script>
import Chart from './components/PieChart'

export default {
  name: 'PieChart',
  components: { Chart },
  data() {
    return {
      // options: ['一月', '二月'],
      month: undefined,
      chart: null
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
  top: 15%;
  left: 9%;
}
</style>

