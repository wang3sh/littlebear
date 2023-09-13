package com.yupi.wsyoj.judge.codeSandbox.Impl;

import com.yupi.wsyoj.judge.codeSandbox.CodeSandBox;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeResponse;

/**
 * 第三方沙箱
 */
public class ThirdPartyCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
