<template>
  <div class="dashboard-editor-container">

    <!-- <github-corner style="position: absolute; top: 0px; border: 0; right: 0;"/> -->

    <el-row :gutter="32" style="height: calc(100vh - 150px);width: 100%;">
      <el-col :xs="24" :sm="24" :lg="18" style="height: 100%">
        <pie-chart :option="option" height="100%" width="100%" class="chart-wrapper"/>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="6">
        <panel-group :sales="sales" :num="num"/>
      </el-col>

    </el-row>

  </div>
</template>

<script>
// import GithubCorner from '@/components/GithubCorner'
import PanelGroup from './components/PanelGroup'
import PieChart from './components/PieChart'
import { fetchIndexChart, fetchSaleNum } from '@/api/chart'

export default {
  name: 'DashboardAdmin',
  components: {
    // GithubCorner,
    PanelGroup,
    PieChart
  },
  data() {
    return {
      option: {
        legend: {
          data: []
        },
        series: {
          data: []
        }
      },
      sales: 0,
      num: 0
    }
  },
  created() {
    this.getChart()
    this.getSaleNum()
  },
  methods: {
    getChart() {
      fetchIndexChart().then(response => {
        if (response.data.code === 20000) {
          const temp = Object.assign({}, this.option)
          temp.series.data = response.data.data
          const legendData = []
          for (const v of temp.series.data) {
            legendData.push(v.name)
            this.sales += v.value
          }
          temp.legend.data = legendData
          this.option = temp
        }
      })
    },
    getSaleNum() {
      fetchSaleNum().then(response => {
        this.num = response.data.data
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}
</style>
