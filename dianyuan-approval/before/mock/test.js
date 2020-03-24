export default {
  getInfo: res => {
    const data=res.data;
    if (data) {
      return {
        code: 20000,
        data: data
      }
    }
    return {
      code: 50008,
      message: 'data lost'
    }
  }
}
