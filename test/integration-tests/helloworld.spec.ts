import chai, { expect } from "chai";
import chaiHttp from "chai-http";
import app from "../../src/application";

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
