<style>
.selected-version-eq {
  background-color: #187 !important;
  color: #fff;
}

.current-version-old {
  background-color: #ff6600 !important;
  color: #fff;
}

.current-version-young {
  background-color: #2db7f5 !important;
  color: #fff;
}

.annotation-square {
  width: 15px;
  height: 15px;
}
.annotation-words {
  /* height: 100%; */
  width: 70px;
  font-size: 13px;
  font-weight: bold;
}
.annotation-container {
  display: flex;
}
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

    <Row>
      <i-col span="23"
        offset="1">
        <div class="annotation-container">
          <div class="annotation-container">
            <div class="selected-version-eq annotation-square"></div>
            <p class="annotation-words">&nbsp;: 当前版本</p>
          </div>
          <div class="annotation-container">
            <div class="current-version-young annotation-square"></div>
            <p class="annotation-words">&nbsp;: 新版本</p>
          </div>
          <div class="annotation-container">
            <div class="current-version-old annotation-square"></div>
            <p class="annotation-words">&nbsp;: 过时版本</p>
          </div>
        </div>

      </i-col>
    </Row>

    <Form ref="queryParamFrom"
      class="query-pram-from"
      :model="paramData.data">

      <Row>
        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="nodeName">
            <i-input type="text"
              placeholder="服务节点名称"
              v-model="paramData.data.nodeName">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="2"
          offset="1"
          class="page-col">
          <FormItem prop="nodeNamespace">
            <i-input type="text"
              placeholder="节点命名空间"
              v-model="paramData.data.nodeNamespace">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="2"
          offset="1"
          class="page-col">
          <FormItem prop="currentVersion">
            <i-input type="text"
              placeholder="当前版本"
              v-model="paramData.data.currentVersion">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="2"
          offset="1"
          class="page-col">
          <FormItem prop="deployType">
            <Select v-model="paramData.data.deployType"
              @on-change="getdata"
              placeholder="部署类型"
              style="width:200px"
              clearable>
              <Option v-for="item in deployTypeEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </i-col>

        <i-col span="2"
          offset="1"
          class="page-col">
          <FormItem prop="runningStatus">
            <Select v-model="paramData.data.runningStatus"
              @on-change="getdata"
              placeholder="运行状态"
              style="width:200px"
              clearable>
              <Option v-for="item in runningStatusEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </i-col>

        <i-col span="3"
          offset="19"
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
          style="margin-right: 5px"
          v-if="canBeStopNode(row.canStop)"
          @click="setRunningStatusDownByDevOpsK8sNotificationVo(row)">停止运行</Button>
        <Button type="error"
          size="small"
          :disabled="globalButtonLoding"
          v-if="showOnPhysicalDeployment(row)"
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
      routerPath: this.$route.path,
      deployTypeEunmArr: [
        {
          value: '0',
          label: '物理地址部署'
        },
        {
          value: '1',
          label: 'kubernetes部署'
        }
      ],
      runningStatusEunmArr: [
        {
          value: '0',
          label: '运行中'
        },
        {
          value: '1',
          label: '已停止'
        }
      ],
      apiStatusEunmArr: [
        {
          value: '0',
          label: '未启用'
        },
        {
          value: '1',
          label: '启用'
        }
      ],
      paramData: {
        data: {
          nodeName: '',
          physicalPath: ''
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
        { title: '服务节点名称', key: 'nodeName' },
        { title: '节点命名空间', key: 'nodeNamespace' },
        { title: '选中版本', key: 'selectedVersion' },
        { title: '当前版本', key: 'currentVersion' },
        { title: '部署类型', key: 'deployType' },
        { title: '运行状态', key: 'runningStatus' },
        { title: '物理地址', key: 'physicalPath' },
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
        
        this.API_PTAH.upSteamNodeInfoFind,
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
            this.API_PTAH.upSteamNodeInfoDelete,
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
    handleReset() {
      this.$refs.queryParamFrom.resetFields()
      this.getdata()
    },
    pushAdd() {
      this.$router.push('/upsteam-node-info-list/upsteam-node-info-add')
    },
    pushEditor(id) {
      this.$router.push({
        path: `/upsteam-node-info-list/upsteam-node-info-editor/${id}`
      })
    },
    setRunningStatusDownByDevOpsK8sNotificationVo(row) {
      this.$store.commit('statusGlobalScreenLoding')
      this.utils.netUtil.post(
        
        this.API_PTAH.setRunningStatusDownByDevOpsK8sNotificationVo,
        {
          id: row.id,
          nodeName: row.nodeName,
          nodeNamespace: row.nodeNamespace,
          currentVersion: row.currentVersion
        },
        response => {
          const stopResult = response.data.data
          const message = stopResult
            ? '停止成功！'
            : '停止失败，请前往k8s控制台确认！'
          this.$store.commit('statusGlobalScreenLoding')
          this.$Message.success(message)
          this.getdata()
        },
        () => {
          this.$store.commit('statusGlobalScreenLoding')
        }
      )
    },
    canBeStopNode(canStop) {
      return canStop === '1' ? true : false
    },
    showOnPhysicalDeployment(row) {
      const physicalDeployment =
        row.deployType === '物理地址部署' ? true : false
      const runningStatus = row.runningStatus === '已停止' ? true : false
      return physicalDeployment || runningStatus
    }
  }
}
</script>