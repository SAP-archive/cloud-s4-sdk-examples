const fs = require("fs");
const yaml = require("js-yaml");

if (process.argv.length !== 3) {
  console.error(
    "You have not provided a name to set. Usage: npm run init -- <YOUR-APPNAME>"
  );
  process.exit(1);
}

const name = process.argv.splice(-1)[0];
console.log(`Using ${name} as name...`);

console.log("Setting appname in package.json...");
const packageJson = JSON.parse(fs.readFileSync("./package.json", "utf8"));
packageJson.name = name;
fs.writeFileSync("./package.json", JSON.stringify(packageJson, null, 2) + "\n");

console.log("Setting appname in manifest.yml...");
const manifestYml = yaml.load(fs.readFileSync("./manifest.yml", "utf8"));
manifestYml.applications[0].name = name;
fs.writeFileSync("./manifest.yml", yaml.dump(manifestYml));

console.log("Done!");
