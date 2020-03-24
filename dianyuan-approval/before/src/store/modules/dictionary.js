const dictionary = {
  state: {
    dictData: {}
  },
  mutations: {
    PUT_DICTDATA: (state, dictData) => {
      const data = {}
      dictData.datas.some(function(item, index) {
        data[item.dataCode] = item
      })
      state.dictData[dictData.code] = data
    }
  },
  actions: {
    storeDictionary({ commit }, data) {
      commit('PUT_DICTDATA', data)
    }
  }
}

export default dictionary
