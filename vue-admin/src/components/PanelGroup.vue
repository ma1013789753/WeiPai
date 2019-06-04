<template>
  <div>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <el-badge :value="18" class="item">
          <div class="card-panel-text">提现申请</div>
          </el-badge>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="documentation" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <el-badge :value="8" class="item">
          <div class="card-panel-text">原创申请</div>
          </el-badge>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="sina2" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <el-badge :value="5" class="item">
          <div class="card-panel-text">待审微博账号</div>
          </el-badge>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="user" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <el-badge :value="18" class="item">
          <div class="card-panel-text">待审头条账号</div>
          </el-badge>
        </div>
      </div>
    </el-col>
  </el-row>
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="wechat" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <el-badge :value="88" class="item">
            <div class="card-panel-text">待审微信账号</div>
            </el-badge>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="sub" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">

            <div class="card-panel-text">待审公众号</div>

          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="feedback" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <el-badge :value="7" class="item">
            <div class="card-panel-text">意见反馈</div>
            </el-badge>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {

  methods: {

  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;
  .card-panel-col{
    margin-bottom: 32px;
  }
  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }
      .icon-people {
         background: #40c9c6;
      }
      .icon-message {
        background: #36a3f7;
      }
      .icon-money {
        background: #f4516c;
      }
      .icon-shopping {
        background: #34bfa3
      }
    }
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shopping {
      color: #34bfa3
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
        margin-right: 10px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

.item {
  margin-top: 20px;
  margin-right: 20px;
}
</style>
