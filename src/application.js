const express = require("express");
const { helloWorld } = require("./hello-world-route");
const { indexRoute } = require("./index-route");

class App {
  constructor() {
    this.app = express();
    this.config();
    this.routes();
  }

  config() {
    this.app.use(express.json());
    this.app.use(express.urlencoded({ extended: false }));
  }

  routes() {
    const router = express.Router();

    router.get("/", indexRoute);
    router.get("/hello", helloWorld);
    this.app.use("/", router);
  }
}

module.exports = new App().app;
