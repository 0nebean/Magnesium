var api = {}


api.getMenuList = '/framework/findBasicMenuList'
api.logout = '/framework/logout'
api.getCurrentUagLoginInfo = '/framework/getCurrentUagLoginInfo'


api.serverInfoDelete = '/serverInfo/delete'
api.serverInfoFind = '/serverInfo/find'
api.serverInfoFindById = '/serverInfo/findById'
api.serverInfoAdd = '/serverInfo/add'
api.serverInfoUpdate = '/serverInfo/update'
api.syncServerMachineNodeInfo = '/serverInfo/syncServerMachineNodeInfo'
api.findServerByName = '/serverInfo/findServerByName'

api.apiInfoFind = '/apiInfo/find'
api.apiInfoAdd = '/apiInfo/add'
api.apiInfoFindById = '/apiInfo/findById'
api.apiInfoUpdate = '/apiInfo/update'
api.apiInfoDelete = '/apiInfo/delete'
api.findApiByAppId = '/apiInfo/findApiByAppId'
api.findAppApiRelationshipByServerIdAndAppId = '/apiInfo/findAppApiRelationshipByServerIdAndAppId'

api.serverMachineNodeInfoDelete = '/serverMachineNodeInfo/delete'
api.serverMachineNodeInfoFind = '/serverMachineNodeInfo/find'
api.serverMachineNodeInfoFindById = '/serverMachineNodeInfo/findById'
api.serverMachineNodeInfoAdd = '/serverMachineNodeInfo/add'
api.serverMachineNodeInfoUpdate = '/serverMachineNodeInfo/update'
api.syncNginxNodeInfo = '/serverMachineNodeInfo/syncNginxNodeInfo'

api.upSteamNodeInfoDelete = '/upSteamNodeInfo/delete'
api.updateSelectedVersion = '/upSteamNodeInfo/updateSelectedVersion'
api.upSteamNodeInfoFind = '/upSteamNodeInfo/find'
api.upSteamNodeInfoFindById = '/upSteamNodeInfo/findById'
api.upSteamNodeInfoAdd = '/upSteamNodeInfo/add'
api.upSteamNodeInfoUpdate = '/upSteamNodeInfo/update'
api.findVersionListByNodeName = '/upSteamNodeInfo/findVersionListByNodeName'
api.setRunningStatusDownByDevOpsK8sNotificationVo = '/upSteamNodeInfo/setRunningStatusDownByDevOpsK8sNotificationVo'

api.upSteamNameAdd = '/upSteamName/add'
api.upSteamNameDelete = '/upSteamName/delete'
api.upSteamNameFind = '/upSteamName/find'


api.appInfoDelete = '/appInfo/delete'
api.appInfoFind = '/appInfo/find'
api.appInfoFindById = '/appInfo/findById'
api.appInfoAdd = '/appInfo/add'
api.appInfoUpdate = '/appInfo/update'


api.bindingApi = '/appInfo/bindingApi'
api.unBindingApi = '/appInfo/unBindingApi'
api.syncAppApiRelationship = '/appInfo/syncAppApiRelationship'
api.ipWhtieListAdd = '/ipWhtieList/add'
api.ipWhtieListDelete = '/ipWhtieList/delete'
api.ipWhtieListFind = '/ipWhtieList/find'
api.ipWhtieListFind = '/ipWhtieList/find'
api.unLoginAccessWhiteListFindUnBindingData = '/unLoginAccessWhiteList/findUnBindingData'
api.unLoginAccessWhiteListAdd = '/unLoginAccessWhiteList/add'
api.unLoginAccessWhiteListFind = '/unLoginAccessWhiteList/find'
api.unLoginAccessWhiteListDelete = '/unLoginAccessWhiteList/delete'

api.uagUserInfoFind = '/uagUserInfo/find'
api.uagUserInfoFindUagUserAppList = '/uagUserInfo/findUagUserAppList'
api.uagUserInfoAddAccount = '/uagUserInfo/addAccount'
api.uagUserInfoToggleIsLockStatus = '/uagUserInfo/toggleIsLockStatus'
api.uagUserInfoRestPassword = '/uagUserInfo/restPassword'
api.uagUserInfoFindById = '/uagUserInfo/findById'
api.uagUserInfoModify= '/uagUserInfo/modify'
api.uagLogFind = '/uagLog/find'

export default api