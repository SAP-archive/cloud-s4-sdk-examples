const { resolve } = require("path");

exports.indexRoute = function(req, res) {
  res.sendFile(resolve(__dirname, "../index.html"));
}
