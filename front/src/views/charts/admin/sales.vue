<template>
  <div class="chart-container">
    <chart :option="option" height="100%" width="100%"/>
    <el-form ref="form" label-width="100px" size="mini" class="month-select">
      <el-form-item label="时间">
        <el-row :gutter="24">
          <el-col :span="10">
            <el-date-picker
              v-model="start"
              :picker-options="startPicker"
              value-format="yyyy-MM"
              type="month"
              placeholder="开始时间"/>
          </el-col>
          <el-col :span="10">
            <el-date-picker
              v-model="end"
              :picker-options="endPicker"
              value-format="yyyy-MM"
              type="month"
              placeholder="结束时间"/>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="changeChart">确定</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Chart from './components/mixChart'
import { parseTime } from '@/utils'
import { fetchSalesChart } from '@/api/chart'

export default {
  name: 'MixChart',
  components: { Chart },
  data() {
    return {
      start: null,
      end: null,
      startPicker: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        }
      },
      endPicker: {
        disabledDate: (time) => {
          if (this.start != null) {
            return time.getTime() > Date.now() || time.getTime() < this.start
          }
          return time.getTime() > Date.now()
        }
      },
      option: {
        start: undefined,
        end: undefined,
        xAxis: {
          data: []
        },
        series: [{
          data: []
        }, {
          data: []
        }, {
          data: []
        }]
      }
    }
  },
  created() {
    this.initDate()
  },
  methods: {
    initDate() {
      let tempDate = new Date()
      tempDate.setFullYear(tempDate.getFullYear() - 1)
      this.start = parseTime(tempDate, '{y}-{m}')
      // console.log(tempDate.getMonth())
      if (tempDate.getMonth() === 0) {
        tempDate.setMonth(11)
        this.end = parseTime(tempDate, '{y}-{m}')
      } else {
        tempDate = new Date()
        tempDate.setMonth(tempDate.getMonth() - 1)
        this.end = parseTime(tempDate, '{y}-{m}')
      }
      this.changeChart()
    },
    changeChart() {
      if (this.start === null || this.end === null) {
        return
      }
      // console.log(this.start + ' --- ' + this.end)
      fetchSalesChart(
        this.start,
        this.end
      ).then(response => {
        if (response.data.code === 20000) {
          const temp = Object.assign({}, this.option)
          const data = response.data.data
          console.log(data)
          const xData = []
          const series = [{ data: [] }, { data: [] }, { data: [] }]
          for (const v of data) {
            xData.push(v.date)
            series[0].data.push(v.income)
            series[1].data.push(v.expenditure)
            series[2].data.push(v.profit)
          }
          temp.start = parseTime(this.start, '{y}年{m}月')
          temp.end = parseTime(this.end, '{y}年{m}月')
          // const xData = this.getMonthAll(this.start, this.end)
          temp.xAxis.data = xData
          temp.series = series
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
    },
    getMonthAll(begin, end) {
      const d1 = begin
      const d2 = end
      const dateArray = []
      const s1 = d1.split('-')
      const s2 = d2.split('-')
      let mCount = 0
      if (parseInt(s1[0]) < parseInt(s2[0])) {
        mCount = (parseInt(s2[0]) - parseInt(s1[0])) * 12 + parseInt(s2[1]) - parseInt(s1[1]) + 1
      } else {
        mCount = parseInt(s2[1]) - parseInt(s1[1]) + 1
      }
      if (mCount > 0) {
        let startM = parseInt(s1[1])
        let startY = parseInt(s1[0])
        for (let i = 0; i < mCount; i++) {
          if (startM < 12) {
            dateArray[i] = startY + '年' + (startM > 9 ? startM : '0' + startM) + '月'
            startM += 1
          } else {
            dateArray[i] = startY + '年' + (startM > 9 ? startM : '0' + startM) + '月'
            startM = 1
            startY += 1
          }
        }
      }
      return dateArray
    }
  }
}
</script>

<style>
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
.month-select .el-form-item__label{
  color: aliceblue;
}
</style>
