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
          <FormItem prop="appName">
            <i-input type="text"
              placeholder="应用名"
              v-model="paramData.data.appName">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="2"
          offset="1"
          class="page-col">
          <FormItem prop="appCategory">
            <Select v-model="paramData.data.appCategory"
              @on-change="getdata"
              placeholder="应用类型"
              style="width:200px"
              clearable>
              <Option v-for="item in appCategoryEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </i-col>

        <i-col span="2"
          offset="1"
          class="page-col">
          <FormItem prop="authType">
            <Select v-model="paramData.data.authType"
              @on-change="getdata"
              placeholder="授权模式"
              style="width:200px"
              clearable>
              <Option v-for="item in authTypeEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </i-col>

        <i-col span="2"
          offset="1"
          class="page-col">
          <FormItem prop="appStatus">
            <Select v-model="paramData.data.appStatus"
              @on-change="getdata"
              placeholder="应用状态"
              style="width:200px"
              clearable>
              <Option v-for="item in appStatusEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </i-col>

        <i-col span="5" offset="17" class="button-group">
          <Button type="info"
            @click="pushAdd()">新增</Button>
          <Button type="success"
            @click="getdata()">查询</Button>
          <Button type="warning"
            @click="handleReset()">重置</Button>
          <Button type="primary"
            @click="syncAppApiRelationship()">同步应用信息</Button>
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
        <Button type="warning"
          size="small"
          style="margin-right: 5px"
          @click="showApiBindModal(row)">接口管理</Button>
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
    <AppApiModal :show-modal="showModal"
      :modal-title="modalTitle"
      :app-id="selectAppId"
      @closeApiBindModal="closeApiBindModal" />
    <Spin size="large"
      fix
      v-if="globalScreenLoding" />
  </div>
</template>
<script>
import AppApiModal from './AppApiModal.vue'
export default {
  components: {
    AppApiModal
  },
  data() {
    return {
      routerPath: this.$route.path,
      authTypeEunmArr: [
        {
          value: '0',
          label: '授权码 (共享令牌)'
        },
        {
          value: '1',
          label: '私有令牌  (一设备一令牌 需要登录)'
        },
        {
          value: '2',
          label: 'IP白名单+通行令牌 (管理后台模式)'
        },
        {
          value: '3',
          label: '私有免登陆令牌 (一设备一令牌 免登陆)'
        }
      ],
      appCategoryEunmArr: [
        {
          value: '0',
          label: '基础应用'
        },
        {
          value: '1',
          label: '普通应用'
        }
      ],
      appStatusEunmArr: [
        {
          value: '0',
          label: '连接'
        },
        {
          value: '1',
          label: '断开'
        }
      ],
      paramData: {
        data: {
          appStatus: '',
          authType: '',
          appCategory: '',
          appName: ''
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
        { title: '应用名', key: 'appName' },
        { title: '应用ID', key: 'openId' },
        { title: '授权模式', key: 'authType' },
        { title: '应用状态', key: 'appStatus' },
        { title: '修改时间', key: 'updateTime' },
        { title: '操作', slot: 'action', width: 250, align: 'center' }
      ],
      tableData: [],
      showModal: false,
      selectAppId: '',
      modalTitle: ''
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
      return this.utils.initTableListRowClass(index)
    },
    getdata() {
      this.utils.netUtil.post(
        this.API_PTAH.appInfoFind,
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
            this.API_PTAH.appInfoDelete,
            { id: id },
            () => {
              this.tableData.splice(index, 1)
              this.$Message.success('删除成功!')
              this.$store.commit('statusGlobalButtonLoding')
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
    syncAppApiRelationship() {
      this.$store.commit('statusGlobalScreenLoding')
      this.utils.netUtil.post(
        this.API_PTAH.syncAppApiRelationship,
        {},
        () => {
          this.$Message.success('同步数据成功!')
          this.$store.commit('statusGlobalScreenLoding')
        },
        () => {
          this.$store.commit('statusGlobalScreenLoding')
        }
      )
    },
    handleReset() {
      this.$refs.queryParamFrom.resetFields()
      this.getdata()
    },
    pushAdd() {
      this.$router.push(`${this.$route.path}/app-info-add`)
    },
    pushEditor(id) {
      this.$router.push({ path: `${this.$route.path}/app-info-editor/${id}` })
    },
    showApiBindModal(item) {
      this.modalTitle = `${item.appName} 接口管理`
      this.showModal = true
      this.selectAppId = item.id
    },
    closeApiBindModal() {
      this.showModal = false
    }
  }
}
</script>