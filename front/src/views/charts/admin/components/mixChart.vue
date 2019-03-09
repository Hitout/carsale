<template>
  <div :class="className" :id="id" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'
import { debounce } from '@/utils'

export default {
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
    },
    option: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      // start: '2018年01月',
      // end: '2018年12月',
      myChart: null
    }
  },
  computed: {
    opt() {
      const obj = {
        backgroundColor: '#344b58',
        title: {
          text: this.option.start + '-' + this.option.end + ' 销量报表',
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
            magicType: { show: true, type: ['line', 'bar', 'stack', 'tiled'] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        grid: {
          left: '5%',
          right: '5%',
          borderWidth: 0,
          top: 150,
          bottom: 95,
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          x: '5%',
          top: '10%',
          textStyle: {
            color: '#90979c'
          },
          data: ['收入', '支出', '利润']
        },
        calculable: true,
        xAxis: [{
          type: 'category',
          axisLine: {
            lineStyle: {
              color: '#90979c'
            }
          },
          splitLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          splitArea: {
            show: false
          },
          axisLabel: {
            interval: 0
          },
          data: this.option.xAxis.data
        }],
        yAxis: [{
          type: 'value',
          name: '支出 & 收入',
          splitLine: {
            show: false
          },
          axisLine: {
            lineStyle: {
              formatter: '{value} W',
              color: '#90979c'
            }
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            interval: 0
          },
          splitArea: {
            show: false
          }
        },
        {
          type: 'value',
          name: '利润',
          splitLine: {
            show: false
          },
          axisLine: {
            lineStyle: {
              formatter: '{value} W',
              color: '#90979c'
            }
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            interval: 0
          },
          splitArea: {
            show: false
          }
        }],
        series: [{
          name: '利润',
          type: 'line',
          symbolSize: 10,
          symbol: 'circle',
          yAxisIndex: 1,
          itemStyle: {
            normal: {
              color: 'rgba(252,230,48,1)',
              barBorderRadius: 0,
              label: {
                show: true,
                position: 'top',
                formatter(p) {
                  return p.value > 0 ? p.value : ''
                }
              }
            }
          },
          data: this.option.series[2].data
        },
        {
          name: '收入',
          type: 'bar',
          // stack: 'total',
          barMaxWidth: 35,
          barGap: '10%',
          itemStyle: {
            normal: {
              color: 'rgba(255,144,128,1)',
              label: {
                show: true,
                position: 'top',
                textStyle: {
                  color: '#fff'
                },
                // position: 'insideTop',
                formatter(p) {
                  return p.value > 0 ? p.value : ''
                }
              }
            }
          },
          data: this.option.series[0].data
        },
        {
          name: '支出',
          type: 'bar',
          // stack: 'total',
          barMaxWidth: 35,
          barGap: '10%',
          itemStyle: {
            normal: {
              color: 'rgba(0,191,183,1)',
              barBorderRadius: 0,
              label: {
                show: true,
                position: 'top',
                formatter(p) {
                  return p.value > 0 ? p.value : ''
                }
              }
            }
          },
          data: this.option.series[1].data
        }]
      }
      return obj
    }
  },
  watch: {
    option: function() {
      this.chartChange()
    }
  },
  updated() {
    if (!this.myChart) {
      this.initChart()
    }
    this.ChartChange()
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
      this.myChart = echarts.init(document.getElementById(this.id))
      this.myChart.setOption(this.opt)
    },
    chartChange() {
      this.myChart.setOption(this.opt)
    }
  }
}
</script>
