let examPaper = new Vue({
    el: '#exam_paper',
    data: {
        //考生提交的题目与答案结果
        result: [],
        //考生当前答的一道题目，用于保存在结果里
        current: {
            questionId: '',
            questionType: '',
            answer: '',
        },
        //当前一道题目的分页数据
        pageInfo: {},
        //当前一道题目
        questions: [],
        paperInfo: {},
        multiAnswer: [],
        remaining: 0,
        hour: '',
        minute: '',
        second: ''
    },
    mounted: function () {
        this.getPaperInfo()
        this.getPageInfo(1)
        this.countDown()
    },
    methods: {
        //ajax请求获取试卷信息
        getPaperInfo: function () {
            axios.get('examPaper').then((response) => {
                if (response.data.success) {
                    this.paperInfo = response.data.element
                    this.remaining = this.paperInfo.examRemaining
                    this.hour = Math.floor((this.remaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
                    this.minute = Math.floor((this.remaining % (1000 * 60 * 60)) / (1000 * 60))
                    this.second = Math.floor((this.remaining % (1000 * 60)) / 1000)
                    if (window.name === "") {
                        $.tooltip('开始考试！', 2000, true)
                        window.name = 'reload'
                    }
                } else if (response.data.message === '考试结束') {
                    axios({
                        method: 'post',
                        url: 'submitExam',
                        data: this.result,
                        header: {
                            'Content-Type': 'application/json'  //如果写成contentType会报错
                        }
                    }).then((response) => {
                        if (response.data.success) {
                            $.dialog('alert', '提示', '你因中途离开考试，考试时间已到！', 3000, function () {
                                location.href = response.data.message + '/student'
                            }); //3s自动关闭
                        }
                    })
                }

            })
        },
        //ajax请求获取题目信息分页数据
        getPageInfo: function (pageNum) {
            this.multiAnswer = []
            this.current={questionId:'',questionType:'',answer:''}
            let element = document.querySelectorAll(".option");
            for (let op of element) {
                if (op.checked) {
                    op.checked = false
                }
            }
            axios.get('paperQuestion?pageNum=' + pageNum).then((response) => {
                this.pageInfo = response.data.element
                //设置一道题目
                this.questions = this.pageInfo.list
                //设置当前题目的id，用于和考生选的答案一并封装给当前题目，保存在答案集合中
                this.current.questionId = this.questions[0].id
                this.current.questionType = this.questions[0].queType
                //如果考生当前考试中回答过这道题目，设置考生回答的答案
                if (this.questions[0].stuAnswer !== null) {
                    this.current.answer = this.questions[0].stuAnswer
                }
            }).then((response) => {
                let element = document.querySelectorAll(".option");
                if (this.current.answer != null) {
                    for (let option of element) {
                        if (option.checked) {
                            option.checked = false
                        }
                        if (this.current.questionType === '2') {
                            this.multiAnswer = this.multiAnswerToArray()
                        } else if (option.value === this.current.answer) {
                            option.checked = true
                        }
                    }
                } else {
                    this.multiAnswer = []
                    for (let option of element) {
                        if (option.checked) {
                            option.checked = false
                        }
                    }
                }
            })
        },
        //下一题逻辑
        next: function () {

            if (!this.pageInfo.isLastPage) {
                this.getPageInfo(this.pageInfo.nextPage)
            }
        },
        pre: function () {
            if (!this.pageInfo.isFirstPage) {
                this.getPageInfo(this.pageInfo.prePage)
            }
        },
        //将多选答案字符串分割成数组
        multiAnswerToArray: function () {
            return this.current.answer.split(',')
        },
        //将多选题数组转换成字符串
        multiAnswerToString: function () {
            let answers = ''
            for (let i = 0; i < this.multiAnswer.length; i++) {
                if (i === this.multiAnswer.length - 1) {
                    answers += this.multiAnswer[i]
                } else {
                    answers += this.multiAnswer[i] + ','
                }
            }
            if(this.multiAnswer[0] === ''){
                this.multiAnswer.shift()
            }
            if(this.multiAnswer[this.multiAnswer.length-1] === ''){
                this.multiAnswer.pop()
            }
            return answers
        },
        //保存考生当前题目的选择的答案
        isAnswer: function () {
            if (this.multiAnswer.length !== 0) {
                this.current.answer = this.multiAnswerToString()
            } else {
                //获取当前题目的四个选项dom
                let element = document.querySelectorAll(".option");
                //遍历选项，获取选中的选项，并赋值给当前题目current的答案answer
                for (let option of element) {
                    if (option.checked) {
                        this.current.answer = option.value
                    }
                }
            }

            axios.get('/student/saveAnswer?queId=' + this.current.questionId +
                "&answer=" + this.current.answer).then((response) => {
                //do
            })
        },
        //提交试卷
        submitExam: function () {
            // console.log(this.result)
            axios.get('/student/checkPaperDone?papName=' + this.paperInfo.papName).then((response) => {
                if (response.data.element === false) {
                    $.confirm({
                        confirmButton: '确认',
                        cancelButton: '取消',
                        title: '提醒你',
                        content: '确认交卷吗？',
                        confirm: () => {
                            axios({
                                method: 'post',
                                url: 'submitExam',
                                data: this.result,
                                header: {
                                    'Content-Type': 'application/json'  //如果写成contentType会报错
                                }
                            }).then((response) => {
                                if (response.data.success) {
                                    $.dialog('alert', '提示', '正在处理中...', 2000, function () {
                                        location.href = response.data.message + '/student/submitSuccessPage'
                                    }); //2s自动关闭
                                }
                            })
                        },
                        cancel: function () {
                            //取消
                        }
                    });
                } else {
                    $.tooltip('你已经交卷，请勿重复提交！');
                }
            })
        },
        //倒计时函数
        countDown: function () {
            clearInterval(this.timer)
            this.timer = setInterval(() => {
                if (this.hour === 0) {
                    if (this.minute !== 0 && this.second === 0) {
                        this.second = 59
                        this.minute -= 1
                    } else if (this.minute === 0 && this.second === 0) {
                        this.second = 0
                        clearInterval(this.timer)
                        axios.get('/student/checkPaperDone?papName=' + this.paperInfo.papName).then((response) => {
                            if (response.data.element === false) {
                                axios({
                                    method: 'post',
                                    url: 'submitExam',
                                    data: this.result,
                                    header: {
                                        'Content-Type': 'application/json'  //如果写成contentType会报错
                                    }
                                }).then((response) => {
                                    if (response.data.success) {
                                        $.dialog('alert', '提示', '考试时间到，正在提交试卷...', 2000, function () {
                                            location.href = response.data.message + '/student/submitSuccessPage'
                                        }); //2s自动关闭
                                    }
                                })
                            } else {
                                $.tooltip('你已经交卷，请勿重复提交！');
                            }
                        })
                    } else {
                        this.second -= 1
                    }
                } else {
                    if (this.minute !== 0 && this.second === 0) {
                        this.second = 59
                        this.minute -= 1
                    } else if (this.minute === 0 && this.second === 0) {
                        this.hour -= 1
                        this.minute = 59
                        this.second = 59
                    } else {
                        this.second -= 1
                    }
                }
            }, 1000)
        },
        //格式化倒计时时间 如果小于10 + 0 ?
        formatNum(num) {
            return num < 10 ? '0' + num : '' + num
        }
    },
    computed: {
        hourString() {
            return this.formatNum(this.hour)
        },
        minuteString() {
            return this.formatNum(this.minute)
        },
        secondString() {
            return this.formatNum(this.second)
        }
    }
})