<#list upSteamNodes as nodes>
    upstream ${nodes.nodeName} {
    <#list nodes.nodes as instance>
        server ${instance.physicalPath};
    </#list>
    }

</#list>