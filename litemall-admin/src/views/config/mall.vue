<template>
  <div class="app-container mini-container">
    <el-form ref="dataForm" :rules="rules" :model="dataForm" status-icon label-width="300px">
      <el-form-item :label="$t('config_mall.form.mall_name')" prop="litemall_mall_name">
        <el-input v-model="dataForm.litemall_mall_name"/>
      </el-form-item>
      <el-form-item :label="$t('config_mall.form.mall_desc')" prop="litemall_mall_desc">
        <el-input v-model="dataForm.litemall_mall_desc"/>
      </el-form-item>
      <el-form-item :label="$t('config_mall.form.mall_business_hours')">
        <el-col :span="11">
          <el-input v-model="dataForm.litemall_mall_business_start_time" :placeholder="$t('config_mall.placeholder.litemall_mall_business_start_time')" />
        </el-col>
        <el-col :span="2" style="text-align: center;">-</el-col>
        <el-col :span="11">
          <el-input v-model="dataForm.litemall_mall_business_end_time" :placeholder="$t('config_mall.placeholder.litemall_mall_business_end_time')" />
        </el-col>
      </el-form-item>
      <el-form-item :label="$t('config_mall.form.mall_address')" prop="litemall_mall_address">
        <el-input v-model="dataForm.litemall_mall_address"/>
      </el-form-item>
      <el-form-item :label="$t('config_mall.form.mall_coordinates')">
        <el-col :span="11">
          <el-input v-model="dataForm.litemall_mall_longitude" :placeholder="$t('config_mall.placeholder.mall_longitude')" />
        </el-col>
        <el-col :span="2" style="text-align: center;">-</el-col>
        <el-col :span="11">
          <el-input v-model="dataForm.litemall_mall_latitude" :placeholder="$t('config_mall.placeholder.mall_latitude')" />
        </el-col>
      </el-form-item>
      <el-form-item :label="$t('config_mall.form.mall_phone')" prop="litemall_mall_phone">
        <el-input v-model="dataForm.litemall_mall_phone"/>
      </el-form-item>
      <el-form-item :label="$t('config_mall.form.mall_qq')" prop="litemall_mall_qq">
        <el-input v-model="dataForm.litemall_mall_qq"/>
      </el-form-item>
      <el-form-item :label="$t('config_mall.form.mall_banner')" prop="litemall_mall_banner">
        <el-upload
          :action="uploadPath"
          :show-file-list="false"
          :headers="headers"
          :on-success="uploadBanner"
          class="avatar-uploader banner"
          accept=".jpg,.jpeg,.png,.gif"
        >
          <img :src="dataForm.litemall_mall_banner" class="avatar" v-if="dataForm.litemall_mall_banner">
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </el-form-item>
      <el-form-item :label="$t('config_mall.form.mall_logo')" prop="litemall_mall_logo">
        <el-upload
          :action="uploadPath"
          :show-file-list="false"
          :headers="headers"
          :on-success="uploadLogo"
          class="avatar-uploader"
          accept=".jpg,.jpeg,.png,.gif"
        >
          <img :src="dataForm.litemall_mall_logo" class="avatar" v-if="dataForm.litemall_mall_logo">
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </el-form-item>

       <el-form-item :label="$t('config_mall.form.mall_notice')">
          <el-input type="textarea" v-model="dataForm.litemall_mall_notice"></el-input>
        </el-form-item>

      <el-form-item>
        <el-button @click="cancel">{{ $t('app.button.cancel') }}</el-button>
        <el-button type="primary" @click="update">{{ $t('app.button.confirm') }}</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { listMall, updateMall } from '@/api/config'
import { createStorage, uploadPath } from '@/api/storage'
import { getToken } from '@/utils/auth'
export default {
  name: 'ConfigMail',
  data() {
    return {
      dataForm: {
        litemall_mall_name: '',
        litemall_mall_address: '',
        litemall_mall_phone: '',
        litemall_mall_qq: '',
        litemall_mall_longitude: '',
        litemall_mall_latitude: '',
        litemall_mall_banner:'',
        litemall_mall_logo:'',
        litemall_mall_desc:'',
        litemall_mall_notice:'',
        litemall_mall_business_start_time: '',
        litemall_mall_business_end_time: ''
      },
      rules: {
        litemall_mall_name: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        litemall_mall_business_start_time: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        litemall_mall_business_end_time: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        litemall_mall_address: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        litemall_mall_phone: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        litemall_mall_qq: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        litemall_mall_banner: [
          { required: true, message: '不能为空', trigger: 'change' }
        ],
        litemall_mall_logo: [
          { required: true, message: '不能为空', trigger: 'change' }
        ],
      },
      uploadPath:uploadPath,
    }
  },
  computed: {
    headers() {
      return {
        'X-Litemall-Admin-Token': getToken()
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init: function() {
      listMall().then(response => {
        this.dataForm = response.data.data
      })
    },
    uploadBanner: function(response) {
      this.dataForm.litemall_mall_banner = response.data.url
    },
    uploadLogo: function(response) {
      this.dataForm.litemall_mall_logo = response.data.url
    },
    cancel() {
      this.init()
    },
    update() {
      this.$refs['dataForm'].validate((valid) => {
        if (!valid) {
          return false
        }
        this.doUpdate()
      })
    },
    doUpdate() {
      updateMall(this.dataForm)
        .then(response => {
          this.$notify.success({
            title: '成功',
            message: '商场配置成功'
          })
        })
        .catch(response => {
          this.$notify.error({
            title: '失败',
            message: response.data.errmsg
          })
        })
    }
  }
}
</script>
<style>
.avatar-uploader .el-upload {
  width: 145px;
  height: 145px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #20a0ff;
}


.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 145px;
  height: 145px;
  line-height: 145px;
  text-align: center;
}

.avatar-uploader.banner .el-upload{
  width: 300px;
}

.avatar {
  width: 100%;
  height: 100%;
  display: block;
}
</style>
