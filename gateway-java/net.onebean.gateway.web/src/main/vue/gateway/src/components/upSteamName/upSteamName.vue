<template>
  <div>
    <a @click="showDataList()"
      type="primary">管理服务节点名称</a>

    <Drawer title="服务节点"
      width="512"
      :closable="false"
      v-model="showDataListState">
      <Button @click="showAddFrom = true"
        type="info">新增</Button>
      <Divider />
      <Table :columns="columns"
        :data="listData">
        <template slot-scope="{ row, index }" slot="action" v-if="showOnPhysicalDeployment(row.deployType)">
          <a @click="deleteData(row.id ,index)">删除</a>
        </template>
      </Table>
    </Drawer>

    <Drawer title="新增服务节点"
      :closable="false"
      @on-close="onCloseAddView"
      v-model="showAddFrom">
      <Form :label-width="80"
        ref="addDataParam"
        :rules="validateRule"
        :model="addDataParam">

        <FormItem label="节点名称"
          prop='upsteamName'>
          <i-input placeholder="请输入应用名称"
            v-model="addDataParam.upsteamName"></i-input>
        </FormItem>
        <br />
        <br />
        <br />
        <FormItem>
          <Button type="primary"
            @click="handleSubmit('addDataParam')">提交</Button>
        </FormItem>
      </Form>
    </Drawer>

  </div>
</template>

<script>
export default {
  data() {
    return {
      paramData: {
        data: { upsteamName: '' },
        page: {
          currentPage: 1,
          pageSize: 10
        },
        sort: {
          orderBy: 'id',
          sort: 'desc'
        }
      },
      addDataParam: {
        upsteamName: '',
        deployType: '0'
      },
      validateRule: {
        upsteamName: [
          {
            required: true,
            max: 30,
            pattern: /^[A-Za-z]+[A-Za-z-]*[A-Za-z]+$/,
            message:
              '服务节点名称不能为空,最多30个字符,必须以英文开头和结尾只能包含中横线 [ - ]',
            trigger: 'blur'
          }
        ]
      },
      showDataListState: false,
      showAddFrom: false,
      columns: [
        {
          title: '节点名称',
          key: 'upsteamName'
        },
        { title: '操作', slot: 'action', width: 250, align: 'center' }
      ],
      listData: []
    }
  },
  mounted: function() {},
  methods: {
    showOnPhysicalDeployment(deployType) {
      return deployType === '0' ? true : false
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
    showDataList() {
      this.showDataListState = true
      if (this.listData.length == 0) {
        this.getDataList()
      }
    },
    getDataList() {
      this.utils.netUtil.post(
        
        this.API_PTAH.upSteamNameFind,
        this.paramData,
        response => {
          this.listData = response.data.data
        }
      )
    },
    deleteData(id, index) {
      this.$Modal.confirm({
        title: '警告',
        content: '确认删除该条数据吗',
        onOk: () => {
          this.utils.netUtil.post(
            this.API_PTAH.upSteamNameDelete,
            { id: id },
            () => {
              this.listData.splice(index, 1)
              this.$Message.success('删除成功!')
            }
          )
        }
      })
    },
    commitData() {
      this.utils.netUtil.post(
        
        this.API_PTAH.upSteamNameAdd,
        this.addDataParam,
        () => {
          this.addDataParam.upsteamName = ''
          this.showAddFrom = false
          this.$Message.success('提交成功!')
          this.getDataList()
        }
      )
    },
    onCloseAddView() {
      this.addDataParam.upsteamName = ''
    }
  }
}
</script>

<style>
</style>


