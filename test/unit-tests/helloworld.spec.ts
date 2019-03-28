import chai, { expect } from "chai";
import { createRequest, createResponse } from "node-mocks-http";
import { spy } from "sinon";
import sinonChai from "sinon-chai";
import { helloWorld } from "../../src/hello-world-route";

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
});
