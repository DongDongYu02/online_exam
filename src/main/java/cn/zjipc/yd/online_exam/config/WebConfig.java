package cn.zjipc.yd.online_exam.config;

import cn.zjipc.yd.online_exam.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/js/**", "/css/**", "/fonts/**", "/", "/admin/loginPage",
                        "/admin/login", "/teacher/login", "/student/login");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //========================================/admin/..=======================================================
        //     * 登录页映射
        registry.addViewController("/admin/loginPage").setViewName("admin/login");
        //     * url /admin 跳转到后台首页
        registry.addViewController("/admin").setViewName("admin/index");
        //     * url /admin/ 跳转到后台首页
        registry.addViewController("/admin/").setViewName("admin/index");
        //     * 管理员管理页映射
        registry.addViewController("/admin/adminManage").setViewName("admin/admin_manage");
        //     * 教师信息管理页映射
        registry.addViewController("/admin/teacherManage").setViewName("admin/teacher_manage");
        //     * 学生信息管理页映射
        registry.addViewController("/admin/studentManage").setViewName("admin/student_manage");
        //     * 专业信息管理页映射
        registry.addViewController("/admin/majorManage").setViewName("admin/major_manage");
        //     * 试卷信息管理页映射
        registry.addViewController("/admin/paperManage").setViewName("admin/paper_manage");
        //     * 题目信息管理页映射
        registry.addViewController("/admin/questionManage").setViewName("admin/question_manage");

        //=======================================/student/..=====================================================
        //     * url /student 跳转到学生首页
        registry.addViewController("/student").setViewName("student/index");
        //     * 学生考试页映射
        registry.addViewController("/student/examPaperPage").setViewName("student/exam_paper");
        //     * 学生成绩页映射
        registry.addViewController("/student/myResultsPage").setViewName("student/my_results");
        //     * 学生交卷成功页映射
        registry.addViewController("/student/submitSuccessPage").setViewName("student/submit_success");

        //--------------------------------------/teacher/..=====================================================
        //     * url /teacher 跳转到教师首页
        registry.addViewController("/teacher").setViewName("teacher/index");
        //     * url /teacher/ 跳转到教师首页
        registry.addViewController("/teacher/").setViewName("teacher/index");
        //     * 我的学生页面映射
        registry.addViewController("/teacher/studentInfo").setViewName("teacher/myStudent");
        //     * 我的专业试卷管理页映射
        registry.addViewController("/teacher/myPaperManage").setViewName("teacher/myPaper");
        //     * 试卷题目管理页映射
        registry.addViewController("/teacher/paperQuestionManage").setViewName("teacher/myPaperQuestions");


    }
}
