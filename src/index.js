const app = require("./application");

const port = 8080;

app.listen(port, () => {
  console.log("Express server listening on port " + port);
});
