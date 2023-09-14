package com.yupi.wsyoj.judge.codeSandbox.Impl;

import com.yupi.wsyoj.judge.codeSandbox.CodeSandBox;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeResponse;
import com.yupi.wsyoj.judge.codeSandbox.model.JudgeInfo;
import com.yupi.wsyoj.model.enums.JudgeInfoMessageEnum;
import com.yupi.wsyoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 测试可用性
 */
public class ExampleSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        //如果希望调用代码沙箱前后输出日志，便于分析，可以使用代理模式对方法增强
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        
        return executeCodeResponse;
    }
}
