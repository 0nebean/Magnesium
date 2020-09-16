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
    <Row>
      <i-col span="12"
        offset="6">
        <Form ref="serverInfoFrom"
          :model="serverInfoFrom"
          :rules="serverInfoFromValidate"
          :label-width="120">

          <FormItem label="服务名称"
            prop="serverName">
            <i-input v-model="serverInfoFrom.serverName"
              placeholder="请输入服务名称"></i-input>
          </FormItem>

          <FormItem label="服务节点"
            prop="upsteamNodeName">
            <Select v-model="serverInfoFrom.upsteamNodeName"
              filterable
              clearable
              remote
              :label-in-value="true"
              :remote-method="queryUpSteamInfo"
              @on-change="upSteamNameChanged"
              placeholder="请搜索以选择服务节点"
              :loading="isLoadingUpSteamNodeInfo">
              <Option v-for="(option, index) in upSteamNodeInfo"
                :value="option.upsteamName"
                :key="index">{{option.upsteamName}} - {{option.deployType == '0'?'物理部署':'kubernetes部署'}}</Option>
            </Select>
            <upSteamName v-if="isPhysicalDeployment" />
          </FormItem>

          <FormItem label="暴露的版本"
            prop="selectedVersion"
            v-if="!isPhysicalDeployment">
            <Select v-model="serverInfoFrom.selectedVersion"
              @on-change="updateSelectedVersion()"
              placeholder="选择暴露的版本">
              <Option v-for="item in selectedVersionEunmArr"
                :value="item.version"
                :disabled="item.disabled"
                :key="item.version">{{ item.version }}</Option>
            </Select>
          </FormItem>

          <FormItem label="是否需要域名"
            prop="isFront">
            <i-switch v-model="serverInfoFrom.isFront"
              @on-change="cleanNeedHost()"
              :true-value="yesOrNoEunm.yes"
              :false-value="yesOrNoEunm.no" />
          </FormItem>

          <FormItem label="监听端口号"
            v-if="showOnNeedHost()"
            prop="listenPort">
            <i-input v-model="serverInfoFrom.listenPort"
              placeholder="请输入监听端口号"></i-input>
          </FormItem>

          <FormItem label="域名"
            v-if="showOnNeedHost()"
            prop="serverHost">
            <i-input v-model="serverInfoFrom.serverHost"
              placeholder="请输域名"></i-input>
          </FormItem>

          <FormItem label="是否开启ssl"
            v-if="showOnNeedHost()"
            prop="isSsl">
            <i-switch v-model="serverInfoFrom.isSsl"
              @on-change="cleanSslOn()"
              :true-value="yesOrNoEunm.yes"
              :false-value="yesOrNoEunm.no" />
          </FormItem>

          <FormItem label="ssl监听端口号"
            v-if="showOnSslOn()"
            prop="sslListenPort">
            <i-input v-model="serverInfoFrom.sslListenPort"
              placeholder="请输入ssl监听端口号"></i-input>
          </FormItem>

          <FormItem label="证书key路径"
            v-if="showOnSslOn()"
            prop="sslCrtKeyPath">
            <i-input v-model="serverInfoFrom.sslCrtKeyPath"
              placeholder="请输入证书key路径"></i-input>
          </FormItem>

          <FormItem label="证书路径"
            v-if="showOnSslOn()"
            prop="sslCrtPath">
            <i-input v-model="serverInfoFrom.sslCrtPath"
              placeholder="请输入证书路径"></i-input>
          </FormItem>

          <FormItem>
            <Button type="primary"
            :disabled="globalButtonLoding"
              @click="handleSubmit('serverInfoFrom')">提交</Button>
            <Button @click="handleReset('serverInfoFrom')"
              style="margin-left: 8px">重置</Button>
          </FormItem>
        </Form>
      </i-col>
    </Row>
    <Spin size="large"
      fix
      v-if="globalScreenLoding" />
  </div>

</template>


<script>
import upSteamName from '../upSteamName/upSteamName'

export default {
  data() {
    return {
      isLoadingUpSteamNodeInfo: false,
      isPhysicalDeployment: true,
      selectedVersionEunmArr: [],
      upSteamNodeInfo: [],
      routerPath: this.$route.path,
      serverInfoFrom: {
        listenPort: '',
        sslListenPort: '',
        serverHost: '',
        sslCrtKeyPath: '',
        sslCrtPath: '',
        serverName: '',
        upsteamNodeName: '',
        selectedVersion: '',
        deployType: '0',
        isSsl: '0',
        isFront: '0'
      },
      yesOrNoEunm: {
        no: '0',
        yes: '1'
      },
      serverInfoFromValidate: {
        listenPort: [
          {
            required: true,
            pattern: /^[0-9]{2,6}$/,
            message: '监听端口号输入不正确',
            trigger: 'blur'
          }
        ],
        sslListenPort: [
          {
            required: true,
            pattern: /^[0-9]{2,6}$/,
            message: 'ssl监听端口号输入不正确',
            trigger: 'blur'
          }
        ],
        serverHost: [
          {
            required: true,
            pattern: /^([A-Za-z0-9]+[-]?[A-Za-z0-9]+[.]{1}[A-Za-z0-9]+[-]?[A-Za-z0-9]+[.]{1}[A-Za-z0-9]+[\s]?)+$/,
            message: '域名由英文字母,数字开头可以包含符号[-],多个域名用空格分隔',
            trigger: 'blur'
          }
        ],
        sslCrtKeyPath: [
          {
            required: true,
            pattern: /^\/\S*[A-Za-z0-9]+$/,
            message: '证书key路径不能为空,并且只能以`/`开头 英文或数字结尾',
            trigger: 'blur'
          }
        ],
        sslCrtPath: [
          {
            required: true,
            pattern: /^\/\S*[A-Za-z0-9]+$/,
            message: '证书路径不能为空,并且只能以`/`开头 英文或数字结尾',
            trigger: 'blur'
          }
        ],
        isSsl: [
          {
            required: true,
            message: '是否开启ssl不能为空',
            trigger: 'change'
          }
        ],
        isFront: [
          {
            required: true,
            message: '是否需要域名不能为空',
            trigger: 'change'
          }
        ],
        selectedVersion: [
          {
            required: true,
            message: '暴露的版本不能为空',
            trigger: 'change'
          }
        ],
        serverName: [
          {
            required: true,
            message: '服务名称不能为空',
            trigger: 'blur'
          }
        ],
        upsteamNodeName: [
          {
            required: true,
            message: '服务节点不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  components: {
    upSteamName
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
  methods: {
    queryUpSteamInfo(query) {
      if (query !== '') {
        this.isLoadingUpSteamNodeInfo = true
        this.utils.netUtil.post(
          this.API_PTAH.upSteamNameFind,
          {
            data: {
              nodeName: query,
              physicalPath: ''
            },
            page: {
              currentPage: 1,
              pageSize: 10000
            },
            sort: {
              orderBy: 'id',
              sort: 'desc'
            }
          },
          response => {
            this.isLoadingUpSteamNodeInfo = false
            this.upSteamNodeInfo = response.data.data
          }
        )
      } else {
        this.upSteamNodeInfo = []
      }
    },
    handleSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.commitData()
        } else {
          this.$Message.error('请完善表单信息!')
        }
      })
    },
    handleReset(name) {
      this.clearVersionList()
      this.$refs[name].resetFields()
    },
    commitData() {
      this.$store.commit('statusGlobalButtonLoding')
      if (this.isPhysicalDeployment) {
        this.clearVersionList()
      }
      this.utils.netUtil.post(
        
        this.API_PTAH.serverInfoAdd,
        this.serverInfoFrom,
        () => {
          // this.$store.commit('statusGlobalButtonLoding')
          // this.$store.commit('statusGlobalScreenLoding')
          // this.syncOpenrestyNodeInfo()
          this.$Message.success('提交成功!')
          this.$store.commit('statusGlobalButtonLoding')
          this.$router.push('/server-info-list')
        },()=>{
          this.$store.commit('statusGlobalButtonLoding')
          // this.$store.commit('statusGlobalScreenLoding')
        }
      )
    },
    upSteamNameChanged(option) {
      if (typeof option != 'undefined') {
        let lable = option.label
        lable = this.utils.objectUtil.subStringAfterTarget('- ', lable)
        this.isPhysicalDeployment = lable === '物理部署' ? true : false
        this.serverInfoFrom.deployType = this.isPhysicalDeployment ? '0' : '1'
        if (!this.isPhysicalDeployment) {
          this.findVersionListByNodeName()
        }
      }
    },
    findVersionListByNodeName() {
      this.utils.netUtil.post(
        
        this.API_PTAH.findVersionListByNodeName,
        this.serverInfoFrom,
        res => {
          this.selectedVersionEunmArr = res.data.data
        }
      )
    },
    updateSelectedVersion() {
      this.loading = !this.loading
      this.utils.netUtil.post(
        
        this.API_PTAH.updateSelectedVersion,
        this.serverInfoFrom,
        res => {
          if (res.data.data) {
            this.loading = !this.loading
            this.$Message.success('版本信息切换成功！')
          }
        },()=>{
          this.loading = !this.loading
        }
      )
    },
    // syncOpenrestyNodeInfo() {
    //   this.utils.netUtil.post(
        
    //     this.API_PTAH.syncServerMachineNodeInfo,
    //     {},
    //     () => {
    //       this.$store.commit('statusGlobalScreenLoding')
    //       this.$Message.success('同步数据成功,版本信息已即时生效！')
    //       this.$router.push('/server-info-list')
    //     },
    //     () => {
    //       this.$store.commit('statusGlobalScreenLoding')
    //     }
    //   )
    // },
    clearVersionList() {
      this.serverInfoFrom.selectedVersion = ''
      this.selectedVersionEunmArr = []
    },
    showOnNeedHost() {
      return this.serverInfoFrom.isFront === '1' ? true : false
    },
    showOnSslOn() {
      return this.serverInfoFrom.isSsl === '1' ? true : false
    },
    cleanNeedHost() {
      this.serverInfoFrom.serverHost = ''
      this.serverInfoFrom.listenPort = ''
      this.serverInfoFrom.isSsl = '0'
      this.cleanSslOn()
    },
    cleanSslOn() {
      this.serverInfoFrom.sslListenPort = ''
      this.serverInfoFrom.sslCrtKeyPath = ''
      this.serverInfoFrom.sslCrtPath = ''
    }
  }
}
</script>
