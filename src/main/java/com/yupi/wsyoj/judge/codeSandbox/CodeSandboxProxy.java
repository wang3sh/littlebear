package com.yupi.wsyoj.judge.codeSandbox;

import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 静态代理对代码沙箱增强：前后输出日志
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandBox{

    private final CodeSandBox codeSandBox;

    public CodeSandboxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息: " + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱处理信息: " + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
