let resultTable = new Vue({
    el:'#result_list',
    data:{
        pageInfo:{}
    },
    created:function (){
        this.getPageInfo(1)
    },
    methods:{
        getPageInfo:function (pageNum){
            axios.get('myResults?pageNum='+ pageNum).then((response)=>{
                this.pageInfo = response.data.element
            })
        }
    }
})