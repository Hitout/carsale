<template>
  <div class="chart-container">
    <chart
      :option="option"
      height="100%"
      width="100%"/>
    <div class="block month-select">
      <el-date-picker
        v-model="date"
        :picker-options="pickerOpt"
        type="month"
        placeholder="月份"
        @change="changeChart"/>
    </div>
  </div>
</template>

<script>
import Chart from './components/PieChart'
import { parseTime } from '@/utils'
import { fetchEmpSalesChart } from '@/api/chart'

export default {
  name: 'PieChart',
  components: { Chart },
  data() {
    return {
      date: null,
      pickerOpt: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6
        }
      },
      option: {
        name: null,
        date: null,
        legend: {
          data: []
        },
        series: {
          data: []
        }
      }
    }
  },
  created() {
    this.initDate()
  },
  methods: {
    initDate() {
      this.option.name = this.$store.getters.name
      // 上个月
      const tempDate = new Date()
      if (tempDate.getMonth() === 0) {
        tempDate.setFullYear(tempDate.getFullYear() - 1)
        tempDate.setMonth(11)
        this.date = parseTime(tempDate, '{y}-{m}')
      } else {
        tempDate.setMonth(tempDate.getMonth() - 1)
        this.date = parseTime(tempDate, '{y}-{m}')
      }
      this.changeChart()
    },
    changeChart() {
      if (this.date === null) {
        return
      }
      if (this.date > new Date()) {
        this.$message.error('输入错误')
        return
      }
      const temp = Object.assign({}, this.option)
      fetchEmpSalesChart(this.$store.getters.id, parseTime(this.date, '{y}-{m}')).then(response => {
        if (response.data.code === 20000) {
          temp.series.data = response.data.data
          const legendData = []
          for (const v of temp.series.data) {
            legendData.push(v.name)
          }
          temp.legend.data = legendData
          temp.date = parseTime(this.date, '{y}年{m}月')
          console.log(temp.month + this.date)
          console.log(temp)
          this.option = temp
        } else {
          this.$notify({
            title: '错误',
            message: response.data.message,
            type: 'error',
            duration: 2000
          })
        }
      })
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

