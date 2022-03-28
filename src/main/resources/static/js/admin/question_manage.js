let questionTable = new Vue({
    el: '#question_list_table',
    data: {
        pageInfo: {},
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
        paperList: [],
        multiAnswer: [],
        queNameTemp:'',
        //新增题目名称校验变量
        newQueNameHasError: false,
        newQueNameErrorMsg: '',
        //新增题目类型校验变量
        newQueTypeHasError: false,
        newQueTypeErrorMsg: '',
        //新增题目选项内容校验变量
        newQueOptionHasError: false,
        newQueOptionErrorMsg: '',
        //新增单选答案选择框校验变量
        newQueSingleAnswerHasError: false,
        newQueSingleAnswerErrorMsg: '',
        //新增多选答案选择框校验变量
        newQueMultiAnswerHasError: false,
        newQueMultiAnswerErrorMsg: '',
        //新增判断答案选择框校验变量
        newQueJudgeAnswerHasError: false,
        newQueJudgeAnswerErrorMsg: '',
        //新增所属试卷选择框校验变量
        newQuePaperHasError: false,
        newQuePaperErrorMsg: '',

        //修改题目名称校验变量
        updateQueNameHasError: false,
        updateQueNameErrorMsg: '',
        //修改题目类型校验变量
        updateQueTypeHasError: false,
        updateQueTypeErrorMsg: '',
        //修改题目选项内容校验变量
        updateQueOptionHasError: false,
        updateQueOptionErrorMsg: '',
        //修改单选答案选择框校验变量
        updateQueSingleAnswerHasError: false,
        updateQueSingleAnswerErrorMsg: '',
        //修改多选答案选择框校验变量
        updateQueMultiAnswerHasError: false,
        updateQueMultiAnswerErrorMsg: '',
        //修改判断答案选择框校验变量
        updateQueJudgeAnswerHasError: false,
        updateQueJudgeAnswerErrorMsg: '',
    },
    //获取试卷列表分页数据
    created: function () {
        this.getPageInfo(1)
    },
    methods: {
        //关闭新增模态框
        closeAddModal: function () {
            $('#question_add_model').modal('hide')
            this.clearNewModalStatus()
        },
        //关闭后清除新增模态框的所有输入数据以及校验状态
        clearNewModalStatus: function () {
            this.newQuestionInfo.queName = ''
            this.newQuestionInfo.queType = ''
            this.newQuestionInfo.queOption1 = ''
            this.newQuestionInfo.queOption2 = ''
            this.newQuestionInfo.queOption3 = ''
            this.newQuestionInfo.queOption4 = ''
            this.newQuestionInfo.queAnswer = ''
            this.newQuestionInfo.quePaper = ''
            this.newQueNameErrorMsg = ''
            this.newQueNameHasError = false
            this.newQueTypeErrorMsg = ''
            this.newQueTypeHasError = false
            this.newQueOptionErrorMsg = ''
            this.newQueOptionHasError = false
            this.newQueSingleAnswerErrorMsg = ''
            this.newQueSingleAnswerHasError = false
            this.newQueMultiAnswerErrorMsg = ''
            this.newQueMultiAnswerHasError = false
            this.newQueJudgeAnswerErrorMsg = ''
            this.newQueJudgeAnswerHasError = false
            this.newQuePaperErrorMsg = ''
            this.newQuePaperHasError = false
            this.multiAnswer.length = 0
        },
        //关闭后清除修改模态框的所有输入数据以及校验状态
        clearUpdateModalStatus: function () {
            this.updateQueNameErrorMsg = ''
            this.updateQueNameHasError = false
            this.updateQueOptionErrorMsg = ''
            this.updateQueOptionHasError = false
            this.updateQueSingleAnswerErrorMsg = ''
            this.updateQueSingleAnswerHasError = false
            this.updateQueMultiAnswerErrorMsg = ''
            this.updateQueMultiAnswerHasError = false
            this.updateQueJudgeAnswerErrorMsg = ''
            this.updateQueJudgeAnswerHasError = false
            this.multiAnswer.length = 0
        },
        //关闭修改模态框
        closeUpdateModal: function () {
            $('#question_update_model').modal('hide')
            this.clearUpdateModalStatus()
        },
        //点击添加按钮打开题目添加模态框，并获取试卷列表
        add: function () {
            $("#question_add_model").modal({
                backdrop: 'static'
            });
            this.multiAnswer = []
            this.getPaperList()
        },

        //点击修改按钮打开修改题目信息模态框，获取试卷列表并回显该题目的信息
        update: function (id) {
            $("#question_update_model").modal({
                backdrop: 'static'
            });
            this.getPaperList()
            this.getQuestion(id)
        },

        del: function (id) {
            if (confirm('确认删除？')) {
                this.deleteQuestion(id)
            }

        },

        //根据页码获取题目列表数据
        getPageInfo: function (pageNum) {
            axios.get('questionInfo/operate?pageNum=' + pageNum).then((response) => {
                if (response.data.element.list.length === 0) {
                    this.getPageInfo(response.data.element.pageNum - 1)
                }
                this.pageInfo = response.data.element
            })
        },

        //获取试卷列表
        getPaperList: function () {
            axios.get('paperInfo/allPaper').then((response) => {
                this.paperList = response.data.element
            })
        },

        //将多选题复选框提交的数组拼接成字符串保存
        multiAnswerToString: function () {
            let answers = ''
            for (let i = 0; i < this.multiAnswer.length; i++) {
                if (i === this.multiAnswer.length - 1) {
                    answers += this.multiAnswer[i]
                } else {
                    answers += this.multiAnswer[i] + ','
                }
            }
            return answers
        },

        //将多选答案字符串分割成数组
        multiAnswerToArray: function () {
            return this.updateQuestionInfo.queAnswer.split(',')
        },


        //根据题目id ajax请求获取试卷信息
        getQuestion: function (id) {
            axios.get('questionInfo/operate/' + id).then((response) => {
                if (response.data.success === true) {
                    this.updateQuestionInfo.id = response.data.element.id
                    this.updateQuestionInfo.queName = response.data.element.queName
                    this.updateQuestionInfo.queType = response.data.element.queType
                    this.updateQuestionInfo.queOption1 = response.data.element.queOption1
                    this.updateQuestionInfo.queOption2 = response.data.element.queOption2
                    this.updateQuestionInfo.queOption3 = response.data.element.queOption3
                    this.updateQuestionInfo.queOption4 = response.data.element.queOption4
                    this.updateQuestionInfo.queAnswer = response.data.element.queAnswer
                    this.updateQuestionInfo.quePaper = response.data.element.quePaper
                    this.queNameTemp = response.data.element.queName
                    this.multiAnswer = this.multiAnswerToArray()

                }
            })
        },
        //ajax请求添加题目接口
        addQuestion: function () {
            if (this.checkNewQueName() && this.checkNewQueType()) {
                if (this.newQuestionInfo.queType === '1' && this.checkNewQueOption()
                    && this.checkNewQueSingleAnswer() && this.checkNewQuePaper() ||
                    this.newQuestionInfo.queType === '2' && this.checkNewQueOption()
                    && this.checkNewQueMultiAnswer() && this.checkNewQuePaper() && this.checkNewQueOption() ||
                    this.newQuestionInfo.queType === '3' && this.checkNewQueJudgeAnswer() && this.checkNewQuePaper()) {
                    if (this.newQuestionInfo.queType === '3') {
                        this.newQuestionInfo.queOption1 = '1'
                        this.newQuestionInfo.queOption2 = '2'
                    }
                    if (this.newQuestionInfo.queType === '2') {
                        this.newQuestionInfo.queAnswer = this.multiAnswerToString()
                    }
                    this.newQuestionInfo.queName = this.newQuestionInfo.queName.replaceAll(' ', '')
                    let value = this.$refs['newQueNameRef'].value;
                    axios.get('questionInfo/checkQueName?queName=' + value).then((response) => {
                        if(response.data.success){
                            axios.post('questionInfo/operate?' +
                                'queName=' + this.newQuestionInfo.queName +
                                '&queType=' + this.newQuestionInfo.queType +
                                '&queOption1=' + this.newQuestionInfo.queOption1 +
                                '&queOption2=' + this.newQuestionInfo.queOption2 +
                                '&queOption3=' + this.newQuestionInfo.queOption3 +
                                '&queOption4=' + this.newQuestionInfo.queOption4 +
                                '&queAnswer=' + this.newQuestionInfo.queAnswer +
                                '&quePaper=' + this.newQuestionInfo.quePaper)
                                .then((response) => {
                                    if (response.data.success === true) {
                                        $('#question_add_model').modal('hide')
                                        if (this.pageInfo.endRow === 8 && this.pageInfo.isLastPage) {
                                            this.getPageInfo(this.pageInfo.pages + 1)
                                        } else {
                                            this.getPageInfo(this.pageInfo.pages)
                                        }
                                        $.tooltip(response.data.message, 2500, true);
                                        this.clearNewQuestionInfo()
                                    }
                                })
                        }else{
                            this.newQueNameHasError = true
                            this.newQueNameErrorMsg = '题目已存在！'
                        }
                    });
                }

            }
        },

        ///ajax请求修改题目接口
        updateAxios:function (){
            axios.put('questionInfo/operate?' +
                'id=' + this.updateQuestionInfo.id +
                '&queName=' + this.updateQuestionInfo.queName +
                '&queType=' + this.updateQuestionInfo.queType +
                '&queOption1=' + this.updateQuestionInfo.queOption1 +
                '&queOption2=' + this.updateQuestionInfo.queOption2 +
                '&queOption3=' + this.updateQuestionInfo.queOption3 +
                '&queOption4=' + this.updateQuestionInfo.queOption4 +
                '&queAnswer=' + this.updateQuestionInfo.queAnswer +
                '&quePaper=' + this.updateQuestionInfo.quePaper)
                .then((response) => {
                    if (response.data.success === true) {
                        $('#question_update_model').modal('hide')
                        $.tooltip(response.data.message, 2500, true);
                        this.getPageInfo(this.pageInfo.pageNum)
                    }
                })
        },
        updateQuestion: function () {
            if (this.checkUpdateQueName() && this.checkUpdateQueOption()) {
                if (this.updateQuestionInfo.queType === '1' && this.checkUpdateQueSingleAnswer() ||
                    this.updateQuestionInfo.queType === '2' && this.checkUpdateQueMultiAnswer() ||
                    this.updateQuestionInfo.queType === '3' && this.checkUpdateQueJudgeAnswer()) {
                    this.updateQuestionInfo.queName = this.updateQuestionInfo.queName.replaceAll(' ', '')
                    let value = this.$refs['updateQueNameRef'].value;
                    if(value.toUpperCase() === this.queNameTemp.toUpperCase()){
                        this.updateAxios()
                    }else{
                        axios.get('questionInfo/checkQueName?queName=' + value).then((response) => {
                            if(response.data.success){
                                this.updateAxios()
                            }else{
                                this.updateQueNameHasError = true
                                this.updateQueNameErrorMsg = '题目已存在！'
                            }
                        })
                    }

                }
            }

        },
        //ajax请求删除题目byId
        deleteQuestion(id) {
            axios.delete('questionInfo/operate/' + id).then((response) => {
                if (this.pageInfo.list.length === 0) {
                    this.getPageInfo(this.pageInfo.pageNum - 1)
                }
                this.getPageInfo(this.pageInfo.pageNum)
                $.tooltip(response.data.message, 2500, true);
            })
        },
        //新增题目名称输入框校验
        checkNewQueName: function () {
            if (this.$refs['newQueNameRef'].value === '') {
                this.newQueNameHasError = true
                this.newQueNameErrorMsg = '题目不能为空'
                return false
            }
            this.newQueNameHasError = false
            this.newQueNameErrorMsg = ''
            return true
        },
        //新增题目类型选择框校验
        checkNewQueType: function () {
            if (!this.$refs['newQueType1Ref'].checked
                && !this.$refs['newQueType2Ref'].checked
                && !this.$refs['newQueType3Ref'].checked) {
                this.newQueTypeHasError = true
                this.newQueTypeErrorMsg = '请选择题目类型'
                return false
            }
            this.newQueTypeHasError = false
            this.newQueTypeErrorMsg = ''
            return true
        },
        //新增题目选项输入框校验
        checkNewQueOption: function () {
            if (this.$refs['newQueOption1Ref'].value === ''
                || this.$refs['newQueOption2Ref'].value === ''
                || this.$refs['newQueOption3Ref'].value === ''
                || this.$refs['newQueOption4Ref'].value === '') {
                this.newQueOptionHasError = true
                this.newQueOptionErrorMsg = '请输入完整的选项答案！'
                return false
            }
            this.newQueOptionHasError = false
            this.newQueOptionErrorMsg = ''
            return true
        },
        //新增单选答案选择框校验
        checkNewQueSingleAnswer: function () {
            if (!this.$refs['newQueSingleAnswerARef'].checked
                && !this.$refs['newQueSingleAnswerBRef'].checked
                && !this.$refs['newQueSingleAnswerCRef'].checked
                && !this.$refs['newQueSingleAnswerDRef'].checked) {
                this.newQueSingleAnswerHasError = true
                this.newQueSingleAnswerErrorMsg = '请选择题目正确答案'
                return false
            }
            this.newQueSingleAnswerHasError = false
            this.newQueSingleAnswerErrorMsg = ''
            return true
        },
        //新增多选答案选择框校验
        checkNewQueMultiAnswer: function () {
            if (this.multiAnswer.length <= 1) {
                this.newQueMultiAnswerHasError = true
                this.newQueMultiAnswerErrorMsg = '请选择至少两个答案！'
                return false
            }
            this.newQueMultiAnswerHasError = false
            this.newQueMultiAnswerErrorMsg = ''
            return true
        },
        //新增判断答案选择框校验
        checkNewQueJudgeAnswer: function () {
            if (!this.$refs['newQueJudgeAnswer1Ref'].checked &&
                !this.$refs['newQueJudgeAnswer2Ref'].checked) {
                this.newQueJudgeAnswerHasError = true
                this.newQueJudgeAnswerErrorMsg = '请选择正确答案！'
                return false
            }
            this.newQueJudgeAnswerHasError = false
            this.newQueJudgeAnswerErrorMsg = ''
            return true
        },
        //新增判断答案选择框校验
        checkNewQuePaper: function () {
            if (this.$refs['newQuePaperRef'].value === '') {
                this.newQuePaperHasError = true
                this.newQuePaperErrorMsg = '请选择题目所属试卷！'
                return false
            }
            this.newQuePaperHasError = false
            this.newQuePaperErrorMsg = ''
            return true
        },
        //修改题目名称输入框校验
        checkUpdateQueName: function () {
            if (this.$refs['updateQueNameRef'].value === '') {
                this.updateQueNameHasError = true
                this.updateQueNameErrorMsg = '题目不能为空'
                return false
            }
            this.updateQueNameHasError = false
            this.updateQueNameErrorMsg = ''
            return true
        },
        //修改题目选项输入框校验
        checkUpdateQueOption: function () {
            if (this.$refs['updateQueOption1Ref'].value === ''
                || this.$refs['updateQueOption2Ref'].value === ''
                || this.$refs['updateQueOption3Ref'].value === ''
                || this.$refs['updateQueOption4Ref'].value === '') {
                this.updateQueOptionHasError = true
                this.updateQueOptionErrorMsg = '请输入完整的选项答案！'
                return false
            }
            this.updateQueOptionHasError = false
            this.updateQueOptionErrorMsg = ''
            return true
        },
        //修改单选答案选择框校验
        checkUpdateQueSingleAnswer: function () {
            if (!this.$refs['updateQueSingleAnswerARef'].checked
                && !this.$refs['updateQueSingleAnswerBRef'].checked
                && !this.$refs['updateQueSingleAnswerCRef'].checked
                && !this.$refs['updateQueSingleAnswerDRef'].checked) {
                this.updateQueSingleAnswerHasError = true
                this.updateQueSingleAnswerErrorMsg = '请选择题目正确答案'
                return false
            }
            this.updateQueSingleAnswerHasError = false
            this.updateQueSingleAnswerErrorMsg = ''
            return true
        },
        //修改多选答案选择框校验
        checkUpdateQueMultiAnswer: function () {
            if (this.multiAnswer.length <= 1) {
                this.updateQueMultiAnswerHasError = true
                this.updateQueMultiAnswerErrorMsg = '请选择至少两个答案！'
                return false
            }
            this.updateQueMultiAnswerHasError = false
            this.updateQueMultiAnswerErrorMsg = ''
            return true
        },
        //修改判断答案选择框校验
        checkUpdateQueJudgeAnswer: function () {
            if (!this.$refs['updateQueJudgeAnswer1Ref'].checked &&
                !this.$refs['updateQueJudgeAnswer2Ref'].checked) {
                this.updateQueJudgeAnswerHasError = true
                this.updateQueJudgeAnswerErrorMsg = '请选择正确答案！'
                return false
            }
            this.updateQueJudgeAnswerHasError = false
            this.updateQueJudgeAnswerErrorMsg = ''
            return true
        },
    }
});