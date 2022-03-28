let studentIndex = new Vue({
    el:'#student_paper_list',
    data:{
        examList:[],
        nowTime: new Date().getTime()

    },
    created:function (){
        axios.get('/student/myExamList').then((response)=>{
            this.examList = response.data.element
        })
    },
    methods:{
        //点击试卷开始考试前判断逻辑
        startExam:function (papName,startTime,endTime){
            if(this.nowTime - Date.parse(startTime) < 0){
                $.tooltip('此考试未开始，请注意考试时间！');
                return;
            }
            if(this.nowTime - Date.parse(endTime) > 0){
                $.tooltip('此考试已结束！');
                return;
            }
            //判断学生是否已经完成该考试
            axios.get('/student/checkPaperDone?papName=' + papName).then((response)=>{
                if(response.data.element === false){
                    $.dialog('confirm','提示','你准备好开始考试了吗？',0,function(){
                        axios.get('student/startExam?papName=' + papName).then((response)=>{
                            if(response.data.success){
                                //重定向到考试页面
                                location.href = response.data.message + '/student/examPaperPage'
                            }else{
                                $.confirm({
                                    confirmButton: '继续考试',
                                    cancelButton: '取消',
                                    title: '提醒你',
                                    content: '你当前已经在进行一项考试，请完成该考试再进行其他考试',
                                    confirm: () => {
                                        location.href = response.data.message + '/student/examPaperPage'
                                    },
                                    cancel: function () {
                                        //取消
                                    }
                                });

                            }
                        })
                    });
                }else{
                    // $.tooltip('你已经完成此项考试，无法重复考试！',1500,true);
                    $.alert({
                        title: '提示',
                        content: '你已完成此项考试，无法重复进行考试!',
                    });
                }
            })
        }
    }
})
