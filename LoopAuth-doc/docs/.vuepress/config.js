module.exports = {
    title: 'LoopAuth',
    description: '精简、轻量、更细粒度的Java权限框架',
    themeConfig: {
        nav: [
            { text: '首页', link: '/' },
            { text: '文档', link: '/doc/' },
            { text: 'Gitee', link: 'https://gitee.com/lucky-color/loop-auth' },
            { text: 'GitHub', link: 'https://github.com/ChangZou/LoopAuth' },
        ],
        sidebar: [
            {
              title: '写在前面的骚话',    // 标题
              collapsable: false,   // 展开状态
              children: [   // 子导航
                '/doc/',
                '/doc/preamble/contribute'
              ]
            },
            {
              title: '不看骚话,直接上手',
              children: [
                '/doc/start/cite',
                '/doc/start/spring'
              ],
              collapsable: false,
            }
        ],
        
    }
}