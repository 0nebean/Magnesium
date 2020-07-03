package net.onebean.api.mngt.common;


/**
 * 错误码定义
 * @author 0neBean
 * 1-4位 1102 代表 服务管理中心
 * 异常类型：5～6位，标识数据库、接口、缓存、权限等
 *	01-参数异常，参数有效性校验错误，在接口的response 的msg中请将校验有问题的参数返回给调用方；
 *	02-数据库异常，操作数据库异常，log的message中要有sql、参数信息，sql如果能在堆栈中体现，可以不放在message中；
 *	03-缓存异常，如对redis、JVM缓存操作失败；
 *	04-权限异常，功能权限、数据权限类异常；
 *	05-接口异常，接口调用异常，log 的message中要包含接口地址、接口参数信息，如果能在堆栈中体现，可以不放在message中；
 *	06-业务异常, 业务执行异常， 比如”用户名已存在”，此种类型的异常通常前端要关注；
 *	07-其它运行异常，如JAVA程序运行时异常；
 *	08-消息队列异常，如对rabbitMq 的操作失败；
 *	...
 *	99-除以上外的异常
 *	异常序列号：6～10位，每个模块自定义，要求模块范围内唯一性
 */
public enum ErrorCodesEnum {
    SUSSESS("0",""),
    //01
    REQUEST_PARAM_ERROR("1102010001","请求参数校验异常"),
    SIGN_ERROR("1101010002","签名校验异常"),
    TIME_STAMP_ERROR("1101010003","过时的请求"),
    //02
    NONE_QUERY_DATA("1102020001","没有对应的数据"),
    GET_DATE_ERR("1102020002","数据库查询数据异常"),
    INSERT_DATA_ERROR("1102020003","插入数据失败"),
    DIRTY_DATA_ERR("1102020004","脏数据异常"),
    REPEAT_DATA_ERR("1102020005","已有重复的数据"),
    //03
    PUT_CACHE_ERR("1202030001","插入redis数据失败"),
    GET_REDIS_LOCK_ERR("1202030002","获取分布式锁失败"),
    //05
    CLOUD_API_ERROR("1104050001","远程调用接口失败"),
    READ_APOLLO_ERROR("1202050002","读取配置失败"),
    JSCH_ERROR("1202050003","读取服务器配置失败"),
    READ_VALUE_FROM_APOLLO("1101050001","从配置中心获取信息失败"),
    //06
    DATA_BINDING_ERR("1101060003","数据被引用,无法删除,请先解除关联"),
    DATA_REPEAT_ERR("1102060001","已有重复的数据"),
    REPEART_PROXY_PATH_ERR("1102060003","已有重复的代理地址"),
    SPRING_CLOUD_ERR("1102060004","微服务调用异常"),
    DEPRECATED_LESS_FIVE("1102060005","该节点弃用不足5分钟，不允许停用！"),
    READ_APOLLO_EMPTY_VALUE("1202060007","从配置中心获取的值为空"),
    UPDATE_NGINX_SAFE_ROLL_BACK_ERROR("1202060008","更新UAG失败,已回滚,请人工介入"),
    UPDATE_NGINX_UN_ROLL_BACK_ERROR("1202060009","更新UAG失败,执行回滚操作失败,请立即人工介入,很紧急"),
    //07
    JSON_CAST_ERROR("1102070001","序列化JSON异常"),
    REF_ERROR("1102070002","反射对象属性异常"),
    IO_ERROR("1202070003","读写文件异常"),
    VALUE_CAN_NOT_BE_EMPTY_ERR("1202070004","获取值为空异常"),
    INVALID_OPERATION_LINUX_PATH("1202070005","操作linux的路径中不能包含'*'的危险操作"),
    //08
    RABBIT_MQ_BUSSINES_ERR("1202070001","mq业务处理异常"),
    //99
    OTHER("1102999999","操作失败"),
    ;

    private String code;

    private String msg;

    private ErrorCodesEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String code(){
        return this.code;
    }

    public String msg(){
        return this.msg;
    }
}
