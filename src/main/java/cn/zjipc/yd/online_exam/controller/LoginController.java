package cn.zjipc.yd.online_exam.controller;

import cn.zjipc.yd.online_exam.bean.Admin;
import cn.zjipc.yd.online_exam.bean.AjaxMsg;
import cn.zjipc.yd.online_exam.bean.Student;
import cn.zjipc.yd.online_exam.bean.Teacher;
import cn.zjipc.yd.online_exam.service.AdminService;
import cn.zjipc.yd.online_exam.service.StudentService;
import cn.zjipc.yd.online_exam.service.TeacherService;
import cn.zjipc.yd.online_exam.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LoginController {
    private final AdminService adminService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public LoginController(AdminService adminService, TeacherService teacherService, StudentService studentService) {
        this.adminService = adminService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    /**
     * 后台登录接口
     *
     * @param admin 封装了管理员账号和密码的管理员对象
     */
    @PostMapping("/admin/login")
    public String adminLogin(Admin admin, Model model, HttpSession session, HttpServletResponse response) {
        Admin a = adminService.checkAdmin(admin);
        if (a != null) {
            session.setAttribute("user", a);
            Cookie cookie = new Cookie("JSESSIONID",session.getId());
            cookie.setMaxAge(21600);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/admin/";
        }
        model.addAttribute("loginMsg", "用户名或密码错误！");
        return "/admin/login";
    }

    /**
     * 学生登录接口
     *
     * @param student 封装了学生学号和密码的对象
     */
    @PostMapping("/student/login")
    @ResponseBody
    public AjaxMsg studentLogin(Student student, HttpServletRequest request,HttpServletResponse response) {
        //获取客户端发送请求时的服务器ip，用于返回给ajax重定向
        String reqIP = WebUtil.getRequestIP(request);
        //用户名密码验证成功返回true反之返回false
        Student stu = studentService.studentAuth(student);
        if (stu != null) {
            request.getSession().setAttribute("user", stu);
            request.getSession().removeAttribute("stuLoginMsg");
            Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
            cookie.setMaxAge(21600);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new AjaxMsg(true, reqIP);
        }
        return new AjaxMsg(false, reqIP);
    }

    /**
     * 教师登录
     *
     * @param teacher 封装了工号和密码的教师对象
     */
    @PostMapping("/teacher/login")
    @ResponseBody
    public AjaxMsg teachLogin(Teacher teacher, HttpServletRequest request,HttpServletResponse response) {
        //获取客户端发送请求时的服务器ip，用于返回给ajax重定向
        String reqIP = WebUtil.getRequestIP(request);
        //用户名密码验证成功返回true反之返回false
        Teacher t = teacherService.teacherAuth(teacher);
        if (t != null) {
            request.getSession().setAttribute("user", t);
            request.getSession().removeAttribute("teaLoginMsg");
            Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
            cookie.setMaxAge(21600);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new AjaxMsg(true, reqIP);
        }
        return new AjaxMsg(false, reqIP);

    }
}
