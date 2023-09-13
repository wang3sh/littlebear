package com.yupi.wsyoj.judge.codeSandbox.Impl;

import com.yupi.wsyoj.judge.codeSandbox.CodeSandBox;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeResponse;

/**
 * 远程调用
 */
public class RemoteCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
