const chai = require("chai");
const { createRequest, createResponse } = require("node-mocks-http");
const { spy } = require("sinon");
const sinonChai = require("sinon-chai");
const { helloWorld, doSomething } = require("../../src/hello-world-route");

const expect = chai.expect;

chai.use(sinonChai);

describe("hello world route", () => {
  it("responds with \"Hello, World!\"", () => {
    const response = createResponse();
    spy(response, "send");
    spy(response, "status");

    helloWorld(createRequest(), response);

    expect(response.status).to.have.been.calledWith(200);
    expect(response.send).to.have.been.calledWith("Hello, World!");
  });

  it("example of test util", done => {
    doSomething(getTestDestinationByAlias("EXAMPLE")).then(response => {
      expect(response).to.equal("Hello");
      done();
    });
  });
});
