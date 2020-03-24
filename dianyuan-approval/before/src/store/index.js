import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import dictionary from './modules/dictionary'
import user from './modules/user'
import tagsView from './modules/tagsView'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    dictionary,
    user,
    tagsView
  },
  getters
})

export default store
