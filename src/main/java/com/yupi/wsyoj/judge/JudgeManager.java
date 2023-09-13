package com.yupi.wsyoj.judge;

import com.yupi.wsyoj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.wsyoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.yupi.wsyoj.judge.strategy.JudgeContext;
import com.yupi.wsyoj.judge.strategy.JudgeStrategy;
import com.yupi.wsyoj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.wsyoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 简化对判题功能的调用
 * 多个语言会有多个if-else,可以在这里实现。
 */
@Service
public class JudgeManager {

    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(questionSubmit.getLanguage())) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
