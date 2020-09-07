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
          <FormItem prop="uagId">
            <i-input
              type="text"
              placeholder="用户网关ID"
              v-model="paramData.data.uagId"
            >
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="2" offset="1" class="page-col">
          <FormItem prop="phoneNumber">
            <i-input
              type="text"
              placeholder="手机号码"
              v-model="paramData.data.phoneNumber"
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
          phoneNumber: ''
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
        { title: '手机号', key: 'phoneNumber' },
        { title: '验证码', key: 'context' },
        { title: '创建时间', key: 'createTime' }
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
        this.API_PTAH.userSmsRecord,
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
