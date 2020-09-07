package net.onebean.gateway.common;


/**
 * 错误码定义
 * @author Icc
 * 1-4位 1010 代表 服务管理中心
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
    REQUEST_PARAM_ERROR("1010010001","请求参数校验异常"),
    SIGN_ERROR("1010010002","签名校验异常"),
    TIME_STAMP_ERROR("1010010003","过时的请求"),
    //02
    NONE_QUERY_DATA("1010020001","没有对应的数据"),
    GET_DATE_ERR("1010020002","数据库查询数据异常"),
    INSERT_DATA_ERROR("1010020003","插入数据失败"),
    DIRTY_DATA_ERR("1010020004","脏数据异常"),
    REPEAT_DATA_ERR("1010020005","已有重复的数据"),
    GET_CACHE_ERR("1010020006","获取redis数据失败"),
    DELETE_CACHE_ERR("1010020007","删除redis数据失败"),
    //03
    PUT_CACHE_ERR("1010030001","插入redis数据失败"),
    GET_REDIS_LOCK_ERR("1010030002","获取分布式锁失败"),
    //05
    CLOUD_API_ERROR("1010050001","远程调用接口失败"),
    READ_APOLLO_ERROR("1010050002","读取配置失败"),
    JSCH_ERROR("1010050003","读取服务器配置失败"),
    READ_VALUE_FROM_APOLLO("1010030004","从配置中心获取信息失败"),
    //06
    DATA_REPEAT_ERR("1010060001","已有重复的数据"),
    DATA_BINDING_ERR("1010060002","数据被引用,无法删除,请先解除关联"),
    REPEART_PROXY_PATH_ERR("1010060003","已有重复的代理地址"),
    SPRING_CLOUD_ERR("1010060004","微服务调用异常"),
    DEPRECATED_LESS_FIVE("1010060005","该节点弃用不足5分钟，不允许停用！"),
    NO_DATA_TO_SYNC("1010060006","没有获取到需要同步的数据"),
    READ_APOLLO_EMPTY_VALUE("1010060007","从配置中心获取的值为空"),
    UPDATE_NGINX_SAFE_ROLL_BACK_ERROR("1010060008","更新UAG失败,已回滚,请人工介入"),
    UPDATE_NGINX_UN_ROLL_BACK_ERROR("1010060009","更新UAG失败,执行回滚操作失败,请立即人工介入,很紧急"),
    INVALID_SMS_CODE("1010040010","无效的验证码"),
    INVALID_ACCOUNT("1010040011","无效的账户"),
    INVALID_PASSWORD("1010040012","无效的密码"),
    //07
    JSON_CAST_ERROR("1010070001","序列化JSON异常"),
    REF_ERROR("1010070002","反射对象属性异常"),
    IO_ERROR("1010070003","读写文件异常"),
    VALUE_CAN_NOT_BE_EMPTY_ERR("1010070004","获取值为空异常"),
    INVALID_OPERATION_LINUX_PATH("1010070005","操作linux的路径中不能包含'*'的危险操作"),
    TEMPLATE_ERROR("1010070006","模板生成异常"),
    TOO_BUSY_ERR("1010070007","操作频繁,请稍后再试"),
    //08
    RABBIT_MQ_BUSSINES_ERR("1010080001","mq业务处理异常"),
    //99
    OTHER("1010999999","操作失败"),
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
