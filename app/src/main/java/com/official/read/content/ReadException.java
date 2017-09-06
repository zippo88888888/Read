package com.official.read.content;

/**
 * com.official.read.content
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public class ReadException extends RuntimeException {

    /** 正常状态 */
    public static final int CONNECTION_200 = 200;
    /** 服务器错误 */
    public static final int ERROR_HTTP_500 = 0;
    /** 请求地址错误 */
    public static final int ERROR_HTTP_404 = 1;
    /** 读取超时 */
    public static final int ERROR_READ_TIMEOUT = 2;
    /** 连接超时 */
    public static final int ERROR_CONNECTION_TIMEOUT = 3;
    /** 未知错误 */
    public static final int ERROR_UNKNOWN = -1;


    private static final long serialVersionUID = 987654321L;

    public ReadException() {
        super();
    }

    public ReadException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ReadException(String detailMessage) {
        super(detailMessage);
    }

    public ReadException(Throwable throwable) {
        super(throwable);
    }
}
