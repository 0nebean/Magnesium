<style>
</style>

<template>
  <div>
    <Row>
      <i-col span="24"
        class="bread-crumb">
        <Breadcrumb>
          <BreadcrumbItem v-for="(item,index) in breadcrumbList"
            :to="item.path"
            :key="index">
            <Icon :type="item.icon"></Icon> {{item.name}}
          </BreadcrumbItem>
        </Breadcrumb>
      </i-col>
    </Row>
    <Divider />
    <router-view></router-view>
    <Form ref="queryParamFrom"
      class="query-pram-from"
      :model="paramData.data">

      <Row>
        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="ipAddress">
            <i-input type="text"
              placeholder="节点内网IP地址"
              v-model="paramData.data.ipAddress">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="2"
          offset="1"
          class="page-col">
          <FormItem prop="serverMachineType">
            <Select v-model="paramData.data.serverMachineType"
              @on-change="getdata"
              placeholder="服务节点类型"
              style="width:200px"
              clearable>
              <Option v-for="item in serverMachineTypeEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </i-col>

        <i-col span="3"
          offset="18"
          class="button-group">
          <Button type="info"
            @click="pushAdd()">新增</Button>
          <Button type="success"
            @click="getdata()">查询</Button>
          <Button type="warning"
            @click="handleReset()">重置</Button>
        </i-col>

      </Row>

    </Form>

    <br />
    <Table border
      :columns="columns"
      :data="tableData"
      class="list-button-group">
      <template slot-scope="{ row, index }"
        slot="action">
        <Button type="primary"
          size="small"
          style="margin-right: 5px"
          @click="pushEditor(row.id)">编辑</Button>
        <Button type="error"
          size="small"
          :disabled="globalButtonLoding"
          @click="deleteData(row.id ,index)">删除</Button>
      </template>
    </Table>
    <Row>
      <i-col span="6"
        offset="17"
        class="page-col">
        <Page :total="paramData.data.totalCount"
          :current="paramData.data.currentPage"
          :page-size="paramData.data.pageSize"
          @on-change="handlePage"
          @on-page-size-change='handlePageSize'
          show-sizer
          class="pagination" />
      </i-col>
    </Row>
    <Spin size="large"
      fix
      v-if="globalScreenLoding" />
  </div>
</template>
<script>
export default {
  data() {
    return {
      syncNginxLoading: false,
      routerPath: this.$route.path,
      serverMachineTypeEunmArr: [
        {
          value: '0',
          label: 'openresty'
        },
        {
          value: '1',
          label: 'kubernetes-master'
        }
      ],
      accessAuthTypeEunmArr: [
        {
          value: '0',
          label: '密码模式'
        },
        {
          value: '1',
          label: '公私钥模式'
        }
      ],
      paramData: {
        data: {
          ipAddress: '',
          serverMachineType: ''
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
        { title: '节点内网IP地址', key: 'ipAddress' },
        { title: '服务节点类型', key: 'serverMachineType' },
        { title: '访问账户', key: 'accessUser' },
        { title: '访问端口号', key: 'accessPort' },
        { title: '访问授权方式', key: 'accessAuthType' },
        { title: '修改时间', key: 'updateTime' },
        { title: '操作', slot: 'action', width: 225, align: 'center' }
      ],
      tableData: []
    }
  },
  computed: {
    breadcrumbList: function() {
      return this.utils.routerUtil.initRouterTreeNameArr(this.routerPath)
    },
    globalScreenLoding: function() {
      return this.$store.state.globalScreenLoding
    },
    globalButtonLoding: function() {
      return this.$store.state.globalButtonLoding
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
    rowClassName(row, index) {
      return this.utils.styleUtil.initTableListRowClass(index)
    },
    getdata() {
      this.utils.netUtil.post(
        
        this.API_PTAH.serverMachineNodeInfoFind,
        this.paramData,
        response => {
          this.tableData = response.data.data
          this.paramData.data.totalCount = response.data.page.totalCount
          this.paramData.data.pageSize = response.data.page.pageSize
          this.paramData.data.currentPage = response.data.page.currentPage
        }
      )
    },
    deleteData(id, index) {
      this.$store.commit('statusGlobalButtonLoding')
      this.$Modal.confirm({
        title: '警告',
        content: '确认删除该条数据吗',
        onOk: () => {
          this.utils.netUtil.post(
            this.API_PTAH.serverMachineNodeInfoDelete,
            { id: id },
            response => {
              response.data
              this.tableData.splice(index, 1)
              this.$store.commit('statusGlobalButtonLoding')
              this.$Message.success('删除成功!')
            },
            () => {
              this.$store.commit('statusGlobalButtonLoding')
            }
          )
        },
        onCancel: () => {
          this.$store.commit('statusGlobalButtonLoding')
        }
      })
    },
    handleSync() {
      this.syncNginxLoading = !this.syncNginxLoading
      this.utils.netUtil.post(
        
        this.API_PTAH.syncNginxNodeInfo,
        {},
        () => {
          this.$Message.success('已发送同步消息!')
          setTimeout(() => {
            this.syncNginxLoading = !this.syncNginxLoading
          }, 1000)
        }
      )
    },
    handleReset() {
      this.$refs.queryParamFrom.resetFields()
      this.getdata()
    },
    pushAdd() {
      this.$router.push(
        '/server-machine-node-info-list/server-machine-node-info-add'
      )
    },
    pushEditor(id) {
      this.$router.push({
        path: `/server-machine-node-info-list/server-machine-node-info-editor/${id}`
      })
    }
  }
}
</script>