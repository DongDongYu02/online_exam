package cn.zjipc.yd.online_exam.interceptor;

import cn.zjipc.yd.online_exam.bean.Admin;
import cn.zjipc.yd.online_exam.bean.Student;
import cn.zjipc.yd.online_exam.bean.Teacher;
import cn.zjipc.yd.online_exam.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        Object user = request.getSession().getAttribute("user");
        String requestURI = request.getRequestURI().substring(1,6);
        if (user == null) {
            if (requestURI.equals("teach")) {
                request.getSession().setAttribute("teaLoginMsg", "请先登录!");
                request.getSession().removeAttribute("stuLoginMsg");
                response.sendRedirect("/");
                return false;
            }
            if (requestURI.equals("stude")) {
                request.getSession().setAttribute("stuLoginMsg", "请先登录!");
                request.getSession().removeAttribute("teaLoginMsg");
                response.sendRedirect("/");
                return false;
            }
            if (requestURI.equals("admin")) {
                response.sendRedirect("/admin/loginPage");
                return false;
            }
        }
        if (requestURI.equals("admin")) {
            Object admin = request.getSession().getAttribute("user");
            if (!(admin instanceof Admin)) {
                response.sendRedirect(WebUtil.getRequestIP(request)+"/");
                return false;
            }
        }
        if (requestURI.equals("teach")) {
            Object teacher = request.getSession().getAttribute("user");
            if (!(teacher instanceof Teacher)) {
                response.sendRedirect(WebUtil.getRequestIP(request)+"/");
                return false;
            }
        }
        if("stude".equals(requestURI)){
            Object student = request.getSession().getAttribute("user");
            if(!(student instanceof Student)){
                response.sendRedirect(WebUtil.getRequestIP(request)+"/");
                return false;

            }
        }
        return true;
    }
}
