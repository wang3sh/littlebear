package com.yupi.wsyoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.wsyoj.common.ErrorCode;
import com.yupi.wsyoj.exception.BusinessException;
import com.yupi.wsyoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.wsyoj.model.entity.Question;
import com.yupi.wsyoj.model.entity.QuestionSubmit;
import com.yupi.wsyoj.model.entity.QuestionSubmit;
import com.yupi.wsyoj.model.entity.User;
import com.yupi.wsyoj.model.enums.QuestionSubmitLanguageEnum;
import com.yupi.wsyoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.wsyoj.service.QuestionService;
import com.yupi.wsyoj.service.QuestionSubmitService;
import com.yupi.wsyoj.service.QuestionSubmitService;
import com.yupi.wsyoj.mapper.QuestionSubmitMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
* @createDate 2023-09-10 10:16:14
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{
    @Resource
    private QuestionService questionService;

    /**
     * 提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //语言是否合法？
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setLanguage(questionSubmit.getLanguage());
        questionSubmit.setCode(questionSubmit.getCode());
        questionSubmit.setJudgeInfo("{}");
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        boolean save = this.save(questionSubmit);
        if(!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入错误");
        }
        return questionSubmit.getQuestionId();
    }

}




