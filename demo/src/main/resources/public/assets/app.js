window.eruptSiteConfig = {
    //global config
    domain: "",
    title: "DEMO",
    desc: "ERUPT 演示系统",
    routerReuse: false,
    amapKey: "15bc4df0bd6758645308fa591f439799",
    logoPath: "/assets/logo.svg",
    r_tools: [{
        text: "功能按钮",
        icon: "fa-eercast",
        mobileHidden: true,
        click: function (event, token) {
            console.log(event);
            console.log(token);
        },
        load: function () {
            console.log("loaded")
        }
    }]
};