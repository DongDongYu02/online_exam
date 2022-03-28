package cn.zjipc.yd.online_exam.controller;

import cn.zjipc.yd.online_exam.bean.*;
import cn.zjipc.yd.online_exam.service.StudentService;
import cn.zjipc.yd.online_exam.utils.WebUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 获取学生专业的试卷列表
     *
     * @param session 获取session中的用户
     */
    @GetMapping("myExamList")
    @ResponseBody
    public AjaxMsg myExamList(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof Student) {
            Student student = (Student) user;
            String stuMajor = student.getStuMajor();
            List<Paper> studentPaperList = studentService.getStudentPaperList(stuMajor);
            return new AjaxMsg(true, "获取成功", studentPaperList);
        }
        return new AjaxMsg(false, "你可能不是学生？");
    }

    /**
     * 保存试卷名称并返回请求ip端口给前端做重定向
     *
     * @param papName 试卷名称
     */
    @GetMapping("startExam")
    @ResponseBody
    public AjaxMsg startExam(String papName, HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (user instanceof Student) {
            //判断当前学生是否已经有在考试的信息。
            String tempPapName = studentService.getExamTempPapNameByStuId(((Student) user).getId());
            if (tempPapName != null) {//如果有，保存该试卷名称
                request.getSession().setAttribute("papName", tempPapName);
                return new AjaxMsg(false, WebUtil.getRequestIP(request));
            }
            //如果没有，保存学生点击试卷的试卷名称
            request.getSession().setAttribute("papName", papName);
            //并添加临时考试数据
            studentService.addStudentExamTemp(((Student) user).getId(), papName);
            return new AjaxMsg(true, WebUtil.getRequestIP(request));
        }
        return new AjaxMsg(false, "你可能不是学生");
    }

    /**
     * 获取试卷信息接口
     *
     * @param session 从session中获取试卷名称
     */
    @GetMapping("examPaper")
    @ResponseBody
    public AjaxMsg getExamPaper(HttpSession session) throws ParseException {
        String papName = (String) session.getAttribute("papName");
        Student student = (Student) session.getAttribute("user");
        if (papName != null) {
            Paper examPaper = studentService.getExamPaper(papName);
            long examRemaining = studentService.getExamRemaining(student.getId(), papName);
            if (examPaper != null && examRemaining > 0) {
                examPaper.setExamRemaining(examRemaining);
                return new AjaxMsg(true, "获取成功", examPaper);
            }
            if (examPaper != null) {
                return new AjaxMsg(false, "考试结束");
            }
        }
        return new AjaxMsg(false, "没有此试卷");
    }

    /**
     * 获取考生开始考试后的试卷题目的分页数据
     *
     * @param session 获取考生开始开始的试卷名称
     * @param pageNum 页码/第几题？
     */
    @GetMapping("paperQuestion")
    @ResponseBody
    public AjaxMsg getPaperQuestion(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        String papName = (String) session.getAttribute("papName");
        Student user = (Student) session.getAttribute("user");
        if (pageNum != null) {
            PageHelper.startPage(pageNum, 1);
            List<Question> questions = studentService.checkIsAnswer(String.valueOf(user.getId()),
                    studentService.getQuestionListByPapName(papName));
            PageInfo<Question> pageInfo = new PageInfo<>(questions);
            return new AjaxMsg(true, "获取成功", pageInfo);
        }
        return new AjaxMsg(false, "没有此试卷");
    }

    @GetMapping("saveAnswer")
    @ResponseBody
    public AjaxMsg saveAnswer(HttpSession session, String queId, String answer) {
        Student user = (Student) session.getAttribute("user");
        studentService.saveAnswer(String.valueOf(user.getId()), queId, answer);
        return new AjaxMsg(true, "保存成功");
    }

    /**
     * 学生交卷接口
     *
     */
    @PostMapping("/submitExam")
    @ResponseBody
    public AjaxMsg submitExam( HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (user instanceof Student) {
            Student student = (Student) user;
            String paperName = (String) request.getSession().getAttribute("papName");
            if (studentService.referExam(paperName, student.getId()) > 0) {
                return new AjaxMsg(true, WebUtil.getRequestIP(request));
            }
        }
        return new AjaxMsg(false, "交卷失败");
    }

    /**
     * 获取学生历史成绩列表的分页数据接口
     *
     * @param pageNum 页码
     */
    @GetMapping("/myResults")
    @ResponseBody
    public AjaxMsg myResults(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof Student) {
            Student student = (Student) user;
            PageHelper.startPage(pageNum, 5);
            List<StudentScores> studentScores = studentService.getStudentScores(student.getId());
            PageInfo<StudentScores> pageInfo = new PageInfo<>(studentScores);
            return new AjaxMsg(true, "查询成功", pageInfo);
        }
        return new AjaxMsg(false, "你可能不是学生？");
    }

    /**
     * 检查学生是否已做过试卷
     *
     * @param papName 试卷名称
     */
    @GetMapping("/checkPaperDone")
    @ResponseBody
    public AjaxMsg checkPaperDone(String papName, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user instanceof Student) {
            Student student = (Student) user;
            if (studentService.checkPaperDone(student.getId(), papName)) {
                return new AjaxMsg(true, "已经做过了", true);
            }
            return new AjaxMsg(true, "没有做过", false);
        }
        return new AjaxMsg(false, "你可能不是学生？");
    }
}
