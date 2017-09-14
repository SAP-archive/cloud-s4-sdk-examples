"use strict";

const chromedriver = require("chromedriver");

module.exports = {
    before: function (done) {
        console.log(chromedriver.path);
        chromedriver.start();
        done();
    },
    after: function (done) {
        chromedriver.stop();
        done();
    }
};
