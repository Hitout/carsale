<template>
  <div class="r-echarts-line">
    <div class="top">
      <div class="title">
        {{origin.title}}
      </div>
      <div class="select-list">
        <Select style="width:120px;margin-right:.5rem" v-model="pagePick">
          <Option v-for="item in origin.page_select" :key="item" :value="item.val">{{item.name}}</Option>
        </Select>
        <Select style="width:120px" v-model="typePick">
          <Option v-for="item in typeList" :value="item.name" :key="item">{{item.name}}</Option>
        </Select>
      </div>
    </div>
    <div class="des">说明：符合于本次筛选条件的共有<span class='tips'>{{origin.desc}}</span>条<span style="font-weight:700;color:black">职位信息</span>。</div>
    <div class="bottom" id="echart" ref="mychart">

    </div>
  </div>
</template>

<script>
// echarts相关
let echarts = require('echarts/lib/echarts');
require('echarts/lib/chart/bar');
require('echarts/lib/component/tooltip');
require('echarts/lib/component/toolbox');
require('echarts/lib/component/legend');
require('echarts/lib/component/markLine');

export default {
  name: 'r-echarts-line',
  data () {
    return {
      typePick: '数值',
      typeList: [
        {
          name: '数值'
        },
        {
          name: '百分比'
        }
      ],
      pagePick: 0,
      // myChart实例
      myChart: {},
      percent: {
        label: {
          normal: {
            show: true,
            position: 'inside',
            formatter: '{c}%'
          }
        }
      },
      numeric: {
        label: {
          normal: {
            show: true,
            position: 'inside',
            formatter: '{c}'
          }
        }
      }
    }
  },
  props: {
    index: {
      required: true,
      type: Number
    },
    data: {
      required: true,
      type: Object
    }
  },
  mounted () {
    this.setEchart();
  },
  updated () {
    if (!this.myChart) {
      this.setEchart();
    }
    this.chartChange();
  },
  computed: {
    origin () {
      return this.data;
    },
    opt() {
      let that = this;
      let obj = {
        color: ['#606c94'],
        tooltip: {
        },
        toolbox: {
          show: true,
          feature: {
            saveAsImage: {show: true}
          }
        },
        label: {
          normal: {
            show: true,
            position: 'inside',
            formatter: '{c}'
          },
          emphasis: {
            show: true
          }
        },
        xAxis: {
          type: 'value',
        },
        yAxis: {
          data: that.origin[that.type][that.pagePick].key,
          axisLabel: {
            interval: 0,
            rotate: -30
          }
        },
        series: [{
          name: that.origin.title,
          type: 'bar',
          data: that.origin[that.type][that.pagePick].val,
          barMaxWidth: '30',
          markLine: {
            data: [
              {type: 'average', name: '平均值'}
            ]
          }
        }]
      }
      return obj;
    },
    type () {
      if (this.typePick == '数值') {
        return 'numeric';
      } else if (this.typePick == '百分比') {
        return 'percent';
      } else {
        return 'numeric';
      }
    }
  },
  methods: {
    setEchart () {
      let dom = this.$refs.mychart;
      this.myChart = echarts.init(dom);
      this.myChart.setOption(this.opt);
    },
    chartChange () {
      this.myChart.setOption(this.opt);
      if (this.typePick == '百分比') {
        this.myChart.setOption(this.percent);
      }
      if (this.typePick == '数值') {
        this.myChart.setOption(this.numeric);
      }
    }
  }
}
</script>
