package com.yupi.wsyoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.wsyoj.common.BaseResponse;
import com.yupi.wsyoj.common.ErrorCode;
import com.yupi.wsyoj.common.ResultUtils;
import com.yupi.wsyoj.exception.BusinessException;
import com.yupi.wsyoj.exception.ThrowUtils;
import com.yupi.wsyoj.model.dto.question.QuestionQueryRequest;
import com.yupi.wsyoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.wsyoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yupi.wsyoj.model.entity.Question;
import com.yupi.wsyoj.model.entity.QuestionSubmit;
import com.yupi.wsyoj.model.entity.User;
import com.yupi.wsyoj.model.vo.QuestionSubmitVO;
import com.yupi.wsyoj.model.vo.QuestionVO;
import com.yupi.wsyoj.service.QuestionSubmitService;
import com.yupi.wsyoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return 提交结果
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交
        final User loginUser = userService.getLoginUser(request);
        long questionId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionId);
    }

    /**
     * 分页获取题目提交列表
     * 应该只有用户和管理员能看到自己的答案、代码
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(
            @RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
            HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        User loginUser = userService.getLoginUser(request);
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }



}
