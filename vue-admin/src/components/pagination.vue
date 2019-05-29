<template>
    <div class="pagination">
        <el-pagination  small background class="text-center"  @size-change="handleSizeChange" @current-change="handleCurrentChange"
                                :current-page="page.current" :page-sizes="pageSizes" :page-size="page.size"
                                layout="total, sizes, prev, pager, next" :total="total">
        </el-pagination>
    </div>
</template>

<script>
export default {
    props: {
        total: {
            type: Number
        } // 总条数
    },
    data() {
        return {
            pageSizes: [10, 20, 50, 100],
            page: {
                current: 1,//当前页
                size:20, //页条目 默认10个
                search1:"",
                search2:"",
                search3:"",
                search4:"",
            }
        };
    },

    created(){
        this.parentHandleclick(this.page);
    },
    methods: {
        // 每页条数变更
        handleSizeChange(val) {
            this.page.size = val;
            this.$emit('pageChange', this.page);
        },
        // 当前页码变更
        handleCurrentChange(val) {
            this.page.current = val;
            this.$emit('pageChange', this.page);
        },

        parentHandleclick(val){
            this.page.search1 = val.search1;
            this.page.search2 = val.search2;
            this.page.search3 = val.search3;
            this.page.current = 1;
            this.$emit('pageChange', this.page);
        }
    }
}
</script>
<style>
.pagination {
    margin: 20px 0;
    width: 100%;
    margin-top: 10px;
    text-align: right
}
</style>
