<template>
  <Menu
    mode="horizontal"
    theme="light"
    :active-name="this.utils.routerUtil.getRouterFirstPath(this.$route.path)"
  >
    <router-link to="/uag-userinfo-list" replace>
      <MenuItem name="/uag-userinfo-list">
        <Icon type="md-contacts" />
        用户管理
      </MenuItem>
    </router-link>
    <router-link to="/uag-log-list" replace>
      <MenuItem name="/uag-log-list">
        <Icon type="md-list" />
        操作日志
      </MenuItem>
    </router-link>
    <router-link to="/sms-record-list" replace>
      <MenuItem name="/sms-record-list">
        <Icon type="md-list" />
        短信发送记录
      </MenuItem>
    </router-link>
  </Menu>
</template>
<script>
export default {
  computed: {},
  data() {
    return {}
  }
}
</script>
