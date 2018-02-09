package com.kuaimasoft.common.exception;

public class KuaiMaException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private Integer code;

    public KuaiMaException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
