<template></template>
<script>
export default {
  created() {
    if (typeof this.$ssrContext !== "undefined") {
      this.$ssrContext.userHeadTags +=
      `<script type='application/ld+json'>
          {
            "@context": "https://schema.org",
            "@type": "Article",
            "headline": "${this.$page.title}",
            "url": "${'https://loopauth.sobercoding.com' + this.$page.path}",
            "image": [
              "https://loopauth.sobercoding.com/logo.png"
              ],
            "author": [{
                "@type": "Person",
                "name": "Sober",
                "url": "https://gitee.com/Chang_Zou"
            }]
          }
      <\/script>`;
    }
  }
};
</script>