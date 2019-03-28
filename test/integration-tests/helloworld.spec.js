const chai = require("chai");
const chaiHttp = require("chai-http");
const app = require("../../src/application");

const expect = chai.expect;

chai.use(chaiHttp);

describe("GET /hello", () => {
  it("responds with \"Hello, World!\"", done => {
    chai
      .request(app)
      .get("/hello")
      .end((_, res) => {
        expect(res).to.have.status(200);
        expect(res.text).to.equal("Hello, World!");
        done();
      });
  });
});
