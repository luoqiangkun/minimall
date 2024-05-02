<template>
    <div class="page">
        <section>
            <div class="header">订单信息</div>
            <div class="body">
                <el-row>
                    <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                        <div class="field">
                            <label>订单号：</label>
                            <div class="text">{{orderDetail.order.orderSn}}</div>
                        </div>
                    </el-col>
                    <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                         <div class="field">
                            <label>下单时间：</label>
                            <div class="text">{{orderDetail.order.addTime}}</div>
                        </div>
                    </el-col>
                    <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                         <div class="field">
                            <label>下单用户：</label>
                            <div class="text">{{orderDetail.user.nickname}}</div>
                        </div>
                    </el-col>
                   <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                         <div class="field">
                            <label>用户留言：</label>
                            <div class="text">{{orderDetail.order.message}}</div>
                        </div>
                    </el-col>
                </el-row>
            </div>
        </section>

         <section>
            <div class="header">状态信息</div>
            <div class="body">
                <el-row>
                   <div class="field">
                        <label>订单状态：</label>
                        <div class="text"> 
                            <el-button type="primary" plain>{{ orderDetail.order.orderStatus | orderStatusFilter }}</el-button>
                        </div>
                    </div>
                </el-row>
                <el-row>
                    <el-button type="primary" @click="handlePay">{{ $t('mall_order.button.pay') }}</el-button>
                    <template v-if="!orderDetail.order.shipType">
                        <el-button type="primary" @click="handleShip">快递发货</el-button>
                        <el-button type="primary" @click="deliveryDialogVisible = true">同城配送</el-button>
                    </template>
                    <template v-if="orderDetail.order.shipStatus == 1">
                        <el-button type="primary" @click="handleReceive">确认送达</el-button>
                    </template>
                    <el-button type="primary" @click="handleRefund">{{ $t('mall_order.button.refund') }}</el-button>
                </el-row>
            </div>
        </section>

        <section>
            <div class="header">配送信息</div>
            <div class="body">
                <el-row>
                    <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6" v-if="orderDetail.order.shipType">
                        <div class="field">
                            <label>配送方式：</label>
                            <div class="text" v-if="orderDetail.order.shipType == 1">快递配送</div>
                            <div class="text" v-else-if="orderDetail.order.shipType == 2">同城配送</div>
                            <div class="text" v-else-if="orderDetail.order.shipType == 3">用户自提</div>
                        </div>
                    </el-col>
                    <template v-if="orderDetail.order.shipType != 3">
                          <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                            <div class="field">
                                <label>收货人：</label>
                                <div class="text">{{orderDetail.order.consignee}}</div>
                            </div>
                        </el-col>
                        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                            <div class="field">
                                <label>联系方式：</label>
                                <div class="text">{{orderDetail.order.mobile}}</div>
                            </div>
                        </el-col>
                        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                            <div class="field">
                                <label>收货地址：</label>
                                <div class="text">{{orderDetail.order.address}}</div>
                            </div>
                        </el-col>
                    </template>
                    <template v-if="orderDetail.order.shipType == 2">
                         <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                            <div class="field">
                                <label>配送人：</label>
                                <div class="text">{{orderDetail.order.deliveryPerson}}</div>
                            </div>
                        </el-col>
                        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                            <div class="field">
                                <label>联系方式：</label>
                                <div class="text">{{orderDetail.order.deliveryMobile}}</div>
                            </div>
                        </el-col>
                        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6">
                            <div class="field">
                                <label>配送时间：</label>
                                <div class="text">{{orderDetail.order.deliveryTime}}</div>
                            </div>
                        </el-col>
                    </template>

                    <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="6" v-if="orderDetail.order.shipType">
                        <div class="field" v-if="orderDetail.order.shipType == 3">
                            <div class="text"  v-if="orderDetail.order.shipStatus == 1">用户未自提</div>
                            <div class="text" v-else>用户已自提</div>
                        </div>
                         <div class="field" v-else>
                            <label>配送状态：</label>
                            <div class="text" v-if="orderDetail.order.shipStatus == 1">等待配送</div>
                            <div class="text" v-else-if="orderDetail.order.shipStatus == 2">配送中</div>
                            <div class="text" v-else-if="orderDetail.order.shipStatus == 3">已配送</div>
                        </div>
                    </el-col>

                </el-row>
            </div>
        </section>

        <section>
            <div class="header">商品信息</div>
            <div class="body">
                <el-row>
                    <el-table :data="orderDetail.orderGoods" border fit highlight-current-row>
                        <el-table-column align="center" :label="$t('mall_order.table.detail_goods_name')" prop="goodsName" />
                        <el-table-column align="center" :label="$t('mall_order.table.detail_goods_sn')" prop="goodsSn" />
                        <el-table-column align="center" :label="$t('mall_order.table.detail_goods_specifications')" prop="specifications" />
                        <el-table-column align="center" :label="$t('mall_order.table.detail_goods_price')" prop="price" />
                        <el-table-column align="center" :label="$t('mall_order.table.detail_goods_number')" prop="number" />
                        <el-table-column align="center" :label="$t('mall_order.table.detail_goods_pic_url')" prop="picUrl">
                            <template slot-scope="scope">
                            <img :src="scope.row.picUrl" width="40">
                            </template>
                        </el-table-column>
                    </el-table>
                </el-row>
                <el-row>
                    <div class="price-total">
                     {{ $t('mall_order.text.detail_price_info', {
                actual_price: orderDetail.order.actualPrice,
                goods_price: orderDetail.order.goodsPrice,
                freight_price: orderDetail.order.freightPrice,
                coupon_price: orderDetail.order.couponPrice,
                integral_price: orderDetail.order.integralPrice
              }) }}
                    </div>
                </el-row>
            </div>
        </section>

        
        <section>
            <div class="header"></div>
            <div class="body"></div>
        </section>

          <!-- 收款对话框 -->
    <el-dialog :visible.sync="payDialogVisible" :title="$t('mall_order.dialog.pay')" width="40%" center>
      <el-form ref="payForm" :model="payForm" status-icon label-position="left" label-width="100px">
        <div style="margin-bottom: 10px;">
          {{ $t('mall_order.message.pay_confirm', { order_sn: payForm.orderSn }) }}
        </div>
        <el-form-item :label="$t('mall_order.form.pay_old_money')" prop="oldMoney">
          <el-input-number v-model="payForm.oldMoney" :controls="false" disabled />
        </el-form-item>
        <el-form-item :label="$t('mall_order.form.pay_new_money')" prop="newMoney">
          <el-input-number v-model="payForm.newMoney" :controls="false" />
        </el-form-item>
      </el-form>
      <el-table :data="payForm.goodsList">
        <el-table-column property="goodsName" :label="$t('mall_order.table.pay_goods_name')" />
        <el-table-column :label="$t('mall_order.table.pay_goods_specifications')">
          <template slot-scope="scope">
            {{ scope.row.specifications.join('-') }}
          </template>
        </el-table-column>
        <el-table-column property="onumber" width="100" :label="$t('mall_order.table.pay_goods_number')" />
        <!-- <el-table-column label="实际数量" width="100">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.number" :min="0" :controls="false" />
          </template>
        </el-table-column> -->
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="payDialogVisible = false">{{ $t('app.button.cancel') }}</el-button>
        <el-button type="primary" @click="confirmPay">{{ $t('app.button.confirm') }}</el-button>
      </div>
    </el-dialog>

    <!-- 发货对话框 -->
    <el-dialog :visible.sync="shipDialogVisible" :title="$t('mall_order.dialog.ship')" width="570px">
      <el-form ref="shipForm" size="small" :model="shipForm" status-icon label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('mall_order.form.ship_channel')" prop="shipChannel">
          <el-select v-model="shipForm.shipChannel" :placeholder="$t('mall_order.placeholder.ship_channel')" style="width:100%;">
            <el-option v-for="item in channels" :key="item.code" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('mall_order.form.ship_sn')" prop="shipSn">
          <el-input v-model="shipForm.shipSn" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="shipDialogVisible = false">{{ $t('app.button.cancel') }}</el-button>
        <el-button type="primary" @click="confirmShip">{{ $t('app.button.confirm') }}</el-button>
      </div>
    </el-dialog>

    <!-- 退款对话框 -->
    <el-dialog :visible.sync="refundDialogVisible" :title="$t('mall_order.dialog.refund')">
      <el-form ref="refundForm" :model="refundForm" status-icon label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('mall_order.form.refund_money')" prop="refundMoney">
          <el-input v-model="refundForm.refundMoney" :disabled="true" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="refundDialogVisible = false">{{ $t('app.button.cancel') }}</el-button>
        <el-button type="primary" @click="confirmRefund">{{ $t('app.button.confirm') }}</el-button>
      </div>
    </el-dialog>

    <!-- 同城配送对话框 -->
    <el-dialog :visible.sync="deliveryDialogVisible" :title="$t('mall_order.dialog.delivery')" width="570px">
      <el-form ref="deliveryForm" :model="deliveryForm" size="small" label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="配送人" prop="deliveryPerson" :rules="[{ required: true, trigger: 'blur', message: '请输入配送人'}]">
          <el-input v-model="deliveryForm.deliveryPerson" />
        </el-form-item>
        <el-form-item label="联系方式" prop="deliveryMobile" :rules="[{ required: true,validator: checkPhone, trigger: 'blur'}]">
          <el-input v-model="deliveryForm.deliveryMobile" />
        </el-form-item>
        <el-form-item label="配送时间" prop="deliveryTime" :rules="[{ required: true, message: '请选择配送时间', trigger: 'change'}]">
          <el-date-picker
            v-model="deliveryForm.deliveryTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%;"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="deliveryDialogVisible = false">{{ $t('app.button.cancel') }}</el-button>
        <el-button type="primary" @click="handleDelivery">{{ $t('app.button.confirm') }}</el-button>
      </div>
    </el-dialog>

    <!-- 确认收货 -->
    <el-dialog :visible.sync="receiveDialogVisible" :title="$t('mall_order.dialog.tip')" width="30%">
      <span>{{}}</span>
      <div slot="footer" class="dialog-footer">
        <el-button @click="receiveDialogVisible = false">{{ $t('app.button.cancel') }}</el-button>
        <el-button type="primary" @click="handleReceive">{{ $t('app.button.confirm') }}</el-button>
      </div>
    </el-dialog>

    </div>
</template>
<script>
import { listChannel, detailOrder, refundOrder, payOrder, shipOrder, deliveryOrder, receiveOrder } from '@/api/order'
import checkPermission from '@/utils/permission' // 权限判断函数

const statusMap = {
  101: '未付款',
  102: '用户取消',
  103: '系统取消',
  201: '已付款',
  202: '申请退款',
  203: '已退款',
  301: '已发货',
  401: '用户收货',
  402: '系统收货'
}

export default {
  name: 'OrderDetail',
  filters: {
    orderStatusFilter(status) {
      return statusMap[status]
    }
  },
  data() {
    return {
      statusMap,
      orderDetail: {
        order: {},
        user: {},
        orderGoods: []
      },
      shipForm: {
        orderId: undefined,
        shipChannel: undefined,
        shipSn: undefined
      },
      shipDialogVisible: false,
      payForm: {
        orderId: undefined,
        orderSn: '',
        oldMoney: 0,
        newMoney: 0,
        goodsList: []
      },
      payDialogVisible: false,
      refundForm: {
        orderId: undefined,
        refundMoney: undefined
      },
      refundDialogVisible: false,
      downloadLoading: false,
      channels: [],
      deliveryDialogVisible: false,
      deliveryForm: {
        orderSn: '',
        deliveryPerson: '',
        deliveryPerson: '',
        deliveryTime: ''
      },
      checkPhone: (rule, value, callback) => { // 手机号验证
        if (!value) {
          return callback(new Error('手机号不能为空'))
        } else {
          const reg = /^1[3456789]\d{9}$/
          if (reg.test(value)) {
            callback()
          } else {
            return callback(new Error('请输入正确的手机号'))
          }
        }
      },
      receiveDialogVisible: false
    }
  },
  created() {
    if(this.$route.params.id){
        this.getDetail( this.$route.params.id )
        this.getChannel()
    }
  },
  methods: {
    checkPermission,
    getDetail(id) {
      detailOrder(id).then(response => {
        this.orderDetail = response.data.data
      })
    },
    getChannel() {
      listChannel().then(response => {
        this.channels = response.data.data
      })
    },
    handlePay() {
      let row = this.orderDetail.order  
      this.payForm.orderId = row.id
      this.payForm.orderSn = row.orderSn
      this.payForm.oldMoney = row.actualPrice
      this.payForm.newMoney = row.actualPrice
      this.payForm.goodsList = row.goodsVoList
      this.payForm.goodsList.forEach(element => {
        element.onumber = element.number
      })
      this.payDialogVisible = true
    },
    confirmPay() {
      if (this.payForm.oldMoney !== this.payForm.newMoney) {
        const diff = this.payForm.newMoney - this.payForm.oldMoney
        this.$confirm('差额 ' + diff + '元， 是否确认提交')
          .then(_ => {
            this.confirmPay2()
          })
          .catch(_ => {})
      } else {
        this.confirmPay2()
      }
    },
    confirmPay2() {
      payOrder(this.payForm).then(response => {
        this.$notify.success({
          title: '成功',
          message: '订单收款操作成功'
        })
        this.getList()
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.data.errmsg
        })
      }).finally(() => {
        this.payDialogVisible = false
      })
    },
    handleShip(row) {
      this.shipForm.orderId = row.id
      this.shipForm.shipChannel = row.shipChannel
      this.shipForm.shipSn = row.shipSn

      this.shipDialogVisible = true
      this.$nextTick(() => {
        this.$refs['shipForm'].clearValidate()
      })
    },
    confirmShip() {
      this.$refs['shipForm'].validate((valid) => {
        if (valid) {
          shipOrder(this.shipForm).then(response => {
            this.shipDialogVisible = false
            this.$notify.success({
              title: '成功',
              message: '确认发货成功'
            })
            this.getList()
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
        }
      })
    },
    handleRefund(row) {
      this.refundForm.orderId = row.id
      this.refundForm.refundMoney = row.actualPrice

      this.refundDialogVisible = true
      this.$nextTick(() => {
        this.$refs['refundForm'].clearValidate()
      })
    },
    confirmRefund() {
      this.$refs['refundForm'].validate((valid) => {
        if (valid) {
          refundOrder(this.refundForm).then(response => {
            this.refundDialogVisible = false
            this.$notify.success({
              title: '成功',
              message: '确认退款成功'
            })
            this.getList()
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
        }
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['订单ID', '订单编号', '用户ID', '订单状态', '是否删除', '收货人', '收货联系电话', '收货地址']
        const filterVal = ['id', 'orderSn', 'userId', 'orderStatus', 'isDelete', 'consignee', 'mobile', 'address']
        excel.export_json_to_excel2(tHeader, this.list, filterVal, '订单信息')
        this.downloadLoading = false
      })
    },
    printOrder() {
      this.$print(this.$refs.print)
      this.orderDialogVisible = false
    },
    handleDelivery() {
      deliveryOrder(Object.assign({},  this.deliveryForm, {orderSn: this.orderDetail.order.orderSn})).then(response => {
        this.deliveryDialogVisible = false
        this.$notify.success({
          title: '成功',
          message: '操作成功'
        })
        this.getList()
      }).catch(response => {
        this.$notify.error({
          title: '失败',
          message: response.data.errmsg
        })
      })
    },
    handleReceive() {
      const message = this.orderDetail.order.ship_type == 2 ? '确认用户已收到商品了吗？' : '确认用户已取到商品了吗？'
      this.$confirm(message)
        .then( ()=> {
          this.receiveDialogVisible = false
          receiveOrder(this.orderDetail.order.orderSn).then(response => {
            this.$notify.success({
              title: '成功',
              message: '操作成功'
            })

            this.getList()
          }).catch(response => {
            this.$notify.error({
              title: '失败',
              message: response.data.errmsg
            })
          })
        })
        .catch(_ => {})
    }
  }
}
</script>
<style lang="scss" scoped>
.page {
    padding: 20px;
    font-size: 14px;
}
.field {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}
.field label {
    font-weight: 300;
}
section {
    margin-bottom: 15px;
    .header {
        font-weight: 600;
    }
    .body {
        padding: 15px 0;
    }
}
.price-total {
    margin-top: 10px;
    text-align: right;
    color: red;
}
::v-deep .el-table__expanded-cell {
  padding: 6px 80px;
}


</style>
