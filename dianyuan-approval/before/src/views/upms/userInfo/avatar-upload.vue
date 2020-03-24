<template>
  <div class="components-container">
    <pan-thumb :image="image" />
    <el-button type="text" icon="upload" style="position: absolute;bottom: 15px;margin-left: 40px;" @click="imagecropperShow=true">
      修改头像
    </el-button>
    <image-cropper
      v-show="imagecropperShow"
      :key="imagecropperKey"
      :width="300"
      :height="300"
      :params="{fix:'png'}"
      url="/oss/put"
      lang-type="zh"
      field="file"
      @close="close"
      @crop-upload-success="cropSuccess"
    />
  </div>
</template>

<script>
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
export default {
  name: 'AvatarUpload',
  components: { ImageCropper, PanThumb },
  model: {
    prop: 'value',
    event: 'change'
  },
  props: {
    value: { type: String, default: () => 'https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191' }
  },
  data() {
    return {
      imagecropperShow: false,
      imagecropperKey: 0,
      image: this.value
    }
  },
  watch: { value: function(newval, oldval) {
    this.image = newval
  } },
  methods: {
    cropSuccess(resData) {
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.image = resData
      this.$emit('change', resData)
    },
    close() {
      this.imagecropperShow = false
    }
  }
}
</script>

<style scoped>
  .avatar{
    width: 200px;
    height: 200px;
    border-radius: 50%;
  }
</style>

