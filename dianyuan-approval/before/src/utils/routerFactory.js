export function constructorRouters(routers) {
  const res = []
  routers.forEach(route => {
    const tmp = { ...route }
    if (tmp.children && tmp.parentId !== '-1' && tmp.children.length > 0) {
      tmp.component = () => import('@/views/dashboard/emptyRouteView')
    } else {
      tmp.component = () => import(`@/views/${route.component}`)
    }

    if (tmp.children) {
      tmp.children = constructorRouters(tmp.children)
    }
    tmp.meta = { 'title': tmp.title, 'icon': tmp.icon }
    res.push(tmp)
  })
  return res
}
