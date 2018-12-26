<template>
  <div :class="className" :id="id" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'
import resize from './mixins/resize'

// var pay = new Array(20)
// var sale = new Array(20)
// var substract = new Array(20)

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    id: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '200px'
    },
    height: {
      type: String,
      default: '200px'
    }
  },
  data() {
    return {
      // sale: undefined,
      // pay: undefined,
      // substract: undefined,
      start: '2018年01月',
      end: '2018年12月',
      chart: null
    }
  },
  mounted() {
    this.initChart()
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(document.getElementById(this.id))
      // const xData = (function() {
      //   const data = []
      //   for (let i = 1; i < 13; i++) {
      //     data.push(i + 'month')
      //   }
      //   return data
      // })()
      this.chart.setOption({
        // backgroundColor: '#344b58',
        title: {
          text: this.start + '-' + this.end + ' 销量报表',
          x: '20',
          top: '20',
          textStyle: {
            color: '#fff',
            fontSize: '22'
          },
          subtextStyle: {
            color: '#90979c',
            fontSize: '16'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            textStyle: {
              color: '#fff'
            }
          }
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar'] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        calculable: true,
        legend: {
          data: ['收入', '支出', '利润']
        },
        xAxis: [
          {
            type: 'category',
            data: [
              '4月',
              '5月',
              '6月',
              '7月',
              '8月',
              '9月',
              '10月',
              '11月',
              '12月',
              '1月',
              '2月',
              '3月'
            ]
          }
        ],
        yAxis: [{
          type: 'value',
          name: '价格',
          axisLabel: {
            formatter: '{value} W'
          }
        },
        {
          type: 'value',
          name: '利润',
          axisLabel: {
            formatter: '{value} W'
          }
        }],
        series: [{
          name: '收入',
          type: 'bar',
          data: [
            709,
            1917,
            2455,
            2610,
            1719,
            1433,
            1544,
            3285,
            5208,
            3372,
            2484,
            4078
          ]
        },
        {
          name: '支出',
          type: 'bar',
          data: [
            327,
            1776,
            507,
            1200,
            800,
            482,
            204,
            1390,
            1001,
            951,
            381,
            220
          ]
        },
        {
          name: '利润',
          type: 'line',
          yAxisIndex: 1,
          data: [
            709,
            1917,
            2455,
            2610,
            1719,
            1433,
            1544,
            3285,
            5208,
            3372,
            2484,
            4078
          ]
        }]
      })
    }
  }
}
</script>
