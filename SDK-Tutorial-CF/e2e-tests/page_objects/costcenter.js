module.exports = {
    url: function () {
        return this.api.launchUrl;
    },
    elements: {
        body: 'body',
        create: 
        {
           selector : "//button//*[text()='Create']",
           locateStrategy: "xpath"
        },
        title: 
        {
           selector : "//div[contains(@class, 'sapMTitle')]",
           locateStrategy: "xpath"
        }
    }
};
