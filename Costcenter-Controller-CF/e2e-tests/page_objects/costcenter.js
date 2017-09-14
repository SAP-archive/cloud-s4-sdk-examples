module.exports = {
    url: function () {
        return this.api.launchUrl + "/index.html";
    },
    elements: {
        body: 'body',
        create: 
        {
           selector : "//span[text()='Create']",
           locateStrategy: "xpath"
        },
        title: 
        {
           selector : "//span[text()='Cost Center Controller']",
           locateStrategy: "xpath"
        }
    }
};
