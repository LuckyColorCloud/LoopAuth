module.exports = {
    head: [
        ['link', { rel: 'icon', href: '/logo.png' }]
    ],
    title: 'LoopAuth',
    description: '精简、轻量、更细粒度的Java权限框架',
    themeConfig: {
        nav: [
            { text: '首页', link: '/' },
            { text: '文档', link: '/doc/' },
            { text: '交流群', link: '/doc/#交流群' },
            { text: 'Gitee', link: 'https://gitee.com/lucky-color/loop-auth' },
            { text: 'GitHub', link: 'https://github.com/ChangZou/LoopAuth' },
        ],
        sidebar: [
            {
              title: '写在前面的骚话',    // 标题
              collapsable: false,   // 展开状态
              sidebarDepth: 2,  // 目录深度
              children: [   // 子导航
                '/doc/',
                '/doc/preamble/contribute',
                '/doc/preamble/cite',
              ]
            },
            {
              title: '不看骚话,直接上手',
              sidebarDepth: 2,
              children: [
                '/doc/start/spring',
                '/doc/start/loopAuthFaceImpl',
                '/doc/start/auth',
                '/doc/start/exception',
                '/doc/start/redis',
              ],
              collapsable: false,
            },
            {
                title: '正经内容',
                sidebarDepth: 2,
                children: [
                  '/doc/context/userSession'
                ],
                collapsable: false,
            }
        ],
        
    }
}