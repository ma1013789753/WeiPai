<template>
  <div>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <router-link to="/flow/cash">
          <el-badge :value="this.withdrawCount" class="item" v-if="this.withdrawCount != 0">
          <div class="card-panel-text">提现申请</div>
          </el-badge>
            <el-badge class="item" v-else>
              <div class="card-panel-text">提现申请</div>
            </el-badge>
          </router-link>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="documentation" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <router-link to="/spare/sparelist/list">
          <el-badge :value="this.originalShareCount" class="item" v-if="this.originalShareCount != 0">
          <div class="card-panel-text">原创申请</div>
          </el-badge>
            <el-badge class="item" v-else>
              <div class="card-panel-text">原创申请</div>
            </el-badge>
          </router-link>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="sina2" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <router-link to="/enduser/weiboacc">
          <el-badge :value="this.sinaApplyCount" class="item" v-if="this.sinaApplyCount != 0">
          <div class="card-panel-text">待审微博账号</div>
          </el-badge>
            <el-badge class="item" v-else>
              <div class="card-panel-text">待审微博账号</div>
            </el-badge>
          </router-link>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="user" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <router-link to="/enduser/toutiaoacc">
          <el-badge :value="this.toutiaoApplyCount" class="item" v-if="this.toutiaoApplyCount != 0">
          <div class="card-panel-text">待审头条账号</div>
          </el-badge>
            <el-badge  class="item" v-else>
              <div class="card-panel-text">待审头条账号</div>
            </el-badge>
          </router-link>
        </div>
      </div>
    </el-col>
  </el-row>
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="wechat2" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <router-link to="/enduser/weichatacc">
            <el-badge :value="this.wechartApplyCount" class="item" v-if="this.wechartApplyCount != 0">
            <div class="card-panel-text">待审微信账号</div>
            </el-badge>
              <el-badge class="item" v-else>
                <div class="card-panel-text">待审微信账号</div>
              </el-badge>
            </router-link>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="sub" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <router-link to="/enduser/subscription">
              <el-badge :value="this.subscriptionApplyCount" class="item" v-if="this.subscriptionApplyCount != 0">
              <div class="card-panel-text">待审公众号</div>
              </el-badge>
              <el-badge class="item" v-else>
                <div class="card-panel-text">待审公众号</div>
              </el-badge>
            </router-link>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="feedback" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <router-link to="/message/feedback">
            <el-badge :value="this.pendingFeedback" class="item" v-if="this.pendingFeedback != 0">
            <div class="card-panel-text">意见反馈</div>
            </el-badge>
              <el-badge  class="item" v-else>
                <div class="card-panel-text">意见反馈</div>
              </el-badge>
            </router-link>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {withdrawCount,originalShareCount,platformAccCount,getPendingFeedback} from '@/api/notification'

export default {
  data() {
    return {
      withdrawCount:0,
      originalShareCount:0,
      sinaApplyCount:0,
      toutiaoApplyCount:0,
      wechartApplyCount:0,
      subscriptionApplyCount:0,
      pendingFeedback:0
    }
  },
  created(){
    this.initNotice()
  },
  methods: {
    initNotice(){
      this.initWithdrawCount();
      this.initOriginalShareCount();
      this.initSinaApplyCount();
      this.initSubscriptionApplyCount();
      this.initWechartApplyCount();
      this.initToutiaoApplyCount();
      this.initPendingFeedback();
    },
    initWithdrawCount(){
      withdrawCount().then(res => {
       this.withdrawCount = res.data;
      })
    },
    initOriginalShareCount(){
      originalShareCount().then(res => {
        this.originalShareCount = res.data;
      })
    },
    initPendingFeedback(){
      getPendingFeedback().then(res => {
        this.pendingFeedback = res.data;
      })
    },
    initSinaApplyCount(){
      platformAccCount(0).then(res => {
        this.sinaApplyCount = res.data;
      })
    },
    initSubscriptionApplyCount(){
      platformAccCount(1).then(res => {
        this.subscriptionApplyCount = res.data;
      })
    },
    initWechartApplyCount(){
      platformAccCount(2).then(res => {
        this.wechartApplyCount = res.data;
      })
    },
    initToutiaoApplyCount(){
      platformAccCount(3).then(res => {
        this.toutiaoApplyCount = res.data;
      })
    },
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
