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
        <Form ref="upSteamNodeInfoFrom"
          :model="upSteamNodeInfoFrom"
          :rules="fromValidate"
          :label-width="110">

          <FormItem label="服务节点名称"
            prop="nodeName">
            <Select v-model="upSteamNodeInfoFrom.nodeName"
              :disabled="showOnKubernetesDeployment()"
              filterable
              remote
              :remote-method="queryUpSteamInfo"
              placeholder="请搜索以选择服务节点"
              :loading="isLoadingUpSteamNodeInfo">
              <Option v-for="(option, index) in upSteamNodeInfo"
                :value="option.upsteamName"
                :key="index">{{option.upsteamName}}</Option>
            </Select>
            <upSteamName v-if="!showOnKubernetesDeployment()" />
          </FormItem>

          <FormItem label="节点命名空间"
            v-if="showOnKubernetesDeployment()"
            prop="nodeNamespace">
            <i-input v-model="upSteamNodeInfoFrom.nodeNamespace"
              disabled
              placeholder="请输入命名空间"></i-input>
          </FormItem>

          <FormItem label="	当前版本"
            v-if="showOnKubernetesDeployment()"
            prop="currentVersion">
            <i-input v-model="upSteamNodeInfoFrom.currentVersion"
              disabled
              placeholder="请输入当前版本"></i-input>
          </FormItem>

          <FormItem label="选中版本"
            v-if="showOnKubernetesDeployment()"
            prop="selectedVersion">
            <i-input v-model="upSteamNodeInfoFrom.selectedVersion"
              disabled
              placeholder="请输入选中版本"></i-input>
          </FormItem>

          <FormItem label="部署类型"
            prop="deployType">
            <Select v-model="upSteamNodeInfoFrom.deployType"
              disabled
              placeholder="选择部署类型">
              <Option v-for="item in deployTypeEunmArr"
                :value="item.value"
                :disabled="item.disabled"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>

          <FormItem label="物理地址"
            prop="physicalPath">
            <i-input v-model="upSteamNodeInfoFrom.physicalPath"
              :disabled="showOnKubernetesDeployment()"
              placeholder="请输入物理地址"></i-input>
          </FormItem>

          <FormItem>
            <Button type="primary"
              :disabled="globalButtonLoding"
              @click="handleSubmit('upSteamNodeInfoFrom')">提交</Button>
          </FormItem>
        </Form>
      </i-col>
    </Row>
  </div>

</template>


<script>
import upSteamName from '../upSteamName/upSteamName'

export default {
  props: {
    query: { userId: 0 }
  },
  data() {
    return {
      deployTypeEunmArr: [
        {
          value: '0',
          label: '物理地址部署'
        },
        {
          value: '1',
          label: 'kubernetes部署',
          disabled: true
        }
      ],
      routerPath: this.$route.path,
      isLoadingUpSteamNodeInfo: false,
      upSteamNodeInfo: [],
      upSteamNodeInfoFrom: {
        id: this.$route.params.id,
        nodeName: '',
        physicalPath: ''
      },
      fromValidate: {
        nodeName: [
          {
            required: true,
            max: 30,
            pattern: /^[A-Za-z]+[A-Za-z-]*[A-Za-z]+$/,
            message:
              '服务节点名称不能为空,最多30个字符,必须以英文开头和结尾只能包含中横线 [ - ]',
            trigger: 'blur'
          }
        ],
        physicalPath: [
          {
            pattern: /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):{1}[0-9]{4,}$/,
            required: true,
            message: '物理地址不能为空 并且为标准的ip地址格式+端口',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  components: {
    upSteamName
  },
  mounted: function() {
    this.loadData()
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
    showOnKubernetesDeployment() {
      return this.upSteamNodeInfoFrom.deployType === '1' ? true : false
    },
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
    loadData() {
      this.utils.netUtil.post(
        
        this.API_PTAH.upSteamNodeInfoFindById,
        this.upSteamNodeInfoFrom,
        response => {
          this.upSteamNodeInfoFrom = response.data.data
        }
      )
    },
    commitData() {
      this.$store.commit('statusGlobalButtonLoding')
      this.upSteamNodeInfoFrom.id = this.$route.params.id
      this.utils.netUtil.post(
        
        this.API_PTAH.upSteamNodeInfoUpdate,
        this.upSteamNodeInfoFrom,
        () => {
          this.$Message.success('提交成功!')
          this.$store.commit('statusGlobalButtonLoding')
          this.$router.push('/upsteam-node-info-list')
        },
        () => {
          this.$store.commit('statusGlobalButtonLoding')
        }
      )
    }
  }
}
</script>
