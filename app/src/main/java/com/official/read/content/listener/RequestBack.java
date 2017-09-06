package com.official.read.content.listener;

import com.official.read.content.ReadException;

/**
 * com.official.read.content
 * Created by ZP on 2017/7/12.
 * description:
 * version: 1.0
 */

public interface RequestBack<T> {

    void success(T data, String msg);

    void error(ReadException e, int errorCode);
}
