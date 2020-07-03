<template>

  <Menu mode="horizontal"
    theme="light"
    :active-name="this.utils.routerUtil.getRouterFirstPath(this.$route.path)">
    <router-link to="/app-info-list"
      replace>
      <MenuItem name="/app-info-list">
      <Icon type="ios-construct" />
      应用管理
      </MenuItem>
    </router-link>
    <router-link to="/server-info-list"
      replace>
      <MenuItem name="/server-info-list">
      <Icon type="md-analytics" /> 服务管理
      </MenuItem>
    </router-link>
    <router-link to="/server-machine-node-info-list"
      replace>
      <MenuItem name="/server-machine-node-info-list">
      <Icon type="ios-aperture" />
      服务器节点管理
      </MenuItem>
    </router-link>
    <router-link to="/upsteam-node-info-list"
      replace>
      <MenuItem name="/upsteam-node-info-list">
      <Icon type="md-cloud-done" />
      服务节点管理
      </MenuItem>
    </router-link>
  </Menu>
</template>
<script>
export default {
  computed: {

  },
  data() {
    return {}
  }
}
</script>
