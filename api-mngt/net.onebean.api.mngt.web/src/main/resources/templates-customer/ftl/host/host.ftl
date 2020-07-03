server {

<#if hostNode.isSsl == '1'>
    listen ${hostNode.sslListenPort};
<#else>
    listen ${hostNode.listenPort};
</#if>
    server_name ${hostNode.serverHost};
<#if hostNode.isSsl == '1'>
    ssl_certificate ${hostNode.sslCrtPath};
    ssl_certificate_key ${hostNode.sslCrtKeyPath};
</#if>

    access_log /usr/local/openresty/nginx/uag/logs/front/${hostNode.upsteamNodeName}-access.log;
    error_log  /usr/local/openresty/nginx/uag/logs/front/${hostNode.upsteamNodeName}-error.log debug;

    location / {
        proxy_pass http://${hostNode.upsteamNodeName};
    }

}

<#if hostNode.isSsl == '1'>
server {

    listen ${hostNode.listenPort};
    server_name ${hostNode.serverHost};

    access_log /usr/local/openresty/nginx/uag/logs/front/${hostNode.upsteamNodeName}-access.log;
    error_log  /usr/local/openresty/nginx/uag/logs/front/${hostNode.upsteamNodeName}-error.log debug;

    return      301 https://$server_name$request_uri;

}
</#if>