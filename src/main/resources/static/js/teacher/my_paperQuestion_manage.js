let questionTable = new Vue({
    el:'#question_list_table',
    data:{
        pageInfo:{},
        newQuestionInfo: {
            queName: '',
            queType: '',
            queOption1: '',
            queOption2: '',
            queOption3: '',
            queOption4: '',
            queAnswer: '',
            quePaper: ''

        },
        updateQuestionInfo: {
            id: '',
            queName: '',
            queType: '',
            queOption1: '',
            queOption2: '',
            queOption3: '',
            queOption4: '',
            queAnswer: '',
            quePaper: ''
        },
        multiAnswer:[]
    },
    created:function (){
        this.getPageInfo(1)
    },
    methods:{
        getPageInfo:function (pageNum){
            axios.get('myPaperInfo/questions?' +
                'pageNum=' + pageNum).then((response)=>{
                this.pageInfo = response.data.element
            })
        },
        //点击添加按钮打开题目添加模态框，并获取试卷列表
        add: function () {
            $("#question_add_model").modal({
                backdrop: 'static'
            });
            this.multiAnswer = []
        },
        //点击修改按钮打开修改题目信息模态框，获取试卷列表并回显该题目的信息
        update: function (id) {
            $("#question_update_model").modal({
                backdrop: 'static'
            });
            this.getQuestion(id)
        },
        del: function (id) {
            // if (confirm('确认删除？')) {
            // }
            $.confirm({
                confirmButton: '确认',
                cancelButton: '取消',
                title: '题目管理',
                content: '确认删除题目？',
                confirm: ()=>{
                     this.deleteQuestion(id)
                },
                cancel: function(){
                    //取消
                }
            });
        },
        multiAnswerToString:function (){
            let answers=''
            for( let i = 0 ;i < this.multiAnswer.length;i++){
                if(i === this.multiAnswer.length -1){
                    answers += this.multiAnswer[i]
                }else{
                    answers += this.multiAnswer[i]+','
                }
            }
            return answers
        },
        //将多选答案字符串分割成数组
        multiAnswerToArray:function (){
            return  this.updateQuestionInfo.queAnswer.split(',')
        },
        //清除添加对象数据
        clearNewQuestionInfo: function () {
            this.newQuestionInfo.queName = ''
            this.newQuestionInfo.queType = ''
            this.newQuestionInfo.queOption1 = ''
            this.newQuestionInfo.queOption2 = ''
            this.newQuestionInfo.queOption3 = ''
            this.newQuestionInfo.queOption4 = ''
            this.newQuestionInfo.queAnswer = ''
        },
        //根据题目id ajax请求获取试卷信息
        getQuestion: function (id) {
            axios.get('myPaperInfo/questions/' + id).then((response) => {
                if (response.data.success === true) {
                    this.updateQuestionInfo.id = response.data.element.id
                    this.updateQuestionInfo.queName = response.data.element.queName
                    this.updateQuestionInfo.queType = response.data.element.queType
                    this.updateQuestionInfo.queOption1 = response.data.element.queOption1
                    this.updateQuestionInfo.queOption2 = response.data.element.queOption2
                    this.updateQuestionInfo.queOption3 = response.data.element.queOption3
                    this.updateQuestionInfo.queOption4 = response.data.element.queOption4
                    this.updateQuestionInfo.queAnswer = response.data.element.queAnswer
                    this.multiAnswer = this.multiAnswerToArray()

                }
            })
        },
        //ajax请求添加题目接口
        addQuestion: function () {
            if (this.newQuestionInfo.queType === '3') {
                this.newQuestionInfo.queOption1 = '1'
                this.newQuestionInfo.queOption2 = '2'
            }
            if(this.newQuestionInfo.queType === '2'){
                this.newQuestionInfo.queAnswer = this.multiAnswerToString()
            }

            $.confirm({
                confirmButton: '确认',
                cancelButton: '取消',
                title: '题目管理',
                content: '确认添加题目？',
                confirm: ()=>{
                    axios.post('myPaperInfo/questions?' +
                        'queName=' + this.newQuestionInfo.queName +
                        '&queType=' + this.newQuestionInfo.queType +
                        '&queOption1=' + this.newQuestionInfo.queOption1 +
                        '&queOption2=' + this.newQuestionInfo.queOption2 +
                        '&queOption3=' + this.newQuestionInfo.queOption3 +
                        '&queOption4=' + this.newQuestionInfo.queOption4 +
                        '&queAnswer=' + this.newQuestionInfo.queAnswer)
                        .then((response) => {
                            if (response.data.success === true) {
                                $('#question_add_model').modal('hide')
                                $.tooltip(response.data.message, 2500, true);
                                if (this.pageInfo.endRow === 8) {
                                    this.getPageInfo(this.pageInfo.pages + 1)
                                } else {
                                    this.getPageInfo(this.pageInfo.pages)
                                }
                                this.clearNewQuestionInfo()
                            }
                        })
                },
                cancel: function(){
                    //取消
                }
            });
        },

        ///ajax请求修改题目接口
        updateQuestion: function () {
            if(this.updateQuestionInfo.queType === '3'){
                this.updateQuestionInfo.queOption1 = '1'
                this.updateQuestionInfo.queOption2 = '2'
                this.updateQuestionInfo.queOption3 = ''
                this.updateQuestionInfo.queOption4 = ''
            }

            $.confirm({
                confirmButton: '确认',
                cancelButton: '取消',
                title: '题目管理',
                content: '确认修改题目？',
                confirm: ()=>{
                    axios.put('myPaperInfo/questions?' +
                        'id=' + this.updateQuestionInfo.id +
                        '&queName=' + this.updateQuestionInfo.queName +
                        '&queType=' + this.updateQuestionInfo.queType +
                        '&queOption1=' + this.updateQuestionInfo.queOption1 +
                        '&queOption2=' + this.updateQuestionInfo.queOption2 +
                        '&queOption3=' + this.updateQuestionInfo.queOption3 +
                        '&queOption4=' + this.updateQuestionInfo.queOption4 +
                        '&queAnswer=' + this.updateQuestionInfo.queAnswer)
                        .then((response) => {
                            if (response.data.success === true) {
                                $.tooltip(response.data.message, 2500, true);
                                $('#question_update_model').modal('hide')
                                this.getPageInfo(this.pageInfo.pageNum)
                            }
                        })
                },
                cancel: function(){
                    //取消
                }
            });
        },

        //ajax请求删除题目byId
        deleteQuestion(id) {
            axios.delete('myPaperInfo/questions/' + id).then((response) => {
                $.tooltip(response.data.message, 2500, true);
                this.getPageInfo(this.pageInfo.pageNum)
            })
        }
    }
})