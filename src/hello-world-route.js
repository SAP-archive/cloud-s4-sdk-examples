const { executeHttpRequest, HttpMethod } = require("@sap/cloud-sdk-core");

async function helloWorld(req, res) {
  await doSomething({
    url: "http://example.com"
  }).then(greeting => {
    res.status(200).send(`${greeting}, World!`);
  });
}

function doSomething(destination) {
  return executeHttpRequest(destination, {
    method: HttpMethod.GET
  })
  .then(() => "Hello")
  .catch(() => "Bye");
}

exports.helloWorld = helloWorld;
exports.doSomething = doSomething;
