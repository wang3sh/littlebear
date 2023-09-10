package com.yupi.wsyoj.service;

import com.yupi.wsyoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.wsyoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.wsyoj.model.entity.User;

/**
* @author Administrator
* @description 针对表【question_submit(题目提交表)】的数据库操作Service
* @createDate 2023-09-10 10:16:14
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
