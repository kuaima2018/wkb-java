package com.chinadrtv.common.code;

public enum CommonRspCode implements RspCode {

    /** 系统 */
    SUCCESS("成功", "000"), 
    REPEAT("重复交易", "001"), 
    OFFLINE("系统离线状态", "002"), 
    ERROR("异常", "003"), 
    PARAMBUG("参数不完整", "004"), 
    FAILD("失败", "005"),

    /** 验证 */
    SIGNATURE_FAIL("验签失败", "010"), 
    FORMAT_FAIL("请求数据格式非法", "060"), 
    VERIFY_FAIL("请求数据校验失败", "062"), 
    DATA_NOT_EXIST("数据不存在", "070"), 
    DATA_REPEAT("数据重复", "071"), 
    ROLE_ERROR("权限不足", "091"),

    /** 远程异常 */
    DB_ERROR("数据库异常", "097"), 
    SYS_TIMEOUT("系统超时", "098"), 
    SYS_ERROR("系统错误", "099"),

    /** 连接 */
    TIMEOUT("连接超时失效", "011"), 
    ILLEGAL_IP("请求IP非法", "061");

    private String value;

    private String chName;

    private CommonRspCode(String chName, String value) {
        this.value = value;
        this.chName = chName;
    }

    @Override
    public String getCode() {
        return value;
    }

    @Override
    public String getName() {
        return chName;
    }

    public static CommonRspCode getEnum(String value) {
        CommonRspCode[] crc = CommonRspCode.values();
        for (int i = 0; i < crc.length; i++) {
            if (crc[i].getCode().equals(value)) {
                return crc[i];
            }
            i++;
        }
        return null;
    }

}
