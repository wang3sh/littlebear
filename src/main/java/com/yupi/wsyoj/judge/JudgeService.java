package com.yupi.wsyoj.judge;

import com.yupi.wsyoj.model.entity.QuestionSubmit;

/**
 * 判题service，可以改造成微服务
 * 因为后续用MQ进行异步操作，用户提交题目后可以视为直接进行后续操作了
 * 用户提交到DB，同时给消息队列加入消息，然后服务获取消息，进行判题。
 * 1. 根据提交的题目id，从DB获取题目，同时应该获取到提交信息。
 * 2. 开始判题
 * 3. 将判题的结果发送到DB,进行状态更新。
 */
public interface JudgeService {

    QuestionSubmit doJudge(Long questionSubmitId);
}
