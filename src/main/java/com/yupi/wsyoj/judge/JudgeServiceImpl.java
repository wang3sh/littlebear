package com.yupi.wsyoj.judge;

import cn.hutool.json.JSONUtil;
import com.yupi.wsyoj.common.ErrorCode;
import com.yupi.wsyoj.exception.BusinessException;
import com.yupi.wsyoj.judge.codeSandbox.CodeSandBox;
import com.yupi.wsyoj.judge.codeSandbox.CodeSandboxFactory;
import com.yupi.wsyoj.judge.codeSandbox.CodeSandboxProxy;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeResponse;
import com.yupi.wsyoj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.wsyoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.yupi.wsyoj.judge.strategy.JudgeContext;
import com.yupi.wsyoj.judge.strategy.JudgeStrategy;
import com.yupi.wsyoj.model.dto.question.JudgeCase;
import com.yupi.wsyoj.model.dto.question.JudgeConfig;
import com.yupi.wsyoj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.wsyoj.model.entity.Question;
import com.yupi.wsyoj.model.entity.QuestionSubmit;
import com.yupi.wsyoj.model.enums.JudgeInfoMessageEnum;
import com.yupi.wsyoj.model.enums.QuestionSubmitLanguageEnum;
import com.yupi.wsyoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.wsyoj.model.vo.QuestionSubmitVO;
import com.yupi.wsyoj.service.QuestionService;
import com.yupi.wsyoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
        /**
         *  1. 根据提交的题目id，从DB获取题目，同时应该获取到提交信息。
         *  2. 开始判题
         *  3. 将判题的结果发送到DB,进行状态更新。
         */
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 判题并不是无条件判题
        // 只有等待中的题才需要判题
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.JUDGING.getValue());
        questionSubmitUpdate.setId(questionSubmitId);
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "状态更新错误");
        }
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String jc = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(jc, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        //建造者模式
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        //沙箱输出结果
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        //开始判题：输入输出
        //注意这里的if-else，以及在这里进行判题逻辑是否优雅
        //并且针对不同的语言，有不同的标准，比如java执行的时间要比c++多
        //--> 用策略模式优化

        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        judgeContext.setJudgeCaseList(judgeCaseList);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        questionSubmitUpdate.setId(questionSubmitId);
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmit latestSubmitStatus = questionSubmitService.getById(questionSubmitId);
        return latestSubmitStatus;
    }
}
