package cn.zjipc.yd.online_exam.utils;


import cn.zjipc.yd.online_exam.bean.Paper;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;

public class WebUtil {

    public static String getRequestIP(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    public static boolean checkRole(String url, String role) {
        if (url.contains("admin") || url.contains("teacher") || url.contains("student")) {
            if ("admin".equals(role)) {
                String substring = url.substring(0, 6);
                return substring.contains(role);
            }
            String substring = url.substring(0, 8);
            return substring.contains(role);
        }
        return false;
    }

    public static Paper paperFormatDateTime(Paper paper) {
        String startTime = paper.getStartTime();
        String endTime = paper.getEndTime();
        String newStartTime = startTime.substring(0, 10) + 'T' + startTime.substring(11, 19);
        String newEndTime = endTime.substring(0, 10) + 'T' + endTime.substring(11, 19);
        paper.setStartTime(newStartTime);
        paper.setEndTime(newEndTime);
        return paper;
    }

}
