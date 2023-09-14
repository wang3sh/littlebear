package com.yupi.wsyoj.judge.strategy;

import com.yupi.wsyoj.model.dto.question.JudgeCase;
import com.yupi.wsyoj.judge.codeSandbox.model.JudgeInfo;
import com.yupi.wsyoj.model.entity.Question;
import com.yupi.wsyoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 若不清楚要给策略模式传递什么参数，可以先定义一个上下文
 * 先把之前写的普通策略粘过去就知道了
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private QuestionSubmit questionSubmit;
}
