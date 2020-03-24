const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  roles: state => state.user.roles,
  addRouters: state => state.user.addRouters,
  routers: state => state.user.routers,
  cachedViews: state => state.tagsView.cachedViews,
  visitedViews: state => state.tagsView.visitedViews,
  dictData: state => state.dictionary.dictData
}
export default getters
