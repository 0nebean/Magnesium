local webUtil = require("util.webUtil");
local constants = require("comm.constants");
local config = require("comm.config");
local stringUtil = require("util.stringUtil");
local comm = require("comm.comm");
local errorCodesEnum = require("comm.errorCodesEnum");

local function isIpMatch(allowIpTable,remoteIp)
	local bool = false;
	for _, allowIp in ipairs(allowIpTable) do
		if (allowIp == remoteIp) then
			bool = true;
			break;
		end
	end
	return bool;
end


--========================================================================

ngx.log(ngx.DEBUG, "devops Allow Check start");

local allowIps = config.get(constants.ALLOW_IPS);
ngx.log(ngx.DEBUG, "devops allow ips = ["..allowIps.."]");
local allowIpTable = stringUtil.split(allowIps, ",");

local remoteIp = webUtil.getRemoteIp();
ngx.log(ngx.DEBUG, "remoteIp = ["..remoteIp.."]");

local match = isIpMatch(allowIpTable,remoteIp);

if (not match) then
    -- ip不匹配时候
    ngx.log(ngx.ERR, "remoteIp not match allowIps");
    comm.error4OpenApiWithCode("deny ip access",errorCodesEnum.deny_ip_access);
end

ngx.log(ngx.DEBUG, "remoteIp match allowIps, devops Allow Check done!");

