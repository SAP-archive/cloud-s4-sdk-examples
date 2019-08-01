import { getTestDestinationByAlias } from '@sap/cloud-sdk-test-util';
import chai, { expect } from "chai";
import { createRequest, createResponse } from "node-mocks-http";
import { spy } from "sinon";
import sinonChai from "sinon-chai";
import { doSomething, helloWorld } from "../../src/hello-world-route";

chai.use(sinonChai);

describe("hello world route", () => {
  it("responds with \"Hello, World!\"", async () => {
    const response = createResponse();
    spy(response, "send");
    spy(response, "status");

    await helloWorld(createRequest(), response);

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
