package com.jvv.reapal.facade.result

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/5
 * @time 16:22
 * @version 1.0
 */
enum CommonResultCode implements ResultCodeable{

    EXECUTE_SUCCESS("200", "执行成功"),

    NULL_ARGUMENT_EXCEPTION("comm_10_0001", "参数为空"),

    /**
     * 参数个数错误
     */
    ILLEGAL_NUMBER_OF_ARGUMENT("comm_10_0101", "参数个数错误"),
    /**
     * 参数类型错误
     */
    ILLEGAL_ARGUMENT_TYPE("comm_10_0201", "参数类型错误"),

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT_EXCEPTION("comm_10_0999", "非法参数"),

    /**
     * 数据库异常
     */
    DB_EXCEPRION("comm_10_1999", "数据库异常"),

    /**
     * 业务异常
     */
	BIZ_EXCEPRION("comm_10_8999", "业务异常"),

    /**
     * 系统异常(系统的基础异常)
     */
	SYS_EXCEPRION("comm_10_9999", "系统异常"),

    /**
     * 系统异常(系统内部异常，比如工具类常，组件内部异常)
     */
	NEST_EXCEPRION("comm_20_9999", "系统异常"),

    /**
     * 网络异常
     */
    NET_EXCEPRION("comm_90_9999", "网络异常"),

    /**
     * 未知异常
     */
    UNKNOWN_EXCEPRION("comm_99_9999", "未知异常"),

    UNBINDCARD_EXCEPTION("10001","未绑卡")
    /**
     * 枚举值
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    private CommonResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    String getCode() {
        return code
    }

    @Override
    String getMessage() {
        return message
    }
}