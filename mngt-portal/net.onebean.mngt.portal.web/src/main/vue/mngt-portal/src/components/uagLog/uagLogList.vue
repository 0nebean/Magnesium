<style></style>

<template>
  <div>
    <Row>
      <i-col span="24" class="bread-crumb">
        <Breadcrumb>
          <BreadcrumbItem
            v-for="(item, index) in breadcrumbList"
            :to="item.path"
            :key="index"
          >
            <Icon :type="item.icon"></Icon> {{ item.name }}
          </BreadcrumbItem>
        </Breadcrumb>
      </i-col>
    </Row>
    <Divider />
    <router-view></router-view>
    <Form ref="queryParamFrom" class="query-pram-from" :model="paramData.data">
      <Row>
        <i-col span="4" offset="1" class="page-col">
          <FormItem prop="appName">
            <i-input
              type="text"
              placeholder="应用名称"
              v-model="paramData.data.appName"
            >
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="2" offset="1" class="page-col">
          <FormItem prop="operatorName">
            <i-input
              type="text"
              placeholder="操作人姓名"
              v-model="paramData.data.operatorName"
            >
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="3" offset="17" class="button-group">
          <Button type="success" @click="getdata()">查询</Button>
          <Button type="warning" @click="handleReset()">重置</Button>
        </i-col>
      </Row>
    </Form>

    <br />
    <Table
      border
      :columns="columns"
      :data="tableData"
      class="list-button-group"
    >
    </Table>
    <Row>
      <i-col span="6" offset="17" class="page-col">
        <Page
          :total="paramData.data.totalCount"
          :current="paramData.data.currentPage"
          :page-size="paramData.data.pageSize"
          @on-change="handlePage"
          @on-page-size-change="handlePageSize"
          show-sizer
          class="pagination"
        />
      </i-col>
    </Row>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      tableData: [],
      routerPath: this.$route.path,
      uagUserinfoAppArr: [],
      paramData: {
        data: {
          operatorName: ''
        },
        page: {
          currentPage: 1,
          pageSize: 10
        },
        sort: {
          orderBy: 'id',
          sort: 'desc'
        }
      },
      columns: [
        { title: '应用名称', key: 'appName' },
        { title: '操作项', key: 'operatorDescription' },
        { title: '操作时间', key: 'createTime' },
        { title: '操作人', key: 'operatorName' }
      ]
    }
  },
  computed: {
    breadcrumbList: function() {
      return this.utils.routerUtil.initRouterTreeNameArr(this.routerPath)
    }
  },
  mounted: function() {
    this.getdata()
  },
  methods: {
    handlePage(value) {
      this.paramData.page.currentPage = value
      this.getdata()
    },
    handlePageSize(value) {
      this.paramData.page.pageSize = value
      this.getdata()
    },
    getdata() {
      this.utils.netUtil.post(
        this.API_PTAH.uagLogFind,
        this.paramData,
        response => {
          this.tableData = response.data.data
          this.paramData.data.totalCount = response.data.page.totalCount
          this.paramData.data.pageSize = response.data.page.pageSize
          this.paramData.data.currentPage = response.data.page.currentPage
        }
      )
    },
    handleReset() {
      this.$refs.queryParamFrom.resetFields()
      this.getdata()
    }
  }
}
</script>
