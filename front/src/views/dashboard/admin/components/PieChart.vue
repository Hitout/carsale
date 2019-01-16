<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import { debounce } from '@/utils'

export default {
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    option: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      myChart: {}
    }
  },
  computed: {
    opt() {
      const obj = {
        title: {
          text: '昨日销售额',
          x: '20',
          top: '20',
          textStyle: {
            fontSize: '22'
          },
          subtextStyle: {
            color: '#90979c',
            fontSize: '16'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} ({d}%)'
        },
        legend: {
          left: 'center',
          bottom: '10',
          data: this.option.legend.data
        },
        calculable: true,
        series: [
          {
            type: 'pie',
            roseType: 'radius',
            radius: [15, 95],
            center: ['50%', '38%'],
            data: this.option.series.data,
            animationEasing: 'cubicInOut',
            animationDuration: 1600
          }
        ]
      }
      return obj
    }
  },
  watch: {
    option: function() {
      this.chartChange()
    }
  },
  mounted() {
    this.initChart()
    this.__resizeHandler = debounce(() => {
      if (this.myChart) {
        this.myChart.resize()
      }
    }, 100)
    window.addEventListener('resize', this.__resizeHandler)
  },
  updated() {
    if (!this.myChart) {
      this.initChart()
    }
    this.ChartChange()
  },
  beforeDestroy() {
    if (!this.myChart) {
      return
    }
    window.removeEventListener('resize', this.__resizeHandler)
    this.myChart.dispose()
    this.myChart = null
  },
  methods: {
    initChart() {
      this.myChart = echarts.init(this.$el, 'macarons')

      this.myChart.setOption(this.opt)
    },
    chartChange() {
      this.myChart.setOption(this.opt)
    }
  }
}
</script>
