package cn.zjipc.yd.online_exam.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxMsg {
    private boolean isSuccess;
    private String message;
    private Object element;

    public AjaxMsg(boolean isSuccess,String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
