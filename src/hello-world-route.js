const { executeHttpRequest, HttpMethod } = require('@sap/cloud-sdk-core');

exports.helloWorld = async function(req, res) {
  await doSomething({
    url: "http://example.com"
  }).then(greeting => {
    res.status(200).send(`${greeting}, World!`);
  });
}

exports.doSomething = function (destination) {
  return executeHttpRequest(destination, {
    method: HttpMethod.GET
  })
  .then(() => "Hello")
  .catch(() => "Bye");
}
