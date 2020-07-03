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
          <FormItem prop="username">
            <i-input
              type="text"
              placeholder="用户名"
              v-model="paramData.data.username"
            >
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="4" offset="1" class="page-col">
          <FormItem prop="appCategory">
            <Select
              v-model="paramData.data.appId"
              style="width:200px"
              @on-change="selectedAppInfo"
              clearable
            >
              <Option
                v-for="item in uagUserinfoAppArr"
                :value="item.appId"
                :key="item.appId"
                >{{ item.appName }}</Option
              >
            </Select>
          </FormItem>
        </i-col>

        <i-col span="3" offset="17" class="button-group">
          <Button type="info" @click="pushAdd()">新增</Button>
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
      <template slot-scope="{ row }" slot="action">
        <Button
          type="primary"
          size="small"
          style="margin-right: 5px"
          @click="goEditor(row)"
          >编辑</Button
        >
        <Button
          type="warning"
          size="small"
          style="margin-right: 5px"
          :disabled="globalButtonLoding"
          @click="resetPassword(row)"
          >重置密码</Button
        >
        <Button
          type="warning"
          size="small"
          style="margin-right: 5px"
          v-show="showOnLock(row)"
          :disabled="globalButtonLoding"
          @click="toggleIsLockStatus(row)"
          >解锁</Button
        >
        <Button
          type="error"
          size="small"
          v-show="showOnUnLock(row)"
          :disabled="globalButtonLoding"
          @click="toggleIsLockStatus(row)"
          >锁定</Button
        >
      </template>
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
          id: '',
          username: '',
          password: '',
          appId: this.$store.state.userInfoListAppId
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
        { title: 'UAG-USER-ID', key: 'id' },
        { title: '用户名', key: 'username' },
        { title: '姓名', key: 'nickName' },
        { title: '创建时间', key: 'createTime' },
        { title: '修改时间', key: 'updateTime' },
        { title: '操作人', key: 'operatorName' },
        { title: '操作', slot: 'action', width: 250, align: 'center' }
      ]
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
    async getdata() {
      const response = await this.utils.netUtil.postAsync(
        this.API_PTAH.uagUserInfoFindUagUserAppList,
        {}
      )

      if (response && response.length > 0) {
        this.uagUserinfoAppArr = response
        if (this.paramData.data.appId === '0') {
          this.paramData.data.appId = this.uagUserinfoAppArr[0].appId
        }

        this.utils.netUtil.post(
          this.API_PTAH.uagUserInfoFind,
          this.paramData,
          response => {
            this.tableData = response.data.data
            this.paramData.data.totalCount = response.data.page.totalCount
            this.paramData.data.pageSize = response.data.page.pageSize
            this.paramData.data.currentPage = response.data.page.currentPage
          }
        )
      }
    },
    toggleIsLockStatus(row) {
      this.$store.commit('statusGlobalButtonLoding')
      this.utils.netUtil.post(
        this.API_PTAH.uagUserInfoToggleIsLockStatus,
        { appId: this.paramData.data.appId, userId: row.id },
        response => {
          if (response.data.data) {
            this.$store.commit('statusGlobalButtonLoding')
            this.$Message.success('提交成功!')
            this.getdata()
          }
        },
        () => {
          this.$store.commit('statusGlobalButtonLoding')
        }
      )
    },
    resetPassword(row) {
      this.$store.commit('statusGlobalButtonLoding')
      this.utils.netUtil.post(
        this.API_PTAH.uagUserInfoRestPassword,
        { appId: this.paramData.data.appId, userId: row.id },
        response => {
          if (response.data.data) {
            this.$store.commit('statusGlobalButtonLoding')
            this.$Message.success('密码已重置为123456')
            this.getdata()
          }
        },
        () => {
          this.$store.commit('statusGlobalButtonLoding')
        }
      )
    },
    goEditor(row) {
      this.$router.push({
        path: `/uag-userinfo-list/uag-userinfo-editor/${this.paramData.data.appId}/${row.id}`
      })
    },
    handleReset() {
      this.$refs.queryParamFrom.resetFields()
      this.getdata()
    },
    pushAdd() {
      if (this.paramData.data.appId === '') {
        this.$Message.warning('请选择应用')
        return
      }
      this.$router.push({
        path: `/uag-userinfo-list/uag-userinfo-add/${this.paramData.data.appId}`
      })
    },
    selectedAppInfo(item) {
      if (item != '' && typeof item != 'undefined') {
        this.getdata()
      }
    },
    showOnLock(item) {
      return item.isLock === '1'
    },
    showOnUnLock(item) {
      return item.isLock === '0'
    }
  }
}
</script>
