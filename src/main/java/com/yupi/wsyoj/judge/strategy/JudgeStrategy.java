package com.yupi.wsyoj.judge.strategy;

import com.yupi.wsyoj.judge.codeSandbox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}
