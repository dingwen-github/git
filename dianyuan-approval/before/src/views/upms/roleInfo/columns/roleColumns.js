import moment from 'moment'
const roleTypeDict = [{ name: '全局通用', id: 'G' }, { name: '系统固有', id: 'F' }, { name: '系统公用', id: 'P' }]
export const USER_CONFIG = {
  columns: [
    { label: '编码', prop: 'roleCode', sortable: 'custom', minWidth: 110 },
    {
      label: '名称',
      prop: 'roleName',
      sortable: 'custom',
      minWidth: 110
    },
    {
      label: '创建时间',
      prop: 'createDate',
      align: 'center',
      sortable: 'custom',
      minWidth: 110,
      render: (h, params) => {
        const row = params.row
        return h('div', [
          h('span', {}, moment(row.createDate).format('YYYY-MM-DD H:mm:ss'))
        ])
      }
    },
    {
      label: '类型',
      prop: 'roleType',
      align: 'center',
      sortable: 'custom',
      minWidth: 110
    },
    {
      label: '描述',
      prop: 'roleDesc',
      minWidth: 200
    }],
  formConfig: [
    { span: 12, label: '角色代码', prop: 'roleCode', type: 'text', rules: { required: true }},
    { span: 12, label: '角色名称', prop: 'roleName', type: 'text', rules: { required: true }},
    {
      span: 12, label: '角色类型',
      prop: 'roleType',
      type: 'select',
      data: roleTypeDict,
      rules: { required: true, message: '请选择角色', trigger: 'change' }
    },
    { span: 12, label: '角色描述', prop: 'roleDesc', type: 'textarea' }],
  formModel: {
    roleCode: '',
    roleName: '',
    createDate: '',
    roleType: 'G',
    roleDesc: ''
  },
  filterConfig: [
    { label: '角色代码', prop: 's_roleCode', type: 'text' },
    { label: '角色名称', prop: 's_roleName', type: 'text' },
    { label: '角色类型',
      prop: 's_roleType',
      type: 'select',
      data: roleTypeDict },
    { label: '创建时间', prop: 's_createDate', type: 'datepicker' }
  ],
  gridBtnConfig: {
    create: true, update: true, delete: true, view: false,
    expands: [
      { emitName: 'handlePower', name: '编辑权限' }
    ]
  }
}
